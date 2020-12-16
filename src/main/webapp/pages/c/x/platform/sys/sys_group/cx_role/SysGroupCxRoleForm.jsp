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
	action="${pageContext.request.contextPath}/c/x/platform/sys/sys_group/cx_role/save.do"
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
<tr style="display: none;" >
		<td align="right"><%=c.x.platform.sys.sys_group.cx_role.alias.SysGroupCxRoleAlias . sysGroupId%></td>
		<td><input id="id_input$sys_group$SYS_GROUP_ID_" type="text" name="sys_group.sysGroupId"
			value="${requestScope.s.sysGroupId}"></td>
		<td><span id="id_span$sys_group$SYS_GROUP_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.sys.sys_group.cx_role.alias.SysGroupCxRoleAlias . sysGroupName%></td>
		<td><input id="id_input$sys_group$SYS_GROUP_NAME_" placeholder="请输入<%=c.x.platform.sys.sys_group.cx_role.alias.SysGroupCxRoleAlias . sysGroupName%>"  type="text" name="sys_group.sysGroupName"
			value="${requestScope.s.sysGroupName}">
		</td>
		<td><span id="id_span$sys_group$SYS_GROUP_NAME_"></span>
		</td>
</tr>

<tr>
		<td align="right"><%=c.x.platform.sys.sys_group.cx_role.alias.SysGroupCxRoleAlias . sysGroupCode%></td>
		<td><input id="id_input$sys_group$SYS_GROUP_CODE_" placeholder="请输入<%=c.x.platform.sys.sys_group.cx_role.alias.SysGroupCxRoleAlias . sysGroupCode%>"  type="text" name="sys_group.sysGroupCode"
			value="${requestScope.s.sysGroupCode}">


		</td>
		<td><span id="id_span$sys_group$SYS_GROUP_CODE_"></span>
		</td>
</tr>

	
<tr>
		<td align="right"><%=c.x.platform.sys.sys_group.cx_role.alias.SysGroupCxRoleAlias . permissionGrade%></td>
		<td><input id="id_input$sys_group$PERMISSION_GRADE_" placeholder="请输入<%=c.x.platform.sys.sys_group.cx_role.alias.SysGroupCxRoleAlias . permissionGrade%>"  type="text" name="sys_group.permissionGrade"
			value="${requestScope.s.permissionGrade}">
		</td>
		<td><span id="id_span$sys_group$PERMISSION_GRADE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.sys.sys_group.cx_role.alias.SysGroupCxRoleAlias . sysGroupType%></td>
		<td><input id="id_input$sys_group$SYS_GROUP_TYPE_" placeholder="请输入<%=c.x.platform.sys.sys_group.cx_role.alias.SysGroupCxRoleAlias . sysGroupType%>"  type="text" name="sys_group.sysGroupType"
			value="${requestScope.s.sysGroupType}">
		</td>
		<td><span id="id_span$sys_group$SYS_GROUP_TYPE_"></span>
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
var url = "${pageContext.request.contextPath}/c/x/platform/sys/sys_group/cx_role/list.do";
window.location.href=url;
}
/**
 * 
 检查
 */
function check(){
	var a="<font color=red>自定义信息</font>";
	a=null;
	var flag=null;
	var return_flag=true;
flag=ayCheckColumn("名称","id_span$sys_group$SYS_GROUP_NAME_","id_input$sys_group$SYS_GROUP_NAME_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("权限等级","id_span$sys_group$PERMISSION_GRADE_","id_input$sys_group$PERMISSION_GRADE_","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("TYPE_","id_span$sys_group$SYS_GROUP_TYPE_","id_input$sys_group$SYS_GROUP_TYPE_","VARCHAR","yes","64","0","0",a,true);
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