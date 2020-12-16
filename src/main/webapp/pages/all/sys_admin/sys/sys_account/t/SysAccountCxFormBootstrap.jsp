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
				<td><input readonly="readonly" id="idInput_sysAccount_sysUserName"
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
	action="${pageContext.request.contextPath}/all/admin/sys/sys_account/t/save.do"
	method="post">
<table class="table  table-bordered table-hover" border="1">
	
<tr style="display: none;">
		<td align="right"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias . sysAccountId%></td>
		<td><input id="id_input$sys_account$SYS_ACCOUNT_ID_" type="text" name="sys_account.sysAccountId"
			value="${requestScope.s.sysAccountId}"></td>
		<td><span id="id_span$sys_account$SYS_ACCOUNT_ID_"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias . sysUserId%></td>
		<td><input id="idInput_sysAccount_sysUserId" placeholder="请输入<%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias . sysUserId%>"  type="text" name="sys_account.sysUserId"
			value="${requestScope.s.sysUserId}">
		</td>
		<td><span id="id_span$sys_account$SYS_USER_ID_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias .sysAccountName%></td>
		<td><input id="id_input$sys_account$SYS_ACCOUNT_NAME_" placeholder="请输入<%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias .sysAccountName%>"  type="text" name="sys_account.sysAccountName"
			value="${requestScope.s.sysAccountName}">
		</td>
		<td><span id="id_span$sys_account$SYS_ACCOUNT_NAME_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias .password%></td>
		<td><input id="id_input$sys_account$PASSWORD_" placeholder="请输入<%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias .password%>"  type="text" name="sys_account.password"
			value="${requestScope.s.password}">
		</td>
		<td><span id="id_span$sys_account$PASSWORD_"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias . desc%></td>
		<td><input id="id_input$sys_account$DESC_" placeholder="请输入<%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias . desc%>"  type="text" name="sys_account.desc"
			value="${requestScope.s.desc}">
		</td>
		<td><span id="id_span$sys_account$DESC_"></span>
		</td>
</tr>	
<!-- 
<tr>
		<td align="right"><%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias . registerType%></td>
		<td><input id="id_input$sys_account$REGISTER_TYPE_" placeholder="请输入<%=all.sys_admin.sys.sys_account.t.alias.SysAccountTAlias . registerType%>"  type="text" name="sys_account.registerType"
			value="${requestScope.s.registerType}">
		</td>
		<td><span id="id_span$sys_account$REGISTER_TYPE_"></span>
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
            url: '${pageContext.request.contextPath}/all/sys_admin/sys/sys_account/sys_user_jqgrid/listJson.do',
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
                name: 'SYS_USER_NAME_',
                key: true,
                width: 300
            },
            {
                label: '主键',
                name: 'SYS_USER_ID_',
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
   //alert('SYS_USER_ID_ ='+rowData.SYS_USER_ID_);
	// alert('SYS_USER_NAME_ ='+rowData.SYS_USER_NAME_);
	 $("#idInput_sysAccount_sysUserName").val(rowData.SYS_USER_NAME_);
	 $("#idInput_sysAccount_sysUserId").val(rowData.SYS_USER_ID_);
	//alert('a');
	$('#idBootstrapModal').modal('hide');
});


/**
 * 
 返回
 */
function back(){
var url = "${pageContext.request.contextPath}/all/admin/sys/sys_account/t/list.do";
window.location.href=url;
}
/**
 * 
 检查
 */
function check(){
	var a="<font color=red>自定义信息</font>";
	a=null;
	var flag=true;
	var return_flag=true;
flag=ayCheckColumn("SYS_USER_ID_","id_span$sys_account$SYS_USER_ID_","idInput_sysAccount_sysUserId","VARCHAR","yes","255","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("用户名","id_span$sys_account$SYS_ACCOUNT_NAME_","id_input$sys_account$SYS_ACCOUNT_NAME_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("密码","id_span$sys_account$PASSWORD_","id_input$sys_account$PASSWORD_","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","id_span$sys_account$DESC_","id_input$sys_account$DESC_","VARCHAR","yes","1000","0","0",a,true);
if(flag){}else{return_flag=false;}
	return return_flag;
}
/**
 * 
 保存
 */
function save(){
var flag=check();
if(flag){
}else{
	return false;
}
	   //提交
	var obj_form= document.getElementById('id_form_save');
obj_form.submit();
 }
</script>
<script type="text/javascript">
//初始化日期
</script>