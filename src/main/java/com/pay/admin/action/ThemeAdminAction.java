package com.pay.admin.action;

import com.pay.admin.service.ThemeAdminService;
import com.pay.data.controller.BaseController;
import com.pay.user.model.Theme;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 主题管理
 */
public class ThemeAdminAction extends BaseController {
    private final ThemeAdminService themeAdminService = new ThemeAdminService();

    /**
     * 管理员查看主题信息列表
     */
    public void adminList() {
        Date date = getParaToDate("date");
        List<Theme> list = themeAdminService.adminListService(date);
        success(list);
    }


    /**
     * 主题信息申请通过/或者拒绝
     */
    public void passTheme() {
        String id = getPara("id");
        boolean status = getParaToBoolean("status");
        result(themeAdminService.passThemeService(id, status));
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
