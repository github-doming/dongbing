// 1声明变量
// 日期格式
var ayCalendarFormat = null;
// 输入框的id
var ayCalendarInputId = null;
// 2声明变量
// 定义星期几
var array_week_name = new Array("日", "一", "二", "三", "四", "五", "六");
// 定义阳历中每个月的最大天数
var month_day_size = new Array(12);
month_day_size[0] = 31;
month_day_size[1] = 28;
month_day_size[2] = 31;
month_day_size[3] = 30;
month_day_size[4] = 31;
month_day_size[5] = 30;
month_day_size[6] = 31;
month_day_size[7] = 31;
month_day_size[8] = 30;
month_day_size[9] = 31;
month_day_size[10] = 30;
month_day_size[11] = 31;
// 3声明对象
var ayCalendarObjDate = null;
// 现在的第几年
var ayCalendarNowYear = null;
// 选择的第几年
var ayCalendarSelectYear = null;
// 现在的第几月
var ayCalendarNowMonth = null;
// 选择的第几月
var ayCalendarSelectMonth = null;
// 现在在一月中处于第几天
var ayCalendarNowDay = null;
// 选择的第几天
var ayCalendarSelectDay = null;
// 时
var ayCalendarNowHour = null;
var ayCalendarSelectHour = null;
// 分
var ayCalendarNowMinute = null;
var ayCalendarSelectMinute = null;
// 秒
var ayCalendarNowSecond = null;
var ayCalendarSelectSecond = null;
// 现在在一周中处于第几天
var ayCalendarNowWeekday = null;
var ayCalendarSelectWeekDay = null;
/**
 * 样式
 * 
 */
// {
// 显示日的td的样式
var dayTdClass = "width:21px;height:20px;background-color:#D8F0FC;font-size:10pt;";
// }
/**
 * 样式
 * 
 */
/**
 * 显示下一月
 */
function ayCalendarNextMonth() {
	ayCalendarSelectMonth = ayCalendarSelectMonth + 1;
	if (ayCalendarSelectMonth > 12) {
		ayCalendarSelectMonth = 1;
		ayCalendarSelectYear = ayCalendarSelectYear + 1;
	}
	// 重置天数
	ayCalendarReset();
	// 显示下拉框的年
	ayCalendarSelectHtmlYear(ayCalendarSelectYear);
	// 显示下拉框的月
	ayCalendarSelectHtmlMonth(ayCalendarSelectMonth);
	// input输入框设置值
	ayCalendarSetValue();
}
/**
 * 显示上一月
 */
function ayCalendarSelectHtmlMonth() {
	ayCalendarSelectMonth = ayCalendarSelectMonth - 1;
	if (ayCalendarSelectMonth < 1) {
		ayCalendarSelectMonth = 12;
		ayCalendarSelectYear = ayCalendarSelectYear - 1;
	}
	// 重置天数
	ayCalendarReset();
	// 显示下拉框的年
	ayCalendarSelectHtmlYear(ayCalendarSelectYear);
	// 显示下拉框的月
	ayCalendarSelectHtmlMonth(ayCalendarSelectMonth);
	// input输入框设置值
	ayCalendarSetValue();
}
/**
 * 显示下一年
 */
function ayCalendarNextYear() {
	ayCalendarSelectYear = ayCalendarSelectYear + 1;
	// 重置天数
	ayCalendarReset();
	// 显示下拉框的年
	ayCalendarSelectHtmlYear(ayCalendarSelectYear);
	// input输入框设置值
	ayCalendarSetValue();
}
/**
 * 显示上一年
 */
function ay$calendar$last_year() {
	ayCalendarSelectYear = ayCalendarSelectYear - 1;
	// 重置天数
	ayCalendarReset();
	// 显示下拉框的年
	ayCalendarSelectHtmlYear(ayCalendarSelectYear);
	// input输入框设置值
	ayCalendarSetValue();
}
/*
 * 
 * 显示下拉框的年
 */
