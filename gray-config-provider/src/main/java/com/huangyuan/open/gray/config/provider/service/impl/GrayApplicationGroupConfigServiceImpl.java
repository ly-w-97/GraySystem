package com.huangyuan.open.gray.config.provider.service.impl;

import com.facishare.eservice.base.result.EserviceResult;
import com.facishare.open.common.storage.mysql.dao.Pager;
import com.facishare.wechat.gray.api.constant.GrayErrorCode;
import com.facishare.wechat.gray.api.model.arg.GrayApplicationGroupConfigArg;
import com.facishare.wechat.gray.api.model.result.GrayApplicationGroupConfigResult;
import com.facishare.wechat.gray.api.service.GrayApplicationGroupConfigService;
import com.huangyuan.open.gray.config.provider.constant.GrayConstant;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationGroupConfigManager;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationInfoManager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationGroupConfigDO;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationInfoDO;
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
 * @author suxq
 * @date 2018/8/27
 */
@Service
public class GrayApplicationGroupConfigServiceImpl implements GrayApplicationGroupConfigService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrayApplicationGroupConfigServiceImpl.class);

    @Resource
    private GrayApplicationGroupConfigManager grayApplicationGroupConfigManager;
    @Resource
    private GrayApplicationInfoManager grayApplicationInfoManager;

    @Override
    public EserviceResult<Pager<GrayApplicationGroupConfigResult>> queryGrayApplicationGroupConfigPage(Pager<GrayApplicationGroupConfigResult> pager,
                                                                                                       GrayApplicationGroupConfigArg arg) {

        if (pager == null) {
            LOGGER.warn("Params illegal, pager is null, arg={}", arg);
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        Pager<GrayApplicationGroupConfigDO> queryPage = new Pager<>();
        queryPage.setCurrentPage(pager.getCurrentPage());
        queryPage.setPageSize(pager.getPageSize());

        if (StringUtils.isNotBlank(arg.getGroupName())) {
            queryPage.addParam("groupName", arg.getGroupName());
        }
        if (arg.getStatus() != null) {
            queryPage.addParam("status", arg.getStatus());
        }

        List<GrayApplicationGroupConfigResult> datas = Lists.newArrayList();
        try {
            queryPage = grayApplicationGroupConfigManager.queryGrayApplicationGroupConfigPage(queryPage);
            datas = buildResult(queryPage.getData());
            pager.setRecordSize(queryPage.getRecordSize());
            pager.setData(datas);
            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), pager);
        } catch (Exception e) {
            LOGGER.error("An exception occurred queryGrayApplicationGroupConfigPage, pager={}, arg={}", pager, arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    public EserviceResult<List<GrayApplicationGroupConfigResult>> queryGrayGroupConfigList(GrayApplicationGroupConfigArg arg) {
        if (arg == null) {
            LOGGER.warn("Params illegal, pager is null");
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {
            List<GrayApplicationGroupConfigResult> datas = Lists.newArrayList();
            List<GrayApplicationGroupConfigDO> list = grayApplicationGroupConfigManager.queryGrayApplicationGroupConfig(null, null);
            datas = buildResult(list);
            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), datas);
        } catch (Exception e) {
            LOGGER.error("An exception occurred queryGrayGroupConfigList, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> updateGroupConfig(GrayApplicationGroupConfigArg arg) {
        if (arg == null
                || arg.getId() == null
                || StringUtils.isBlank(arg.getGroupName())) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationGroupConfigManager
                    .updateGroupConfig(arg.getId(), arg.getGroupName(), arg.getDescribe(), arg.getFsEas(),arg.getStatus()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> delGrayApplicationGroupConfig(GrayApplicationGroupConfigArg arg) {
        if (arg == null
                || arg.getId() == null) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            //校验是否还有被灰度服务引用
            List<GrayApplicationInfoDO> list = grayApplicationInfoManager.queryGrayApplicationInfo(null, arg.getId());
            if (CollectionUtils.isEmpty(list)) {
                LOGGER.warn(" gray group config have applicationInfo,don't del it, arg={}, list={}", arg, list);
                return new EserviceResult<>(GrayErrorCode.GRAY_APPLICATION_GROUP_DELETE.getErrorCode(),
                        GrayErrorCode.GRAY_APPLICATION_GROUP_DELETE.getDescription());
            }

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationGroupConfigManager
                    .delGrayApplicationGroupConfig(arg.getId()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @CacheEvict(value = GrayConstant.GRAY_SERVICE_CACHE, allEntries=true)
    public EserviceResult<Boolean> insertGrayApplicationGroupConfig(GrayApplicationGroupConfigArg arg) {
        if (arg == null
                || StringUtils.isBlank(arg.getGroupName())) {
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(),
                    GrayErrorCode.PARAMS_ERROR.getDescription());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationGroupConfigManager
                    .insertGrayApplicationGroupConfig(arg.getGroupName(), arg.getStatus(), arg.getDescribe(), arg.getFsEas()));
        } catch (Exception e) {
            LOGGER.error("An exception occurred, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    private List<GrayApplicationGroupConfigResult> buildResult(List<GrayApplicationGroupConfigDO> list) {
        List<GrayApplicationGroupConfigResult> groupConfigResults = Lists.newArrayList();

        if (CollectionUtils.isEmpty(list)) {
            return groupConfigResults;
        }

        GrayApplicationGroupConfigResult groupConfigResult;
        for (GrayApplicationGroupConfigDO groupConfigDO : list) {
            groupConfigResult = new GrayApplicationGroupConfigResult();
            groupConfigResult.setId(groupConfigDO.getId());
            groupConfigResult.setGroupName(groupConfigDO.getGroupName());
            groupConfigResult.setFsEas(groupConfigDO.getFsEas());
            groupConfigResult.setStatus(groupConfigDO.getStatus());
            groupConfigResult.setDescribe(groupConfigDO.getDescribe());
            groupConfigResult.setCreateTime(groupConfigDO.getCreateTime());
            groupConfigResult.setUpdateTime(groupConfigDO.getUpdateTime());
            groupConfigResults.add(groupConfigResult);
        }
        return groupConfigResults;
    }
}
