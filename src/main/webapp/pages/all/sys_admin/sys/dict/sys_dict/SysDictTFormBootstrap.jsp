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
		action="${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict/save/bootstrap.do"
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
			<tr>
				<th colspan="3">数据字典 <c:choose>
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
			<tr style="display: none;">
				<td>
					<p class="text-right">上一级数据字典id：</p>
				</td>
				<td><input id="id_input_parent_menu_id" type="text"
					name="sys_dict_tree.parent" value="${requestScope.p.sysDictTreeId}"></td>
				<td></td>
			</tr>
			<tr>
				<td>
					<p class="text-right">上一级名称：</p>
				</td>
				<td><input id="id_input_parent_menu_name"
					class="input-medium search-query" readonly="readonly" type="text"
					name="" value="${requestScope.p.sysDictTreeName}"> <input
					onclick="parent('${requestScope.p.path}');" class="btn"
					type="button" value="选择"></td>
				<td width="30%"></td>
			</tr>
			<tr style="display: none;">
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.idx%></td>
				<td><input id="id_input$sys_dict$IDX_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . idx%>"
					type="text" name="sys_dict.idx" value="${requestScope.s.idx}">
				</td>
				<td><span id="id_span$sys_dict$IDX_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sysDictId%></td>
				<td><input  id="id_input$sys_dict$SYS_DICT_ID_" type="text"
					name="sys_dict.sysDictId" value="${requestScope.s.sysDictId}"></td>
				<td><span id="id_span$sys_dict$SYS_DICT_ID_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sysDictName%></td>
				<td><input id="id_input$sys_dict$SYS_DICT_NAME_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . sysDictName%>"
					type="text" name="sys_dict.sysDictName"
					value="${requestScope.s.sysDictName}"></td>
				<td><span id="id_span$sys_dict$SYS_DICT_NAME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sysDictCode%></td>
				<td><input id="id_input$sys_dict$SYS_DICT_CODE_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . sysDictCode%>"
					type="text" name="sys_dict.sysDictCode"
					value="${requestScope.s.sysDictCode}"></td>
				<td><span id="id_span$sys_dict$SYS_DICT_CODE_"></span></td>
			</tr>
			<tr style="display: none;">
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableName%></td>
				<td><input id="id_input$sys_dict$TABLE_NAME_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . tableName%>"
					type="text" name="sys_dict.tableName"
					value="${requestScope.s.tableName}"></td>
				<td><span id="id_span$sys_dict$TABLE_NAME_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableKey%></td>
				<td><input id="id_input$sys_dict$TABLE_KEY_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . tableKey%>"
					type="text" name="sys_dict.tableKey"
					value="${requestScope.s.tableKey}"></td>
				<td><span id="id_span$sys_dict$TABLE_KEY_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableValue%></td>
				<td><input id="id_input$sys_dict$TABLE_VALUE_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . tableValue%>"
					type="text" name="sys_dict.tableValue"
					value="${requestScope.s.tableValue}"></td>
				<td><span id="id_span$sys_dict$TABLE_VALUE_"></span></td>
			</tr>
			<tr style="display: none;">
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.sn%></td>
				<td><input id="id_input$sys_dict$SN_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . sn%>"
					type="text" name="sys_dict.sn" value="${requestScope.s.sn}">
				</td>
				<td><span id="id_span$sys_dict$SN_"></span></td>
			</tr>
			
			<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.valueCn%></td>
				<td><input id="id_input$sys_dict$VALUE_CN_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . valueCn%>"
					type="text" name="sys_dict.valueCn"
					value="${requestScope.s.valueCn}"></td>
				<td><span id="id_span$sys_dict$VALUE_CN_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.def%></td>
				<td><input id="id_input$sys_dict$DEF_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . def%>"
					type="text" name="sys_dict.def" value="${requestScope.s.def}">
				</td>
				<td><span id="id_span$sys_dict$DEF_"></span></td>
			</tr>
			<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.show%></td>
				<td><input id="id_input$sys_dict$SHOW_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . show%>"
					type="text" name="sys_dict.show" value="${requestScope.s.show}">
				</td>
				<td><span id="id_span$sys_dict$SHOW_"></span></td>
			</tr>
			
			<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.desc%></td>
				<td><input id="id_input$sys_dict$DESC_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . desc%>"
					type="text" name="sys_dict.desc" value="${requestScope.s.desc}">
				</td>
				<td><span id="id_span$sys_dict$DESC_"></span></td>
			</tr>
			<!-- 
			

