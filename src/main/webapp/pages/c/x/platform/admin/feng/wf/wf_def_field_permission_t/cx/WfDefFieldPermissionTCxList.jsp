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
<form action="${pageContext.request.contextPath}/c/x/platform/admin/feng/wf/wf_def_field_permission_t/cx/list.do"  id="id_form_list" method="post">
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
				<th width="60px;" >操作</th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','id');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . id%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','field_name');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . fieldName%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . fieldName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','is_show');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . isShow%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . isShow%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','is_write');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . isWrite%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . isWrite%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','def_form_id');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . defFormId%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . defFormId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','node_name');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . nodeName%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . nodeName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','def_process_id');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . defProcessId%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . defProcessId%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.id"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . id%></option>
	<option value="hit.field_name"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . fieldName%></option>
	<option value="hit.is_show"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . isShow%></option>
	<option value="hit.is_write"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . isWrite%></option>
	<option value="hit.def_form_id"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . defFormId%></option>
	<option value="hit.node_name"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . nodeName%></option>
	<option value="hit.def_process_id"><%=c.x.platform.admin.feng.wf.wf_def_field_permission_t.cx.alias.WfDefFieldPermissionTCxAlias . defProcessId%></option>
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
			<td><input value="<%=info.get("id")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("id")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("id")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.id">&nbsp;<%=info.get("id")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.field_name">&nbsp;<%=info.get("field_name")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.is_show">&nbsp;<%=info.get("is_show")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.is_write">&nbsp;<%=info.get("is_write")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.def_form_id">&nbsp;<%=info.get("def_form_id")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.node_name">&nbsp;<%=info.get("node_name")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.def_process_id">&nbsp;<%=info.get("def_process_id")%></td>
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
	var url = "${pageContext.request.contextPath}/c/x/platform/admin/feng/wf/wf_def_field_permission_t/cx/del_all.do";
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
		var url = "${pageContext.request.contextPath}/c/x/platform/admin/feng/wf/wf_def_field_permission_t/cx/del.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/c/x/platform/admin/feng/wf/wf_def_field_permission_t/cx/form.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/c/x/platform/admin/feng/wf/wf_def_field_permission_t/cx/form.do";
 	window.location.href=url;
}
	</script>