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
	action="${pageContext.request.contextPath}/all/gen/wx_p_mt_news/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . idx%></td>
		<td><input id="id_input$wx_p_mt_news$IDX_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . idx%>"  type="text" name="wx_p_mt_news.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$wx_p_mt_news$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . wxPMtNewsId%></td>
		<td><input id="id_input$wx_p_mt_news$WX_P_MT_NEWS_ID_" type="text" name="wx_p_mt_news.wxPMtNewsId"
			value="${requestScope.s.wxPMtNewsId}"></td>
		<td><span id="id_span$wx_p_mt_news$WX_P_MT_NEWS_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . name%></td>
		<td><input id="id_input$wx_p_mt_news$NAME_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . name%>"  type="text" name="wx_p_mt_news.name"
			value="${requestScope.s.name}">


		</td>
		<td><span id="id_span$wx_p_mt_news$NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . mediaId%></td>
		<td><input id="id_input$wx_p_mt_news$MEDIA_ID_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . mediaId%>"  type="text" name="wx_p_mt_news.mediaId"
			value="${requestScope.s.mediaId}">


		</td>
		<td><span id="id_span$wx_p_mt_news$MEDIA_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . type%></td>
		<td><input id="id_input$wx_p_mt_news$TYPE_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . type%>"  type="text" name="wx_p_mt_news.type"
			value="${requestScope.s.type}">


		</td>
		<td><span id="id_span$wx_p_mt_news$TYPE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . createdAt%></td>
		<td><input id="id_input$wx_p_mt_news$CREATED_AT_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . createdAt%>"  type="text" name="wx_p_mt_news.createdAt"
			value="${requestScope.s.createdAt}">


		</td>
		<td><span id="id_span$wx_p_mt_news$CREATED_AT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . newsId%></td>
		<td><input id="id_input$wx_p_mt_news$NEWS_ID_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . newsId%>"  type="text" name="wx_p_mt_news.newsId"
			value="${requestScope.s.newsId}">


		</td>
		<td><span id="id_span$wx_p_mt_news$NEWS_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . createTime%></td>
		<td><input id="id_input$wx_p_mt_news$CREATE_TIME_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . createTime%>"  type="text" name="wx_p_mt_news.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_mt_news$CREATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_mt_news$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . createTimeLong%></td>
		<td><input id="id_input$wx_p_mt_news$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . createTimeLong%>"  type="text" name="wx_p_mt_news.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$wx_p_mt_news$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . updateTimeLong%></td>
		<td><input id="id_input$wx_p_mt_news$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . updateTimeLong%>"  type="text" name="wx_p_mt_news.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$wx_p_mt_news$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . updateTime%></td>
		<td><input id="id_input$wx_p_mt_news$UPDATE_TIME_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . updateTime%>"  type="text" name="wx_p_mt_news.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_mt_news$UPDATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_mt_news$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . state%></td>
		<td><input id="id_input$wx_p_mt_news$STATE_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . state%>"  type="text" name="wx_p_mt_news.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$wx_p_mt_news$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . desc%></td>
		<td><input id="id_input$wx_p_mt_news$DESC_" placeholder="请输入<%=all.gen.wx_p_mt_news.t.alias.WxPMtNewsTAlias . desc%>"  type="text" name="wx_p_mt_news.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$wx_p_mt_news$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/gen/wx_p_mt_news/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$wx_p_mt_news$IDX_","id_input$wx_p_mt_news$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("名称","id_span$wx_p_mt_news$NAME_","id_input$wx_p_mt_news$NAME_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("媒体文章ID","id_span$wx_p_mt_news$MEDIA_ID_","id_input$wx_p_mt_news$MEDIA_ID_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型","id_span$wx_p_mt_news$TYPE_","id_input$wx_p_mt_news$TYPE_","VARCHAR","yes","128","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("微信创建时间","id_span$wx_p_mt_news$CREATED_AT_","id_input$wx_p_mt_news$CREATED_AT_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("NEWS_ID_","id_span$wx_p_mt_news$NEWS_ID_","id_input$wx_p_mt_news$NEWS_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间 CREATE_TIME_","id_span$wx_p_mt_news$CREATE_TIME_","id_input$wx_p_mt_news$CREATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间数字型 CREATE_TIME_LONG_","id_span$wx_p_mt_news$CREATE_TIME_LONG_","id_input$wx_p_mt_news$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_mt_news$UPDATE_TIME_LONG_","id_input$wx_p_mt_news$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_mt_news$UPDATE_TIME_","id_input$wx_p_mt_news$UPDATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$wx_p_mt_news$STATE_","id_input$wx_p_mt_news$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$wx_p_mt_news$DESC_","id_input$wx_p_mt_news$DESC_","VARCHAR","yes","512","0","0",a,true);
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