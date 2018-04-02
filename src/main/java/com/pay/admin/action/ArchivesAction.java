package com.pay.admin.action;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;
import com.pay.admin.service.ArchivesService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Delete;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.utils.FieldUtils;
import com.pay.data.utils.FileImageUtils;
import com.pay.user.model.Archives;

/**
 * @createTime: 2018/3/31
 * @author: HingLo
 * @description: 档案管理
 */
public class ArchivesAction extends BaseController {
    private final static ArchivesService archivesservice = new ArchivesService();

    /**
     * 档案列表
     *
     * @param userId 指定用户的档案列表
     */
    @Before(Get.class)
    public void list(String userId) {
        if (StrUtil.isBlank(userId)) {
            error("指定的用户不能为空");
        } else {
            success(archivesservice.listService(userId));
        }

    }

    /**
     * 添加档案信息
     *
     * @param archives 档案信息对象
     */
    @Before(Post.class)
    public void add(UploadFile pdfName, @Para("") Archives archives) {
        String userId = getUserId();
        //设置执行人为当前用户
        archives.setExecute(userId);

        if (pdfName != null && FileImageUtils.isSpecifySuffixUtils(pdfName, FieldUtils.SUFFIX)) {
            result(archivesservice.addService(archives, pdfName), "添加失败");
        } else {
            error("文件格式不正确");
        }
    }

    /**
     * 删除指定编号的档案
     *
     * @param id 档案的编号
     */
    @Before(Delete.class)
    public void delete(int id) {
        result(archivesservice.deleteService(id), "档案删除失败");
    }

    /**
     * 下载指定的文件
     *
     * @param id 档案的唯一ID
     */
    @Before(Get.class)
    public void download(int id) {
        String fileName = archivesservice.downloadService(id);
        String token = RandomUtil.randomNumbers(10);
        //将token放入到缓存中
        CacheKit.put("ArchToken", id, token);
        success("/u/download?id=" + id + "&token=" + token + "&pdfName=" + fileName);
    }

}
