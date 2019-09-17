package com.huangyuan.open.gray.base.exception;

import com.huangyuan.open.gray.base.enums.BaseEnum;

import java.io.Serializable;


/**
 * 封装一层业务异常；
 */
public class EserviceException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -1604099877336215370L;

    /**
     * 错误码；
     */
    private String errCode;

    /**
     * 错误信息；
     */
    private String errMsg;

    public EserviceException(BaseEnum baseEnum) {
        super(baseEnum.i18Text());
        this.errCode = baseEnum.getErrorCode();
        this.errMsg = this.getMessage();
    }

    public EserviceException(BaseEnum baseEnum, Object... args) {
        super(baseEnum.i18Text(args));
        this.errCode = baseEnum.getErrorCode();
        this.errMsg = this.getMessage();
    }

    public EserviceException(String errCode, String errMsg) {
        super(errMsg);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public EserviceException(String errCode, String errMsg, Throwable e) {
        super(e);
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    @Override
    public String toString() {
        return "EserviceException{" +
                "errCode='" + errCode + '\'' +
                ", errMsg='" + errMsg + '\'' +
                "} " + super.toString();
    }
}
