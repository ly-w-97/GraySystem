package com.huangyuan.open.gray.config.api.constant;

/**
 *
 * @author suxq
 * @date 2018/6/14
 */
public enum StatusTypeEnum {
    //开启
    OPEN(1, "(!@#$)wechat_union.gray.enum.status_open"),

    // 关闭
    CLOSE(2, "(!@#$)wechat_union.gray.enum.status_close"),;

    private Integer typeCode;
    private String typeDesc;

    StatusTypeEnum(Integer typeCode, String typeDesc) {
        this.typeCode = typeCode;
        this.typeDesc = typeDesc;
    }

    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }
}
