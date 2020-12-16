/**
 * 依赖
 * 
 * @import "ay_string.js"
 * 
 * @author 
 * @description 检查check
 * 
 * @date 2006.3.28
 */
/**
 * 日志
 */
// console.log("加载js");
/**
 * 检查字段
 * 
 * @param {Object}
 *            cnName 中文字段提示
 * @param {Object}
 *            columnName_msg 中文信息提示span的id
 * @param {Object}
 *            columnName 列名
 * @param {Object}
 *            columnType 列类型
 * @param {Object}
 *            columnNull 是否为空
 * @param {Object}
 *            columnPrecision 最大字段长度
 * @param {Object}
 *            sqlScale 小数长度
 * @param {Object}
 *            minPrecision 最小字段长度
 * @param {Object}
 *            custom_msg 中文信息提示(自定义)
 * @param {Object}
 *            is_msg 是否需要中文字段提示
 */
// ayCheckColumn= function(cnName, columnName_msg, columnName,columnType,
// columnNull, columnPrecision, sqlScale, minPrecision,custom_msg, is_msg) {
 function ayCheckColumn(cnName, columnName_msg, columnName,columnType, columnNull, columnPrecision, sqlScale, minPrecision,custom_msg, is_msg) {
		try
	    {
			 if (false) {
					return CfCheck
							.check_column_html_v3(cnName, columnName_msg, columnName,
									columnType, columnNull, columnPrecision, sqlScale,
									minPrecision);
				}
				if (false) {
					return ayCheckColumnHtml_v5(columnName, columnType, columnNull,
							columnPrecision, sqlScale, minPrecision, cnName,
							columnName_msg, custom_msg);
				}
				return ayCheckColumnHtml_v6(columnName, columnType, columnNull,
						columnPrecision, sqlScale, minPrecision, is_msg, cnName,
						columnName_msg, custom_msg);
	    } catch (e) {
	    	// alert(e);
			alert(e.stack);
			// alert( e. fileName);
			// alert( e. lineNumber);
			// alert( e. message);
			// alert( e. name);
			// alert( e. number);
	    }
}
/**
 * 检查字段;
 * 
 * 
 * 1;对input的border进行红色加粗提示;
 * 
 * 2;是否需要中文字段提示;
 * 
 * 
 * @param {Object}
 *            columnName 列名
 * @param {Object}
 *            columnType 列类型
 * @param {Object}
 *            columnNull 是否为空
 * @param {Object}
 *            columnPrecision 最大字段长度
 * @param {Object}
 *            sqlScale 小数长度
 * @param {Object}
 *            minPrecision 最小字段长度
 * @param {Object}
 *            is_msg 是否需要中文字段提示
 * @param {Object}
 *            cnName 中文字段提示
 * @param {Object}
 *            columnName_msg 中文信息提示span的id
 * @param {Object}
 *            custom_msg 中文信息提示(自定义)
 */
