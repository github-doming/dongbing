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
		<span style="font-size:30px;">角色管理
			<c:choose>
				<c:when test="${requestScope.id==null}">添加</c:when>
				<c:otherwise>修改</c:otherwise>
			</c:choose>
		</span>
	</div>
</center>
<form id="id_form_save"
	action="${pageContext.request.contextPath}/com/ibm/admin/ibm_role/w/save.do"
	method="post">
	<input style="width:100px;height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white;margin: 20px;" onclick="back();" class="btn"
				type="button" value="返回列表"></input>
<table style="width: 80%;margin: 50px auto;text-align: center;"  class="table  table-bordered table-hover" border="1">
<tr>
		<td align="right" width="45%">角色名</td>
		<td align="left" ><input id="topicId_input"  type="hidden" name="IBM_ROLE_ID_"
			value="${requestScope.s.ibmRoleId}">
			<input id="id_input$ibm_input$ROLE_NAME_" placeholder="请输入角色名"  type="text" name="ROLE_NAME_"
			value="${requestScope.s.roleName}">
			<span id="id_span$ibm_role$ROLE_NAME_"></span>

		</td>
</tr>	
<tr>
		<td align="right">角色CODE</td>
		<td align="left" >
			<c:choose>
				<c:when test="${requestScope.id==null}">
					<input id="id_input$ibm_input$ROLE_CODE_" placeholder="请输入角色CODE"  type="text" name="ROLE_CODE_" value="${requestScope.s.roleCode}">
				</c:when>
				<c:otherwise>
					<input id="id_input$ibm_input$ROLE_CODE_" readonly="readonly" placeholder="请输入角色CODE" style="background-color: gainsboro;" type="text" name="ROLE_CODE_" value="${requestScope.s.roleCode}">
				</c:otherwise>
			</c:choose>
			
			<span id="id_span$ibm_role$ROLE_CODE_"></span>
		</td>
</tr>
<tr>
		<td align="right">权限等级</td>
		<td align="left" ><input id="id_input$ibm_input$ROLE_LEVEL_" placeholder="请输入权限等级"  type="text" name="ROLE_LEVEL_"
			value="${requestScope.s.roleLevel}">
			<span id="id_span$ibm_role$ROLE_LEVEL_"></span>
		</td>
</tr>
<tr>
		<td align="right">托管总数</td>
		<td align="left" ><input id="id_input$ibm_input$ONLINE_NUMBER_MAX_" placeholder="请输入托管总数"  type="text" name="ONLINE_NUMBER_MAX_"
			value="${requestScope.s.onlineNumberMax}">
			<span id="id_span$ibm_role$ONLINE_NUMBER_MAX_"></span>
		</td>
</tr>
<tr>
		<td colspan="2">
			<button type="button"  data-toggle="modal" data-target="#handicapModal">
			  设置盘口权限
			</button>
			<input type="hidden" name="HANDICAP_ID_" id="handicap_input_id" value="${requestScope.s.handicapId}"/>
			<div ></div>
		</td>
</tr>
<tr>
		<td colspan="2">
			<button type="button"  data-toggle="modal" data-target="#planModal">
			  设置方案权限
			</button>
			<input type="hidden" name="PLAN_ID_" id="plan_input_id" value="${requestScope.s.planId}"/>
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

<!-- 盘口Modal -->
<div class="modal fade" id="handicapModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">设置盘口权限</h4>
      </div>
      <div class="modal-body" id="handicapModalBody">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveHandicap();">保存</button>
      </div>
    </div>
  </div>
</div>

<!-- 方案Modal -->
<div class="modal fade" id="planModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">设置方案权限</h4>
      </div>
      <div class="modal-body" id="planModalBody">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="savePlan();">保存</button>
      </div>
    </div>
  </div>
</div>
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
				 		var handicapArr = $("#handicap_input_id");
				 		if(handicapArr.val().indexOf(v.IBM_HANDICAP_ID_)!=-1){
				 			option = "<input type='checkbox' name='handicapIds' class='handicap_input_class' checked='checked' id='handicap_id+"+i+"' value='"+v.IBM_HANDICAP_ID_+"'/><label for='handicap_id+"+i+"'>"+v.HANDICAP_NAME_+"</label><br/>";
				 		}else{
				 			option = "<input type='checkbox' name='handicapIds' class='handicap_input_class' id='handicap_id+"+i+"' value='"+v.IBM_HANDICAP_ID_+"'/><label for='handicap_id+"+i+"'>"+v.HANDICAP_NAME_+"</label><br/>";
				 		}
				 		var handicap_selects = $("#handicapModalBody");
				 		handicap_selects.append(option);
				 	});
				 },
				 error:function(){
				 	alert("失败");
				 }
			 });
			 
		$.ajax({
				 url:"<%=request.getContextPath()%>/ibm/admin/ibm_plan/w/listJson.do",
				 dataType:"json",
				 type:"post",
				 success:function(data){
					var option;
				 	$(data.data).each(function(i,v){
				 		var planArr = $("#plan_input_id");
				 		if(planArr.val().indexOf(v.IBM_PLAN_ID_)!=-1){
				 			option = "<input type='checkbox' name='planIds' class='plan_input_class' checked='checked' id='plan_id+"+i+"' value='"+v.IBM_PLAN_ID_+"' /><label for='plan_id+"+i+"'>"+v.GAME_NAME_+":"+v.PLAN_NAME_+"</label><br/>";
				 		}else{
				 			option = "<input type='checkbox' name='planIds' class='plan_input_class' id='plan_id+"+i+"' value='"+v.IBM_PLAN_ID_+"'/><label for='plan_id+"+i+"'>"+v.GAME_NAME_+":"+v.PLAN_NAME_+"</label><br/>";
				 		}
				 		var plan_selects = $("#planModalBody");
				 		plan_selects.append(option);
				 	});
				 },
				 error:function(){
				 	alert("失败");
				 }
			 });
	});

	function saveHandicap(){
		$("#handicap_input_id").val("");
		var handicaps = $("input[name='handicapIds']:checked");
		var arr = [];
		handicaps.each(function(index,value){
			arr.push(value.value);
		});
		console.log(arr);
		$("#handicap_input_id").val(arr.join(","));
	}

	function savePlan(){
		$("#plan_input_id").val("");
		var plans = $("input[name='planIds']:checked");
		var arr = [];
		plans.each(function(index,value){
			arr.push(value.value);
		});
		console.log(arr);
		$("#plan_input_id").val(arr.join(","));
	}
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
	flag=ayCheckColumn("角色名","id_span$ibm_role$ROLE_NAME_","id_input$ibm_input$ROLE_NAME_","VARCHAR","no","64","0","0",a,true);
	if(flag){}else{return_flag=false;}
	flag=ayCheckColumn("角色CODE","id_span$ibm_role$ROLE_CODE_","id_input$ibm_input$ROLE_CODE_","VARCHAR","no","64","0","0",a,true);
	if(flag){}else{return_flag=false;}
	flag=ayCheckColumn("角色等级","id_span$ibm_role$ROLE_LEVEL_","id_input$ibm_input$ROLE_LEVEL_","INT","no","3","0","0",a,true);
	if(flag){}else{return_flag=false;}
	flag=ayCheckColumn("允许会员在线数量","id_span$ibm_role$ONLINE_NUMBER_MAX_","id_input$ibm_input$ONLINE_NUMBER_MAX_","INT","no","3","0","0",a,true);
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