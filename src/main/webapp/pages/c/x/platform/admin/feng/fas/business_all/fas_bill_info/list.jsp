<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>

<%@page import="c.a.util.core.jdbc.bean.data_row.JdbcRowDto"%>
<%@page import="c.a.util.core.jdbc.bean.data_row.JdbcDataDto"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>

	
<table class="class_crud_table" border="0" cellpadding="0"
	cellspacing="1">
	<tr bgcolor="#FFFFFF">
		<td width="99.9%">&nbsp;&nbsp;&nbsp;<span
			class="class_font_size_12px">当前位置：菜单管理 &gt; &nbsp;${requestScope.menu_db_name}</span></td>
		<td align="right" valign="middle">&nbsp;</td>
	</tr>
	<tr bgcolor="#FFFFFF">
		<td width="99.9%"><input class="btn" id="id_input_new"
			type="button" value="新增" onclick="newRecord()"> <input
			class="btn" id="id_input_new" type="button" value="删除"
			onclick="delAllRecord()"></td>
		<td align="right" valign="middle">&nbsp;</td>
	</tr>

	<tr height="25px;" bgcolor="#FFFFFF">
		<td width="99.9%"></td>
		<td align="right" valign="middle">&nbsp;</td>
	</tr>

</table>
<table style="width:100%;table-layout:fixed"  id="id_table" class="table  table-bordered table-hover">
	<%
		JdbcDataDto cJdbcData = (JdbcDataDto) request.getAttribute("data");

		//列名
	%>
	<tr>

		<td>操作</td>


		<%
			for (String str : cJdbcData.getFieldList()) {
		%>

		<td><%=str%></td>

		<%
			}
		%>
	</tr>


	<%
		//数据
			for (JdbcRowDto row : cJdbcData.getRowList()) {
	%>
	<tr>

	<td><a onclick="delRecord('<%=row.getMap().get("id")%>')" href="#"> 删除 </a> <a
			class="class_a" onclick="updateRecord('<%=row.getMap().get("id")%>')"
			href="#"> 编辑 </a></td>



		<%
			for (String str : row.getList()) {
		%>

		<td><%=str%></td>

		<%
			}
		%>
	</tr>


	<%
		}
	%>



</table>

</body>
</html>


<script type="text/javascript">

/**
 * 
 删除记录
 */
function delRecord(id){
	if(confirm("确定要删除吗？"))
	{
		var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/fas/business_all/fas_bill_info/del.do";
		url=url+"?id="+id;
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

	//alert(id);
	var url= "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/fas/business_all/fas_bill_info/form.do";
url=url+"?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
	var url= "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/fas/business_all/fas_bill_info/form.do";

 	window.location.href=url;
}
</script>