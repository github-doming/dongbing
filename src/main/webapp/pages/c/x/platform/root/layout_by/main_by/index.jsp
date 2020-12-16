<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>

<%@page
	import="c.x.c.feng.tools.config.portals.Config"%>


<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<%@ include
	file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>

<style type="text/css">
html,body,table {
	background-color: #f5f5f5;
	width: 100%;
}

.form-signin-heading {
	font-size: 36px;
	margin-bottom: 20px;
	color: #0663a2;
}
</style>

<title></title>


</head>
<body>


<table style="width: 100%;" border="0">
<%
					java.util.ArrayList<java.util.HashMap<String, Object>> listMap=(java.util.ArrayList<java.util.HashMap<String, Object>>)	request.getAttribute("list_menus_first");
					if(listMap!=null){
 
						for(java.util.HashMap<String, Object> map:listMap){
						
						Long id=(Long)map.get("id");
						String id_li="id_li_"+id;
						String name=(String)map.get("name");
						
						%> <input id='<%=id_li%>'
			onclick="list_menus_first('<%=id%>','<%=id_li%>');"
			class="btn  btn-link" type="button" value="<%=name%>"></input> <% 
						}			
					
					}
					%>
</table>


<IFRAME
			style="width: 100%;" id="id_iframe_second" name="name_iframe_second"
			src="" FRAMEborder="0" SCROLLING="auto"></IFRAME> 
			
			

<IFRAME
			style="width: 100%;" id="id_iframe_third" name="name_iframe_third"
			src="" FRAMEborder="0" SCROLLING="auto"></IFRAME> 
			
<IFRAME
			style="width: 100%;" id="id_iframe_home" name="name_iframe_home"
			src="" FRAMEborder="0" SCROLLING="auto"></IFRAME> 

</body>
</html>



<script type="text/javascript">
function iframe_resize(id){  
var w=window.screen.width; //弹出当前页面的宽度  
var h=window.screen.height; //弹出当前页面的高度  
var obj = document.getElementById(id);  //取得父页面IFrame对象  
var ua = navigator.userAgent.toLowerCase();
obj.height =30;
} 
function iframe_resize_2(id){  
	var w=window.screen.width; //弹出当前页面的宽度  
	var h=window.screen.height; //弹出当前页面的高度  
	var obj = document.getElementById(id);  //取得父页面IFrame对象  
	var ua = navigator.userAgent.toLowerCase();
	//obj.height =500;

	if(ua.indexOf("firefox")>0){
		if(w==800&&h==600){
			obj.height =206;
		}else{
			if(w==1024&&h==768){

				obj.height =370;
				
			}else{
				obj.height = w/4+h/23;  //调整IFrame的高度为此页面的高度  
			}
		}
	}
	if(ua.indexOf("chrome")>0){
		if(w==800&&h==600){
			obj.height =330;
		}else{
			if(w==1024&&h==768){
				obj.height =460;
			}else{
				 obj.height = w/3+h/12;  //调整IFrame的高度为此页面的高度  	
			}
		}
			
	}

	if(ua.indexOf("msie")>0){
		if(w==800&&h==600){
			obj.height =250;
		}else{
			if(w==1024&&h==768){
				obj.height =415;
			}else{
				 obj.height = w/4+h/8;  //调整IFrame的高度为此页面的高度  	
			}
		}
		
	}


	
	
} 


iframe_resize("id_iframe_second");
iframe_resize("id_iframe_third");
iframe_resize_2("id_iframe_home");

</script>


<script type="text/javascript">




/**
 * 
 加载所有的 2级菜单
 */
function list_menus_first(id,id_li){

//设置css
//alert(id_name);
	$("#" + id_li).attr("class", "btn  btn-info");

	$("#" + id_li_temp).attr("class", "btn  btn-link");
	id_li_temp=id_li;	

//加载第二级菜单

	 var obj_iframe_tree  = top.document.getElementById("id_iframe_second");
var url='${pageContext.request.contextPath}/admin/menu_by.do?parent_id='+id;

//alert('url'+url);
	 obj_iframe_tree.src = url;


	
}


/**
 *初始化 
 
 */


//设置第一级菜单css
var id_li_temp="id_li_${requestScope.menu_id_00010001}";
$("#" + id_li_temp).attr("class", "btn  btn-info");
//$("#" + id_temp).html("wfwef");


//加载第二级菜单
var obj_iframe_second  = top.document.getElementById("id_iframe_second");
var url='${pageContext.request.contextPath}/admin/menu_by.do?parent_id='+${requestScope.menu_id_00010001};
//alert('url'+url);
obj_iframe_second.src = url;

</script>
