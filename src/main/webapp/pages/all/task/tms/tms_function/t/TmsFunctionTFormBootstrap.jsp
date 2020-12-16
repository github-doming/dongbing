<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>

<script type="text/javascript" 
	src="${pageContext.request.contextPath}/pages/c/x/imports/compo/cyui/cyui_select.js?version=1"></script>
	
 <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/editor/ueditor/ueditor.config.js"></script>
  <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/editor/ueditor/ueditor.all.js"></script>
   	
<title></title>
</head>
<body>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>
<form id="id_form_save"
	action="${pageContext.request.contextPath}/all/task/tms/tms_function/t/save/bootstrap.do"
	method="post">
<table class="table  table-bordered table-hover" border="0">
	<tr>
		<th colspan="3">功能接口 <c:choose>
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
<tr style="display: none;">
		<td>
		<p class="text-right">上一级功能接口id：</p>
		</td>
		<td><input id="id_input_parent_menu_id" type="text"
			name="tms_function.parent" value="${requestScope.p.tmsFunctionId}"></td>
		<td></td>
	</tr>
	<tr>
		<td>
		<p class="text-right">上一级功能接口名称：</p>
		</td>
		<td><input id="id_input_parent_menu_name"
			class="input-medium search-query" readonly="readonly" type="text"
			name="" value="${requestScope.p.name}"> <input
			onclick="parent('${requestScope.s.path}');" class="btn" type="button"
			value="选择"></td>
		<td width="30%"></td>
	</tr>

<tr style="display: none;">
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . tmsFunctionId%></td>
		<td><input id="idInput_TmsFunctionT_tmsFunctionId"  type="text" name="tms_function.tmsFunctionId"
			value="${requestScope.s.tmsFunctionId}"></td>
		<td><span id="idSpan_TmsFunctionT_tmsFunctionId"></span>
		</td>
</tr>	

			
	

<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . name%></td>
		<td><input id="idInput_TmsFunctionT_name"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . name%>" type="text" name="tms_function.name"
			value="${requestScope.s.name}">


		</td>
		<td><span id="idSpan_TmsFunctionT_name"></span>
		</td>
</tr>	



<tr>
				<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . projectName%></td>
				<td><span defValue="${requestScope.s.tmsProjectId}" id="idInput_TmsFunctionT_tmsProjectId" name="tms_function.tmsProjectId"
					class="cyui_select" idColumn="TMS_PROJECT_ID_" textColumn="PROJECT_NAME_"
					url="${pageContext.request.contextPath}/all/task/tms/tms_project/t/select/list.do">
				</span></td>
				<td><span id="idSpan_TmsFunctionT_tmsProjectId"></span></td>
</tr>



<tr>
				<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . type%></td>
				<td><span defValue="${requestScope.s.type}" id="idInput_TmsFunctionT_type" name="tms_function.type"
					class="cyui_select" idColumn="table_key_" textColumn="value_cn_"
					url="${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict/list_by_code.do?code=project_type">
				</span></td>
				<td><span id="idSpan_TmsFunctionT_type"></span></td>
</tr>


<tr>
		<td width="10%"  align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiDef%></td>
		<td width="80%">


<script type="text/plain" id="idInput_TmsFunctionT_apiDef"  name="tms_function.apiDef" >${requestScope.s.apiDef}</script>
    
		</td>
		<td width="10%"><span id="idSpan_TmsFunctionT_apiDef"></span>
		</td>
</tr>	



<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiExample%></td>
		<td width="80%">
<script type="text/plain" id="idInput_TmsFunctionT_apiExample" name="tms_function.apiExample" >${requestScope.s.apiExample}</script>
    
		</td>
		<td><span id="idSpan_TmsFunctionT_apiExample"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiParameter%></td>
		<td width="80%">
<script type="text/plain" id="idInput_TmsFunctionT_apiParameter" name="tms_function.apiParameter" >${requestScope.s.apiParameter}</script>
 
		</td>
		<td><span id="idSpan_TmsFunctionT_apiParameter"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiReturn%></td>
		<td width="80%">
		
<script type="text/plain" id="idInput_TmsFunctionT_apiReturn" name="tms_function.apiReturn" >${requestScope.s.apiReturn}</script>
 

		</td>
		<td><span id="idSpan_TmsFunctionT_apiReturn"></span>
		</td>
</tr>	




