package com.pay.user.action;

import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.user.model.Education;
import com.pay.user.service.EducationService;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 教育经历
 */
public class EducationAction extends BaseController {
    private final EducationService educationService = new EducationService();


    /**
     * 教育信息列表
     */
    @Before(Get.class)
    public void list() {
        String userId = getUserId();
        success(educationService.listService(userId));

    }

    /**
     * 添加教育信息
     *
     * @param education 教育经历
     */
    @Before(Post.class)
    public void add(@Para("") Education education) {
        String userId = getUserId();
        education.setUserId(userId);
        result(educationService.addService(education));

    }

    /**
     * 删除教育信息
     */
    @Before(Delete.class)
    public void delete() {
        String id = getPara("id");
        result(educationService.deleteService(id));
    }

    /**
     * 更新教育信息
     *
     * @param education 教育经历
     */
    @Before(Put.class)
    public void update(@Para("") Education education) {
        result(educationService.updateService(education));
    }
}
