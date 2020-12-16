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
	action="${pageContext.request.contextPath}/all/gen/wx_p_position/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . idx%></td>
		<td><input id="id_input$wx_p_position$IDX_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . idx%>"  type="text" name="wx_p_position.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$wx_p_position$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . wxPPositionId%></td>
		<td><input id="id_input$wx_p_position$WX_P_POSITION_ID_" type="text" name="wx_p_position.wxPPositionId"
			value="${requestScope.s.wxPPositionId}"></td>
		<td><span id="id_span$wx_p_position$WX_P_POSITION_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . appUserId%></td>
		<td><input id="id_input$wx_p_position$APP_USER_ID_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . appUserId%>"  type="text" name="wx_p_position.appUserId"
			value="${requestScope.s.appUserId}">


		</td>
		<td><span id="id_span$wx_p_position$APP_USER_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . fullname%></td>
		<td><input id="id_input$wx_p_position$FULLNAME_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . fullname%>"  type="text" name="wx_p_position.fullname"
			value="${requestScope.s.fullname}">


		</td>
		<td><span id="id_span$wx_p_position$FULLNAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . weixinId%></td>
		<td><input id="id_input$wx_p_position$WEIXIN_ID_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . weixinId%>"  type="text" name="wx_p_position.weixinId"
			value="${requestScope.s.weixinId}">


		</td>
		<td><span id="id_span$wx_p_position$WEIXIN_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . openId%></td>
		<td><input id="id_input$wx_p_position$OPEN_ID_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . openId%>"  type="text" name="wx_p_position.openId"
			value="${requestScope.s.openId}">


		</td>
		<td><span id="id_span$wx_p_position$OPEN_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . latitude%></td>
		<td><input id="id_input$wx_p_position$LATITUDE_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . latitude%>"  type="text" name="wx_p_position.latitude"
			value="${requestScope.s.latitude}">


		</td>
		<td><span id="id_span$wx_p_position$LATITUDE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . longitude%></td>
		<td><input id="id_input$wx_p_position$LONGITUDE_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . longitude%>"  type="text" name="wx_p_position.longitude"
			value="${requestScope.s.longitude}">


		</td>
		<td><span id="id_span$wx_p_position$LONGITUDE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . sysPrecision%></td>
		<td><input id="id_input$wx_p_position$SYS_PRECISION_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . sysPrecision%>"  type="text" name="wx_p_position.sysPrecision"
			value="${requestScope.s.sysPrecision}">


		</td>
		<td><span id="id_span$wx_p_position$SYS_PRECISION_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . nickName%></td>
		<td><input id="id_input$wx_p_position$NICK_NAME_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . nickName%>"  type="text" name="wx_p_position.nickName"
			value="${requestScope.s.nickName}">


		</td>
		<td><span id="id_span$wx_p_position$NICK_NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . createTime%></td>
		<td><input id="id_input$wx_p_position$CREATE_TIME_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . createTime%>"  type="text" name="wx_p_position.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_position$CREATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_position$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . createTimeLong%></td>
		<td><input id="id_input$wx_p_position$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . createTimeLong%>"  type="text" name="wx_p_position.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$wx_p_position$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . updateTimeLong%></td>
		<td><input id="id_input$wx_p_position$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . updateTimeLong%>"  type="text" name="wx_p_position.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$wx_p_position$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . updateTime%></td>
		<td><input id="id_input$wx_p_position$UPDATE_TIME_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . updateTime%>"  type="text" name="wx_p_position.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_position$UPDATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_position$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . state%></td>
		<td><input id="id_input$wx_p_position$STATE_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . state%>"  type="text" name="wx_p_position.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$wx_p_position$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . desc%></td>
		<td><input id="id_input$wx_p_position$DESC_" placeholder="请输入<%=all.gen.wx_p_position.t.alias.WxPPositionTAlias . desc%>"  type="text" name="wx_p_position.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$wx_p_position$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/gen/wx_p_position/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$wx_p_position$IDX_","id_input$wx_p_position$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("APP用户主键APP_USER_ID_","id_span$wx_p_position$APP_USER_ID_","id_input$wx_p_position$APP_USER_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("姓名","id_span$wx_p_position$FULLNAME_","id_input$wx_p_position$FULLNAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("微信号ID","id_span$wx_p_position$WEIXIN_ID_","id_input$wx_p_position$WEIXIN_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("OPEN_ID_","id_span$wx_p_position$OPEN_ID_","id_input$wx_p_position$OPEN_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("LATITUDE_","id_span$wx_p_position$LATITUDE_","id_input$wx_p_position$LATITUDE_","DOUBLE","yes","22","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("LONGITUDE_","id_span$wx_p_position$LONGITUDE_","id_input$wx_p_position$LONGITUDE_","DOUBLE","yes","22","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("SYS_PRECISION_","id_span$wx_p_position$SYS_PRECISION_","id_input$wx_p_position$SYS_PRECISION_","DOUBLE","yes","22","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("微信用户昵称","id_span$wx_p_position$NICK_NAME_","id_input$wx_p_position$NICK_NAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间 CREATE_TIME_","id_span$wx_p_position$CREATE_TIME_","id_input$wx_p_position$CREATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间数字型 CREATE_TIME_LONG_","id_span$wx_p_position$CREATE_TIME_LONG_","id_input$wx_p_position$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_position$UPDATE_TIME_LONG_","id_input$wx_p_position$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_position$UPDATE_TIME_","id_input$wx_p_position$UPDATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$wx_p_position$STATE_","id_input$wx_p_position$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$wx_p_position$DESC_","id_input$wx_p_position$DESC_","VARCHAR","yes","512","0","0",a,true);
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