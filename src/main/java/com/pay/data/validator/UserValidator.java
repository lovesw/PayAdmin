package com.pay.data.validator;

import com.jfinal.core.Controller;

/**
 * @createTime: 2018/4/12
 * @author: HingLo
 * @description: 用户添加校验器
 */
public class UserValidator extends BaseValidator {
    @Override
    protected void validate(Controller c) {
        validateRequiredString("id", "idMsg", "员工编号不能为空");
        validateRequiredString("name", "nameMsg", "员工姓名不能为空");
        validateLong("department", "departmentMsg", "员工部门不能为空");
        validateLong("position", "positionMsg", "员工职位信息不能为空");
    }


}
