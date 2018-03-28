package com.pay.data.utils;

import cn.hutool.core.util.StrUtil;
import com.pay.data.exception.ResultException;

import java.util.regex.Pattern;

/**
 * 数据校验方法
 *
 * @author: HingLo
 * @createTime 2017-09-13 11:44
 **/
public class CheckDataUtils {


    /**
     * ID检测，用于检测ID格式的有效性
     *
     * @param str 5-30位，字符，中划线，下划线组成
     * @return
     */
    public static boolean checkIdUtils(String str) {
        int max = 30;
        int min = 5;
        if (StrUtil.isNotBlank(str)) {
            if (str.length() > max || str.length() < min) {
                return false;
            }
            String pattern = "^[a-zA-Z0-9_-]{5,30}$";
            return Pattern.matches(pattern, str);
        }
        return false;
    }

    /**
     * ID检测，用于检测ID格式的有效性
     *
     * @param str 数组
     * @return
     */
    public static boolean checkIdUtils(String... str) {
        for (String s : str) {
            if (!checkIdUtils(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验Id格式是否通过
     *
     * @param str
     * @return
     */
    public static boolean checkIdResultUtils(String str) {
        if (checkIdUtils(str)) {
            return true;
        } else {
            throw new ResultException(-1, "ID校验未通过");
        }

    }


}
