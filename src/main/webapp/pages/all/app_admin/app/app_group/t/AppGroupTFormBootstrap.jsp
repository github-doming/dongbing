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
	action="${pageContext.request.contextPath}/all/admin/app/app_group/t/save/bootstrap.do"
	method="post">
<table class="table  table-bordered table-hover" border="1">
	<tr>
		<th colspan="3">角色 <c:choose>
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
	
	
<tr  style="display: none;">
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . appGroupId%></td>
		<td><input id="id_input$app_group$APP_GROUP_ID_" type="text" name="app_group.appGroupId"
			value="${requestScope.s.appGroupId}"></td>
		<td><span id="id_span$app_group$APP_GROUP_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . appGroupName%></td>
		<td><input id="id_input$app_group$APP_GROUP_NAME_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias .appGroupName%>"  type="text" name="app_group.AppGroupName"
			value="${requestScope.s.appGroupName}">


		</td>
		<td><span id="id_span$app_group$APP_GROUP_NAME_"></span>
		</td>
</tr>


<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . appGroupCode%></td>
		<td><input id="id_input$app_group$APP_GROUP_CODE_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . appGroupCode%>"  type="text" name="app_group.AppGroupCode"
			value="${requestScope.s.appGroupCode}">


		</td>
		<td><span id="id_span$app_group$APP_GROUP_CODE_"></span>
		</td>
</tr>

<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . permissionGrade%></td>
		<td><input id="id_input$app_group$PERMISSION_GRADE_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . permissionGrade%>"  type="text" name="app_group.permissionGrade"
			value="${requestScope.s.permissionGrade}">


		</td>
		<td><span id="id_span$app_group$PERMISSION_GRADE_"></span>
		</td>
</tr>	
	<!-- 
<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . idx%></td>
		<td><input id="id_input$app_group$IDX_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . idx%>"  type="text" name="app_group.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$app_group$IDX_"></span>
		</td>
</tr>	

	
	
<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . appGroupType%></td>
		<td><input id="id_input$app_group$GROUP_TYPE_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias .appGroupType%>"  type="text" name="app_group.appGroupType"
			value="${requestScope.s.appGroupType}">


		</td>
		<td><span id="id_span$app_group$APP_GROUP_TYPE_"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . createUser%></td>
		<td><input id="id_input$app_group$CREATE_USER_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . createUser%>"  type="text" name="app_group.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="id_span$app_group$CREATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . createTime%></td>
		<td><input id="id_input$app_group$CREATE_TIME_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . createTime%>"  type="text" name="app_group.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$app_group$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_group$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . createTimeLong%></td>
		<td><input id="id_input$app_group$CREATE_TIME_LONG_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . createTimeLong%>"  type="text" name="app_group.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$app_group$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . updateUser%></td>
		<td><input id="id_input$app_group$UPDATE_USER_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . updateUser%>"  type="text" name="app_group.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="id_span$app_group$UPDATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . updateTime%></td>
		<td><input id="id_input$app_group$UPDATE_TIME_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . updateTime%>"  type="text" name="app_group.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$app_group$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_group$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . updateTimeLong%></td>
		<td><input id="id_input$app_group$UPDATE_TIME_LONG_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . updateTimeLong%>"  type="text" name="app_group.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$app_group$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . state%></td>
		<td><input id="id_input$app_group$STATE_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . state%>"  type="text" name="app_group.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$app_group$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . desc%></td>
		<td><input id="id_input$app_group$DESC_" placeholder="请输入<%=all.app_admin.app.app_group.t.alias.AppGroupTAlias . desc%>"  type="text" name="app_group.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$app_group$DESC_"></span>
		</td>
</tr>	

-->
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
var url = "${pageContext.request.contextPath}/all/admin/app/app_group/t/list/bootstrap.do";
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

flag=ayCheckColumn("用户组名称","id_span$app_group$APP_GROUP_NAME_","id_input$app_group$APP_GROUP_NAME_","VARCHAR","yes","256","0","0",a,true);
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