<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/pages/cosmos/lib/third/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/pages/cosmos/lib/third/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/pages/cosmos/lib/third/easyui/demo/demo.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/cosmos/lib/third/easyui/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/cosmos/lib/third/easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<!-- 表格 -->
	<table id="test" title=" " class="easyui-treegrid"
		style="width: 100%; height: 300px"
	url="${pageContext.request.contextPath}/${resources_name_all}/${ay_table_name_child}/${ay_table_name_module}/listJson.do"
		rownumbers="true" idField="${fieldNamePk}" treeField="name" >
		<thead>
			<tr>
			#foreach( $name in $ay_list_column )
<th field="${name.fieldName}" width="160"><%=${ay_package_name}.alias.${ay_table_class}Alias . ${name.fieldName}%></th>
#end
			</tr>
		</thead>
	</table>

</body>
</html>

