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
<form action="${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/bbs/buy/bbs_post_info/list/bootstrap.do"  id="id_form_list" method="post">

<table  align="center" border="1">
	<tr>
		<td>升降序</td>
		<td><input value="${requestScope.sortFieldValue}" id="sortFieldId"
			name="sortFieldName" /></td>
		<td>排序的列值</td>
		<td><input value="${requestScope.sortOrderValue}"
			id="sortOrderId" name="sortOrderName" /></td>
	</tr>
	
	<tr>
		<td>树id</td>
		<td><input value="${requestScope.value_first$tree$id}" id="id_first$tree$id"
			name="name_first$tree$id" /></td>
		<td></td>
		<td></td>
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
		<!-- style="display: none; cursor: pointer;" -->
<th width="30px;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','id');"
				title="按[<%=c.x.platform.feng.feng.bbs.buy.bbs_post_info.alias.BbsPostInfoAlias . id%>]排序" style="cursor: pointer;"><%=c.x.platform.feng.feng.bbs.buy.bbs_post_info.alias.BbsPostInfoAlias . id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','content');"
				title="按[<%=c.x.platform.feng.feng.bbs.buy.bbs_post_info.alias.BbsPostInfoAlias . content%>]排序" style="cursor: pointer;"><%=c.x.platform.feng.feng.bbs.buy.bbs_post_info.alias.BbsPostInfoAlias . content%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th width="60px;"
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','title');"
				title="按[<%=c.x.platform.feng.feng.bbs.buy.bbs_post_info.alias.BbsPostInfoAlias . title%>]排序" style="cursor: pointer;"><%=c.x.platform.feng.feng.bbs.buy.bbs_post_info.alias.BbsPostInfoAlias . title%></th>
	<th  width="60px;" class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.id"><%=c.x.platform.feng.feng.bbs.buy.bbs_post_info.alias.BbsPostInfoAlias . id%></option>
	<option value="hit.content"><%=c.x.platform.feng.feng.bbs.buy.bbs_post_info.alias.BbsPostInfoAlias . content%></option>
	<option value="hit.title"><%=c.x.platform.feng.feng.bbs.buy.bbs_post_info.alias.BbsPostInfoAlias . title%></option>
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
			<td>
			
			<a onclick="delRecord('<%=info.get("id")%>')" href="#"> 删除</a>
			 <a onclick="updateRecord('<%=info.get("id")%>')" href="#"> 编辑 </a>
			 <a onclick="viewRecord('<%=info.get("id")%>')" href="#"> 查看 </a>
			</td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.id">&nbsp;<%=info.get("id")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.content">&nbsp;<%=info.get("content")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.title">&nbsp;<%=info.get("title")%></td>
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
	var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/bbs/buy/bbs_post_info/del_all/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/bbs/buy/bbs_post_info/del/bootstrap.do?id="+id;
		var first$tree$id=document.getElementById("id_first$tree$id").value;
		url=url+"&&first$tree$id="+first$tree$id;
		window.location.href= url;
	}
	else
	{
	}
}

/**
 * 
 请求查看
 */
function viewRecord(id){
	var url= "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/bbs/buy/bbs_post_info/view/bootstrap.do?id="+id;
	var first$tree$id=document.getElementById("id_first$tree$id").value;
	url=url+"&&first$tree$id="+first$tree$id;
 	window.location.href=url;
}

/**
 * 
 请求更新
 */
function updateRecord(id){
	var url= "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/bbs/buy/bbs_post_info/form/bootstrap.do?id="+id;
	var first$tree$id=document.getElementById("id_first$tree$id").value;
	url=url+"&&first$tree$id="+first$tree$id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/bbs/buy/bbs_post_info/form/bootstrap.do";
 	var first$tree$id=document.getElementById("id_first$tree$id").value;
 	url=url+"?first$tree$id="+first$tree$id;
 	window.location.href=url;
}
	</script>