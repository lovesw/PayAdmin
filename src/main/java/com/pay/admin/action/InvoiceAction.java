package com.pay.admin.action;

import com.jfinal.aop.Before;
import com.pay.admin.service.InvoiceService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.user.model.Invoice;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 发票的发票管理
 */
public class InvoiceAction extends BaseController {
    private final InvoiceService invoiceService = new InvoiceService();

    /**
     * 发票列表信息
     */
    @Before(Get.class)
    public void list() {
        success(invoiceService.listService());

    }

    /**
     * 添加发票信息
     */
    @Before(Post.class)
    public void add() {
        Invoice invoice = getBean(Invoice.class, "");
        result(invoiceService.addService(invoice));

    }

    /**
     * 删除发票信息
     */
    @Before(Delete.class)
    public void delete() {
        String id = getPara("id");
        result(invoiceService.deleteService(id));
    }


    /**
     * 更新发票的信息
     */
    @Before(Put.class)
    public void update() {
        Invoice invoice = getBean(Invoice.class, "");
        result(invoiceService.updateService(invoice));
    }

}
