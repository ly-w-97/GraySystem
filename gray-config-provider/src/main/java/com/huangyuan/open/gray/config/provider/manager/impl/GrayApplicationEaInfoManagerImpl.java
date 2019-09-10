package com.huangyuan.open.gray.config.provider.manager.impl;


import com.facishare.open.common.storage.mysql.dao.Pager;
import com.huangyuan.open.gray.config.provider.dao.GrayApplicationEaInfoDAO;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationEaInfoManager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationEaInfoDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author suxq
 * @date 2018/8/27
 */
@Service
public class GrayApplicationEaInfoManagerImpl implements GrayApplicationEaInfoManager {

    @Resource
    private GrayApplicationEaInfoDAO grayApplicationEaInfoDAO;


    @Override
    public Pager<GrayApplicationEaInfoDO> queryGrayApplicationEaInfoPage(Pager<GrayApplicationEaInfoDO> queryPage) {
        return grayApplicationEaInfoDAO.queryGrayApplicationEaInfoPage(queryPage);
    }

    @Override
    public GrayApplicationEaInfoDO queryGrayApplicationEaInfo(Long id, String fsEa) {
        GrayApplicationEaInfoDO grayApplicationEaInfoDO = new GrayApplicationEaInfoDO();
        if (id != null) {
            grayApplicationEaInfoDO.setId(id);
        }
        if (StringUtils.isNotBlank(fsEa)) {
            grayApplicationEaInfoDO.setFsEa(fsEa);
        }
        return grayApplicationEaInfoDAO.queryGrayApplicationEaInfo(grayApplicationEaInfoDO);
    }

    @Override
    public Boolean updateApplicationEaInfo(Long id, String fsEa, String eaName, String describe) {
        GrayApplicationEaInfoDO grayApplicationEaInfoDO = new GrayApplicationEaInfoDO();
        grayApplicationEaInfoDO.setId(id);
        grayApplicationEaInfoDO.setFsEa(fsEa);
        grayApplicationEaInfoDO.setDescribe(fsEa);
        grayApplicationEaInfoDO.setEaName(eaName);
        grayApplicationEaInfoDO.setUpdateTime(System.currentTimeMillis());
        return grayApplicationEaInfoDAO.updateApplicationEaInfo(grayApplicationEaInfoDO);
    }

    @Override
    public Boolean delGrayApplicationEaInfo(Long id) {
        GrayApplicationEaInfoDO grayApplicationEaInfoDO = new GrayApplicationEaInfoDO();
        grayApplicationEaInfoDO.setId(id);
        return grayApplicationEaInfoDAO.delGrayApplicationEaInfo(grayApplicationEaInfoDO);
    }

    @Override
    public Boolean insertGrayApplicationEaInfo(String fsEa, String eaName, String describe) {
        GrayApplicationEaInfoDO grayApplicationEaInfoDO = new GrayApplicationEaInfoDO();
        grayApplicationEaInfoDO.setFsEa(fsEa);
        grayApplicationEaInfoDO.setDescribe(describe);
        grayApplicationEaInfoDO.setEaName(eaName);
        grayApplicationEaInfoDO.setCreateTime(System.currentTimeMillis());
        return grayApplicationEaInfoDAO.insertGrayApplicationEaInfo(grayApplicationEaInfoDO);
    }
}
