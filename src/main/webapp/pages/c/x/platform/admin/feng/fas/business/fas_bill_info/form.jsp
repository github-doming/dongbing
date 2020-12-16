<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/kaida/common.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>

<form id="id_form_save"
	action="${pageContext.request.contextPath}/admin/business/fas_bill_info/save.do"
	method="post">
	
	
	
	
	
	
	
	
	
	<!-- 第1个table  -->



<table width="100%" border="0" cellpadding="0" cellspacing="1"
	bgcolor="#7A9DB8">
	<tr bgcolor="#FFFFFF">
		<td height="25" valign="top">&nbsp;&nbsp;<img
			src="${pageContext.request.contextPath}/pages/c/x/imports/feng/kaida/crud/form/images/current_directory.jpg"
			align="middle">&nbsp;<span class="class_font_size_12px">当前位置：菜单管理 &gt;
			
			菜单 <c:choose>
			<c:when test="${requestScope.id==null}">添加</c:when>
			<c:otherwise>修改</c:otherwise>
		</c:choose>
		
		</span></td>
	</tr>
	
	<tr bgcolor="#FFFFFF">
		<td height="25" valign="top">&nbsp;&nbsp;&nbsp;<span class="font_5">
			
			
			<input onclick="back();" class="btn"
			type="button" value="返回列表"></input> <c:choose>
			<c:when test="${requestScope.id==null}">
				<input onclick='save();' class="btn" type="button" value="新增">
			</c:when>
			<c:otherwise>
				<input onclick='save();' class="btn" type="button" value="编辑">
			</c:otherwise>
		</c:choose>
			
		</span></td>
	</tr>
	
	
	<tr bgcolor="#FFFFFF">
		<td align="center" style="padding-right: 25px; padding-bottom: 50px;padding-left: 25px; padding-top: 50px;">
		<!-- 第2个table  -->
	
		
		
		<table width="99%" border="0" cellpadding="0" cellspacing="1"
			bgcolor="#CCCCCC">



<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . id%>:</span></td>
		<td><input id="id_input$fas_bill_info$id" type="text" name="fas_bill_info.id"
			value="${requestScope.id}"></td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$id"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_name%>:</span></td>
		<td><input id="id_input$fas_bill_info$business_name" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_name%>"  type="text" name="fas_bill_info.business_name"
			value="${requestScope.s.business_name}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$business_name"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_id%>:</span></td>
		<td><input id="id_input$fas_bill_info$business_id" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_id%>"  type="text" name="fas_bill_info.business_id"
			value="${requestScope.s.business_id}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$business_id"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_begin%>:</span></td>
		<td><input id="id_input$fas_bill_info$account_begin" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_begin%>"  type="text" name="fas_bill_info.account_begin"
			value="${requestScope.s.account_begin}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$account_begin"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_begin_id%>:</span></td>
		<td><input id="id_input$fas_bill_info$account_begin_id" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_begin_id%>"  type="text" name="fas_bill_info.account_begin_id"
			value="${requestScope.s.account_begin_id}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$account_begin_id"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_amount%>:</span></td>
		<td><input id="id_input$fas_bill_info$account_amount" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_amount%>"  type="text" name="fas_bill_info.account_amount"
			value="${requestScope.s.account_amount}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$account_amount"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_balance%>:</span></td>
		<td><input id="id_input$fas_bill_info$account_balance" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . account_balance%>"  type="text" name="fas_bill_info.account_balance"
			value="${requestScope.s.account_balance}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$account_balance"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . create_time_dt%>:</span></td>
		<td><input id="id_input$fas_bill_info$create_time_dt" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . create_time_dt%>"  type="text" name="fas_bill_info.create_time_dt"
			value="${requestScope.s.create_time_dt}">
