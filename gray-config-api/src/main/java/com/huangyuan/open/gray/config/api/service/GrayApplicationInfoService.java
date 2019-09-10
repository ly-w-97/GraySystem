package com.huangyuan.open.gray.config.api.service;

import com.huangyuan.open.gray.config.api.model.arg.GrayApplicationInfoArg;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationInfoResult;
import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.base.result.EserviceResult;

import java.util.List;


public interface GrayApplicationInfoService {

    /**
     * 分页查询灰度服务信息
     *
     * @param pager 分页
     * @param arg   参数
     *              id              记录id    【非必填】
     *              applicationName 应用名称 【非必填】 eg:wechat-union-core | wechat-union-customer
     *              groupId         分组id  【非必填】
     * @return NewModelResult<Pager<GrayApplicationInfoResult>>
     */
    EserviceResult<Pager<GrayApplicationInfoResult>> queryGrayApplicationInfoPage(Pager<GrayApplicationInfoResult> pager, GrayApplicationInfoArg arg);

    /**
     * 分页查询灰度服务信息
     *
     * @param arg 参数
     * @return NewModelResult<Pager<GrayApplicationInfoResult>>
     */
    EserviceResult<List<GrayApplicationInfoResult>> queryGrayApplicationInfoList(GrayApplicationInfoArg arg);

    /**
     * 修改灰度服务信息
     *
     * @param arg 参数
     *            id              记录id          【必填】
     *            applicationName 应用名称        【必填】
     *            groupId         分组id          【必填】
     *            describe        描叙            【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> updateGrayApplicationInfo(GrayApplicationInfoArg arg);

    /**
     * 删除灰度服务信息
     *
     * @param arg 参数
     *            id              记录id          【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> delGrayApplicationInfo(GrayApplicationInfoArg arg);

    /**
     * 新增灰度服务信息
     *
     * @param arg 参数
     *            applicationName 应用名称   【必填】
     *            groupId         分组id     【必填】
     *            status          状态       【必填】
     *            describe        描叙       【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> insertGrayApplicationInfo(GrayApplicationInfoArg arg);
}
