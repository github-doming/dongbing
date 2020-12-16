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
	action="${pageContext.request.contextPath}/all/gen/wx_p_msg/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . idx%></td>
		<td><input id="id_input$wx_p_msg$IDX_" placeholder="请输入<%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . idx%>"  type="text" name="wx_p_msg.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$wx_p_msg$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . wxPMsgId%></td>
		<td><input id="id_input$wx_p_msg$WX_P_MSG_ID_" type="text" name="wx_p_msg.wxPMsgId"
			value="${requestScope.s.wxPMsgId}"></td>
		<td><span id="id_span$wx_p_msg$WX_P_MSG_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . agentid%></td>
		<td><input id="id_input$wx_p_msg$AGENTID_" placeholder="请输入<%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . agentid%>"  type="text" name="wx_p_msg.agentid"
			value="${requestScope.s.agentid}">


		</td>
		<td><span id="id_span$wx_p_msg$AGENTID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . userId%></td>
		<td><input id="id_input$wx_p_msg$USER_ID_" placeholder="请输入<%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . userId%>"  type="text" name="wx_p_msg.userId"
			value="${requestScope.s.userId}">


		</td>
		<td><span id="id_span$wx_p_msg$USER_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . content%></td>
		<td><input id="id_input$wx_p_msg$CONTENT_" placeholder="请输入<%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . content%>"  type="text" name="wx_p_msg.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="id_span$wx_p_msg$CONTENT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . createTime%></td>
		<td><input id="id_input$wx_p_msg$CREATE_TIME_" placeholder="请输入<%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . createTime%>"  type="text" name="wx_p_msg.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_msg$CREATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_msg$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . createTimeLong%></td>
		<td><input id="id_input$wx_p_msg$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . createTimeLong%>"  type="text" name="wx_p_msg.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$wx_p_msg$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . updateTimeLong%></td>
		<td><input id="id_input$wx_p_msg$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . updateTimeLong%>"  type="text" name="wx_p_msg.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$wx_p_msg$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . updateTime%></td>
		<td><input id="id_input$wx_p_msg$UPDATE_TIME_" placeholder="请输入<%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . updateTime%>"  type="text" name="wx_p_msg.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_msg$UPDATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_msg$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . state%></td>
		<td><input id="id_input$wx_p_msg$STATE_" placeholder="请输入<%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . state%>"  type="text" name="wx_p_msg.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$wx_p_msg$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . desc%></td>
		<td><input id="id_input$wx_p_msg$DESC_" placeholder="请输入<%=all.gen.wx_p_msg.t.alias.WxPMsgTAlias . desc%>"  type="text" name="wx_p_msg.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$wx_p_msg$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/gen/wx_p_msg/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$wx_p_msg$IDX_","id_input$wx_p_msg$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("代理应用ID","id_span$wx_p_msg$AGENTID_","id_input$wx_p_msg$AGENTID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("用户ID","id_span$wx_p_msg$USER_ID_","id_input$wx_p_msg$USER_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内容","id_span$wx_p_msg$CONTENT_","id_input$wx_p_msg$CONTENT_","TEXT","yes","65535","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间 CREATE_TIME_","id_span$wx_p_msg$CREATE_TIME_","id_input$wx_p_msg$CREATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间数字型 CREATE_TIME_LONG_","id_span$wx_p_msg$CREATE_TIME_LONG_","id_input$wx_p_msg$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_msg$UPDATE_TIME_LONG_","id_input$wx_p_msg$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_msg$UPDATE_TIME_","id_input$wx_p_msg$UPDATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$wx_p_msg$STATE_","id_input$wx_p_msg$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$wx_p_msg$DESC_","id_input$wx_p_msg$DESC_","VARCHAR","yes","512","0","0",a,true);
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