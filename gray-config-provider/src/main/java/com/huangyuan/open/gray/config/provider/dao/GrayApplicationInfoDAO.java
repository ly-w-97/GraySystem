package com.huangyuan.open.gray.config.provider.dao;


import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationInfoDO;

import java.util.List;

/**
 * @author huangy
 * @date 2018/8/27
 */
public interface GrayApplicationInfoDAO {

    /**
     * 分页查询灰度服务信息
     *
     * @param queryPage 分页参数
     *                  【groupId】 分组id   非必填
     *                  【applicatioName】服务名称  非必填
     *                  【status】 状态  非必填
     * @return List<GrayApplicationInfoDO> 灰度企业信息
     */
    Pager<GrayApplicationInfoDO> queryGrayApplicationInfoPage(Pager<GrayApplicationInfoDO> queryPage);

    /**
     * 查询灰度服务信息
     *
     * @param grayApplicationInfoDO 查询参数
     * @return List<GrayApplicationInfoDO> 灰度企业信息
     */
    List<GrayApplicationInfoDO> queryGrayApplicationInfo(GrayApplicationInfoDO grayApplicationInfoDO);

    /**
     * 修改灰度服务信息
     *
     * @param grayApplicationInfoDO 修改参数
     *                              【applicationName】应用名称 非必填
     *                              【groupId】 分组id       非必填
     *                              【describe】 描叙      非必填
     * @return true-操作成功, false-操作失败
     */
    Boolean updateGrayApplicationInfo(GrayApplicationInfoDO grayApplicationInfoDO);

    /**
     * 删除灰度服务信息
     *
     * @param grayApplicationInfoDO 删除参数
     *                              【id】 必填
     * @return true-操作成功, false-操作失败
     */
    Boolean delGrayApplicationInfo(GrayApplicationInfoDO grayApplicationInfoDO);

    /**
     * 新增灰度服务信息
     *
     * @param grayApplicationInfoDO 新增参数
     *                              【applicationName】应用名称 非必填
     *                              【groupId】 分组id       非必填
     *                              【describe】 描叙      非必填
     *                              【status】      非必填 （1：开启 2：关闭） 不填默认是1：开启
     * @return true-操作成功, false-操作失败
     */
    Boolean insertGrayApplicationInfo(GrayApplicationInfoDO grayApplicationInfoDO);
}
