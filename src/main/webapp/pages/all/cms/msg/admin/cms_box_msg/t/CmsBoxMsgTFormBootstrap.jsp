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
	action="${pageContext.request.contextPath}/all/cms/msg/admin/cms_box_msg/t/save/bootstrap.do"
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
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . idx%></td>
		<td><input id="idInput_CmsBoxMsgT_idx" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . idx%>"  type="text" name="cms_box_msg.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_idx"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . cmsBoxMsgId%></td>
		<td><input id="idInput_CmsBoxMsgT_cmsBoxMsgId" type="text" name="cms_box_msg.cmsBoxMsgId"
			value="${requestScope.s.cmsBoxMsgId}"></td>
		<td><span id="idSpan_CmsBoxMsgT_cmsBoxMsgId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . cmsBoxTopicId%></td>
		<td><input id="idInput_CmsBoxMsgT_cmsBoxTopicId" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . cmsBoxTopicId%>"  type="text" name="cms_box_msg.cmsBoxTopicId"
			value="${requestScope.s.cmsBoxTopicId}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_cmsBoxTopicId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . cmsMsgTopicId%></td>
		<td><input id="idInput_CmsBoxMsgT_cmsMsgTopicId" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . cmsMsgTopicId%>"  type="text" name="cms_box_msg.cmsMsgTopicId"
			value="${requestScope.s.cmsMsgTopicId}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_cmsMsgTopicId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . appUserId%></td>
		<td><input id="idInput_CmsBoxMsgT_appUserId" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . appUserId%>"  type="text" name="cms_box_msg.appUserId"
			value="${requestScope.s.appUserId}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_appUserId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . tableName%></td>
		<td><input id="idInput_CmsBoxMsgT_tableName" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . tableName%>"  type="text" name="cms_box_msg.tableName"
			value="${requestScope.s.tableName}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_tableName"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . tableId%></td>
		<td><input id="idInput_CmsBoxMsgT_tableId" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . tableId%>"  type="text" name="cms_box_msg.tableId"
			value="${requestScope.s.tableId}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_tableId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . sendUser%></td>
		<td><input id="idInput_CmsBoxMsgT_sendUser" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . sendUser%>"  type="text" name="cms_box_msg.sendUser"
			value="${requestScope.s.sendUser}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_sendUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . receiveUser%></td>
		<td><input id="idInput_CmsBoxMsgT_receiveUser" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . receiveUser%>"  type="text" name="cms_box_msg.receiveUser"
			value="${requestScope.s.receiveUser}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_receiveUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . channel%></td>
		<td><input id="idInput_CmsBoxMsgT_channel" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . channel%>"  type="text" name="cms_box_msg.channel"
			value="${requestScope.s.channel}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_channel"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . msgType%></td>
		<td><input id="idInput_CmsBoxMsgT_msgType" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . msgType%>"  type="text" name="cms_box_msg.msgType"
			value="${requestScope.s.msgType}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_msgType"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . msgTitle%></td>
		<td><input id="idInput_CmsBoxMsgT_msgTitle" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . msgTitle%>"  type="text" name="cms_box_msg.msgTitle"
			value="${requestScope.s.msgTitle}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_msgTitle"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . content%></td>
		<td><input id="idInput_CmsBoxMsgT_content" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . content%>"  type="text" name="cms_box_msg.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_content"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . createTime%></td>
		<td><input id="idInput_CmsBoxMsgT_createTime" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . createTime%>"  type="text" name="cms_box_msg.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_CmsBoxMsgT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsBoxMsgT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . createTimeLong%></td>
		<td><input id="idInput_CmsBoxMsgT_createTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . createTimeLong%>"  type="text" name="cms_box_msg.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . updateTime%></td>
		<td><input id="idInput_CmsBoxMsgT_updateTime" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . updateTime%>"  type="text" name="cms_box_msg.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_CmsBoxMsgT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_CmsBoxMsgT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . updateTimeLong%></td>
		<td><input id="idInput_CmsBoxMsgT_updateTimeLong" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . updateTimeLong%>"  type="text" name="cms_box_msg.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . state%></td>
		<td><input id="idInput_CmsBoxMsgT_state" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . state%>"  type="text" name="cms_box_msg.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . desc%></td>
		<td><input id="idInput_CmsBoxMsgT_desc" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . desc%>"  type="text" name="cms_box_msg.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_desc"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . extTableName%></td>
		<td><input id="idInput_CmsBoxMsgT_extTableName" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . extTableName%>"  type="text" name="cms_box_msg.extTableName"
			value="${requestScope.s.extTableName}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_extTableName"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . extTableId%></td>
		<td><input id="idInput_CmsBoxMsgT_extTableId" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . extTableId%>"  type="text" name="cms_box_msg.extTableId"
			value="${requestScope.s.extTableId}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_extTableId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . extTableDesc%></td>
		<td><input id="idInput_CmsBoxMsgT_extTableDesc" placeholder="请输入<%=all.cms.msg.admin.cms_box_msg.t.alias.CmsBoxMsgTAlias . extTableDesc%>"  type="text" name="cms_box_msg.extTableDesc"
			value="${requestScope.s.extTableDesc}">


		</td>
		<td><span id="idSpan_CmsBoxMsgT_extTableDesc"></span>
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
var url = "${pageContext.request.contextPath}/all/cms/msg/admin/cms_box_msg/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","idSpan_CmsBoxMsgT_idx","idInput_CmsBoxMsgT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CMS个人信箱_主题主键CMS_BOX_TOPIC_ID_","idSpan_CmsBoxMsgT_cmsBoxTopicId","idInput_CmsBoxMsgT_cmsBoxTopicId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CMS_消息主题主键 CMS_MSG_TOPIC_ID_","idSpan_CmsBoxMsgT_cmsMsgTopicId","idInput_CmsBoxMsgT_cmsMsgTopicId","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("用户主键APP_USER_ID_","idSpan_CmsBoxMsgT_appUserId","idInput_CmsBoxMsgT_appUserId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("相当于表名","idSpan_CmsBoxMsgT_tableName","idInput_CmsBoxMsgT_tableName","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("相当于表的ID","idSpan_CmsBoxMsgT_tableId","idInput_CmsBoxMsgT_tableId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送用户ID SEND_USER_","idSpan_CmsBoxMsgT_sendUser","idInput_CmsBoxMsgT_sendUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接收用户ID RECEIVE_USER_","idSpan_CmsBoxMsgT_receiveUser","idInput_CmsBoxMsgT_receiveUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送渠道(邮件，短信，微信)(用逗号分割)CHANNEL_","idSpan_CmsBoxMsgT_channel","idInput_CmsBoxMsgT_channel","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型MSG_TYPE_","idSpan_CmsBoxMsgT_msgType","idInput_CmsBoxMsgT_msgType","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("消息标题MSG_TITLE_","idSpan_CmsBoxMsgT_msgTitle","idInput_CmsBoxMsgT_msgTitle","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内容CONTENT_","idSpan_CmsBoxMsgT_content","idInput_CmsBoxMsgT_content","TEXT","yes","65535","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsBoxMsgT_createTime","idInput_CmsBoxMsgT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_CmsBoxMsgT_createTimeLong","idInput_CmsBoxMsgT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsBoxMsgT_updateTime","idInput_CmsBoxMsgT_updateTime","TIMESTAMP","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_CmsBoxMsgT_updateTimeLong","idInput_CmsBoxMsgT_updateTimeLong","BIGINT","no","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_CmsBoxMsgT_state","idInput_CmsBoxMsgT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_CmsBoxMsgT_desc","idInput_CmsBoxMsgT_desc","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("EXT_TABLE_NAME_","idSpan_CmsBoxMsgT_extTableName","idInput_CmsBoxMsgT_extTableName","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("EXT_TABLE_ID_","idSpan_CmsBoxMsgT_extTableId","idInput_CmsBoxMsgT_extTableId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("EXT_TABLE_DESC_","idSpan_CmsBoxMsgT_extTableDesc","idInput_CmsBoxMsgT_extTableDesc","VARCHAR","yes","64","0","0",a,true);
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