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
	action="${pageContext.request.contextPath}/all/sys_admin/sys/sys_dict_tree/t/save/bootstrap.do"
	method="post">
<table class="table  table-bordered table-hover" border="0">
	<tr>
		<th colspan="3">数据字典 <c:choose>
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
		<p class="text-right">上一级数据字典id：</p>
		</td>
		<td><input id="id_input_parent_menu_id" type="text"
			name="sys_dict_tree.parent" value="${requestScope.p.sysDictTreeId}"></td>
		<td></td>
	</tr>
	<tr>
		<td>
		<p class="text-right">上一级数据字典名称：</p>
		</td>
		<td><input id="id_input_parent_menu_name"
			class="input-medium search-query" readonly="readonly" type="text"
			name="" value="${requestScope.p.sysDictTreeName}"> <input
			onclick="parent('${requestScope.s.path}');" class="btn" type="button"
			value="选择"></td>
		<td width="30%"></td>
	</tr>
	
	

<tr style="display: none;">
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . sysDictTreeId%></td>
		<td><input id="idInput_SysDictTreeT_sysDictTreeId"  type="text" name="sys_dict_tree.sysDictTreeId"
			value="${requestScope.s.sysDictTreeId}"></td>
		<td><span id="idSpan_SysDictTreeT_sysDictTreeId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . sysDictTreeName%></td>
		<td><input id="idInput_SysDictTreeT_sysDictTreeName"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . sysDictTreeName%>" type="text" name="sys_dict_tree.sysDictTreeName"
			value="${requestScope.s.sysDictTreeName}">


		</td>
		<td><span id="idSpan_SysDictTreeT_sysDictTreeName"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . sysDictTreeCode%></td>
		<td><input id="idInput_SysDictTreeT_sysDictTreeCode"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . sysDictTreeCode%>" type="text" name="sys_dict_tree.sysDictTreeCode"
			value="${requestScope.s.sysDictTreeCode}">


		</td>
		<td><span id="idSpan_SysDictTreeT_sysDictTreeCode"></span>
		</td>
</tr>	

<!-- 

<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . idx%></td>
		<td><input id="idInput_SysDictTreeT_idx"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . idx%>" type="text" name="sys_dict_tree.idx"
			value="${requestScope.s.idx}">


		</td>
		<td><span id="idSpan_SysDictTreeT_idx"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . sn%></td>
		<td><input id="idInput_SysDictTreeT_sn"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . sn%>" type="text" name="sys_dict_tree.sn"
			value="${requestScope.s.sn}">


		</td>
		<td><span id="idSpan_SysDictTreeT_sn"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . parent%></td>
		<td><input id="idInput_SysDictTreeT_parent"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . parent%>" type="text" name="sys_dict_tree.parent"
			value="${requestScope.s.parent}">


		</td>
		<td><span id="idSpan_SysDictTreeT_parent"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . path%></td>
		<td><input id="idInput_SysDictTreeT_path"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . path%>" type="text" name="sys_dict_tree.path"
			value="${requestScope.s.path}">


		</td>
		<td><span id="idSpan_SysDictTreeT_path"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . treeCode%></td>
		<td><input id="idInput_SysDictTreeT_treeCode"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . treeCode%>" type="text" name="sys_dict_tree.treeCode"
			value="${requestScope.s.treeCode}">


		</td>
		<td><span id="idSpan_SysDictTreeT_treeCode"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . createUser%></td>
		<td><input id="idInput_SysDictTreeT_createUser"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . createUser%>" type="text" name="sys_dict_tree.createUser"
			value="${requestScope.s.createUser}">


		</td>
		<td><span id="idSpan_SysDictTreeT_createUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . createTime%></td>
		<td><input id="idInput_SysDictTreeT_createTime"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . createTime%>" type="text" name="sys_dict_tree.createTime"
			value="${requestScope.s.createTime}">



