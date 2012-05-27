package com.unism.web.formbean.base;

import java.util.List;


public class BaseForm {

	
	/**
	 * 当前页
	 */
	private int page;

	/**
	 * 
	 */
	private int total;
	private List<?> rows;

	public int getPage() {
		return page < 1 ? 1 : page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	
}
