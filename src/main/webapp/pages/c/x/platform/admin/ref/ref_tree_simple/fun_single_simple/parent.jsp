

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Language" content="zh-cn"></meta>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"></meta>
<!-- 加载css -->

<!-- 加载js -->






<!-- 加载Bootstrap -->
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">


<!-- 加载js -->



<!-- 加载jquery-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery/jquery-1.9.1.js">
        </script>
<!-- 加载cf.js-->


<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_table.js?+Math.random()">
        </script>
        
        
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/compo/tree_table/js/ay_tree_table.js?+Math.random()">
        </script>


<!-- 加载js -->



        
        
        
 <script type="text/javascript">


ayTreeTable_change_my= function(model,cp,id_tr,id_tr_img,id_tr_img_custom,img_custom_open,img_custom_close) {

	  ayTreeTable_change(model,cp,id_tr,id_tr_img,id_tr_img_custom,img_custom_open,img_custom_close);
	  

  }

ayTreeTable_file_click_my= function(id,url) {


	
	  
  }

ayTreeTable_folder_click_my= function(id,url) {

	 
	

	  	  
	    }

  
	</script>

 <script type="text/javascript">






 function selectRecord(parent_id){
	

var url= "${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_tree_simple/fun_single_simple/select.do?parent_id="+parent_id;
//alert('c='+url);

var obj_form=document.getElementById("id_form_tree");

obj_form.action=url;

obj_form.submit();

}


 

	</script>
<title></title>
</head>






















<body>
<form id="id_form_tree" method="post">


选择上一级菜单
		<input onclick="window.close();" class="btn"
			type="button" value="关闭"></input>
			
			
<table class="table  table-bordered table-hover" border="1">
	<thead>
		<tr>

		
			<th>名称</th>
	
				<th>操作</th>
		</tr>

	</thead>
<tbody>


	${requestScope.c_tree}

</tbody>








</table>
</form>
</body>

</html>