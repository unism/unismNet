package com.unism.infra.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.ByteArrayRequestEntity;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.log4j.Logger;

public class HttpClientBuddy {
	/**
	 * 
	 */
	public static final String DEFAULT_CONTENT_ENCODING = "UTF-8";
	/**
	 * 
	 */
	public static final String SERVICE_REQEUST_ENCODING = "ISO-8859-1";
	/**
	 * 
	 */
	protected static Logger logger = Logger.getLogger(HttpClientBuddy.class);

	/**
	 * 
	 */ 
	private HttpClient m_oHttpClient = null;

	/**
	 * 
	 */
	private String m_sEncoding = "";

	/**
	 * 
	 */
	private String[][] m_arrRequestHeaders = null;

	/**
	 * 
	 */
	public HttpClientBuddy() {
		this("UTF-8");
	}

	/**
	 * @param encoding
	 */
	public HttpClientBuddy(String encoding) {
		this.m_sEncoding = encoding;
		this.m_oHttpClient = new HttpClient();
	}

	/**
	 * @param encoding
	 * @param _arrRequestHeaders
	 */
	public HttpClientBuddy(String encoding, String[][] _arrRequestHeaders) {
		this.m_sEncoding = encoding;
		this.m_oHttpClient = new HttpClient();
		this.m_arrRequestHeaders = _arrRequestHeaders;
	}

	/**
	 * @param _sPostUri
	 * @param sContent
	 * @return
	 * @throws CMyException
	 */
	public ResponseBuddy doPost(String _sPostUri, String sContent)
			throws CMyException {
		try {
			return doPost(_sPostUri, sContent.getBytes(this.m_sEncoding));
		} catch (Exception e) {
			throw new CMyException("发送请求时出现异常", e);
		}

	}

	/**
	 * @param _sPostUri
	 * @param _fileContent
	 * @return
	 * @throws CMyException
	 */
	public ResponseBuddy updateFile(String _sPostUri, byte[] _fileContent)
			throws CMyException {
		validUrl(_sPostUri);

		PostMethod post = new PostMethod(_sPostUri);
		prepare(post);
		post.setRequestHeader("Content-Type", "multipart/form-data");

		if (_fileContent != null) {
			ByteArrayRequestEntity entity = new ByteArrayRequestEntity(
					_fileContent);
			post.setContentChunked(true);
			post.setRequestEntity(entity);
		}

		return renderResponse(post);
	}

