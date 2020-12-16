<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>

<!-- 加载js -->
<script type="text/javascript">
function go(){
 	var url = "${pageContext.request.contextPath}/pages/c/x/d/admin/feng/bs/sys/fav/sys_org_info/list.do";
 	window.location.href=url;
 }
/**
 *使用模态窗口 
 */
function parent(path){
	var url = "${pageContext.request.contextPath}/pages/c/x/d/admin/feng/bs/sys/fav/sys_org_info/parent.do";
	url=url +"?path="+path;
	var name="n";
	var needRefresh=window.showModalDialog(url,name, 'dialogHeight:400px;dialogWidth:450px;center:yes;help:no;status:no;scroll:yes');

	if(needRefresh == true){

	}
	return false;
}
</script>
<title></title>
</head>
<body>
<form id="id_form_save"
	action="${pageContext.request.contextPath}/pages/c/x/d/admin/feng/bs/sys/fav/sys_org_info/save.do"
	method="post">
<table class="table  table-bordered table-hover" border="0">
	<tr>
		<th colspan="3">菜单 <c:choose>
			<c:when test="${requestScope.id==null}">添加</c:when>
			<c:otherwise>修改</c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr>
		<td colspan="3"><input onclick="go();" class="btn" type="button"
			value="返回列表"></input> <c:choose>
			<c:when test="${requestScope.id==null}">
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
			name="sys_org_info.parent" value="${requestScope.p.id}"></td>
		<td></td>
	</tr>
	
	
	<tr>
		<td>
		<p class="text-right">上一级菜单名称：</p>
		</td>
		<td><input id="id_input_parent_menu_name"
			class="input-medium search-query" readonly="readonly" type="text"
			name="" value="${requestScope.p.name}"> <input
			onclick="parent('${requestScope.s.path}');" class="btn" type="button"
			value="选择"></td>
		<td width="30%"></td>
	</tr>
<tr style="display: none;">
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . id%></td>
		<td><input type="text" name="sys_org_info.id"
			value="${requestScope.id}"></td>
		<td></td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . name%></td>
		<td><input placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . name%>" type="text" name="sys_org_info.name"
			value="${requestScope.s.name}"></td>
		<td></td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . path%></td>
		<td><input placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . path%>" type="text" name="sys_org_info.path"
			value="${requestScope.s.path}"></td>
		<td></td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . tree_code%></td>
		<td><input placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . tree_code%>" type="text" name="sys_org_info.tree_code"
			value="${requestScope.s.tree_code}"></td>
		<td></td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . parent%></td>
		<td><input placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . parent%>" type="text" name="sys_org_info.parent"
			value="${requestScope.s.parent}"></td>
		<td></td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . sequence%></td>
		<td><input placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_org_info.alias.SysOrgInfoAlias . sequence%>" type="text" name="sys_org_info.sequence"
			value="${requestScope.s.sequence}"></td>
		<td></td>
</tr>	
	
	
	

</table>
</form>
</body>
</html>
