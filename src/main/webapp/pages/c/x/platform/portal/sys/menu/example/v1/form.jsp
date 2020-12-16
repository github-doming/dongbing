<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">



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
	rel="stylesheet" media="screen">
	










<script type="text/javascript">
var obj = window.dialogArguments;
//alert("您传递的参数为：" + obj);


function checkLeave()
{
	 //alert("关闭中");
	
}

</script>
</head>
<body onbeforeunload="checkLeave()">
<%

//c.x.all.simple.root.sys.menu.entity.SysMenuInfo info=(c.x.all.simple.root.sys.menu.entity. SysMenuInfo)request.getAttribute("s");

%>


<form id="id_form_save"
	action="${pageContext.request.contextPath}/menu/save.do"
	method="post">

<table align="center" border="0">
	<tr>
		<th align="left" colspan="2">保存
		</td>
	</tr>
	<tr>
		<td align="right">id：</td>
		<td><input type="text" name="sys_menu_info.id" value="${requestScope.id}"></td>
	</tr>


	<tr>
		<td align="right">菜单名称：</td>
		<td><input type="text" name="sys_menu_info.name" value="${requestScope.s.name}"></td>
	</tr>
	<tr>
		<td align="right">菜单url：</td>
		<td><input type="text" name="sys_menu_info.url" value="${requestScope.s.url}"></td>
	</tr>

	<tr>
		<td align="right">菜单path：</td>
		<td><input type="text" name="sys_menu_info.path" value="${requestScope.s.path}"></td>
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





