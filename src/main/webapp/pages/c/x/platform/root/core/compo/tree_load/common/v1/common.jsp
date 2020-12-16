<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>

<!--   taglib-->

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- meta -->

<meta http-equiv="Content-Language" content="zh-cn"></meta>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<!-- 加载css -->







<!-- Bootstrap -->


<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/cxy_simple/cxy_simple_admin/includes/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">

<!-- Bootstrap -->





<!-- 加载css-->
<!--
<link
	href="${pageContext.request.contextPath}/cxy_simple/cxy_simple_admin/includes/tree_table/css/ay_tree_table.css"
	rel="stylesheet" media="screen">
	
	-->

<!-- 加载jquery的js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/cxy_simple/cxy_simple_admin/includes/jquery/jquery-1.9.1.js">
        </script>

<!-- 加载bootstrap的js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/cxy_simple/cxy_simple_admin/includes/bootstrap/js/bootstrap.js">
        </script>





<script type="text/javascript"
	src="${pageContext.request.contextPath}/cxy_simple/cxy_simple_admin/includes/js/ay_string.js?+Math.random()">
        </script>


<script type="text/javascript"
	src="${pageContext.request.contextPath}/cxy_simple/cxy_simple_admin/includes/js/ay_table.js?+Math.random()">
        </script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/cxy_simple/cxy_simple_admin/includes/js/ay_check.js?+Math.random()">
        </script>


<script type="text/javascript"
	src="${pageContext.request.contextPath}/cxy_simple/cxy_simple_admin/includes/tree_table/js/ay_tree_table.js?+Math.random()">
        </script>




<!-- 加载js -->







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










