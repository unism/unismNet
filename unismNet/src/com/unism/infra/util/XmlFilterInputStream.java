package com.unism.infra.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Title: XmlFilterInputStream.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:16:32
 * @version CMS V1.0 
 */
/**
 * @Title: XmlFilterInputStream.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:16:41
 * @version CMS V1.0 
 */
class XmlFilterInputStream extends InputStream {
	/**
	 * 
	 */
	private InputStream m_in;

	/**
	 * @param in
	 */
	public XmlFilterInputStream(InputStream in) {
		this.m_in = in;
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#read()
	 */
	public int read() throws IOException {
		int b = this.m_in.read();
		if (isValid(b)) {
			return b;
		}
		do {
			b = this.m_in.read();
		} while (!isValid(b));

		return b;
	}

	/**
	 * @param _byte
	 * @return
	 */
	private boolean isValid(int _byte) {
		return (_byte >= 32) || (_byte == 9) || (_byte == 10) || (_byte == 13)
				|| (_byte == -1);
	}
}