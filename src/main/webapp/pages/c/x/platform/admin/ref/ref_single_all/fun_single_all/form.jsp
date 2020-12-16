<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>



<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>




<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/pages/c/x/platform/root/common/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen"><script type="text/javascript">


</script>
</head>
<body>
<%
	//c.x.platform.sys.sys_menu.cx.entity.SysMenuCx info=(c.x.platform.sys.sys_menu.cx.entity.SysMenuCx)request.getAttribute("s");
%>


<form id="id_form_save"
	action="${pageContext.request.contextPath}/admin/gen/single_simple/save.do"
	method="post">

<table class="table  table-bordered table-hover" border="1">


	<tr>
		<th colspan="3">菜单 <c:choose>
			<c:when test="${requestScope.sysId==null}">添加</c:when>
			<c:otherwise>修改</c:otherwise>
		</c:choose>


		</td>
	</tr>


	<tr>
		<td colspan="3"><input onclick="go();" class="btn" type="button"
			value="返回列表"></input> <c:choose>
			<c:when test="${requestScope.sysId==null}">


				<input class="btn" type="submit" value="新增">
			</c:when>



			<c:otherwise>

				<input class="btn" type="submit" value="编辑">
			</c:otherwise>
		</c:choose></td>
	</tr>




	<tr>
		<td align="right">id：</td>
		<td><input type="text" name="gen_single_simple.id"
			value="${requestScope.sysId}"></td>
		<td></td>
	</tr>


	<tr>
		<td align="right">菜单名称：</td>
		<td><input type="text" name="gen_single_simple.name"
			value="${requestScope.s.name}"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right">菜单url：</td>
		<td><input type="text" name="gen_single_simple.url"
			value="${requestScope.s.url}"></td>
		<td></td>
	</tr>

	<tr>
		<td align="right">菜单path：</td>
		<td><input type="text" name="gen_single_simple.path"
			value="${requestScope.s.path}"></td>
		<td></td>
	</tr>

	<tr>
		<td align="center" colspan="2"><input type="submit" value="提交"></td>
	</tr>
</table>
</td>
</tr>
</table>
</form>




</body>
</html>



<script type="text/javascript">


function go(){


 	var url = "${pageContext.request.contextPath}/admin/gen/single_simple/list.do";

 	window.location.href=url;


 	

 }








</script>



