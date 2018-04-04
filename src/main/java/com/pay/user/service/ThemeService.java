package com.pay.user.service;

import cn.hutool.core.util.StrUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pay.data.entity.ThemeEntity;
import com.pay.data.utils.FieldUtils;
import com.pay.user.model.Theme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/4/3
 * @author: HingLo
 * @description: 主题管理服务层
 */
public class ThemeService {

    /**
     * 添加主题信息
     *
     * @param theme 主题对象
     * @return 添加结果
     */
    public boolean addService(Theme theme) {
        theme.setDate(new Date());
        return theme.save();
    }

    /**
     * 更新用户信息
     *
     * @param theme 需要更新的主题信息
     * @return 操作结果
     */
    public boolean updateService(Theme theme) {
        Long id = theme.getId();
        if (StrUtil.isBlank(id.toString())) {
            return false;
        }
        Theme theme1 = Theme.dao.findById(id);
        //当前Id查询的主题信息是否为null
        if (theme1 == null) {
            return false;
        }
        //如果该主题状态，通过或者主题不能编辑，则返回false
        if (!theme1.getStatus().equals(FieldUtils.THEME_STATUS_1)) {
            return false;
        }
        //设置主题状态为待审核
        return theme.update();
    }

    /**
     * 查看指定年月日的主题信息
     *
     * @param date 指定时间
     * @return 主题信息列表
     */
    public List<Record> listService(Date date, String userId) {
        String sql = "select date, t.id,(SELECT name from user as u WHERE u.id=t.design_id) as dname,service_type,(select name from cooperation as c where c.id=t.cooperation_id) as cname,name,ename,shelves_date,money_type,turnover,status  from theme as t where make_id=? and date_format(date,'%y-%m') = date_format(?,'%y-%m')";
        return Db.find(sql, userId, date);
        // list.forEach(x -> x.setTurnover(0.0));
    }

    /**
     * 查询按照主题分成的合作商家
     *
     * @return 商家信息
     */
    public List<Record> cListService() {
        String sql = "select id,name,fillname from cooperation";
        return Db.find(sql);
    }

    /**
     * 批量添加主题
     *
     * @param serviceType   业务类型，默认是：手机主题
     * @param cooperationId 合作市场商家的Id
     * @param makeId        制作人ID
     * @param list          批量的主题信息
     * @return 返回是否添加成功
     */
    @Before(Tx.class)
    public boolean addService(String serviceType, Long cooperationId, String makeId, List<ThemeEntity> list) {
        List<Theme> themeList = new ArrayList<>(list.size());
        Date date = new Date();
        list.forEach(themeEntity -> {
            Theme theme = new Theme();
            theme.setServiceType(serviceType);
            theme.setCooperationId(cooperationId);
            theme.setMakeId(makeId);
            theme.setEname(themeEntity.getEname());
            theme.setShelvesDate(themeEntity.getShelvesDate());
            theme.setName(themeEntity.getName());
            theme.setMoneyType(themeEntity.getMoneyType());
            theme.setDesignId(themeEntity.getDesignId());
            theme.setDate(date);
            themeList.add(theme);
        });
        if (Db.batchSave(themeList, themeList.size()).length == list.size()) {
            return true;
        } else {
            //抛出异常，然后进行实物回滚
            throw new RuntimeException("主题添加失败");
        }
    }

    /***
     * 获取用户的信息
     * @return 设计师列表
     */
    public List<Record> uListService() {
        String sql = "select id,name from user where id in (select user_id from user_role where role_id=?)";
        return Db.find(sql, FieldUtils.DESIGN);
    }

    /**
     * 查询与指定员工相关的设计主题
     *
     * @param date   时间
     * @param userId 员工编号
     * @return 主题集合
     */
    public List<Record> allListService(Date date, String userId) {
        String sql = "select date, t.id,(SELECT name from user as u WHERE u.id=t.design_id) as dname,service_type,(select name from cooperation as c where c.id=t.cooperation_id) as cname,(SELECT name from user as u where u.id=t.make_id) as mname,name,ename,shelves_date,money_type,turnover,status  from theme as t where (make_id=? or design_id=?) and date_format(date,'%y-%m') = date_format(?,'%y-%m')";
        return Db.find(sql, userId, userId, date);

    }
}
