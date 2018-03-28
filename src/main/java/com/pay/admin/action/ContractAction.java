package com.pay.admin.action;

import com.jfinal.aop.Before;
import com.jfinal.upload.UploadFile;
import com.pay.admin.service.ContractService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Get;
import com.pay.data.utils.FileImageUtils;
import com.pay.user.model.Contract;

/**
 * @createTime: 2018/3/9
 * @author: HingLo
 * @description: 合同管理
 */
public class ContractAction extends BaseController {
    private final ContractService contractService = new ContractService();

    /**
     * 定义文件的后缀是PDF
     */
    private static String SUFFIX = ".pdf";

    /**
     * 合同列表信息
     */
    @Before(Get.class)
    public void list() {
        success(contractService.listService());
    }

    /**
     * 在线预览合同信息
     */
    @Before(Get.class)
    public void pdf() {
        String pdfName = getPara("pdf");
        if (FileImageUtils.isSpecifySuffixUtils(pdfName, SUFFIX)) {
            renderFile(contractService.lookPDFService(pdfName));
        } else {
            render("/404.html");
        }
    }

    /**
     * 添加合同信息
     */
    public void add() {
        UploadFile uploadFile = getFile("pdfName");
        Contract contract = getBean(Contract.class, "");
        if (uploadFile != null && FileImageUtils.isSpecifySuffixUtils(uploadFile, SUFFIX)) {
            result(contractService.addService(contract, uploadFile));
        }
        error("文件格式不正确");
    }

}
