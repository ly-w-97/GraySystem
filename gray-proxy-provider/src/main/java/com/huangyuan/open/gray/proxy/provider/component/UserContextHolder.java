package com.huangyuan.open.gray.proxy.provider.component;

import com.facishare.open.common.model.FsUserVO;
import org.springframework.core.NamedThreadLocal;

/**
 * 线程变量
 * （1）在aop中获取身份信息，写入该线程变量
 * （2）在请求执行完毕后，清空身份信息
 */
public class UserContextHolder {

    private static final ThreadLocal<FsUserVO> fsUserVOHolder =
            new NamedThreadLocal<>("FsUserVO");

    public static void resetFsUserVO() {
        fsUserVOHolder.remove();
    }

    public static ThreadLocal<FsUserVO> getFsUserVOHolder() {
        return fsUserVOHolder;
    }


    public static FsUserVO getFsUserVO() {
        return fsUserVOHolder.get();
    }

}
