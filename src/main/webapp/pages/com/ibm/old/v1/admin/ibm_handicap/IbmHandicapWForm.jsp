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
		<span style="font-size:30px;">盘口管理
			<c:choose>
				<c:when test="${requestScope.id==null}">添加</c:when>
				<c:otherwise>修改</c:otherwise>
			</c:choose>
		</span>
	</div>
</center>
<form id="id_form_save"
	action="${pageContext.request.contextPath}/ibm/admin/ibm_handicap/w/save.do"
	method="post">
	<input style="width:100px;height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white;margin: 20px;" onclick="back();" class="btn"
				type="button" value="返回列表"></input>
<table style="width: 80%;margin: 50px auto;text-align: center;"  class="table  table-bordered table-hover" border="1">
<tr>
		<td align="right" width="45%">盘口名称</td>
		<td align="left" ><input id="topicId_input"  type="hidden" name="IBM_HANDICAP_ID_"
			value="${requestScope.s.ibmHandicapId}">
			<input id="topicId_input"  type="hidden" name="IBM_HANDICAP_ITEM_ID_"
			value="${requestScope.i.ibmHandicapItemId}">
			<input id="id_input$ibm_input$HANDICAP_NAME_" placeholder="请输入盘口名称"  type="text" name="HANDICAP_NAME_"
			value="${requestScope.s.handicapName}">
			<span id="id_span$ibm_handicap$HANDICAP_NAME_"></span>

		</td>
</tr>	
<tr>
		<td align="right">盘口CODE</td>
		<td align="left" ><input id="id_input$ibm_input$HANDICAP_CODE_" placeholder="请输入盘口CODE"  type="text" name="HANDICAP_CODE_"
			value="${requestScope.s.handicapCode}">
			<span id="id_span$ibm_handicap$HANDICAP_CODE_"></span>
		</td>
</tr>
<tr>
		<td align="right">盘口地址</td>
		<td align="left" ><input id="id_input$ibm_input$HANDICAP_URL_" placeholder="请输入盘口地址"  type="text" name="HANDICAP_URL_"
			value="${requestScope.i.handicapUrl}">
			<span id="id_span$ibm_handicap$HANDICAP_URL_"></span>
		</td>
</tr>
<tr>
		<td align="right">校验码</td>
		<td align="left" ><input id="id_input$ibm_input$HANDICAP_CAPTCHA_" placeholder="请输入校验码"  type="text" name="HANDICAP_CAPTCHA_"
			value="${requestScope.i.handicapCaptcha}">
			<span id="id_span$ibm_handicap$HANDICAP_CAPTCHA_"></span>
		</td>
</tr>
<tr>
		<td align="right">盘口类型</td>
		<td align="left" ><input id="id_input$ibm_input$HANDICAP_TYPE_" placeholder="请输入盘口类型"  type="text" name="HANDICAP_TYPE_"
			value="${requestScope.s.handicapType}">
			<span id="id_span$ibm_handicap$HANDICAP_TYPE_"></span>
		</td>
</tr>
<tr>
		<td align="right">盘口说明</td>
		<td align="left" ><input id="id_input$ibm_input$HANDICAP_EXPLAIN_" placeholder="请输入盘口说明"  type="text" name="HANDICAP_EXPLAIN_"
			value="${requestScope.s.handicapExplain}">
			<span id="id_span$ibm_handicap$HANDICAP_EXPLAIN_"></span>
		</td>
</tr>
<tr>
		<td align="right">盘口价值</td>
		<td align="left" ><input id="id_input$ibm_input$HANDICAP_WORTH_T_" placeholder="请输入盘口价值"  type="text" name="HANDICAP_WORTH_T_"
			value="${requestScope.s.handicapWorthT}">
			<span id="id_span$ibm_handicap$HANDICAP_WORTH_T_"></span>
		</td>
