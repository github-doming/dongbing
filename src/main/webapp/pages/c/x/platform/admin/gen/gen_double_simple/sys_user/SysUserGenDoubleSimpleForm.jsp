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
	action="${pageContext.request.contextPath}/c/x/platform/admin/gen/gen_double_simple/sys_user/save.do"
	method="post">

<table  align="center" border="1">
<tr>
		<td>树id</td>
		<td><input value="${requestScope.value_first$tree$id}" id="id_first$tree$id"
			name="name_first$tree$id" /></td>
	<td>树名称</td>
		<td><input value="${requestScope.value_first$tree$name}" id="id_first$tree$name"
			name="name_first$tree$name" /></td>
	</tr>
</table>


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
		<td>
		<p class="text-right">上一级菜单id：</p>
		</td>
		<td><input id="id_input_parent_menu_id" type="text"
			name="sys_org.parent" value="${requestScope.p.sysOrgId}"></td>
		<td></td>
	</tr>
	<tr>
		<td>
		<p class="text-right">上一级菜单名称：</p>
		</td>
		<td><input id="id_input_parent_menu_name"
			class="input-medium search-query" readonly="readonly" type="text"
			name="" value="${requestScope.p.name}"> <input
			onclick="parent('${requestScope.s.path}');"  class="btn" type="button"
			value="选择"></td>
		<td width="30%"></td>
	</tr>
	
	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . id%></td>
		<td><input id="id_input$sys_user$ID_" placeholder="请输入<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . id%>"  type="text" name="sys_user.id"
			value="${requestScope.s.id}">
		</td>
		<td><span id="id_span$sys_user$ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . sysUserId%></td>
		<td><input id="id_input$sys_user$SYS_USER_ID_" type="text" name="sys_user.sysUserId"
			value="${requestScope.SYS_USER_ID_}"></td>
		<td><span id="id_span$sys_user$SYS_USER_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . sysPersonId%></td>
		<td><input id="id_input$sys_user$SYS_PERSON_ID_" placeholder="请输入<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . sysPersonId%>"  type="text" name="sys_user.sysPersonId"
			value="${requestScope.s.sysPersonId}">
		</td>
		<td><span id="id_span$sys_user$SYS_PERSON_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . name%></td>
		<td><input id="id_input$sys_user$NAME_" placeholder="请输入<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . name%>"  type="text" name="sys_user.name"
			value="${requestScope.s.name}">
		</td>
		<td><span id="id_span$sys_user$NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . permissionGrade%></td>
		<td><input id="id_input$sys_user$PERMISSION_GRADE_" placeholder="请输入<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . permissionGrade%>"  type="text" name="sys_user.permissionGrade"
			value="${requestScope.s.permissionGrade}">
		</td>
		<td><span id="id_span$sys_user$PERMISSION_GRADE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . desc%></td>
		<td><input id="id_input$sys_user$DESC_" placeholder="请输入<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . desc%>"  type="text" name="sys_user.desc"
			value="${requestScope.s.desc}">
		</td>
		<td><span id="id_span$sys_user$DESC_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . createTime%></td>
		<td><input id="id_input$sys_user$CREATE_TIME_" placeholder="请输入<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . createTime%>"  type="text" name="sys_user.createTime"
			value="${requestScope.s.createTime}">
		</td>
		<td><span id="id_span$sys_user$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . createTimeLong%></td>
		<td><input id="id_input$sys_user$CREATE_TIME_LONG_" placeholder="请输入<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . createTimeLong%>"  type="text" name="sys_user.createTimeLong"
			value="${requestScope.s.createTimeLong}">
		</td>
		<td><span id="id_span$sys_user$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . updateTime%></td>
		<td><input id="id_input$sys_user$UPDATE_TIME_" placeholder="请输入<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . updateTime%>"  type="text" name="sys_user.updateTime"
			value="${requestScope.s.updateTime}">
		</td>
		<td><span id="id_span$sys_user$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . updateTimeLong%></td>
		<td><input id="id_input$sys_user$UPDATE_TIME_LONG_" placeholder="请输入<%=c.x.platform.admin.gen.gen_double_simple.sys_user.alias.SysUserGenDoubleSimpleAlias . updateTimeLong%>"  type="text" name="sys_user.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">
		</td>
		<td><span id="id_span$sys_user$UPDATE_TIME_LONG_"></span>
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
 *上一级;
 *不使用模态窗口 ;
 */
function parent(path){

	//alert('path='+path);
	var url = "${pageContext.request.contextPath}/c/x/platform/admin/gen/gen_double_simple/sys_org/parent.do";
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
	var url = "${pageContext.request.contextPath}/c/x/platform/admin/gen/gen_double_simple/sys_org/parent.do";
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
var url = "${pageContext.request.contextPath}/c/x/platform/admin/gen/gen_double_simple/sys_user/list.do";
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
flag=ayCheckColumn("ID_","id_span$sys_user$ID_","id_input$sys_user$ID_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("SYS_PERSON_ID_","id_span$sys_user$SYS_PERSON_ID_","id_input$sys_user$SYS_PERSON_ID_","VARCHAR","yes","128","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("用户名","id_span$sys_user$NAME_","id_input$sys_user$NAME_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("数据权限等级","id_span$sys_user$PERMISSION_GRADE_","id_input$sys_user$PERMISSION_GRADE_","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$sys_user$DESC_","id_input$sys_user$DESC_","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CREATE_TIME_","id_span$sys_user$CREATE_TIME_","id_input$sys_user$CREATE_TIME_","DATETIME","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CREATE_TIME_LONG_","id_span$sys_user$CREATE_TIME_LONG_","id_input$sys_user$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("UPDATE_TIME_","id_span$sys_user$UPDATE_TIME_","id_input$sys_user$UPDATE_TIME_","DATETIME","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("UPDATE_TIME_LONG_","id_span$sys_user$UPDATE_TIME_LONG_","id_input$sys_user$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
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