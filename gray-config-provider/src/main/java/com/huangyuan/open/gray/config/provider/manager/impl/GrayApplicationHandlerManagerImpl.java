package com.huangyuan.open.gray.config.provider.manager.impl;

import com.facishare.wechat.gray.api.constant.StatusTypeEnum;
import com.huangyuan.open.gray.config.provider.constant.GrayConstant;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationGroupConfigManager;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationHandlerManager;
import com.huangyuan.open.gray.config.provider.manager.GrayApplicationInfoManager;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationGroupConfigDO;
import com.huangyuan.open.gray.config.provider.model.entity.GrayApplicationInfoDO;
import com.huangyuan.open.gray.config.provider.model.vo.GrayApplicationVO;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author suxq
 * @date 2018/8/30
 */
@Service
public class GrayApplicationHandlerManagerImpl implements GrayApplicationHandlerManager {
    @Resource
    private GrayApplicationInfoManager grayApplicationInfoManager;
    @Resource
    private GrayApplicationGroupConfigManager grayApplicationGroupConfigManager;

    @Override
    public GrayApplicationVO getGrayApplicationConfig(String applicationName) {
        GrayApplicationVO applicationVO = new GrayApplicationVO();
        List<GrayApplicationInfoDO> applicationInfoDOs = grayApplicationInfoManager.queryGrayApplicationInfo(applicationName, null);
        if (CollectionUtils.isNotEmpty(applicationInfoDOs)) {
            GrayApplicationInfoDO applicationInfoDO = applicationInfoDOs.get(0);
            applicationVO.setApplicationName(applicationInfoDO.getApplicationName());
            applicationVO.setApplicationStatus(applicationInfoDO.getStatus());

            // 获取该服务的灰度分组
            if (applicationInfoDO.getGroupId() != null) {
                List<GrayApplicationGroupConfigDO> groupConfigDOs = grayApplicationGroupConfigManager.
                        queryGrayApplicationGroupConfig(applicationInfoDO.getGroupId(), null);
                if (CollectionUtils.isNotEmpty(groupConfigDOs)) {
                    GrayApplicationGroupConfigDO group = groupConfigDOs.get(0);
                    applicationVO.setGroupName(group.getGroupName());
                    applicationVO.setGroupStatus(group.getStatus());
                }
            }

            // 获取该服务的灰度机器列表
            applicationVO.setIpAddressList(getIpAddress(applicationInfoDO.getIpAddress()));
        } else {
            return null;
        }

        return applicationVO;
    }

    @Override
    public Boolean checkGrayFsEa(String applicationName, String fsEa) {
        // 根据应用名称。查询应用信息
        List<GrayApplicationInfoDO> applicationInfoDOs = grayApplicationInfoManager.queryGrayApplicationInfo(applicationName, null);
        Boolean isGrayEa = Boolean.FALSE;

        if (CollectionUtils.isNotEmpty(applicationInfoDOs)) {
            GrayApplicationInfoDO applicationInfoDO = applicationInfoDOs.get(0);
            // 查询到分组信息（当前灰度环境）
            List<GrayApplicationGroupConfigDO> list =
                    grayApplicationGroupConfigManager.queryGrayApplicationGroupConfig(applicationInfoDO.getGroupId(), null);

            if (CollectionUtils.isNotEmpty(list)) {

                GrayApplicationGroupConfigDO grayApplicationGroupConfigDO = list.get(0);

                // 判断灰度总开关是否开启，开启状态下才能进行灰度
                boolean isOpen = grayApplicationGroupConfigDO.getStatus().compareTo(StatusTypeEnum.OPEN.getTypeCode()) == 0;

                if (isOpen) {
                    if (StringUtils.isNotBlank(grayApplicationGroupConfigDO.getFsEas())) {
                        String fsEas = grayApplicationGroupConfigDO.getFsEas();
                        String[] fsArr = fsEas.split(";");

                        for (String ea : fsArr) {
                            if (ea.equals("all") || fsEa.equals(ea)) {
                                isGrayEa = Boolean.TRUE;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return isGrayEa;
    }

    /**
     * 将IP字符串转换成IP地址列表
     *
     * @param ipAddress ip地址，如：10.21.0.7;10.32.1.1;
     * @return List<String>
     */
    private List<String> getIpAddress(String ipAddress) {
        String[] arr = ipAddress.split(GrayConstant.IP_ADDRESS_MARGIN);

        if (arr.length == 0) {
            return Lists.newArrayList();

        } else {
            List<String> ipList = new ArrayList<>(arr.length);
            CollectionUtils.addAll(ipList, arr);
            return ipList;
        }
    }
}
