package com.huangyuan.open.gray.config.api.model.result;

import java.io.Serializable;

/**
 *
 * @author suxq
 * @date 2018/8/27
 */
public class GrayApplicationGroupConfigResult implements Serializable{

    /**
     * 记录id
     */
    private Long id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 灰度企业账户
     */
    private String fsEas;

    /**
     * 状态 （1：开启 2：关闭）
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long updateTime;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFsEas() {
        return fsEas;
    }

    public void setFsEas(String fsEas) {
        this.fsEas = fsEas;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Override
    public String toString() {
        return "GrayApplicationGroupConfigDO{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", fsEas='" + fsEas + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", describe=" + describe +
                '}';
    }
}
