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
	action="${pageContext.request.contextPath}/all/workflow/actwf/actwf_flow_def/t/save/bootstrap.do"
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
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . actwfFlowDefId%></td>
		<td><input id="idInput_ActwfFlowDefT_actwfFlowDefId" type="text" name="actwf_flow_def.actwfFlowDefId"
			value="${requestScope.s.actwfFlowDefId}"></td>
		<td><span id="idSpan_ActwfFlowDefT_actwfFlowDefId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . name%></td>
		<td><input id="idInput_ActwfFlowDefT_name" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . name%>"  type="text" name="actwf_flow_def.name"
			value="${requestScope.s.name}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_name"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . key%></td>
		<td><input id="idInput_ActwfFlowDefT_key" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . key%>"  type="text" name="actwf_flow_def.key"
			value="${requestScope.s.key}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_key"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . actDefId%></td>
		<td><input id="idInput_ActwfFlowDefT_actDefId" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . actDefId%>"  type="text" name="actwf_flow_def.actDefId"
			value="${requestScope.s.actDefId}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_actDefId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . actDepId%></td>
		<td><input id="idInput_ActwfFlowDefT_actDepId" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . actDepId%>"  type="text" name="actwf_flow_def.actDepId"
			value="${requestScope.s.actDepId}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_actDepId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . actModelId%></td>
		<td><input id="idInput_ActwfFlowDefT_actModelId" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . actModelId%>"  type="text" name="actwf_flow_def.actModelId"
			value="${requestScope.s.actModelId}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_actModelId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . edition%></td>
		<td><input id="idInput_ActwfFlowDefT_edition" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . edition%>"  type="text" name="actwf_flow_def.edition"
			value="${requestScope.s.edition}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_edition"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . mainEdition%></td>
		<td><input id="idInput_ActwfFlowDefT_mainEdition" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . mainEdition%>"  type="text" name="actwf_flow_def.mainEdition"
			value="${requestScope.s.mainEdition}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_mainEdition"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . setting%></td>
		<td><input id="idInput_ActwfFlowDefT_setting" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . setting%>"  type="text" name="actwf_flow_def.setting"
			value="${requestScope.s.setting}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_setting"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . state%></td>
		<td><input id="idInput_ActwfFlowDefT_state" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . state%>"  type="text" name="actwf_flow_def.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_state"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . sn%></td>
		<td><input id="idInput_ActwfFlowDefT_sn" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . sn%>"  type="text" name="actwf_flow_def.sn"
			value="${requestScope.s.sn}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_sn"></span>
		</td>
</tr>	

<!-- 


<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . createUser%></td>
		<td><input id="idInput_ActwfFlowDefT_createUser" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . createUser%>"  type="text" name="actwf_flow_def.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_createUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . createTime%></td>
		<td><input id="idInput_ActwfFlowDefT_createTime" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . createTime%>"  type="text" name="actwf_flow_def.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_ActwfFlowDefT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_ActwfFlowDefT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . createTimeLong%></td>
		<td><input id="idInput_ActwfFlowDefT_createTimeLong" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . createTimeLong%>"  type="text" name="actwf_flow_def.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . updateUser%></td>
		<td><input id="idInput_ActwfFlowDefT_updateUser" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . updateUser%>"  type="text" name="actwf_flow_def.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_updateUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . updateTime%></td>
		<td><input id="idInput_ActwfFlowDefT_updateTime" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . updateTime%>"  type="text" name="actwf_flow_def.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_ActwfFlowDefT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_ActwfFlowDefT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . updateTimeLong%></td>
		<td><input id="idInput_ActwfFlowDefT_updateTimeLong" placeholder="请输入<%=all.workflow.actwf.actwf_flow_def.t.alias.ActwfFlowDefTAlias . updateTimeLong%>"  type="text" name="actwf_flow_def.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_ActwfFlowDefT_updateTimeLong"></span>
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
var url = "${pageContext.request.contextPath}/all/workflow/actwf/actwf_flow_def/t/list/bootstrap.do";
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
flag=ayCheckColumn("名称","idSpan_ActwfFlowDefT_name","idInput_ActwfFlowDefT_name","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("英文标识KEY_","idSpan_ActwfFlowDefT_key","idInput_ActwfFlowDefT_key","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("ACT流程定义ID","idSpan_ActwfFlowDefT_actDefId","idInput_ActwfFlowDefT_actDefId","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("ACT流程发布ID","idSpan_ActwfFlowDefT_actDepId","idInput_ActwfFlowDefT_actDepId","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("设计模型ID,关联activity中的ACT_RE_MODEL表主键","idSpan_ActwfFlowDefT_actModelId","idInput_ActwfFlowDefT_actModelId","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("显示版本EDITION_","idSpan_ActwfFlowDefT_edition","idInput_ActwfFlowDefT_edition","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否主版本","idSpan_ActwfFlowDefT_mainEdition","idInput_ActwfFlowDefT_mainEdition","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}


if(false){
	
	flag=ayCheckColumn("定义属性设置","idSpan_ActwfFlowDefT_setting","idInput_ActwfFlowDefT_setting","TEXT","yes","65535","0","0",a,true);
	if(flag){}else{return_flag=false;}
	
	
flag=ayCheckColumn("创建者CREATE_USER_","idSpan_ActwfFlowDefT_createUser","idInput_ActwfFlowDefT_createUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_ActwfFlowDefT_createTime","idInput_ActwfFlowDefT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_ActwfFlowDefT_createTimeLong","idInput_ActwfFlowDefT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","idSpan_ActwfFlowDefT_updateUser","idInput_ActwfFlowDefT_updateUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_ActwfFlowDefT_updateTime","idInput_ActwfFlowDefT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_ActwfFlowDefT_updateTimeLong","idInput_ActwfFlowDefT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
}

flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_ActwfFlowDefT_state","idInput_ActwfFlowDefT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","idSpan_ActwfFlowDefT_sn","idInput_ActwfFlowDefT_sn","INT","yes","10","0","0",a,true);
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