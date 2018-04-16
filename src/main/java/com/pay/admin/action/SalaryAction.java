package com.pay.admin.action;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.aop.Duang;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Record;
import com.pay.admin.service.SalaryService;
import com.pay.data.controller.BaseController;
import com.pay.data.interceptors.Get;
import com.pay.data.interceptors.Post;
import com.pay.data.interceptors.Put;
import com.pay.data.utils.CommonUtils;
import com.pay.user.model.Salary;

import java.util.Date;
import java.util.Map;

/**
 * @createTime: 2018/4/11
 * @author: HingLo
 * @description: 薪水管理
 */
public class SalaryAction extends BaseController {

    private static final SalaryService salaryService = Duang.duang(SalaryService.class);

    /**
     * 管理员查看当前月需要发放的工资
     */
    @Before(Get.class)
    public void list(String month) {
        Date date;
        if (StrUtil.isBlank(month)) {
            date = CommonUtils.getLastMonth();
        } else {
            date = DateUtil.parse(month + "-01", "yyyy-MM-dd");
        }
        success(salaryService.listService(date));
    }

    /**
     * 添加薪水
     *
     * @param salary 薪水对象
     */
    @Before(Post.class)
    public void add(@Para("") Salary salary) {
        result(salaryService.addService(salary), "添加失败");
    }

    /**
     * 修改指定的字段
     */
    @Before(Put.class)
    public void update() {
        Record record = getRecordPara();
        Map<String, Object> map = record.getColumns();
        //全勤奖的特殊处理
        if (map.containsKey("total_reward")) {
            record.set("total_reward", Convert.toInt(record.get("total_reward")) == 1);
        }
        success(salaryService.updateService(record));
    }

    /**
     * 获取没有添加工资人的信息
     */
    @Before(Get.class)
    public void uList() {
        success(salaryService.uListService());
    }

    /**
     * 检测绩效是否可以计算
     */
    @Before(Get.class)
    public void check() {
        success(salaryService.check());
    }

    /**
     * 计算绩效
     */
    @Before(Get.class)
    public void count() {
        result(salaryService.countService(), "绩效计算出错,请稍后重试");
    }


    /***
     * 用户查看自己的绩效详情
     * @param userId 指定的员工Id
     */
    @Before(Get.class)
    public void acount(String userId, String month) {
        success(salaryService.acountService(userId,CommonUtils.getOneMonth(month)));
    }

}
