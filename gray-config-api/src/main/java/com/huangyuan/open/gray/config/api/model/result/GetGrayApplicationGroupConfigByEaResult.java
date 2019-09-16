package com.huangyuan.open.gray.config.api.model.result;

import java.io.Serializable;

/**
 * @author huangy on 2019-09-12
 */
public class GetGrayApplicationGroupConfigByEaResult implements Serializable {

    /**
     * 记录id
     */
    private Long id;

    /**
     * 分组名称
     */
    private String groupName;

    /**
     * 状态 （1：开启 2：关闭）
     */
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "GetGrayApplicationGroupConfigByEaResult{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", status=" + status +
                '}';
    }
}
