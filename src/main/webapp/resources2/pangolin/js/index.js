$(function() {
	//下面部分内容的高度样式
  	var winW=$(window).width();
  	var winH=$(window).height();
  	var bottonHeight=winH-90+"px";
  	$(".bottonContent").css("height",bottonHeight);
  	$(".leftNav").css("height",bottonHeight);
  	$(".iframeContent").css("height",bottonHeight);

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
  	});

  	//二级菜单选中样式
  	$(".leftpanelinner ul li").click(function(){
    	$(this).addClass("active");
    	$(this).siblings().removeClass("active");
  	});
})