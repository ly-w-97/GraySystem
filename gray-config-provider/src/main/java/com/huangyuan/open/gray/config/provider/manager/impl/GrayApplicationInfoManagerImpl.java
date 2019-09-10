package com.huangyuan.open.gray.config.provider.manager.impl;

import com.facishare.open.common.storage.mysql.dao.Pager;
import com.huangyuan.open.gray.config.provider.dao.GrayApplicationInfoDAO;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationInfoManager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationInfoDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author suxq
 * @date 2018/8/27
 */
@Service
public class GrayApplicationInfoManagerImpl implements GrayApplicationInfoManager {

    @Resource
    private GrayApplicationInfoDAO grayApplicationInfoDAO;


    @Override
    public Pager<GrayApplicationInfoDO> queryGrayApplicationInfoPage(Pager<GrayApplicationInfoDO> queryPage) {
        return grayApplicationInfoDAO.queryGrayApplicationInfoPage(queryPage);
    }

    @Override
    public List<GrayApplicationInfoDO> queryGrayApplicationInfo(String applicationName, Long groupId) {
        GrayApplicationInfoDO grayApplicationInfoDO = new GrayApplicationInfoDO();
        grayApplicationInfoDO.setApplicationName(applicationName);
        grayApplicationInfoDO.setGroupId(groupId);
        return grayApplicationInfoDAO.queryGrayApplicationInfo(grayApplicationInfoDO);
    }

    @Override
    public Boolean updateGrayApplicationInfo(Long id, String applicationName, Long groupId,
                                             String describe, String ipAddress, Integer status) {
        GrayApplicationInfoDO grayApplicationInfoDO = new GrayApplicationInfoDO();
        grayApplicationInfoDO.setId(id);
        grayApplicationInfoDO.setApplicationName(applicationName);
        grayApplicationInfoDO.setGroupId(groupId);
        grayApplicationInfoDO.setDescribe(describe);
        grayApplicationInfoDO.setIpAddress(ipAddress);
        grayApplicationInfoDO.setUpdateTime(System.currentTimeMillis());
        grayApplicationInfoDO.setStatus(status);
        return grayApplicationInfoDAO.updateGrayApplicationInfo(grayApplicationInfoDO);
    }

    @Override
    public Boolean delGrayApplicationInfo(Long id) {
        GrayApplicationInfoDO grayApplicationInfoDO = new GrayApplicationInfoDO();
        grayApplicationInfoDO.setId(id);
        return grayApplicationInfoDAO.delGrayApplicationInfo(grayApplicationInfoDO);
    }

    @Override
    public Boolean insertGrayApplicationInfo(String applicationName, Long groupId, Integer status,
                                             String describe, String ipAddress) {
        GrayApplicationInfoDO grayApplicationInfoDO = new GrayApplicationInfoDO();
        grayApplicationInfoDO.setApplicationName(applicationName);
        grayApplicationInfoDO.setGroupId(groupId);
        grayApplicationInfoDO.setStatus(status == null ? 1 : status);
        grayApplicationInfoDO.setDescribe(describe);
        grayApplicationInfoDO.setCreateTime(System.currentTimeMillis());
        grayApplicationInfoDO.setIpAddress(ipAddress);
        return grayApplicationInfoDAO.insertGrayApplicationInfo(grayApplicationInfoDO);
    }
}
