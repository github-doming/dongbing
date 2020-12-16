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
	action="${pageContext.request.contextPath}/c/x/platform/admin/gen/fun_single_simple/gen_tree_simple/save.do"
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
			name="fun_single_simple.parent" value="${requestScope.p.funSingleSimpleId}"></td>
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
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . funSingleSimpleId%></td>
		<td><input id="id_input$fun_single_simple$FUN_SINGLE_SIMPLE_ID_"  type="text" name="fun_single_simple.funSingleSimpleId"
			value="${requestScope.s.funSingleSimpleId}"></td>
		<td><span id="id_span$fun_single_simple$FUN_SINGLE_SIMPLE_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . id%></td>
		<td><input id="id_input$fun_single_simple$ID_"  placeholder="请输入<%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . id%>" type="text" name="fun_single_simple.id"
			value="${requestScope.s.id}">
		</td>
		<td><span id="id_span$fun_single_simple$ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . name%></td>
		<td><input id="id_input$fun_single_simple$NAME_"  placeholder="请输入<%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . name%>" type="text" name="fun_single_simple.name"
			value="${requestScope.s.name}">
		</td>
		<td><span id="id_span$fun_single_simple$NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . path%></td>
		<td><input id="id_input$fun_single_simple$PATH_"  placeholder="请输入<%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . path%>" type="text" name="fun_single_simple.path"
			value="${requestScope.s.path}">
		</td>
		<td><span id="id_span$fun_single_simple$PATH_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . treeCode%></td>
		<td><input id="id_input$fun_single_simple$TREE_CODE_"  placeholder="请输入<%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . treeCode%>" type="text" name="fun_single_simple.treeCode"
			value="${requestScope.s.treeCode}">
		</td>
		<td><span id="id_span$fun_single_simple$TREE_CODE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . url%></td>
		<td><input id="id_input$fun_single_simple$URL_"  placeholder="请输入<%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . url%>" type="text" name="fun_single_simple.url"
			value="${requestScope.s.url}">
		</td>
		<td><span id="id_span$fun_single_simple$URL_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . parent%></td>
		<td><input id="id_input$fun_single_simple$PARENT_"  placeholder="请输入<%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . parent%>" type="text" name="fun_single_simple.parent"
			value="${requestScope.s.parent}">
		</td>
		<td><span id="id_span$fun_single_simple$PARENT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . picOpen%></td>
		<td><input id="id_input$fun_single_simple$PIC_OPEN_"  placeholder="请输入<%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . picOpen%>" type="text" name="fun_single_simple.picOpen"
			value="${requestScope.s.picOpen}">
		</td>
		<td><span id="id_span$fun_single_simple$PIC_OPEN_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . picClose%></td>
		<td><input id="id_input$fun_single_simple$PIC_CLOSE_"  placeholder="请输入<%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . picClose%>" type="text" name="fun_single_simple.picClose"
			value="${requestScope.s.picClose}">
		</td>
		<td><span id="id_span$fun_single_simple$PIC_CLOSE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . pic%></td>
		<td><input id="id_input$fun_single_simple$PIC_"  placeholder="请输入<%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . pic%>" type="text" name="fun_single_simple.pic"
			value="${requestScope.s.pic}">
		</td>
		<td><span id="id_span$fun_single_simple$PIC_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . sn%></td>
		<td><input id="id_input$fun_single_simple$SN_"  placeholder="请输入<%=c.x.platform.admin.gen.fun_single_simple.gen_tree_simple.alias.FunSingleSimpleGenTreeSimpleAlias . sn%>" type="text" name="fun_single_simple.sn"
			value="${requestScope.s.sn}">
		</td>
		<td><span id="id_span$fun_single_simple$SN_"></span>
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
	var url = "${pageContext.request.contextPath}/c/x/platform/admin/gen/fun_single_simple/gen_tree_simple/parent.do";
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
	var url = "${pageContext.request.contextPath}/c/x/platform/admin/gen/fun_single_simple/gen_tree_simple/parent.do";
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
 	var url = "${pageContext.request.contextPath}/c/x/platform/admin/gen/fun_single_simple/gen_tree_simple/list.do";
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
flag=ayCheckColumn("主键","id_span$fun_single_simple$ID_","id_input$fun_single_simple$ID_","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("名称","id_span$fun_single_simple$NAME_","id_input$fun_single_simple$NAME_","VARCHAR","no","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("PATH_","id_span$fun_single_simple$PATH_","id_input$fun_single_simple$PATH_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树编码","id_span$fun_single_simple$TREE_CODE_","id_input$fun_single_simple$TREE_CODE_","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("URL_","id_span$fun_single_simple$URL_","id_input$fun_single_simple$URL_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("上一级","id_span$fun_single_simple$PARENT_","id_input$fun_single_simple$PARENT_","VARCHAR","yes","128","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("PIC_OPEN_","id_span$fun_single_simple$PIC_OPEN_","id_input$fun_single_simple$PIC_OPEN_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("PIC_CLOSE_","id_span$fun_single_simple$PIC_CLOSE_","id_input$fun_single_simple$PIC_CLOSE_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("PIC_","id_span$fun_single_simple$PIC_","id_input$fun_single_simple$PIC_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("排序","id_span$fun_single_simple$SN_","id_input$fun_single_simple$SN_","INT","yes","10","0","0",a,true);
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
