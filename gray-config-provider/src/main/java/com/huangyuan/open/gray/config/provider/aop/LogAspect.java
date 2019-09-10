package com.huangyuan.open.gray.config.provider.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.UUID;

/**
 * Author: Ansel Qiao Create Time: 15/7/26
 */
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    public Object around(ProceedingJoinPoint point) throws Throwable {

        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        String sigName = className + "." + methodName;

        LOGGER.info("==== Start [{}]:  params[{}]", sigName, Arrays.toString(point.getArgs()));

        long startTime = System.currentTimeMillis();
        final Object result = point.proceed();
        long endTime = System.currentTimeMillis();
        final long cost = endTime - startTime;


        LOGGER.info("==== End [{}]:  params[{}], result[{}], cost[{}]", sigName,
                Arrays.toString(point.getArgs()), result, cost);

        return result;
    }

}
