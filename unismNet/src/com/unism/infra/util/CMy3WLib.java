package com.unism.infra.util;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import sun.misc.BASE64Encoder;

/**
 * @Title: CMy3WLib.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:09:38
 * @version CMS V1.0 
 */
public class CMy3WLib {
	/**
	 * 
	 */
	private static Map s_hmProxyInfo = new HashMap(4);

	/**
	 * @param _sKey
	 * @param _sValue
	 */
	public static void setProxyInfo(String _sKey, String _sValue) {
		s_hmProxyInfo.put(_sKey.toUpperCase(), _sValue);
	}

	/**
	 * @param p_urlSrcFile
	 * @return
	 * @throws CMyException
	 */
	public static boolean getFile(URL p_urlSrcFile) throws CMyException {
		return getFile(p_urlSrcFile, null);
	}

	/**
	 * @param paramURL
	 * @param paramString
	 * @return
	 * @throws CMyException
	 */
	public static boolean getFile(URL paramURL, String paramString)
			throws CMyException {
		InputStream localInputStream = null;
		try {
			if (paramURL.getProtocol().equalsIgnoreCase("HTTPS"))
				localInputStream = openSSLURL(paramURL);
			else
				localInputStream = paramURL.openStream();
		} catch (Exception localException1) {
			if (localInputStream != null)
				try {
					localInputStream.close();
				} catch (Exception localException2) {
					throw new CMyException(111, "获取源文件失败(CMy3WLib.getFile)",
							localException1);
				}

		}

		DataInputStream localDataInputStream = null;
		FileOutputStream localFileOutputStream = null;
		byte[] arrayOfByte = null;
		try {
			localDataInputStream = new DataInputStream(localInputStream);

			if (paramString != null) {
				localFileOutputStream = new FileOutputStream(paramString);
			}

			arrayOfByte = new byte[2048];
			int i;
			while ((i = localDataInputStream.read(arrayOfByte, 0,
					arrayOfByte.length)) != -1)
				if (localFileOutputStream != null)
					localFileOutputStream.write(arrayOfByte, 0, i);
		} catch (Exception localException3) {
			throw new CMyException(50, "保存文件失败(CMy3WLib.getFile)",
					localException3);
		} finally {
			if (localInputStream != null)
				try {
					localInputStream.close();
				} catch (Exception localException4) {
				}
			if (localDataInputStream != null)
				try {
					localDataInputStream.close();
				} catch (Exception localException5) {
				}
			if (localFileOutputStream != null)
				try {
					localFileOutputStream.close();
				} catch (Exception localException6) {
				}
		}
		return true;
	}

