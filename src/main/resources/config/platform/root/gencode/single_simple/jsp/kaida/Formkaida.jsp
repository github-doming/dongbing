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
	action="${ay_s}{pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/save.do"
	method="post">
	
	
	
	
	
	
	
	
	
	<!-- 第1个table  -->



<table width="100%" border="0" cellpadding="0" cellspacing="1"
	bgcolor="#7A9DB8">
	<tr bgcolor="#FFFFFF">
		<td height="25" valign="top">&nbsp;&nbsp;<img
			src="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/form/images/current_directory.jpg"
			align="middle">&nbsp;<span class="class_font_size_12px">当前位置：菜单管理 &gt;
			
			菜单 <c:choose>
			<c:when test="${ay_s}{requestScope.id==null}">添加</c:when>
			<c:otherwise>修改</c:otherwise>
		</c:choose>
		
		</span></td>
	</tr>
	
	<tr bgcolor="#FFFFFF">
		<td height="25" valign="top">&nbsp;&nbsp;&nbsp;<span class="font_5">
			
			
			<input onclick="back();" class="btn"
			type="button" value="返回列表"></input> <c:choose>
			<c:when test="${ay_s}{requestScope.id==null}">
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



#foreach( $name in $ay_list_column )
#if ($name.isPk)
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%>:</span></td>
		<td><input id="idInput_${ay_table_class}_${name.fieldName}" type="text" name="${ay_table_name}.${name.columnName}"
			value="${ay_s}{requestScope.${name.columnName}}"></td>
		<td style="width:25%;"><span id="idSpan_${ay_table_class}_${name.fieldName}"></span>
		</td>
</tr>	
#else
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%>:</span></td>
		<td><input id="idInput_${ay_table_class}_${name.fieldName}" placeholder="请输入<%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%>"  type="text" name="${ay_table_name}.${name.columnName}"
			value="${ay_s}{requestScope.s.${name.fieldName}}">
#if ($name.sql_column_type=='DATETIME'||$name.sql_column_type=='DATE'||$name.sql_column_type=='TIME'||$name.sql_column_type=='TIMESTAMP')
<img
onclick=ayCalendarShow('idInput_${ay_table_class}_${name.fieldName}',event,'${name.sqlTypeStr}'); src="${ay_s}{pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
#else
#end
		</td>
		<td style="width:25%;"><span id="idSpan_${ay_table_class}_${name.fieldName}"></span>
		</td>
</tr>	
#end
#end
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
var url = "${ay_s}{pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/list.do";
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
#foreach( $name in $ay_list_column )
#if ($name.isPk)
#else	
flag=ayCheckColumn("${name.comment}","idSpan_${ay_table_class}_${name.fieldName}","idInput_${ay_table_class}_${name.fieldName}","${name.sqlTypeStr}","${name.isNullStr}","${name.precision}","${name.scale}","0",a,true);
if(flag){}else{return_flag=false;}
#end
#end
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
#foreach( $name in $ay_list_column )
#if ($name.sql_column_type=='DATETIME'||$name.sql_column_type=='DATE'||$name.sql_column_type=='TIME'||$name.sql_column_type=='TIMESTAMP')
ayCalendarNow('idInput_${ay_table_class}_${name.fieldName}','${name.sqlTypeStr}');
#else
#end
#end
</script>