package com.unism.web.formbean.scene;

import java.util.List;

import com.unism.web.formbean.base.BaseForm;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class SceneVO extends BaseForm {

	/**
	 * 返回的json对象
	 */
	private JSONArray result;// 返回的json

	
	public JSONArray getResult() {
		return result;
	}

	public void setResult(JSONArray result) {
		this.result = result;
	}

	@Override
	public int getPage() {
		// TODO Auto-generated method stub
		return super.getPage();
	}

	@Override
	public void setPage(int page) {
		// TODO Auto-generated method stub
		super.setPage(page);
	}

	@Override
	public List<?> getRows() {
		// TODO Auto-generated method stub
		return super.getRows();
	}

	@Override
	public void setRows(List<?> rows) {
		// TODO Auto-generated method stub
		super.setRows(rows);
	}

	@Override
	public int getTotal() {
		// TODO Auto-generated method stub
		return super.getTotal();
	}

	@Override
	public void setTotal(int total) {
		// TODO Auto-generated method stub
		super.setTotal(total);
	}

	
}