	/**
	 * @param p_urlSrcFile
	 * @return
	 */
	private static InputStream openSSLURL(URL p_urlSrcFile) {
		try {
			URLConnection urlConnection = p_urlSrcFile.openConnection();

			TrustManager[] tm = { new MyX509TrustManager(null) };
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, tm, new SecureRandom());
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			HttpsURLConnection sslConnection = (HttpsURLConnection) urlConnection;
			sslConnection.setSSLSocketFactory(ssf);
			return urlConnection.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * @param paramString
	 * @return
	 * @throws Exception
	 */
	public static String getURLContent(String paramString) throws Exception {
		URL localURL = getURL(paramString);
		InputStream localInputStream = null;
		DataInputStream localDataInputStream = null;
		Object localObject1 = null;
		byte[] arrayOfByte = null;

		StringBuffer localStringBuffer = new StringBuffer();
		try {
			localInputStream = localURL.openStream();
		} catch (Exception localException1) {
			if (localInputStream != null)
				try {
					localInputStream.close();
				} catch (Exception localException3) {
				}
			throw new CMyException(111, "获取源文件失败(CMy3WLib.getFile)",
					localException1);
		}

		try {
			localDataInputStream = new DataInputStream(localInputStream);

			arrayOfByte = new byte[2048];
			int i;
			while ((i = localDataInputStream.read(arrayOfByte, 0,
					arrayOfByte.length)) != -1)
				localStringBuffer.append(new String(arrayOfByte, 0, i));
		} catch (Exception localException2) {
			throw new CMyException(50, "保存文件失败(CMy3WLib.getFile)",
					localException2);
		} finally {
			if (localInputStream != null)
				try {
					localInputStream.close();
				} catch (Exception localException4) {
				}
			if (localDataInputStream != null)
				try {
					localDataInputStream.close();
				} catch (Exception localException5) {
				}
			/*
			 * if (localObject1 != null) try { localObject1.close(); } catch
			 * (Exception localException6) { }
			 */
		}
		return localStringBuffer.toString();
	}

	/**
	 * @param p_sSrcFileUrl
	 * @return
	 * @throws CMyException
	 */
	public static boolean getFile(String p_sSrcFileUrl) throws CMyException {
		return getFile(p_sSrcFileUrl, null);
	}

	/**
	 * @param p_sSrcFileUrl
	 * @return
	 * @throws Exception
	 */
	public static URL getURL(String p_sSrcFileUrl) throws Exception {
		String sProxyHost = (String) s_hmProxyInfo.get("PROXY_HOST");
		if ((sProxyHost == null) || (sProxyHost.length() <= 0)) {
			return new URL(p_sSrcFileUrl);
		}
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("proxyHost", sProxyHost);
		System.getProperties()
				.put("proxyPort", s_hmProxyInfo.get("PROXY_PORT"));

		URL newURL = new URL(p_sSrcFileUrl);

		String sProxyUser = (String) s_hmProxyInfo.get("PROXY_USER");

		if ((sProxyUser == null) || (sProxyUser.length() <= 0)) {
			return newURL;
		}
		String authString = sProxyUser + ":"
				+ s_hmProxyInfo.get("PROXY_PASSWORD");
		String auth = "Basic "
				+ new BASE64Encoder().encode(authString.getBytes());
		URLConnection conn = newURL.openConnection();
		conn.setRequestProperty("Proxy-Authorization", auth);
		return newURL;
	}

	/**
	 * @param p_sSrcFileUrl
	 * @param p_sDstPathFileName
	 * @return
	 * @throws CMyException
	 */
	public static boolean getFile(String p_sSrcFileUrl,
			String p_sDstPathFileName) throws CMyException {
		URL urlSrcFile = null;
		try {
			urlSrcFile = getURL(p_sSrcFileUrl);
		} catch (Exception ex) {
			throw new CMyException(110, "无效的源文件地址(CMy3WLib.getFile)", ex);
		}

		return getFile(urlSrcFile, p_sDstPathFileName);
	}

	public static boolean getFile(String p_sProtocol, String p_sHost,
			String p_sFile, String p_sDstPathFileName) throws CMyException {
		return getFile(p_sProtocol, p_sHost, -1, p_sFile, p_sDstPathFileName);
	}

	/**
	 * @param p_sProtocol
	 * @param p_sHost
	 * @param p_nPort
	 * @param p_sFile
	 * @param p_sDstPathFileName
	 * @return
	 * @throws CMyException
	 */
	public static boolean getFile(String p_sProtocol, String p_sHost,
			int p_nPort, String p_sFile, String p_sDstPathFileName)
			throws CMyException {
		URL urlSrcFile = null;
		try {
			urlSrcFile = new URL(p_sProtocol, p_sHost, p_nPort, p_sFile);
		} catch (MalformedURLException ex) {
			throw new CMyException(110, "无效的源文件地址(CMy3WLib.getFile)", ex);
		}

		return getFile(urlSrcFile, p_sDstPathFileName);
	}

	/**
	 * @param sURL
	 * @param mContent
	 * @return
	 * @throws CMyException
	 */
	public static ResponseBuddy doGet(String sURL, Map mContent)
			throws CMyException {
		HttpClientBuddy hcb = new HttpClientBuddy();
		return hcb.doGet(sURL, mContent);
	}

	/**
	 * @param sURL
	 * @param sContent
	 * @return
	 * @throws CMyException
	 */
	public static ResponseBuddy doGet(String sURL, String sContent)
			throws CMyException {
		HttpClientBuddy hcb = new HttpClientBuddy();
		return hcb.doGet(sURL, sContent);
	}

	/**
	 * @param sURL
	 * @param mContent
	 * @return
	 * @throws CMyException
	 */
	public static ResponseBuddy doPost(String sURL, Map mContent)
			throws CMyException {
		return doPost(sURL, mContent, "UTF-8");
	}

	/**
	 * @param sURL
	 * @param sContent
	 * @return
	 * @throws CMyException
	 */
	public static ResponseBuddy doPost(String sURL, String sContent)
			throws CMyException {
		return doPost(sURL, sContent, "UTF-8");
	}

	/**
	 * @param sURL
	 * @param mContent
	 * @param sEncoding
	 * @return
	 * @throws CMyException
	 */
	public static ResponseBuddy doPost(String sURL, Map mContent,
			String sEncoding) throws CMyException {
		HttpClientBuddy hcb = new HttpClientBuddy(sEncoding);
		return hcb.doPost(sURL, mContent, false);
	}

	/**
	 * @param sURL
	 * @param sContent
	 * @param sEncoding
	 * @return
	 * @throws CMyException
	 */
	public static ResponseBuddy doPost(String sURL, String sContent,
			String sEncoding) throws CMyException {
		HttpClientBuddy hcb = new HttpClientBuddy(sEncoding);
		return hcb.doPost(sURL, sContent.toString(), false);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String sUrl = "http://127.0.0.1:8070/cms/test.jsp";
			Map mContent = new HashMap();
			mContent.put("a", "中国");
			mContent.put("b", "english");
			doPost(sUrl, mContent);
			doPost(sUrl, "a=aaa&b=bbb");
		} catch (CMyException ex) {
			ex.printStackTrace(System.out);
		}
	}

	/**
	 * @Title: CMy3WLib.java
	 * @Package com.trs.infra.util
	 * @author dfreng
	 * @date 2011-7-13 上午12:53:33
	 * @version CMS V1.0 
	 */
	private static class MyX509TrustManager implements X509TrustManager {
		/**
		 * 
		 */
		private MyX509TrustManager() {
		}

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
		 */
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
		 */
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
		 */
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		/**
		 * @param paramMyX509TrustManager
		 */
		MyX509TrustManager(MyX509TrustManager paramMyX509TrustManager) {
			this();
		}
	}
}