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
	action="${pageContext.request.contextPath}/all/cms/msg/admin/cms_box_topic/t/save/bootstrap.do"
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
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . idx%></td>
		<td><input id="idInput_CmsBoxTopicT_idx" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . idx%>"  type="text" name="cms_box_topic.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_idx"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsBoxTopicId%></td>
		<td><input id="idInput_CmsBoxTopicT_cmsBoxTopicId" type="text" name="cms_box_topic.cmsBoxTopicId"
			value="${requestScope.s.cmsBoxTopicId}"></td>
		<td><span id="idSpan_CmsBoxTopicT_cmsBoxTopicId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsSendBoxDraftId%></td>
		<td><input id="idInput_CmsBoxTopicT_cmsSendBoxDraftId" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsSendBoxDraftId%>"  type="text" name="cms_box_topic.cmsSendBoxDraftId"
			value="${requestScope.s.cmsSendBoxDraftId}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_cmsSendBoxDraftId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsMsgTopicId%></td>
		<td><input id="idInput_CmsBoxTopicT_cmsMsgTopicId" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . cmsMsgTopicId%>"  type="text" name="cms_box_topic.cmsMsgTopicId"
			value="${requestScope.s.cmsMsgTopicId}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_cmsMsgTopicId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . appUserId%></td>
		<td><input id="idInput_CmsBoxTopicT_appUserId" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . appUserId%>"  type="text" name="cms_box_topic.appUserId"
			value="${requestScope.s.appUserId}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_appUserId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . tableName%></td>
		<td><input id="idInput_CmsBoxTopicT_tableName" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . tableName%>"  type="text" name="cms_box_topic.tableName"
			value="${requestScope.s.tableName}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_tableName"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . tableId%></td>
		<td><input id="idInput_CmsBoxTopicT_tableId" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . tableId%>"  type="text" name="cms_box_topic.tableId"
			value="${requestScope.s.tableId}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_tableId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . sendUser%></td>
		<td><input id="idInput_CmsBoxTopicT_sendUser" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . sendUser%>"  type="text" name="cms_box_topic.sendUser"
			value="${requestScope.s.sendUser}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_sendUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . receiveUser%></td>
		<td><input id="idInput_CmsBoxTopicT_receiveUser" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . receiveUser%>"  type="text" name="cms_box_topic.receiveUser"
			value="${requestScope.s.receiveUser}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_receiveUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . channel%></td>
		<td><input id="idInput_CmsBoxTopicT_channel" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . channel%>"  type="text" name="cms_box_topic.channel"
			value="${requestScope.s.channel}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_channel"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . topicType%></td>
		<td><input id="idInput_CmsBoxTopicT_topicType" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . topicType%>"  type="text" name="cms_box_topic.topicType"
			value="${requestScope.s.topicType}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_topicType"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . topicTitle%></td>
		<td><input id="idInput_CmsBoxTopicT_topicTitle" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . topicTitle%>"  type="text" name="cms_box_topic.topicTitle"
			value="${requestScope.s.topicTitle}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_topicTitle"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . content%></td>
		<td><input id="idInput_CmsBoxTopicT_content" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . content%>"  type="text" name="cms_box_topic.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_content"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . createTime%></td>
		<td><input id="idInput_CmsBoxTopicT_createTime" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . createTime%>"  type="text" name="cms_box_topic.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_CmsBoxTopicT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsBoxTopicT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . createTimeLong%></td>
		<td><input id="idInput_CmsBoxTopicT_createTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . createTimeLong%>"  type="text" name="cms_box_topic.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . updateTime%></td>
		<td><input id="idInput_CmsBoxTopicT_updateTime" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . updateTime%>"  type="text" name="cms_box_topic.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_CmsBoxTopicT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsBoxTopicT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . updateTimeLong%></td>
		<td><input id="idInput_CmsBoxTopicT_updateTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . updateTimeLong%>"  type="text" name="cms_box_topic.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . state%></td>
		<td><input id="idInput_CmsBoxTopicT_state" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . state%>"  type="text" name="cms_box_topic.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . desc%></td>
		<td><input id="idInput_CmsBoxTopicT_desc" placeholder="请输入<%=all.cms.msg.admin.cms_box_topic.t.alias.CmsBoxTopicTAlias . desc%>"  type="text" name="cms_box_topic.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_CmsBoxTopicT_desc"></span>
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
var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_box_topic/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","idSpan_CmsBoxTopicT_idx","idInput_CmsBoxTopicT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CMS个人发件箱_主题草稿主键CMS_SEND_BOX_DRAFT_ID_","idSpan_CmsBoxTopicT_cmsSendBoxDraftId","idInput_CmsBoxTopicT_cmsSendBoxDraftId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CMS_消息主题主键 CMS_MSG_TOPIC_ID_","idSpan_CmsBoxTopicT_cmsMsgTopicId","idInput_CmsBoxTopicT_cmsMsgTopicId","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("用户主键APP_USER_ID_","idSpan_CmsBoxTopicT_appUserId","idInput_CmsBoxTopicT_appUserId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("相当于表名","idSpan_CmsBoxTopicT_tableName","idInput_CmsBoxTopicT_tableName","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("相当于表的ID","idSpan_CmsBoxTopicT_tableId","idInput_CmsBoxTopicT_tableId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送用户ID SEND_USER_","idSpan_CmsBoxTopicT_sendUser","idInput_CmsBoxTopicT_sendUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接收用户ID RECEIVE_USER_","idSpan_CmsBoxTopicT_receiveUser","idInput_CmsBoxTopicT_receiveUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送渠道(邮件，短信，微信)(用逗号分割)CHANNEL_","idSpan_CmsBoxTopicT_channel","idInput_CmsBoxTopicT_channel","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型TOPIC_TYPE_","idSpan_CmsBoxTopicT_topicType","idInput_CmsBoxTopicT_topicType","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("主题标题TOPIC_TITLE_","idSpan_CmsBoxTopicT_topicTitle","idInput_CmsBoxTopicT_topicTitle","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内容CONTENT_","idSpan_CmsBoxTopicT_content","idInput_CmsBoxTopicT_content","TEXT","yes","65535","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsBoxTopicT_createTime","idInput_CmsBoxTopicT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsBoxTopicT_createTimeLong","idInput_CmsBoxTopicT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsBoxTopicT_updateTime","idInput_CmsBoxTopicT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsBoxTopicT_updateTimeLong","idInput_CmsBoxTopicT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_CmsBoxTopicT_state","idInput_CmsBoxTopicT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_CmsBoxTopicT_desc","idInput_CmsBoxTopicT_desc","VARCHAR","yes","512","0","0",a,true);
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