package com.huangyuan.open.gray.config.provider.model.entity;

import java.io.Serializable;


public class GrayApplicationIpConfigDO implements Serializable{
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

    public GrayApplicationIpConfigDO() {
    }

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
                ", describe='" + describe + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
