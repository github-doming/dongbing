
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include
	file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>


<frameset cols="30%,*">

	<frame scrolling=yes frameborder=1 id="id_frame_first"
		name="name_frame_first"
		src="${pageContext.request.contextPath}/all/task/tms/project_user/tms_project/list/bootstrap.do">
	<frame scrolling=yes frameborder=1 id="id_frame_second"
		name="name_frame_second"
		src="${pageContext.request.contextPath}/all/task/tms/project_user/app_user/list/bootstrap.do">


</frameset>




</html>


