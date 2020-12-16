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
					java.util.ArrayList<java.util.HashMap<String, Object>> listMap_child=(java.util.ArrayList<java.util.HashMap<String, Object>>)	request.getAttribute("list_menus_child");
					if(listMap_child!=null){
 
						for(java.util.HashMap<String, Object> map:listMap_child){
						
						Long id=(Long)map.get("id");
						String id_li="id_li_"+id;
						String name=(String)map.get("name");
						String url=(String)map.get("url");
						
						%> <input id='<%=id_li%>'
			onclick="list_menus_first('<%=id%>','<%=id_li%>','<%=url%>');"
			class="btn  btn-link" type="button" value="<%=name%>"></input> <% 
						}			
					
					}
					%>
</table>



	

</body>
</html>




<script type="text/javascript">

</script>



<script type="text/javascript">

/**
 * 
 加载所有的 2级菜单
 */
function list_menus_first(id,id_li,url_open){

//设置css
//alert(id_name);
	$("#" + id_li).attr("class", "btn  btn-success");

	$("#" + id_li_temp).attr("class", "btn  btn-link");
	id_li_temp=id_li;	

//加载第3级菜单

	 var obj_iframe_third  = top.document.getElementById("id_iframe_third");
//alert(' obj_iframe_third ='+ obj_iframe_third );
var url='${pageContext.request.contextPath}/admin/menu_third.do?parent_id='+id;

//alert('url='+url);
	 obj_iframe_third.src = url;



/**
 *加载页面到home
 
 */
//alert('url_open='+url_open);
//打开home
var obj_iframe_home = top.document.getElementById("id_iframe_home");


if(url_open==null||url_open==''){
//alert('null');
var url_home="${pageContext.request.contextPath}/pages/c/x/c/admin/feng/layout/home/index.jsp";
obj_iframe_home.src = url_home;
}else{
	if(url_open.indexOf('http')==0){
	// 直接打开
	//alert('http url 1='+url_open);
	 obj_iframe_home.src=url_open;
	}else{
	 var url_open_2='${pageContext.request.contextPath}'+url_open;
	//alert('url_open_2='+url_open_2);
	 obj_iframe_home.src = url_open_2;
	}
}


}


/**
 *初始化 
 
 */


//设置第2级菜单css
var id_li_temp="id_li_${requestScope.menu_id_00010001}";
$("#" + id_li_temp).attr("class", "btn  btn-success");
//$("#" + id_temp).html("wfwef");


//加载第3级菜单
var obj_iframe_third = top.document.getElementById("id_iframe_third");
//alert(' obj_iframe_third ='+ obj_iframe_third );
var url='${pageContext.request.contextPath}/admin/menu_third.do?parent_id='+${requestScope.menu_id_00010001};
//alert('url='+url);
obj_iframe_third.src = url;

</script>
