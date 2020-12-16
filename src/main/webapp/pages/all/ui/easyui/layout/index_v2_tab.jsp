<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="zh-CN">
<head>

<title>动态加载页面,非iframe</title>
<%@ include file="/pages/all/ui/easyui/common/common.jsp"%>



<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


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
		<div data-options="region:'north'" style="height: 10px"></div>
		<div data-options="region:'south',split:true" style="height: 50px;"></div>
		<div data-options="region:'west',split:true" title=" "
			style="width: 200px;">
			<ul id='tree_id' class="easyui-tree"
				data-options="url:'${pageContext.request.contextPath}/easyui/layout/menu/list.do',method:'get',animate:true,dnd:false"></ul>
		</div>
		<div
			data-options="region:'center',title:' '">
			<div id='tab_id' class="easyui-tabs"
				data-options="fit:true,border:false,plain:true">
				
				<div title="首页" style="padding: 5px">
					<table class="easyui-datagrid"
						data-options="url:'${pageContext.request.contextPath}/pages/all/ui/easyui/layout/data/datagrid_data1.json',method:'get',singleSelect:true,fit:true,fitColumns:true">
						<thead>
							<tr>
								<th data-options="field:'itemid'" width="80">Item ID</th>
								<th data-options="field:'productid'" width="100">Product ID</th>
								<th data-options="field:'listprice',align:'right'" width="80">List
									Price</th>
								<th data-options="field:'unitcost',align:'right'" width="80">Unit
									Cost</th>
								<th data-options="field:'attr1'" width="150">Attribute</th>
								<th data-options="field:'status',align:'center'" width="50">Status</th>
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
$('#tree_id').tree({
	onClick: function(node){
		//alert(node.text);  
	//alert(node.url); 
		$('#tab_id').tabs('add',{
			title: node.text,
			selected: true
		});
		var tab = $('#tab_id').tabs('getSelected');  // get selected panel
		tab.panel('refresh', '${pageContext.request.contextPath}'+node.url);
	
	}
});

</script>

