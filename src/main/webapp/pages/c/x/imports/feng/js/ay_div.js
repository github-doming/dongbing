/**
 * 支持firefox,相当于ie的element.outerHTML
 * 
 * @param obj_element
 * @return
 */
function ayDivFindOuterHtml(obj_element) {
	return document.createElement("DIV").appendChild(
			obj_element.cloneNode(true)).parentNode.innerHTML;
	// return obj_element.outerHTML;
}
/**
 * 显示弹出框
 * 
 * @param id_div
 * @param obj_parent
 * @return
 */
function ayDivShowPop(id_element, obj_element_parent) {
	if(false){
		alert('getBoundingClientRect().left=' + obj_element_parent
				.getBoundingClientRect().left);
		alert('getBoundingClientRect().top=' + obj_element_parent
				.getBoundingClientRect().top);
		alert('getBoundingClientRect().bottom=' + obj_element_parent
				.getBoundingClientRect().bottom);
		
	}
	
	// 设置坐标,其它div设置为隐藏才有效
	var obj_element = document.getElementById(id_element);
	var left = obj_element_parent.getBoundingClientRect().left + "px";// x坐标
	var top = obj_element_parent.getBoundingClientRect().bottom + "px"; // y坐标
	obj_element.style.marginLeft = left;
	obj_element.style.marginTop = top;
	/**
	 *  不隐藏
	 */
	//  obj_element.style.display = 'block';//不支持firefox
	obj_element.style.display = '';
	/**
	 *  不隐藏
	 */
}
/**
 * 隐藏窗口
 * 
 * @param id_div
 * @return
 */
function ayDivHidePop(id_element) {
	var obj_element = document.getElementById(id_element);
	if (obj_element != null) {
		obj_element.style.display = "none";
	}
}


/**
 *  onmouseout事件
 */
function ayDivOnmouseout( event, obj_element ) {
	
	 ayDivOnmouseout_v2( event, obj_element );
}
/**
 *  onmouseout事件
 */
function ayDivOnmouseout_v2( event, obj_element ) {
	
	if (ayDivIsMouseLeaveOrEnter(event, obj_element )) {
	obj_element.style.display = 'none';
	}
}
/**
 *  onmouseout事件
 */
function ayDivOnmouseout_v1(id_element, event, handler) {
	var obj_element = document.getElementById(id_element);
	//alert(obj_element.innerHTML);
	if (ayDivIsMouseLeaveOrEnter(event, handler)) {
		obj_element.style.display = 'none';
	}
}
/**
 * 支持firefox;鼠标mouseLeave事件;
 * @param event
 * @param handler
 * @return
 */
function ayDivIsMouseLeaveOrEnter(event, handler) {
	return ayDivIsMouseLeaveOrEnter_v2(event, handler);
	//return ayDivIsMouseLeaveOrEnter_v1(event, handler);
}
/**
 * 
 * 支持firefox;鼠标mouseLeave事件;
 * @param c_event
 * @param c_handler
 * @return
 */
function ayDivIsMouseLeaveOrEnter_v2(c_event, c_handler) {
	//alert( ' c_handler='+c_handler.innerHTML);
	// alert( 'c_event.type='+c_event.type);
	if (c_event.type == 'mouseout' || c_event.type == 'mouseover') {
	} else {
		return false;
	}
	var reltg = null;
	if (c_event.relatedTarget) {
		reltg = c_event.relatedTarget;
	} else {
		if (c_event.type == 'mouseout') {
			reltg = c_event.toElement;
		} else {
			reltg = c_event.fromElement;
		}
	}
	while (reltg && reltg != c_handler) {
		reltg = reltg.parentNode;
	}
	return (reltg != c_handler);
}
/**
 * 支持firefox
 * @param e
 * @param handler
 * @return
 */
function ayDivIsMouseLeaveOrEnter_v1(e, handler) {
	//alert( ' c_handler='+handler.innerHTML);
	if (e.type != 'mouseout' && e.type != 'mouseover')
		return false;
	var reltg = e.relatedTarget ? e.relatedTarget
			: e.type == 'mouseout' ? e.toElement : e.fromElement;
	while (reltg && reltg != handler)
		reltg = reltg.parentNode;
	return (reltg != handler);
}
