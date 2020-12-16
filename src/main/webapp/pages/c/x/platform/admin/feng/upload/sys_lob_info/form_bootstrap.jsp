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
	action="${pageContext.request.contextPath}/cxy/simple/cxy_simple_upload/admin/upload/sys_lob_info/save.do"
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
		<td align="right"><%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . id%></td>
		<td><input id="id_input$sys_lob_info$id" type="text" name="sys_lob_info.id"
			value="${requestScope.id}"></td>
		<td><span id="id_span$sys_lob_info$id"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . file_name%></td>
		<td><input id="id_input$sys_lob_info$file_name" placeholder="请输入<%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . file_name%>"  type="text" name="sys_lob_info.file_name"
			value="${requestScope.s.file_name}">
		</td>
		<td><span id="id_span$sys_lob_info$file_name"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . file%></td>
		<td><input id="id_input$sys_lob_info$file" placeholder="请输入<%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . file%>"  type="text" name="sys_lob_info.file"
			value="${requestScope.s.file}">
		</td>
		<td><span id="id_span$sys_lob_info$file"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . user_id%></td>
		<td><input id="id_input$sys_lob_info$user_id" placeholder="请输入<%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . user_id%>"  type="text" name="sys_lob_info.user_id"
			value="${requestScope.s.user_id}">
		</td>
		<td><span id="id_span$sys_lob_info$user_id"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . create_time%></td>
		<td><input id="id_input$sys_lob_info$create_time" placeholder="请输入<%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . create_time%>"  type="text" name="sys_lob_info.create_time"
			value="${requestScope.s.create_time}">
<img
onclick=ayCalendarShow('id_input$sys_lob_info$create_time',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$sys_lob_info$create_time"></span>
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
var url = "${pageContext.request.contextPath}/cxy/simple/cxy_simple_upload/admin/upload/sys_lob_info/list/bootstrap.do";
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
flag=ayCheckColumn("文件名","id_span$sys_lob_info$file_name","id_input$sys_lob_info$file_name","VARCHAR","no","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("文件","id_span$sys_lob_info$file","id_input$sys_lob_info$file","LONGBLOB","yes","2147483647","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("用户ID","id_span$sys_lob_info$user_id","id_input$sys_lob_info$user_id","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("create_time","id_span$sys_lob_info$create_time","id_input$sys_lob_info$create_time","DATETIME","yes","19","0","0",a,true);
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
ayCalendarNow('id_input$sys_lob_info$create_time','DATETIME');
</script>