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
<form action="${pageContext.request.contextPath}/all/gen/sys_exception/t/list/bootstrap.do"  id="id_form_list" method="post">
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
<table style="display: none;" align="center"">
	<tr>
		<td>升降序</td>
		<td><input value="${requestScope.sortFieldValue}" id="sortFieldId"
			name="sortFieldName" /></td>
		<td>排序的列值</td>
		<td><input value="${requestScope.sortOrderValue}"
			id="sortOrderId" name="sortOrderName" /></td>
	</tr>
</table>
<table style="width:100%;table-layout:fixed"  id="id_table" class="table  table-bordered table-hover">
	<caption></caption>
	<thead>
		<tr>
				<th width="60px;" ><input id="id_checkbox_if_all" onclick=ayTableCheckAll();
				type="checkbox" name="name_checkbox_if_all" />全选</th>
				<th width="150px;" >操作</th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ID_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . id%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_EXCEPTION_ID_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysExceptionId%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysExceptionId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','NAME_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . name%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . name%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','EXCEPTION_CLASS_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . exceptionClass%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . exceptionClass%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','BIZ_CLASS_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . bizClass%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . bizClass%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','METHOD_NAME_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . methodName%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . methodName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','LINE_NUMBER_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . lineNumber%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . lineNumber%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','MSG_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . msg%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . msg%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SERVLET_PATH_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . servletPath%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . servletPath%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_USER_ID_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysUserId%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysUserId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_USER_NAME_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysUserName%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysUserName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_USER_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createUser%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createTime%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_USER_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateUser%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . state%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . state%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CODE_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . code%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . code%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','AB_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . ab%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . ab%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . desc%>]排序" style="cursor: pointer;"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . desc%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.ID_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . id%></option>
	<option value="hit.SYS_EXCEPTION_ID_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysExceptionId%></option>
	<option value="hit.NAME_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . name%></option>
	<option value="hit.EXCEPTION_CLASS_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . exceptionClass%></option>
	<option value="hit.BIZ_CLASS_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . bizClass%></option>
	<option value="hit.METHOD_NAME_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . methodName%></option>
	<option value="hit.LINE_NUMBER_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . lineNumber%></option>
	<option value="hit.MSG_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . msg%></option>
	<option value="hit.SERVLET_PATH_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . servletPath%></option>
	<option value="hit.SYS_USER_ID_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysUserId%></option>
	<option value="hit.SYS_USER_NAME_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysUserName%></option>
	<option value="hit.CREATE_USER_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createUser%></option>
	<option value="hit.CREATE_TIME_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createTimeLong%></option>
	<option value="hit.UPDATE_USER_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateUser%></option>
	<option value="hit.UPDATE_TIME_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateTimeLong%></option>
	<option value="hit.STATE_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . state%></option>
	<option value="hit.CODE_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . code%></option>
	<option value="hit.AB_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . ab%></option>
	<option value="hit.DESC_"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . desc%></option>
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
			<td><input value="<%=info.get("SYS_EXCEPTION_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("SYS_EXCEPTION_ID_")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("SYS_EXCEPTION_ID_")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.ID_">&nbsp;<%=info.get("ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SYS_EXCEPTION_ID_">&nbsp;<%=info.get("SYS_EXCEPTION_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.NAME_">&nbsp;<%=info.get("NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.EXCEPTION_CLASS_">&nbsp;<%=info.get("EXCEPTION_CLASS_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.BIZ_CLASS_">&nbsp;<%=info.get("BIZ_CLASS_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.METHOD_NAME_">&nbsp;<%=info.get("METHOD_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.LINE_NUMBER_">&nbsp;<%=info.get("LINE_NUMBER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.MSG_">&nbsp;<%=info.get("MSG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SERVLET_PATH_">&nbsp;<%=info.get("SERVLET_PATH_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SYS_USER_ID_">&nbsp;<%=info.get("SYS_USER_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SYS_USER_NAME_">&nbsp;<%=info.get("SYS_USER_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CREATE_USER_">&nbsp;<%=info.get("CREATE_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CREATE_TIME_LONG_">&nbsp;<%=info.get("CREATE_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UPDATE_USER_">&nbsp;<%=info.get("UPDATE_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UPDATE_TIME_LONG_">&nbsp;<%=info.get("UPDATE_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.STATE_">&nbsp;<%=info.get("STATE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CODE_">&nbsp;<%=info.get("CODE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.AB_">&nbsp;<%=info.get("AB_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.DESC_">&nbsp;<%=info.get("DESC_")%></td>
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
	var url = "${pageContext.request.contextPath}/all/gen/sys_exception/t/del_all/bootstrap.do";
	var objs = document.getElementsByName("name_checkbox_ids");
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
		var url = "${pageContext.request.contextPath}/all/gen/sys_exception/t/del/bootstrap.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/all/gen/sys_exception/t/form/bootstrap.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/gen/sys_exception/t/form/bootstrap.do";
 	window.location.href=url;
}
	</script>