// ayCheckColumnHtml_v6 = function(columnName, columnType,
// columnNull,columnPrecision, sqlScale, minPrecision, is_msg,
// cnName,columnName_msg, custom_msg) {
function ayCheckColumnHtml_v6 (columnName, columnType, columnNull,columnPrecision, sqlScale, minPrecision, is_msg, cnName,columnName_msg, custom_msg) {
// 1其它类型
	if (columnType == "CHAR" || columnType == "VARCHAR"
||columnType == "INT UNSIGNED" || columnType == "SMALLINT UNSIGNED"
|| columnType == "BIGINT UNSIGNED"|| columnType == "TINYINT UNSIGNED"
||columnType == "INT" || columnType == "SMALLINT"
|| columnType == "BIGINT" || columnType == "TINYINT"
||columnType == "FLOAT" || columnType == "DOUBLE"
||columnType == "DECIMAL" || columnType == "NUMERIC"
||columnType == "DATETIME" || columnType == "TIMESTAMP"
||columnType == "DATE"
||columnType == "TIME") {
	}else{
		alert( "列名columnName="+columnName );
		alert( "其它类型columnType="+columnType );	
	}
	// 2业务开始
	var c_element_columnName = document.getElementById(columnName);
	if (c_element_columnName == null) {
		// 列名 input的id不能为空
		alert(columnName + " [列名 input的id] is null");
		return false;
	}
	// 1;还原，最初设置;
	ayCheckInputBorderInit(columnName);
	// 2;验证参数;
	// custom_msg参数是否存在;
	var flag_custom_msg = false;
	if (custom_msg == null || "undefined" == typeof(custom_msg) || !custom_msg) {
		flag_custom_msg = false;
	} else {
		flag_custom_msg = true;
	}
	// is_msg参数是否存在;
	// !is_msg取反
	// alert('1 is_msg='+is_msg);
	// if (is_msg == null || "undefined" == typeof(is_msg) || !is_msg) {
	if (is_msg == null || "undefined" == typeof(is_msg)) {
		is_msg = true;
		// is_msg = false;
	} else {
		// is_msg = true;
		// is_msg = false;
	}
	// 3;提示框;
	var c_element_msg = null;
	if (is_msg) {
		// var c_element_msg = document.getElementById(columnName + '_msg');
		c_element_msg = document.getElementById(columnName_msg);
		if (c_element_msg == null) {
			// 错误提示框的id不能为空
			alert(columnName_msg + " 中文信息提示span的id is null");
			return false;
		}
		// 还原，最初设置;
		c_element_msg.innerHTML = "";
	}
	// 4;是否为空;
	var flag = ayCheckBlank(columnName);
	if (flag) {
		if (columnNull == 'yes') {
			// 列可以为空
			// c_element_msg.innerHTML = "true";
			// c_element_msg.innerHTML = "";
			// return true;
		}
		if (columnNull == 'no') {
			if (is_msg) {
				// 列不可以为空
				if (flag_custom_msg) {
					c_element_msg.innerHTML = custom_msg;
				} else {
					c_element_msg.innerHTML = cnName + "[不可以为空]";
				}
				// 增加input的红色加粗提示
				ayCheckInputBorderRed(columnName);
			}
			return false;
		}
	} else {
		// 5;验证长度;
		// 文本不为空时//
		// {
		var flag = ayCheckMinPrecision(columnName, minPrecision);
		if (flag) {
		} else {
			if (is_msg) {
				// 长度错误
				if (flag_custom_msg) {
					c_element_msg.innerHTML = custom_msg;
				} else {
					c_element_msg.innerHTML = cnName + "[长度错误,最小长度必须超过"
							+ columnPrecision + "个字符！]";
				}
				// 增加input的红色加粗提示
				ayCheckInputBorderRed(columnName);
			}
			return false;
		}
		var flag = ayCheckColumnPrecision(columnName, columnPrecision);
		if (flag) {
		} else {
			if (is_msg) {
				// 长度错误
				if (flag_custom_msg) {
					c_element_msg.innerHTML = custom_msg;
				} else {
					c_element_msg.innerHTML = cnName + "[长度错误,最大长度不能超过"
							+ columnPrecision + "个字符！]";
				}
				// 增加input的红色加粗提示
				ayCheckInputBorderRed(columnName);
			}
			return false;
		}
		// 6;验证字段类型;
		// 检查字段类型,如果是正整数类型
		if (columnType == "INT UNSIGNED" || columnType == "SMALLINT UNSIGNED"
				|| columnType == "BIGINT UNSIGNED"
				|| columnType == "TINYINT UNSIGNED") {
			var flag = ayCheckNumber_plus(columnName);
			// alert("flag="+flag);
			if (flag) {
				// c_element_msg.innerHTML = "true";
				// c_element_msg.innerHTML = "";
				// return true;
			} else {
				if (is_msg) {
					// 类型错误，只能为数字
					if (flag_custom_msg) {
						c_element_msg.innerHTML = custom_msg;
					} else {
						c_element_msg.innerHTML = cnName + "[只能为正整数]";
					}
					// 增加input的红色加粗提示
					ayCheckInputBorderRed(columnName);
				}
				return false;
			}
		}
		// 检查字段类型,如果是整数类型
		if (columnType == "INT" || columnType == "SMALLINT"
				|| columnType == "BIGINT" || columnType == "TINYINT") {
			var flag = ayCheckNumber(columnName);
			// alert("flag="+flag);
			if (flag) {
				// c_element_msg.innerHTML = "true";
				// c_element_msg.innerHTML = "";
				// return true;
			} else {
				// alert('2 is_msg='+is_msg);
				if (is_msg) {
					// 类型错误，只能为数字
					if (flag_custom_msg) {
						c_element_msg.innerHTML = custom_msg;
					} else {
						c_element_msg.innerHTML = cnName + "[只能为整数]";
					}
					// 增加input的红色加粗提示
					ayCheckInputBorderRed(columnName);
				}
				return false;
			}
		}
		// 检查字段类型,如果是FLOAT类型或DOUBLE类型
		if (columnType == "FLOAT" || columnType == "DOUBLE") {
			var flag = ayCheckNumber_scale(columnName);
			// alert("flag="+flag);
			if (flag) {
				// c_element_msg.innerHTML = "true";
				// c_element_msg.innerHTML = "";
				// return true;
			} else {
				if (is_msg) {
					// 类型错误，只能为数字
					if (flag_custom_msg) {
						c_element_msg.innerHTML = custom_msg;
					} else {
						c_element_msg.innerHTML = cnName + "[只能为数字]";
					}
					// 增加input的红色加粗提示
					ayCheckInputBorderRed(columnName);
				}
				return false;
			}
		}
		// 检查字段类型,如果是decimal或NUMERIC类型
		if (columnType == "DECIMAL" || columnType == "NUMERIC") {
			// var flag = ayCheckScale(columnName, sqlScale);
			var flag = ayCheckScale(columnName, columnPrecision,
					sqlScale);
			// alert("flag="+flag);
			if (flag) {
				// c_element_msg.innerHTML = "true";
				// c_element_msg.innerHTML = "";
				// return true;
			} else {
				if (is_msg) {
					// 类型错误，只能为小数
					if (flag_custom_msg) {
						c_element_msg.innerHTML = custom_msg;
					} else {
						c_element_msg.innerHTML = cnName + "[小数格式错误]";
						// c_element_msg.innerHTML ="[格式错误]"+ cnName ;
					}
					// 增加input的红色加粗提示
					ayCheckInputBorderRed(columnName);
				}
				return false;
			}
		}
		// 如果字段类型是DATETIME或TIMESTAMP
		if (columnType == "DATETIME" || columnType == "TIMESTAMP") {
			var flag = ayCheckDatetime(columnName);
			if (flag) {
			} else {
				flag = ayCheckDatetime_cn(columnName);
				if (flag) {
				} else {
					if (is_msg) {
						// 格式错误
						if (flag_custom_msg) {
							c_element_msg.innerHTML = custom_msg;
						} else {
							c_element_msg.innerHTML = cnName
									+ "[时间格式只能是yyyy-MM-dd HH:mm:ss]";
						}
						// 增加input的红色加粗提示
						ayCheckInputBorderRed(columnName);
					}
					return false;
				}
			}
		}
		// 如果字段类型是DATE
		if (columnType == "DATE") {
			var flag = ayCheckDate(columnName);
			if (flag) {
			} else {
				if (is_msg) {
					// 格式错误
					if (flag_custom_msg) {
						c_element_msg.innerHTML = custom_msg;
					} else {
						c_element_msg.innerHTML = cnName
								+ "[时间格式只能是yyyy-MM-dd]";
					}
					// 增加input的红色加粗提示
					ayCheckInputBorderRed(columnName);
				}
				return false;
			}
		}
		// 如果字段类型是TIME
		if (columnType == "TIME") {
			var flag = ayCheckTime(columnName);
			if (flag) {
			} else {
				if (is_msg) {
					// 格式错误
					if (flag_custom_msg) {
						c_element_msg.innerHTML = custom_msg;
					} else {
						c_element_msg.innerHTML = cnName + "[时间格式只能是HH:mm:ss]";
					}
					// 增加input的红色加粗提示
					ayCheckInputBorderRed(columnName);
				}
				return false;
			}
		}
		// }
		// 文本不为空时//
	}
	return true;
}
/**
 * 对input的border还原，最初设置;
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckInputBorderInit = function(c_id) {
	 function ayCheckInputBorderInit(c_id) {
	// alert("1");
	var c_element = document.getElementById(c_id);
	if (c_element == null) {
		// 列名 input的id不能为空
		alert(c_id + " [对input的border还原，最初设置时]列名 input的id is null");
		return false;
	}
	c_element.style.border = "";
}
/**
 * 对input的border进行红色加粗提示;
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckInputBorderRed = function(c_id) {
 function ayCheckInputBorderRed(c_id) {
	// alert("1");
	var c_element = document.getElementById(c_id);
	c_element.style.border = "2px solid #FF0000";
}
	 /**
		 * 检查是否空字符串
		 * 
		 * @param {Object}
		 *            c_id
		 */
