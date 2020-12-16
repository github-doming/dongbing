<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<script type="text/javascript" 
	src="${pageContext.request.contextPath}/pages/c/x/imports/compo/cyui/cyui_select.js?version=<%=version %>"></script>
	
	
<title></title>
</head>
<body>
	<form id="id_form_save"
		action="${pageContext.request.contextPath}/c/x/platform/sys/sys_menu/cx/save.do"
		method="post">
		<table class="table  table-bordered table-hover" border="0">
			<tr>
				<th colspan="3">菜单 <c:choose>
						<c:when test="${requestScope.id==null}">添加</c:when>
						<c:otherwise>修改</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td colspan="3"><input onclick="back();" class="btn"
					type="button" value="返回列表"></input> <c:choose>
						<c:when test="${requestScope.id==null}">
							<input onclick='save();' class="btn" type="button" value="新增">
						</c:when>
						<c:otherwise>
							<input onclick='save();' class="btn" type="button" value="编辑">
						</c:otherwise>
					</c:choose></td>
			</tr>
			<!--  	<tr style="display: none;">
-->
			<tr>
				<td>
					<p class="text-right">上一级菜单主键</p>
				</td>
				<td><input size=100 id="id_input_parent_menu_id" type="text"
					name="sys_menu.parent" value="${requestScope.p.sysMenuId}"></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<p class="text-right">上一级菜单名称</p>
				</td>
				<td><input id="id_input_parent_menu_name"
					class="input-medium search-query" readonly="readonly" type="text"
					name="" value="${requestScope.p.sysMenuName}"> <input
					onclick="parent('${requestScope.s.path}');" class="btn"
					type="button" value="选择"></td>
				<td width="30%"></td>
			</tr>
			<tr style="display: none;">
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.sysMenuId%></td>
				<td><input id="id_input$sys_menu$SYS_MENU_ID_" type="text"
					name="sys_menu.sysMenuId" value="${requestScope.s.sysMenuId}"></td>
				<td><span id="id_span$sys_menu$SYS_MENU_ID_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.sysMenuName%></td>
				<td><input id="id_input$sys_menu$NAME_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.sysMenuName%>"
					type="text" name="sys_menu.sysMenuName"
					value="${requestScope.s.sysMenuName}"></td>
				<td><span id="id_span$sys_menu$NAME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.path%></td>
				<td><input style="background: #CCCCCC" disabled="disabled"
					size=100 id="id_input$sys_menu$PATH_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias . path%>"
					type="text" name="sys_menu.path" value="${requestScope.s.path}">
				</td>
				<td><span id="id_span$sys_menu$PATH_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.treeCode%></td>
				<td><input id="id_input$sys_menu$TREE_CODE_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias . treeCode%>"
					type="text" name="sys_menu.treeCode"
					value="${requestScope.s.treeCode}"></td>
				<td><span id="id_span$sys_menu$TREE_CODE_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.url%></td>
				<td><input size=100 id="id_input$sys_menu$URL_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias . url%>"
					type="text" name="sys_menu.url" value="${requestScope.s.url}">
				</td>
				<td><span id="id_span$sys_menu$URL_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.parent%></td>
				<td><input size=100 id="id_input$sys_menu$PARENT_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias . parent%>"
					type="text" name="sys_menu.parent" value="${requestScope.s.parent}">
				</td>
				<td><span id="id_span$sys_menu$PARENT_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.sn%></td>
				<td><input id="id_input$sys_menu$SN_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias . sn%>"
					type="text" name="sys_menu.sn" value="${requestScope.s.sn}">
				</td>
				<td><span id="id_span$sys_menu$SN_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.permissionGrade%></td>
				<td><input id="id_input$sys_menu$PERMISSION_GRADE_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias . permissionGrade%>"
					type="text" name="sys_menu.permissionGrade"
					value="${requestScope.s.permissionGrade}"></td>
				<td><span id="id_span$sys_menu$PERMISSION_GRADE_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.permissionCode%></td>
				<td><input id="id_input$sys_menu$PERMISSION_CODE_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias . permissionCode%>"
					type="text" name="sys_menu.permissionCode"
					value="${requestScope.s.permissionCode}"></td>
				<td><span id="id_span$sys_menu$PERMISSION_CODE_"></span></td>
			</tr>
			<!--  
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.state%></td>
				<td><input id="id_input$sys_menu$STATE_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias . state%>"
					type="text" name="sys_menu.state" value="${requestScope.s.state}">
				</td>
				<td><span id="id_span$sys_menu$STATE_"></span></td>
			</tr>
			-->
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.state%></td>
				<td><span defValue="${requestScope.s.state}" id="id_select_state" name="sys_menu.state"
					class="cyui_select" idColumn="table_key_" textColumn="value_cn_"
					url="${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict/list_by_code.do?code=state">
				</span></td>
				<td><span id="id_span$sys_menu$STATE_"></span></td>
			</tr>
			<!--  
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.picOpen%></td>
				<td><input id="id_input$sys_menu$PIC_OPEN_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.picOpen%>"
					type="text" name="sys_menu.picOpen"
					value="${requestScope.s.picOpen}"></td>
				<td><span id="id_span$sys_menu$PIC_OPEN_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.picClose%></td>
				<td><input id="id_input$sys_menu$PIC_CLOSE_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.picClose%>"
					type="text" name="sys_menu.picClose"
					value="${requestScope.s.picClose}"></td>
				<td><span id="id_span$sys_menu$PIC_CLOSE_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.pic%></td>
				<td><input id="id_input$sys_menu$PIC_"
					placeholder="请输入<%=c.x.platform.sys.sys_menu.cx.alias.SysMenuCxAlias.pic%>"
					type="text" name="sys_menu.pic" value="${requestScope.s.pic}">
				</td>
				<td><span id="id_span$sys_menu$PIC_"></span></td>
			</tr>
			-->
		</table>
	</form>
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">
	/**
	 *上一级窗口 ;
	 */
	function parent(path) {
		//alert('path='+path);
		var url = "${pageContext.request.contextPath}/c/x/platform/sys/sys_menu/cx/parent.do";
		url = url + "?path=" + path;
		var name = 'newwindow';
		ayFormOpenwindow(url, name, 800, 400);
		return false;
	}
	/**
	 *上一级;
	 *使用模态窗口 ;
	 */
	function parent_v1(path) {
		var url = "${pageContext.request.contextPath}/c/x/platform/sys/sys_menu/cx/parent.do";
		url = url + "?path=" + path;
		var name = "n";
		var needRefresh = window
				.showModalDialog(url, name,
						'dialogHeight:400px;dialogWidth:450px;center:yes;help:no;status:no;scroll:yes');
		if (needRefresh == true) {
		}
		return false;
	}
	/**
	 * 
	 返回
	 */
	function back() {
		var url = "${pageContext.request.contextPath}/c/x/platform/sys/sys_menu/cx/list.do";
		window.location.href = url;
	}
	/**
	 * 
	 检查
	 */
	function check() {
		var a = "<font color=red>自定义信息</font>";
		a = null;
		var flag = null;
		var return_flag = true;
		flag = ayCheckColumn("名称", "id_span$sys_menu$NAME_",
				"id_input$sys_menu$NAME_", "VARCHAR", "no", "32", "0", "0", a,
				true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("PATH_", "id_span$sys_menu$PATH_",
				"id_input$sys_menu$PATH_", "VARCHAR", "yes", "1024", "0", "0",
				a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("TREE_CODE_", "id_span$sys_menu$TREE_CODE_",
				"id_input$sys_menu$TREE_CODE_", "VARCHAR", "yes", "32", "0",
				"0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("URL_", "id_span$sys_menu$URL_",
				"id_input$sys_menu$URL_", "VARCHAR", "yes", "256", "0", "0", a,
				true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("PARENT_", "id_span$sys_menu$PARENT_",
				"id_input$sys_menu$PARENT_", "VARCHAR", "yes", "64", "0", "0",
				a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("SN_", "id_span$sys_menu$SN_",
				"id_input$sys_menu$SN_", "INT", "yes", "10", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("权限等级", "id_span$sys_menu$PERMISSION_GRADE_",
				"id_input$sys_menu$PERMISSION_GRADE_", "INT", "yes", "10", "0",
				"0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("权限编码", "id_span$sys_menu$PERMISSION_CODE_",
				"id_input$sys_menu$PERMISSION_CODE_", "VARCHAR", "yes", "256",
				"0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		return return_flag;
	}
	/**
	 * 
	 保存
	 */
	function save() {
		var flag = check();
		if (flag) {
		} else {
			return false;
		}
		//提交
		var obj_form = document.getElementById('id_form_save');
		obj_form.submit();
	}
</script>
<script type="text/javascript">
	//初始化日期
</script>
<script>
	cyui_select("id_select_state");
</script>
