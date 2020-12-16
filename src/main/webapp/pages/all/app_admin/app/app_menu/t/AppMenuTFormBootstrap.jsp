<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>
	<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>
	<form id="id_form_save"
		action="${pageContext.request.contextPath}/all/admin/app/app_menu/t/save/bootstrap.do"
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
			<tr style="display: none;">
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.appMenuId%></td>
				<td><input id="id_input$app_menu$APP_MENU_ID_" type="text"
					name="app_menu.appMenuId" value="${requestScope.s.appMenuId}"></td>
				<td><span id="id_span$app_menu$APP_MENU_ID_"></span></td>
			</tr>
			<tr style="display: none;">
				<td>
					<p class="text-right">上一级菜单id：</p>
				</td>
				<td><input id="id_input_parent_menu_id" type="text"
					name="app_menu.parent" value="${requestScope.p.appMenuId}"></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<p class="text-right">上一级菜单名称：</p>
				</td>
				<td><input id="id_input_parent_menu_name"
					class="input-medium search-query" readonly="readonly" type="text"
					name="" value="${requestScope.p.appMenuName}"> <input
					onclick="parent('${requestScope.s.path}');" class="btn"
					type="button" value="选择"></td>
				<td width="30%"></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.appMenuName%></td>
				<td><input id="id_input$app_menu$APP_MENU_NAME_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias . appMenuName%>"
					type="text" name="app_menu.appMenuName"
					value="${requestScope.s.appMenuName}"></td>
				<td><span id="id_span$app_menu$APP_MENU_NAME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.adminUrl%></td>
				<td><input size=100 id="id_input$app_menu$ADMIN_URL_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias . adminUrl%>"
					type="text" name="app_menu.adminUrl"
					value="${requestScope.s.adminUrl}"></td>
				<td><span id="id_span$app_menu$ADMIN_URL_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.pcUrl%></td>
				<td><input size=100 id="id_input$app_menu$PC_URL_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias . pcUrl%>"
					type="text" name="app_menu.pcUrl" value="${requestScope.s.pcUrl}">
				</td>
				<td><span id="id_span$app_menu$PC_URL_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.pcPic%></td>
				<td><input size=100 id="id_input$app_menu$PC_PIC_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias . pcPic%>"
					type="text" name="app_menu.pcPic" value="${requestScope.s.pcPic}">
				</td>
				<td><span id="id_span$app_menu$PC_PIC_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.permissionCode%></td>
				<td><input id="id_input$app_menu$PERMISSION_CODE_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias . permissionCode%>"
					type="text" name="app_menu.permissionCode"
					value="${requestScope.s.permissionCode}"></td>
				<td><span id="id_span$app_menu$PERMISSION_CODE_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.sn%></td>
				<td><input id="id_input$app_menu$SN_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias . sn%>"
					type="text" name="app_menu.sn" value="${requestScope.s.sn}">
				</td>
				<td><span id="id_span$app_menu$SN_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.path%></td>
				<td><input style="background: #CCCCCC" disabled="disabled"
					size=100 id="id_input$app_menu$PATH_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias . path%>"
					type="text" name="app_menu.path" value="${requestScope.s.path}">
				</td>
				<td><span id="id_span$app_menu$PATH_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.state%></td>
				<td><input id="id_input$app_menu$STATE_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.state%>"
					type="text" name="app_menu.state" value="${requestScope.s.state}">
				</td>
				<td><span id="id_span$app_menu$STATE_"></span></td>
			</tr>
			<!-- 
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.idx%></td>
				<td><input id="id_input$app_menu$IDX_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.idx%>"
					type="text" name="app_menu.idx" value="${requestScope.s.idx}">
				</td>
				<td><span id="id_span$app_menu$IDX_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.parent%></td>
				<td><input id="id_input$app_menu$PARENT_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.parent%>"
					type="text" name="app_menu.parent" value="${requestScope.s.parent}">
				</td>
				<td><span id="id_span$app_menu$PARENT_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.treeCode%></td>
				<td><input id="id_input$app_menu$TREE_CODE_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.treeCode%>"
					type="text" name="app_menu.treeCode"
					value="${requestScope.s.treeCode}"></td>
				<td><span id="id_span$app_menu$TREE_CODE_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.createUser%></td>
				<td><input id="id_input$app_menu$CREATE_USER_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.createUser%>"
					type="text" name="app_menu.createUser"
					value="${requestScope.s.createUser}"></td>
				<td><span id="id_span$app_menu$CREATE_USER_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.createTime%></td>
				<td><input id="id_input$app_menu$CREATE_TIME_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.createTime%>"
					type="text" name="app_menu.createTime"
					value="${requestScope.s.createTime}"> <img
					onclick=ayCalendarShow(
					'id_input$app_menu$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
				</td>
				<td><span id="id_span$app_menu$CREATE_TIME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.createTimeLong%></td>
				<td><input id="id_input$app_menu$CREATE_TIME_LONG_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.createTimeLong%>"
					type="text" name="app_menu.createTimeLong"
					value="${requestScope.s.createTimeLong}"></td>
				<td><span id="id_span$app_menu$CREATE_TIME_LONG_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.updateUser%></td>
				<td><input id="id_input$app_menu$UPDATE_USER_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.updateUser%>"
					type="text" name="app_menu.updateUser"
					value="${requestScope.s.updateUser}"></td>
				<td><span id="id_span$app_menu$UPDATE_USER_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.updateTime%></td>
				<td><input id="id_input$app_menu$UPDATE_TIME_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.updateTime%>"
					type="text" name="app_menu.updateTime"
					value="${requestScope.s.updateTime}"> <img
					onclick=ayCalendarShow(
					'id_input$app_menu$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
				</td>
				<td><span id="id_span$app_menu$UPDATE_TIME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.updateTimeLong%></td>
				<td><input id="id_input$app_menu$UPDATE_TIME_LONG_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.updateTimeLong%>"
					type="text" name="app_menu.updateTimeLong"
					value="${requestScope.s.updateTimeLong}"></td>
				<td><span id="id_span$app_menu$UPDATE_TIME_LONG_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.desc%></td>
				<td><input id="id_input$app_menu$DESC_"
					placeholder="请输入<%=all.app_admin.app.app_menu.t.alias.AppMenuTAlias.desc%>"
					type="text" name="app_menu.desc" value="${requestScope.s.desc}">
				</td>
				<td><span id="id_span$app_menu$DESC_"></span></td>
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
		var url = "${pageContext.request.contextPath}/all/admin/app/app_menu/t/parent/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/admin/app/app_menu/t/parent/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/admin/app/app_menu/t/list/bootstrap.do";
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
		flag = ayCheckColumn("菜单名称APP_MENU_NAME_",
				"id_span$app_menu$APP_MENU_NAME_",
				"id_input$app_menu$APP_MENU_NAME_", "VARCHAR", "yes", "32",
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
