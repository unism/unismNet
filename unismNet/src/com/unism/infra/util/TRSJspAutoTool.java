package com.unism.infra.util;

import java.io.File;
import java.io.PrintStream;

/**
 * @Title: TRSJspAutoTool.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:48:04
 * @version CMS V1.0 
 */
/**
 * @Title: TRSJspAutoTool.java
 * @Package com.trs.infra.util
 * @author dfreng
 * @date 2011-7-13 上午01:51:45
 * @version CMS V1.0 
 */
public class TRSJspAutoTool {
	/**
	 * 
	 */
	private static String TEMPLATE_PATH = "/template/";
	/**
	 * 
	 */
	private String m_sAuthor;
	/**
	 *  
	 */
	private String m_sSrcFileName;
	/**
	 * 
	 */
	private String m_sBaseObj;
	/**
	 * 
	 */
	private String m_sDescription;
	/**
	 * 
	 */
	private String m_sClassPath;
	/**
	 * 
	 */
	private String m_sCodingTemplateFile = null;
	/**
	 * 
	 */
	private String m_sConfigTemplateFile = null;
	/**
	 * 
	 */
	private String m_sDowithTemplateFile = null;
	/**
	 * 
	 */
	private String m_sSearchFields = null;

	/**
	 * 
	 */
	private String m_sTemplateFile = null;

	/**
	 * 
	 */
	private boolean m_bMergeFile = false;
	/**
	 * 
	 */
	private boolean m_bFileNameByBaseObj = false;

	/**
	 * @param _sAuthor
	 * @param _sClassPath
	 * @param _sFileName
	 * @param _sBaseObj
	 * @param _sDescription
	 */
	public TRSJspAutoTool(String _sAuthor, String _sClassPath,
			String _sFileName, String _sBaseObj, String _sDescription) {
		setAuthor(_sAuthor);
		setClassPath(_sClassPath);
		setSrcFileName(_sFileName);
		setBaseObj(_sBaseObj);
		setDescription(_sDescription);
	}

	/**
	 * @return
	 */
	protected String getAuthor() {
		return this.m_sAuthor;
	}

	/**
	 * @param author
	 */
	protected void setAuthor(String author) {
		this.m_sAuthor = author;
	}

	/**
	 * @return
	 */
	/**
	 * @return
	 */
	protected String getBaseObj() {
		return this.m_sBaseObj;
	}

	/**
	 * @param baseObj
	 */
	protected void setBaseObj(String baseObj) {
		this.m_sBaseObj = baseObj;
	}

	/**
	 * @return
	 */
	protected String getClassPath() {
		return this.m_sClassPath;
	}

	/**
	 * @param classPath
	 */
	protected void setClassPath(String classPath) {
		this.m_sClassPath = classPath;
	}

	/**
	 * @return
	 */
	protected String getDescription() {
		return this.m_sDescription;
	}

	/**
	 * @param description
	 */
	protected void setDescription(String description) {
		this.m_sDescription = description;
	}

	/**
	 * @return
	 */
	protected String getSrcFileName() {
		return this.m_sSrcFileName;
	}

	/**
	 * @param fileName
	 */
	protected void setSrcFileName(String fileName) {
		this.m_sSrcFileName = fileName;
	}

	/**
	 * @param _sFileName
	 * @return
	 */
	private String extractFilePrex(String _sFileName) {
		int nPose = _sFileName.indexOf('.');
		if (nPose < 0) {
			return _sFileName;
		}
		return _sFileName.substring(0, nPose);
	}

