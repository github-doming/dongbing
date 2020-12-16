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
	<div style="display: none;" id="id_div_msg">正在提交中...</div>
	<form
		action="${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/list.do"
		id="id_form_list" method="post">
		<table class="table table-hover" border="0">
			<caption></caption>
			<thead>
				<tr>
					<!-- 
				<th></th>
		 -->
				</tr>
			</thead>
			<tbody>
				<tr>
					<!-- 
					<td></td>
		 -->
				</tr>
				<tr>
					<td><input class="btn" id="id_input_new" type="button"
						value="新增" onclick="newRecord()"> <input class="btn"
						id="id_input_new" type="button" value="删除"
						onclick="delAllRecord()"> <input class="btn"
						id="id_input_new" type="button" value="导出excel" onclick="excel();">
						<input class="btn" id="id_input_new" type="button"
						value="导出excel2" onclick="excel2();"> <input class="btn"
						id="id_input_new" type="button" value="打印" onclick="doPrint();"></td>
				</tr>
			</tbody>
		</table>
		<table style="display: none;" align="center"">
			<tr>
				<td>升降序</td>
				<td><input value="${requestScope.sortFieldValue}"
					id="sortFieldId" name="sortFieldName" /></td>
				<td>排序的列值</td>
				<td><input value="${requestScope.sortOrderValue}"
					id="sortOrderId" name="sortOrderName" /></td>
			</tr>
		</table>
		<table style="width: 100%; table-layout: fixed" id="id_table"
			class="table  table-bordered table-hover">
			<caption></caption>
			<thead>
				<tr>
					<th width="60px;"><input id="id_checkbox_if_all"
						onclick=ayTableCheckAll(); type="checkbox"
						name="name_checkbox_if_all" />全选</th>
					<th width="150px;">操作</th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_ACCOUNT_ID_');"
						title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . sysAccountId%>]排序"
						style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.sysAccountId%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_USER_ID_');"
						title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . sysUserId%>]排序"
						style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.sysUserId%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_ACCOUNT_NAME_');"
						title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias .sysAccountName%>]排序"
						style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.sysAccountName%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PASSWORD_');"
						title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias .password%>]排序"
						style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.password%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
						title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . desc%>]排序"
						style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.desc%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','REGISTER_TYPE_');"
						title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . registerType%>]排序"
						style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.registerType%></th>
					<th class="class_color">
						<!--隐藏列事件--> <select style="width: 60px;"
						onchange="ayTableHiddenColumn(this,'id_table',2);">
							<option value=" &nbsp;" selected="selected">&nbsp;</option>
							<option value="hit.SYS_ACCOUNT_ID_"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.sysAccountId%></option>
							<option value="hit.SYS_USER_ID_"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.sysUserId%></option>
							<option value="hit.SYS_ACCOUNT_NAME_"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.sysAccountName%></option>
							<option value="hit.PASSWORD_"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.password%></option>
							<option value="hit.DESC_"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.desc%></option>
							<option value="hit.REGISTER_TYPE_"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias.registerType%></option>
					</select> <!--隐藏列事件-->
					</th>
				</tr>
			</thead>
			<tbody>
				<%
					java.util.ArrayList<java.util.HashMap<String, Object>> listMap = (java.util.ArrayList<java.util.HashMap<String, Object>>) request
							.getAttribute("list");
					for (java.util.HashMap<String, Object> info : listMap) {
				%>
				<tr>
					<td><input value="<%=info.get("SYS_ACCOUNT_ID_")%>"
						type="checkbox" name="name_checkbox_ids"></td>
					<td><a onclick="delRecord('<%=info.get("SYS_ACCOUNT_ID_")%>')"
						href="#"> 删除 </a> <a
						onclick="updateRecord('<%=info.get("SYS_ACCOUNT_ID_")%>')"
						href="#"> 编辑 </a></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.SYS_ACCOUNT_ID_">&nbsp;<%=info.get("SYS_ACCOUNT_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.SYS_USER_ID_">&nbsp;<%=info.get("SYS_USER_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.SYS_ACCOUNT_NAME_">&nbsp;<%=info.get("SYS_ACCOUNT_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.PASSWORD_">&nbsp;<%=info.get("PASSWORD_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.DESC_">&nbsp;<%=info.get("DESC_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.REGISTER_TYPE_">&nbsp;<%=info.get("REGISTER_TYPE_")%></td>
					<td style="word-wrap: break-word;">&nbsp;</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<%@ include file="/pages/c/x/imports/feng/page/simple/page.jsp"%>
	</form>
</body>
</html>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/page/simple/page.js?version=<%=version %>">
	
</script>
<!-- 加载js -->
<script type="text/javascript">
	/**
	 * 
	 删除所有记录
	 */
	function delAllRecord(id) {
		var url = "${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/del_all.do";
		var objs = document.getElementsByName("name_checkbox_ids");
		var checkedNumber = 0;
		for (var i = 0; i < objs.length; i++) {
			if (objs[i].checked) {
				checkedNumber = checkedNumber + 1;
			}
		}
		if (checkedNumber == 0) {
			alert('请先选择要删除的行');
		} else {
			if (confirm("确定要删除吗？")) {
				document.getElementById("id_form_list").action = url;
				document.getElementById("id_form_list").submit();
			} else {
			}
		}
		return false;
	}
	/**
	 * 
	 删除记录
	 */
	function delRecord(id) {
		if (confirm("确定要删除吗？")) {
			var url = "${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/del.do?id="
					+ id;
			window.location.href = url;
		} else {
		}
	}
	/**
	 * 
	 请求更新
	 */
	function updateRecord(id) {
		var url = "${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/form.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 
	 请求新增
	 */
	function newRecord() {
		var url = "${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/form.do";
		window.location.href = url;
	}
</script>
<script type="text/javascript">
	function excel() {
		var pageIndex = '${cPage.pageIndex}';
		var c_cp = '${pageContext.request.contextPath}';
		//alert('excel='+pageIndex);
		// 表单
		var formObj = document.getElementById('id_form_list');
		var obj_page = document.getElementById('id_page');
		obj_page.value = pageIndex;
		formObj.action = '${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/excel.do';
		// 提交
		formObj.submit();
		formObj.action = '${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/list.do';
	}
	function excel2() {
		var pageIndex = '${cPage.pageIndex}';
		var c_cp = '${pageContext.request.contextPath}';
		//alert('excel='+pageIndex);
		// 表单
		var formObj = document.getElementById('id_form_list');
		var obj_page = document.getElementById('id_page');
		obj_page.value = pageIndex;
		formObj.action = '${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/excel2.do';
		// 提交
		formObj.submit();
		formObj.action = '${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/list.do';
	}
	function doPrint() {
		var pageIndex = '${cPage.pageIndex}';
		var c_cp = '${pageContext.request.contextPath}';
		//alert('excel='+pageIndex);
		// 表单
		var formObj = document.getElementById('id_form_list');
		var obj_page = document.getElementById('id_page');
		obj_page.value = pageIndex;
		formObj.action = '${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/print.do';
		// 提交
		formObj.submit();
		formObj.action = '${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/list.do';
		//window.print();
	}
</script>
