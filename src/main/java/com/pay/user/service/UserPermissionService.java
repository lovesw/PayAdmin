package com.pay.user.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pay.data.utils.FieldUtils;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/4/9
 * @author: HingLo
 * @description: 用户访问管理添加的功能服务层
 */
public class UserPermissionService {


    /**
     * @param date 指定日期的月份
     * @return 返回指定月份的比例分成信息
     */
    public List<Record> proListService(Date date) {
        String sql = "select name,p.id,month,make,design from proportion as p,cooperation as co  where co.id=p.cooperation_id and month=?";
        return Db.find(sql, date);
    }

    /**
     * 获取公司列表
     */
    public List<Record> companyListService() {
        String sql = "select id,name from company where  status=true";
        return Db.find(sql);
    }

    /**
     * 查询按照主题分成的合作商家
     *
     * @return 商家信息
     */
    public List<Record> cooperationListService() {
        String sql = "select id,name,fillname,separate from cooperation where status=true";
        return Db.find(sql);
    }

    /***
     * 获取用户的信息
     * @return 设计师列表
     */
    public List<Record> designListService() {
        String sql = "select id,name from user where id in (select user_id from user_role where role_id=?) and mark=true";
        return Db.find(sql, FieldUtils.DESIGN);
    }

}
