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
<form action="${pageContext.request.contextPath}/all/workflow/actwf/actwf_business_def/t/list/bootstrap.do"  id="id_form_list" method="post">
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
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ACTWF_BUSINESS_DEF_ID_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actwfBusinessDefId%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actwfBusinessDefId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','KEY_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . key%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . key%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STEP_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . step%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . step%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','NAME_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . name%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . name%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','WF_FLOW_DEF_ID_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . wfFlowDefId%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . wfFlowDefId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','WF_FORM_DEF_ID_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . wfFormDefId%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . wfFormDefId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ACT_DEF_ID_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actDefId%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actDefId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ACT_DEF_KEY_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actDefKey%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actDefKey%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_USER_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createUser%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createTime%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_USER_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateUser%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SN_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . sn%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . sn%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . state%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . state%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . desc%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . desc%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.ACTWF_BUSINESS_DEF_ID_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actwfBusinessDefId%></option>
	<option value="hit.KEY_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . key%></option>
	<option value="hit.STEP_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . step%></option>
	<option value="hit.NAME_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . name%></option>
	<option value="hit.WF_FLOW_DEF_ID_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . wfFlowDefId%></option>
	<option value="hit.WF_FORM_DEF_ID_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . wfFormDefId%></option>
	<option value="hit.ACT_DEF_ID_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actDefId%></option>
	<option value="hit.ACT_DEF_KEY_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actDefKey%></option>
	<option value="hit.CREATE_USER_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createUser%></option>
	<option value="hit.CREATE_TIME_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createTimeLong%></option>
	<option value="hit.UPDATE_USER_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateUser%></option>
	<option value="hit.UPDATE_TIME_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateTimeLong%></option>
	<option value="hit.SN_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . sn%></option>
	<option value="hit.STATE_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . state%></option>
	<option value="hit.DESC_"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . desc%></option>
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
			<td><input value="<%=info.get("ACTWF_BUSINESS_DEF_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("ACTWF_BUSINESS_DEF_ID_")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("ACTWF_BUSINESS_DEF_ID_")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.ACTWF_BUSINESS_DEF_ID_">&nbsp;<%=info.get("ACTWF_BUSINESS_DEF_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.KEY_">&nbsp;<%=info.get("KEY_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.STEP_">&nbsp;<%=info.get("STEP_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.NAME_">&nbsp;<%=info.get("NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.WF_FLOW_DEF_ID_">&nbsp;<%=info.get("WF_FLOW_DEF_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.WF_FORM_DEF_ID_">&nbsp;<%=info.get("WF_FORM_DEF_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.ACT_DEF_ID_">&nbsp;<%=info.get("ACT_DEF_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.ACT_DEF_KEY_">&nbsp;<%=info.get("ACT_DEF_KEY_")%></td>
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
	<td style="display: none;word-wrap : break-word;  " id="hit.SN_">&nbsp;<%=info.get("SN_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.STATE_">&nbsp;<%=info.get("STATE_")%></td>
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
	var url = "${pageContext.request.contextPath}/all/workflow/actwf/actwf_business_def/t/del_all/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/workflow/actwf/actwf_business_def/t/del/bootstrap.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/all/workflow/actwf/actwf_business_def/t/form/bootstrap.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/workflow/actwf/actwf_business_def/t/form/bootstrap.do";
 	window.location.href=url;
}
	</script>