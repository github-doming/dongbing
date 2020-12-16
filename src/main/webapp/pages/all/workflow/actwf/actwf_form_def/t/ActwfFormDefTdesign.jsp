<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
   <script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/editor/ueditor/ueditor.config2.js?v=2"></script>
<script type="text/javascript" charset="utf-8"
	src="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/editor/ueditor/ueditor.all2.js?v=2"></script>
   <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/editor/ueditor/lang/zh-cn/zh-cn2.js?v=2"></script>
    <script src="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/editor/ueditor/dyui_form_design2/design_plugin.js?v=2"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/pages/cosmos/lib/all/third/editor/ueditor/dyui_form_design2/css/toolbars.css?v=1">
    <style type="text/css">
        div{
            width:95%;
        }
    </style>
</head>
<body>
<%@ include file="/pages/c/x/imports/feng/calendar/calendar.jsp"%>
<form id="id_form_save"
	action="${pageContext.request.contextPath}/all/workflow/actwf/actwf_form_def/t/save/bootstrap.do"
	method="post">
<table class="table  table-bordered table-hover" border="1">
	<tr>
		<th colspan="3">菜单 <c:choose>
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
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . actwfFormDefId%></td>
		<td><input id="idInput_ActwfFormDefT_actwfFormDefId" type="text" name="actwf_form_def.actwfFormDefId"
			value="${requestScope.s.actwfFormDefId}"></td>
		<td><span id="idSpan_ActwfFormDefT_actwfFormDefId"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . name%></td>
		<td><input disabled="disabled"  id="idInput_ActwfFormDefT_name" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . name%>"  type="text" name="actwf_form_def.name"
			value="${requestScope.s.name}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_name"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . title%></td>
		<td><input disabled="disabled" id="idInput_ActwfFormDefT_title" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . title%>"  type="text" name="actwf_form_def.title"
			value="${requestScope.s.title}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_title"></span>
		</td>
</tr>	
<tr>
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . content%></td>
		<td>
  <script  id="myEditor" name="actwf_form_def.content" type="text/plain">
       ${requestScope.s.content}
    </script>
		</td>
		<td><span id="idSpan_ActwfFormDefT_content"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . key%></td>
		<td><input id="idInput_ActwfFormDefT_key" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . key%>"  type="text" name="actwf_form_def.key"
			value="${requestScope.s.key}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_key"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . tableName%></td>
		<td><input id="idInput_ActwfFormDefT_tableName" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . tableName%>"  type="text" name="actwf_form_def.tableName"
			value="${requestScope.s.tableName}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_tableName"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . edition%></td>
		<td><input id="idInput_ActwfFormDefT_edition" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . edition%>"  type="text" name="actwf_form_def.edition"
			value="${requestScope.s.edition}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_edition"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . type%></td>
		<td><input id="idInput_ActwfFormDefT_type" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . type%>"  type="text" name="actwf_form_def.type"
			value="${requestScope.s.type}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_type"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlView%></td>
		<td><input id="idInput_ActwfFormDefT_urlView" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlView%>"  type="text" name="actwf_form_def.urlView"
			value="${requestScope.s.urlView}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_urlView"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlUpload%></td>
		<td><input id="idInput_ActwfFormDefT_urlUpload" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlUpload%>"  type="text" name="actwf_form_def.urlUpload"
			value="${requestScope.s.urlUpload}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_urlUpload"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlDownload%></td>
		<td><input id="idInput_ActwfFormDefT_urlDownload" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . urlDownload%>"  type="text" name="actwf_form_def.urlDownload"
			value="${requestScope.s.urlDownload}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_urlDownload"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . sn%></td>
		<td><input id="idInput_ActwfFormDefT_sn" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . sn%>"  type="text" name="actwf_form_def.sn"
			value="${requestScope.s.sn}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_sn"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . state%></td>
		<td><input id="idInput_ActwfFormDefT_state" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . state%>"  type="text" name="actwf_form_def.state"
			value="${requestScope.s.state}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_state"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . desc%></td>
		<td><input id="idInput_ActwfFormDefT_desc" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . desc%>"  type="text" name="actwf_form_def.desc"
			value="${requestScope.s.desc}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_desc"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createUser%></td>
		<td><input id="idInput_ActwfFormDefT_createUser" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createUser%>"  type="text" name="actwf_form_def.createUser"
			value="${requestScope.s.createUser}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_createUser"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createTime%></td>
		<td><input id="idInput_ActwfFormDefT_createTime" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createTime%>"  type="text" name="actwf_form_def.createTime"
			value="${requestScope.s.createTime}">
