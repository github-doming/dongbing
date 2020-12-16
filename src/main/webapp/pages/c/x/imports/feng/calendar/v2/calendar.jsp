<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" 
"http://www.w3.org/TR/html4/strict.dtd">


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
<div id="id_div_calendar" style="display: none; left: 0px; top: 0px;"
	class="class_div">
<table border=0>
	<tr>
		<td class="class_span_year"><span title="上一年"
			onclick=ay$calendar$last_year();> <b> &lt;&lt; </b> </span> <span
			title="上一月" onclick=ayCalendarSelectHtmlMonth();>&lt;</span> <select
			style="width: 70px;" onchange=ayCalendarSelectChangeYear(this);
			id="id_select_year">
	
		</select> <select style="width: 58px;"
			onchange=ayCalendarSelectChangeMonth(this); id="id_select_month">
	
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
		<td align="left">时间 <select style="width: 58px;"
			onchange=ayCalendarSelectChangeHour(this); id="id_select_hour">
	
		</select> <select style="width: 58px;"
			onchange=ayCalendarSelectChangeMinute(this);
			id="id_select_minute">
		
		</select> <select style="width: 58px;"
			onchange=ayCalendarSelectChangeSecond(this);
			id="id_select_second">
	
		</select></td>
	</tr>
	<tr>
		<td align="right"><input onclick=" ayCalendarToday();"
			class="class_button" ; type=button value="今天"></input> <input
			onclick=" ayCalendarEnter();" class="class_button" ; type=button
			value="确定"></input></td>
	</tr>
</table>
</div>
<!-- 
<p></p>
<input id="id_input" type="text" value="a"></input>
<img onclick=ayCalendarShow('id_input',event); src="${pageContext.request.contextPath}/pages/c/x/includes/simple/calendar/images/calendar.gif"></img>
 -->
<script type="text/javascript">
function ayCalendarShow_v1(id_element,event){
	try
    {
    } catch (e) {
		alert(e.stack);
    }
//输入框的id
	ayCalendarInputId=id_element;
    //1初始化 
    ayCalendarNow_init();
    //2弹出窗口
	//alert('event='+event);
	//alert('event.type='+event.type);
	var obj_element = document.getElementById(id_element);
	ayDivShowPop("id_div_calendar",obj_element);


	//给input赋值(现在的时间)

	// input输入框设置值
	ayCalendarSetValue();;
}
</script>
<script type="text/javascript">
/**
 * 点击div以外的元素 隐藏这个div
 */
window.onload = function() {
	document.onclick = function(e) {
		var obj_element = e ? e.target : window.event.srcElement;
		//节点名称
		alert('节点名称='+obj_element.nodeName) ;
		alert(obj_element.nodeType);
				if(obj_element.nodeName=='BODY'||obj_element.nodeName=='HTML') {
					if(obj_element.id != 'id_div_calendar') {
						document.getElementById('id_div_calendar').style.display = 'none';
					}
				}
	};
};
</script>
