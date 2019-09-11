package com.huangyuan.open.gray.config.provider.dao;


import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationIpConfigDO;

import java.util.List;


public interface GrayApplicationIpConfigDAO {

    /**
     * 分页查询灰度服务ip信息
     *
     * @param queryPage 查询参数
     * @return Pager<GrayApplicationIpConfigDO> 灰度企业信息
     */
    Pager<GrayApplicationIpConfigDO> queryGrayApplicationIpConfigPage(Pager<GrayApplicationIpConfigDO> queryPage);

    /**
     * 查询灰度服务ip信息
     *
     * @param grayApplicationIpConfigDO 查询参数
     *                                  【id】非必填
     * @return List<GrayApplicationIpConfigDO> 灰度企业信息
     */
    List<GrayApplicationIpConfigDO> queryGrayApplicationIpConfig(GrayApplicationIpConfigDO grayApplicationIpConfigDO);

    /**
     * 修改灰度服务ip信息
     *
     * @param grayApplicationIpConfigDO 修改参数
     *                                  【groupName】 非必填
     *                                  【status】 非必填  1：开启 2：关闭）
     * @return true-操作成功, false-操作失败
     */
    Boolean updateApplicationIPConfig(GrayApplicationIpConfigDO grayApplicationIpConfigDO);

    /**
     * 删除灰度服务ip信息
     *
     * @param grayApplicationIpConfigDO 删除参数
     *                                  【id】   必填
     * @return true-操作成功, false-操作失败
     */
    Boolean delGrayApplicationIpConfig(GrayApplicationIpConfigDO grayApplicationIpConfigDO);

    /**
     * 新增灰度服务ip信息
     *
     * @param grayApplicationIpConfigDO 新增参数
     *                                  【groupName】   非必填
     *                                  【status】      非必填 （1：开启 2：关闭） 不填默认是1：开启
     * @return true-操作成功, false-操作失败
     */
    Boolean insertGrayApplicationIpConfig(GrayApplicationIpConfigDO grayApplicationIpConfigDO);
}
