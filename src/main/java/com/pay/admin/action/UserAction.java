package com.pay.admin.action;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Record;
import com.pay.admin.service.UserService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.data.validator.UserValidator;
import com.pay.user.model.User;

/**
 * @createTime: 2018/3/5
 * @author: HingLo
 * @description: 用户管理
 */
public class UserAction extends BaseController {

    private final UserService userService = Duang.duang(UserService.class);

    /**
     * 全部的用户信息
     */
    @Before(Get.class)
    public void list() {
        success(userService.listService());
    }

    /***
     * 添加员工信息
     * @param user 员工信息对象
     */
    @Before({Post.class, UserValidator.class})
    public void add(@Para("") User user) {
        boolean bool = userService.addService(user);
        result(bool, "添加失败");

    }

    /***
     * 删除员工编信息
     * @param id 员工编号
     */
    @Before(Delete.class)
    public void delete(String id) {
        boolean bool = userService.deleteService(id);
        result(bool, "删除失败");
    }

    /**
     * 重置密码
     *
     * @param userId 员工编号
     */
    @Before(Put.class)
    public void reset(String userId) {
        result(userService.resetService(userId));
    }

    /**
     * 员工信息开启状态
     *
     * @param id 员工编号
     */
    public void mark(String id) {
        String userId = getUserId();
        if (StrUtil.equals(userId, id)) {
            error("不能禁用自己！");
        } else {
            result(userService.markService(id));
        }

    }

    /**
     * 修改员工的部门，职位等信息
     */

    @Before(Put.class)
    public void update() {
        Record record = getRecordPara();
        result(userService.updateService(record), "修改失败");

    }
//*************************添加用户的所属部门信息与职位信息需要的接口************************************//

    /***
     * 获取部门信息
     */
    @Before(Get.class)
    public void dList() {
        success(userService.dListService());
    }

    /***
     * 获取部门信息
     */
    @Before(Get.class)
    public void pList(Long id) {
        if (id == null) {
            error("部门信息Id不正确");
        } else {
            success(userService.pListService(id));
        }
    }

}
