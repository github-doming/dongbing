<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/kaida/common.jsp"%>
<title></title>
</head>
<body>
<div style="display: none;" id="id_div_msg">正在提交中...</div>
<form action="${pageContext.request.contextPath}/admin/sys/fas_subject_info/list.do"  id="id_form_list" method="post">
<table  style="display: none;" class="table table-hover" border="0">
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





<table class="class_crud_table" border="0" cellpadding="0" cellspacing="1">
	<tr bgcolor="#FFFFFF">
		<td width="99.9%" >
		&nbsp;&nbsp;<img
			src="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/form/images/current_directory.jpg"
			align="middle">&nbsp;<span class="class_font_size_12px">当前位置：菜单管理 &gt;
			&nbsp;菜单列表</span>
			</td>
		<td align="right"
	
			background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/pannel_title_bg.gif"
			valign="middle">&nbsp;</td>
	</tr> 
	<tr bgcolor="#FFFFFF">
		<td width="99.9%">
		<input class="btn" id="id_input_new" type="button"
				value="新增" onclick="newRecord()"> <input class="btn"
				id="id_input_new" type="button" value="删除" onclick="delAllRecord()">
		</td>
		<td align="right"
			background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/pannel_title_bg.gif"
			valign="middle">&nbsp;</td>
	</tr>
	
	<tr height="25px;"bgcolor="#FFFFFF">
		<td width="99.9%">
		
		</td>
		<td align="right"
			background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/pannel_title_bg.gif"
			valign="middle">&nbsp;</td>
	</tr>
	
</table>



<table class="class_crud_table" id="id_table" border="0" cellpadding="0" cellspacing="1">
	<caption></caption>
	<thead>
		<tr>
				<th width="60px;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><input id="id_checkbox_if_all" onclick=ayTableCheckAll();
				type="checkbox" name="" />全选</th>
				<th width="60px;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif">操作</th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','id');"
				title="按[<%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . id%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','name');"
				title="按[<%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . name%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . name%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','help_code');"
				title="按[<%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . help_code%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . help_code%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','sys_description');"
				title="按[<%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . sys_description%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . sys_description%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','sys_enable');"
				title="按[<%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . sys_enable%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . sys_enable%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','sys_del');"
				title="按[<%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . sys_del%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . sys_del%></th>
	<th class="class_color" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.id"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . id%></option>
	<option value="hit.name"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . name%></option>
	<option value="hit.help_code"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . help_code%></option>
	<option value="hit.sys_description"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . sys_description%></option>
	<option value="hit.sys_enable"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . sys_enable%></option>
	<option value="hit.sys_del"><%=c.x.platform.feng.feng.fas.cx.fas_subject_info.alias.FasSubjectInfoAlias . sys_del%></option>
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
			<td class="class_crud_td_white"><input value="<%=info.get("${name.columnName}")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td  class="class_crud_td_white"><a class="class_a" onclick="delRecord('<%=info.get("id")%>')" href="#"> 删除
			</a> <a class="class_a" onclick="updateRecord('<%=info.get("id")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.id">&nbsp;<%=info.get("id")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.name">&nbsp;<%=info.get("name")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.help_code">&nbsp;<%=info.get("help_code")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.sys_description">&nbsp;<%=info.get("sys_description")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.sys_enable">&nbsp;<%=info.get("sys_enable")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.sys_del">&nbsp;<%=info.get("sys_del")%></td>
			<td class="class_crud_td_white">&nbsp;</td>
		</tr>
		<%
		}
	%>
	</tbody>
</table>
<%@ include file="/pages/c/x/imports/feng/page/kaida/page.jsp"%>
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
	var url = "${pageContext.request.contextPath}/admin/sys/fas_subject_info/del_all.do";
	var objs = document.getElementsByName("checkboxIds");
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
		var url = "${pageContext.request.contextPath}/admin/sys/fas_subject_info/del.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/admin/sys/fas_subject_info/form.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/admin/sys/fas_subject_info/form.do";
 	window.location.href=url;
}
	</script>