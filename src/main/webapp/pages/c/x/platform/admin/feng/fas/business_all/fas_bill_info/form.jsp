<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>

<!--   标签taglib-->
<!--   {-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 自定义标签 -->

<%@page import="c.x.c.feng.compo.jdbc_row.nut.bean.JdbcRow"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>

<form id="id_form_save"
	action="${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/fas/business_all/fas_bill_info/save.do"
	method="post">

<table  style="width:100%;table-layout:fixed"  id="id_table" class="table  table-bordered table-hover" >
	<tr bgcolor="#FFFFFF">
		<td height="25" valign="top">&nbsp;&nbsp;&nbsp;<span>当前位置：菜单管理 &gt;
			
			菜单 <c:choose>
			<c:when test="${requestScope.id==null}">添加</c:when>
			<c:otherwise>修改</c:otherwise>
		</c:choose>
		
		</span></td>
	</tr>
	
	<tr bgcolor="#FFFFFF">
		<td height="25" valign="top">&nbsp;&nbsp;&nbsp;<span class="font_5">
			
			
			<input onclick="back();" class="btn"
			type="button" value="返回列表"></input> <c:choose>
			<c:when test="${requestScope.id==null}">
				<input onclick='save();' class="btn" type="button" value="新增">
			</c:when>
			<c:otherwise>
				<input onclick='save();' class="btn" type="button" value="编辑">
			</c:otherwise>
		</c:choose>
			
		</span></td>
	</tr>

	<tr>
		<td>id</td>
		<td><input id="id_input$sys_type_info$id" type="text" name="sys_type_info.id"
			value="${requestScope.row.map.id}"></td>
	</tr>
	
	<tr>
		<td>name</td>
		<td><input id="id_input$sys_type_info$name" type="text" name="sys_type_info.name"
			value="${requestScope.row.map.business_name}"></td>
	</tr>
	
	<tr>
		<td></td>
		<td></td>
	</tr>
</table>
</form>
</body>
</html>

<!-- 加载js -->
<script type="text/javascript">
/**
 * 
 返回
 */
function back(){
var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/fas/business_all/fas_bill_info/list.do";
window.location.href=url;
}

/**
 * 
 保存
 */
function save(){

	   //提交
	var obj_form= document.getElementById('id_form_save');
obj_form.submit();
 }

</script>