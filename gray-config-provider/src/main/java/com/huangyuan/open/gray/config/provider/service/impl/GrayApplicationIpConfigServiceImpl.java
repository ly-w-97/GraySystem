package com.huangyuan.open.gray.config.provider.service.impl;

import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.base.result.EserviceResult;
import com.huangyuan.open.gray.config.api.constant.GrayErrorCode;
import com.huangyuan.open.gray.config.api.model.arg.GrayApplicationIpConfigArg;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationIpConfigResult;
import com.huangyuan.open.gray.config.api.service.GrayApplicationIpConfigService;
import com.huangyuan.open.gray.config.provider.constant.GrayConstant;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationInfoManager;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationIpConfigManager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationInfoDO;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationIpConfigDO;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangy
 * @date 2018/8/27
 */
@Service
public class GrayApplicationIpConfigServiceImpl implements GrayApplicationIpConfigService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrayApplicationIpConfigServiceImpl.class);

    @Resource
    private GrayApplicationIpConfigManager grayApplicationIpConfigManager;
    @Resource
    private GrayApplicationInfoManager grayApplicationInfoManager;

    @Override
    public EserviceResult<Pager<GrayApplicationIpConfigResult>> queryGrayApplicationIpConfigPage(Pager<GrayApplicationIpConfigResult> pager, GrayApplicationIpConfigArg arg) {

        if (pager == null) {
            LOGGER.warn("Params illegal, pager is null, arg={}", arg);
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        Pager<GrayApplicationIpConfigDO> queryPage = new Pager<>();
        queryPage.setCurrentPage(pager.getCurrentPage());
        queryPage.setPageSize(pager.getPageSize());

        if (arg.getApplicationId() != null) {
            queryPage.addParam("applicationId", arg.getApplicationId());
        }

        List<GrayApplicationIpConfigResult> datas = Lists.newArrayList();
        try {
            queryPage = grayApplicationIpConfigManager.queryGrayApplicationIpConfigPage(queryPage);
            datas = buildResult(queryPage.getData());
            pager.setRecordSize(queryPage.getRecordSize());
            pager.setData(datas);
            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), pager);
        } catch (Exception e) {
            LOGGER.error("An exception occurred queryGrayApplicationIpConfigPage, pager={}, arg={}", pager, arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> updateApplicationIPConfig(GrayApplicationIpConfigArg arg) {
        if (arg == null
                || arg.getId() == null
                || arg.getApplicationId() == null
                || StringUtils.isBlank(arg.getIpAddress())) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationIpConfigManager
                    .updateApplicationIPConfig(arg.getId(), arg.getIpAddress(), arg.getApplicationId(), arg.getDescribe()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> delGrayApplicationIpConfig(GrayApplicationIpConfigArg arg) {
        if (arg == null
                || arg.getId() == null) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationIpConfigManager
                    .delGrayApplicationIpConfig(arg.getId()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> insertGrayApplicationIpConfig(GrayApplicationIpConfigArg arg) {
        if (arg == null
                || arg.getApplicationId() == null
                || StringUtils.isBlank(arg.getIpAddress())) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationIpConfigManager
                    .insertGrayApplicationIpConfig(arg.getIpAddress(), arg.getApplicationId(), arg.getDescribe()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    private List<GrayApplicationIpConfigResult> buildResult(List<GrayApplicationIpConfigDO> list) {
        List<GrayApplicationIpConfigResult> ipConfigResults = Lists.newArrayList();

        if (CollectionUtils.isEmpty(list)) {
            return ipConfigResults;
        }

        GrayApplicationIpConfigResult ipConfigResult;
        Map<Long, String> map = getApplicationInfo();
        for (GrayApplicationIpConfigDO grayApplicationIpConfigDO : list) {
            ipConfigResult = new GrayApplicationIpConfigResult();
            ipConfigResult.setId(grayApplicationIpConfigDO.getId());
            ipConfigResult.setIpAddress(grayApplicationIpConfigDO.getIpAddress());
            ipConfigResult.setApplicationId(grayApplicationIpConfigDO.getApplicationId());
            if (MapUtils.isNotEmpty(map) && map.containsKey(grayApplicationIpConfigDO.getApplicationId())) {
                ipConfigResult.setApplicationName(map.get(grayApplicationIpConfigDO.getApplicationId()));
            }
            ipConfigResult.setDescribe(grayApplicationIpConfigDO.getDescribe());
            ipConfigResult.setCreateTime(grayApplicationIpConfigDO.getCreateTime());
            ipConfigResult.setUpdateTime(grayApplicationIpConfigDO.getUpdateTime());
            ipConfigResults.add(ipConfigResult);
        }
        return ipConfigResults;
    }

    /**
     * 获取灰度服务信息
     *
     * @return Map<Long, String>
     */
    private Map<Long, String> getApplicationInfo() {
        List<GrayApplicationInfoDO> applicationInfoDOs = grayApplicationInfoManager.queryGrayApplicationInfo(null, null);
        Map<Long, String> map = new HashMap<>();
        for (GrayApplicationInfoDO applicationInfoDO : applicationInfoDOs) {
            map.put(applicationInfoDO.getId(), applicationInfoDO.getApplicationName());
        }
        return map;
    }
}
