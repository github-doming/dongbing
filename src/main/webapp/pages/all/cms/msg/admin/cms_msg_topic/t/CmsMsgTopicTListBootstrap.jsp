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
<form action="${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg_topic/t/list/bootstrap.do"  id="id_form_list" method="post">
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
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CMS_MSG_TOPIC_ID_');"
				title="按[<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . cmsMsgTopicId%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . cmsMsgTopicId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SEND_USER_');"
				title="按[<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . sendUser%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . sendUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','RECEIVE_USER_');"
				title="按[<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . receiveUser%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . receiveUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','MSG_TOPIC_TYPE_');"
				title="按[<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . msgTopicType%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . msgTopicType%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','MSG_TOPIC_TITLE_');"
				title="按[<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . msgTopicTitle%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . msgTopicTitle%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CONTENT_');"
				title="按[<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . content%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . content%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . state%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . state%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.CMS_MSG_TOPIC_ID_"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . cmsMsgTopicId%></option>
	<option value="hit.SEND_USER_"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . sendUser%></option>
	<option value="hit.RECEIVE_USER_"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . receiveUser%></option>
	<option value="hit.MSG_TOPIC_TYPE_"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . msgTopicType%></option>
	<option value="hit.MSG_TOPIC_TITLE_"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . msgTopicTitle%></option>
	<option value="hit.CONTENT_"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . content%></option>
	<option value="hit.STATE_"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . state%></option>
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
			<td><input value="<%=info.get("CMS_MSG_TOPIC_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("CMS_MSG_TOPIC_ID_")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("CMS_MSG_TOPIC_ID_")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CMS_MSG_TOPIC_ID_">&nbsp;<%=info.get("CMS_MSG_TOPIC_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SEND_USER_">&nbsp;<%=info.get("SEND_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.RECEIVE_USER_">&nbsp;<%=info.get("RECEIVE_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.MSG_TOPIC_TYPE_">&nbsp;<%=info.get("MSG_TOPIC_TYPE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.MSG_TOPIC_TITLE_">&nbsp;<%=info.get("MSG_TOPIC_TITLE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CONTENT_">&nbsp;<%=info.get("CMS_CONTENT_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.STATE_">&nbsp;<%=info.get("STATE_")%></td>
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
	var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg_topic/t/del_all/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg_topic/t/del/bootstrap.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg_topic/t/form/bootstrap.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg_topic/t/form/bootstrap.do";
 	window.location.href=url;
}
	</script>