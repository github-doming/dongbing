<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html>
<body>
<a href="${pageContext.request.contextPath}/platform/admin/login.do">后台管理</a>
<br></br>
<a href="${pageContext.request.contextPath}/menu/list.do">查询menu(listObject)分页</a>
<br></br>
<button onclick="save.a()">测试javascript</button>
</body>
</html>
<script type="text/javascript">
save=function(){
	alert('save1');
}
save.a=function(){
	alert('a1');
}
</script>