function ayCalendarSelectHtmlYear(now_year) {
	// 下拉框,显示年
	var yearSelectObj = document.getElementById("id_select_year");
	// alert( 'html='+yearSelectObj.innerHTML);
	
	// 适应ie
	// 删除所有选项option
	yearSelectObj.options.length = 0;
	for (var i = 1970; i <= 2030; i++) {
		var elementObj = document.createElement('option');
		// 赋值
		elementObj.value = i;
		elementObj.text = i;
		if (i == now_year) {
			elementObj.selected = 'selected';
		}
		yearSelectObj.add(elementObj, yearSelectObj.options[null]);
	}
	// 适应ie
}
/**
 * 
 * 改变下拉框的年
 * 
 * @param {}
 *            yearSelectObj
 */
function ayCalendarSelectChangeYear(yearSelectObj) {
	// 序号，取当前选中选项的序号
	var index = yearSelectObj.selectedIndex;
	ayCalendarSelectYear = yearSelectObj.options[index].value;
	// 重置天数
	ayCalendarReset();
	// input输入框设置值
	ayCalendarSetValue();;
}
/*
 * 
 * 显示下拉框的月
 */
function ayCalendarSelectHtmlMonth(now_month) {
	// 显示月
	var MonthSelectObj = document.getElementById("id_select_month");
	// 适应ie
	// 删除所有选项option
	MonthSelectObj.options.length = 0;
	for (var i = 1; i <= 12; i++) {
		var elementObj = document.createElement('option');
		elementObj.value = i;
		elementObj.text = i;
		if (i == now_month) {
			elementObj.selected = 'selected';
		}
		MonthSelectObj.add(elementObj, MonthSelectObj.options[null]);
	}
	// 适应ie
}
/**
 * 
 * 改变下拉框的月
 * 
 * @param {}
 *            MonthSelectObj
 */
function ayCalendarSelectChangeMonth(MonthSelectObj) {
	// 序号，取当前选中选项的序号
	var index = MonthSelectObj.selectedIndex;
	// alert(yearSelectObj.options[index].value);
	ayCalendarSelectMonth = MonthSelectObj.options[index].value;
	// 重置天数
	ayCalendarReset();
	// input输入框设置值
	ayCalendarSetValue();;
}
/*
 * 
 * 显示下拉框的时
 */
function ayCalendarHourSelectHtml(now_hour) {
	// 显示时
	var obj_select_hour = document.getElementById("id_select_hour");
	// 适应ie
	// 删除所有选项option
	obj_select_hour.options.length = 0;
	for (var i = 0; i <= 24; i++) {
		var elementObj = document.createElement('option');
		elementObj.value = i;
		elementObj.text = i;
		if (i == now_hour) {
			elementObj.selected = 'selected';
		}
		obj_select_hour.add(elementObj, obj_select_hour.options[null]);
	}
	// 适应ie
}
/**
 * 改变下拉框的时
 * 
 * @param {}
 *            obj_select_hour
 */
function ayCalendarSelectChangeHour(obj_select_hour) {
	// 序号，取当前选中选项的序号
	var index = obj_select_hour.selectedIndex;
	ayCalendarSelectHour = obj_select_hour.options[index].value;
	// 重置天数
	ayCalendarReset();
	// input输入框设置值
	ayCalendarSetValue();;
}
/*
 * 
 * 显示下拉框的分
 */
function ayCalendarMinuteSelectHtml(now_minute) {
	// 显示分
	var obj_select_minute = document.getElementById("id_select_minute");
	// 适应ie
	// 删除所有选项option
	obj_select_minute.options.length = 0;
	for (var i = 0; i <= 60; i++) {
		var elementObj = document.createElement('option');
		elementObj.value = i;
		elementObj.text = i;
		if (i == now_minute) {
			elementObj.selected = 'selected';
		}
		obj_select_minute.add(elementObj, obj_select_minute.options[null]);
	}
	// 适应ie
}
/**
 * 改变下拉框的分
 * 
 * @param {}
 *            obj_select_minute
 */
function ayCalendarSelectChangeMinute(obj_select_minute) {
	// 序号，取当前选中选项的序号
	var index = obj_select_minute.selectedIndex;
	ayCalendarSelectMinute = obj_select_minute.options[index].value;
	// 重置天数
	ayCalendarReset();
	// input输入框设置值
	ayCalendarSetValue();;
}
/*
 * 
 * 显示下拉框的秒
 */
