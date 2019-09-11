package com.huangyuan.open.gray.config.provider.dao.impl;


import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.config.provider.dao.GrayApplicationInfoDAO;
import com.huangyuan.open.gray.config.provider.dao.base.CommonDAO;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationInfoDO;
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
public class GrayApplicationInfoDAOImpl extends CommonDAO<GrayApplicationInfoDO> implements GrayApplicationInfoDAO {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public Pager<GrayApplicationInfoDO> queryGrayApplicationInfoPage(Pager<GrayApplicationInfoDO> queryPage) {
        return this.queryPage("queryGrayApplicationInfoPageCount", "queryGrayApplicationInfoPageList", queryPage);
    }

    @Override
    public List<GrayApplicationInfoDO> queryGrayApplicationInfo(GrayApplicationInfoDO grayApplicationInfoDO) {
        return super.getList("queryGrayApplicationInfo", grayApplicationInfoDO);
    }

    @Override
    public Boolean updateGrayApplicationInfo(GrayApplicationInfoDO grayApplicationInfoDO) {
        try {
            return super.update("updateGrayApplicationInfo", grayApplicationInfoDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred，grayApplicationInfoDO={}，e={}", grayApplicationInfoDO, e);
            return false;
        }
    }

    @Override
    public Boolean delGrayApplicationInfo(GrayApplicationInfoDO grayApplicationInfoDO) {
        try {
            return super.delete("delGrayApplicationInfo", grayApplicationInfoDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred delete，grayApplicationInfoDO={}，e={}", grayApplicationInfoDO, e);
            return false;
        }
    }

    @Override
    public Boolean insertGrayApplicationInfo(GrayApplicationInfoDO grayApplicationInfoDO) {
        try {
            return super.save("insertGrayApplicationInfo", grayApplicationInfoDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred，grayApplicationInfoDO={}，e={}", grayApplicationInfoDO, e);
            return false;
        }
    }
}
