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
	action="${pageContext.request.contextPath}/all/task/tms/tms_task/t/save/bootstrap.do"
	method="post">
<table class="table  table-bordered table-hover" border="0">
	<tr>
		<th colspan="3">任务 <c:choose>
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
		<p class="text-right">上一级任务id：</p>
		</td>
		<td><input id="id_input_parent_menu_id" type="text"
			name="tms_task.parent" value="${requestScope.p.tmsTaskId}"></td>
		<td></td>
	</tr>
	<tr>
		<td>
		<p class="text-right">上一级任务名称：</p>
		</td>
		<td><input id="id_input_parent_menu_name"
			class="input-medium search-query" readonly="readonly" type="text"
			name="" value="${requestScope.p.name}"> <input
			onclick="parent('${requestScope.s.path}');" class="btn" type="button"
			value="选择"></td>
		<td width="30%"></td>
	</tr>

<tr style="display: none;">
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . tmsTaskId%></td>
		<td><input id="idInput_TmsTaskT_tmsTaskId"  type="text" name="tms_task.tmsTaskId"
			value="${requestScope.s.tmsTaskId}"></td>
		<td><span id="idSpan_TmsTaskT_tmsTaskId"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . tmsProjectId%></td>
		<td><input id="idInput_TmsTaskT_tmsProjectId"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . tmsProjectId%>" type="text" name="tms_task.tmsProjectId"
			value="${requestScope.s.tmsProjectId}">


		</td>
		<td><span id="idSpan_TmsTaskT_tmsProjectId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . name%></td>
		<td><input id="idInput_TmsTaskT_name"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . name%>" type="text" name="tms_task.name"
			value="${requestScope.s.name}">


		</td>
		<td><span id="idSpan_TmsTaskT_name"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . projectName%></td>
		<td><input id="idInput_TmsTaskT_projectName"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . projectName%>" type="text" name="tms_task.projectName"
			value="${requestScope.s.projectName}">


		</td>
		<td><span id="idSpan_TmsTaskT_projectName"></span>
		</td>
</tr>	


<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . content%></td>
		<td><input id="idInput_TmsTaskT_content"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . content%>" type="text" name="tms_task.content"
			value="${requestScope.s.content}">


		</td>
		<td><span id="idSpan_TmsTaskT_content"></span>
		</td>
</tr>	


<!-- 

<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . idx%></td>
		<td><input id="idInput_TmsTaskT_idx"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . idx%>" type="text" name="tms_task.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_TmsTaskT_idx"></span>
		</td>
</tr>	


