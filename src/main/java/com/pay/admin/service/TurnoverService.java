package com.pay.admin.service;

import cn.hutool.core.date.DateUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.pay.data.entity.ScaleEntity;
import com.pay.data.entity.TurnoverEntity;
import com.pay.data.utils.CommonUtils;
import com.pay.data.utils.FieldUtils;
import com.pay.user.model.Cooperation;
import com.pay.user.model.Scale;
import com.pay.user.model.Turnover;
import com.pay.user.model.TurnoverNote;

import java.util.*;

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
    @Before(Tx.class)
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
        TurnoverNote turnoverNote = getTurnoverNote(null, cooperationId, date);


        return turnoverNote.save() && Db.batchSave(turnoverList, turnoverList.size()).length > 0;

    }

    /**
     * 获取一个记录上月该主题市场已经填写过的对象
     *
     * @param cooperationId 市场
     * @param date          月份
     * @return TurnoverNote 对象
     */
    private TurnoverNote getTurnoverNote(Long companyId, Long cooperationId, Date date) {
        //记录date时间是否填写
        TurnoverNote turnoverNote = new TurnoverNote();
        turnoverNote.setCooperationId(cooperationId);
        turnoverNote.setCompanyId(companyId);
        turnoverNote.setDate(date);
        turnoverNote.setStatus(true);
        return turnoverNote;
    }

    /**
     * 添加按照比例分成
     *
     * @param list          比例集合
     * @param cooperationId 合作市场
     * @param companyId     公司
     * @param money         市场总金额
     * @return 是否添加成功
     */
    @Before(Tx.class)
    public boolean sAddService(List<ScaleEntity> list, Long companyId, Long cooperationId, Double money) {
        Date date = CommonUtils.getLastMonth();
        //判断当前月是否已经添加了
        if (!checksService(companyId, cooperationId)) {
            return false;
        }
        List<Scale> scaleList = new ArrayList<>(list.size());
        //查看是否有相同的人
        Set<String> stringSet = new HashSet<>(list.size());
        //临时变量，用来计算最终的比例是否是%
        Double sum = 0.0;
        for (ScaleEntity scaleEntity : list) {
            Scale scale = new Scale();
            scale.setCooperationId(cooperationId);
            scale.setCompanyId(companyId);
            scale.setMonth(date);
            //设置总金额
            scale.setPay(money);
            //设置分配比例
            scale.setRatio(scaleEntity.getNum());
            scale.setUserId(scaleEntity.getId());
            scale.setDate(new Date());
            //将用户Id添加到stringList中
            stringSet.add(scaleEntity.getId());
            sum += scaleEntity.getNum();
            scaleList.add(scale);
        }
        //看看将全部的金额分配出去了
        if (sum != 100) {
            return false;
        }
        //长度不相同，说明有重复的人，这样就不通过
        if (list.size() != stringSet.size()) {
            return false;
        }

        //标记上个月已经添加了
        TurnoverNote turnoverNote = getTurnoverNote(companyId, cooperationId, date);
        return turnoverNote.save() && Db.batchSave(scaleList, scaleList.size()).length > 0;
    }

    /**
     * 主题分成检测是否已经填写
     *
     * @param cooperationId 合作市场的Id
     * @return 返回是否填写过（true:能填写，false：不能填写）
     */
    public boolean checkService(Long cooperationId) {
        Date date = CommonUtils.getLastMonth();
        String sql = "select * from turnover_note where cooperation_id=? and date=?";
        List<TurnoverNote> turnoverNoteList = TurnoverNote.dao.find(sql, cooperationId, date);
        return turnoverNoteList.isEmpty();
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
    public List<Record> sListService(Long companyId, Long cooperationId) {
        Cooperation cooperation = Cooperation.dao.findById(cooperationId);
        if (cooperation == null) {
            return null;
        } else {
            //如果是按照主题分成就直接返回，不做查询
            if (cooperation.getSeparate()) {
                return null;
            } else {
                String sql = "select scale.id, ratio,month,pay,u.id as uid,u.name from scale,user as u where u.id=scale.user_id and company_id=? and cooperation_id=? and date_format(month,'%y-%d')=date_format(?,'%y-%d') ";
                return Db.find(sql, companyId, cooperationId, CommonUtils.getLastMonth());
            }
        }


    }

    /**
     * 修改比例分成信息
     *
     * @param recordList 批量修改对象
     * @return 返回是否修改成功
     */
    @Before(Tx.class)
    public boolean sUpdateService(List<Record> recordList) {
        System.out.println(recordList);
        return Db.batchUpdate("scale", "id", recordList, recordList.size()).length > 0;
    }

    /**
     * 检测该该公司该市场该是否已经填写过
     *
     * @param cooperationId 合作市场
     * @param companyId     公司Id
     * @return 是否填写
     */
    public boolean checksService(Long companyId, Long cooperationId) {
        Date date = CommonUtils.getLastMonth();
        String sql = "select * from turnover_note where cooperation_id=? and company_id=? and date=?";
        List<TurnoverNote> turnoverNoteList = TurnoverNote.dao.find(sql, cooperationId, companyId, date);
        return turnoverNoteList.isEmpty();
    }
}
