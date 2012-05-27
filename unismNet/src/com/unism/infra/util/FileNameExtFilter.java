package com.unism.infra.util;

import java.io.File;
import java.io.FilenameFilter;

public class FileNameExtFilter implements FilenameFilter {
	/**
	 * 
	 */
	private String m_sFileExt = null;

	/**
	 * @param _sFileExt
	 */
	public FileNameExtFilter(String _sFileExt) {
		setFileExt(_sFileExt);
	}

	/**
	 * @param _sFilePathName
	 * @return
	 */
	private static String extractFileExt(String _sFilePathName) {
		int nPos = _sFilePathName.lastIndexOf('.');
		return nPos >= 0 ? _sFilePathName.substring(nPos + 1) : "";
	}

	/* (non-Javadoc)
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	public boolean accept(File arg0, String _sFileName) {
		String sFileExt = extractFileExt(_sFileName);
		return sFileExt.equalsIgnoreCase(getFileExt());
	}

	/**
	 * @return
	 */
	public String getFileExt() {
		return this.m_sFileExt;
	}

	/**
	 * @param fileExt
	 */
	public void setFileExt(String fileExt) {
		this.m_sFileExt = fileExt;
	}
}