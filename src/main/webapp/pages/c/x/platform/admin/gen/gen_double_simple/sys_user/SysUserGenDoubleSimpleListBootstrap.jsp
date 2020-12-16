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
<form action="${pageContext.request.contextPath}/c/x/platform/admin/gen/gen_double_simple/sys_user/list.do"  id="id_form_list" method="post">

<table  align="center" border="1">
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
			name="name_first$tree$id" /></td>
		<td></td>
		<td></td>
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
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ID_');"
				title="按[<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . id%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_USER_ID_');"
				title="按[<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . sysUserId%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . sysUserId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_PERSON_ID_');"
				title="按[<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . sysPersonId%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . sysPersonId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','NAME_');"
				title="按[<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . name%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . name%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PERMISSION_GRADE_');"
				title="按[<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . permissionGrade%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . permissionGrade%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . desc%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . desc%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . createTime%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . updateTime%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . updateTimeLong%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.ID_"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . id%></option>
	<option value="hit.SYS_USER_ID_"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . sysUserId%></option>
	<option value="hit.SYS_PERSON_ID_"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . sysPersonId%></option>
	<option value="hit.NAME_"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . name%></option>
	<option value="hit.PERMISSION_GRADE_"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . permissionGrade%></option>
	<option value="hit.DESC_"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . desc%></option>
	<option value="hit.CREATE_TIME_"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . createTimeLong%></option>
	<option value="hit.UPDATE_TIME_"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . updateTimeLong%></option>
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
			<td><a onclick="delRecord('<%=info.get("SYS_USER_ID_")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("SYS_USER_ID_")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.ID_">&nbsp;<%=info.get("ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SYS_USER_ID_">&nbsp;<%=info.get("SYS_USER_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SYS_PERSON_ID_">&nbsp;<%=info.get("SYS_PERSON_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.NAME_">&nbsp;<%=info.get("NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.PERMISSION_GRADE_">&nbsp;<%=info.get("PERMISSION_GRADE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.DESC_">&nbsp;<%=info.get("DESC_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CREATE_TIME_LONG_">&nbsp;<%=info.get("CREATE_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UPDATE_TIME_LONG_">&nbsp;<%=info.get("UPDATE_TIME_LONG_")%></td>
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
	var url = "${pageContext.request.contextPath}/c/x/platform/admin/gen/gen_double_simple/sys_user/del_all.do";
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
		var url = "${pageContext.request.contextPath}/c/x/platform/admin/gen/gen_double_simple/sys_user/del.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/c/x/platform/admin/gen/gen_double_simple/sys_user/form.do?id="+id;
	var first$tree$id=document.getElementById("id_first$tree$id").value;
	url=url+"&&first$tree$id="+first$tree$id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/c/x/platform/admin/gen/gen_double_simple/sys_user/form.do";
 	var first$tree$id=document.getElementById("id_first$tree$id").value;
 	url=url+"?first$tree$id="+first$tree$id;
 	window.location.href=url;
}
	</script>