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
	action="${pageContext.request.contextPath}/all/gen/sys_area/t/save/bootstrap.do"
	method="post">
<table class="table  table-bordered table-hover" border="0">
	<tr>
		<th colspan="3">菜单 <c:choose>
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
		<p class="text-right">上一级菜单id：</p>
		</td>
		<td><input id="id_input_parent_menu_id" type="text"
			name="sys_area.parent" value="${requestScope.p.sysAreaId}"></td>
		<td></td>
	</tr>
	<tr>
		<td>
		<p class="text-right">上一级菜单名称：</p>
		</td>
		<td><input id="id_input_parent_menu_name"
			class="input-medium search-query" readonly="readonly" type="text"
			name="" value="${requestScope.p.name}"> <input
			onclick="parent('${requestScope.s.path}');" class="btn" type="button"
			value="选择"></td>
		<td width="30%"></td>
	</tr>
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . id%></td>
		<td><input id="id_input$sys_area$ID_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . id%>" type="text" name="sys_area.id"
			value="${requestScope.s.id}">


		</td>
		<td><span id="id_span$sys_area$ID_"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . sysAreaId%></td>
		<td><input id="id_input$sys_area$SYS_AREA_ID_"  type="text" name="sys_area.sysAreaId"
			value="${requestScope.s.sysAreaId}"></td>
		<td><span id="id_span$sys_area$SYS_AREA_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . parent%></td>
		<td><input id="id_input$sys_area$PARENT_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . parent%>" type="text" name="sys_area.parent"
			value="${requestScope.s.parent}">


		</td>
		<td><span id="id_span$sys_area$PARENT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . name%></td>
		<td><input id="id_input$sys_area$NAME_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . name%>" type="text" name="sys_area.name"
			value="${requestScope.s.name}">


		</td>
		<td><span id="id_span$sys_area$NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . path%></td>
		<td><input id="id_input$sys_area$PATH_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . path%>" type="text" name="sys_area.path"
			value="${requestScope.s.path}">


		</td>
		<td><span id="id_span$sys_area$PATH_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . treeCode%></td>
		<td><input id="id_input$sys_area$TREE_CODE_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . treeCode%>" type="text" name="sys_area.treeCode"
			value="${requestScope.s.treeCode}">


		</td>
		<td><span id="id_span$sys_area$TREE_CODE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . sn%></td>
		<td><input id="id_input$sys_area$SN_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . sn%>" type="text" name="sys_area.sn"
			value="${requestScope.s.sn}">


		</td>
		<td><span id="id_span$sys_area$SN_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . state%></td>
		<td><input id="id_input$sys_area$STATE_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . state%>" type="text" name="sys_area.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="id_span$sys_area$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . code%></td>
		<td><input id="id_input$sys_area$CODE_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . code%>" type="text" name="sys_area.code"
			value="${requestScope.s.code}">


		</td>
		<td><span id="id_span$sys_area$CODE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . desc%></td>
		<td><input id="id_input$sys_area$DESC_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . desc%>" type="text" name="sys_area.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="id_span$sys_area$DESC_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . createTime%></td>
		<td><input id="id_input$sys_area$CREATE_TIME_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . createTime%>" type="text" name="sys_area.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('id_input$sys_area$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$sys_area$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . createTimeLong%></td>
		<td><input id="id_input$sys_area$CREATE_TIME_LONG_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . createTimeLong%>" type="text" name="sys_area.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="id_span$sys_area$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . updateTime%></td>
		<td><input id="id_input$sys_area$UPDATE_TIME_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . updateTime%>" type="text" name="sys_area.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('id_input$sys_area$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$sys_area$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.gen.sys_area.t.alias.SysAreaTAlias . updateTimeLong%></td>
		<td><input id="id_input$sys_area$UPDATE_TIME_LONG_"  placeholder="请输入<%=all.gen.sys_area.t.alias.SysAreaTAlias . updateTimeLong%>" type="text" name="sys_area.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="id_span$sys_area$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
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
	var url = "${pageContext.request.contextPath}/all/gen/sys_area/t/parent/bootstrap.do";
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
	var url = "${pageContext.request.contextPath}/all/gen/sys_area/t/parent/bootstrap.do";
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
 	var url = "${pageContext.request.contextPath}/all/gen/sys_area/t/list/bootstrap.do";
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
flag=ayCheckColumn("主键","id_span$sys_area$ID_","id_input$sys_area$ID_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("上一级","id_span$sys_area$PARENT_","id_input$sys_area$PARENT_","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("名称","id_span$sys_area$NAME_","id_input$sys_area$NAME_","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("PATH_","id_span$sys_area$PATH_","id_input$sys_area$PATH_","VARCHAR","yes","1024","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树编码","id_span$sys_area$TREE_CODE_","id_input$sys_area$TREE_CODE_","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","id_span$sys_area$SN_","id_input$sys_area$SN_","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("是否显示","id_span$sys_area$STATE_","id_input$sys_area$STATE_","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("行政编码","id_span$sys_area$CODE_","id_input$sys_area$CODE_","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$sys_area$DESC_","id_input$sys_area$DESC_","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CURRENT_TIMESTAMP(3)","id_span$sys_area$CREATE_TIME_","id_input$sys_area$CREATE_TIME_","TIMESTAMP","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("CREATE_TIME_LONG_","id_span$sys_area$CREATE_TIME_LONG_","id_input$sys_area$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("UPDATE_TIME_","id_span$sys_area$UPDATE_TIME_","id_input$sys_area$UPDATE_TIME_","TIMESTAMP","yes","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("UPDATE_TIME_LONG_","id_span$sys_area$UPDATE_TIME_LONG_","id_input$sys_area$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
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
