<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Login</title>
<%@ include file="/pages/all/ui/miniui/common/common.jsp"%>
<style type="text/css">
body {
	width: 100%;
	height: 100%;
	margin: 0;
	overflow: hidden;
}
</style>
</head>
<body>
	<div id="loginWindow" class="mini-window" title="用户登录"
		style="width: 350px; height: 165px;" showModal="true"
		showCloseButton="false">
		<div id="loginForm" style="padding: 15px; padding-top: 10px;">
			<table>
				<tr>
					<td style="width: 60px;"><label for="username$text">帐号：</label></td>
					<td><input id="username" name="j_username"
						onvalidation="onUserNameValidation" class="mini-textbox"
						required="true" style="width: 150px;" /></td>
				</tr>
				<tr>
					<td style="width: 60px;"><label for="pwd$text">密码：</label></td>
					<td><input id="pwd" name="j_password"
						onvalidation="onPwdValidation" class="mini-password"
						requiredErrorText="密码不能为空" required="true" style="width: 150px;"
						onenter="onLoginClick" /> &nbsp;&nbsp;<a href="#">忘记密码?</a></td>
				</tr>
				<tr>
					<td></td>
					<td style="padding-top: 5px;"><a onclick="onLoginClick"
						class="mini-button" style="width: 60px;">登录</a> <a
						onclick="onResetClick" class="mini-button" style="width: 60px;">重置</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var loginWindow = mini.get("loginWindow");
		loginWindow.show();
		function onLoginClick(e) {
			var form = new mini.Form("#loginWindow");
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			//从后台验证
			var data = form.getData(); //获取表单多个控件的数据
			var json = mini.encode(data); //序列化成JSON
			var jsonObject = JSON.parse(json);
			//jsonObject.version=2;
			json = JSON.stringify(jsonObject);
			//alert('json='+json);
			$.ajax({
				url : "${pageContext.request.contextPath}/platform/admin/checkJson.do",
				type : "post",
				data : {
					json : json
				},
				success : function(text) {
					//alert("提交成功，返回结果:" + text);
					var obj = JSON.parse(text); 
					//alert("提交成功，返回结果:" + obj.success);
					//alert("提交成功，返回结果:" + obj.message);
				}
			});
			//从后台验证
			loginWindow.hide();
			mini.loading("登录成功，马上转到系统...", "登录成功");
			setTimeout(function() {
				window.location = "${pageContext.request.contextPath}/platform/admin/index/app/miniui.do";
			}, 1500);
		}
		function onResetClick(e) {
			var form = new mini.Form("#loginWindow");
			form.clear();
		}
		/////////////////////////////////////
		function isEmail(s) {
			if (s.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1)
				return true;
			else
				return false;
		}
		function onUserNameValidation(e) {
			if (e.isValid) {
				if (e.value.length < 1) {
					e.errorText = "账号不能少于1个字符";
					e.isValid = false;
				}
			}
		}
		function onPwdValidation(e) {
			if (e.isValid) {
				if (e.value.length < 1) {
					e.errorText = "密码不能少于1个字符";
					e.isValid = false;
				}
			}
		}
	</script>
</body>
</html>