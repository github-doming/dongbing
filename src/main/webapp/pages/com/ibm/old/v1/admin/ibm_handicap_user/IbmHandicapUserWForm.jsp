<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
</head>
<script type="text/javascript" src="<%=request.getContextPath() %>/pages/cosmos/lib/all/third/editor/kindeditor/kindeditor-all-min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/pages/cosmos/lib/all/third/editor/kindeditor/lang/zh-CN.js"></script>
<body>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>
<center>
	<div style="color:white;width:100%;height:100px;background-color:rgb(29,41,57);padding-top:20px">
		<span style="font-size:30px;">用户盘口管理
			<c:choose>
				<c:when test="${requestScope.id==null}">添加</c:when>
				<c:otherwise>修改</c:otherwise>
			</c:choose>
		</span>
	</div>
</center>
<form id="id_form_save"
	action="${pageContext.request.contextPath}/ibm/admin/ibm_handicap_user/t/save.do"
	method="post">
	<input style="width:100px;height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white;margin: 20px;" onclick="back();" class="btn"
				type="button" value="返回列表"></input>
<table style="width: 80%;margin: 50px auto;text-align: center;"  class="table  table-bordered table-hover" border="1">
<tr>
		<td align="right" width="45%">用户盘口名称</td>
		<td align="left" ><input id="topicId_input"  type="hidden" name="IBM_HANDICAP_USER_ID_"
			value="${requestScope.s.ibmHandicapUserId}">
			<select id="id_select_HANDICAP_NAME_" name="IBM_HANDICAP_ID_"></select>
		</td>
</tr>	
<tr>
		<td align="right">所属用户</td>
		<td align="left" >
			<select id="id_select_APP_USER_NAME" name="APP_USER_ID_"></select>
		</td>
</tr>
<tr>
		<td align="right">会员最大在线数量</td>
		<td align="left" ><input id="id_input$ibm_input$ONLINE_NUMBER_MAX_" placeholder="请输入会员最大在线数量"  type="number" name="ONLINE_NUMBER_MAX_"
			value="${requestScope.s.onlineNumberMax}">
			<span id="id_span$ibm_handicap$ONLINE_NUMBER_MAX_"></span>
		</td>
</tr>
<tr>
	<td colspan="3">
		<c:choose>
			<c:when test="${requestScope.id==null}">
				<input style="width:300px;height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white" onclick='save();' class="btn" type="button" value="新增">
			</c:when>
			<c:otherwise>
				<input style="width:300px;height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white" onclick='save();' class="btn" type="button" value="编辑">
			</c:otherwise>
		</c:choose>
	</td>
</tr>
</table>
</form>

</body>
<!-- 加载js -->
<script type="text/javascript">

$(document).ready(function(){
		$.ajax({
				 url:"<%=request.getContextPath()%>/ibm/admin/ibm_handicap/w/listJson.do",
				 dataType:"json",
				 type:"post",
				 success:function(data){
					var option;
				 	$(data.data).each(function(i,v){
				 		if('${requestScope.s.handicapId}'==v.IBM_HANDICAP_ID_){
				 			option = "<option value='"+v.IBM_HANDICAP_ID_+"' selected='selected' >"+v.HANDICAP_NAME_+"</option>";
				 		}else{
				 			option = "<option value='"+v.IBM_HANDICAP_ID_+"' >"+v.HANDICAP_NAME_+"</option>";
				 		}
				 		var handicap_selects = $("#id_select_HANDICAP_NAME_");
				 		handicap_selects.append(option);
				 	});
				 },
				 error:function(){
				 	alert("失败");
				 }
		});
			 
		$.ajax({
				 url:"<%=request.getContextPath()%>/ibm/admin/app_user/list_json.do",
				 dataType:"json",
				 type:"post",
				 success:function(data){
					var option;
				 	$(data.data).each(function(i,v){
				 		if('${requestScope.s.appUserId}'==v.APP_USER_ID_){
				 			option = "<option value='"+v.APP_USER_ID_+"' selected='selected' >"+v.APP_USER_NAME_+"</option>";
				 		}else{
				 			option = "<option value='"+v.APP_USER_ID_+"' >"+v.APP_USER_NAME_+"</option>";
				 		}
				 		var user_selects = $("#id_select_APP_USER_NAME");
				 		user_selects.append(option);
				 	});
				 },
				 error:function(){
				 	alert("失败");
				 }
		});
			 
	});
 
/**
 * 
 返回
 */
function back(){
	history.go(-1);
}

/**
 * 
 检查
 */
function check(){
	var a="<font color=red>自定义信息</font>";
	a=null;
	var flag=null;
	var return_flag=true;
flag=ayCheckColumn("会员最大在线数量","id_span$ibm_handicap$ONLINE_NUMBER_MAX_","id_input$ibm_input$ONLINE_NUMBER_MAX_","INT","no","4","0","0",a,true);
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
//	editor.sync();
obj_form.submit();
 }
</script>
<script type="text/javascript">
//初始化日期
</script>
</html>