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
<form action="${pageContext.request.contextPath}/all/job/config/list.do"  id="id_form_list" method="post">
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
				value="新增" onclick="newRecord()">
				
				
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
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_QUARTZ_CONFIG_ID_');"
				title="按[<%=all.job.alias.SysQuartzConfigTAlias . sysQuartzConfigId%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzConfigTAlias . sysQuartzConfigId%></th>
		
		
		
		

			<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STARTED_CN_');"
				title="按[<%=all.job.alias.SysQuartzConfigTAlias . startedCn%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzConfigTAlias . startedCn%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','INSTANDBY_MODE_CN_');"
				title="按[<%=all.job.alias.SysQuartzConfigTAlias . instandbyModeCn%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzConfigTAlias . instandbyModeCn%></th>
	
	
	
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.job.alias.SysQuartzConfigTAlias . createTime%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzConfigTAlias . createTime%></th>
	
			<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.job.alias.SysQuartzConfigTAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.job.alias.SysQuartzConfigTAlias . updateTime%></th>

	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	
	<option value="hit.SYS_QUARTZ_CONFIG_ID_"><%=all.job.alias.SysQuartzConfigTAlias . sysQuartzConfigId%></option>
	
	
	<option value="hit.CREATE_TIME_"><%=all.job.alias.SysQuartzConfigTAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.job.alias.SysQuartzConfigTAlias . createTimeLong%></option>
	<option value="hit.UPDATE_TIME_"><%=all.job.alias.SysQuartzConfigTAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.job.alias.SysQuartzConfigTAlias . updateTimeLong%></option>
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
			<td><input value="<%=info.get("SYS_QUARTZ_CONFIG_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td>
			
				<a onclick="start('<%=info.get("SYS_QUARTZ_CONFIG_ID_")%>')"
						href="#"> 启动 </a> 
						
							<a onclick="pause('<%=info.get("SYS_QUARTZ_CONFIG_ID_")%>')"
						href="#"> 暂停 </a> 
						
					
							<a onclick="shutdown('<%=info.get("SYS_QUARTZ_CONFIG_ID_")%>')"
						href="#"> 关闭 </a> 
							
			 <a onclick="updateRecord('<%=info.get("SYS_QUARTZ_CONFIG_ID_")%>')" href="#"> 编辑 </a></td>
	
		<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;" id="hit.SYS_QUARTZ_CONFIG_ID_">&nbsp;<%=info.get("SYS_QUARTZ_CONFIG_ID_")%></td>
	
	
	
		<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.STARTED_CN_">&nbsp;<%=info.get("STARTED_CN_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.INSTANDBY_MODE_CN_">&nbsp;<%=info.get("INSTANDBY_MODE_CN_")%></td>
	
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>

	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>
	
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
 * 关闭
 */
function shutdown(id) {
	var url = "${pageContext.request.contextPath}/all/job/config/shutdown.do?id="
			+ id;
	window.location.href = url;
}

/**
 * 启动
 */
function start(id) {
	var url = "${pageContext.request.contextPath}/all/job/config/start.do?id="
			+ id;
	window.location.href = url;
}
/**
 * 暂停
 */
function pause(id) {
	var url = "${pageContext.request.contextPath}/all/job/config/pause.do?id="
			+ id;
	window.location.href = url;
}

/**
 * 
 删除所有记录
 */
function delAllRecord(id){
	var url = "${pageContext.request.contextPath}/all/gen/sys_quartz_config/t/del_all.do";
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
		var url = "${pageContext.request.contextPath}/all/gen/sys_quartz_config/t/del.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/all/job/config/form.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/job/config/form.do";
 	window.location.href=url;
}
	</script>