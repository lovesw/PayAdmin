package com.pay.user.bean;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseEquipment<M extends BaseEquipment<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Long id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Long getId() {
		return getLong("id");
	}

	public M setUserId(java.lang.String userId) {
		set("user_id", userId);
		return (M)this;
	}
	
	public java.lang.String getUserId() {
		return getStr("user_id");
	}

	public M setName(java.lang.String name) {
		set("name", name);
		return (M)this;
	}
	
	public java.lang.String getName() {
		return getStr("name");
	}

	public M setVersion(java.lang.String version) {
		set("version", version);
		return (M)this;
	}
	
	public java.lang.String getVersion() {
		return getStr("version");
	}

	public M setNumber(java.lang.String number) {
		set("number", number);
		return (M)this;
	}
	
	public java.lang.String getNumber() {
		return getStr("number");
	}

	public M setPrice(java.math.BigDecimal price) {
		set("price", price);
		return (M)this;
	}
	
	public java.math.BigDecimal getPrice() {
		return get("price");
	}

	public M setNum(java.lang.Integer num) {
		set("num", num);
		return (M)this;
	}
	
	public java.lang.Integer getNum() {
		return getInt("num");
	}

	public M setStatus(java.lang.Long status) {
		set("status", status);
		return (M)this;
	}
	
	public java.lang.Long getStatus() {
		return getLong("status");
	}

	public M setDate(java.util.Date date) {
		set("date", date);
		return (M)this;
	}
	
	public java.util.Date getDate() {
		return get("date");
	}

}
