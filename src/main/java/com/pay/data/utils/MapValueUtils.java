package com.pay.data.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @createTime: 2018/1/9
 * @author: HingLo
 * @description: 用于定义一些常量对应的值
 */
public class MapValueUtils {

    public static Map<Integer, String> bannerTypeMap = initBannerTypeMap();

    private static Map<Integer, String> initBannerTypeMap() {
        HashMap<Integer, String> map = new HashMap<>(4);
        return map;
    }

}
