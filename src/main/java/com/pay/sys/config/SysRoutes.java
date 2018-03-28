package com.pay.sys.config;

import com.jfinal.config.Routes;
import com.pay.data.interceptors.LoginInterceptor;
import com.pay.data.interceptors.PermissionInterceptor;
import com.pay.sys.action.PermissionAction;

/**
 * @createTime: 2018/2/28
 * @author: HingLo
 * @description: 系统管理员的路由控制
 */
public class SysRoutes extends Routes {
    @Override
    public void config() {
        //登录拦截
        addInterceptor(new LoginInterceptor());
//        添加全局的权限拦截器
        addInterceptor(new PermissionInterceptor());
        //添加权限管理的配置
        add("/sys/p", PermissionAction.class);


    }
}
