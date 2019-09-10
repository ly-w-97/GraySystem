package com.huangyuan.open.gray.common.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

/**
 * 通用处理代码
 * @author huangy on 2019/1/12
 */
public class CommonUitl {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUitl.class);

    /**
     *  获取给定的接口名称
     */
    public static <T> String getInterface(Invoker<T> invoker, Invocation invocation) {
        return getClass(invoker) + "#" + invocation.getMethodName();
    }

    /**
     * 获取消费的接口
     *
     * @param invoker 调用者
     * @param <T>      参数类型
     * @return <T>
     */
    public static  <T> String getClass(Invoker<T> invoker) {
        if (invoker == null) {
            return "";
        }

        try {
            return invoker.getInterface().getName();

        } catch (Exception e) {
            LOGGER.error("getApplicationName fail, please check, invokers={}", invoker, e);
            return "";
        }
    }

    /**
     * aop中获取接口名称
     */
    public static String getInterfaceInAop(ProceedingJoinPoint point) {
        String className = point.getTarget().getClass().getSimpleName();
        if (className.endsWith("Impl")) {
            className = className.substring(0, className.length() - 4);
        }

        final String methodName = point.getSignature().getName();

        return className + "#" + methodName;
    }
}