function ayCalendarSecondSelectHtml(now_second) {
	// 显示秒
	var secondSelectObj = document.getElementById("id_select_second");
	// 适应ie
	// 删除所有选项option
	//alert('secondSelectObj='+secondSelectObj);
	if(secondSelectObj==null){
		return ;
	}
	secondSelectObj.options.length = 0;
	for (var i = 0; i <= 60; i++) {
		var elementObj = document.createElement('option');
		elementObj.value = i;
		elementObj.text = i;
		if (i == now_second) {
			elementObj.selected = 'selected';
		}
		secondSelectObj.add(elementObj, secondSelectObj.options[null]);
	}
	// 适应ie
}
/**
 * 改变下拉框的秒
 * 
 * @param {}
 *            secondSelectObj
 */
function ayCalendarSelectChangeSecond(secondSelectObj) {
	// 序号，取当前选中选项的序号
	var index = secondSelectObj.selectedIndex;
	ayCalendarSelectSecond = secondSelectObj.options[index].value;
	// 重置天数
	ayCalendarReset();
	// input输入框设置值
	ayCalendarSetValue();;
}
/**
 * 
 * 3显示日期(天数) (重置天数)
 */
function ayCalendarReset() {
	var html = ayCalendarDayHtml(ayCalendarSelectYear,
			ayCalendarSelectMonth, ayCalendarSelectDay);
	var obj_div = document.getElementById("id_div_calendar_show_day");
	obj_div.innerHTML = html;
}
/**
 * 2显示现在的日期;
 * 
 * 初始化;
 */
function ayCalendarNowInitiate() {
	// 1初始化
	// 声明对象
	ayCalendarObjDate = new Date();
	// 现在的第几年
	ayCalendarNowYear = ayCalendarObjDate.getFullYear();
	// 选择的第几年
	ayCalendarSelectYear = ayCalendarNowYear;
	// 现在的第几月
	ayCalendarNowMonth = ayCalendarObjDate.getMonth() + 1;
	// 选择的第几月
	ayCalendarSelectMonth = ayCalendarNowMonth;
	// 现在在一月中处于第几天
	ayCalendarNowDay = ayCalendarObjDate.getDate();
	// 选择的第几天
	ayCalendarSelectDay = ayCalendarNowDay;
	// 时
	ayCalendarNowHour = ayCalendarObjDate.getHours();
	ayCalendarSelectHour = ayCalendarNowHour;
	// 分
	ayCalendarNowMinute = ayCalendarObjDate.getMinutes();
	ayCalendarSelectMinute = ayCalendarNowMinute;
	// 秒
	ayCalendarNowSecond = ayCalendarObjDate.getSeconds();
	ayCalendarSelectSecond = ayCalendarNowSecond;
	// 现在在一周中处于第几天
	ayCalendarNowWeekday = ayCalendarObjDate.getDay();
	ayCalendarSelectWeekDay = ayCalendarNowWeekday;
	// 2初始化下拉框的值
	// 显示下拉框的年
	ayCalendarSelectHtmlYear(ayCalendarSelectYear);
	// 显示下拉框的月
	ayCalendarSelectHtmlMonth(ayCalendarSelectMonth);
	// 显示下拉框的时分秒
	ayCalendarHourSelectHtml(ayCalendarSelectHour);
	ayCalendarMinuteSelectHtml(ayCalendarSelectMinute);
	ayCalendarSecondSelectHtml(ayCalendarSelectSecond);
	// 3
	// 5显示现在的日期
	ayCalendarReset();
}
/**
 * 1显示日期(天数)
 * 
 * @param {}
 *            input_year
 * @param {}
 *            monthInput
 * @param {}
 *            input_day
 */
