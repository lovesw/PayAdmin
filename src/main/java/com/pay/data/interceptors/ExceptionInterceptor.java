package com.pay.data.interceptors;


import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.pay.data.utils.FieldUtils;
import com.pay.data.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @createTime: 2018/2/11
 * @author: HingLo
 * @description: 全局异常拦截器处理
 */
@Slf4j
public class ExceptionInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        //全局拦截器，设置响应的头中允许访问指定的Header
        inv.getController().getResponse().setHeader("Access-Control-Expose-Headers", FieldUtils.AUTHORIZATION);
        try {
            inv.invoke();
        } catch (Exception e) {
            e.printStackTrace();
            inv.getController().renderJson(ResultUtils.error(-1, e.getMessage()));
        }
    }
}
