<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<!-- We support more than 40 localizations -->
<script type="text/ecmascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/complex/table/jq_grid/5.3.2/js/i18n/grid.locale-cn.js"></script>
<!-- This is the Javascript file of jqGrid -->
<script type="text/ecmascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/complex/table/jq_grid/5.3.2/js/jquery.jqGrid.min.js"></script>
<!-- This is the localization file of the grid controlling messages, labels, etc.
    <!-- The link to the CSS that the grid needs -->
<link rel="stylesheet" type="text/css" media="screen"
	href="${pageContext.request.contextPath}/pages/c/x/includes/complex/table/jq_grid/5.3.2/css/ui.jqgrid.css" />
<script>
	$.jgrid.defaults.width = 780;
</script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery_ui/blue_3/jquery-ui.css">
<script
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery_ui/blue_3/jquery-ui.js"></script>
</head>
<body>
	<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>
	<table class="table  table-bordered table-hover" border="1">
		<tr>
			<th colspan="3">项目人员 <c:choose>
					<c:when test="${requestScope.id==null}">添加</c:when>
					<c:otherwise>修改</c:otherwise>
				</c:choose></th>
		</tr>
		<tr>
			<td colspan="3"><input onclick="back();" class="btn"
				type="button" value="返回列表"></input> <c:choose>
					<c:when test="${requestScope.id==null}">
						<input onclick='save();' class="btn" type="button" value="新增">
					</c:when>
					<c:otherwise>
						<input onclick='save();' class="btn" type="button" value="编辑">
					</c:otherwise>
				</c:choose></td>
		</tr>
		<tr>
			<td>
				<p class="text-right">用户名：</p>
			</td>
			<td><input readonly="readonly"
				id="idInput_appAccount_appUserName"
				class="input-medium search-query" type="text"
				name="app_user.appUserName" value="${requestScope.s.appUserName}">
				<!-- 按钮触发模态框 -->
				<button class="btn btn-primary" data-toggle="modal"
					data-target="#idBootstrapModal">绑定用户</button></td>
			<td width="30%"></td>
		</tr>
	</table>
	<form id="id_form_save"
		action="${pageContext.request.contextPath}/all/task/tms/project_user/app_user/save/bootstrap.do"
		method="post">
		<table style="display: none;" align="center" border="1">
			<tr>
				<td>树id</td>
				<td><input value="${requestScope.value_first$tree$id}"
					id="id_first$tree$id" name="name_first$tree$id" /></td>
				<td>树名称</td>
				<td><input value="${requestScope.value_first$tree$name}"
					id="id_first$tree$name" name="name_first$tree$name" /></td>
			</tr>
		</table>
		<table class="table  table-bordered table-hover" border="1">
			<tr style="display: none;" >
				<td>
					<p class="text-right">项目id：</p>
				</td>
				<td><input id="id_input_parent_menu_id" type="text"
					name="tms_project.parent" value="${requestScope.p.tmsProjectId}"></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<p class="text-right">项目名称：</p>
				</td>
				<td><input id="id_input_parent_menu_name"
					class="input-medium search-query" readonly="readonly" type="text"
					name="" value="${requestScope.p.projectName}"> <input
					onclick="parent('${requestScope.p.path}');" class="btn"
					type="button" value="选择项目"></td>
				<td width="30%"></td>
			</tr>
			
<tr style="display: none;" >
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.appUserId%></td>
		<td><input id="idInput_appAccount_appUserId" type="text" name="app_user.appUserId"
			value="${requestScope.s.appUserId}"></td>
		<td><span id="id_span$app_user$APP_USER_ID_"></span>
		</td>
</tr>	
			
			
			<!-- 
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.idx%></td>
		<td><input id="id_input$app_user$IDX_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.idx%>"  type="text" name="app_user.idx"
			value="${requestScope.s.idx}">
		</td>
		<td><span id="id_span$app_user$IDX_"></span>
		</td>
</tr>	

