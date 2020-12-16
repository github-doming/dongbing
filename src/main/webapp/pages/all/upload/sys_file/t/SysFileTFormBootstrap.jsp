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
<!-- 
<form id="id_form_save"
	action="${pageContext.request.contextPath}/all/upload/sys_file/t/save/bootstrap.do"
	method="post">
 -->
<form enctype="multipart/form-data" id="id_form_save"
	action="${pageContext.request.contextPath}/all/upload/sys_file/t/save/bootstrap.do"
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
		<td align="right"><%=all.upload.sys_file.t.alias.SysFileTAlias . name%></td>
		<td><input id="id_input$sys_file$FILE_NAME_" placeholder="请输入<%=all.upload.sys_file.t.alias.SysFileTAlias . name%>"  type="text" name="sys_file.fileName"
			value="${requestScope.s.fileName}">


		</td>
		<td><span id="id_span$sys_file$FILE_NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.upload.sys_file.t.alias.SysFileTAlias . filePath%></td>
		<td><input id="id_input$sys_file$FILE_PATH_" placeholder="请输入<%=all.upload.sys_file.t.alias.SysFileTAlias . filePath%>"  type="text" name="sys_file.filePath"
			value="${requestScope.s.filePath}">


		</td>
		<td><span id="id_span$sys_file$FILE_PATH_"></span>
		</td>
</tr>	


<tr>
		<td align="right">选择文件上传 :</td>
		<td><input type="file" id="id_input$sys_bytes$BYTES_" placeholder="请输入"  type="file"  name="sys_bytes.bytes"
			value="">


		</td>
		<td><span id="id_span$sys_bytes$BYTES_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.upload.sys_file.t.alias.SysFileTAlias . type%></td>
		<td><input id="id_input$sys_file$FILE_TYPE_" placeholder="请输入<%=all.upload.sys_file.t.alias.SysFileTAlias . type%>"  type="text" name="sys_file.fileType"
			value="${requestScope.s.fileType}">


		</td>
		<td><span id="id_span$sys_file$FILE_TYPE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.upload.sys_file.t.alias.SysFileTAlias . assort%></td>
		<td><input id="id_input$sys_file$ASSORT_" placeholder="请输入<%=all.upload.sys_file.t.alias.SysFileTAlias . assort%>"  type="text" name="sys_file.assort"
			value="${requestScope.s.assort}">


		</td>
		<td><span id="id_span$sys_file$ASSORT_"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.upload.sys_file.t.alias.SysFileTAlias . desc%></td>
		<td><input id="id_input$sys_file$DESC_" placeholder="请输入<%=all.upload.sys_file.t.alias.SysFileTAlias . desc%>"  type="text" name="sys_file.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$sys_file$DESC_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.upload.sys_file.t.alias.SysFileTAlias . code%></td>
		<td><input id="id_input$sys_file$CODE_" placeholder="请输入<%=all.upload.sys_file.t.alias.SysFileTAlias . code%>"  type="text" name="sys_file.code"
			value="${requestScope.s.code}">


		</td>
		<td><span id="id_span$sys_file$CODE_"></span>
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
var url = "${pageContext.request.contextPath}/all/upload/sys_file/t/list/bootstrap.do";
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

flag=ayCheckColumn("后台文件表名称NAME_","id_span$sys_file$FILE_NAME_","id_input$sys_file$FILE_NAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("文件路径FILE_PATH_","id_span$sys_file$FILE_PATH_","id_input$sys_file$FILE_PATH_","VARCHAR","yes","250","0","0",a,true);

if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型","id_span$sys_file$FILE_TYPE_","id_input$sys_file$FILE_TYPE_","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否分类ASSORT_","id_span$sys_file$ASSORT_","id_input$sys_file$ASSORT_","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}

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