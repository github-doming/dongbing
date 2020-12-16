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
<div style="display: none;" id="id_div_msg">正在提交中...</div>
<form action="${pageContext.request.contextPath}/admin/business/fas_bill_info/list.do"  id="id_form_list" method="post">
<table  style="display: none;" class="table table-hover" border="0">
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
				id="id_input_new" type="button" value="删除" onclick="delAllRecord()">
			</td>
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
	<tr bgcolor="#FFFFFF">
		<td width="99.9%">
		<input class="btn" id="id_input_new" type="button"
				value="新增" onclick="newRecord()"> <input class="btn"
				id="id_input_new" type="button" value="删除" onclick="delAllRecord()">
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



<table class="class_crud_table" id="id_table" border="0" cellpadding="0" cellspacing="1">
	<caption></caption>
	<thead>
		<tr>
				<th width="60px;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><input id="id_checkbox_if_all" onclick=ayTableCheckAll();
				type="checkbox" name="" />全选</th>
				<th width="60px;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif">操作</th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','id');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . id%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','business_name');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_name%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_name%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','business_id');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_id%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','account_begin');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_begin%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_begin%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','account_begin_id');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_begin_id%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_begin_id%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','account_amount');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_amount%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_amount%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','account_balance');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_balance%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_balance%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','create_time_dt');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . create_time_dt%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . create_time_dt%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','create_time');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . create_time%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . create_time%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','value_1');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_1%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_1%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','value_2');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_2%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_2%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','value_3');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_3%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_3%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','value_5');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_5%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_5%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','business_from');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_from%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_from%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','business_to');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_to%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_to%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','row');"
				title="按[<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . row%>]排序" style="cursor: pointer;" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . row%></th>
	<th class="class_color" background="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/list/images/table_th_bg.gif"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.id"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . id%></option>
	<option value="hit.business_name"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_name%></option>
	<option value="hit.business_id"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_id%></option>
	<option value="hit.account_begin"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_begin%></option>
	<option value="hit.account_begin_id"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_begin_id%></option>
	<option value="hit.account_amount"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_amount%></option>
	<option value="hit.account_balance"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_balance%></option>
	<option value="hit.create_time_dt"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . create_time_dt%></option>
	<option value="hit.create_time"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . create_time%></option>
	<option value="hit.value_1"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_1%></option>
	<option value="hit.value_2"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_2%></option>
	<option value="hit.value_3"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_3%></option>
	<option value="hit.value_5"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_5%></option>
	<option value="hit.business_from"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_from%></option>
	<option value="hit.business_to"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_to%></option>
	<option value="hit.row"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . row%></option>
			</select> <!--隐藏列事件--></th>
		</tr>
	</thead>
	<tbody>
		<%
		java.util.ArrayList<java.util.HashMap<String, Object>> listMap = (java.util.ArrayList<java.util.HashMap<String, Object>>) request
				.getAttribute("list");
	for (java.util.HashMap<String, Object> info : listMap) {
	%>
		<tr>
			<td class="class_crud_td_white"><input value="<%=info.get("${name.columnName}")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td  class="class_crud_td_white"><a class="class_a" onclick="delRecord('<%=info.get("id")%>')" href="#"> 删除
			</a> <a class="class_a" onclick="updateRecord('<%=info.get("id")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.id">&nbsp;<%=info.get("id")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.business_name">&nbsp;<%=info.get("business_name")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.business_id">&nbsp;<%=info.get("business_id")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.account_begin">&nbsp;<%=info.get("account_begin")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.account_begin_id">&nbsp;<%=info.get("account_begin_id")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.account_amount">&nbsp;<%=info.get("account_amount")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.account_balance">&nbsp;<%=info.get("account_balance")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.create_time_dt">&nbsp;<%=info.get("create_time_dt")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.create_time">&nbsp;<%=info.get("create_time")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.value_1">&nbsp;<%=info.get("value_1")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.value_2">&nbsp;<%=info.get("value_2")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.value_3">&nbsp;<%=info.get("value_3")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.value_5">&nbsp;<%=info.get("value_5")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.business_from">&nbsp;<%=info.get("business_from")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.business_to">&nbsp;<%=info.get("business_to")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td class="class_crud_td_white" id="hit.row">&nbsp;<%=info.get("row")%></td>
			<td class="class_crud_td_white">&nbsp;</td>
		</tr>
		<%
		}
	%>
	</tbody>
</table>
<%@ include file="/pages/c/x/imports/feng/page/kaida/page.jsp"%>
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
function delAllRecord(id){
	var url = "${pageContext.request.contextPath}/admin/business/fas_bill_info/del_all.do";
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
/**
 * 
 删除记录
 */
function delRecord(id){
	if(confirm("确定要删除吗？"))
	{
		var url = "${pageContext.request.contextPath}/admin/business/fas_bill_info/del.do?id="+id;
		window.location.href= url;
	}
	else
	{
	}
}
/**
 * 
 请求更新
 */
function updateRecord(id){
	var url= "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/fas/business/fas_bill_info/form.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/fas/business/fas_bill_info/form.do";
 	window.location.href=url;
}
	</script>