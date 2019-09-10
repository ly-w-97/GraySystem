package com.huangyuan.open.gray.config.api.model.result;

import java.io.Serializable;

/**
 *
 * @author suxq
 * @date 2018/8/27
 */
public class GrayApplicationIpConfigResult implements Serializable{
    /**
     * 记录id
     */
    private Long id;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 灰度服务应用id
     */
    private Long applicationId;

    /**
     * 灰度服务应用名称
     */
    private String applicationName;

    /**
     * 描叙
     */
    private String describe;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "GrayApplicationIpConfigDO{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", describe='" + describe + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
