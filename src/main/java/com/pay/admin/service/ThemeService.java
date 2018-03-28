package com.pay.admin.service;

import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.Db;
import com.pay.data.utils.FiledUtils;
import com.pay.data.utils.IdUtils;
import com.pay.user.model.Theme;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 主题管理
 */
public class ThemeService {
    /**
     * 添加主题信息
     *
     * @param theme 主题对象
     * @return 添加结果
     */
    public boolean addService(Theme theme) {
        theme.setId(IdUtils.getId());
        theme.setDate(new Date());
        //主题审核是否通过
        theme.setStatus(FiledUtils.THEME_STATUS_0);
        //主题信息是否可以修改
        theme.setUserStatus(true);
        return theme.save();
    }

    /**
     * 查看指定年月日的主题信息
     *
     * @param date 指定时间
     * @return 主题信息列表
     */
    public List<Theme> userListService(Date date, String userId) {
        String sql = "select * from theme where userId=?";
        if (date != null) {
            sql += " and date_format(date,'%y-%m') = date_format(?,'%y-%m')";
        }
        List<Theme> list = Theme.dao.find(sql, userId, date);
        list.forEach(x -> x.setTurnover(0.0));
        return list;
    }

    /**
     * 查看指定年月日的主题信息
     *
     * @param date 指定时间
     * @return 主题信息列表
     */
    public List<Theme> adminListService(Date date) {
        String sql = "select * from theme where ";
        if (date != null) {
            sql += " date_format(date,'%y-%m') = date_format(?,'%y-%m')";
        }
        return Theme.dao.find(sql, date);
    }

    /**
     * 更新用户信息
     *
     * @param theme 需要更新的主题信息
     * @return 操作结果
     */
    public boolean updateService(Theme theme) {
        String id = theme.getId();
        if (StrUtil.isBlank(id)) {
            return false;
        }
        Theme theme1 = Theme.dao.findById(id);
        //当前Id查询的主题信息是否为null
        if (theme1 == null) {
            return false;
        }
        //如果该主题状态，通过或者主题不能编辑，则返回false
        if (!theme1.getStatus().equals(FiledUtils.THEME_STATUS_1) || !theme1.getUserStatus()) {
            return false;
        }
        //设置主题状态为待审核
        theme.setStatus(FiledUtils.THEME_STATUS_0);
        theme.setUserStatus(true);
        return theme.update();
    }

    /**
     * 通过或者拒绝
     *
     * @param id     主题的唯一ID
     * @param status 状态
     * @return 操作结果
     */
    public boolean passThemeService(String id, boolean status) {
        if (status) {
            String sql = "update theme set status=? and set user_status=false where id=? and  user_status=true";
            return Db.update(sql, FiledUtils.THEME_STATUS_1, id) > 0;
        } else {
            String sql = "update theme set status=? and set user_status=false where id=? and user_status=true";
            return Db.update(sql, FiledUtils.THEME_STATUS_2, id) > 0;
        }

    }

    /**
     * 设置主题的营业额
     *
     * @param id    主题的唯一编号
     * @param money 设置主题的营业额
     * @return 操作结果
     */
    public boolean turnoverService(String id, Double money) {
        String sql = "update theme set turnover=? where id=?";
        return Db.update(sql, money, id) > 0;
    }
}
