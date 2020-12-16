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
	action="${pageContext.request.contextPath}/all/gen/tms_project/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . idx%></td>
		<td><input id="id_input$tms_project$IDX_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . idx%>"  type="text" name="tms_project.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$tms_project$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . tmsProjectIdId%></td>
		<td><input id="id_input$tms_project$TMS_PROJECT_ID_ID_" type="text" name="tms_project.tmsProjectIdId"
			value="${requestScope.s.tmsProjectIdId}"></td>
		<td><span id="id_span$tms_project$TMS_PROJECT_ID_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . sn%></td>
		<td><input id="id_input$tms_project$SN_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . sn%>"  type="text" name="tms_project.sn"
			value="${requestScope.s.sn}">


		</td>
		<td><span id="id_span$tms_project$SN_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . parent%></td>
		<td><input id="id_input$tms_project$PARENT_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . parent%>"  type="text" name="tms_project.parent"
			value="${requestScope.s.parent}">


		</td>
		<td><span id="id_span$tms_project$PARENT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . path%></td>
		<td><input id="id_input$tms_project$PATH_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . path%>"  type="text" name="tms_project.path"
			value="${requestScope.s.path}">


		</td>
		<td><span id="id_span$tms_project$PATH_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . treeCode%></td>
		<td><input id="id_input$tms_project$TREE_CODE_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . treeCode%>"  type="text" name="tms_project.treeCode"
			value="${requestScope.s.treeCode}">


		</td>
		<td><span id="id_span$tms_project$TREE_CODE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . projectName%></td>
		<td><input id="id_input$tms_project$PROJECT_NAME_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . projectName%>"  type="text" name="tms_project.projectName"
			value="${requestScope.s.projectName}">


		</td>
		<td><span id="id_span$tms_project$PROJECT_NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . type%></td>
		<td><input id="id_input$tms_project$TYPE_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . type%>"  type="text" name="tms_project.type"
			value="${requestScope.s.type}">


		</td>
		<td><span id="id_span$tms_project$TYPE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . title%></td>
		<td><input id="id_input$tms_project$TITLE_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . title%>"  type="text" name="tms_project.title"
			value="${requestScope.s.title}">


		</td>
		<td><span id="id_span$tms_project$TITLE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . contentShort%></td>
		<td><input id="id_input$tms_project$CONTENT_SHORT_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . contentShort%>"  type="text" name="tms_project.contentShort"
			value="${requestScope.s.contentShort}">


		</td>
		<td><span id="id_span$tms_project$CONTENT_SHORT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . content%></td>
		<td><input id="id_input$tms_project$CONTENT_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . content%>"  type="text" name="tms_project.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="id_span$tms_project$CONTENT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . urlView%></td>
		<td><input id="id_input$tms_project$URL_VIEW_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . urlView%>"  type="text" name="tms_project.urlView"
			value="${requestScope.s.urlView}">


		</td>
		<td><span id="id_span$tms_project$URL_VIEW_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . createUser%></td>
		<td><input id="id_input$tms_project$CREATE_USER_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . createUser%>"  type="text" name="tms_project.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="id_span$tms_project$CREATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . createTime%></td>
		<td><input id="id_input$tms_project$CREATE_TIME_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . createTime%>"  type="text" name="tms_project.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$tms_project$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$tms_project$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . createTimeLong%></td>
		<td><input id="id_input$tms_project$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . createTimeLong%>"  type="text" name="tms_project.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$tms_project$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateUser%></td>
		<td><input id="id_input$tms_project$UPDATE_USER_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateUser%>"  type="text" name="tms_project.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="id_span$tms_project$UPDATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateTime%></td>
		<td><input id="id_input$tms_project$UPDATE_TIME_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateTime%>"  type="text" name="tms_project.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$tms_project$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$tms_project$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateTimeLong%></td>
		<td><input id="id_input$tms_project$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . updateTimeLong%>"  type="text" name="tms_project.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$tms_project$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . state%></td>
		<td><input id="id_input$tms_project$STATE_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . state%>"  type="text" name="tms_project.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$tms_project$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_project.t.alias.TmsProjectTAlias . desc%></td>
		<td><input id="id_input$tms_project$DESC_" placeholder="请输入<%=all.gen.tms_project.t.alias.TmsProjectTAlias . desc%>"  type="text" name="tms_project.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$tms_project$DESC_"></span>
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
var url = "${pageContext.request.contextPath}/all/gen/tms_project/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$tms_project$IDX_","id_input$tms_project$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","id_span$tms_project$SN_","id_input$tms_project$SN_","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树上一级","id_span$tms_project$PARENT_","id_input$tms_project$PARENT_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树PATH_","id_span$tms_project$PATH_","id_input$tms_project$PATH_","VARCHAR","yes","1024","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树编码","id_span$tms_project$TREE_CODE_","id_input$tms_project$TREE_CODE_","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("项目名PROJECT_NAME_","id_span$tms_project$PROJECT_NAME_","id_input$tms_project$PROJECT_NAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型TYPE_","id_span$tms_project$TYPE_","id_input$tms_project$TYPE_","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("主题","id_span$tms_project$TITLE_","id_input$tms_project$TITLE_","VARCHAR","yes","128","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("简介","id_span$tms_project$CONTENT_SHORT_","id_input$tms_project$CONTENT_SHORT_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内容","id_span$tms_project$CONTENT_","id_input$tms_project$CONTENT_","VARCHAR","yes","4096","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("查看地址URL_VIEW_","id_span$tms_project$URL_VIEW_","id_input$tms_project$URL_VIEW_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者CREATE_USER_","id_span$tms_project$CREATE_USER_","id_input$tms_project$CREATE_USER_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","id_span$tms_project$CREATE_TIME_","id_input$tms_project$CREATE_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","id_span$tms_project$CREATE_TIME_LONG_","id_input$tms_project$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","id_span$tms_project$UPDATE_USER_","id_input$tms_project$UPDATE_USER_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$tms_project$UPDATE_TIME_","id_input$tms_project$UPDATE_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$tms_project$UPDATE_TIME_LONG_","id_input$tms_project$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$tms_project$STATE_","id_input$tms_project$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$tms_project$DESC_","id_input$tms_project$DESC_","VARCHAR","yes","512","0","0",a,true);
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