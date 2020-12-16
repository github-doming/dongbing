<!-- 
gen_version=${gen_version} 
 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<style type="text/css">
/*=======================================*/ /*阻塞id_block*/ /*{*/
.by_alert_block {
	background-color: #999;
	/*--  background-color: #FFFFFF;--*/ /*--定义透明度,支持ie--*/
	filter: alpha(opacity =   80, Style =   0);
	/*--定义透明度,支持firefox--*/
	opacity: 0.8;
	width: 100%;
	height: 100%;
	left: 0px;
	top: 0px;
	POSITION: fixed;
}
/*}*/ /*阻塞id_block*/
</style>
<style type="text/css">
.by_alert_pop {
	position: absolute;
	left: 30px;
	top: 30px;
	padding: 5px;
	background: #f0f3f9;
	font-size: 12px;
	-moz-box-shadow: 2px 2px 4px #666666;
	-webkit-box-shadow: 2px 2px 4px #666666;
}

.by_alert_title {
	line-height: 24px;
	background: #beceeb;
	border-bottom: 1px solid #a0b3d6;
	padding-left: 5px;
	cursor: move;
}
</style>
<div id="block_id" class="by_alert_block" style="display: none;">
	<!--<div   style="position: absolute; left: 10%; top: 10%; z-index: 99;">-->
	<div style="position: absolute; left: 1%; top: 1%; z-index: 99;">
		<div id="pop_id" class='by_alert_pop'
			style="width: 1100px; height: 500px;">
			<div id="title_id" class="by_alert_title">
				<SPAN>新消息</SPAN>
			</div>
			<div>
				<INPUT onclick="ay$alert$onclick_block_hide('submit','block_id');"
					type="button" value="确定" /> <INPUT
					onclick="ay$alert$onclick_block_hide('esc','block_id');"
					type="button" value="取消" />
			</div>
			<div>吃饭</div>
		</div>
	</div>
</div>
<script
	src="${pageContext.request.contextPath}/pages/example/c/x/all/feng/compo/byui/drag/by_drag.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var oBox = document.getElementById("pop_id");
	var oBar = document.getElementById("title_id");
	by_drag_start(oBar, oBox);
</script>
