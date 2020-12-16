

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	

	
<%@ page isELIgnored="false"%>
<%@ page language="java" isErrorPage="true" %>

<html xmlns="http://www.w3.org/1999/xhtml">   
<head>
<meta http-equiv="Content-Language" content="zh-cn"></meta>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<!-- 加载css -->

<!-- 加载js -->


<title> title</title>
</head>
<body>

http://localhost:8080/a/platform/root/core/error/runtimeexception.jsp

<br>

</br>

异常:



 <%
		 
		 
		
				if (null == exception) {
					out.write("Source of error is unknown.");
					out.write("<br></br>");
					out.write("找不到页面");
				} else {
					java.io.StringWriter sw = new java.io.StringWriter();
					java.io.PrintWriter pw = new java.io.PrintWriter(sw);

					exception.printStackTrace(pw);
					out.write(sw.getBuffer().toString());

				}
			
		 
		 %>
		 





</body>
</html>