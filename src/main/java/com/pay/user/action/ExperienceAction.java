package com.pay.user.action;

import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.user.model.Experience;
import com.pay.user.service.ExperienceService;

import java.util.Date;

/**
 * @createTime: 2018/3/9
 * @author: HingLo
 * @description: 工作经历信息
 */
public class ExperienceAction extends BaseController {
    private final ExperienceService experienceService = new ExperienceService();


    /**
     * 工作经历列表信息
     */
    @Before(Get.class)
    public void list() {
        String id = getUserId();
        success(experienceService.listService(id));

    }

    /**
     * 添加工作经历信息
     *
     * @param experience 工作经历对象
     */
    @Before(Post.class)
    public void add(@Para("") Experience experience) {
        String userId = getUserId();
        experience.setUserId(userId);
        result(experienceService.addService(experience));

    }

    /**
     * 删除工作经历信息
     */
    @Before(Delete.class)
    public void delete() {
        String id = getPara("id");
        result(experienceService.deleteService(id));
    }


    /***
     * 更新工作经历的信息
     * @param experience 工作经历信息
     */
    @Before(Put.class)
    public void update(@Para("") Experience experience) {
        result(experienceService.updateService(experience));
    }

}
