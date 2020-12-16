/**
 * @author 陈俊先
 * @description 用于date的js
 * @date 2008.11.25
 * 
 */

/**
 * 日志
 */
// console.log("加载ay_date.js");
/**
 * 定义
 */
//ayDateNow = function() {
function ayDateNow() {
	try {
		var date = new Date(); // 日期对象
		var now = "";
		// now = date.getFullYear()+"年"; //读英文就行了
		now = date.getFullYear() + "-"; // 读英文就行了
		var i = date.getMonth();
		if (i >= 0 && i <= 8) {
			// now = now +'0'+ (i+1)+"月";//取月的时候取的是当前月-1如果想取当前月+1就可以了
			now = now + '0' + (i + 1) + "-";
		} else {
			// now = now + (date.getMonth()+1)+"月";//取月的时候取的是当前月-1如果想取当前月+1就可以了
			now = now + (i + 1) + "-";
		}
		// alert('now Month='+now);
		var i = date.getDate();
		if (i >= 0 && i <= 9) {
			// now = now + '0'+ i+"日";
			now = now + '0' + i + "";
		} else {
			// now = now + date.getDate()+"日";
			now = now + date.getDate() + "";
		}
		now = now + " ";
		var i = date.getHours();
		if (i >= 0 && i <= 9) {
			now = now + '0' + i + ":";
		} else {
			now = now + i + ":";
		}
		var i = date.getMinutes();
		if (i >= 0 && i <= 9) {
			now = now + '0' + i + ":";
		} else {
			now = now + date.getMinutes() + ":";
		}
		var i = date.getSeconds();
		if (i >= 0 && i <= 9) {
			now = now + '0' + i + "";
		} else {
			now = now + date.getSeconds() + "";
		}
		// alert(now);
		// document.getElementById("datepicker").value = now;
		// //div的html是now这个字符串
		return now;
	} catch (e) {
		alert(e.stack);
	}
}
