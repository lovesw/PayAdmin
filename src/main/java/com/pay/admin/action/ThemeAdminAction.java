package com.pay.admin.action;

import com.jfinal.aop.Before;
import com.pay.admin.service.ThemeAdminService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Put;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 主题管理
 */
public class ThemeAdminAction extends BaseController {
    private final ThemeAdminService themeAdminService = new ThemeAdminService();

    /**
     * 管理员查待审核的主题信息列表
     */
    @Before(Get.class)
    public void list() {
        success(themeAdminService.adminListService());
    }


    /**
     * 主题信息申请通过/或者拒绝
     *
     * @param id     主题
     * @param status 审核状态（1：通过，2：为通过）
     * @param msg    未通过的原因
     */
    @Before(Put.class)
    public void pass(Long id, int status, String msg) {
        result(themeAdminService.passService(id, status, msg), "审核处理失败");
    }

    /**
     * 设置主题的分配营业额
     */
    public void turnover() {
        String t = getPara("money");
        Double money = Double.valueOf(t);
        String id = getPara("id");
        result(themeAdminService.turnoverService(id, money));
    }


}
