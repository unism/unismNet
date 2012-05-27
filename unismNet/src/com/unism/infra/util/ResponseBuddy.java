package com.unism.infra.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.log4j.Logger;

/**
 * @Title: ResponseBuddy.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:22:14
 * @version CMS V1.0 
 */
public class ResponseBuddy {
	/**
	 * 
	 */
	private static final String DEFAULT_CHARSET_XML = "UTF-8";
	/**
	 * 
	 */
	private static final String DEFAULT_CHARSET_HTML = "GBK";
	/**
	 * 
	 */
	static final transient Logger mLogger = Logger
			.getLogger(ResponseBuddy.class);

	/**
	 * 
	 */
	static final transient Pattern PATTERM_CONTENT_TYPE = Pattern
			.compile("(?i).*charset\\s*=\\s*([^\\s]+)");

	/**
	 * 
	 */
	static final transient Pattern PATTHERN_HTML_CONTENT_TYPE = Pattern
			.compile("(?i)<\\s*meta\\s*http-equiv=\\s*[\"]?\\s*content-type\\s*[\"]?\\s*content=\\s*[\"]?\\s*text/html;\\s*charset\\s*=\\s*([^\"\\s]+)[\"]?\\s*>");

	/**
	 * 
	 */
	static final transient Pattern PATTHERN_XML_CONTENT_TYPE = Pattern
			.compile("(?i)<\\?xml.*encoding\\s*=\\s*\"\\s*([^\\\"\\\\s]+)\\s*\"\\s*\\?>");

	/**
	 * 
	 */
	int m_nStatusCode = 0;

	/**
	 * 
	 */
	byte[] m_barrBody = null;

	/**
	 * 
	 */
	String m_sContentType = null;

	/**
	 * 
	 */
	String m_sCharset = null;

	/**
	 * 
	 */
	Map m_mpHeaders = null;

	/**
	 * @param oHttpMethod
	 * @throws IOException
	 */
	public ResponseBuddy(HttpMethod oHttpMethod) throws IOException {
		setHeaders(oHttpMethod.getResponseHeaders());
		setStatusCode(oHttpMethod.getStatusCode());
		setBody(oHttpMethod.getResponseBody());
		this.m_sContentType = getHeader("Content-Type");
	}

	/**
	 * 
	 */
	public void init() {
	}

	/**
	 * @return
	 */
	public InputStream getBodyAsStream() {
		if ((getStatusCode() > 400) || (this.m_barrBody == null)) {
			return null;
		}
		return new ByteArrayInputStream(this.m_barrBody);
	}

	/**
	 * @return
	 */
	public String getBodyAsString() {
		if (getStatusCode() > 400) {
			return null;
		}
		guessCharset();
		try {
			return new String(this.m_barrBody, this.m_sCharset);
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 */
	private void guessCharset() {
		if (this.m_sCharset != null) {
			return;
		}
		if (getStatusCode() > 400) {
			this.m_sCharset = "GBK";
			return;
		}
		if (this.m_sContentType == null) {
			setContentType("text/HTML");
		}

		Matcher matchCharset = PATTERM_CONTENT_TYPE
				.matcher(this.m_sContentType);
		if (matchCharset.find()) {
			this.m_sCharset = matchCharset.group(1);
			mLogger.debug("charset in Content-Type:" + this.m_sCharset);
		}

		if (this.m_sCharset != null) {
			this.m_sCharset = this.m_sCharset.toUpperCase();
		} else if ((this.m_sContentType.trim().startsWith("text"))
				|| (this.m_sContentType.trim().startsWith("application/xml"))) {
			String contentValue = new String(this.m_barrBody);
			if (this.m_sContentType.trim().startsWith("text/html")) {
				matchCharset = PATTHERN_HTML_CONTENT_TYPE.matcher(contentValue);
				if (matchCharset.find()) {
					this.m_sCharset = matchCharset.group(1);
					mLogger.debug("charset in HTML META Content-Type:"
							+ this.m_sCharset);
				}
				if (this.m_sCharset == null) {
					this.m_sCharset = "GBK";
				}
				this.m_sCharset = this.m_sCharset.toUpperCase();
			} else if ((this.m_sContentType.trim()
					.startsWith("application/xml"))
					|| (this.m_sContentType.trim().startsWith("text/xml"))) {
				matchCharset = PATTHERN_XML_CONTENT_TYPE.matcher(contentValue);
				if (matchCharset.find()) {
					this.m_sCharset = matchCharset.group(1);
					mLogger.debug("charset in XML encoding:" + this.m_sCharset);
				}
				if (this.m_sCharset == null) {
					this.m_sCharset = "UTF-8";
				}
				this.m_sCharset = this.m_sCharset.toUpperCase();
			} else {
				this.m_sCharset = "GBK";
			}
			if (this.m_sCharset.equalsIgnoreCase("GB2312"))
				this.m_sCharset = "GBK";
		} else {
			this.m_sCharset = "";
		}
	}

	/**
	 * @param pContent
	 */
	public void setBody(byte[] pContent) {
		this.m_barrBody = pContent;
	}

	/**
	 * @return
	 */
	public int getContentLength() {
		return Integer.parseInt(getHeader("Content-Length"));
	}

	/**
	 * @return
	 */
	public String getContentType() {
		return this.m_sContentType;
	}

	/**
	 * @param pContentType
	 */
	public void setContentType(String pContentType) {
		this.m_sContentType = pContentType;
	}

	/**
	 * @return
	 */
	public int getStatusCode() {
		return this.m_nStatusCode;
	}

	/**
	 * @param pResponseCode
	 */
	public void setStatusCode(int pResponseCode) {
		this.m_nStatusCode = pResponseCode;
	}

	/**
	 * @return
	 */
	public String getCharset() {
		guessCharset();
		return this.m_sCharset;
	}

	/**
	 * @return
	 */
	public Map getHeaders() {
		return this.m_mpHeaders;
	}

	/**
	 * @param headers
	 */
	public void setHeaders(Header[] headers) {
		this.m_mpHeaders = new HashMap();
		for (int i = 0; i < headers.length; i++) {
			Header oHeader = headers[i];
			String sHeaderName = oHeader.getName();
			String sHeaderValue = oHeader.getValue();
			List lstHeaders = (List) this.m_mpHeaders.get(sHeaderName);
			if (lstHeaders == null) {
				lstHeaders = new ArrayList();
				this.m_mpHeaders.put(sHeaderName, lstHeaders);
			}
			lstHeaders.add(sHeaderValue);
		}
	}

	/**
	 * @param _sName
	 * @return
	 */
	public String getHeader(String _sName) {
		List oHeader = (List) this.m_mpHeaders.get(_sName);
		if ((oHeader != null) && (!oHeader.isEmpty())) {
			return (String) oHeader.get(0);
		}
		return null;
	}
}