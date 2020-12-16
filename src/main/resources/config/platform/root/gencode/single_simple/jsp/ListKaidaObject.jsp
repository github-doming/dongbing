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
<form action="${ay_s}{pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/list_object/kaida.do"  id="id_form_list" method="post">
<table class="class_crud_table" border="0" cellpadding="0" cellspacing="1">
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
		<tr bgcolor="#FFFFFF">
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
	<caption></caption>
	<thead>
		<tr>
				<th width="60px;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><input id="id_checkbox_if_all" onclick=ayTableCheckAll();
				type="checkbox" name="" />全选</th>
				<th width="60px;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif">操作</th>
#foreach( $name in $ay_list_column )
		<!-- style="display: none; cursor: pointer;" -->
<th background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif" 
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','${name.columnName}');"
				title="按[<%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%>]排序" style="cursor: pointer;"><%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%></th>
#end			
	<th class="class_color" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
#foreach( $name in $ay_list_column )
	<option value="hit.${name.columnName}"><%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%></option>
#end	
			</select> <!--隐藏列事件--></th>
		</tr>
	</thead>
	<tbody>
		<%
		java.util.ArrayList<${ay_package_name}.entity.${ay_table_class}> listMap = (java.util.ArrayList<${ay_package_name}.entity.${ay_table_class}>) request
				.getAttribute("list");
	for (${ay_package_name}.entity.${ay_table_class} info : listMap) {
	%>
		<tr>
			<td class="class_crud_td_white" ><input value="<%=info.get${methodNamePk}()%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td class="class_crud_td_white" ><a onclick="delRecord('<%=info.get${methodNamePk}()%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get${methodNamePk}()%>')" href="#"> 编辑 </a></td>
#foreach( $name in $ay_list_column )
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" style="word-wrap : break-word;  " id="hit.${name.columnName}">&nbsp;<%=info.get${name.methodName}()%></td>
#end	
			<td class="class_crud_td_white"  style="word-wrap : break-word;  ">&nbsp;</td>
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
	var url = "${ay_s}{pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/del_all.do";
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
		var url = "${ay_s}{pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/del.do?id="+id;
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
	var url= "${ay_s}{pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/form/Kaida.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${ay_s}{pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/form/Kaida.do";
 	window.location.href=url;
}
	</script>