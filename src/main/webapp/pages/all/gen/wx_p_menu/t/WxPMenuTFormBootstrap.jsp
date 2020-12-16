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
	action="${pageContext.request.contextPath}/all/gen/wx_p_menu/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . idx%></td>
		<td><input id="id_input$wx_p_menu$IDX_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . idx%>"  type="text" name="wx_p_menu.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$wx_p_menu$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . wxPMenuId%></td>
		<td><input id="id_input$wx_p_menu$WX_P_MENU_ID_" type="text" name="wx_p_menu.wxPMenuId"
			value="${requestScope.s.wxPMenuId}"></td>
		<td><span id="id_span$wx_p_menu$WX_P_MENU_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . name%></td>
		<td><input id="id_input$wx_p_menu$NAME_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . name%>"  type="text" name="wx_p_menu.name"
			value="${requestScope.s.name}">


		</td>
		<td><span id="id_span$wx_p_menu$NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . key%></td>
		<td><input id="id_input$wx_p_menu$KEY_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . key%>"  type="text" name="wx_p_menu.key"
			value="${requestScope.s.key}">


		</td>
		<td><span id="id_span$wx_p_menu$KEY_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . url%></td>
		<td><input id="id_input$wx_p_menu$URL_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . url%>"  type="text" name="wx_p_menu.url"
			value="${requestScope.s.url}">


		</td>
		<td><span id="id_span$wx_p_menu$URL_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . type%></td>
		<td><input id="id_input$wx_p_menu$TYPE_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . type%>"  type="text" name="wx_p_menu.type"
			value="${requestScope.s.type}">


		</td>
		<td><span id="id_span$wx_p_menu$TYPE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . sn%></td>
		<td><input id="id_input$wx_p_menu$SN_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . sn%>"  type="text" name="wx_p_menu.sn"
			value="${requestScope.s.sn}">


		</td>
		<td><span id="id_span$wx_p_menu$SN_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . parent%></td>
		<td><input id="id_input$wx_p_menu$PARENT_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . parent%>"  type="text" name="wx_p_menu.parent"
			value="${requestScope.s.parent}">


		</td>
		<td><span id="id_span$wx_p_menu$PARENT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . path%></td>
		<td><input id="id_input$wx_p_menu$PATH_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . path%>"  type="text" name="wx_p_menu.path"
			value="${requestScope.s.path}">


		</td>
		<td><span id="id_span$wx_p_menu$PATH_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . treeCode%></td>
		<td><input id="id_input$wx_p_menu$TREE_CODE_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . treeCode%>"  type="text" name="wx_p_menu.treeCode"
			value="${requestScope.s.treeCode}">


		</td>
		<td><span id="id_span$wx_p_menu$TREE_CODE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . createTime%></td>
		<td><input id="id_input$wx_p_menu$CREATE_TIME_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . createTime%>"  type="text" name="wx_p_menu.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_menu$CREATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_menu$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . createTimeLong%></td>
		<td><input id="id_input$wx_p_menu$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . createTimeLong%>"  type="text" name="wx_p_menu.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$wx_p_menu$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . updateTimeLong%></td>
		<td><input id="id_input$wx_p_menu$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . updateTimeLong%>"  type="text" name="wx_p_menu.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$wx_p_menu$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . updateTime%></td>
		<td><input id="id_input$wx_p_menu$UPDATE_TIME_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . updateTime%>"  type="text" name="wx_p_menu.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_menu$UPDATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_menu$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . state%></td>
		<td><input id="id_input$wx_p_menu$STATE_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . state%>"  type="text" name="wx_p_menu.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$wx_p_menu$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . desc%></td>
		<td><input id="id_input$wx_p_menu$DESC_" placeholder="请输入<%=all.gen.wx_p_menu.t.alias.WxPMenuTAlias . desc%>"  type="text" name="wx_p_menu.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$wx_p_menu$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/gen/wx_p_menu/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$wx_p_menu$IDX_","id_input$wx_p_menu$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("菜单名称","id_span$wx_p_menu$NAME_","id_input$wx_p_menu$NAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("菜单Key","id_span$wx_p_menu$KEY_","id_input$wx_p_menu$KEY_","VARCHAR","yes","80","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("访???地址URL","id_span$wx_p_menu$URL_","id_input$wx_p_menu$URL_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型TYPE_","id_span$wx_p_menu$TYPE_","id_input$wx_p_menu$TYPE_","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","id_span$wx_p_menu$SN_","id_input$wx_p_menu$SN_","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树上一级","id_span$wx_p_menu$PARENT_","id_input$wx_p_menu$PARENT_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树PATH_","id_span$wx_p_menu$PATH_","id_input$wx_p_menu$PATH_","VARCHAR","yes","1024","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树编码","id_span$wx_p_menu$TREE_CODE_","id_input$wx_p_menu$TREE_CODE_","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间 CREATE_TIME_","id_span$wx_p_menu$CREATE_TIME_","id_input$wx_p_menu$CREATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间数字型 CREATE_TIME_LONG_","id_span$wx_p_menu$CREATE_TIME_LONG_","id_input$wx_p_menu$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_menu$UPDATE_TIME_LONG_","id_input$wx_p_menu$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_menu$UPDATE_TIME_","id_input$wx_p_menu$UPDATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$wx_p_menu$STATE_","id_input$wx_p_menu$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$wx_p_menu$DESC_","id_input$wx_p_menu$DESC_","VARCHAR","yes","512","0","0",a,true);
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