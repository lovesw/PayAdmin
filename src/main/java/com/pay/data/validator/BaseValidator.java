package com.pay.data.validator;

import cn.hutool.core.util.NumberUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;
import com.jfinal.validate.Validator;
import com.pay.data.utils.ResultUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @createTime: 2018/3/7
 * @author: HingLo
 * @description: 参数验证的基类，可以写一些公用的校验
 */
abstract class BaseValidator extends Validator {
    /**
     * //校验手机号码正则表达式
     */
    private static final String MOBILE_PATTERN = "\\b(1[3,5,7,8,9]\\d{9})\\b";

    /**
     * 验证手机号码
     *
     * @param field    需要校验的字段名称
     * @param errorKey 错误的key
     * @param errorMsg 错误信息
     */
    protected void validateMobilePattern(String field, String errorKey, String errorMsg) {
        validateRegex(field, MOBILE_PATTERN, false, errorKey, errorMsg);
    }

    /**
     * 验证该字符串只能是纯数字组成
     *
     * @param field    需要校验的字段名称
     * @param errorKey 错误的key
     * @param errorMsg 错误信息
     */
    void validateNumberString(String field, String errorKey, String errorMsg) {
        String para = controller.getPara(field).trim();
        if (StrKit.isBlank(para) || !NumberUtil.isNumber(para)) {
            addError(errorKey, errorMsg);
        }
    }

    @Override
    protected void handleError(Controller c) {
        Map<String, Object> mapError = new HashMap<>(1);
        Enumeration<String> enumeration = c.getAttrNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            mapError.put(key, c.getAttr(key));
        }
        c.renderJson(ResultUtils.error(-1, "请求参数校验失败", mapError));
    }
}