<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.keyCn%></td>
				<td><input id="id_input$sys_dict$KEY_CN_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . keyCn%>"
					type="text" name="sys_dict.keyCn" value="${requestScope.s.keyCn}">
				</td>
				<td><span id="id_span$sys_dict$KEY_CN_"></span></td>
			</tr>
			
						
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableId%></td>
		<td><input id="id_input$sys_dict$TABLE_ID_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableId%>"  type="text" name="sys_dict.tableId"
			value="${requestScope.s.tableId}">
		</td>
		<td><span id="id_span$sys_dict$TABLE_ID_"></span>
		</td>
</tr>	
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableColumn%></td>
		<td><input id="id_input$sys_dict$TABLE_COLUMN_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.tableColumn%>"  type="text" name="sys_dict.tableColumn"
			value="${requestScope.s.tableColumn}">
		</td>
		<td><span id="id_span$sys_dict$TABLE_COLUMN_"></span>
		</td>
</tr>	
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.dataType%></td>
		<td><input id="id_input$sys_dict$DATA_TYPE_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.dataType%>"  type="text" name="sys_dict.dataType"
			value="${requestScope.s.dataType}">
		</td>
		<td><span id="id_span$sys_dict$DATA_TYPE_"></span>
		</td>
</tr>	
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.columnType%></td>
		<td><input id="id_input$sys_dict$COLUMN_TYPE_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.columnType%>"  type="text" name="sys_dict.columnType"
			value="${requestScope.s.columnType}">
		</td>
		<td><span id="id_span$sys_dict$COLUMN_TYPE_"></span>
		</td>
</tr>	
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.columnAssort%></td>
		<td><input id="id_input$sys_dict$COLUMN_ASSORT_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.columnAssort%>"  type="text" name="sys_dict.columnAssort"
			value="${requestScope.s.columnAssort}">
		</td>
		<td><span id="id_span$sys_dict$COLUMN_ASSORT_"></span>
		</td>
</tr>
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createUser%></td>
		<td><input id="id_input$sys_dict$CREATE_USER_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createUser%>"  type="text" name="sys_dict.createUser"
			value="${requestScope.s.createUser}">
		</td>
		<td><span id="id_span$sys_dict$CREATE_USER_"></span>
		</td>
</tr>	
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createTime%></td>
		<td><input id="id_input$sys_dict$CREATE_TIME_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createTime%>"  type="text" name="sys_dict.createTime"
			value="${requestScope.s.createTime}">
