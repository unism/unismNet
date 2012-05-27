package com.unism.web.formbean.product;

import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.unism.web.formbean.base.BaseForm;
import com.unism.web.util.PageView;

public class ProductTypeVO extends BaseForm {
	/**
	 * 返回的json对象
	 */
	private JSONObject result;// 返回的json
	
	private JSONArray json;
	
	private JSONObject data;

	private String typeid;
	private String name;
	private String note;
	private Integer parentid ;

	
	
	public ProductTypeVO() {
	}

	
	public ProductTypeVO(String name, String note) {
		this.name = name;
		this.note = note;
	}


	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public JSONArray getJson() {
		return json;
	}

	public void setJson(JSONArray json) {
		this.json = json;
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
	
	

	@JSON(serialize = false)
	public String getTypeid() {
		return typeid;
	}
	
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public JSONObject getResult() {
		return result;
	}

	public void setResult(JSONObject result) {
		this.result = result;
	}

	@Override
	public int getPage() {
		return super.getPage();
	}

	@Override
	public void setPage(int page) {
		super.setPage(page);
	}

	@Override
	public List<?> getRows() {
		return super.getRows();
	}

	@Override
	public void setRows(List<?> rows) {
		super.setRows(rows);
	}

	@Override
	public int getTotal() {
		return super.getTotal();
	}

	@Override
	public void setTotal(int total) {
		super.setTotal(total);
	}

}
