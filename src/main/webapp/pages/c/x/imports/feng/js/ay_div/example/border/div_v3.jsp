<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<html>
<head>
<style type="text/css">
.class_div{
background: #f5f5f5;
 border-style: groove; 
 position: absolute; 
 z-index: 99;
}
</style>
<title>div定位</title>
</head>
<body>

http://localhost:9080/a/pages/c/x/imports/feng/js/ay_div/example/border/div_v3.jsp

<p></p>
<!-- display: none;  -->
<div id="id_div_a" style=" display: none;  left:0px;top:0px;width: 100px; height: 100px;" class="class_div">
	槽线式边框1
	槽线式边框2
	<p></p>
	</div>
	
	
div定位
不同浏览器
<p></p>


div定位
不同浏览器
<p></p>

div定位
不同浏览器
<p></p>

<!-- 
  <p style=border-style:groove>槽线式边框</p>
 -->
<input onclick=show(this); type="text" value="a"></input>
<p></p>
<input onclick=show(this); type="text" value="b"></input>

<p></p>
<select>
<option>1</option>
</select>
</body>
</html>
<script type="text/javascript">
function show(obj_div){
	var ua = navigator.userAgent.toLowerCase();
	//alert('obj_div.getBoundingClientRect().left='+obj_div.getBoundingClientRect().left);
	//alert('obj_div.getBoundingClientRect().top='+obj_div.getBoundingClientRect().top);
	//alert('obj_div.getBoundingClientRect().bottom='+obj_div.getBoundingClientRect().bottom);
	//设置坐标,其它div设置为隐藏才有效
		var obj_div_a = document.getElementById("id_div_a");
		var left=obj_div.getBoundingClientRect().left+ "px";//x坐标 
		var  top=obj_div.getBoundingClientRect().bottom+ "px"; //y坐标 
		obj_div_a.style.marginLeft =left;
		obj_div_a.style.marginTop =top;
//不隐藏
		obj_div_a .style.display = '';
}
</script>