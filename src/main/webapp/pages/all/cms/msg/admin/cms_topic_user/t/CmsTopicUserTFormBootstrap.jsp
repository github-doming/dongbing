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
	action="${pageContext.request.contextPath}/all/cms/msg/admin/cms_topic_user/t/save/bootstrap.do"
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
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . idx%></td>
		<td><input id="idInput_CmsTopicUserT_idx" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . idx%>"  type="text" name="cms_topic_user.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_CmsTopicUserT_idx"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . cmsTopicUserId%></td>
		<td><input id="idInput_CmsTopicUserT_cmsTopicUserId" type="text" name="cms_topic_user.cmsTopicUserId"
			value="${requestScope.s.cmsTopicUserId}"></td>
		<td><span id="idSpan_CmsTopicUserT_cmsTopicUserId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . appUserId%></td>
		<td><input id="idInput_CmsTopicUserT_appUserId" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . appUserId%>"  type="text" name="cms_topic_user.appUserId"
			value="${requestScope.s.appUserId}">


		</td>
		<td><span id="idSpan_CmsTopicUserT_appUserId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . cmsTopicId%></td>
		<td><input id="idInput_CmsTopicUserT_cmsTopicId" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . cmsTopicId%>"  type="text" name="cms_topic_user.cmsTopicId"
			value="${requestScope.s.cmsTopicId}">


		</td>
		<td><span id="idSpan_CmsTopicUserT_cmsTopicId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . tableName%></td>
		<td><input id="idInput_CmsTopicUserT_tableName" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . tableName%>"  type="text" name="cms_topic_user.tableName"
			value="${requestScope.s.tableName}">


		</td>
		<td><span id="idSpan_CmsTopicUserT_tableName"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . createTime%></td>
		<td><input id="idInput_CmsTopicUserT_createTime" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . createTime%>"  type="text" name="cms_topic_user.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_CmsTopicUserT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsTopicUserT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . createTimeLong%></td>
		<td><input id="idInput_CmsTopicUserT_createTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . createTimeLong%>"  type="text" name="cms_topic_user.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_CmsTopicUserT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . updateTime%></td>
		<td><input id="idInput_CmsTopicUserT_updateTime" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . updateTime%>"  type="text" name="cms_topic_user.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_CmsTopicUserT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsTopicUserT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . updateTimeLong%></td>
		<td><input id="idInput_CmsTopicUserT_updateTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . updateTimeLong%>"  type="text" name="cms_topic_user.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_CmsTopicUserT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . state%></td>
		<td><input id="idInput_CmsTopicUserT_state" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . state%>"  type="text" name="cms_topic_user.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_CmsTopicUserT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . desc%></td>
		<td><input id="idInput_CmsTopicUserT_desc" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . desc%>"  type="text" name="cms_topic_user.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_CmsTopicUserT_desc"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . topicType%></td>
		<td><input id="idInput_CmsTopicUserT_topicType" placeholder="请输入<%=all.cms.msg.admin.cms_topic_user.t.alias.CmsTopicUserTAlias . topicType%>"  type="text" name="cms_topic_user.topicType"
			value="${requestScope.s.topicType}">


		</td>
		<td><span id="idSpan_CmsTopicUserT_topicType"></span>
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
var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_topic_user/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","idSpan_CmsTopicUserT_idx","idInput_CmsTopicUserT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("用户主键APP_USER_ID_","idSpan_CmsTopicUserT_appUserId","idInput_CmsTopicUserT_appUserId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CMS_主题主键CMS_TOPIC_ID_","idSpan_CmsTopicUserT_cmsTopicId","idInput_CmsTopicUserT_cmsTopicId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("相当于表名","idSpan_CmsTopicUserT_tableName","idInput_CmsTopicUserT_tableName","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsTopicUserT_createTime","idInput_CmsTopicUserT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsTopicUserT_createTimeLong","idInput_CmsTopicUserT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsTopicUserT_updateTime","idInput_CmsTopicUserT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsTopicUserT_updateTimeLong","idInput_CmsTopicUserT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_CmsTopicUserT_state","idInput_CmsTopicUserT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_CmsTopicUserT_desc","idInput_CmsTopicUserT_desc","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("TOPIC_TYPE_","idSpan_CmsTopicUserT_topicType","idInput_CmsTopicUserT_topicType","CHAR","yes","16","0","0",a,true);
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