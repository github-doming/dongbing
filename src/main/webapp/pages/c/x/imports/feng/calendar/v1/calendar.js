// 1声明变量
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
var class_td_day = "width:21px;height:20px;background-color:#D8F0FC;font-size:10pt;";
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
}
/*
 * 
 * 显示下拉框的年
 */
function ayCalendarSelectHtmlYear(now_year) {
	// 下拉框,显示年
	var yearSelectObj = document.getElementById("id_select_year");
	if (false) {
		// 序号，取当前选中选项的序号
		var index = yearSelectObj.selectedIndex;
		// 设置选项option的文本
		yearSelectObj.options[index].text = now_year;
	}
	// alert( 'html='+yearSelectObj.innerHTML);
	var html = null;
	for (var i = 1970; i <= 2030; i++) {
		if (i == now_year) {
			html = html + "<option   value='" + i + "' selected='selected'>"
					+ i + "</option>";
		} else {
			html = html + "<option   value='" + i + "'>" + i + "</option>";
		}
	}
	yearSelectObj.innerHTML = html;
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
	// alert(yearSelectObj.options[index].value);
	ayCalendarSelectYear = yearSelectObj.options[index].value;
	// 重置天数
	ayCalendarReset();
}
/*
 * 
 * 显示下拉框的月
 */
function ayCalendarSelectHtmlMonth(now_month) {
	// 显示月
	var MonthSelectObj = document.getElementById("id_select_month");
	if (false) {
		// 序号，取当前选中选项的序号
		var index = MonthSelectObj.selectedIndex;
		// 设置选项option的文本
		MonthSelectObj.options[index].text = now_month;
	}
	// alert( 'html='+MonthSelectObj.innerHTML);
	var html = null;
	for (var i = 1; i <= 12; i++) {
		if (i == now_month) {
			html = html + "<option   value='" + i + "' selected='selected'>"
					+ i + "</option>";
		} else {
			html = html + "<option   value='" + i + "'>" + i + "</option>";
		}
	}
	MonthSelectObj.innerHTML = html;
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
}
/*
 * 
 * 显示下拉框的时
 */
function ayCalendarHourSelectHtml(now_hour) {
	// 显示时
	var obj_select_hour = document.getElementById("id_select_hour");
	if (false) {
		// 序号，取当前选中选项的序号
		var index = obj_select_hour.selectedIndex;
		// 设置选项option的文本
		obj_select_hour.options[index].text = now_hour;
	}
	var html = null;
	for (var i = 1; i <= 24; i++) {
		if (i == now_hour) {
			html = html + "<option   value='" + i + "' selected='selected'>"
					+ i + "</option>";
		} else {
			html = html + "<option   value='" + i + "'>" + i + "</option>";
		}
	}
	obj_select_hour.innerHTML = html;
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
}
/*
 * 
 * 显示下拉框的分
 */
function ayCalendarMinuteSelectHtml(now_minute) {
	// 显示分
	var obj_select_minute = document.getElementById("id_select_minute");
	if (false) {
		// 序号，取当前选中选项的序号
		var index = obj_select_minute.selectedIndex;
		// 设置选项option的文本
		obj_select_minute.options[index].text = now_minute;
	}
	var html = null;
	for (var i = 1; i <= 60; i++) {
		if (i == now_minute) {
			html = html + "<option   value='" + i + "' selected='selected'>"
					+ i + "</option>";
		} else {
			html = html + "<option   value='" + i + "'>" + i + "</option>";
		}
	}
	obj_select_minute.innerHTML = html;
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
}
/*
 * 
 * 显示下拉框的秒
 */
function ayCalendarSecondSelectHtml(now_second) {
	// 显示秒
	var obj_select_second = document.getElementById("id_select_second");
	if (false) {
		// 序号，取当前选中选项的序号
		var index = obj_select_second.selectedIndex;
		// 设置选项option的文本
		obj_select_second.options[index].text = now_second;
	}
	var html = null;
	for (var i = 1; i <= 60; i++) {
		if (i == now_second) {
			html = html + "<option   value='" + i + "' selected='selected'>"
					+ i + "</option>";
		} else {
			html = html + "<option   value='" + i + "'>" + i + "</option>";
		}
	}
	obj_select_second.innerHTML = html;
}
/**
 * 改变下拉框的秒
 * 
 * @param {}
 *            obj_select_second
 */
