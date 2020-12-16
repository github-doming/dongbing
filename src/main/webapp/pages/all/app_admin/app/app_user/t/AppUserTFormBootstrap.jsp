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
		action="${pageContext.request.contextPath}/all/admin/app/app_user/t/save/bootstrap.do"
		method="post">
		<table class="table  table-bordered table-hover" border="1">
			<tr>
				<th colspan="3">用户 <c:choose>
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
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserId%></td>
				<td><input id="id_input$app_user$APP_USER_ID_" type="text"
					name="app_user.appUserId" value="${requestScope.s.appUserId}"></td>
				<td><span id="id_span$app_user$APP_USER_ID_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserName%></td>
				<td><input id="id_input$app_user$APP_USER_NAME_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias . appUserName%>"
					type="text" name="app_user.appUserName"
					value="${requestScope.s.appUserName}"></td>
				<td><span id="id_span$app_user$APP_USER_NAME_"></span></td>
			</tr>
		

			<tr style="display: none;">
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.headPortrait%></td>
				<td><input size=100 id="id_input$app_user$HEAD_PORTRAIT_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias . headPortrait%>"
					type="text" name="app_user.headPortrait"
					value="${requestScope.s.headPortrait}"></td>
				<td><span id="id_span$app_user$HEAD_PORTRAIT_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.nickname%></td>
				<td><input id="id_input$app_user$NICKNAME_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias . nickname%>"
					type="text" name="app_user.nickname"
					value="${requestScope.s.nickname}"></td>
				<td><span id="id_span$app_user$NICKNAME_"></span></td>
			</tr>
		  <tr style="display: none;">
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.permissionGrade%></td>
				<td><input id="id_input$app_user$PERMISSION_GRADE_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias . permissionGrade%>"
					type="text" name="app_user.permissionGrade"
					value="${requestScope.s.permissionGrade}"></td>
				<td><span id="id_span$app_user$PERMISSION_GRADE_"></span></td>
			</tr>

