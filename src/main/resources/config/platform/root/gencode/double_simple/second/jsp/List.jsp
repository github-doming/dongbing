<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/common.jsp"%>
<title></title>
</head>
<body>
<div style="display: none;" id="id_div_msg">正在提交中...</div>
<form action="${ay_s}{pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/list.do"  id="id_form_list" method="post">

<table  align="center" border="1">
	<tr>
		<td>升降序</td>
		<td><input value="${ay_s}{requestScope.sortFieldValue}" id="sortFieldId"
			name="sortFieldName" /></td>
		<td>排序的列值</td>
		<td><input value="${requestScope.sortOrderValue}"
			id="sortOrderId" name="sortOrderName" /></td>
	</tr>
	
	<tr>
		<td>树id</td>
		<td><input value="${ay_s}{requestScope.value_first${ay_s}tree${ay_s}id}" id="id_first${ay_s}tree${ay_s}id"
			name="first${ay_s}tree${ay_s}id" /></td>
		<td>树名称</td>
		<td><input value="${ay_s}{requestScope.value_first${ay_s}tree${ay_s}name}" id="id_first${ay_s}tree${ay_s}name"
			name="name_first${ay_s}tree${ay_s}name" /></td>
	</tr>
	
	
</table>

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
<table style="width:100%;table-layout:fixed"  id="id_table" class="table  table-bordered table-hover">
	<caption></caption>
	<thead>
		<tr>
				<th width="60px;" ><input id="id_checkbox_if_all" onclick=ayTableCheckAll();
				type="checkbox" name="" />全选</th>
				<th width="60px;" >操作</th>
#foreach( $name in $second_list_column )
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','${name.columnName}');"
				title="按[<%=${second_package_name_all}.alias.${second_table_class}Alias . ${name.fieldName}%>]排序" style="cursor: pointer;"><%=${second_package_name_all}.alias.${second_table_class}Alias . ${name.fieldName}%></th>
#end			
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
#foreach( $name in $second_list_column )
	<option value="hit.${name.columnName}"><%=${second_package_name_all}.alias.${second_table_class}Alias . ${name.fieldName}%></option>
#end	
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
			<td><input value="<%=info.get("${name.columnName}")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("${second_columnNamePk}")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("${second_columnNamePk}")%>')" href="#"> 编辑 </a></td>
#foreach( $name in $second_list_column )
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.${name.columnName}">&nbsp;<%=info.get("${name.columnName}")%></td>
#end	
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
	var url = "${ay_s}{pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/del_all.do";
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
		var url = "${ay_s}{pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/del.do?id="+id;
		var first${ay_s}tree${ay_s}id=document.getElementById("id_first${ay_s}tree${ay_s}id").value;
		url=url+"&&first${ay_s}tree${ay_s}id="+first${ay_s}tree${ay_s}id;
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
	var url= "${ay_s}{pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/form.do?id="+id;
	var first${ay_s}tree${ay_s}id=document.getElementById("id_first${ay_s}tree${ay_s}id").value;
	url=url+"&&first${ay_s}tree${ay_s}id="+first${ay_s}tree${ay_s}id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${ay_s}{pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/form.do";
 	var first${ay_s}tree${ay_s}id=document.getElementById("id_first${ay_s}tree${ay_s}id").value;
 	url=url+"?first${ay_s}tree${ay_s}id="+first${ay_s}tree${ay_s}id;
 	window.location.href=url;
}
	</script>