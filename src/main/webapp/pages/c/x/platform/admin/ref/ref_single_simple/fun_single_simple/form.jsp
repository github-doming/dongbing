<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%> 
<!-- jquery_ui -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery_ui/blue/jquery-ui.css">
<script src="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery_ui/blue/jquery-ui.js"></script>
<!-- jquery_ui -->
</head>
<body>
<form id="id_form_save"
	method="post">
<table class="table  table-bordered table-hover" border="1">
	<tr>
		<th colspan="3">[代码参考_单表]菜单 <c:choose>
			<c:when test="${requestScope.sysId==null}">添加</c:when>
			<c:otherwise>修改</c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr>
		<td colspan="3"><input onclick="go();" class="btn" type="button"
			value="返回列表"></input> <c:choose>
			<c:when test="${requestScope.sysId==null}">
				<input onclick="save();" class="btn" type="button" value="新增">
			</c:when>
			<c:otherwise>
				<input onclick="save();" class="btn" type="button" value="编辑">
			</c:otherwise>
		</c:choose></td>
	</tr>
	<tr>
		<td align="right">id：</td>
		<td><input type="text" name="fun_single_simple.id"
			value="${requestScope.sysId}"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right">菜单名称：</td>
		<td><input id="id_input$fun_single_simple$name"  type="text" name="fun_single_simple.name"
			value="${requestScope.s.name}"></td>
		<td>
		<span id="id_span$fun_single_simple$name"></span>
		</td>
	</tr>
	<tr>
		<td align="right">菜单url：</td>
		<td><input id="id_input$fun_single_simple$url"  type="text" name="fun_single_simple.url"
			value="${requestScope.s.url}"></td>
		<td>
			<span id="id_span$fun_single_simple$url"></span>
		</td>
	</tr>
	<tr>
		<td align="right">菜单path：</td>
		<td><input type="text" name="fun_single_simple.path"
			value="${requestScope.s.path}"></td>
		<td></td>
	</tr>
</table>
</td>
</tr>
</table>
</form>
</body>
</html>
<script type="text/javascript">
function go(){
 	var url = "${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_single_simple/fun_single_simple/list.do";
 	window.location.href=url;
 }
/**
 * 
 检查
 */
function check(){
	var a="<font color=red>aaa</font>";
	a=null;
	var flag=null;
	flag=ayCheckColumn("名称","id_span$fun_single_simple$name","id_input$fun_single_simple$name","VARCHAR","no","64","0","0",a,true);
	 flag=ayCheckColumn("url","id_span$fun_single_simple$url","id_input$fun_single_simple$url","VARCHAR","no","2","0","0",a,true);
	return flag;
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
var 	url="${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_single_simple/fun_single_simple/save.do"
	var obj_form= document.getElementById('id_form_save');
obj_form.action=url;
obj_form.submit();
 }
</script>
