package com.pay.data.validator;

import com.jfinal.core.Controller;
import com.pay.data.utils.ResultUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @createTime: 2018/3/7
 * @author: HingLo
 * @description: 登录参数校验
 */
public class LoginValidator extends BaseValidator {
    @Override
    protected void validate(Controller c) {
        validateNumberString("username", "nameMsg", "用户名格式不正确");
        validateRequiredString("password", "passwordMsg", "请输入密码");
    }
}
