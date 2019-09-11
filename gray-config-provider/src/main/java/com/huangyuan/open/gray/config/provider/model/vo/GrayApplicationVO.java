package com.huangyuan.open.gray.config.provider.model.vo;


import com.huangyuan.open.gray.config.api.constant.StatusTypeEnum;

import java.io.Serializable;
import java.util.List;


public class GrayApplicationVO implements Serializable{

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 分组标识（名称）
     */
    private String groupName;

    /**
     * 机器ip地址
     */
    private List<String> ipAddressList;

    /**
     * 服务状态（1：开启 2：关闭）
     */
    private Integer applicationStatus;

    /**
     * 分组状态（1：开启 2：关闭）
     */
    private Integer groupStatus;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<String> getIpAddressList() {
        return ipAddressList;
    }

    public void setIpAddressList(List<String> ipAddressList) {
        this.ipAddressList = ipAddressList;
    }

    public Integer getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(Integer applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    /**
     * 判断是否灰度
     *
     * @return true
     */
    public Boolean checkGray() {
        return this.groupStatus.compareTo(StatusTypeEnum.OPEN.getTypeCode()) == 0
                && this.getApplicationStatus().compareTo(StatusTypeEnum.OPEN.getTypeCode()) == 0;
    }

    @Override
    public String toString() {
        return "GrayApplicationVO{" +
                "applicationName='" + applicationName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", ipAddressList=" + ipAddressList +
                ", applicationStatus=" + applicationStatus +
                ", groupStatus=" + groupStatus +
                '}';
    }
}
