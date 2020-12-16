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
	action="${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_class_t/cx/save.do"
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
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_class_t.cx.alias.EduClassTCxAlias . id%></td>
		<td><input id="id_input$edu_class_t$id" type="text" name="edu_class_t.id"
			value="${requestScope.s.id}"></td>
		<td><span id="id_span$edu_class_t$id"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_class_t.cx.alias.EduClassTCxAlias . name%></td>
		<td><input id="id_input$edu_class_t$name" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_class_t.cx.alias.EduClassTCxAlias . name%>"  type="text" name="edu_class_t.name"
			value="${requestScope.s.name}">
		</td>
		<td><span id="id_span$edu_class_t$name"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_class_t.cx.alias.EduClassTCxAlias . licenseType%></td>
		<td><input id="id_input$edu_class_t$license_type" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_class_t.cx.alias.EduClassTCxAlias . licenseType%>"  type="text" name="edu_class_t.licenseType"
			value="${requestScope.s.licenseType}">
		</td>
		<td><span id="id_span$edu_class_t$license_type"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_class_t.cx.alias.EduClassTCxAlias . price%></td>
		<td><input id="id_input$edu_class_t$price" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_class_t.cx.alias.EduClassTCxAlias . price%>"  type="text" name="edu_class_t.price"
			value="${requestScope.s.price}">
		</td>
		<td><span id="id_span$edu_class_t$price"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_class_t.cx.alias.EduClassTCxAlias . timeType%></td>
		<td><input id="id_input$edu_class_t$time_type" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_class_t.cx.alias.EduClassTCxAlias . timeType%>"  type="text" name="edu_class_t.timeType"
			value="${requestScope.s.timeType}">
		</td>
		<td><span id="id_span$edu_class_t$time_type"></span>
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
var url = "${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_class_t/cx/list.do";
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
flag=ayCheckColumn("name","id_span$edu_class_t$name","id_input$edu_class_t$name","VARCHAR","no","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("驾证类型","id_span$edu_class_t$license_type","id_input$edu_class_t$license_type","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("价格以分为单位","id_span$edu_class_t$price","id_input$edu_class_t$price","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("学习周期类型","id_span$edu_class_t$time_type","id_input$edu_class_t$time_type","BIGINT UNSIGNED","yes","20","0","0",a,true);
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