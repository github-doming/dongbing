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
						<p>1.修改会员最大在线数</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/ibm_cloud_config/update_online_member_count.do?json={\'token\':\'1\',data:{\'ONLINE_NUMBER_MAX\':\'5\'}}');">
								http://IP:PORT/PROJECT/ibm/admin/ibm_cloud_config/update_online_member_count.do </a>
						</p>
					</td>
					<td>
						<p>{token : "用户token",data {ONLINE_NUMBER_MAX:"会员最大在线数"}}</p>
					</td>
					<td>
						<p>{code : "请求结果代码",</p>
						<p>message : "请求结果消息"}</p>
					</td>
				</tr>

				<tr>
					<td>
						<p>2.发送消息</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/cms_topic/w/save.do?json={\'token\':\'1\',data:{\'TITLE_\':\'test\',\'CONTENT_\':\'testtest\',\'APP_USER_TYPE_\':\'ADMIN\',\'STATE_\':\'OPEN\'}}');">
								http://IP:PORT/PROJECT/ibm/admin/cms_topic/w/save.do </a>
						</p>
					</td>
					<td>
						<p>{token : "用户token",data {TITLE_:"消息标题",CONTENT_:"消息内容",APP_USER_TYPE_:"用户组",STATE_:"消息状态"}}</p>
					</td>
					<td>
						<p>{code : "请求结果代码",</p>
						<p>message : "请求结果消息"}</p>
					</td>
				</tr>				

				<tr>
					<td>
						<p>3.后台删除消息</p>
					</td>
					<td>
						<p>
							<a href="javascript:void(0);"
								onClick="findUrl('${pageContext.request.contextPath}/ibm/admin/cms_topic/w/del.do?json={\'token\':\'1\',data:{\'TOPIC_ID_\':\'f26ca710b1b74c7cb2153ff3c7b9310e\'}}');">
								http://IP:PORT/PROJECT/ibm/admin/cms_topic/w/del.do </a>
						</p>
					</td>
					<td>
						<p>{token : "用户token",data {TOPIC_ID_:"后台消息ID"}}</p>
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
<script type="text/javascript">
    function findUrl(url) {
        url = encodeURI(url);
        window.open(url,"_blank");
        return url;
    }
</script>
</html>