<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserType%></td>
				<td><input id="id_input$app_user$APP_USER_TYPE_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserType%>"
					type="text" name="app_user.appUserType"
					value="${requestScope.s.appUserType}"></td>
				<td><span id="id_span$app_user$APP_USER_TYPE_"></span></td>
			</tr>
			
			
		<tr style="display: none;">
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.state%></td>
				<td><input id="id_input$app_user$STATE_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias . state%>"
					type="text" name="app_user.state" value="${requestScope.s.state}">
				</td>
				<td><span id="id_span$app_user$STATE_"></span></td>
			</tr>

			<!-- 
			
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.idx%></td>
				<td><input id="id_input$app_user$IDX_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.idx%>"
					type="text" name="app_user.idx" value="${requestScope.s.idx}">
				</td>
				<td><span id="id_span$app_user$IDX_"></span></td>
			</tr>
			
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserCode%></td>
				<td><input id="id_input$app_user$APP_USER_CODE_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserCode%>"
					type="text" name="app_user.appUserCode"
					value="${requestScope.s.appUserCode}"></td>
				<td><span id="id_span$app_user$APP_USER_CODE_"></span></td>
			</tr>
			
			
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.gender%></td>
				<td><input id="id_input$app_user$GENDER_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.gender%>"
					type="text" name="app_user.gender" value="${requestScope.s.gender}">
				</td>
				<td><span id="id_span$app_user$GENDER_"></span></td>
			</tr>
			
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.sn%></td>
				<td><input id="id_input$app_user$SN_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.sn%>"
					type="text" name="app_user.sn" value="${requestScope.s.sn}">
				</td>
				<td><span id="id_span$app_user$SN_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.age%></td>
				<td><input id="id_input$app_user$AGE_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.age%>"
					type="text" name="app_user.age" value="${requestScope.s.age}">
				</td>
				<td><span id="id_span$app_user$AGE_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.createUser%></td>
				<td><input id="id_input$app_user$CREATE_USER_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.createUser%>"
					type="text" name="app_user.createUser"
					value="${requestScope.s.createUser}"></td>
				<td><span id="id_span$app_user$CREATE_USER_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.createTime%></td>
				<td><input id="id_input$app_user$CREATE_TIME_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.createTime%>"
					type="text" name="app_user.createTime"
					value="${requestScope.s.createTime}"> <img
					onclick=ayCalendarShow(
					'id_input$app_user$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
				</td>
				<td><span id="id_span$app_user$CREATE_TIME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.createTimeLong%></td>
				<td><input id="id_input$app_user$CREATE_TIME_LONG_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.createTimeLong%>"
					type="text" name="app_user.createTimeLong"
					value="${requestScope.s.createTimeLong}"></td>
				<td><span id="id_span$app_user$CREATE_TIME_LONG_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.updateUser%></td>
				<td><input id="id_input$app_user$UPDATE_USER_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.updateUser%>"
					type="text" name="app_user.updateUser"
					value="${requestScope.s.updateUser}"></td>
				<td><span id="id_span$app_user$UPDATE_USER_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.updateTime%></td>
				<td><input id="id_input$app_user$UPDATE_TIME_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.updateTime%>"
					type="text" name="app_user.updateTime"
					value="${requestScope.s.updateTime}"> <img
					onclick=ayCalendarShow(
					'id_input$app_user$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
				</td>
				<td><span id="id_span$app_user$UPDATE_TIME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.updateTimeLong%></td>
				<td><input id="id_input$app_user$UPDATE_TIME_LONG_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.updateTimeLong%>"
					type="text" name="app_user.updateTimeLong"
					value="${requestScope.s.updateTimeLong}"></td>
				<td><span id="id_span$app_user$UPDATE_TIME_LONG_"></span></td>
			</tr>
			
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.desc%></td>
				<td><input id="id_input$app_user$DESC_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.desc%>"
					type="text" name="app_user.desc" value="${requestScope.s.desc}">
				</td>
				<td><span id="id_span$app_user$DESC_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.mainAppAccountId%></td>
				<td><input id="id_input$app_user$MAIN_APP_ACCOUNT_ID_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.mainAppAccountId%>"
					type="text" name="app_user.mainAppAccountId"
					value="${requestScope.s.mainAppAccountId}"></td>
				<td><span id="id_span$app_user$MAIN_APP_ACCOUNT_ID_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.sysUserId%></td>
				<td><input id="id_input$app_user$SYS_USER_ID_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.sysUserId%>"
					type="text" name="app_user.sysUserId"
					value="${requestScope.s.sysUserId}"></td>
				<td><span id="id_span$app_user$SYS_USER_ID_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.realname%></td>
				<td><input id="id_input$app_user$REALNAME_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.realname%>"
					type="text" name="app_user.realname"
					value="${requestScope.s.realname}"></td>
				<td><span id="id_span$app_user$REALNAME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.loginTime%></td>
				<td><input id="id_input$app_user$LOGIN_TIME_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.loginTime%>"
					type="text" name="app_user.loginTime"
					value="${requestScope.s.loginTime}"> <img
					onclick=ayCalendarShow(
					'id_input$app_user$LOGIN_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
				</td>
				<td><span id="id_span$app_user$LOGIN_TIME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.loginTimeLong%></td>
				<td><input id="id_input$app_user$LOGIN_TIME_LONG_"
					placeholder="请输入<%=all.app_admin.app.app_user.t.alias.AppUserTAlias.loginTimeLong%>"
					type="text" name="app_user.loginTimeLong"
					value="${requestScope.s.loginTimeLong}"></td>
				<td><span id="id_span$app_user$LOGIN_TIME_LONG_"></span></td>
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
		var url = "${pageContext.request.contextPath}/all/admin/app/app_user/t/list/bootstrap.do";
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
		flag = ayCheckColumn("APP_USER_NAME_",
				"id_span$app_user$APP_USER_NAME_",
				"id_input$app_user$APP_USER_NAME_", "CHAR", "yes", "16", "0",
				"0", a, true);
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


