package com.pay.admin.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.pay.data.utils.FileImageUtils;
import com.pay.user.model.Archives;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/31
 * @author: HingLo
 * @description: 档案管理的服务层
 */
public class ArchivesService {

    /**
     * 删除指定的档案信息
     *
     * @param id 档案的唯一的Id
     * @return 返回是否删除成功
     */
    public boolean deleteService(int id) {
        return Archives.dao.deleteById(id);
    }

    /**
     * 添加档案信息
     *
     * @param archives 档案对象
     * @return 返回是否添加成功
     */
    public boolean addService(Archives archives, UploadFile padName) {
        //合同的时间判断,开始时间必须小于结束时间
        if (archives.getEndDate().getTime() < archives.getStartDate().getTime()) {
            return false;
        }
        archives.setDate(new Date());

        //随机生成一个合同名称
        String fileName = DateUtil.format(new Date(), "yyyyMMddHHmmss") + RandomUtil.randomNumbers(2) + ".pdf";
        archives.setEnclosure(fileName);
        archives.setDate(new Date());
        return FileImageUtils.savePDF(padName, fileName) && archives.save();
    }

    /**
     * 指定用户的额档案列表信息
     *
     * @param userId 用户的唯一ID
     * @return 返回用户列表
     */
    public List<Record> listService(String userId) {
        String sql = "select id,content,start_date,end_date,(select name from user where id=execute) as execute from  archives as a where user_id=?";
        return Db.find(sql, userId);
    }

    /**
     * 文件下载  返回文件
     *
     * @param id 文件的唯一Id
     * @return 返回文件对象
     */
    public String downloadService(int id) {
        return Archives.dao.findById(id).getEnclosure();
    }
}
