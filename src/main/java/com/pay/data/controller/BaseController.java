package com.pay.data.controller;

import com.jfinal.core.Controller;
import com.pay.data.enums.ExceptionEnum;
import com.pay.data.utils.ResultUtils;

import java.util.Date;

/**
 * @createTime: 2018/2/9
 * @author: HingLo
 * @description: 控制器
 */
public class BaseController extends Controller {
    /**
     * 获取用户的Id
     *
     * @return 返回员工编号
     */
    protected String getUserId() {
        return getAttr("userId");
    }


    /**
     * 成功后的返回
     *
     * @param data 返回对象
     */
    protected void success(Object data) {
        renderJson(ResultUtils.success(data));
    }

    /**
     * 不带返回值的成功返回
     */
    protected void success() {
        renderJson(ResultUtils.success());
    }

    /**
     * 带返回消息的错误返回
     *
     * @param msg 错误信息
     */
    protected void error(String msg) {
        renderJson(ResultUtils.error(msg));
    }

    /**
     * 带错误码回码与错误消息的错误返回值
     *
     * @param code 错我代码
     * @param msg  错误信息
     */
    protected void error(int code, String msg) {
        renderJson(ResultUtils.error(code, msg));
    }

    /**
     * 传入枚举类型的错误信息
     *
     * @param exceptionEnum 定义好的枚举类型的错误对象
     */
    protected void error(ExceptionEnum exceptionEnum) {
        error(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    /**
     * 需要判断的返回的结果，使用此方法，且操作成功后，无数据返回
     *
     * @param bool 操作是否成功
     */
    protected void result(boolean bool) {
        if (bool) {
            success();
        } else {
            error("操作失败");
        }
    }

    /**
     * 携带错误消息提示的结果
     *
     * @param bool 操作结果（True/False)
     * @param msg  错误就返回消息
     */
    protected void result(boolean bool, String msg) {
        if (bool) {
            success();
        } else {
            error(msg);
        }
    }

    /**
     * 携带成功数据的消息结果
     *
     * @param bool 操作结果
     * @param msg  携带成功需要数据
     */
    protected void result(boolean bool, Object msg) {
        if (bool) {
            success(msg);
        } else {
            error("操作失败");
        }
    }

}
