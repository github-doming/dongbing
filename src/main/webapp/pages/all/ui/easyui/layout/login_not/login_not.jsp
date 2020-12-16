<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="zh-CN">
<head>
<title>技术后台</title>
<%@ include file="/pages/all/ui/easyui/common/common.jsp"%>
</head>
<body>
	<div id="loginWin" class="easyui-window" title="登录"
		style="width: 350px; height: 188px; padding: 5px;" minimizable="false"
		maximizable="false" resizable="false" collapsible="false">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding: 5px; background: #fff; border: 1px solid #ccc;">
				<form id="loginForm" method="post">
					<div style="padding: 5px 0;">
						<label for="login">帐号:</label> <input type="text" name="j_username"
							style="width: 260px;"></input>
					</div>
					<div style="padding: 5px 0;">
						<label for="password">密码:</label> <input type="password"
							name="j_password" style="width: 260px;"></input>
					</div>
					<div style="padding: 5px 0; text-align: center; color: red;"
						id="showMsg"></div>
				</form>
			</div>
			<div region="south" border="false"
				style="text-align: right; padding: 5px 0;">
				<a class="easyui-linkbutton" iconCls="icon-ok"
					href="javascript:void(0)" onclick="login()">登录</a> <a
					class="easyui-linkbutton" iconCls="icon-cancel"
					href="javascript:void(0)" onclick="cleardata()">重置</a>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	document.onkeydown = function(e) {
		var event = e || window.event;
		var code = event.keyCode || event.which || event.charCode;
		if (code == 13) {
			login();
		}
	}
	$(function() {
		$("input[name='login']").focus();
	});
	function cleardata() {
		$('#loginForm').form('clear');
	}
	function login() {
		if ($("input[name='login']").val() == "" || $("input[name='password']").val() == "") {
			$("#showMsg").html("用户名或密码为空，请输入");
			$("input[name='login']").focus();
		} else {
			var json= jQuery("#loginForm").funForm2Json();
			//alert('json='+json);
			//ajax异步提交  
			$.ajax({
				type : "POST", //post提交方式默认是get
				url : "${pageContext.request.contextPath}/platform/admin/checkJson.do",
				data : {
					json : json
				},
				error : function(request) { // 设置表单提交出错                 
					$("#showMsg").html(request); //登录错误提示信息
				},
				success : function(data) {
					
					
					
					document.location = "${pageContext.request.contextPath}/platform/admin/index/app/easyui.do";
				}
			});
		}
	}
</script>
</html>