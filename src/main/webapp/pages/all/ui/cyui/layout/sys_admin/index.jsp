<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@page import="c.a.config.SysConfig"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<title><%=SysConfig.title_main()%></title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width" />
<link
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap3/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap3/css/bootstrap-theme.min.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery/jquery-3.3.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap3/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/compo/cyui/cyui_treemenu_div.js?v=2"></script>
<style type="text/css">
.page_hidden {
	display: none;
}
.page_show {
	display: block !important;
}
</style>
</head>
<body>
<!-- start header -->
<header class="main-header"
		style="background-image: url(${pageContext.request.contextPath}/pages/example/c/x/all/feng/layout/blue3.jpg);">
		<div class="container">
			<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<h1 style="color: #FFFFFF;">
						<span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>奔亿娱乐</h1>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<a href="${pageContext.request.contextPath}/platform/admin/index.do"
						class="btn btn-default btn-doc" target="_self">系统首页</a>
							<span id="id_menu_first">  </span>
								<a href="javascript:cyui_help();"
						class="btn btn-default btn-doc">密码修改</a>
						 <a
						href="${pageContext.request.contextPath}/platform/admin/logout.do"
						class="btn btn-default btn-doc" target="_self">系统退出</a>
				</div>
			</div>
		</div>
	</header>
	<!-- end header -->
	<div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<div id="cyui_treemenu_div_div_id" class="cyui_treemenu_div_div"
				url="${pageContext.request.contextPath}/all/ui/cyui/layout/sys_menu/comm/list.do"
				treeColumn="text" idField="id" parentField="pid" urlField="url"
				rootParentId="0"></div>
		</div>
		<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
			<ul id="cyui_tab_id" class="nav nav-tabs">
				<li class="active"><a href="#home" data-toggle="tab"> 技术后台</a></li>
			</ul>
			<div id="cyui_tab_content_id" class="tab-content">
				<div class="tab-pane fade in active" id="home">
					<p>这里的技术后台是程序员管理的后台，不是业务后台</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script>
	var contextPath = '${pageContext.request.contextPath}';
	//var file_gif=contextPath+"/pages/c/x/imports/compo/tree_table/images/v2/file.gif";
	//var folder_gif=contextPath+"/pages/c/x/imports/compo/tree_table/images/v2/folder.gif";
	//var folder_closed_gif=contextPath+"/pages/c/x/imports/compo/tree_table/images/v2/folder-closed.gif";
	var file_gif = contextPath
			+ "/pages/c/x/imports/compo/tree_table/images/v1/file.gif";
	var folder_gif = contextPath
			+ "/pages/c/x/imports/compo/tree_table/images/v1/folder.gif";
	var folder_closed_gif = contextPath
			+ "/pages/c/x/imports/compo/tree_table/images/v1/folder-closed.gif";
	
//file_gif='';
//folder_gif='';
//folder_closed_gif='';

	
	
</script>
<script>
	cyui_tab_init();
	cyui_treemenu_div_menuFirstInit(contextPath +'/all/ui/cyui/menu_first/list.do');
</script>
	