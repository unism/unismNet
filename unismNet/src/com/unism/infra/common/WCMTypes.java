package com.unism.infra.common;

public class WCMTypes {
	public static final int YES = 1;
	public static final int NO = 0;
	public static final int XML_IMPORT_NEW = 1;
	public static final int XML_IMPORT_UPDATE = 2;
	public static final int XML_IMPORT_RESTORE = 3;
	public static final int REMIND_ONLINE = 0;
	public static final int REMIND_BYEMAIL = 1;
	public static final int REMIND_BYMOBILE = 2;
	public static final int TEMPLATE_COLUMN_TAG = 1;
	public static final int TEMPLATE_OUTLINE_TAG = 2;
	public static final int TEMPLATE_RECORD_TAG = 4;
	public static final int TEMPLATE_DEAL_MODE_UPDATE = 1;
	public static final int TEMPLATE_DEAL_MODE_IGNORE = 2;
	public static final int TEMPLATE_DEAL_MODE_CHANGE = 3;
	public static final int TEMPLATE_DEAL_MODE_QUIT = 4;
	public static final int TEMPLATEAPD_DEAL_MODE_UPDATE = 1;
	public static final int TEMPLATEAPD_DEAL_MODE_IGNORE = 2;
	public static final int TEMPLATEAPD_DEAL_MODE_CHANGE = 3;
	public static final int TEMPLATEAPD_DEAL_MODE_QUIT = 4;
	public static final int REMIND_NO = 0; 
	public static final int REMIND_YES = 1;
	public static final int REMIND_DONE = 2;
	public static final int OBJ_UNKNOWN = -1;
	public static final int OBJ_OTHER = 0;
	public static final int OBJ_FIELDINFO = 1;
	public static final int OBJ_TABLEINFO = 2;
	public static final int OBJ_WCMID = 3;
	public static final int OBJ_EXTENDEDFIELD = 4;
	public static final int OBJ_WCMOBJTRIGGER = 5;
	public static final int OBJ_CHANNEL = 101;
	public static final int OBJ_WEBSITE = 103;
	public static final int OBJ_REPLACE = 105;
	public static final int OBJ_CONTENTLINK = 106;
	public static final int OBJ_CHANNELSYN = 107;
	public static final int OBJ_GROUP = 201;
	public static final int OBJ_RIGHT = 202;
	public static final int OBJ_ROLE = 203;
	public static final int OBJ_USER = 204;
	public static final int OBJ_RIGHTDEF = 205;
	public static final int OBJ_TRUSTEEINFO = 206;
	public static final int OBJ_ADDRESS = 301;
	public static final int OBJ_ADDRESSGRP = 302;
	public static final int OBJ_BOOKMARK = 303;
	public static final int OBJ_CONTACT = 304;
	public static final int OBJ_CONTACTGRP = 305;
	public static final int OBJ_EVENT = 306;
	public static final int OBJ_MARKKIND = 307;
	public static final int OBJ_MARKSHARE = 308;
	public static final int OBJ_MESSAGE = 309;
	public static final int OBJ_MSGQUEUE = 310;
	public static final int OBJ_TASK = 311;
	public static final int OBJ_TASKPOOL = 312;
	public static final int OBJ_EVENTTYPE = 313;
	public static final int OBJ_USERSETTING = 314;
	public static final int OBJ_RECENT = 315;
	public static final int OBJ_FLOW = 401;
	public static final int OBJ_FLOWACTION = 402;
	public static final int OBJ_FLOWNODE = 403;
	public static final int OBJ_FLOWNODEBRANCH = 404;
	public static final int OBJ_FLOWDOC = 405;
	public static final int OBJ_FLOWDOCBAK = 406;
	public static final int OBJ_FLOWNODEEVENT = 407;
	public static final int OBJ_FLOWEVENTCONDITION = 408;
	public static final int OBJ_FLOWEVENTOPERATE = 409;
	public static final int OBJ_OPERATIONBEAN = 410;
	public static final int OBJ_CONDITIONBEAN = 411;
	public static final int OBJ_SCHEDULE = 501;
	public static final int OBJ_APPENDIX = 601;
	public static final int OBJ_DOCKIND = 602;
	public static final int OBJ_DOCREPLY = 603;
	public static final int OBJ_FILETYPE = 604;
	public static final int OBJ_DOCUMENT = 605;
	public static final int OBJ_EXPIRATION = 606;
	public static final int OBJ_SECURITY = 607;
	public static final int OBJ_SOURCE = 608;
	public static final int OBJ_RELATION = 609;
	public static final int OBJ_STATUS = 610;
	public static final int OBJ_DOCBAK = 611;
	public static final int OBJ_WCMEXCELDATA = 612;
	public static final int OBJ_HITSCOUNT = 613;
	public static final int OBJ_CHNLDOC = 614;
	public static final int OBJ_CONFIG = 701;
	public static final int OBJ_LOG = 702;
	public static final int OBJ_LOGTYPE = 703;
	public static final int OBJ_OPERTYPE = 704;
	public static final int OBJ_MEETINGROOM = 801;
	public static final int OBJ_MEETINGCONT = 802;
	public static final int OBJ_MEETINGUSER = 803;
	public static final int OBJ_BULLETIN = 804;
	public static final int OBJ_WCMRESULTSET = 888;
	public static final int OBJ_JOB = 901;
	public static final int OBJ_WCMFORMINFO = 1001;
	public static final int OBJ_WCMFORMFIELDS = 1002;
	public static final int OBJ_BIGTABLE = 1101;
	public static final int OBJ_BIGTABLEFIELD = 1102;
	public static final int OBJ_BIGTABLEMPLOY = 1103;
	public static final int OBJ_INFOVIEW = 1104;
	public static final int OBJ_INFOVIEWFIELD = 1105;
	public static final int OBJ_INFOVIEWEMPLOY = 1106;
	public static final int OBJ_INFOVIEWGROUP = 1107;
	public static final int OBJ_INFOVIEWVIEW = 1108;
	public static final int OBJ_USERDEF = 2000;
	public static final int OPER_UNKOWN = -1;
	public static final int OPER_OTHER = 0;
	public static final int OPER_SYSTEM_SET = 1;
	public static final int OPER_SITE_ADD = 101;
	public static final int OPER_SITE_EDIT = 102;
	public static final int OPER_SITE_DEL = 103;
	public static final int OPER_SITE_PUB = 104;
	public static final int OPER_SITE_RECYCLE = 105;
	public static final int OPER_SITE_RESCOVER = 106;
	public static final int OPER_SCHEDULE_PUB = 107;
	public static final int OPER_CHNL_ADD = 201;
	public static final int OPER_CHNL_EDIT = 202;
	public static final int OPER_CHNL_DEL = 203;
	public static final int OPER_CHNL_PUB = 204;
	public static final int OPER_CHNL_RECYCLE = 205;
	public static final int OPER_CHNL_RESCOVER = 206;
	public static final int OPER_DOC_ADD = 301;
	public static final int OPER_DOC_EDIT = 302;
	public static final int OPER_DOC_DEL = 303;
	public static final int OPER_DOC_PUB = 304;
	public static final int OPER_FLOW_ADD = 401;
	public static final int OPER_FLOW_EDIT = 402;
	public static final int OPER_FLOW_DEL = 403;
	public static final int OPER_TEMPLATE_ADD = 501;
	public static final int OPER_TEMPLATE_EDIT = 502;
	public static final int OPER_TEMPLATE_DEL = 503;
	public static final int OPER_USER_LOGIN = 601;
	public static final int OPER_USER_LOGOUT = 602;
	public static final int OPER_SYSTEM_WARN = 603;
	public static final int OPER_USER_DEFINE = 2000;
	public static final int LOG_UNKOWN = -1;
	public static final int LOG_SYSTEM = 1;
	public static final int LOG_SECURITY = 2;
	public static final int LOG_FLOW = 3;
	public static final int LOG_PUB = 4;
	public static final int LOG_JOB = 5;
	public static final int LOG_EXCEPTION = 6;
	public static final int LOG_OTHER = 7;
	public static final int LOG_SCHEDULE_PUB = 8;
	public static final int PUBLISH_NOT_NEED = 0;
	public static final int PUBLISH_SELF_NEED = 1;
	public static final int PUBLISH_CHILD_NEED = 2;
	public static final int MESSAGE_UNREAD = 0;
	public static final int MESSAGE_SENT = 1;
	public static final int MESSAGE_RECEIVED = 2;

