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
	href="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/easyui/demo/demo.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<!-- 表格 -->
	<table id="dg" title="后台字节表SYS_BYTES 管理" class="easyui-datagrid"
		style="width: 100%; height: 100%"
		url="${pageContext.request.contextPath}/all/upload/sys_bytes/t/listJson.do"
		toolbar="#toolbar" rownumbers="true" fitColumns="true"
		singleSelect="true" pagination="true">
		<thead>
			<tr>
<th field="IDX_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . idx%></th>
<th field="SYS_BYTES_ID_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . sysBytesId%></th>
<th field="SYS_FILE_ID_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . sysFileId%></th>
<th field="BYTES_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . bytes%></th>
<th field="NAME_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . name%></th>
<th field="TYPE_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . type%></th>
<th field="CREATE_USER_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . createUser%></th>
<th field="CREATE_TIME_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . createTime%></th>
<th field="CREATE_TIME_LONG_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . createTimeLong%></th>
<th field="UPDATE_USER_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . updateUser%></th>
<th field="UPDATE_TIME_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . updateTime%></th>
<th field="UPDATE_TIME_LONG_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . updateTimeLong%></th>
<th field="STATE_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . state%></th>
<th field="DESC_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . desc%></th>
<th field="CODE_" width="50"><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . code%></th>
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
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . idx%>:</label> 
<input name="IDX_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . sysBytesId%>:</label> 
<input name="SYS_BYTES_ID_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . sysFileId%>:</label> 
<input name="SYS_FILE_ID_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . bytes%>:</label> 
<input name="BYTES_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . name%>:</label> 
<input name="NAME_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . type%>:</label> 
<input name="TYPE_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . createUser%>:</label> 
<input name="CREATE_USER_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . createTime%>:</label> 
<input name="CREATE_TIME_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . createTimeLong%>:</label> 
<input name="CREATE_TIME_LONG_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . updateUser%>:</label> 
<input name="UPDATE_USER_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . updateTime%>:</label> 
<input name="UPDATE_TIME_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . updateTimeLong%>:</label> 
<input name="UPDATE_TIME_LONG_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . state%>:</label> 
<input name="STATE_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . desc%>:</label> 
<input name="DESC_" class="easyui-validatebox" required="false">
</div>
<div class="fitem">
<label><%=all.upload.sys_bytes.t.alias.SysBytesTAlias . code%>:</label> 
<input name="CODE_" class="easyui-validatebox" required="false">
</div>
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
		url="${pageContext.request.contextPath}/all/upload/sys_bytes/t/saveJson.do";
	}
	//编辑	
	function editObject() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '编辑');
			$('#fm').form('load', row);
			url="${pageContext.request.contextPath}/all/upload/sys_bytes/t/saveJson.do";
			url = url + '?id=' + row.SYS_BYTES_ID_;
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
			var url="${pageContext.request.contextPath}/all/upload/sys_bytes/t/delJson.do?id="
					+ row.SYS_BYTES_ID_;
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
