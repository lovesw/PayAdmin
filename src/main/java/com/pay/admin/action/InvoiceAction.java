package com.pay.admin.action;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
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

import java.util.Date;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 发票的发票管理
 */
public class InvoiceAction extends BaseController {
    private final InvoiceService invoiceService = new InvoiceService();

    /**
     * 根据指定的年月来查看响应的发票信息
     *
     * @param billMonth 年月，格式如：2018-03
     */
    public void list(String billMonth) {
        if (StrUtil.isBlank(billMonth)) {
            error("指定的月份不正确");
        } else {
            Date date = DateUtil.parseDate(billMonth + "-01");
            success(invoiceService.listService(date));
        }
    }


    /**
     * 添加发票信息
     *
     * @param type    1:有发票 2:无发票
     * @param invoice 发票的对象
     * @param image   发票的图片
     */
    @Before(Post.class)
    public void add(UploadFile image, @Para("") Invoice invoice, int type) {
        if (image != null && FileImageUtils.iconUtils(image.getOriginalFileName())) {
            //设置月份，将传入的月份自动格式化为月的第一日
            invoice.setBillMonth(DateUtil.parseDate(getPara("billMonth") + "-01"));
            //去掉tariff的%
            String tariff = getPara("tariff");
            invoice.setTariff(Convert.toFloat(StrUtil.removeSuffix(tariff, "%")));

            //tariffMoney 去掉￥
            String tariffMoney = getPara("tariffMoney");
            invoice.setTariffMoney(Convert.toDouble(StrUtil.removePrefix(tariffMoney, "￥")));

            // tariffTotal 去掉￥
            String tariffTotal = getPara("tariffTotal");
            invoice.setTariffTotal(Convert.toFloat(StrUtil.removePrefix(tariffTotal, "￥")));

            result(invoiceService.addService(image, invoice, type), "发票添加失败");
        } else {
            error("发票扫描不正确");
        }
    }

    /**
     * 删除发票信息
     *
     * @param id 发票的唯一Id
     */
    @Before(Delete.class)
    public void delete(String id) {
        result(invoiceService.deleteService(id));
    }

    /**
     * 更新发票的信息
     */
    @Before(Put.class)
    public void update(Invoice invoice) {
        result(invoiceService.updateService(invoice));
    }

    /**
     * 图片预览
     *
     * @param id 发票的唯一Id
     */
    @Before(Get.class)
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
