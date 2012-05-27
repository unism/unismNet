package com.unism.infra.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Title: XmlCharCheckedInputStream.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:17:40
 * @version CMS V1.0 
 */
public class XmlCharCheckedInputStream extends InputStream {
	/**
	 * 
	 */
	private InputStream m_ins;

	/**
	 * @param ins
	 */
	public XmlCharCheckedInputStream(InputStream ins) {
		this.m_ins = ins;
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#read()
	 */
	public int read() throws IOException {
		int b = this.m_ins.read();
		if (isValid(b)) {
			return b;
		}
		do {
			b = this.m_ins.read();
		} while (!isValid(b));

		return b;
	}

	/**
	 * @param _byte
	 * @return
	 */
	private boolean isValid(int _byte) {
		if ((_byte >= 32) || (_byte == -1)) {
			return true;
		}

		return (_byte == 9) || (_byte == 10) || (_byte == 13);
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#close()
	 */
	public void close() throws IOException {
		if (this.m_ins != null) {
			this.m_ins.close();
		}
		super.close();
	}
}