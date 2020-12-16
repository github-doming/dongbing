
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
 * 
 * @param {Object}
 *            title
 * @param {Object}
 *            msg
 * @param {Object}
 *            callback
 * @param {Object}
 *            c_width 自定义宽度
 */
 function ay$alert$msg(title, msg, callback, c_width) {

	ay$alert$onclick_block('msg', 'id_block', title, msg, callback, c_width);
}

/**
 * alert 事件
 * 
 * @param {Object}
 *            title
 * @param {Object}
 *            msg
 * @param {Object}
 *            callback
 * @param {Object}
 *            c_width 自定义宽度
 */
 function ay$alert$alert(title, msg, callback, c_width) {

 ay$alert$onclick_block('alert', 'id_block', title, msg, callback, c_width);
}
/**
 * confirm 事件
 * 
 * @param {Object}
 *            title
 * @param {Object}
 *            msg
 * @param {Object}
 *            callback
 * 
 * @param {Object}
 *            c_width 自定义宽度
 */
ay$alert$confirm = function(title, msg, callback, c_width) {

	ay$alert$onclick_block('confirm', 'id_block', title, msg, callback, c_width);
}

/**
 * 支持firefox,相当于ie的element.outerHTML
 * 
 * @param {Object}
 *            element
 */
ay$alert$getOuterHTML = function(element) {

	return document.createElement("DIV").appendChild(element.cloneNode(true)).parentNode.innerHTML;
}

/**
 * 单击事件
 * 
 * @param {Object}
 *            c_div
 * @param {Object}
 *            parentObj
 */
ay$alert$onclick = function(c_div, parentObj) {
	// var obj = event.srcElement;
	// alert("obj");
	ay$alert$showPop(c_div, parentObj);
}

/**
 * 单击事件
 * 
 * @param {Object}
 *            parentObj
 */
ay$alert$onclick_v1 = function(parentObj) {
	// var obj = event.srcElement;
	// alert(obj);
	ay$alert$showPop("countersignDiv", parentObj);
}

/**
 * 隐藏窗口(阻塞事件)
 * 
 * @param {Object}
 *            c_button_type 确定或取消按钮
 * @param {Object}
 *            aimObjId
 */
