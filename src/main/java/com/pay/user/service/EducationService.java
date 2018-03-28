package com.pay.user.service;

import com.pay.data.utils.IdUtils;
import com.pay.user.model.Education;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 教育经历
 */
public class EducationService {


    /**
     * 教育信息列表
     *
     * @param userId 用户的唯一Id
     * @return 教育信息列表
     */
    public List<Education> listService(String userId) {
        String sql = "select * from education where user_id=?";
        return Education.dao.find(sql, userId);
    }

    /**
     * 添加教育信息对象
     *
     * @param education 教育信息信息对象
     * @return 添加的操作结果
     */
    public boolean addService(Education education) {
        education.setId(IdUtils.getId());
        education.setDate(new Date());
        return education.save();
    }

    /**
     * 通过教育信息的唯一Id删除教育信息全部信息
     *
     * @param id 指定的教育信息Id
     * @return 返回值
     */
    public boolean deleteService(String id) {
        return Education.dao.deleteById(id);
    }

    /***
     * 修改教育信息信息
     * @param education 教育信息实体对象
     * @return 返回操作结果
     */
    public boolean updateService(Education education) {
        return education.update();
    }
}
