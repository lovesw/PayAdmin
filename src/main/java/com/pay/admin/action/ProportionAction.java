package com.pay.admin.action;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.pay.admin.service.ProportionService;
import com.pay.data.controller.BaseController;
import com.pay.data.entity.MakeDesign;
import com.pay.data.interceptors.Put;
import com.pay.data.utils.CommonUtils;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/4/3
 * @author: HingLo
 * @description: 比例分成
 */
public class ProportionAction extends BaseController {
    private static final ProportionService proportionService = new ProportionService();

    /**
     * 查看指定月份的每个市场的比例分成
     *
     * @param date 指定的月份，格式如：2018-05
     */
    public void list(String date) {
        if (StrUtil.isBlank(date)) {
            error("查询参数不正确");
        } else {
            success(proportionService.listService(CommonUtils.getOneMonth(date)));
        }
    }

    /**
     * 按照主题比例分成的商家列表
     */
    public void clist() {
        success(proportionService.cListService());
    }

    /***
     * 添加每个月的分成比例
     */
    public void add(String str) {
        //将比例分成的json格式的字符串转为MakeDesign集合
        List<MakeDesign> list = CommonUtils.strJsonToBean(str, MakeDesign.class);
        boolean bool = proportionService.addService(list);
        result(bool, "添加失败");
    }

    /**
     * 修改指定的分成比例
     *
     * @param id     指定的Id
     * @param make   制作人的比例
     * @param design 设计人的比例
     */
    @Before(Put.class)
    public void update(int id, float make, float design) {
        //todo: 此方法需要一个时间拦截器，在指定的时间段才能修改过期后就不能修改
        result(proportionService.updateService(id, make, design));
    }


}
