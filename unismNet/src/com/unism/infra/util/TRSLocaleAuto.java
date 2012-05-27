package com.unism.infra.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @Title: TRSLocaleAuto.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:19:44
 * @version CMS V1.0 
 */
public class TRSLocaleAuto {
	private static ArrayList s_listElements = null;

	/**
	 * 
	 */
	private static ArrayList s_listOpers = null;
	/**
	 * 
	 */
	private static final String INI_COMPONENTS = "/components.ini";
	/**
	 * 
	 */
	private static final String INI_ELEMENTS = "/elements.ini";
	/**
	 * 
	 */
	private static final String INI_OPERS = "/opers.ini";
	

	/**
	 * @param paramString
	 * @return
	 * @throws CMyException
	 */
	private static ArrayList loadINI(String paramString) throws CMyException {
		ArrayList localArrayList = new ArrayList();
		Object localObject1 = null;

		FileInputStream localFileInputStream = null;
		BufferedReader localBufferedReader = null;
		try {
			localFileInputStream = new FileInputStream(CMyFile
					.mapResouceFullPath(paramString, TRSLocaleAuto.class));

			localBufferedReader = new BufferedReader(new InputStreamReader(
					localFileInputStream, CMyString.FILE_WRITING_ENCODING));
			String str;
			while ((str = localBufferedReader.readLine()) != null) {
				str = str.trim();
				if ((str.length() <= 0) || (str.charAt(0) == '#')) {
					continue;
				}
				localArrayList.add(str);
			}
		} catch (FileNotFoundException localFileNotFoundException) {
			throw new CMyException(55, "要读取得文件没有找到(CMyFile.readFile)",
					localFileNotFoundException);
		} catch (IOException localIOException) {
			throw new CMyException(53, "读文件时错误(CMyFile.readFile)",
					localIOException);
		} finally {
			try {
				/*
				 * if (localObject1 != null) localObject1.close();
				 */
				if (localBufferedReader != null)
					localBufferedReader.close();
				if (localFileInputStream != null)
					localFileInputStream.close();
			} catch (Exception localException) {
			}
		}
		return localArrayList;
	}

	/**
	 * @param _sName
	 * @param _sComponentName
	 * @return
	 * @throws CMyException
	 */
	public static String createLocal(String _sName, String _sComponentName)
			throws CMyException {
		String sResult = "";

		int nElementCount = getElements().size();

		for (int i = 0; i < nElementCount; i++) {
			String sElementName = (String) getElements().get(i);
			sResult = sResult
					+ createLocal(_sName, _sComponentName, sElementName);
		}
		return sResult;
	}

	/**
	 * @param _sName
	 * @param _sComponentName
	 * @param _sElementName
	 * @return
	 * @throws CMyException
	 */
	public static String createLocal(String _sName, String _sComponentName,
			String _sElementName) throws CMyException {
		String sResult = "";

		int nOperCount = getOpers().size();

		for (int j = 0; j < nOperCount; j++) {
			sResult = sResult
					+ createLocal(_sName, _sComponentName, _sElementName,
							(String) getOpers().get(j), null) + "\n";
		}
		return sResult;
	}

	/**
	 * @param _sName
	 * @param _sComponentName
	 * @param _sElementName
	 * @param _sOperName
	 * @param _sPropFile
	 * @return
	 */
	public static String createLocal(String _sName, String _sComponentName,
			String _sElementName, String _sOperName, String _sPropFile) {
		String sKey = _sComponentName + "." + _sElementName + "." + _sOperName;
		String sValue = _sName;
		String sOldValue = null;
		if (_sPropFile != null) {
			if (CMyFile.fileExists(_sPropFile))
				sOldValue = setProperty(_sPropFile, sKey, sValue);
			else {
				System.out.println("指定的Properties文件不存在！");
			}
		}
		return "<%=LocaleServer.getString(\"" + sKey + "\", \"" + sValue
				+ "\")%>" + (sOldValue == null ? "" : sOldValue);
	}

	/**
	 * @param _sName
	 * @param _sComponentName
	 * @param _sElementName
	 * @param _sOperName
	 * @param _sPropFile
	 * @return
	 */
	public static String createJSPLocal(String _sName, String _sComponentName,
			String _sElementName, String _sOperName, String _sPropFile) {
		String sKey = _sComponentName + "." + _sElementName + "." + _sOperName;
		String sValue = _sName;
		if (_sPropFile != null) {
			if (CMyFile.fileExists(_sPropFile))
				setProperty(_sPropFile, sKey, sValue);
			else {
				System.out.println("指定的Properties文件不存在！");
			}
		}
		return "LocaleServer.getString(\"" + sKey + "\", \"" + sValue + "\")";
	}

	/**
	 * @param paramString1
	 * @param paramString2
	 * @param paramString3
	 * @return
	 */
	private static String setProperty(String paramString1, String paramString2,
			String paramString3) {
		FileInputStream localFileInputStream = null;
		Properties localProperties = null;

		String str2;
		try {
			paramString2 = paramString2.toLowerCase();
			String str1 = paramString1;
			localFileInputStream = new FileInputStream(str1);
			localProperties = new Properties();
			localProperties.load(localFileInputStream);
			if (!localProperties.containsKey(paramString2)) {
				localProperties.setProperty(paramString2, paramString3);
				localProperties.store(new FileOutputStream(str1),
						"Generated by WCM Auto Tool");

				str2 = null;// jsr 66;
			}
			if (localProperties.getProperty(paramString2).equals(paramString3)) {
				str2 = null;// jsr 44;
			}
			str2 = localProperties.getProperty(paramString2);
		} catch (Exception localException1) {
			localException1.printStackTrace(System.out);
		} finally {
			try {
				if (localFileInputStream != null)
					localFileInputStream.close();
				if (localProperties != null) {
					localProperties.clear();
					localProperties = null;
				}
			} catch (Exception localException2) {
			}
		}
		return null;
	}

	/**
	 * @return
	 * @throws CMyException
	 */
	public static ArrayList getElements() throws CMyException {
		if (s_listElements == null) {
			s_listElements = loadINI("/elements.ini");
		}
		return s_listElements;
	}

	/**
	 * @return
	 * @throws CMyException
	 */
	public static ArrayList getOpers() throws CMyException {
		if (s_listOpers == null) {
			s_listOpers = loadINI("/opers.ini");
		}
		return s_listOpers;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if ((args == null) || (args.length < 1)) {
			System.out.print("传入的参数错误！");
			return;
		}
		try {
			if (args.length == 2)
				System.out.println(createLocal(args[0], args[1]));
			else if (args.length == 3)
				System.out.print(createLocal(args[0], args[1], args[2]));
			else if (args.length == 5)
				System.out.print(createLocal(args[0], args[1], args[2],
						args[3], args[4]));
			else if (args.length == 6)
				System.out.print(createJSPLocal(args[0], args[1], args[2],
						args[3], args[4]));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}