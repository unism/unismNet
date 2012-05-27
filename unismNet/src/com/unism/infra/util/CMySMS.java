package com.unism.infra.util;

import java.io.PrintStream;

/**
 * @Title: CMySMS.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:35:04
 * @version CMS V1.0 
 */
public class CMySMS {
	/**
	 *  
	 */
	private static String SENDURL_PRE = null;

	/**
	 * 
	 */
	private static String PARAM_NAME_TO = "";

	/**
	 * 
	 */
	private static String PARAM_NAME_CONTENT = "";

	/**
	 * 
	 */
	public String sFrom = null;

	/**
	 * 
	 */
	public String sTo = null;

	/**
	 * 
	 */
	public String sContent = null;

	/**
	 * @param _urlPre
	 */
	public static void setSendUrlPre(String _urlPre) {
		SENDURL_PRE = _urlPre;
	}

	/**
	 * @param _paramNameTo
	 */
	public static void setParamNameTo(String _paramNameTo) {
		PARAM_NAME_TO = _paramNameTo;
	}

	/**
	 * @param _paramNameContent
	 */
	public static void setParamNameContent(String _paramNameContent) {
		PARAM_NAME_CONTENT = _paramNameContent;
	}

	/**
	 * @param _sMobile
	 * @return
	 */
	public static boolean isValidMobile(String _sMobile) {
		if (_sMobile == null) {
			return false;
		}

		_sMobile = _sMobile.trim();
		if (_sMobile.length() != 11) {
			return false;
		}

		String sKind = _sMobile.substring(0, 3);

		return (!sKind.equals("130")) && (!sKind.equals("133"));
	}

	/**
	 * @return
	 */
	private String getSendUrl() {
		String sUrl = SENDURL_PRE;
		if (PARAM_NAME_TO.trim().length() > 0) {
			sUrl = sUrl + "&" + PARAM_NAME_TO + "="
					+ CMyString.URLEncode(this.sTo);
			sUrl = sUrl + "&" + PARAM_NAME_CONTENT + "="
					+ CMyString.URLEncode(this.sContent);
		} else {
			sUrl = CMyString.replaceStr(sUrl, "$$MOBILE$$", this.sTo);
			sUrl = CMyString.replaceStr(sUrl, "$$CONTENT$$", this.sContent);
		}
		System.out.println(sUrl);
		return sUrl;
	}

	/**
	 * @return
	 */
	public String getFrom() {
		return this.sFrom;
	}

	/**
	 * @param _sFrom
	 */
	public void setFrom(String _sFrom) {
		this.sFrom = _sFrom;
	}

	/**
	 * @return
	 */
	public String getTo() {
		return this.sTo;
	}

	/**
	 * @param _sTo
	 * @throws CMyException
	 */
	public void setTo(String _sTo) throws CMyException {
		if (!isValidMobile(_sTo)) {
			throw new CMyException(10, "手机号码无效或不支持（CMySMS.setTo）");
		}
		this.sTo = _sTo;
	}

	/**
	 * @return
	 */
	public String getContent() {
		return this.sContent;
	}

	/**
	 * @param _sContent
	 * @throws CMyException
	 */
	public void setContent(String _sContent) throws CMyException {
		if ((_sContent == null) || (_sContent.trim().length() == 0)) {
			throw new CMyException(10, "短信息内容不能为空（CMySMS.setContent）");
		}
		this.sContent = _sContent;
	}

	/**
	 * @return
	 * @throws CMyException
	 */
	public boolean send() throws CMyException {
		if ((this.sTo == null) || (this.sContent == null)) {
			return false;
		}

		if (SENDURL_PRE == null) {
			throw new CMyException(10, "发送短信息的URL没有设定（CMySMS.send）");
		}

		try {
			if (this.sFrom != null) {
				this.sContent = (this.sContent + "-" + this.sFrom);
			}

			return CMy3WLib.getFile(getSendUrl());
		} catch (Exception ex) {
			throw new CMyException(1, "发送短信息时发生错误！", ex);
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CMySMS mySMS = new CMySMS();

		SENDURL_PRE = "http://gpdlm.easmart.net/s/index.php?message=myusername*mypassword*$$MOBILE$$*$$CONTENT$$";
		try {
			mySMS.setContent("你好,作个测试");
			mySMS.setFrom("TRS WCM 5.0");
			mySMS.setTo("13910839880");

			if (mySMS.send())
				System.out.println("Successful!");
			else {
				System.out.println("False!");
			}

		} catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}
}