package com.huangyuan.open.gray.config.provider.dao.impl;


import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.config.provider.dao.GrayApplicationIpConfigDAO;
import com.huangyuan.open.gray.config.provider.dao.base.CommonDAO;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationIpConfigDO;
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
public class GrayApplicationIpConfigDAOImpl extends CommonDAO<GrayApplicationIpConfigDO> implements GrayApplicationIpConfigDAO {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public Pager<GrayApplicationIpConfigDO> queryGrayApplicationIpConfigPage(Pager<GrayApplicationIpConfigDO> queryPage) {
        return this.queryPage("queryGrayApplicationIpConfigPageCount", "queryGrayApplicationIpConfigPageList", queryPage);
    }

    @Override
    public List<GrayApplicationIpConfigDO> queryGrayApplicationIpConfig(GrayApplicationIpConfigDO grayApplicationIpConfigDO) {
        return super.getList("queryGrayApplicationIpConfig", grayApplicationIpConfigDO);
    }

    @Override
    public Boolean updateApplicationIPConfig(GrayApplicationIpConfigDO grayApplicationIpConfigDO) {
        try {
            return super.update("updateApplicationIPConfig", grayApplicationIpConfigDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred，grayApplicationIpConfigDO={}，e={}", grayApplicationIpConfigDO, e);
            return false;
        }
    }

    @Override
    public Boolean delGrayApplicationIpConfig(GrayApplicationIpConfigDO grayApplicationIpConfigDO) {
        try {
            return super.delete("delGrayApplicationIpConfig", grayApplicationIpConfigDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred delete，grayApplicationIpConfigDO={}，e={}", grayApplicationIpConfigDO, e);
            return false;
        }
    }

    @Override
    public Boolean insertGrayApplicationIpConfig(GrayApplicationIpConfigDO grayApplicationIpConfigDO) {
        try {
            return super.save("insertGrayApplicationIpConfig", grayApplicationIpConfigDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred，grayApplicationIpConfigDO={}，e={}", grayApplicationIpConfigDO, e);
            return false;
        }
    }
}