</tr>
<tr>
		<td align="right">盘口版本</td>
		<td align="left" ><input id="id_input$ibm_input$HANDICAP_VERSION_" placeholder="请输入盘口版本"  type="text" name="HANDICAP_VERSION_"
			value="${requestScope.s.handicapVersion}">
			<span id="id_span$ibm_handicap$HANDICAP_VERSION_"></span>
		</td>
</tr>
<tr>
		<td align="right">排序</td>
		<td align="left" ><input id="id_input$ibm_input$SN_" placeholder="请输入排序"  type="text" name="SN_"
			value="${requestScope.s.sn}">
			<span id="id_span$ibm_handicap$SN_"></span>
		</td>
</tr>
<tr>
		<td colspan="2">
			<button type="button"  data-toggle="modal" data-target="#handicapGameModal">
			  设置盘口游戏
			</button>
			<input type="hidden" name="GAME_ID_" id="handicap_input_id" value="${requestScope.gameIds}"/>
			<div ></div>
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
<div class="modal fade" id="handicapGameModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">设置盘口游戏</h4>
      </div>
      <div class="modal-body" id="handicapModalBody">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveGame();">保存</button>
      </div>
    </div>
  </div>
</div>
</body>
<!-- 加载js -->
<script type="text/javascript">

$(document).ready(function(){
		$.ajax({
				 url:"<%=request.getContextPath()%>/ibm/admin/ibm_game/w/listJson.do",
				 dataType:"json",
				 type:"post",
				 success:function(data){
					var option;
				 	$(data.data).each(function(i,v){
				 		var handicapArr = $("#handicap_input_id");
				 		if(handicapArr.val().indexOf(v.IBM_GAME_ID_)!=-1){
				 			option = "<input type='checkbox' name='gameIds' class='handicap_input_class' checked='checked' id='handicap_id+"+i+"' value='"+v.IBM_GAME_ID_+"'/><label for='handicap_id+"+i+"'>"+v.GAME_NAME_+"</label><br/>";
				 		}else{
				 			option = "<input type='checkbox' name='gameIds' class='handicap_input_class' id='handicap_id+"+i+"' value='"+v.IBM_GAME_ID_+"'/><label for='handicap_id+"+i+"'>"+v.GAME_NAME_+"</label><br/>";
				 		}
				 		var handicap_selects = $("#handicapModalBody");
				 		handicap_selects.append(option);
				 	});
				 },
				 error:function(){
				 	alert("失败");
				 }
			 });
	});
	
	function saveGame(){
		$("#handicap_input_id").val("");
		var games = $("input[name='gameIds']:checked");
		var arr = [];
		games.each(function(index,value){
			arr.push(value.value);
		});
		console.log(arr);
		$("#handicap_input_id").val(arr.join(","));
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
flag=ayCheckColumn("盘口名称","id_span$ibm_handicap$HANDICAP_NAME_","id_input$ibm_input$HANDICAP_NAME_","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("盘口CODE","id_span$ibm_handicap$HANDICAP_CODE_","id_input$ibm_input$HANDICAP_CODE_","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("盘口地址","id_span$ibm_handicap$HANDICAP_URL_","id_input$ibm_input$HANDICAP_URL_","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("校验码","id_span$ibm_handicap$HANDICAP_CAPTCHA_","id_input$ibm_input$HANDICAP_CAPTCHA_","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("盘口类型","id_span$ibm_handicap$HANDICAP_TYPE_","id_input$ibm_input$HANDICAP_TYPE_","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("盘口说明","id_span$ibm_handicap$HANDICAP_EXPLAIN_","id_input$ibm_input$HANDICAP_EXPLAIN_","VARCHAR","yes","255","0","0",a,false);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("盘口价值","id_span$ibm_handicap$HANDICAP_WORTH_T_","id_input$ibm_input$HANDICAP_WORTH_T_","INT","no","3","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("盘口版本","id_span$ibm_handicap$HANDICAP_VERSION_","id_input$ibm_input$HANDICAP_VERSION_","VARCHAR","no","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("排序","id_span$ibm_handicap$SN_","id_input$ibm_input$SN_","INT","no","4","0","0",a,false);
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