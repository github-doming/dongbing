<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@page import="c.a.config.SysConfig"%>
<!--导入标签-->
<!--记得import进标签 --><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>重新跳转</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<style type="text/css">
.form-signin-heading {
	font-size: 36px;
	margin-bottom: 20px;
	color: #0663a2;
}
</style>
</head>
<body>
	<form id="id_form_1" method="post">
		<table class="table table-hover">
		</table>
	</form>
</body>
</html>
<script type="text/javascript">
var c_post = function(){
	var formObj = document.getElementById('id_form_1');
	formObj.action='${pageContext.request.contextPath}<%=SysConfig.TargetDoLogin%>';
	formObj.submit();
	return true;
};
</script>
<script type="text/javascript">
var c_cp="${pageContext.request.contextPath}";
var login_url=c_cp +'<%=SysConfig.RequestDoLogin%>';
top.location.href = login_url;
</script>
