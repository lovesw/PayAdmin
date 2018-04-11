package com.pay.admin.action;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Record;
import com.pay.admin.service.DepartmentService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.data.validator.DepartmentValidator;
import com.pay.user.model.Department;

/**
 * @createTime: 2018/4/11
 * @author: HingLo
 * @description: 部门职位管理
 */
public class DepartmentAction extends BaseController {
    private static final DepartmentService departmentService = new DepartmentService();

    /**
     * 查看部门职位信息
     */
    @Before(Get.class)
    public void list() {
        success(departmentService.listService());
    }

    /**
     * 添加部门或者职位信息
     *
     * @param department 部门职位实体信息
     */
    @Before({Post.class, DepartmentValidator.class})
    public void add(@Para("") Department department) {
        result(departmentService.addService(department), "添加失败");
    }

    /**
     * 修改部门或者职位名称
     *
     * @param id   id
     * @param name 新名称
     */
    @Before(Put.class)
    public void update(Long id, String name) {
        if (id == null || StrUtil.isBlank(name)) {
            error("修改的Id不正确");
        } else {
            Record record = new Record();
            record.set("id", id);
            record.set("name", name);
            result(departmentService.updateService(record), "修改失败");
        }

    }

    /**
     * 通过Id来删除部门信息
     *
     * @param id id
     */
    @Before(Delete.class)
    public void delete(Long id) {
        if (id == null) {
            error("请求参数不正确");
        } else {
            result(departmentService.deleteService(id), "删除失败");
        }
    }

}
