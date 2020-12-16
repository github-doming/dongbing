<!DOCTYPE HTML>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html>
<head>
<meta http-equiv="Content-Language" content="zh-cn"></meta>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<title></title>
</head>
<body>

<p contenteditable="true">这是一段可编辑的段落。请试着编辑该文本。</p>

<table  style="width:100%;"  border="1">

	<tr >
		<td>a</td>
		<td>b</td>
		<td>c</td>
	</tr>

	<tr>
		<td>a</td>
		<td style="width:80%;">
		
		<iframe style="width:100%;" id="id_iframe"  src="http://www.163.com"></iframe>
		
		
		</td>
		<td>c</td>
	</tr>
	<tr>
		<td>a</td>
		<td>b</td>
		<td>c</td>
	</tr>



</table>


</body>
</html>

    
    

<script>  
function iframe_resize(){  


var w=window.screen.width; //弹出当前页面的宽度  
var h=window.screen.height; //弹出当前页面的高度  


var obj = document.getElementById("id_iframe");  //取得父页面IFrame对象  


var ua = navigator.userAgent.toLowerCase();
if(ua.indexOf("firefox")>0){
		 obj.height = w/4+h/23;  //调整IFrame的高度为此页面的高度  
}
if(ua.indexOf("chrome")>0){
		 obj.height = w/3+h/12;  //调整IFrame的高度为此页面的高度  	
}

if(ua.indexOf("msie")>0){
	 obj.height = w/4+h/8;  //调整IFrame的高度为此页面的高度  	
}


	

  
} 
iframe_resize();
</script> 
