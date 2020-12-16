<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include
	file="/pages/c/x/platform/root/common/kaida/common.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
<%@ include
	file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>

<form enctype="multipart/form-data" id="id_form_save"
	action="${pageContext.request.contextPath}/admin/upload/sys_lob_info/save.do"
	method="post"><!-- 第1个table  -->



<table width="100%" border="0" cellpadding="0" cellspacing="1"
	bgcolor="#7A9DB8">
	<tr bgcolor="#FFFFFF">
		<td height="25" valign="top">&nbsp;&nbsp;<img
			src="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/form/images/current_directory.jpg"
			align="middle">&nbsp;<span class="class_font_size_12px">当前位置：菜单管理
		&gt; 菜单 <c:choose>
			<c:when test="${requestScope.id==null}">添加</c:when>
			<c:otherwise>修改</c:otherwise>
		</c:choose> </span></td>
	</tr>

	<tr bgcolor="#FFFFFF">
		<td height="25" valign="top">&nbsp;&nbsp;&nbsp;<span
			class="font_5"> <input onclick="back();" class="btn"
			type="button" value="返回列表"></input> <c:choose>
			<c:when test="${requestScope.id==null}">
				<input onclick='save();' class="btn" type="button" value="新增">
			</c:when>
			<c:otherwise>
				<input onclick='save();' class="btn" type="button" value="编辑">
			</c:otherwise>
		</c:choose> </span></td>
	</tr>


	<tr bgcolor="#FFFFFF">
		<td align="center"
			style="padding-right: 25px; padding-bottom: 50px; padding-left: 25px; padding-top: 50px;">
		<!-- 第2个table  -->



		<table width="99%" border="0" cellpadding="0" cellspacing="1"
			bgcolor="#CCCCCC">



			<tr bgcolor="#FFFFFF">
				<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . id%>:</span></td>
				<td><input id="id_input$sys_lob_info$id" type="text"
					name="sys_lob_info.id" value="${requestScope.id}"></td>
				<td style="width: 25%;"><span id="id_span$sys_lob_info$id"></span>
				</td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . file_name%>:</span></td>
				<td><input id="id_input$sys_lob_info$file_name"
					placeholder="请输入<%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . file_name%>"
					type="text" name="sys_lob_info.file_name"
					value="${requestScope.s.file_name}"></td>
				<td style="width: 25%;"><span
					id="id_span$sys_lob_info$file_name"></span></td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . file%>:</span></td>
				<td><input id="id_input$sys_lob_info$file"
					placeholder="请输入<%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . file%>"
					type="text" name="sys_lob_info.file" value="${requestScope.s.file}">
				</td>
				<td style="width: 25%;"><span id="id_span$sys_lob_info$file"></span>
				</td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . user_id%>:</span></td>
				<td><input id="id_input$sys_lob_info$user_id"
					placeholder="请输入<%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . user_id%>"
					type="text" name="sys_lob_info.user_id"
					value="${requestScope.s.user_id}"></td>
				<td style="width: 25%;"><span id="id_span$sys_lob_info$user_id"></span>
				</td>
			</tr>




			<tr bgcolor="#FFFFFF">
				<td align="right"><span class="class_font_size_12px">


				选择文件上传 : </span></td>
				<td><input type="file" name="file" /></td>
				<td style="width: 25%;"><span id="id_span$sys_lob_info$user_id"></span>
				</td>
			</tr>







			<tr bgcolor="#FFFFFF">
				<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . create_time%>:</span></td>
				<td><input id="id_input$sys_lob_info$create_time"
					placeholder="请输入<%=c.x.platform.feng.feng.upload.sys_lob_info.alias.SysLobInfoAlias . create_time%>"
					type="text" name="sys_lob_info.create_time"
					value="${requestScope.s.create_time}"> <img
					onclick=ayCalendarShow('id_input$sys_lob_info$create_time',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
				</td>
				<td style="width: 25%;"><span
					id="id_span$sys_lob_info$create_time"></span></td>
			</tr>
			<tr bgcolor="#FFFFFF">
				<td align="center" colspan="3"></td>
			</tr>



		</table>



		<!-- 第2个table  --></td>
	</tr>
</table>







<!-- 第1个table  --></form>
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">

/**
 * 
 返回
 */
function back(){
var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/upload/admin/upload/sys_lob_info/list.do";
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