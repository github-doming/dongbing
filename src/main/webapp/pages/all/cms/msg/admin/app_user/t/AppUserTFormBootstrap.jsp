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
	action="${pageContext.request.contextPath}/all/cms/msg/admin/app_user/t/save/bootstrap.do"
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
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . idx%></td>
		<td><input id="idInput_AppUserT_idx" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . idx%>"  type="text" name="app_user.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_AppUserT_idx"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . appUserId%></td>
		<td><input id="idInput_AppUserT_appUserId" type="text" name="app_user.appUserId"
			value="${requestScope.s.appUserId}"></td>
		<td><span id="idSpan_AppUserT_appUserId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . mainAppAccountId%></td>
		<td><input id="idInput_AppUserT_mainAppAccountId" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . mainAppAccountId%>"  type="text" name="app_user.mainAppAccountId"
			value="${requestScope.s.mainAppAccountId}">


		</td>
		<td><span id="idSpan_AppUserT_mainAppAccountId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . sysUserId%></td>
		<td><input id="idInput_AppUserT_sysUserId" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . sysUserId%>"  type="text" name="app_user.sysUserId"
			value="${requestScope.s.sysUserId}">


		</td>
		<td><span id="idSpan_AppUserT_sysUserId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . appUserName%></td>
		<td><input id="idInput_AppUserT_appUserName" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . appUserName%>"  type="text" name="app_user.appUserName"
			value="${requestScope.s.appUserName}">


		</td>
		<td><span id="idSpan_AppUserT_appUserName"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . appUserCode%></td>
		<td><input id="idInput_AppUserT_appUserCode" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . appUserCode%>"  type="text" name="app_user.appUserCode"
			value="${requestScope.s.appUserCode}">


		</td>
		<td><span id="idSpan_AppUserT_appUserCode"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . appUserType%></td>
		<td><input id="idInput_AppUserT_appUserType" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . appUserType%>"  type="text" name="app_user.appUserType"
			value="${requestScope.s.appUserType}">


		</td>
		<td><span id="idSpan_AppUserT_appUserType"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . tableName%></td>
		<td><input id="idInput_AppUserT_tableName" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . tableName%>"  type="text" name="app_user.tableName"
			value="${requestScope.s.tableName}">


		</td>
		<td><span id="idSpan_AppUserT_tableName"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . headPortrait%></td>
		<td><input id="idInput_AppUserT_headPortrait" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . headPortrait%>"  type="text" name="app_user.headPortrait"
			value="${requestScope.s.headPortrait}">


		</td>
		<td><span id="idSpan_AppUserT_headPortrait"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . nickname%></td>
		<td><input id="idInput_AppUserT_nickname" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . nickname%>"  type="text" name="app_user.nickname"
			value="${requestScope.s.nickname}">


		</td>
		<td><span id="idSpan_AppUserT_nickname"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . realname%></td>
		<td><input id="idInput_AppUserT_realname" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . realname%>"  type="text" name="app_user.realname"
			value="${requestScope.s.realname}">


		</td>
		<td><span id="idSpan_AppUserT_realname"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . gender%></td>
		<td><input id="idInput_AppUserT_gender" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . gender%>"  type="text" name="app_user.gender"
			value="${requestScope.s.gender}">


		</td>
		<td><span id="idSpan_AppUserT_gender"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . age%></td>
		<td><input id="idInput_AppUserT_age" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . age%>"  type="text" name="app_user.age"
			value="${requestScope.s.age}">


		</td>
		<td><span id="idSpan_AppUserT_age"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . permissionGrade%></td>
		<td><input id="idInput_AppUserT_permissionGrade" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . permissionGrade%>"  type="text" name="app_user.permissionGrade"
			value="${requestScope.s.permissionGrade}">


		</td>
		<td><span id="idSpan_AppUserT_permissionGrade"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . sn%></td>
		<td><input id="idInput_AppUserT_sn" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . sn%>"  type="text" name="app_user.sn"
			value="${requestScope.s.sn}">


		</td>
		<td><span id="idSpan_AppUserT_sn"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . loginTime%></td>
		<td><input id="idInput_AppUserT_loginTime" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . loginTime%>"  type="text" name="app_user.loginTime"
			value="${requestScope.s.loginTime}">



