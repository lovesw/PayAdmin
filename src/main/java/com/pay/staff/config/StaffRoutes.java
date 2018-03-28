package com.pay.staff.config;

import com.jfinal.config.Routes;
import com.pay.data.interceptors.LoginInterceptor;
import com.pay.data.interceptors.ParaInterceptor;
import com.pay.data.interceptors.PermissionInterceptor;
import com.pay.user.action.*;

/**
 * @createTime: 2018/3/27
 * @author: HingLo
 * @description: 员工相关的工作路由
 */
public class StaffRoutes extends Routes {

    @Override
    public void config() {
        //登录拦截
        addInterceptor(new LoginInterceptor());
        //添加全局的权限拦截器
        addInterceptor(new PermissionInterceptor());
        //请求参数拦截器
        addInterceptor(new ParaInterceptor());


        add("/s/i", ImageAction.class);
        add("/s/f", UserInfoAction.class);
        add("/s/e", ExperienceAction.class);
        add("/s/ec", EducationAction.class);
        add("/s/p", PunishAction.class);

    }
}
