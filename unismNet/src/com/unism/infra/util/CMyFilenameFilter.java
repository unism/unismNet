package com.unism.infra.util;

import java.io.File;
import java.io.FilenameFilter;

public class CMyFilenameFilter implements FilenameFilter {
	/**
	 * 
	 */
	private String sExt;

	/**
	 * @param _extendName
	 */
	public CMyFilenameFilter(String _extendName) {
		this.sExt = _extendName;
	}

	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	public boolean accept(File _dir, String _name) {
		return _name.endsWith(this.sExt);
	}
}