<img
onclick=ayCalendarShow('idInput_ActwfFormDefT_createTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_ActwfFormDefT_createTime"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createTimeLong%></td>
		<td><input id="idInput_ActwfFormDefT_createTimeLong" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . createTimeLong%>"  type="text" name="actwf_form_def.createTimeLong"
			value="${requestScope.s.createTimeLong}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_createTimeLong"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateUser%></td>
		<td><input id="idInput_ActwfFormDefT_updateUser" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateUser%>"  type="text" name="actwf_form_def.updateUser"
			value="${requestScope.s.updateUser}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_updateUser"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateTime%></td>
		<td><input id="idInput_ActwfFormDefT_updateTime" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateTime%>"  type="text" name="actwf_form_def.updateTime"
			value="${requestScope.s.updateTime}">
<img
onclick=ayCalendarShow('idInput_ActwfFormDefT_updateTime',event,'TIMESTAMP'); src="${pageContext.request.contextPath}/pages/c/x/imports/feng/calendar/images/calendar.gif"></img>
		</td>
		<td><span id="idSpan_ActwfFormDefT_updateTime"></span>
		</td>
</tr>	
<tr style="display: none;">
		<td align="right"><%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateTimeLong%></td>
		<td><input id="idInput_ActwfFormDefT_updateTimeLong" placeholder="请输入<%=all.workflow.actwf.actwf_form_def.t.alias.ActwfFormDefTAlias . updateTimeLong%>"  type="text" name="actwf_form_def.updateTimeLong"
			value="${requestScope.s.updateTimeLong}">
		</td>
		<td><span id="idSpan_ActwfFormDefT_updateTimeLong"></span>
		</td>
</tr>	
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
function back(){
var url = "${pageContext.request.contextPath}/all/workflow/actwf/actwf_form_def/t/list/bootstrap.do";
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
	if(false){
flag=ayCheckColumn("英文标识KEY_","idSpan_ActwfFormDefT_key","idInput_ActwfFormDefT_key","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("名称","idSpan_ActwfFormDefT_name","idInput_ActwfFormDefT_name","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("相当于表名","idSpan_ActwfFormDefT_tableName","idInput_ActwfFormDefT_tableName","VARCHAR","yes","32","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("标题TITLE_","idSpan_ActwfFormDefT_title","idInput_ActwfFormDefT_title","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("显示版本EDITION_","idSpan_ActwfFormDefT_edition","idInput_ActwfFormDefT_edition","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("类型","idSpan_ActwfFormDefT_type","idInput_ActwfFormDefT_type","CHAR","yes","16","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("查看地址URL_VIEW_","idSpan_ActwfFormDefT_urlView","idInput_ActwfFormDefT_urlView","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("上传地址URL_UPLOAD_","idSpan_ActwfFormDefT_urlUpload","idInput_ActwfFormDefT_urlUpload","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("下载地址URL_DOWNLOAD_","idSpan_ActwfFormDefT_urlDownload","idInput_ActwfFormDefT_urlDownload","VARCHAR","yes","256","0","0",a,true);
if(flag){}else{return_flag=false;}
	flag=ayCheckColumn("内容CONTENT_","idSpan_ActwfFormDefT_content","idInput_ActwfFormDefT_content","TEXT","yes","65535","0","0",a,true);
	if(flag){}else{return_flag=false;}	
flag=ayCheckColumn("创建者CREATE_USER_","idSpan_ActwfFormDefT_createUser","idInput_ActwfFormDefT_createUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_ActwfFormDefT_createTime","idInput_ActwfFormDefT_createTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("创建时间","idSpan_ActwfFormDefT_createTimeLong","idInput_ActwfFormDefT_createTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新者UPDATE_USER_","idSpan_ActwfFormDefT_updateUser","idInput_ActwfFormDefT_updateUser","VARCHAR","yes","64","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_ActwfFormDefT_updateTime","idInput_ActwfFormDefT_updateTime","TIMESTAMP","no","26","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("更新时间","idSpan_ActwfFormDefT_updateTimeLong","idInput_ActwfFormDefT_updateTimeLong","BIGINT","yes","19","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("次序","idSpan_ActwfFormDefT_sn","idInput_ActwfFormDefT_sn","INT","yes","10","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("DEL逻辑删除，OPEN打开或激活，CLOSE关闭或禁用","idSpan_ActwfFormDefT_state","idInput_ActwfFormDefT_state","CHAR","yes","8","0","0",a,true);
if(flag){}else{return_flag=false;}
flag=ayCheckColumn("描述","idSpan_ActwfFormDefT_desc","idInput_ActwfFormDefT_desc","VARCHAR","yes","512","0","0",a,true);
if(flag){}else{return_flag=false;}
}
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
    <script type="text/javascript">
        UE.getEditor('myEditor',{
            //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
            toolbars:[['preview','FullScreen', 'Source', 'Undo', 'Redo','Bold','test','dyui_textbox']],
            //focus时自动清空初始化时的内容
            autoClearinitialContent:true,
            //关闭字数统计
            wordCount:false,
            //关闭elementPath
            elementPathEnabled:false,
            //默认的编辑区域高度
            initialFrameHeight:300
            //更多其他参数，请参考ueditor.config.js中的配置项
        })
    </script>