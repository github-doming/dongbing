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
<center>
	<div style="color:white;width:100%;height:100px;background-color:rgb(29,41,57);padding-top:20px">
		<span style="font-size:30px;">线程池管理</span>
	</div>
</center>
<form action=""  id="id_form_list" method="post">
<table id="topic_title" style="width:90%;table-layout:fixed; margin: 0px auto;"  id="id_table" class="table  table-bordered table-hover">
	<caption></caption>
	<thead>
		<tr>
			<th style="width: 10%;" >操作</th>
			<th>当前状态</th>
		</tr>
	</thead>
	<tbody>

<tr>
	<td>
		<a onclick="stateChange('START');" href="javascript:0" >开启</a>
		<a onclick="stateChange('STOP');" href="javascript:0" >关闭</a>
	</td>
	<td style="word-wrap : break-word;  " id="state">&nbsp;</td>
</tr>
	</tbody>
</table>
<br/>
</form>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/page/simple/page.js">
        </script>
<!-- 加载js -->
<script>
	
	
	function stateChange(state){
			var json = {
				'token':'4a97e2d6fa5b445c8ea1c57f89d1fb25',
				'cmd':state
			}
			$.ajax({
					 url:"<%=request.getContextPath()%>/ibm/pc/servlet/ibm_plan_statistics/planStatistics.dm",
					 dataType:"json",
					 data:{json: JSON.stringify(json)},
					 type:"post",
					 success:function(data){
					 	if(state=='START'){
					 		$('#state').text('开启');
					 	}else{
					 		$('#state').text('关闭');
					 	}
						
					 },
					 error:function(){
					 	alert("失败");
					 }
			});
		}
		

	
</script>
</html>