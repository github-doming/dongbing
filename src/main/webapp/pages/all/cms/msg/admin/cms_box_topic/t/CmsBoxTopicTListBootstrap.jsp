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
<form action="${pageContext.request.contextPath}/all/cms/msg/admin/cms_box_topic/t/list/bootstrap.do"  id="id_form_list" method="post">
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
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . idx%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . idx%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CMS_BOX_TOPIC_ID_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsBoxTopicId%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsBoxTopicId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CMS_SEND_BOX_DRAFT_ID_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsSendBoxDraftId%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsSendBoxDraftId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CMS_MSG_TOPIC_ID_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsMsgTopicId%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsMsgTopicId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','APP_USER_ID_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . appUserId%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . appUserId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TABLE_NAME_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . tableName%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . tableName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TABLE_ID_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . tableId%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . tableId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SEND_USER_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . sendUser%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . sendUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','RECEIVE_USER_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . receiveUser%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . receiveUser%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CHANNEL_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . channel%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . channel%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TOPIC_TYPE_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . topicType%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . topicType%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TOPIC_TITLE_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . topicTitle%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . topicTitle%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CONTENT_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . content%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . content%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . createTime%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . updateTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . state%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . state%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . desc%>]排序" style="cursor: pointer;"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . desc%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.IDX_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . idx%></option>
	<option value="hit.CMS_BOX_TOPIC_ID_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsBoxTopicId%></option>
	<option value="hit.CMS_SEND_BOX_DRAFT_ID_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsSendBoxDraftId%></option>
	<option value="hit.CMS_MSG_TOPIC_ID_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsMsgTopicId%></option>
	<option value="hit.APP_USER_ID_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . appUserId%></option>
	<option value="hit.TABLE_NAME_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . tableName%></option>
	<option value="hit.TABLE_ID_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . tableId%></option>
	<option value="hit.SEND_USER_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . sendUser%></option>
	<option value="hit.RECEIVE_USER_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . receiveUser%></option>
	<option value="hit.CHANNEL_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . channel%></option>
	<option value="hit.TOPIC_TYPE_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . topicType%></option>
	<option value="hit.TOPIC_TITLE_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . topicTitle%></option>
	<option value="hit.CONTENT_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . content%></option>
	<option value="hit.CREATE_TIME_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . createTimeLong%></option>
	<option value="hit.UPDATE_TIME_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . updateTime%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . updateTimeLong%></option>
	<option value="hit.STATE_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . state%></option>
	<option value="hit.DESC_"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . desc%></option>
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
			<td><input value="<%=info.get("CMS_BOX_TOPIC_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("CMS_BOX_TOPIC_ID_")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("CMS_BOX_TOPIC_ID_")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.IDX_">&nbsp;<%=info.get("IDX_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CMS_BOX_TOPIC_ID_">&nbsp;<%=info.get("CMS_BOX_TOPIC_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CMS_SEND_BOX_DRAFT_ID_">&nbsp;<%=info.get("CMS_SEND_BOX_DRAFT_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CMS_MSG_TOPIC_ID_">&nbsp;<%=info.get("CMS_MSG_TOPIC_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.APP_USER_ID_">&nbsp;<%=info.get("APP_USER_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TABLE_NAME_">&nbsp;<%=info.get("TABLE_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TABLE_ID_">&nbsp;<%=info.get("TABLE_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SEND_USER_">&nbsp;<%=info.get("SEND_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.RECEIVE_USER_">&nbsp;<%=info.get("RECEIVE_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CHANNEL_">&nbsp;<%=info.get("CHANNEL_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TOPIC_TYPE_">&nbsp;<%=info.get("TOPIC_TYPE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.TOPIC_TITLE_">&nbsp;<%=info.get("TOPIC_TITLE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CONTENT_">&nbsp;<%=info.get("CONTENT_")%></td>
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
	var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_box_topic/t/del_all/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_box_topic/t/del/bootstrap.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/all/cms/msg/admin/cms_box_topic/t/form/bootstrap.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/cms/msg/admin/cms_box_topic/t/form/bootstrap.do";
 	window.location.href=url;
}
	</script>