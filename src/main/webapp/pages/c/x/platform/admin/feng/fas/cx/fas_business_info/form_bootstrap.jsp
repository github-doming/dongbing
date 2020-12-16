<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/kaida/common.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>

<form id="id_form_save"
	action="${pageContext.request.contextPath}/admin/sys/fas_business_info/save.do"
	method="post">
	
	
	
	
	
	
	
	
	
	<!-- 第1个table  -->



<table width="100%" border="0" cellpadding="0" cellspacing="1"
	bgcolor="#7A9DB8">
	<tr bgcolor="#FFFFFF">
		<td height="25" valign="top">&nbsp;&nbsp;<img
			src="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/form/images/current_directory.jpg"
			align="middle">&nbsp;<span class="class_font_size_12px">当前位置：菜单管理 &gt;
			
			菜单 <c:choose>
			<c:when test="${requestScope.id==null}">添加</c:when>
			<c:otherwise>修改</c:otherwise>
		</c:choose>
		
		</span></td>
	</tr>
	
	<tr bgcolor="#FFFFFF">
		<td height="25" valign="top">&nbsp;&nbsp;&nbsp;<span class="font_5">
			
			
			<input onclick="back();" class="btn"
			type="button" value="返回列表"></input> <c:choose>
			<c:when test="${requestScope.id==null}">
				<input onclick='save();' class="btn" type="button" value="新增">
			</c:when>
			<c:otherwise>
				<input onclick='save();' class="btn" type="button" value="编辑">
			</c:otherwise>
		</c:choose>
			
		</span></td>
	</tr>
	
	
	<tr bgcolor="#FFFFFF">
		<td align="center" style="padding-right: 25px; padding-bottom: 50px;padding-left: 25px; padding-top: 50px;">
		<!-- 第2个table  -->
	
		
		
		<table width="99%" border="0" cellpadding="0" cellspacing="1"
			bgcolor="#CCCCCC">



<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . id%>:</span></td>
		<td><input id="id_input$fas_business_info$id" type="text" name="fas_business_info.id"
			value="${requestScope.id}"></td>
		<td style="width:25%;"><span id="id_span$fas_business_info$id"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . name%>:</span></td>
		<td><input id="id_input$fas_business_info$name" placeholder="请输入<%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . name%>"  type="text" name="fas_business_info.name"
			value="${requestScope.s.name}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_business_info$name"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . help_code%>:</span></td>
		<td><input id="id_input$fas_business_info$help_code" placeholder="请输入<%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . help_code%>"  type="text" name="fas_business_info.help_code"
			value="${requestScope.s.help_code}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_business_info$help_code"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . sys_description%>:</span></td>
		<td><input id="id_input$fas_business_info$sys_description" placeholder="请输入<%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . sys_description%>"  type="text" name="fas_business_info.sys_description"
			value="${requestScope.s.sys_description}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_business_info$sys_description"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . sys_enable%>:</span></td>
		<td><input id="id_input$fas_business_info$sys_enable" placeholder="请输入<%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . sys_enable%>"  type="text" name="fas_business_info.sys_enable"
			value="${requestScope.s.sys_enable}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_business_info$sys_enable"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . sys_del%>:</span></td>
		<td><input id="id_input$fas_business_info$sys_del" placeholder="请输入<%=c.x.platform.feng.feng.fas.cx.fas_business_info.alias.FasBusinessInfoAlias . sys_del%>"  type="text" name="fas_business_info.sys_del"
			value="${requestScope.s.sys_del}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_business_info$sys_del"></span>
		</td>
</tr>	
	<tr bgcolor="#FFFFFF">
		<td align="center" colspan="3"></td>
	</tr>
	
	

		</table>



		<!-- 第2个table  --></td>
	</tr>
</table>







<!-- 第1个table  -->







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
var url = "${pageContext.request.contextPath}/admin/sys/fas_business_info/list.do";
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
flag=ayCheckColumn("name","id_span$fas_business_info$name","id_input$fas_business_info$name","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("help_code","id_span$fas_business_info$help_code","id_input$fas_business_info$help_code","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("备注","id_span$fas_business_info$sys_description","id_input$fas_business_info$sys_description","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("sys_enable","id_span$fas_business_info$sys_enable","id_input$fas_business_info$sys_enable","TINYINT","yes","3","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("sys_del","id_span$fas_business_info$sys_del","id_input$fas_business_info$sys_del","TINYINT","yes","3","0","0",a,true);
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