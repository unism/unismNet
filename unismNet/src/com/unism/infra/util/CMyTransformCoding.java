package com.unism.infra.util;

/**
 * @Title: CMyTransformCoding.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:40:48
 * @version CMS V1.0 
 */
public class CMyTransformCoding {
	static {
		System.loadLibrary("TransformCoding");
	}

	/**
	 * @param paramString
	 * @return
	 */
	public native String TransformCoding(String paramString);
}