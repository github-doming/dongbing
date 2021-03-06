<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script
	src="${pageContext.request.contextPath}/pages/cosmos/lib/third/miniui/boot.js"
	type="text/javascript"></script>

<script
	src="${pageContext.request.contextPath}/pages/cosmos/common/third/miniui/miniui.js"
	type="text/javascript"></script>
		
<title> ${ay_table_comment} 管理</title>
</head>
<body>
	<style type="text/css">
html,body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
</style>
	<div class="mini-toolbar" style="padding: 2px; border-bottom: 0;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a onclick="add()" class="mini-button"
					iconCls="icon-add" plain="true">增加</a> <a onclick="edit()"
					class="mini-button" iconCls="icon-add" plain="true">编辑</a> <a
					onclick="remove()" class="mini-button" iconCls="icon-remove"
					plain="true">删除</a> <span class="separator"></span> <a
					onclick="reload()" class="mini-button" iconCls="icon-reload"
					plain="true">刷新</a> <a onclick="query()" class="mini-button"
					iconCls="icon-search" plain="true">查询</a></td>
			</tr>
			<tr>
				<td><label style="font-family: Verdana;">名称: </label> <input
					class="mini-textbox" /></td>
			</tr>
		</table>
	</div>
	<!--撑满页面-->
	<div class="mini-fit">
		<div id="datagrid1" class="mini-datagrid"
			style="width: 100%; height: 100%;"
			url="${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/listJson.do"
			idField="UUID_" sizeList="[5,10,20,50]" pageSize="10" multiSelect="true">
			<div property="columns">
				<div type="checkcolumn"></div>
				
#foreach( $name in $ay_list_column )
#if ($name.sql_column_type=='DATETIME'||$name.sql_column_type=='DATE'||$name.sql_column_type=='TIME'||$name.sql_column_type=='TIMESTAMP')
<div field="${name.columnName}" width="100" headerAlign="center"  
allowSort="true"><%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%></div>
#else
<div field="${name.columnName}" width="100" headerAlign="center" 
dateFormat="yyyy-MM-dd" allowSort="true"><%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%></div>
#end
#end

			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		grid.load();
		function add() {
			var win = mini.open({
				url : "${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/form.do",
				title : "新增${ay_table_comment}",
				//width : 600,
				//height : 400,
				allowResize : true, //允许尺寸调节
				allowDrag : true, //允许拖拽位置
				showCloseButton : true, //显示关闭按钮
				showMaxButton : true, //显示最大化按钮
				showModal : true,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : "new"
					};
					//iframe.contentWindow.SetData(data);
				},
				ondestroy : function(action) {
					grid.reload();
				}
			});
			win.max();
		}
		function edit() {
			var row = grid.getSelected();
			if (row) {
				var id=row.${columnNamePk};
				//alert('id='+id);
				var win = mini.open({
					url : "${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/form.do?id="+id,
					title : "编辑${ay_table_comment}",
					width : 600,
					height : 400,
					allowResize : true, //允许尺寸调节
					allowDrag : true, //允许拖拽位置
					showCloseButton : true, //显示关闭按钮
					showMaxButton : true, //显示最大化按钮
					showModal : true,
					onload : function() {
						var iframe = this.getIFrameEl();
						var data = {
							action : "edit",
							id : row.id
						};
						//iframe.contentWindow.SetData(data);
					},
					ondestroy : function(action) {
						grid.reload();
					}
				});
				win.max();
			} else {
				alert("请选中一条记录");
			}
		}
		function remove() {
			var rows = grid.getSelecteds();
			if (rows.length > 0) {
				if (confirm("确定删除选中记录？")) {
					var ids = [];
					var l = rows.length;
					for (var i = 0; i < l; i++) {
						var r = rows[i];
						ids.push(r.SYS_ID_);
					}
					var id = ids.join(',');
					grid.loading("操作中，请稍后......");
					$.ajax({
						url : "${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/del_all.do?id=" + id,
						success : function(text) {
							//将json字符串转换成json对象 
							var jsonObject = JSON.parse(text);
							//进行提示
							mini_showTips({
								msg: jsonObject.message
							});
							grid.reload();
						},
						error : function() {
						}
					});
				}
			} else {
				alert("请选中一条记录");
			}
		}
	</script>
</body>
</html>