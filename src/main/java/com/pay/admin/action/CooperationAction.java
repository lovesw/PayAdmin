package com.pay.admin.action;

import com.jfinal.aop.Before;
import com.pay.admin.service.CooperationService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.user.model.Cooperation;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 合作商家管理
 */
public class CooperationAction extends BaseController {
    private final CooperationService cooperationService = new CooperationService();


    /**
     * 合作公司列表信息
     */
    @Before(Get.class)
    public void list() {
        success(cooperationService.listService());

    }

    /**
     * 添加合作公司信息
     */
    @Before(Post.class)
    public void add() {
        Cooperation cooperation = getBean(Cooperation.class, "");
        result(cooperationService.addService(cooperation));

    }

    /**
     * 删除合作公司信息
     */
    @Before(Delete.class)
    public void delete() {
        String id = getPara("id");
        result(cooperationService.deleteService(id));
    }


    /**
     * 更新合作公司的信息
     */
    @Before(Put.class)
    public void update() {
        Cooperation cooperation = getBean(Cooperation.class, "");
        result(cooperationService.updateService(cooperation));
    }

}
