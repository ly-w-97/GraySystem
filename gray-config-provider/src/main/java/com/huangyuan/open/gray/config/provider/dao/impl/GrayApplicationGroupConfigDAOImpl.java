package com.huangyuan.open.gray.config.provider.dao.impl;


import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.config.provider.dao.GrayApplicationGroupConfigDAO;
import com.huangyuan.open.gray.config.provider.dao.base.CommonDAO;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationGroupConfigDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @author huangy
 * @date 2018/8/27
 */
@Repository
public class GrayApplicationGroupConfigDAOImpl extends CommonDAO<GrayApplicationGroupConfigDO> implements GrayApplicationGroupConfigDAO {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public Pager<GrayApplicationGroupConfigDO> queryGrayApplicationGroupConfigPage(Pager<GrayApplicationGroupConfigDO> queryPage) {
        return this.queryPage("queryGrayApplicationGroupConfigPageCount", "queryGrayApplicationGroupConfigPageList", queryPage);
    }

    @Override
    public List<GrayApplicationGroupConfigDO> queryGrayApplicationGroupConfig(GrayApplicationGroupConfigDO grayApplicationGroupConfigDO) {
        return super.getList("queryGrayApplicationGroupConfig", grayApplicationGroupConfigDO);
    }


    @Override
    public Boolean updateGroupConfig(GrayApplicationGroupConfigDO grayApplicationGroupConfigDO) {
        try {
            return super.update("updateGroupConfig", grayApplicationGroupConfigDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred，grayApplicationGroupConfigDO={}，e={}", grayApplicationGroupConfigDO, e);
            return false;
        }
    }

    @Override
    public Boolean delGrayApplicationGroupConfig(GrayApplicationGroupConfigDO grayApplicationGroupConfigDO) {
        try {
            return super.delete("delGrayApplicationGroupConfig", grayApplicationGroupConfigDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred delete，grayApplicationGroupConfigDO={}，e={}", grayApplicationGroupConfigDO, e);
            return false;
        }
    }

    @Override
    public Boolean insertGrayApplicationGroupConfig(GrayApplicationGroupConfigDO grayApplicationGroupConfigDO) {
        try {
            return super.save("insertGrayApplicationGroupConfig", grayApplicationGroupConfigDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred，grayApplicationGroupConfigDO={}，e={}",
                    grayApplicationGroupConfigDO, e);
            return false;
        }
    }
}
