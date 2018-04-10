package com.pay.admin.service;

import cn.hutool.core.date.DateUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pay.data.entity.ScaleEntity;
import com.pay.data.entity.TurnoverEntity;
import com.pay.data.utils.CommonUtils;
import com.pay.data.utils.FieldUtils;
import com.pay.user.model.Cooperation;
import com.pay.user.model.Fillturnover;
import com.pay.user.model.Scale;
import com.pay.user.model.Turnover;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/4/10
 * @author: HingLo
 * @description: 工资管理的服务层
 */
public class TurnoverService {
    /**
     * 查询指定市场近6个月的所有主题的收入
     *
     * @param id 指定市场的id
     * @return 返回主题信息
     */
    public List<Record> listService(Long id) {
        String sql = "select  t.id,(SELECT name from user as u WHERE u.id=t.design_id) as dname, (select name from cooperation as c where c.id=t.cooperation_id) as cname,(select name from company  as c where t.company_id=c.id) as coname,(select name from user as u where u.id=t.make_id) as mname,name,ename,shelves_date,";
        sql += "(select num from turnover where tid=t.id and date_format(date,'%y-%m') = date_format(?,'%y-%m')) as num ";
        sql += "from theme as t where t.status=? and money_type=? and (shelves_date between ? and ?) and t.cooperation_id=?";
        //当前月的1号
        Date date = CommonUtils.getOneMonth();
        // 获取6个月前的时间
        Date date1 = DateUtil.offsetMonth(date, -6);
        //获取上个月的时间
        Date date2 = CommonUtils.getLastMonth();

        return Db.find(sql, date2, FieldUtils.THEME_STATUS_1, FieldUtils.THEME_MONEY_1, date1, date, id);

    }

    /**
     * @param list          添加的实体信息
     * @param cooperationId 合作商家Id
     * @return 返回是否添加成
     */
    public boolean addService(List<TurnoverEntity> list, Long cooperationId) {
        Date date = CommonUtils.getLastMonth();
        //判断当前月是否已经添加了
        if (!checkService(cooperationId)) {
            return false;
        }


        List<Turnover> turnoverList = new ArrayList<>(list.size());
        for (TurnoverEntity turnoverEntity : list) {
            Turnover turnover = new Turnover();
            turnover.setTid(turnoverEntity.getId());
            turnover.setNum(turnoverEntity.getNum());
            turnover.setDate(date);
            turnoverList.add(turnover);
        }
        //标记上个月已经添加了
        Fillturnover fillturnover = getFillturnover(cooperationId, date);


        return fillturnover.save() && Db.batchSave(turnoverList, turnoverList.size()).length > 0;

    }

    /**
     * 获取一个记录上月该主题市场已经填写过的对象
     *
     * @param cooperationId 市场
     * @param date          月份
     * @return Fillturnover 对象
     */
    private Fillturnover getFillturnover(Long cooperationId, Date date) {
        //记录date时间是否填写
        Fillturnover fillturnover = new Fillturnover();
        fillturnover.setCooperationId(cooperationId);
        fillturnover.setDate(date);
        fillturnover.setStatus(true);
        return fillturnover;
    }

    /**
     * 添加按照比例分成
     *
     * @param list          比例集合
     * @param cooperationId 合作市场
     * @param money         市场总金额
     * @return 是否添加成功
     */
    public boolean sAddService(List<ScaleEntity> list, Long cooperationId, Double money) {
        Date date = CommonUtils.getLastMonth();
        //判断当前月是否已经添加了
        if (!checkService(cooperationId)) {
            return false;
        }
        List<Scale> scaleList = new ArrayList<>(list.size());
        //临时变量，用来计算最终的比例是否是%
        Double sum = 0.0;
        for (ScaleEntity scaleEntity : list) {
            Scale scale = new Scale();
            scale.setCooperationId(cooperationId);
            scale.setMonth(date);
            //设置总金额
            scale.setPay(money);
            //设置分配比例
            scale.setRatio(scaleEntity.getNum());
            scale.setUserId(scaleEntity.getId());
            sum += scaleEntity.getNum();
            scaleList.add(scale);
        }
        //看看将全部的金额分配出去了
        if (sum != 100) {
            return false;
        }
        //标记上个月已经添加了
        Fillturnover fillturnover = getFillturnover(cooperationId, date);
        return fillturnover.save() && Db.batchSave(scaleList, scaleList.size()).length > 0;
    }

    /**
     * @param cooperationId 合作市场的Id
     * @return 返回是否填写过（true:能填写，false：不能填写）
     */
    public boolean checkService(Long cooperationId) {
        Date date = CommonUtils.getLastMonth();
        String sql = "select * from fillturnover where cooperation_id=? and date=?";
        List<Fillturnover> fillturnoverList = Fillturnover.dao.find(sql, cooperationId, date);
        return fillturnoverList.isEmpty();
    }

    /**
     * 修改上月该主题的收益
     *
     * @param tid 主题id
     * @param num 需要设置的金额
     * @return 是否修改成功
     */
    public boolean updateService(Long tid, double num) {
        Date date = CommonUtils.getLastMonth();
        String sql = "update turnover set num=? where tid=? and date=?";
        return Db.update(sql, num, tid, date) > 0;
    }

    /**
     * 市场相关的人员
     *
     * @param cooperationId 市场Id
     * @return 相关设计人员的信息
     */
    public List<Record> sListService(Long cooperationId) {
        Cooperation cooperation = Cooperation.dao.findById(cooperationId);
        if (cooperation == null) {
            return null;
        } else {
            //如果是按照主题分成就直接返回，不做查询
            if (cooperation.getSeparate()) {
                return null;
            }

            String sql = "select scale.id, num,month,pay,u.id,u.name from scale,user as u where u.id=scale.user_id and cooperation_id=? and date_format(date,'%y-%d')=date_format(?,'%y-%d') ";
            return Db.find(sql, cooperationId, CommonUtils.getLastMonth());
        }


    }

    /**
     * 修改比例分成信息
     *
     * @param id  比例分成的唯一Id
     * @param num 分成比例
     * @return 返回是否修改成功
     */
    public boolean sUpdateService(Long id, Double num) {
        String sql = "update scale set ratio=? where id=? ";
        return Db.update(sql, num, id) > 0;


    }
}
