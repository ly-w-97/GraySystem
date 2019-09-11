package com.huangyuan.open.gray.config.provider.manager;

import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationIpConfigDO;

import java.util.List;

/**
 * @author huangy
 * @date 2018/8/27
 */
public interface GrayApplicationIpConfigManager {

    /**
     * 分页查询灰度服务ip信息
     *
     * @param queryPage 分页查询参数    【非必填】
     * @return Pager<GrayApplicationIpConfigDO>
     */
    Pager<GrayApplicationIpConfigDO> queryGrayApplicationIpConfigPage(Pager<GrayApplicationIpConfigDO> queryPage);

    /**
     * 查询灰度服务ip信息
     *
     * @param id            记录id         【非必填】
     * @param applicationId 灰度服务id 【非必填】
     * @return List<GrayApplicationIpConfigDO>
     */
    List<GrayApplicationIpConfigDO> queryGrayApplicationIpConfig(Long id, Long applicationId);

    /**
     * 修改灰度服务ip信息
     *
     * @param id            记录id   【必填】
     * @param ipAddress     ip地址 【必填】
     * @param applicationId 灰度服务id 【必填】
     * @param describe      描叙      【必填】
     * @return true-操作成功, false-操作失败
     */
    Boolean updateApplicationIPConfig(Long id, String ipAddress, Long applicationId, String describe);

    /**
     * 删除灰度服务ip信息
     *
     * @param id 记录id
     * @return true-操作成功, false-操作失败
     */
    Boolean delGrayApplicationIpConfig(Long id);

    /**
     * 新增灰度服务ip信息
     *
     * @param ipAddress     ip地址 【必填】
     * @param applicationId 灰度服务id 【必填】
     * @param describe      描叙      【必填】
     * @return true-操作成功, false-操作失败
     */
    Boolean insertGrayApplicationIpConfig(String ipAddress, Long applicationId, String describe);
}
