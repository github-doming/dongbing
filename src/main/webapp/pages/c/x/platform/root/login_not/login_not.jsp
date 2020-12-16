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
<%@ include file="/pages/c/x/platform/root/common/common.jsp"%>
<title></title>
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<style type="text/css">
.form-signin-heading {
	font-size: 36px;
	margin-bottom: 20px;
	color: #0663a2;
}
</style>
</head>
<body>
	<div class="hero-unit">
		<table width="100%" height="100%" border="0">
			<tr>
				<td width="10%"></td>
				<td></td>
				<td width="10%"></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<form id="id_form_1" method="post">
						<table class="table table-hover">
							<caption></caption>
							<thead>
								<tr>
									<th colspan="2">
										<h2 class="form-signin-heading"><%=SysConfig.title_login()%></h2>
									</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td align="center" valign="middle" colspan="2">用户名<br></br>
										<input style="width: 300px;"
										class="input-block-level required" value="" name="j_username"
										type="text"></input></td>
								</tr>
								<tr>
									<td align="center" valign="middle" colspan="2">密码<br></br>
										<input style="width: 300px;"
										class="input-block-level required" value="" name="j_password"
										type="password"></input> <lable id="id_lable_pwd"></lable></td>
								</tr>
								<!-- 验证码 -->
								<tr>
									<td align="center" valign="middle" colspan="2">验证码<br></br>
										<input style="width: 300px;"
										class="input-block-level required" value=""
										name="j_verifyCode" type="text"></input> <img
										id="verifyCodeImgId"
										src="${pageContext.request.contextPath}/c/a/verify_code.do" /> <a
										href='#' onclick="javascript:changeVerifyCode()"><label>看不清？</label></a></td>
								</tr>
								<tr>
									<td align="center" valign="middle" colspan="2"><input
										class="btn btn-primary btn-large"
										onClick="javascript:c_post();" type="button" value="登录">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input
										class="btn btn-primary btn-large"
										onClick="javascript:c_register();" type="button" value="注册"></input>
										</input></td>
								</tr>
								<tr>
									<td align="center" valign="middle" colspan="2">
										<h6>
											Copyright © Powered By<a href="" target="_blank"><%=SysConfig.title_version()%></a>1.0
										</h6>
									</td>
								</tr>
							</tbody>
						</table>
					</form>
				</td>
				<td></td>
			</tr>
			<tr height="30%">
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</table>
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
	//var a=jQuery("#id_form_1").funForm2Json();
	//alert('a='+a);
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
						  jQuery("#id_lable_pwd").html("<font color=red>"+jsonObj.messageSys+"</font>");
					}
			    }
			});
//ajax请求
};


function changeVerifyCode() {
		var img = document.getElementById("verifyCodeImgId");
		//img.src = "${pageContext.request.contextPath}/verifyCode?date=" + new Date();
		img.src = "${pageContext.request.contextPath}/c/a/verify_code.do?date=" + new Date();
		
}
</script>
