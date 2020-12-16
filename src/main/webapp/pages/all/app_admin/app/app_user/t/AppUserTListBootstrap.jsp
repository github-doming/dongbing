<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>
	<div style="display: none;" id="id_div_msg">正在提交中...</div>
	<form
		action="${pageContext.request.contextPath}/all/admin/app/app_user/t/list/bootstrap.do"
		id="id_form_list" method="post">
		<table class="table table-hover" border="0">
			<caption></caption>
			<thead>
				<tr>
					<!-- 
				<th></th>
		 -->
				</tr>
			</thead>
			<tbody>
				<tr>
					<!-- 
					<td></td>
		 -->
				</tr>
				<tr>
					<td><input class="btn" id="id_input_new" type="button"
						value="新增" onclick="newRecord()"> <input class="btn"
						id="id_input_new" type="button" value="删除"
						onclick="delAllRecord()"></td>
				</tr>
			</tbody>
		</table>
		<table style="display: none;" align="center"">
			<tr>
				<td>升降序</td>
				<td><input value="${requestScope.sortFieldValue}"
					id="sortFieldId" name="sortFieldName" /></td>
				<td>排序的列值</td>
				<td><input value="${requestScope.sortOrderValue}"
					id="sortOrderId" name="sortOrderName" /></td>
			</tr>
		</table>
		<table style="width: 100%; table-layout: fixed" id="id_table"
			class="table  table-bordered table-hover">
			<caption></caption>
			<thead>
				<tr>
					<th width="60px;"><input id="id_checkbox_if_all"
						onclick=ayTableCheckAll(); type="checkbox"
						name="name_checkbox_if_all" />全选</th>
					<th width="150px;">操作</th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','APP_USER_NAME_');"
						title="按[<%=all.app_admin.app.app_user.t.alias.AppUserTAlias . appUserName%>]排序"
						style="cursor: pointer;"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserName%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','NICKNAME_');"
						title="按[<%=all.app_admin.app.app_user.t.alias.AppUserTAlias . nickname%>]排序"
						style="cursor: pointer;"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.nickname%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','APP_USER_TYPE_');"
						title="按[<%=all.app_admin.app.app_user.t.alias.AppUserTAlias . appUserType%>]排序"
						style="cursor: pointer;"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserType%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
						title="按[<%=all.app_admin.app.app_user.t.alias.AppUserTAlias . state%>]排序"
						style="cursor: pointer;"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.state%></th>
					<th class="class_color">
						<!--隐藏列事件--> <select style="width: 60px;"
						onchange="ayTableHiddenColumn(this,'id_table',2);">
							<option value=" &nbsp;" selected="selected">&nbsp;</option>
							<option value="hit.IDX_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.idx%></option>
							<option value="hit.APP_USER_ID_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserId%></option>
							<option value="hit.APP_USER_NAME_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserName%></option>
							<option value="hit.APP_USER_CODE_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserCode%></option>
							<option value="hit.APP_USER_TYPE_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.appUserType%></option>
							<option value="hit.HEAD_PORTRAIT_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.headPortrait%></option>
							<option value="hit.NICKNAME_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.nickname%></option>
							<option value="hit.GENDER_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.gender%></option>
							<option value="hit.PERMISSION_GRADE_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.permissionGrade%></option>
							<option value="hit.SN_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.sn%></option>
							<option value="hit.AGE_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.age%></option>
							<option value="hit.CREATE_USER_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.createUser%></option>
							<option value="hit.CREATE_TIME_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.createTime%></option>
							<option value="hit.CREATE_TIME_LONG_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.createTimeLong%></option>
							<option value="hit.UPDATE_USER_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.updateUser%></option>
							<option value="hit.UPDATE_TIME_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.updateTime%></option>
							<option value="hit.UPDATE_TIME_LONG_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.updateTimeLong%></option>
							<option value="hit.STATE_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.state%></option>
							<option value="hit.DESC_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.desc%></option>
							<option value="hit.MAIN_APP_ACCOUNT_ID_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.mainAppAccountId%></option>
							<option value="hit.SYS_USER_ID_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.sysUserId%></option>
							<option value="hit.REALNAME_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.realname%></option>
							<option value="hit.LOGIN_TIME_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.loginTime%></option>
							<option value="hit.LOGIN_TIME_LONG_"><%=all.app_admin.app.app_user.t.alias.AppUserTAlias.loginTimeLong%></option>
					</select> <!--隐藏列事件-->
					</th>
				</tr>
			</thead>
			<tbody>
				<%
					java.util.ArrayList<java.util.HashMap<String, Object>> listMap = (java.util.ArrayList<java.util.HashMap<String, Object>>) request
							.getAttribute("list");
					for (java.util.HashMap<String, Object> info : listMap) {
				%>
				<tr>
					<td><input value="<%=info.get("APP_USER_ID_")%>"
						type="checkbox" name="name_checkbox_ids"></td>
					<td><a onclick="delRecord('<%=info.get("APP_USER_ID_")%>')"
						href="#"> 删除 </a> <a
						onclick="updateRecord('<%=info.get("APP_USER_ID_")%>')" href="#">
							编辑 </a></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.IDX_">&nbsp;<%=info.get("IDX_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.APP_USER_ID_">&nbsp;<%=info.get("APP_USER_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.APP_USER_NAME_">&nbsp;<%=info.get("APP_USER_NAME_")%></td>
					
						<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;"
						id="hit.NICKNAME_">&nbsp;<%=info.get("NICKNAME_")%></td>
					
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;"
						id="hit.APP_USER_CODE_">&nbsp;<%=info.get("APP_USER_CODE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.APP_USER_TYPE_">&nbsp;<%=info.get("APP_USER_TYPE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.HEAD_PORTRAIT_">&nbsp;<%=info.get("HEAD_PORTRAIT_")%></td>
				
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.GENDER_">&nbsp;<%=info.get("GENDER_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.PERMISSION_GRADE_">&nbsp;<%=info.get("PERMISSION_GRADE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.SN_">&nbsp;<%=info.get("SN_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.AGE_">&nbsp;<%=info.get("AGE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.CREATE_USER_">&nbsp;<%=info.get("CREATE_USER_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.CREATE_TIME_LONG_">&nbsp;<%=info.get("CREATE_TIME_LONG_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.UPDATE_USER_">&nbsp;<%=info.get("UPDATE_USER_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.UPDATE_TIME_LONG_">&nbsp;<%=info.get("UPDATE_TIME_LONG_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.STATE_">&nbsp;<%=info.get("STATE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.DESC_">&nbsp;<%=info.get("DESC_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.MAIN_APP_ACCOUNT_ID_">&nbsp;<%=info.get("MAIN_APP_ACCOUNT_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.SYS_USER_ID_">&nbsp;<%=info.get("SYS_USER_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.REALNAME_">&nbsp;<%=info.get("REALNAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.LOGIN_TIME_">&nbsp;<%=info.get("LOGIN_TIME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.LOGIN_TIME_LONG_">&nbsp;<%=info.get("LOGIN_TIME_LONG_")%></td>
					<td style="word-wrap: break-word;">&nbsp;</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<%@ include file="/pages/c/x/imports/feng/page/simple/page.jsp"%>
	</form>
</body>
</html>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/page/simple/page.js?version=<%=version%>">
	
</script>
<!-- 加载js -->
<script type="text/javascript">
	/**
	 * 
	 删除所有记录
	 */
	function delAllRecord(id) {
		var url = "${pageContext.request.contextPath}/all/admin/app/app_user/t/del_all/bootstrap.do";
		var objs = document.getElementsByName("name_checkbox_ids");
		var checkedNumber = 0;
		for (var i = 0; i < objs.length; i++) {
			if (objs[i].checked) {
				checkedNumber = checkedNumber + 1;
			}
		}
		if (checkedNumber == 0) {
			alert('请先选择要删除的行');
		} else {
			if (confirm("确定要删除吗？")) {
				document.getElementById("id_form_list").action = url;
				document.getElementById("id_form_list").submit();
			} else {
			}
		}
		return false;
	}
	/**
	 * 
	 删除记录
	 */
	function delRecord(id) {
		if (confirm("确定要删除吗？")) {
			var url = "${pageContext.request.contextPath}/all/admin/app/app_user/t/del/bootstrap.do?id="
					+ id;
			window.location.href = url;
		} else {
		}
	}
	/**
	 * 
	 请求更新
	 */
	function updateRecord(id) {
		var url = "${pageContext.request.contextPath}/all/admin/app/app_user/t/form/bootstrap.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 
	 请求新增
	 */
	function newRecord() {
		var url = "${pageContext.request.contextPath}/all/admin/app/app_user/t/form/bootstrap.do";
		window.location.href = url;
	}
</script>