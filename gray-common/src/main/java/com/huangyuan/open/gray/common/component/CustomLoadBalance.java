package com.huangyuan.open.gray.common.component;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

import java.util.List;

/**
 * @author huangy on 2018/9/11
 */
public interface CustomLoadBalance {

    <T> Invoker<T> superFilterSelect(List<Invoker<T>> invokers, URL url, Invocation invocation);

}
