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
<form action="${pageContext.request.contextPath}/c/x/platform/admin/feng/wf/wf_ins_node_t/cx/list.do"  id="id_form_list" method="post">
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
				title="按[<%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . id%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','node_name');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . nodeName%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . nodeName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','status');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . status%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . status%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','to_node_name');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . toNodeName%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . toNodeName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','start_time_dt');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . startTimeDt%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . startTimeDt%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','start_time');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . startTime%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . startTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','end_time_dt');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . endTimeDt%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . endTimeDt%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','end_time');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . endTime%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . endTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ins_process_id');"
				title="按[<%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . insProcessId%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . insProcessId%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.id"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . id%></option>
	<option value="hit.node_name"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . nodeName%></option>
	<option value="hit.status"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . status%></option>
	<option value="hit.to_node_name"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . toNodeName%></option>
	<option value="hit.start_time_dt"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . startTimeDt%></option>
	<option value="hit.start_time"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . startTime%></option>
	<option value="hit.end_time_dt"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . endTimeDt%></option>
	<option value="hit.end_time"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . endTime%></option>
	<option value="hit.ins_process_id"><%=c.x.platform.admin.feng.wf.wf_ins_node_t.cx.alias.WfInsNodeTCxAlias . insProcessId%></option>
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
	<td style="word-wrap : break-word;  " id="hit.node_name">&nbsp;<%=info.get("node_name")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.status">&nbsp;<%=info.get("status")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.to_node_name">&nbsp;<%=info.get("to_node_name")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.start_time_dt">&nbsp;<%=info.get("start_time_dt")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.start_time">&nbsp;<%=info.get("start_time")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.end_time_dt">&nbsp;<%=info.get("end_time_dt")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.end_time">&nbsp;<%=info.get("end_time")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.ins_process_id">&nbsp;<%=info.get("ins_process_id")%></td>
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
	var url = "${pageContext.request.contextPath}/c/x/platform/admin/feng/wf/wf_ins_node_t/cx/del_all.do";
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
		var url = "${pageContext.request.contextPath}/c/x/platform/admin/feng/wf/wf_ins_node_t/cx/del.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/c/x/platform/admin/feng/wf/wf_ins_node_t/cx/form.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/c/x/platform/admin/feng/wf/wf_ins_node_t/cx/form.do";
 	window.location.href=url;
}
	</script>