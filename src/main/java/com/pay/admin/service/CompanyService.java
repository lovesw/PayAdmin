package com.pay.admin.service;

import cn.hutool.core.util.StrUtil;
import com.jfinal.plugin.activerecord.Db;
import com.pay.data.utils.IdUtils;
import com.pay.user.model.Company;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/3/8
 * @author: HingLo
 * @description: 公司管理的服务层
 */
public class CompanyService {
    /**
     * 公司列表
     *
     * @return 公司列表
     */
    public List<Company> listService() {
        String sql = "select * from company ";
        return Company.dao.find(sql);
    }

    /**
     * 添加公司对象
     *
     * @param company 公司信息对象
     * @return 添加的操作结果
     */
    public boolean addService(Company company) {
        //设置默认为未启用状态
        company.setStatus(false);
        //设置公司的添加时间
        company.setDate(new Date());
        return company.save();
    }

    /**
     * 通过公司的唯一Id删除公司全部信息
     *
     * @param id 指定的公司Id
     * @return 返回值
     */
    public boolean deleteService(String id) {
        if (StrUtil.isBlank(id)) {
            return false;
        }
        return Company.dao.deleteById(id);
    }

    /***
     * 修改公司信息
     * @param company 公司实体对象
     * @return 返回操作结果
     */
    public boolean updateService(Company company) {
        return company.update();
    }

    /**
     * 修改公司状态
     *
     * @param id     公司的唯一标识符
     * @param status 公司状态
     * @return 是否修改成功
     */
    public boolean statusService(int id, boolean status) {
        String sql = "update company set status=? where id=? and status=?";
        return Db.update(sql, status, id, !status) > 0;
    }
}
