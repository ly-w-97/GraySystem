package com.huangyuan.open.gray.proxy.provider.component;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.cluster.loadbalance.LeastActiveLoadBalance;
import com.huangyuan.open.gray.common.component.CustomLoadBalance;
import com.huangyuan.open.gray.common.component.GrayComponent;
import com.huangyuan.open.gray.common.utils.SpringContextUtil;
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

        // TODO by huangy on 2019-09-16. 获取到你们的身份信息（我们使用企业来区分是否灰度）
        String fsEa = null;

        GrayComponent grayComponent = SpringContextUtil.getBean("grayComponent");

        return grayComponent.selectByEa(invokers, url, invocation, fsEa, this);
    }

    @Override
    public<T> Invoker<T> superFilterSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        return super.doSelect(invokers, url, invocation);
    }
}