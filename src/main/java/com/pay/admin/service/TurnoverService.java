package com.pay.admin.service;

import cn.hutool.core.date.DateUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pay.data.entity.TurnoverEntity;
import com.pay.data.utils.CommonUtils;
import com.pay.data.utils.FieldUtils;
import com.pay.user.model.Fillturnover;
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
        //记录date时间是否填写
        Fillturnover fillturnover = new Fillturnover();
        fillturnover.setCooperationId(cooperationId);
        fillturnover.setDate(date);
        fillturnover.setStatus(true);


        return fillturnover.save() && Db.batchSave(turnoverList, turnoverList.size()).length > 0;

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
}
