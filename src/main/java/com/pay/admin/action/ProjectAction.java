package com.pay.admin.action;

import com.jfinal.aop.Before;
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
     */
    @Before(Get.class)
    public void list() {
        String id = getPara("userId");
        success(projectService.listService(id));
    }

    /**
     * 添加项目经历信息
     */
    @Before(Post.class)
    public void add() {
        Project project = getBean(Project.class, "");
        result(projectService.addService(project));
    }

    /**
     * 删除项目经历信息
     */
    @Before(Delete.class)
    public void delete() {
        String id = getPara("id");
        result(projectService.deleteService(id));
    }


    /**
     * 更新项目经历的信息
     */
    @Before(Put.class)
    public void update() {
        Project project = getBean(Project.class, "");
        result(projectService.updateService(project));
    }

}
