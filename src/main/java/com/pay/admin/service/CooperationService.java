package com.pay.admin.service;

import com.jfinal.plugin.activerecord.Db;
import com.pay.data.utils.IdUtils;
import com.pay.user.model.Cooperation;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 合作商家管理
 */
public class CooperationService {


    /**
     * 合作公司列表
     *
     * @return 合作公司列表
     */
    public List<Cooperation> listService() {
        String sql = "select * from cooperation ";
        return Cooperation.dao.find(sql);
    }

    /**
     * 添加合作公司对象
     *
     * @param cooperation 合作公司信息对象
     * @param type        分成类型
     * @return 添加的操作结果
     */
    public boolean addService(Cooperation cooperation, int type) {
        //设置为按照主题分成
        if (type == 1) {
            cooperation.setSeparate(true);
        }

        cooperation.setDate(new Date());
        //设置默认为未启用状态
        cooperation.setStatus(false);
        return cooperation.save();
    }

    /**
     * 通过合作公司的唯一Id删除合作公司全部信息
     *
     * @param id 指定的公司Id
     * @return 返回值
     */
    public boolean deleteService(String id) {
        return Cooperation.dao.deleteById(id);
    }

    /***
     * 修改合作公司信息
     * @param cooperation 合作公司实体对象
     * @return 返回操作结果
     */
    public boolean updateService(Cooperation cooperation) {
        return cooperation.update();
    }

    /**
     * 修改合作公司状态
     *
     * @param id     合作公司的唯一标识符
     * @param status 合作公司状态
     * @return 是否修改成功
     */
    public boolean statusService(int id, boolean status) {
        String sql = "update cooperation set status=? where id=? and status=?";
        return Db.update(sql, status, id, !status) > 0;
    }

    /***
     * 修改分成方式
     * @param id id
     * @param separate 分成类型1：主题，其他是：比例
     * @return 是否修改成功
     */
    public boolean changeService(int id, int separate) {
        String sql = "update cooperation set separate=? where id=?";
        return Db.update(sql, separate == 1, id) > 0;

    }
}
