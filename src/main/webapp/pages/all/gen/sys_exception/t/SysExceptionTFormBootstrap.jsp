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
	action="${pageContext.request.contextPath}/all/gen/sys_exception/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . id%></td>
		<td><input id="id_input$sys_exception$ID_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . id%>"  type="text" name="sys_exception.id"
			value="${requestScope.s.id}">


		</td>
		<td><span id="id_span$sys_exception$ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysExceptionId%></td>
		<td><input id="id_input$sys_exception$SYS_EXCEPTION_ID_" type="text" name="sys_exception.sysExceptionId"
			value="${requestScope.s.sysExceptionId}"></td>
		<td><span id="id_span$sys_exception$SYS_EXCEPTION_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . name%></td>
		<td><input id="id_input$sys_exception$NAME_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . name%>"  type="text" name="sys_exception.name"
			value="${requestScope.s.name}">


		</td>
		<td><span id="id_span$sys_exception$NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . exceptionClass%></td>
		<td><input id="id_input$sys_exception$EXCEPTION_CLASS_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . exceptionClass%>"  type="text" name="sys_exception.exceptionClass"
			value="${requestScope.s.exceptionClass}">


		</td>
		<td><span id="id_span$sys_exception$EXCEPTION_CLASS_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . bizClass%></td>
		<td><input id="id_input$sys_exception$BIZ_CLASS_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . bizClass%>"  type="text" name="sys_exception.bizClass"
			value="${requestScope.s.bizClass}">


		</td>
		<td><span id="id_span$sys_exception$BIZ_CLASS_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . methodName%></td>
		<td><input id="id_input$sys_exception$METHOD_NAME_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . methodName%>"  type="text" name="sys_exception.methodName"
			value="${requestScope.s.methodName}">


		</td>
		<td><span id="id_span$sys_exception$METHOD_NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . lineNumber%></td>
		<td><input id="id_input$sys_exception$LINE_NUMBER_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . lineNumber%>"  type="text" name="sys_exception.lineNumber"
			value="${requestScope.s.lineNumber}">


		</td>
		<td><span id="id_span$sys_exception$LINE_NUMBER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . msg%></td>
		<td><input id="id_input$sys_exception$MSG_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . msg%>"  type="text" name="sys_exception.msg"
			value="${requestScope.s.msg}">


		</td>
		<td><span id="id_span$sys_exception$MSG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . servletPath%></td>
		<td><input id="id_input$sys_exception$SERVLET_PATH_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . servletPath%>"  type="text" name="sys_exception.servletPath"
			value="${requestScope.s.servletPath}">


		</td>
		<td><span id="id_span$sys_exception$SERVLET_PATH_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysUserId%></td>
		<td><input id="id_input$sys_exception$SYS_USER_ID_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysUserId%>"  type="text" name="sys_exception.sysUserId"
			value="${requestScope.s.sysUserId}">


		</td>
		<td><span id="id_span$sys_exception$SYS_USER_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysUserName%></td>
		<td><input id="id_input$sys_exception$SYS_USER_NAME_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . sysUserName%>"  type="text" name="sys_exception.sysUserName"
			value="${requestScope.s.sysUserName}">


		</td>
		<td><span id="id_span$sys_exception$SYS_USER_NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createUser%></td>
		<td><input id="id_input$sys_exception$CREATE_USER_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createUser%>"  type="text" name="sys_exception.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="id_span$sys_exception$CREATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createTime%></td>
		<td><input id="id_input$sys_exception$CREATE_TIME_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createTime%>"  type="text" name="sys_exception.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$sys_exception$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$sys_exception$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createTimeLong%></td>
		<td><input id="id_input$sys_exception$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . createTimeLong%>"  type="text" name="sys_exception.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$sys_exception$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateUser%></td>
		<td><input id="id_input$sys_exception$UPDATE_USER_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateUser%>"  type="text" name="sys_exception.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="id_span$sys_exception$UPDATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateTime%></td>
		<td><input id="id_input$sys_exception$UPDATE_TIME_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateTime%>"  type="text" name="sys_exception.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$sys_exception$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$sys_exception$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateTimeLong%></td>
		<td><input id="id_input$sys_exception$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . updateTimeLong%>"  type="text" name="sys_exception.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$sys_exception$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . state%></td>
		<td><input id="id_input$sys_exception$STATE_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . state%>"  type="text" name="sys_exception.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$sys_exception$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . code%></td>
		<td><input id="id_input$sys_exception$CODE_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . code%>"  type="text" name="sys_exception.code"
			value="${requestScope.s.code}">


		</td>
		<td><span id="id_span$sys_exception$CODE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . ab%></td>
		<td><input id="id_input$sys_exception$AB_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . ab%>"  type="text" name="sys_exception.ab"
			value="${requestScope.s.ab}">


		</td>
		<td><span id="id_span$sys_exception$AB_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_exception.t.alias.SysExceptionTAlias . desc%></td>
		<td><input id="id_input$sys_exception$DESC_" placeholder="请输入<%=all.gen.sys_exception.t.alias.SysExceptionTAlias . desc%>"  type="text" name="sys_exception.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$sys_exception$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/gen/sys_exception/t/list/bootstrap.do";
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
flag=ayCheckColumn("主键","id_span$sys_exception$ID_","id_input$sys_exception$ID_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("名称","id_span$sys_exception$NAME_","id_input$sys_exception$NAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("EXCEPTION_CLASS_","id_span$sys_exception$EXCEPTION_CLASS_","id_input$sys_exception$EXCEPTION_CLASS_","VARCHAR","no","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("BIZ_CLASS_","id_span$sys_exception$BIZ_CLASS_","id_input$sys_exception$BIZ_CLASS_","VARCHAR","no","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("METHOD_NAME_","id_span$sys_exception$METHOD_NAME_","id_input$sys_exception$METHOD_NAME_","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("LINE_NUMBER_","id_span$sys_exception$LINE_NUMBER_","id_input$sys_exception$LINE_NUMBER_","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("MSG_","id_span$sys_exception$MSG_","id_input$sys_exception$MSG_","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("SERVLET_PATH_","id_span$sys_exception$SERVLET_PATH_","id_input$sys_exception$SERVLET_PATH_","VARCHAR","no","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("SYS_USER_ID_","id_span$sys_exception$SYS_USER_ID_","id_input$sys_exception$SYS_USER_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("SYS_USER_NAME_","id_span$sys_exception$SYS_USER_NAME_","id_input$sys_exception$SYS_USER_NAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者CREATE_USER_","id_span$sys_exception$CREATE_USER_","id_input$sys_exception$CREATE_USER_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","id_span$sys_exception$CREATE_TIME_","id_input$sys_exception$CREATE_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","id_span$sys_exception$CREATE_TIME_LONG_","id_input$sys_exception$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","id_span$sys_exception$UPDATE_USER_","id_input$sys_exception$UPDATE_USER_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$sys_exception$UPDATE_TIME_","id_input$sys_exception$UPDATE_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$sys_exception$UPDATE_TIME_LONG_","id_input$sys_exception$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$sys_exception$STATE_","id_input$sys_exception$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("编码CODE_","id_span$sys_exception$CODE_","id_input$sys_exception$CODE_","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("名称缩写AB_","id_span$sys_exception$AB_","id_input$sys_exception$AB_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$sys_exception$DESC_","id_input$sys_exception$DESC_","VARCHAR","yes","512","0","0",a,true);
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