// ayCheckBlank = function(c_id) {
 function ayCheckBlank (c_id) {
	// alert('ayCheckBlank')
		var c_element = document.getElementById(c_id);
		if (c_element == null) {
			alert(c_id + "没有定义");
		}
		if (c_element.value == null) {
			alert(c_id + "的value没有定义");
		}
		var c_value = ayStringTrim(c_element.value);
		if (c_value == "") {
			// alert(c_id + '不能为空字符串.');
			return true;
		} else {
			return false;
		}
	try {
	} catch (e) {
		// alert(e);
		alert(e.stack);
		// alert( e. fileName);
		// alert( e. lineNumber);
		// alert( e. message);
		// alert( e. name);
		// alert( e. number);
	}
}
/**
 * 检查最大长度
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckColumnPrecision = function(c_id, columnPrecision) {
function ayCheckColumnPrecision (c_id, columnPrecision) {
	if (columnPrecision == null || "undefined" == typeof(columnPrecision)
			|| !columnPrecision) {
		return true;
	}
	var c_element = document.getElementById(c_id);
	var c_length = (c_element.value.length);
	if (c_length <= columnPrecision) {
		return true;
	} else {
		// alert(c_id + "长度错误,最大长度不能超过" + columnPrecision+ "个字符！");
		return false;
	}
}
/**
 * 检查最小长度
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckMinPrecision = function(c_id, minPrecision) {
 function ayCheckMinPrecision (c_id, minPrecision) {
	if (minPrecision == null || "undefined" == typeof(minPrecision)
			|| !minPrecision) {
		return true;
	}
	var c_element = document.getElementById(c_id);
	var c_length = (c_element.value.length);
	if (minPrecision <= c_length) {
		return true;
	} else {
		// alert(c_id + "长度错误,最小长度必须超过" + minPrecision+ "个字符！");
		return false;
	}
}
/**
 * 检查是否个位数字; 利用正则表达式判断是否是0-9的阿拉伯数字;
 * 
 * @param {Object}
 *            c_data
 */
