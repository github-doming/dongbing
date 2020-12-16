<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include
	file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>
	<div style="display: none;" id="id_div_msg">正在提交中...</div>
	<form id="id_form_list"
		action="${pageContext.request.contextPath}/menu/list.do"
		method="post">
		<table class="table table-hover" border="0">
			<caption></caption>
			<thead>
				<tr>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<div align="center" valign="middle">
							菜单名称 <input type="text" name="menu_name" v
								value="${requestScope.value_menu_name}"> <input
								class="btn btn-info" type="submit" value="搜索">
						</div>
					</td>
				</tr>
				<tr>
					<td><input class="btn" id="id_input_new" type="button"
						value="新增" onclick="newRecord()"> modules <i
						class="icon-chevron-right"></i> <i class="icon-chevron-down"></i>
						<input class="btn" id="id_input_new" type="button" value="删除"
						onclick="delAllRecord()"></td>
				</tr>
			</tbody>
		</table>
		<table style="display: none;" align="center"">
			<tr>
				<td>升降序</td>
				<td><input value="${requestScope.sortFieldValue}" id="sortFieldId"
					name="sortFieldName" /></td>
				<td>排序的列值</td>
				<td><input value="${requestScope.sortOrderValue}"
					id="sortOrderId" name="sortOrderName" /></td>
			</tr>
		</table>
		<table id="id_table" class="table  table-bordered table-hover">
			<caption></caption>
			<thead>
				<tr>
					<th><input id="id_checkbox_if_all"
						onclick=ayTableCheckAll(); type="checkbox" name="" />全选</th>
					<th>操作</th>
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','sys_menu_id_');"
						title="按[id]排序" style="cursor: pointer;">id</th>
					<th title="按[名称]排序" style="display: none; cursor: pointer;">名称</th>
					<th style="display: none; cursor: pointer;">path</th>
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','tree_code_');"
						style="cursor: pointer;">tree_code</th>
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','url');"
						style="cursor: pointer;">url</th>
					<th class="class_color">
						<!--隐藏列事件--> <select
						onchange="ayTableHiddenColumn(this,'id_table',2);">
							<option value=" &nbsp;" selected="selected">&nbsp;</option>
							<option value="hit.name">名称</option>
							<option value="hit.path">path</option>
					</select> <!--隐藏列事件-->
					</th>
				</tr>
			</thead>
			<tbody>
				<%
					java.util.ArrayList<all.gen.sys_menu.t.entity.SysMenuT> listMap = (java.util.ArrayList<all.gen.sys_menu.t.entity.SysMenuT>) request
						.getAttribute("list");
						for (all.gen.sys_menu.t.entity.SysMenuT info : listMap) {
				%>
				<tr>
					<td><input value="<%=info.getSysMenuId()%>" type="checkbox"
						name="name_checkbox_ids"></td>
					<td><button class="btn btn-link"
							onclick="delRecord('<%=info.getSysMenuId()%>')" href="#">删除</button> <a
						onclick="updateRecord('<%=info.getSysMenuId()%>')" href="#"> 编辑 </a></td>
					<td>&nbsp;<%=info.getSysMenuId()%></td>
					<td id="hit.name" style="display: none;">&nbsp;<%=info.getName()%></td>
					<td id="hit.path" style="display: none;">&nbsp;<%=info.getPath()%></td>
					<td>&nbsp;<%=info.getTreeCode()%></td>
					<td>&nbsp;<%=info.getUrl()%></td>
					<td>&nbsp;</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<%@ include file="/pages/c/x/imports/feng/page/all/page.jsp"%>
	</form>
</body>
</html>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/page/all/page.js?version=<%=version%>">
</script>
<!-- 加载js -->
<script type="text/javascript">
function delAllRecord(id){
	var url = "${pageContext.request.contextPath}/menu/del_all.do";
	var objs = document.getElementsByName("checkboxIds");
	var checkedNumber = 0;
	for (var i = 0; i < objs.length; i++) {
		if (objs[i].checked) {
			checkedNumber = checkedNumber + 1;
		}
	}
	if (checkedNumber == 0) {
	alert('请先选择要删除的行');
	}else{
		if(confirm("确定要删除吗？"))
		{
			document.getElementById("id_form_list").action=url;
			document.getElementById("id_form_list").submit();
		}
		else
		{
		}
	}
	return false;
}
function delRecord(id){
	var url = "${pageContext.request.contextPath}/menu/del.do?id="+id;
	window.location.href= url;
	return false;
}

function updateRecord(id){
	var url = "${pageContext.request.contextPath}/menu/form.do?id="+id;
	var name='newwindow';
	ayFormOpenwindow (url,name,800,400) ;
	return false;
}

function updateRecord_v1(id){
	var url = "${pageContext.request.contextPath}/menu/form.do?id="+id;
	//alert(url);
	var name="n";
	var needRefresh=window.showModalDialog(url,name, 'dialogHeight:400px;dialogWidth:450px;center:yes;help:no;status:no;scroll:yes');
	//alert("操作成功");
 /*for chrome
    */
    if (needRefresh == undefined) {
        //alert("for chrome");
    	needRefresh = window.returnValue;
    }
	//alert('needRefresh='+needRefresh);
	//alert('1');
	if(needRefresh == true){
		//alert('2');
		document.getElementById("id_form_list").submit();
	}
	if(needRefresh == "window.opener.returnValue"){
		/*alert("for chrome");
		*/
		//alert('3');
		document.getElementById("id_form_list").submit();
	}
	return false;
}
/**
 *上一级;
 *不使用模态窗口 ;
 */
function newRecord(){
	var url= "${pageContext.request.contextPath}/menu/form.do";
	var name='newwindow';
	ayFormOpenwindow (url,name,800,400) ;
	return false;
}
function newRecord_v1(){
	var url= "${pageContext.request.contextPath}/menu/form.do";
	//alert(url);
	var name="n";
	var needRefresh=window.showModalDialog(url,name, 'dialogHeight:400px;dialogWidth:450px;center:yes;help:no;status:no;scroll:yes');
	//alert("新增成功");
	 /*for chrome
    */
    if (needRefresh == undefined) {
        //alert("for chrome");
    	needRefresh = window.returnValue;
    }
	//alert('needRefresh='+needRefresh);
	//alert('1');
	if(needRefresh == true){
		//alert('2');
		document.getElementById("id_form_list").submit();
	}
	if(needRefresh == "window.opener.returnValue"){
		   //alert("for chrome");
		//alert('3');
		document.getElementById("id_form_list").submit();
	}
	return false;
}
	</script>
