<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/share/taglib.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/control/product/";
%>
<script>
	$(function() {
		$('#proform').hide();
		$('#pro')
				.datagrid(
						{
							title : '',
							iconCls : 'icon-save',
							height : 620,
							nowrap : false,
							striped : true,
							collapsible : true,
							url : '/unismNet/control/product/list.action',
							sortName : 'name',
							sortOrder : 'desc',
							remoteSort : false,
							idField : '编号',
							pagination : true,
							rownumbers : true,
							fitColumns:true,
							frozenColumns : [ [ {
								field : 'ck',
								checkbox : true
							}, {
								title : '编号',
								field : 'typeid',
								width : 50,
								sortable : true
							} ] ],
							columns : [
									[ {
										title : '基本信息',
										colspan : 5
									}, {
										title : '操作',
										colspan : 3
									} ],
									[
											{
												field : 'name',
												title : '产品名称',
												width : 120
											},
											{
												field : 'note',
												title : '产品描述',
												width : 270,
												rowspan : 2,
												sortable : true,
												sorter : function(a, b) {
													return (a > b ? 1 : -1);
												}
											},
											{
												field : 'pic',
												title : '产品图片',
												width : 120,
												rowspan : 2,
												sortable : true,
												sorter : function(a, b) {
													return (a > b ? 1 : -1);
												}
											},
											{
												field : 'childType',
												title : '创建下级类别',
												width : 100,
												rowspan : 2,
												sortable : true,
												sorter : function(a, b) {
													return (a > b ? 1 : -1);
												},
												formatter : function(value, rec) {
													return '<a href="javascript:"><span style="color:green">创建子类别</span></a>';
												}
											},
											{
												field : 'parent',
												title : '父级产品',
												width : 150,
												rowspan : 2
											},
											
	{
												field : 'look',
												title : '查看子类',
												width : 100,
												align : 'center',
												rowspan : 2,
												formatter : function(value, rec) {
													return '<a href="javascript:"><span style="color:blue">查看子类</span></a>';
												}
											},
											{
												field : 'edits',
												title : '编辑',
												width : 50,
												align : 'center',
												rowspan : 2,
												formatter : function(value, rec) {
													return '<a href="javascript:sigedit('
															+ rec.typeid
															+ ')"><span style="color:red">编辑</span></a>';
												}
											},{
												field : 'dels',
												title : '删除',
												width : 50,
												align : 'center',
												rowspan : 2,
												formatter : function(value, rec) {
													return '<a href="javascript:del('
															+ rec.typeid
															+ ')"><span style="color:blue">删除</span></a>';
												}
											} ]

							],

							toolbar : [ {
								id : 'btnadd',
								text : '添加',
								iconCls : 'icon-add',
								handler : function() {
									$('#btnsave').linkbutton('enable');
									$('#add').window('open');
									$('#proform').show();
									$('#proform').form('clear');
									$('#proform').appendTo('#aa');
									addWindow();
								}
							}, '-', {
								id : 'btnedit',
								text : '修改',
								iconCls : 'icon-edit',
								handler : function() {
									editWindow();
								}
							}, '-', {
								id : 'btncancel',
								text : '删除',
								iconCls : 'icon-cancel',
								handler : function() {
									//getSelectIds("pro","所选行为空");//
									delWindow();
								}
							}, '-', {
								id : 'btnsearch',
								text : '查询',
								iconCls : 'icon-search',
								handler : function() {
									$('#btnsearch').linkbutton('enable');
									$('#query').window('open');
								}
							} ]
						});
		var p = $('#pro').datagrid('getPager');
		$(p).pagination({
			pageSize : 15,//每页显示的记录条数，默认为10  
			pageList : [ 5, 10, 15, 20, 25, 30 ],//可以设置每页记录条数的列表  
			beforePageText : '第',//页数文本框前显示的汉字  
			afterPageText : "共{pages}页",
			displayMsg : "共{total}条记录"
		});
		var fieldName = 'name'; // the field name
		var opts = $('#pro').datagrid('getColumnOption', fieldName); // get the specified column option
		console.log("c:" + opts.field);// the title to display
	});
	function resize() {
		$('#pro').datagrid('resize', {
			height : 800
		});
	}

	function displayMsg() {
		$('#pro').datagrid('getPager').pagination({
			displayMsg : '当前显示从{from}到{to}共{total}记录'
		});
	}
	function close1() {
		$('#add').window('close');
	}
	function close2() {
		$('#edit').window('close');
	}
	function add() {
		$('#proform').form('submit', {
			url : '',
			onSubmit : function() {
				return $('#proform').form('validate');
			},
			success : function() {
				close1();
			}
		});
		$.messager.alert('add', '添加信息成功!!!', 'info', function() {
			$('#pro').datagrid({
				url : 'easyAction.action',
				loadMsg : '更新数据中......'
			});
			displayMsg();
		});
	}

	function findbyid() {
		var row = $('#pro').datagrid('getSelected');
		if (row) {
			$.ajax({
				type : "GET",
				url : "/unismNet/control/product/pro_getproductbyid.action",///unismNet/control/center/datagrid.action
				data : "typeid=" + row.typeid,
				dataType : "json",
				contentType : "application/json; charset=utf-8",
				success : function(data) {
					$('#name').attr("value", data.name);
					$('#note').attr("value", data.note);
				}
			});
		}
	}
	function sigedit(id) {
		$('#proform').form('clear');
		var selected = $('#pro').datagrid('getSelected');
		$.ajax({
			type : "GET",
			url : "/unismNet/control/product/pro_getproductbyid.action",
			data : "typeid=" + selected.typeid,
			dataType : "json",
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				$('#edit').window({
					title : "修改记录",
					collapsible : true,
					closable : true,
					draggable : true,
					resizable : true,
					modal : true,
					shadow : true,
					inline : true,
					minimizable : false,
					maximizable : true
				});
				$('#edit').window('open');
				$('#proform').show();
				$('#proform').form('clear');
				$('#proform').appendTo('#ee');
				$('#name').attr("value", data.name);
				$('#note').attr("value", data.note);
			}

		});
		editWindow();
	}
	function del(id) {
		var selected = $('#pro').datagrid('getSelected');
		if (selected) {
			$.messager.confirm('产品类型删除确认', '是否确认删除?', function() {
				if (id) {
					id = selected.id;
					$.ajax({
						type : "POST",
						url : "/unismNet/control/product/pro_del.action",
						data : "typeid=" + selected.typeid,
						dataType : "json",
						success : function callback() {
						}
					});
					$('#pro').datagrid('reload');
				}
			});
		} else {
			$.messager.alert('warning', '请选择一行数据', 'warning');
		}
	}
	function editWindow() {

		var row = $('#pro').datagrid('getSelected');
		if (row.typeid) {
			$('#btnedit').linkbutton('enable');
			$('#edit').window({
				title : "修改记录",
				collapsible : true,
				closable : true,
				draggable : true,
				resizable : true,
				modal : true,
				shadow : true,
				inline : true,
				minimizable : false,
				maximizable : true
			});
			$('#edit').window('open');
			$('#proform').form('clear');
			$('#proform').show();
			$('#proform').appendTo('#ee');
			$('#proform').form(
					'load',
					'/unismNet/control/product/pro_getproductbyid.action?typeid='
							+ row.typeid);
			$('.icon-ok')
					.unbind('click')
					.removeAttr('onclick')
					.click(
							function() {
								$('#proform')
										.form(
												'submit',
												{
													url : '/unismNet/control/product/pro_edit.action?typeid='
															+ row.typeid,
													onSubmit : function() {
													},
													success : function(data) {
														if (data) {
															$('#pro').datagrid(
																	'reload');
															$('#edit').window(
																	'close');
														} else {
															$.messager.alert(
																	'错误',
																	'信息更新错误',
																	'error');
														}
													}
												});
							});
		} else {
			$.messager.show({
				title : '警告',
				msg : '请先选择要修改的记录。'
			});
		}
	}
	//添加方法
	function addWindow() {
		$('#proform').form('clear');
		$('.icon-ok').unbind('click').removeAttr('onclick').click(function() {
			$('#proform').form('submit', {
				url : '/unismNet/control/product/pro_add.action',
				onSubmit : function() {
				},
				success : function(data) {
					if (data) {
						$('#pro').datagrid('reload');
						$('#add').window('close');
					} else {
						$.messager.alert('错误', data, 'error');
					}
				}
			});
		});
	}
	function delWindow() {
		var ids = [];
		var rows = $('#pro').datagrid('getSelections');
		console.log(rows.length);
		if (rows != '') {
			for ( var i = 0; i < rows.length; i++) {
				alert(rows[i].typeid);
				ids.push(rows[i].typeid);
			}
			ids.join(',');
			$.messager.confirm('产品类型删除确认', '是否确认删除?', function(data) {
				if (data) {
					$.ajax({
						type : "POST",
						url : "/unismNet/control/product/pro_del.action",
						data : "typeid=" + ids,
						dataType : "json",
						success : function(data) {
							if (data) {
								$('#pro').datagrid('reload');
							} else {
								$.messager.alert('错误', data, 'error');
							}
						}
					});
					$('#pro').datagrid('reload');
				}
			});
		} else {
			$.messager.show({
				title : '警告',
				msg : '请先选择要删除的记录。'
			});
		}
	}

	function getSelectIds(dataTableId, noOneSelectMessage) {
		var rows = $('#' + dataTableId).datagrid('getSelections');
		var num = rows.length;
		var ids = null;
		if (num < 1) {
			if (null != noOneSelectMessage)
				$.messager.alert('提示消息', noOneSelectMessage, 'info');
			return null;
		} else {
			for ( var i = 0; i < num; i++) {
				if (null == ids || i == 0) {
					ids = rows[i].typeid;
				} else {
					ids = ids + "," + rows[i].typeid;
				}
			}
			alert(ids);
			return ids;
		}
	}
