package com.unism.infra.util;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public final class Report {
	/**
	 * 
	 */
	public static final int TYPE_ERROR = 5;
	/**
	 * 
	 */
	public static final int TYPE_WARN = 4;
	/**
	 * 
	 */
	public static final int TYPE_INFO = 3;
	/**
	 * 
	 */
	public static final int TYPE_DEBUG = 2;
	/**
	 * 
	 */
	private int m_nType = 3;
	/**
	 *  
	 */
	private String m_sRportTitle;
	/**
	 * 
	 */
	private String m_sRportDetail;
	/**
	 * 
	 */
	private Object m_oRelateObject = null;

	/**
	 * @param _sReportTitle
	 * @param _throwable
	 */
	public Report(String _sReportTitle, Throwable _throwable) {
		this.m_sRportTitle = _sReportTitle;

		if (_throwable != null) {
			setType(5);
			this.m_sRportDetail = CMyException.getStackTraceText(_throwable);
		}
	}

	/**
	 * @param _sReportTitle
	 */
	public Report(String _sReportTitle) {
		this.m_sRportTitle = _sReportTitle;
	}

	/**
	 * @return
	 */
	public String getRportDetail() {
		return this.m_sRportDetail;
	}

	/**
	 * @return
	 */
	public String getRportTitle() {
		return this.m_sRportTitle;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[" + this.m_sRportTitle + "] \n Detail："
				+ CMyString.showNull(this.m_sRportDetail);
	}

	/**
	 * @param _bReturnJSONXML
	 * @return
	 */
	public Element toXMLElement(boolean _bReturnJSONXML) {
		Element root = DocumentHelper.createElement("REPORT");

		root.addElement("IS" + (_bReturnJSONXML ? "_" : "-") + "SUCCESS")
				.addText(getType() != 5 ? "true" : "false");

		if (CMyString.containsCDATAStr(this.m_sRportTitle))
			root.addElement("TITLE").addText(this.m_sRportTitle);
		else {
			root.addElement("TITLE").addCDATA(this.m_sRportTitle);
		}

		root.addElement("TYPE").addText(String.valueOf(getType()));

		if (this.m_sRportDetail != null) {
			if (CMyString.containsCDATAStr(this.m_sRportDetail))
				root.addElement(
						"ERROR" + (_bReturnJSONXML ? "_" : "-") + "INFO")
						.addText(this.m_sRportDetail);
			else {
				root.addElement(
						"ERROR" + (_bReturnJSONXML ? "_" : "-") + "INFO")
						.addCDATA(this.m_sRportDetail);
			}
		}

		return root;
	}

	/**
	 * @return
	 */
	public String toXML() {
		return toXMLElement(false).asXML();
	}

	/**
	 * @return
	 */
	public String toJSONXML() {
		return toXMLElement(true).asXML();
	}

	/**
	 * @return
	 */
	public int getType() {
		return this.m_nType;
	}

	/**
	 * @param type
	 */
	public void setType(int type) {
		this.m_nType = type;
	}

	/**
	 * @param rportDetail
	 */
	public void setRportDetail(String rportDetail) {
		this.m_sRportDetail = rportDetail;
	}

	/**
	 * @param rportTitle
	 */
	public void setRportTitle(String rportTitle) {
		this.m_sRportTitle = rportTitle;
	}

	/**
	 * @return
	 */
	public Object getRelateObject() {
		return this.m_oRelateObject;
	}

	/**
	 * @param relateObject
	 */
	public void setRelateObject(Object relateObject) {
		this.m_oRelateObject = relateObject;
	}
}