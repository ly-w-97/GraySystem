package com.huangyuan.open.gray.config.api.service;

import com.huangyuan.open.gray.base.result.EserviceResult;
import com.huangyuan.open.gray.config.api.model.arg.UpdateGrayIpListArg;

/**
 * 灰度支持接口
 * @author huangy on 2019-08-02
 */
public interface GraySupportService {

    /**
     * 更新redis中 某个应用的灰度机器列表
     * （1）如果灰度总开关是关闭状态，直接清空该应用的key
     * （2）否则，如果该应用的灰度开关是关闭状态，直接清空该应用的key
     * （3）设置该应用的key，value为灰度ip列表
     * @param arg applicationName必填
     * @return 更新结果
     */
    EserviceResult<Boolean> updateGrayIpList(UpdateGrayIpListArg arg);

}
