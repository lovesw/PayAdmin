package com.pay.data.interceptors;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.pay.data.entity.Result;
import com.pay.data.utils.ResultUtils;

/**
 * 该拦截器用于所有请求都必须传递操作用户的userId,否则就无权限操作
 *
 * @createTime: 2018/3/27
 * @author: HingLo
 * @description: 请求参数拦截器
 */
public class ParaInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller controller = inv.getController();
        String userId = controller.getPara("userId");
        if (StrUtil.isNotBlank(userId)) {
            //将用户的userId放入的request中
            controller.setAttr("userId", userId);
            inv.invoke();
        } else {
            Result result = ResultUtils.error(-1, "请求参数不正确");
            controller.renderJson(result);
        }
    }
}
