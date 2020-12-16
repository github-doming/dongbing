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
	action="${pageContext.request.contextPath}/all/gen/wx_p_mo_text/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . idx%></td>
		<td><input id="id_input$wx_p_mo_text$IDX_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . idx%>"  type="text" name="wx_p_mo_text.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$wx_p_mo_text$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . wxPMoTextId%></td>
		<td><input id="id_input$wx_p_mo_text$WX_P_MO_TEXT_ID_" type="text" name="wx_p_mo_text.wxPMoTextId"
			value="${requestScope.s.wxPMoTextId}"></td>
		<td><span id="id_span$wx_p_mo_text$WX_P_MO_TEXT_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . toUserNaem%></td>
		<td><input id="id_input$wx_p_mo_text$TO_USER_NAEM__" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . toUserNaem%>"  type="text" name="wx_p_mo_text.toUserNaem"
			value="${requestScope.s.toUserNaem}">


		</td>
		<td><span id="id_span$wx_p_mo_text$TO_USER_NAEM__"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . fromUserName%></td>
		<td><input id="id_input$wx_p_mo_text$FROM_USER_NAME_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . fromUserName%>"  type="text" name="wx_p_mo_text.fromUserName"
			value="${requestScope.s.fromUserName}">


		</td>
		<td><span id="id_span$wx_p_mo_text$FROM_USER_NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . msgType%></td>
		<td><input id="id_input$wx_p_mo_text$MSG_TYPE_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . msgType%>"  type="text" name="wx_p_mo_text.msgType"
			value="${requestScope.s.msgType}">


		</td>
		<td><span id="id_span$wx_p_mo_text$MSG_TYPE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . content%></td>
		<td><input id="id_input$wx_p_mo_text$CONTENT_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . content%>"  type="text" name="wx_p_mo_text.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="id_span$wx_p_mo_text$CONTENT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . msgid%></td>
		<td><input id="id_input$wx_p_mo_text$MSGID_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . msgid%>"  type="text" name="wx_p_mo_text.msgid"
			value="${requestScope.s.msgid}">


		</td>
		<td><span id="id_span$wx_p_mo_text$MSGID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . appUserId%></td>
		<td><input id="id_input$wx_p_mo_text$APP_USER_ID_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . appUserId%>"  type="text" name="wx_p_mo_text.appUserId"
			value="${requestScope.s.appUserId}">


		</td>
		<td><span id="id_span$wx_p_mo_text$APP_USER_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . fullname%></td>
		<td><input id="id_input$wx_p_mo_text$FULLNAME_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . fullname%>"  type="text" name="wx_p_mo_text.fullname"
			value="${requestScope.s.fullname}">


		</td>
		<td><span id="id_span$wx_p_mo_text$FULLNAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . weixinId%></td>
		<td><input id="id_input$wx_p_mo_text$WEIXIN_ID_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . weixinId%>"  type="text" name="wx_p_mo_text.weixinId"
			value="${requestScope.s.weixinId}">


		</td>
		<td><span id="id_span$wx_p_mo_text$WEIXIN_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . nickName%></td>
		<td><input id="id_input$wx_p_mo_text$NICK_NAME_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . nickName%>"  type="text" name="wx_p_mo_text.nickName"
			value="${requestScope.s.nickName}">


		</td>
		<td><span id="id_span$wx_p_mo_text$NICK_NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . createTime%></td>
		<td><input id="id_input$wx_p_mo_text$CREATE_TIME_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . createTime%>"  type="text" name="wx_p_mo_text.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_mo_text$CREATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_mo_text$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . createTimeLong%></td>
		<td><input id="id_input$wx_p_mo_text$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . createTimeLong%>"  type="text" name="wx_p_mo_text.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$wx_p_mo_text$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . updateTimeLong%></td>
		<td><input id="id_input$wx_p_mo_text$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . updateTimeLong%>"  type="text" name="wx_p_mo_text.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$wx_p_mo_text$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . updateTime%></td>
		<td><input id="id_input$wx_p_mo_text$UPDATE_TIME_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . updateTime%>"  type="text" name="wx_p_mo_text.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_mo_text$UPDATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_mo_text$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . state%></td>
		<td><input id="id_input$wx_p_mo_text$STATE_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . state%>"  type="text" name="wx_p_mo_text.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$wx_p_mo_text$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . desc%></td>
		<td><input id="id_input$wx_p_mo_text$DESC_" placeholder="请输入<%=all.gen.wx_p_mo_text.t.alias.WxPMoTextTAlias . desc%>"  type="text" name="wx_p_mo_text.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$wx_p_mo_text$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/gen/wx_p_mo_text/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$wx_p_mo_text$IDX_","id_input$wx_p_mo_text$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接收?????户openID","id_span$wx_p_mo_text$TO_USER_NAEM__","id_input$wx_p_mo_text$TO_USER_NAEM__","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送的用???openID","id_span$wx_p_mo_text$FROM_USER_NAME_","id_input$wx_p_mo_text$FROM_USER_NAME_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("消息类型","id_span$wx_p_mo_text$MSG_TYPE_","id_input$wx_p_mo_text$MSG_TYPE_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内容","id_span$wx_p_mo_text$CONTENT_","id_input$wx_p_mo_text$CONTENT_","TEXT","yes","65535","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("消息id","id_span$wx_p_mo_text$MSGID_","id_input$wx_p_mo_text$MSGID_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("APP用户主键APP_USER_ID_","id_span$wx_p_mo_text$APP_USER_ID_","id_input$wx_p_mo_text$APP_USER_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("姓名","id_span$wx_p_mo_text$FULLNAME_","id_input$wx_p_mo_text$FULLNAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("微信号ID","id_span$wx_p_mo_text$WEIXIN_ID_","id_input$wx_p_mo_text$WEIXIN_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("微信用户昵称","id_span$wx_p_mo_text$NICK_NAME_","id_input$wx_p_mo_text$NICK_NAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间 CREATE_TIME_","id_span$wx_p_mo_text$CREATE_TIME_","id_input$wx_p_mo_text$CREATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间数字型 CREATE_TIME_LONG_","id_span$wx_p_mo_text$CREATE_TIME_LONG_","id_input$wx_p_mo_text$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_mo_text$UPDATE_TIME_LONG_","id_input$wx_p_mo_text$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_mo_text$UPDATE_TIME_","id_input$wx_p_mo_text$UPDATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$wx_p_mo_text$STATE_","id_input$wx_p_mo_text$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$wx_p_mo_text$DESC_","id_input$wx_p_mo_text$DESC_","VARCHAR","yes","512","0","0",a,true);
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