<img
onclick=ayCalendarShow('id_input$fas_bill_info$create_time_dt',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$create_time_dt"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . create_time%>:</span></td>
		<td><input id="id_input$fas_bill_info$create_time" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . create_time%>"  type="text" name="fas_bill_info.create_time"
			value="${requestScope.s.create_time}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$create_time"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_1%>:</span></td>
		<td><input id="id_input$fas_bill_info$value_1" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_1%>"  type="text" name="fas_bill_info.value_1"
			value="${requestScope.s.value_1}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$value_1"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_2%>:</span></td>
		<td><input id="id_input$fas_bill_info$value_2" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_2%>"  type="text" name="fas_bill_info.value_2"
			value="${requestScope.s.value_2}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$value_2"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_3%>:</span></td>
		<td><input id="id_input$fas_bill_info$value_3" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_3%>"  type="text" name="fas_bill_info.value_3"
			value="${requestScope.s.value_3}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$value_3"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_5%>:</span></td>
		<td><input id="id_input$fas_bill_info$value_5" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . value_5%>"  type="text" name="fas_bill_info.value_5"
			value="${requestScope.s.value_5}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$value_5"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_from%>:</span></td>
		<td><input id="id_input$fas_bill_info$business_from" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_from%>"  type="text" name="fas_bill_info.business_from"
			value="${requestScope.s.business_from}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$business_from"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_to%>:</span></td>
		<td><input id="id_input$fas_bill_info$business_to" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . business_to%>"  type="text" name="fas_bill_info.business_to"
			value="${requestScope.s.business_to}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$business_to"></span>
		</td>
</tr>	
<tr bgcolor="#FFFFFF">
		<td align="right"><span class="class_font_size_12px"><%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . row%>:</span></td>
		<td><input id="id_input$fas_bill_info$row" placeholder="请输入<%=c.x.platform.feng.feng.fas.business.family_account_bill_info.alias.FamilyAccountBillInfoAlias . row%>"  type="text" name="fas_bill_info.row"
			value="${requestScope.s.row}">
		</td>
		<td style="width:25%;"><span id="id_span$fas_bill_info$row"></span>
		</td>
</tr>	
	<tr bgcolor="#FFFFFF">
		<td align="center" colspan="3"></td>
	</tr>
	
	

		</table>



		<!-- 第2个table  --></td>
	</tr>
</table>







<!-- 第1个table  -->







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
var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/fas/business/fas_bill_info/list.do";
window.location.href=url;
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
flag=ayCheckColumn("business_name","id_span$fas_bill_info$business_name","id_input$fas_bill_info$business_name","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("业务分类ID","id_span$fas_bill_info$business_id","id_input$fas_bill_info$business_id","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("account_begin","id_span$fas_bill_info$account_begin","id_input$fas_bill_info$account_begin","DECIMAL","yes","18","2","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("account_begin_id","id_span$fas_bill_info$account_begin_id","id_input$fas_bill_info$account_begin_id","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("增减金额","id_span$fas_bill_info$account_amount","id_input$fas_bill_info$account_amount","DECIMAL","yes","18","2","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("结余","id_span$fas_bill_info$account_balance","id_input$fas_bill_info$account_balance","DECIMAL","yes","18","2","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("create_time_dt","id_span$fas_bill_info$create_time_dt","id_input$fas_bill_info$create_time_dt","TIMESTAMP","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("create_time","id_span$fas_bill_info$create_time","id_input$fas_bill_info$create_time","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("收入或支出","id_span$fas_bill_info$value_1","id_input$fas_bill_info$value_1","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("状态(中文)","id_span$fas_bill_info$value_2","id_input$fas_bill_info$value_2","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("value_3","id_span$fas_bill_info$value_3","id_input$fas_bill_info$value_3","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("value_5","id_span$fas_bill_info$value_5","id_input$fas_bill_info$value_5","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("business_from","id_span$fas_bill_info$business_from","id_input$fas_bill_info$business_from","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("business_to","id_span$fas_bill_info$business_to","id_input$fas_bill_info$business_to","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("row","id_span$fas_bill_info$row","id_input$fas_bill_info$row","BIGINT","yes","19","0","0",a,true);
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
ayCalendarNow('id_input$fas_bill_info$create_time_dt','TIMESTAMP');
</script>