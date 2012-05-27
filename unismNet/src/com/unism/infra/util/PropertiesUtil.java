package com.unism.infra.util;

import com.unism.infra.common.WCMException;

import java.util.Map;

/**
 * @Title: PropertiesUtil.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:27:35
 * @version CMS V1.0 
 */
public class PropertiesUtil {
	/**
	 * @param _properties
	 * @param _sName
	 * @return
	 */
	public static String getProperty(Map _properties, String _sName) {
		if (_sName == null) {
			return null;
		}

		return (String) _properties.get(_sName.toUpperCase());
	}

	/**
	 * @param _properties
	 * @param _sName
	 * @return
	 */
	public static String getPropertyTrim(Map _properties, String _sName) {
		String sValue = getProperty(_properties, _sName);
		return sValue == null ? null : sValue.trim();
	}

	/**
	 * @param _properties
	 * @param _sName
	 * @return
	 * @throws WCMException
	 */
	public static String getRequiredProperty(Map _properties, String _sName)
			throws WCMException {
		String sValue = getProperty(_properties, _sName);
		if ((sValue == null) || (sValue.length() == 0)) {
			throw new WCMException(1106, "Property [" + _sName + "] required!");
		}

		return sValue;
	}

	/**
	 * @param _properties
	 * @param _sName
	 * @return
	 * @throws WCMException
	 */
	public static String getRequiredPropertyTrim(Map _properties, String _sName)
			throws WCMException {
		String sValue = getPropertyTrim(_properties, _sName);
		if ((sValue == null) || (sValue.length() == 0)) {
			throw new WCMException(1106, "Property [" + _sName + "] required!");
		}

		return sValue;
	}

	/**
	 * @param _properties
	 * @param _sName
	 * @param _nDefault
	 * @return
	 * @throws WCMException
	 */
	public static int getIntProperty(Map _properties, String _sName,
			int _nDefault) throws WCMException {
		String sValue = getPropertyTrim(_properties, _sName);
		if ((sValue == null) || (sValue.length() == 0))
			return _nDefault;
		try {
			return Integer.parseInt(sValue);
		} catch (Exception ex) {
			throw new WCMException(2, "Value [" + sValue + "] of property ["
					+ _nDefault + "] is not integer!", ex);
		}

	}

	/**
	 * @param _properties
	 * @param _sName
	 * @param _bDefault
	 * @return
	 */
	public static boolean getBooleanProperty(Map _properties, String _sName,
			boolean _bDefault) {
		String sValue = getPropertyTrim(_properties, _sName);
		if ((sValue == null) || (sValue.length() == 0)) {
			return _bDefault;
		}
		return sValue.equalsIgnoreCase("true");
	}
}