package com.huangyuan.open.gray.config.provider.dao.impl;


import com.facishare.open.common.storage.mysql.dao.Pager;
import com.huangyuan.open.gray.config.provider.dao.GrayApplicationEaInfoDAO;
import com.huangyuan.open.gray.config.provider.dao.base.CommonDAO;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationEaInfoDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 *
 * @author suxq
 * @date 2018/8/27
 */
@Repository
public class GrayApplicationEaInfoDAOImpl extends CommonDAO<GrayApplicationEaInfoDO> implements GrayApplicationEaInfoDAO {
    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public Pager<GrayApplicationEaInfoDO> queryGrayApplicationEaInfoPage(Pager<GrayApplicationEaInfoDO> queryPage) {
        return this.queryPage("queryGrayApplicationEaInfoPageCount", "queryGrayApplicationEaInfoPageList", queryPage);
    }

    @Override
    public GrayApplicationEaInfoDO queryGrayApplicationEaInfo(GrayApplicationEaInfoDO grayApplicationEaInfoDO) {
        return super.getUnique("queryGrayApplicationEaInfo", grayApplicationEaInfoDO);
    }

    @Override
    public Boolean updateApplicationEaInfo(GrayApplicationEaInfoDO grayApplicationEaInfoDO) {
        try {
            return super.update("updateApplicationEaInfo", grayApplicationEaInfoDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred，grayApplicationEaInfoDO={}，e={}", grayApplicationEaInfoDO, e);
            return false;
        }
    }

    @Override
    public Boolean delGrayApplicationEaInfo(GrayApplicationEaInfoDO grayApplicationEaInfoDO) {
        try {
            return super.delete("delGrayApplicationEaInfo", grayApplicationEaInfoDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred delete，grayApplicationEaInfoDO={}，e={}", grayApplicationEaInfoDO, e);
            return false;
        }
    }

    @Override
    public Boolean insertGrayApplicationEaInfo(GrayApplicationEaInfoDO grayApplicationEaInfoDO) {
        try {
            return super.save("insertGrayApplicationEaInfo", grayApplicationEaInfoDO) > 0;
        } catch (Exception e) {
            LOGGER.error("An exception occurred，grayApplicationEaInfoDO={}，e={}",
                    grayApplicationEaInfoDO, e);
            return false;
        }
    }
}
