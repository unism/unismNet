package com.unism.infra.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @Title: ExceptionMessages.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:34:47
 * @version CMS V1.0 
 */
public class ExceptionMessages {
	/**
	 * 
	 */
	private static final String BUNDLE_NAME = "exception_messages";
	/**
	 * 
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle("exception_messages");

	/**
	 * @param _nErrorNumber
	 * @return
	 */
	public static String getString(int _nErrorNumber) {
		String key = "ExNum." + String.valueOf(_nErrorNumber);
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
		}
		return '!' + key + '!';
	}
}