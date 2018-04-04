package com.pay.user.config;

import com.jfinal.config.Routes;
import com.pay.admin.action.PunishAction;
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
        //登录拦截器
        addInterceptor(new LoginInterceptor());
        //图片访问相关
        add("/u/i", ImageAction.class);
        //用户信息处理相关
        add("/u/f", UserInfoAction.class);
        //用户工作经历
        add("/u/e", ExperienceAction.class);
        //用户教育经历
        add("/u/ec", EducationAction.class);
        //设计主题用户添加
        add("/u/t", ThemeAction.class);
        //用户访问一些管理员添加的功能而用户不需要权限的资源
        add("/u/unp", UserNotPermissionAction.class);

    }
}
