package com.huangyuan.open.gray.config.provider.manager.impl;


import com.facishare.open.common.storage.mysql.dao.Pager;
import com.huangyuan.open.gray.config.provider.dao.GrayApplicationIpConfigDAO;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationIpConfigManager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationIpConfigDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author suxq
 * @date 2018/8/27
 */
@Service
public class GrayApplicationIpConfigManagerImpl implements GrayApplicationIpConfigManager {

    @Resource
    private GrayApplicationIpConfigDAO grayApplicationIpConfigDAO;

    @Override
    public Pager<GrayApplicationIpConfigDO> queryGrayApplicationIpConfigPage(Pager<GrayApplicationIpConfigDO> queryPage) {
        return grayApplicationIpConfigDAO.queryGrayApplicationIpConfigPage(queryPage);
    }

    @Override
    public List<GrayApplicationIpConfigDO> queryGrayApplicationIpConfig(Long id, Long applicationId) {
        GrayApplicationIpConfigDO grayApplicationIpConfigDO = new GrayApplicationIpConfigDO();
        grayApplicationIpConfigDO.setId(id);
        grayApplicationIpConfigDO.setApplicationId(applicationId);
        return grayApplicationIpConfigDAO.queryGrayApplicationIpConfig(grayApplicationIpConfigDO);
    }

    @Override
    public Boolean updateApplicationIPConfig(Long id, String ipAddress, Long applicationId, String describe) {
        GrayApplicationIpConfigDO grayApplicationIpConfigDO = new GrayApplicationIpConfigDO();
        grayApplicationIpConfigDO.setId(id);
        grayApplicationIpConfigDO.setIpAddress(ipAddress);
        grayApplicationIpConfigDO.setApplicationId(applicationId);
        grayApplicationIpConfigDO.setDescribe(describe);
        grayApplicationIpConfigDO.setUpdateTime(System.currentTimeMillis());
        return grayApplicationIpConfigDAO.updateApplicationIPConfig(grayApplicationIpConfigDO);
    }

    @Override
    public Boolean delGrayApplicationIpConfig(Long id) {
        GrayApplicationIpConfigDO grayApplicationIpConfigDO = new GrayApplicationIpConfigDO();
        grayApplicationIpConfigDO.setId(id);
        return grayApplicationIpConfigDAO.delGrayApplicationIpConfig(grayApplicationIpConfigDO);
    }

    @Override
    public Boolean insertGrayApplicationIpConfig(String ipAddress, Long applicationId, String describe) {
        GrayApplicationIpConfigDO grayApplicationIpConfigDO = new GrayApplicationIpConfigDO();
        grayApplicationIpConfigDO.setIpAddress(ipAddress);
        grayApplicationIpConfigDO.setApplicationId(applicationId);
        grayApplicationIpConfigDO.setDescribe(describe);
        grayApplicationIpConfigDO.setCreateTime(System.currentTimeMillis());
        return grayApplicationIpConfigDAO.insertGrayApplicationIpConfig(grayApplicationIpConfigDO);
    }
}