	/**
	 * @param _sPostUri
	 * @param _oUpdateFile
	 * @return
	 * @throws CMyException
	 */
	public ResponseBuddy updateFile(String _sPostUri, File _oUpdateFile)
			throws CMyException {
		validUrl(_sPostUri);
		PostMethod filePost = new PostMethod(_sPostUri);
		prepare(filePost);
		filePost.getParams().setBooleanParameter(
				"http.protocol.expect-continue", true);
		try {
			Part[] parts = { new FilePart(_oUpdateFile.getName(), _oUpdateFile) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			this.m_oHttpClient.getHttpConnectionManager().getParams()
					.setConnectionTimeout(5000);
		} catch (Exception e) {
			throw new CMyException("上传文件时，出现异常", e);
		}
		return renderResponse(filePost);
	}

	/**
	 * @param _sPostUri
	 * @param _btContent
	 * @return
	 * @throws CMyException
	 */
	public ResponseBuddy doPost(String _sPostUri, byte[] _btContent)
			throws CMyException {
		validUrl(_sPostUri);

		PostMethod post = new PostMethod(_sPostUri);
		prepare(post);

		if (_btContent != null) {
			ByteArrayRequestEntity entity = new ByteArrayRequestEntity(
					_btContent);
			post.setContentChunked(true);
			post.setRequestEntity(entity);
		}

		return renderResponse(post);
	}

	/**
	 * @param _sPostUri
	 * @param sContent
	 * @param _bChunked
	 * @return
	 * @throws CMyException
	 */
	public ResponseBuddy doPost(String _sPostUri, String sContent,
			boolean _bChunked) throws CMyException {
		try {
			return doPost(_sPostUri, sContent.getBytes(this.m_sEncoding),
					_bChunked);
		} catch (Exception e) {
			throw new CMyException("发送请求时出现异常", e);
		}

	}

	/**
	 * @param _sPostUri
	 * @param mContent
	 * @param _bChunked
	 * @return
	 * @throws CMyException
	 */
	public ResponseBuddy doPost(String _sPostUri, Map mContent,
			boolean _bChunked) throws CMyException {
		return doPost(_sPostUri, getContent(mContent), _bChunked);
	}

	/**
	 * @param mContent
	 * @return
	 * @throws CMyException
	 */
	private String getContent(Map mContent) throws CMyException {
		if (mContent == null) {
			return "";
		}
		StringBuffer sbContent = new StringBuffer(mContent.size() * 20);
		try {
			for (Iterator itr = mContent.entrySet().iterator(); itr.hasNext();) {
				Map.Entry entry = (Map.Entry) itr.next();
				sbContent.append(entry.getKey());
				sbContent.append("=");
				sbContent.append(URLEncoder.encode((String) entry.getValue(),
						"UTF-8"));
				sbContent.append("&");
			}
		} catch (Exception e) {
			throw new CMyException("构造发送内容时出现异常", e);
		}
		return sbContent.toString();
	}

	/**
	 * @param _sPostUri
	 * @param _btContent
	 * @param _bChunked
	 * @return
	 * @throws CMyException
	 */
	public ResponseBuddy doPost(String _sPostUri, byte[] _btContent,
			boolean _bChunked) throws CMyException {
		validUrl(_sPostUri);

		PostMethod post = new PostMethod(_sPostUri);
		prepare(post);

		if (_btContent != null) {
			ByteArrayRequestEntity entity = new ByteArrayRequestEntity(
					_btContent);
			post.setContentChunked(_bChunked);
			post.setRequestEntity(entity);
		}

		return renderResponse(post);
	}

	/**
	 * @param httpMethod
	 */
	private void prepare(HttpMethod httpMethod) {
		httpMethod
				.setRequestHeader("Content-Type",
						"application/x-www-form-urlencoded;charset="
								+ this.m_sEncoding);
		if (this.m_arrRequestHeaders != null)
			for (int i = 0; i < this.m_arrRequestHeaders.length; i++)
				httpMethod.setRequestHeader(this.m_arrRequestHeaders[i][0],
						this.m_arrRequestHeaders[i][1]);
	}

	/**
	 * @param httpMethod
	 * @return
	 * @throws CMyException
	 */
	private ResponseBuddy renderResponse(HttpMethod httpMethod)
			throws CMyException {
		String _sMethodUri = null;
		int httpResult = 0;
		ResponseBuddy localResponseBuddy;

		try {
			_sMethodUri = httpMethod.getURI().getURI();
			httpResult = this.m_oHttpClient.executeMethod(httpMethod);
			localResponseBuddy = new ResponseBuddy(httpMethod);
			return localResponseBuddy;
		} catch (Exception ex) {

			throw new CMyException("向目标地址[" + _sMethodUri
					+ "]提交数据时失败！(response编号[" + httpResult + "])", ex);
		} finally {
			httpMethod.releaseConnection();
		}

	}

	/**
	 * @param _sPostUri
	 * @param _sQueryString
	 * @return
	 * @throws CMyException
	 */
	public ResponseBuddy doGet(String _sPostUri, String _sQueryString)
			throws CMyException {
		validUrl(_sPostUri);

		GetMethod get = new GetMethod(_sPostUri);
		prepare(get);
		if (_sQueryString != null) {
			get.setQueryString(_sQueryString);
		}

		return renderResponse(get);
	}

	/**
	 * @param _sPostUri
	 * @param mContent
	 * @return
	 * @throws CMyException
	 */
	public ResponseBuddy doGet(String _sPostUri, Map mContent)
			throws CMyException {
		return doGet(_sPostUri, getContent(mContent));
	}

	/**
	 * @param _sPostUri
	 * @throws CMyException
	 */
	private void validUrl(String _sPostUri) throws CMyException {
		URL oUrl = null;
		try {
			oUrl = new URL(_sPostUri);
		} catch (MalformedURLException ex) {
			throw new CMyException("不是合法的HTTP请求的目标地址[" + _sPostUri + "]", ex);
		}

		if (oUrl.getProtocol().equalsIgnoreCase("https"))
			Protocol.registerProtocol("https", new Protocol("https",
					new MySSLSocketFactory(), 443));
	}
}