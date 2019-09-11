package com.huangyuan.open.gray.proxy.provider.aop;

import com.facishare.eservice.base.exception.EserviceException;
import com.facishare.eservice.base.result.EserviceResult;
import com.facishare.eservice.cases.api.constant.SMErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Created by guom on 2018/7/4.
 */
@Component
public class ExceptionAspect {

	/**
	 * 一般业务异常日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionAspect.class);
	
    public Object around(ProceedingJoinPoint point) throws Throwable {
    	
    	try {
    		return point.proceed();
    	} catch (EserviceException e) {
    		warnLog(point, e);
            return new EserviceResult<>(e.getErrCode(), e.getErrMsg());
        } catch (Exception e) {
			LOGGER.error("An exception occurred, method[{}], params[{}]",
                    point.getSignature().getName(), point.getArgs(), e);
            return new EserviceResult<>(SMErrorCode.SYSTEM_ERROR);
        }
    }
    
    private void warnLog(ProceedingJoinPoint point, EserviceException e) {
		LOGGER.warn("An ProxyException occurred, method[{}], params[{}]",
                point.getSignature().getName(), point.getArgs(), e);
    }
}
