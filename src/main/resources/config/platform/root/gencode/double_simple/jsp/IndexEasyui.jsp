<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="zh-CN">
<head>
<title> </title>
<%@ include file="/pages/all/ui/easyui/common/common.jsp"%>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

</head>
<body>
<!-- 布局 -->
	<div class="easyui-layout" fit="true">
		<div data-options="region:'north'" style="height: 5px"></div>
		<div data-options="region:'south',split:true" style="height: 5px;"></div>
		<div data-options="region:'west',split:true" title=" "
			style="width: 200px;">
			<ul id='tree_id' class="easyui-tree"
				data-options="url:'${ay_s}{pageContext.request.contextPath}/${first_pages_name_all}/${first_table_name_module}/${first_table_name_child}/listJson.do',method:'get',animate:true,dnd:false"></ul>
		</div>
		<div data-options="region:'center',title:' '">
			<div id='tab_id' class="easyui-tabs"
				data-options="fit:true,border:false,plain:true">
	<!-- 表格 -->
	<table id="dg" title="管理" class="easyui-datagrid"
		style="width: 100%; height: 300px"
		url="${pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/listJson.do"
		toolbar="#toolbar" rownumbers="true" fitColumns="true"
		singleSelect="true" pagination="true">
		<thead>
			<tr>
#foreach( $name in $second_list_column )
<th field="${name.columnName}" width="50"><%=${second_package_name_all}.alias.${second_table_class}Alias . ${name.fieldName}%></th>
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
</div></div></div>	
	<!-- 表单 -->
	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle"></div>
		<form id="fm" method="post">
#foreach( $name in  $second_list_column )
<div class="fitem">
<label><%=${second_package_name_all}.alias.${second_table_class}Alias . ${name.fieldName}%>:</label> 
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
		url="${pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/saveJson.do";
	}
	//编辑	
	function editObject() {
		var row = $('#dg').datagrid('getSelected');
		if (row) {
			$('#dlg').dialog('open').dialog('setTitle', '编辑');
			$('#fm').form('load', row);
			url="${pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/saveJson.do";
			url = url + '?id=' + row.${second_columnNamePk};
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
			var url="${pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/delJson.do?id="
					+ row.${second_columnNamePk};
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
<script type="text/javascript">
$('#tree_id').tree({
	onClick : function(node) {
		//alert(node.id);  
		$('#dg').datagrid('load',{
	 	treeId: node.id
		});
	}
});
</script>

