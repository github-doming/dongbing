<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@page import="c.a.config.SysConfig"%>
<%@page import="all.ui.easyui.bean.EasyuiMenuBean"%>
<%@page import="java.util.List"%>
<!--导入标签-->
<!--记得import进标签 --><%@ taglib prefix="c"
	uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="zh-CN">
  <title></title>
  <meta charset="utf-8">
  <meta http-equiv = "X-UA-Compatible" content = "IE=edge,chrome=1" /> 
  <meta name="renderer" content="webkit" />
  

<%@ include file="/pages/c/x/platform/root/common/bootstrap/common.jsp"%>

<!-- 
 
  <script src="${pageContext.request.contextPath}/resources2/pangolin/js/jq.js" ></script>
  <script src="${pageContext.request.contextPath}/resources2/pangolin/js/bootstrap.min.js" ></script>
  <script src="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery/jquery-2.1.4.min.js?version=<%=version %>"></script>
  <script src="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap3/js/bootstrap.min.js?version=<%=version %>"></script>
 
-->
 

  
  <script src="${pageContext.request.contextPath}/resources2/pangolin/js/jquery.sparkline.min.js?version=<%=version %>" ></script>
  <script src="${pageContext.request.contextPath}/resources2/pangolin/js/toggles.min.js?version=<%=version %>" ></script>
  <script src="${pageContext.request.contextPath}/resources2/pangolin/js/custom.js?version=<%=version %>" ></script>
  <link href="${pageContext.request.contextPath}/resources2/pangolin/css/style.default.css?version=<%=version %>"  rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources2/pangolin/css/index2.css?version=<%=version %>"  rel="stylesheet">
  <link href="${pageContext.request.contextPath}/resources2/pangolin/css/modal.css?version=<%=version %>"  rel="stylesheet">
</head>
<style>
body{overflow-y:hidden;}
.ChineseName{font-size:20px;}
.header-title {
    float: left;
    font-size: 24px;
    line-height: 50px;
}
a, a:hover, a:link, a:visited, a:active {
    text-decoration: none;
}
li {
    display: list-item;
    text-align: -webkit-match-parent;
}
.iframes_two , .iframes_three{display:none;width:50%;float:left;}
</style>
<body>
	<div class="header">
	    <div class="productName">
	      <div class="productNameText">
	        <p class="ChineseName">&nbsp;&nbsp;<%=SysConfig.title_main()%></p>
	        <p class="EnglishName">&nbsp;&nbsp;&nbsp;</p>
	      </div>
	    </div>
	    <div class="headerRight">
	      <div class="userInfo headermenu">
	          <!-- <span><a id="getOrderList" href="#" style="color:red">您有<span id="count"></span>条信息待处理</a></span> -->
	          <div class="btn-group">
			      <button type="button" class="btn btn-default dropdown-toggle blurBtn" data-toggle="dropdown">
			         欢迎您,${requestScope.currentUserName}
			          
			          <img src="${pageContext.request.contextPath}/resources2/pangolin/images/photos/loggeduser.png" alt="" />
			          <span></span>
			          <span class="caret"></span>
			      </button>
			      <ul class="dropdown-menu dropdown-menu-usermenu pull-right infoContent">
			          <!-- 
			          <li><a href="javascript:;" class="personalData" target="main"><i class="glyphicon glyphicon-user"></i>个人资料</a></li>
			          <li><a href="javascript:;"  class="passwordSetting" target="main"><i class="glyphicon glyphicon-cog"></i>密码设定</a></li>
			          -->
			          <li><a href="${pageContext.request.contextPath}/platform/admin/logout.do"><i class="glyphicon glyphicon-log-out"></i>系统退出</a></li>
			      <li><a href="${pageContext.request.contextPath}/platform/admin/index.do" target="help"><i class="glyphicon glyphicon-question-sign"></i>系统首页</a></li>
			       <li><a href="javascript:help();" target="help"><i class="glyphicon glyphicon-question-sign"></i>系统帮助</a></li>
			      </ul>
		      </div>
	      </div>
	    </div>
    </div>
    <div class="topNav">
	    <div class="navBtn">
	      <a class="menutoggle"><i class="fa fa-bars"></i></a>
	    </div>
	    <ul class="topNavUl">
	      <!-- １级菜单 -->
	       <%
List<EasyuiMenuBean> menuList=(List<EasyuiMenuBean>)request.getAttribute("menuList");
if(menuList!=null){
	for(EasyuiMenuBean menuFirst:menuList){
   		String id=menuFirst.getId();
		String name=menuFirst.getText();
%>
	       <li>
		       <a href="" name="<%=id%>">
		           <i class="icons iconsBg4"></i><span><%=name%></span>
		       </a>
	       </li>
 <%}	}%> 
	    </ul>
	</div>
	<div class="bottonContent">
	    <div class="leftpanel">
	      <div class="leftpanelinner">
  <!-- １级菜单 -->
