<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<script type="text/javascript">
	
</script>
</head>
<body>
	<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>

	<form id="id_form_save"
		action="${pageContext.request.contextPath}/all/admin/sys/sys_user/t/save.do"
		method="post">
		<table class="table  table-bordered table-hover" border="1">
			<tr>
				<th colspan="3">用户 <c:choose>
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
				<td align="right">角色</td>
				<td>
					<%
						java.util.ArrayList<all.gen.sys_group.t.entity.SysGroupT> listMap = (java.util.ArrayList<all.gen.sys_group.t.entity.SysGroupT>) request
							.getAttribute("list_role");
							for (all.gen.sys_group.t.entity.SysGroupT info : listMap) {
					%> <input value="<%=info.getSysGroupId()%>" name="name_checkbox_role"
					id="id_checkbox_role" type="checkbox"></input> <%=info.getSysGroupName()%>
					<%
						}
					%>
				</td>
				<td></td>
			</tr>


			<tr style="display: none;">
				<td align="right"><%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . sysUserId%></td>
				<td><input id="id_input$sys_user$SYS_USER_ID_" type="text"
					name="sys_user.SYS_USER_ID_" value="${requestScope.s.sysUserId}"></td>
				<td><span id="id_span$sys_user$SYS_USER_ID_"></span></td>
			</tr>
		
			<tr>
				<td align="right"><%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . sysUserName%></td>
				<td><input id="id_input$sys_user$SYS_USER_NAME_"
					placeholder="请输入<%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . sysUserName%>"
					type="text" name="sys_user.SYS_USER_NAME_" value="${requestScope.s.sysUserName}">
				</td>
				<td><span id="id_span$sys_user$SYS_USER_NAME_"></span></td>
			</tr>
		<!-- 
			<tr>
				<td align="right"><%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . permissionGrade%></td>
				<td><input id="id_input$sys_user$PERMISSION_GRADE_"
					placeholder="请输入<%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . permissionGrade%>"
					type="text" name="sys_user.PERMISSION_GRADE_"
					value="${requestScope.s.permissionGrade}"></td>
				<td><span id="id_span$sys_user$PERMISSION_GRADE_"></span></td>
			</tr>
			
			 -->
			<tr>
				<td align="right"><%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . desc%></td>
				<td><input id="id_input$sys_user$DESC_"
					placeholder="请输入<%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . desc%>"
					type="text" name="sys_user.DESC_" value="${requestScope.s.desc}">
				</td>
				<td><span id="id_span$sys_user$DESC_"></span></td>
			</tr>
			
			
			<!-- 
			
		
			
			
			
			<tr>
				<td align="right"><%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . createTime%></td>
				<td><input id="id_input$sys_user$CREATE_TIME_"
					placeholder="请输入<%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . createTime%>"
					type="text" name="sys_user.CREATE_TIME_"
					value="${requestScope.s.createTime}"></td>
				<td><span id="id_span$sys_user$CREATE_TIME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . createTimeLong%></td>
				<td><input id="id_input$sys_user$CREATE_TIME_LONG_"
					placeholder="请输入<%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . createTimeLong%>"
					type="text" name="sys_user.CREATE_TIME_LONG_"
					value="${requestScope.s.createTimeLong}"></td>
				<td><span id="id_span$sys_user$CREATE_TIME_LONG_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . updateTime%></td>
				<td><input id="id_input$sys_user$UPDATE_TIME_"
					placeholder="请输入<%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . updateTime%>"
					type="text" name="sys_user.UPDATE_TIME_"
					value="${requestScope.s.updateTime}"></td>
				<td><span id="id_span$sys_user$UPDATE_TIME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . updateTimeLong%></td>
				<td><input id="id_input$sys_user$UPDATE_TIME_LONG_"
					placeholder="请输入<%=all.sys_admin.sys.sys_user.t.alias.SysUserCxAlias . updateTimeLong%>"
					type="text" name="sys_user.UPDATE_TIME_LONG_"
					value="${requestScope.s.updateTimeLong}"></td>
				<td><span id="id_span$sys_user$UPDATE_TIME_LONG_"></span></td>
			</tr>
			
			 -->
			<tr>
				<td align="center" colspan="3"></td>
			</tr>
		</table>
	</form>
</body>
</html>
<!-- 加载js -->
<script type="text/javascript">
	/**
	 * 
	 返回
	 */
	function back() {
		var url = "${pageContext.request.contextPath}/all/admin/sys/sys_user/t/list.do";
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
		//flag = ayCheckColumn("SYS_PERSON_ID_", "id_span$sys_user$SYS_PERSON_ID_", "id_input$sys_user$SYS_PERSON_ID_", "VARCHAR", "yes", "255", "0", "0", a, true);
		//if (flag) {
		//} else {
			//return_flag = false;
		//}
		flag = ayCheckColumn("用户名", "id_span$sys_user$SYS_USER_NAME_", "id_input$sys_user$SYS_USER_NAME_", "VARCHAR", "yes", "256", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		//flag = ayCheckColumn("密码", "id_span$sys_user$PWD_", "id_input$sys_user$PWD_", "VARCHAR", "yes", "256", "0", "0", a, true);
		//if (flag) {
		//} else {
			//return_flag = false;
		//}
		flag = ayCheckColumn("数据权限等级", "id_span$sys_user$PERMISSION_GRADE_", "id_input$sys_user$PERMISSION_GRADE_", "INT", "yes", "10", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("描述", "id_span$sys_user$DESC_", "id_input$sys_user$DESC_", "VARCHAR", "yes", "1024", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		//flag = ayCheckColumn("CREATE_TIME_", "id_span$sys_user$CREATE_TIME_", "id_input$sys_user$CREATE_TIME_", "DATETIME", "yes", "19", "0", "0", a, true);
		//if (flag) {
		//} else {
		//	return_flag = false;
		//}
		//flag = ayCheckColumn("CREATE_TIME_LONG_", "id_span$sys_user$CREATE_TIME_LONG_", "id_input$sys_user$CREATE_TIME_LONG_", "BIGINT", "yes", "19", "0", "0", a, true);
		//if (flag) {
		//} else {
		//	return_flag = false;
		//}
		//flag = ayCheckColumn("UPDATE_TIME_", "id_span$sys_user$UPDATE_TIME_", "id_input$sys_user$UPDATE_TIME_", "DATETIME", "yes", "19", "0", "0", a, true);
		//if (flag) {
		//} else {
		//	return_flag = false;
		//}
		//flag = ayCheckColumn("UPDATE_TIME_LONG_", "id_span$sys_user$UPDATE_TIME_LONG_", "id_input$sys_user$UPDATE_TIME_LONG_", "BIGINT", "yes", "19", "0", "0", a, true);
		//if (flag) {
		//} else {
		//	return_flag = false;
		//}
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



<script type="text/javascript">
/**
 * 加载页面时初始化
 */
//所有角色(所选择中的)
//{
 var   value_ids='${requestScope.ids_role}';
//alert(value_ids);
ayTableCheckboxEditLoadIds(value_ids,'name_checkbox_role');
//}
//所有角色(所选择中的)
</script>