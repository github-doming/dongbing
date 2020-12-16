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
<form id="id_form_save"
	action="${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/pss/gen2/pss_productgroup_info/save.do"
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
			name="pss_productgroup_info.parent" value="${requestScope.p.id}"></td>
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
<tr style="display: none;">
		<td align="right"><%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . id%></td>
		<td><input id="id_input$pss_productgroup_info$id"  type="text" name="pss_productgroup_info.id"
			value="${requestScope.id}"></td>
		<td><span id="id_span$pss_productgroup_info$id"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . name%></td>
		<td><input id="id_input$pss_productgroup_info$name"  placeholder="请输入<%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . name%>" type="text" name="pss_productgroup_info.name"
			value="${requestScope.s.name}">
		</td>
		<td><span id="id_span$pss_productgroup_info$name"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . path%></td>
		<td><input id="id_input$pss_productgroup_info$path"  placeholder="请输入<%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . path%>" type="text" name="pss_productgroup_info.path"
			value="${requestScope.s.path}">
		</td>
		<td><span id="id_span$pss_productgroup_info$path"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . tree_code%></td>
		<td><input id="id_input$pss_productgroup_info$tree_code"  placeholder="请输入<%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . tree_code%>" type="text" name="pss_productgroup_info.tree_code"
			value="${requestScope.s.tree_code}">
		</td>
		<td><span id="id_span$pss_productgroup_info$tree_code"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . parent%></td>
		<td><input id="id_input$pss_productgroup_info$parent"  placeholder="请输入<%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . parent%>" type="text" name="pss_productgroup_info.parent"
			value="${requestScope.s.parent}">
		</td>
		<td><span id="id_span$pss_productgroup_info$parent"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . sequence%></td>
		<td><input id="id_input$pss_productgroup_info$sequence"  placeholder="请输入<%=c.x.platform.feng.feng.pss.gen2.pss_productgroup_info.alias.Gen2PssProductgroupInfoAlias . sequence%>" type="text" name="pss_productgroup_info.sequence"
			value="${requestScope.s.sequence}">
		</td>
		<td><span id="id_span$pss_productgroup_info$sequence"></span>
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
	var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/pss/gen2/pss_productgroup_info/parent.do";
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
	var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/pss/gen2/pss_productgroup_info/parent.do";
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
 	var url = "${pageContext.request.contextPath}/pages/c/x/platform/admin/feng/pss/gen2/pss_productgroup_info/list.do";
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
flag=ayCheckColumn("商品分类名称","id_span$pss_productgroup_info$name","id_input$pss_productgroup_info$name","VARCHAR","no","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("path","id_span$pss_productgroup_info$path","id_input$pss_productgroup_info$path","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("tree_code","id_span$pss_productgroup_info$tree_code","id_input$pss_productgroup_info$tree_code","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("parent","id_span$pss_productgroup_info$parent","id_input$pss_productgroup_info$parent","VARCHAR","no","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("排序","id_span$pss_productgroup_info$sequence","id_input$pss_productgroup_info$sequence","INT","yes","10","0","0",a,true);
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