<tr>
				<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . backendDeveloperName%></td>
				<td><span defValue="${requestScope.s.backendDeveloperId}" id="idInput_TmsFunctionT_backendDeveloperId" name="tms_function.backendDeveloperId"
					class="cyui_select" idColumn="USER_ID_" textColumn="APP_USER_NAME_"
					url="${pageContext.request.contextPath}/all/task/tms/project_user/app_user/select/list.do">
				</span></td>
				<td><span id="idSpan_TmsFunctionT_backendDeveloperId"></span></td>
</tr>



<tr>
				<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . frontDeveloperName%></td>
				<td><span defValue="${requestScope.s.frontDeveloperId}" id="idInput_TmsFunctionT_frontDeveloperId" name="tms_function.frontDeveloperId"
					class="cyui_select" idColumn="USER_ID_" textColumn="APP_USER_NAME_"
					url="${pageContext.request.contextPath}/all/task/tms/project_user/app_user/select/list.do">
				</span></td>
				<td><span id="idSpan_TmsFunctionT_frontDeveloperId"></span></td>
</tr>






<!--  

<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . idx%></td>
		<td><input id="idInput_TmsFunctionT_idx"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . idx%>" type="text" name="tms_function.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_TmsFunctionT_idx"></span>
		</td>
</tr>	


<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . frontDeveloperId%></td>
		<td><input id="idInput_TmsFunctionT_frontDeveloperId"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . frontDeveloperId%>" type="text" name="tms_function.frontDeveloperId"
			value="${requestScope.s.frontDeveloperId}">


		</td>
		<td><span id="idSpan_TmsFunctionT_frontDeveloperId"></span>
		</td>
</tr>

<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . frontDeveloperId%></td>
		<td><input id="idInput_TmsFunctionT_frontDeveloperId"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . frontDeveloperId%>" type="text" name="tms_function.frontDeveloperId"
			value="${requestScope.s.frontDeveloperId}">


		</td>
		<td><span id="idSpan_TmsFunctionT_frontDeveloperId"></span>
		</td>
