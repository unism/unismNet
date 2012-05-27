package com.unism.infra.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

public class MySSLSocketFactory implements ProtocolSocketFactory {
	/**
	 * 
	 */
	private SSLContext sslcontext = null;

	/**
	 * @return
	 */
	private static SSLContext createEasySSLContext() {
		try {
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, new TrustManager[] { new MyX509TrustManager() },
					new SecureRandom());
			return context;
		} catch (Exception e) {
			throw new HttpClientError(e.toString());
		}

	}

	/**
	 * @return
	 */
	private SSLContext getSSLContext() {
		if (this.sslcontext == null)
			this.sslcontext = createEasySSLContext();
		return this.sslcontext;
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String, int, java.net.InetAddress, int)
	 */
	public Socket createSocket(String host, int port, InetAddress clientHost,
			int clientPort) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port,
				clientHost, clientPort);
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String, int, java.net.InetAddress, int, org.apache.commons.httpclient.params.HttpConnectionParams)
	 */
	public Socket createSocket(String host, int port, InetAddress localAddress,
			int localPort, HttpConnectionParams params) throws IOException,
			UnknownHostException {
		return createSocket(host, port, localAddress, localPort);
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.httpclient.protocol.ProtocolSocketFactory#createSocket(java.lang.String, int)
	 */
	public Socket createSocket(String host, int port) throws IOException,
			UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return (obj != null)
				&& (obj.getClass().equals(MySSLSocketFactory.class));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return MySSLSocketFactory.class.hashCode();
	}

	/**
	 * @Title: MySSLSocketFactory.java
	 * @Package com.trs.infra.util
	 * @author dfreng
	 * @date 2011-7-13 上午11:29:37
	 * @version CMS V1.0 
	 */
	public static class MyX509TrustManager implements X509TrustManager {
		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
		 */
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
		 */
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		/* (non-Javadoc)
		 * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
		 */
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}
	}
}