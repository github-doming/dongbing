

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Language" content="zh-cn">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>


<%@ include file="/pages/c/x/imports/feng/alert/alert.jsp"%>

     <script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/alert/ay_alert.js?v=1">
        </script>
        
        
</head>
<body>


http://localhost:8080/a/pages/c/x/imports/feng/alert/example_alert.jsp


加上拖动
<button onclick="post();">新建面板</button>



<p></p>


</body>
</html>


<script type="text/javascript">
function post(){
	
	ay$alert$alert('block_id', function(returnType){
		if(returnType){
			alert('回调1');
		}else{
			alert('回调2');
		}
		
	});
}






</script>