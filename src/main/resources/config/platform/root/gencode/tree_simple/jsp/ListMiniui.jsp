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
<title>${ay_table_comment}管理</title>
</head>
<body>
	<div class="mini-toolbar" style="border-bottom: 0; padding: 0px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;"><a class="mini-button"
					iconCls="icon-save" plain="true" onclick="onSave()">保存菜单</a> <a
					class="mini-button" iconCls="icon-addfolder" plain="true"
					onclick="newRow();">添加菜单</a> <a class="mini-button"
					iconCls="icon-addfolder" plain="true" onclick="newSubRow();">添加子菜单</a>
					<a class="mini-button" iconCls="icon-remove" plain="true"
					onclick="delAll();">删除菜单</a> <a class="mini-button"
					iconCls="icon-expand" plain="true" onclick="expand();">展开菜单</a> <a
					class="mini-button" iconCls="icon-collapse" plain="true"
					onclick="collapse();">收起菜单</a></td>
			</tr>
		</table>
	</div>
	<!--Splitter-->
	<div id="treegrid_menu" class="mini-treegrid"
		style="width: 100%; height: 380px;"
		url="${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/listJson.do"
		showTreeIcon="true"
		resultAsTree="false" showCheckBox="false" checkRecursive="true"
		expandOnLoad="true" allowResize="true" allowCellEdit="true"
		allowCellSelect="true" multiSelect="true" 
		 treeColumn="name" idField="${fieldNamePk}" parentField="parent" 
		 multiSelect="true"
		>
		<div property="columns">
				<div type="checkcolumn"></div>
				
				
#foreach( $name in $ay_list_column )
	<div name="${name.fieldName}" field="${name.fieldName}" width="100">
<%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%> 
<input property="editor" class="mini-textbox" style="width: 100%;" />
			</div>
#end
		
			<div name="action" cellCls="actionIcons" width="200"
				headerAlign="center" align="center" renderer="onActionRenderer"
				cellStyle="padding:0;">操作</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var grid = mini.get("#treegrid_menu");
		//展开
		function expand() {
			grid.expandAll();
		}
		//收起
		function collapse() {
			grid.collapseAll();
		}
		//行功能按钮
		function onActionRenderer(e) {
			var record = e.record;
			var pkId = record.id;
			var html = '<span onclick="newSubRow()" class="mini-button-text  mini-button-icon-text "><span class="mini-button-icon mini-iconfont icon-add" style=""></span>&nbsp;&nbsp;&nbsp;添加子菜单</span>';
			html += '<span onclick="delRow()" class="mini-button-text  mini-button-icon-text "><span class="mini-button-icon mini-iconfont icon-remove" style=""></span>&nbsp;&nbsp;&nbsp;删除</span>';
			return html;
		}
		//添加菜单		
		function newRow() {
			var node = grid.getSelectedNode();
			grid.addNode({}, "before", node);
			grid.cancelEdit();
			grid.beginEditRow(node);
		}
		//在当前选择行的下添加子记录
		function newSubRow() {
			var node = grid.getSelectedNode();
			var newNode = {};
			grid.addNode(newNode, "add", node);
		}
		//删除一行
		function delRow(row_uid) {
			var row = null;
			if (row_uid) {
				row = grid.getRowByUID(row_uid);
			} else {
				row = grid.getSelected();
			}
			if (!row) {
				alert("请选择删除的行！");
				return;
			}
			if (!confirm("确定删除此记录？")) {
				return;
			}
			//alert('row.menuId=' + row.SYS_MENU_ID_);
			var delId = row.${columnNamePk};
			if (row && !delId) {
				grid.removeNode(row);
				return;
			} else if (delId) {
				$.ajax({
					url : "${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/del.do?id=" + delId,
					type : "POST",
					success : function(text) {
						//将json字符串转换成json对象 
						var jsonObject = JSON.parse(text);
						//进行提示
						mini.alert(jsonObject.message);
						grid.removeNode(row);
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert(jqXHR.responseText);
					}
				});
			}
		}
		
		//删除多行
		function delAll() {
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
								msg : jsonObject.message
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
		//批量保存
		function onSave() {
			//表格检验
			grid.validate();
			if (!grid.isValid()) {
				return;
			}
			//获得表格的每行值
			var data = grid.getData();
			var json = mini.encode(data);
			$.ajax({
				url : "${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/save.do",
				type : "POST",
				data : {
					json : json
				},
				success : function(text) {
					//将json字符串转换成json对象 
					var jsonObject = JSON.parse(text);
					//进行提示
					//mini.alert("成功批量保存菜单!");
					mini.alert(jsonObject.message);
					grid.load();
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert(jqXHR.responseText);
				}
			});
		}
	</script>
</body>
</html>