<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . sn%></td>
		<td><input id="idInput_TmsTaskT_sn"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . sn%>" type="text" name="tms_task.sn"
			value="${requestScope.s.sn}">


		</td>
		<td><span id="idSpan_TmsTaskT_sn"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . parent%></td>
		<td><input id="idInput_TmsTaskT_parent"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . parent%>" type="text" name="tms_task.parent"
			value="${requestScope.s.parent}">


		</td>
		<td><span id="idSpan_TmsTaskT_parent"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . path%></td>
		<td><input id="idInput_TmsTaskT_path"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . path%>" type="text" name="tms_task.path"
			value="${requestScope.s.path}">


		</td>
		<td><span id="idSpan_TmsTaskT_path"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . treeCode%></td>
		<td><input id="idInput_TmsTaskT_treeCode"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . treeCode%>" type="text" name="tms_task.treeCode"
			value="${requestScope.s.treeCode}">


		</td>
		<td><span id="idSpan_TmsTaskT_treeCode"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . title%></td>
		<td><input id="idInput_TmsTaskT_title"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . title%>" type="text" name="tms_task.title"
			value="${requestScope.s.title}">


		</td>
		<td><span id="idSpan_TmsTaskT_title"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . contentShort%></td>
		<td><input id="idInput_TmsTaskT_contentShort"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . contentShort%>" type="text" name="tms_task.contentShort"
			value="${requestScope.s.contentShort}">


		</td>
		<td><span id="idSpan_TmsTaskT_contentShort"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . priority%></td>
		<td><input id="idInput_TmsTaskT_priority"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . priority%>" type="text" name="tms_task.priority"
			value="${requestScope.s.priority}">


		</td>
		<td><span id="idSpan_TmsTaskT_priority"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . sendUser%></td>
		<td><input id="idInput_TmsTaskT_sendUser"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . sendUser%>" type="text" name="tms_task.sendUser"
			value="${requestScope.s.sendUser}">


		</td>
		<td><span id="idSpan_TmsTaskT_sendUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . receiveUser%></td>
		<td><input id="idInput_TmsTaskT_receiveUser"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . receiveUser%>" type="text" name="tms_task.receiveUser"
			value="${requestScope.s.receiveUser}">


		</td>
		<td><span id="idSpan_TmsTaskT_receiveUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . type%></td>
		<td><input id="idInput_TmsTaskT_type"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . type%>" type="text" name="tms_task.type"
			value="${requestScope.s.type}">


		</td>
		<td><span id="idSpan_TmsTaskT_type"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . urlView%></td>
		<td><input id="idInput_TmsTaskT_urlView"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . urlView%>" type="text" name="tms_task.urlView"
			value="${requestScope.s.urlView}">


		</td>
		<td><span id="idSpan_TmsTaskT_urlView"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . reply%></td>
		<td><input id="idInput_TmsTaskT_reply"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . reply%>" type="text" name="tms_task.reply"
			value="${requestScope.s.reply}">


		</td>
		<td><span id="idSpan_TmsTaskT_reply"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . file%></td>
		<td><input id="idInput_TmsTaskT_file"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . file%>" type="text" name="tms_task.file"
			value="${requestScope.s.file}">


		</td>
		<td><span id="idSpan_TmsTaskT_file"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . readTime%></td>
		<td><input id="idInput_TmsTaskT_readTime"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . readTime%>" type="text" name="tms_task.readTime"
			value="${requestScope.s.readTime}">



