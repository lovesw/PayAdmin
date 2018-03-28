package com.pay.user.config;

import com.jfinal.config.Routes;
import com.pay.data.interceptors.LoginInterceptor;
import com.pay.user.action.ImageAction;
import com.pay.user.action.LoginAction;

/**
 * @createTime: 2018/2/28
 * @author: HingLo
 * @description: 路由管理
 */
public class UserRoutes extends Routes {
    @Override
    public void config() {
        add("/u", LoginAction.class);
    }


}
