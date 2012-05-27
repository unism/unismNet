<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/share/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>联创思源${ctx}</title>
<link rel="stylesheet" type="text/css"
	href="${ctx }/easyUI/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${ctx }/easyUI/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/css.css">
<link rel="stylesheet" type="text/css" href="${ctx }/css/demo.css">

<script type="text/javascript" src="${ctx }/easyUI/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx }/js/datetime.js"></script>
<script type="text/javascript" src="${ctx }/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${ctx }/easyUI/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${ctx }/js/main.js"></script>
<script language='javascript' src='${ctx }/easyUI/jquery.cookie.js'></script>

<!-- <script language='javascript' src='${ctx }/js/weather.js'></script> -->
<style type="text/css">
body {
	padding: 0px;
	margin: 0px;
}
</style>
</head>
<body class="easyui-layout">
	<div region="north" title="" split="false" border="false"
		style="height:60px;background:#B3DFDA;">
		<!-- <script type="text/javascript" src="http://julying.com/lab/weather/get/?jquery=false&parentid=wea"></script> -->
		<img src="${ctx}/images/logo.jpg" height="60px" width=""
			title="unismWeb" />
	</div>

	<div region="west" iconCls="icon-reload" title="导航树" split="true"
		style="width:200px;">
		<ul class="easyui-tree" id="tt"
			url="${ctx}/control/center/json.action"></ul>
	</div>

	<div region="east" split="true" title=""
		style="width:200px;padding1:1px;overflow:hidden;">
		<div class="easyui-accordion" fit="true" border="false">
			<div title="用户管理" style="padding:10px;overflow:auto;">
				<ul class="easyui-tree" url="${ctx}/control/center/user.action"></ul>
			</div>
			<div title="组织管理" selected="true" style="padding:10px;">
				<ul class="easyui-tree" url="${ctx}/control/center/org.action"></ul>
			</div>
			<div title="角色管理" style="padding:10px">
				<ul class="easyui-tree" url="${ctx}/control/center/role.action"></ul>
			</div>
		</div>
	</div>

	<div class="ymd" region="center" title="首页" style="overflow:hidden;">

		<div id="easyui-tabs" class="easyui-tabs" fit="true" border="false">

			<div title="首页" style="padding:20px;overflow:hidden;">
				<div style="margin-top:20px;">
					<input type="button" value="提交" id="btn" />
				</div>
			</div>
		</div>




	</div>


	<div id="mm" class="easyui-menu" style="width:150px;">
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">关闭全部</div>
		<div id="mm-tabcloseother">关闭其他</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">关闭右侧标签</div>
		<div id="mm-tabcloseleft">关闭左侧标签</div>
	</div>

</body>
</html>
