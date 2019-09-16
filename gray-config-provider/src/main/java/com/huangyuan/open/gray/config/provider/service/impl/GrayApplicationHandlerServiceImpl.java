package com.huangyuan.open.gray.config.provider.service.impl;

import com.huangyuan.open.gray.base.common.BeanUtils;
import com.huangyuan.open.gray.base.result.EserviceResult;
import com.huangyuan.open.gray.config.api.constant.GrayErrorCode;
import com.huangyuan.open.gray.config.api.model.arg.GetGrayApplicationConfigNewArg;
import com.huangyuan.open.gray.config.api.model.arg.GetGrayApplicationGroupConfigByEaArg;
import com.huangyuan.open.gray.config.api.model.result.GetGrayApplicationGroupConfigByEaResult;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationResult;
import com.huangyuan.open.gray.config.api.service.GrayApplicationHandlerService;
import com.huangyuan.open.gray.config.provider.constant.GrayConstant;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationGroupConfigManager;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationHandlerManager;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationInfoManager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationGroupConfigDO;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationInfoDO;
import com.huangyuan.open.gray.config.provider.model.vo.GrayApplicationVO;

import com.huangyuan.open.gray.config.provider.util.ConvertUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author huangy
 * @date 2018/8/27
 */
@Service
public class GrayApplicationHandlerServiceImpl implements GrayApplicationHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrayApplicationHandlerServiceImpl.class);

    @Resource
    private GrayApplicationHandlerManager grayApplicationHandlerManager;
    @Resource
    private GrayApplicationInfoManager grayApplicationInfoManager;
    @Resource
    private GrayApplicationGroupConfigManager grayApplicationGroupConfigManager;


    @Override
    @Cacheable(value = GrayConstant.GRAY_SERVICE_CACHE)
    public EserviceResult<GrayApplicationResult> getGrayApplicationConfig(String applicationName) {
        if (StringUtils.isBlank(applicationName)) {
            LOGGER.warn("Params illegal，applicationName={}", applicationName);
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(), GrayErrorCode.PARAMS_ERROR.getErrorMessage());
        }

        try {
            GrayApplicationVO grayApplicationVO = grayApplicationHandlerManager.getGrayApplicationConfig(applicationName);
            if (grayApplicationVO == null) {
                LOGGER.warn("grayApplicationVO is null, please check gray config, applicationName={}", applicationName);
                return new EserviceResult<>(GrayErrorCode.GRAY_CONFIG_NOT_EXISTS.getErrorCode(), GrayErrorCode.GRAY_CONFIG_NOT_EXISTS.getErrorMessage());
            }

            GrayApplicationResult grayApplicationResult = new GrayApplicationResult();
            BeanUtils.copeProperties(grayApplicationResult, grayApplicationVO);

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationResult);
        } catch (Exception e) {
            LOGGER.error("An exception occurred for getGrayApplicationConfig，applicationName={}",
                    applicationName, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @Cacheable(value = GrayConstant.GRAY_SERVICE_CACHE)
    public EserviceResult<Boolean> checkGrayFsEa(String applicationName, String fsEa) {
        if (StringUtils.isBlank(applicationName) || StringUtils.isBlank(fsEa)) {
            LOGGER.warn("Params illegal，applicationName={}, sEa={}", applicationName, fsEa);
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(), GrayErrorCode.PARAMS_ERROR.getErrorMessage());
        }

        try {

            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                    GrayErrorCode.SUCCESS.getDescription(), grayApplicationHandlerManager.checkGrayFsEa(applicationName, fsEa));
        } catch (Exception e) {
            LOGGER.error("An exception occurred for checkGrayFsEa，applicationName={}, fsEa={}",
                    applicationName, fsEa, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    @Override
    @Cacheable(value = GrayConstant.GRAY_SERVICE_CACHE)
    public EserviceResult<GrayApplicationResult> getGrayApplicationConfigNew(GetGrayApplicationConfigNewArg arg) {

        if (StringUtils.isEmpty(arg.getApplicationName()) || StringUtils.isEmpty(arg.getIp())) {
            LOGGER.warn("getGrayApplicationConfigNew fail, param error, arg={}", arg);
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(), GrayErrorCode.PARAMS_ERROR.getErrorMessage());
        }

        try {

            List<GrayApplicationInfoDO> applicationInfoDOs = grayApplicationInfoManager.queryGrayApplicationInfo(arg.getApplicationName(), null);

            List<String> ipAddressList;
            for (GrayApplicationInfoDO grayApplicationInfoDO : applicationInfoDOs) {
                ipAddressList = ConvertUtils.getIpAddress(grayApplicationInfoDO.getIpAddress());

                if (ipAddressList.contains(arg.getIp())) {
                    return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(), GrayErrorCode.SUCCESS.getDescription(),
                            genarateGrayApplicationResult(grayApplicationInfoDO));
                }
            }

            // 到这里，说明灰度配置不存在，配置有问题（或者该应用不参与灰度）
            return new EserviceResult<>(GrayErrorCode.GRAY_CONFIG_NOT_EXISTS.getErrorCode(),
                    GrayErrorCode.GRAY_CONFIG_NOT_EXISTS.getDescription());

        } catch (Exception e) {
            LOGGER.error("getGrayApplicationConfigNew fail, Exception occur，arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }

    private GrayApplicationResult genarateGrayApplicationResult(GrayApplicationInfoDO grayApplicationInfoDO) {

        GrayApplicationResult result = new GrayApplicationResult();

        result.setApplicationName(grayApplicationInfoDO.getApplicationName());
        result.setApplicationStatus(grayApplicationInfoDO.getStatus());

        // 获取该服务的灰度分组
        if (grayApplicationInfoDO.getGroupId() != null) {
            List<GrayApplicationGroupConfigDO> groupConfigDOs = grayApplicationGroupConfigManager.
                    queryGrayApplicationGroupConfig(grayApplicationInfoDO.getGroupId(), null);
            if (CollectionUtils.isNotEmpty(groupConfigDOs)) {
                GrayApplicationGroupConfigDO group = groupConfigDOs.get(0);
                result.setGroupName(group.getGroupName());
                result.setGroupStatus(group.getStatus());
            }
        }

        // 获取该服务的灰度机器列表
        result.setIpAddressList(ConvertUtils.getIpAddress(grayApplicationInfoDO.getIpAddress()));

        return result;
    }


    @Override
    @Cacheable(value = GrayConstant.GRAY_SERVICE_CACHE)
    public EserviceResult<GetGrayApplicationGroupConfigByEaResult> getGrayApplicationGroupConfigByEa(GetGrayApplicationGroupConfigByEaArg arg) {

        if (StringUtils.isEmpty(arg.getFsEa())) {
            LOGGER.warn("getGrayApplicationGroupConfigByEa fail, param error, arg={}", arg);
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(), GrayErrorCode.PARAMS_ERROR.getErrorMessage());
        }

        try {
            GetGrayApplicationGroupConfigByEaResult result = new GetGrayApplicationGroupConfigByEaResult();

            // 查询出全部分组信息
            List<GrayApplicationGroupConfigDO> groupConfigDOs = grayApplicationGroupConfigManager.
                    queryGrayApplicationGroupConfig(null, null);

            // 如果企业列表含有该企业，则返回
            for (GrayApplicationGroupConfigDO groupConfigDO : groupConfigDOs) {
                String fsEas = groupConfigDO.getFsEas();
                String[] fsArr = fsEas.split(";");

                for (String ea : fsArr) {
                    if (arg.getFsEa().equals(ea)) {
                        result.setGroupName(groupConfigDO.getGroupName());
                        result.setId(groupConfigDO.getId());
                        result.setStatus(groupConfigDO.getStatus());
                        return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(),
                                GrayErrorCode.SUCCESS.getDescription(), result);
                    }
                }
            }

            return new EserviceResult<>(GrayErrorCode.EA_IS_NOT_GRAY.getErrorCode(), GrayErrorCode.EA_IS_NOT_GRAY.getErrorMessage());

        } catch (Exception e) {
            LOGGER.error("getGrayApplicationGroupConfigByEa fail, Exception occur, arg={}", arg, e);
            return new EserviceResult<>(GrayErrorCode.SYSTEM_ERROR.getErrorCode(),
                    GrayErrorCode.SYSTEM_ERROR.getDescription());
        }
    }
}
