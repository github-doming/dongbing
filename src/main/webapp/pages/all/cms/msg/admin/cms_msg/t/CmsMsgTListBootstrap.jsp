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
<form action="${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg/t/list/bootstrap.do"  id="id_form_list" method="post">
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
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . idx%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . idx%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CMS_MSG_ID_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . cmsMsgId%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . cmsMsgId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CMS_MSG_TOPIC_ID_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . cmsMsgTopicId%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . cmsMsgTopicId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SEND_USER_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . sendUser%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . sendUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','RECEIVE_USER_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . receiveUser%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . receiveUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','MSG_TYPE_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . msgType%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . msgType%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','MSG_TITLE_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . msgTitle%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . msgTitle%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CONTENT_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . content%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . content%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','URL_VIEW_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . urlView%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . urlView%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','REPLY_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . reply%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . reply%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_USER_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createUser%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createTime%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_USER_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateUser%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . state%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . state%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . desc%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . desc%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.IDX_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . idx%></option>
	<option value="hit.CMS_MSG_ID_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . cmsMsgId%></option>
	<option value="hit.CMS_MSG_TOPIC_ID_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . cmsMsgTopicId%></option>
	<option value="hit.SEND_USER_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . sendUser%></option>
	<option value="hit.RECEIVE_USER_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . receiveUser%></option>
	<option value="hit.MSG_TYPE_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . msgType%></option>
	<option value="hit.MSG_TITLE_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . msgTitle%></option>
	<option value="hit.CONTENT_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . content%></option>
	<option value="hit.URL_VIEW_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . urlView%></option>
	<option value="hit.REPLY_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . reply%></option>
	<option value="hit.CREATE_USER_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createUser%></option>
	<option value="hit.CREATE_TIME_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createTimeLong%></option>
	<option value="hit.UPDATE_USER_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateUser%></option>
	<option value="hit.UPDATE_TIME_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateTimeLong%></option>
	<option value="hit.STATE_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . state%></option>
	<option value="hit.DESC_"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . desc%></option>
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
			<td><input value="<%=info.get("CMS_MSG_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("CMS_MSG_ID_")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("CMS_MSG_ID_")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.IDX_">&nbsp;<%=info.get("IDX_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CMS_MSG_ID_">&nbsp;<%=info.get("CMS_MSG_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CMS_MSG_TOPIC_ID_">&nbsp;<%=info.get("CMS_MSG_TOPIC_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SEND_USER_">&nbsp;<%=info.get("SEND_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.RECEIVE_USER_">&nbsp;<%=info.get("RECEIVE_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.MSG_TYPE_">&nbsp;<%=info.get("MSG_TYPE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.MSG_TITLE_">&nbsp;<%=info.get("MSG_TITLE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CONTENT_">&nbsp;<%=info.get("CONTENT_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.URL_VIEW_">&nbsp;<%=info.get("URL_VIEW_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.REPLY_">&nbsp;<%=info.get("REPLY_")%></td>
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
	var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg/t/del_all/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg/t/del/bootstrap.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg/t/form/bootstrap.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg/t/form/bootstrap.do";
 	window.location.href=url;
}
	</script>