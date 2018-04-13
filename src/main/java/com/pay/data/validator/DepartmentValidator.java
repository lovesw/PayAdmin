package com.pay.data.validator;

import com.jfinal.core.Controller;
import com.pay.data.utils.ResultUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @createTime: 2018/4/11
 * @author: HingLo
 * @description: 部门职位管理校验
 */
public class DepartmentValidator extends BaseValidator {
    @Override
    protected void validate(Controller c) {
        validateLong("pid", "pidMsg", "请选择部门或者公司");
        validateRequiredString("name", "namedMsg", "请输入部门或者职称的名称");
    }

}