</tr>
	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . frontDeveloperName%></td>
		<td><input id="idInput_TmsFunctionT_frontDeveloperName"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . frontDeveloperName%>" type="text" name="tms_function.frontDeveloperName"
			value="${requestScope.s.frontDeveloperName}">


		</td>
		<td><span id="idSpan_TmsFunctionT_frontDeveloperName"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . backendDeveloperId%></td>
		<td><input id="idInput_TmsFunctionT_backendDeveloperId"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . backendDeveloperId%>" type="text" name="tms_function.backendDeveloperId"
			value="${requestScope.s.backendDeveloperId}">


		</td>
		<td><span id="idSpan_TmsFunctionT_backendDeveloperId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . backendDeveloperName%></td>
		<td><input id="idInput_TmsFunctionT_backendDeveloperName"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . backendDeveloperName%>" type="text" name="tms_function.backendDeveloperName"
			value="${requestScope.s.backendDeveloperName}">


		</td>
		<td><span id="idSpan_TmsFunctionT_backendDeveloperName"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiDef%></td>
		<td><input id="idInput_TmsFunctionT_apiDef"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiDef%>" type="text" name="tms_function.apiDef"
			value="${requestScope.s.apiDef}">


		</td>
		<td><span id="idSpan_TmsFunctionT_apiDef"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiExample%></td>
		<td><input id="idInput_TmsFunctionT_apiExample"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiExample%>" type="text" name="tms_function.apiExample"
			value="${requestScope.s.apiExample}">


		</td>
		<td><span id="idSpan_TmsFunctionT_apiExample"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiParameter%></td>
		<td><input id="idInput_TmsFunctionT_apiParameter"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiParameter%>" type="text" name="tms_function.apiParameter"
			value="${requestScope.s.apiParameter}">


		</td>
		<td><span id="idSpan_TmsFunctionT_apiParameter"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiReturn%></td>
		<td><input id="idInput_TmsFunctionT_apiReturn"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . apiReturn%>" type="text" name="tms_function.apiReturn"
			value="${requestScope.s.apiReturn}">


		</td>
		<td><span id="idSpan_TmsFunctionT_apiReturn"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . step%></td>
		<td><input id="idInput_TmsFunctionT_step"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . step%>" type="text" name="tms_function.step"
			value="${requestScope.s.step}">


		</td>
		<td><span id="idSpan_TmsFunctionT_step"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . sn%></td>
		<td><input id="idInput_TmsFunctionT_sn"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . sn%>" type="text" name="tms_function.sn"
			value="${requestScope.s.sn}">


		</td>
		<td><span id="idSpan_TmsFunctionT_sn"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . parent%></td>
		<td><input id="idInput_TmsFunctionT_parent"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . parent%>" type="text" name="tms_function.parent"
			value="${requestScope.s.parent}">


		</td>
		<td><span id="idSpan_TmsFunctionT_parent"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . path%></td>
		<td><input id="idInput_TmsFunctionT_path"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . path%>" type="text" name="tms_function.path"
			value="${requestScope.s.path}">


		</td>
		<td><span id="idSpan_TmsFunctionT_path"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . treeCode%></td>
		<td><input id="idInput_TmsFunctionT_treeCode"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . treeCode%>" type="text" name="tms_function.treeCode"
			value="${requestScope.s.treeCode}">


		</td>
		<td><span id="idSpan_TmsFunctionT_treeCode"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . urlView%></td>
		<td><input id="idInput_TmsFunctionT_urlView"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . urlView%>" type="text" name="tms_function.urlView"
			value="${requestScope.s.urlView}">


		</td>
		<td><span id="idSpan_TmsFunctionT_urlView"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . createUser%></td>
		<td><input id="idInput_TmsFunctionT_createUser"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . createUser%>" type="text" name="tms_function.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="idSpan_TmsFunctionT_createUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . createTime%></td>
		<td><input id="idInput_TmsFunctionT_createTime"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . createTime%>" type="text" name="tms_function.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_TmsFunctionT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsFunctionT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . createTimeLong%></td>
		<td><input id="idInput_TmsFunctionT_createTimeLong"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . createTimeLong%>" type="text" name="tms_function.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_TmsFunctionT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . updateUser%></td>
		<td><input id="idInput_TmsFunctionT_updateUser"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . updateUser%>" type="text" name="tms_function.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="idSpan_TmsFunctionT_updateUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . updateTime%></td>
		<td><input id="idInput_TmsFunctionT_updateTime"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . updateTime%>" type="text" name="tms_function.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_TmsFunctionT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsFunctionT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . updateTimeLong%></td>
		<td><input id="idInput_TmsFunctionT_updateTimeLong"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . updateTimeLong%>" type="text" name="tms_function.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_TmsFunctionT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . state%></td>
		<td><input id="idInput_TmsFunctionT_state"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . state%>" type="text" name="tms_function.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_TmsFunctionT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . desc%></td>
		<td><input id="idInput_TmsFunctionT_desc"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . desc%>" type="text" name="tms_function.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_TmsFunctionT_desc"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . tenantCode%></td>
		<td><input id="idInput_TmsFunctionT_tenantCode"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . tenantCode%>" type="text" name="tms_function.tenantCode"
			value="${requestScope.s.tenantCode}">


		</td>
		<td><span id="idSpan_TmsFunctionT_tenantCode"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . tenantNumber%></td>
		<td><input id="idInput_TmsFunctionT_tenantNumber"  placeholder="请输入<%=all.task.tms.tms_function.t.alias.TmsFunctionTAlias . tenantNumber%>" type="text" name="tms_function.tenantNumber"
			value="${requestScope.s.tenantNumber}">


		</td>
		<td><span id="idSpan_TmsFunctionT_tenantNumber"></span>
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
	var url = "${pageContext.request.contextPath}/all/task/tms/tms_function/t/parent/bootstrap.do";
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
	var url = "${pageContext.request.contextPath}/all/task/tms/tms_function/t/parent/bootstrap.do";
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
 	var url = "${pageContext.request.contextPath}/all/task/tms/tms_function/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","idSpan_TmsFunctionT_idx","idInput_TmsFunctionT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("TMS项目表主键TMS_PROJECT_ID_","idSpan_TmsFunctionT_tmsProjectId","idInput_TmsFunctionT_tmsProjectId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("项目名称PROJECT_NAME_","idSpan_TmsFunctionT_projectName","idInput_TmsFunctionT_projectName","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("前端开发人员主键(APP用户主键)","idSpan_TmsFunctionT_frontDeveloperId","idInput_TmsFunctionT_frontDeveloperId","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("前端开发人员名称FRONT_DEVELOPER_NAME_","idSpan_TmsFunctionT_frontDeveloperName","idInput_TmsFunctionT_frontDeveloperName","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("后台开发人员主键(APP用户主键)","idSpan_TmsFunctionT_backendDeveloperId","idInput_TmsFunctionT_backendDeveloperId","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("后台开发人员名称BACKEND_DEVELOPER_NAME_","idSpan_TmsFunctionT_backendDeveloperName","idInput_TmsFunctionT_backendDeveloperName","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("功能接口名称NAME_","idSpan_TmsFunctionT_name","idInput_TmsFunctionT_name","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接口定义API_DEF_","idSpan_TmsFunctionT_apiDef","idInput_TmsFunctionT_apiDef","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接口示例API_EXAMPLE","idSpan_TmsFunctionT_apiExample","idInput_TmsFunctionT_apiExample","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接口参数API_PARAMETER_","idSpan_TmsFunctionT_apiParameter","idInput_TmsFunctionT_apiParameter","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("接口返回说明API_RETURN_","idSpan_TmsFunctionT_apiReturn","idInput_TmsFunctionT_apiReturn","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("进度STEP_","idSpan_TmsFunctionT_step","idInput_TmsFunctionT_step","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","idSpan_TmsFunctionT_sn","idInput_TmsFunctionT_sn","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树上一级","idSpan_TmsFunctionT_parent","idInput_TmsFunctionT_parent","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树PATH_","idSpan_TmsFunctionT_path","idInput_TmsFunctionT_path","VARCHAR","yes","1024","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树编码","idSpan_TmsFunctionT_treeCode","idInput_TmsFunctionT_treeCode","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型TYPE_","idSpan_TmsFunctionT_type","idInput_TmsFunctionT_type","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("查看地址URL_VIEW_","idSpan_TmsFunctionT_urlView","idInput_TmsFunctionT_urlView","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者CREATE_USER_","idSpan_TmsFunctionT_createUser","idInput_TmsFunctionT_createUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_TmsFunctionT_createTime","idInput_TmsFunctionT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_TmsFunctionT_createTimeLong","idInput_TmsFunctionT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","idSpan_TmsFunctionT_updateUser","idInput_TmsFunctionT_updateUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_TmsFunctionT_updateTime","idInput_TmsFunctionT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_TmsFunctionT_updateTimeLong","idInput_TmsFunctionT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_TmsFunctionT_state","idInput_TmsFunctionT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_TmsFunctionT_desc","idInput_TmsFunctionT_desc","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("租户编码TENANT_CODE_","idSpan_TmsFunctionT_tenantCode","idInput_TmsFunctionT_tenantCode","CHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("租户编号TENANT_NUMBER_","idSpan_TmsFunctionT_tenantNumber","idInput_TmsFunctionT_tenantNumber","INT","yes","10","0","0",a,true);

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


<script type="text/javascript">
        UE.getEditor('idInput_TmsFunctionT_apiDef',{
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            toolbars:[['FullScreen', 'Source', 'Undo', 'Redo','Bold','test']],
            //focus时自动清空初始化时的内容
           // autoClearinitialContent:true,
            //关闭字数统计
            wordCount:false,
            //关闭elementPath
            elementPathEnabled:false,
            //默认的编辑区域高度
            initialFrameHeight:150
            //更多其他参数，请参考ueditor.config.js中的配置项
        })
           UE.getEditor('idInput_TmsFunctionT_apiExample',{
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            toolbars:[['FullScreen', 'Source', 'Undo', 'Redo','Bold','test']],
            //focus时自动清空初始化时的内容
            //autoClearinitialContent:true,
            //关闭字数统计
            wordCount:false,
            //关闭elementPath
            elementPathEnabled:false,
            //默认的编辑区域高度
            initialFrameHeight:150
            //更多其他参数，请参考ueditor.config.js中的配置项
        })
           UE.getEditor('idInput_TmsFunctionT_apiParameter',{
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            toolbars:[['FullScreen', 'Source', 'Undo', 'Redo','Bold','test']],
            //focus时自动清空初始化时的内容
            //autoClearinitialContent:true,
            //关闭字数统计
            wordCount:false,
            //关闭elementPath
            elementPathEnabled:false,
            //默认的编辑区域高度
            initialFrameHeight:150
            //更多其他参数，请参考ueditor.config.js中的配置项
        })
           UE.getEditor('idInput_TmsFunctionT_apiReturn',{
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            toolbars:[['FullScreen', 'Source', 'Undo', 'Redo','Bold','test']],
            //focus时自动清空初始化时的内容
            //autoClearinitialContent:true,
            //关闭字数统计
            wordCount:false,
            //关闭elementPath
            elementPathEnabled:false,
            //默认的编辑区域高度
            initialFrameHeight:150
            //更多其他参数，请参考ueditor.config.js中的配置项
        })
    </script>
    
<script>
	cyui_select("idInput_TmsFunctionT_tmsProjectId");
	cyui_select("idInput_TmsFunctionT_type");
	cyui_select("idInput_TmsFunctionT_frontDeveloperId");
	cyui_select("idInput_TmsFunctionT_backendDeveloperId");
	
	
</script>

