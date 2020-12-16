/**
 * @author 陈俊先
 * @description 自定义aler
 * @date 2008.11.25
 * 
 */
/**
 * 日志
 */
// console.log("加载ay_alert.js");
/**
 * msg 事件
 */
 function ay$alert$msg(id, callback) {
	ay$alert$onclick_block(id, callback);
}
/**
 * alert 事件
 * 
 */
 function ay$alert$alert(id, callback) {
 ay$alert$onclick_block(id, callback);
}
/**
 * confirm 事件
 * 
 */
ay$alert$confirm = function(id, callback) {
	ay$alert$onclick_block(id, callback);
}
/**
 * 单击事件
 * 
 * @param {Object}
 *            id
 * @param {Object}
 *            parentObject
 */
ay$alert$onclick = function(id, parentObject) {
	ay$alert$showPop(id, parentObject);
}
/**
 * 隐藏窗口(阻塞事件)

 */
ay$alert$onclick_block_hide = function(c_button_type, id) {
	// 隐藏被ID为objID的对象（层）遮挡的所有select
	// {
	var sels = document.getElementsByTagName('select');
	for (var i = 0; i < sels.length; i++) {
		sels[i].style.display = '';
	}
	// }
	// 隐藏被ID为objID的对象（层）遮挡的所有select
	//
	var object = document.getElementById(id);
	if (object != null) {
		object.style.display = "none";
	}
	var c_return = null;
	if (c_button_type == 'submit') {
		c_return = true;
	}
	if (c_button_type == 'esc') {
		c_return = false;
	}
	if (c_method_block != null) {
		c_method_block(c_return);
	}
}
/**
 * 回调方法的参数
 */
var c_method_block = null;
/**
 * 显示弹出框(阻塞事件)
 * 
 */
ay$alert$onclick_block = function(id, callback) {
	// 覆盖整个屏幕（锁定整个页面）
	// {

	var object = document.getElementById(id);
	object.style.position = "absolute";
	object.style.width = Math.max(document.documentElement.scrollWidth,
			document.documentElement.clientWidth)
			+ "px";
	object.style.height = Math.max(document.documentElement.scrollHeight,
			document.documentElement.clientHeight)
			+ "px";
	// }
	// 覆盖整个屏幕（锁定整个页面）
	// 覆盖select
	// {
	var sels = document.getElementsByTagName('select');
	for (var i = 0; i < sels.length; i++) {
		sels[i].style.display = 'none';
	}
	// }
	// 

	object.style.marginLeft = "0px";
	object.style.marginTop = "0px";
	object.style.display = '';
	
	
	
	// 回调
	c_method_block = callback;
	
}
/**
 * 显示弹出框
 * 
 */
ay$alert$showPop = function(id, parentObject) {
	var object = document.getElementById(id);
	object.style.marginLeft = parentObject.getBoundingClientRect().left + "px";
	object.style.marginTop = parentObject.getBoundingClientRect().bottom + "px";
	object.style.display = '';
}
/**
 * 隐藏窗口

 */
ay$alert$hidePop = function(id) {
	var object = document.getElementById(id);
	if (object != null) {
		object.style.display = "none";
	}
}
