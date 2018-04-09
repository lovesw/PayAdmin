package com.pay.user.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

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
}
