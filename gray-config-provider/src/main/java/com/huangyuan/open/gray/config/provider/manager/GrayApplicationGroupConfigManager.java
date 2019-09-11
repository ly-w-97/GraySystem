package com.huangyuan.open.gray.config.provider.manager;


import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationGroupConfigDO;

import java.util.List;

/**
 * @author huangy
 * @date 2018/8/27
 */
public interface GrayApplicationGroupConfigManager {

    /**
     * 分页查询灰度服务分组信息
     *
     * @param queryPage 分页参数
     * @return Pager<GrayApplicationGroupConfigDO>
     */
    Pager<GrayApplicationGroupConfigDO> queryGrayApplicationGroupConfigPage(Pager<GrayApplicationGroupConfigDO> queryPage);

    /**
     * 查询灰度服务分组信息
     *
     * @param id   记录id 【非必填】
     * @param fsEa 灰度企业 【非必填】
     * @return List<GrayApplicationGroupConfigDO>
     */
    List<GrayApplicationGroupConfigDO> queryGrayApplicationGroupConfig(Long id, String fsEa);

    /**
     * 修改灰度服务分组信息
     *
     * @param id        记录id   【必填】
     * @param groupName 分组名称 【必填】
     * @param describe  描叙      【非必填】
     * @param fsEas     灰度企业账号   【非必填】
     * @param status    状态     【非必填】
     * @return true-操作成功, false-操作失败
     */
    Boolean updateGroupConfig(Long id, String groupName, String describe, String fsEas, Integer status);

    /**
     * 删除灰度服务分组信息
     *
     * @param id 记录id
     * @return true-操作成功, false-操作失败
     */
    Boolean delGrayApplicationGroupConfig(Long id);

    /**
     * 新增灰度服务分组信息
     *
     * @param groupName 分组名称  【必填】
     * @param status    状态      【必填】
     * @param describe  描叙      【必填】
     * @param fsEas     灰度企业账号      【非必填】
     * @return true-操作成功, false-操作失败
     */
    Boolean insertGrayApplicationGroupConfig(String groupName, Integer status, String describe, String fsEas);
}
