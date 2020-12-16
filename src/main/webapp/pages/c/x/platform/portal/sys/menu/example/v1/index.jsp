<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">




<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>





<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/pages/c/x/platform/root/common/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">


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


