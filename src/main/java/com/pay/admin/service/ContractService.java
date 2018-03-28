package com.pay.admin.service;

import com.jfinal.upload.UploadFile;
import com.pay.data.utils.FileImageUtils;
import com.pay.data.utils.HumpToUnderline;
import com.pay.user.model.Contract;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public List<Map<String, Object>> listService() {
        String sql = "select c.id,u.name as user_id,title,pdf,first,second,c.date,start_date,end_date from contract as c,user as u where c.user_id=u.id ";
        //转为驼峰命名
        return HumpToUnderline.underlineToHumps(Contract.dao.find(sql));
    }

    /**
     * 预览PDF格式的文件
     *
     * @param pdfName PDF名称
     * @return PDF的文件对象
     */
    public File lookPDFService(String pdfName) {
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
        String fileName = contract.getUserId() + ".pdf";
        contract.setPdf(fileName);
        contract.setDate(new Date());
        return FileImageUtils.savePDF(uploadFile, fileName) && contract.save();
    }
}
