<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@page import="c.a.config.SysConfig"%>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>登录</title>
<!-- Bootstrap -->
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/fontawesome/5.6.3/css/all.css"
/>
<!--[if lt IE 9]>
      <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
      <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
    <![endif]-->
<style type="text/css">
* {
	margin: 0;
	padding: 0;
}
body {
	background-image: url('bg.jpg');
	background-size: 100% 100%;
	background-repeat: no-repeat;
	height: 100%;
}
.content-box {
	margin: calc(50vh - 202.5px) auto;
	height: 100%;
}
.content {
	width: 550px;
	margin: 0 auto;
	background-color: #f7f7f7;
	padding: 20px 30px;
	border-radius: 10px;
}
.content h3 {
	font-weight: bold;
	text-align: center;
}
.input-box {
	margin: 20px 0;
	font-size: 100%;
}
.my-input {
	position: relative;
}
.form-inline .form-control {
	width: 100%;
	height: 40px;
	padding: 10px;
	padding-left: 40px;
}
.my-input i {
	position: absolute;
	margin: 14px 12px;
	color: #8a8a8a;
}
.tips {
	color: red;
	font-size: 12px;
}
.login-btn {
	width: 100%;
	height: 42px;
	font-size: 100%;
}
.input-code {
	width: 290px;
}
.form-code {
	height: 42px;
}
.form-code .form-control {
	width: 240px;
	padding: 10px;
}
.code-img {
	position: absolute;
	left: 260px;
	bottom: 0px;
}
.code-img #canvas {
	border: 1px solid #dddddd;
}
.reset-code {
	color: #337ab7;
}
</style>
</head>
<body>
	<div class="content-box">
		<div class="content">
			<h3><%=SysConfig.title_login()%></h3>
			<form  id="id_form_1" name="loginForm" action=""
				method="post" onsubmit="return checkForm()">
				
				<div style="display: none;"  class="form-group input-box">
					<label>机构</label>
					<div class="form-inline my-input">
						<i class="fa fa-phone fa-lg" aria-hidden="true"></i> <input name="j_type"
							type="text" class="form-control" id="userType"
							placeholder="请输入您的机构" value="">
					</div>
					<span class="tips user-type-tips"></span>
				</div>
				
				
				<div class="form-group input-box">
					<label for="userTel">账号</label>
					<div class="form-inline my-input">
						<i class="fa fa-key fa-lg" aria-hidden="true"></i> <input name="j_username"
							type="text" class="form-control" id="userTel"
							placeholder="请输入您的账号" value="">
					</div>
					<span class="tips user-tips"></span>
				</div>
				<div class="form-group input-box">
					<label for="userPwd">密码</label>
					<div class="form-inline my-input">
						<i class="fa fa-lock fa-lg" aria-hidden="true"></i> <input  name="j_password" 
							type="password" class="form-control" id="userPwd"
							placeholder="请输入您的密码" value="">
					</div>
					<span class="tips pwd-tips"></span>
				</div>
				<div style="display: none;" class="form-group input-box">
					<label for="loginCode">验证码</label>
					<div class="form-inline form-code my-input">
						<input type="text" class="form-control input-code" id="loginCode"
							placeholder="请输入验证码" value="">
						<div class="code-img">
							<canvas id="canvas" width="125" height="36"></canvas>
							<span class="reset-code">看不清，换一张</span>
						</div>
					</div>
					<span class="tips code-tips"></span>
				</div>
				<button onClick="javascript:c_post();" type="button" class="btn btn-primary login-btn">登录</button>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		//产生验证码
		var show_num = [];
		$(document).ready(function() {
			$("#userTel").blur(function() {
				//checkTel();
			});
			$("#userPwd").blur(function() {
				//checkPwd();
			});
			$("#loginCode").blur(function() {
				//checkCode();
			});
			draw(show_num);//加载验证码
			//看不清楚重新获取验证码
			$("#canvas").on('click', function() {
				draw(show_num);
			})
			$(".reset-code").on('click', function() {
				draw(show_num);
			})
		});
		function checkForm() {
			checkTel();
			checkPwd();
			checkCode();
			return false;
		}
		function checkTel() {
			// 验证账号
			var tel = $("#userTel").val();
			var reg = /^1[34578]\d{9}$/;
			if (tel === "") {
				$(".user-tips").text("*账号不能为空");
				return false;
			} else if (!(reg.test(tel))) {
				$(".user-tips").text("*请输入正确的账号");
				return false;
			} else {
				$(".user-tips").hide();
				return true;
			}
		}
		function checkPwd() {
			// 验证密码
			var pwd = $("#userPwd").val();
			if (pwd === "") {
				$(".pwd-tips").text("*密码不能为空");
				return false;
			} else {
				$(".pwd-tips").hide();
				return true;
			}
		}
		function checkCode() {
			// 验证码
			var code_val = $("#loginCode").val().toLowerCase();
			var code = show_num.join("");
			if (code_val === "") {
				$(".code-tips").text("*请输入验证码");
				return false;
			} else if (code_val != code) {
				$(".code-tips").text("*验证码错误！");
				$("#loginCode").val('');
				setTimeout(function() {
					$(".code-tips").hide();
					draw(show_num);
				}, 800)
				return false;
			}
			$(".code-tips").hide();
			return true;
		}
		function draw(show_num) {
			var canvas_width = $('#canvas').width();
			var canvas_height = $('#canvas').height();
			var canvas = document.getElementById("canvas");
			var context = canvas.getContext("2d");
			canvas.width = canvas_width;
			canvas.height = canvas_height;
			var aCode = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
					'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
					'Y', 'Z' ];
			var aLength = aCode.length;
			for (var i = 0; i <= 3; i++) {
				var j = Math.floor(Math.random() * aLength);//获取到随机的索引值
				var deg = Math.random() * 30 * Math.PI / 180;//产生0~30之间的随机弧度
				var txt = aCode[j];//得到随机的一个内容
				show_num[i] = txt.toLowerCase();
				var x = 10 + i * 20;//文字在canvas上的x坐标
				var y = 20 + Math.random() * 8;//文字在canvas上的y坐标
				context.font = "bold 23px 微软雅黑";
				context.translate(x, y);
				context.rotate(deg);
				context.fillStyle = randomColor();
				context.fillText(txt, 0, 0);
				context.rotate(-deg);
				context.translate(-x, -y);
			}
			for (var i = 0; i <= 5; i++) { //验证码上显示线条
				context.strokeStyle = randomColor();
				context.beginPath();
				context.moveTo(Math.random() * canvas_width, Math.random()
						* canvas_height);
				context.lineTo(Math.random() * canvas_width, Math.random()
						* canvas_height);
				context.stroke();
			}
			for (var i = 0; i <= 30; i++) { //验证码上显示小点
				context.strokeStyle = randomColor();
				context.beginPath();
				var x = Math.random() * canvas_width;
				var y = Math.random() * canvas_height;
				context.moveTo(x, y);
				context.lineTo(x + 1, y + 1);
				context.stroke();
			}
		}
		function randomColor() {//得到随机的颜色值
			var r = Math.floor(Math.random() * 200);
			var g = Math.floor(Math.random() * 200);
			var b = Math.floor(Math.random() * 200);
			return "rgb(" + r + "," + g + "," + b + ")";
		}
	</script>
</body>
</html>



<script type="text/javascript">
var c_post = function(){
	var json= jQuery("#id_form_1").funForm2Json();
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
						//jQuery("#id_lable_pwd").html("<font color=red>"+jsonObj.messageSys+"</font>");
						$(".pwd-tips").text(jsonObj.messageSys);
					}
			    }
			});
//ajax请求
};
</script>