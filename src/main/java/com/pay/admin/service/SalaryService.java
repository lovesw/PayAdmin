package com.pay.admin.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pay.data.utils.CommonUtils;
import com.pay.user.model.Salary;
import com.pay.user.model.SalaryNote;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/4/12
 * @author: HingLo
 * @description: 管理员管理工资
 */
public class SalaryService {

    /**
     * 管理员查看指定年月的工资
     *
     * @param month 指定的时间
     * @return 工资表
     */
    public List<Record> listService(Date month) {

        //应该发的
        String shout_count = "(base_pay + work_reward+achievements+reward+house+other) ";
        //应该扣的
        String take_count = "(take+take_error+take_other+s.social)  ";
        //数据库sql
        String sql = "select u.name ,s.* ,";
        //应发合计
        sql += shout_count + "as should_count,";
        //应扣合计
        sql += take_count + "as take_count,";
        //申报工资
        sql += "(" + shout_count + "-(take+take_error+take_other)) as submit_money,";
        //实际发工资
        sql += "(" + shout_count + "-" + take_count + ") as actual_money";

        sql += " from user as u  left join salary as s  on s.user_id=u.id and s.month=?";

        List<Record> list = Db.find(sql, month);
        //处理工资列表，税计算，没有填写的计算等
        return CommonUtils.taxUtil(CommonUtils.salaryUtil(list));
    }

    /**
     * 添加薪水
     *
     * @param salary 薪水对象
     * @return 是否添加成功
     */
    @Before(Tx.class)
    public boolean addService(Salary salary) {
        //检查是否可以可添加
        if (!checks(salary.getUserId())) {
            return false;
        }

        salary.setDate(new Date());
        salary.setMonth(CommonUtils.getLastMonth());
        //记录当月此人已经填写基本工资
        SalaryNote salaryNote = new SalaryNote();
        salaryNote.setMonth(CommonUtils.getLastMonth());
        salaryNote.setUserId(salary.getUserId());
        return salary.save() && salaryNote.save();
    }

    /**
     * 获取尚未添加工资的人的信息
     *
     * @return 没有添加上月工资员工列表，
     */
    public List<Record> uListService() {
        String sql = "select id,name from user where id not in (select user_id from salary_note where month=?)";
        return Db.find(sql, CommonUtils.getLastMonth());
    }

    /**
     * 检测当月所有人是否全部填写了基本工资
     *
     * @return 是否填写完毕，True：可以计算绩效，false 不可以计算
     */
    public boolean check() {
        String sql = "select u.name, s.id  from user as u  left join salary as s  on s.user_id=u.id and s.month=?";
        List<Record> recordList = Db.find(sql, CommonUtils.getLastMonth());
        for (Record record : recordList) {
            System.out.println("" + record.get("id"));
            if (record.get("id") != null && Convert.toInt(record.get("id")) != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检测该用户是否可以添加该月的工资
     *
     * @param userId 用户的唯一Id
     * @return 是否可以添加工资
     */
    private boolean checks(String userId) {
        String sql = "select id from salary_note where user_id=?";
        return Db.find(sql, userId).isEmpty();

    }


    /**
     * 计算绩效
     *
     * @return 绩效计算是否成功
     */
    public boolean countService() {


        return false;
    }

    /**
     * @param record 修改的指定字段
     * @return 是否修改成功
     */
    public boolean updateService(Record record) {
        return Db.update("salary", "id", record);
    }

}
