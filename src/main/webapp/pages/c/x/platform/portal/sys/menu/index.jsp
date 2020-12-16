<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>





<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<%@ include file="/pages/c/x/platform/root/common/common.jsp"%>


<title></title>



	<style type="text/css">

        html,body,table{background-color:#f5f5f5;width:100%;}
      
      
    .form-signin-heading{font-size:36px;margin-bottom:20px;color:#0663a2;}
    
    
      
    
    
    
     </style>

</head>
<body>

<form class="form-inline" id="id_form_query" action="${pageContext.request.contextPath}/menu/list.do"
			target="name_iframe_list" method="post">
			
			<div  align="center" valign="middle">
			
				菜单名称
	<input  type="text" name="menu_name" value="">
	<input class="btn" type="submit" value="搜索">
			
			</div>


		</form>
		
		
		


		<!-- 

height="350px"

height="330px"

 -->


 
		<IFRAME name="name_iframe_list" width="100%" height="330px" FRAMEborder="0" SCROLLING="auto"></IFRAME>


</body>
</html>

<script type="text/javascript">

document.getElementById("id_form_query").submit();

</script>


