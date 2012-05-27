package com.unism.web.formbean.product;

import com.unism.web.formbean.base.BaseForm;

public class ProductTypeForm extends BaseForm {

	private Integer typeid;
	private Integer parentid;
	private String name;
	private String note;
	private String query;

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	// @JSON(serialize = false)
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
}