ay$alert$onclick_block_hide = function(c_button_type, aimObjId) {

	// 隐藏被ID为objID的对象（层）遮挡的所有select
	// {
	var sels = document.getElementsByTagName('select');
	for (var i = 0; i < sels.length; i++) {
		sels[i].style.display = '';
	}
	// }
	// 隐藏被ID为objID的对象（层）遮挡的所有select

	// /////////////////////////////////////////
	var aimObj = document.getElementById(aimObjId);
	if (aimObj != null) {
		aimObj.style.display = "none";
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
 * @param {Object}
 *            c_msg_type 弹出框类型alert或confirm
 * @param {Object}
 *            domObjId
 * @param {Object}
 *            title 消息头
 * @param {Object}
 *            msg 消息内容
 * @param {Object}
 *            callback 回调
 * @param {Object}
 *            c_width 自定义宽度
 */
ay$alert$onclick_block = function(c_msg_type, domObjId, title, msg, callback,
		c_width) {

	// 覆盖整个屏幕（锁定整个页面）
	// {

	var id_block = document.getElementById("id_block");
//alert('id_block='+id_block);
	id_block.style.position = "absolute";
	id_block.style.width = Math.max(document.documentElement.scrollWidth,
			document.documentElement.clientWidth)
			+ "px";

	id_block.style.height = Math.max(document.documentElement.scrollHeight,
			document.documentElement.clientHeight)
			+ "px";
	// }
	// 覆盖整个屏幕（锁定整个页面）

	// 【覆盖select】
	// {
	var sels = document.getElementsByTagName('select');
	for (var i = 0; i < sels.length; i++) {
		sels[i].style.display = 'none';
	}
	// }
	// 【覆盖select】

	// //////////////////////////////////////
	var domObj = document.getElementById(domObjId);

	domObj.style.marginLeft = "0px";
	domObj.style.marginTop = "0px";

	// domObj.style.display = 'block';//不支持firefox
	domObj.style.display = '';

	// 显示信息

	var obj_title = document.getElementById("id_alert_title");
	obj_title.innerHTML = title;
	var obj_msg = document.getElementById("id_alert_msg");
	obj_msg.innerHTML = msg;
	// alert或confirm
	var obj_input_esc = document.getElementById("id_input_esc");
	obj_input_esc.style.display = "none";

	if (c_msg_type == 'msg') {
		var obj_input_submit = document.getElementById("id_input_submit");
		obj_input_submit.style.display = "none";
	}

	
	if (c_msg_type == 'alert') {

	}

	if (c_msg_type == 'confirm') {

		obj_input_esc.style.display = "";
	}

	// 回调
	c_method_block = callback;

	// {
	// 增加自定义宽度

	if (c_width != null) {
		var id_table_pop = document.getElementById("id_table_pop");

		id_table_pop.style.width = c_width + "px";

	}

	// }
}

/**
 * 显示弹出框
 * 
 * @param {Object}
 *            domObjId
 * @param {Object}
 *            parentObj
 */
ay$alert$showPop = function(domObjId, parentObj) {

	// alert("domObjId="+domObjId);
	// alert("parentObj="+parentObj);
	// alert("parentObj="+parentObj.id);
	var domObj = document.getElementById(domObjId);
	// alert("domObj.innerHTML="+domObj.innerHTML);
	domObj.style.marginLeft = parentObj.getBoundingClientRect().left + "px";
	domObj.style.marginTop = parentObj.getBoundingClientRect().bottom + "px";

	// alert(" domObj.style.marginLeft="+ domObj.style.marginLeft);
	// alert(" parentObj.getBoundingClientRect().left="+
	// parentObj.getBoundingClientRect().left);

	// alert(" domObj.style.marginTop="+ domObj.style.marginTop);

	// alert(" parentObj.getBoundingClientRect().bottom ="+
	// parentObj.getBoundingClientRect().bottom );

	// domObj.style.display = 'block';//不支持firefox
	domObj.style.display = '';

}

/**
 * 隐藏窗口
 * 
 * @param {Object}
 *            aimObjId
 */
ay$alert$hidePop = function(aimObjId) {
	var aimObj = document.getElementById(aimObjId);
	if (aimObj != null) {
		aimObj.style.display = "none";
	}

}

/**
 * onmouseout事件
 * 
 * @param {Object}
 *            domObjId
 * @param {Object}
 *            event
 * @param {Object}
 *            handler
 */
ay$alert$onmouseout = function(domObjId, event, handler) {
	var domObj = document.getElementById(domObjId);
	// alert(domObj.innerHTML);
	if (ay$alert$isMouseLeaveOrEnter(event, handler)) {

		domObj.style.display = 'none';

		// domObj.style.marginLeft = "0px";
		// domObj.style.marginTop = "0px";
		// alert(" domObj.style.marginLeft="+ domObj.style.marginLeft);
	}

}

/**
 * 支持firefox;鼠标ouseLeave事件;
 * 
 * @param {Object}
 *            e
 * @param {Object}
 *            handler
 */
ay$alert$isMouseLeaveOrEnter = function(e, handler) {
	return ay$alert$isMouseLeaveOrEnter_v2(e, handler);
}
/**
 * 支持firefox;鼠标ouseLeave事件;
 * 
 * @param {Object}
 *            e
 * @param {Object}
 *            handler
 */
ay$alert$isMouseLeaveOrEnter_v2 = function(e, handler) {
	// alert( ' handler1='+handler.innerHTML);
	if (e.type != 'mouseout' && e.type != 'mouseover') {
		return false;
	}
	var reltg = null;
	if (e.relatedTarget) {

		reltg = e.relatedTarget;
	} else {

		if (e.type == 'mouseout') {
			reltg = e.toElement;

		} else {

			reltg = e.fromElement;
		}

	}

	while (reltg && reltg != handler) {
		reltg = reltg.parentNode;
	}
	return (reltg != handler);
}
/**
 * 支持firefox
 * 
 * @param {Object}
 *            e
 * @param {Object}
 *            handler
 */
ay$alert$isMouseLeaveOrEnter_v1 = function(e, handler) {
	// alert( ' handler1='+handler.innerHTML);
	if (e.type != 'mouseout' && e.type != 'mouseover')
		return false;
	var reltg = e.relatedTarget ? e.relatedTarget : e.type == 'mouseout'
			? e.toElement
			: e.fromElement;
	while (reltg && reltg != handler)
		reltg = reltg.parentNode;
	return (reltg != handler);
}
