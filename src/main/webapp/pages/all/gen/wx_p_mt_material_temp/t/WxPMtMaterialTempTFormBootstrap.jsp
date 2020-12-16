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
	action="${pageContext.request.contextPath}/all/gen/wx_p_mt_material_temp/t/save/bootstrap.do"
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
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . idx%></td>
		<td><input id="id_input$wx_p_mt_material_temp$IDX_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . idx%>"  type="text" name="wx_p_mt_material_temp.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="id_span$wx_p_mt_material_temp$IDX_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . wxPMtMaterialTempId%></td>
		<td><input id="id_input$wx_p_mt_material_temp$WX_P_MT_MATERIAL_TEMP_ID_" type="text" name="wx_p_mt_material_temp.wxPMtMaterialTempId"
			value="${requestScope.s.wxPMtMaterialTempId}"></td>
		<td><span id="id_span$wx_p_mt_material_temp$WX_P_MT_MATERIAL_TEMP_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . name%></td>
		<td><input id="id_input$wx_p_mt_material_temp$NAME_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . name%>"  type="text" name="wx_p_mt_material_temp.name"
			value="${requestScope.s.name}">


		</td>
		<td><span id="id_span$wx_p_mt_material_temp$NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . type%></td>
		<td><input id="id_input$wx_p_mt_material_temp$TYPE_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . type%>"  type="text" name="wx_p_mt_material_temp.type"
			value="${requestScope.s.type}">


		</td>
		<td><span id="id_span$wx_p_mt_material_temp$TYPE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . mediaId%></td>
		<td><input id="id_input$wx_p_mt_material_temp$MEDIA_ID_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . mediaId%>"  type="text" name="wx_p_mt_material_temp.mediaId"
			value="${requestScope.s.mediaId}">


		</td>
		<td><span id="id_span$wx_p_mt_material_temp$MEDIA_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . createdAt%></td>
		<td><input id="id_input$wx_p_mt_material_temp$CREATED_AT_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . createdAt%>"  type="text" name="wx_p_mt_material_temp.createdAt"
			value="${requestScope.s.createdAt}">


		</td>
		<td><span id="id_span$wx_p_mt_material_temp$CREATED_AT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . materialId%></td>
		<td><input id="id_input$wx_p_mt_material_temp$MATERIAL_ID_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . materialId%>"  type="text" name="wx_p_mt_material_temp.materialId"
			value="${requestScope.s.materialId}">


		</td>
		<td><span id="id_span$wx_p_mt_material_temp$MATERIAL_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . createTime%></td>
		<td><input id="id_input$wx_p_mt_material_temp$CREATE_TIME_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . createTime%>"  type="text" name="wx_p_mt_material_temp.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_mt_material_temp$CREATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_mt_material_temp$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . createTimeLong%></td>
		<td><input id="id_input$wx_p_mt_material_temp$CREATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . createTimeLong%>"  type="text" name="wx_p_mt_material_temp.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$wx_p_mt_material_temp$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . updateTimeLong%></td>
		<td><input id="id_input$wx_p_mt_material_temp$UPDATE_TIME_LONG_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . updateTimeLong%>"  type="text" name="wx_p_mt_material_temp.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$wx_p_mt_material_temp$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . updateTime%></td>
		<td><input id="id_input$wx_p_mt_material_temp$UPDATE_TIME_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . updateTime%>"  type="text" name="wx_p_mt_material_temp.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$wx_p_mt_material_temp$UPDATE_TIME_',event,'DATETIME'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$wx_p_mt_material_temp$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . state%></td>
		<td><input id="id_input$wx_p_mt_material_temp$STATE_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . state%>"  type="text" name="wx_p_mt_material_temp.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$wx_p_mt_material_temp$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . desc%></td>
		<td><input id="id_input$wx_p_mt_material_temp$DESC_" placeholder="请输入<%=all.gen.wx_p_mt_material_temp.t.alias.WxPMtMaterialTempTAlias . desc%>"  type="text" name="wx_p_mt_material_temp.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$wx_p_mt_material_temp$DESC_"></span>
		</td>
</tr>	
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
var url = "${pageContext.request.contextPath}/all/gen/wx_p_mt_material_temp/t/list/bootstrap.do";
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
flag=ayCheckColumn("索引","id_span$wx_p_mt_material_temp$IDX_","id_input$wx_p_mt_material_temp$IDX_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("名称","id_span$wx_p_mt_material_temp$NAME_","id_input$wx_p_mt_material_temp$NAME_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("???体文件类型","id_span$wx_p_mt_material_temp$TYPE_","id_input$wx_p_mt_material_temp$TYPE_","VARCHAR","yes","128","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("媒体id","id_span$wx_p_mt_material_temp$MEDIA_ID_","id_input$wx_p_mt_material_temp$MEDIA_ID_","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("微信创建时间","id_span$wx_p_mt_material_temp$CREATED_AT_","id_input$wx_p_mt_material_temp$CREATED_AT_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("文件id","id_span$wx_p_mt_material_temp$MATERIAL_ID_","id_input$wx_p_mt_material_temp$MATERIAL_ID_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间 CREATE_TIME_","id_span$wx_p_mt_material_temp$CREATE_TIME_","id_input$wx_p_mt_material_temp$CREATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间数字型 CREATE_TIME_LONG_","id_span$wx_p_mt_material_temp$CREATE_TIME_LONG_","id_input$wx_p_mt_material_temp$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_mt_material_temp$UPDATE_TIME_LONG_","id_input$wx_p_mt_material_temp$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","id_span$wx_p_mt_material_temp$UPDATE_TIME_","id_input$wx_p_mt_material_temp$UPDATE_TIME_","DATETIME","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$wx_p_mt_material_temp$STATE_","id_input$wx_p_mt_material_temp$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$wx_p_mt_material_temp$DESC_","id_input$wx_p_mt_material_temp$DESC_","VARCHAR","yes","512","0","0",a,true);
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