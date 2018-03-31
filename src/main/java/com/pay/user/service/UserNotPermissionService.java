package com.pay.user.service;

import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.Db;
import com.pay.data.utils.FileImageUtils;
import com.pay.data.utils.FieldUtils;
import com.pay.user.model.Contract;
import com.pay.user.model.Equipment;
import com.pay.user.model.Project;
import com.pay.user.model.Punish;

import java.io.File;
import java.util.List;

/**
 * @createTime: 2018/3/30
 * @author: HingLo
 * @description: 用户访问一些不需要权限的方法
 */
public class UserNotPermissionService {
    /**
     * 用户查看自己的合同信息
     *
     * @param userId 用户Id
     * @return 返回合同列表
     */
    public List<Contract> conListService(String userId) {
        String sql = "select c.id,title,pdf, (SELECT name from user where id=c.first) as first, (SELECT name from user where id=c.second) as second,c.date,start_date,end_date from contract as c  WHERE second=?";
        return Contract.dao.find(sql, userId);
    }

    /**
     * 合同预览，返回合同文件
     *
     * @param id     合同的唯一编号
     * @param userId 用户Id
     * @return 合同的PDF
     */
    public File conLookService(String id, String userId) {
        Contract contract = Contract.dao.findById(id);
        //必须校验该合同是否属于登录人本人
        if (contract != null && StrUtil.equals(contract.getSecond(), userId)) {
            return FileImageUtils.readPDF(contract.getPdf());
        }
        return null;
    }

    /**
     * 用户的设备列表
     *
     * @param userId 员工编号
     * @return 用户的设备集合
     */
    public List<Equipment> eqListService(String userId) {
        String sql = "select * from  equipment where user_id=?";
        return Equipment.dao.find(sql, userId);
    }

    /***
     * 修改设备领取的状态
     * @param userId 员工编号
     * @param id 设备的唯一编号
     * @param status 修改的状态。（1,领取，2：申请归还，3:申请保修)
     * @return 修改结果
     */
    public boolean eqUpdateService(String userId, String id, int status) {
        if (StrUtil.isBlank(userId) || StrUtil.isBlank(id)) {
            return false;
        }
        String sql = "update equipment set status=? where id=? and user_id=?";
        return (status == 1 || status == 2 || status == 3) && Db.update(sql, status, id, userId) > 0;
    }


    /**
     * 奖罚记录信息列表
     *
     * @param userId 用户的唯一Id
     * @return 奖罚记录信息列表
     */
    public List<Punish> pnListService(String userId) {
        //将用户执行人的名称命名为execute
        String sql = "select p.id,user_id,content,u.name as execute ,p.status,p.date,p.edate from punish as p,user as u where user_id=? and u.id=p.execute";
        return Punish.dao.find(sql, userId);
    }


    /**
     * 奖罚记录的统一或者异议
     *
     * @param id     奖罚记录的唯一Id
     * @param status 同意或者异议
     * @return 返回操作结果
     */
    public boolean pnUpdateAgreeService(String id, Boolean status, String userId) {
        String sql = "update punish set status=? where id = ? and user_id=?";
        Integer res = status ? FieldUtils.PUNISH_STATUS_1 : FieldUtils.PUNISH_STATUS_2;
        return Db.update(sql, res, id, userId) > 0;
    }

    /**
     * 项目经历信息列表
     *
     * @param userId 用户的唯一Id
     * @return 项目经历信息列表
     */
    public List<Project> prListService(String userId) {
        String sql = "select id, content,progress,remark,start_date from project where user_id=?";
        return Project.dao.find(sql, userId);
    }
}
