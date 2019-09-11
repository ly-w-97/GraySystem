package com.huangyuan.open.gray.config.provider.manager;


import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationEaInfoDO;

/**
 * @author huangy
 * @date 2018/8/27
 */
public interface GrayApplicationEaInfoManager {

    /**
     * 分页查询灰度企业信息
     *
     * @return Pager<GrayApplicationEaInfoDO>
     */
    Pager<GrayApplicationEaInfoDO> queryGrayApplicationEaInfoPage(Pager<GrayApplicationEaInfoDO> queryPage);

    /**
     * 查询灰度企业信息
     *
     * @param id   记录id
     * @param fsEa 灰度企业账户
     * @return GrayApplicationEaInfoDO
     */
    GrayApplicationEaInfoDO queryGrayApplicationEaInfo(Long id, String fsEa);

    /**
     * 修改灰度企业信息
     *
     * @param id       记录id
     * @param fsEa     企业账户id
     * @param eaName   企业名称
     * @param describe 描叙
     * @return true-操作成功, false-操作失败
     */
    Boolean updateApplicationEaInfo(Long id, String fsEa, String eaName, String describe);


    /**
     * 删除灰度服务ip信息
     *
     * @param id 记录id
     * @return true-操作成功, false-操作失败
     */
    Boolean delGrayApplicationEaInfo(Long id);

    /**
     * 新增灰度企业信息
     *
     * @param fsEa     企业账户id
     * @param eaName   企业名称
     * @param describe 描叙
     * @return true-操作成功, false-操作失败
     */
    Boolean insertGrayApplicationEaInfo(String fsEa, String eaName, String describe);
}
