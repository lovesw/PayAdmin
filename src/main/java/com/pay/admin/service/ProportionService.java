package com.pay.admin.service;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.pay.data.entity.MakeDesign;
import com.pay.data.utils.CommonUtils;
import com.pay.user.model.Proportion;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/4/3
 * @author: HingLo
 * @description: 市场比例每月的分成
 */
public class ProportionService {
    /**
     * @param date 指定日期的月份
     * @return 返回指定月份的比例分成信息
     */
    public List<Record> listService(Date date) {
        String sql = "select name,p.id,month,make,design from proportion as p,cooperation as co  where co.id=p.cooperation_id and month=?";
        return Db.find(sql, date);
    }

    /**
     * 查询按照主题分成的合作商家
     *
     * @return 商家信息
     */
    public List<Record> cListService() {
        String sql = "select id,name,fillname from cooperation where separate=true ";
        return Db.find(sql);
    }

    /**
     * 添加上个月的分成比例
     *
     * @return 是否添加成功
     */
    public boolean addService(List<MakeDesign> list) {
        String sql = "select id from proportion where month=?";
        //检测上个月是否已经添加
        Date date = CommonUtils.getLastMonth();
        List<Record> recordList = Db.find(sql, date);
        if (!recordList.isEmpty()) {
            return false;
        } else {

            //添加的数据处理
            List<Proportion> list1 = new ArrayList<>(list.size());
            list.forEach(x -> {
                Proportion proportion = new Proportion();
                proportion.setDesign(x.getDesign());
                proportion.setMake(x.getMake());
                proportion.setCooperationId(x.getId());
                proportion.setMonth(date);

                list1.add(proportion);
            });
            return Db.batchSave(list1, list.size()).length == list.size();
        }

    }

    /**
     * 修改设计与制作人的比例
     *
     * @param id     指定月与市场的分成比例的唯一Id
     * @param make   制作人的分成比例
     * @param design 设计人的分成比例
     * @return 是否修改成功
     */
    public boolean updateService(int id, float make, float design) {
        String sql = "update proportion set make=? , design=? where id=? and month=?";
        return Db.update(sql, make, design, id, CommonUtils.getLastMonth()) > 0;
    }
}
