<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>
<table class="table  table-bordered table-hover" border="1">
	<thead>
		<tr>
			<th>名称</th>
			<th>链接</th>
				<th>排序</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		${requestScope.c_tree}
	</tbody>
</table>
</body>
</html>

<!-- 加载js -->
<script type="text/javascript">
ayTreeTable_change_my= function(db_id,model,cp,id_tr,id_tr_img,id_tr_img_custom,img_custom_open,img_custom_close) {
	  ayTreeTable_change(model,cp,id_tr,id_tr_img,id_tr_img_custom,img_custom_open,img_custom_close);
  }
ayTreeTable_file_click_my= function(db_id,id,url) {
  }
ayTreeTable_folder_click_my= function(db_id,id,url) {
	    }
	</script>
<script type="text/javascript">
 function editRecord(id){
	 	var url= "${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_tree_simple/fun_single_simple/form.do?action=edit&&id="+id;
	 	//alert(url);
	 	window.location.href=url;
}
 function newRecord(parent_id){
 	var url= "${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_tree_simple/fun_single_simple/form.do?action=new&&parent_id="+parent_id;
 	//alert(url);
 	window.location.href=url;
 }
 function delRecord(id){
	 	if(confirm("将会删除所有子节点,确定要删除吗？"))
		{
			var url = "${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_tree_simple/fun_single_simple/del.do?id="+id;
		 	window.location.href= url;
		}
		else
		{
		}
 }
	</script>