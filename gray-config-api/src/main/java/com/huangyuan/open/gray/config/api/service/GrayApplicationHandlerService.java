package com.huangyuan.open.gray.config.api.service;

import com.huangyuan.open.gray.config.api.model.arg.GetGrayApplicationConfigNewArg;
import com.huangyuan.open.gray.config.api.model.arg.GetGrayApplicationGroupConfigByEaArg;
import com.huangyuan.open.gray.config.api.model.result.GetGrayApplicationGroupConfigByEaResult;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationResult;
import com.huangyuan.open.gray.base.result.EserviceResult;

public interface GrayApplicationHandlerService {

    /**
     * 查询灰度服务配置信息
     *
     * @param applicationName 服务名称  【必填】
     * @return GrayApplicationResult 返回信息： 应用名称，分组标识，机器IP地址列表，是否灰度(checkGray)
     */
    EserviceResult<GrayApplicationResult> getGrayApplicationConfig(String applicationName);

    /**
     * 检查灰度企业是否灰度
     *
     * @param applicationName 服务名称  【必填】
     * @param fsEa            企业id    【必填】
     * @return Boolean true-存在，为灰度企业 ，false-不存在，非灰度企业
     */
    EserviceResult<Boolean> checkGrayFsEa(String applicationName, String fsEa);

    /**
     * 查询灰度服务配置信息
     *      支持多个灰度分组
     * @param arg 服务名称、ip 【必填】
     * @return GrayApplicationResult 返回信息： 应用名称，分组标识，机器IP地址列表，是否灰度(checkGray)
     */
    EserviceResult<GrayApplicationResult> getGrayApplicationConfigNew(GetGrayApplicationConfigNewArg arg);

    /**
     * 查询该企业所属的灰度分组信息
     * @param arg fsEa必填
     * @return GetGrayApplicationGroupConfigByEaResult 分组信息
     */
    EserviceResult<GetGrayApplicationGroupConfigByEaResult> getGrayApplicationGroupConfigByEa(GetGrayApplicationGroupConfigByEaArg arg);

}
