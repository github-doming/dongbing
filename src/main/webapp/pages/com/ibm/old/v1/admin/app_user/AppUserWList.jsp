<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>
<div style="display: none;" id="id_div_msg">正在提交中...</div>
<center>
	<div style="color:white;width:100%;height:100px;background-color:rgb(29,41,57);padding-top:20px" >
		<span style="font-size:30px;">用户管理</span>
	</div>
</center>
<form action="${pageContext.request.contextPath}/ibm/admin/app_user/list.do"  id="id_form_list" method="post">
<table id="topic_title" style="width:90%;table-layout:fixed; margin: 0px auto;"  id="id_table" class="table  table-bordered table-hover">
	<caption></caption>
	<thead>
	<tr>
		<th>
			<input class="btn" style="height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white" id="id_input_new" type="button" value="删除" onclick="delAllRecord()">
		</th>
		<th  colspan="5" style="text-align: right">
			<input value="${ibmRoleId}" name="ibm_role_id" type="hidden"/>
			<input id="seach_text" style="width:300px;height:20px" name="userName" id="id_input_new" placeholder="请输入用户名" type="text" value="${userName}"> 
			<input class="btn" style="height:32px;border-radius:10px;background-color:rgb(29,41,57);color:white"id="id_input_new" type="button" value="搜索" onclick="seachRecord()">
		</th>
	</tr>
		<tr>
<th style="width: 10%;" ><input id="id_checkbox_if_all" onclick=ayTableCheckAll(); type="checkbox" name="name_checkbox_if_all" />全选</th>
<th style="width: 10%;" >操作</th>
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ROLE_NAME_');"
				title="按[角色名]排序" style="cursor: pointer;">用户名</th>
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ROLE_CODE_');"
				title="按[角色CODE]排序" style="cursor: pointer;">用户CODE</th>
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ROLE_CODE_');"
				title="按[角色CODE]排序" style="cursor: pointer;">用户类型</th>
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ROLE_CODE_');"
				title="按[角色CODE]排序" style="cursor: pointer;">昵称</th>
		</tr>
	</thead>
	<tbody>
		<%
		java.util.ArrayList<java.util.HashMap<String, Object>> listMap = (java.util.ArrayList<java.util.HashMap<String, Object>>) request
				.getAttribute("list");
	for (java.util.HashMap<String, Object> info : listMap) {
	%>
<tr>
	<td><input value="<%=info.get("APP_USER_ID_")%>" type="checkbox"
		name="name_checkbox_ids"></td>
	<td><a onclick="delRecord('<%=info.get("APP_USER_ID_")%>')" href="#"> 删除
	<td style="word-wrap : break-word;  " id="hit.CMS_TOPIC_TITLE_">&nbsp;<%=info.get("APP_USER_NAME_")%></td>
	<td style="word-wrap : break-word;  " id="hit.CMS_TOPIC_TITLE_">&nbsp;<%=info.get("APP_USER_CODE_")%></td>
	<td style="word-wrap : break-word;  " id="hit.CMS_TOPIC_TITLE_">&nbsp;<input type="hidden"  class="user_type_input" value="<%=info.get("APP_USER_TYPE_")%>" />
		<select style="width: 100px;" class="user_type_select" onchange="changePower(this)">
		</select>
		<img onclick="updatePower(this,'<%=info.get("APP_USER_ID_")%>')" src="${pageContext.request.contextPath}/pages/com/ibm/image/confirm.png" style="width: 20px;height: 20px;display: none;" class="confirm_img" />
	</td>
	<td style="word-wrap : break-word;  " id="hit.CMS_TOPIC_TITLE_">&nbsp;<%=info.get("NICKNAME_")%></td>
</tr>
		<%
		}
	%>
	<tr>
		<th colspan="6">
			<%@ include file="/pages/com/cms/admin/page.jsp"%>
		</th>
	</tr>
	</tbody>
</table>
<br/>
</form>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/page/simple/page.js">
        </script>
<!-- 加载js -->
<script type="text/javascript">
function toPost(id){
	window.location.href="${pageContext.request.contextPath}/com/cms/cms_topic/wck/topic_post.do?cms_topic_id="+id;
}
/**
 *模糊查询记录
 */
function seachRecord(){
	$("#id_form_list").submit();
}

/**
 * 修改权限
 */
function changePower(user_type_select){
	$(user_type_select).next().css("display","inline-block");
}


/**
 * 加载角色名
 */
 $(document).ready(function(){
 	var types = $(".user_type_input");
 	var type_selects = $(".user_type_select");
 	var i=0;
 	for (;i<types.length;i++) {
 		var type = $(types[i]);
 		var type_select = $(type_selects[i]);
	 	loadAjax(type,type_select);
 	}
});

function loadAjax(type,type_select){
	$.ajax({
		 url:"<%=request.getContextPath()%>/com/ibm/admin/ibm_role/w/listPower.do",
		 dataType:"json",
		 type:"post",
		 data:{userType:type.val()},
		 success:function(data){
		 	 var option;
		 	 $(data.data).each(function(index,value){
		 		if(value.ROLE_CODE_== type.val()){
			 		option = "<option value="+value.ROLE_CODE_+" selected='selected'>"+value.ROLE_CODE_+"</option>";
			 	}else{
			 		option = "<option value="+value.ROLE_CODE_+">"+value.ROLE_CODE_+"</option>";
			 	}
				type_select.append(option);
		 	 });
		 },
		 error:function(){
		 	alert("失败");
		 }
	 });
}

/**
 * 
 删除所有记录
 */
function delAllRecord(id){
	var url = "${pageContext.request.contextPath}/ibm/admin/app_user/del_all.do";
	var objs = document.getElementsByName("name_checkbox_ids");
	var checkedNumber = 0;
	for (var i = 0; i < objs.length; i++) {
		if (objs[i].checked) {
			checkedNumber = checkedNumber + 1;
		}
	}
	if (checkedNumber == 0) {
	alert('请先选择要删除的行');
	}else{
		if(confirm("确定要删除吗？"))
		{
			document.getElementById("id_form_list").action=url;
			document.getElementById("id_form_list").submit();
		}
		else
		{
		}
	}
	return false;
}

/**
 * 
 删除记录
 */
function delRecord(id){
	if(confirm("确定要删除吗？"))
	{
		var url = "${pageContext.request.contextPath}/ibm/admin/app_user/del.do?id="+id;
		window.location.href= url;
	}
}
/**
 * 
 请求更新
 */
function updatePower(img,userId){
	var code = $(img).prev().val();
	console.log(userId);
	console.log(code);
	$.ajax({
		 url:"<%=request.getContextPath()%>/ibm/admin/app_user/modify_user_type.do",
		 dataType:"json",
		 type:"post",
		 data:{roleCode:code,userId:userId},
		 success:function(data){
 		 	location.reload();
		 	 alert("修改成功");
		 },
		 error:function(){
		 	alert("失败");
		 }
	 });
}
	</script>
</html>