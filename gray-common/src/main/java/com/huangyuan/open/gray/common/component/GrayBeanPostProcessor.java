package com.huangyuan.open.gray.common.component;

import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProviderConfig;
import com.huangyuan.open.gray.common.support.GrayHandlerHelper;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * bean后置处理器，用于动态设置提供者的group
 *
 * 利用dubbo的group属性区分灰度环境和正式环境
 *
 * @author huangy on 2018/8/31
 */
@Component
public class GrayBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrayBeanPostProcessor.class);

    /**
     * 服务名称
     */
    @Value("${dubbo.application.name}")
    private String applicationName;

    private static final String DEFAULT_GRAY_FILTER = "grayFilter";

    @Resource
    private GrayHandlerHelper grayHandlerHelper;

    @Resource
    private ConsumerConfig consumerConfig;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        // 初始化提供者的配置
        initProvider(bean);

        // 初始化消费者配置
        initConsumer();

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private void initProvider(Object bean) {

        try {

            // 只需设置ProviderConfig对象，所有的interface默认使用ProviderConfig的参数
            if (bean instanceof ProviderConfig) {

                // 通过 服务、机器，判断是否需要灰度分组
                String group = grayHandlerHelper.justGrayAndGetGroup(applicationName);

                if (StringUtils.isNotEmpty(group)) {

                    // 灰度机器，初始化provider的通用配置
                    ProviderConfig providerConfig = (ProviderConfig)bean;
                    providerConfig.setGroup(group);
                }
            }

        } catch (Exception e) {
            LOGGER.error("initProvider fail, can not set provider, please check", e);
        }
    }

    private void initConsumer() {
        try {

            // 设置灰度过滤器
            String filter = consumerConfig.getFilter();
            if (StringUtils.isEmpty(filter)) {
                consumerConfig.setFilter(DEFAULT_GRAY_FILTER);

            } else if (!filter.contains(DEFAULT_GRAY_FILTER)) {
                // 兼容已有filter的情况，并且filter里面不包含grayFilter
                filter = connectFilter(filter);
                consumerConfig.setFilter(filter);
            } else {
//                LOGGER.info("GrayBeanPostProcessor has contain gray filter");
            }

            // 设置分组
            consumerConfig.setGroup("*");

            // 设置负载均衡策略
            consumerConfig.setLoadbalance("grayLoadBalance");

        } catch (Exception e) {
            LOGGER.error("initConsumer fail, can not set consumer, please check", e);
        }
    }

    private String connectFilter(String originFilter) {
        return originFilter + "," + DEFAULT_GRAY_FILTER;
    }
}