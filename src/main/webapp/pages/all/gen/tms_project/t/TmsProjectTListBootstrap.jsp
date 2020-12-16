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
<form action="${pageContext.request.contextPath}/all/gen/tms_project/t/list/bootstrap.do"  id="id_form_list" method="post">
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
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . idx%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . idx%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TMS_PROJECT_ID_ID_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . tmsProjectIdId%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . tmsProjectIdId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SN_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . sn%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . sn%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PARENT_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . parent%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . parent%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PATH_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . path%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . path%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TREE_CODE_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . treeCode%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . treeCode%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PROJECT_NAME_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . projectName%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . projectName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TYPE_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . type%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . type%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TITLE_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . title%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . title%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CONTENT_SHORT_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . contentShort%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . contentShort%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CONTENT_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . content%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . content%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','URL_VIEW_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . urlView%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . urlView%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_USER_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . createUser%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . createUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . createTime%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_USER_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateUser%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . state%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . state%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=all.gen.tms_project.t.alias.TmsProjectTAlias . desc%>]排序" style="cursor: pointer;"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . desc%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.IDX_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . idx%></option>
	<option value="hit.TMS_PROJECT_ID_ID_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . tmsProjectIdId%></option>
	<option value="hit.SN_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . sn%></option>
	<option value="hit.PARENT_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . parent%></option>
	<option value="hit.PATH_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . path%></option>
	<option value="hit.TREE_CODE_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . treeCode%></option>
	<option value="hit.PROJECT_NAME_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . projectName%></option>
	<option value="hit.TYPE_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . type%></option>
	<option value="hit.TITLE_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . title%></option>
	<option value="hit.CONTENT_SHORT_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . contentShort%></option>
	<option value="hit.CONTENT_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . content%></option>
	<option value="hit.URL_VIEW_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . urlView%></option>
	<option value="hit.CREATE_USER_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . createUser%></option>
	<option value="hit.CREATE_TIME_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . createTimeLong%></option>
	<option value="hit.UPDATE_USER_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateUser%></option>
	<option value="hit.UPDATE_TIME_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateTimeLong%></option>
	<option value="hit.STATE_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . state%></option>
	<option value="hit.DESC_"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . desc%></option>
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
			<td><input value="<%=info.get("TMS_PROJECT_ID_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("TMS_PROJECT_ID_ID_")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("TMS_PROJECT_ID_ID_")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.IDX_">&nbsp;<%=info.get("IDX_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TMS_PROJECT_ID_ID_">&nbsp;<%=info.get("TMS_PROJECT_ID_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SN_">&nbsp;<%=info.get("SN_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.PARENT_">&nbsp;<%=info.get("PARENT_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.PATH_">&nbsp;<%=info.get("PATH_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TREE_CODE_">&nbsp;<%=info.get("TREE_CODE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.PROJECT_NAME_">&nbsp;<%=info.get("PROJECT_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TYPE_">&nbsp;<%=info.get("TYPE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TITLE_">&nbsp;<%=info.get("TITLE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CONTENT_SHORT_">&nbsp;<%=info.get("CONTENT_SHORT_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CONTENT_">&nbsp;<%=info.get("CONTENT_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.URL_VIEW_">&nbsp;<%=info.get("URL_VIEW_")%></td>
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
	var url = "${pageContext.request.contextPath}/all/gen/tms_project/t/del_all/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/gen/tms_project/t/del/bootstrap.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/all/gen/tms_project/t/form/bootstrap.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/gen/tms_project/t/form/bootstrap.do";
 	window.location.href=url;
}
	</script>