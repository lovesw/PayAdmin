package com.pay.admin.config;

import com.jfinal.config.Routes;
import com.pay.admin.action.*;
import com.pay.data.interceptors.LoginInterceptor;
import com.pay.data.interceptors.PermissionInterceptor;

/**
 * @createTime: 2018/2/28
 * @author: HingLo
 * @description: 系统管理员的路由控制
 */
public class AdminRoutes extends Routes {
    @Override
    public void config() {
        //登录拦截
        addInterceptor(new LoginInterceptor());
        //添加全局的权限拦截器
        addInterceptor(new PermissionInterceptor());
        //管理员级别查看用户信息
        add("/admin/u", UserAction.class);


        //添加权限管理的URl
        add("/admin/t", ThemeAction.class);
        add("/admin/c", CompanyAction.class);
        add("/admin/ca", CooperationAction.class);
        add("/admin/ct", ContractAction.class);
        add("/admin/pr", ProjectAction.class);
        add("/admin/i", InvoiceAction.class);
        add("/admin/ea", EquipmentAction.class);


    }
}
