<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
	<form id="id_form_save"
		action="${pageContext.request.contextPath}/c/a/monitor/gc.do"
		method="post">
		<div class="alert alert-info">系统监控</div>
		<!-- ${requestScope.line} -->
		<table class="table table-hover" border="0">
		<tr>
				
				<td><input onclick='save();' class="btn btn-primary"  type="button" value="内存整理"></input></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>操作系统物理内存使用率</td>
				<td>${requestScope.memoryRate}%</td>
				<td><progress max="100" value="${requestScope.memoryRate}"
						id="memoryRate"></progress></td>
			</tr>
			<tr>
				<td>JVM内存使用率</td>
				<td>${requestScope.vmRate}%</td>
				<td><progress max="100" value="${requestScope.vmRate}"
						id="vmRate"></progress></td>
			</tr>
		</table>
		${requestScope.s}
	</form>
</body>
</html>
<script type="text/javascript">
	/**
	 * 
	 保存
	 */
	function save() {
		//提交
		var obj_form = document.getElementById('id_form_save');
		obj_form.submit();
	}
</script>