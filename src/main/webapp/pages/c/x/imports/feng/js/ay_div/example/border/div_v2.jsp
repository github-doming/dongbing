<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">

<html>
<head>
<title>div定位</title>
</head>
<body>
<!-- 

  <p style=border-style:groove>槽线式边框</p>
 -->



<div onclick=show(this); id="id_div_a"
	style="width: 100px; height: 100px; border-style: groove;">槽线式边框1</div>


<div onclick=show(this); id="id_div_b"
	style=" width: 200px; height: 200px; border-style: groove;">槽线式边框2</div>


</body>
</html>
<script type="text/javascript">

function show(obj_div){
//1
//offsetWidth = clientWidth + 滚动条 + 边框
// offsetHeight = clientHeight + 滚动条 + 边框

//2
//chrome认为clientWidth才是透过浏览器看内容的这个区域宽度
//ie认为offsetWidth才是透过浏览器看内容的这个区域宽度

//10
if(false){
	
	alert('obj_div.offsetLeft='+obj_div.offsetLeft);
	alert('obj_div.offsetTop='+obj_div.offsetTop);
}
//11
if(false){
	alert('obj_div.offsetWidth='+obj_div.offsetWidth);

	alert('obj_div.offsetHeight='+obj_div.offsetHeight);
}
	//12
	if(false){
	alert('obj_div.clientWidth='+obj_div.	clientWidth);
	alert('obj_div.clientHeight='+obj_div.	clientHeight);
	}
	//13
if(false){
	alert('obj_div.scrollWidth='+obj_div.	scrollWidth);
	alert('obj_div.scrollHeight='+obj_div.	scrollHeight);
}
	//15取得坐标

	alert('obj_div.getBoundingClientRect().left='+obj_div.getBoundingClientRect().left);
	alert('obj_div.getBoundingClientRect().top='+obj_div.getBoundingClientRect().top);


	//16设置坐标,其它div设置为隐藏才有效
		var obj_div_a = document.getElementById("id_div_a");
		obj_div_a.style.display = "none";

		//相对定位
obj_div.style.marginLeft = '100px';     //x坐标 
obj_div.style.marginTop = '100px'; //y坐标 



	
	
}

</script>