// ayCheckDigit = function(c_id) {
function ayCheckDigit(c_id) {
	var c_element = document.getElementById(c_id);
	var c_value = ayStringTrim(c_element.value);
	var reg = new RegExp("^[0-9]$");
	return (reg.test(c_value));
}
/**
 * 是否是数值
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckNumber = function(c_id) {
 function 	ayCheckNumber(c_id) {
	return ayCheckNumber_v1(c_id);
}
/**
 * 是否是数值
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckNumber_v1 = function(c_id) {
	 function ayCheckNumber_v1 (c_id) {
	var c_element = document.getElementById(c_id);
	var c_value = ayStringTrim(c_element.value);
	var reg = new RegExp("^[-]?[0-9]+$");
	return reg.test(c_value);
}
/**
 * 是否是数值(正整数)
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckNumber_plus = function(c_id) {
 function ayCheckNumber_plus (c_id) {
	var c_element = document.getElementById(c_id);
	var c_value = ayStringTrim(c_element.value);
	var reg = new RegExp("^[0-9]+$");
	return reg.test(c_value);
}
/**
 * 是否是数值(不包括小数位数)
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckNumber_scale = function(c_id) {
 function ayCheckNumber_scale(c_id) {
	var c_element = document.getElementById(c_id);
	var c_value = ayStringTrim(c_element.value);
	var reg = new RegExp("^[-]?[0-9]+[\.]?[0-9]+$");
	return reg.test(c_value);
}
/**
 * 检查是否是整数或小数(包括整数位数,小数位数)
 * 
 * @param {Object}
 *            c_id
 * @param {Object}
 *            columnPrecision
 * @param {Object}
 *            scale
 */
