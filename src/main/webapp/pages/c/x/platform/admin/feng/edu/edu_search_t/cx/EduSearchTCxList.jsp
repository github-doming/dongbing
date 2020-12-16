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
<form action="${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_search_t/cx/list.do"  id="id_form_list" method="post">
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
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . id%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','name');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . name%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . name%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','address');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . address%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . address%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','license_type');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . licenseType%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . licenseType%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','price');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . price%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . price%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','time_type');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . timeType%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . timeType%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','score');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . score%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . score%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','edu_class_id');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . eduClassId%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . eduClassId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','edu_teacher_id');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . eduTeacherId%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . eduTeacherId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','uid');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . uid%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . uid%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','distance');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . distance%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . distance%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','lat');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . lat%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . lat%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','lng');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . lng%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . lng%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','description');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . description%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . description%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','street_id');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . streetId%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . streetId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','is_del');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . isDel%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . isDel%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','time_description');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . timeDescription%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . timeDescription%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','square');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . square%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . square%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','max_student');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxStudent%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxStudent%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','max_teacher');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxTeacher%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxTeacher%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','max_automoblie');"
				title="按[<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxAutomoblie%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxAutomoblie%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.id"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . id%></option>
	<option value="hit.name"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . name%></option>
	<option value="hit.address"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . address%></option>
	<option value="hit.license_type"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . licenseType%></option>
	<option value="hit.price"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . price%></option>
	<option value="hit.time_type"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . timeType%></option>
	<option value="hit.score"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . score%></option>
	<option value="hit.edu_class_id"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . eduClassId%></option>
	<option value="hit.edu_teacher_id"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . eduTeacherId%></option>
	<option value="hit.uid"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . uid%></option>
	<option value="hit.distance"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . distance%></option>
	<option value="hit.lat"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . lat%></option>
	<option value="hit.lng"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . lng%></option>
	<option value="hit.description"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . description%></option>
	<option value="hit.street_id"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . streetId%></option>
	<option value="hit.is_del"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . isDel%></option>
	<option value="hit.time_description"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . timeDescription%></option>
	<option value="hit.square"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . square%></option>
	<option value="hit.max_student"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxStudent%></option>
	<option value="hit.max_teacher"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxTeacher%></option>
	<option value="hit.max_automoblie"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxAutomoblie%></option>
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
	<td style="word-wrap : break-word;  " id="hit.name">&nbsp;<%=info.get("name")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.address">&nbsp;<%=info.get("address")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.license_type">&nbsp;<%=info.get("license_type")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.price">&nbsp;<%=info.get("price")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.time_type">&nbsp;<%=info.get("time_type")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.score">&nbsp;<%=info.get("score")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.edu_class_id">&nbsp;<%=info.get("edu_class_id")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.edu_teacher_id">&nbsp;<%=info.get("edu_teacher_id")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.uid">&nbsp;<%=info.get("uid")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.distance">&nbsp;<%=info.get("distance")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.lat">&nbsp;<%=info.get("lat")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.lng">&nbsp;<%=info.get("lng")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.description">&nbsp;<%=info.get("description")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.street_id">&nbsp;<%=info.get("street_id")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.is_del">&nbsp;<%=info.get("is_del")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.time_description">&nbsp;<%=info.get("time_description")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.square">&nbsp;<%=info.get("square")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.max_student">&nbsp;<%=info.get("max_student")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.max_teacher">&nbsp;<%=info.get("max_teacher")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.max_automoblie">&nbsp;<%=info.get("max_automoblie")%></td>
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
	var url = "${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_search_t/cx/del_all.do";
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
		var url = "${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_search_t/cx/del.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_search_t/cx/form.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_search_t/cx/form.do";
 	window.location.href=url;
}
	</script>