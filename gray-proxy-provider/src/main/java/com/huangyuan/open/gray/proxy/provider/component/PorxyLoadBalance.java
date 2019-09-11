package com.huangyuan.open.gray.proxy.provider.component;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.cluster.loadbalance.LeastActiveLoadBalance;
import com.facishare.eservice.common.component.CustomLoadBalance;
import com.facishare.eservice.common.component.GrayComponent;
import com.facishare.eservice.common.utils.SpringContextUtil;
import com.facishare.open.common.model.FsUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author huangy on 2018/8/20
 */
public class PorxyLoadBalance extends LeastActiveLoadBalance implements CustomLoadBalance {

    private static final Logger LOGGER = LoggerFactory.getLogger(PorxyLoadBalance.class);

    @Override
    protected<T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {

        ThreadLocal<FsUserVO> fsUserVOHolder = UserContextHolder.getFsUserVOHolder();
        FsUserVO fsUserVO = fsUserVOHolder.get();
        String fsEa = null;

        LOGGER.info("PorxyLoadBalance doSelect : fsUserVO={}", fsUserVO);

        if (fsUserVO == null) {
            // 获取不到企业信息，不进行灰度（设置空字符串，就查找不到灰度信息，会选择正常服务，并且设置正常服务的invocation）
            fsEa = "";
        } else {
            fsEa = fsUserVO.getEnterpriseAccount();
        }
        GrayComponent grayComponent = SpringContextUtil.getBean("grayComponent");

        return grayComponent.selectByEa(invokers, url, invocation, fsEa, this);
    }

    @Override
    public<T> Invoker<T> superFilterSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        return super.doSelect(invokers, url, invocation);
    }
}