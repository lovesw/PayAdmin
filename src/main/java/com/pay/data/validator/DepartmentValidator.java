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
        validateNumberString("pid", "pidMsg", "请选择部门或者公司");
        validateRequiredString("name", "namedMsg", "请输入部门或者职称的名称");
    }

    @Override
    protected void handleError(Controller c) {
        Map<String, Object> mapError = new HashMap<>(2);
        Enumeration<String> enumeration = c.getAttrNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            mapError.put(key, c.getAttr(key));
        }
        c.renderJson(ResultUtils.error(-1, "请求参数校验失败", mapError));
    }
}
