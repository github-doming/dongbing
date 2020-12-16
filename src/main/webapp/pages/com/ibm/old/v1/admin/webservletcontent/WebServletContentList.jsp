<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title></title>
</head>
<body>
<div style="display: none;" id="id_div_msg">正在提交中...</div>
<center>
	<div style="color:white;width:100%;height:100px;background-color:rgb(29,41,57);padding-top:20px">
		<span style="font-size:30px;">线程池管理</span>
	</div>
</center>
<form action=""  id="id_form_list" method="post">
<table id="topic_title" style="width:90%;table-layout:fixed; margin: 0px auto;"  id="id_table" class="table  table-bordered table-hover">
	<caption></caption>
	<thead>
		<tr>
			<th style="width: 10%;" ><input id="id_checkbox_if_all" onclick=ayTableCheckAll(); type="checkbox" name="name_checkbox_if_all" />全选</th>
			<th style="width: 10%;" >操作</th>
			<th>线程池类型</th>
			<th>当前的线程数</th>
			<th>正在执行任务数</th>
			<th>线程池核心大小</th>
			<th>已安排执行的大致任务总数</th>
			<th>线程池最大容量</th>
			<th>线程池空闲时间</th>
		</tr>
	</thead>
	<tbody>
		<%
		java.util.HashMap<String,java.util.HashMap<String, Object>> map = (java.util.HashMap<String,java.util.HashMap<String, Object>>) request
				.getAttribute("threadPool");
	for (java.util.Map.Entry<String, java.util.HashMap<String, Object>> entry : map.entrySet()) {
		String code = entry.getKey();
		java.util.HashMap<String, Object> info = entry.getValue();
	%>
<tr>
	<td><input value="<%=code%>" type="checkbox"
		name="name_checkbox_ids"></td>
	<td><a href="${pageContext.request.contextPath}/pages/com/ibm/admin/webservletcontent/WebServletContentForm.jsp?code=<%=code%>&corePoolSize=<%=info.get("corePoolSize")%>&maximumPoolSize=<%=info.get("maximumPoolSize")%>&keepAliveTimeSeconds=<%=info.get("keepAliveTimeSeconds")%>" > 编辑 </a></td>
	<td style="word-wrap : break-word;  " id="code">&nbsp;<%=code%></td>
	<td style="word-wrap : break-word;  " id="poolSize">&nbsp;<%=info.get("poolSize")%></td>
	<td style="word-wrap : break-word;  " id="activeCount">&nbsp;<%=info.get("activeCount")%></td>
	<td style="word-wrap : break-word;  " id="corePoolSize">&nbsp;<%=info.get("corePoolSize")%></td>
	<td style="word-wrap : break-word;  " id="taskCount">&nbsp;<%=info.get("taskCount")%></td>
	<td style="word-wrap : break-word;  " id="maximumPoolSize">&nbsp;<%=info.get("maximumPoolSize")%></td>
	<td style="word-wrap : break-word;  " id="keepAliveTimeSeconds">&nbsp;<%=info.get("keepAliveTimeSeconds")%></td>
</tr>
		<%
		}
	%>
	</tbody>
</table>
<br/>
</form>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/page/simple/page.js">
        </script>
<!-- 加载js -->
</html>