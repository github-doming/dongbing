<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>
<form id="id_form_tree" method="post">选择上一级菜单 <input
	onclick="window.close();" class="btn" type="button" value="关闭"></input>
<table style="word-wrap : break-word;  table-layout:fixed" class="table  table-bordered table-hover" border="1">

	<thead>
		<tr>
			<th width="35%">名称</th>
		
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
<!-- 加载js -->
<script type="text/javascript">
/**
 * 树
 */
ayTreeTable_change_my= function(db_id,model,cp,id_tr,id_tr_img,id_tr_img_custom,img_custom_open,img_custom_close) {
	  ayTreeTable_change(model,cp,id_tr,id_tr_img,id_tr_img_custom,img_custom_open,img_custom_close);
  }
/**
 * 树
 */
ayTreeTable_file_click_my= function(db_id,id_tr,id_name,url) {
  }
/**
 * 树
 */
ayTreeTable_folder_click_my= function(db_id,id_tr,id_name,url) {
	    }
	</script>
<script type="text/javascript">
/**
 * 选择上一级
 */
 function selectRecord(parent_id){
var url= "${pageContext.request.contextPath}/c/x/platform/sys/sys_menu/cx/select.do?parent_id="+parent_id;
var obj_form=document.getElementById("id_form_tree");
obj_form.action=url;
obj_form.submit();
}
	</script>