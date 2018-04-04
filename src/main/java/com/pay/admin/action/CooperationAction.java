package com.pay.admin.action;

import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
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
     *
     * @param type        分成方式
     * @param cooperation 合作方方的实体
     */
    @Before(Post.class)
    public void add(@Para("") Cooperation cooperation, int type) {
        result(cooperationService.addService(cooperation, type));

    }

    /**
     * 删除合作公司信息
     *
     * @param id 合作公司的唯一id
     */
    @Before(Delete.class)
    public void delete(String id) {
        result(cooperationService.deleteService(id));
    }


    /**
     * 更新合作公司的信息
     *
     * @param cooperation 合作方的实体信息
     */
    @Before(Put.class)
    public void update(@Para("") Cooperation cooperation) {
        result(cooperationService.updateService(cooperation));
    }

    /**
     * 修改分成方式
     *
     * @param id       合作公司的Id
     * @param separate 分成类型
     */
    @Before(Put.class)
    public void change(int id, int separate) {
        result(cooperationService.changeService(id, separate));
    }


    /**
     * 修改合作公司的状态
     *
     * @param id     合作公司的唯一标识符
     * @param status 需要修改的状态(true:启用，false：禁用）
     */
    @Before(Put.class)
    public void status(int id, boolean status) {
        boolean bool = cooperationService.statusService(id, status);
        result(bool, "公司状态修改失败");
    }

}
