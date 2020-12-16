/**
 * 
 * 依赖
 * 
 * 
 * @description 用于扩展jquery的js
 * 
 * 
 */
/**
 * 日志
 */
// console.log("加载js");



//表单数据转成json对象
jQuery.fn.funForm2Object= function() {

	var o = {};
	var a = this.serializeArray();
	jQuery.each(a, function() {
		if (o[this.name] !== undefined) {

			if (!o[this.name].push) {
				o[this.name] = [ o[this.name] ];
			}
			o[this.name].push(this.value || '');
		} else {
			//alert( this.value);
			o[this.name] = this.value || '';
		}
	});
	return o;
};

//表单数据转成json数据
jQuery.fn.funForm2Json = function() {
	return JSON.stringify(this.funForm2Object());
};


//清除查询
function clearQuery() {
	var parent = jQuery("td.search-form");
	//	var inputArray = jQuery("input", parent);
	var inputArray = parent.find("input");
	inputArray.each(function(i) {
		var element = jQuery(this);
		element.val("");
	});
}
//清除查询
function clearQuery(parentId,childrenId) {
	var parent = jQuery(parentId);
	var inputArray = parent.find(childrenId);
	inputArray.each(function(i) {
		var element = jQuery(this);
		element.val("");
	});
}