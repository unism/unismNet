package com.unism.infra.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Reports {
	/**
	 * 
	 */
	private List m_failedReport = new ArrayList();

	/**
	 * 
	 */
	private List m_warnedReport = new ArrayList();

	/**
	 * 
	 */
	private List m_sucessedReport = new ArrayList();
	/**
	 * 
	 */
	private int[] m_ids;
	/**
	 * 
	 */
	private String m_sTitle;
	/**
	 * 
	 */
	private ArrayList m_arExtraData;

	/**
	 * @return
	 */
	public ArrayList getExtraData() {
		return this.m_arExtraData;
	}

	/**
	 * @param arEextraData
	 */
	public void setExtraData(ArrayList arEextraData) {
		this.m_arExtraData = arEextraData;
	}

	/**
	 * @param _sTitle
	 */
	public Reports(String _sTitle) {
		this.m_sTitle = _sTitle;
	}

	/**
	 * @param _reports
	 */
	public void add(Reports _reports) {
		this.m_failedReport.addAll(_reports.m_failedReport);
		this.m_warnedReport.addAll(_reports.m_warnedReport);
		this.m_sucessedReport.addAll(_reports.m_sucessedReport);
	}

	/**
	 * @param _sMessage
	 * @param _cause
	 * @return
	 */
	public Report addFailedReport(String _sMessage, Throwable _cause) {
		Report report = createReport(_sMessage, _cause);
		report.setType(5);
		this.m_failedReport.add(report);
		return report;
	}

	/**
	 * @param _sMessage
	 * @param _cause
	 * @return
	 */
	public Report addWarnedReport(String _sMessage, Throwable _cause) {
		Report report = createReport(_sMessage, _cause);
		report.setType(4);
		this.m_warnedReport.add(report);
		return report;
	}

	/**
	 * @param _sMessage
	 */
	public void addSucessedReport(String _sMessage) {
		this.m_sucessedReport.add(createReport(_sMessage, null));
	}

	/**
	 * @return
	 */
	public List getFailedReporter() {
		return this.m_failedReport;
	}

	/**
	 * @param _nIndex
	 * @return
	 */
	public Report getFailedReporterAt(int _nIndex) {
		if (this.m_failedReport.size() <= _nIndex) {
			return null;
		}
		return (Report) this.m_failedReport.get(_nIndex);
	}

	/**
	 * @return
	 */
	public List getWarnedReporter() {
		return this.m_warnedReport;
	}

	/**
	 * @param _nIndex
	 * @return
	 */
	public Report getWarnedReporterAt(int _nIndex) {
		return (Report) this.m_warnedReport.get(_nIndex);
	}

	/**
	 * @return
	 */
	public List getSucessedReporter() {
		return this.m_sucessedReport;
	}

	/**
	 * @param _nIndex
	 * @return
	 */
	public Report getSucessedReporterAt(int _nIndex) {
		if (this.m_sucessedReport.size() <= _nIndex) {
			return null;
		}
		return (Report) this.m_sucessedReport.get(_nIndex);
	}

	/**
	 * @return
	 */
	public int getReportsNum() {
		return this.m_failedReport.size() + this.m_sucessedReport.size();
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return this.m_sTitle;
	}

	/**
	 * @param _sTitle
	 */
	public void setTitle(String _sTitle) {
		this.m_sTitle = _sTitle;
	}

	/**
	 * @param _sMsg
	 * @param _throwable
	 * @return
	 */
	private Report createReport(String _sMsg, Throwable _throwable) {
		return new Report(_sMsg, _throwable);
	}

	/**
	 * @param _reports
	 */
	public void merge(Reports _reports) {
		List sucessed = _reports.getSucessedReporter();
		this.m_sucessedReport.addAll(sucessed);

		List failed = _reports.getFailedReporter();
		this.m_failedReport.addAll(failed);

		List warned = _reports.getWarnedReporter();
		this.m_warnedReport.addAll(warned);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String sResult = "[" + this.m_sTitle + "] Reports:";
		int nCount = this.m_sucessedReport.size();
		for (int i = 0; i < nCount; i++) {
			sResult = sResult + "\n" + getSucessedReporterAt(i);
		}

		nCount = this.m_warnedReport.size();
		for (int i = 0; i < nCount; i++) {
			sResult = sResult + "\n" + getWarnedReporterAt(i);
		}

		nCount = this.m_failedReport.size();
		for (int i = 0; i < nCount; i++) {
			sResult = sResult + "\n" + getFailedReporterAt(i);
		}
		return sResult;
	}

	/**
	 * @return
	 */
	public String toXML() {
		return toXML(false);
	}

	/**
	 * @param _bReturnJSONXML
	 * @return
	 */
	public String toXML(boolean _bReturnJSONXML) {
		Element root = DocumentHelper.createElement("REPORTS");

		root.addElement("IS" + (_bReturnJSONXML ? "_" : "-") + "SUCCESS")
				.addText(this.m_failedReport.size() <= 0 ? "true" : "false");

		root.addElement("TITLE").addCDATA(this.m_sTitle);

		if ((getResultIds() != null) && (getResultIds().length > 0)) {
			Element element = root.addElement("ObjectIds");
			for (int i = 0; i < getResultIds().length; i++) {
				element.addElement("ObjectId").addText(
						String.valueOf(getResultIds()[i]));
			}
		}

		int nCount = this.m_sucessedReport.size();
		for (int i = 0; i < nCount; i++) {
			root.add(getSucessedReporterAt(i).toXMLElement(_bReturnJSONXML));
		}

		nCount = this.m_warnedReport.size();
		for (int i = 0; i < nCount; i++) {
			root.add(getWarnedReporterAt(i).toXMLElement(_bReturnJSONXML));
		}

		nCount = this.m_failedReport.size();
		for (int i = 0; i < nCount; i++) {
			root.add(getFailedReporterAt(i).toXMLElement(_bReturnJSONXML));
		}

		return root.asXML();
	}

	/**
	 * @return
	 */
	public String toJSONXML() {
		return toXML(true);
	}

	/**
	 * @param _bReturnJson
	 * @param _bMustHasSuccess
	 * @return
	 */
	public String asXml(boolean _bReturnJson, boolean _bMustHasSuccess) {
		Element root = DocumentHelper.createElement("REPORTS");

		boolean bSuccess = this.m_failedReport.isEmpty();
		if ((bSuccess) && (_bMustHasSuccess)) {
			bSuccess = !this.m_sucessedReport.isEmpty();
		}
		root.addElement("IS" + (_bReturnJson ? "_" : "-") + "SUCCESS").addText(
				bSuccess ? "true" : "false");

		root.addElement("TITLE").addCDATA(this.m_sTitle);

		if ((getResultIds() != null) && (getResultIds().length > 0)) {
			Element element = root.addElement("ObjectIds");
			for (int i = 0; i < getResultIds().length; i++) {
				element.addElement("ObjectId").addText(
						String.valueOf(getResultIds()[i]));
			}
		}

		int nCount = this.m_sucessedReport.size();
		for (int i = 0; i < nCount; i++) {
			root.add(getSucessedReporterAt(i).toXMLElement(_bReturnJson));
		}

		nCount = this.m_warnedReport.size();
		for (int i = 0; i < nCount; i++) {
			root.add(getWarnedReporterAt(i).toXMLElement(_bReturnJson));
		}

		nCount = this.m_failedReport.size();
		for (int i = 0; i < nCount; i++) {
			root.add(getFailedReporterAt(i).toXMLElement(_bReturnJson));
		}

		return root.asXML();
	}

	/**
	 * @param report
	 */
	public void addReport(Report report) {
		switch (report.getType()) {
		case 3:
			this.m_sucessedReport.add(report);
			break;
		case 5:
			this.m_failedReport.add(report);
			break;
		case 4:
			this.m_warnedReport.add(report);
			break;
		}
	}

	/**
	 * @param _ids
	 */
	public void setResultIds(int[] _ids) {
		this.m_ids = filterZero(_ids);
	}

	/**
	 * @param _ids
	 * @return
	 */
	private int[] filterZero(int[] _ids) {
		int nZeroCount = 0;
		for (int i = 0; i < _ids.length; i++) {
			if (_ids[i] == 0) {
				nZeroCount++;
			}
		}

		int[] nonZeroIds = new int[_ids.length - nZeroCount];
		Arrays.sort(_ids);
		for (int i = 0; i < nonZeroIds.length; i++) {
			nonZeroIds[i] = _ids[(i + nZeroCount)];
		}

		return nonZeroIds;
	}

	/**
	 * @return
	 */
	public int[] getResultIds() {
		return this.m_ids != null ? this.m_ids : new int[0];
	}
}