<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.mainAppAccountId%></td>
		<td><input id="id_input$app_user$MAIN_APP_ACCOUNT_ID_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.mainAppAccountId%>"  type="text" name="app_user.mainAppAccountId"
			value="${requestScope.s.mainAppAccountId}">
		</td>
		<td><span id="id_span$app_user$MAIN_APP_ACCOUNT_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.sysUserId%></td>
		<td><input id="id_input$app_user$SYS_USER_ID_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.sysUserId%>"  type="text" name="app_user.sysUserId"
			value="${requestScope.s.sysUserId}">
		</td>
		<td><span id="id_span$app_user$SYS_USER_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.appUserCode%></td>
		<td><input id="id_input$app_user$APP_USER_CODE_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.appUserCode%>"  type="text" name="app_user.appUserCode"
			value="${requestScope.s.appUserCode}">
		</td>
		<td><span id="id_span$app_user$APP_USER_CODE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.appUserType%></td>
		<td><input id="id_input$app_user$APP_USER_TYPE_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.appUserType%>"  type="text" name="app_user.appUserType"
			value="${requestScope.s.appUserType}">
		</td>
		<td><span id="id_span$app_user$APP_USER_TYPE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.tableName%></td>
		<td><input id="id_input$app_user$TABLE_NAME_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.tableName%>"  type="text" name="app_user.tableName"
			value="${requestScope.s.tableName}">
		</td>
		<td><span id="id_span$app_user$TABLE_NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.headPortrait%></td>
		<td><input id="id_input$app_user$HEAD_PORTRAIT_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.headPortrait%>"  type="text" name="app_user.headPortrait"
			value="${requestScope.s.headPortrait}">
		</td>
		<td><span id="id_span$app_user$HEAD_PORTRAIT_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.nickname%></td>
		<td><input id="id_input$app_user$NICKNAME_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.nickname%>"  type="text" name="app_user.nickname"
			value="${requestScope.s.nickname}">
		</td>
		<td><span id="id_span$app_user$NICKNAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.realname%></td>
		<td><input id="id_input$app_user$REALNAME_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.realname%>"  type="text" name="app_user.realname"
			value="${requestScope.s.realname}">
		</td>
		<td><span id="id_span$app_user$REALNAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.gender%></td>
		<td><input id="id_input$app_user$GENDER_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.gender%>"  type="text" name="app_user.gender"
			value="${requestScope.s.gender}">
		</td>
		<td><span id="id_span$app_user$GENDER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.age%></td>
		<td><input id="id_input$app_user$AGE_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.age%>"  type="text" name="app_user.age"
			value="${requestScope.s.age}">
		</td>
		<td><span id="id_span$app_user$AGE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.permissionGrade%></td>
		<td><input id="id_input$app_user$PERMISSION_GRADE_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.permissionGrade%>"  type="text" name="app_user.permissionGrade"
			value="${requestScope.s.permissionGrade}">
		</td>
		<td><span id="id_span$app_user$PERMISSION_GRADE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.sn%></td>
		<td><input id="id_input$app_user$SN_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.sn%>"  type="text" name="app_user.sn"
			value="${requestScope.s.sn}">
		</td>
		<td><span id="id_span$app_user$SN_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.loginTime%></td>
		<td><input id="id_input$app_user$LOGIN_TIME_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.loginTime%>"  type="text" name="app_user.loginTime"
			value="${requestScope.s.loginTime}">
