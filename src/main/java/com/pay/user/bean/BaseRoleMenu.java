package com.pay.user.bean;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseRoleMenu<M extends BaseRoleMenu<M>> extends Model<M> implements IBean {

    public M setRoleId(java.lang.Long roleId) {
        set("role_id", roleId);
        return (M) this;
    }

    public java.lang.Long getRoleId() {
        return getLong("role_id");
    }

    public M setMid(java.lang.Long mid) {
        set("mid", mid);
        return (M) this;
    }

    public java.lang.Long getMid() {
        return getLong("mid");
    }

}
