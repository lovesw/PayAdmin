package com.pay.admin.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.upload.UploadFile;
import com.pay.data.utils.FileImageUtils;
import com.pay.data.utils.HumpToUnderline;
import com.pay.user.model.Contract;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @createTime: 2018/3/9
 * @author: HingLo
 * @description: 合同管理的服务层
 */
public class ContractService {

    /**
     * 合同列表
     *
     * @return 列表
     */
    public List<Contract> listService() {
        String sql = "select c.id,title,pdf, (SELECT name from user where id=c.first) as first, (SELECT name from user where id=c.second) as second,c.date,start_date,end_date from contract as c";
        //转为驼峰命名
        return Contract.dao.find(sql);
    }

    /**
     * 预览PDF格式的文件
     *
     * @param id 合同的唯一ID
     * @return PDF的文件对象
     */
    public File lookPDFService(String id) {
        String pdfName = Contract.dao.findById(id).getPdf();
        return FileImageUtils.readPDF(pdfName);
    }

    /**
     * 添加合同信息
     *
     * @param contract   合同信息的实体
     * @param uploadFile 合同PDF文件
     * @return 上传结果
     */
    public boolean addService(Contract contract, UploadFile uploadFile) {

        //如果甲方与乙方是同一个人，就返回
        if (StrUtil.equals(contract.getSecond(), contract.getFirst())) {
            return false;
        }
        //合同的时间判断,开始时间必须小于结束时间
        if (contract.getEndDate().getTime() < contract.getStartDate().getTime()) {
            return false;
        }

        //随机生成一个合同名称
        String fileName = DateUtil.format(new Date(), "yyyyMMddHHmmss") + RandomUtil.randomNumbers(2) + ".pdf";
        contract.setPdf(fileName);
        contract.setDate(new Date());
        return FileImageUtils.savePDF(uploadFile, fileName) && contract.save();
    }
}
