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
<form action="${pageContext.request.contextPath}/all/task/tms/project_user/app_user/list/bootstrap.do"  id="id_form_list" method="post">

<table style="display: none;"  align="center" border="1">
	<tr>
		<td>升降序</td>
		<td><input value="${requestScope.sortFieldValue}" id="sortFieldId"
			name="sortFieldName" /></td>
		<td>排序的列值</td>
		<td><input value="${requestScope.sortOrderValue}"
			id="sortOrderId" name="sortOrderName" /></td>
	</tr>
	
	<tr>
		<td>树id</td>
		<td><input value="${requestScope.value_first$tree$id}" id="id_first$tree$id"
			name="first$tree$id" /></td>
		<td>树名称</td>
		<td><input value="${requestScope.value_first$tree$name}" id="id_first$tree$name"
			name="name_first$tree$name" /></td>
	</tr>
	
	
</table>

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
				id="id_input_new" type="button" value="删除" onclick="delAllRecord()">
			</td>
		</tr>
	</tbody>
</table>
<table style="width:100%;table-layout:fixed"  id="id_table" class="table  table-bordered table-hover">
	<caption></caption>
	<thead>
		<tr>
				<th width="60px;" ><input id="id_checkbox_if_all" onclick=ayTableCheckAll();
				type="checkbox" name="" />全选</th>
				<th width="60px;" >操作</th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','IDX_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . idx%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . idx%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','APP_USER_ID_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserId%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','MAIN_APP_ACCOUNT_ID_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . mainAppAccountId%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . mainAppAccountId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_USER_ID_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . sysUserId%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . sysUserId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','APP_USER_NAME_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserName%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','APP_USER_CODE_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserCode%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserCode%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','APP_USER_TYPE_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserType%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserType%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TABLE_NAME_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . tableName%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . tableName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','HEAD_PORTRAIT_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . headPortrait%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . headPortrait%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','NICKNAME_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . nickname%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . nickname%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','REALNAME_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . realname%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . realname%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','GENDER_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . gender%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . gender%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','AGE_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . age%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . age%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PERMISSION_GRADE_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . permissionGrade%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . permissionGrade%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SN_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . sn%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . sn%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','LOGIN_TIME_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . loginTime%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . loginTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','LOGIN_TIME_LONG_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . loginTimeLong%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . loginTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . state%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . state%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_USER_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . createUser%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . createUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . createTime%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_USER_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . updateUser%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . updateUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . updateTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TENANT_CODE_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . tenantCode%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . tenantCode%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TENANT_NUMBER_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . tenantNumber%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . tenantNumber%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . desc%>]排序" style="cursor: pointer;"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . desc%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.IDX_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . idx%></option>
	<option value="hit.APP_USER_ID_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserId%></option>
	<option value="hit.MAIN_APP_ACCOUNT_ID_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . mainAppAccountId%></option>
	<option value="hit.SYS_USER_ID_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . sysUserId%></option>
	<option value="hit.APP_USER_NAME_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserName%></option>
	<option value="hit.APP_USER_CODE_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserCode%></option>
	<option value="hit.APP_USER_TYPE_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . appUserType%></option>
	<option value="hit.TABLE_NAME_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . tableName%></option>
	<option value="hit.HEAD_PORTRAIT_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . headPortrait%></option>
	<option value="hit.NICKNAME_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . nickname%></option>
	<option value="hit.REALNAME_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . realname%></option>
	<option value="hit.GENDER_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . gender%></option>
	<option value="hit.AGE_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . age%></option>
	<option value="hit.PERMISSION_GRADE_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . permissionGrade%></option>
	<option value="hit.SN_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . sn%></option>
	<option value="hit.LOGIN_TIME_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . loginTime%></option>
	<option value="hit.LOGIN_TIME_LONG_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . loginTimeLong%></option>
	<option value="hit.STATE_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . state%></option>
	<option value="hit.CREATE_USER_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . createUser%></option>
	<option value="hit.CREATE_TIME_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . createTimeLong%></option>
	<option value="hit.UPDATE_USER_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . updateUser%></option>
	<option value="hit.UPDATE_TIME_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . updateTimeLong%></option>
	<option value="hit.TENANT_CODE_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . tenantCode%></option>
	<option value="hit.TENANT_NUMBER_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . tenantNumber%></option>
	<option value="hit.DESC_"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias . desc%></option>
			</select> <!--隐藏列事件--></th>
		</tr>
	</thead>
	<tbody>
		<%
		java.util.ArrayList<java.util.HashMap<String, Object>> listMap = (java.util.ArrayList<java.util.HashMap<String, Object>>) request
				.getAttribute("list");
	for (java.util.HashMap<String, Object> info : listMap) {
	%>
		<tr>
			<td><input value="<%=info.get("${name.columnName}")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("APP_USER_ID_")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("APP_USER_ID_")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.IDX_">&nbsp;<%=info.get("IDX_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.APP_USER_ID_">&nbsp;<%=info.get("APP_USER_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.MAIN_APP_ACCOUNT_ID_">&nbsp;<%=info.get("MAIN_APP_ACCOUNT_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.SYS_USER_ID_">&nbsp;<%=info.get("SYS_USER_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.APP_USER_NAME_">&nbsp;<%=info.get("APP_USER_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.APP_USER_CODE_">&nbsp;<%=info.get("APP_USER_CODE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.APP_USER_TYPE_">&nbsp;<%=info.get("APP_USER_TYPE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.TABLE_NAME_">&nbsp;<%=info.get("TABLE_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.HEAD_PORTRAIT_">&nbsp;<%=info.get("HEAD_PORTRAIT_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.NICKNAME_">&nbsp;<%=info.get("NICKNAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.REALNAME_">&nbsp;<%=info.get("REALNAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.GENDER_">&nbsp;<%=info.get("GENDER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.AGE_">&nbsp;<%=info.get("AGE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.PERMISSION_GRADE_">&nbsp;<%=info.get("PERMISSION_GRADE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.SN_">&nbsp;<%=info.get("SN_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.LOGIN_TIME_">&nbsp;<%=info.get("LOGIN_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.LOGIN_TIME_LONG_">&nbsp;<%=info.get("LOGIN_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.STATE_">&nbsp;<%=info.get("STATE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.CREATE_USER_">&nbsp;<%=info.get("CREATE_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.CREATE_TIME_LONG_">&nbsp;<%=info.get("CREATE_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.UPDATE_USER_">&nbsp;<%=info.get("UPDATE_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.UPDATE_TIME_LONG_">&nbsp;<%=info.get("UPDATE_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.TENANT_CODE_">&nbsp;<%=info.get("TENANT_CODE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.TENANT_NUMBER_">&nbsp;<%=info.get("TENANT_NUMBER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.DESC_">&nbsp;<%=info.get("DESC_")%></td>
			<td style="word-wrap : break-word;  ">&nbsp;</td>
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
function delAllRecord(id){
	var url = "${pageContext.request.contextPath}/all/task/tms/project_user/app_user/del_all/bootstrap.do";
	var objs = document.getElementsByName("checkboxIds");
	var checkedNumber = 0;
	for (var i = 0; i < objs.length; i++) {
		if (objs[i].checked) {
			checkedNumber = checkedNumber + 1;
		}
	}
	if (checkedNumber == 0) {
	alert('请先选择要删除的行');
	}else{
		if(confirm("确定要删除吗？"))
		{
			document.getElementById("id_form_list").action=url;
			document.getElementById("id_form_list").submit();
		}
		else
		{
		}
	}
	return false;
}
/**
 * 
 删除记录
 */
function delRecord(id){
	if(confirm("确定要删除吗？"))
	{
		var url = "${pageContext.request.contextPath}/all/task/tms/project_user/app_user/del/bootstrap.do?id="+id;
		var first$tree$id=document.getElementById("id_first$tree$id").value;
		url=url+"&&first$tree$id="+first$tree$id;
		window.location.href= url;
	}
	else
	{
	}
}
/**
 * 
 请求更新
 */
function updateRecord(id){
	var url= "${pageContext.request.contextPath}/all/task/tms/project_user/app_user/form/bootstrap.do?id="+id;
	var first$tree$id=document.getElementById("id_first$tree$id").value;
	url=url+"&&first$tree$id="+first$tree$id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/task/tms/project_user/app_user/form/bootstrap.do";
 	var first$tree$id=document.getElementById("id_first$tree$id").value;
 	url=url+"?first$tree$id="+first$tree$id;
 	window.location.href=url;
}
	</script>