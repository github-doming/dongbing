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
	action="${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_search_t/cx/save.do"
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
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . id%></td>
		<td><input id="id_input$edu_search_t$id" type="text" name="edu_search_t.id"
			value="${requestScope.s.id}"></td>
		<td><span id="id_span$edu_search_t$id"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . name%></td>
		<td><input id="id_input$edu_search_t$name" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . name%>"  type="text" name="edu_search_t.name"
			value="${requestScope.s.name}">
		</td>
		<td><span id="id_span$edu_search_t$name"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . address%></td>
		<td><input id="id_input$edu_search_t$address" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . address%>"  type="text" name="edu_search_t.address"
			value="${requestScope.s.address}">
		</td>
		<td><span id="id_span$edu_search_t$address"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . licenseType%></td>
		<td><input id="id_input$edu_search_t$license_type" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . licenseType%>"  type="text" name="edu_search_t.licenseType"
			value="${requestScope.s.licenseType}">
		</td>
		<td><span id="id_span$edu_search_t$license_type"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . price%></td>
		<td><input id="id_input$edu_search_t$price" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . price%>"  type="text" name="edu_search_t.price"
			value="${requestScope.s.price}">
		</td>
		<td><span id="id_span$edu_search_t$price"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . timeType%></td>
		<td><input id="id_input$edu_search_t$time_type" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . timeType%>"  type="text" name="edu_search_t.timeType"
			value="${requestScope.s.timeType}">
		</td>
		<td><span id="id_span$edu_search_t$time_type"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . score%></td>
		<td><input id="id_input$edu_search_t$score" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . score%>"  type="text" name="edu_search_t.score"
			value="${requestScope.s.score}">
		</td>
		<td><span id="id_span$edu_search_t$score"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . eduClassId%></td>
		<td><input id="id_input$edu_search_t$edu_class_id" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . eduClassId%>"  type="text" name="edu_search_t.eduClassId"
			value="${requestScope.s.eduClassId}">
		</td>
		<td><span id="id_span$edu_search_t$edu_class_id"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . eduTeacherId%></td>
		<td><input id="id_input$edu_search_t$edu_teacher_id" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . eduTeacherId%>"  type="text" name="edu_search_t.eduTeacherId"
			value="${requestScope.s.eduTeacherId}">
		</td>
		<td><span id="id_span$edu_search_t$edu_teacher_id"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . uid%></td>
		<td><input id="id_input$edu_search_t$uid" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . uid%>"  type="text" name="edu_search_t.uid"
			value="${requestScope.s.uid}">
		</td>
		<td><span id="id_span$edu_search_t$uid"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . distance%></td>
		<td><input id="id_input$edu_search_t$distance" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . distance%>"  type="text" name="edu_search_t.distance"
			value="${requestScope.s.distance}">
		</td>
		<td><span id="id_span$edu_search_t$distance"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . lat%></td>
		<td><input id="id_input$edu_search_t$lat" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . lat%>"  type="text" name="edu_search_t.lat"
			value="${requestScope.s.lat}">
		</td>
		<td><span id="id_span$edu_search_t$lat"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . lng%></td>
		<td><input id="id_input$edu_search_t$lng" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . lng%>"  type="text" name="edu_search_t.lng"
			value="${requestScope.s.lng}">
		</td>
		<td><span id="id_span$edu_search_t$lng"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . description%></td>
		<td><input id="id_input$edu_search_t$description" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . description%>"  type="text" name="edu_search_t.description"
			value="${requestScope.s.description}">
		</td>
		<td><span id="id_span$edu_search_t$description"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . streetId%></td>
		<td><input id="id_input$edu_search_t$street_id" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . streetId%>"  type="text" name="edu_search_t.streetId"
			value="${requestScope.s.streetId}">
		</td>
		<td><span id="id_span$edu_search_t$street_id"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . isDel%></td>
		<td><input id="id_input$edu_search_t$is_del" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . isDel%>"  type="text" name="edu_search_t.isDel"
			value="${requestScope.s.isDel}">
		</td>
		<td><span id="id_span$edu_search_t$is_del"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . timeDescription%></td>
		<td><input id="id_input$edu_search_t$time_description" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . timeDescription%>"  type="text" name="edu_search_t.timeDescription"
			value="${requestScope.s.timeDescription}">
		</td>
		<td><span id="id_span$edu_search_t$time_description"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . square%></td>
		<td><input id="id_input$edu_search_t$square" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . square%>"  type="text" name="edu_search_t.square"
			value="${requestScope.s.square}">
		</td>
		<td><span id="id_span$edu_search_t$square"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxStudent%></td>
		<td><input id="id_input$edu_search_t$max_student" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxStudent%>"  type="text" name="edu_search_t.maxStudent"
			value="${requestScope.s.maxStudent}">
		</td>
		<td><span id="id_span$edu_search_t$max_student"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxTeacher%></td>
		<td><input id="id_input$edu_search_t$max_teacher" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxTeacher%>"  type="text" name="edu_search_t.maxTeacher"
			value="${requestScope.s.maxTeacher}">
		</td>
		<td><span id="id_span$edu_search_t$max_teacher"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxAutomoblie%></td>
		<td><input id="id_input$edu_search_t$max_automoblie" placeholder="请输入<%=c.x.platform.admin.feng.edu.edu_search_t.cx.alias.EduSearchTCxAlias . maxAutomoblie%>"  type="text" name="edu_search_t.maxAutomoblie"
			value="${requestScope.s.maxAutomoblie}">
		</td>
		<td><span id="id_span$edu_search_t$max_automoblie"></span>
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
var url = "${pageContext.request.contextPath}/c/x/platform/admin/feng/edu/edu_search_t/cx/list.do";
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
flag=ayCheckColumn("name","id_span$edu_search_t$name","id_input$edu_search_t$name","VARCHAR","no","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("address","id_span$edu_search_t$address","id_input$edu_search_t$address","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("驾证类型","id_span$edu_search_t$license_type","id_input$edu_search_t$license_type","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("价格以分为单位","id_span$edu_search_t$price","id_input$edu_search_t$price","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("学习周期类型","id_span$edu_search_t$time_type","id_input$edu_search_t$time_type","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("分数(好评)","id_span$edu_search_t$score","id_input$edu_search_t$score","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("班级id","id_span$edu_search_t$edu_class_id","id_input$edu_search_t$edu_class_id","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("老师id","id_span$edu_search_t$edu_teacher_id","id_input$edu_search_t$edu_teacher_id","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("uid","id_span$edu_search_t$uid","id_input$edu_search_t$uid","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("距离","id_span$edu_search_t$distance","id_input$edu_search_t$distance","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("纬度","id_span$edu_search_t$lat","id_input$edu_search_t$lat","DOUBLE","yes","22","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("经度","id_span$edu_search_t$lng","id_input$edu_search_t$lng","DOUBLE","yes","22","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("description","id_span$edu_search_t$description","id_input$edu_search_t$description","VARCHAR","yes","1000","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("street_id","id_span$edu_search_t$street_id","id_input$edu_search_t$street_id","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否删除","id_span$edu_search_t$is_del","id_input$edu_search_t$is_del","INT UNSIGNED","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("time_description","id_span$edu_search_t$time_description","id_input$edu_search_t$time_description","VARCHAR","yes","1000","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("面积（米）","id_span$edu_search_t$square","id_input$edu_search_t$square","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("max_student","id_span$edu_search_t$max_student","id_input$edu_search_t$max_student","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("max_teacher","id_span$edu_search_t$max_teacher","id_input$edu_search_t$max_teacher","BIGINT UNSIGNED","yes","20","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("最多汽车数","id_span$edu_search_t$max_automoblie","id_input$edu_search_t$max_automoblie","BIGINT UNSIGNED","yes","20","0","0",a,true);
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