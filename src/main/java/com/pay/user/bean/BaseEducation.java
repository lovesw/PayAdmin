package com.pay.user.bean;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseEducation<M extends BaseEducation<M>> extends Model<M> implements IBean {

	public M setId(java.lang.String id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.String getId() {
		return getStr("id");
	}

	public M setUserId(java.lang.String userId) {
		set("user_id", userId);
		return (M)this;
	}
	
	public java.lang.String getUserId() {
		return getStr("user_id");
	}

	public M setStartDate(java.util.Date startDate) {
		set("start_date", startDate);
		return (M)this;
	}
	
	public java.util.Date getStartDate() {
		return get("start_date");
	}

	public M setEndDate(java.util.Date endDate) {
		set("end_date", endDate);
		return (M)this;
	}
	
	public java.util.Date getEndDate() {
		return get("end_date");
	}

	public M setSchool(java.lang.String school) {
		set("school", school);
		return (M)this;
	}
	
	public java.lang.String getSchool() {
		return getStr("school");
	}

	public M setEdu(java.lang.String edu) {
		set("edu", edu);
		return (M)this;
	}
	
	public java.lang.String getEdu() {
		return getStr("edu");
	}

	public M setMajor(java.lang.String major) {
		set("major", major);
		return (M)this;
	}
	
	public java.lang.String getMajor() {
		return getStr("major");
	}

	public M setDate(java.util.Date date) {
		set("date", date);
		return (M)this;
	}
	
	public java.util.Date getDate() {
		return get("date");
	}

}
