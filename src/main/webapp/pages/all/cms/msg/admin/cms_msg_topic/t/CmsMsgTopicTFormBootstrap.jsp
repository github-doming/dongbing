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
	action="${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg_topic/t/save/bootstrap.do"
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
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . idx%></td>
		<td><input id="idInput_CmsMsgTopicT_idx" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . idx%>"  type="text" name="cms_msg_topic.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_idx"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . cmsMsgTopicId%></td>
		<td><input id="idInput_CmsMsgTopicT_cmsMsgTopicId" type="text" name="cms_msg_topic.cmsMsgTopicId"
			value="${requestScope.s.cmsMsgTopicId}"></td>
		<td><span id="idSpan_CmsMsgTopicT_cmsMsgTopicId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . sendUser%></td>
		<td><input id="idInput_CmsMsgTopicT_sendUser" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . sendUser%>"  type="text" name="cms_msg_topic.sendUser"
			value="${requestScope.s.sendUser}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_sendUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . receiveUser%></td>
		<td><input id="idInput_CmsMsgTopicT_receiveUser" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . receiveUser%>"  type="text" name="cms_msg_topic.receiveUser"
			value="${requestScope.s.receiveUser}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_receiveUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . msgTopicType%></td>
		<td><input id="idInput_CmsMsgTopicT_msgTopicType" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . msgTopicType%>"  type="text" name="cms_msg_topic.msgTopicType"
			value="${requestScope.s.msgTopicType}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_msgTopicType"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . msgTopicTitle%></td>
		<td><input id="idInput_CmsMsgTopicT_msgTopicTitle" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . msgTopicTitle%>"  type="text" name="cms_msg_topic.msgTopicTitle"
			value="${requestScope.s.msgTopicTitle}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_msgTopicTitle"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . content%></td>
		<td><input id="idInput_CmsMsgTopicT_content" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . content%>"  type="text" name="cms_msg_topic.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_content"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . urlView%></td>
		<td><input id="idInput_CmsMsgTopicT_urlView" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . urlView%>"  type="text" name="cms_msg_topic.urlView"
			value="${requestScope.s.urlView}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_urlView"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . reply%></td>
		<td><input id="idInput_CmsMsgTopicT_reply" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . reply%>"  type="text" name="cms_msg_topic.reply"
			value="${requestScope.s.reply}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_reply"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . keywords%></td>
		<td><input id="idInput_CmsMsgTopicT_keywords" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . keywords%>"  type="text" name="cms_msg_topic.keywords"
			value="${requestScope.s.keywords}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_keywords"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . createUser%></td>
		<td><input id="idInput_CmsMsgTopicT_createUser" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . createUser%>"  type="text" name="cms_msg_topic.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_createUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . createUserName%></td>
		<td><input id="idInput_CmsMsgTopicT_createUserName" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . createUserName%>"  type="text" name="cms_msg_topic.createUserName"
			value="${requestScope.s.createUserName}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_createUserName"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . createTime%></td>
		<td><input id="idInput_CmsMsgTopicT_createTime" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . createTime%>"  type="text" name="cms_msg_topic.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_CmsMsgTopicT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsMsgTopicT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . createTimeLong%></td>
		<td><input id="idInput_CmsMsgTopicT_createTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . createTimeLong%>"  type="text" name="cms_msg_topic.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . updateUser%></td>
		<td><input id="idInput_CmsMsgTopicT_updateUser" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . updateUser%>"  type="text" name="cms_msg_topic.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_updateUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . updateTime%></td>
		<td><input id="idInput_CmsMsgTopicT_updateTime" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . updateTime%>"  type="text" name="cms_msg_topic.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_CmsMsgTopicT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsMsgTopicT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . updateTimeLong%></td>
		<td><input id="idInput_CmsMsgTopicT_updateTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . updateTimeLong%>"  type="text" name="cms_msg_topic.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . state%></td>
		<td><input id="idInput_CmsMsgTopicT_state" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . state%>"  type="text" name="cms_msg_topic.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . desc%></td>
		<td><input id="idInput_CmsMsgTopicT_desc" placeholder="请输入<%=all.cms.msg.admin.cms_msg_topic.t.alias.CmsMsgTopicTAlias . desc%>"  type="text" name="cms_msg_topic.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_CmsMsgTopicT_desc"></span>
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
var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_msg_topic/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","idSpan_CmsMsgTopicT_idx","idInput_CmsMsgTopicT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送用户ID SEND_USER_","idSpan_CmsMsgTopicT_sendUser","idInput_CmsMsgTopicT_sendUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接收用户ID RECEIVE_USER_","idSpan_CmsMsgTopicT_receiveUser","idInput_CmsMsgTopicT_receiveUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型MSG_TOPIC_TYPE_","idSpan_CmsMsgTopicT_msgTopicType","idInput_CmsMsgTopicT_msgTopicType","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("标题MSG_TOPIC_TITLE_","idSpan_CmsMsgTopicT_msgTopicTitle","idInput_CmsMsgTopicT_msgTopicTitle","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内容CONTENT_","idSpan_CmsMsgTopicT_content","idInput_CmsMsgTopicT_content","TEXT","yes","65535","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("查看地址URL_VIEW_","idSpan_CmsMsgTopicT_urlView","idInput_CmsMsgTopicT_urlView","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否回复REPLY_","idSpan_CmsMsgTopicT_reply","idInput_CmsMsgTopicT_reply","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("关键字KEYWORDS_","idSpan_CmsMsgTopicT_keywords","idInput_CmsMsgTopicT_keywords","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者CREATE_USER_","idSpan_CmsMsgTopicT_createUser","idInput_CmsMsgTopicT_createUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者名称CREATE_USER_NAME_","idSpan_CmsMsgTopicT_createUserName","idInput_CmsMsgTopicT_createUserName","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsMsgTopicT_createTime","idInput_CmsMsgTopicT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsMsgTopicT_createTimeLong","idInput_CmsMsgTopicT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","idSpan_CmsMsgTopicT_updateUser","idInput_CmsMsgTopicT_updateUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsMsgTopicT_updateTime","idInput_CmsMsgTopicT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsMsgTopicT_updateTimeLong","idInput_CmsMsgTopicT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_CmsMsgTopicT_state","idInput_CmsMsgTopicT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_CmsMsgTopicT_desc","idInput_CmsMsgTopicT_desc","VARCHAR","yes","512","0","0",a,true);
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