	/**
	 * @param _sFileExt
	 * @return
	 */
	private String getDestFileName(String _sFileExt) {
		String sSrcFileName = getSrcFileName();

		if (isFileNameByBaseObj()) {
			String sSrcFilePath = CMyFile.extractFilePath(sSrcFileName);
			String sFileName = getBaseObj().toLowerCase() + "_"
					+ extractFilePrex(getTemplateFile()) + "." + _sFileExt;
			return sSrcFilePath + sFileName;
		}

		String sDestFileName = sSrcFileName;
		int nPose = sSrcFileName.lastIndexOf(".");
		if (nPose > 0) {
			sDestFileName = sSrcFileName.substring(0, nPose);
		}
		sDestFileName = sDestFileName + "." + _sFileExt;
		return sDestFileName;
	}

	/**
	 * @return
	 */
	private String extractFileName() {
		String sSrcFileName = getSrcFileName();
		int nPose = sSrcFileName.lastIndexOf(".");
		if (nPose > 0) {
			sSrcFileName = sSrcFileName.substring(0, nPose);
		}
		nPose = sSrcFileName.lastIndexOf('/');
		if (nPose < 0)
			nPose = sSrcFileName.lastIndexOf('\\');
		return sSrcFileName.substring(nPose + 1);
	}

