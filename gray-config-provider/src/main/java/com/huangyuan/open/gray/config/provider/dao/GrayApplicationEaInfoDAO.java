package com.huangyuan.open.gray.config.provider.dao;

import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationEaInfoDO;

/**
 * @author huangy
 * @date 2018/8/27
 */
public interface GrayApplicationEaInfoDAO {

    /**
     * 分页查询灰度企业信息
     *
     * @param queryPage 分页查询参数
     * @return Pager<GrayApplicationEaInfoDO> queryPage 灰度企业信息
     */
    Pager<GrayApplicationEaInfoDO> queryGrayApplicationEaInfoPage(Pager<GrayApplicationEaInfoDO> queryPage);

    /**
     * 查询灰度企业信息
     *
     * @param grayApplicationEaInfoDO 查询参数
     *                                【id】非必填
     * @return GrayApplicationEaInfoDO 灰度企业信息
     */
    GrayApplicationEaInfoDO queryGrayApplicationEaInfo(GrayApplicationEaInfoDO grayApplicationEaInfoDO);


    /**
     * 修改灰度企业信息
     *
     * @param grayApplicationEaInfoDO 修改参数
     *                                【fsEa】   非必填
     *                                【eaName】 非必填
     * @return true-操作成功, false-操作失败
     */
    Boolean updateApplicationEaInfo(GrayApplicationEaInfoDO grayApplicationEaInfoDO);

    /**
     * 删除灰度企业信息
     *
     * @param grayApplicationEaInfoDO 删除参数
     *                                【id】   必填
     * @return true-操作成功, false-操作失败
     */
    Boolean delGrayApplicationEaInfo(GrayApplicationEaInfoDO grayApplicationEaInfoDO);

    /**
     * 新增灰度企业信息
     *
     * @param grayApplicationEaInfoDO 新增参数
     *                                【fsEa】   非必填
     *                                【eaName】 非必填
     * @return true-操作成功, false-操作失败
     */
    Boolean insertGrayApplicationEaInfo(GrayApplicationEaInfoDO grayApplicationEaInfoDO);

}
