<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@page import="c.a.config.SysConfig"%>
<%@page import="c.a.config.SysConfig"%>
<!--导入标签-->
<!--记得import进标签 --><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="zh-CN">
<head>
<%@ include file="/pages/c/x/platform/root/common/common.jsp"%>
    <meta charset="utf-8" name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0">
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <style>
		*{margin:0;padding:0;}
        body{background: #c8c8c8;}
		.top_nav{height:50px;background:#e5e5e5;border-bottom:#b7b7b7;}
		.top_nav img{height:45px;width:200px;margin-top:2px;margin-left:5px;}
		.bj{background:url(${pageContext.request.contextPath}/pages/all/cds/layout/login_not/timg.jpg) center center  no-repeat;position:fixed;top:50px;left:0;right:0;bottom:0;}
		.tongji{margin-top:30px;text-align:center;font-size:26px;heigth:26px;line-height:26px;}
		.login_div{width:400px;height:260px;position:fixed;top:50%;left:50%;margin-left:-200px;margin-top:-130px;padding:20px; background-color:rgba(255,255,255,0.3);}
		.login_div .title{height:30px;line-height:30px;margin-bottom:20px;text-align: center;}
		.login_div .user , .login_div  .password{height:30px;line-height:30px;    margin: 0 auto;margin-bottom:30px;border:1px solid #b7b7b7;display: block;width: 300px;}
		.login_div .login{height:30px;line-height:30px;width:300px;text-align:center;background:#f1f1f1;border:1px solid #b7b7b7;font-size:16px;margin:0 auto;cursor:pointer;margin-top:10px;}
		.login_div .yzm{width: 300px;margin:0 auto;height:30px;}
		.login_div .yzm img{float:left;width:120px;height:30px;}
		.login_div .yzm input{float:right;width:120px;height:26px;margin-left:10px;}
		
    </style>
</head>
<body>
	<form id="id_form_1" method="post">
	<div class="top_nav"><img src="" /></div>
	<div class="bj">
		<div class="tongji">广州市统计局大数据平台</div>
		<div class="login_div">
			<div class="title">用户登录</div>
			<input value=""  name="j_username" type="text" class="user" placeholder="用户名"/>
			<input value=""  name="j_password" type="password" class="password" placeholder="密码"/>
			<div class="yzm">
				<img src=""/>
				<input type="text" placeholder="验证码"/>
			</div>
			<div onClick="javascript:c_post();" class="login">登录</div>
		</div>
	</div>
	
</form>
</body>
</html>



<script type="text/javascript">
var c_register = function(){
	var json= jQuery("#id_form_1").funForm2Json();
		  //ajax请求
	var ajax_url='${pageContext.request.contextPath}/platform/admin/register_check.do';
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
							formObj.action='${pageContext.request.contextPath}/platform/admin/register.do';
					    	formObj.submit();
							
						}else{
							  jQuery("#id_lable_pwd").html(json.message);
						}
				    }
				});
	//ajax请求
};
var c_post = function(){
	//var a=jQuery("#id_form_1").funForm2Json();
	//alert('a='+a);
	
	var json= jQuery("#id_form_1").funForm2Json();
	
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
						
						alert('error');
						
						  jQuery("#id_lable_pwd").html("<font color=red>"+jsonObj.message+"</font>");
					}
			    }
			});
//ajax请求
};

	function changeVerifyCode() {
		var img = document.getElementById("verifyCodeImgId");
		img.src = "${pageContext.request.contextPath}/verifyCode?date=" + new Date();
	}
</script>