<img
onclick=ayCalendarShow('idInput_TmsTaskT_readTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsTaskT_readTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . version%></td>
		<td><input id="idInput_TmsTaskT_version"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . version%>" type="text" name="tms_task.version"
			value="${requestScope.s.version}">


		</td>
		<td><span id="idSpan_TmsTaskT_version"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . svnVersion%></td>
		<td><input id="idInput_TmsTaskT_svnVersion"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . svnVersion%>" type="text" name="tms_task.svnVersion"
			value="${requestScope.s.svnVersion}">


		</td>
		<td><span id="idSpan_TmsTaskT_svnVersion"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . svnTime%></td>
		<td><input id="idInput_TmsTaskT_svnTime"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . svnTime%>" type="text" name="tms_task.svnTime"
			value="${requestScope.s.svnTime}">



<img
onclick=ayCalendarShow('idInput_TmsTaskT_svnTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsTaskT_svnTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . taskStartTime%></td>
		<td><input id="idInput_TmsTaskT_taskStartTime"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . taskStartTime%>" type="text" name="tms_task.taskStartTime"
			value="${requestScope.s.taskStartTime}">



<img
onclick=ayCalendarShow('idInput_TmsTaskT_taskStartTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsTaskT_taskStartTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . taskStartTimeLong%></td>
		<td><input id="idInput_TmsTaskT_taskStartTimeLong"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . taskStartTimeLong%>" type="text" name="tms_task.taskStartTimeLong"
			value="${requestScope.s.taskStartTimeLong}">


		</td>
		<td><span id="idSpan_TmsTaskT_taskStartTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . taskEndTime%></td>
		<td><input id="idInput_TmsTaskT_taskEndTime"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . taskEndTime%>" type="text" name="tms_task.taskEndTime"
			value="${requestScope.s.taskEndTime}">



<img
onclick=ayCalendarShow('idInput_TmsTaskT_taskEndTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsTaskT_taskEndTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . taskEndTimeLong%></td>
		<td><input id="idInput_TmsTaskT_taskEndTimeLong"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . taskEndTimeLong%>" type="text" name="tms_task.taskEndTimeLong"
			value="${requestScope.s.taskEndTimeLong}">


		</td>
		<td><span id="idSpan_TmsTaskT_taskEndTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . planStartTime%></td>
		<td><input id="idInput_TmsTaskT_planStartTime"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . planStartTime%>" type="text" name="tms_task.planStartTime"
			value="${requestScope.s.planStartTime}">



<img
onclick=ayCalendarShow('idInput_TmsTaskT_planStartTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsTaskT_planStartTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . planStartTimeLong%></td>
		<td><input id="idInput_TmsTaskT_planStartTimeLong"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . planStartTimeLong%>" type="text" name="tms_task.planStartTimeLong"
			value="${requestScope.s.planStartTimeLong}">


		</td>
		<td><span id="idSpan_TmsTaskT_planStartTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . planEndTime%></td>
		<td><input id="idInput_TmsTaskT_planEndTime"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . planEndTime%>" type="text" name="tms_task.planEndTime"
			value="${requestScope.s.planEndTime}">



<img
onclick=ayCalendarShow('idInput_TmsTaskT_planEndTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsTaskT_planEndTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . planEndTimeLong%></td>
		<td><input id="idInput_TmsTaskT_planEndTimeLong"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . planEndTimeLong%>" type="text" name="tms_task.planEndTimeLong"
			value="${requestScope.s.planEndTimeLong}">


		</td>
		<td><span id="idSpan_TmsTaskT_planEndTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . createUser%></td>
		<td><input id="idInput_TmsTaskT_createUser"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . createUser%>" type="text" name="tms_task.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="idSpan_TmsTaskT_createUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . createTime%></td>
		<td><input id="idInput_TmsTaskT_createTime"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . createTime%>" type="text" name="tms_task.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_TmsTaskT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsTaskT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . createTimeLong%></td>
		<td><input id="idInput_TmsTaskT_createTimeLong"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . createTimeLong%>" type="text" name="tms_task.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_TmsTaskT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . updateUser%></td>
		<td><input id="idInput_TmsTaskT_updateUser"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . updateUser%>" type="text" name="tms_task.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="idSpan_TmsTaskT_updateUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . updateTime%></td>
		<td><input id="idInput_TmsTaskT_updateTime"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . updateTime%>" type="text" name="tms_task.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_TmsTaskT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsTaskT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . updateTimeLong%></td>
		<td><input id="idInput_TmsTaskT_updateTimeLong"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . updateTimeLong%>" type="text" name="tms_task.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_TmsTaskT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . state%></td>
		<td><input id="idInput_TmsTaskT_state"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . state%>" type="text" name="tms_task.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_TmsTaskT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . desc%></td>
		<td><input id="idInput_TmsTaskT_desc"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . desc%>" type="text" name="tms_task.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_TmsTaskT_desc"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . tenantCode%></td>
		<td><input id="idInput_TmsTaskT_tenantCode"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . tenantCode%>" type="text" name="tms_task.tenantCode"
			value="${requestScope.s.tenantCode}">


		</td>
		<td><span id="idSpan_TmsTaskT_tenantCode"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . tenantNumber%></td>
		<td><input id="idInput_TmsTaskT_tenantNumber"  placeholder="请输入<%=all.task.tms.tms_task.t.alias.TmsTaskTAlias . tenantNumber%>" type="text" name="tms_task.tenantNumber"
			value="${requestScope.s.tenantNumber}">


		</td>
		<td><span id="idSpan_TmsTaskT_tenantNumber"></span>
		</td>
</tr>	

 -->
 
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
	var url = "${pageContext.request.contextPath}/all/task/tms/tms_task/t/parent/bootstrap.do";
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
	var url = "${pageContext.request.contextPath}/all/task/tms/tms_task/t/parent/bootstrap.do";
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
 	var url = "${pageContext.request.contextPath}/all/task/tms/tms_task/t/list/bootstrap.do";
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
	
	if(false){
flag=ayCheckColumn("索引","idSpan_TmsTaskT_idx","idInput_TmsTaskT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("TMS项目表主键TMS_PROJECT_ID_","idSpan_TmsTaskT_tmsProjectId","idInput_TmsTaskT_tmsProjectId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("任务名称NAME_","idSpan_TmsTaskT_name","idInput_TmsTaskT_name","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("项目名称PROJECT_NAME_","idSpan_TmsTaskT_projectName","idInput_TmsTaskT_projectName","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","idSpan_TmsTaskT_sn","idInput_TmsTaskT_sn","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树上一级","idSpan_TmsTaskT_parent","idInput_TmsTaskT_parent","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树PATH_","idSpan_TmsTaskT_path","idInput_TmsTaskT_path","VARCHAR","yes","1024","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树编码","idSpan_TmsTaskT_treeCode","idInput_TmsTaskT_treeCode","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("主题","idSpan_TmsTaskT_title","idInput_TmsTaskT_title","VARCHAR","yes","128","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("简介","idSpan_TmsTaskT_contentShort","idInput_TmsTaskT_contentShort","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("内容","idSpan_TmsTaskT_content","idInput_TmsTaskT_content","VARCHAR","yes","4096","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("优先级high,medium,low","idSpan_TmsTaskT_priority","idInput_TmsTaskT_priority","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("发送用户(发布人)SEND_USER_","idSpan_TmsTaskT_sendUser","idInput_TmsTaskT_sendUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接收用户(处理人)RECEIVE_USER_","idSpan_TmsTaskT_receiveUser","idInput_TmsTaskT_receiveUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型TYPE_","idSpan_TmsTaskT_type","idInput_TmsTaskT_type","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("查看地址URL_VIEW_","idSpan_TmsTaskT_urlView","idInput_TmsTaskT_urlView","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否回复REPLY_","idSpan_TmsTaskT_reply","idInput_TmsTaskT_reply","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否有附件FILE_","idSpan_TmsTaskT_file","idInput_TmsTaskT_file","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("阅读时间","idSpan_TmsTaskT_readTime","idInput_TmsTaskT_readTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("版本迭代VERSION_","idSpan_TmsTaskT_version","idInput_TmsTaskT_version","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("SVN版本SVN_VERSION_","idSpan_TmsTaskT_svnVersion","idInput_TmsTaskT_svnVersion","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("SVN版本时间SVN_TIME_","idSpan_TmsTaskT_svnTime","idInput_TmsTaskT_svnTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("任务开始时间TASK_START_TIME_","idSpan_TmsTaskT_taskStartTime","idInput_TmsTaskT_taskStartTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("任务开始时间数字型TASK_START_TIME_LONG_","idSpan_TmsTaskT_taskStartTimeLong","idInput_TmsTaskT_taskStartTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("任务结束时间TASK_END_TIME_","idSpan_TmsTaskT_taskEndTime","idInput_TmsTaskT_taskEndTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("任务结束时间数字型TASK_END_TIME_LONG_","idSpan_TmsTaskT_taskEndTimeLong","idInput_TmsTaskT_taskEndTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("计划开始时间PLAN_START_TIME_","idSpan_TmsTaskT_planStartTime","idInput_TmsTaskT_planStartTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("计划开始时间数字型PLAN_START_TIME_LONG_","idSpan_TmsTaskT_planStartTimeLong","idInput_TmsTaskT_planStartTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("计划结束时间PLAN_END_TIME_","idSpan_TmsTaskT_planEndTime","idInput_TmsTaskT_planEndTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("计划结束时间数字型PLAN_END_TIME_LONG_","idSpan_TmsTaskT_planEndTimeLong","idInput_TmsTaskT_planEndTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者CREATE_USER_","idSpan_TmsTaskT_createUser","idInput_TmsTaskT_createUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_TmsTaskT_createTime","idInput_TmsTaskT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_TmsTaskT_createTimeLong","idInput_TmsTaskT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","idSpan_TmsTaskT_updateUser","idInput_TmsTaskT_updateUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_TmsTaskT_updateTime","idInput_TmsTaskT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_TmsTaskT_updateTimeLong","idInput_TmsTaskT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_TmsTaskT_state","idInput_TmsTaskT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_TmsTaskT_desc","idInput_TmsTaskT_desc","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("租户编码TENANT_CODE_","idSpan_TmsTaskT_tenantCode","idInput_TmsTaskT_tenantCode","CHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("租户编号TENANT_NUMBER_","idSpan_TmsTaskT_tenantNumber","idInput_TmsTaskT_tenantNumber","INT","yes","10","0","0",a,true);


	}
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
