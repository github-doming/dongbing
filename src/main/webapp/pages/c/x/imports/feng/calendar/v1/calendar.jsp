<!-- 
http://localhost:9080/a/pages/c/x/imports/feng/calendar/calendar.jsp
 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/imports/feng/js/ay_div.js?+Math.random()">
        </script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/calendar/calendar.js?+Math.random()">
        </script>
<style type="text/css">
/*
整个日期div样式
*/
.class_div {
	background: #f5f5f5;
	border-style: groove;
	position: absolute;
	z-index: 99;
}
/*
功能按钮样式
*/
.class_button {
	background-color: #2650A6;
	color: white;
	height: 16px;
	border: 0 solid #CCCCCC;
	font-size: 9pt;
	cursor: hand;
}
/*
年月选择的样式(跟v3一样)
*/
.class_span_year {
	background-color: #2650A6;
	color: white;
	font-size: 9pt;
	cursor: hand;
}
.class_span_year_v3 {
	background-color: #2650A6;
	color: white;
	font-size: 9pt;
	cursor: hand;
}
.class_span_year_v2 {
	color: #2650A6;
	font-size: 9pt;
	cursor: hand;
}
.class_span_year_v1 {
	background-color: #2650A6;
	color: #FFCC00;
	font-size: 9pt;
	cursor: hand;
}
</style>
<div onmouseout="ayDivOnmouseout(event,this);" id="id_div_calendar"
	style="display: none; left: 0px; top: 0px;" class="class_div">
<table border=0>
	<tr>
		<td class="class_span_year"><span title="上一年"
			onclick=ay$calendar$last_year();> <b> &lt;&lt; </b> </span> <span
			title="上一月" onclick=ayCalendarSelectHtmlMonth();>&lt;</span> <select
			onchange=ayCalendarSelectChangeYear(this); id="id_select_year">
			<option selected="selected">2012</option>
			<option>2013</option>
		</select> <select onchange=ayCalendarSelectChangeMonth(this);
			id="id_select_month">
			<option selected="selected">5</option>
			<option>6</option>
			<option>12</option>
		</select> <span title="下一月" onclick=ayCalendarNextMonth();>&gt;</span> <span
			title="下一年" onclick=ayCalendarNextYear();> <b>&gt;&gt;
		</b></span></td>
	</tr>
	<tr>
		<td>
		<div align="center" id="id_div_calendar_show_day"></div>
		</td>
	</tr>
	<tr>
		<td align="left">时间 <select
			onchange=ayCalendarSelectChangeHour(this); id="id_select_hour">
			<option>11</option>
			<option>12</option>
		</select> <select onchange=ayCalendarSelectChangeMinute(this);
			id="id_select_minute">
			<option>35</option>
			<option>36</option>
		</select> <select onchange=ayCalendarSelectChangeSecond(this);
			id="id_select_second">
			<option>15</option>
			<option>16</option>
			<option>17</option>
		</select></td>
	</tr>
	<tr>
		<td align="right"><input class="class_button" ; type=button
			value="今天"></input> <input onclick=" ayCalendarEnter();"
			class="class_button" ; type=button value="确定"></input></td>
	</tr>
</table>
</div>
<!-- 
<p></p>
<input id="id_input" type="text" value="a"></input>
<img onclick=ayCalendarShow('id_input',event); src="${pageContext.request.contextPath}/pages/c/x/includes/simple/calendar/images/calendar.gif"></img>
 -->
<script type="text/javascript">
function ayCalendarShow(id_element,event){
//输入框的id
	ayCalendarInputId=id_element;
    //1初始化 
    ayCalendarNow_init();
    //2弹出窗口
	//alert('event='+event);
	//alert('event.type='+event.type);
	var obj_element = document.getElementById(id_element);
	ayDivShowPop("id_div_calendar",obj_element);
}
</script>
