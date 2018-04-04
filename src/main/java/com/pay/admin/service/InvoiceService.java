package com.pay.admin.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.pay.data.utils.FieldUtils;
import com.pay.data.utils.FileImageUtils;
import com.pay.data.utils.IdUtils;
import com.pay.user.model.Invoice;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 发票服务管理的服务层
 */
public class InvoiceService {

    /**
     * 根据指定的日期查询发票
     *
     * @param date 指定的日期
     * @return 发票记录
     */
    public List<Record> listService(Date date) {
        String sql = "select i.id,c.name,co.name as names ,service_type,ticket_date,ticket_id,ticket_num,i.ticket_type,goods_name,tariff,tariff_total,tariff_money,bill_date,bill_month,invoice_type from invoice as i,company as c, cooperation as co where c.id=i.company_id and i.cooperation_id=co.id and bill_month=?";
        return Db.find(sql, date);
    }

    /**
     * 添加发票对象
     *
     * @param invoice 发票信息对象
     * @param img     发票扫描件
     * @return 添加的操作结果
     */
    public boolean addService(UploadFile img, Invoice invoice, int type) {
        //解析发票类型：有发票与无发票
        if (type == 1) {
            invoice.setInvoiceType(true);
        } else {
            invoice.setInvoiceType(false);
        }
        //生成图片名称
        String imgName = IdUtils.getId() + ".png";
        //保存发票的扫面件
        boolean bool = FileImageUtils.saveImageUtils(img, imgName, FieldUtils.INVOICE);
        //设置图片名称
        invoice.setImg(imgName);
        //设置时间
        invoice.setDate(new Date());
        return bool && invoice.save();
    }

    /**
     * 通过发票的唯一Id删除发票全部信息
     *
     * @param id 指定的发票Id
     * @return 返回值
     */
    public boolean deleteService(String id) {
        //获取图片名称
        String fileName = Invoice.dao.findById(id).getImg();

        if (Invoice.dao.deleteById(id)) {
            //删除指定的文件图片,不管是否删除成功
            FileImageUtils.deleteFileUtils(fileName, FieldUtils.INVOICE);
            return true;
        } else {
            return false;
        }
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
