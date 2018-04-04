package com.pay.admin.service;

import com.jfinal.plugin.activerecord.Db;
import com.pay.data.utils.FieldUtils;
import com.pay.user.model.Theme;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 主题管理
 */
public class ThemeAdminService {


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
     * 通过或者拒绝
     *
     * @param id     主题的唯一ID
     * @param status 状态
     * @return 操作结果
     */
    public boolean passThemeService(String id, boolean status) {
        if (status) {
            String sql = "update theme set status=? and set user_status=false where id=? and  user_status=true";
            return Db.update(sql, FieldUtils.THEME_STATUS_1, id) > 0;
        } else {
            String sql = "update theme set status=? and set user_status=false where id=? and user_status=true";
            return Db.update(sql, FieldUtils.THEME_STATUS_2, id) > 0;
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
