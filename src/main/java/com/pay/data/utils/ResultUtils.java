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
     * @param object
     * @return
     */
    public static Result success(Object object) {
        return new Result(200, "操作成功", object);
    }


    /**
     * 默认成功的方法，不需要返回参数
     *
     * @return
     */
    public static Result success() {
        return success(null);
    }


    /**
     * 请求失败，传入失败code,与信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code, String msg) {
        return new Result(code, msg, null);
    }

    /**
     * 请求失败，传入失败code,与信息
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result error(Integer code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    /**
     * 错误信息，返回默认的-1 代码，提示信息自定义
     *
     * @param msg
     * @return
     */
    public static Result error(String msg) {
        return new Result(-1, msg, null);
    }

    /**
     * 重载error方法，传入一个枚举类型，
     *
     * @param exceptionEnum
     * @return
     */
    public static Result error(ExceptionEnum exceptionEnum) {
        return new Result(exceptionEnum.getCode(), exceptionEnum.getMessage(), null);
    }


}
