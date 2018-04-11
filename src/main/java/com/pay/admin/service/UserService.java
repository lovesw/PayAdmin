package com.pay.admin.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pay.user.model.User;
import com.pay.user.model.UserInfo;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/5
 * @author: HingLo
 * @description: 用户管理的服务层
 */
public class UserService {
    /**
     * 查询全部员工信息
     *
     * @return 员工信息列表
     */
    public List<Record> listService() {
        String sql1 = "select id,name,(select name from department as d where d.id=department) as department,(select name from department as d where d.id=position) as position,uf.phone,mark,s_date,z_date from user ,user_info as uf where uf.user_id=user.id";
        String sql = "select id,name,department,position,uf.phone,mark,s_date,z_date from user ,user_info as uf where uf.user_id=user.id";
        return Db.find(sql1);
    }

    /**
     * 创建用户
     *
     * @param user 员工信息
     * @return 操作结果
     */
    public boolean addService(User user) {
        user.setPassword(SecureUtil.md5("123456"));
        user.setDate(new Date());
        //默认可以修改
        user.setStatus(true);

        //初始化用户账号其他信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        return user.save() && userInfo.save();
    }


    /**
     * 删除员工信息
     *
     * @param userId 员工唯一编号
     * @return 操作结果
     */
    public boolean deleteService(String userId) {
        return !StrUtil.isBlank(userId) && User.dao.deleteById(userId);
    }

    /**
     * 重置密码，默认重置为123456
     *
     * @param id 员工编号
     * @return 返回操作结果
     */
    public boolean resetService(String id) {
        String sql = "update user set password=? where id=?";
        return Db.update(sql, SecureUtil.md5("123456"), id) > 0;
    }

    /**
     * 是否启动可以修改信息
     *
     * @param id 员工Id
     * @return 操作结果
     */
    public boolean markService(String id) {
        User user = User.dao.findById(id);
        boolean bool = user.getMark();
        return user.setMark(!bool).update();
    }

    /**
     * @param record 修改的信息column
     * @return 是否修改成功
     */
    public boolean updateService(Record record) {
        if (StrUtil.equals(record.get("status"), "1")) {
            record.set("status", true);
        } else {
            record.set("status", false);
        }
        return Db.update("user", "id", record);
    }
}