function ayCalendarSelectChangeSecond(obj_select_second) {
	// 序号，取当前选中选项的序号
	var index = obj_select_second.selectedIndex;
	ayCalendarSelectSecond = obj_select_second.options[index].value;
	// 重置天数
	ayCalendarReset();
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
function ayCalendarNow_init() {
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
	if (true) {
	}
	if (false) {
		alert('现在第几年ayCalendarSelectYear=' + (ayCalendarSelectYear));
		alert('现在第几月 ayCalendarSelectMonth= ' + (ayCalendarSelectMonth));
		alert('现在在一月中处于第几天ayCalendarSelectDay=' + ayCalendarSelectDay);
		alert('现在在一周中处于第几天ayCalendarSelectWeekDay='
				+ ayCalendarSelectWeekDay);
		alert('星期几=' + array_week_name[ayCalendarSelectWeekDay]);
		alert('每个月的最大天数=' + month_day_size[ayCalendarSelectMonth]);
	}
	// 5显示现在的日期
	ayCalendarReset();
}
/**
 * 1显示日期(天数)
 * 
 * @param {}
 *            input_year
 * @param {}
 *            input_month
 * @param {}
 *            input_day
 */
function ayCalendarDayHtml(input_year, input_month, input_day) {
	input_month = input_month - 1;
	// 每个月的第一天
	var obj_first_date = new Date();
	obj_first_date.setFullYear(input_year, input_month, 1);
	// 在一周中处于第几天
	var first_week_day = obj_first_date.getDay();
	// alert('每个月的第一天 在一周中处于第几天 day='+first_week_day);
	// 每个月的最后一天
	var obj_last_date = new Date();
	obj_last_date.setFullYear(input_year, input_month,
			month_day_size[input_month]);
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
				html = html + "&nbsp;";
				html = html + "</td>";
				continue;
			}
			// 每个月的最后一天
			if (i == 6 && j > (last_week_day + 1)) {
				html = html + "<td>";
				html = html + "&nbsp;";
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
					html = html + "<td style='" + class_td_day + "'>";
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
				html = html + "&nbsp;";
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
var temp_td_day = null;
/**
 * td 天的事件
 * 
 * @param {}
 *            obj_day
 */
function ayCalendarDayOnclick(obj_day) {
	// 1
	// 改变颜色
	if (temp_td_day != null) {
		temp_td_day.style.color = "";
	}
	temp_td_day = obj_day;
	obj_day.style.color = "#2650A6";
	// 2
	// 现在的天
	// alert(obj_day.innerHTML);
	ayCalendarSelectDay = obj_day.innerHTML;
}
/**
 * 确认
 * 
 */
function ayCalendarEnter() {
	if (false) {
		alert('ayCalendarSelectYear=' + ayCalendarSelectYear);
		alert('ayCalendarSelectMonth =' + ayCalendarSelectMonth);
		alert('ayCalendarSelectDay =' + ayCalendarSelectDay);
		alert('ayCalendarSelectHour=' + ayCalendarSelectHour);
		alert('ayCalendarSelectMinute =' + ayCalendarSelectMinute);
		alert('ayCalendarSelectSecond =' + ayCalendarSelectSecond);
	}
	var obj_element = document.getElementById(ayCalendarInputId);
	// 时间格式 2013-07-19 11:25:37
	var value_date = ayCalendarSelectYear + "-" + ayCalendarSelectMonth
			+ "-" + ayCalendarSelectDay;
	value_date = value_date + " ";
	value_date = value_date + ayCalendarSelectHour + ":"
			+ ayCalendarSelectMinute + ":" + ayCalendarSelectSecond;
	obj_element.value = value_date;
}