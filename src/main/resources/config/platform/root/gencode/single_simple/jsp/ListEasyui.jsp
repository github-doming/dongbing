<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/pages/cosmos/lib/third/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/pages/cosmos/lib/third/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/pages/cosmos/lib/third/easyui/demo/demo.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/cosmos/lib/third/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/cosmos/lib/third/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<!-- 表格 -->
	<table id="dg" title="${ay_table_comment} 管理" class="easyui-datagrid"
		style="width: 100%; height: 100%"
		url="${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/listJson.do"
		toolbar="#toolbar" rownumbers="true" fitColumns="true"
		singleSelect="true" pagination="true">
		<thead>
			<tr>
#foreach( $name in $ay_list_column )
<th field="${name.columnName}" width="50"><%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%></th>
#end
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
			onclick="newObject()">新建</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true" onclick="editObject()">编辑</a> <a
			href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
			onclick="delObject()">删除</a>
	</div>
	<!-- 表单 -->
	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle"></div>
		<form id="fm" method="post">
#foreach( $name in $ay_list_column )
<div class="fitem">
<label><%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%>:</label> 
<input name="${name.columnName}" class="easyui-validatebox" required="false">
</div>
#end
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
			onclick="saveObject()">保存</a> <a href="#" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
</html>
<script type="text/javascript">
	//新增	
	function newObject() {
		$('#dlg').dialog('open').dialog('setTitle', '新建');
		$('#fm').form('clear');
		url="${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/saveJson.do";
	}
	//编辑	
	function editObject() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '编辑');
			$('#fm').form('load', row);
			url="${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/saveJson.do";
			url = url + '?id=' + row.${columnNamePk};
			//alert('url=' + url);
		}
	}
	//保存
	function saveObject() {
		$('#fm').form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.errorMsg) {
					$.messager.show({
						title : 'Error',
						msg : result.errorMsg
					});
				} else {
					// close the dialog
					$('#dlg').dialog('close'); 
					 // reload the Object data
					$('#dg').datagrid('reload');
				}
			}
		});
	}
	//删除
	function delObject() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			var url="${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/delJson.do?id="
					+ row.${columnNamePk};
			$.messager.confirm('Confirm','你确定删除吗',function(r) {
				if (r) {
	
				$.post(url, {}, function(result) {
				// $('#dg').datagrid('reload');  
				if (result.success) {
				// 重新加载
				$('#dg').datagrid('reload');
				
				} else {
				// 显示错误信息
				$.messager.show({
				
				title : 'Error',
				msg : result.message
				});
				}
	
				}, 'json');

				}
			});

		}
}
</script>
