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
		action="${pageContext.request.contextPath}/all/job/job/list.do"
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
						onclick="delAllRecord()"></td>
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
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_QUARTZ_JOB_ID_');"
						title="按[<%=all.job.alias.SysQuartzJobTAlias . sysQuartzJobId%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzJobTAlias.sysQuartzJobId%></th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_JOB_NAME_');"
						title="按[<%=all.job.alias.SysQuartzJobTAlias . sysJobName%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzJobTAlias.sysJobName%></th>



					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','JOB_NAME_');"
						title="按[<%=all.job.alias.SysQuartzJobTAlias . jobName%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzJobTAlias.jobName%></th>



					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','JOB_CLASS_NAME_');"
						title="按[<%=all.job.alias.SysQuartzJobTAlias . jobClassName%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzJobTAlias.jobClassName%></th>


					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','QUARTZ_CN_');"
						title="按[<%=all.job.alias.SysQuartzJobTAlias . quartzCn%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzJobTAlias.quartzCn%></th>


		
		

					<th class="class_color">
						<!--隐藏列事件--> <select style="width: 60px;"
						onchange="ayTableHiddenColumn(this,'id_table',2);">
							<option value=" &nbsp;" selected="selected">&nbsp;</option>

							<option value="hit.SYS_QUARTZ_JOB_ID_"><%=all.job.alias.SysQuartzJobTAlias.sysQuartzJobId%></option>
							<option value="hit.SYS_JOB_NAME_"><%=all.job.alias.SysQuartzJobTAlias.sysJobName%></option>
							<option value="hit.JOB_NAME_"><%=all.job.alias.SysQuartzJobTAlias.jobName%></option>
							<option value="hit.JOB_CLASS_NAME_"><%=all.job.alias.SysQuartzJobTAlias.jobClassName%></option>



							<option value="hit.QUARTZ_CN_"><%=all.job.alias.SysQuartzJobTAlias.quartzCn%></option>


							
							<option value="hit.CREATE_TIME_"><%=all.job.alias.SysQuartzJobTAlias.createTime%></option>
							<option value="hit.CREATE_TIME_LONG_"><%=all.job.alias.SysQuartzJobTAlias.createTimeLong%></option>
							<option value="hit.UPDATE_TIME_"><%=all.job.alias.SysQuartzJobTAlias.updateTime%></option>
							<option value="hit.UPDATE_TIME_LONG_"><%=all.job.alias.SysQuartzJobTAlias.updateTimeLong%></option>
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
					<td><input value="<%=info.get("SYS_QUARTZ_JOB_ID_")%>"
						type="checkbox" name="name_checkbox_ids"></td>
					<td><a onclick="run('<%=info.get("SYS_QUARTZ_JOB_ID_")%>')"
						href="#"> 运行 </a> 
						
						
						
						
						<a
						onclick="delRecord('<%=info.get("SYS_QUARTZ_JOB_ID_")%>')"
						href="#"> 删除 </a> <a
						onclick="updateRecord('<%=info.get("SYS_QUARTZ_JOB_ID_")%>')"
						href="#"> 编辑 </a></td>





					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="display: none; word-wrap: break-word;"
						id="hit.SYS_QUARTZ_JOB_ID_">&nbsp;<%=info.get("SYS_QUARTZ_JOB_ID_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.SYS_JOB_NAME_">&nbsp;<%=info.get("SYS_JOB_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.JOB_NAME_">&nbsp;<%=info.get("JOB_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.JOB_CLASS_NAME_">&nbsp;<%=info.get("JOB_CLASS_NAME_")%></td>





					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.QUARTZ_CN_">&nbsp;<%=info.get("QUARTZ_CN_")%></td>



	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;"  " id="hit.CREATE_USER_">&nbsp;<%=info.get("CREATE_USER_")%></td>
	
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;" id="hit.CREATE_TIME_">&nbsp;<%=info.get("CREATE_TIME_")%></td>

	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;" id="hit.UPDATE_USER_">&nbsp;<%=info.get("UPDATE_USER_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="display: none;word-wrap : break-word;" id="hit.UPDATE_TIME_">&nbsp;<%=info.get("UPDATE_TIME_")%></td>



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
	 请求执行计划
	 */
	function run(id) {
		var url = "${pageContext.request.contextPath}/all/job/job/run.do?id="
				+ id;
		window.location.href = url;
	}

	/**
	 * 
	 删除所有记录
	 */
	function delAllRecord(id) {
		var url = "${pageContext.request.contextPath}/all/job/job/delAll.do";
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
			var url = "${pageContext.request.contextPath}/all/job/job/del.do?id="
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
		var url = "${pageContext.request.contextPath}/all/job/job/form.do?id="
				+ id;
		window.location.href = url;
	}
	/**
	 * 
	 请求新增
	 */
	function newRecord() {
		var url = "${pageContext.request.contextPath}/all/job/job/form.do";
		window.location.href = url;
	}
</script>