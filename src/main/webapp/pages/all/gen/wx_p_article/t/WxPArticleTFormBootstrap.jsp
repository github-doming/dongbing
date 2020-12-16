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
	action="${pageContext.request.contextPath}/all/gen/wx_p_article/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . idx%></td>
		<td><input id="id_input$wx_p_article$IDX_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . idx%>"  type="text" name="wx_p_article.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$wx_p_article$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . wxPArticleId%></td>
		<td><input id="id_input$wx_p_article$WX_P_ARTICLE_ID_" type="text" name="wx_p_article.wxPArticleId"
			value="${requestScope.s.wxPArticleId}"></td>
		<td><span id="id_span$wx_p_article$WX_P_ARTICLE_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . author%></td>
		<td><input id="id_input$wx_p_article$AUTHOR_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . author%>"  type="text" name="wx_p_article.author"
			value="${requestScope.s.author}">


		</td>
		<td><span id="id_span$wx_p_article$AUTHOR_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . title%></td>
		<td><input id="id_input$wx_p_article$TITLE_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . title%>"  type="text" name="wx_p_article.title"
			value="${requestScope.s.title}">


		</td>
		<td><span id="id_span$wx_p_article$TITLE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . contentSourceUrl%></td>
		<td><input id="id_input$wx_p_article$CONTENT_SOURCE_URL_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . contentSourceUrl%>"  type="text" name="wx_p_article.contentSourceUrl"
			value="${requestScope.s.contentSourceUrl}">


		</td>
		<td><span id="id_span$wx_p_article$CONTENT_SOURCE_URL_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . content%></td>
		<td><input id="id_input$wx_p_article$CONTENT_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . content%>"  type="text" name="wx_p_article.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="id_span$wx_p_article$CONTENT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . showCoverPic%></td>
		<td><input id="id_input$wx_p_article$SHOW_COVER_PIC_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . showCoverPic%>"  type="text" name="wx_p_article.showCoverPic"
			value="${requestScope.s.showCoverPic}">


		</td>
		<td><span id="id_span$wx_p_article$SHOW_COVER_PIC_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . materialId%></td>
		<td><input id="id_input$wx_p_article$MATERIAL_ID_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . materialId%>"  type="text" name="wx_p_article.materialId"
			value="${requestScope.s.materialId}">


		</td>
		<td><span id="id_span$wx_p_article$MATERIAL_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . sysFileId%></td>
		<td><input id="id_input$wx_p_article$SYS_FILE_ID_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . sysFileId%>"  type="text" name="wx_p_article.sysFileId"
			value="${requestScope.s.sysFileId}">


		</td>
		<td><span id="id_span$wx_p_article$SYS_FILE_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . createTime%></td>
		<td><input id="id_input$wx_p_article$CREATE_TIME_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . createTime%>"  type="text" name="wx_p_article.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_article$CREATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_article$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . createTimeLong%></td>
		<td><input id="id_input$wx_p_article$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . createTimeLong%>"  type="text" name="wx_p_article.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$wx_p_article$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . updateTimeLong%></td>
		<td><input id="id_input$wx_p_article$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . updateTimeLong%>"  type="text" name="wx_p_article.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$wx_p_article$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . updateTime%></td>
		<td><input id="id_input$wx_p_article$UPDATE_TIME_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . updateTime%>"  type="text" name="wx_p_article.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_article$UPDATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_article$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . state%></td>
		<td><input id="id_input$wx_p_article$STATE_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . state%>"  type="text" name="wx_p_article.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$wx_p_article$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . desc%></td>
		<td><input id="id_input$wx_p_article$DESC_" placeholder="请输入<%=all.gen.wx_p_article.t.alias.WxPArticleTAlias . desc%>"  type="text" name="wx_p_article.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$wx_p_article$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/gen/wx_p_article/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$wx_p_article$IDX_","id_input$wx_p_article$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("作者","id_span$wx_p_article$AUTHOR_","id_input$wx_p_article$AUTHOR_","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("标题","id_span$wx_p_article$TITLE_","id_input$wx_p_article$TITLE_","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("原文章地址","id_span$wx_p_article$CONTENT_SOURCE_URL_","id_input$wx_p_article$CONTENT_SOURCE_URL_","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("图文消息页面的内容","id_span$wx_p_article$CONTENT_","id_input$wx_p_article$CONTENT_","TEXT","yes","65535","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否显示封面","id_span$wx_p_article$SHOW_COVER_PIC_","id_input$wx_p_article$SHOW_COVER_PIC_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("MATERIAL_ID_","id_span$wx_p_article$MATERIAL_ID_","id_input$wx_p_article$MATERIAL_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("后台文件表主键SYS_FILE_ID_","id_span$wx_p_article$SYS_FILE_ID_","id_input$wx_p_article$SYS_FILE_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间 CREATE_TIME_","id_span$wx_p_article$CREATE_TIME_","id_input$wx_p_article$CREATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间数字型 CREATE_TIME_LONG_","id_span$wx_p_article$CREATE_TIME_LONG_","id_input$wx_p_article$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_article$UPDATE_TIME_LONG_","id_input$wx_p_article$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_article$UPDATE_TIME_","id_input$wx_p_article$UPDATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$wx_p_article$STATE_","id_input$wx_p_article$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$wx_p_article$DESC_","id_input$wx_p_article$DESC_","VARCHAR","yes","512","0","0",a,true);
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