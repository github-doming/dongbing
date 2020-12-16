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
	action="${pageContext.request.contextPath}/all/cms/msg/admin/cms_send_box_draft/t/save/bootstrap.do"
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
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . idx%></td>
		<td><input id="idInput_CmsSendBoxDraftT_idx" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . idx%>"  type="text" name="cms_send_box_draft.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_idx"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . cmsSendBoxDraftId%></td>
		<td><input id="idInput_CmsSendBoxDraftT_cmsSendBoxDraftId" type="text" name="cms_send_box_draft.cmsSendBoxDraftId"
			value="${requestScope.s.cmsSendBoxDraftId}"></td>
		<td><span id="idSpan_CmsSendBoxDraftT_cmsSendBoxDraftId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . tableName%></td>
		<td><input id="idInput_CmsSendBoxDraftT_tableName" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . tableName%>"  type="text" name="cms_send_box_draft.tableName"
			value="${requestScope.s.tableName}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_tableName"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . sendUser%></td>
		<td><input id="idInput_CmsSendBoxDraftT_sendUser" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . sendUser%>"  type="text" name="cms_send_box_draft.sendUser"
			value="${requestScope.s.sendUser}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_sendUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . receiveUserList%></td>
		<td><input id="idInput_CmsSendBoxDraftT_receiveUserList" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . receiveUserList%>"  type="text" name="cms_send_box_draft.receiveUserList"
			value="${requestScope.s.receiveUserList}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_receiveUserList"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . channel%></td>
		<td><input id="idInput_CmsSendBoxDraftT_channel" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . channel%>"  type="text" name="cms_send_box_draft.channel"
			value="${requestScope.s.channel}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_channel"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . topicType%></td>
		<td><input id="idInput_CmsSendBoxDraftT_topicType" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . topicType%>"  type="text" name="cms_send_box_draft.topicType"
			value="${requestScope.s.topicType}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_topicType"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . topicTitle%></td>
		<td><input id="idInput_CmsSendBoxDraftT_topicTitle" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . topicTitle%>"  type="text" name="cms_send_box_draft.topicTitle"
			value="${requestScope.s.topicTitle}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_topicTitle"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . content%></td>
		<td><input id="idInput_CmsSendBoxDraftT_content" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . content%>"  type="text" name="cms_send_box_draft.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_content"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . createTime%></td>
		<td><input id="idInput_CmsSendBoxDraftT_createTime" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . createTime%>"  type="text" name="cms_send_box_draft.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_CmsSendBoxDraftT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . createTimeLong%></td>
		<td><input id="idInput_CmsSendBoxDraftT_createTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . createTimeLong%>"  type="text" name="cms_send_box_draft.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . updateTime%></td>
		<td><input id="idInput_CmsSendBoxDraftT_updateTime" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . updateTime%>"  type="text" name="cms_send_box_draft.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_CmsSendBoxDraftT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . updateTimeLong%></td>
		<td><input id="idInput_CmsSendBoxDraftT_updateTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . updateTimeLong%>"  type="text" name="cms_send_box_draft.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . state%></td>
		<td><input id="idInput_CmsSendBoxDraftT_state" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . state%>"  type="text" name="cms_send_box_draft.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . desc%></td>
		<td><input id="idInput_CmsSendBoxDraftT_desc" placeholder="请输入<%=all.cms.msg.admin.cms_send_box_draft.t.alias.CmsSendBoxDraftTAlias . desc%>"  type="text" name="cms_send_box_draft.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_CmsSendBoxDraftT_desc"></span>
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
var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_send_box_draft/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","idSpan_CmsSendBoxDraftT_idx","idInput_CmsSendBoxDraftT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("相当于表名","idSpan_CmsSendBoxDraftT_tableName","idInput_CmsSendBoxDraftT_tableName","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送用户ID SEND_USER_","idSpan_CmsSendBoxDraftT_sendUser","idInput_CmsSendBoxDraftT_sendUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接收用户ID(多个)RECEIVE_USER_LIST_","idSpan_CmsSendBoxDraftT_receiveUserList","idInput_CmsSendBoxDraftT_receiveUserList","VARCHAR","yes","1024","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送渠道(邮件，短信，微信)(用逗号分割)CHANNEL_","idSpan_CmsSendBoxDraftT_channel","idInput_CmsSendBoxDraftT_channel","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型TOPIC_TYPE_","idSpan_CmsSendBoxDraftT_topicType","idInput_CmsSendBoxDraftT_topicType","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("主题标题TOPIC_TITLE_","idSpan_CmsSendBoxDraftT_topicTitle","idInput_CmsSendBoxDraftT_topicTitle","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内容CONTENT_","idSpan_CmsSendBoxDraftT_content","idInput_CmsSendBoxDraftT_content","TEXT","yes","65535","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsSendBoxDraftT_createTime","idInput_CmsSendBoxDraftT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsSendBoxDraftT_createTimeLong","idInput_CmsSendBoxDraftT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsSendBoxDraftT_updateTime","idInput_CmsSendBoxDraftT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsSendBoxDraftT_updateTimeLong","idInput_CmsSendBoxDraftT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_CmsSendBoxDraftT_state","idInput_CmsSendBoxDraftT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_CmsSendBoxDraftT_desc","idInput_CmsSendBoxDraftT_desc","VARCHAR","yes","512","0","0",a,true);
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