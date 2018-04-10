package com.pay.user.action;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.PermissionInterceptor;
import com.pay.user.service.UserPermissionService;

import java.util.Date;

/**
 * @createTime: 2018/4/9
 * @author: HingLo
 * @description: 用户访问管理员功能的需要权限的
 */
@Before(PermissionInterceptor.class)
public class UserPermissionAction extends BaseController {
    private final static UserPermissionService userPermissionService = new UserPermissionService();

    /**
     * 用户查看指定月的主题分成比例
     *
     * @param date 指定的年月
     */
    public void proList(String date) {
        if (StrUtil.isBlank(date)) {
            error("查询参数不正确");
        } else {
            Date date1 = DateUtil.parseDate(date + "-01");
            success(userPermissionService.proListService(date1));
        }
    }

    /**
     * 获取公司列表
     */
    public void companyList() {
        success(userPermissionService.companyListService());
    }

    /**
     * 查看商家列表
     */
    @Before(Get.class)
    public void cooperationList() {
        success(userPermissionService.cooperationListService());
    }



}
