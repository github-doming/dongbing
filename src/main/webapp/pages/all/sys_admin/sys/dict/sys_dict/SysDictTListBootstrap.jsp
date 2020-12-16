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
		action="${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict/list/bootstrap.do"
		id="id_form_list" method="post">
		<table style="display: none;" align="center" border="1">
			<tr>
				<td>升降序</td>
				<td><input value="${requestScope.sortFieldValue}"
					id="sortFieldId" name="sortFieldName" /></td>
				<td>排序的列值</td>
				<td><input value="${requestScope.sortOrderValue}"
					id="sortOrderId" name="sortOrderName" /></td>
			</tr>
			<tr>
				<td>树id</td>
				<td><input value="${requestScope.value_first$tree$id}"
					id="id_first$tree$id" name="first$tree$id" /></td>
				<td>树名称</td>
				<td><input value="${requestScope.value_first$tree$name}"
					id="id_first$tree$name" name="name_first$tree$name" /></td>
			</tr>
		</table>
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
						onclick="delAllRecord()"></td>
				</tr>
			</tbody>
		</table>
		<table style="width: 100%; table-layout: fixed" id="id_table"
			class="table  table-bordered table-hover">
			<caption></caption>
			<thead>
				<tr>
					<th width="60px;"><input id="id_checkbox_if_all"
						onclick=ayTableCheckAll(); type="checkbox" name="" />全选</th>
					<th width="60px;">操作</th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','IDX_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . idx%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.idx%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_DICT_ID_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . sysDictId%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sysDictId%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_DICT_NAME_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . sysDictName%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sysDictName%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_DICT_CODE_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . sysDictCode%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sysDictCode%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TABLE_NAME_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . tableName%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableName%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TABLE_ID_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . tableId%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableId%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TABLE_COLUMN_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . tableColumn%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableColumn%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TABLE_KEY_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . tableKey%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableKey%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TABLE_VALUE_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . tableValue%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableValue%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DATA_TYPE_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . dataType%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.dataType%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','COLUMN_TYPE_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . columnType%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.columnType%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','COLUMN_ASSORT_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . columnAssort%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.columnAssort%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SN_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . sn%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sn%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;" 
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','KEY_CN_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . keyCn%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.keyCn%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','VALUE_CN_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . valueCn%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.valueCn%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DEF_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . def%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.def%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SHOW_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . show%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.show%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_USER_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . createUser%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createUser%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . createTime%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createTime%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . createTimeLong%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createTimeLong%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_USER_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . updateUser%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateUser%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . updateTime%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateTime%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . updateTimeLong%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateTimeLong%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . state%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.state%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
						title="按[<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . desc%>]排序"
						style="cursor: pointer;"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.desc%></th>
					<th class="class_color">
						<!--隐藏列事件--> <select style="width: 60px;"
						onchange="ayTableHiddenColumn(this,'id_table',2);">
							<option value=" &nbsp;" selected="selected">&nbsp;</option>
							<option value="hit.IDX_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.idx%></option>
							<option value="hit.SYS_DICT_ID_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sysDictId%></option>
							<option value="hit.SYS_DICT_NAME_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sysDictName%></option>
							<option value="hit.SYS_DICT_CODE_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sysDictCode%></option>
							<option value="hit.TABLE_NAME_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableName%></option>
							<option value="hit.TABLE_ID_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableId%></option>
							<option value="hit.TABLE_COLUMN_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableColumn%></option>
							<option value="hit.TABLE_KEY_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableKey%></option>
							<option value="hit.TABLE_VALUE_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableValue%></option>
							<option value="hit.DATA_TYPE_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.dataType%></option>
							<option value="hit.COLUMN_TYPE_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.columnType%></option>
							<option value="hit.COLUMN_ASSORT_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.columnAssort%></option>
							<option value="hit.SN_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sn%></option>
							<option value="hit.KEY_CN_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.keyCn%></option>
							<option value="hit.VALUE_CN_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.valueCn%></option>
							<option value="hit.DEF_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.def%></option>
							<option value="hit.SHOW_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.show%></option>
							<option value="hit.CREATE_USER_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createUser%></option>
							<option value="hit.CREATE_TIME_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createTime%></option>
							<option value="hit.CREATE_TIME_LONG_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createTimeLong%></option>
							<option value="hit.UPDATE_USER_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateUser%></option>
							<option value="hit.UPDATE_TIME_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateTime%></option>
							<option value="hit.UPDATE_TIME_LONG_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateTimeLong%></option>
							<option value="hit.STATE_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.state%></option>
							<option value="hit.DESC_"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.desc%></option>
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
					<td><input value="<%=info.get("${name.columnName}")%>"
						type="checkbox" name="name_checkbox_ids"></td>
					<td><a onclick="delRecord('<%=info.get("SYS_DICT_ID_")%>')"
						href="#"> 删除 </a> <a
						onclick="updateRecord('<%=info.get("SYS_DICT_ID_")%>')" href="#">
							编辑 </a></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.IDX_">&nbsp;<%=info.get("IDX_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.SYS_DICT_ID_">&nbsp;<%=info.get("SYS_DICT_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.SYS_DICT_NAME_">&nbsp;<%=info.get("SYS_DICT_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.SYS_DICT_CODE_">&nbsp;<%=info.get("SYS_DICT_CODE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.TABLE_NAME_">&nbsp;<%=info.get("TABLE_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.TABLE_ID_">&nbsp;<%=info.get("TABLE_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.TABLE_COLUMN_">&nbsp;<%=info.get("TABLE_COLUMN_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.TABLE_KEY_">&nbsp;<%=info.get("TABLE_KEY_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.TABLE_VALUE_">&nbsp;<%=info.get("TABLE_VALUE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.DATA_TYPE_">&nbsp;<%=info.get("DATA_TYPE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.COLUMN_TYPE_">&nbsp;<%=info.get("COLUMN_TYPE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.COLUMN_ASSORT_">&nbsp;<%=info.get("COLUMN_ASSORT_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.SN_">&nbsp;<%=info.get("SN_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none;word-wrap: break-word;" id="hit.KEY_CN_">&nbsp;<%=info.get("KEY_CN_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.VALUE_CN_">&nbsp;<%=info.get("VALUE_CN_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.DEF_">&nbsp;<%=info.get("DEF_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.SHOW_">&nbsp;<%=info.get("SHOW_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.CREATE_USER_">&nbsp;<%=info.get("CREATE_USER_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.CREATE_TIME_LONG_">&nbsp;<%=info.get("CREATE_TIME_LONG_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.UPDATE_USER_">&nbsp;<%=info.get("UPDATE_USER_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.UPDATE_TIME_LONG_">&nbsp;<%=info.get("UPDATE_TIME_LONG_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.STATE_">&nbsp;<%=info.get("STATE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;" id="hit.DESC_">&nbsp;<%=info.get("DESC_")%></td>
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
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/page/simple/page.js?version=<%=version%>">
</script>
<!-- 加载js -->
<script type="text/javascript">
	/**
	 * 
	 删除所有记录
	 */
	function delAllRecord(id) {
		var url = "${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict/del_all/bootstrap.do";
		var objs = document.getElementsByName("checkboxIds");
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
			var url = "${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict/del/bootstrap.do?id="
					+ id;
			var first$tree$id = document.getElementById("id_first$tree$id").value;
			url = url + "&&first$tree$id=" + first$tree$id;
			window.location.href = url;
		} else {
		}
	}
	/**
	 * 
	 请求更新
	 */
	function updateRecord(id) {
		var url = "${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict/form/bootstrap.do?id="
				+ id;
		var first$tree$id = document.getElementById("id_first$tree$id").value;
		url = url + "&&first$tree$id=" + first$tree$id;
		window.location.href = url;
	}
	/**
	 * 
	 请求新增
	 */
	function newRecord() {
		var url = "${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict/form/bootstrap.do";
		var first$tree$id = document.getElementById("id_first$tree$id").value;
		url = url + "?first$tree$id=" + first$tree$id;
		window.location.href = url;
	}
</script>