<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@page import="c.a.config.SysConfig"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="expires">
	<meta http-equiv="Cache-Control">
	<meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, width=device-width">
	<title>登录</title>
	<link href="${pageContext.request.contextPath}/pages/all/ui/i1/game/common/img/common/favicon.ico" rel="SHORTCUT ICON">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/pages/all/ui/i1/game/common/css/base.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/pages/all/ui/i1/game/common/css/login/login.css"/>
</head>
<body>
	<div class="login">
		<div class="login_text clearfix">
			<div class="fl">
				<p><%=SysConfig.title_login()%></p>
				<img src="${pageContext.request.contextPath}/pages/all/ui/i1/game/common/img/common/login_bg.png" alt="">
			</div>
			<div class="from fr">
				<p>登录</p>
				<div class="clearfix item">
					<label for="account">
						<img src="${pageContext.request.contextPath}/pages/all/ui/i1/game/common/img/common/account.png" alt="">
					</label>
					<input type="text" id="account" placeholder="输入账号或邮箱">
					<ul class="user_list">
						<!--<li></li>-->
					</ul>
				</div>
				<div class="clearfix item">
					<label for="password">
						<img src="${pageContext.request.contextPath}/pages/all/ui/i1/game/common/img/common/login-password.png" alt="">
					</label>
					<input type="password" id="password" placeholder="输入密码">
				</div>
				<div style="display: none;" class="clearfix text">
					<div class="text_item clearfix">
						<label for="VerifyCode">
							<img src="${pageContext.request.contextPath}/pages/all/ui/i1/game/common/img/common/login-password_2.png" alt="">
						</label>
						<input type="text" id="VerifyCode" placeholder="输入右侧验证码">
					</div>
					<div class="text_right clearfix">
						<img src="" alt="">
						<div class="lost_">
							<img src="${pageContext.request.contextPath}/pages/all/ui/i1/game/common/img/common/login-f.png" alt="">
						</div>
					</div>
				</div>
				 <!--记住密码，账号-->
				 <div style="display: none;" class="forget">
					<input type="checkbox" name="forget" id="forget">
					<span>记住账号和密码</span>
				</div>
				<!-- 错误提示 -->
				<span id="id_lable_pwd"></span>
				<span class="error">错误提示</span>
				<button class="btn_login">登录</button>
			</div>
		</div>
	</div>
		<form style="display: none;"  id="id_form_1" method="post">
	        <div class="userName">
	          <span class="signinFormicon"><i class="fa fa-user"></i></span>
	          <input type="text" class="user" name="j_username" placeholder="用户名" id="j_username"/>
	        </div>
	        <div class="passwordNum">
	          <span class="signinFormicon"><i class="fa fa-unlock-alt"></i></span>
	          <input type="password" class="password" name="j_password" placeholder="用户密码" id="j_password"/>
	        </div>
		</form>
	<script src="${pageContext.request.contextPath}/pages/all/ui/i1/game/lib/jquery/3.3.1/jquery-3.3.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/pages/all/ui/i1/game/lib/jquery/jquery.cookie.js"></script>
	<script src="${pageContext.request.contextPath}/pages/all/ui/i1/game/lib/encrypt/jsencrypt.js"></script>
	<script src="${pageContext.request.contextPath}/pages/all/ui/i1/game/common/js/config.js" type="text/javascript" charset="utf-8"></script>
	<script src="${pageContext.request.contextPath}/pages/all/ui/i1/game/common/js/login/login.js?v=1"></script>
</body>
</html>
<script type="text/javascript">
var c_post = function(){
	var name = $("#account").val();
	var password = $("#password").val();
	$("#j_username").val(name);
	$("#j_password").val(password);
	var jsonOjbect = {
		j_username: name,
		j_password: password
	}
	var json=JSON.stringify(jsonOjbect); 
	//alert('json='+json);
	  //ajax请求
	var ajax_url='${pageContext.request.contextPath}/platform/admin/checkJson.do';
	jQuery.ajax({
				url:ajax_url,
				type: "POST",
				async: false,
				cache: true,
				data : {
					json : json
				},
				contentType:'application/x-www-form-urlencoded;charset=UTF-8',
			    error: function(request) {
			        alert("Connection error");
			    },
			    success: function(jsonData,status) {
				//解析json
			     	var jsonObj = jQuery.parseJSON(jsonData);
			      	var success = jsonObj.success;
					if(success){
						 var formObj = document.getElementById('id_form_1');
						 formObj.action='${pageContext.request.contextPath}<%=SysConfig.TargetDoLogin%>';
					     formObj.submit();
					}else{
						//alert("jsonObj.messageSys="+jsonObj.messageSys);
						 jQuery("#id_lable_pwd").html("<font color=red>"+jsonObj.messageSys+"</font>");
					}
			    }
			});
//ajax请求
};
</script>