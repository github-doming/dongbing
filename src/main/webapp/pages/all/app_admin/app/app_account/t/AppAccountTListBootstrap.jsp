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
		action="${pageContext.request.contextPath}/all/admin/app/app_account/t/list/bootstrap.do"
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
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ACCOUNT_NAME_');"
						title="按[<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias . accountName%>]排序"
						style="cursor: pointer;"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.accountName%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PASSWORD_');"
						title="按[<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias . password%>]排序"
						style="display: none;cursor: pointer;"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.password%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
						title="按[<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias . state%>]排序"
						style="cursor: pointer;"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.state%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','REGISTER_TYPE_');"
						title="按[<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias . registerType%>]排序"
						style="cursor: pointer;"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.registerType%></th>
					<th class="class_color">
						<!--隐藏列事件--> <select style="width: 60px;"
						onchange="ayTableHiddenColumn(this,'id_table',2);">
							<option value=" &nbsp;" selected="selected">&nbsp;</option>
							<option value="hit.ACCOUNT_NAME_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.accountName%></option>
							<option value="hit.STATE_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.state%></option>
							<option value="hit.REGISTER_TYPE_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.registerType%></option>
							<!-- 
							<option value="hit.IDX_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.idx%></option>
							<option value="hit.APP_ACCOUNT_ID_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.appAccountId%></option>
							<option value="hit.APP_USER_ID_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.appUserId%></option>
							<option value="hit.PASSWORD_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.password%></option>
							<option value="hit.CREATE_USER_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createUser%></option>
							<option value="hit.CREATE_TIME_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createTime%></option>
							<option value="hit.CREATE_TIME_LONG_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createTimeLong%></option>
							<option value="hit.UPDATE_USER_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateUser%></option>
							<option value="hit.UPDATE_TIME_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateTime%></option>
							<option value="hit.UPDATE_TIME_LONG_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateTimeLong%></option>
							<option value="hit.DESC_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.desc%></option>
							<option value="hit.LOGIN_TIME_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.loginTime%></option>
							<option value="hit.LOGIN_TIME_LONG_"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.loginTimeLong%></option>
					 -->
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
					<td><input value="<%=info.get("APP_ACCOUNT_ID_")%>"
						type="checkbox" name="name_checkbox_ids"></td>
					<td><a onclick="delRecord('<%=info.get("APP_ACCOUNT_ID_")%>')"
						href="#"> 删除 </a> <a
						onclick="updateRecord('<%=info.get("APP_ACCOUNT_ID_")%>')"
						href="#"> 编辑 </a></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.IDX_">&nbsp;<%=info.get("IDX_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.APP_ACCOUNT_ID_">&nbsp;<%=info.get("APP_ACCOUNT_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.APP_USER_ID_">&nbsp;<%=info.get("APP_USER_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.ACCOUNT_NAME_">&nbsp;<%=info.get("ACCOUNT_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.PASSWORD_">&nbsp;<%=info.get("PASSWORD_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.CREATE_USER_">&nbsp;<%=info.get("CREATE_USER_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.CREATE_TIME_LONG_">&nbsp;<%=info.get("CREATE_TIME_LONG_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.UPDATE_USER_">&nbsp;<%=info.get("UPDATE_USER_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.UPDATE_TIME_LONG_">&nbsp;<%=info.get("UPDATE_TIME_LONG_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.STATE_">&nbsp;<%=info.get("STATE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.DESC_">&nbsp;<%=info.get("DESC_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.CODE_">&nbsp;<%=info.get("CODE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.REGISTER_TYPE_">&nbsp;<%=info.get("REGISTER_TYPE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.LOGIN_TIME_">&nbsp;<%=info.get("LOGIN_TIME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.LOGIN_TIME_LONG_">&nbsp;<%=info.get("LOGIN_TIME_LONG_")%></td>
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
		var url = "${pageContext.request.contextPath}/all/admin/app/app_account/t/del_all/bootstrap.do";
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
			var url = "${pageContext.request.contextPath}/all/admin/app/app_account/t/del/bootstrap.do?id="
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
		var url = "${pageContext.request.contextPath}/all/admin/app/app_account/t/form/bootstrap.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 
	 请求新增
	 */
	function newRecord() {
		var url = "${pageContext.request.contextPath}/all/admin/app/app_account/t/form/bootstrap.do";
		window.location.href = url;
	}
</script>