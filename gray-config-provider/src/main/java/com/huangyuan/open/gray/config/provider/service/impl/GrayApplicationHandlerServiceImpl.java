package com.huangyuan.open.gray.config.provider.service.impl;


import com.facishare.common.BeanUtils;
import com.facishare.eservice.base.result.EserviceResult;
import com.facishare.wechat.gray.api.constant.GrayErrorCode;
import com.facishare.wechat.gray.api.model.result.GrayApplicationResult;
import com.facishare.wechat.gray.api.service.GrayApplicationHandlerService;
import com.huangyuan.open.gray.config.provider.constant.GrayConstant;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationHandlerManager;
import com.huangyuan.open.gray.config.provider.model.vo.GrayApplicationVO;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author suxq
 * @date 2018/8/27
 */
@Service
public class GrayApplicationHandlerServiceImpl implements GrayApplicationHandlerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GrayApplicationHandlerServiceImpl.class);

    @Resource
    private GrayApplicationHandlerManager grayApplicationHandlerManager;


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
}
