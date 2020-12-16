<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="zh-CN">
<head>
<title>技术后台(easyUI)</title>
<%@ include file="/pages/all/ui/easyui/common/common.jsp"%>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap3/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<!-- 加载bootstrap的js -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap3/js/bootstrap.min.js">
</script>
<!-- Bootstrap -->
</head>
<body>
	<div class="easyui-layout" fit="true">
		<div data-options="region:'north'" style="height: 5px"></div>
		<div data-options="region:'south',split:true" style="height: 5px;"></div>
		<div data-options="region:'west',split:true" title=" "
			style="width: 200px;">
			<ul id='tree_id' class="easyui-tree"
				data-options="url:'${pageContext.request.contextPath}/easyui/layout/menu/list.do',method:'get',animate:true,dnd:false"></ul>
		</div>
		<div data-options="region:'center',title:' '">
			<div id='tab_id' class="easyui-tabs"
				data-options="fit:true,border:false,plain:true">
				<div title="首页" style="padding: 5px">
					<table class="easyui-datagrid"
						data-options="url:'${pageContext.request.contextPath}/pages/all/ui/easyui/layout/data/datagrid_data1.json',method:'get',singleSelect:true,fit:true,fitColumns:true">
						<thead>
							<tr>
							</tr>
						</thead>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	function findIframeHeight() {
		
		var height=null;
		var w = window.screen.width; //弹出当前页面的宽度  
		var h = window.screen.height; //弹出当前页面的高度  
		var ua = navigator.userAgent.toLowerCase();
		var flag = false;
		if (ua.indexOf("firefox") > 0) {
			flag = true;
			if (w == 800 && h == 600) {
				height = 206;
			} else {
				if (w == 1024 && h == 768) {
					height = 370;
				} else {
					//调整IFrame的高度为此页面的高度  	
					height = h / 10 * 7;
				}
			}
		}
		if (ua.indexOf("chrome") > 0) {
			//alert('ua=' + ua);
			flag = true;
			if (w == 800 && h == 600) {
				height = 330;
			} else {
				if (w == 1024 && h == 768) {
					height = 460;
				} else {
					//调整IFrame的高度为此页面的高度  	
					height = h / 10 * 7;
				}
			}
		}
		if (ua.indexOf("msie") > 0) {
			flag = true;
			if (w == 800 && h == 600) {
				height = 250;
			} else {
				if (w == 1024 && h == 768) {
					height = 415;
				} else {
					//调整IFrame的高度为此页面的高度  	
					height = h / 10 * 7;
				}
			}
		}
		if (!flag) {
			if (w == 800 && h == 600) {
				height = 250;
			} else {
				if (w == 1024 && h == 768) {
					obj.height = 415;
				} else {
					//调整IFrame的高度为此页面的高度  	
					height = h / 10 * 7;
				}
			}
		}
		return height;
	}
</script>
<script type="text/javascript">
var height=findIframeHeight();

	$('#tree_id').tree({
		onClick : function(node) {
			//alert(node.text);  
			//alert(node.url); 
			var url='${pageContext.request.contextPath}'+node.url;
			addTab(node.text,  url, 'icon-mini-refresh');
		}
	});
	function addTab(title, href, icon) {
		var tt = $('#tab_id');
		if (tt.tabs('exists', title)) {//如果tab已经存在,则选中并刷新该tab 
			tt.tabs('select', title);
			refreshTab({
				tabTitle : title,
				url : href
			});
		} else {
			if (href) {
				var content = '<iframe  scrolling="yes" frameborder="0" src="'
						+ href + '" style="width:100%;height:'+height+'px;"></iframe>';
			} else {
				var content = '未实现';
			}
			tt.tabs('add', {
				title : title,
				closable : true,
				content : content,
				iconCls : icon || 'icon-default'
			});
		}
	}
	/** 
	 * 刷新tab
	 * @cfg 
	 *example: {tabTitle:'tabTitle',url:'refreshUrl'}
	 *如果tabTitle为空，则默认刷新当前选中的tab
	 *如果url为空，则默认以原来的url进行reload
	 */
	function refreshTab(cfg) {
		var refresh_tab = cfg.tabTitle ? $('#tab_id').tabs('getTab',
				cfg.tabTitle) : $('#tab_id').tabs('getSelected');
		if (refresh_tab && refresh_tab.find('iframe').length > 0) {
			var _refresh_ifram = refresh_tab.find('iframe')[0];
			var refresh_url = cfg.url ? cfg.url : _refresh_ifram.src;
			//_refresh_ifram.src = refresh_url;
			_refresh_ifram.contentWindow.location.href = refresh_url;
		}
	}
</script>
