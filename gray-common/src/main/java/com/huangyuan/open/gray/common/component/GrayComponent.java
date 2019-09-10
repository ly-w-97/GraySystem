package com.huangyuan.open.gray.common.component;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.facishare.eservice.base.result.EserviceResult;
import com.huangyuan.open.gray.common.support.GrayConfigHepler;
import com.huangyuan.open.gray.common.support.GrayHandlerHelper;
import com.huangyuan.open.gray.common.utils.CommonUitl;
import com.facishare.wechat.gray.api.service.GrayApplicationHandlerService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 灰度组件
 * @author huangy on 2018/8/20
 */
@Component
public class GrayComponent {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrayComponent.class);

    @Resource
    private GrayApplicationHandlerService grayApplicationHandlerService;
    @Resource
    private GrayHandlerHelper grayHandlerHelper;
    @Resource
    private GrayConfigHepler grayConfigHepler;

    /**
     * 判断企业是否灰度企业
     *      1、是灰度企业，选择灰度服务
     *          1.1、若灰度服务的个数大于1，再根据传入的负载均衡类进行过滤
     *          1.2、若灰度服务的个数为0，使用正常服务运行
     *      2、不是灰度企业，选择非灰度服务，个数大于1，再根据传入的负载均衡类进行过滤
     *
     * @param invokers 初始的invokers队列
     * @param url dubbo调用url
     * @param invocation 远程调用参数
     * @param fsEa 访问人员所属企业
     * @param loadBalance 负载均衡对象，如果经过选择后，可用invoker数量大于1，再使用该对象进行筛选
     * @return 符合条件的invoker
     */
    public <T> Invoker<T> selectByEa(List<Invoker<T>> invokers, URL url, Invocation invocation,
                                          String fsEa, CustomLoadBalance loadBalance) {

        // 临时invoker数组，里面可能是正常invoker、也可能是灰度invoker，具体看处理逻辑
        List<Invoker<T>> temInvokers = new ArrayList<>();

        // 获取服务名称
        String providerApplicationName = getProviderApplicationName(invokers);

        // 判断是否走灰度
        boolean tag = justGray(fsEa, invokers, invocation, providerApplicationName);

        // 根据灰度标志进行筛选
        return filterSelect(invokers, url, invocation, loadBalance, providerApplicationName, temInvokers, tag);
    }

    /**
     * 1、如果当前机器上的当前服务是灰度服务，那么往下调用的下一个服务，也是灰度服务
     * 2、如果往下层的调用没有灰度服务，那么使用正常服务
     * 3、经过筛选以后，服务数量大于1，使用传入的loadBalance进行筛选
     *
     * @param invokers 初始的invokers队列
     * @param url dubbo调用url
     * @param invocation 远程调用参数
     * @param loadBalance 负载均衡对象，如果经过选择后，可用invoker数量大于1，再使用该对象进行筛选
     * @return 符合条件的invoker
     */
    public <T> Invoker<T> selectByIpAndApplication(List<Invoker<T>> invokers, URL url, Invocation invocation,
                                                   CustomLoadBalance loadBalance) {

        // 临时invoker数组，里面可能是正常invoker、也可能是灰度invoker，具体看处理逻辑
        List<Invoker<T>> temInvokers = new ArrayList<>();

        // 获取消费方的applicationName
        String consumerApplicationName = getConsumerApplicationName(invokers);

        // 获取提供方的applicationName
        String providerApplicationName = getProviderApplicationName(invokers);

        boolean tag;

        /*
         * 判断调用服务是否 必须 走灰度
         */
        tag = justInterfaceGray(invokers, invocation);

        if (!tag) {
            // 不是必须走灰度，再根据当前消费者判断，判断规则如下

            /*
             * 获取该服务的灰度机器列表，并且判断本机器在灰度机器列表中
             * 在，说明这台机器上这个服务（消费者）是灰度服务
             * 不在，说明这台机器上这个服务（消费者）不是灰度服务
             */
            tag = grayHandlerHelper.justGaryByIpAndApplication(consumerApplicationName);
        }

        return filterSelect(invokers, url, invocation, loadBalance, providerApplicationName, temInvokers, tag);
    }

    /**
     * 设置正常服务的group为空字符串。
     *          因为一个服务只有1个provider的情况下，不走loadBalance，但是group是*的情况下，无法调用provider，
     *          因此正常服务，需要设置group的值为空串，这样子在provider端，就不会把group的值拼成key了
     * 先到loadBalance再到filter，在filter里面设置*（正常服务的group）为空字符串
     * @param invocation 远程调用参数
     */
    public static void setFormalServiceGroup(Invocation invocation) {
        try {
            Map<String, String> attachments = invocation.getAttachments();
            String group = attachments.get("group");

            /*
             * size = 0，表示没有经过loadBalance的正常服务
             * (StringUtils.isNotEmpty(group) && "*".equals(group)) 这个操作是为了多一重保障
             */
            if ((attachments.size() == 0) || (StringUtils.isNotEmpty(group) && "*".equals(group))) {
                attachments.put("group", "");
            } else {
//                LOGGER.info("convertGroupInfo, not set group empty string, invocation={}", invocation);
            }
        } catch (Exception e) {
            LOGGER.error("convertGroupInfo fail, invocation={}", invocation, e);
        }
    }

    private void setGroupInfo(Invocation invocation, String value) {
        try {
            Map<String, String> attachments = invocation.getAttachments();
            attachments.put("group", value);

        } catch (Exception e) {
            LOGGER.error("AppointLoadBalance : setGroup fail : invocation={}", invocation, e);
        }
    }

    // 选择灰度服务
    private <T> void findGrayService(List<Invoker<T>> invokers, List<Invoker<T>> grayInvokers, String grayGroup) {
        for (Invoker<T> invoker : invokers) {
            try {
                Field field = invoker.getClass().getDeclaredField("providerUrl");
                field.setAccessible(true);

                URL url = (URL) field.get(invoker);
                String group = url.getParameters().get("default.group");
                if (StringUtils.isNotEmpty(group) && grayGroup.equalsIgnoreCase(group)) {
                    grayInvokers.add(invoker);
                }

            } catch (Exception e) {
                LOGGER.error("getGrayService fail : invoker={}", invoker, e);
            }
        }

    }

    /**
     * 选择正式服务
     *
     * @param invokers       调用者
     * @param formalInvokers 正式服务
     * @param <T>            <T>
     */
    private <T> void findFormalService(List<Invoker<T>> invokers, List<Invoker<T>> formalInvokers) {
        for (Invoker<T> invoker : invokers) {
            try {
                Field field = invoker.getClass().getDeclaredField("providerUrl");
                field.setAccessible(true);

                URL url = (URL) field.get(invoker);
                String group = url.getParameters().get("default.group");
                if (StringUtils.isEmpty(group)) {
                    formalInvokers.add(invoker);
                }

            } catch (Exception e) {
                LOGGER.error("getGrayService fail : invoker={}", invoker, e);
            }
        }
    }


    /**
     * 判断该企业是否灰度企业
     *
     * @param fsEa                    企业账号
     * @param providerApplicationName 应用名称
     * @return boolean
     */
    private boolean checkGrayFsEa(String fsEa, String providerApplicationName) {

        try {

            EserviceResult<Boolean> modelResult = grayApplicationHandlerService.checkGrayFsEa(providerApplicationName, fsEa);
            if (modelResult == null) {
                LOGGER.error("checkGrayFsEa : grayApplicationHandlerService.checkGrayFsEa, fsEa={}", fsEa);
                return false;
            } else if (!modelResult.isSuccess() || (modelResult.getData() == null)) {
                LOGGER.warn("checkGrayFsEa : grayApplicationHandlerService.checkGrayFsEa, fsEa={}, modelResult={}",
                        fsEa, modelResult);
                return false;
            } else {
                Boolean result = modelResult.getData();
                if (result) {
//                    LOGGER.info("checkGrayFsEa : the fsEa is gray ea, fsEa={}", fsEa);
                } else {
//                    LOGGER.info("checkGrayFsEa : the fsEa is not gray ea, fsEa={}", fsEa);
                }
                return result;
            }

        } catch (Exception e) {
            LOGGER.error("checkGrayFsEa fail, fsEa={}, providerApplicationName={}",
                    fsEa, providerApplicationName, e);
            // 降级，走正常服务
            return false;
        }
    }


    /**
     * 获取provider的applicationName属性值
     *
     * @param invokers 调用者
     * @param <T>      参数类型
     * @return <T>
     */
    private <T> String getProviderApplicationName(List<Invoker<T>> invokers) {
        if (CollectionUtils.isEmpty(invokers)) {
            return "";
        }

        try {
            Invoker<T> invoker = invokers.get(0);
            Field field = invoker.getClass().getDeclaredField("providerUrl");
            field.setAccessible(true);

            URL url = (URL) field.get(invoker);
            return url.getParameters().get("application");

        } catch (Exception e) {
            LOGGER.error("getApplicationName fail, please check, invokers={}", invokers, e);
            return "";
        }
    }


    /**
     * 获取consumer的applicationName
     *
     * @param invokers 调用者
     * @param <T>      参数类型
     * @return <T>
     */
    private <T> String getConsumerApplicationName(List<Invoker<T>> invokers) {
        if (CollectionUtils.isEmpty(invokers)) {
            return "";
        }

        try {
            Invoker<T> invoker = invokers.get(0);
            return invoker.getUrl().getParameters().get("application");

        } catch (Exception e) {
            LOGGER.error("getConsumerApplicationName fail, please check, invokers={}", invokers, e);
            return "";
        }
    }

    /**
     * 根据灰度标志，筛选服务
     * @param invokers 初始的invokers列表
     * @param url dubbo调用url
     * @param invocation 远程调用参数
     * @param loadBalance 负载均衡对象，如果经过选择后，可用invoker数量大于1，再使用该对象进行筛选
     * @param providerApplicationName 提供方的applicationName
     * @param temInvokers 临时invokers列表
     * @param tag 是否灰度标志   true灰度  false正式
     * @return 经过筛选后的invoker
     */
    private <T> Invoker<T> filterSelect(List<Invoker<T>> invokers, URL url, Invocation invocation,
                                        CustomLoadBalance loadBalance, String providerApplicationName,
                                        List<Invoker<T>> temInvokers, boolean tag) {
        // tag标志判断是否灰度
        if (tag) {

            // 查询该服务对应的灰度group
            String grayGroup = grayHandlerHelper.getGrayGroup(providerApplicationName);
            if (StringUtils.isNotEmpty(grayGroup)) {
//                LOGGER.info("select gray provider");
                // 选择灰度服务
                findGrayService(invokers, temInvokers, grayGroup);
            }

            if (CollectionUtils.isEmpty(temInvokers)) {

//                LOGGER.info("select gray provider but no exists, select formal provider");

                // 这个服务没有灰度服务，降级，使用正常服务
                findFormalService(invokers, temInvokers);
                setGroupInfo(invocation, "");

            } else {
                // 已经筛选出灰度服务，把url字段的group改成灰度值
                setGroupInfo(invocation, grayGroup);
            }

        } else {
//            LOGGER.info("select formal provider");
            // 选择正常服务
            findFormalService(invokers, temInvokers);

            // 筛选出正常服务，把url字段的group值设置为空字符串
            setGroupInfo(invocation, "");
        }

        // 到这里，正常情况下，肯定已经筛选出invoker列表了
        if (CollectionUtils.isEmpty(temInvokers)) {
            // 如果还没有服务，可能是别的问题，直接调用传入的负载均衡
            LOGGER.error("componentSelect fail, please check, providerApplicationName={}", providerApplicationName);
            return loadBalance.superFilterSelect(invokers, url, invocation);

        } else {
            if (temInvokers.size() == 1) {
                return temInvokers.get(0);
            } else {
                // 选出的服务如果大于1，再进行负载均衡策略
                return loadBalance.superFilterSelect(temInvokers, url, invocation);
            }
        }
    }

    /**
     * 获取消费的接口
     *
     * @param invokers 调用者
     * @param <T>      参数类型
     * @return <T>
     */
    private <T> String getInterface(List<Invoker<T>> invokers, Invocation invocation) {
        if (CollectionUtils.isEmpty(invokers)) {
            return "";
        }

        try {
            Invoker<T> invoker = invokers.get(0);
            return CommonUitl.getInterface(invoker, invocation);
        } catch (Exception e) {
            LOGGER.error("getApplicationName fail, please check, invokers={}", invokers, e);
            return "";
        }
    }

    /**
     * 没有身份态的情况下，判断该接口是否需要走灰度
     * 如果在灰度服务列表中，走灰度，否则走非灰度
     */
    private <T> boolean justInterfaceGray(List<Invoker<T>> invokers, Invocation invocation) {
        try {
            String _Interface = getInterface(invokers, invocation);
            List<String> getGrayInterfaces = grayConfigHepler.getGrayInterfaceList();
            if (CollectionUtils.isEmpty(getGrayInterfaces)) {
                LOGGER.info("getGrayInterfaces is empty, run formal provider");
                return false;
            }
            return  getGrayInterfaces.contains("all") || getGrayInterfaces.contains(_Interface);

        } catch (Exception e) {
            LOGGER.error("justInterfaceGray fail, invokers={}, invocation={}", invokers, invocation, e);
            // 降级，走正常服务
            return false;
        }
    }

    /**
     * 判断是否走灰度
     */
    private <T> boolean justGray(String fsEa, List<Invoker<T>> invokers,
                                 Invocation invocation, String providerApplicationName) {
        if (StringUtils.isEmpty(fsEa)) {
            // 没有身份态，判断是否需要走灰度服务
            return justInterfaceGray(invokers, invocation);

        } else {
            boolean tag;

            // 有身份态，有些服务也只能走灰度服务，因此需要进行判断
            tag = justInterfaceGray(invokers, invocation);
            if (tag) {
                return true;
            }

            // 如果没有强制走灰度服务，则再根据身份态判断
            return checkGrayFsEa(fsEa, providerApplicationName);
        }
    }
}