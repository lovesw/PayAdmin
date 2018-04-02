package com.pay.data.utils;

import com.pay.data.entity.Result;
import com.pay.data.enums.ExceptionEnum;


/**
 * @createTime: 2018/1/3
 * @author: HingLo
 * @description: 结果值返回类型定义
 */
public class ResultUtils {


    /**
     * 请求成功
     *
     * @param object 结果值返回对象
     * @return json格式数据Result对象
     */
    public static Result<Object> success(Object object) {
        return new Result<>(200, "操作成功", object);
    }


    /**
     * 默认成功的方法，不需要返回参数
     *
     * @return json格式数据Result对象
     */
    public static Result<Object> success() {
        return success(null);
    }


    /**
     * 请求失败，传入失败code,与信息
     *
     * @param code 错误码
     * @param msg  错误提示消息
     * @return json格式数据Result对象
     */
    public static Result<Object> error(Integer code, String msg) {
        return error(code, msg, null);
    }

    /**
     * 请求失败，传入失败code,与信息
     *
     * @param code 错误码
     * @param msg  错误消息信息
     * @param data 错误提示内容
     * @return json格式数据Result对象
     */
    public static Result<Object> error(Integer code, String msg, Object data) {
        return new Result<>(code, msg, data);
    }

    /**
     * 错误信息，返回默认的-1 代码，提示信息自定义
     *
     * @param msg 错误消息提示信息
     * @return json格式数据Result对象
     */
    public static Result<Object> error(String msg) {
        return error(-1, msg, null);
    }

    /**
     * 重载error方法，传入一个枚举类型，
     *
     * @param exceptionEnum 错误消息的枚举类型
     * @return json格式数据Result对象
     */
    public static Result<Object> error(ExceptionEnum exceptionEnum) {
        return error(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }


}
