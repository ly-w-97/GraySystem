package com.huangyuan.open.gray.config.api.model.arg;

import java.io.Serializable;

/**
 * @author huangy on 2019-09-12
 */
public class GetGrayApplicationConfigNewArg implements Serializable {

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 机器ip
     *     用于找到ip处于哪个分组
     */
    private String ip;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "GetGrayApplicationConfigNewArg{" +
                "applicationName='" + applicationName + '\'' +
                ", ip='" + ip + '\'' +
                '}';
    }
}
