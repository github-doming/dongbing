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
	action="${pageContext.request.contextPath}/pages/c/x/d/admin/feng/bs/sys/fav/sys_area_info/save.do"
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
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . id%></td>
		<td><input id="id_input$sys_area_info$id" type="text" name="sys_area_info.id"
			value="${requestScope.id}"></td>
		<td><span id="id_span$sys_area_info$id"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . parent%></td>
		<td><input id="id_input$sys_area_info$parent" placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . parent%>"  type="text" name="sys_area_info.parent"
			value="${requestScope.s.parent}">
		</td>
		<td><span id="id_span$sys_area_info$parent"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . parent_ids%></td>
		<td><input id="id_input$sys_area_info$parent_ids" placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . parent_ids%>"  type="text" name="sys_area_info.parent_ids"
			value="${requestScope.s.parent_ids}">
		</td>
		<td><span id="id_span$sys_area_info$parent_ids"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . code%></td>
		<td><input id="id_input$sys_area_info$code" placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . code%>"  type="text" name="sys_area_info.code"
			value="${requestScope.s.code}">
		</td>
		<td><span id="id_span$sys_area_info$code"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . name%></td>
		<td><input id="id_input$sys_area_info$name" placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . name%>"  type="text" name="sys_area_info.name"
			value="${requestScope.s.name}">
		</td>
		<td><span id="id_span$sys_area_info$name"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . remarks%></td>
		<td><input id="id_input$sys_area_info$remarks" placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . remarks%>"  type="text" name="sys_area_info.remarks"
			value="${requestScope.s.remarks}">
		</td>
		<td><span id="id_span$sys_area_info$remarks"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . del_flag%></td>
		<td><input id="id_input$sys_area_info$del_flag" placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . del_flag%>"  type="text" name="sys_area_info.del_flag"
			value="${requestScope.s.state}">
		</td>
		<td><span id="id_span$sys_area_info$del_flag"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . path%></td>
		<td><input id="id_input$sys_area_info$path" placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . path%>"  type="text" name="sys_area_info.path"
			value="${requestScope.s.path}">
		</td>
		<td><span id="id_span$sys_area_info$path"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . tree_code%></td>
		<td><input id="id_input$sys_area_info$tree_code" placeholder="请输入<%=c.x.platform.admin.d.feng.sys.fav.sys_area_info.alias.FunAreaInfoAlias . tree_code%>"  type="text" name="sys_area_info.tree_code"
			value="${requestScope.s.tree_code}">
		</td>
		<td><span id="id_span$sys_area_info$tree_code"></span>
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
var url = "${pageContext.request.contextPath}/pages/c/x/d/admin/feng/bs/sys/fav/sys_area_info/list.do";
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
flag=ayCheckColumn("父级编号","id_span$sys_area_info$parent","id_input$sys_area_info$parent","BIGINT","no","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("所有父级编号","id_span$sys_area_info$parent_ids","id_input$sys_area_info$parent_ids","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("区域编码","id_span$sys_area_info$code","id_input$sys_area_info$code","VARCHAR","yes","100","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("区域名称","id_span$sys_area_info$name","id_input$sys_area_info$name","VARCHAR","no","100","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("备注","id_span$sys_area_info$remarks","id_input$sys_area_info$remarks","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("删除标记（0：正常；1：删除）","id_span$sys_area_info$del_flag","id_input$sys_area_info$del_flag","CHAR","yes","1","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("path","id_span$sys_area_info$path","id_input$sys_area_info$path","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("tree_code","id_span$sys_area_info$tree_code","id_input$sys_area_info$tree_code","VARCHAR","yes","10","0","0",a,true);
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