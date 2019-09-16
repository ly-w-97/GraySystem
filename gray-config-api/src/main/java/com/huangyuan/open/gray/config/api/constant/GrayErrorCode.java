package com.huangyuan.open.gray.config.api.constant;

/**
 * * 微联服务号客服错误码，采用纷享标准错误码格式：
 * 0： 端，C、S
 * 1：状态，1成功、2警告、3失败
 * 2-3：业务，企业互联20
 * 4-5：模块，客服系统06
 * 6-9：业务自定义状态码  0000-2000 服务通使用
 *
 * 客服接入微信客户消息错误码定义，前缀如下，后面加4位的自定义错误码
 * 成功的：C12006
 * 警告的：C22006
 * 失败的：C32006
 * Create by suxq on 2018/05/14.
 * @author suxq
 */
public enum GrayErrorCode {
    // 成功
    SUCCESS("C120070000", "OK", "(!@#$)wechat_union.core.check_controller.success"),

    // 警告
    PARAMS_ERROR("C220070001", "params error", "(!@#$)wechat_union.web.check_impl.parameter_ error"),

    // 失败
    SERVER_BUSY("C320070001", "server is busy", "(!@#$)wechat_union.web.check_impl.busy_ system"),
    SYSTEM_ERROR("C320070002", "system error", "系统错误"),

    //该灰度服务正在被机器IP引用，不可删除
    GRAY_APPLICATION_INFO_DELETE("C320070003", "gray application have IP, don't delete it", "(!@#$)wechat_union.gray.code.application_info_delete"),
    GRAY_APPLICATION_GROUP_DELETE("C320070004", "gray group config have applicationInfo,don't delete it", "(!@#$)wechat_union.gray.code.application_group_delete"),

    // 灰度配置不存在
    GRAY_CONFIG_NOT_EXISTS("C320070005", "gray config is not exists", "灰度配置不存在"),

    EA_IS_NOT_GRAY("C320070005", "the ea is not gray, can not find gray group name", "这是一个非灰度企业，无法找到其所在分组"),
    ;

    private String errorCode;
    private String errorMessage;
    private String description;

    GrayErrorCode(String errorCode, String errorMessage, String description) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.description = description;
    }

    GrayErrorCode(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }


    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public String getDescription() {
        return this.description;
    }


    @Override
    public String toString() {
        return "ErrorCode{" +
                "errorCode=" + errorCode +
                ", errorMessage='" + errorMessage + '\'' +
                ", description='" + description + '\'' +
                "} " + super.toString();
    }
}
