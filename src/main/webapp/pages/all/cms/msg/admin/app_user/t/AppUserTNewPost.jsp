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
	<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>

	<form id="id_form_save"
		action="${pageContext.request.contextPath}/all/cms/msg/websocket/cms_msg_topic/saveBatch.do"
		method="post">
		<table class="table  table-bordered table-hover" border="1">
			<tr>
				<th colspan="3"><c:choose>
						<c:when test="${requestScope.id==null}"></c:when>
						<c:otherwise>修改</c:otherwise>
					</c:choose></th>
			</tr>
			<tr>
				<td colspan="3">
					<!-- 
		<input onclick="back();" class="btn"
			type="button" value="返回列表"></input>
			 --> <c:choose>
						<c:when test="${requestScope.id==null}">
							<input onclick='save();' class="btn" type="button" value="发送">
						</c:when>
						<c:otherwise>
							<input onclick='save();' class="btn" type="button" value="编辑">
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td align="right">发送用户</td>
				<td><input id="token" size=100  placeholder="请输入" type="text"
					name="token" value="${requestScope.token}"></td>
				<td><span id=""></span></td>
			</tr>
			<tr>
				<td align="right">接收用户</td>
				<td><input id="receiveUser" size=100 type="text"
					name="receiveUser" value="${requestScope.userIds}"></td>
				<td><span id=""></span></td>
			</tr>
			<tr>
				<td align="right">内容</td>
				<td><textarea id="content" name="content" class="form-control"
						rows="3"></textarea></td>
				<td><span id=""></span></td>
			</tr>
			<tr>
				<td align="center" colspan="3"></td>
			</tr>
		</table>
	</form>
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">
	/**
	 * 
	 保存
	 */
	function save() {
		var data = {
			receiveUser:$("#receiveUser").val(),
			content:$("#content").val()
		}
		var jsonVar = {
			data : data,
			token : $("#token").val()
		}
		var jsonData=JSON.stringify(jsonVar);
		alert('jsonVar=' + JSON.stringify(jsonVar));
		var url="${pageContext.request.contextPath}/all/cms/msg/websocket/cms_msg_post/saveBatch.do";
		url=url+"?json="+jsonData;
		alert('url=' + url);
		$
				.ajax({
					url : url,
					type : "POST",
					//data : postData,
					success : function(text) {
						alert('text=' + text);
						//将json字符串转换成json对象 
						var jsonObject = JSON.parse(text);
						//进行提示
						alert('jsonObject=' + JSON.stringify(jsonObject));
					},
					error : function(XMLHttpRequest, textStatus, errorThrown) {
						alert("出错,错误码=" + XMLHttpRequest.status);
						alert("出错=" + XMLHttpRequest.responseText);
					}
				});
	}
</script>
<script type="text/javascript">
	//初始化日期
</script>