	/**
	 * @param _sFileContent
	 * @return
	 */
	private String dowithFileContent(String _sFileContent) {
		String sNewContent = _sFileContent;

		String sFileName = extractFileName();
		sNewContent = CMyString.replaceStr(sNewContent, "${Author}",
				getAuthor());
		sNewContent = CMyString.replaceStr(sNewContent, "${FileName}",
				sFileName);
		sNewContent = CMyString.replaceStr(sNewContent, "${BaseObj}",
				getBaseObj());
		sNewContent = CMyString.replaceStr(sNewContent, "${BaseObjLower}",
				getBaseObj().toLowerCase());
		sNewContent = CMyString.replaceStr(sNewContent, "${Description}",
				getDescription());
		sNewContent = CMyString.replaceStr(sNewContent, "${DateTime}",
				CMyDateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		sNewContent = CMyString.replaceStr(sNewContent, "${Date}", CMyDateTime
				.now().toString("yyyy-MM-dd"));

		if (getSearchFields() != null) {
			sNewContent = CMyString.replaceStr(sNewContent, "${Fields}",
					getSearchFields());
			String[] arSearchFields = CMyString.split(getSearchFields(), ",");
			sNewContent = dowithFields(arSearchFields, sNewContent);
		}

		return sNewContent;
	}

	/**
	 * @param _arSearchFields
	 * @param _sContent
	 * @return
	 */
	private String dowithFields(String[] _arSearchFields, String _sContent) {
		String sFieldBeginFlag = "${Field_Begin}";
		int nStartPose = _sContent.indexOf(sFieldBeginFlag);
		if (nStartPose < 0) {
			return _sContent;
		}
		String sFieldEndFlag = "${Field_End}";
		int nEndPose = _sContent.indexOf(sFieldEndFlag, nStartPose);
		if (nEndPose < 0) {
			return _sContent;
		}
		String sNewContent = "";
		String sNeedReplaceContent = _sContent.substring(nStartPose
				+ sFieldBeginFlag.length(), nEndPose);
		for (int i = 0; i < _arSearchFields.length; i++) {
			sNewContent = sNewContent
					+ CMyString.replaceStr(sNeedReplaceContent, "${Field}",
							_arSearchFields[i]) + "\n";
		}
		return dowithFields(_arSearchFields, _sContent.substring(0, nStartPose)
				+ sNewContent
				+ _sContent.substring(nEndPose + sFieldEndFlag.length()));
	}

	/**
	 * @param _sFileName
	 * @param _sFileContent
	 * @throws CMyException
	 */
	private void writeFile(String _sFileName, String _sFileContent)
			throws CMyException {
		if (CMyFile.fileExists(_sFileName)) {
			CMyFile.copyFile(_sFileName, _sFileName + "_"
					+ CMyDateTime.now().toString("yyyyMMddHHmm") + ".bak");
		}
		CMyFile.writeFile(_sFileName, dowithFileContent(_sFileContent));
	}

	/**
	 * @throws CMyException
	 */
	public void createFile() throws CMyException {
		String sDstFileContent = CMyFile.readFile(getCodingTemplateFile());
		if (isMergeFile()) {
			String sSrcFileContent = CMyFile.readFile(getSrcFileName());
			sDstFileContent = sDstFileContent + "\n" + sSrcFileContent;
		}
		String sDestFileName = getDestFileName(CMyFile
				.extractFileExt(getCodingTemplateFile()));
		writeFile(sDestFileName, sDstFileContent);

		String[] arArgs = { getClassPath(), sDestFileName };
		TRSImportsOrgnize.main(arArgs);

		System.out.println("产生了文件：" + sDestFileName);

		if (getDowithTemplateFile() != null) {
			sDstFileContent = CMyFile.readFile(getDowithTemplateFile());
			sDestFileName = getDestFileName(CMyFile
					.extractFileExt(getDowithTemplateFile()));
			writeFile(sDestFileName, sDstFileContent);
			System.out.println("产生了dowith文件：" + sDestFileName);
		}

		if (getConfigTemplateFile() != null) {
			sDstFileContent = CMyFile.readFile(getConfigTemplateFile());
			sDestFileName = getDestFileName(CMyFile
					.extractFileExt(getConfigTemplateFile()));
			writeFile(sDestFileName, sDstFileContent);
			System.out.println("产生了配置文件：" + sDestFileName);
		}
	}

	/**
	 * @return
	 */
	protected String getCodingTemplateFile() {
		return this.m_sCodingTemplateFile;
	}

	/**
	 * @param codingTemplate
	 */
	protected void setCodingTemplateFile(String codingTemplate) {
		this.m_sCodingTemplateFile = codingTemplate;
	}

	/**
	 * @return
	 */
	protected String getConfigTemplateFile() {
		return this.m_sConfigTemplateFile;
	}

	/**
	 * @param configTemplate
	 */
	protected void setConfigTemplateFile(String configTemplate) {
		this.m_sConfigTemplateFile = configTemplate;
	}

	/**
	 * @return
	 */
	public boolean isMergeFile() {
		return this.m_bMergeFile;
	}

	/**
	 * @param mergeFile
	 */
	public void setMergeFile(boolean mergeFile) {
		this.m_bMergeFile = mergeFile;
	}

	/**
	 * @param _sTemplateFile
	 * @param _bMergeFile
	 * @param _sSearchFields
	 * @throws CMyException
	 */
	public void init(String _sTemplateFile, boolean _bMergeFile,
			String _sSearchFields) throws CMyException {
		setMergeFile(_bMergeFile);

		setTemplateFile(_sTemplateFile);

		String sAbsoulteTemplateFile = CMyFile.mapResouceFullPath(TEMPLATE_PATH
				+ _sTemplateFile, getClass());
		setCodingTemplateFile(sAbsoulteTemplateFile);

		int nPose = sAbsoulteTemplateFile.lastIndexOf('.');
		String sAbsoluteConfigTemplateFile = sAbsoulteTemplateFile.substring(0,
				nPose)
				+ ".xml";

		if (CMyFile.fileExists(sAbsoluteConfigTemplateFile))
			setConfigTemplateFile(sAbsoluteConfigTemplateFile);
		else {
			setConfigTemplateFile(null);
		}
		String sAbsoluteDowithTemplateFile = sAbsoulteTemplateFile.substring(0,
				nPose)
				+ "_dowith.jsp";
		if (CMyFile.fileExists(sAbsoluteDowithTemplateFile))
			setDowithTemplateFile(sAbsoluteDowithTemplateFile);
		else {
			setDowithTemplateFile(null);
		}
		if ((_sSearchFields != null) && (!_sSearchFields.equals("*"))) {
			setSearchFields(_sSearchFields);
		} else
			setSearchFields(null);
	}

	/**
	 * @return
	 */
	public String getDowithTemplateFile() {
		return this.m_sDowithTemplateFile;
	}

	/**
	 * @param dowithTemplateFile
	 */
	public void setDowithTemplateFile(String dowithTemplateFile) {
		this.m_sDowithTemplateFile = dowithTemplateFile;
	}

	/**
	 * @return
	 */
	public String getSearchFields() {
		return this.m_sSearchFields;
	}

	/**
	 * @param searchFields
	 */
	public void setSearchFields(String searchFields) {
		this.m_sSearchFields = searchFields;
	}

	/**
	 * @return
	 */
	public boolean isFileNameByBaseObj() {
		return this.m_bFileNameByBaseObj;
	}

	/**
	 * @param fileNameByBaseObj
	 */
	public void setFileNameByBaseObj(boolean fileNameByBaseObj) {
		this.m_bFileNameByBaseObj = fileNameByBaseObj;
	}

	/**
	 * @return
	 */
	public String getTemplateFile() {
		return this.m_sTemplateFile;
	}

	/**
	 * @param templateFile
	 */
	public void setTemplateFile(String templateFile) {
		this.m_sTemplateFile = templateFile;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if ((args == null) || (args.length < 6)) {
			System.err.println("参数情况：可以指定8个参数，前6个参数必须指定，后一个参数可以不传入：");
			System.err
					.println(" 1:作者 \n 2:Class路径 \n 3:文件名(带路径的文件名) \n 4:对象名称 \n 5:对象的描述 \n 6:模板 \n 7:是否仅仅结合传入的文件名(默认为true) \n 8:需要检索的字段(*表示不需要) ");

			return;
		}
		File aFile = new File(args[1]);
		if (!aFile.exists()) {
			System.out.println("指定的ClassPath[" + args[1] + "]不存在！");
			return;
		}

		aFile = new File(args[2]);
		if (!aFile.exists()) {
			System.out.println("指定待处理文件[" + args[2] + "]不存在！");
			return;
		}

		String sTemplateFile = args[5];

		TRSJspAutoTool aTool = new TRSJspAutoTool(args[0], args[1], args[2],
				args[3], args[4]);
		String sSearchFields = "*";
		if (args.length >= 8) {
			sSearchFields = args[7];
		}
		if (sTemplateFile.equalsIgnoreCase("all")) {
			aTool.setFileNameByBaseObj(true);
			File aTemplatePath = null;
			try {
				aTemplatePath = new File(CMyFile
						.extractFilePath(CMyFile.mapResouceFullPath(
								TEMPLATE_PATH, TRSJspAutoTool.class)));
			} catch (CMyException ex) {
				System.out.println("指定的模板目录[" + TEMPLATE_PATH + "]不存在！");
				ex.printStackTrace(System.err);
				return;
			}
			File[] aTemplateFile = aTemplatePath
					.listFiles(new FileNameExtFilter("jsp"));
			for (int i = 0; i < aTemplateFile.length; i++)
				try {
					aTool
							.init(aTemplateFile[i].getName(), false,
									sSearchFields);
					aTool.setDowithTemplateFile(null);
					aTool.createFile();
				} catch (Throwable ex) {
					System.err.println("根据模板[" + aTemplateFile[i].getName()
							+ "]产生文件失败！");
					ex.printStackTrace(System.err);
				}
		} else {
			try {
				if (sTemplateFile.indexOf('.') <= 0) {
					System.out.println("指定模板文件[" + sTemplateFile
							+ "]不符合规范,必须具有.！");
					return;
				}
				aTool.init(sTemplateFile, !"false".equalsIgnoreCase(args[6]),
						sSearchFields);
			} catch (CMyException ex) {
				System.out.println("指定模板文件[" + sTemplateFile + "]可能不存在！");
				ex.printStackTrace(System.err);
				return;
			}
			try {
				aTool.createFile();
			} catch (Throwable ex) {
				System.err.println("产生列表页面失败！");
				ex.printStackTrace(System.err);
			}
		}
	}
}