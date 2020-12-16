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
<form action="${pageContext.request.contextPath}/all/sys_admin/sys/sys_monitor/t/list/bootstrap.do"  id="id_form_list" method="post">
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
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','IDX_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . idx%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . idx%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_MONITOR_ID_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . sysMonitorId%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . sysMonitorId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','OS_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . os%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . os%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PID_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . pid%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . pid%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','THREAD_COUNT_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . threadCount%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . threadCount%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CPU_RATE_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . cpuRate%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . cpuRate%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','MEMORY_TOTAL_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryTotal%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryTotal%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','MEMORY_JVM_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryJvm%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryJvm%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','MEMORY_RATE_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryRate%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryRate%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UP_SPEED_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . upSpeed%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . upSpeed%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DOWN_SPEED_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . downSpeed%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . downSpeed%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . createTime%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . updateTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . state%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . state%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . desc%>]排序" style="cursor: pointer;"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . desc%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.IDX_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . idx%></option>
	<option value="hit.SYS_MONITOR_ID_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . sysMonitorId%></option>
	<option value="hit.OS_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . os%></option>
	<option value="hit.PID_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . pid%></option>
	<option value="hit.THREAD_COUNT_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . threadCount%></option>
	<option value="hit.CPU_RATE_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . cpuRate%></option>
	<option value="hit.MEMORY_TOTAL_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryTotal%></option>
	<option value="hit.MEMORY_JVM_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryJvm%></option>
	<option value="hit.MEMORY_RATE_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryRate%></option>
	<option value="hit.UP_SPEED_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . upSpeed%></option>
	<option value="hit.DOWN_SPEED_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . downSpeed%></option>
	<option value="hit.CREATE_TIME_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . createTimeLong%></option>
	<option value="hit.UPDATE_TIME_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . updateTimeLong%></option>
	<option value="hit.STATE_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . state%></option>
	<option value="hit.DESC_"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . desc%></option>
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
			<td><input value="<%=info.get("SYS_MONITOR_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("SYS_MONITOR_ID_")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("SYS_MONITOR_ID_")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.IDX_">&nbsp;<%=info.get("IDX_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SYS_MONITOR_ID_">&nbsp;<%=info.get("SYS_MONITOR_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.OS_">&nbsp;<%=info.get("OS_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.PID_">&nbsp;<%=info.get("PID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.THREAD_COUNT_">&nbsp;<%=info.get("THREAD_COUNT_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CPU_RATE_">&nbsp;<%=info.get("CPU_RATE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.MEMORY_TOTAL_">&nbsp;<%=info.get("MEMORY_TOTAL_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.MEMORY_JVM_">&nbsp;<%=info.get("MEMORY_JVM_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.MEMORY_RATE_">&nbsp;<%=info.get("MEMORY_RATE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UP_SPEED_">&nbsp;<%=info.get("UP_SPEED_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.DOWN_SPEED_">&nbsp;<%=info.get("DOWN_SPEED_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CREATE_TIME_LONG_">&nbsp;<%=info.get("CREATE_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UPDATE_TIME_LONG_">&nbsp;<%=info.get("UPDATE_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.STATE_">&nbsp;<%=info.get("STATE_")%></td>
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
	var url = "${pageContext.request.contextPath}/all/sys_admin/sys/sys_monitor/t/del_all/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/sys_admin/sys/sys_monitor/t/del/bootstrap.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/all/sys_admin/sys/sys_monitor/t/form/bootstrap.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/sys_admin/sys/sys_monitor/t/form/bootstrap.do";
 	window.location.href=url;
}
	</script>