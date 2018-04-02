package com.pay.admin.action;

import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.pay.admin.service.CompanyService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.user.model.Company;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 公司信息管理
 */
public class CompanyAction extends BaseController {

    private final CompanyService companyService = new CompanyService();

    /**
     * 公司列表信息
     */
    @Before(Get.class)
    public void list() {
        success(companyService.listService());

    }

    /**
     * 添加公司信息
     *
     * @param company 公司实体信息
     */
    @Before(Post.class)
    public void add(@Para("") Company company) {
        result(companyService.addService(company));
    }

    /**
     * 删除公司信息
     *
     * @param id 公司的唯一id
     */
    @Before(Delete.class)
    public void delete(String id) {
        result(companyService.deleteService(id));
    }

    /**
     * 更新公司信息
     *
     * @param company 公司实体信息
     */
    @Before(Put.class)
    public void update(@Para("") Company company) {
        result(companyService.updateService(company));
    }

    /**
     * 修改公司的状态
     *
     * @param id     公司的唯一标识符
     * @param status 需要修改的状态(true:启用，false：禁用）
     */
    @Before(Put.class)
    public void status(int id, boolean status) {
        boolean bool = companyService.statusService(id, status);
        result(bool, "公司状态修改失败");
    }

}
