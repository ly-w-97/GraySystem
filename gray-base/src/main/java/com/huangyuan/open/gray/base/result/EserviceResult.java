package com.huangyuan.open.gray.base.result;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author huangy on 2019-09-10
 */
public class EserviceResult<T> implements Serializable {

    private static final long serialVersionUID = -2506165336796218885L;

    private String errCode;

    private String errMsg;

    private T data;

    private String traceMsg;

    public EserviceResult() {

    }

    public EserviceResult(String errCode, String errMsg) {
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public EserviceResult(String errCode, String errMsg, T data) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTraceMsg() {
        return traceMsg;
    }

    public void setTraceMsg(String traceMsg) {
        this.traceMsg = traceMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public boolean isSuccess() {
        return StringUtils.isNotBlank(getErrCode())
                && getErrCode().length() > 1
                && getErrCode().substring(1, 2).equals("1");
    }

    @Override
    public String toString() {
        return "NewModelResult [errCode=" + errCode + ", errMsg=" + errMsg
                + ", data=" + data + ", traceMsg=" + traceMsg + "]";
    }

}
