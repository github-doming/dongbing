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
<form action="${pageContext.request.contextPath}/all/workflow/actwf/actwf_form_def/t/list/bootstrap.do"  id="id_form_list" method="post">
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
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ACTWF_FORM_DEF_ID_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . actwfFormDefId%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . actwfFormDefId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','KEY_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . key%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . key%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','NAME_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . name%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . name%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TABLE_NAME_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . tableName%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . tableName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TITLE_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . title%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . title%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CONTENT_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . content%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . content%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','EDITION_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . edition%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . edition%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TYPE_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . type%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . type%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','URL_VIEW_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlView%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlView%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','URL_UPLOAD_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlUpload%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlUpload%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','URL_DOWNLOAD_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlDownload%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlDownload%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_USER_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createUser%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createTime%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_USER_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateUser%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SN_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . sn%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . sn%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th  style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . state%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . state%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th style="display: none; cursor: pointer;" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . desc%>]排序" style="cursor: pointer;"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . desc%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.ACTWF_FORM_DEF_ID_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . actwfFormDefId%></option>
	<option value="hit.KEY_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . key%></option>
	<option value="hit.NAME_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . name%></option>
	<option value="hit.TABLE_NAME_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . tableName%></option>
	<option value="hit.TITLE_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . title%></option>
	<option value="hit.CONTENT_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . content%></option>
	<option value="hit.EDITION_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . edition%></option>
	<option value="hit.TYPE_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . type%></option>
	<option value="hit.URL_VIEW_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlView%></option>
	<option value="hit.URL_UPLOAD_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlUpload%></option>
	<option value="hit.URL_DOWNLOAD_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlDownload%></option>
	<option value="hit.CREATE_USER_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createUser%></option>
	<option value="hit.CREATE_TIME_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createTimeLong%></option>
	<option value="hit.UPDATE_USER_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateUser%></option>
	<option value="hit.UPDATE_TIME_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateTimeLong%></option>
	<option value="hit.SN_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . sn%></option>
	<option value="hit.STATE_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . state%></option>
	<option value="hit.DESC_"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . desc%></option>
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
			<td><input value="<%=info.get("ACTWF_FORM_DEF_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td>
			
			<a onclick="designRecord('<%=info.get("ACTWF_FORM_DEF_ID_")%>')" href="#"> 设计 </a>
			
			
			<a onclick="updateRecord('<%=info.get("ACTWF_FORM_DEF_ID_")%>')" href="#"> 编辑 </a>
			
			<a onclick="delRecord('<%=info.get("ACTWF_FORM_DEF_ID_")%>')" href="#"> 删除
			</a> 
			
			
			
			</td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.ACTWF_FORM_DEF_ID_">&nbsp;<%=info.get("ACTWF_FORM_DEF_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.KEY_">&nbsp;<%=info.get("KEY_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.NAME_">&nbsp;<%=info.get("NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TABLE_NAME_">&nbsp;<%=info.get("TABLE_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TITLE_">&nbsp;<%=info.get("TITLE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.CONTENT_">&nbsp;<%=info.get("CONTENT_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.EDITION_">&nbsp;<%=info.get("EDITION_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TYPE_">&nbsp;<%=info.get("TYPE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.URL_VIEW_">&nbsp;<%=info.get("URL_VIEW_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.URL_UPLOAD_">&nbsp;<%=info.get("URL_UPLOAD_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;  " id="hit.URL_DOWNLOAD_">&nbsp;<%=info.get("URL_DOWNLOAD_")%></td>
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
	var url = "${pageContext.request.contextPath}/all/workflow/actwf/actwf_form_def/t/del_all/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/workflow/actwf/actwf_form_def/t/del/bootstrap.do?id="+id;
		window.location.href= url;
	}
	else
	{
	}
}

/**
 * 
 请求design
 */
function designRecord(id){
	var url= "${pageContext.request.contextPath}/all/workflow/actwf/actwf_form_def/t/design.do?id="+id;
 	window.location.href=url;
}

/**
 * 
 请求更新
 */
function updateRecord(id){
	var url= "${pageContext.request.contextPath}/all/workflow/actwf/actwf_form_def/t/form/bootstrap.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/workflow/actwf/actwf_form_def/t/form/bootstrap.do";
 	window.location.href=url;
}
	</script>