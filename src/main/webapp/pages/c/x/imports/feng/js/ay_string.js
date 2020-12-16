
/**
 * @author 
 * @description 用于string的js
 * @date 2005.11.25
 * 
 */
/**
 * 日志
 */
// console.log("加载cf_string.js");
/**
 * 去除字符串左右两端的空格
 * 
 * @param {Object}
 *            obj
 */
//ayStringTrim = function(obj) {
 function ayStringTrim (obj) {	
return obj.replace(/(^\s*)|(\s*$)/g, "");
}
/**
 * 去除字符串左右两端的空格
 * 
 * @param {Object}
 *            obj
 */
//ayStringTrim_v1 = function(obj) {
function ayStringTrim_v1 (obj){
return "trim_v1";
}
/**
 * 为空
 * 
 * @param {Object}
 *            c_id
 */
//ayStringIsBlank = function(c_id) {
 function ayStringIsBlank (c_id) {	
var c_element = document.getElementById(c_id);
	if ("undefined" == typeof(c_element) || !c_element || c_element == null) {
		return true;
	} else {
		var c_value = c_element.value;
		if (ayStringTrim(c_value) == '') {
			// alert('value');
			return true;
		} else {
			return false;
		}
	}
}
/**
 * 不为空
 * 
 * @param {Object}
 *            c_id
 */
//ayStringIsNotBlank = function(c_id) {
	 function ayStringIsNotBlank (c_id) {
	return !ayStringIsNotBlank(c_id);
}
/**
 * 为Null
 * 
 * @param {Object}
 *            c_id
 */
//ayStringIsNull = function(c_id) {
	function ayStringIsNull (c_id) {
	var c_element = document.getElementById(c_id);
	if ("undefined" == typeof(c_e)) {
		// alert('1');
	}
	if ("undefined" == typeof(c_element)) {
		// alert('2');
	}
	if (!c_element) {
		//alert('3');
	}
	if (c_element == null) {
		//alert('4');
	}
	if ("undefined" == typeof(c_element) || !c_element || c_element == null) {
		return true;
	} else {
		return false;
	}
}
/**
 * 不为Null
 * 
 * @param {Object}
 *            c_id
 */
//ayStringIsNotNull = function(c_id) {
 function ayStringIsNotNull (c_id) {
	return !ayStringIsNotBlank(c_id);
}
