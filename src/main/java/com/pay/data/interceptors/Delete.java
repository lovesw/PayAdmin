package com.pay.data.interceptors;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.pay.data.utils.ResultUtils;

/**
 * @createTime: 2018/2/11
 * @author: HingLo
 * @description: delete拦截器
 */
public class Delete implements Interceptor {
    private final String method = "DELETE";

    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        if (method.equalsIgnoreCase(controller.getRequest().getMethod())) {
            inv.invoke();
        } else {
            controller.renderJson(ResultUtils.error(405, "请求方式不支持"));
        }
    }
}
