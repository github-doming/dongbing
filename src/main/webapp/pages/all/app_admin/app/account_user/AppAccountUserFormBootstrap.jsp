<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
	<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>
	<form id="id_form_save"
		action="${pageContext.request.contextPath}/all/admin/app/account_user/save/bootstrap.do"
		method="post">
		<table class="table  table-bordered table-hover" border="1">
			<tr>
				<th colspan="3">账号 <c:choose>
						<c:when test="${requestScope.id==null}">添加</c:when>
						<c:otherwise>修改</c:otherwise>
					</c:choose></th>
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
<tr>
				<td align="right">角色</td>
				<td>
					<%
						java.util.ArrayList<all.gen.app_group.t.entity.AppGroupT> listMap = (java.util.ArrayList<all.gen.app_group.t.entity.AppGroupT>) request
								.getAttribute("roleList");
						for (all.gen.app_group.t.entity.AppGroupT info : listMap) {
					%> <input value="<%=info.getAppGroupId()%>"
					name="name_checkbox_role" id="id_checkbox_role" type="checkbox"></input>
					<%=info.getAppGroupName()%> <%
 	}
 %>
				</td>
				<td></td>
			</tr>
			<tr style="display: none;">
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.appAccountId%></td>
				<td><input id="id_input$app_account$APP_ACCOUNT_ID_"
					type="text" name="app_account.appAccountId"
					value="${requestScope.account.appAccountId}"></td>
				<td><span id="id_span$app_account$APP_ACCOUNT_ID_"></span></td>
			</tr>
			<tr style="display: none;">
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.appUserId%></td>
				<td><input id="id_input$app_account$APP_USER_ID_"
					placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.appUserId%>"
					type="text" name="app_account.appUserId"
					value="${requestScope.account.appUserId}"></td>
				<td><span id="id_span$app_account$APP_USER_ID_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.accountName%></td>
				<td><input id="id_input$app_account$ACCOUNT_NAME_"
					placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias . accountName%>"
					type="text" name="app_account.accountName"
					value="${requestScope.account.accountName}"></td>
				<td><span id="id_span$app_account$ACCOUNT_NAME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.password%></td>
				<td><input id="id_input$app_account$PASSWORD_"
					placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias . password%>"
					type="text" name="app_account.password"
					value="${requestScope.account.password}"></td>
				<td><span id="id_span$app_account$PASSWORD_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.nickname%></td>
				<td><input id="id_input$app_user$NICKNAME_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias . nickname%>"
					type="text" name="app_user.nickname"
					value="${requestScope.user.nickname}"></td>
				<td><span id="id_span$app_user$NICKNAME_"></span></td>
			</tr>
			<!-- 
			<tr>
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.registerType%></td>
				<td><input id="id_input$app_account$REGISTER_TYPE_"
					placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.registerType%>"
					type="text" name="app_account.registerType"
					value="${requestScope.account.registerType}"></td>
				<td><span id="id_span$app_account$REGISTER_TYPE_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.state%></td>
				<td><input id="id_input$app_account$STATE_"
					placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.state%>"
					type="text" name="app_account.state"
					value="${requestScope.account.state}"></td>
				<td><span id="id_span$app_account$STATE_"></span></td>
			</tr>
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.idx%></td>
		<td><input id="id_input$app_account$IDX_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.idx%>"  type="text" name="app_account.idx"
			value="${requestScope.account.idx}">
		</td>
		<td><span id="id_span$app_account$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createUser%></td>
		<td><input id="id_input$app_account$CREATE_USER_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createUser%>"  type="text" name="app_account.createUser"
			value="${requestScope.account.createUser}">
		</td>
		<td><span id="id_span$app_account$CREATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createTime%></td>
		<td><input id="id_input$app_account$CREATE_TIME_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createTime%>"  type="text" name="app_account.createTime"
			value="${requestScope.account.createTime}">
<img
onclick=ayCalendarShow('id_input$app_account$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_account$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createTimeLong%></td>
		<td><input id="id_input$app_account$CREATE_TIME_LONG_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createTimeLong%>"  type="text" name="app_account.createTimeLong"
			value="${requestScope.account.createTimeLong}">
		</td>
		<td><span id="id_span$app_account$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateUser%></td>
		<td><input id="id_input$app_account$UPDATE_USER_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateUser%>"  type="text" name="app_account.updateUser"
			value="${requestScope.account.updateUser}">
		</td>
		<td><span id="id_span$app_account$UPDATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateTime%></td>
		<td><input id="id_input$app_account$UPDATE_TIME_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateTime%>"  type="text" name="app_account.updateTime"
			value="${requestScope.account.updateTime}">
<img
onclick=ayCalendarShow('id_input$app_account$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_account$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateTimeLong%></td>
		<td><input id="id_input$app_account$UPDATE_TIME_LONG_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateTimeLong%>"  type="text" name="app_account.updateTimeLong"
			value="${requestScope.account.updateTimeLong}">
		</td>
		<td><span id="id_span$app_account$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.desc%></td>
		<td><input id="id_input$app_account$DESC_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.desc%>"  type="text" name="app_account.desc"
			value="${requestScope.account.desc}">
		</td>
		<td><span id="id_span$app_account$DESC_"></span>
		</td>
</tr>		
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.loginTime%></td>
		<td><input id="id_input$app_account$LOGIN_TIME_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.loginTime%>"  type="text" name="app_account.loginTime"
			value="${requestScope.account.loginTime}">
<img
onclick=ayCalendarShow('id_input$app_account$LOGIN_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_account$LOGIN_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.loginTimeLong%></td>
		<td><input id="id_input$app_account$LOGIN_TIME_LONG_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.loginTimeLong%>"  type="text" name="app_account.loginTimeLong"
			value="${requestScope.account.loginTimeLong}">
		</td>
		<td><span id="id_span$app_account$LOGIN_TIME_LONG_"></span>
		</td>
</tr>	
 -->
			<tr>
				<td align="center" colspan="3"></td>
			</tr>
		</table>
	</form>
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">
	/**
	 * 
	 返回
	 */
	function back() {
		var url = "${pageContext.request.contextPath}/all/admin/app/account_user/list/bootstrap.do";
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
		flag = ayCheckColumn("账号名称ACCOUNT_NAME_",
				"id_span$app_account$ACCOUNT_NAME_",
				"id_input$app_account$ACCOUNT_NAME_", "VARCHAR", "yes", "256",
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
<script type="text/javascript">
	/**
	 * 加载页面时初始化
	 */
	//所有角色(所选择中的)
	//{
	var value_ids = '${requestScope.ids_role}';
	//alert(value_ids);
	ayTableCheckboxEditLoadIds(value_ids, 'name_checkbox_role');
	//}
	//所有角色(所选择中的)
</script>