function ayCalendarDayHtml(input_year, monthInput, input_day) {
	monthInput = monthInput - 1;
	// 每个月的第一天
	var obj_first_date = new Date();
	obj_first_date.setFullYear(input_year, monthInput, 1);
	// 在一周中处于第几天
	var first_week_day = obj_first_date.getDay();
	// alert('每个月的第一天 在一周中处于第几天 day='+first_week_day);
	// 每个月的最后一天
	var obj_last_date = new Date();
	obj_last_date.setFullYear(input_year, monthInput,
			month_day_size[monthInput]);
	// 在一周中处于第几天
	var last_week_day = obj_last_date.getDay();
	// alert('每个月的最后一天 在一周中处于第几天 day='+last_week_day);
	var html = "<table  border=0>";
	/**
	 * 
	 * 第一行显示周名称
	 */
	// {
	html = html + "<tr>";
	for (var week = 1; week <= 7; week++) {
		html = html + "<td>";
		html = html + array_week_name[week - 1];
		html = html + "</td>"
	}
	html = html + "</tr>";
	// }
	/**
	 * 
	 * 第一行显示周名称
	 */
	// 第二行开始显示天名称
	var name_day = 1;
	for (var i = 1; i <= 6; i++) {
		html = html + "<tr>";
		for (var j = 1; j <= 7; j++) {
			// 每个月的第一天
			if (i == 1 && j < (first_week_day + 1)) {
				html = html + "<td>";
			html = html + "<span>&nbsp;</span>";
				html = html + "</td>";
				continue;
			}
			// 每个月的最后一天
			if (i == 6 && j > (last_week_day + 1)) {
				html = html + "<td>";
			html = html + "<span>&nbsp;</span>";
				html = html + "</td>";
				continue;
			}
			if (name_day <= 31) {
				// 如果是今天，加粗
				if (name_day == ayCalendarNowDay) {
					html = html + "<td >";
					html = html + "<b>";
					html = html + "<span  alt='" + name_day
							+ "' onclick='ayCalendarDayOnclick(this);'>";
					html = html + name_day;
					html = html + "</span>";
					html = html + "</b>";
					html = html + "</td>";
					// 第二行开始显示 天名称
					name_day = name_day + 1;
				} else {
					html = html + "<td style='" + dayTdClass + "'>";
					html = html + "<span   alt='" + name_day
							+ "' onclick='ayCalendarDayOnclick(this);'>";
					html = html + name_day;
					html = html + "</span>";
					html = html + "</td>";
					// 第二行开始显示天名称
					name_day = name_day + 1;
				}
			} else {
				html = html + "<td>";
				html = html + "<span>&nbsp;</span>";
				html = html + "</td>"
			}
		}
		html = html + "</tr>";
	}
	html = html + "</table>";
	// document.write(html);
	return html;
}
// 临时td的day
var ayCalendarDayTdTemp = null;
/**
 * td 天的事件
 * 
 * @param {}
 *            dayObj
 */
function ayCalendarDayOnclick(dayObj) {
	// 1
	// 改变颜色
	if (ayCalendarDayTdTemp != null) {
		ayCalendarDayTdTemp.style.color = "";
	}
	ayCalendarDayTdTemp = dayObj;
	dayObj.style.color = "#2650A6";
	// 2
	// 现在的天
	// alert(dayObj.innerHTML);
	ayCalendarSelectDay = dayObj.innerHTML;
	// input输入框设置值
	ayCalendarSetValue();;
}
/**
 * input输入框设置值
 * 
 */
