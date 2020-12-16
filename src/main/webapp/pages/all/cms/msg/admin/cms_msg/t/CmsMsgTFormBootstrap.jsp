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
	action="${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg/t/save/bootstrap.do"
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
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . idx%></td>
		<td><input id="idInput_CmsMsgT_idx" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . idx%>"  type="text" name="cms_msg.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_CmsMsgT_idx"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . cmsMsgId%></td>
		<td><input id="idInput_CmsMsgT_cmsMsgId" type="text" name="cms_msg.cmsMsgId"
			value="${requestScope.s.cmsMsgId}"></td>
		<td><span id="idSpan_CmsMsgT_cmsMsgId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . cmsMsgTopicId%></td>
		<td><input id="idInput_CmsMsgT_cmsMsgTopicId" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . cmsMsgTopicId%>"  type="text" name="cms_msg.cmsMsgTopicId"
			value="${requestScope.s.cmsMsgTopicId}">


		</td>
		<td><span id="idSpan_CmsMsgT_cmsMsgTopicId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . sendUser%></td>
		<td><input id="idInput_CmsMsgT_sendUser" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . sendUser%>"  type="text" name="cms_msg.sendUser"
			value="${requestScope.s.sendUser}">


		</td>
		<td><span id="idSpan_CmsMsgT_sendUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . receiveUser%></td>
		<td><input id="idInput_CmsMsgT_receiveUser" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . receiveUser%>"  type="text" name="cms_msg.receiveUser"
			value="${requestScope.s.receiveUser}">


		</td>
		<td><span id="idSpan_CmsMsgT_receiveUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . msgType%></td>
		<td><input id="idInput_CmsMsgT_msgType" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . msgType%>"  type="text" name="cms_msg.msgType"
			value="${requestScope.s.msgType}">


		</td>
		<td><span id="idSpan_CmsMsgT_msgType"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . msgTitle%></td>
		<td><input id="idInput_CmsMsgT_msgTitle" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . msgTitle%>"  type="text" name="cms_msg.msgTitle"
			value="${requestScope.s.msgTitle}">


		</td>
		<td><span id="idSpan_CmsMsgT_msgTitle"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . content%></td>
		<td><input id="idInput_CmsMsgT_content" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . content%>"  type="text" name="cms_msg.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="idSpan_CmsMsgT_content"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . urlView%></td>
		<td><input id="idInput_CmsMsgT_urlView" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . urlView%>"  type="text" name="cms_msg.urlView"
			value="${requestScope.s.urlView}">


		</td>
		<td><span id="idSpan_CmsMsgT_urlView"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . reply%></td>
		<td><input id="idInput_CmsMsgT_reply" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . reply%>"  type="text" name="cms_msg.reply"
			value="${requestScope.s.reply}">


		</td>
		<td><span id="idSpan_CmsMsgT_reply"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createUser%></td>
		<td><input id="idInput_CmsMsgT_createUser" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createUser%>"  type="text" name="cms_msg.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="idSpan_CmsMsgT_createUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createTime%></td>
		<td><input id="idInput_CmsMsgT_createTime" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createTime%>"  type="text" name="cms_msg.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_CmsMsgT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsMsgT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createTimeLong%></td>
		<td><input id="idInput_CmsMsgT_createTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . createTimeLong%>"  type="text" name="cms_msg.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_CmsMsgT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateUser%></td>
		<td><input id="idInput_CmsMsgT_updateUser" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateUser%>"  type="text" name="cms_msg.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="idSpan_CmsMsgT_updateUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateTime%></td>
		<td><input id="idInput_CmsMsgT_updateTime" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateTime%>"  type="text" name="cms_msg.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_CmsMsgT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsMsgT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateTimeLong%></td>
		<td><input id="idInput_CmsMsgT_updateTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . updateTimeLong%>"  type="text" name="cms_msg.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_CmsMsgT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . state%></td>
		<td><input id="idInput_CmsMsgT_state" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . state%>"  type="text" name="cms_msg.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_CmsMsgT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . desc%></td>
		<td><input id="idInput_CmsMsgT_desc" placeholder="请输入<%=all.cms.msg.admin.cms_msg.t.alias.CmsMsgTAlias . desc%>"  type="text" name="cms_msg.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_CmsMsgT_desc"></span>
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
var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","idSpan_CmsMsgT_idx","idInput_CmsMsgT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CMS_消息主题主键 CMS_MSG_TOPIC_ID_","idSpan_CmsMsgT_cmsMsgTopicId","idInput_CmsMsgT_cmsMsgTopicId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送用户ID SEND_USER_","idSpan_CmsMsgT_sendUser","idInput_CmsMsgT_sendUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接收用户ID RECEIVE_USER_","idSpan_CmsMsgT_receiveUser","idInput_CmsMsgT_receiveUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型MSG_TYPE_","idSpan_CmsMsgT_msgType","idInput_CmsMsgT_msgType","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("标题MSG_TITLE_","idSpan_CmsMsgT_msgTitle","idInput_CmsMsgT_msgTitle","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内容CONTENT_","idSpan_CmsMsgT_content","idInput_CmsMsgT_content","TEXT","yes","65535","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("查看地址URL_VIEW_","idSpan_CmsMsgT_urlView","idInput_CmsMsgT_urlView","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否回复REPLY_","idSpan_CmsMsgT_reply","idInput_CmsMsgT_reply","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者CREATE_USER_","idSpan_CmsMsgT_createUser","idInput_CmsMsgT_createUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsMsgT_createTime","idInput_CmsMsgT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsMsgT_createTimeLong","idInput_CmsMsgT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","idSpan_CmsMsgT_updateUser","idInput_CmsMsgT_updateUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsMsgT_updateTime","idInput_CmsMsgT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsMsgT_updateTimeLong","idInput_CmsMsgT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_CmsMsgT_state","idInput_CmsMsgT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_CmsMsgT_desc","idInput_CmsMsgT_desc","VARCHAR","yes","512","0","0",a,true);
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