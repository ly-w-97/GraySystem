package com.huangyuan.open.gray.common.component;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;

/**
 * 关于该filter
 * 由于测试环境的服务一般只有1个，因此不会经过loadBalance，由于group设置为*（dubbo源码里面，把*当做key的一部分来获取提供方的invoker），因此无法调用正常服务，
 * 因此该filter用于将非灰度服务的invoker的group重新设置为空，消费方只有1个时，调用方才可以进行正常的服务调用
 * 该filter提高了灰度系统的兼容性
 *
 * 执行流程 loadBalance ——》filter
 *
 * 用于提供者端
 *
 * @author huangy on 2018/9/4
 */
@Activate
public class DubboGrayFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

        // 设置正常服务的group
        GrayComponent.setFormalServiceGroup(invocation);

        return invoker.invoke(invocation);
    }
}
