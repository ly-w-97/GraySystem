package com.huangyuan.open.gray.config.api.model.arg;

import java.io.Serializable;

/**
 *
 * @author suxq
 * @date 2018/8/27
 */
public class GrayApplicationIpConfigArg implements Serializable{
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

    @Override
    public String toString() {
        return "GrayApplicationIpConfigArg{" +
                "id=" + id +
                ", ipAddress='" + ipAddress + '\'' +
                ", applicationId='" + applicationId + '\'' +
                ", describe='" + describe + '\'' +
                '}';
    }
}
