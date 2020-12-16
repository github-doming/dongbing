<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@page import="c.a.config.SysConfig"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>
<style type="text/css">
html,body,table {
	background-color: #f5f5f5;
	width: 100%;
}

.form-signin-heading {
	font-size: 36px;
	margin-bottom: 20px;
	color: #0663a2;
}
</style>
<style type="text/css">
.class_div {
	background: #f5f5f5;
	position: absolute;
	z-index: 99;
}

.class_div_1 {
	background: #f5f5f5;
	border-style: groove;
	position: absolute;
	z-index: 99;
}
</style>
<title></title>
</head>
<body>
	<div id="divAId"
		style="display: none; left: 0px; top: 0px; width: 200px; height: 200px;"
		class="class_div">
		槽线式边框1 槽线式边框2 槽线式边框3
		<p></p>
	</div>
	<!-- 4行3列 -->
	<table style="width: 100%;" border="0">
		<!-- top,logo的tr高度 3% -->
		<tr height="3%">
			<td colspan="3">
				<!-- table 1行多列 --> <!-- top的form表单 -->
				<table style="width: 100%;" border="0">
					<tr>
						<td>
							<!-- logo -->
							<h3 class="text-info"><%=SysConfig.title_main()%></h3>
						</td>
						<!-- logo -->
						<td>
							<!-- 功能搜索 --> <!-- 权限码 --> <permission:permission
								code="funSearchCode">
								<input onKeyUp=show(this,event);
									class="input-medium search-query" id="funSearchId"
									type="text"></input>
								<button class="btn" onclick="funSearch();">功能搜索</button>
							</permission:permission> <!-- 权限码 --> <!-- 功能搜索 -->
						</td>
						<td>
							<!-- 退出 -->
							<div class="dropdown">
								<a class="dropdown-toggle" data-toggle="dropdown"> 你好，
									${requestScope.current_user_name} <!-- 权限码 --> <permission:permission
										code="funCustomCode">
										<b class="caret"></b>
									</permission:permission> <!-- 权限码 -->
								</a>
								<!-- 权限码 -->
								<permission:permission code="funCustomCode">
									<ul class="dropdown-menu">
										<!-- links -->
										<li><a herf="#">个人信息</a></li>
										<li><a herf="#"> 修改密码</a></li>
										<li><a herf="#">我的面板设置</a></li>
										<!-- links -->
									</ul>
								</permission:permission>
								<!-- 权限码 -->
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
									href="${pageContext.request.contextPath}/platform/admin/logout.do">退出</a>
							</div> <!-- 退出 -->
						</td>
					</tr>
				</table> <!-- table 1行多列 -->
			</td>
		</tr>
		<!-- 1级菜单的tr高度 3% -->
		<tr height="3%">
			<td colspan="3">
				<!-- 1级菜单 --> <!-- 

 --> <%
 	java.util.ArrayList<java.util.HashMap<String, Object>> listMap=(java.util.ArrayList<java.util.HashMap<String, Object>>)	request.getAttribute("menuFirstMapList");
   			if(listMap!=null){
   				for(java.util.HashMap<String, Object> map:listMap){
   				String id=(String)map.get("SYS_MENU_ID_");
   				String liId="liId_"+id;
   				String name=(String)map.get("SYS_MENU_NAME_");
 %> <input id='<%=liId%>'
				onclick="funMenuFirstMapList('<%=id%>','<%=liId%>');"
				class="btn  btn-link" type="button" value="<%=name%>"></input> <%
 	}			
   			}
 %> <!-- 1级菜单 -->
			</td>
		</tr>
		<tr>
			<!-- 2级菜单的td  宽度25% -->
			<td id="treeTdId" width="25%" height="100%">
				<!-- 2级菜单 --> <IFRAME style="width: 100%;" id="treeIframeId"
					name="treeIframeName" src="" FRAMEborder="0" SCROLLING="auto"></IFRAME>
				<!-- 2级菜单 -->
			</td>
			<!-- 隐藏2级菜单的td  宽度1% -->
			<td width="1%">
				<!-- 隐藏2级菜单 --> <img onclick="funShowLeftTree();" id="midImgId"
				src="${pageContext.request.contextPath}/pages/c/x/platform/root/layout/middle/images/left.gif"></img>
				<!-- 隐藏2级菜单 -->
			</td>
			<td>
				<!-- 功能页面展示与home --> <IFRAME style="width: 100%;" id="homeIframeId"
					name="homeIframeName"
					src="${pageContext.request.contextPath}/pages/c/x/platform/root/layout/home/index.jsp"
					FRAMEborder="0" SCROLLING="auto"></IFRAME> <!-- 功能页面展示与home -->
			</td>
		</tr>
		<!-- 版权的tr高度 3% -->
		<tr height="3%">
			<td colspan="3">
				<!-- 版权说明 -->
				<table style="width: 100%;" border="0">
					<tr>
						<td style="text-align: center">
							<h5 class="text-info"><%=SysConfig.title_main()%></h5>
						</td>
					</tr>
				</table> <!-- 版权说明 -->
			</td>
		</tr>
	</table>
	<!-- 4行3列 -->
