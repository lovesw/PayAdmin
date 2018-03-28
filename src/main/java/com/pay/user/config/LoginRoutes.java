package com.pay.user.config;

import com.jfinal.config.Routes;
import com.pay.data.interceptors.LoginInterceptor;
import com.pay.user.action.*;

/**
 * @createTime: 2018/3/20
 * @author: HingLo
 * @description: 仅仅为登录拦截的权限。即所有用户都拥有的权限
 */
public class LoginRoutes extends Routes {
    @Override
    public void config() {
        addInterceptor(new LoginInterceptor());
        add("/u/i", ImageAction.class);
        add("/u/f", UserInfoAction.class);
        add("/u/e", ExperienceAction.class);
        add("/u/ec", EducationAction.class);
        add("/u/p", PunishAction.class);
    }
}
