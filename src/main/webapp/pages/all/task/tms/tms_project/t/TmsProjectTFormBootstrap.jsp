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
	action="${pageContext.request.contextPath}/all/task/tms/tms_project/t/save/bootstrap.do"
	method="post">
<table class="table  table-bordered table-hover" border="0">
	<tr>
		<th colspan="3">项目 <c:choose>
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
		<p class="text-right">上一级项目id：</p>
		</td>
		<td><input id="id_input_parent_menu_id" type="text"
			name="tms_project.parent" value="${requestScope.p.tmsProjectId}"></td>
		<td></td>
	</tr>
	<tr>
		<td>
		<p class="text-right">上一级项目名称：</p>
		</td>
		<td><input id="id_input_parent_menu_name"
			class="input-medium search-query" readonly="readonly" type="text"
			name="" value="${requestScope.p.projectName}"> <input
			onclick="parent('${requestScope.s.path}');" class="btn" type="button"
			value="选择"></td>
		<td width="30%"></td>
	</tr>

<tr style="display: none;">
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . tmsProjectId%></td>
		<td><input id="idInput_TmsProjectT_tmsProjectId"  type="text" name="tms_project.tmsProjectId"
			value="${requestScope.s.tmsProjectId}"></td>
		<td><span id="idSpan_TmsProjectT_tmsProjectId"></span>
		</td>
</tr>	


<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . projectName%></td>
		<td><input id="idInput_TmsProjectT_projectName"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . projectName%>" type="text" name="tms_project.projectName"
			value="${requestScope.s.projectName}">


		</td>
		<td><span id="idSpan_TmsProjectT_projectName"></span>
		</td>
</tr>	

<!-- 

<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . idx%></td>
		<td><input id="idInput_TmsProjectT_idx"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . idx%>" type="text" name="tms_project.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_TmsProjectT_idx"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . sn%></td>
		<td><input id="idInput_TmsProjectT_sn"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . sn%>" type="text" name="tms_project.sn"
			value="${requestScope.s.sn}">


		</td>
		<td><span id="idSpan_TmsProjectT_sn"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . parent%></td>
		<td><input id="idInput_TmsProjectT_parent"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . parent%>" type="text" name="tms_project.parent"
			value="${requestScope.s.parent}">


		</td>
		<td><span id="idSpan_TmsProjectT_parent"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . path%></td>
		<td><input id="idInput_TmsProjectT_path"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . path%>" type="text" name="tms_project.path"
			value="${requestScope.s.path}">


		</td>
		<td><span id="idSpan_TmsProjectT_path"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . treeCode%></td>
		<td><input id="idInput_TmsProjectT_treeCode"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . treeCode%>" type="text" name="tms_project.treeCode"
			value="${requestScope.s.treeCode}">


		</td>
		<td><span id="idSpan_TmsProjectT_treeCode"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . type%></td>
		<td><input id="idInput_TmsProjectT_type"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . type%>" type="text" name="tms_project.type"
			value="${requestScope.s.type}">


		</td>
		<td><span id="idSpan_TmsProjectT_type"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . urlView%></td>
		<td><input id="idInput_TmsProjectT_urlView"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . urlView%>" type="text" name="tms_project.urlView"
			value="${requestScope.s.urlView}">


		</td>
		<td><span id="idSpan_TmsProjectT_urlView"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . createUser%></td>
		<td><input id="idInput_TmsProjectT_createUser"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . createUser%>" type="text" name="tms_project.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="idSpan_TmsProjectT_createUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . createTime%></td>
		<td><input id="idInput_TmsProjectT_createTime"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . createTime%>" type="text" name="tms_project.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_TmsProjectT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsProjectT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . createTimeLong%></td>
		<td><input id="idInput_TmsProjectT_createTimeLong"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . createTimeLong%>" type="text" name="tms_project.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_TmsProjectT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . updateUser%></td>
		<td><input id="idInput_TmsProjectT_updateUser"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . updateUser%>" type="text" name="tms_project.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="idSpan_TmsProjectT_updateUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . updateTime%></td>
		<td><input id="idInput_TmsProjectT_updateTime"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . updateTime%>" type="text" name="tms_project.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_TmsProjectT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_TmsProjectT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . updateTimeLong%></td>
		<td><input id="idInput_TmsProjectT_updateTimeLong"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . updateTimeLong%>" type="text" name="tms_project.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_TmsProjectT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . state%></td>
		<td><input id="idInput_TmsProjectT_state"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . state%>" type="text" name="tms_project.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_TmsProjectT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . desc%></td>
		<td><input id="idInput_TmsProjectT_desc"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . desc%>" type="text" name="tms_project.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_TmsProjectT_desc"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . tenantCode%></td>
		<td><input id="idInput_TmsProjectT_tenantCode"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . tenantCode%>" type="text" name="tms_project.tenantCode"
			value="${requestScope.s.tenantCode}">


		</td>
		<td><span id="idSpan_TmsProjectT_tenantCode"></span>
		</td>
</tr>	


 
<tr>
		<td align="right"><%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . tenantNumber%></td>
		<td><input id="idInput_TmsProjectT_tenantNumber"  placeholder="请输入<%=all.task.tms.tms_project.t.alias.TmsProjectTAlias . tenantNumber%>" type="text" name="tms_project.tenantNumber"
			value="${requestScope.s.tenantNumber}">


		</td>
		<td><span id="idSpan_TmsProjectT_tenantNumber"></span>
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
	var url = "${pageContext.request.contextPath}/all/task/tms/tms_project/t/parent/bootstrap.do";
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
	var url = "${pageContext.request.contextPath}/all/task/tms/tms_project/t/parent/bootstrap.do";
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
 	var url = "${pageContext.request.contextPath}/all/task/tms/tms_project/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","idSpan_TmsProjectT_idx","idInput_TmsProjectT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","idSpan_TmsProjectT_sn","idInput_TmsProjectT_sn","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树上一级","idSpan_TmsProjectT_parent","idInput_TmsProjectT_parent","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树PATH_","idSpan_TmsProjectT_path","idInput_TmsProjectT_path","VARCHAR","yes","1024","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树编码","idSpan_TmsProjectT_treeCode","idInput_TmsProjectT_treeCode","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("项目名称PROJECT_NAME_","idSpan_TmsProjectT_projectName","idInput_TmsProjectT_projectName","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型TYPE_","idSpan_TmsProjectT_type","idInput_TmsProjectT_type","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("查看地址URL_VIEW_","idSpan_TmsProjectT_urlView","idInput_TmsProjectT_urlView","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者CREATE_USER_","idSpan_TmsProjectT_createUser","idInput_TmsProjectT_createUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_TmsProjectT_createTime","idInput_TmsProjectT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_TmsProjectT_createTimeLong","idInput_TmsProjectT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","idSpan_TmsProjectT_updateUser","idInput_TmsProjectT_updateUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_TmsProjectT_updateTime","idInput_TmsProjectT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_TmsProjectT_updateTimeLong","idInput_TmsProjectT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_TmsProjectT_state","idInput_TmsProjectT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_TmsProjectT_desc","idInput_TmsProjectT_desc","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("租户编码TENANT_CODE_","idSpan_TmsProjectT_tenantCode","idInput_TmsProjectT_tenantCode","CHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("租户编号TENANT_NUMBER_","idSpan_TmsProjectT_tenantNumber","idInput_TmsProjectT_tenantNumber","INT","yes","10","0","0",a,true);
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
