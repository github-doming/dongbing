<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>


<%@ include
	file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>


<script type="text/javascript">
</script>
</head>
<body>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>

<form id="id_form_save"
	action="${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/bbs/cx/bbs_note_info/save/bootstrap.do"
	method="post">

<table align="center" border="1">
	<tr>
		<td>树id</td>
		<td><input value="${requestScope.value_first$tree$id}"
			id="id_first$tree$id" name="name_first$tree$id" /></td>
		<td>树名称</td>
		<td><input value="${requestScope.value_first$tree$name}"
			id="id_first$tree$name" name="name_first$tree$name" /></td>
	</tr>
</table>


<table border=1 style="width: 100%; table-layout: fixed" id="id_table"
	class="table  table-bordered table-hover">
	<col style="width: 10%" />
	<col style="width: 80%" />
	<col style="width: 10%" />



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
		<td>
		<p class="text-right">上一级菜单id：</p>
		</td>
		<td><input id="id_input_parent_menu_id" type="text"
			name="bbs_notegroup_info.parent" value="${requestScope.p.id}"></td>
		<td></td>
	</tr>
	<tr>
		<td>
		<p class="text-right">上一级菜单名称：</p>
		</td>
		<td><input id="id_input_parent_menu_name"
			class="input-medium search-query" readonly="readonly" type="text"
			name="" value="${requestScope.p.name}"> <input
			onclick="parent('requestScope.s.path');" class="btn" type="button"
			value="选择"></td>
		<td></td>
	</tr>


	<tr>
		<td align="right"><%=c.x.platform.feng.feng.bbs.cx.bbs_note_info.alias.BbsNoteInfoAlias.id%></td>
		<td><input id="id_input$bbs_note_info$id" type="text"
			name="bbs_note_info.id" value="${requestScope.id}"></td>
		<td><span id="id_span$bbs_note_info$id"></span></td>
	</tr>
	<tr>
		<td align="right"><%=c.x.platform.feng.feng.bbs.cx.bbs_note_info.alias.BbsNoteInfoAlias.title%></td>
		<td><input size=100 id="id_input$bbs_note_info$title"
			placeholder="请输入<%=c.x.platform.feng.feng.bbs.cx.bbs_note_info.alias.BbsNoteInfoAlias.title%>"
			type="text" name="bbs_note_info.title"
			value="${requestScope.s.title}"></td>
		<td><span id="id_span$bbs_note_info$title"></span></td>
	</tr>
	<tr>
		<td align="right"><%=c.x.platform.feng.feng.bbs.cx.bbs_note_info.alias.BbsNoteInfoAlias.content%></td>
		<td width="90%"><!-- 
		<input id="id_input$bbs_note_info$content" placeholder="请输入<%=c.x.platform.feng.feng.bbs.cx.bbs_note_info.alias.BbsNoteInfoAlias.content%>"  type="text" name="bbs_note_info.content"
			value="${requestScope.s.content}">
			 --> <textarea name="bbs_note_info.content"
			id="id_input$bbs_note_info$content" style="width: 100%" rows=20
			cols=85>${requestScope.s.content}</textarea></td>
		<td><span id="id_span$bbs_note_info$content"></span></td>
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
 *上一级;
 *使用模态窗口 ;
 */
function parent(path){
	var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/bbs/cx/bbs_notegroup_info/parent/bootstrap.do";
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
var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/bbs/cx/bbs_note_info/list/bootstrap.do";
var first$tree$id=document.getElementById("id_first$tree$id").value;
url=url+"?first$tree$id="+first$tree$id;
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
flag=ayCheckColumn("标题","id_span$bbs_note_info$title","id_input$bbs_note_info$title","VARCHAR","no","32","0","0",a,true);
if(flag){}else{return_flag=false;}
//flag=ayCheckColumn("内容","id_span$bbs_note_info$content","id_input$bbs_note_info$content","VARCHAR","yes","10000","0","0",a,true);
//if(flag){}else{return_flag=false;}
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