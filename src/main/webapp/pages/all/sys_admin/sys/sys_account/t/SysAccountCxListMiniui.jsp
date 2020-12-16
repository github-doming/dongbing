<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script
	src="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/miniui/boot.js"
	type="text/javascript"></script>

	
<script
	src="${pageContext.request.contextPath}/pages/cosmos/common/all/third/miniui/miniui.js"
	type="text/javascript"></script>
	
<title> 账号 管理</title>
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
			url="${pageContext.request.contextPath}/all/admin/sys/sys_account/t/listJson.do"
			idField="SYS_ID_" sizeList="[5,10,20,50]" pageSize="10" multiSelect="true">
			<div property="columns">
				<div type="checkcolumn"></div>
				
<div field="SYS_ACCOUNT_ID_" width="100" headerAlign="center" 
dateFormat="yyyy-MM-dd" allowSort="true"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountCxAlias . sysAccountId%></div>
<div field="SYS_USER_ID_" width="100" headerAlign="center" 
dateFormat="yyyy-MM-dd" allowSort="true"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountCxAlias . sysUserId%></div>
<div field="SYS_ACCOUNT_NAME_" width="100" headerAlign="center" 
dateFormat="yyyy-MM-dd" allowSort="true"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountCxAlias .sysAccountName%></div>
<div field="PASSWORD_" width="100" headerAlign="center" 
dateFormat="yyyy-MM-dd" allowSort="true"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountCxAlias .password%></div>
<div field="DESC_" width="100" headerAlign="center" 
dateFormat="yyyy-MM-dd" allowSort="true"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountCxAlias . desc%></div>
<div field="REGISTER_TYPE_" width="100" headerAlign="center" 
dateFormat="yyyy-MM-dd" allowSort="true"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountCxAlias . registerType%></div>

			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("datagrid1");
		grid.load();
		function add() {
			var win = mini.open({
				url : "${pageContext.request.contextPath}/all/admin/sys/sys_account/t/form/miniui.do",
				title : "新增员工",
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
				var id=row.SYS_ACCOUNT_ID_;
				//alert('id='+id);
				var win = mini.open({
					url : "${pageContext.request.contextPath}/all/admin/sys/sys_account/t/form/miniui.do?id="+id,
					title : "编辑员工",
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
						url : "${pageContext.request.contextPath}/all/admin/sys/sys_account/t/del_all.do?id=" + id,
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