package com.unism.infra.util;

/**
 * 异常编号
 * 
 * @author Administrator
 * 
 */
public class ExceptionNumber {
	public static final int ERR_UNKNOWN = 0;
	public static final int ERR_MYEXCEPTION = 1;
	public static final int ERR_DATACONVERT = 2;
	public static final int ERR_PARAM_INVALID = 10;
	public static final int ERR_CLASS_CONFIG = 11;
	public static final int ERR_RUNTIME_CONFIG = 12;
	public static final int ERR_CLASS_NOTFOUND = 13;
	public static final int ERR_CONFIG_MISSING = 14;
	public static final int ERR_OBJ_NULL = 20;
	public static final int ERR_NEW_INSTANCE = 21;
	public static final int ERR_NUMOP_FAIL = 30;
	public static final int ERR_DBOP_FAIL = 40;
	public static final int ERR_CONNECTION_GETFAIL = 41;
	public static final int ERR_FILEOP_FAIL = 50;
	public static final int ERR_FILEOP_OPEN = 51;
	public static final int ERR_FILEOP_CLOSE = 52;
	public static final int ERR_FILEOP_READ = 53;
	public static final int ERR_FILEOP_WRITE = 54;
	public static final int ERR_FILE_NOTFOUND = 55;
	public static final int ERR_FILEOP_MOVE = 56;
	public static final int ERR_URL_MALFORMED = 110;
	public static final int ERR_NET_OPENSTREAM = 111;
	public static final int ERR_XMLFILE_PARSE = 150;
	public static final int ERR_XMLSTRUCTURE_NOTROOT = 151;
	public static final int ERR_XMLELEMENT_NOTFOUND = 152;
	public static final int ERR_XML_PROPERTY_REQUIRED = 153;
	public static final int ERR_XMLSTRING_PARSE = 154;
	public static final int ERR_ZIP_UNZIP = 200;
	public static final int ERR_PUBLISH = 220;
	public static final int ERR_PUBLISH_SERVER_NOTSTARTED = 221;
	public static final int ERR_PUBLISH_UPDATE_QUOTING_FOLDERS = 222;
	public static final int ERR_PUBLISH_TASK_STILLOPEN = 223;
	public static final int ERR_PUBLISH_CREATE_TASK = 224;
	public static final int ERR_PUBLISH_CANCEL_TASK = 225;
	public static final int ERR_PUBLISH_UNKNOWN_FOLDER_TYPE = 226;
	public static final int ERR_PUBLISH_UNKNOWN_CONTENT_TYPE = 227;
	public static final int ERR_PUBLISH_UNKNOWN_PUBLISH_TYPE = 228;
	public static final int ERR_PUBLISH_ANALYZE_DETAIL_PAGE_TASK = 229;
	public static final int ERR_PUBLISH_OUTLINE_TEMPLATE_REQUIRED = 230;
	public static final int ERR_PUBLISH_DETAIL_TEMPLATE_REQUIRED = 231;
	public static final int ERR_PUBLISH_TAGDOC_GRAMMAR_ILLEGAL = 232;
	public static final int ERR_PUBLISH_TEMPLATE_SEMANTIC_ANALYSE = 233;
	public static final int ERR_PUBLISH_TAG_PARSE = 234;
	public static final int ERR_PUBLISH_TAG_PROPERTY_VALUE_INVALID = 235;
	public static final int ERR_PUBLISH_FIlE_DISTRIBUTE = 236;
	public static final int ERR_PUBLISH_PAGE_GENERATE = 237;
	public static final int ERR_WCMEXCEPTION = 1100;
	public static final int ERR_MESSAGE = 1008;
	public static final int ERR_USER_NOTLOGIN = 1001;
	public static final int ERR_USER_NORIGHT = 1002;
	public static final int ERR_OBJ_NOTFOUND = 1011;
	public static final int ERR_OBJ_NOTLOCKED = 1012;
	public static final int ERR_OBJ_LOCKED = 1013;
	public static final int ERR_PROPERTY_NOT_EXIST = 1101;
	public static final int ERR_PROPERTY_NOTALLOW_EDIT = 1102;
	public static final int ERR_PROPERTY_TYPE_INVALID = 1103;
	public static final int ERR_PROPERTY_VALUE_INVALID = 1104;
	public static final int ERR_NEWOBJ_INVALID = 1105;
	public static final int ERR_PROPERTY_NOT_SET = 1106;
	public static final int ERR_PROPERTY_NOT_MODIFIED = 1107;
	public static final int ERR_PROPERTY_VALUE_DUPLICATED = 1108;
	public static final int ERR_IMPORTFILE_INVALID = 1200;
	public static final int ERR_DOC_DUPLICATED = 1300;
	public static final int ERR_INSTANCE_INVALID = 1351;
	public static final int ERR_SEND_NOTIFICATION = 1352;
	public static final int ERR_REFRESH_NOTIFICATION_SERVER = 1353;
	public static final int ERR_MESSAGE_UNKNOWN_SENDTYPE = 1354;
	public static final int ERR_SYS_OBJECT_NOT_FOUND = 1100;
	public static final int ERR_SYS_PARAM_NOT_SET = 1101;
	public static final int ERR_SYS_PARAM_INVALID = 1102;
	public static final int ERR_SYS_RUNTIME = 1103;
	public static final int ERR_HOST_NOT_FOUND = 101100;
	public static final int ERR_TEMPLATE_NOT_FOUND = 102100;
	public static final int ERR_TEMPLATE_HOST_NOT_FOUND = 102101;
	public static final int ERR_FACTORY_CONFIG = 1400;

	/**
	 * 获取错误编号，转变为String类型
	 * 
	 * @param _nExceptionNum
	 * @return
	 */
	public static String getErrNoMsg(int _nExceptionNum) {
		String sExceptionMsg = ExceptionMessages.getString(_nExceptionNum);
		if (sExceptionMsg == null) {
			return ExceptionMessages.getString(0);
		}
		return sExceptionMsg;
	}
}