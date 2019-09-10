package com.huangyuan.open.gray.config.api.service;

import com.huangyuan.open.gray.config.api.model.arg.GrayApplicationGroupConfigArg;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationGroupConfigResult;
import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.base.result.EserviceResult;

import java.util.List;


public interface GrayApplicationGroupConfigService {


    /**
     * 分页查询灰度服务分组信息
     *
     * @param pager 分页参数
     * @param arg   参数
     *              id        记录id    【非必填】
     *              groupName 分组名称 【非必填】
     * @return NewModelResult<Pager<GrayApplicationGroupConfigResult>>
     */
    EserviceResult<Pager<GrayApplicationGroupConfigResult>> queryGrayApplicationGroupConfigPage(Pager<GrayApplicationGroupConfigResult> pager, GrayApplicationGroupConfigArg arg);

    /**
     * 查询灰度服务分组信息
     *
     * @param arg 参数
     *            id        记录id    【非必填】
     * @return NewModelResult<Pager<GrayApplicationGroupConfigResult>>
     */
    EserviceResult<List<GrayApplicationGroupConfigResult>> queryGrayGroupConfigList(GrayApplicationGroupConfigArg arg);

    /**
     * 修改灰度服务分组信息
     *
     * @param arg 参数
     *            id        记录id   【必填】
     *            groupName 分组名称 【必填】
     *            describe  描叙     【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> updateGroupConfig(GrayApplicationGroupConfigArg arg);

   /**
     * 删除灰度服务分组信息
     *
     * @param arg 参数
     *            id        记录id   【必填】
     * @return true-操作成功, false-操作失败
     */
   EserviceResult<Boolean> delGrayApplicationGroupConfig(GrayApplicationGroupConfigArg arg);

    /**
     * 新增灰度服务分组信息
     *
     * @param arg 参数
     *            groupName 分组名称  【必填】
     *            status    状态      【必填】
     *            describe  描叙      【必填】
     * @return true-操作成功, false-操作失败
     */
    EserviceResult<Boolean> insertGrayApplicationGroupConfig(GrayApplicationGroupConfigArg arg);

}
