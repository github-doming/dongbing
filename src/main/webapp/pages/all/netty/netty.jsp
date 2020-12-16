<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<script type="text/javascript">
</script>
</head>
<body>
	<form id="id_form_save"
		action="${pageContext.request.contextPath}/c/a/monitor/gc.do"
		method="post">
		<div class="alert alert-info">通道与用户 NettyUser.channelUserAll</div>
		<table style="width: 100%; table-layout: fixed" id="id_table"
			class="table  table-bordered table-hover">
			<caption></caption>
			<thead>
				<tr>
					<th>appUserId</th>
					<th>channelId</th>
				</tr>
			</thead>
			<tbody>
				<%
					for (io.netty.channel.Channel childChannel : c.a.util.netty.core.TcpNettyServerChannelInboundHandler.group) {
						String childChannelId = childChannel.id().asLongText();
						String appUserId = c.a.util.netty.bean.NettyUser.channelUserAll.get(childChannelId);
				%>
				<tr>
					<td><%=appUserId%></td>
					<td><%=childChannelId%></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<div class="alert alert-info">用户与通道NettyUser.userChannelAll</div>
		<table style="width: 100%; table-layout: fixed" id="id_table"
			class="table  table-bordered table-hover">
			<caption></caption>
			<thead>
				<tr>
					<th>channelId</th>
					<th>appUserId</th>
					<th>remoteAddress</th>
				</tr>
			</thead>
			<tbody>
				<%
					java.util.Set<String> userList = c.a.util.netty.bean.NettyUser.userChannelAll.keySet();
					for (String appUserId : userList) {
						io.netty.channel.Channel channel = c.a.util.netty.bean.NettyUser.userChannelAll.get(appUserId);
						String childChannelId = channel.id().asLongText();
						String remoteAddress = channel.remoteAddress().toString();
				%>
				<tr>
					<td><%=childChannelId%></td>
					<td><%=appUserId%></td>
					<td><%=remoteAddress%></td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<!-- 
			${requestScope.s}
		 -->
		
	</form>
</body>
</html>
<script type="text/javascript">
	/**
	 * 
	 保存
	 */
	function save() {
		//提交
		var obj_form = document.getElementById('id_form_save');
		obj_form.submit();
	}
</script>