package com.pay.admin.service;

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
     * @return 添加的操作结果
     */
    public boolean addService(Invoice invoice) {
        invoice.setId(IdUtils.getId());
        return invoice.save();
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
}
