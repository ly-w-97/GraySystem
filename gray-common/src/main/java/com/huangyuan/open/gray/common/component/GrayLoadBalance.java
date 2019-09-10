package com.huangyuan.open.gray.common.component;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.cluster.loadbalance.LeastActiveLoadBalance;
import com.huangyuan.open.gray.common.utils.SpringContextUtil;

import java.util.List;

/**
 * 灰度负载均衡（用于非web、fcp项目）
 * @author huangy on 2018/8/20
 */
public class GrayLoadBalance extends LeastActiveLoadBalance implements CustomLoadBalance {

    @Override
    protected<T> Invoker<T> doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {

        GrayComponent grayComponent = SpringContextUtil.getBean("grayComponent");

        return grayComponent.selectByIpAndApplication(invokers, url, invocation, this);
    }

    @Override
    public <T> Invoker<T> superFilterSelect(List<Invoker<T>> invokers, URL url, Invocation invocation) {
        return super.doSelect(invokers, url, invocation);
    }
}

