package com.unism.web.util;

import java.util.List;

public class JsonBean<T>

{

	private int total;

	private List<T> rows;

	public int getTotal()

	{

		return total;

	}

	public void setTotal(int total)

	{

		this.total = total;

	}

	public List<T> getRows()

	{

		return rows;

	}

	public void setRows(List<T> rows)

	{

		this.rows = rows;

	}

	/**
	 * 
	 * 计算当前页开始记录
	 * 
	 * 
	 * 
	 * @param pageSize
	 * 
	 *            每页记录数
	 * 
	 * @param currentPage
	 * 
	 *            当前第几页
	 * 
	 * @return 当前页开始记录号
	 */

	public static int countOffset(final int pageSize, final int currentPage)

	{

		final int offset = currentPage == 0 ? 0 : pageSize * (currentPage - 1);

		return offset;
		
	}
}
