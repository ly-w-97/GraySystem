package com.huangyuan.open.gray.proxy.provider.aop;

import com.facishare.eservice.base.result.EserviceResult;
import com.facishare.eservice.common.interceptor.ResultFilter;
import com.github.trace.TraceContext;
import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.*;

/**
 * Author: Ansel Qiao Create Time: 15/7/26
 */
@Component
public class LogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    public Object around(ProceedingJoinPoint point) throws Throwable {

        String requestId = getShortUUID();
        MDC.put("requestId", requestId);
        MDC.put("traceId", TraceContext.get() == null? "" : TraceContext.get().getTraceId());

        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        String sigName = className + "." + methodName;

        long startTime = System.currentTimeMillis();
        LOGGER.info("==== Start [{}]: params[{}]", sigName, Arrays.toString(point.getArgs()));

        Object result = point.proceed();

        long endTime = System.currentTimeMillis();
        final long cost = endTime - startTime;
        LOGGER.info("==== End [{}]: params[{}], result[{}], cost[{}]", sigName,
                Arrays.toString(point.getArgs()), result, cost);

        setTranceId(result, requestId);

        return result;
    }

    /**
     * 获取一个UUID一半长度的字符串，取其奇数位上的字符.
     */
    private static String getShortUUID() {
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final char[] chars = new char[uuid.length() / 2];
        for (int i = 0; i < uuid.length(); i++) {
            if (i % 2 == 0) {
                chars[i / 2] = uuid.charAt(i);
            }
        }
        return new String(chars);
    }

    private void setTranceId(Object result, String requestId) {
        try {
            if (result == null) {
                return;
            }

            if (!(result instanceof EserviceResult)) {
                return;
            }

            String traceId = TraceContext.get() == null? "" : TraceContext.get().getTraceId();
            List<String> serverIpList = getLocalIPList();
            String serverIp = CollectionUtils.isEmpty(serverIpList)? "" : serverIpList.get(0);
            String someMsg = String.format("cases-provider Server: %s, traceId: %s, requestId: %s", serverIp, traceId, requestId);

            EserviceResult newModelResult = (EserviceResult)result;
            newModelResult.setTraceMsg(someMsg);
        } catch (Exception e) {
            LOGGER.warn("service back tranceId error: ", e);
        }

    }

    private static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) { // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }

}
