package com.unism.infra.util;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @Title: AttributesString.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午12:55:14
 * @version CMS V1.0 
 */
public class AttributesString {
	/**
	 * 属性之间的分割符
	 */
	public static final String ATTRIBUTE_DELIM = "&";
	/**
	 * 
	 */
	private HashMap m_hAttributesMap = null;

	/**
	 * 
	 */
	private ArrayList m_arNameList = null;

	/**
	 * 
	 */
	private String m_sDelim = null;

	/**
	 * 默认构造韩函数
	 */
	public AttributesString() {
	}

	/**
	 * 直接使用属性字符串构造对象
	 * @param _attributes - 属性字符串（可忽略）
	 */
	public AttributesString(String _attributes) {
		setAttributes(_attributes);
	} 

	/* 
	 * 获得属性字符串
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	public String toString() {
		return getAttributes();
	}

	/**
	 * 获得属性字符串
	 * @return 属性字符串（String对象）
	 */
	public String getAttributes() {
		int nCount = getNameList().size();
		if (nCount == 0) {
			return null;
		}
		String sRet = "";
		for (int i = 0; i < nCount; i++) {
			if (i > 0) {
				sRet = sRet + getDelim();
			}
			sRet = sRet + getAttribute((String) getNameList().get(i));
		}
		return sRet;
	}

	/**
	 * 直接用属性字符串设置属性
	 * @param _attributes - 属性字符串
	 */
	public void setAttributes(String _attributes) {
		if (_attributes == null) {
			return;
		}
		clear();

		StringTokenizer stAttributes = null;
		stAttributes = new StringTokenizer(_attributes, getDelim());
		while (stAttributes.hasMoreElements()) {
			String sTemp = stAttributes.nextToken();
			int nPose = sTemp.indexOf("=");
			if (nPose <= 0) {
				continue;
			}
			String sName = sTemp.substring(0, nPose);
			String sValue = sTemp.substring(nPose + 1);
			putAttributeValue(sName, sValue, true);
		}
	}

	/**
	 * 取指定名称的属性值 若没有找到该属性，则返回null. 
	 * @param _sName - 属性名称
	 * @return 属性值
	 */
	public String getAttributeValue(String _sName) {
		_sName = _sName.toUpperCase();
		Object oValue = getValue(_sName);
		if (oValue == null) {
			return null;
		}
		if ((oValue instanceof String)) {
			return (String) oValue;
		}
		if ((oValue instanceof String[])) {
			String[] arTemp = (String[]) oValue;
			return arTemp[0];
		}
		return null;
	}

	/**
	 * @param _sName
	 * @return
	 */
	public String[] getAttributeValues(String _sName) {
		_sName = _sName.toUpperCase();
		Object oValue = getValue(_sName);
		if (oValue == null) {
			return null;
		}
		String[] arTemp = (String[]) null;
		if ((oValue instanceof String)) {
			arTemp = new String[1];
			arTemp[0] = ((String) oValue);
		}
		if ((oValue instanceof String[])) {
			arTemp = (String[]) oValue;
		}
		return arTemp;
	}

	/**
	 * @param _sName
	 * @return
	 */
	private String getAttribute(String _sName) {
		String sName = _sName.toUpperCase();
		Object oValue = getValue(sName);
		if (oValue == null) {
			return null;
		}
		if ((oValue instanceof String)) {
			return _sName + "=" + (String) oValue;
		}
		if ((oValue instanceof String[])) {
			String[] arTemp = (String[]) oValue;
			String sTemp = "";
			for (int i = 0; i < arTemp.length; i++) {
				if (i > 0) {
					sTemp = sTemp + getDelim();
				}
				sTemp = sTemp + _sName + "=" + arTemp[i];
			}
			return sTemp;
		}
		return null;
	}

	/**
	 * 设置指定名称的属性值
	 * @param _name - 属性名（不区分大小写）；
	 * @param _value  - 属性值（字符串）
	 */
	public void putAttributeValue(String _name, String _value) {
		putAttributeValue(_name, _value, false);
	}

	/**
	 * @return
	 */
	protected HashMap getAttributesMap() {
		if (this.m_hAttributesMap == null)
			this.m_hAttributesMap = new HashMap(5);
		return this.m_hAttributesMap;
	}

	/**
	 * 
	 */
	protected void clear() {
		if (this.m_hAttributesMap == null)
			return;
		this.m_arNameList.clear();
		this.m_hAttributesMap.clear();
		this.m_hAttributesMap = null;
	}

	/**
	 * @param _sName
	 * @return
	 */
	private Object getValue(String _sName) {
		_sName = _sName.toUpperCase();
		return getAttributesMap().get(_sName);
	}

	/**
	 * @param _sName
	 * @param _sValue
	 * @param _bAllowMultValue
	 */
	public void putAttributeValue(String _sName, String _sValue,
			boolean _bAllowMultValue) {
		String sName = _sName.toUpperCase();
		Object oValue = getValue(sName);
		if (oValue == null) {
			getAttributesMap().put(sName, _sValue);
			getNameList().add(_sName);
			return;
		}

		if (!_bAllowMultValue) {
			getAttributesMap().put(sName, _sValue);
			return;
		}

		String[] arValue = (String[]) null;
		if ((oValue instanceof String)) {
			arValue = new String[2];
			arValue[0] = ((String) oValue);
			arValue[1] = _sValue;
		}
		if ((oValue instanceof String[])) {
			String[] arTemp = (String[]) oValue;
			arValue = new String[arTemp.length + 1];
			for (int i = 0; i < arTemp.length; i++) {
				arValue[i] = arTemp[i];
			}
			arValue[arTemp.length] = _sValue;
		}
		if (arValue != null)
			getAttributesMap().put(sName, arValue);
	}

	/**
	 * @return
	 */
	public List getNameList() {
		if (this.m_arNameList == null)
			this.m_arNameList = new ArrayList(5);
		return this.m_arNameList;
	}

	/**
	 * @return
	 */
	public String getDelim() {
		if (this.m_sDelim == null) {
			return "&";
		}
		return this.m_sDelim;
	}

	/**
	 * @param delim
	 */
	public void setDelim(String delim) {
		this.m_sDelim = delim;
	}

	/**
	 * @return
	 */
	public int size() {
		if (this.m_arNameList == null) {
			return 0;
		}
		return getNameList().size();
	}

	/**
	 * @param _sName
	 * @return
	 */
	public boolean containsName(String _sName) {
		if (_sName == null) {
			return false;
		}
		return getAttributesMap().containsKey(_sName.toUpperCase());
	}

	/**
	 * @param _nIndex
	 * @return
	 */
	public String getNameAt(int _nIndex) {
		return (String) getNameList().get(_nIndex);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AttributesString aAttributesString = new AttributesString("a=1&b=2&c=3");
		System.out.println("a=" + aAttributesString.getAttributeValue("a"));
		System.out.println("b=" + aAttributesString.getAttributeValue("b"));
		System.out.println("c=" + aAttributesString.getAttributeValue("c"));
		System.out
				.println("Attributelist:" + aAttributesString.getAttributes());

		System.out.println("Test for pub attribute value:");
		aAttributesString.putAttributeValue("b", "22");
		aAttributesString.putAttributeValue("d", "4");
		System.out
				.println("Attributelist:" + aAttributesString.getAttributes());
	}
}