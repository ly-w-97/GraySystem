package com.huangyuan.open.gray.config.provider.model.entity;

import java.io.Serializable;


public class GrayApplicationEaInfoDO implements Serializable{

    /**
     * 记录id
     */
    private Long id;

    /**
     * 企业账户id
     */
    private String fsEa;

    /**
     * 企业名称
     */
    private String eaName;

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

    public GrayApplicationEaInfoDO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFsEa() {
        return fsEa;
    }

    public void setFsEa(String fsEa) {
        this.fsEa = fsEa;
    }

    public String getEaName() {
        return eaName;
    }

    public void setEaName(String eaName) {
        this.eaName = eaName;
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
        return "GrayApplicationEaInfoDO{" +
                "id=" + id +
                ", fsEa='" + fsEa + '\'' +
                ", eaName='" + eaName + '\'' +
                ", describe='" + describe + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
