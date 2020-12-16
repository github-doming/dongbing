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
		action="${pageContext.request.contextPath}/all/job/config/save.do"
		method="post">
		<table class="table  table-bordered table-hover" border="1">
			<tr>
				<th colspan="3">菜单 <c:choose>
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


			<tr>
				<td align="right"><%=all.job.alias.SysQuartzConfigTAlias.sysQuartzConfigId%></td>
				<td><input
					id="id_input$sys_quartz_config$SYS_QUARTZ_CONFIG_ID_" type="text"
					name="sys_quartz_config.sysQuartzConfigId"
					value="${requestScope.s.sysQuartzConfigId}"></td>
				<td><span id="id_span$sys_quartz_config$SYS_QUARTZ_CONFIG_ID_"></span>
				</td>
			</tr>
			<tr>
				<td align="right"><%=all.job.alias.SysQuartzConfigTAlias.started%></td>
				<td><input id="id_input$sys_quartz_config$STARTED_"
					placeholder="请输入<%=all.job.alias.SysQuartzConfigTAlias . started%>"
					type="text" name="sys_quartz_config.started"
					value="${requestScope.s.started}"></td>
				<td><span id="id_span$sys_quartz_config$STARTED_"></span></td>
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
		var url = "${pageContext.request.contextPath}/all/job/config/list.do";
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
		flag = ayCheckColumn("是否启动STARTED_",
				"id_span$sys_quartz_config$STARTED_",
				"id_input$sys_quartz_config$STARTED_", "CHAR", "yes", "8", "0",
				"0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}

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