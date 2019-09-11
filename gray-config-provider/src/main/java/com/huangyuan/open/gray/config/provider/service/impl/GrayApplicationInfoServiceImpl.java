package com.huangyuan.open.gray.config.provider.service.impl;

import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.base.result.EserviceResult;
import com.huangyuan.open.gray.config.api.constant.GrayErrorCode;
import com.huangyuan.open.gray.config.api.model.arg.GrayApplicationInfoArg;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationInfoResult;
import com.huangyuan.open.gray.config.api.service.GrayApplicationInfoService;
import com.huangyuan.open.gray.config.provider.constant.GrayConstant;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationGroupConfigManager;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationInfoManager;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationIpConfigManager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationGroupConfigDO;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationInfoDO;
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
public class GrayApplicationInfoServiceImpl implements GrayApplicationInfoService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrayApplicationInfoServiceImpl.class);

    @Resource
    private GrayApplicationInfoManager grayApplicationInfoManager;
    @Resource
    private GrayApplicationIpConfigManager grayApplicationIpConfigManager;
    @Resource
    private GrayApplicationGroupConfigManager grayApplicationGroupConfigManager;

    @Override
    public EserviceResult<Pager<GrayApplicationInfoResult>> queryGrayApplicationInfoPage(Pager<GrayApplicationInfoResult> pager, GrayApplicationInfoArg arg) {

        if (pager == null) {
            LOGGER.warn("Params illegal, pager is null, arg={}", arg);
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        Pager<GrayApplicationInfoDO> queryPage = new Pager<>();
        queryPage.setCurrentPage(pager.getCurrentPage());
        queryPage.setPageSize(pager.getPageSize());
        if (arg.getStatus() != null) {
            queryPage.addParam("status", arg.getStatus());
        }
        if (StringUtils.isNotBlank(arg.getApplicationName())) {
            queryPage.addParam("applicationName", arg.getApplicationName());
        }
        if (arg.getGroupId() != null) {
            queryPage.addParam("groupId", arg.getGroupId());
        }

        List<GrayApplicationInfoResult> datas = Lists.newArrayList();
        try {
            queryPage = grayApplicationInfoManager.queryGrayApplicationInfoPage(queryPage);
            datas = buildResult(queryPage.getData());
            pager.setRecordSize(queryPage.getRecordSize());
            pager.setData(datas);
            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), pager);
        } catch (Exception e) {
            LOGGER.error("An exception occurred queryGrayApplicationInfoPage, pager={}, arg={}", pager, arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    public EserviceResult<List<GrayApplicationInfoResult>> queryGrayApplicationInfoList(GrayApplicationInfoArg arg) {
        if (arg == null) {
            LOGGER.warn("Params illegal, pager is null");
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {
            List<GrayApplicationInfoResult> datas = Lists.newArrayList();
            List<GrayApplicationInfoDO> list = grayApplicationInfoManager.queryGrayApplicationInfo(null, null);
            datas = buildResult(list);
            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), datas);
        } catch (Exception e) {
            LOGGER.error("An exception occurred queryGrayApplicationInfoList, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> updateGrayApplicationInfo(GrayApplicationInfoArg arg) {
        if (arg == null
                || arg.getId() == null
                || StringUtils.isBlank(arg.getApplicationName())
                || arg.getGroupId() == null) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationInfoManager
                    .updateGrayApplicationInfo(arg.getId(), arg.getApplicationName(),
                            arg.getGroupId(), arg.getDescribe(), arg.getIpAddress(), arg.getStatus()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> delGrayApplicationInfo(GrayApplicationInfoArg arg) {
        if (arg == null
                || arg.getId() == null) {
            LOGGER.warn("Params illegal, pager is null, arg={}", arg);
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationInfoManager
                    .delGrayApplicationInfo(arg.getId()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> insertGrayApplicationInfo(GrayApplicationInfoArg arg) {
        if (arg == null
                || StringUtils.isBlank(arg.getApplicationName())
                || arg.getGroupId() == null) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationInfoManager
                    .insertGrayApplicationInfo(arg.getApplicationName(), arg.getGroupId(), arg.getStatus(),
                            arg.getDescribe(), arg.getIpAddress()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    private List<GrayApplicationInfoResult> buildResult(List<GrayApplicationInfoDO> list) {
        List<GrayApplicationInfoResult> applicationInfoResults = Lists.newArrayList();


        if (CollectionUtils.isEmpty(list)) {
            return applicationInfoResults;
        }

        Map<Long, String> map = getGroupMap();
        GrayApplicationInfoResult infoResult;
        for (GrayApplicationInfoDO grayApplicationInfoDO : list) {
            infoResult = new GrayApplicationInfoResult();
            infoResult.setId(grayApplicationInfoDO.getId());
            infoResult.setApplicationName(grayApplicationInfoDO.getApplicationName());
            infoResult.setGroupId(grayApplicationInfoDO.getGroupId());
            if (MapUtils.isNotEmpty(map) && map.containsKey(grayApplicationInfoDO.getGroupId())) {
                infoResult.setGroupName(map.get(grayApplicationInfoDO.getGroupId()));
            }
            infoResult.setStatus(grayApplicationInfoDO.getStatus());
            infoResult.setDescribe(grayApplicationInfoDO.getDescribe());
            infoResult.setCreateTime(grayApplicationInfoDO.getCreateTime());
            infoResult.setUpdateTime(grayApplicationInfoDO.getUpdateTime());
            infoResult.setIpAddress(grayApplicationInfoDO.getIpAddress());
            applicationInfoResults.add(infoResult);
        }
        return applicationInfoResults;
    }


    /**
     * 获取分组
     *
     * @return Map<Long, String>
     */
    private Map<Long, String> getGroupMap() {
        List<GrayApplicationGroupConfigDO> groupConfigDOs = grayApplicationGroupConfigManager.queryGrayApplicationGroupConfig(null, null);
        Map<Long, String> map = new HashMap<>();
        for (GrayApplicationGroupConfigDO groupConfigDO : groupConfigDOs) {
            map.put(groupConfigDO.getId(), groupConfigDO.getGroupName());
        }
        return map;
    }
}
