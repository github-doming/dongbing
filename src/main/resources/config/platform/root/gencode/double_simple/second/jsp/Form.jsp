<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/common.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>

<form id="id_form_save"
	action="${ay_s}{pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/save.do"
	method="post">

<table  align="center" border="1">
<tr>
		<td>树id</td>
		<td><input value="${ay_s}{requestScope.value_first${ay_s}tree${ay_s}id}" id="id_first${ay_s}tree${ay_s}id"
			name="name_first${ay_s}tree${ay_s}id" /></td>
	<td>树名称</td>
		<td><input value="${ay_s}{requestScope.value_first${ay_s}tree${ay_s}name}" id="id_first${ay_s}tree${ay_s}name"
			name="name_first${ay_s}tree${ay_s}name" /></td>
	</tr>
</table>


<table class="table  table-bordered table-hover" border="1">
	<tr>
		<th colspan="3">菜单 <c:choose>
			<c:when test="${ay_s}{requestScope.id==null}">添加</c:when>
			<c:otherwise>修改</c:otherwise>
		</c:choose></th>
	</tr>
	<tr>
		<td colspan="3"><input onclick="back();" class="btn"
			type="button" value="返回列表"></input> <c:choose>
			<c:when test="${ay_s}{requestScope.id==null}">
				<input onclick='save();' class="btn" type="button" value="新增">
			</c:when>
			<c:otherwise>
				<input onclick='save();' class="btn" type="button" value="编辑">
			</c:otherwise>
		</c:choose></td>
	</tr>
	
	
	<tr>
		<td>
		<p class="text-right">上一级菜单id：</p>
		</td>
		<td><input id="id_input_parent_menu_id" type="text"
			name="${first_table_name}.parent" value="${ay_s}{requestScope.p.${first_fieldNamePk}}"></td>
		<td></td>
	</tr>
	<tr>
		<td>
		<p class="text-right">上一级菜单名称：</p>
		</td>
		<td><input id="id_input_parent_menu_name"
			class="input-medium search-query" readonly="readonly" type="text"
			name="" value="${ay_s}{requestScope.p.name}"> <input
			onclick="parent('${ay_s}{requestScope.s.path}');"  class="btn" type="button"
			value="选择"></td>
		<td width="30%"></td>
	</tr>
	
	
#foreach( $name in $second_list_column )
#if ($name.isPk)
<tr>
		<td align="right"><%=${second_package_name_all}.alias.${second_table_class}Alias . ${name.fieldName}%></td>
		<td><input id="id_input${ay_s}${second_table_name}${ay_s}${name.columnName}" type="text" name="${second_table_name}.${name.fieldName}"
			value="${ay_s}{requestScope.s.${name.fieldName}}"></td>
		<td><span id="id_span${ay_s}${second_table_name}${ay_s}${name.columnName}"></span>
		</td>
</tr>	
#else
<tr>
		<td align="right"><%=${second_package_name_all}.alias.${second_table_class}Alias . ${name.fieldName}%></td>
		<td><input id="id_input${ay_s}${second_table_name}${ay_s}${name.columnName}" placeholder="请输入<%=${second_package_name_all}.alias.${second_table_class}Alias . ${name.fieldName}%>"  type="text" name="${second_table_name}.${name.fieldName}"
			value="${ay_s}{requestScope.s.${name.fieldName}}">

#if ($name.dataType=='Date'||$name.dataType=='Date')

<img
onclick=ayCalendarShow('id_input${ay_s}${second_table_name}${ay_s}${name.columnName}',event,'${name.sqlTypeStr}'); src="${ay_s}{pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
#else
#end
		</td>
		<td><span id="id_span${ay_s}${second_table_name}${ay_s}${name.columnName}"></span>
		</td>
</tr>	
#end
#end
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
 *上一级;
 *不使用模态窗口 ;
 */
function parent(path){

	//alert('path='+path);
	var url = "${ay_s}{pageContext.request.contextPath}/${first_pages_name_all}/${first_table_name_module}/${first_table_name_child}/parent.do";
	url=url +"?path="+path;
	var name='newwindow';
	ayFormOpenwindow (url,name,800,400) ;
	return false;
}
/**
 *上一级;
 *使用模态窗口 ;
 */
function parent_v1(path){
	var url = "${ay_s}{pageContext.request.contextPath}/${first_pages_name_all}/${first_table_name_module}/${first_table_name_child}/parent.do";
	url=url +"?path="+path;
	var name="n";
	var needRefresh=window.showModalDialog(url,name, 'dialogHeight:400px;dialogWidth:450px;center:yes;help:no;status:no;scroll:yes');
	if(needRefresh == true){
	}
	return false;
}

 
/**
 * 
 返回
 */
function back(){
var url = "${ay_s}{pageContext.request.contextPath}/${second_pages_name_all}/${second_table_name_module}/${second_table_name_child}/list.do";
var first${ay_s}tree${ay_s}id=document.getElementById("id_first${ay_s}tree${ay_s}id").value;
url=url+"?first${ay_s}tree${ay_s}id="+first${ay_s}tree${ay_s}id;
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
#foreach( $name in $second_list_column )
#if ($name.isPk)
#else	
flag=ayCheckColumn("${name.comment}","id_span${ay_s}${second_table_name}${ay_s}${name.columnName}","id_input${ay_s}${second_table_name}${ay_s}${name.columnName}","${name.sqlTypeStr}","${name.isNullStr}","${name.precision}","${name.scale}","0",a,true);
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
#foreach( $name in $second_list_column )
#if ($name.sql_column_type=='DATETIME'||$name.sql_column_type=='DATE'||$name.sql_column_type=='TIME'||$name.sql_column_type=='TIMESTAMP')
ayCalendarNow('id_input${ay_s}${second_table_name}${ay_s}${name.columnName}','${name.sqlTypeStr}');
#else
#end
#end
</script>