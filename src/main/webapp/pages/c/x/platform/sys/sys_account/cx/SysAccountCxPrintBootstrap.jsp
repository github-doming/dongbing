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

<table class="table table-hover" border="0">
			<caption></caption>
			<thead>
				<tr>
					<!-- 
				<th></th>
		 -->
				</tr>
			</thead>
			<tbody>
				<tr>
					<!-- 
					<td></td>
		 -->
				</tr>
				<tr>
					<td>



						<input class="btn" id="id_input_new" type="button" value="打印"
						onclick="doPrint();"></td>

				</tr>
			</tbody>
		</table>
		

<form action="${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/list.do"  id="id_form_list" method="post">
<table id="sample-table-1" border="1" cellpadding="2" cellspacing="1">
	<caption></caption>
	<thead>
		<tr>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_ACCOUNT_ID_');"
				title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . sysAccountId%>]排序" style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . sysAccountId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_USER_ID_');"
				title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . sysUserId%>]排序" style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . sysUserId%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','SYS_ACCOUNT_NAME_');"
				title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias .sysAccountName%>]排序" style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias .sysAccountName%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','PWD_');"
				title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias .password%>]排序" style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias .password%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
				title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . desc%>]排序" style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . desc%></th>
		<!-- style="display: none; cursor: pointer;" -->
<th
				onclick="ayPageOrderEvent('${pageContext.request.contextPath}','REGISTER_TYPE_');"
				title="按[<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . registerType%>]排序" style="cursor: pointer;"><%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . registerType%></th>
	<th class="class_color">
			</th>
		</tr>
	</thead>
	<tbody>
		<%
		java.util.ArrayList<java.util.HashMap<String, Object>> listMap = (java.util.ArrayList<java.util.HashMap<String, Object>>) request
				.getAttribute("list");
	for (java.util.HashMap<String, Object> info : listMap) {
	%>
		<tr>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SYS_ACCOUNT_ID_">&nbsp;<%=info.get("SYS_ACCOUNT_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SYS_USER_ID_">&nbsp;<%=info.get("SYS_USER_ID_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.SYS_ACCOUNT_NAME_">&nbsp;<%=info.get("SYS_ACCOUNT_NAME_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.PASSWORD_">&nbsp;<%=info.get("PASSWORD_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.DESC_">&nbsp;<%=info.get("DESC_")%></td>
	<!--  style="display: none;word-wrap : break-word;" -->
	<td style="word-wrap : break-word;  " id="hit.REGISTER_TYPE_">&nbsp;<%=info.get("REGISTER_TYPE_")%></td>
			<td style="word-wrap : break-word;  ">&nbsp;</td>
		</tr>
		<%
		}
	%>
	</tbody>
</table>
</form>
</body>
</html>



<script type="text/javascript">
	

	function doPrint() {
		
		
		window.print();
	}
</script>
