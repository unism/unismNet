package com.unism.infra.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * @Title: TRSImportsOrgnize.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午11:20:13
 * @version CMS V1.0 
 */
public class TRSImportsOrgnize {
	/**
	 * 
	 */
	public static final String CLASSES_PATH_FLAG = File.separatorChar
			+ "classes" + File.separatorChar;
	/**
	 * 
	 */
	public static final String FLAG_PAGE_START = "include file=\"";
	/**
	 * 
	 */
	public static final int TYPE_NOFLAG = -1;
	/**
	 *  
	 */
	public static final int TYPE_PAGE_START = 0;
	/**
	 * 
	 */
	public static final int TYPE_INCLUDE = 1;
	/**
	 * 
	 */
	public static final int TYPE_HTML_VALUE = 2;
	/**
	 * 
	 */
	public static final int TYPE_CODE_START = 3;
	/**
	 * 
	 */
	public static final int TYPE_CODE_END = 4;
	/**
	 * 
	 */
	public static final int TYPE_COMMENT = 5;
	/**
	 * 
	 */
	public static final String[] OPERATION_STR = { "<%!", "<%=", "<%", "%>",
			"-", "=", "(", ")", ".", "!" };

	/**
	 * 
	 */
	public static final String[] EXCLUDE_PATH = { "images", "include", "cvs" };

	/**
	 * 
	 */
	public static ArrayList arExcludeClass = new ArrayList();
	/**
	 * 
	 */
	public static Hashtable hTRSClasses;
	/**
	 * 
	 */
	private static final String FLAG_WCM_IMPORTS_BEGIN = "<!------- WCM IMPORTS BEGIN ------------>";
	/**
	 * 
	 */
	private static final String FLAG_WCM_IMPORTS_END = "<!------- WCM IMPORTS END ------------>";