	public static String getObjName(int _objType, boolean _bChineseName) {
		switch (_objType) {
		case 1:
			return _bChineseName ? "字段信息" : "FieldInfo";
		case 2:
			return _bChineseName ? "表信息" : "TableInfo";
		case 3:
			return _bChineseName ? "ID对象" : "WCMId";
		case 4:
			return _bChineseName ? "扩展字段对象" : "ExtendedField";
		case 5:
			return _bChineseName ? "系统触发器" : "WCMObjTrigger";
		case 101:
			return _bChineseName ? "栏目" : "Channel";
		case 103:
			return _bChineseName ? "站点" : "WebSite";
		case 105:
			return _bChineseName ? "替换内容" : "Replace";
		case 106:
			return _bChineseName ? "内容超链接" : "ContentLink";
		case 107:
			return _bChineseName ? "同步配置" : "ChannelSyn";
		case 201:
			return _bChineseName ? "组" : "Group";
		case 202:
			return _bChineseName ? "权限" : "Right";
		case 203:
			return _bChineseName ? "角色" : "Role";
		case 204:
			return _bChineseName ? "用户" : "User";
		case 205:
			return _bChineseName ? "权限定义" : "RightDef";
		case 206:
			return _bChineseName ? "托管信息" : "TrusteeInfo";
		case 301:
			return _bChineseName ? "通信地址" : "Address";
		case 302:
			return _bChineseName ? "通信地址组" : "AddressGrp";
		case 303:
			return _bChineseName ? "书签" : "BookMark";
		case 304:
			return _bChineseName ? "联系人" : "Contact";
		case 305:
			return _bChineseName ? "联系人组" : "ContactGrp";
		case 306:
			return _bChineseName ? "事件" : "Event";
		case 307:
			return _bChineseName ? "书签类别" : "MarkKind";
		case 308:
			return _bChineseName ? "书签共享" : "MarkShare";
		case 309:
			return _bChineseName ? "消息" : "Message";
		case 310:
			return _bChineseName ? "消息队列" : "MsgQueue";
		case 311:
			return _bChineseName ? "任务" : "Task";
		case 312:
			return _bChineseName ? "任务队列" : "TaskPool";
		case 313:
			return _bChineseName ? "事件类型" : "EventType";
		case 314:
			return _bChineseName ? "个性化定制" : "UserSetting";
		case 401:
			return _bChineseName ? "工作流" : "Flow";
		case 402:
			return _bChineseName ? "工作流动作" : "FlowAction";
		case 403:
			return _bChineseName ? "工作流节点" : "FlowNode";
		case 404:
			return _bChineseName ? "工作流节点的分支" : "FlowNodeBranch";
		case 405:
			return _bChineseName ? "工作流流转情况" : "FlowDoc";
		case 406:
			return _bChineseName ? "工作流流转情况" : "FlowDocBak";
		case 407:
			return _bChineseName ? "工作流节点事件" : "FlowNodeEvent";
		case 408:
			return _bChineseName ? "工作流节点事件条件" : "FlowEventCondition";
		case 409:
			return _bChineseName ? "工作流节点事件操作" : "FlowEventOperate";
		case 411:
			return _bChineseName ? "工作流条件系统配置操作" : "ConditionBean";
		case 410:
			return _bChineseName ? "工作流操作系统配置操作" : "OperationBean";
		case 501:
			return _bChineseName ? "计划" : "Schedule";
		case 601:
			return _bChineseName ? "附件" : "Appendix";
		case 602:
			return _bChineseName ? "文档分类" : "DocKind";
		case 603:
			return _bChineseName ? "文档回复" : "DocReply";
		case 604:
			return _bChineseName ? "文件类型" : "FileType";
		case 605:
			return _bChineseName ? "文档" : "Document";
		case 606:
			return _bChineseName ? "文档过期" : "Expiration";
		case 607:
			return _bChineseName ? "文档安全" : "Security";
		case 608:
			return _bChineseName ? "文档来源" : "Source";
		case 609:
			return _bChineseName ? "相关文档" : "Relation";
		case 610:
			return _bChineseName ? "文档状态" : "Status";
		case 611:
			return _bChineseName ? "文档备份" : "DocBak";
		case 612:
			return _bChineseName ? "WCM电子表格对象" : "WCMExcelData";
		case 701:
			return _bChineseName ? "配置" : "Config";
		case 702:
			return _bChineseName ? "日志" : "Log";
		case 703:
			return _bChineseName ? "日志类型" : "LogType";
		case 704:
			return _bChineseName ? "操作类型" : "OperType";
		case 801:
			return _bChineseName ? "会议室" : "MeetingRoom";
		case 802:
			return _bChineseName ? "会议内容" : "MeetingCont";
		case 803:
			return _bChineseName ? "会议用户" : "MeetingUser";
		case 804:
			return _bChineseName ? "公告留言" : "Bulletin";
		case 901:
			return _bChineseName ? "工作" : "Job";
		case 1001:
			return _bChineseName ? "用户自定义表单" : "WCMFormInfo";
		case 1002:
			return _bChineseName ? "用户自定义表单中的字段信息" : "WCMFormFields";
		}

		return _bChineseName ? "未知" : "UNKNOWN";
	}

	public static String getOperName(int _operType) {
		switch (_operType) {
		case 1:
			return "修改系统配置";
		case 101:
			return "添加站点";
		case 102:
			return "修改站点";
		case 103:
			return "删除站点";
		case 104:
			return "发布站点";
		case 105:
			return "放入回收站";
		case 106:
			return "恢复站点";
		case 201:
			return "添加频道";
		case 202:
			return "修改频道";
		case 203:
			return "删除频道";
		case 204:
			return "发布频道";
		case 205:
			return "放入回收站";
		case 206:
			return "恢复站点";
		case 301:
			return "添加文档";
		case 302:
			return "修改文档";
		case 303:
			return "删除文档";
		case 304:
			return "发布文档";
		case 401:
			return "添加工作流";
		case 402:
			return "修改工作流";
		case 403:
			return "删除工作流";
		case 501:
			return "添加模板";
		case 502:
			return "修改模板";
		case 503:
			return "删除模板";
		case 601:
			return "登录系统";
		case 602:
			return "退出系统";
		case 107:
			return "计划发布";
		}

		return "未知";
	}
}