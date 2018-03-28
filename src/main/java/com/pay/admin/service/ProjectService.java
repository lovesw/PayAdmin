package com.pay.admin.service;

import com.pay.data.utils.IdUtils;
import com.pay.user.model.Project;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/9
 * @author: HingLo
 * @description: 项目经历的服务层
 */
public class ProjectService {


    /**
     * 项目经历信息列表
     *
     * @param userId 用户的唯一Id
     * @return 项目经历信息列表
     */
    public List<Project> listService(String userId) {
        String sql = "select * from project where user_id=?";
        return Project.dao.find(sql, userId);
    }

    /**
     * 添加项目经历信息对象
     *
     * @param project 项目经历信息信息对象
     * @return 添加的操作结果
     */
    public boolean addService(Project project) {
        project.setId(IdUtils.getId());
        project.setDate(new Date());
        return project.save();
    }

    /**
     * 通过项目经历信息的唯一Id删除项目经历信息全部信息
     *
     * @param id 指定的项目经历信息Id
     * @return 返回值
     */
    public boolean deleteService(String id) {
        return Project.dao.deleteById(id);
    }

    /***
     * 修改项目经历信息信息
     * @param project 项目经历信息实体对象
     * @return 返回操作结果
     */
    public boolean updateService(Project project) {
        return project.update();
    }

}
