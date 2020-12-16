<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<script type="text/javascript">
var date_start = new Date();

</script>

<title></title>
</head>
<body  style="display: none;" >
<div style="display: none;" id="id_div_msg">正在提交中...</div>
<form action="${pageContext.request.contextPath}/pages/c/x/d/admin/feng/bs/sys/fav/sys_area_info/list.do"  id="id_form_list" method="post">
<table class="table table-hover" border="0">
	<caption></caption>
	<thead>
		<tr>
			<th></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td></td>
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
				type="checkbox" name="" />全选</th>
				<th width="60px;" >操作</th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','id');"
				title="按[<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . id%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','parent');"
				title="按[<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . parent%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . parent%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','parent_ids');"
				title="按[<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . parent_ids%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . parent_ids%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','code');"
				title="按[<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . code%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . code%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','name');"
				title="按[<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . name%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . name%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','remarks');"
				title="按[<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . remarks%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . remarks%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','del_flag');"
				title="按[<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . del_flag%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . del_flag%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','path');"
				title="按[<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . path%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . path%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','tree_code');"
				title="按[<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . tree_code%>]排序" style="cursor: pointer;"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . tree_code%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.id"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . id%></option>
	<option value="hit.parent"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . parent%></option>
	<option value="hit.parent_ids"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . parent_ids%></option>
	<option value="hit.code"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . code%></option>
	<option value="hit.name"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . name%></option>
	<option value="hit.remarks"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . remarks%></option>
	<option value="hit.del_flag"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . del_flag%></option>
	<option value="hit.path"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . path%></option>
	<option value="hit.tree_code"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . tree_code%></option>
			</select> <!--隐藏列事件--></th>
		</tr>
	</thead>
	<tbody>
		<%
		java.util.ArrayList<c.x.platform.admin.d.feng.sys.fav.sys_area_info.entity.FunAreaInfo> listMap = (java.util.ArrayList<c.x.platform.admin.d.feng.sys.fav.sys_area_info.entity.FunAreaInfo>) request
				.getAttribute("list");
	for (c.x.platform.admin.d.feng.sys.fav.sys_area_info.entity.FunAreaInfo info : listMap) {
	%>
		<tr>
			<td><input value="<%=info.getUuid()%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.getUuid()%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.getUuid()%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.id">&nbsp;<%=info.getUuid()%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.parent">&nbsp;<%=info.getParent()%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.parent_ids">&nbsp;<%=info.getParent_ids()%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.code">&nbsp;<%=info.getCode()%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.name">&nbsp;<%=info.getName()%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.remarks">&nbsp;<%=info.getRemarks()%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.del_flag">&nbsp;<%=info.getDel_flag()%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.path">&nbsp;<%=info.getPath()%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.tree_code">&nbsp;<%=info.getTreeCode()%></td>
			<td style="word-wrap : break-word;  ">&nbsp;</td>
		</tr>
		<%
		}
	%>
	</tbody>
</table>
<%@ include file="/pages/c/x/imports/feng/page/all/page.jsp"%>
</form>
</body>
</html>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/page/all/page.js?version=<%=version%>">
        </script>
<!-- 加载js -->
<script type="text/javascript">
/**
 * 
 删除所有记录
 */
function delAllRecord(id){
	var url = "${pageContext.request.contextPath}/pages/c/x/d/admin/feng/bs/sys/fav/sys_area_info/del_all.do";
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
		var url = "${pageContext.request.contextPath}/pages/c/x/d/admin/feng/bs/sys/fav/sys_area_info/del.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/pages/c/x/d/admin/feng/bs/sys/fav/sys_area_info/form.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/pages/c/x/d/admin/feng/bs/sys/fav/sys_area_info/form.do";
 	window.location.href=url;
}
	</script>
	
		<script type="text/javascript">
	$("body").show();
	
		</script>
		
		
		
	<script type="text/javascript">

var date_end=new Date();
var gotime=date_end.getTime()-date_start.getTime();
alert("加载js时间="+gotime+"毫秒");
</script>