package com.pay.data.enums;

/**
 * @createTime: 2018/1/4
 * @author: HingLo
 * @description: 错误类型返回定义
 */
public enum ExceptionEnum {
    /**
     * 请求头错误的返回码
     */
    HEADERERROR(-2, "请求头错误"),
    /**
     * token失效的返回码
     */
    TOKENINVALID(-3, "token invalid ,请重新登录"),


    NOTPERMISSION(403, "你没有权限访问该资源,请联系管理员");

    private Integer code;
    private String message;


    ExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public ExceptionEnum setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ExceptionEnum setMessage(String message) {
        this.message = message;
        return this;
    }
}
