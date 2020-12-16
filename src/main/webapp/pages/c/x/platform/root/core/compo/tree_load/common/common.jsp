<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/pages/c/x/platform/root/core/compo/tree_load/common/version.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!--   标签taglib-->
<!--   {-->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 自定义标签 -->
<%@ taglib prefix="permission" uri="/ay_permission"%>


<!--  }-->
<!--   标签taglib-->
<!-- meta -->
<meta http-equiv="Content-Language" content="zh-cn"></meta>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<!-- 加载css -->
<!--   {-->
<!-- Bootstrap -->
<!-- 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
-->
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<!-- Bootstrap -->
<!--
<link
	href="${pageContext.request.contextPath}/pages/c/x/a/admin/feng/compo/tree_table/includes/tree_table/css/ay_tree_table.css"
	rel="stylesheet" media="screen">
	-->
<!--  }-->
<!-- 加载css-->
<!-- 加载js -->
<!--   {-->
<!-- 加载jquery的js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery/jquery-1.9.1.js">
        </script>
<!-- 加载bootstrap的js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap/js/bootstrap.js">
        </script>
<!-- 加载自定义的js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_string.js?version=<%=version %>">
        </script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_table.js?version=<%=version %>">
        </script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_check.js??version=<%=version %>">
        </script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/compo/tree_table/js/ay_tree_table.js?version=<%=version %>">
        </script>
        
        <script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/compo/tree_load/js/ay_tree_load.js?version=<%=version %>">
        </script>
        
<!--  }-->
<!-- 加载js-->
<%
	// 上下文路径
	String c_contextPath = request.getContextPath();
	// WebRoot URL 路径
	String c_contextPath_all = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath();
		if(false){
			out.write("c_contextPath ="+c_contextPath );
			out.write("<br>");
			out.write("c_contextPath_all="+c_contextPath_all);
			out.write("<br>");
			out.write("URI="+ request.getRequestURI());
			out.write("<br>");
			out.write("URL="+ request.getRequestURL());
		}
%>
<!-- 初始化 -->
<script type='text/javascript'>
//alert('10');
<%
/**
弹出信息
*
*/
java.util.List<String> list_msg = (java.util.ArrayList<String>)request.getAttribute("msg");
String msg=null;
if(list_msg !=null&&list_msg .size()>0){
	System.out.println("list_msg .size="+list_msg .size());
	msg="";
	for(String str:list_msg){
		msg=msg+str+"\\n";
	}
}
%>
<%
//System.out.println("msg="+msg);
if(msg !=null){
%>
	alert('<%=msg%>');
	<% 
}
%>
</script>
