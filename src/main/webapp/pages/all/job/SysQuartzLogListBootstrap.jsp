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
<form action="${pageContext.request.contextPath}/all/job/log/list.do"  id="id_form_list" method="post">
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
			<td>
			
			<input class="btn" id="id_input_new" type="button"
				value="新增" onclick="newRecord()">
				
				 <input class="btn"
				id="id_input_new" type="button" value="删除" onclick="delAllRecord()">
				
				
			 <input class="btn"
				id="id_input_new" type="button" value="清空" onclick="clearRecord()">
				
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
<th style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_QUARTZ_LOG_ID_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . sysQuartzLogId%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . sysQuartzLogId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','JOB_NAME_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . jobName%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . jobName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','JOB_CLASS_NAME_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . jobClassName%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . jobClassName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TRIGGER_NAME_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . triggerName%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . triggerName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CONTENT_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . content%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . content%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','RUN_TIME_');"
				title="按[持续时间]排序" style="cursor: pointer;">持续时间</th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','START_TIME_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . startTime%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . startTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','START_TIME_LONG_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . startTimeLong%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . startTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','END_TIME_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . endTime%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . endTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','END_TIME_LONG_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . endTimeLong%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . endTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . createTime%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . updateTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . state%>]排序" style="cursor: pointer;">状态</th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=all.job.alias.SysQuartzLogTAlias . desc%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzLogTAlias . desc%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	
	
	
	<option value="hit.SYS_QUARTZ_LOG_ID_"><%=all.job.alias.SysQuartzLogTAlias . sysQuartzLogId%></option>
	<option value="hit.JOB_NAME_"><%=all.job.alias.SysQuartzLogTAlias . jobName%></option>
	<option value="hit.JOB_CLASS_NAME_"><%=all.job.alias.SysQuartzLogTAlias . jobClassName%></option>
	<option value="hit.TRIGGER_NAME_"><%=all.job.alias.SysQuartzLogTAlias . triggerName%></option>
	<option value="hit.CONTENT_"><%=all.job.alias.SysQuartzLogTAlias . content%></option>
	<option value="hit.RUN_TIME_">持续时间</option>
	<option value="hit.START_TIME_"><%=all.job.alias.SysQuartzLogTAlias . startTime%></option>
	<option value="hit.START_TIME_LONG_"><%=all.job.alias.SysQuartzLogTAlias . startTimeLong%></option>
	<option value="hit.END_TIME_"><%=all.job.alias.SysQuartzLogTAlias . endTime%></option>
	<option value="hit.END_TIME_LONG_"><%=all.job.alias.SysQuartzLogTAlias . endTimeLong%></option>
	<option value="hit.CREATE_TIME_"><%=all.job.alias.SysQuartzLogTAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.job.alias.SysQuartzLogTAlias . createTimeLong%></option>
	<option value="hit.UPDATE_TIME_"><%=all.job.alias.SysQuartzLogTAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.job.alias.SysQuartzLogTAlias . updateTimeLong%></option>
	<option value="hit.STATE_">状态</option>
	<option value="hit.DESC_"><%=all.job.alias.SysQuartzLogTAlias . desc%></option>
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
			<td><input value="<%=info.get("SYS_QUARTZ_LOG_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td>
			
			<a onclick="delRecord('<%=info.get("SYS_QUARTZ_LOG_ID_")%>')" href="#"> 删除
			</a>
			
			
			</td>
	
	

	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;"   id="hit.SYS_QUARTZ_LOG_ID_">&nbsp;<%=info.get("SYS_QUARTZ_LOG_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.JOB_NAME_">&nbsp;<%=info.get("JOB_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.JOB_CLASS_NAME_">&nbsp;<%=info.get("JOB_CLASS_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TRIGGER_NAME_">&nbsp;<%=info.get("TRIGGER_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CONTENT_">&nbsp;<%=info.get("CONTENT_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.RUN_TIME_">&nbsp;<%=info.get("RUN_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.START_TIME_">&nbsp;<%=info.get("START_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td  style="display: none;word-wrap : break-word;" id="hit.START_TIME_LONG_">&nbsp;<%=info.get("START_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.END_TIME_">&nbsp;<%=info.get("END_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td  style="display: none;word-wrap : break-word;" id="hit.END_TIME_LONG_">&nbsp;<%=info.get("END_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td  style="display: none;word-wrap : break-word;" id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td  style="display: none;word-wrap : break-word;" id="hit.CREATE_TIME_LONG_">&nbsp;<%=info.get("CREATE_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td  style="display: none;word-wrap : break-word;" id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td  style="display: none;word-wrap : break-word;" id="hit.UPDATE_TIME_LONG_">&nbsp;<%=info.get("UPDATE_TIME_LONG_")%></td>
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
清空记录
 */
function clearRecord(){
	//alert('clear');
	if(confirm("确定要清空吗？"))
	{
		var url = "${pageContext.request.contextPath}/all/job/log/clear.do";
		window.location.href= url;
	}
	else
	{
	}
}


/**
 * 
 删除所有记录
 */
function delAllRecord(id){
	var url = "${pageContext.request.contextPath}/all/job/log/delAll.do";
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
		var url = "${pageContext.request.contextPath}/all/job/log/del.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/all/gen/sys_quartz_log/t/form/bootstrap.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/gen/sys_quartz_log/t/form/bootstrap.do";
 	window.location.href=url;
}
	</script>