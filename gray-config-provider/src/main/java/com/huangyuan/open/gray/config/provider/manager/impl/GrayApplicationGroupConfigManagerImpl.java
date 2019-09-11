package com.huangyuan.open.gray.config.provider.manager.impl;

import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.config.provider.dao.GrayApplicationGroupConfigDAO;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationGroupConfigManager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationGroupConfigDO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


/**
 *
 * @author huangy
 * @date 2018/8/27
 */
@Service
public class GrayApplicationGroupConfigManagerImpl implements GrayApplicationGroupConfigManager {

    @Resource
    private GrayApplicationGroupConfigDAO grayApplicationGroupConfigDAO;


    @Override
    public Pager<GrayApplicationGroupConfigDO> queryGrayApplicationGroupConfigPage(Pager<GrayApplicationGroupConfigDO> queryPage) {
        return grayApplicationGroupConfigDAO.queryGrayApplicationGroupConfigPage(queryPage);
    }

    @Override
    public List<GrayApplicationGroupConfigDO> queryGrayApplicationGroupConfig(Long id, String fsEa) {
        GrayApplicationGroupConfigDO grayApplicationGroupConfigDO = new GrayApplicationGroupConfigDO();
        grayApplicationGroupConfigDO.setId(id);
        grayApplicationGroupConfigDO.setFsEas(fsEa);
        return grayApplicationGroupConfigDAO.queryGrayApplicationGroupConfig(grayApplicationGroupConfigDO);
    }

    @Override
    public Boolean updateGroupConfig(Long id, String groupName, String describe, String fsEas, Integer status) {
        GrayApplicationGroupConfigDO grayApplicationGroupConfigDO = new GrayApplicationGroupConfigDO();
        grayApplicationGroupConfigDO.setId(id);
        grayApplicationGroupConfigDO.setGroupName(groupName);
        grayApplicationGroupConfigDO.setDescribe(describe);
        grayApplicationGroupConfigDO.setUpdateTime(System.currentTimeMillis());
        grayApplicationGroupConfigDO.setFsEas(fsEas);
        grayApplicationGroupConfigDO.setStatus(status);
        return grayApplicationGroupConfigDAO.updateGroupConfig(grayApplicationGroupConfigDO);
    }

    @Override
    public Boolean delGrayApplicationGroupConfig(Long id) {
        GrayApplicationGroupConfigDO grayApplicationGroupConfigDO = new GrayApplicationGroupConfigDO();
        grayApplicationGroupConfigDO.setId(id);
        return grayApplicationGroupConfigDAO.delGrayApplicationGroupConfig(grayApplicationGroupConfigDO);
    }

    @Override
    public Boolean insertGrayApplicationGroupConfig(String groupName, Integer status, String describe, String fsEas) {
        GrayApplicationGroupConfigDO grayApplicationGroupConfigDO = new GrayApplicationGroupConfigDO();
        grayApplicationGroupConfigDO.setGroupName(groupName);
        grayApplicationGroupConfigDO.setStatus(status == null ? 1 : status);
        grayApplicationGroupConfigDO.setDescribe(describe);
        grayApplicationGroupConfigDO.setCreateTime(System.currentTimeMillis());
        grayApplicationGroupConfigDO.setFsEas(fsEas);
        return grayApplicationGroupConfigDAO.insertGrayApplicationGroupConfig(grayApplicationGroupConfigDO);
    }
}