</body>
</html>
<script type="text/javascript">
	function iframeResize(id) {
		var w = window.screen.width; //弹出当前页面的宽度  
		var h = window.screen.height; //弹出当前页面的高度  
		var obj = document.getElementById(id); //取得父页面IFrame对象  
		var ua = navigator.userAgent.toLowerCase();
		var flag = false;
		if (ua.indexOf("firefox") > 0) {
			flag = true;
			if (w == 800 && h == 600) {
				obj.height = 206;
			} else {
				if (w == 1024 && h == 768) {
					obj.height = 370;
				} else {
					//调整IFrame的高度为此页面的高度  	
					obj.height = h / 10 * 7;
				}
			}
		}
		if (ua.indexOf("chrome") > 0) {
			//alert('ua=' + ua);
			flag = true;
			if (w == 800 && h == 600) {
				obj.height = 330;
				//obj.height =h/10*7;
			} else {
				if (w == 1024 && h == 768) {
					obj.height = 460;
				} else {
					//调整IFrame的高度为此页面的高度  	
					obj.height = h / 10 * 7;
				}
			}
		}
		if (ua.indexOf("msie") > 0) {
			flag = true;
			if (w == 800 && h == 600) {
				obj.height = 250;
			} else {
				if (w == 1024 && h == 768) {
					obj.height = 415;
				} else {
					//调整IFrame的高度为此页面的高度  	
					obj.height = h / 10 * 7;
				}
			}
		}
		if (!flag) {
			if (w == 800 && h == 600) {
				obj.height = 250;
			} else {
				if (w == 1024 && h == 768) {
					obj.height = 415;
				} else {
					//调整IFrame的高度为此页面的高度  	
					obj.height = h / 10 * 7;
				}
			}
		}
	}
	iframeResize("treeIframeId");
	iframeResize("homeIframeId");
</script>
<script type="text/javascript">
	/**
	 * 
	 功能搜索
	 */
	function funSearch() {

		var funSearchObj = document.getElementById("funSearchId");

		//打开home
		var iframeHomeObj = top.document.getElementById("homeIframeId");
		var url = 'http://www.baidu.com';

		iframeHomeObj.src = url;
	}
	/**
	 * 
	 显示左边菜单
	 */
	function funShowLeftTree() {
		var left = '${pageContext.request.contextPath}/pages/c/x/platform/root/layout/middle/images/left.gif';
		var midImgObj = document.getElementById("midImgId");
		var midImgObj_src = midImgObj.src;

		var midImgObj_index = midImgObj_src.indexOf(left);

		if (midImgObj_index >= 0) {

			//隐藏

			midImgObj.src = '${pageContext.request.contextPath}/pages/c/x/platform/root/layout/middle/images/right.gif';
			$("#treeTdId").hide();
		} else {
			//显示

			midImgObj.src = '${pageContext.request.contextPath}/pages/c/x/platform/root/layout/middle/images/left.gif';
			$("#treeTdId").show();
		}
	}
	/**
	 * 
	 加载所有的 2级菜单
	 */
	function funMenuFirstMapList(id, liId) {
		//设置css

		$("#" + liId).attr("class", "btn  btn-info");
		$("#" + liTempId).attr("class", "btn  btn-link");
		liTempId = liId;
		//加载第二级菜单
		var treeIframeObj = top.document.getElementById("treeIframeId");
		var url = '${pageContext.request.contextPath}/platform/admin/sys/tree_table.do?parent_id=' + id;

		treeIframeObj.src = url;
	}
	/**
	 *初始化 
	 */
	//设置第一级菜单css
	var liTempId = "liId_${requestScope.menu_id_00010001}";
	$("#" + liTempId).attr("class", "btn  btn-info");

	//加载第二级菜单
	var treeIframeObj = top.document.getElementById("treeIframeId");
	var url = '${pageContext.request.contextPath}/platform/admin/sys/tree_table.do?parent_id=${requestScope.menu_id_00010001}';
	treeIframeObj.src = url;
</script>
<script type="text/javascript">
	function show(obj_div, event) {
		ayDivShowPop("divAId", obj_div);
	}
</script>
