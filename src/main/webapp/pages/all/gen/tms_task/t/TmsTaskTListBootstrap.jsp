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
	 	var url= "${pageContext.request.contextPath}/all/gen/tms_task/t/form/bootstrap.do?action=edit&&id="+id;
	 	window.location.href=url;
}
 /**
  * 新增
  */
 function newRecord(parent_id){
 	var url= "${pageContext.request.contextPath}/all/gen/tms_task/t/form/bootstrap.do?action=new&&parent_id="+parent_id;
 	window.location.href=url;
 }
 /**
  * 删除
  */
 function delRecord(id){
	 	if(confirm("将会删除所有子节点,确定要删除吗？"))
		{
			var url = "${pageContext.request.contextPath}/all/gen/tms_task/t/del/bootstrap.do?id="+id;
		 	window.location.href= url;
		}
		else
		{
		}
 }
</script>


<script type="text/javascript">
	/**
	 * 
	 状态;
	 全展开;全关闭;
	 */
	function tree(state) {
		var url = "${pageContext.request.contextPath}/all/gen/tms_task/t/list/bootstrap.do";
		var formObj = document.getElementById("treeFormId");
		formObj.action = url + "?state=" + state;
		//formObj.action = url ;
		formObj.submit();
	}
</script>
<title></title>
</head>
<body>

<form id="treeFormId" method="post">
	<table>
		<tr>
			<td><span> <input value="展" type="button" class="btn"
					onclick="tree('open');"></input> <input value="闭" type="button"
					class="btn" onclick="tree('close');" type="button"></input> <input
					value="" name="search" style="width: 32px;" type="text"></input> <input
					value="搜" type="button" class="btn" onclick="tree('open');" type="button"></input>
			</span></td>
		</tr>
	</table>
</form>

<table style="word-wrap : break-word;  table-layout:fixed" class="table  table-bordered table-hover" border="1">

	<thead>
		<tr>
			<th width="35%">名称</th>
			<th  width="20%">链接</th>
			<th width="5%">排序</th>
			<th>操作</th>
		</tr>
	</thead>
	<tbody>
		${requestScope.c_tree}
	</tbody>
</table>
</body>
</html>

