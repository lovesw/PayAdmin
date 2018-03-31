package com.pay.user.config;

import com.jfinal.config.Routes;
import com.pay.data.interceptors.LoginInterceptor;
import com.pay.data.interceptors.ParaInterceptor;
import com.pay.data.interceptors.PermissionInterceptor;
import com.pay.user.action.EducationAction;
import com.pay.user.action.ExperienceAction;
import com.pay.user.action.ImageAction;
import com.pay.user.action.UserInfoAction;

/**
 * 此下的路由是因为需要添加权限拦截，与传入指定用户的参数拦截器。其主要操作是用户操作。多数 情况只是用于查看
 *
 * @createTime: 2018/3/29
 * @author: HingLo
 * @description: 员工相关的，但是需要权限拦截的,并且需要拦截指定用户的ID的
 */
public class ParaUserRoutes extends Routes {
    @Override
    public void config() {
        //登录拦截
        addInterceptor(new LoginInterceptor());

        //添加全局的权限拦截器
        addInterceptor(new PermissionInterceptor());
        //请求参数拦截器
        addInterceptor(new ParaInterceptor());

        add("/p/u/i", ImageAction.class);
        add("/p/u/f", UserInfoAction.class);
        add("/p/u/e", ExperienceAction.class);
        add("/p/u/ec", EducationAction.class);
    }
}
