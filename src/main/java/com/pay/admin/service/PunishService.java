package com.pay.admin.service;

import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.Db;
import com.pay.data.utils.FieldUtils;
import com.pay.data.utils.IdUtils;
import com.pay.user.model.Punish;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 奖罚记录
 */
public class PunishService {
    /**
     * 奖罚记录信息列表
     *
     * @param userId 用户的唯一Id
     * @return 奖罚记录信息列表
     */
    public List<Punish> listService(String userId) {
        //将用户执行人的名称命名为execute
        String sql = "select p.id,user_id,content,u.name as execute ,p.status,p.date,p.edate from punish as p,user as u where user_id=? and u.id=p.execute";
        return Punish.dao.find(sql, userId);
    }

    /**
     * 添加奖罚记录信息对象
     *
     * @param punish 奖罚记录信息信息对象
     * @return 添加的操作结果
     */
    public boolean addService(Punish punish) {
        punish.setId(IdUtils.getId());
        punish.setStatus(FieldUtils.PUNISH_STATUS_0);
        punish.setDate(new Date());
        return punish.save();
    }

    /**
     * 通过奖罚记录信息的唯一Id与执行人的Id删除奖罚记录<br>
     * 注意：执行人的Id必须是填写人员
     *
     * @param id     指定的奖罚记录信息Id
     * @param userId 执行人的Id
     * @return 返回值
     */
    public boolean deleteService(String id, String userId) {
        String sql = "delete from punish where id=? and execute=?";
        return Db.delete(sql, id, userId) > 0;
    }

    /***
     * 修改奖罚记录信息信息
     * @param punish 奖罚记录信息实体对象
     * @return 返回操作结果
     */
    public boolean updateService(Punish punish) {
        //获取当前奖罚记录的执行人
        String executeId = Punish.dao.findById(punish.getId()).getExecute();
        //判断添加该条奖惩记录是否与修改该条奖惩记录为同一人
        if (StrUtil.equals(executeId, punish.getExecute())) {
            //一旦修改了奖罚信息，就需要重新确认
            punish.setStatus(FieldUtils.PUNISH_STATUS_0);
            //修改时间
            punish.setDate(new Date());
            return punish.update();
        }
        return false;

    }

}