<img
onclick=ayCalendarShow('id_input$app_user$LOGIN_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_user$LOGIN_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.loginTimeLong%></td>
		<td><input id="id_input$app_user$LOGIN_TIME_LONG_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.loginTimeLong%>"  type="text" name="app_user.loginTimeLong"
			value="${requestScope.s.loginTimeLong}">
		</td>
		<td><span id="id_span$app_user$LOGIN_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.state%></td>
		<td><input id="id_input$app_user$STATE_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.state%>"  type="text" name="app_user.state"
			value="${requestScope.s.state}">
		</td>
		<td><span id="id_span$app_user$STATE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.createUser%></td>
		<td><input id="id_input$app_user$CREATE_USER_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.createUser%>"  type="text" name="app_user.createUser"
			value="${requestScope.s.createUser}">
		</td>
		<td><span id="id_span$app_user$CREATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.createTime%></td>
		<td><input id="id_input$app_user$CREATE_TIME_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.createTime%>"  type="text" name="app_user.createTime"
			value="${requestScope.s.createTime}">
<img
onclick=ayCalendarShow('id_input$app_user$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_user$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.createTimeLong%></td>
		<td><input id="id_input$app_user$CREATE_TIME_LONG_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.createTimeLong%>"  type="text" name="app_user.createTimeLong"
			value="${requestScope.s.createTimeLong}">
		</td>
		<td><span id="id_span$app_user$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.updateUser%></td>
		<td><input id="id_input$app_user$UPDATE_USER_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.updateUser%>"  type="text" name="app_user.updateUser"
			value="${requestScope.s.updateUser}">
		</td>
		<td><span id="id_span$app_user$UPDATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.updateTime%></td>
		<td><input id="id_input$app_user$UPDATE_TIME_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.updateTime%>"  type="text" name="app_user.updateTime"
			value="${requestScope.s.updateTime}">
<img
onclick=ayCalendarShow('id_input$app_user$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_user$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.updateTimeLong%></td>
		<td><input id="id_input$app_user$UPDATE_TIME_LONG_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.updateTimeLong%>"  type="text" name="app_user.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">
		</td>
		<td><span id="id_span$app_user$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.tenantCode%></td>
		<td><input id="id_input$app_user$TENANT_CODE_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.tenantCode%>"  type="text" name="app_user.tenantCode"
			value="${requestScope.s.tenantCode}">
		</td>
		<td><span id="id_span$app_user$TENANT_CODE_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.tenantNumber%></td>
		<td><input id="id_input$app_user$TENANT_NUMBER_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.tenantNumber%>"  type="text" name="app_user.tenantNumber"
			value="${requestScope.s.tenantNumber}">
		</td>
		<td><span id="id_span$app_user$TENANT_NUMBER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.desc%></td>
		<td><input id="id_input$app_user$DESC_" placeholder="请输入<%=all.task.tms.project_user.app_user.alias.AppUserProjectUserAlias.desc%>"  type="text" name="app_user.desc"
			value="${requestScope.s.desc}">
		</td>
		<td><span id="id_span$app_user$DESC_"></span>
		</td>
</tr>	
 -->
			<tr>
				<td align="center" colspan="3"></td>
			</tr>
		</table>
	</form>
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="idBootstrapModal" tabindex="-1"
		role="dialog" aria-labelledby="idBootstrapModalLabel"
		aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title" id="idBootstrapModalLabel">用户列表</h4>
				</div>
				<div class="modal-body">
					<!-- 用户列表 -->
					<table id="IdJqGrid"></table>
					<div id="IdJqGridPager"></div>
<script type="text/javascript"> 
    //${pageContext.request.contextPath}/example/c/x/all/complex/table/jq_grid/sys_user/list/jsonp.do?callback=?
    //${pageContext.request.contextPath}/example/c/x/all/complex/table/jq_grid/sys_user/list/json.do
   	//${pageContext.request.contextPath}/example/c/x/all/complex/table/jq_grid/sys_menu/list/jsonp.do?callback=?
    $(document).ready(function() {
        $("#IdJqGrid").jqGrid({
            url: '${pageContext.request.contextPath}/all/app_admin/app/app_account/app_user_jqgrid/listJson.do',
            mtype: "GET",
            //styleUI : 'Bootstrap',
            height: 300,
            rowNum: 10,
            rowList:[10,20,30],
            pager: "#IdJqGridPager",
            viewrecords: true,
            //shrinkToFit:true,//此属性用来说明当初始化列宽度时候的计算类型，如果为ture，则按比例初始化列宽度。如果为false，则列宽度使用colModel指定的宽度
            rownumbers: true,  //是否显示行号
            multiselect: true,  //是否有多选功能
multiboxonly:true,
beforeSelectRow: beforeSelectRow,//js方法
            emptyrecords : "没有记录",
            datatype: "json",
            //datatype: "jsonp",
            colModel: [
            {
                label: '名称',
                name: 'APP_USER_NAME_',
                key: true,
                width: 300
            },
            {
                label: '主键',
                name: 'APP_USER_ID_',
                key: true,
                width: 300
  },   
            {
                label: '描述',
                name: 'DESC_',
                width: 300
            }
            ]
        });
    });
    function beforeSelectRow()
	{
    	$("#IdJqGrid").jqGrid('resetSelection');
	    return(true);
	}
</script>
					<!-- 用户列表 -->
				</div>
				<div class="modal-footer">
					<button id="idButton_select" type="button" class="btn btn-primary">
						选择</button>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<!-- /.modal -->
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">
	$("#idButton_select").click(function() {
		//var records = $("#IdJqGrid").jqGrid('getGridParam','records');
		//var selrow = $("#IdJqGrid").jqGrid('getGridParam','selrow');
		var rowid = $("#IdJqGrid").getGridParam("selrow");
		// alert('rowid='+rowid);
		var rowData = $("#IdJqGrid").getRowData(rowid); //根据行ID，获取选中行的数据
		//alert('APP_USER_ID_ ='+rowData.APP_USER_ID_);
		// alert('APP_USER_NAME_ ='+rowData.APP_USER_NAME_);
		$("#idInput_appAccount_appUserName").val(rowData.APP_USER_NAME_);
		$("#idInput_appAccount_appUserId").val(rowData.APP_USER_ID_);
		//alert('a');
		$('#idBootstrapModal').modal('hide');
	});
	/**
	 *上一级;
	 *不使用模态窗口 ;
	 */
	function parent(path) {
		//alert('path='+path);
		var url = "${pageContext.request.contextPath}/all/task/tms/project_user/tms_project/parent/bootstrap.do";
		url = url + "?path=" + path;
		var name = 'newwindow';
		ayFormOpenwindow(url, name, 800, 400);
		return false;
	}
	/**
	 *上一级;
	 *使用模态窗口 ;
	 */
	function parent_v1(path) {
		var url = "${pageContext.request.contextPath}/all/task/tms/project_user/tms_project/parent/bootstrap.do";
		url = url + "?path=" + path;
		var name = "n";
		var needRefresh = window
				.showModalDialog(url, name,
						'dialogHeight:400px;dialogWidth:450px;center:yes;help:no;status:no;scroll:yes');
		if (needRefresh == true) {
		}
		return false;
	}
	/**
	 * 
	 返回
	 */
	function back() {
		var url = "${pageContext.request.contextPath}/all/task/tms/project_user/app_user/list/bootstrap.do";
		var first$tree$id = document.getElementById("id_first$tree$id").value;
		url = url + "?first$tree$id=" + first$tree$id;
		window.location.href = url;
	}
	/**
	 * 
	 检查
	 */
	function check() {
		var a = "<font color=red>自定义信息</font>";
		a = null;
		var flag = true;
		var return_flag = true;
		if (false) {
			flag = ayCheckColumn("索引", "id_span$app_user$IDX_",
					"id_input$app_user$IDX_", "BIGINT", "yes", "19", "0", "0",
					a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("用户主账号MAIN_APP_ACCOUNT_ID_",
					"id_span$app_user$MAIN_APP_ACCOUNT_ID_",
					"id_input$app_user$MAIN_APP_ACCOUNT_ID_", "VARCHAR", "yes",
					"64", "0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("用户主键SYS_USER_ID_",
					"id_span$app_user$SYS_USER_ID_",
					"id_input$app_user$SYS_USER_ID_", "VARCHAR", "yes", "64",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("APP用户名称APP_USER_NAME_",
					"id_span$app_user$APP_USER_NAME_",
					"id_input$app_user$APP_USER_NAME_", "VARCHAR", "yes", "32",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("APP用户编码APP_USER_CODE_",
					"id_span$app_user$APP_USER_CODE_",
					"id_input$app_user$APP_USER_CODE_", "VARCHAR", "yes", "32",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("APP用户类型APP_USER_TYPE_",
					"id_span$app_user$APP_USER_TYPE_",
					"id_input$app_user$APP_USER_TYPE_", "CHAR", "yes", "16",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("相当于表名", "id_span$app_user$TABLE_NAME_",
					"id_input$app_user$TABLE_NAME_", "VARCHAR", "yes", "32",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("头像HEAD_PORTRAIT_",
					"id_span$app_user$HEAD_PORTRAIT_",
					"id_input$app_user$HEAD_PORTRAIT_", "VARCHAR", "yes",
					"256", "0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("昵称NICKNAME_", "id_span$app_user$NICKNAME_",
					"id_input$app_user$NICKNAME_", "VARCHAR", "yes", "64", "0",
					"0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("实名", "id_span$app_user$REALNAME_",
					"id_input$app_user$REALNAME_", "VARCHAR", "yes", "32", "0",
					"0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("性别", "id_span$app_user$GENDER_",
					"id_input$app_user$GENDER_", "CHAR", "yes", "8", "0", "0",
					a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("年龄", "id_span$app_user$AGE_",
					"id_input$app_user$AGE_", "INT", "yes", "10", "0", "0", a,
					true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("数据权限等级",
					"id_span$app_user$PERMISSION_GRADE_",
					"id_input$app_user$PERMISSION_GRADE_", "INT", "yes", "10",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("次序", "id_span$app_user$SN_",
					"id_input$app_user$SN_", "INT", "yes", "10", "0", "0", a,
					true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("登录时间LOGIN_TIME_",
					"id_span$app_user$LOGIN_TIME_",
					"id_input$app_user$LOGIN_TIME_", "TIMESTAMP", "no", "26",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("登录时间数字型LOGIN_TIME_LONG_",
					"id_span$app_user$LOGIN_TIME_LONG_",
					"id_input$app_user$LOGIN_TIME_LONG_", "BIGINT", "yes",
					"19", "0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用",
					"id_span$app_user$STATE_", "id_input$app_user$STATE_",
					"CHAR", "yes", "16", "0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("创建者CREATE_USER_",
					"id_span$app_user$CREATE_USER_",
					"id_input$app_user$CREATE_USER_", "VARCHAR", "yes", "64",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("创建时间", "id_span$app_user$CREATE_TIME_",
					"id_input$app_user$CREATE_TIME_", "TIMESTAMP", "no", "26",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("创建时间数字型CREATE_TIME_LONG_",
					"id_span$app_user$CREATE_TIME_LONG_",
					"id_input$app_user$CREATE_TIME_LONG_", "BIGINT", "yes",
					"19", "0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("更新者UPDATE_USER_",
					"id_span$app_user$UPDATE_USER_",
					"id_input$app_user$UPDATE_USER_", "VARCHAR", "yes", "64",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("更新时间", "id_span$app_user$UPDATE_TIME_",
					"id_input$app_user$UPDATE_TIME_", "TIMESTAMP", "no", "26",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("更新时间数字型UPDATE_TIME_LONG_",
					"id_span$app_user$UPDATE_TIME_LONG_",
					"id_input$app_user$UPDATE_TIME_LONG_", "BIGINT", "yes",
					"19", "0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("租户编码TENANT_CODE_",
					"id_span$app_user$TENANT_CODE_",
					"id_input$app_user$TENANT_CODE_", "CHAR", "yes", "32", "0",
					"0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("租户编号TENANT_NUMBER_",
					"id_span$app_user$TENANT_NUMBER_",
					"id_input$app_user$TENANT_NUMBER_", "INT", "yes", "10",
					"0", "0", a, true);
			if (flag) {
			} else {
				return_flag = false;
			}
			flag = ayCheckColumn("描述", "id_span$app_user$DESC_",
					"id_input$app_user$DESC_", "VARCHAR", "yes", "512", "0",
					"0", a, true);
		}

		if (flag) {
		} else {
			return_flag = false;
		}
		return return_flag;
	}
	/**
	 * 
	 保存
	 */
	function save() {
		var flag = check();
		if (flag) {
		} else {
			return false;
		}
		//提交
		var obj_form = document.getElementById('id_form_save');
		obj_form.submit();
	}
</script>
<script type="text/javascript">
	//初始化日期
</script>