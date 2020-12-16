<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<!-- 加载js -->
<script type="text/javascript">
/**
 * 树
 */
ayTreeTable_change_my= function(db_id,model,cp,id_tr,id_tr_img,id_tr_img_custom,img_custom_open,img_custom_close) {
	//alert('db_id='+db_id);
	//alert('id_tr='+id_tr);
	//alert('id_name='+id_name);
	//alert('url='+url);
	  ayTreeTable_change(model,cp,id_tr,id_tr_img,id_tr_img_custom,img_custom_open,img_custom_close);
  }
/**
 * 树
 */
ayTreeTable_file_click_my= function(db_id,id,url) {
  }
/**
 * 树
 */
ayTreeTable_folder_click_my= function(db_id,id,url) {
	    }
	</script>
<script type="text/javascript">
/**
 * 角色授权_保存
 */
 function save(){
	 var obj_form = document.getElementById('id_form');
	 obj_form.submit();
}
	</script>
<title></title>
</head>
<body>
<form action="${pageContext.request.contextPath}/all/admin/app/app_group/t/authorize_save.do" id="id_form" method="post">
<p></p>
<b>角色授权

</b>
<p></p>

 
<table  style="display: none;" border="1">
<tr>
<td>
<span>角色id<input name="name_roleId" value="${requestScope.group_id}"></input></span>
</td>
</tr>
</table>
<span>
<input onclick="back();" class="btn"
			type="button" value="返回列表"></input>
<input class=btn onclick="save();" type=button value="保存"></input></span>
<table style="word-wrap : break-word;  table-layout:fixed" class="table  table-bordered table-hover" border="1">
	<thead>
		<tr>
				<th width="35%">名称</th>
			<th width="20%">链接</th>
					<th width="5%">排序</th>
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
<script type="text/javascript">
/**
 * 
 返回
 */
function back(){
var url = "${pageContext.request.contextPath}/all/admin/app/app_group/t/list/bootstrap.do";
window.location.href=url;
}
</script>
<script type="text/javascript">
/**
 * 初始化
 */
ayTreeTable_checkbox_edit('${requestScope.list_path}','my');
	</script>