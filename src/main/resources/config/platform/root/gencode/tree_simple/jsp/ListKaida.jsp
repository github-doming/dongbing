<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/kaida/common.jsp"%>
<title></title>
</head>
<body>




<table class="class_crud_table" border="0" cellpadding="0" cellspacing="1">
	<tr bgcolor="#FFFFFF">
		<td width="99.9%" >
		&nbsp;&nbsp;<img
			src="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/form/images/current_directory.jpg"
			align="middle">&nbsp;<span class="class_font_size_12px">当前位置：菜单管理 &gt;
			&nbsp;菜单列表</span>
			</td>
		<td align="right"
	
			background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/pannel_title_bg.gif"
			valign="middle">&nbsp;</td>
	</tr> 
	
	
	<tr height="25px;"bgcolor="#FFFFFF">
		<td width="99.9%">
		
		</td>
		<td align="right"
			background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/pannel_title_bg.gif"
			valign="middle">&nbsp;</td>
	</tr>
	
</table>




<table class="class_crud_table" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr bgcolor="#FFFFFF">
			<th background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif">名称</th>
			<th background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif">链接</th>
			<th background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif">排序</th>
			<th background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif">操作</th>
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
 * 编辑
 */
 function editRecord(id){
	 	var url= "${ay_s}{pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/form.do?action=edit&&id="+id;
	 	window.location.href=url;
}
 /**
  * 新增
  */
 function newRecord(parent_id){
 	var url= "${ay_s}{pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/form.do?action=new&&parent_id="+parent_id;
 	window.location.href=url;
 }
 /**
  * 删除
  */
 function delRecord(id){
	 	if(confirm("将会删除所有子节点,确定要删除吗？"))
		{
			var url = "${ay_s}{pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/del.do?id="+id;
		 	window.location.href= url;
		}
		else
		{
		}
 }
	</script>