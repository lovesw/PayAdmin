package com.pay.user.action;

import com.jfinal.aop.Before;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Put;
import com.pay.user.service.UserNotPermissionService;

/**
 * @createTime: 2018/3/30
 * @author: HingLo
 * @description: 用户访问一些不需要的权限的信息的方法
 */
public class UserNotPermissionAction extends BaseController {
    private final static UserNotPermissionService eqUpdateService = new UserNotPermissionService();

    /***
     * 用户查看自己的合同列表<br>
     * */
    @Before(Get.class)
    public void conList() {
        String userId = getUserId();
        success(eqUpdateService.conListService(userId));
    }

    /**
     * 用户预览自己的合同PDF
     *
     * @param id 预览合同的PDF
     */
    @Before(Get.class)
    public void conLook(String id) {
        String userId = getUserId();
        renderFile(eqUpdateService.conLookService(id, userId));
    }


    /**
     * 用户查看自己的设备列表
     */
    @Before(Get.class)
    public void eqList() {
        String userId = getUserId();
        success(eqUpdateService.eqListService(userId));
    }

    /***
     * 修改设备状态信息
     * @param id 设备的唯一Id
     * @param status 修改的设备状态
     */
    @Before(Put.class)
    public void eqUpdate(String id, int status) {
        String userId = getUserId();
        result(eqUpdateService.eqUpdateService(userId, id, status));
    }

    /**
     * 登录用户查看自己的惩罚记录
     */
    @Before(Get.class)
    public void pnList() {
        String userId = getUserId();
        success(eqUpdateService.pnListService(userId));
    }


    /**
     * 被奖罚人对奖罚的同意
     *
     * @param status true:表示同意,false表示有异议
     * @param id     奖罚的Id
     */
    @Before(Put.class)
    public void pnUpdateAgree(Boolean status, String id) {
        String userId = getUserId();
        result(eqUpdateService.pnUpdateAgreeService(id, status, userId));
    }

    /**
     * 用户查看自己的项目经历
     */
    public void prList() {
        String userId = getUserId();
        success(eqUpdateService.prListService(userId));
    }


}
