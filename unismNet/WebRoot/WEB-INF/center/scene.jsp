<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/share/taglib.jsp"%>

<script>
	$(function() {
		$('#test').datagrid({
			title : '',
			iconCls : 'icon-save',
			height : 620,
			nowrap : false,
			striped : true,
			collapsible : true,
			url : '/unismNet/control/scene/execute',
			sortName : 'sceneDesc',
			sortOrder : 'desc',
			remoteSort : false,
			idField : '编号',
			frozenColumns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				title : '编号',
				field : 'sceneId',
				width : 250,
				sortable : true
			} ] ],
			columns : [ [ {
				title : '基本信息',
				colspan : 3
			}, {
				field : 'opt',
				title : '操作',
				width : 100,
				align : 'center',
				rowspan : 2,
				formatter : function(value, rec) {
					return '<span style="color:red">编辑 删除</span>';
				}
			} ], [ {
				field : 'sceneName',
				title : '场景名称',
				width : 120
			}, {
				field : 'sceneAddr',
				title : '场景地址',
				width : 220,
				rowspan : 2,
				sortable : true,
				sorter : function(a, b) {
					return (a > b ? 1 : -1);
				}
			}, {
				field : 'sceneDesc',
				title : '场景描述',
				width : 150,
				rowspan : 2
			} ] ],
			pagination : true,
			rownumbers : true,
			toolbar : [ {
				id : 'btnadd',
				text : '添加',
				iconCls : 'icon-add',
				handler : function() {
					$('#btnsave').linkbutton('enable');
					alert('add')
				}
			}, {
				id : 'btncut',
				text : '剪切',
				iconCls : 'icon-cut',
				handler : function() {
					$('#btnsave').linkbutton('enable');
					alert('cut')
				}
			}, '-', {
				id : 'btnsave',
				text : '保存',
				disabled : false,
				iconCls : 'icon-save',
				handler : function() {
					$('#btnsave').linkbutton('disable');
					alert('save')
				}
			} ]
		});
		var p = $('#test').datagrid('getPager');
		$(p).pagination({
			/*
				onBeforeRefresh : function() {
					alert('before refresh');
				}
			 */
			pageSize : 10,//每页显示的记录条数，默认为10  
			pageList : [ 5, 10, 15, 20,25,30],//可以设置每页记录条数的列表  
			beforePageText : '第',//页数文本框前显示的汉字  
            afterPageText:"共{pages}页",
            displayMsg:"共{total}条记录"
		});
	});
	function resize() {
		$('#test').datagrid('resize', {
			height : 800
		});
	}
</script>

<table id="test"></table>

