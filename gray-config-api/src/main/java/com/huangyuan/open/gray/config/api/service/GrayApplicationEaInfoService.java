package com.huangyuan.open.gray.config.api.service;

import com.huangyuan.open.gray.config.api.model.arg.GrayApplicationEaInfoArg;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationEaInfoResult;
import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.base.result.EserviceResult;

public interface GrayApplicationEaInfoService {

    /**
     * 分页查询灰度企业信息
     *
     * @param pager 分页参数
     * @param arg   参数
     *              id     记录id        【非必填】
     *              fsEa   企业账号id 【非必填】
     * @return NewModelResult<Pager<GrayApplicationEaInfoResult>>
     */
    EserviceResult<Pager<GrayApplicationEaInfoResult>> queryGrayApplicationEaInfoPage(Pager<GrayApplicationEaInfoResult> pager, GrayApplicationEaInfoArg arg);

    /**
     * 修改灰度企业信息
     *
     * @param arg 参数
     *            id       记录id     【必填】
     *            fsEa     企业账户id 【必填】
     *            eaName   企业名称   【必填】
     *            describe 描叙       【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> updateApplicationEaInfo(GrayApplicationEaInfoArg arg);

    /**
     * 删除灰度企业信息
     *
     * @param arg 参数
     *            id       记录id     【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> delGrayApplicationEaInfo(GrayApplicationEaInfoArg arg);

    /**
     * 新增灰度企业信息
     *
     * @param arg 参数
     *            fsEa     企业账户id   【必填】
     *            eaName   企业名称    【必填】
     *            describe 描叙       【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> insertGrayApplicationEaInfo(GrayApplicationEaInfoArg arg);
}
