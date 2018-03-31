package com.pay.admin.action;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;
import com.pay.admin.service.ContractService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.utils.FieldUtils;
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
     * 管理员查看的全部合同列表信息
     */
    @Before(Get.class)
    public void list() {
        success(contractService.listService());
    }

    /***
     * 在线预览合同信息,此方法主要是用于管理员查看
     * @param id 合同的唯一编号
     */
    @Before(Get.class)
    public void pdf(String id) {
        if (StrUtil.isNotBlank(id)) {
            String pdfName = contractService.lookPDFService(id);
            String token = RandomUtil.randomNumbers(10);
            //将token放入到缓存中
            CacheKit.put("pdfToken", id, token);
            //响应token
            success("/u/pdf?token=" + token + "&id=" + id + "&pdfName=" + pdfName);
        } else {
            error("参数无效");
        }
    }

    /**
     * 添加合同信息
     */
    @Before(Post.class)
    public void add(UploadFile pdfName, @Para("") Contract contract) {
        String userId = getUserId();
        //设置登录人就为甲方
        contract.setFirst(userId);

        if (pdfName != null && FileImageUtils.isSpecifySuffixUtils(pdfName, FieldUtils.SUFFIX)) {
            result(contractService.addService(contract, pdfName));
        } else {
            error("文件格式不正确");
        }

    }

}