	static {
		try {
			initClasses("D:/MyWorkspace/TRSWCM52SRC/context/WEB-INF/classes/");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @param _sClassPath
	 */
	private static void initClasses(String _sClassPath) {
		initExcludeClass();
		hTRSClasses = getFileHashtable(_sClassPath);
	}

	/**
	 * 
	 */
	private static void initExcludeClass() {
		arExcludeClass.add("ExceptionNumber.class");
		arExcludeClass.add("User.class");
		arExcludeClass.add("ContextHelper.class");
		arExcludeClass.add("WCMException.class");
		arExcludeClass.add("LoginHelper.class");
		arExcludeClass.add("RequestParser.class");
		arExcludeClass.add("ResponseHelper.class");
		arExcludeClass.add("DreamFactory.class");
	}

	/**
	 * @param _sClassName
	 * @return
	 */
	public static boolean isExcludeClass(String _sClassName) {
		return arExcludeClass.indexOf(_sClassName) >= 0;
	}

	/**
	 * @param _sAbsoluteFile
	 * @return
	 */
	public static String transformPathToClassPath(String _sAbsoluteFile) {
		int nPose = _sAbsoluteFile.lastIndexOf(".");
		if ((nPose < 0)
				|| (nPose < _sAbsoluteFile.lastIndexOf(File.separatorChar)))
			return _sAbsoluteFile;
		String sClassPath = _sAbsoluteFile.substring(0, nPose);

		nPose = _sAbsoluteFile.indexOf(CLASSES_PATH_FLAG);
		if (nPose < 0)
			return _sAbsoluteFile;
		sClassPath = sClassPath.substring(nPose + CLASSES_PATH_FLAG.length());

		return sClassPath.replace(File.separatorChar, '.');
	}

	/**
	 * @param _sPath
	 * @return
	 */
	public static Hashtable getFileHashtable(String _sPath) {
		Hashtable hClassesMap = new Hashtable();
		File newFile = new File(_sPath);
		File[] arFile = newFile.listFiles();
		if (arFile == null)
			return hClassesMap;
		for (int i = 0; i < arFile.length; i++) {
			if (arFile[i].isFile()) {
				if (isExcludeClass(arFile[i].getName())) {
					continue;
				}
				String sClassPath = transformPathToClassPath(arFile[i]
						.getAbsolutePath());
				hClassesMap.put(arFile[i].getName(), sClassPath);
			}
			if (arFile[i].isDirectory()) {
				hClassesMap.putAll(getFileHashtable(arFile[i].getPath()));
			}
		}

		return hClassesMap;
	}

	/**
	 * @param _sClassName
	 * @return
	 */
	public static String mapClassFullPath(String _sClassName) {
		if (_sClassName.indexOf(".") < 0) {
			_sClassName = _sClassName + ".class";
		}
		return (String) hTRSClasses.get(_sClassName);
	}

	/**
	 * @param _sPath
	 * @return
	 */
	private static boolean isExcludePath(String _sPath) {
		String sPathName = CMyFile.extractFileName(_sPath);
		for (int i = 0; i < EXCLUDE_PATH.length; i++) {
			if (EXCLUDE_PATH[i].equalsIgnoreCase(sPathName))
				return true;
		}
		return false;
	}

	/**
	 * @param _sFileName
	 * @return
	 */
	private static boolean isExcludeFile(String _sFileName) {
		String sFileExt = CMyFile.extractFileExt(_sFileName);
		return !sFileExt.equalsIgnoreCase("jsp");
	}

	/**
	 * @param _sPath
	 * @throws Exception
	 */
	public static void dowithPath(String _sPath) throws Exception {
		File newFile = new File(_sPath);
		if (newFile.isFile()) {
			dowithFile(newFile.getAbsolutePath());
			return;
		}

		if (isExcludePath(newFile.getName())) {
			return;
		}
		File[] arFile = newFile.listFiles();
		for (int i = 0; i < arFile.length; i++)
			if (arFile[i].isFile()) {
				dowithFile(arFile[i].getAbsolutePath());
			} else if (arFile[i].isDirectory())
				dowithPath(arFile[i].getPath());
	}

	/**
	 * @param _sFileName
	 * @return
	 * @throws CMyException
	 */
	public static String dowithFile(String _sFileName) throws CMyException {
		if (isExcludeFile(_sFileName)) {
			return null;
		}
		FileReader fileReader = null;

		StringBuffer buffContentBefore = null;
		StringBuffer buffContentAfter = null;

		FileInputStream fis = null;
		BufferedReader buffReader = null;
		ArrayList arClassPath = new ArrayList();
		String sLine;
		String sImports = null;
		try {
			fis = new FileInputStream(_sFileName);
			buffReader = new BufferedReader(new InputStreamReader(fis,
					CMyString.FILE_WRITING_ENCODING));
			boolean bCodeStart = false;
			boolean bDowithLine = false;
			int nLineCount = 0;
			boolean bImportStart = false;
			boolean bImportEnd = false;

			while ((sLine = buffReader.readLine()) != null) {
				if (!bImportEnd) {
					if (bImportStart) {
						if (sLine
								.indexOf("<!------- WCM IMPORTS END ------------>") >= 0) {
							bImportEnd = true;
							bImportStart = false;
						}

					} else if (sLine
							.indexOf("<!------- WCM IMPORTS BEGIN ------------>") >= 0) {
						bImportStart = true;
					} else {
						if (buffContentBefore == null)
							buffContentBefore = new StringBuffer();
						else {
							buffContentBefore.append('\n');
						}
						buffContentBefore.append(sLine);
					}
				} else {
					if (buffContentAfter == null) {
						buffContentAfter = new StringBuffer();
						buffContentBefore.append('\n');
					}
					buffContentAfter.append('\n');
					buffContentAfter.append(sLine);

					nLineCount++;
					int nLineType = getLineType(sLine);

					switch (nLineType) {
					case 5:
						bDowithLine = false;
						break;
					case 3:
						if (!bCodeStart) {
							if (sLine.indexOf("%>") > 0)
								bCodeStart = false;
							else
								bCodeStart = true;
						} else
							System.out.println("分析第[" + nLineCount + "]行["
									+ sLine + "]发现异常:以前的代码没有结束!");

						bDowithLine = true;
						break;
					case 4:
						if (bCodeStart)
							bCodeStart = false;
						else {
							System.out.println("分析第[" + nLineCount + "]行["
									+ sLine + "]发现异常:没有开始!");
						}
						bDowithLine = true;
						break;
					case 2:
						sLine = subHTMLValue(sLine);
						bDowithLine = sLine != null;
						break;
					case -1:
						bDowithLine = bCodeStart;
						break;
					case 0:
					case 1:
					}

					if (bDowithLine) {
						arClassPath = mergeArrayList(arClassPath,
								dowithLine(sLine));
						bDowithLine = false;
					}
				}
			}

			if (buffContentAfter == null) {
				System.out.println("文件[" + _sFileName + "]不是系统规范文件！不在处理范围内");

			}
		} catch (FileNotFoundException ex) {
			throw new CMyException(55, "要读取得文件没有找到(CMyFile.readFile)", ex);
		} catch (IOException ex) {
			throw new CMyException(53, "读文件时错误(CMyFile.readFile)", ex);
		} finally {
			try {
				if (fileReader != null)
					fileReader.close();
				if (buffReader != null)
					buffReader.close();
				if (fis != null)
					fis.close();
			} catch (Exception localException) {
			}
		}

		CMyFile.writeFile(_sFileName, buffContentBefore + sImports
				+ buffContentAfter);

		return "";
	}

	/**
	 * @param _sLine
	 * @return
	 */
	private static ArrayList dowithLine(String _sLine) {
		String sLine = _sLine;
		sLine = sLine.trim();
		if (sLine.length() <= 0) {
			return null;
		}
		for (int i = 0; i < OPERATION_STR.length; i++) {
			sLine = CMyString.replaceStr(sLine, OPERATION_STR[i], " ");
		}
		sLine = sLine.trim();
		if (sLine.length() <= 0) {
			return null;
		}
		ArrayList arImportClasses = new ArrayList();
		String sClassPath = null;
		sLine = CMyString.replaceStr(sLine, "\t", " ");
		String[] arObject = sLine.split(" ");
		for (int i = 0; i < arObject.length; i++) {
			if (arObject[i].trim().length() == 0) {
				continue;
			}
			sClassPath = mapClassFullPath(arObject[i]);
			if (sClassPath == null) {
				continue;
			}
			if (arImportClasses.indexOf(sClassPath) >= 0) {
				continue;
			}
			arImportClasses.add(sClassPath);
		}

		return arImportClasses;
	}

	/**
	 * @param _sLine
	 * @return
	 */
	private static int getLineType(String _sLine) {
		_sLine = _sLine.trim();

		if (_sLine.indexOf("include file=\"") >= 0) {
			return 0;
		}
		if (_sLine.indexOf("<%@include ") >= 0) {
			return 1;
		}
		if (_sLine.indexOf("<%=") >= 0) {
			return 2;
		}
		if (_sLine.indexOf("<%") >= 0) {
			return 3;
		}
		if (_sLine.indexOf("%>") >= 0) {
			return 4;
		}
		if ((_sLine.indexOf("//") == 0) || (_sLine.indexOf("*") == 0)
				|| (_sLine.indexOf("/*") == 0)
				|| (_sLine.indexOf("page import=") > 0)) {
			return 5;
		}
		return -1;
	}

	/**
	 * @param _sLine
	 * @return
	 */
	private static String subHTMLValue(String _sLine) {
		int nStart = 0;
		int nEnd = 0;
		String sCode = null;
		while (nEnd >= 0) {
			nStart = _sLine.indexOf("<%=", nEnd);
			if (nStart < 0)
				return sCode;
			nEnd = _sLine.indexOf("%>", nStart);
			if (nEnd < 0) {
				return sCode;
			}
			sCode = sCode + " " + _sLine.substring(nStart + 3, nEnd);
		}

		return sCode;
	}

	/**
	 * @param _oldList
	 * @param _newList
	 * @return
	 */
	private static ArrayList mergeArrayList(ArrayList _oldList,
			ArrayList _newList) {
		if ((_newList == null) || (_newList.isEmpty())) {
			return _oldList;
		}
		Object oValue = null;
		for (int i = 0; i < _newList.size(); i++) {
			oValue = _newList.get(i);
			if (_oldList.indexOf(oValue) >= 0) {
				continue;
			}
			_oldList.add(oValue);
		}

		return _oldList;
	}

	/**
	 * @param _classesList
	 * @return
	 */
	private static String createImportContent(ArrayList _classesList) {
		Collections.sort(_classesList);
		String sClassPath = null;
		String sImportContent = "";
		for (int i = 0; i < _classesList.size(); i++) {
			sClassPath = (String) _classesList.get(i);
			sImportContent = sImportContent + "<%@ page import=\"" + sClassPath
					+ "\" %>";
			if (i + 1 < _classesList.size()) {
				sImportContent = sImportContent + "\n";
			}
		}
		return "<!------- WCM IMPORTS BEGIN ------------>\n" + sImportContent
				+ "\n" + "<!------- WCM IMPORTS END ------------>";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if ((args == null) || (args.length < 2)) {
			System.err.println("必须指定两个参数\n第一个参数为Class的存放路径，第二个参数为待处理的文件或目录");
			return;
		}
		String sClassPath = args[0];
		String sFilePath = args[1];
		File aFile = new File(sClassPath);
		if (!aFile.exists()) {
			System.out.println("指定的ClassPath[" + sClassPath + "]不存在！");
			return;
		}

		initClasses(sClassPath);

		aFile = new File(sFilePath);
		if (!aFile.exists()) {
			System.out.println("指定待处理的目录或文件[" + sFilePath + "]不存在！");
			return;
		}
		try {
			if (aFile.isDirectory())
				dowithPath(sFilePath);
			else
				dowithFile(sFilePath);
		} catch (Exception ex) {
			System.err.println("处理目录或文件的Imports[" + sFilePath + "]时候出现异常！");
			ex.printStackTrace();
		}
	}
}