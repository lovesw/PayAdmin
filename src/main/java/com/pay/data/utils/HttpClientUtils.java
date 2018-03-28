package com.pay.data.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @createTime: 2018/3/6
 * @author: HingLo
 * @description: 网易云的网络请求
 */
public class HttpClientUtils {
    /**
     * 定义成功的响应码
     */
    public static final int RES_CODE = 200;

    private static final String APP_KEY = "ae3d9ab623932937a74b8982e757b40f";
    private static final String URL = "http://apis.juhe.cn/idcard/index";


    public static String getFormData(String url, String card) {
        //设置请求的URL
        HttpRequest httpRequest = HttpRequest.get(URL + url);
        Map<String, Object> params = new HashMap<>(3);
        params.put("key", APP_KEY);
        params.put("cardno", card);
        params.put("dtype", "json");
        //添加参数
        httpRequest.form(params);
        //执行请求
        HttpResponse httpResponse = httpRequest.execute();
        return httpResponse.body();

    }


    /**
     * 获取响应结果码
     *
     * @param result Json结果集
     * @return 返回响应吗
     */
    public static boolean getCode(String result) {
        JSON json = JSONUtil.parse(result);
        return 200 == Convert.toInt(json.getByPath("resultcode"));
    }

    /**
     * 从json格式数据中返回指定的名称的值
     *
     * @param result json格式的结果集
     * @param key    返回的key
     * @return 返回指定key的值
     */
    public static Object getResult(String result, String key) {
        JSON json = JSONUtil.parse(result);
        return json.getByPath(key);
    }


}
