package com.huangyuan.open.gray.proxy.provider.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class UserInfoAspect {
	
    public Object around(ProceedingJoinPoint point) throws Throwable {

		// 埋入身份信息
		buryFsUserVO(point);

    	Object object = point.proceed();

    	// 清空身份信息
		clearFsUserVO();

    	return object;
    }

    private void buryFsUserVO(ProceedingJoinPoint point) {
		// TODO by huangy on 2019-09-16. 埋入身份信息
	}

	private void clearFsUserVO() {
		// TODO by huangy on 2019-09-16. 清空线程变量的身份信息
	}
}
