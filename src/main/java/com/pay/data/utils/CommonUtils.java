package com.pay.data.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.jfinal.plugin.activerecord.Record;

import java.util.Date;
import java.util.List;

/**
 * @createTime: 2018/4/4
 * @author: HingLo
 * @description: 通常用的一些工具方法
 */
public class CommonUtils {

    /**
     * 获取上个月的1号
     *
     * @return 上个月的一号日期
     */
    public static Date getLastMonth() {
        return DateUtil.offsetMonth(getOneMonth(), -1);
    }

    /***
     *
     * 获取每个月的1号
     * @return 每个月1号的日期
     */
    public static Date getOneMonth() {
        String dateStr = DateUtil.format(new Date(), "yyyy-MM") + "-01";
        return DateUtil.parse(dateStr);
    }

    /**
     * 将指定的年月格式化为年月日
     *
     * @param month 年月，如：2018-08格式
     * @return 指定年月的1号
     */
    public static Date getOneMonth(String month) {
        return DateUtil.parse(month + "-01", "yyyy-MM-dd");
    }

    /**
     * 定义数组不是null，不是空白字符串
     *
     * @param str 字符串数组
     * @return 是否不是null，空白字符，不可见字符
     */
    public static boolean isNotBlank(String... str) {
        for (String s : str) {
            if (StrUtil.isBlank(s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param str Json格式的字符串
     * @param t   T class类型
     * @param <T> 泛型T
     * @return json实体解析集合
     */
    public static <T> List<T> strJsonToBean(String str, Class<T> t) {
        JSONArray jsonArray = JSONUtil.parseArray(str);
        //将jsonArray转为TurnoverEntity集合
        return JSONUtil.toList(jsonArray, t);
    }

    /**
     * 工资查看的sql 语句
     *
     * @return 拼接好的SQL，如果有其他条件，直接走后拼接
     */
    public static String salarySql() {
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

        sql += " from user as u  left join salary as s  on s.user_id=u.id ";
        return sql;
    }

    /**
     * 工资指定月没有填写的处理，将没有填写基本工资的人处理为0
     *
     * @param list 工资列表
     * @return 处理好的工资列表
     */
    public static List<Record> salaryUtil(List<Record> list) {
        list.forEach(x -> {
            x.set("tax", 0);
            String[] s = x.getColumnNames();
            for (String s1 : s) {
                if (x.get(s1) == null) {
                    x.set(s1, 0);
                }
            }
        });
        return list;
    }

    /**
     * 税计算处理，与实际发放的工资处理
     *
     * @param list 工资集合
     * @return 计算好的工资集合
     */
    public static List<Record> taxUtil(List<Record> list) {
        for (Record record : list) {
            if (Convert.toInt(record.get("id")) != 0) {
                //申报工资
                double submit_money = Convert.toDouble(record.get("submit_money"));
                //扣除社保的工资
                double x = submit_money - Convert.toDouble(record.get("social"));
                //税收
                double tax = getTax(x);
                //设置税收
                record.set("tax", getTax(x));
                //实际发放的工资
                Double actual_money = Convert.toDouble(record.get("actual_money"));
                //设置实际发送的工资，减去税收
                record.set("actual_money", actual_money - tax);
            }

        }
        return list;
    }

    /**
     * 计算税率的
     *
     * @param money 计算工资
     * @return 返回税
     */
    private static Double getTax(Double money) {
        double tax;
        if (money <= 3500)
            tax = 0;
        else if (money <= 5000)
            tax = (money - 3500) * 0.03f;
        else if (money <= 8000)//假设工资7000块，计算时需要注意5000以下的部分按0.03计算，5001~8000部分按0.1计算，所以计算表达式如下
            tax = (money - 5000) * 0.1f + (5000 - 3500) * 0.03f;
        else if (money <= 12500)
            tax = (money - 8000) * 0.2f + (8000 - 5000) * 0.1f + (5000 - 3500) * 0.03f;
        else if (money <= 38500)
            tax = (money - 12500) * 0.25f + (12500 - 8000) * 0.2f + (8000 - 5000) * 0.1f + (5000 - 3500) * 0.03f;
        else if (money <= 58500)
            tax = (money - 38500) * 0.3f + (38500 - 12500) * 0.25f + (12500 - 8000) * 0.2f + (8000 - 5000) * 0.1f + (5000 - 3500) * 0.03f;
        else if (money <= 83500)
            tax = (money - 58500) * 0.35f + (58500 - 38500) * 0.3f + (38500 - 12500) * 0.25f + (12500 - 8000) * 0.2f + (8000 - 5000) * 0.1f + (5000 - 3500) * 0.03f;
        else
            tax = (money - 83500) * 0.45f + (83500 - 58500) * 0.35f + (58500 - 38500) * 0.3f + (38500 - 12500) * 0.25f + (12500 - 8000) * 0.2f + (8000 - 5000) * 0.1f + (5000 - 3500) * 0.03f;
        return Convert.toDouble(NumberUtil.roundStr(tax, 2));
    }
}
