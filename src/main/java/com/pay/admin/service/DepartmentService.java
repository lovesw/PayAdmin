package com.pay.admin.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pay.user.model.Department;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/4/11
 * @author: HingLo
 * @description: 部门管理的服务层
 */
public class DepartmentService {
    /**
     * 查看部门信息
     *
     * @return 部门信息集合
     */
    public List<Department> listService() {
        String sql = "select * from department";
        return Department.dao.find(sql);
    }

    /**
     * 添加部门职位信息
     *
     * @param department 部门实体对象
     * @return 部门信息是否添加成功
     */
    public boolean addService(Department department) {
        department.setDate(new Date());
        return department.save();
    }

    /**
     * 修改部门职位信息
     *
     * @param record 部门实体对象
     * @return 部门信息是否修改成功
     */

    public boolean updateService(Record record) {
        return Db.update("department", "id", record);
    }

    /**
     * 通过部门的Id来删除部门信息，但是如果有人使用部门信息就不能删除
     *
     * @param id 部门Id
     * @return 是否删除成功
     */
    public boolean deleteService(Long id) {
        return Department.dao.deleteById(id);
    }
}
