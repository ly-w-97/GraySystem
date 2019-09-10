package com.huangyuan.open.gray.config.api.model.arg;

import java.io.Serializable;

/**
 *
 * @author suxq
 * @date 2018/8/27
 */
public class GrayApplicationInfoArg implements Serializable{

    /**
     * 记录id
     */
    private Long id;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 状态（1：开启  2：关闭）
     */
    private Integer status;

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

    private String ipAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Override
    public String toString() {
        return "GrayApplicationInfoArg{" +
                "id=" + id +
                ", applicationName='" + applicationName + '\'' +
                ", groupId=" + groupId +
                ", status=" + status +
                ", describe='" + describe + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
