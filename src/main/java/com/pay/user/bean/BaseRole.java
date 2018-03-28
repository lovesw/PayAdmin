package com.pay.user.bean;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRole<M extends BaseRole<M>> extends Model<M> implements IBean {

    public M setId(java.lang.Long id) {
        set("id", id);
        return (M) this;
    }

    public java.lang.Long getId() {
        return getLong("id");
    }

    public M setName(java.lang.String name) {
        set("name", name);
        return (M) this;
    }

    public java.lang.String getName() {
        return getStr("name");
    }

    public M setContent(java.lang.String content) {
        set("content", content);
        return (M) this;
    }

    public java.lang.String getContent() {
        return getStr("content");
    }

    public M setDate(java.util.Date date) {
        set("date", date);
        return (M) this;
    }

    public java.util.Date getDate() {
        return get("date");
    }

}
