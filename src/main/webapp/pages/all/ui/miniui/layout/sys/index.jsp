<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>后台管理</title>
<%@ include file="/pages/all/ui/miniui/common/common.jsp"%>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}
.header {
	background:
		url(${pageContext.request.contextPath}/pages/all/ui/miniui/layout/img/header.gif)
		repeat-x 0 -1px;
}
</style>
</head>
<body>
	<!--Layout-->
	<div id="layout1" class="mini-layout"
		style="width: 100%; height: 100%;">
		<div class="header" region="north" height="70" showSplit="false"
			showHeader="false">
			<h1
				style="margin: 0; padding: 15px; cursor: default; font-family: 微软雅黑, 黑体, 宋体;">技术后台(miniUI)</h1>
			<div style="position: absolute; top: 18px; right: 10px;">
				<%
					java.util.ArrayList<java.util.HashMap<String, Object>> listMap=(java.util.ArrayList<java.util.HashMap<String, Object>>)	request.getAttribute("menuFirstMapList");
							if(listMap!=null){
								for(java.util.HashMap<String, Object> map:listMap){
								String id=(String)map.get("SYS_MENU_ID_");
								String id_li="id_li_"+id;
								String name=(String)map.get("SYS_MENU_NAME_");
				%>
				<a onclick="selectMenu('<%=id%>')"
					class="mini-button mini-button-iconTop" iconCls="icon-node"
					plain="true"><%=name%></a>
				<%
					}			
							}
				%>
			</div>
		</div>
		<div title="south" region="south" showSplit="false" showHeader="false"
			height="30">
			<div style="line-height: 28px; text-align: center; cursor: default">Copyright
				©  xx公司版权所有</div>
		</div>
		<div title="center" region="center" style="border: 0;"
			bodyStyle="overflow:hidden;">
			<!--Splitter-->
			<div class="mini-splitter" style="width: 100%; height: 100%;"
				borderStyle="border:0;">
				<div size="230" maxSize="350" minSize="100"
					showCollapseButton="true" style="border: 0;">
					<!--OutlookTree-->
					<div id="leftTree" class="mini-outlooktree"
						url="${pageContext.request.contextPath}/platform/admin/sys/menuListJson.do?pid=${requestScope.menu_id_00010001}"
						onnodeclick="onNodeSelect" textField="text" idField="id"
						parentField="pid"></div>
				</div>
				<div showCollapseButton="false" style="border: 0;">
					<!--Tabs-->
					<div id="mainTabs" class="mini-tabs" activeIndex="2"
						style="width: 100%; height: 100%;" plain="false"
						onactivechanged="onTabsActiveChanged">
						<!-- 
                  <div title="首页" url="../../docs/api/overview.html" >        
                    </div>
                    <div title="子页面关闭" url="../tabs/pages/page1.html" >        
                    </div>
                    <div title="弹出面板" url="${pageContext.request.contextPath}/pages/all/ui/miniui/grid/datagrid.jsp" >        
                    </div>
                    <div title="弹出面板" url="../datagrid/fixedcolumns.html" >        
                    </div>
                 -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		mini.parse();
		var tree = mini.get("leftTree");
		top['index']=window;
		function showTab(node) {
			var tabs = mini.get("mainTabs");
			var id = "tab$" + node.id;
			var tab = tabs.getTab(id);
			if (!tab) {
				tab = {};
				tab._nodeid = node.id;
				tab.name = id;
				tab.title = node.text;
				tab.showCloseButton = true;
				if (node.url == '') {
					tab.url = 'http://www.baidu.com';
				} else {
					//这里拼接了url，实际项目，应该从后台直接获得完整的url地址
					// tab.url = mini_JSPath + "../../docs/api/" + node.id + ".html";
					tab.url = '${pageContext.request.contextPath}' + node.url;
				}
				tabs.addTab(tab);
			}
			tabs.activeTab(tab);
		}
		function onNodeSelect(e) {
			var node = e.node;
			var isLeaf = e.isLeaf;
			if (isLeaf) {
				showTab(node);
			}
		}
		function onClick(e) {
			var text = this.getText();
			alert(text);
		}
		function onQuickClick(e) {
			tree.expandPath("datagrid");
			tree.selectNode("datagrid");
		}
		function onTabsActiveChanged(e) {
			var tabs = e.sender;
			var tab = tabs.getActiveTab();
			if (tab && tab._nodeid) {
				var node = tree.getNode(tab._nodeid);
				if (node && !tree.isSelectedNode(node)) {
					tree.selectNode(node);
				}
			}
		}
		//选择菜单
		function selectMenu(id) {
			var url = "${pageContext.request.contextPath}/platform/admin/sys/menuListJson.do";
			url = url + "?pid=" + id;
			tree.load(url);
		}
	</script>
</body>
</html>