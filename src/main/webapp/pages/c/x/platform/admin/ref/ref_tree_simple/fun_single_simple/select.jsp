<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">   
<head>
<meta http-equiv="Content-Language" content="zh-cn"></meta>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<!-- 加载css -->
<!-- 加载js -->
<script type="text/javascript">
window.returnValue=true;
var obj_menu_name=opener.document.getElementById("id_input_parent_menu_name");
obj_menu_name.value="${requestScope.s.name}";
//alert('obj_menu_name.value='+obj_menu_name.value);
var obj_menu_id=opener.document.getElementById("id_input_parent_menu_id");
obj_menu_id.value="${requestScope.s.sysId}";
window.close();
</script>
<title></title>
</head>
<body>
</body>
</html>
