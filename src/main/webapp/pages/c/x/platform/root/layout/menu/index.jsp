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
	<form id="treeFormId" method="post">
		<table>
			<tr>
				<td><span> <input value="展" type="button" class="btn"
						onclick="tree('open');"></input> <input value="闭" type="button"
						class="btn" onclick="tree('close');" type="button"></input> <input
						value="" name="search" style="width: 32px;" type="text"></input> <input
						value="搜" type="button" class="btn" onclick="tree();"
						type="button"></input>
				</span></td>
			</tr>
		</table>
		<table class="table  table-bordered table-hover" border="1">
			${requestScope.c_tree}
		</table>
</body>
</form>
</html>
<script type="text/javascript">
	/**
	 *  节点隐藏
	 */
	ayTreeTable_change_my = function(db_id, model, cp, id_tr, id_tr_img, id_tr_img_custom, img_custom_open, img_custom_close) {

		ayTreeTable_change(model, cp, id_tr, id_tr_img, id_tr_img_custom, img_custom_open, img_custom_close);
	}
	/**
	 * 
	 文件单击
	 */
	ayTreeTable_file_click_my = function(db_id, id_tr, id_name, url) {
		
		
		var homeIframeObj = top.document.getElementById("homeIframeId");
		var obj_name = document.getElementById(id_name);
		$("#" + id_name).attr("class", "label label-info");
		$("#" + id_name_temp).attr("class", "");
		id_name_temp = id_name;
		if (url == null || url == '') {
		} else {
			if (url.indexOf('http') == 0) {
				// 直接打开
				homeIframeObj.src = url;
			} else {
				url = '${pageContext.request.contextPath}' + url;
				url = url + "?menu_db_id=" + db_id;

				homeIframeObj.src = url;
			}
		}
	}
	/**
	 * 
	 文件夹单击
	 */
	ayTreeTable_folder_click_my = function(db_id, id_tr, id_name, url) {
		if (url == null || url == '') {
		} else {
			var homeIframeObj = top.document.getElementById("homeIframeId");
			homeIframeObj.src = '${pageContext.request.contextPath}' + url;
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
		var url = '${pageContext.request.contextPath}/platform/admin/sys/tree_table.do?parent_id=${requestScope.parent_id}';
		var formObj = document.getElementById("treeFormId");
		formObj.action = url + "&&state=" + state;
		formObj.submit();
	}
</script>
<script type="text/javascript">
	/**
	 *初始化 
	 */
	/*
	设置2级菜单的第1个节点
	 */
	//节点名称的id(临时)
	var id_name_temp = "id_name_${requestScope.map_child.SYS_MENU_ID_}_my";
	//设置2级菜单的第1个节点的css
	$("#" + id_name_temp).attr("class", "label label-info");
	//打开页面(2级菜单的第1个节点)
	//打开home
	var homeIframeObj = top.document.getElementById("homeIframeId");
	var url = '${pageContext.request.contextPath}/admin/sys/menu/list.do';
	var urlOpen = '${requestScope.map_child.URL_}';
	if (urlOpen == '') {
		url = "${pageContext.request.contextPath}/pages/c/x/platform/root/layout/home/index.jsp";
		homeIframeObj.src = url;
	} else {
		if (urlOpen.indexOf('http') == 0) {
			// 直接打开
			urlOpen = urlOpen + "?sso=${requestScope.sso}";
			homeIframeObj.src = urlOpen;
		} else {
			url = '${pageContext.request.contextPath}' + urlOpen;
			var menuAyId = '${requestScope.map_child.id}';
			url = url + "?menu_db_id=" + menuAyId;
			homeIframeObj.src = url;
		}
	}
	/*
	设置2级菜单的第1个节点
	 */
</script>
<script type="text/javascript">

</script>
