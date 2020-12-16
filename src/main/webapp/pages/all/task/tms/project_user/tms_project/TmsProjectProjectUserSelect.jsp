<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Language" content="zh-cn"></meta>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<!-- 加载css -->
<title></title>
</head>
<body>
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">
/**
 * 初始化
 */
window.returnValue=true;
var obj_menu_name=opener.document.getElementById("id_input_parent_menu_name");
obj_menu_name.value="${requestScope.s.name}";
var obj_menu_id=opener.document.getElementById("id_input_parent_menu_id");
obj_menu_id.value="${requestScope.s.tmsProjectId}";
window.close();
</script>