function ayCalendarSetValue(){
	try {
		if (false) {
			alert('ayCalendarSelectYear=' + ayCalendarSelectYear);
			alert('ayCalendarSelectMonth =' + ayCalendarSelectMonth);
			alert('ayCalendarSelectDay =' + ayCalendarSelectDay);
			alert('ayCalendarSelectHour=' + ayCalendarSelectHour);
			alert('ayCalendarSelectMinute =' + ayCalendarSelectMinute);
			alert('ayCalendarSelectSecond =' + ayCalendarSelectSecond);
		}
		var elementObj = document.getElementById(ayCalendarInputId);
		// 时间格式 2013-07-19 11:25:37
		// 月数补0
		var ayCalendarSelectMonth$html = null;
		if (ayCalendarSelectMonth < 10) {
			ayCalendarSelectMonth$html = "0" + ayCalendarSelectMonth;
		} else {
			ayCalendarSelectMonth$html = "" + ayCalendarSelectMonth;
		}
		// 天数补0
		var ayCalendarSelectDay$html = null;
		if (ayCalendarSelectDay < 10) {
			ayCalendarSelectDay$html = "0" + ayCalendarSelectDay;
		} else {
			ayCalendarSelectDay$html = "" + ayCalendarSelectDay;
		}
		// 时数补0
		var ayCalendarSelectHour$html = null;
		if (ayCalendarSelectHour < 10) {
			ayCalendarSelectHour$html = "0" + ayCalendarSelectHour;
		} else {
			ayCalendarSelectHour$html = "" + ayCalendarSelectHour;
		}
		// 分数补0
		var ayCalendarSelectMinute$html = null;
		if (ayCalendarSelectMinute < 10) {
			ayCalendarSelectMinute$html = "0" + ayCalendarSelectMinute;
		} else {
			ayCalendarSelectMinute$html = "" + ayCalendarSelectMinute;
		}
		// 秒数补0
		var ayCalendarSelectSecond$html = null;
		if (ayCalendarSelectSecond < 10) {
			ayCalendarSelectSecond$html = "0" + ayCalendarSelectSecond;
		} else {
			ayCalendarSelectSecond$html = "" + ayCalendarSelectSecond;
		}
		// 组装(按日期格式组装)
		var dateValue = null;
		if (ayCalendarFormat == null || ayCalendarFormat == 'DATETIME'
				|| ayCalendarFormat == 'TIMESTAMP'
				|| ayCalendarFormat == 'yyyy-MM-dd HH:mm:ss') {
			dateValue = ayCalendarSelectYear + "-"
					+ ayCalendarSelectMonth$html + "-"
					+ ayCalendarSelectDay$html;
			dateValue = dateValue + " ";
			dateValue = dateValue + ayCalendarSelectHour$html + ":"
					+ ayCalendarSelectMinute$html + ":"
					+ ayCalendarSelectSecond$html;
		}
		if (ayCalendarFormat == 'DATE' || ayCalendarFormat == 'yyyy-MM-dd') {
			dateValue = ayCalendarSelectYear + "-"
					+ ayCalendarSelectMonth$html + "-"
					+ ayCalendarSelectDay$html;
		}
		if (ayCalendarFormat == 'TIME' || ayCalendarFormat == 'HH:mm:ss') {
			dateValue = ayCalendarSelectHour$html + ":"
					+ ayCalendarSelectMinute$html + ":"
					+ ayCalendarSelectSecond$html;
		}
		// alert('dateValue =' + dateValue);
		elementObj.value = dateValue;
	} catch (e) {
		alert(e.stack);
	}
}
/**
 * 今天
 * 
 */
function ayCalendarToday() {
	// 初始化
	ayCalendarNowInitiate();
	// input输入框设置值
	ayCalendarSetValue();;
}
/**
 * 确认
 * 
 */
function ayCalendarEnter() {
	if (true) {
		// input输入框设置值
		// ayCalendarSetValue();;
	}
	// 隐藏div
	document.getElementById('id_div_calendar').style.display = 'none';
}
/**
 * 
 * 显示并弹出div
 * 
 * @param {}
 *            id_element
 * @param {}
 *            event
 */
function ayCalendarShow(id_element, event, format) {
	try {
		// 日期格式
		ayCalendarFormat = format;
		// 输入框的id
		ayCalendarInputId = id_element;
		// 1初始化
		ayCalendarNowInitiate();
		// 2弹出窗口
		// alert('event='+event);
		// alert('event.type='+event.type);
		var elementObj = document.getElementById(id_element);
		ayDivShowPop("id_div_calendar", elementObj);
		// 3给input赋值(现在的时间)
		// input输入框设置值
		// ayCalendarSetValue();;
	} catch (e) {
		alert(e.stack);
	}
}
/**
 * 不弹出div，马上初始化[input输入框]现在时间的值
 * 
 * @param {}
 *            id_element
 */
function ayCalendarNow(id_element, format) {
	try {
		// 日期格式
		ayCalendarFormat = format;
		// 输入框的id
		ayCalendarInputId = id_element;
		// 1初始化
		ayCalendarNowInitiate();
		// 3给input赋值(现在的时间)
		// input输入框设置值
		ayCalendarSetValue();;
	} catch (e) {
		alert(e.stack);
	}
}