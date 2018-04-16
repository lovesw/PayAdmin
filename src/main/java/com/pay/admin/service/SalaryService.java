package com.pay.admin.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pay.data.utils.CommonUtils;
import com.pay.user.model.Proportion;
import com.pay.user.model.Salary;
import com.pay.user.model.SalaryNote;

import java.util.*;

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

        //获取工资计算你的sql
        String sql = CommonUtils.salarySql() + "and s.month=? ";
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
            if (record.get("id") == null || Convert.toInt(record.get("id")) == 0) {
                return false;
            }
        }
        //检测该月的比例分成是否填写
        sql = "select id from proportion where month=?";
        return !Db.find(sql, CommonUtils.getLastMonth()).isEmpty();
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
    @Before(Tx.class)
    public boolean countService() {
        if (!check()) {
            throw new RuntimeException("无法计算绩效,尚有员工未填写基本工资");
        } else {
            //获取所有在职人员
            String sql = "select id from user where mark=true";
            List<Record> list = Db.find(sql);
            //初始化每个员工的绩效工资
            Map<String, Double> salaryMap = new HashMap<>(list.size());
            list.forEach(x -> salaryMap.put(x.getStr("id"), 0.0));

            //获取在职人员的比例分成的工资
            sql = "select user_id,(pay*ratio/100) as mon from scale where user_id in (select id from user where mark=true) and month=?";
            list = Db.find(sql, CommonUtils.getLastMonth());
            list.forEach(x -> {
                String userId = x.getStr("user_id");
                salaryMap.put(userId, salaryMap.get(userId) + x.getDouble("mon"));
            });

            //获取公司上个月的主题分成比例
            Map<Long, Proportion> map = getProportion(CommonUtils.getLastMonth());


            //获取近6个月的主题全部信息
            sql = "select design_id,make_id,cooperation_id,num from theme as t ,turnover as tu where tu.tid=t.id and tu.date=?";
            list = Db.find(sql, CommonUtils.getLastMonth());

            //开始计算绩效
            list.forEach(x -> {
                String designId = x.getStr("design_id");
                String makeId = x.getStr("make_id");
                Long cooperationId = x.getLong("cooperation_id");
                Double num = x.getDouble("num");//分成的总工资

                Double designNum = salaryMap.get(designId) + (map.get(cooperationId).getDesign() * num) / 100;
                Double makeNum = salaryMap.get(makeId) + (map.get(cooperationId).getMake() * num) / 100;

                salaryMap.put(designId, designNum);
                salaryMap.put(makeId, makeNum);

            });
            //将绩效的map对象转为Record对象

            sql = "select id,user_id from salary where month=?";
            list = Db.find(sql, CommonUtils.getLastMonth());
            List<Record> recordList = new ArrayList<>(list.size());
            list.forEach(x -> {

                Record record = new Record();
                record.set("id", x.getLong("id"));
                record.set("achievements", salaryMap.get(x.getStr("user_id")));
                recordList.add(record);
            });
            return Db.batchUpdate("salary", "id", recordList, recordList.size()).length > 0;
        }
    }

    /**
     * @param record 修改的指定字段
     * @return 是否修改成功
     */
    public boolean updateService(Record record) {
        return Db.update("salary", "id", record);
    }

    /**
     * 计算员工的绩效详情
     *
     * @param userId 员工编号
     * @param date   指定的年月
     * @return 绩效总和列表
     */
    public List<Record> acountService(String userId, Date date) {
        //获取市场
        String sql = "select id,name from cooperation where status=true";
        List<Record> cooperationList = Db.find(sql);
        //获取公司
        sql = "select id,name from company where status=true";
        List<Record> companyList = Db.find(sql);
        Map<Long, Map<Long, Record>> map = new HashMap<>(companyList.size());
        companyList.forEach(x -> {
            Long companyId = x.getLong("id");

            Map<Long, Record> recordMap = new HashMap<>(cooperationList.size());
            cooperationList.forEach(action -> {
                Record record = new Record();
                record.set("cooperationName", action.getStr("name"));
                record.set("companyName", x.getStr("name"));
                record.set("num", 0);
                recordMap.put(action.getLong("id"), record);
            });
            map.put(companyId, recordMap);
        });
        //第一步，获取比例分成的信息
        sql = "select company_id,cooperation_id, user_id,(pay*ratio/100) as mon from scale where user_id =? and month=?";
        List<Record> list = Db.find(sql, userId, date);
        list.forEach(x -> {
            Long companyId = x.getLong("company_id");
            Long cooperationId = x.getLong("cooperation_id");
            Double num = x.getDouble("mon");

            //获公司
            Map<Long, Record> recordMap = map.get(companyId);
            //市场该人的绩效
            Record record = recordMap.get(cooperationId);
            Double money = record.getDouble("num");
            record.set("num", money + num);
            //修改后的工资
            recordMap.put(cooperationId, record);
            map.put(companyId, recordMap);
        });

        //获取公司市场分成比例
        Map<Long, Proportion> map1 = getProportion(date);
        //获取与该员工相关的工资绩效主题
        sql = "select design_id,make_id,cooperation_id,company_id,num from theme as t ,turnover as tu where tu.tid=t.id and tu.date=? and (design_id=? or make_id=?)";

        List<Record> themeList = Db.find(sql, date, userId, userId);

        themeList.forEach(x -> {

            Long companyId = x.getLong("company_id");
            Long cooperationId = x.getLong("cooperation_id");
            Double num = x.getDouble("num");
            String makeId = x.getStr("make_id");
            String designId = x.getStr("design_id");

            //获获取指定公司下的所有市场该人的绩效
            Map<Long, Record> recordMap = map.get(companyId);
            //市场该人的绩效
            Record record = recordMap.get(cooperationId);
            Double money = record.getDouble("num");


            if (StrUtil.equals(userId, makeId)) {
                money += num * map1.get(cooperationId).getMake() / 100;
            }
            if (StrUtil.equals(userId, designId)) {
                money += num * map1.get(cooperationId).getDesign() / 100;
            }

            record.set("num", money);
            //修改后该市场的绩效工资
            recordMap.put(cooperationId, record);
            map.put(companyId, recordMap);
        });

        List<Record> recordList = new ArrayList<>();


        map.forEach((a, b) -> b.forEach((x, y) -> {
            recordList.add(y);
        }));
//去掉没有绩效的
        for (int i = recordList.size() - 1; i >= 0; i--) {
            Record x = recordList.get(i);
            if (x.getDouble("num") == 0) {
                recordList.remove(x);
            }
        }
        return recordList;
    }

    /**
     * 获取公司的指定市场的设计与制作的分成比例
     *
     * @param date 指定年月
     * @return 市场的分成比例
     */
    private Map<Long, Proportion> getProportion(Date date) {

        String sql = "select * from proportion where month=?";
        List<Proportion> proportionList = Proportion.dao.find(sql, date);
        Map<Long, Proportion> map1 = new HashMap<>(proportionList.size());
        proportionList.forEach(x -> map1.put(x.getId(), x));
        return map1;
    }
}
