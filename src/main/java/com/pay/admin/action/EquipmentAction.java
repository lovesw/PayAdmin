package com.pay.admin.action;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.paragetter.Para;
import com.pay.data.interceptors.*;
import com.pay.admin.service.EquipmentService;
import com.pay.data.controller.BaseController;
import com.pay.user.model.Equipment;

/**
 * @createTime: 2018/3/13
 * @author: HingLo
 * @description: 设备管理
 */
public class EquipmentAction extends BaseController {

    private final EquipmentService equipmentService = new EquipmentService();

    /**
     * 设备列表信息
     *
     * @param userId 查看人的指定编号
     */
    @Before(Get.class)
    public void list(String userId) {
        success(equipmentService.listService(userId));
    }

    /**
     * 查看系统中的所有设备信息
     */
    @Before(Get.class)
    public void listAll() {
        success(equipmentService.listAllService());
    }

    /**
     * 添加设备信息<br>
     * 设备添加需要权限管理
     *
     * @param equipment 设备信息
     */
    @Before(Post.class)
    public void add(@Para("") Equipment equipment) {
        result(equipmentService.addService(equipment));
    }

    /***
     * 管理员处理申请操作
     * @param id 设备到的唯一Id
     * @param status 状态
     */
    @Before(Put.class)
    public void apply(String id, int status) {
        result(equipmentService.applyService(id, status));
    }


    /**
     * 删除设备信息
     *
     * @param id 设备的唯一id
     */
    @Before(Delete.class)
    public void delete(String id) {
        result(equipmentService.deleteService(id));
    }

}
