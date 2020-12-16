<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>
<form id="id_form_save"
	action="${pageContext.request.contextPath}/all/gen/tms_task/t/save/bootstrap.do"
	method="post">
<table class="table  table-bordered table-hover" border="0">
	<tr>
		<th colspan="3">菜单 <c:choose>
			<c:when test="${requestScope.id==null}">添加</c:when>
			<c:otherwise>修改</c:otherwise>
		</c:choose>
		</td>
	</tr>
	<tr>
		<td colspan="3"><input onclick="back();" class="btn" type="button"
			value="返回列表"></input> <c:choose>
			<c:when test="${requestScope.id==null}">
				<input onclick='save();' class="btn" type="button"  value="新增">
			</c:when>
			<c:otherwise>
				<input onclick='save();' class="btn" type="button" value="编辑">
			</c:otherwise>
		</c:choose></td>
	</tr>
	<!--  	<tr style="display: none;">
-->
<tr>
		<td>
		<p class="text-right">上一级菜单id：</p>
		</td>
		<td><input id="id_input_parent_menu_id" type="text"
			name="tms_task.parent" value="${requestScope.p.tmsTaskId}"></td>
		<td></td>
	</tr>
	<tr>
		<td>
		<p class="text-right">上一级菜单名称：</p>
		</td>
		<td><input id="id_input_parent_menu_name"
			class="input-medium search-query" readonly="readonly" type="text"
			name="" value="${requestScope.p.name}"> <input
			onclick="parent('${requestScope.s.path}');" class="btn" type="button"
			value="选择"></td>
		<td width="30%"></td>
	</tr>
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . idx%></td>
		<td><input id="id_input$tms_task$IDX_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . idx%>" type="text" name="tms_task.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$tms_task$IDX_"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . tmsTaskId%></td>
		<td><input id="id_input$tms_task$TMS_TASK_ID_"  type="text" name="tms_task.tmsTaskId"
			value="${requestScope.s.tmsTaskId}"></td>
		<td><span id="id_span$tms_task$TMS_TASK_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . tmsProjectIdId%></td>
		<td><input id="id_input$tms_task$TMS_PROJECT_ID_ID_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . tmsProjectIdId%>" type="text" name="tms_task.tmsProjectIdId"
			value="${requestScope.s.tmsProjectIdId}">


		</td>
		<td><span id="id_span$tms_task$TMS_PROJECT_ID_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . sn%></td>
		<td><input id="id_input$tms_task$SN_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . sn%>" type="text" name="tms_task.sn"
			value="${requestScope.s.sn}">


		</td>
		<td><span id="id_span$tms_task$SN_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . parent%></td>
		<td><input id="id_input$tms_task$PARENT_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . parent%>" type="text" name="tms_task.parent"
			value="${requestScope.s.parent}">


		</td>
		<td><span id="id_span$tms_task$PARENT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . path%></td>
		<td><input id="id_input$tms_task$PATH_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . path%>" type="text" name="tms_task.path"
			value="${requestScope.s.path}">


		</td>
		<td><span id="id_span$tms_task$PATH_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . treeCode%></td>
		<td><input id="id_input$tms_task$TREE_CODE_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . treeCode%>" type="text" name="tms_task.treeCode"
			value="${requestScope.s.treeCode}">


		</td>
		<td><span id="id_span$tms_task$TREE_CODE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . title%></td>
		<td><input id="id_input$tms_task$TITLE_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . title%>" type="text" name="tms_task.title"
			value="${requestScope.s.title}">


		</td>
		<td><span id="id_span$tms_task$TITLE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . contentShort%></td>
		<td><input id="id_input$tms_task$CONTENT_SHORT_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . contentShort%>" type="text" name="tms_task.contentShort"
			value="${requestScope.s.contentShort}">


		</td>
		<td><span id="id_span$tms_task$CONTENT_SHORT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . content%></td>
		<td><input id="id_input$tms_task$CONTENT_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . content%>" type="text" name="tms_task.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="id_span$tms_task$CONTENT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . priority%></td>
		<td><input id="id_input$tms_task$PRIORITY_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . priority%>" type="text" name="tms_task.priority"
			value="${requestScope.s.priority}">


		</td>
		<td><span id="id_span$tms_task$PRIORITY_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . sendUser%></td>
		<td><input id="id_input$tms_task$SEND_USER_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . sendUser%>" type="text" name="tms_task.sendUser"
			value="${requestScope.s.sendUser}">


		</td>
		<td><span id="id_span$tms_task$SEND_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . receiveUser%></td>
		<td><input id="id_input$tms_task$RECEIVE_USER_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . receiveUser%>" type="text" name="tms_task.receiveUser"
			value="${requestScope.s.receiveUser}">


		</td>
		<td><span id="id_span$tms_task$RECEIVE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . type%></td>
		<td><input id="id_input$tms_task$TYPE_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . type%>" type="text" name="tms_task.type"
			value="${requestScope.s.type}">


		</td>
		<td><span id="id_span$tms_task$TYPE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . urlView%></td>
		<td><input id="id_input$tms_task$URL_VIEW_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . urlView%>" type="text" name="tms_task.urlView"
			value="${requestScope.s.urlView}">


		</td>
		<td><span id="id_span$tms_task$URL_VIEW_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . reply%></td>
		<td><input id="id_input$tms_task$REPLY_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . reply%>" type="text" name="tms_task.reply"
			value="${requestScope.s.reply}">


		</td>
		<td><span id="id_span$tms_task$REPLY_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . file%></td>
		<td><input id="id_input$tms_task$FILE_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . file%>" type="text" name="tms_task.file"
			value="${requestScope.s.file}">


		</td>
		<td><span id="id_span$tms_task$FILE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . readTime%></td>
		<td><input id="id_input$tms_task$READ_TIME_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . readTime%>" type="text" name="tms_task.readTime"
			value="${requestScope.s.readTime}">