<img
onclick=ayCalendarShow('idInput_AppUserT_loginTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_AppUserT_loginTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . loginTimeLong%></td>
		<td><input id="idInput_AppUserT_loginTimeLong" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . loginTimeLong%>"  type="text" name="app_user.loginTimeLong"
			value="${requestScope.s.loginTimeLong}">


		</td>
		<td><span id="idSpan_AppUserT_loginTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . state%></td>
		<td><input id="idInput_AppUserT_state" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . state%>"  type="text" name="app_user.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_AppUserT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . createUser%></td>
		<td><input id="idInput_AppUserT_createUser" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . createUser%>"  type="text" name="app_user.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="idSpan_AppUserT_createUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . createTime%></td>
		<td><input id="idInput_AppUserT_createTime" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . createTime%>"  type="text" name="app_user.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_AppUserT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_AppUserT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . createTimeLong%></td>
		<td><input id="idInput_AppUserT_createTimeLong" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . createTimeLong%>"  type="text" name="app_user.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_AppUserT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . updateUser%></td>
		<td><input id="idInput_AppUserT_updateUser" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . updateUser%>"  type="text" name="app_user.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="idSpan_AppUserT_updateUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . updateTime%></td>
		<td><input id="idInput_AppUserT_updateTime" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . updateTime%>"  type="text" name="app_user.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_AppUserT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_AppUserT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . updateTimeLong%></td>
		<td><input id="idInput_AppUserT_updateTimeLong" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . updateTimeLong%>"  type="text" name="app_user.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_AppUserT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . tenantCode%></td>
		<td><input id="idInput_AppUserT_tenantCode" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . tenantCode%>"  type="text" name="app_user.tenantCode"
			value="${requestScope.s.tenantCode}">


		</td>
		<td><span id="idSpan_AppUserT_tenantCode"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . tenantNumber%></td>
		<td><input id="idInput_AppUserT_tenantNumber" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . tenantNumber%>"  type="text" name="app_user.tenantNumber"
			value="${requestScope.s.tenantNumber}">


		</td>
		<td><span id="idSpan_AppUserT_tenantNumber"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . desc%></td>
		<td><input id="idInput_AppUserT_desc" placeholder="请输入<%=all.cms.msg.admin.app_user.t.alias.AppUserTAlias . desc%>"  type="text" name="app_user.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_AppUserT_desc"></span>
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
var url = "${pageContext.request.contextPath}/all/cms/msg/admin/app_user/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","idSpan_AppUserT_idx","idInput_AppUserT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("用户主账号MAIN_APP_ACCOUNT_ID_","idSpan_AppUserT_mainAppAccountId","idInput_AppUserT_mainAppAccountId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("用户主键SYS_USER_ID_","idSpan_AppUserT_sysUserId","idInput_AppUserT_sysUserId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("APP用户名称APP_USER_NAME_","idSpan_AppUserT_appUserName","idInput_AppUserT_appUserName","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("APP用户编码APP_USER_CODE_","idSpan_AppUserT_appUserCode","idInput_AppUserT_appUserCode","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("APP用户类型APP_USER_TYPE_","idSpan_AppUserT_appUserType","idInput_AppUserT_appUserType","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("相当于表名","idSpan_AppUserT_tableName","idInput_AppUserT_tableName","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("头像HEAD_PORTRAIT_","idSpan_AppUserT_headPortrait","idInput_AppUserT_headPortrait","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("昵称NICKNAME_","idSpan_AppUserT_nickname","idInput_AppUserT_nickname","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("实名","idSpan_AppUserT_realname","idInput_AppUserT_realname","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("性别","idSpan_AppUserT_gender","idInput_AppUserT_gender","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("年龄","idSpan_AppUserT_age","idInput_AppUserT_age","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("数据权限等级","idSpan_AppUserT_permissionGrade","idInput_AppUserT_permissionGrade","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","idSpan_AppUserT_sn","idInput_AppUserT_sn","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("登录时间LOGIN_TIME_","idSpan_AppUserT_loginTime","idInput_AppUserT_loginTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("登录时间数字型LOGIN_TIME_LONG_","idSpan_AppUserT_loginTimeLong","idInput_AppUserT_loginTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_AppUserT_state","idInput_AppUserT_state","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者CREATE_USER_","idSpan_AppUserT_createUser","idInput_AppUserT_createUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_AppUserT_createTime","idInput_AppUserT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间数字型CREATE_TIME_LONG_","idSpan_AppUserT_createTimeLong","idInput_AppUserT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","idSpan_AppUserT_updateUser","idInput_AppUserT_updateUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_AppUserT_updateTime","idInput_AppUserT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间数字型UPDATE_TIME_LONG_","idSpan_AppUserT_updateTimeLong","idInput_AppUserT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("租户编码TENANT_CODE_","idSpan_AppUserT_tenantCode","idInput_AppUserT_tenantCode","CHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("租户编号TENANT_NUMBER_","idSpan_AppUserT_tenantNumber","idInput_AppUserT_tenantNumber","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_AppUserT_desc","idInput_AppUserT_desc","VARCHAR","yes","512","0","0",a,true);
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