package com.huangyuan.open.gray.config.provider.dao;


import com.facishare.open.common.storage.mysql.dao.Pager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationGroupConfigDO;

import java.util.List;

/**
 * @author suxq
 * @date 2018/8/27
 */
public interface GrayApplicationGroupConfigDAO {

    /**
     * 查询灰度服务分组信息
     *
     * @param queryPage 查询参数
     * @return Pager<GrayApplicationGroupConfigDO> 灰度企业信息
     */
    Pager<GrayApplicationGroupConfigDO> queryGrayApplicationGroupConfigPage(Pager<GrayApplicationGroupConfigDO> queryPage);

    /**
     * 查询灰度服务分组信息
     *
     * @param grayApplicationGroupConfigDO 查询参数
     *                                     【id】非必填
     * @return   List<GrayApplicationGroupConfigDO> 灰度企业信息
     */
    List<GrayApplicationGroupConfigDO> queryGrayApplicationGroupConfig(GrayApplicationGroupConfigDO grayApplicationGroupConfigDO);

    /**
     * 修改灰度服务分组信息
     *
     * @param grayApplicationGroupConfigDO 修改参数
     *                                     【groupName】 必填
     * @return true-操作成功, false-操作失败
     */
    Boolean updateGroupConfig(GrayApplicationGroupConfigDO grayApplicationGroupConfigDO);

    /**
     * 删除灰度服务分组信息
     *
     * @param grayApplicationGroupConfigDO 删除参数
     *                                     【id】   必填
     * @return true-操作成功, false-操作失败
     */
    Boolean delGrayApplicationGroupConfig(GrayApplicationGroupConfigDO grayApplicationGroupConfigDO);

    /**
     * 新增灰度服务分组信息
     *
     * @param grayApplicationGroupConfigDO 新增参数
     *                                     【groupName】   非必填
     *                                     【status】      非必填 （1：开启 2：关闭） 不填默认是1：开启
     * @return true-操作成功, false-操作失败
     */
    Boolean insertGrayApplicationGroupConfig(GrayApplicationGroupConfigDO grayApplicationGroupConfigDO);
}
