package com.huangyuan.open.gray.config.api.model.arg;

import java.io.Serializable;

/**
 * @author huangy on 2019-09-12
 */
public class GetGrayApplicationGroupConfigByEaArg implements Serializable {

    private String fsEa;

    public String getFsEa() {
        return fsEa;
    }

    public void setFsEa(String fsEa) {
        this.fsEa = fsEa;
    }

    @Override
    public String toString() {
        return "GetGrayApplicationGroupConfigByEaArg{" +
                "fsEa='" + fsEa + '\'' +
                '}';
    }
}
