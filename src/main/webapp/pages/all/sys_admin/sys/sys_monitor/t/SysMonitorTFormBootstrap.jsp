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
	action="${pageContext.request.contextPath}/all/sys_admin/sys/sys_monitor/t/save/bootstrap.do"
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
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . idx%></td>
		<td><input id="id_input$sys_monitor$IDX_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . idx%>"  type="text" name="sys_monitor.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$sys_monitor$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . sysMonitorId%></td>
		<td><input id="id_input$sys_monitor$SYS_MONITOR_ID_" type="text" name="sys_monitor.sysMonitorId"
			value="${requestScope.s.sysMonitorId}"></td>
		<td><span id="id_span$sys_monitor$SYS_MONITOR_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . os%></td>
		<td><input id="id_input$sys_monitor$OS_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . os%>"  type="text" name="sys_monitor.os"
			value="${requestScope.s.os}">


		</td>
		<td><span id="id_span$sys_monitor$OS_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . pid%></td>
		<td><input id="id_input$sys_monitor$PID_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . pid%>"  type="text" name="sys_monitor.pid"
			value="${requestScope.s.pid}">


		</td>
		<td><span id="id_span$sys_monitor$PID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . threadCount%></td>
		<td><input id="id_input$sys_monitor$THREAD_COUNT_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . threadCount%>"  type="text" name="sys_monitor.threadCount"
			value="${requestScope.s.threadCount}">


		</td>
		<td><span id="id_span$sys_monitor$THREAD_COUNT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . cpuRate%></td>
		<td><input id="id_input$sys_monitor$CPU_RATE_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . cpuRate%>"  type="text" name="sys_monitor.cpuRate"
			value="${requestScope.s.cpuRate}">


		</td>
		<td><span id="id_span$sys_monitor$CPU_RATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryTotal%></td>
		<td><input id="id_input$sys_monitor$MEMORY_TOTAL_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryTotal%>"  type="text" name="sys_monitor.memoryTotal"
			value="${requestScope.s.memoryTotal}">


		</td>
		<td><span id="id_span$sys_monitor$MEMORY_TOTAL_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryJvm%></td>
		<td><input id="id_input$sys_monitor$MEMORY_JVM_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryJvm%>"  type="text" name="sys_monitor.memoryJvm"
			value="${requestScope.s.memoryJvm}">


		</td>
		<td><span id="id_span$sys_monitor$MEMORY_JVM_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryRate%></td>
		<td><input id="id_input$sys_monitor$MEMORY_RATE_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . memoryRate%>"  type="text" name="sys_monitor.memoryRate"
			value="${requestScope.s.memoryRate}">


		</td>
		<td><span id="id_span$sys_monitor$MEMORY_RATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . upSpeed%></td>
		<td><input id="id_input$sys_monitor$UP_SPEED_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . upSpeed%>"  type="text" name="sys_monitor.upSpeed"
			value="${requestScope.s.upSpeed}">


		</td>
		<td><span id="id_span$sys_monitor$UP_SPEED_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . downSpeed%></td>
		<td><input id="id_input$sys_monitor$DOWN_SPEED_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . downSpeed%>"  type="text" name="sys_monitor.downSpeed"
			value="${requestScope.s.downSpeed}">


		</td>
		<td><span id="id_span$sys_monitor$DOWN_SPEED_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . createTime%></td>
		<td><input id="id_input$sys_monitor$CREATE_TIME_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . createTime%>"  type="text" name="sys_monitor.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$sys_monitor$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$sys_monitor$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . createTimeLong%></td>
		<td><input id="id_input$sys_monitor$CREATE_TIME_LONG_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . createTimeLong%>"  type="text" name="sys_monitor.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$sys_monitor$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . updateTime%></td>
		<td><input id="id_input$sys_monitor$UPDATE_TIME_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . updateTime%>"  type="text" name="sys_monitor.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$sys_monitor$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$sys_monitor$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . updateTimeLong%></td>
		<td><input id="id_input$sys_monitor$UPDATE_TIME_LONG_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . updateTimeLong%>"  type="text" name="sys_monitor.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$sys_monitor$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . state%></td>
		<td><input id="id_input$sys_monitor$STATE_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . state%>"  type="text" name="sys_monitor.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$sys_monitor$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . desc%></td>
		<td><input id="id_input$sys_monitor$DESC_" placeholder="请输入<%=all.sys_admin.sys.sys_monitor.t.alias.SysMonitorTAlias . desc%>"  type="text" name="sys_monitor.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$sys_monitor$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/sys_admin/sys/sys_monitor/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$sys_monitor$IDX_","id_input$sys_monitor$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("操作系统OS_","id_span$sys_monitor$OS_","id_input$sys_monitor$OS_","VARCHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("PID_","id_span$sys_monitor$PID_","id_input$sys_monitor$PID_","VARCHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("JVM 线程数THREAD_COUNT_","id_span$sys_monitor$THREAD_COUNT_","id_input$sys_monitor$THREAD_COUNT_","VARCHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CPU使用率CPU_RATE_","id_span$sys_monitor$CPU_RATE_","id_input$sys_monitor$CPU_RATE_","VARCHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("物理内存总数MEMORY_TOTAL_","id_span$sys_monitor$MEMORY_TOTAL_","id_input$sys_monitor$MEMORY_TOTAL_","VARCHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("JVM内存使用MEMORY_JVM_","id_span$sys_monitor$MEMORY_JVM_","id_input$sys_monitor$MEMORY_JVM_","VARCHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内存占用率MEMORY_RATE_","id_span$sys_monitor$MEMORY_RATE_","id_input$sys_monitor$MEMORY_RATE_","VARCHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("上行速度UP_SPEED_","id_span$sys_monitor$UP_SPEED_","id_input$sys_monitor$UP_SPEED_","VARCHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("下行速度DOWN_SPEED_","id_span$sys_monitor$DOWN_SPEED_","id_input$sys_monitor$DOWN_SPEED_","VARCHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","id_span$sys_monitor$CREATE_TIME_","id_input$sys_monitor$CREATE_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","id_span$sys_monitor$CREATE_TIME_LONG_","id_input$sys_monitor$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$sys_monitor$UPDATE_TIME_","id_input$sys_monitor$UPDATE_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$sys_monitor$UPDATE_TIME_LONG_","id_input$sys_monitor$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$sys_monitor$STATE_","id_input$sys_monitor$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$sys_monitor$DESC_","id_input$sys_monitor$DESC_","VARCHAR","yes","512","0","0",a,true);
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