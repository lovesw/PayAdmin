package com.pay.admin.action;

import com.jfinal.aop.Before;
import com.pay.admin.service.EquipmentService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
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
     */
    @Before(Get.class)
    public void list() {
        String userId = getPara("userId");
        success(equipmentService.listService(userId));
    }

    /**
     * 添加设备信息
     */
    @Before(Post.class)
    public void add() {
        Equipment equipment = getBean(Equipment.class, "");
        result(equipmentService.addService(equipment));
    }

    /**
     * 修改设备状态信息
     */
    @Before(Put.class)
    public void update() {
        String id = getPara("id");
        String userId = getPara("userId");
        int status = getParaToInt("status");
        result(equipmentService.updateService(userId, id, status));
    }

    /**
     * 删除设备信息
     */
    @Before(Delete.class)
    public void delete() {
        String id = getPara("id");
        result(equipmentService.deleteService(id));
    }


}
