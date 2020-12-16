/**
 * 检查字段;
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
 *            cnName 中文字段提示
 * @param {Object}
 *            columnName_msg 中文信息提示span的id
 * @param {Object}
 *            custom_msg 中文信息提示(自定义)
 */
// ayCheckColumnHtml_v5 = function(columnName, columnType,
// columnNull,columnPrecision, sqlScale, minPrecision, cnName,
// columnName_msg,custom_msg) {
	function ayCheckColumnHtml_v5 (columnName, columnType, columnNull,columnPrecision, sqlScale, minPrecision, cnName, columnName_msg,custom_msg) {
	// 1;验证参数;
	// custom_msg参数是否存在;
	var flag_custom_msg = false;
	if (custom_msg == null || "undefined" == typeof(custom_msg) || !custom_msg) {
		flag_custom_msg = false;
	} else {
		flag_custom_msg = true;
	}
	// 2;
	// var c_element_msg = document.getElementById(columnName + '_msg');
	var c_element_msg = document.getElementById(columnName_msg);
	if (c_element_msg == null) {
		// 错误提示框的id不能为空
		alert(columnName_msg + " is null");
		return false;
	}
	c_element_msg.innerHTML = "";
	var flag = ayCheckBlank(columnName);
	if (flag) {
		if (columnNull == 'yes') {
			// 列可以为空
			// c_element_msg.innerHTML = "true";
			// c_element_msg.innerHTML = "";
			// return true;
		}
		if (columnNull == 'no') {
			// 列不可以为空
			if (flag_custom_msg) {
				c_element_msg.innerHTML = custom_msg;
			} else {
				c_element_msg.innerHTML = cnName + "[不可以为空]";
			}
			return false;
		}
	} else {
		// 文本不为空时//
		// {
		var flag = ayCheckMinPrecision(columnName, minPrecision);
		if (flag) {
		} else {
			// 长度错误
			if (flag_custom_msg) {
				c_element_msg.innerHTML = custom_msg;
			} else {
				c_element_msg.innerHTML = cnName + "[长度错误,最小长度必须超过"
						+ columnPrecision + "个字符！]";
			}
			return false;
		}
		var flag = ayCheckColumnPrecision(columnName, columnPrecision);
		if (flag) {
		} else {
			// 长度错误
			if (flag_custom_msg) {
				c_element_msg.innerHTML = custom_msg;
			} else {
				c_element_msg.innerHTML = cnName + "[长度错误,最大长度不能超过"
						+ columnPrecision + "个字符！]";
			}
			return false;
		}
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
				// 类型错误，只能为数字
				if (flag_custom_msg) {
					c_element_msg.innerHTML = custom_msg;
				} else {
					c_element_msg.innerHTML = cnName + "[只能为正整数]";
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
				// 类型错误，只能为数字
				if (flag_custom_msg) {
					c_element_msg.innerHTML = custom_msg;
				} else {
					c_element_msg.innerHTML = cnName + "[只能为整数]";
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
				// 类型错误，只能为数字
				if (flag_custom_msg) {
					c_element_msg.innerHTML = custom_msg;
				} else {
					c_element_msg.innerHTML = cnName + "[只能为数字]";
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
				// 类型错误，只能为小数
				if (flag_custom_msg) {
					c_element_msg.innerHTML = custom_msg;
				} else {
					c_element_msg.innerHTML = cnName + "[小数格式错误]";
					// c_element_msg.innerHTML ="[格式错误]"+ cnName ;
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
					// 格式错误
					if (flag_custom_msg) {
						c_element_msg.innerHTML = custom_msg;
					} else {
						c_element_msg.innerHTML = cnName
								+ "[时间格式只能是yyyy-MM-dd HH:mm:ss]";
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
				// 格式错误
				if (flag_custom_msg) {
					c_element_msg.innerHTML = custom_msg;
				} else {
					c_element_msg.innerHTML = cnName + "[时间格式只能是yyyy-MM-dd]";
				}
				return false;
			}
		}
		// 如果字段类型是TIME
		if (columnType == "TIME") {
			var flag = ayCheckTime(columnName);
			if (flag) {
			} else {
				// 格式错误
				if (flag_custom_msg) {
					c_element_msg.innerHTML = custom_msg;
				} else {
					c_element_msg.innerHTML = cnName + "[时间格式只能是HH:mm:ss]";
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
 * 检查字段;
 * 
 * 1不能自定义信息提示;
 * 
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
 */
// ayCheckColumnHtml_v3 = function(cnName, columnName_msg,
// columnName,columnType, columnNull, columnPrecision, sqlScale, minPrecision) {
function ayCheckColumnHtml_v3 (cnName, columnName_msg, columnName,columnType, columnNull, columnPrecision, sqlScale, minPrecision) {
	// var c_element_msg = document.getElementById(columnName + '_msg');
	var c_element_msg = document.getElementById(columnName_msg);
	c_element_msg.innerHTML = "";
	var flag = ayCheckBlank(columnName);
	if (flag) {
		if (columnNull == 'yes') {
			// 列可以为空
			// c_element_msg.innerHTML = "true";
			// c_element_msg.innerHTML = "";
			// return true;
		}
		if (columnNull == 'no') {
			// 列不可以为空
			c_element_msg.innerHTML = cnName + "[不可以为空]";
			return false;
		}
	} else {
		// 文本不为空时//
		// {
		var flag = ayCheckMinPrecision(columnName, minPrecision);
		if (flag) {
		} else {
			// 长度错误
			c_element_msg.innerHTML = cnName + "[长度错误,最小长度必须超过"
					+ columnPrecision + "个字符！]";
			return false;
		}
		var flag = ayCheckColumnPrecision(columnName, columnPrecision);
		if (flag) {
		} else {
			// 长度错误
			c_element_msg.innerHTML = cnName + "[长度错误,最大长度不能超过"
					+ columnPrecision + "个字符！]";
			return false;
		}
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
				// 类型错误，只能为数字
				c_element_msg.innerHTML = cnName + "[只能为正整数]";
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
				// 类型错误，只能为数字
				c_element_msg.innerHTML = cnName + "[只能为整数]";
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
				// 类型错误，只能为数字
				c_element_msg.innerHTML = cnName + "[只能为数字]";
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
				// 类型错误，只能为小数
				c_element_msg.innerHTML = cnName + "[小数格式错误]";
				// c_element_msg.innerHTML ="[格式错误]"+ cnName ;
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
					// 类型错误，只能为数字
					c_element_msg.innerHTML = cnName
							+ "[时间格式只能是yyyy-MM-dd HH:mm:ss]";
					return false;
				}
			}
		}
		// 如果字段类型是DATE
		if (columnType == "DATE") {
			var flag = ayCheckDate(columnName);
			if (flag) {
			} else {
				// 类型错误，只能为数字
				c_element_msg.innerHTML = cnName + "[时间格式只能是yyyy-MM-dd]";
				return false;
			}
		}
		// 如果字段类型是TIME
		if (columnType == "TIME") {
			var flag = ayCheckTime(columnName);
			if (flag) {
			} else {
				// 类型错误，只能为数字
				c_element_msg.innerHTML = cnName + "[时间格式只能是HH:mm:ss]";
				return false;
			}
		}
		// }
		// 文本不为空时//
	}
	return true;
}
/**
 * 检查字段;
 * 
 * 1不支持中文信息提示(自定义);
 * 
 * 2不支持最小字段长度;
 * 
 * 3不支持小数长度;
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
 */
// ayCheckColumnHtml_v2 = function(cnName, columnName_msg,
// columnName,columnType, columnNull, columnPrecision) {
	function ayCheckColumnHtml_v2 (cnName, columnName_msg, columnName,columnType, columnNull, columnPrecision) {
	// var c_element_msg = document.getElementById(columnName + '_msg');
	var c_element_msg = document.getElementById(columnName_msg);
	c_element_msg.innerHTML = "";
	// 1;a ,可以为空, true;
	// 2;a ,不可为空, true;
	// 3;空值,可以为空, true;
	// 4;空值,不可为空, false;
	var flag = ayCheckBlank(columnName);
	if (flag) {
		if (columnNull == 'yes') {
			// 列可以为空
			// c_element_msg.innerHTML = "true";
			// c_element_msg.innerHTML = "";
			// return true;
		}
		if (columnNull == 'no') {
			// 列不可以为空
			c_element_msg.innerHTML = cnName + "不可以为空";
			return false;
		}
	} else {
		// 文本不为空时//
		// {
		var flag = ayCheckColumnPrecision(columnName, columnPrecision);
		if (flag) {
		} else {
			// 长度错误
			c_element_msg.innerHTML = cnName + "长度错误,最大长度不能超过"
					+ columnPrecision + "个字符！";
			return false;
		}
		// 检查字段类型
		if (columnType == "INT" || columnType == "SMALLINT"
				|| columnType == "BIGINT" || columnType == "TINYINT") {
			var flag = ayCheckNumber(columnName);
			// alert("flag="+flag);
			if (flag) {
				// c_element_msg.innerHTML = "true";
				// c_element_msg.innerHTML = "";
				// return true;
			} else {
				// 类型错误，只能为数字
				c_element_msg.innerHTML = cnName + "只能为数字";
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
					// 类型错误，只能为数字
					c_element_msg.innerHTML = cnName
							+ "时间格式只能是yyyy-MM-dd HH:mm:ss";
					return false;
				}
			}
		}
		// 如果字段类型是DATE
		if (columnType == "DATE") {
			var flag = ayCheckDate(columnName);
			if (flag) {
			} else {
				// 类型错误，只能为数字
				c_element_msg.innerHTML = cnName + "时间格式只能是yyyy-MM-dd";
				return false;
			}
		}
		// 如果字段类型是TIME
		if (columnType == "TIME") {
			var flag = ayCheckTime(columnName);
			if (flag) {
			} else {
				// 类型错误，只能为数字
				c_element_msg.innerHTML = cnName + "时间格式只能是HH:mm:ss";
				return false;
			}
		}
		// }
		// 文本不为空时//
	}
	return true;
}
/**
 * 检查字段;
 * 
 * 1不支持中文信息提示(自定义);
 * 
 * 2不支持最小字段长度;
 * 
 * 3不支持小数长度;
 * 
 * 4只支持两种类型Long和Integer;
 * 
 * 5有bug,空值时还继续检查类型;
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
 */
// ayCheckColumnHtml_v1 = function(cnName, columnName_msg,
// columnName,columnType, columnNull, columnPrecision) {
 function ayCheckColumnHtml_v1 (cnName, columnName_msg, columnName,columnType, columnNull, columnPrecision) {
	// var c_element_msg = document.getElementById(columnName + '_msg');
	var c_element_msg = document.getElementById(columnName_msg);
	c_element_msg.innerHTML = "";
	// 1;a ,可以为空, true;
	// 2;a ,不可为空, true;
	// 3;空值,可以为空, true;
	// 4;空值,不可为空, false;
	if (columnNull == 'yes') {
		// 列可以为空
		// c_element_msg.innerHTML = "true";
		// c_element_msg.innerHTML = "";
		// return true;
	}
	if (columnNull == 'no') {
		var flag = ayCheckBlank(columnName);
		if (flag) {
			// 列不可以为空
			c_element_msg.innerHTML = cnName + "不可以为空";
			return false;
		}
	}
	// ayCheckMaxLength(columnName);
	var flag = ayCheckColumnPrecision(columnName, columnPrecision);
	if (flag) {
	} else {
		// 长度错误
		c_element_msg.innerHTML = cnName + "长度错误,最大长度不能超过" + columnPrecision
				+ "个字符！";
		return false;
	}
	if (columnType == "Long" || columnType == "Integer") {
		var flag = ayCheckNumber(columnName);
		// alert("flag="+flag);
		if (flag) {
			// c_element_msg.innerHTML = "true";
			// c_element_msg.innerHTML = "";
			// return true;
		} else {
			// 类型错误，只能为数字
			c_element_msg.innerHTML = cnName + "只能为数字";
			return false;
		}
	}
	return true;
}
/**
 * 检查字段;
 * 
 * 1不支持中文信息提示(自定义);
 * 
 * 2不支持最小字段长度;
 * 
 * 3不支持小数长度;
 * 
 * 4只支持两种类型Long和Integer;
 * 
 * 5有bug,有时不支持最大字段长度，如果允许为空将 return true;
 * 
 * @param {Object}
 *            columnName 列名
 * @param {Object}
 *            columnType 列类型
 * @param {Object}
 *            columnNull 是否为空
 * @param {Object}
 *            columnPrecision 最大字段长度
 */
// ayCheckColumn_v1 = function(columnName, columnType,
// columnNull,columnPrecision) {
function ayCheckColumn_v1 (columnName, columnType, columnNull,
			columnPrecision) {
	// 1;a ,可以为空, true;
	// 2;a ,不可为空, true;
	// 3;空值,可以为空, true;
	// 4;空值,不可为空, false;
	// 有bug，出现第二种情况才检查最大字段长度
	// 2;a ,不可为空, true;
	if (columnNull == 'yes') {
		// 列可以为空
		return true;
	}
	if (columnNull == 'no') {
		var flag = ayCheckBlank(columnName);
		if (flag) {
			// 列不可以为空
			return false;
		}
	}
	ayCheckMaxLength(columnName);
	var flag = ayCheckColumnPrecision(columnName, columnPrecision);
	if (flag) {
	} else {
		// 长度错误
		return false;
	}
	if (columnType == "Long" || columnType == "Integer") {
		var flag = ayCheckNumber(columnName);
		// alert("flag="+flag);
		if (flag) {
			return true;
		} else {
			// 类型错误，只能为数字
			return false;
		}
	}
	return false;
}
/**
 * 检查最大长度
 * 
 * @param {Object}
 *            c_id
 */
 // ayCheckMaxLength = function(c_id) {
function ayCheckMaxLength_v1 (c_id) {
	 	var c_element = document.getElementById(c_id);
	 	var c_length = (c_element.value.length);
	 	// alert(c_obj.maxLength );
	 	// -1支持firefox,2147483647支持ie
	 	if (c_element.maxLength == "-1" || c_element.maxLength == 2147483647) {
	 		alert(c_id + '最大长度没有定义');
	 		return false;
	 	} else {
	 		if (c_length > c_element.maxLength) {
	 			// alert(c_id + "长度错误,最大长度不能超过" + c_obj.maxLength + "个字符！");
	 			return false;
	 		} else {
	 			return true;
	 		}
	 	}
}
/**
 * [弃用];
 * 
 * 用于幼儿园产品A1;
 * 
 * 必须有小数点;
 * 
 * 检查小数(包括小数位数);
 * 
 * @param {Object}
 *            c_id
 * @param {Object}
 *            scale
 */
	 	// ayCheckScale_v1 = function(c_id, scale) {
function ayCheckScale_v1 (c_id, scale) {
	 		// 1;scale参数是否存在
	 		if (scale == null || "undefined" == typeof(scale) || !scale) {
	 			return true;
	 		}
	 		if (scale == '0') {
	 			return true;
	 		}
	 		// 2;检查是否数值
	 		if (ayCheckNumber_scale(c_id)) {
	 		} else {
	 			// alert( "数值错误");
	 			return false;
	 		}
	 		// 3;检查小数位数
	 		var c_element = document.getElementById(c_id);
	 		// 4;小数位数
	 		var value_scale = c_element.value.split('.');
	 		var length_scale = value_scale.length;
	 		if (length_scale != 2) {
	 			// alert( "长度错误");
	 			return false;
	 		}
	 		// 5;小数长度
	 		var c_length = value_scale[1].length;
	 		if (c_length <= scale) {
	 			return true;
	 		} else {
	 			// alert(c_id + "长度错误,最大长度不能超过" + scale+ "个字符！");
	 			return false;
	 		}
	 	}
/**
 * (密码是否相等)
 * 
 * @param {Object}
 *            dest
 * @param {Object}
 *            orig
 * @param {Object}
 *            columnName_msg 中文信息提示span的id
 */
	 		// ayCheckPassword = function(dest, orig, columnName_msg) {
function ayCheckPassword (dest, orig, columnName_msg) {
	 			var c_element_msg = document.getElementById(columnName_msg);
	 			c_element_msg.innerHTML = "";
	 			var flag = ayCheckEqual(dest, orig);
	 			if (flag) {
	 				return true;
	 			} else {
	 				c_element_msg.innerHTML = "[密码与确认密码不一致]";
	 				return false;
	 			}
	 		}
/**
 * 值是否相等
 * 
 * @param {Object}
 *            dest
 * @param {Object}
 *            orig
 */
	 		// ayCheckEqual = function(dest, orig) {
function ayCheckEqual (dest, orig) {
	 			var c_element_dest = document.getElementById(dest);
	 			var c_element_orig = document.getElementById(orig);
	 			if (ayStringIsNull(dest) || ayStringIsNull(orig)) {
	 				return false;
	 			} else {
	 				var c_value_dest = c_element_dest.value;
	 				var c_value_orig = c_element_orig.value;
	 				if (c_value_dest == c_value_orig) {
	 					return true;
	 				} else {
	 					return false;
	 				}
	 			}
	 		}
/**
 * 验证账户;
 * 
 * 匹配帐号是否合法(字母开头，允许1-16字节，允许字母数字下划线);
 * 
 * 数字开头也行;
 * 
 * @param {Object}
 *            columnName 列名
 * @param {Object}
 *            cnName 中文字段提示
 * @param {Object}
 *            columnName_msg 中文信息提示span的id
 * 
 * 
 */
	 		// ayCheckAccount = function(columnName, cnName,
			// columnName_msg) {
function ayCheckAccount (columnName, cnName, columnName_msg) {
	 			// return ayCheckAccount_v2(columnName);
	 			return ayCheckAccount_v6(columnName, cnName, columnName_msg);
	 		}
/**
 * 验证账户;
 * 
 * 匹配帐号是否合法(字母开头，允许1-16字节，允许字母数字下划线);
 * 
 * 数字开头也行;
 * 
 * @param {Object}
 *            columnName 列名
 * @param {Object}
 *            cnName 中文字段提示
 * @param {Object}
 *            columnName_msg 中文信息提示span的id
 * 
 * 
 */
	 		// ayCheckAccount_v6 = function(columnName, cnName,
			// columnName_msg) {
 function ayCheckAccount_v6 (columnName, cnName, columnName_msg) {
	 			var c_element_msg = document.getElementById(columnName_msg);
	 			c_element_msg.innerHTML = "";
	 			// 数字开头也行;
	 			var flag = ayCheckAccount_v1(columnName);
	 			// alert('flag='+flag);
	 			if (flag) {
	 				return true;
	 			} else {
	 				// c_element_msg.innerHTML = cnName +
					// "[1至16个字符,由英文、数字或"_"组成]";
	 				// c_element_msg.innerHTML = cnName + "[1至16个字符,由英文或数字组成]";
	 				// c_element_msg.innerHTML = cnName +
					// "[长度只能6-16位，由英文、数字或"_"组成]";
	 				c_element_msg.innerHTML = cnName + "[长度只能1-16位，由英文或数字组成]";
	 				// alert('c_element_msg.innerHTML ='+c_element_msg.innerHTML
					// );
	 				return false;
	 			}
	 		}
/**
 * 验证账户;
 * 
 * 只允许字母数字下划线;
 * 
 * @param {Object}
 *            c_id
 */
	 		// ayCheckAccount_v5 = function(c_id) {
 function 	ayCheckAccount_v5 (c_id) {
	 			// alert('ayCheckAccount_v4');
	 			var c_element = document.getElementById(c_id);
	 			var c_value = ayStringTrim(c_element.value);
	 			// alert(c_value);
	 			var reg = new RegExp("^[a-zA-Z0-9_]+$");
	 			return reg.test(c_value);
	 		}
/**
 * 验证账户;
 * 
 * 只允许字母数字;
 * 
 * @param {Object}
 *            c_id
 */
	 		// ayCheckAccount_v4 = function(c_id) {
 function ayCheckAccount_v4 (c_id) {
	 			// alert('ayCheckAccount_v4');
	 			var c_element = document.getElementById(c_id);
	 			var c_value = ayStringTrim(c_element.value);
	 			// alert(c_value);
	 			var reg = new RegExp("^[a-zA-Z0-9]+$");
	 			return reg.test(c_value);
	 		}
/**
 * 验证账户;
 * 
 * 匹配帐号是否合法(字母开头，允许1-16字节，允许字母数字下划线);
 * 
 * @param {Object}
 *            c_id
 */
	 		// ayCheckAccount_v3 = function(c_id) {
 function 	ayCheckAccount_v3 (c_id) {
	 			var c_element = document.getElementById(c_id);
	 			var c_value = ayStringTrim(c_element.value);
	 			var reg = new RegExp("^[a-zA-Z][a-zA-Z0-9_]{0,15}$");
	 			return reg.test(c_value);
	 		}
/**
 * 验证账户;
 * 
 * 匹配帐号是否合法(允许1-16字节，允许字母数字下划线);
 * 
 * @param {Object}
 *            c_id
 */
	 		// ayCheckAccount_v2 = function(c_id) {
 function ayCheckAccount_v2 (c_id) {
	 			var c_element = document.getElementById(c_id);
	 			var c_value = ayStringTrim(c_element.value);
	 			var reg = new RegExp("^[a-zA-Z0-9_]{1,16}$");
	 			return reg.test(c_value);
	 		}
/**
 * 验证账户;
 * 
 * 匹配帐号是否合法(允许1-16字节，允许字母数字);
 * 
 * @param {Object}
 *            c_id
 */
	 		// ayCheckAccount_v1 = function(c_id) {
function ayCheckAccount_v1 (c_id) {
	 			var c_element = document.getElementById(c_id);
	 			var c_value = ayStringTrim(c_element.value);
	 			var reg = new RegExp("^[a-zA-Z0-9]{1,16}$");
	 			return reg.test(c_value);
	 		}
/**
 * 匹配身份证：d{15}|d{18}; 注：中国的身份证为15位或18位;
 * 
 * @param {Object}
 *            c_id
 */
	 		// ayCheckIdentityCard = function(c_id) {
function ayCheckIdentityCard (c_id) {
	 			var c_element = document.getElementById(c_id);
	 			var c_value = ayStringTrim(c_element.value);
	 			var reg = new RegExp("^d{15}|d{18}$");
	 			return reg.test(c_value);
	 		}