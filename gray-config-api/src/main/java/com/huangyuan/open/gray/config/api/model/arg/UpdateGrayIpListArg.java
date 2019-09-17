package com.huangyuan.open.gray.config.api.model.arg;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author huangy on 2019-08-02
 */
public class UpdateGrayIpListArg implements Serializable {

    /**
     * 服务名称
     */
    private String applicationName;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public boolean invalid() {
        return StringUtils.isEmpty(applicationName);
    }

    @Override
    public String toString() {
        return "UpdateGrayIpListArg{" +
                "applicationName='" + applicationName + '\'' +
                '}';
    }
}