// ayCheckScale = function(c_id, columnPrecision, scale) {
function ayCheckScale (c_id, columnPrecision, scale) {
	// 1;需要匹配的值;
	var c_element = document.getElementById(c_id);
	var c_value = ayStringTrim(c_element.value);
	// 2;整数长度;
	var str_Integer = columnPrecision - scale;
	var reg = null;
	// reg = new RegExp("^[-]?[0-9]{0,3}$");
	var expression = "^[-]?[0-9]{0," + str_Integer + "}$";
	reg = new RegExp(expression);
	var flag = reg.test(c_value);
	if (flag) {
		return flag;
	} else {
		// reg = new RegExp("^[-]?[0-9]{0,3}[\.][0-9]{0,2}$");
		expression = "^[-]?[0-9]{0," + str_Integer + "}[\.][0-9]{0," + scale
				+ "}$";
		reg = new RegExp(expression);
		flag = reg.test(c_value);
		return flag;
	}
}
/**
 * 日期格式(yyyy-MM-dd HH:mm:ss)是否正确
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckDatetime = function(c_id) {
 function ayCheckDatetime (c_id) {
	var c_element = document.getElementById(c_id);
	var c_value = ayStringTrim(c_element.value);
	var reg = new RegExp("^([0-9]{4})-([0-9]{2})-([0-9]{2})([ ]+)([0-9]{2}):([0-9]{2}):([0-9]{2})$");
	return reg.test(c_value);
}
/**
 * 日期格式(yyyy年MM月dd日00 HH时mm分ss秒)是否正确
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckDatetime_cn = function(c_id) {
 function ayCheckDatetime_cn (c_id) {
	var c_element = document.getElementById(c_id);
	var c_value = ayStringTrim(c_element.value);
	var reg = new RegExp("^([0-9]{4})年([0-9]{2})月([0-9]{2})日([ ]+)([0-9]{2})时([0-9]{2})分([0-9]{2})秒$");
	return reg.test(c_value);
}
/**
 * 日期格式(yyyy-MM-dd)是否正确
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckDate = function(c_id) {
function ayCheckDate (c_id) {
	var c_element = document.getElementById(c_id);
	var c_value = ayStringTrim(c_element.value);
	var reg = new RegExp("^([0-9]{4})-([0-9]{2})-([0-9]{2})$");
	return reg.test(c_value);
}
/**
 * 日期格式(HH:mm:ss)是否正确
 * 
 * @param {Object}
 *            c_id
 */
// ayCheckTime = function(c_id) {
function 	ayCheckTime(c_id) {
	var c_element = document.getElementById(c_id);
	var c_value = ayStringTrim(c_element.value);
	var reg = new RegExp("^([0-9]{2}):([0-9]{2}):([0-9]{2})$");
	return reg.test(c_value);
}
