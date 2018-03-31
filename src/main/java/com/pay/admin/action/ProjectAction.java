package com.pay.admin.action;

import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.pay.admin.service.ProjectService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.user.model.Project;

/**
 * @createTime: 2018/3/9
 * @author: HingLo
 * @description: 项目经历
 */
public class ProjectAction extends BaseController {
    private final ProjectService projectService = new ProjectService();

    /**
     * 项目经历列表信息
     *
     * @param userId 指定用户的项目经历
     */
    @Before(Get.class)
    public void list(String userId) {
        success(projectService.listService(userId));
    }

    /**
     * 添加项目经历信息
     *
     * @param project 项目经历对象
     */
    @Before(Post.class)
    public void add(@Para("") Project project) {
        result(projectService.addService(project));
    }

    /**
     * 删除项目经历信息
     *
     * @param id 项目经历的唯一ID
     */
    @Before(Delete.class)
    public void delete(String id) {
        result(projectService.deleteService(id));
    }


    /**
     * 更新项目经历的信息
     *
     * @param project 项目经历
     */
    @Before(Put.class)
    public void update(@Para("") Project project) {
        result(projectService.updateService(project));
    }

}
