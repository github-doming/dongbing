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
	<form
		action="${pageContext.request.contextPath}/all/workflow/activiti/process_definition/list.do"
		id="id_form_list" method="post">
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
					<input class="btn" id="id_input_new" type="button" value="新增"
						onclick="newRecord()"> 
						 <input class="btn"
						id="id_input_new" type="button" value="删除"
						onclick="delAllRecord()"></td>
				</tr>
			</tbody>
		</table>
		<table style="display: none;" align="center"">
			<tr>
				<td>升降序</td>
				<td><input value="${requestScope.sortFieldValue}"
					id="sortFieldId" name="sortFieldName" /></td>
				<td>排序的列值</td>
				<td><input value="${requestScope.sortOrderValue}"
					id="sortOrderId" name="sortOrderName" /></td>
			</tr>
		</table>
		<table style="width: 100%; table-layout: fixed" id="id_table"
			class="table  table-bordered table-hover">
			<caption></caption>
			<thead>
				<tr>
					<th width="60px;"><input id="id_checkbox_if_all"
						onclick=ayTableCheckAll(); type="checkbox"
						name="name_checkbox_if_all" />全选</th>
					<th width="150px;">操作</th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','JOB_NAME_');"
						title="按id排序" style="cursor: pointer;">id</th>
						
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','ProcessDefinitionId');"
						title="按id排序" style="cursor: pointer;">ProcessDefinitionId</th>
							
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','JOB_CLASS_NAME_');"
						title="按name排序" style="cursor: pointer;">name</th>
					<!-- style="display: none; cursor: pointer;" -->
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
						title="按key排序" style="cursor: pointer;">key</th>
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
						title="按MetaInfo排序" style="cursor: pointer;">DeploymentId</th>
						
					<th
						onclick="ayPageOrderEvent('${pageContext.request.contextPath}','DESC_');"
						title="按MetaInfo排序" style="cursor: pointer;">Suspended</th>
							
					<th class="class_color">
						<!--隐藏列事件--> <select style="width: 60px;"
						onchange="ayTableHiddenColumn(this,'id_table',2);">
							<option value=" &nbsp;" selected="selected">&nbsp;</option>
							<option value="hit.ProcessDefinitionId">ProcessDefinitionId/option>
							<option value="hit.name">name</option>
							<option value="hit.ProcessDefinitionKey">ProcessDefinitionKey</option>
							<option value="hit.DeploymentId">DeploymentId</option>
							
							<option value="hit.ProcessDefinitionVersion">ProcessDefinitionVersion</option>
							<option value="hit.Suspended">Suspended</option>
							<option value="hit.Description">Description</option>
					</select> <!--隐藏列事件-->
					</th>
				</tr>
			</thead>
			<tbody>
				<%
					java.util.ArrayList<org.activiti.engine.runtime.ProcessInstance> listMap = (java.util.ArrayList<org.activiti.engine.runtime.ProcessInstance>) request
							.getAttribute("list");
					for (org.activiti.engine.runtime.ProcessInstance info : listMap) {
				%>
				<tr>
					<td><input value="<%=info.getId()%>" type="checkbox"
						name="name_checkbox_ids"></td>
					<td>
								<a onclick="viewRecord('<%=info.getId()%>')" href="#">
							查看 </a> 
					</td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.id">&nbsp;<%=info.getId()%></td>
					<td style="word-wrap: break-word;" id="hit.ProcessDefinitionId">&nbsp;<%=info.getProcessDefinitionId()%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.name">&nbsp;<%=info.getProcessDefinitionName()%></td>
					<!--  style="display: none;word-wrap : break-word;" -->
					<td style="word-wrap: break-word;" id="hit.Key">&nbsp;<%=info.getProcessDefinitionKey()%></td>
					<td style="word-wrap: break-word;" id="hit.DeploymentId">&nbsp;<%=info.getDeploymentId()%></td>
					<td style="display: none;word-wrap: break-word;" id="hit.ProcessDefinitionVersion">&nbsp;<%=info.getProcessDefinitionVersion()%></td>
					<td style="word-wrap: break-word;" id="hit.Suspended">&nbsp;<%=info.isSuspended()%></td>
					<td style="display: none;word-wrap: break-word;" id="hit.Description">&nbsp;<%=info.getDescription()%></td>
					
					
					<td style="word-wrap: break-word;">&nbsp;</td>
				</tr>
				<%
					}
				%>
			</tbody>
		</table>
		<%@ include file="/pages/c/x/imports/feng/page/simple/page.jsp"%>
	</form>
</body>
</html>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/page/simple/page.js?version=<%=version%>">
</script>
<!-- 加载js -->
<script type="text/javascript">
	/**
	 * 
	 查看记录
	 */
	function viewRecord(id) {
		var url = "http://localhost:9080/wf/modeler.html?modelId="+ id;
		window.location.href = url;
	}
</script>