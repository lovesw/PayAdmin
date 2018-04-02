package com.pay.admin.service;

import com.jfinal.upload.UploadFile;
import com.pay.data.utils.FieldUtils;
import com.pay.data.utils.FileImageUtils;
import com.pay.data.utils.IdUtils;
import com.pay.user.model.Invoice;

import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 发票服务管理的服务层
 */
public class InvoiceService {

    /**
     * 发票列表
     *
     * @return 发票列表
     */
    public List<Invoice> listService() {
        String sql = "select * from invoice ";
        return Invoice.dao.find(sql);
    }

    /**
     * 添加发票对象
     *
     * @param invoice 发票信息对象
     * @param img     发票扫描件
     * @return 添加的操作结果
     */
    public boolean addService(UploadFile img, Invoice invoice) {
        //生成图片名称
        String imgName = IdUtils.getId();
        //保存发票的扫面件
        boolean bool = FileImageUtils.saveImageUtils(img, imgName, FieldUtils.INVOICE);
        return bool && invoice.save();
    }

    /**
     * 通过发票的唯一Id删除发票全部信息
     *
     * @param id 指定的发票Id
     * @return 返回值
     */
    public boolean deleteService(String id) {
        return Invoice.dao.deleteById(id);
    }

    /***
     * 修改发票信息
     * @param invoice 发票实体对象
     * @return 返回操作结果
     */
    public boolean updateService(Invoice invoice) {
        return invoice.update();
    }

    /**
     * 图片预览，获取图片的名称
     *
     * @param id 发票的Id
     * @return 图片的名称
     */
    public String imageService(int id) {
        return Invoice.dao.findById(id).getImg();
    }
}
