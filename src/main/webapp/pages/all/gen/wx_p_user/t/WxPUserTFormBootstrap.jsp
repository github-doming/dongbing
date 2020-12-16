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
	action="${pageContext.request.contextPath}/all/gen/wx_p_user/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . idx%></td>
		<td><input id="id_input$wx_p_user$IDX_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . idx%>"  type="text" name="wx_p_user.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$wx_p_user$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . wxPUserId%></td>
		<td><input id="id_input$wx_p_user$WX_P_USER_ID_" type="text" name="wx_p_user.wxPUserId"
			value="${requestScope.s.wxPUserId}"></td>
		<td><span id="id_span$wx_p_user$WX_P_USER_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . appUserId%></td>
		<td><input id="id_input$wx_p_user$APP_USER_ID_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . appUserId%>"  type="text" name="wx_p_user.appUserId"
			value="${requestScope.s.appUserId}">


		</td>
		<td><span id="id_span$wx_p_user$APP_USER_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . fullname%></td>
		<td><input id="id_input$wx_p_user$FULLNAME_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . fullname%>"  type="text" name="wx_p_user.fullname"
			value="${requestScope.s.fullname}">


		</td>
		<td><span id="id_span$wx_p_user$FULLNAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . weixinId%></td>
		<td><input id="id_input$wx_p_user$WEIXIN_ID_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . weixinId%>"  type="text" name="wx_p_user.weixinId"
			value="${requestScope.s.weixinId}">


		</td>
		<td><span id="id_span$wx_p_user$WEIXIN_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . openId%></td>
		<td><input id="id_input$wx_p_user$OPEN_ID_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . openId%>"  type="text" name="wx_p_user.openId"
			value="${requestScope.s.openId}">


		</td>
		<td><span id="id_span$wx_p_user$OPEN_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . nickName%></td>
		<td><input id="id_input$wx_p_user$NICK_NAME_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . nickName%>"  type="text" name="wx_p_user.nickName"
			value="${requestScope.s.nickName}">


		</td>
		<td><span id="id_span$wx_p_user$NICK_NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . sex%></td>
		<td><input id="id_input$wx_p_user$SEX_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . sex%>"  type="text" name="wx_p_user.sex"
			value="${requestScope.s.sex}">


		</td>
		<td><span id="id_span$wx_p_user$SEX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . language%></td>
		<td><input id="id_input$wx_p_user$LANGUAGE_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . language%>"  type="text" name="wx_p_user.language"
			value="${requestScope.s.language}">


		</td>
		<td><span id="id_span$wx_p_user$LANGUAGE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . city%></td>
		<td><input id="id_input$wx_p_user$CITY_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . city%>"  type="text" name="wx_p_user.city"
			value="${requestScope.s.city}">


		</td>
		<td><span id="id_span$wx_p_user$CITY_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . province%></td>
		<td><input id="id_input$wx_p_user$PROVINCE_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . province%>"  type="text" name="wx_p_user.province"
			value="${requestScope.s.province}">


		</td>
		<td><span id="id_span$wx_p_user$PROVINCE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . country%></td>
		<td><input id="id_input$wx_p_user$COUNTRY_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . country%>"  type="text" name="wx_p_user.country"
			value="${requestScope.s.country}">


		</td>
		<td><span id="id_span$wx_p_user$COUNTRY_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . headimgurl%></td>
		<td><input id="id_input$wx_p_user$HEADIMGURL_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . headimgurl%>"  type="text" name="wx_p_user.headimgurl"
			value="${requestScope.s.headimgurl}">


		</td>
		<td><span id="id_span$wx_p_user$HEADIMGURL_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . createTime%></td>
		<td><input id="id_input$wx_p_user$CREATE_TIME_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . createTime%>"  type="text" name="wx_p_user.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_user$CREATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_user$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . createTimeLong%></td>
		<td><input id="id_input$wx_p_user$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . createTimeLong%>"  type="text" name="wx_p_user.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$wx_p_user$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . updateTimeLong%></td>
		<td><input id="id_input$wx_p_user$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . updateTimeLong%>"  type="text" name="wx_p_user.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$wx_p_user$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . updateTime%></td>
		<td><input id="id_input$wx_p_user$UPDATE_TIME_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . updateTime%>"  type="text" name="wx_p_user.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_user$UPDATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_user$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . state%></td>
		<td><input id="id_input$wx_p_user$STATE_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . state%>"  type="text" name="wx_p_user.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$wx_p_user$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_user.t.alias.WxPUserTAlias . desc%></td>
		<td><input id="id_input$wx_p_user$DESC_" placeholder="请输入<%=all.gen.wx_p_user.t.alias.WxPUserTAlias . desc%>"  type="text" name="wx_p_user.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$wx_p_user$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/gen/wx_p_user/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$wx_p_user$IDX_","id_input$wx_p_user$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("APP用户主键APP_USER_ID_","id_span$wx_p_user$APP_USER_ID_","id_input$wx_p_user$APP_USER_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("姓名","id_span$wx_p_user$FULLNAME_","id_input$wx_p_user$FULLNAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("微信号ID","id_span$wx_p_user$WEIXIN_ID_","id_input$wx_p_user$WEIXIN_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("OPEN_ID_","id_span$wx_p_user$OPEN_ID_","id_input$wx_p_user$OPEN_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("微信用户昵称","id_span$wx_p_user$NICK_NAME_","id_input$wx_p_user$NICK_NAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("SEX_","id_span$wx_p_user$SEX_","id_input$wx_p_user$SEX_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("LANGUAGE_","id_span$wx_p_user$LANGUAGE_","id_input$wx_p_user$LANGUAGE_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CITY_","id_span$wx_p_user$CITY_","id_input$wx_p_user$CITY_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("PROVINCE_","id_span$wx_p_user$PROVINCE_","id_input$wx_p_user$PROVINCE_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("COUNTRY_","id_span$wx_p_user$COUNTRY_","id_input$wx_p_user$COUNTRY_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("HEADIMGURL_","id_span$wx_p_user$HEADIMGURL_","id_input$wx_p_user$HEADIMGURL_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间 CREATE_TIME_","id_span$wx_p_user$CREATE_TIME_","id_input$wx_p_user$CREATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间数字型 CREATE_TIME_LONG_","id_span$wx_p_user$CREATE_TIME_LONG_","id_input$wx_p_user$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_user$UPDATE_TIME_LONG_","id_input$wx_p_user$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_user$UPDATE_TIME_","id_input$wx_p_user$UPDATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$wx_p_user$STATE_","id_input$wx_p_user$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$wx_p_user$DESC_","id_input$wx_p_user$DESC_","VARCHAR","yes","512","0","0",a,true);
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