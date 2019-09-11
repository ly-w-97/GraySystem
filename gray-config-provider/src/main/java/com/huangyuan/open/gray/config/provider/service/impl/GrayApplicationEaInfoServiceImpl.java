package com.huangyuan.open.gray.config.provider.service.impl;


import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.base.result.EserviceResult;
import com.huangyuan.open.gray.config.api.constant.GrayErrorCode;
import com.huangyuan.open.gray.config.api.model.arg.GrayApplicationEaInfoArg;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationEaInfoResult;
import com.huangyuan.open.gray.config.api.service.GrayApplicationEaInfoService;
import com.huangyuan.open.gray.config.provider.constant.GrayConstant;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationEaInfoManager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationEaInfoDO;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * @author huangy
 * @date 2018/8/27
 */
@Service
public class GrayApplicationEaInfoServiceImpl implements GrayApplicationEaInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrayApplicationEaInfoServiceImpl.class);

    @Resource
    private GrayApplicationEaInfoManager grayApplicationEaInfoManager;

    @Override
    public EserviceResult<Pager<GrayApplicationEaInfoResult>> queryGrayApplicationEaInfoPage(Pager<GrayApplicationEaInfoResult> pager,
                                                                                             GrayApplicationEaInfoArg arg) {

        if (pager == null) {
            LOGGER.warn("Params illegal, pager is null, arg={}", arg);
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        Pager<GrayApplicationEaInfoDO> queryPage = new Pager<>();
        queryPage.setCurrentPage(pager.getCurrentPage());
        queryPage.setPageSize(pager.getPageSize());

        if (StringUtils.isNotBlank(arg.getFsEa())) {
            queryPage.addParam("fsEa", arg.getFsEa());
        }

        List<GrayApplicationEaInfoResult> datas = Lists.newArrayList();
        try {
            queryPage = grayApplicationEaInfoManager.queryGrayApplicationEaInfoPage(queryPage);
            datas = buildResult(queryPage.getData());
            pager.setRecordSize(queryPage.getRecordSize());
            pager.setData(datas);
            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), pager);
        } catch (Exception e) {
            LOGGER.error("An exception occurred queryGrayApplicationEaInfoPage, pager={}, arg={}", pager, arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> updateApplicationEaInfo(GrayApplicationEaInfoArg arg) {
        if (arg == null
                || arg.getId() == null
                || StringUtils.isBlank(arg.getFsEa())
                || StringUtils.isBlank(arg.getEaName())) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationEaInfoManager
                    .updateApplicationEaInfo(arg.getId(), arg.getFsEa(), arg.getEaName(), arg.getDescribe()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> delGrayApplicationEaInfo(GrayApplicationEaInfoArg arg) {
        if (arg == null
                || arg.getId() == null) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationEaInfoManager
                    .delGrayApplicationEaInfo(arg.getId()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> insertGrayApplicationEaInfo(GrayApplicationEaInfoArg arg) {
        if (arg == null
                || StringUtils.isBlank(arg.getFsEa())
                || StringUtils.isBlank(arg.getEaName())) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationEaInfoManager
                    .insertGrayApplicationEaInfo(arg.getFsEa(), arg.getEaName(), arg.getDescribe()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    private List<GrayApplicationEaInfoResult> buildResult(List<GrayApplicationEaInfoDO> list) {
        List<GrayApplicationEaInfoResult> eaInfoResults = Lists.newArrayList();

        if (CollectionUtils.isEmpty(list)) {
            return eaInfoResults;
        }

        for (GrayApplicationEaInfoDO eaInfoDO : list) {
            GrayApplicationEaInfoResult eaInfoResult = new GrayApplicationEaInfoResult();
            eaInfoResult.setId(eaInfoDO.getId());
            eaInfoResult.setEaName(eaInfoDO.getEaName());
            eaInfoResult.setFsEa(eaInfoDO.getFsEa());
            eaInfoResult.setDescribe(eaInfoDO.getDescribe());
            eaInfoResult.setCreateTime(eaInfoDO.getCreateTime());
            eaInfoResult.setUpdateTime(eaInfoDO.getUpdateTime());
            eaInfoResults.add(eaInfoResult);
        }
        return eaInfoResults;
    }
}
