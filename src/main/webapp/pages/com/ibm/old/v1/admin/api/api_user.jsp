<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp" %>
    <title>接口测试页</title>
</head>
<body>
<div class="container-fluid">
    <h1>api接口说明</h1>
    <table class="table table-bordered table-striped">
        <thead>
        <tr>
			<th>接口名称</th>
			<th>接口调用示例</th>
			<th>入参说明</th>
			<th>返参说明</th>
		</tr>
        </thead>
        <tbody>
	        <tr>
				<td>
					<p>1.修改用户等级</p>
				</td>
				<td>
					<p>
						<a href="javascript:void(0);"
							onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/app_user/modify_user_type.do?json={\'token\':\'ed2f1c6e58ea4b0d814d855f9c817842\',data:{\'ROLE_CODE_\':\'ADMIN\'}}');">
							http://IP:PORT/PROJECT/ibm/admin/app_user/modify_user_type.do </a>
					</p>
				</td>
				<td>
					<p>{token : "用户token",data {ROLE_CODE_:"角色CODE"}}</p>
				</td>
				<td>
					<p>{code : "请求结果代码",</p>
					<p>message : "请求结果消息"}</p>
				</td>
			</tr>
        </tbody>
    </table>
</div>
</body>
</html>
<script type="text/javascript">
    function findUrl(url) {
        url = encodeURI(url);
        window.open(url, "_blank");
        return url;
    }
</script>
<script type="text/javascript">
</script>
