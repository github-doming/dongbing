<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<title>所有UI</title>
</head>
<body>
	<div class="container-fluid">
		<h1>所有UI</h1>
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th>UI</th>
					<th>sys登录</th>
					<th>sys首页</th>
					<th>app登录</th>
					<th>app首页</th>
				</tr>
			</thead>
			<tbody>

				<tr>
					<td></td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/login2.do">login2</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/index.do">index</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/login.do">login</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/index.do">index</a>
					</td>
				</tr>


				<tr>
					<td>cds</td>
					<td></td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/index/sys/cds.do">cds</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/login/cds.do">cds</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/index/app/cds.do">cds</a>
					</td>
				</tr>
				<tr>
					<td>bootstrap</td>
					<td></td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/index/sys/bootstrap.do">bootstrap</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/login/bootstrap.do">bootstrap</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/index/app/bootstrap.do">bootstrap</a>
					</td>
				</tr>

				<tr>
					<td>easyui</td>
					<td></td>

					<td><a
						href="${pageContext.request.contextPath}/platform/admin/index/sys/easyui.do">easyui</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/login/easyui.do">easyui</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/index/app/easyui.do">easyui</a>
					</td>
				</tr>


				<tr>
					<td>miniui</td>
					<td></td>

					<td><a
						href="${pageContext.request.contextPath}/platform/admin/index/sys/miniui.do">miniui</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/login/miniui.do">miniui</a>
					</td>
					<td><a
						href="${pageContext.request.contextPath}/platform/admin/index/app/miniui.do">miniui</a>
					</td>
				</tr>

			</tbody>
		</table>
	</div>
</body>
</html>
<script type="text/javascript">
	function findUrl(url) {
		//alert('url='+url);
		url = encodeURI(url);
		//alert('url2='+url);
		window.location.href = url;
		return url;
	}
</script>
<script type="text/javascript">
	
</script>
