<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title>接口测试页</title>
</head>
<body>
	<div class="container-fluid">
		<h1>api接口说明(PC端,陈俊先)</h1>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>接口名称</th>
					<th>进度</th>
					<th>接口定义</th>
					<th>接口调用示例</th>
					<th>参数说明</th>
					<th>返回说明</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>PC端登录</td>
					<td>敏婷</td>
					<td>{"session":" ","code":"9625","name": "cjx", "password":
						"cjx" }</td>
					<td><a
						href="javascript:void(0);" onClick="findUrl('${pageContext.request.contextPath}/all/pc/token/login.do?json={session:\'E0FDAD23FDA7ECBFE555036544C539B6\',code:\'2304\',name: \'o1\',password:\'1\'}');" 
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/pc/token/login.do</a>
					</td>
					<td>
						<p>username：登陆用户名</p>
						<p>password:登陆密码</p>
						<p>code:验证码</p>
					</td>
					<td>
						<p>app400：用户名或密码出错</p>
					</td>
				</tr>
				<tr>
					<td>PC端注册</td>
					<td>敏婷</td>
					<td>{"session":"" ,"code":"3776","name": "cjx1", "password":
						"cjx1"}</td>
					<td><a
						href="${pageContext.request.contextPath}/all/pc/token/register.do?json={session:'E0FDAD23FDA7ECBFE555036544C539B6' ,code:'2304',name: 'cjx5',password:'cjx5'}"
						target="_blank">http://IP:PORT${pageContext.request.contextPath}/all/pc/token/register.do</a>
					</td>
					<td>
						<p>username：登陆用户名</p>
						<p>password:登陆密码</p>
						<p>code:验证码</p>
					</td>
					<td></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>
<script type="text/javascript">
function findUrl(url){
	//alert('url='+url);
	url=encodeURI(url);
	//alert('url2='+url);
	window.location.href=url;
	return url;
}
</script>