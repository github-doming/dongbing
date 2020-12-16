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
		action="${pageContext.request.contextPath}/all/job/config/list.do"
		id="id_form_list" method="post">
		<table class="table table-hover" border="0">
			<caption></caption>
			<tbody>
				<tr>
					<td>
							<div class="alert alert-info">mvc线程池</div>
					</td>
				</tr>
				<tr>
					<td>thread_mvc_queue_size=${requestScope.thread_mvc_queue_size} </br>
						thread_mvc_executor.corePoolSize=
						${applicationScope.thread_mvc_executor.corePoolSize} </br>
						thread_mvc_executor.poolSize=
						${applicationScope.thread_mvc_executor.poolSize} </br>
						thread_mvc_executor.largestPoolSize=
						${applicationScope.thread_mvc_executor.largestPoolSize} </br>
						thread_mvc_executor.maximumPoolSize=
						${applicationScope.thread_mvc_executor.maximumPoolSize} </br>
						thread_mvc_executor.activeCount=
						${applicationScope.thread_mvc_executor.activeCount} </br>
						thread_mvc_executor.taskCount=
						${applicationScope.thread_mvc_executor.taskCount} </br>
						thread_mvc_executor.completedTaskCount=
						${applicationScope.thread_mvc_executor.completedTaskCount} </br>
						thread_mvc_executor.keepAliveTime=
						${requestScope.thread_mvc_executor_keepAliveTime} </br>
					</td>
				</tr>
				<tr>
					<td><div class="alert alert-info">local线程池</div></td>
				</tr>
				<tr>
					<td>thread_local_queue_size=${requestScope.thread_local_queue_size} </br>
						thread_local_executor.corePoolSize=
						${applicationScope.thread_local_executor.corePoolSize} </br>
						thread_local_executor.poolSize=
						${applicationScope.thread_local_executor.poolSize} </br>
						thread_local_executor.largestPoolSize=
						${applicationScope.thread_local_executor.largestPoolSize} </br>
						thread_local_executor.maximumPoolSize=
						${applicationScope.thread_local_executor.maximumPoolSize} </br>
						thread_local_executor.activeCount=
						${applicationScope.thread_local_executor.activeCount} </br>
						thread_local_executor.taskCount=
						${applicationScope.thread_local_executor.taskCount} </br>
						thread_local_executor.completedTaskCount=
						${applicationScope.thread_local_executor.completedTaskCount} </br>
						thread_local_executor.keepAliveTime=
						${requestScope.thread_local_executor_keepAliveTime} </br>
					</td>
				</tr>
				<tr>
					<td><input onclick="threadStop();" class="btn btn-primary" type="button"
						value="quartz线程停止"></input></td>
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
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_QUARTZ_CONFIG_ID_');"
						title="按[<%=all.job.alias.SysQuartzConfigTAlias . sysQuartzConfigId%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzConfigTAlias.sysQuartzConfigId%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','STARTED_CN_');"
						title="按[<%=all.job.alias.SysQuartzConfigTAlias . startedCn%>]排序"
						style="cursor: pointer;">线程ID</th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','INSTANDBY_MODE_CN_');"
						title="按[<%=all.job.alias.SysQuartzConfigTAlias . instandbyModeCn%>]排序"
						style="cursor: pointer;">名称</th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','UPDATE_TIME_');"
						title="按[<%=all.job.alias.SysQuartzConfigTAlias . updateTime%>]排序"
						style="cursor: pointer;">状态</th>
					<th class="class_color">
						<!--隐藏列事件--> <select style="width: 60px;"
						onchange="ayTableHiddenColumn(this,'id_table',2);">
							<option value=" &nbsp;" selected="selected">&nbsp;</option>
							<option value="hit.SYS_QUARTZ_CONFIG_ID_"><%=all.job.alias.SysQuartzConfigTAlias.sysQuartzConfigId%></option>
							<option value="hit.CREATE_TIME_"><%=all.job.alias.SysQuartzConfigTAlias.createTime%></option>
							<option value="hit.CREATE_TIME_LONG_"><%=all.job.alias.SysQuartzConfigTAlias.createTimeLong%></option>
							<option value="hit.UPDATE_TIME_"><%=all.job.alias.SysQuartzConfigTAlias.updateTime%></option>
							<option value="hit.UPDATE_TIME_LONG_"><%=all.job.alias.SysQuartzConfigTAlias.updateTimeLong%></option>
					</select> <!--隐藏列事件-->
					</th>
				</tr>
			</thead>
			<tbody>
				<%
					java.util.List<Thread> listMap = (java.util.List<Thread>) request.getAttribute("list");
					for (Thread info : listMap) {
				%>
				<tr>
					<td><input value="<%=info.getId()%>" type="checkbox"
						name="name_checkbox_ids"></td>
					<td></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.SYS_QUARTZ_CONFIG_ID_">&nbsp;<%=info.getId()%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.STARTED_CN_">&nbsp;<%=info.getId()%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.INSTANDBY_MODE_CN_">&nbsp;<%=info.getName()%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.CREATE_TIME_">&nbsp;<%=info.getState()%></td>
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
	 * 启动
	 */
	function start(id) {
		var url = "${pageContext.request.contextPath}/all/job/config/start.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 暂停
	 */
	function pause(id) {
		var url = "${pageContext.request.contextPath}/all/job/config/pause.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 
	 删除所有记录
	 */
	function delAllRecord(id) {
		var url = "${pageContext.request.contextPath}/all/gen/sys_quartz_config/t/del_all.do";
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
			var url = "${pageContext.request.contextPath}/all/gen/sys_quartz_config/t/del.do?id="
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
		var url = "${pageContext.request.contextPath}/all/job/config/form.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 
	 请求新增
	 */
	function newRecord() {
		var url = "${pageContext.request.contextPath}/all/job/config/form.do";
		window.location.href = url;
	}
</script>
<!-- 加载js -->
<script type="text/javascript">
	/**
	 * 
	 thread 线程停止
	 */
	function threadStop() {
		var url = "${pageContext.request.contextPath}/c/a/monitor/thread/stop.do";
		window.location.href = url;
	}
</script>