</script>

<table id="pro"></table>

<form id="proform" method="post">
	<div>
		<input class="easyui-validatebox" type="hidden" id="parentid"
			name="parentid" value="0"></input>
	</div>
	<div>
		产品名称:<input class="easyui-validatebox" type="text" id="name"
			name="name" value="" required="true"></input>
	</div>
	<div>
		产品描述:<input class="easyui-validatebox" type="text" id="note"
			name="note" value="" required="false"></input>
	</div>
</form>

<div id="add" class="easyui-window" title="添加"
	style="padding: 10px;width: 300;height:200;" iconCls="icon-add"
	closed="true" maximizable="false" minimizable="false"
	collapsible="false">
	<div id="aa"></div>
	<a class="easyui-linkbutton" iconCls="icon-ok"
		href="javascript:void(0)">添加</a> <a class="easyui-linkbutton"
		iconCls="icon-cancel" href="javascript:void(0)" onclick="close1()">取消</a>
</div>
<div id="edit" class="easyui-window" title="修改"
	style="padding: 10px;width: 300;height: 200;" iconCls="icon-edit"
	closed="true" maximizable="false" minimizable="false"
	collapsible="false">
	<div id="ee"></div>
	<a class="easyui-linkbutton" iconCls="icon-ok"
		href="javascript:void(0)">修改</a> <a class="easyui-linkbutton"
		iconCls="icon-cancel" href="javascript:void(0)" onclick="close2()">取消</a>
</div>