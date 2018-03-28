package com.pay.user.service;

import com.jfinal.plugin.activerecord.Db;
import com.pay.data.utils.FiledUtils;
import com.pay.data.utils.IdUtils;
import com.pay.user.model.Punish;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 惩罚记录
 */
public class PunishService {
    /**
     * 惩罚记录信息列表
     *
     * @param userId 用户的唯一Id
     * @return 惩罚记录信息列表
     */
    public List<Punish> listService(String userId) {
        String sql = "select * from punish where user_id=?";
        return Punish.dao.find(sql, userId);
    }

    /**
     * 添加惩罚记录信息对象
     *
     * @param punish 惩罚记录信息信息对象
     * @return 添加的操作结果
     */
    public boolean addService(Punish punish) {
        punish.setId(IdUtils.getId());
        punish.setStatus(FiledUtils.PUNISH_STATUS_0);
        punish.setDate(new Date());
        return punish.save();
    }

    /**
     * 通过惩罚记录信息的唯一Id与执行人的Id删除惩罚记录<br>
     * 注意：执行人的Id必须是填写人员
     *
     * @param id     指定的惩罚记录信息Id
     * @param userId 执行人的Id
     * @return 返回值
     */
    public boolean deleteService(String id, String userId) {
        String sql = "delete from punish where id=? and execute=?";
        return Db.delete(sql, id, userId) > 0;
    }

    /***
     * 修改惩罚记录信息信息
     * @param punish 惩罚记录信息实体对象
     * @return 返回操作结果
     */
    public boolean updateService(Punish punish) {
        return punish.update();
    }

    /**
     * 惩罚记录的统一或者异议
     *
     * @param id     惩罚记录的唯一Id
     * @param status 同意或者异议
     * @return 返回操作结果
     */
    public boolean updateAgreeService(String id, Boolean status) {
        if (status) {
            String sql = "update punish set status=? where id = ?";
            return Db.update(sql, FiledUtils.PUNISH_STATUS_1, id) > 0;
        } else {
            String sql = "update punish set status=? where id = ?";
            return Db.update(sql, FiledUtils.PUNISH_STATUS_2, id) > 0;
        }
    }
}
