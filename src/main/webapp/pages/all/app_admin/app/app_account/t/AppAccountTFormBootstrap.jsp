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
				<th colspan="3">账号 <c:choose>
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
					<p class="text-right">用户：</p>
				</td>
				<td><input readonly="readonly" id="idInput_appAccount_appUserName"
					class="input-medium search-query"  type="text"
					name="" value="">
			<!-- 按钮触发模态框 -->
<button class="btn btn-primary" data-toggle="modal" data-target="#idBootstrapModal">
绑定用户
</button>
					</td>
				<td width="30%"></td>
</tr>

	</table>
	
	
	<form id="id_form_save"
		action="${pageContext.request.contextPath}/all/admin/app/app_account/t/save/bootstrap.do"
		method="post">
		<table class="table  table-bordered table-hover" border="1">
			

<tr style="display: none;">
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.appAccountId%></td>
				<td><input id="id_input$app_account$ACCOUNT_ID_" 
					type="text" name="app_account.appAccountId"
					value="${requestScope.s.appAccountId}"></td>
				<td><span id="id_span$app_account$APP_ACCOUNT_ID_"></span></td>
			</tr>


<tr style="display: none;">
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.appUserId%></td>
		<td><input id="idInput_appAccount_appUserId" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.appUserId%>"  type="text" name="app_account.appUserId"
			value="${requestScope.s.appUserId}">
		</td>
		<td><span id="id_span$app_account$APP_USER_ID_"></span>
		</td>
</tr>	

			<tr>
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.accountName%></td>
				<td><input id="id_input$app_account$ACCOUNT_NAME_"
					placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias . accountName%>"
					type="text" name="app_account.accountName"
					value="${requestScope.s.accountName}"></td>
				<td><span id="id_span$app_account$ACCOUNT_NAME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.password%></td>
				<td><input id="id_input$app_account$PASSWORD_"
					placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias . password%>"
					type="text" name="app_account.password"
					value="${requestScope.s.password}"></td>
				<td><span id="id_span$app_account$PASSWORD_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.registerType%></td>
				<td><input id="id_input$app_account$REGISTER_TYPE_"
					placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias . registerType%>"
					type="text" name="app_account.registerType"
					value="${requestScope.s.registerType}"></td>
				<td><span id="id_span$app_account$REGISTER_TYPE_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.state%></td>
				<td><input id="id_input$app_account$STATE_"
					placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias . state%>"
					type="text" name="app_account.state"
					value="${requestScope.s.state}"></td>
				<td><span id="id_span$app_account$STATE_"></span></td>
			</tr>
			<!-- 
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.idx%></td>
		<td><input id="id_input$app_account$IDX_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.idx%>"  type="text" name="app_account.idx"
			value="${requestScope.s.idx}">
		</td>
		<td><span id="id_span$app_account$IDX_"></span>
		</td>
</tr>	


<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createUser%></td>
		<td><input id="id_input$app_account$CREATE_USER_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createUser%>"  type="text" name="app_account.createUser"
			value="${requestScope.s.createUser}">
		</td>
		<td><span id="id_span$app_account$CREATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createTime%></td>
		<td><input id="id_input$app_account$CREATE_TIME_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createTime%>"  type="text" name="app_account.createTime"
			value="${requestScope.s.createTime}">
<img
onclick=ayCalendarShow('id_input$app_account$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_account$CREATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createTimeLong%></td>
		<td><input id="id_input$app_account$CREATE_TIME_LONG_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.createTimeLong%>"  type="text" name="app_account.createTimeLong"
			value="${requestScope.s.createTimeLong}">
		</td>
		<td><span id="id_span$app_account$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateUser%></td>
		<td><input id="id_input$app_account$UPDATE_USER_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateUser%>"  type="text" name="app_account.updateUser"
			value="${requestScope.s.updateUser}">
		</td>
		<td><span id="id_span$app_account$UPDATE_USER_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateTime%></td>
		<td><input id="id_input$app_account$UPDATE_TIME_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateTime%>"  type="text" name="app_account.updateTime"
			value="${requestScope.s.updateTime}">
<img
onclick=ayCalendarShow('id_input$app_account$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_account$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateTimeLong%></td>
		<td><input id="id_input$app_account$UPDATE_TIME_LONG_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.updateTimeLong%>"  type="text" name="app_account.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">
		</td>
		<td><span id="id_span$app_account$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.desc%></td>
		<td><input id="id_input$app_account$DESC_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.desc%>"  type="text" name="app_account.desc"
			value="${requestScope.s.desc}">
		</td>
		<td><span id="id_span$app_account$DESC_"></span>
		</td>
</tr>		
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.loginTime%></td>
		<td><input id="id_input$app_account$LOGIN_TIME_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.loginTime%>"  type="text" name="app_account.loginTime"
			value="${requestScope.s.loginTime}">
<img
onclick=ayCalendarShow('id_input$app_account$LOGIN_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$app_account$LOGIN_TIME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.loginTimeLong%></td>
		<td><input id="id_input$app_account$LOGIN_TIME_LONG_" placeholder="请输入<%=all.app_admin.app.app_account.t.alias.AppAccountTAlias.loginTimeLong%>"  type="text" name="app_account.loginTimeLong"
			value="${requestScope.s.loginTimeLong}">
		</td>
		<td><span id="id_span$app_account$LOGIN_TIME_LONG_"></span>
		</td>
</tr>	
 -->
			<tr>
				<td align="center" colspan="3"></td>
			</tr>
		</table>
	</form>
<!-- 模态框（Modal） -->
<div class="modal fade" id="idBootstrapModal" tabindex="-1" role="dialog" 
   aria-labelledby="idBootstrapModalLabel" aria-hidden="true">
   <div class="modal-dialog modal-lg">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" 
               aria-hidden="true">×
            </button>
            <h4 class="modal-title" id="idBootstrapModalLabel">
             用户列表
            </h4>
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
            <button id="idButton_select"type="button" class="btn btn-primary">
                选择
            </button>
            <button type="button" class="btn btn-default" 
               data-dismiss="modal">关闭
            </button>
         </div>
      </div><!-- /.modal-content -->
   </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>
<!-- 加载js -->
<script type="text/javascript">

$("#idButton_select").click(function(){
	//var records = $("#IdJqGrid").jqGrid('getGridParam','records');
	//var selrow = $("#IdJqGrid").jqGrid('getGridParam','selrow');
	var rowid= $("#IdJqGrid").getGridParam("selrow");
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
	 * 
	 返回
	 */
	function back() {
		var url = "${pageContext.request.contextPath}/all/admin/app/app_account/t/list/bootstrap.do";
		window.location.href = url;
	}
	/**
	 * 
	 检查
	 */
	function check() {
		var a = "<font color=red>自定义信息</font>";
		a = null;
		var flag = null;
		var return_flag = true;
		
		
		flag = ayCheckColumn("账号名称ACCOUNT_NAME_",
				"id_span$app_account$ACCOUNT_NAME_",
				"id_input$app_account$ACCOUNT_NAME_", "VARCHAR", "yes", "256",
				"0", "0", a, true);
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