<img
onclick=ayCalendarShow('id_input$sys_dict$CREATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$sys_dict$CREATE_TIME_"></span>
		</td>
</tr>	
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createTimeLong%></td>
		<td><input id="id_input$sys_dict$CREATE_TIME_LONG_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.createTimeLong%>"  type="text" name="sys_dict.createTimeLong"
			value="${requestScope.s.createTimeLong}">
		</td>
		<td><span id="id_span$sys_dict$CREATE_TIME_LONG_"></span>
		</td>
</tr>	
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateUser%></td>
		<td><input id="id_input$sys_dict$UPDATE_USER_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateUser%>"  type="text" name="sys_dict.updateUser"
			value="${requestScope.s.updateUser}">
		</td>
		<td><span id="id_span$sys_dict$UPDATE_USER_"></span>
		</td>
</tr>	
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateTime%></td>
		<td><input id="id_input$sys_dict$UPDATE_TIME_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateTime%>"  type="text" name="sys_dict.updateTime"
			value="${requestScope.s.updateTime}">
<img
onclick=ayCalendarShow('id_input$sys_dict$UPDATE_TIME_',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="id_span$sys_dict$UPDATE_TIME_"></span>
		</td>
</tr>	
<tr style="display: none;" >
		<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateTimeLong%></td>
		<td><input id="id_input$sys_dict$UPDATE_TIME_LONG_" placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.updateTimeLong%>"  type="text" name="sys_dict.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">
		</td>
		<td><span id="id_span$sys_dict$UPDATE_TIME_LONG_"></span>
		</td>
</tr>	


<tr>
				<td align="right"><%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias.state%></td>
				<td><input id="id_input$sys_dict$STATE_"
					placeholder="请输入<%=all.sys_admin.sys.dict.sys_dict.alias.SysDictTAlias . state%>"
					type="text" name="sys_dict.state" value="${requestScope.s.state}">
				</td>
				<td><span id="id_span$sys_dict$STATE_"></span></td>
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
	 *上一级;
	 *不使用模态窗口 ;
	 */
	function parent(path) {
		//alert('path='+path);
		var url = "${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict_tree/parent/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict_tree/parent/bootstrap.do";
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
		var url = "${pageContext.request.contextPath}/all/sys_admin/sys/dict/sys_dict/list/bootstrap.do";
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
		var flag = null;
		var return_flag = true;
		//flag=ayCheckColumn("索引","id_span$sys_dict$IDX_","id_input$sys_dict$IDX_","BIGINT","yes","19","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		flag = ayCheckColumn("后台数据字典表名称SYS_DICT_NAME_",
				"id_span$sys_dict$SYS_DICT_NAME_",
				"id_input$sys_dict$SYS_DICT_NAME_", "VARCHAR", "no", "32", "0",
				"0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("后台数据字典表编码SYS_DICT_CODE_",
				"id_span$sys_dict$SYS_DICT_CODE_",
				"id_input$sys_dict$SYS_DICT_CODE_", "VARCHAR", "yes", "32",
				"0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		//flag=ayCheckColumn("相当于表名","id_span$sys_dict$TABLE_NAME_","id_input$sys_dict$TABLE_NAME_","VARCHAR","yes","32","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("相当于表的ID","id_span$sys_dict$TABLE_ID_","id_input$sys_dict$TABLE_ID_","VARCHAR","yes","64","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("相当于表的列名","id_span$sys_dict$TABLE_COLUMN_","id_input$sys_dict$TABLE_COLUMN_","VARCHAR","yes","32","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("表名+表的ID+表的列，用点号来分割如jdbc.local.dburl=jdbc:mysql://39.108.124.247:3306/wsd","id_span$sys_dict$TABLE_KEY_","id_input$sys_dict$TABLE_KEY_","VARCHAR","yes","64","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		flag = ayCheckColumn("表的VALUE_", "id_span$sys_dict$TABLE_VALUE_",
				"id_input$sys_dict$TABLE_VALUE_", "VARCHAR", "yes", "1024",
				"0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		//flag=ayCheckColumn("虚拟表的数据类型DATA_TYPE_","id_span$sys_dict$DATA_TYPE_","id_input$sys_dict$DATA_TYPE_","VARCHAR","yes","32","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("虚拟表的列类型COLUMN_TYPE_","id_span$sys_dict$COLUMN_TYPE_","id_input$sys_dict$COLUMN_TYPE_","VARCHAR","yes","32","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("虚拟表的列的分类COLUMN_ASSORT_","id_span$sys_dict$COLUMN_ASSORT_","id_input$sys_dict$COLUMN_ASSORT_","VARCHAR","yes","32","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		flag = ayCheckColumn("次序", "id_span$sys_dict$SN_",
				"id_input$sys_dict$SN_", "INT", "yes", "10", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		//flag = ayCheckColumn("中文标识KEY_CN_", "id_span$sys_dict$KEY_CN_","id_input$sys_dict$KEY_CN_", "VARCHAR", "yes", "64", "0", "0",a, true);
		//if (flag) {} else {return_flag = false;}
		flag = ayCheckColumn("中文值VALUE_CN_", "id_span$sys_dict$VALUE_CN_",
				"id_input$sys_dict$VALUE_CN_", "VARCHAR", "yes", "1024", "0",
				"0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("是否默认值", "id_span$sys_dict$DEF_",
				"id_input$sys_dict$DEF_", "CHAR", "yes", "8", "0", "0", a, true);
		if (flag) {
		} else {
			return_flag = false;
		}
		flag = ayCheckColumn("是否显示SHOW_", "id_span$sys_dict$SHOW_",
				"id_input$sys_dict$SHOW_", "CHAR", "yes", "8", "0", "0", a,
				true);
		if (flag) {
		} else {
			return_flag = false;
		}
		//flag=ayCheckColumn("创建者CREATE_USER_","id_span$sys_dict$CREATE_USER_","id_input$sys_dict$CREATE_USER_","VARCHAR","yes","64","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("创建时间","id_span$sys_dict$CREATE_TIME_","id_input$sys_dict$CREATE_TIME_","TIMESTAMP","no","26","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("创建时间","id_span$sys_dict$CREATE_TIME_LONG_","id_input$sys_dict$CREATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("更新者UPDATE_USER_","id_span$sys_dict$UPDATE_USER_","id_input$sys_dict$UPDATE_USER_","VARCHAR","yes","64","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("更新时间","id_span$sys_dict$UPDATE_TIME_","id_input$sys_dict$UPDATE_TIME_","TIMESTAMP","no","26","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("更新时间","id_span$sys_dict$UPDATE_TIME_LONG_","id_input$sys_dict$UPDATE_TIME_LONG_","BIGINT","yes","19","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		//flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","id_span$sys_dict$STATE_","id_input$sys_dict$STATE_","CHAR","yes","8","0","0",a,true);
		//if(flag){}else{return_flag=false;}
		flag = ayCheckColumn("描述", "id_span$sys_dict$DESC_",
				"id_input$sys_dict$DESC_", "VARCHAR", "yes", "512", "0", "0",
				a, true);
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