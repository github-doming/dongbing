<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script
	src="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/miniui/boot.js?v=2"
	type="text/javascript"></script>
	
	<script
	src="${pageContext.request.contextPath}/pages/cosmos/common/all/third/miniui/miniui.js"
	type="text/javascript"></script>
	
<title></title>
</head>
<body>
	<form id="form1" method="post">
		
		
		<div class="mini-toolbar" style="padding: 2px; border-bottom: 0;">
			<table style="width: 100%;">
				<tr>
					<td style="width: 100%;"><a onclick="save()"
						class="mini-button" iconCls="icon-save" plain="true">保存</a></td>
				</tr>
			</table>
		</div>
		<div id="tabs1" class="mini-tabs" activeIndex="0" plain="false"
			buttons="#tabsButtons">
			<div title="基本信息">
				<table class="table-detail" cellspacing="1" cellpadding="0">
					<caption></caption>
				
					
<tr>
<th>
<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . sysAccountId%>：</th>
<td>
<input name="sysAccountId" value="${requestScope.s.sysAccountId}"
class="mini-textbox" style="width: 90%" /></td>
</tr>

<tr>
<th>
<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . sysUserId%>：</th>
<td>
<input name="sysUserId" value="${requestScope.s.sysUserId}"
class="mini-textbox" style="width: 90%" /></td>
</tr>

<tr>
<th>
<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias .sysAccountName%>：</th>
<td>
<input name="sysAccountName" value="${requestScope.s.sysAccountName}"
class="mini-textbox" style="width: 90%" /></td>
</tr>

<tr>
<th>
<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias .password%>：</th>
<td>
<input name="password" value="${requestScope.s.password}"
class="mini-textbox" style="width: 90%" /></td>
</tr>

<tr>
<th>
<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . desc%>：</th>
<td>
<input name="desc" value="${requestScope.s.desc}"
class="mini-textbox" style="width: 90%" /></td>
</tr>

<tr>
<th>
<%=c.x.platform.sys.sys_account.cx.alias.SysAccountCxAlias . registerType%>：</th>
<td>
<input name="registerType" value="${requestScope.s.registerType}"
class="mini-textbox" style="width: 90%" /></td>
</tr>



				</table>
			</div>
		</div>
	</form>
	<script type="text/javascript">
		mini.parse();
		var form = new mini.Form("form1");
		//保存
		function save() {
			form.validate();
			if (form.isValid() == false) {
				return;
			}
			var formData = $("#form1").serializeArray();
			var formJson = JSON.stringify(formData);
			var postData = {
				json : formJson
			}
			$.ajax({
				url : "${pageContext.request.contextPath}/c/x/platform/sys/sys_account/cx/save/miniui.do",
				type : "POST",
				data : postData,
				success : function(text) {
					//将json字符串转换成json对象 
					var jsonObject = JSON.parse(text);
					mini_onOk();
					//进行提示
					mini_showTips({
						msg: jsonObject.message
					});
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert("出错,错误码=" + XMLHttpRequest.status);
					alert("出错=" + XMLHttpRequest.responseText);
				}
			});
		}
		
	</script>
</body>
</html>