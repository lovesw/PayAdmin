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
        //主题管理
        add("/admin/t", ThemeAdminAction.class);
        //公司管理
        add("/admin/c", CompanyAction.class);
        //合作方管理
        add("/admin/ca", CooperationAction.class);
        //合同管理
        add("/admin/ct", ContractAction.class);
        //项目经历
        add("/admin/pr", ProjectAction.class);
        //发票管理
        add("/admin/i", InvoiceAction.class);
        //设备管理
        add("/admin/eq", EquipmentAction.class);
        //惩罚记录
        add("/admin/p", PunishAction.class);
        //档案管理
        add("/admin/a", ArchivesAction.class);
        //比例分成管理
        add("/admin/pro", ProportionAction.class);
        //主题月盈利
        add("/admin/tu", TurnoverAction.class);

    }
}
