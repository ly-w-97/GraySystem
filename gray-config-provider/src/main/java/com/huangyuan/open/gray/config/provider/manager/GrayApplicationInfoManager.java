package com.huangyuan.open.gray.config.provider.manager;


import com.facishare.open.common.storage.mysql.dao.Pager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationInfoDO;

import java.util.List;

/**
 * @author suxq
 * @date 2018/8/27
 */
public interface GrayApplicationInfoManager {

    /**
     * 分页查询灰度服务信息
     *
     * @param queryPage        分页信息
     *  applicationName 应用名称 【非必填】 eg:wechat-union-core | wechat-union-customer
     *  groupId         分组id  【非必填】
     *  status         状态  【非必填】
     * @return List<GrayApplicationInfoDO>
     */
    Pager<GrayApplicationInfoDO> queryGrayApplicationInfoPage(Pager<GrayApplicationInfoDO> queryPage);

    /**
     * 查询灰度服务信息
     *
     * @param applicationName 应用名称 【必填】 eg:wechat-union-core | wechat-union-customer
     * @param groupId 分组id
     * @return List<GrayApplicationInfoDO>
     */
    List<GrayApplicationInfoDO> queryGrayApplicationInfo(String applicationName, Long groupId);

    /**
     * 修改灰度服务信息
     *
     * @param id              记录id              【必填】
     * @param applicationName 应用名称            【必填】
     * @param groupId         分组id              【必填】
     * @param describe        描叙                【必填】
     * @param ipAddress       IP地址              【必填】
     * @param status          状态                【必填】
     * @return true-操作成功, false-操作失败
     */
    Boolean updateGrayApplicationInfo(Long id, String applicationName,
                                      Long groupId, String describe, String ipAddress, Integer status);

    /**
     * 删除灰度服务信息
     *
     * @param id 记录id
     * @return true-操作成功, false-操作失败
     */
    Boolean delGrayApplicationInfo(Long id);

    /**
     * 新增灰度服务信息
     *
     * @param applicationName 应用名称   【必填】
     * @param groupId         分组id            【必填】
     * @param status          状态           【必填】
     * @param describe        描叙          【必填】
     * @param ipAddress       IP地址
     * @return true-操作成功, false-操作失败
     */
    Boolean insertGrayApplicationInfo(String applicationName, Long groupId, Integer status,
                                      String describe, String ipAddress);
}
