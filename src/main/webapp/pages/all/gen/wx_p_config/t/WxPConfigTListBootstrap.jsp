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
<form action="${pageContext.request.contextPath}/all/gen/wx_p_config/t/list/bootstrap.do"  id="id_form_list" method="post">
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
<table style="width:100%;table-layout:fixed"  id="id_table" class="table  table-bordered table-hover">
	<caption></caption>
	<thead>
		<tr>
				<th width="60px;" ><input id="id_checkbox_if_all" onclick=ayTableCheckAll();
				type="checkbox" name="name_checkbox_if_all" />全选</th>
				<th width="150px;" >操作</th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','IDX_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . idx%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . idx%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','WX_P_CONFIG_ID_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . wxPConfigId%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . wxPConfigId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','INIT_ID_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . initId%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . initId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','APP_TOKEN_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . appToken%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . appToken%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','APP_ID_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . appId%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . appId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','APP_SECRET_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . appSecret%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . appSecret%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ACCESS_TOKEN_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . accessToken%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . accessToken%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','EXPIRES_IN_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . expiresIn%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . expiresIn%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','EXPIRES_TIME_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . expiresTime%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . expiresTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','EXPIRES_TIME_LONG_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . expiresTimeLong%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . expiresTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . createTime%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . createTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CREATE_TIME_LONG_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . createTimeLong%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . createTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_LONG_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . updateTimeLong%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . updateTimeLong%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . updateTime%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . updateTime%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STATE_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . state%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . state%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . desc%>]排序" style="cursor: pointer;"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . desc%></th>
	<th class="class_color"><!--隐藏列事件--> <select  style="width:60px; " 
				onchange="ayTableHiddenColumn(this,'id_table',2);">
				<option value=" &nbsp;" selected="selected">&nbsp;</option>
	<option value="hit.IDX_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . idx%></option>
	<option value="hit.WX_P_CONFIG_ID_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . wxPConfigId%></option>
	<option value="hit.INIT_ID_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . initId%></option>
	<option value="hit.APP_TOKEN_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . appToken%></option>
	<option value="hit.APP_ID_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . appId%></option>
	<option value="hit.APP_SECRET_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . appSecret%></option>
	<option value="hit.ACCESS_TOKEN_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . accessToken%></option>
	<option value="hit.EXPIRES_IN_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . expiresIn%></option>
	<option value="hit.EXPIRES_TIME_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . expiresTime%></option>
	<option value="hit.EXPIRES_TIME_LONG_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . expiresTimeLong%></option>
	<option value="hit.CREATE_TIME_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . createTime%></option>
	<option value="hit.CREATE_TIME_LONG_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . createTimeLong%></option>
	<option value="hit.UPDATE_TIME_LONG_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . updateTimeLong%></option>
	<option value="hit.UPDATE_TIME_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . updateTime%></option>
	<option value="hit.STATE_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . state%></option>
	<option value="hit.DESC_"><%=all.gen.wx_p_config.t.alias.WxPConfigTAlias . desc%></option>
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
			<td><input value="<%=info.get("WX_P_CONFIG_ID_")%>" type="checkbox"
				name="name_checkbox_ids"></td>
			<td><a onclick="delRecord('<%=info.get("WX_P_CONFIG_ID_")%>')" href="#"> 删除
			</a> <a onclick="updateRecord('<%=info.get("WX_P_CONFIG_ID_")%>')" href="#"> 编辑 </a></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.IDX_">&nbsp;<%=info.get("IDX_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.WX_P_CONFIG_ID_">&nbsp;<%=info.get("WX_P_CONFIG_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.INIT_ID_">&nbsp;<%=info.get("INIT_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.APP_TOKEN_">&nbsp;<%=info.get("APP_TOKEN_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.APP_ID_">&nbsp;<%=info.get("APP_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.APP_SECRET_">&nbsp;<%=info.get("APP_SECRET_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.ACCESS_TOKEN_">&nbsp;<%=info.get("ACCESS_TOKEN_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.EXPIRES_IN_">&nbsp;<%=info.get("EXPIRES_IN_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.EXPIRES_TIME_">&nbsp;<%=info.get("EXPIRES_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.EXPIRES_TIME_LONG_">&nbsp;<%=info.get("EXPIRES_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CREATE_TIME_LONG_">&nbsp;<%=info.get("CREATE_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UPDATE_TIME_LONG_">&nbsp;<%=info.get("UPDATE_TIME_LONG_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.STATE_">&nbsp;<%=info.get("STATE_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.DESC_">&nbsp;<%=info.get("DESC_")%></td>
			<td style="word-wrap : break-word;  ">&nbsp;</td>
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
function delAllRecord(id){
	var url = "${pageContext.request.contextPath}/all/gen/wx_p_config/t/del_all/bootstrap.do";
	var objs = document.getElementsByName("name_checkbox_ids");
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
		var url = "${pageContext.request.contextPath}/all/gen/wx_p_config/t/del/bootstrap.do?id="+id;
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
	var url= "${pageContext.request.contextPath}/all/gen/wx_p_config/t/form/bootstrap.do?id="+id;
 	window.location.href=url;
}
/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/all/gen/wx_p_config/t/form/bootstrap.do";
 	window.location.href=url;
}
	</script>