<img
onclick=ayCalendarShow('idInput_SysDictTreeT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_SysDictTreeT_createTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . createTimeLong%></td>
		<td><input id="idInput_SysDictTreeT_createTimeLong"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . createTimeLong%>" type="text" name="sys_dict_tree.createTimeLong"
			value="${requestScope.s.createTimeLong}">


		</td>
		<td><span id="idSpan_SysDictTreeT_createTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . updateUser%></td>
		<td><input id="idInput_SysDictTreeT_updateUser"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . updateUser%>" type="text" name="sys_dict_tree.updateUser"
			value="${requestScope.s.updateUser}">


		</td>
		<td><span id="idSpan_SysDictTreeT_updateUser"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . updateTime%></td>
		<td><input id="idInput_SysDictTreeT_updateTime"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . updateTime%>" type="text" name="sys_dict_tree.updateTime"
			value="${requestScope.s.updateTime}">



<img
onclick=ayCalendarShow('idInput_SysDictTreeT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_SysDictTreeT_updateTime"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . updateTimeLong%></td>
		<td><input id="idInput_SysDictTreeT_updateTimeLong"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . updateTimeLong%>" type="text" name="sys_dict_tree.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">


		</td>
		<td><span id="idSpan_SysDictTreeT_updateTimeLong"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . state%></td>
		<td><input id="idInput_SysDictTreeT_state"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . state%>" type="text" name="sys_dict_tree.state"
			value="${requestScope.s.state}">


		</td>
		<td><span id="idSpan_SysDictTreeT_state"></span>
		</td>
</tr>	

-->

<tr>
		<td align="right"><%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . desc%></td>
		<td><input id="idInput_SysDictTreeT_desc"  placeholder="请输入<%=all.sys_admin.sys.sys_dict_tree.t.alias.SysDictTreeTAlias . desc%>" type="text" name="sys_dict_tree.desc"
			value="${requestScope.s.desc}">


		</td>
		<td><span id="idSpan_SysDictTreeT_desc"></span>
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
	var url = "${pageContext.request.contextPath}/all/sys_admin/sys/sys_dict_tree/t/parent/bootstrap.do";
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
	var url = "${pageContext.request.contextPath}/all/sys_admin/sys/sys_dict_tree/t/parent/bootstrap.do";
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
 	var url = "${pageContext.request.contextPath}/all/sys_admin/sys/sys_dict_tree/t/list/bootstrap.do";
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
	
	flag=ayCheckColumn("后台数据字典分类表名称SYS_DICT_TREE_NAME_","idSpan_SysDictTreeT_sysDictTreeName","idInput_SysDictTreeT_sysDictTreeName","VARCHAR","no","32","0","0",a,true);
	if(flag){}else{return_flag=false;}
/**	
flag=ayCheckColumn("索引","idSpan_SysDictTreeT_idx","idInput_SysDictTreeT_idx","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}

flag=ayCheckColumn("次序","idSpan_SysDictTreeT_sn","idInput_SysDictTreeT_sn","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树上一级","idSpan_SysDictTreeT_parent","idInput_SysDictTreeT_parent","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树PATH_","idSpan_SysDictTreeT_path","idInput_SysDictTreeT_path","VARCHAR","yes","1024","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("树编码","idSpan_SysDictTreeT_treeCode","idInput_SysDictTreeT_treeCode","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建者CREATE_USER_","idSpan_SysDictTreeT_createUser","idInput_SysDictTreeT_createUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_SysDictTreeT_createTime","idInput_SysDictTreeT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_SysDictTreeT_createTimeLong","idInput_SysDictTreeT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","idSpan_SysDictTreeT_updateUser","idInput_SysDictTreeT_updateUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_SysDictTreeT_updateTime","idInput_SysDictTreeT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_SysDictTreeT_updateTimeLong","idInput_SysDictTreeT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_SysDictTreeT_state","idInput_SysDictTreeT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_SysDictTreeT_desc","idInput_SysDictTreeT_desc","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
	**/
	
	
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
