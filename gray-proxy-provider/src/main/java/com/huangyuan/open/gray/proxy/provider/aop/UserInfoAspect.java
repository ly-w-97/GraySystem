package com.huangyuan.open.gray.proxy.provider.aop;

import com.facishare.eservice.cases.api.utils.FsUserVoUtils;
import com.huangyuan.open.gray.proxy.provider.component.UserContextHolder;
import com.facishare.open.common.model.FsUserVO;
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
		FsUserVO fsUserVO = FsUserVoUtils.getFsUserVo(point.getArgs());
		if (fsUserVO != null) {
			UserContextHolder.getFsUserVOHolder().set(fsUserVO);
		}
	}

	private void clearFsUserVO() {
		UserContextHolder.resetFsUserVO();
	}
}
