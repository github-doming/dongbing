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
<center>
	<div style="color:white;width:100%;height:100px;background-color:rgb(29,41,57);padding-top:20px">
		<span style="font-size:30px;">用户方案管理</span>
	</div>
</center>
<form action="${pageContext.request.contextPath}/ibm/admin/ibm_plan_user/t/list.do"  id="id_form_list" method="post">
<table id="topic_title" style="width:90%;table-layout:fixed; margin: 0px auto;"  id="id_table" class="table  table-bordered table-hover">
	<caption></caption>
	<thead>
	<tr>
		<th>
			<input class="btn" style="height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white" id="id_input_new" type="button" value="新增" onclick="newRecord()">
			<input class="btn" style="height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white" id="id_input_new" type="button" value="删除" onclick="delAllRecord()">
		</th>
		<th  colspan="9" style="text-align: right">
			<input value="${ibmPlanId}" name="ibm_plan_id" type="hidden"/>
			<input id="seach_text" style="width:300px;height:20px" name="PLAN_NAME_" id="id_input_new" placeholder="请输入盘口名称" type="text" value="${planName}"> 
			<input class="btn" style="height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white"id="id_input_new" type="button" value="搜索" onclick="seachRecord()">
		</th>
	</tr>
		<tr>
<th style="width: 10%;" ><input id="id_checkbox_if_all" onclick=ayTableCheckAll(); type="checkbox" name="name_checkbox_if_all" />全选</th>
<th style="width: 10%;" >操作</th>
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PLAN_NAME_');"
				title="按[盘口名称]排序" style="cursor: pointer;">方案名称</th>
<th>所属用户</th>
<th>所属游戏</th>
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PROFIT_LIMIT_MAX_T_');"
				title="按[盘口CODE]排序" style="cursor: pointer;">止盈</th>
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','LOSS_LIMIT_MIN_T_');"
				title="按[在线会员数量]排序" style="cursor: pointer;">止损</th>
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','MONITOR_PERIOD_');"
				title="按[会员最大在线数量]排序" style="cursor: pointer;">监控期数</th>
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','BET_MODE_');"
				title="按[使用频次]排序" style="cursor: pointer;">投注模式</th>
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','OPERATE_FREQUENCY_');"
				title="按[使用频次]排序" style="cursor: pointer;">使用频次</th>
		</tr>
	</thead>
	<tbody>
		<%
		java.util.ArrayList<java.util.HashMap<String, Object>> listMap = (java.util.ArrayList<java.util.HashMap<String, Object>>) request
				.getAttribute("list");
	for (java.util.HashMap<String, Object> info : listMap) {
	%>
<tr>
	<td><input value="<%=info.get("IBM_PLAN_USER_ID_")%>" type="checkbox"
		name="name_checkbox_ids"></td>
	<td><a onclick="delRecord('<%=info.get("IBM_PLAN_USER_ID_")%>')" href="#"> 删除
	</a> <a onclick="updateRecord('<%=info.get("IBM_PLAN_USER_ID_")%>')" href="#"> 编辑 </a></td>
	<td style="word-wrap : break-word;  " id="hit.PLAN_NAME_">&nbsp;<%=info.get("PLAN_NAME_")%></td>
	<td style="word-wrap : break-word;  " id="hit.APP_USER_NAME">&nbsp;<%=info.get("APP_USER_NAME")%></td>
	<td style="word-wrap : break-word;  " id="hit.GAME_NAME_">&nbsp;<%=info.get("GAME_NAME_")%></td>
	<td style="word-wrap : break-word;  " id="hit.PROFIT_LIMIT_MAX_T_">&nbsp;<%=info.get("PROFIT_LIMIT_MAX_T_")%></td>
	<td style="word-wrap : break-word;  " id="hit.LOSS_LIMIT_MIN_T_">&nbsp;<%=info.get("LOSS_LIMIT_MIN_T_")%></td>
	<td style="word-wrap : break-word;  " id="hit.MONITOR_PERIOD_">&nbsp;<%=info.get("MONITOR_PERIOD_")%></td>
	<td style="word-wrap : break-word;  " id="hit.BET_MODE_">&nbsp;<%=info.get("BET_MODE_")%></td>
	<td style="word-wrap : break-word;  " id="hit.OPERATE_FREQUENCY_">&nbsp;<%=info.get("OPERATE_FREQUENCY_")%></td>
</tr>
		<%
		}
	%>
	<tr>
		<th colspan="10">
			<%@ include file="/pages/com/cms/admin/page.jsp"%>
		</th>
	</tr>
	</tbody>
</table>
<br/>
</form>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/page/simple/page.js">
        </script>
<!-- 加载js -->
<script type="text/javascript">
function toPost(id){
	window.location.href="${pageContext.request.contextPath}/com/cms/cms_topic/wck/topic_post.do?cms_topic_id="+id;
}
/**
 *模糊查询记录
 */
function seachRecord(){
	$("#id_form_list").submit();
}
/**
 * 
 删除所有记录
 */
function delAllRecord(id){
	var url = "${pageContext.request.contextPath}/ibm/admin/ibm_plan_user/t/delAll.do";
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
		var url = "${pageContext.request.contextPath}/ibm/admin/ibm_plan_user/t/del.do?id="+id;
		window.location.href= url;
	}
}
/**
 * 
 请求更新
 */
function updateRecord(id){
	var url= "${pageContext.request.contextPath}/ibm/admin/ibm_plan_user/t/form.do?id="+id;
 	window.location.href=url;
}

/**
 * 
 请求新增
 */
function newRecord(){
 	var url= "${pageContext.request.contextPath}/ibm/admin/ibm_plan_user/t/form.do";
 	window.location.href=url;
}
	</script>
</html>