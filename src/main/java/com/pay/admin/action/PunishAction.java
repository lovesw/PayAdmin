package com.pay.admin.action;

import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.*;
import com.pay.user.model.Punish;
import com.pay.admin.service.PunishService;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 奖罚记录
 */
public class PunishAction extends BaseController {

    private final PunishService punishService = new PunishService();

    /**
     * 奖罚记录列表信息
     *
     * @param userId 指定员工的唯一编号
     */
    @Before(Get.class)
    public void list(String userId) {
        success(punishService.listService(userId));

    }

    /**
     * 添加奖罚记录信息<br>
     * 添加了权限拦截<br>
     * 去掉Id参数拦截
     *
     * @param punish 奖罚记录
     */
    @Before(Post.class)
    public void add(@Para("") Punish punish) {
        String userId = getPara("userId");
        //设置执行人
        punish.setExecute(getUserId());
        //设置被处罚的人
        punish.setUserId(userId);
        //设置为默认状态即等待确认的状态
        punish.setStatus(0);

        result(punishService.addService(punish), "添加失败");

    }

    /**
     * 删除奖罚记录信息<br>
     * 添加了权限拦截
     */
    @Before(Delete.class)
    public void delete() {
        //执行人的ID
        String userId = getUserId();
        //被删除记录的Id
        String id = getPara("id");

        result(punishService.deleteService(id, userId));
    }


    /**
     * 更新奖罚记录的信息<br>
     * 添加了权限拦截
     *
     * @param punish 奖罚记录
     */
    @Before(Put.class)
    public void update(@Para("") Punish punish) {
        punish.setExecute(getUserId());
        result(punishService.updateService(punish));
    }


}
