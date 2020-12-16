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
		action="${pageContext.request.contextPath}/all/job/trigger/list.do"
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
						value="启动" onclick="startAllRecord()"> <input class="btn"
						id="id_input_new" type="button" value="运行"
						onclick="startRunRecord()"> <input class="btn"
						id="id_input_new" type="button" value="暂停"
						onclick="startPauseRecord()"> <input class="btn"
						id="id_input_new" type="button" value="删除"
						onclick="delAllRecord()"> <input class="btn"
						id="id_input_new" type="button" value="新增" onclick="newRecord()">
					</td>
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
					<th style="display: none; cursor: pointer;"
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_QUARTZ_TRIGGER_ID_');"
						title="按[<%=all.job.alias.SysQuartzTriggerTAlias . sysQuartzTriggerId%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzTriggerTAlias.sysQuartzTriggerId%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_TRIGGER_NAME_');"
						title="按[<%=all.job.alias.SysQuartzTriggerTAlias . sysTriggerName%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzTriggerTAlias.sysTriggerName%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TRIGGER_STATE_');"
						title="按[<%=all.job.alias.SysQuartzTriggerTAlias . triggerStateCn%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzTriggerTAlias.triggerState%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TRIGGER_STATE_CN_');"
						title="按[<%=all.job.alias.SysQuartzTriggerTAlias . triggerStateCn%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzTriggerTAlias.triggerStateCn%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','JOB_NAME_');"
						title="按[<%=all.job.alias.SysQuartzTriggerTAlias . jobName%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzTriggerTAlias.jobName%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','JOB_CLASS_NAME_');"
						title="按[<%=all.job.alias.SysQuartzTriggerTAlias . jobClassName%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzTriggerTAlias.jobClassName%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CRON_EXPRESSION_');"
						title="按[<%=all.job.alias.SysQuartzTriggerTAlias . cronExpression%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzTriggerTAlias.cronExpression%></th>

				</tr>
			</thead>
			<tbody>
				<%
					java.util.ArrayList<java.util.HashMap<String, Object>> listMap = (java.util.ArrayList<java.util.HashMap<String, Object>>) request
							.getAttribute("list");
					for (java.util.HashMap<String, Object> info : listMap) {
				%>
				<tr>
					<td><input value="<%=info.get("SYS_QUARTZ_TRIGGER_ID_")%>"
						type="checkbox" name="name_checkbox_ids"></td>
					<td><a
						onclick="start('<%=info.get("SYS_QUARTZ_TRIGGER_ID_")%>')"
						href="#"> 启动 </a> <a
						onclick="run('<%=info.get("SYS_QUARTZ_TRIGGER_ID_")%>')" href="#">
							运行 </a> <a onclick="pause('<%=info.get("SYS_QUARTZ_TRIGGER_ID_")%>')"
						href="#"> 暂停</a> <a
						onclick="delRecord('<%=info.get("SYS_QUARTZ_TRIGGER_ID_")%>')"
						href="#"> 删除 </a> <a
						onclick="updateRecord('<%=info.get("SYS_QUARTZ_TRIGGER_ID_")%>')"
						href="#"> 编辑 </a></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.SYS_QUARTZ_TRIGGER_ID_">&nbsp;<%=info.get("SYS_QUARTZ_TRIGGER_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.SYS_TRIGGER_NAME_">&nbsp;<%=info.get("SYS_TRIGGER_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.TRIGGER_STATE_">&nbsp;<%=info.get("TRIGGER_STATE_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.TRIGGER_STATE_CN_">&nbsp;<%=info.get("TRIGGER_STATE_CN_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.JOB_NAME_">&nbsp;<%=info.get("JOB_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.JOB_CLASS_NAME_">&nbsp;<%=info.get("JOB_CLASS_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.CRON_EXPRESSION_">&nbsp;<%=info.get("CRON_EXPRESSION_")%></td>
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
	 恢复并运行定时计划(所有记录)
	 */
	function startRunRecord() {
		var url = "${pageContext.request.contextPath}/all/job/trigger/runAll.do";
		var objs = document.getElementsByName("name_checkbox_ids");
		var checkedNumber = 0;
		for (var i = 0; i < objs.length; i++) {
			if (objs[i].checked) {
				checkedNumber = checkedNumber + 1;
			}
		}
		if (checkedNumber == 0) {
			alert('请先选择要运行的行');
		} else {
			if (confirm("确定要运行吗？")) {
				document.getElementById("id_form_list").action = url;
				document.getElementById("id_form_list").submit();
			} else {
			}
		}
		return false;
	}
	/**
	 * 
	 恢复并执行定时计划
	 */
	function run(id) {
		var url = "${pageContext.request.contextPath}/all/job/trigger/run.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 
	 暂停定时计划(所有记录)
	 */
	function startPauseRecord() {
		var url = "${pageContext.request.contextPath}/all/job/trigger/pauseAll.do";
		var objs = document.getElementsByName("name_checkbox_ids");
		var checkedNumber = 0;
		for (var i = 0; i < objs.length; i++) {
			if (objs[i].checked) {
				checkedNumber = checkedNumber + 1;
			}
		}
		if (checkedNumber == 0) {
			alert('请先选择要暂停的行');
		} else {
			if (confirm("确定要暂停吗？")) {
				document.getElementById("id_form_list").action = url;
				document.getElementById("id_form_list").submit();
			} else {
			}
		}
		return false;
	}
	/**
	 * 
	 暂停定时计划
	 */
	function pause(id) {
		var url = "${pageContext.request.contextPath}/all/job/trigger/pause.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 
	 启动所有记录
	 */
	function startAllRecord() {
		var url = "${pageContext.request.contextPath}/all/job/trigger/startAll.do";
		var objs = document.getElementsByName("name_checkbox_ids");
		var checkedNumber = 0;
		for (var i = 0; i < objs.length; i++) {
			if (objs[i].checked) {
				checkedNumber = checkedNumber + 1;
			}
		}
		if (checkedNumber == 0) {
			alert('请先选择要启动的行');
		} else {
			if (confirm("确定要启动吗？")) {
				document.getElementById("id_form_list").action = url;
				document.getElementById("id_form_list").submit();
			} else {
			}
		}
		return false;
	}
	/**
	 * 
	 启动定时计划
	 */
	function start(id) {
		var url = "${pageContext.request.contextPath}/all/job/trigger/start.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 
	 删除所有记录
	 */
	function delAllRecord(id) {
		var url = "${pageContext.request.contextPath}/all/job/trigger/delAll.do";
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
			var url = "${pageContext.request.contextPath}/all/job/trigger/del.do?id="
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
		var url = "${pageContext.request.contextPath}/all/job/trigger/form.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 
	 请求新增
	 */
	function newRecord() {
		var url = "${pageContext.request.contextPath}/all/job/trigger/form.do";
		window.location.href = url;
	}
</script>