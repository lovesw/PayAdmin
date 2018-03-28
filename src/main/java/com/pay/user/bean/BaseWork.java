package com.pay.user.bean;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.activerecord.Model;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseWork<M extends BaseWork<M>> extends Model<M> implements IBean {

    public java.lang.String getId() {
        return getStr("id");
    }

    public M setId(java.lang.String id) {
        set("id", id);
        return (M) this;
    }

    public java.lang.String getUserId() {
        return getStr("user_id");
    }

    public M setUserId(java.lang.String userId) {
        set("user_id", userId);
        return (M) this;
    }

    public java.util.Date getStartDate() {
        return get("start_date");
    }

    public M setStartDate(java.util.Date startDate) {
        set("start_date", startDate);
        return (M) this;
    }

    public java.util.Date getEndDate() {
        return get("end_date");
    }

    public M setEndDate(java.util.Date endDate) {
        set("end_date", endDate);
        return (M) this;
    }

    public java.lang.String getContent() {
        return getStr("content");
    }

    public M setContent(java.lang.String content) {
        set("content", content);
        return (M) this;
    }

    public java.lang.String getProgress() {
        return getStr("progress");
    }

    public M setProgress(java.lang.String progress) {
        set("progress", progress);
        return (M) this;
    }

    public java.lang.String getRemark() {
        return getStr("remark");
    }

    public M setRemark(java.lang.String remark) {
        set("remark", remark);
        return (M) this;
    }

    public java.util.Date getDate() {
        return get("date");
    }

    public M setDate(java.util.Date date) {
        set("date", date);
        return (M) this;
    }

}
