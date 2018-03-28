package com.pay.user.service;

import com.pay.data.utils.IdUtils;
import com.pay.user.model.Experience;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/9
 * @author: HingLo
 * @description: 工作经历信息
 */
public class ExperienceService {


    /**
     * 工作经历信息列表
     *
     * @param userId 用户的唯一Id
     * @return 工作经历信息列表
     */
    public List<Experience> listService(String userId) {
        String sql = "select * from experience where user_id=?";
        return Experience.dao.find(sql, userId);
    }

    /**
     * 添加工作经历信息对象
     *
     * @param experience 工作经历信息信息对象
     * @return 添加的操作结果
     */
    public boolean addService(Experience experience) {
        experience.setId(IdUtils.getId());
        experience.setDate(new Date());
        return experience.save();
    }

    /**
     * 通过工作经历信息的唯一Id删除工作经历信息全部信息
     *
     * @param id 指定的工作经历信息Id
     * @return 返回值
     */
    public boolean deleteService(String id) {
        return Experience.dao.deleteById(id);
    }

    /***
     * 修改工作经历信息信息
     * @param experience 工作经历信息实体对象
     * @return 返回操作结果
     */
    public boolean updateService(Experience experience) {
        return experience.update();
    }

}
