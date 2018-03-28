package com.pay.data.utils;

import cn.hutool.core.util.StrUtil;

/**
 * @createTime: 2017/12/29
 * @author: HingLo
 * @description: 字符串处理工具类
 */
public class StringUtils {

    /**
     * 定义数组不是null，不是空白字符串
     *
     * @param str 字符串数组
     * @return
     */
    public static boolean isNotBlank(String... str) {
        for (String s : str) {
            if (StrUtil.isBlank(s)) {
                return false;
            }
        }
        return true;
    }

}
