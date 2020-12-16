<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@page import="c.a.config.SysConfig"%>
<!--导入标签-->
<!--记得import进标签 --><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="zh-CN">
<head>
    <title>系统登录</title>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content = "IE=edge,chrome=1" />
    <meta name="renderer" content="webkit" />
    <meta name="viewport" content="width=device-width,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" />
   
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
       
<!-- 
  <script src="${pageContext.request.contextPath}/resources2/pangolin/js/jq.js" ></script>
  <script src="${pageContext.request.contextPath}/resources2/pangolin/js/bootstrap.min.js" ></script>
  <script src="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery/jquery-2.1.4.min.js?version=<%=version %>"></script>
  <script src="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap3/js/bootstrap.min.js?version=<%=version %>"></script>
 
  
  
-->

    <script src="${pageContext.request.contextPath}/resources2/js/bootstrapValidator.min.js?version=<%=version %>"></script>
    <link href="${pageContext.request.contextPath}/resources2/pangolin/css/style.default.css?version=<%=version %>"  rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources2/pangolin/css/signin.css?version=<%=version %>"  rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources2/pangolin/css/modal.css?version=<%=version %>" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/resources2/css/bootstrapValidator.css?version=<%=version %>" rel="stylesheet">
   
    <style type="text/css">
    	.warning {color:red;margin-left:26px;}
    	.yanzheng {margin-bottom: 0px;}
    </style>

  </head>
  <body class="signinBg">
  	<div class="productName">
  	  <div class="logoImg">
  	    <!-- Logo image -->
  	    <img src="" />
      </div>
	  <div class="productNameText">
	    <p class="ChineseName"><%=SysConfig.title_login()%></p>
	    <p class="EnglishName"></p>
	  </div>
  	</div>
  	<div class="bottomContent">
	  <div class="leftImg">
	    <!-- Backgroud image -->
	    <img src="${pageContext.request.contextPath}/resources2/pangolin/images/shuzongimg/signinBgImg.png" />
	  </div>
      <div class="rightForm">
	    <form id="signinForm"  method="POST">
	      <div class="signinForm">
	        <p>欢迎登录</p>
	        
	      
	       <div style="display: none;"  class="userName">
	          <span class="signinFormicon"><i class="fa fa-user"></i></span>
	          <input type="text" class="user" name="j_project" placeholder="项目名" id="j_project"/>
	        </div>
	        
	        
	        
	        <div  style="display: none;"  class="userName">
	          <span class="signinFormicon"><i class="fa fa-user"></i></span>
	          <input type="text" class="user" name="j_org" placeholder="机构名" id="j_org"/>
	        </div>
	        
	        
	        <div class="userName">
	          <span class="signinFormicon"><i class="fa fa-user"></i></span>
	          <input type="text" class="user" name="j_username" placeholder="用户名" id="j_username"/>
	        </div>
	        <div class="passwordNum">
	          <span class="signinFormicon"><i class="fa fa-unlock-alt"></i></span>
	          <input type="password" class="password" name="j_password" placeholder="用户密码" id="j_password"/>
	        </div>
	        <!-- 
	        <div class="yanzheng">
	          <span  class="signinFormicon">验证码:</span>
	          <input type="text" class="password yzm" name="captcha" placeholder="验证码" maxlength="4"/>
	          <img src="" id="verifyImg" name="verifyImg"/>   
	        </div>
	         -->
	        <div class="warning"></div>
	        <div onClick="javascript:c_post();" class="submitBtn">
	          <button type="button" class="denglu">登&nbsp;录</button>
	          <button type="button" class="tishiBtn" data-toggle="modal" data-target=".bs-example-modal-lg"></button>
	        </div>
	      </div>	    
	    </form>
	  </div>
  	</div>
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
	//alert('a1='+$("#signinForm").html());
	//var a2=$("#signinForm").funForm2Json();
	//alert('a2='+a2);
	//return ;
	var json= jQuery("#signinForm").funForm2Json();
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
						 var formObj = document.getElementById('signinForm');
						 formObj.action='${pageContext.request.contextPath}<%=SysConfig.TargetDoLogin%>';
					     formObj.submit();
					}else{
						//alert('error='+jsonObj.messageSys);
						jQuery(".warning").html("<font color=red>"+jsonObj.messageSys+"</font>");
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