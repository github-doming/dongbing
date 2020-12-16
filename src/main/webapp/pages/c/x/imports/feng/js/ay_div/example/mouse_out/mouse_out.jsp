<%@ include file="/pages/root/core/common.jsp"%>

<%=c_contextPath_all %>

<p></p>
http://localhost/a/pages/c/x/imports/feng/js/ay_div/example/mouse_out/mouse_out.jsp
<p></p>

<%=c_contextPath_all %>/pages/c/x/imports/feng/js/ay_div/example/mouse_out/mouse_out.jsp

<p></p>


<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_div.js?+Math.random()">
        </script>
        
    

<html>
<head>

</head>

<body>

1输出：(请把鼠标从图片上移开):

<p></p>

<a href="#" 
onmouseover="mouseOver()" onmouseout="mouseOut()">
<img alt="Visit W3School!" src="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_div/images/mouse2.jpg" id="b1" />
</a>



<p></p>
    
2输出：(请把鼠标移到图片上):


        <p></p>
        <img src="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_div/images/mouse2.jpg" alt="mouse"
onmousemove="alert('您的鼠标刚才移到图片上！')" />




<p></p>




</body>
</html>



<script type="text/javascript">
function mouseOver()
{
document.getElementById('b1').src ="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_div/images/mouse1.jpg";
}
function mouseOut()
{
document.getElementById('b1').src ="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_div/images/mouse2.jpg";
}
</script>
