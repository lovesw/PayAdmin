package com.pay.admin.action;

import cn.hutool.core.codec.Base64;
import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.jfinal.upload.UploadFile;
import com.pay.admin.service.InvoiceService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.data.utils.FieldUtils;
import com.pay.data.utils.FileImageUtils;
import com.pay.user.model.Invoice;

import java.text.ParseException;

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
     *
     * @param invoice 发票的对象
     * @param image   发票的图片
     */
    @Before(Post.class)
    public void add(UploadFile image, @Para("") Invoice invoice) {
        if (image != null && FileImageUtils.iconUtils(image.getOriginalFileName())) {
            result(invoiceService.addService(image, invoice), "发票添加失败");
        } else {
            error("发票扫面件不正确");
        }
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

    /**
     * 图片预览
     *
     * @param id 发票的唯一Id
     */
    public void image(int id) {
        String fileName = invoiceService.imageService(id);
        byte[] inputStream = FileImageUtils.readImageUtils(fileName, FieldUtils.INVOICE);
        if (inputStream != null) {
            success(Base64.encode(inputStream));
        } else {
            //用于表示不做任何返回，这样就不会出现自动去找响应的HTML了
            renderNull();
        }
    }
}
