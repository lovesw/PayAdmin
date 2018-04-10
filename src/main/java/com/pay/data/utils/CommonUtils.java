package com.pay.data.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

import java.util.Date;

/**
 * @createTime: 2018/4/4
 * @author: HingLo
 * @description: 通常用的一些工具方法
 */
public class CommonUtils {

    /**
     * 获取上个月的1号
     *
     * @return 上个月的一号日期
     */
    public static Date getLastMonth() {
        return DateUtil.offsetMonth(getOneMonth(), -1);
    }

    /***
     *
     * 获取每个月的1号
     * @return
     */
    public static Date getOneMonth() {
        String dateStr = DateUtil.format(new Date(), "yyyy-MM") + "-01";
        return DateUtil.parse(dateStr);
    }

    /**
     * 定义数组不是null，不是空白字符串
     *
     * @param str 字符串数组
     * @return 是否不是null，空白字符，不可见字符
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
