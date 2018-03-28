package com.pay.admin.service;

import com.pay.data.utils.IdUtils;
import com.pay.user.model.Cooperation;

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
     * @return 添加的操作结果
     */
    public boolean addService(Cooperation cooperation) {
        cooperation.setId(IdUtils.getId());
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


}
