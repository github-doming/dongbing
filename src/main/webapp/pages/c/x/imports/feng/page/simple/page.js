/**
 * 
 * 依赖
 * 
 * @date 2008.11.25
 * 
 */
/**
 * 日志
 */
// console.log("");
/**
 * 
 * 前一页，后一页
 * 
 * @param c_cp
 * @param page
 * @return
 */
ayPageSelect = function(c_cp, page) {
	// document.getElementById('id_input_page_first').disabled = true;
	// document.getElementById('id_input_edit').disabled = true;
	// document.getElementById('id_input_new').disabled = true;
	// 表单
	var formObj = document.getElementById('id_form_list');
	var url = formObj.action;
//	alert('url='+url);
	// 第几页（保存在jsp）
	var obj_page = document.getElementById('id_page');
	obj_page.value = page;
	// 提交
	formObj.submit();
}
/**
 * 
 * 跳转到第几页
 * 
 * @param c_cp
 * @return
 */
ayPageGo = function(c_cp) {
	// 第几页
	var c_gopage = document.getElementById('id_gopage');
	var page = c_gopage.value;
	ayPageSelect(c_cp, page);
}
/**
 * 
 * 显示全部
 * 
 * @param c_cp
 * @return
 */
var reset_submit = function() {
	alert('不要重复提交');
}
ayPageQueryAll = function(c_this, c_cp) {
	if (true) {
		// 二次提交
		// alert('1');
		// alert('onclick='+c_this.onclick);
		c_this.onclick = reset_submit;
		// alert('onclick='+c_this.onclick);
		$("#id_form_list").attr("style", "display: none;");
		$("#id_div_msg").attr("style", "");
		// return false;
	}
	//
	document.getElementById('id_show_all').value = 'show_all_true';
	// 第几页
	var page = "1";
	ayPageSelect(c_cp, page);
}
/**
 * 
 * 不显示全部
 * 
 * @param c_cp
 * @return
 */
ayPageQueryAllNot = function(c_cp) {
	document.getElementById('id_show_all').value = 'show_all_false';
	// 第几页
	var page = "1";
	ayPageSelect(c_cp, page);
}
/**
 * 排序事件
 * 
 * @param property
 * @param obj
 * @return
 */
ayPageOrderEvent = function(c_cp, propertyName, obj) {
	//alert('propertyName='+propertyName);
	// 设置排序的隐藏列
	var property=document.getElementById('sortFieldId');
	var order=document.getElementById('sortOrderId');
	property.value = propertyName;
	if (order.value == 'asc') {
		order.value = "desc";
	} else {
		order.value = "asc";
	}
	// 设置排序的隐藏列
	// 第几页
	var page = "1";
	ayPageSelect(c_cp, page);
}