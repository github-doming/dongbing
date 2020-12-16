<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<script type="text/javascript">
	
</script>
</head>
<body>
	<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>

	<form id="id_form_save"
		action="${pageContext.request.contextPath}/all/job/trigger/save.do"
		method="post">
		<table class="table  table-bordered table-hover" border="1">
			<tr>
				<th colspan="3">计划 <c:choose>
						<c:when test="${requestScope.id==null}">添加</c:when>
						<c:otherwise>修改</c:otherwise>
					</c:choose></th>
			</tr>
			<tr>
				<td colspan="3"><input onclick="back();" class="btn"
					type="button" value="返回列表"></input> <c:choose>
						<c:when test="${requestScope.id==null}">
							<input onclick='save();' class="btn" type="button" value="新增">
						</c:when>
						<c:otherwise>
							<input onclick='save();' class="btn" type="button" value="编辑">
						</c:otherwise>
					</c:choose></td>
			</tr>
		
				<tr style="display: none;">
				<td align="right"><%=all.job.alias.SysQuartzTriggerTAlias.sysQuartzTriggerId%></td>
				<td><input size=100
					id="id_input$sys_quartz_trigger$SYS_QUARTZ_TRIGGER_ID_"  type="text"
					name="sys_quartz_trigger.sysQuartzTriggerId"
					value="${requestScope.s.sysQuartzTriggerId}"></td>
				<td><span
					id="id_span$sys_quartz_trigger$SYS_QUARTZ_TRIGGER_ID_"></span></td>
			</tr>





			<tr>
				<td align="right"><%=all.job.alias.SysQuartzTriggerTAlias.sysTriggerName%></td>
				<td><input size="100"
					id="id_input$sys_quartz_trigger$SYS_TRIGGER_NAME_"
					placeholder="请输入<%=all.job.alias.SysQuartzTriggerTAlias . sysTriggerName%>"
					type="text" name="sys_quartz_trigger.sysTriggerName"
					value="${requestScope.s.sysTriggerName}"></td>
				<td><span id="id_span$sys_quartz_trigger$SYS_TRIGGER_NAME_"></span>
				</td>
			</tr>

			<tr>
				<td align="right"><%=all.job.alias.SysQuartzTriggerTAlias.jobClassName%></td>
				<td><input size=100
					id="id_input$sys_quartz_trigger$JOB_CLASS_NAME_"
					placeholder="请输入<%=all.job.alias.SysQuartzTriggerTAlias . jobClassName%>"
					type="text" name="sys_quartz_trigger.jobClassName"
					value="${requestScope.s.jobClassName}"></td>
				<td><span id="id_span$sys_quartz_trigger$JOB_CLASS_NAME_"></span>
				</td>
			</tr>



			<tr>
				<td align="right"><%=all.job.alias.SysQuartzTriggerTAlias.cronExpression%></td>
				<td><input size=100
					id="id_input$sys_quartz_trigger$CRON_EXPRESSION_"
					placeholder="请输入<%=all.job.alias.SysQuartzTriggerTAlias . cronExpression%>"
					type="text" name="sys_quartz_trigger.cronExpression"
					value="${requestScope.s.cronExpression}"></td>
				<td><span id="id_span$sys_quartz_trigger$CRON_EXPRESSION_"></span>
				</td>
			</tr>





			<tr>
				<td align="right"><%=all.job.alias.SysQuartzTriggerTAlias.jobName%></td>
				<td><input disabled="disabled" size=100
					id="id_input$sys_quartz_trigger$JOB_NAME_"
					placeholder="请输入<%=all.job.alias.SysQuartzTriggerTAlias . jobName%>"
					type="text" name="sys_quartz_trigger.jobName"
					value="${requestScope.s.jobName}"></td>
				<td><span id="id_span$sys_quartz_trigger$JOB_NAME_"></span></td>
			</tr>

			<tr>
				<td align="right"><%=all.job.alias.SysQuartzTriggerTAlias.triggerId%></td>
				<td><input disabled="disabled" size=100
					id="id_input$sys_quartz_trigger$TRIGGER_ID_"
					placeholder="请输入<%=all.job.alias.SysQuartzTriggerTAlias . triggerId%>"
					type="text" name="sys_quartz_trigger.triggerId"
					value="${requestScope.s.triggerId}"></td>
				<td><span id="id_span$sys_quartz_trigger$TRIGGER_ID_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.job.alias.SysQuartzTriggerTAlias.triggerName%></td>
				<td><input disabled="disabled" size=100
					id="id_input$sys_quartz_trigger$TRIGGER_NAME_"
					placeholder="请输入<%=all.job.alias.SysQuartzTriggerTAlias . triggerName%>"
					type="text" name="sys_quartz_trigger.triggerName"
					value="${requestScope.s.triggerName}"></td>
				<td><span id="id_span$sys_quartz_trigger$TRIGGER_NAME_"></span>
				</td>
			</tr>







			<tr>
				<td align="center" colspan="3"></td>
			</tr>
		</table>
	</form>
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">
	/**
	 * 
	 返回
	 */
	function back() {
		var url = "${pageContext.request.contextPath}/all/job/trigger/list.do";
		window.location.href = url;
	}
	/**
	 * 
	 检查
	 */
	function check() {
		var a = "<font color=red>自定义信息</font>";
		a = null;
		var flag = null;
		var return_flag = true;

		flag = ayCheckColumn("计划名称",
				"id_span$sys_quartz_trigger$SYS_TRIGGER_NAME_",
				"id_input$sys_quartz_trigger$SYS_TRIGGER_NAME_", "VARCHAR",
				"yes", "256", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}

		flag = ayCheckColumn("作业名称", "id_span$sys_quartz_trigger$JOB_NAME_",
				"id_input$sys_quartz_trigger$JOB_NAME_", "VARCHAR", "yes",
				"256", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}

		flag = ayCheckColumn("类名",
				"id_span$sys_quartz_trigger$JOB_CLASS_NAME_",
				"id_input$sys_quartz_trigger$JOB_CLASS_NAME_", "VARCHAR",
				"yes", "256", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}

		flag = ayCheckColumn("计划ID", "id_span$sys_quartz_trigger$TRIGGER_ID_",
				"id_input$sys_quartz_trigger$TRIGGER_ID_", "VARCHAR", "yes",
				"64", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}

		flag = ayCheckColumn("计划名称",
				"id_span$sys_quartz_trigger$TRIGGER_NAME_",
				"id_input$sys_quartz_trigger$TRIGGER_NAME_", "VARCHAR", "yes",
				"256", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}

		flag = ayCheckColumn("表达式",
				"id_span$sys_quartz_trigger$CRON_EXPRESSION_",
				"id_input$sys_quartz_trigger$CRON_EXPRESSION_", "VARCHAR",
				"yes", "256", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}

		return return_flag;
	}
	/**
	 * 
	 保存
	 */
	function save() {
		var flag = check();
		if (flag) {
		} else {
			return false;
		}
		//提交
		var obj_form = document.getElementById('id_form_save');
		obj_form.submit();
	}
</script>
<script type="text/javascript">
	//初始化日期
</script>