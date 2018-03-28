package com.pay.admin.action;

import com.pay.admin.service.ThemeService;
import com.pay.data.controller.BaseController;
import com.pay.user.model.Theme;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 主题管理
 */
public class ThemeAction extends BaseController {
    private final ThemeService themeSerivce = new ThemeService();

    /**
     * 添加主题信息
     */
    public void add() {
        Theme theme = getBean(Theme.class, "");
        boolean bool = themeSerivce.addService(theme);
        result(bool);
    }

    /**
     * 用户查看主题列表
     */
    public void userList() {
        Date date = getParaToDate("date");
        String userId = getPara("userId");
        List<Theme> list = themeSerivce.userListService(date, userId);
        success(list);
    }

    /**
     * 管理员查看主题信息列表
     */
    public void adminList() {
        Date date = getParaToDate("date");
        List<Theme> list = themeSerivce.adminListService(date);
        success(list);
    }

    /**
     * 上传者修改用户信息
     */
    public void update() {
        Theme theme = getBean(Theme.class, "");
        boolean bool = themeSerivce.updateService(theme);
        result(bool);
    }

    /**
     * 主题信息申请通过/或者拒绝
     */
    public void passTheme() {
        String id = getPara("id");
        boolean status = getParaToBoolean("status");
        result(themeSerivce.passThemeService(id, status));
    }

    /**
     * 设置主题的分配营业额
     */
    public void turnover() {
        String t = getPara("money");
        Double money = Double.valueOf(t);
        String id = getPara("id");
        result(themeSerivce.turnoverService(id, money));
    }


}