<%
if(menuList!=null){
	for(EasyuiMenuBean menuFirst:menuList){
		String id=menuFirst.getId();
		String name=menuFirst.getText();
		String url=menuFirst.getUrl();
		List<EasyuiMenuBean> menuSecondList=menuFirst.getChildren();
 %>	      
	          <ul class="nav nav-pills nav-stacked nav-bracket <%=id%>">                
		       <!-- 2级菜单 -->
	 <%
		for(EasyuiMenuBean menuSecond:menuSecondList){
			List<EasyuiMenuBean> menuThirdList=menuSecond.getChildren();
			int size=menuThirdList.size();
			String menuSecondUrl=menuSecond.getUrl();
			String menuSecondName=menuSecond.getText();
			if(size==0){
	%>
		      <li class="nav-parent"  >
		             <a href="#"  data-one="<%=menuSecondUrl%>">
		                 <i class="fa fa-suitcase"></i> 
		                 <span><%=menuSecondName%></span>
		             </a>
		        </li>
		      <%  }else{ %>     
		         <li class="nav-parent">
		             <a href="">
		                 <i class="fa fa-suitcase"></i> 
		                 <span><%=menuSecondName%></span>
		             </a>
		             <ul class="children">
 <!-- 3级菜单 -->         
<%
		for(EasyuiMenuBean menuThird:menuThirdList){
			String menuThirdUrl=menuThird.getUrl();
			String menuThirdName=menuThird.getText();
%>
	<li>
	<a href="#"  data-one="<%=menuThirdUrl%>">
	<i class="fa fa-caret-right"></i>
	<%=menuThirdName%>
 	</a>
	</li> 
  <%} %>    		                                           
		              </ul>
		         </li>
	<% }}%>   				 
	          </ul>
<%}	}%> 
	      </div>
	    </div>
	    <div class="mainpanel" >
	      <!-- Content -->
	      <iframe class="iframes iframes_one" src="" frameborder="0" name="main"></iframe>
	      <iframe class="iframes iframes_two" src="" frameborder="0" name="main"></iframe>
	      <iframe class="iframes iframes_three" src="" frameborder="0" name="main"></iframe>
	    </div>
	</div>
	<script type="text/javascript">
	$(function(){
		activateModule(); //打开顶部第一个模块
		  winheight();//设置自适应高度
	})
	function activateModule() {
		try {
			if ( $(".topNavUl").children("li").length > 0 ) {
				$($($(".topNavUl").children("li")[0]).children("a")[0]).click();
			}
		} catch(e) { console.log(e); }
	}
	//禁止a标签刷新、跳转
	$(".topNav li a").attr("href","javascript:;");
	$(".leftpanelinner ul li a").attr("href","javascript:;");
	//一级菜单的切换及其下级菜单的显示
	$(".topNavUl li a").click(function(){
	    $(this).parent().addClass("active01");
	    $(this).parent().siblings().removeClass("active01");
	    var nameUl=$(this).attr("name");
	    $(".leftpanelinner ."+nameUl).show();
	    $(".leftpanelinner ."+nameUl).find("li:first-child").addClass("active");
	    $(".leftpanelinner ."+nameUl).siblings().hide();
	    $(".leftpanelinner ."+nameUl).siblings().find("li").removeClass("active");
	  //  $("."+nameUl+">li:first-child>a").click();
	});
	//二级菜单选中样式
	$(".leftpanelinner ul li").click(function(){
	    $(this).addClass("active");
	    $(this).siblings().removeClass("active");
	});
	//点击菜单显示对应的页面
	$(".nav li a").click(function(){
		var _this = $(this);
		var one = _this.attr('data-one');
		var two = _this.attr('data-two');
		if(two){		//判断有没有两个地址，如果有就打开两个页面
			$(".iframes_one").attr('src','');
			$(".iframes_one").hide();
			$(".iframes_two").attr('src',one);
			$(".iframes_three").attr('src',two);
			$(".iframes_two ,.iframes_three").show();	
		}else{
			//alert("one="+one);
			$(".iframes_one").attr('src',one);
			$(".iframes_one").show();
			$(".iframes_two , .iframes_three").attr('src','');
			$(".iframes_two , .iframes_three").hide();	
		}
		//$(".iframes").attr('src',url);
	});
	function winheight(){
		//下面部分内容的高度样式
		var winH=$(window).height();
		var bottomHeight=winH-92+"px";
		$(".bottonContent, .mainpanel, .iframes").css("height",bottomHeight);
		$(".iframes_two , .iframes_three").hide();		
	}
	</script>
	<script type="text/javascript">
function help(){
	alert("请联系我们的客服");
}
	</script>
</body>
</html>