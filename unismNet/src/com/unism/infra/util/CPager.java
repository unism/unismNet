package com.unism.infra.util;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CPager {
	/**
	 * 
	 */
	public static final int DEFAULT_PAGE_SIZE = 20;
	/**
	 * 
	 */
	private int m_nPageSize = 20;

	/**
	 *  
	 */
	private int m_nItemCount = 0;

	/**
	 * 
	 */
	private int PageCount = 0;

	/**
	 * 
	 */
	private int CurrentPageIndex = 1;

	/**
	 * 
	 */
	private int CurrentPageSize = 0;

	/**
	 * 
	 */
	private boolean m_blNeedRefresh = false;

	/**
	 * 
	 */
	private String m_sPageHrefPre = "";

	/**
	 * 
	 */
	public CPager() {
	}

	/**
	 * 
	 * @param p_nPageSize
	 */
	public CPager(int p_nPageSize) {
		this.m_nPageSize = p_nPageSize;

		if (this.m_nPageSize == 0)
			this.m_nPageSize = 20;
	}

	/**
	 * 
	 */
	private void mp_refreshData() {
		if (!this.m_blNeedRefresh)
			return;
		if (this.m_nPageSize == 0) {
			return;
		}
		if (this.m_nItemCount == 0) {
			this.PageCount = 0;
			this.CurrentPageSize = 0;
		} else {
			this.PageCount = (this.m_nItemCount / this.m_nPageSize);
			if (this.m_nItemCount > this.PageCount * this.m_nPageSize)
				this.PageCount += 1;
			if (this.CurrentPageIndex > this.PageCount)
				this.CurrentPageIndex = this.PageCount;
			if (this.CurrentPageIndex < this.PageCount)
				this.CurrentPageSize = this.m_nPageSize;
			else {
				this.CurrentPageSize = (this.m_nItemCount - (this.CurrentPageIndex - 1)
						* this.m_nPageSize);
			}
		}
		this.m_blNeedRefresh = false;
	}

	/**
	 * 
	 * @param p_nItemCount
	 */
	public void setItemCount(int p_nItemCount) {
		if (p_nItemCount < 0)
			this.m_nItemCount = 0;
		else {
			this.m_nItemCount = p_nItemCount;
		}
		if (!this.m_blNeedRefresh)
			this.m_blNeedRefresh = true;
	}

	/**
	 * 
	 * @param p_oStmt
	 * @param p_sCountSQL
	 * @throws CMyException
	 */
	public void setItemCount(Statement p_oStmt, String p_sCountSQL)
			throws CMyException {
		ResultSet rsCount = null;
		try {
			rsCount = p_oStmt.executeQuery(p_sCountSQL);
			if (rsCount.next())
				this.m_nItemCount = rsCount.getInt(1);
			else {
				this.m_nItemCount = 0;
			}
			rsCount.close();
			if (!this.m_blNeedRefresh)
				this.m_blNeedRefresh = true;
		} catch (SQLException ex) {
			throw new CMyException(40, "从数据库中获取记录总数时出现异常（CPager.setItemCount）",
					ex);
		} catch (Exception ex) {
			throw new CMyException(0, "从数据库中获取记录总数时出现异常（CPager.setItemCount）",
					ex);
		}
	}

	/**
	 * 
	 * @param p_rsData
	 * @throws CMyException
	 */
	public void moveToCurrentPageInRs(ResultSet p_rsData) throws CMyException {
		try {
			if (this.m_blNeedRefresh)
				mp_refreshData();
			for (int i = 1; i <= (this.CurrentPageIndex - 1) * this.m_nPageSize; i++)
				p_rsData.next();
		} catch (SQLException ex) {
			throw new CMyException(40,
					"在记录集中定位到指定页面时出现异常（CPager.moveToCurrentPageInRs）", ex);
		} catch (Exception ex) {
			throw new CMyException(0,
					"在记录集中定位到指定页面时出现异常（CPager.moveToCurrentPageInRs）", ex);
		}
	}

	/**
	 * 
	 * @return
	 */
	public boolean atFirstPage() {
		if (this.m_blNeedRefresh)
			mp_refreshData();
		return this.CurrentPageIndex == 1;
	}

	/**
	 * 
	 * @return
	 */
	public boolean atLastPage() {
		if (this.m_blNeedRefresh)
			mp_refreshData();
		return this.CurrentPageIndex == this.PageCount;
	}

	/**
	 * 
	 * @return
	 */
	public int getFirstItemIndex() {
		if (this.m_nPageSize < 0) {
			return 1;
		}

		if (this.m_blNeedRefresh)
			mp_refreshData();
		return (this.CurrentPageIndex - 1) * this.m_nPageSize + 1;
	}

	/**
	 * 
	 * @return
	 */
	public int getLastItemIndex() {
		if (this.m_nPageSize < 0) {
			return this.m_nItemCount;
		}
		if (this.m_blNeedRefresh) {
			mp_refreshData();
		}

		int nCurrentIndex = (this.CurrentPageIndex - 1) * this.m_nPageSize
				+ this.CurrentPageSize;
		if (nCurrentIndex > this.m_nItemCount) {
			return this.m_nItemCount;
		}
		return nCurrentIndex;
	}

	/**
	 * 
	 * @return
	 */
	public int getPrePageIndex() {
		if (this.m_blNeedRefresh)
			mp_refreshData();
		int nRet = this.CurrentPageIndex - 1;
		if (nRet < 1)
			nRet = 1;
		return nRet;
	}

	/**
	 * 
	 * @return
	 */
	public int getNextPageIndex() {
		if (this.m_blNeedRefresh)
			mp_refreshData();
		int nRet = this.CurrentPageIndex + 1;
		if (nRet > this.PageCount)
			nRet = this.PageCount;
		return nRet;
	}

	/**
	 * 
	 * @param p_sPageHrefPre
	 */
	public void setPageHrefPre(String p_sPageHrefPre) {
		this.m_sPageHrefPre = p_sPageHrefPre;
	}

	/**
	 * 
	 * @param p_PageIndex
	 * @return
	 */
	public String getPageHref(int p_PageIndex) {
		return this.m_sPageHrefPre + p_PageIndex;
	}

	/**
	 * 
	 * @return
	 */
	public int getPageSize() {
		return this.m_nPageSize;
	}

	/**
	 * 
	 * @param newPageSize
	 */
	public void setPageSize(int newPageSize) {
		this.m_nPageSize = newPageSize;
		if (!this.m_blNeedRefresh)
			this.m_blNeedRefresh = true;
	}

	/**
	 * 
	 * @return
	 */
	public int getItemCount() {
		return this.m_nItemCount;
	}

	/**
	 * 
	 * @return
	 */
	public int getPageCount() {
		if (this.m_blNeedRefresh)
			mp_refreshData();
		return this.PageCount;
	}

	/**
	 * 
	 * @param newCurrentPageIndex
	 */
	public void setCurrentPageIndex(int newCurrentPageIndex) {
		this.CurrentPageIndex = (newCurrentPageIndex <= 0 ? 1
				: newCurrentPageIndex);
		if (!this.m_blNeedRefresh)
			this.m_blNeedRefresh = true;
	}

	/**
	 * 
	 * @return
	 */
	public int getCurrentPageIndex() {
		return this.CurrentPageIndex;
	}

	/**
	 * 
	 * @return
	 */
	public int getCurrentPageSize() {
		if (this.m_blNeedRefresh)
			mp_refreshData();
		return this.CurrentPageSize;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CPager pager = new CPager(3);
		pager.setItemCount(10);
		pager.setCurrentPageIndex(1);
		System.out.println(pager.getFirstItemIndex() + ":"
				+ pager.getLastItemIndex());
	}
}