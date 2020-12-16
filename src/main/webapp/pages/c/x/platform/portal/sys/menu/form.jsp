<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	     <%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%> 
<title></title>
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
<table class="table" align="center" border="0">
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
		<td align="center" colspan="2"><input class="btn" type="submit" value="提交"></td>
	</tr>
</table>
</td>
</tr>
</table>
</form>
</body>
</html>
