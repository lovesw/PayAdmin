package com.pay.user.action;

import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.paragetter.Para;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.*;
import com.pay.user.model.Punish;
import com.pay.user.service.PunishService;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 惩罚记录
 */
public class PunishAction extends BaseController {

    private final PunishService punishService = new PunishService();

    /**
     * 惩罚记录列表信息
     */
    @Before(Get.class)
    public void list() {
        String userId = getUserId();
        success(punishService.listService(userId));

    }

    /**
     * 添加惩罚记录信息<br>
     * 添加了权限拦截<br>
     * 去掉Id参数拦截
     *
     * @param punish 惩罚记录
     */
    @Clear({ParaInterceptor.class, PermissionInterceptor.class})
    @Before({Post.class, PermissionInterceptor.class})
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
     * 删除惩罚记录信息<br>
     * 添加了权限拦截
     */
    @Clear({ParaInterceptor.class, PermissionInterceptor.class})
    @Before({Delete.class, PermissionInterceptor.class})
    public void delete() {
        //执行人的ID
        String userId = getUserId();
        //被删除记录的Id
        String id = getPara("id");

        result(punishService.deleteService(id, userId));
    }


    /**
     * 更新惩罚记录的信息<br>
     * 添加了权限拦截
     *
     * @param punish 惩罚记录
     */
    @Clear({ParaInterceptor.class, PermissionInterceptor.class})
    @Before({Put.class, PermissionInterceptor.class})
    public void update(@Para("") Punish punish) {
        result(punishService.updateService(punish));
    }

    /**
     * 确认惩罚记录
     */
    @Before(Put.class)
    public void updateArgee() {
        Boolean status = getParaToBoolean("status");
        String userId = getUserId();
        result(punishService.updateAgreeService(userId, status));
    }


}
