package com.pay.data.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @createTime: 2018/1/3
 * @author: HingLo
 * @description: Json处理工具
 */
public class JsonUtils {
    public static final String encoding = "utf-8";

    /**
     * object 转为json格式的字符串
     *
     * @param object
     * @return
     */
    public static String objectToJson(Object object) {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{cod:0,msg:'json解析错误'}";
    }
}