<img
onclick=ayCalendarShow('id_input$tms_task$READ_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$tms_task$READ_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . version%></td>
		<td><input id="id_input$tms_task$VERSION_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . version%>" type="text" name="tms_task.version"
			value="${requestScope.s.version}">


		</td>
		<td><span id="id_span$tms_task$VERSION_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . svnVersion%></td>
		<td><input id="id_input$tms_task$SVN_VERSION_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . svnVersion%>" type="text" name="tms_task.svnVersion"
			value="${requestScope.s.svnVersion}">


		</td>
		<td><span id="id_span$tms_task$SVN_VERSION_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . svnTime%></td>
		<td><input id="id_input$tms_task$SVN_TIME_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . svnTime%>" type="text" name="tms_task.svnTime"
			value="${requestScope.s.svnTime}">



<img
onclick=ayCalendarShow('id_input$tms_task$SVN_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$tms_task$SVN_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . taskStartTime%></td>
		<td><input id="id_input$tms_task$TASK_START_TIME_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . taskStartTime%>" type="text" name="tms_task.taskStartTime"
			value="${requestScope.s.taskStartTime}">



<img
onclick=ayCalendarShow('id_input$tms_task$TASK_START_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$tms_task$TASK_START_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . taskStartTimeLong%></td>
		<td><input id="id_input$tms_task$TASK_START_TIME_LONG_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . taskStartTimeLong%>" type="text" name="tms_task.taskStartTimeLong"
			value="${requestScope.s.taskStartTimeLong}">


		</td>
		<td><span id="id_span$tms_task$TASK_START_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . taskEndTime%></td>
		<td><input id="id_input$tms_task$TASK_END_TIME_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . taskEndTime%>" type="text" name="tms_task.taskEndTime"
			value="${requestScope.s.taskEndTime}">



<img
onclick=ayCalendarShow('id_input$tms_task$TASK_END_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$tms_task$TASK_END_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . taskEndTimeLong%></td>
		<td><input id="id_input$tms_task$TASK_END_TIME_LONG_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . taskEndTimeLong%>" type="text" name="tms_task.taskEndTimeLong"
			value="${requestScope.s.taskEndTimeLong}">


		</td>
		<td><span id="id_span$tms_task$TASK_END_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . planStartTime%></td>
		<td><input id="id_input$tms_task$PLAN_START_TIME_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . planStartTime%>" type="text" name="tms_task.planStartTime"
			value="${requestScope.s.planStartTime}">



<img
onclick=ayCalendarShow('id_input$tms_task$PLAN_START_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$tms_task$PLAN_START_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . planStartTimeLong%></td>
		<td><input id="id_input$tms_task$PLAN_START_TIME_LONG_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . planStartTimeLong%>" type="text" name="tms_task.planStartTimeLong"
			value="${requestScope.s.planStartTimeLong}">


		</td>
		<td><span id="id_span$tms_task$PLAN_START_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . planEndTime%></td>
		<td><input id="id_input$tms_task$PLAN_END_TIME_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . planEndTime%>" type="text" name="tms_task.planEndTime"
			value="${requestScope.s.planEndTime}">



<img
onclick=ayCalendarShow('id_input$tms_task$PLAN_END_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$tms_task$PLAN_END_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . planEndTimeLong%></td>
		<td><input id="id_input$tms_task$PLAN_END_TIME_LONG_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . planEndTimeLong%>" type="text" name="tms_task.planEndTimeLong"
			value="${requestScope.s.planEndTimeLong}">


		</td>
		<td><span id="id_span$tms_task$PLAN_END_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . createUser%></td>
		<td><input id="id_input$tms_task$CREATE_USER_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . createUser%>" type="text" name="tms_task.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="id_span$tms_task$CREATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . createTime%></td>
		<td><input id="id_input$tms_task$CREATE_TIME_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . createTime%>" type="text" name="tms_task.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$tms_task$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$tms_task$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . createTimeLong%></td>
		<td><input id="id_input$tms_task$CREATE_TIME_LONG_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . createTimeLong%>" type="text" name="tms_task.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$tms_task$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . updateUser%></td>
		<td><input id="id_input$tms_task$UPDATE_USER_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . updateUser%>" type="text" name="tms_task.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="id_span$tms_task$UPDATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . updateTime%></td>
		<td><input id="id_input$tms_task$UPDATE_TIME_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . updateTime%>" type="text" name="tms_task.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$tms_task$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$tms_task$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . updateTimeLong%></td>
		<td><input id="id_input$tms_task$UPDATE_TIME_LONG_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . updateTimeLong%>" type="text" name="tms_task.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$tms_task$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . state%></td>
		<td><input id="id_input$tms_task$STATE_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . state%>" type="text" name="tms_task.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$tms_task$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.tms_task.t.alias.TmsTaskTAlias . desc%></td>
		<td><input id="id_input$tms_task$DESC_"  placeholder="请输入<%=all.gen.tms_task.t.alias.TmsTaskTAlias . desc%>" type="text" name="tms_task.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$tms_task$DESC_"></span>
		</td>
</tr>	
</table>
</form>
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">

/**
 *上一级窗口 ;
 */
function parent(path){

	//alert('path='+path);
	var url = "${pageContext.request.contextPath}/all/gen/tms_task/t/parent/bootstrap.do";
	url=url +"?path="+path;
	var name='newwindow';
	ayFormOpenwindow (url,name,800,400) ;
	return false;
}

/**
 *上一级;
 *使用模态窗口 ;
 */
function parent_v1(path){
	var url = "${pageContext.request.contextPath}/all/gen/tms_task/t/parent/bootstrap.do";
	url=url +"?path="+path;
	var name="n";
	var needRefresh=window.showModalDialog(url,name, 'dialogHeight:400px;dialogWidth:450px;center:yes;help:no;status:no;scroll:yes');
	if(needRefresh == true){
	}
	return false;
}
 /**
  * 
  返回
  */
function back(){
 	var url = "${pageContext.request.contextPath}/all/gen/tms_task/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$tms_task$IDX_","id_input$tms_task$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("TMS项目表主键TMS_PROJECT_ID_ID_","id_span$tms_task$TMS_PROJECT_ID_ID_","id_input$tms_task$TMS_PROJECT_ID_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","id_span$tms_task$SN_","id_input$tms_task$SN_","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树上一级","id_span$tms_task$PARENT_","id_input$tms_task$PARENT_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树PATH_","id_span$tms_task$PATH_","id_input$tms_task$PATH_","VARCHAR","yes","1024","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树编码","id_span$tms_task$TREE_CODE_","id_input$tms_task$TREE_CODE_","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("主题","id_span$tms_task$TITLE_","id_input$tms_task$TITLE_","VARCHAR","yes","128","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("简介","id_span$tms_task$CONTENT_SHORT_","id_input$tms_task$CONTENT_SHORT_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内容","id_span$tms_task$CONTENT_","id_input$tms_task$CONTENT_","VARCHAR","yes","4096","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("优先级high,medium,low","id_span$tms_task$PRIORITY_","id_input$tms_task$PRIORITY_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送用户(发布人)SEND_USER_","id_span$tms_task$SEND_USER_","id_input$tms_task$SEND_USER_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接收用户(处理人)RECEIVE_USER_","id_span$tms_task$RECEIVE_USER_","id_input$tms_task$RECEIVE_USER_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型TYPE_","id_span$tms_task$TYPE_","id_input$tms_task$TYPE_","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("查看地址URL_VIEW_","id_span$tms_task$URL_VIEW_","id_input$tms_task$URL_VIEW_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否回复REPLY_","id_span$tms_task$REPLY_","id_input$tms_task$REPLY_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否有附件FILE_","id_span$tms_task$FILE_","id_input$tms_task$FILE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("阅读时间","id_span$tms_task$READ_TIME_","id_input$tms_task$READ_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("版本迭代VERSION_","id_span$tms_task$VERSION_","id_input$tms_task$VERSION_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("SVN版本SVN_VERSION_","id_span$tms_task$SVN_VERSION_","id_input$tms_task$SVN_VERSION_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("SVN版本时间SVN_TIME_","id_span$tms_task$SVN_TIME_","id_input$tms_task$SVN_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("任务开始时间TASK_START_TIME_","id_span$tms_task$TASK_START_TIME_","id_input$tms_task$TASK_START_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("任务开始时间数字型TASK_START_TIME_LONG_","id_span$tms_task$TASK_START_TIME_LONG_","id_input$tms_task$TASK_START_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("任务结束时间TASK_END_TIME_","id_span$tms_task$TASK_END_TIME_","id_input$tms_task$TASK_END_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("任务结束时间数字型TASK_END_TIME_LONG_","id_span$tms_task$TASK_END_TIME_LONG_","id_input$tms_task$TASK_END_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("计划开始时间PLAN_START_TIME_","id_span$tms_task$PLAN_START_TIME_","id_input$tms_task$PLAN_START_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("计划开始时间数字型PLAN_START_TIME_LONG_","id_span$tms_task$PLAN_START_TIME_LONG_","id_input$tms_task$PLAN_START_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("计划结束时间PLAN_END_TIME_","id_span$tms_task$PLAN_END_TIME_","id_input$tms_task$PLAN_END_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("计划结束时间数字型PLAN_END_TIME_LONG_","id_span$tms_task$PLAN_END_TIME_LONG_","id_input$tms_task$PLAN_END_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者CREATE_USER_","id_span$tms_task$CREATE_USER_","id_input$tms_task$CREATE_USER_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","id_span$tms_task$CREATE_TIME_","id_input$tms_task$CREATE_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","id_span$tms_task$CREATE_TIME_LONG_","id_input$tms_task$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","id_span$tms_task$UPDATE_USER_","id_input$tms_task$UPDATE_USER_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$tms_task$UPDATE_TIME_","id_input$tms_task$UPDATE_TIME_","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$tms_task$UPDATE_TIME_LONG_","id_input$tms_task$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$tms_task$STATE_","id_input$tms_task$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$tms_task$DESC_","id_input$tms_task$DESC_","VARCHAR","yes","512","0","0",a,true);
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
