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
	action="${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_place_t/cx/save.do"
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
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . id%></td>
		<td><input id="id_input$edu_place_t$id" type="text" name="edu_place_t.id"
			value="${requestScope.s.id}"></td>
		<td><span id="id_span$edu_place_t$id"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . name%></td>
		<td><input id="id_input$edu_place_t$name" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . name%>"  type="text" name="edu_place_t.name"
			value="${requestScope.s.name}">
		</td>
		<td><span id="id_span$edu_place_t$name"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . address%></td>
		<td><input id="id_input$edu_place_t$address" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . address%>"  type="text" name="edu_place_t.address"
			value="${requestScope.s.address}">
		</td>
		<td><span id="id_span$edu_place_t$address"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . description%></td>
		<td><input id="id_input$edu_place_t$description" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . description%>"  type="text" name="edu_place_t.description"
			value="${requestScope.s.description}">
		</td>
		<td><span id="id_span$edu_place_t$description"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . timeDescription%></td>
		<td><input id="id_input$edu_place_t$time_description" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . timeDescription%>"  type="text" name="edu_place_t.timeDescription"
			value="${requestScope.s.timeDescription}">
		</td>
		<td><span id="id_span$edu_place_t$time_description"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . square%></td>
		<td><input id="id_input$edu_place_t$square" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . square%>"  type="text" name="edu_place_t.square"
			value="${requestScope.s.square}">
		</td>
		<td><span id="id_span$edu_place_t$square"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . maxStudent%></td>
		<td><input id="id_input$edu_place_t$max_student" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . maxStudent%>"  type="text" name="edu_place_t.maxStudent"
			value="${requestScope.s.maxStudent}">
		</td>
		<td><span id="id_span$edu_place_t$max_student"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . maxTeacher%></td>
		<td><input id="id_input$edu_place_t$max_teacher" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . maxTeacher%>"  type="text" name="edu_place_t.maxTeacher"
			value="${requestScope.s.maxTeacher}">
		</td>
		<td><span id="id_span$edu_place_t$max_teacher"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . maxAutomoblie%></td>
		<td><input id="id_input$edu_place_t$max_automoblie" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . maxAutomoblie%>"  type="text" name="edu_place_t.maxAutomoblie"
			value="${requestScope.s.maxAutomoblie}">
		</td>
		<td><span id="id_span$edu_place_t$max_automoblie"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . timeType%></td>
		<td><input id="id_input$edu_place_t$time_type" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_place_t.cx.alias.EduPlaceTCxAlias . timeType%>"  type="text" name="edu_place_t.timeType"
			value="${requestScope.s.timeType}">
		</td>
		<td><span id="id_span$edu_place_t$time_type"></span>
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
var url = "${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_place_t/cx/list.do";
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
flag=ayCheckColumn("name","id_span$edu_place_t$name","id_input$edu_place_t$name","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("address","id_span$edu_place_t$address","id_input$edu_place_t$address","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("description","id_span$edu_place_t$description","id_input$edu_place_t$description","VARCHAR","yes","1000","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("学习周期描述","id_span$edu_place_t$time_description","id_input$edu_place_t$time_description","VARCHAR","yes","1000","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("面积（米）","id_span$edu_place_t$square","id_input$edu_place_t$square","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("max_student","id_span$edu_place_t$max_student","id_input$edu_place_t$max_student","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("max_teacher","id_span$edu_place_t$max_teacher","id_input$edu_place_t$max_teacher","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("max_automoblie","id_span$edu_place_t$max_automoblie","id_input$edu_place_t$max_automoblie","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("学习周期类型","id_span$edu_place_t$time_type","id_input$edu_place_t$time_type","BIGINT UNSIGNED","yes","20","0","0",a,true);
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