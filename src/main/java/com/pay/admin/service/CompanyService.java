package com.pay.admin.service;

import com.pay.data.utils.IdUtils;
import com.pay.user.model.Company;

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
        company.setId(IdUtils.getId());
        return company.save();
    }

    /**
     * 通过公司的唯一Id删除公司全部信息
     *
     * @param id 指定的公司Id
     * @return 返回值
     */
    public boolean deleteService(String id) {
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
}
