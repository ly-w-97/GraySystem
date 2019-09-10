package com.huangyuan.open.gray.config.api.service;


import com.huangyuan.open.gray.config.api.model.arg.GrayApplicationIpConfigArg;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationIpConfigResult;
import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.base.result.EserviceResult;


public interface GrayApplicationIpConfigService {

    /**
     * 分页查询灰度服务ip信息
     *
     * @param pager 分页参数
     * @param arg 参数
     *            id            记录id         【非必填】
     *            applicationId 灰度服务id    【非必填】
     * @return NewModelResult<Pager<GrayApplicationIpConfigResult>>
     */
    EserviceResult<Pager<GrayApplicationIpConfigResult>> queryGrayApplicationIpConfigPage(Pager<GrayApplicationIpConfigResult> pager, GrayApplicationIpConfigArg arg);

    /**
     * 修改灰度服务ip信息
     *
     * @param arg 参数
     *            id            记录id       【必填】
     *            ipAddress     ip地址      【必填】
     *            applicationId 灰度服务id 【必填】
     *            describe      描叙      【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> updateApplicationIPConfig(GrayApplicationIpConfigArg arg);

    /**
     * 删除灰度服务ip信息
     *
     * @param arg 参数
     *            id            记录id       【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> delGrayApplicationIpConfig(GrayApplicationIpConfigArg arg);

    /**
     * 新增灰度服务ip信息
     *
     * @param arg 参数
     *            ipAddress     ip地址      【必填】
     *            applicationId 灰度服务id 【必填】
     *            describe      描叙      【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> insertGrayApplicationIpConfig(GrayApplicationIpConfigArg arg);
}
