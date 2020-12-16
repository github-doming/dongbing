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
	action="${pageContext.request.contextPath}/all/workflow/actwf/actwf_business_def/t/save/bootstrap.do"
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
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actwfBusinessDefId%></td>
		<td><input id="idInput_ActwfBusinessDefT_actwfBusinessDefId" type="text" name="actwf_business_def.actwfBusinessDefId"
			value="${requestScope.s.actwfBusinessDefId}"></td>
		<td><span id="idSpan_ActwfBusinessDefT_actwfBusinessDefId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . key%></td>
		<td><input id="idInput_ActwfBusinessDefT_key" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . key%>"  type="text" name="actwf_business_def.key"
			value="${requestScope.s.key}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_key"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . step%></td>
		<td><input id="idInput_ActwfBusinessDefT_step" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . step%>"  type="text" name="actwf_business_def.step"
			value="${requestScope.s.step}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_step"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . name%></td>
		<td><input id="idInput_ActwfBusinessDefT_name" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . name%>"  type="text" name="actwf_business_def.name"
			value="${requestScope.s.name}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_name"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . wfFlowDefId%></td>
		<td><input id="idInput_ActwfBusinessDefT_wfFlowDefId" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . wfFlowDefId%>"  type="text" name="actwf_business_def.wfFlowDefId"
			value="${requestScope.s.wfFlowDefId}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_wfFlowDefId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . wfFormDefId%></td>
		<td><input id="idInput_ActwfBusinessDefT_wfFormDefId" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . wfFormDefId%>"  type="text" name="actwf_business_def.wfFormDefId"
			value="${requestScope.s.wfFormDefId}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_wfFormDefId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actDefId%></td>
		<td><input id="idInput_ActwfBusinessDefT_actDefId" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actDefId%>"  type="text" name="actwf_business_def.actDefId"
			value="${requestScope.s.actDefId}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_actDefId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actDefKey%></td>
		<td><input id="idInput_ActwfBusinessDefT_actDefKey" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . actDefKey%>"  type="text" name="actwf_business_def.actDefKey"
			value="${requestScope.s.actDefKey}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_actDefKey"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . state%></td>
		<td><input id="idInput_ActwfBusinessDefT_state" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . state%>"  type="text" name="actwf_business_def.state"
			value="${requestScope.s.state}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . desc%></td>
		<td><input id="idInput_ActwfBusinessDefT_desc" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . desc%>"  type="text" name="actwf_business_def.desc"
			value="${requestScope.s.desc}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_desc"></span>
		</td>
</tr>	
<!-- 
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createUser%></td>
		<td><input id="idInput_ActwfBusinessDefT_createUser" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createUser%>"  type="text" name="actwf_business_def.createUser"
			value="${requestScope.s.createUser}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_createUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createTime%></td>
		<td><input id="idInput_ActwfBusinessDefT_createTime" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createTime%>"  type="text" name="actwf_business_def.createTime"
			value="${requestScope.s.createTime}">
<img
onclick=ayCalendarShow('idInput_ActwfBusinessDefT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createTimeLong%></td>
		<td><input id="idInput_ActwfBusinessDefT_createTimeLong" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . createTimeLong%>"  type="text" name="actwf_business_def.createTimeLong"
			value="${requestScope.s.createTimeLong}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateUser%></td>
		<td><input id="idInput_ActwfBusinessDefT_updateUser" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateUser%>"  type="text" name="actwf_business_def.updateUser"
			value="${requestScope.s.updateUser}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_updateUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateTime%></td>
		<td><input id="idInput_ActwfBusinessDefT_updateTime" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateTime%>"  type="text" name="actwf_business_def.updateTime"
			value="${requestScope.s.updateTime}">
<img
onclick=ayCalendarShow('idInput_ActwfBusinessDefT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateTimeLong%></td>
		<td><input id="idInput_ActwfBusinessDefT_updateTimeLong" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . updateTimeLong%>"  type="text" name="actwf_business_def.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . sn%></td>
		<td><input id="idInput_ActwfBusinessDefT_sn" placeholder="请输入<%=all.workflow.actwf.actwf_business_def.t.alias.ActwfBusinessDefTAlias . sn%>"  type="text" name="actwf_business_def.sn"
			value="${requestScope.s.sn}">
		</td>
		<td><span id="idSpan_ActwfBusinessDefT_sn"></span>
		</td>
</tr>
 -->
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
var url = "${pageContext.request.contextPath}/all/workflow/actwf/actwf_business_def/t/list/bootstrap.do";
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
flag=ayCheckColumn("英文标识KEY_","idSpan_ActwfBusinessDefT_key","idInput_ActwfBusinessDefT_key","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("完成的步骤","idSpan_ActwfBusinessDefT_step","idInput_ActwfBusinessDefT_step","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("名称NAME_","idSpan_ActwfBusinessDefT_name","idInput_ActwfBusinessDefT_name","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("工作流流程定义主健WF_FLOW_DEF_ID_","idSpan_ActwfBusinessDefT_wfFlowDefId","idInput_ActwfBusinessDefT_wfFlowDefId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("工作流表单定义主键WF_FORM_DEF_ID_","idSpan_ActwfBusinessDefT_wfFormDefId","idInput_ActwfBusinessDefT_wfFormDefId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("ACT流程定义ID","idSpan_ActwfBusinessDefT_actDefId","idInput_ActwfBusinessDefT_actDefId","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("绑定流程KEY","idSpan_ActwfBusinessDefT_actDefKey","idInput_ActwfBusinessDefT_actDefKey","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
if(false){
flag=ayCheckColumn("创建者CREATE_USER_","idSpan_ActwfBusinessDefT_createUser","idInput_ActwfBusinessDefT_createUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_ActwfBusinessDefT_createTime","idInput_ActwfBusinessDefT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_ActwfBusinessDefT_createTimeLong","idInput_ActwfBusinessDefT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","idSpan_ActwfBusinessDefT_updateUser","idInput_ActwfBusinessDefT_updateUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_ActwfBusinessDefT_updateTime","idInput_ActwfBusinessDefT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_ActwfBusinessDefT_updateTimeLong","idInput_ActwfBusinessDefT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","idSpan_ActwfBusinessDefT_sn","idInput_ActwfBusinessDefT_sn","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_ActwfBusinessDefT_state","idInput_ActwfBusinessDefT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
}
flag=ayCheckColumn("描述","idSpan_ActwfBusinessDefT_desc","idInput_ActwfBusinessDefT_desc","VARCHAR","yes","512","0","0",a,true);
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