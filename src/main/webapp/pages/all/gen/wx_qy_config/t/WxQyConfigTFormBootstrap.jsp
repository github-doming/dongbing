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
	action="${pageContext.request.contextPath}/all/gen/wx_qy_config/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . tenantNameCn%></td>
		<td><input id="id_input$wx_qy_config$TENANT_NAME_CN_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . tenantNameCn%>"  type="text" name="wx_qy_config.tenantNameCn"
			value="${requestScope.s.tenantNameCn}">


		</td>
		<td><span id="id_span$wx_qy_config$TENANT_NAME_CN_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . appToken%></td>
		<td><input id="id_input$wx_qy_config$APP_TOKEN_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . appToken%>"  type="text" name="wx_qy_config.appToken"
			value="${requestScope.s.appToken}">


		</td>
		<td><span id="id_span$wx_qy_config$APP_TOKEN_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . corpId%></td>
		<td><input id="id_input$wx_qy_config$CORP_ID_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . corpId%>"  type="text" name="wx_qy_config.corpId"
			value="${requestScope.s.corpId}">


		</td>
		<td><span id="id_span$wx_qy_config$CORP_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . corpSecret%></td>
		<td><input id="id_input$wx_qy_config$CORP_SECRET_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . corpSecret%>"  type="text" name="wx_qy_config.corpSecret"
			value="${requestScope.s.corpSecret}">


		</td>
		<td><span id="id_span$wx_qy_config$CORP_SECRET_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . accessToken%></td>
		<td><input id="id_input$wx_qy_config$ACCESS_TOKEN_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . accessToken%>"  type="text" name="wx_qy_config.accessToken"
			value="${requestScope.s.accessToken}">


		</td>
		<td><span id="id_span$wx_qy_config$ACCESS_TOKEN_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . expiresIn%></td>
		<td><input id="id_input$wx_qy_config$EXPIRES_IN_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . expiresIn%>"  type="text" name="wx_qy_config.expiresIn"
			value="${requestScope.s.expiresIn}">


		</td>
		<td><span id="id_span$wx_qy_config$EXPIRES_IN_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . expiresTime%></td>
		<td><input id="id_input$wx_qy_config$EXPIRES_TIME_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . expiresTime%>"  type="text" name="wx_qy_config.expiresTime"
			value="${requestScope.s.expiresTime}">


		</td>
		<td><span id="id_span$wx_qy_config$EXPIRES_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . idx%></td>
		<td><input id="id_input$wx_qy_config$IDX_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . idx%>"  type="text" name="wx_qy_config.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$wx_qy_config$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . wxQyConfigId%></td>
		<td><input id="id_input$wx_qy_config$WX_QY_CONFIG_ID_" type="text" name="wx_qy_config.wxQyConfigId"
			value="${requestScope.s.wxQyConfigId}"></td>
		<td><span id="id_span$wx_qy_config$WX_QY_CONFIG_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . initId%></td>
		<td><input id="id_input$wx_qy_config$INIT_ID_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . initId%>"  type="text" name="wx_qy_config.initId"
			value="${requestScope.s.initId}">


		</td>
		<td><span id="id_span$wx_qy_config$INIT_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . appToken2%></td>
		<td><input id="id_input$wx_qy_config$APP_TOKEN_2" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . appToken2%>"  type="text" name="wx_qy_config.appToken2"
			value="${requestScope.s.appToken2}">


		</td>
		<td><span id="id_span$wx_qy_config$APP_TOKEN_2"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . appId%></td>
		<td><input id="id_input$wx_qy_config$APP_ID_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . appId%>"  type="text" name="wx_qy_config.appId"
			value="${requestScope.s.appId}">


		</td>
		<td><span id="id_span$wx_qy_config$APP_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . appSecret%></td>
		<td><input id="id_input$wx_qy_config$APP_SECRET_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . appSecret%>"  type="text" name="wx_qy_config.appSecret"
			value="${requestScope.s.appSecret}">


		</td>
		<td><span id="id_span$wx_qy_config$APP_SECRET_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . accessToken2%></td>
		<td><input id="id_input$wx_qy_config$ACCESS_TOKEN_2" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . accessToken2%>"  type="text" name="wx_qy_config.accessToken2"
			value="${requestScope.s.accessToken2}">


		</td>
		<td><span id="id_span$wx_qy_config$ACCESS_TOKEN_2"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . expiresIn2%></td>
		<td><input id="id_input$wx_qy_config$EXPIRES_IN_2" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . expiresIn2%>"  type="text" name="wx_qy_config.expiresIn2"
			value="${requestScope.s.expiresIn2}">


		</td>
		<td><span id="id_span$wx_qy_config$EXPIRES_IN_2"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . expiresTime2%></td>
		<td><input id="id_input$wx_qy_config$EXPIRES_TIME_2" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . expiresTime2%>"  type="text" name="wx_qy_config.expiresTime2"
			value="${requestScope.s.expiresTime2}">


		</td>
		<td><span id="id_span$wx_qy_config$EXPIRES_TIME_2"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . expiresTimeLong%></td>
		<td><input id="id_input$wx_qy_config$EXPIRES_TIME_LONG_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . expiresTimeLong%>"  type="text" name="wx_qy_config.expiresTimeLong"
			value="${requestScope.s.expiresTimeLong}">


		</td>
		<td><span id="id_span$wx_qy_config$EXPIRES_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . createTime%></td>
		<td><input id="id_input$wx_qy_config$CREATE_TIME_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . createTime%>"  type="text" name="wx_qy_config.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$wx_qy_config$CREATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_qy_config$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . createTimeLong%></td>
		<td><input id="id_input$wx_qy_config$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . createTimeLong%>"  type="text" name="wx_qy_config.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$wx_qy_config$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . updateTimeLong%></td>
		<td><input id="id_input$wx_qy_config$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . updateTimeLong%>"  type="text" name="wx_qy_config.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$wx_qy_config$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . updateTime%></td>
		<td><input id="id_input$wx_qy_config$UPDATE_TIME_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . updateTime%>"  type="text" name="wx_qy_config.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$wx_qy_config$UPDATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_qy_config$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . state%></td>
		<td><input id="id_input$wx_qy_config$STATE_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . state%>"  type="text" name="wx_qy_config.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$wx_qy_config$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . desc%></td>
		<td><input id="id_input$wx_qy_config$DESC_" placeholder="请输入<%=all.gen.wx_qy_config.t.alias.WxQyConfigTAlias . desc%>"  type="text" name="wx_qy_config.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$wx_qy_config$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/gen/wx_qy_config/t/list/bootstrap.do";
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
flag=ayCheckColumn("租户名称","id_span$wx_qy_config$TENANT_NAME_CN_","id_input$wx_qy_config$TENANT_NAME_CN_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("应用凭证","id_span$wx_qy_config$APP_TOKEN_","id_input$wx_qy_config$APP_TOKEN_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("应用ID","id_span$wx_qy_config$CORP_ID_","id_input$wx_qy_config$CORP_ID_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("应用密钥","id_span$wx_qy_config$CORP_SECRET_","id_input$wx_qy_config$CORP_SECRET_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("获取到的凭证(访问凭证)","id_span$wx_qy_config$ACCESS_TOKEN_","id_input$wx_qy_config$ACCESS_TOKEN_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("凭证有效时间，单位：秒","id_span$wx_qy_config$EXPIRES_IN_","id_input$wx_qy_config$EXPIRES_IN_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("凭证过期时间","id_span$wx_qy_config$EXPIRES_TIME_","id_input$wx_qy_config$EXPIRES_TIME_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("索引","id_span$wx_qy_config$IDX_","id_input$wx_qy_config$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("原始ID","id_span$wx_qy_config$INIT_ID_","id_input$wx_qy_config$INIT_ID_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("应用凭证","id_span$wx_qy_config$APP_TOKEN_2","id_input$wx_qy_config$APP_TOKEN_2","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("应用ID","id_span$wx_qy_config$APP_ID_","id_input$wx_qy_config$APP_ID_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("应用密钥","id_span$wx_qy_config$APP_SECRET_","id_input$wx_qy_config$APP_SECRET_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("获取到的凭证(访问凭证)","id_span$wx_qy_config$ACCESS_TOKEN_2","id_input$wx_qy_config$ACCESS_TOKEN_2","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("凭证有效时间，单位：秒","id_span$wx_qy_config$EXPIRES_IN_2","id_input$wx_qy_config$EXPIRES_IN_2","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("凭证过期时间","id_span$wx_qy_config$EXPIRES_TIME_2","id_input$wx_qy_config$EXPIRES_TIME_2","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("过期时间数字型","id_span$wx_qy_config$EXPIRES_TIME_LONG_","id_input$wx_qy_config$EXPIRES_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间 CREATE_TIME_","id_span$wx_qy_config$CREATE_TIME_","id_input$wx_qy_config$CREATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间数字型 CREATE_TIME_LONG_","id_span$wx_qy_config$CREATE_TIME_LONG_","id_input$wx_qy_config$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_qy_config$UPDATE_TIME_LONG_","id_input$wx_qy_config$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_qy_config$UPDATE_TIME_","id_input$wx_qy_config$UPDATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$wx_qy_config$STATE_","id_input$wx_qy_config$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$wx_qy_config$DESC_","id_input$wx_qy_config$DESC_","VARCHAR","yes","512","0","0",a,true);
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