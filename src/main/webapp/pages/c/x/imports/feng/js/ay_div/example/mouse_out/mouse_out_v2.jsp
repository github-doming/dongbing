
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	

onmouseleave
不支持firefox或chrome

支持ie


http://localhost:9080/a/pages/c/x/imports/feng/js/ay_div/example/mouse_out/mouse_out_v2.jsp

<p></p>





<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery/jquery-1.9.1.js">
        </script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_div.js?+Math.random()">
        </script>



<html>
<head>

</head>

<body>
<p>不论鼠标指针离开被选元素还是任何子元素，都会触发 mouseout 事件。</p>
<p>只有在鼠标指针离开被选元素时，才会触发 mouseleave 事件。</p>


<div onmouseout="mouseOut();" class="out"
	style="width: 200px; height: 200px; background: #f5f5f5; border-style: groove;">



<div style="width: 50px; height: 50px; border-style: groove;">被触发的
Mouseout 事件：<span></span></div>


</div>




<div onmouseleave="mouseLeave();" class="leave"
	style="width: 200px; height: 200px; background: #f5f5f5; border-style: groove;">
<div style="width: 50px; height: 50px; border-style: groove;">被触发的
Mouseleave 事件：<span></span></div>
</div>
</body>

</html>



<script type="text/javascript">
x=0;
y=0;
function mouseLeave()
{
	 $(".leave span").text(y+=1);
}
function mouseOut()
{

$(".out span").text(x+=1);
}
</script>
