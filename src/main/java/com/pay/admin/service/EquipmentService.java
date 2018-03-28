package com.pay.admin.service;

import com.jfinal.plugin.activerecord.Db;
import com.pay.user.model.Equipment;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/13
 * @author: HingLo
 * @description: 设备管理的服务层
 */
public class EquipmentService {

    /**
     * 用户的设备列表
     *
     * @param userId 员工编号
     * @return 用户的设备集合
     */
    public List<Equipment> listService(String userId) {
        String sql = "select * from  equipment where user_id=?";
        return Equipment.dao.find(sql, userId);
    }

    /**
     * 添加设备信息
     *
     * @param equipment 设备信息对象
     * @return 添加结果
     */
    public boolean addService(Equipment equipment) {
        equipment.setDate(new Date());
        return equipment.save();
    }

    /***
     * 修改设备领取的状态
     * @param userId 员工编号
     * @param id 设备百年好
     * @param status 修改的状态。（1,确认，2：归还，3:保修)
     * @return 修改结果
     */
    public boolean updateService(String userId, String id, int status) {
        String sql = "update set status=? where id=? and user_id=?";
        return (status == 1 || status == 2 || status == 3) && Db.update(sql, status, id, userId) > 0;

    }

    /**
     * 删除设备信息
     *
     * @param id 用户的唯一ID
     * @return 返回删除结果
     */
    public boolean deleteService(String id) {
        return Equipment.dao.deleteById(id);

    }
}
