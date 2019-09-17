package com.huangyuan.open.gray.config.provider.service.impl;

import com.huangyuan.open.gray.base.common.Pager;
import com.huangyuan.open.gray.base.exception.EserviceException;
import com.huangyuan.open.gray.base.result.EserviceResult;
import com.huangyuan.open.gray.config.api.constant.GrayErrorCode;
import com.huangyuan.open.gray.config.api.constant.StatusTypeEnum;
import com.huangyuan.open.gray.config.api.model.arg.GrayApplicationGroupConfigArg;
import com.huangyuan.open.gray.config.api.model.arg.GrayApplicationInfoArg;
import com.huangyuan.open.gray.config.api.model.arg.UpdateGrayIpListArg;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationGroupConfigResult;
import com.huangyuan.open.gray.config.api.model.result.GrayApplicationInfoResult;
import com.huangyuan.open.gray.config.api.service.GrayApplicationGroupConfigService;
import com.huangyuan.open.gray.config.api.service.GrayApplicationInfoService;
import com.huangyuan.open.gray.config.api.service.GraySupportService;
import com.huangyuan.open.gray.config.provider.util.RedisUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huangy on 2019-08-02
 */
@Service
public class GraySupportServiceImpl implements GraySupportService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraySupportServiceImpl.class);

    private static final String REDIS_KEY_GRAY_MQ_LISTENER_IP_LIST = "%s:mqlistener:iplist";

    private static final String DEFAULT_GRAY_IP = "";

    @Resource
    private GrayApplicationInfoService grayApplicationInfoService;
    @Resource
    private GrayApplicationGroupConfigService grayApplicationGroupConfigService;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public EserviceResult<Boolean> updateGrayIpList(UpdateGrayIpListArg arg) {

        if ((arg == null) || arg.invalid()) {
            LOGGER.warn("GraySupportServiceImpl fail, param error, arg={}", arg);
            return new EserviceResult<>(GrayErrorCode.PARAMS_ERROR.getErrorCode(), GrayErrorCode.PARAMS_ERROR.getErrorMessage());
        }

        String key = String.format(REDIS_KEY_GRAY_MQ_LISTENER_IP_LIST, arg.getApplicationName());

        // 查询灰度服务
        GrayApplicationInfoResult grayApplicationInfoResult = getGrayApplicationInfoResult(arg.getApplicationName());

        if (checkIsNotOpenGray(grayApplicationInfoResult.getStatus())) {
            // 服务没有开启灰度，就不用继续往下查了，存入空字符串，则这个服务下所有机器都会监听mq
            setDefaultIpListToRedis(key);
            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(), GrayErrorCode.SUCCESS.getErrorMessage(), true);
        }

        // 查询灰度分组
        GrayApplicationGroupConfigResult grayApplicationGroupConfigResult = getGrayApplicationGroupConfigResult(grayApplicationInfoResult);

        if (checkIsNotOpenGray(grayApplicationGroupConfigResult.getStatus())) {
            // 分组没有开启灰度，直接存入空字符串，则这个服务下所有机器都会监听mq
            setDefaultIpListToRedis(key);
            return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(), GrayErrorCode.SUCCESS.getErrorMessage(), true);
        }

        boolean result = redisUtil.set(key, grayApplicationInfoResult.getIpAddress());

        if (!result) {
            LOGGER.warn("updateGrayIpList set redis fail, key={}, ip={}",
                    key, grayApplicationInfoResult.getIpAddress());
        }

        return new EserviceResult<>(GrayErrorCode.SUCCESS.getErrorCode(), GrayErrorCode.SUCCESS.getErrorMessage(), result);
    }

    private GrayApplicationInfoResult getGrayApplicationInfoResult(String applicationName) {
        Pager<GrayApplicationInfoResult> pager = new Pager<>();
        GrayApplicationInfoArg grayApplicationInfoArg = new GrayApplicationInfoArg();
        grayApplicationInfoArg.setApplicationName(applicationName);
        EserviceResult<Pager<GrayApplicationInfoResult>> eserviceResult =
                grayApplicationInfoService.queryGrayApplicationInfoPage(pager, grayApplicationInfoArg);
        LOGGER.info("getGrayApplicationInfoResult, grayApplicationInfoService.queryGrayApplicationInfoPage, grayApplicationInfoArg={}, eserviceResult={}",
                grayApplicationInfoArg, eserviceResult);
        if ((eserviceResult == null) || !eserviceResult.isSuccess() || (eserviceResult.getData() == null)) {
            throw new EserviceException(GrayErrorCode.SYSTEM_ERROR.getErrorCode(), GrayErrorCode.SYSTEM_ERROR.getErrorMessage());
        }

        if (CollectionUtils.isEmpty(eserviceResult.getData().getData())) {
            LOGGER.warn("getGrayApplicationInfoResult fail, arg is invalid, applicationName={}", applicationName);
            throw new EserviceException(GrayErrorCode.PARAMS_ERROR.getErrorCode(), GrayErrorCode.PARAMS_ERROR.getErrorMessage());
        }

        return eserviceResult.getData().getData().get(0);
    }

    private GrayApplicationGroupConfigResult getGrayApplicationGroupConfigResult(GrayApplicationInfoResult grayApplicationInfoResult) {
        Pager<GrayApplicationGroupConfigResult> pager = new Pager<>();
        GrayApplicationGroupConfigArg arg = new GrayApplicationGroupConfigArg();
        arg.setGroupName(grayApplicationInfoResult.getGroupName());
        EserviceResult<Pager<GrayApplicationGroupConfigResult>> eserviceResult
                = grayApplicationGroupConfigService.queryGrayApplicationGroupConfigPage(pager, arg);
        LOGGER.info("getGrayApplicationGroupConfigResult, grayApplicationGroupConfigService.queryGrayApplicationGroupConfigPage, arg={}, eserviceResult={}",
                arg, eserviceResult);

        if ((eserviceResult == null) || !eserviceResult.isSuccess() || (eserviceResult.getData() == null)) {
            throw new EserviceException(GrayErrorCode.SYSTEM_ERROR.getErrorCode(), GrayErrorCode.SYSTEM_ERROR.getErrorMessage());
        }

        if (CollectionUtils.isEmpty(eserviceResult.getData().getData())) {
            LOGGER.warn("getGrayApplicationGroupConfigResult fail, arg is invalid, arg={}", arg);
            throw new EserviceException(GrayErrorCode.SYSTEM_ERROR.getErrorCode(), GrayErrorCode.SYSTEM_ERROR.getErrorMessage());
        }

        return eserviceResult.getData().getData().get(0);
    }

    private boolean checkIsNotOpenGray(Integer status) {
        return StatusTypeEnum.CLOSE.getTypeCode().equals(status);
    }

    private void setDefaultIpListToRedis(String key) {
        redisUtil.set(key, DEFAULT_GRAY_IP);
    }
}

