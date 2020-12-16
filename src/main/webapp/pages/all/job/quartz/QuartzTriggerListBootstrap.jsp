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
		action="${pageContext.request.contextPath}/all/job/quartz/trigger/list.do"
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
			<caption>quartz作业与计划</caption>
			<thead>
				<tr>
					<th width="60px;"><input id="id_checkbox_if_all"
						onclick=ayTableCheckAll(); type="checkbox"
						name="name_checkbox_if_all" />全选</th>
					<th width="150px;">操作</th>


			
			
			
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','TRIGGER_NAME_');"
						title="按[<%=all.job.alias.SysQuartzTriggerTAlias . triggerName%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzTriggerTAlias.triggerName%></th>

			
			

				

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
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
						title="按[<%=all.job.alias.SysQuartzTriggerTAlias . desc%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzTriggerTAlias.desc%></th>
				
				


					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','CRON_EXPRESSION_');"
						title="按[<%=all.job.alias.SysQuartzTriggerTAlias . cronExpression%>]排序"
						style="cursor: pointer;"><%=all.job.alias.SysQuartzTriggerTAlias.cronExpression%></th>









					<th class="class_color">
						<!--隐藏列事件--> <select style="width: 60px;"
						onchange="ayTableHiddenColumn(this,'id_table',2);">
							<option value=" &nbsp;" selected="selected">&nbsp;</option>
								<option value="hit.TRIGGER_NAME_"><%=all.job.alias.SysQuartzTriggerTAlias.triggerName%></option>
							
							<option value="hit.TRIGGER_STATE_"><%=all.job.alias.SysQuartzTriggerTAlias.triggerState%></option>

							<option value="hit.TRIGGER_STATE_CN_"><%=all.job.alias.SysQuartzTriggerTAlias.triggerStateCn%></option>


							
							<option value="hit.JOB_NAME_"><%=all.job.alias.SysQuartzTriggerTAlias.jobName%></option>
							<option value="hit.JOB_CLASS_NAME_"><%=all.job.alias.SysQuartzTriggerTAlias.jobClassName%></option>
							<option value="hit.DESC_"><%=all.job.alias.SysQuartzTriggerTAlias.desc%></option>
						


							<option value="hit.CRON_EXPRESSION_"><%=all.job.alias.SysQuartzTriggerTAlias.cronExpression%></option>

		
		
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
					<td><input value="<%=info.get("TRIGGER_NAME_")%>"
						type="checkbox" name="name_checkbox_ids"></td>
					<td>
					
						
					
					
					
					<a
						onclick="delRecord('<%=info.get("TRIGGER_NAME_")%>')"
						href="#"> 删除 </a> 
						
				
						
						
						</td>

	<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.TRIGGER_NAME_">&nbsp;<%=info.get("TRIGGER_NAME_")%></td>

			
			
					
				<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.TRIGGER_STATE_">&nbsp;<%=info.get("TRIGGER_STATE_")%></td>
				
				
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.TRIGGER_STATE_CN_">&nbsp;<%=info.get("TRIGGER_STATE_CN_")%></td>




					
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.JOB_NAME_">&nbsp;<%=info.get("JOB_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.JOB_CLASS_NAME_">&nbsp;<%=info.get("JOB_CLASS_NAME_")%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.DESC_">&nbsp;<%=info.get("DESC_")%></td>
				

<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.CRON_EXPRESSION_">&nbsp;<%=info.get("CRON_EXPRESSION_")%></td>
	


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
		var url = "${pageContext.request.contextPath}/all/gen/sys_quartz_trigger/t/del_all.do";
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
			var url = "${pageContext.request.contextPath}/all/job/quartz/trigger/del.do?id="
					+ id;
			window.location.href = url;
		} else {
		}
	}
	
</script>