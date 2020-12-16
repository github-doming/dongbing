<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>

	<form id="id_form_save"
		action="${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_tree_simple/fun_single_simple/save.do"
		method="post">
		<table class="table  table-bordered table-hover" border="0">
			<tr>
				<th colspan="3">菜单 <c:choose>
						<c:when test="${requestScope.sysId==null}">添加</c:when>
						<c:otherwise>修改</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td colspan="3"><input onclick="go();" class="btn"
					type="button" value="返回列表"></input> <c:choose>
						<c:when test="${requestScope.sysId==null}">
							<input class="btn" type="submit" value="新增">
						</c:when>
						<c:otherwise>
							<input class="btn" type="submit" value="编辑">
						</c:otherwise>
					</c:choose></td>
			</tr>
			<!--  	<tr style="display: none;">
-->
			<tr>
				<td>
					<p class="text-right">上一级菜单id：</p>
				</td>
				<td><input id="id_input_parent_menu_id" type="text"
					name="fun_single_simple_t.parent" value="${requestScope.p.sysId}"></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<p class="text-right">上一级菜单名称：</p>
				</td>
				<td><input id="id_input_parent_menu_name"
					class="input-medium search-query" readonly="readonly" type="text"
					name="" value="${requestScope.p.name}"> <input
					onclick="parent('${requestScope.s.path}');" class="btn"
					type="button" value="选择"></td>
				<td width="30%"></td>
			</tr>
			<tr style="display: none;">
				<td align="right">id：</td>
				<td><input type="text" name="fun_single_simple_t.id"
					value="${requestScope.sysId}"></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">
					<p class="text-right">菜单名称：</p>
				</td>
				<td><input placeholder="请输入名称" type="text"
					name="fun_single_simple_t.name" value="${requestScope.s.name}"></td>
				<td></td>
			</tr>
			<tr>
				<td align="right">
					<p class="text-right">菜单url：</p>
				</td>
				<td><input type="text" name="fun_single_simple_t.url"
					value="${requestScope.s.url}"></td>
				<td></td>
			</tr>
			<tr style="display: none;">
				<td align="right">
					<p class="text-right">菜单path：</p>
				</td>
				<td><input type="text" name="fun_single_simple_t.path"
					value="${requestScope.s.path}"></td>
				<td></td>
			</tr>
			<c:choose>
				<c:when test="${requestScope.sysId==null}">
					<tr>
						<td align="right">
							<p class="text-right">菜单sequence：</p>
						</td>
						<td><input type="text" name="fun_single_simple_t.sn"
							value="50"></td>
						<td></td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<td align="right">
							<p class="text-right">菜单sequence：</p>
						</td>
						<td><input type="text" name="fun_single_simple_t.sn"
							value="${requestScope.s.sequence}"></td>
						<td></td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
		</td>
		</tr>
		</table>
	</form>
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">
function go(){
 	var url = "${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_tree_simple/fun_single_simple/list.do";
 	window.location.href=url;
 }
 
/**
 *上一级窗口 ;
 */
function parent(path){

	alert('a path='+path);
	var url = "${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_tree_simple/fun_single_simple/parent.do";
	url=url +"?path="+path;
	var name='newwindow';
	ayFormOpenwindow (url,name,800,400) ;
	return false;
}

/**
 *使用模态窗口 
 */
function parent$v2(path){
	//alert('path='+path);
	var url = "${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_tree_simple/fun_single_simple/parent.do";
	url=url +"?path="+path;
	var name="n";
	var needRefresh=window.showModalDialog(url,name, 'dialogHeight:400px;dialogWidth:450px;center:yes;help:no;status:no;scroll:yes');
//alert('needRefresh='+needRefresh);
	if(needRefresh == true){
	//	alert('end');
	}
	return false;
}
/**
 *不用模态窗口 
 */
function parent_v1(){
	//alert('1');
 	var url = "${pageContext.request.contextPath}/c/x/platform/admin/ref/ref_tree_simple/fun_single_simple/parent.do";
 	var obj_form=document.getElementById("id_form_save");
 obj_form.action=url +"?id=${requestScope.sysId}&&action=${requestScope.action}";
 	//alert('a='+obj_form.action);
 	obj_form.submit();
 }
</script>
