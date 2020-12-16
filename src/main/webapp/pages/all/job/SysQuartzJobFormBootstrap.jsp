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
		action="${pageContext.request.contextPath}/all/job/job/save.do"
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
				<td align="right"><%=all.job.alias.SysQuartzJobTAlias.sysQuartzJobId%></td>
				<td><input id="id_input$sys_quartz_job$SYS_QUARTZ_JOB_ID_"
					type="text" name="sys_quartz_job.sysQuartzJobId"
					value="${requestScope.s.sysQuartzJobId}"></td>
				<td><span id="id_span$sys_quartz_job$SYS_QUARTZ_JOB_ID_"></span>
				</td>
			</tr>


			<tr>
		<td align="right"><%=all.job.alias.SysQuartzJobTAlias . sysJobName%></td>
		<td><input id="id_input$sys_quartz_job$SYS_JOB_NAME_" placeholder="请输入<%=all.job.alias.SysQuartzJobTAlias . sysJobName%>"  type="text" name="sys_quartz_job.sysJobName"
			value="${requestScope.s.sysJobName}">


		</td>
		<td><span id="id_span$sys_quartz_job$SYS_JOB_NAME_"></span>
		</td>
</tr>	

			<tr>
				<td align="right"><%=all.job.alias.SysQuartzJobTAlias.jobClassName%></td>
				<td><input id="id_input$sys_quartz_job$JOB_CLASS_NAME_"
					placeholder="请输入<%=all.job.alias.SysQuartzJobTAlias . jobClassName%>"
					type="text" name="sys_quartz_job.jobClassName"
					value="${requestScope.s.jobClassName}"></td>
				<td><span id="id_span$sys_quartz_job$JOB_CLASS_NAME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.job.alias.SysQuartzJobTAlias.json%></td>
				<td><input id="id_input$sys_quartz_job$JSON_"
					placeholder="请输入<%=all.job.alias.SysQuartzJobTAlias . json%>"
					type="text" name="sys_quartz_job.json"
					value="${requestScope.s.json}"></td>
				<td><span id="id_span$sys_quartz_job$JSON_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.job.alias.SysQuartzJobTAlias.state%></td>
				<td><input id="id_input$sys_quartz_job$STATE_"
					placeholder="请输入<%=all.job.alias.SysQuartzJobTAlias . state%>"
					type="text" name="sys_quartz_job.stateDesc"
					value="${requestScope.s.state}"></td>
				<td><span id="id_span$sys_quartz_job$STATE_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.job.alias.SysQuartzJobTAlias.createTime%></td>
				<td><input id="id_input$sys_quartz_job$CREATE_TIME_"
					placeholder="请输入<%=all.job.alias.SysQuartzJobTAlias . createTime%>"
					type="text" name="sys_quartz_job.createTime"
					value="${requestScope.s.createTime}"> <img
					onclick=ayCalendarShow(
					'id_input$sys_quartz_job$CREATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
				</td>
				<td><span id="id_span$sys_quartz_job$CREATE_TIME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.job.alias.SysQuartzJobTAlias.createTimeLong%></td>
				<td><input id="id_input$sys_quartz_job$CREATE_TIME_LONG_"
					placeholder="请输入<%=all.job.alias.SysQuartzJobTAlias . createTimeLong%>"
					type="text" name="sys_quartz_job.createTimeLong"
					value="${requestScope.s.createTimeLong}"></td>
				<td><span id="id_span$sys_quartz_job$CREATE_TIME_LONG_"></span>
				</td>
			</tr>
			<tr>
				<td align="right"><%=all.job.alias.SysQuartzJobTAlias.updateTime%></td>
				<td><input id="id_input$sys_quartz_job$UPDATE_TIME_"
					placeholder="请输入<%=all.job.alias.SysQuartzJobTAlias . updateTime%>"
					type="text" name="sys_quartz_job.updateTime"
					value="${requestScope.s.updateTime}"> <img
					onclick=ayCalendarShow(
					'id_input$sys_quartz_job$UPDATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
				</td>
				<td><span id="id_span$sys_quartz_job$UPDATE_TIME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.job.alias.SysQuartzJobTAlias.updateTimeLong%></td>
				<td><input id="id_input$sys_quartz_job$UPDATE_TIME_LONG_"
					placeholder="请输入<%=all.job.alias.SysQuartzJobTAlias . updateTimeLong%>"
					type="text" name="sys_quartz_job.updateTimeLong"
					value="${requestScope.s.updateTimeLong}"></td>
				<td><span id="id_span$sys_quartz_job$UPDATE_TIME_LONG_"></span>
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
function back(){
var url = "${pageContext.request.contextPath}/all/job/job/list.do";
window.location.href=url;
}
/**
 * 
 检查
 */
function check(){
	var a="<font color=red>自定义信息</font>";
	a=null;
	var flag=true;
	var return_flag=true;
	




	flag=ayCheckColumn("作业名称","id_span$sys_quartz_job$SYS_JOB_NAME_","id_input$sys_quartz_job$SYS_JOB_NAME_","VARCHAR","yes","256","0","0",a,true);
	if(flag){}else{return_flag=false;}




flag=ayCheckColumn("类名","id_span$sys_quartz_job$JOB_CLASS_NAME_","id_input$sys_quartz_job$JOB_CLASS_NAME_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("JSON_","id_span$sys_quartz_job$JSON_","id_input$sys_quartz_job$JSON_","VARCHAR","yes","8192","0","0",a,true);
if(flag){}else{return_flag=false;}


flag=ayCheckColumn("创建时间","id_span$sys_quartz_job$CREATE_TIME_","id_input$sys_quartz_job$CREATE_TIME_","DATETIME","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","id_span$sys_quartz_job$CREATE_TIME_LONG_","id_input$sys_quartz_job$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$sys_quartz_job$UPDATE_TIME_","id_input$sys_quartz_job$UPDATE_TIME_","DATETIME","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$sys_quartz_job$UPDATE_TIME_LONG_","id_input$sys_quartz_job$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
	return return_flag;
}
/**
 * 
 保存
 */
function save(){
var flag=check();
if(flag){
}else{
	return false;
}
	   //提交
	var obj_form= document.getElementById('id_form_save');
obj_form.submit();
 }
</script>
<script type="text/javascript">
//初始化日期
</script>