package com.pay.user.bean;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseContract<M extends BaseContract<M>> extends Model<M> implements IBean {

    public M setId(java.lang.Long id) {
        set("id", id);
        return (M) this;
    }

    public java.lang.Long getId() {
        return getLong("id");
    }

    public M setUserId(java.lang.String userId) {
        set("user_id", userId);
        return (M) this;
    }

    public java.lang.String getUserId() {
        return getStr("user_id");
    }

    public M setTitle(java.lang.String title) {
        set("title", title);
        return (M) this;
    }

    public java.lang.String getTitle() {
        return getStr("title");
    }

    public M setPdf(java.lang.String pdf) {
        set("pdf", pdf);
        return (M) this;
    }

    public java.lang.String getPdf() {
        return getStr("pdf");
    }

    public M setFirst(java.lang.String first) {
        set("first", first);
        return (M) this;
    }

    public java.lang.String getFirst() {
        return getStr("first");
    }

    public M setSecond(java.lang.String second) {
        set("second", second);
        return (M) this;
    }

    public java.lang.String getSecond() {
        return getStr("second");
    }

    public M setDate(java.util.Date date) {
        set("date", date);
        return (M) this;
    }

    public java.util.Date getDate() {
        return get("date");
    }

    public M setStartDate(java.util.Date startDate) {
        set("start_date", startDate);
        return (M) this;
    }

    public java.util.Date getStartDate() {
        return get("start_date");
    }

    public M setEndDate(java.util.Date endDate) {
        set("end_date", endDate);
        return (M) this;
    }

    public java.util.Date getEndDate() {
        return get("end_date");
    }

}
