package com.pay.admin.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pay.data.utils.FieldUtils;
import com.pay.user.model.Causation;
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
     * @return 主题信息列表
     */
    public List<Record> adminListService() {
        String sql = "select date, t.id,(SELECT name from user as u WHERE u.id=t.design_id) as dname,service_type,(select name from cooperation as c where c.id=t.cooperation_id) as cname,(SELECT name from user as u where u.id=t.make_id) as mname,name,ename,shelves_date,money_type,status  from theme as t where t.status=?";
        return Db.find(sql, FieldUtils.THEME_STATUS_0);
    }


    /**
     * 通过或者拒绝
     *
     * @param id     主题的唯一ID
     * @param status 状态
     * @param msg    错误消息提示
     * @return 操作结果
     */
    public boolean passService(Long id, int status, String msg) {
        if (status == FieldUtils.THEME_STATUS_1 || status == FieldUtils.THEME_STATUS_2) {
            String sql = "update theme set status=?  where id=? ";
            //如果未通过就添加未通过的原因
            if (status == FieldUtils.THEME_STATUS_2) {
                Causation causation = new Causation();
                causation.setContent(msg);
                causation.setDate(new Date());
                causation.setTid(id);
                causation.save();
            }
            return Db.update(sql, status, id) > 0;
        } else {
            return false;
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
