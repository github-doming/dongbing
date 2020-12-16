/**
 * 
 * 依赖
 * 
 * 
 * @description 用于tree table的js
 * 
 * 
 */
/**
 * 日志ayTreeTable_
 */
// console.log("加载js");
/**
 * 显示所有的孩子跟孙子
 */
// ayTreeTable_show_all_childs = function(cp, id_root) {
function ayTreeTable_show_all_childs(cp, id_root) {
	var path_root = $("#" + id_root).attr("path");
	$("tr").each(function() {
		var path = $(this).attr("path");
		if ("undefined" == typeof (path)) {
		} else {
			// 查找所有孩子和孙子
			if (path.indexOf(path_root + "") == 0) {
				// 设置style
				$(this).attr("style", "");
			}
		}
	});
}
/**
 * 隐藏所有的孩子跟孙子
 */
// ayTreeTable_hide_all_childs = function(cp, id_root) {
function ayTreeTable_hide_all_childs(cp, id_root) {
	var path_root = $("#" + id_root).attr("path");
	$("tr").each(function() {
		var path = $(this).attr("path");
		if ("undefined" == typeof (path)) {
		} else {
			if (path == path_root) {
			} else {
				// 查找所有孩子和孙子
			if (path.indexOf(path_root + "") == 0) {
				// 设置style
				$(this).attr("style", "display: none;");
			}
		}
	}
}	);
}
/**
 * 设置本身状态
 */
// ayTreeTable_set_state = function(model, cp, id_root,
// id_tr_img,id_tr_img_custom, img_custom_open, img_custom_close) {
function ayTreeTable_set_state(model, cp, id_root, id_tr_img,
		id_tr_img_custom, img_custom_open, img_custom_close) {
	var leaf_root = $("#" + id_root).attr("leaf");
	var state_root = $("#" + id_root).attr("state");
	// 设置本身状态
	if (true) {
		if (state_root == 'open') {
			// 设置本身图片
			if (leaf_root = 'false') {
				if (model == 'menu') {
					$("#i_" + id_root).attr("class", "icon-chevron-right");
				} else {
					$("#" + id_tr_img)
							.attr(
									"src",
									cp
											+ "/pages/c/x/imports/compo/tree_table/images/v2/folder-closed.gif");
				}
				// alert(' img_custom_close='+ img_custom_close);
				$("#" + id_tr_img_custom).attr("src", cp + img_custom_close);
			}
			// 设置状态
			$("#" + id_root).attr("state", "close");
		} else {
			// 设置本身图片
			if (leaf_root == 'false') {
				if (model == 'menu') {
					$("#i_" + id_root).attr("class", "icon-chevron-down");
				} else {
					$("#" + id_tr_img)
							.attr(
									"src",
									cp
											+ "/pages/c/x/imports/compo/tree_table/images/v2/folder.gif");
				}
				$("#" + id_tr_img_custom).attr("src", cp + img_custom_open);
			}
			// 设置状态
			$("#" + id_root).attr("state", "open");
		}
	}
	// 设置本身状态
}
/**
 * 隐然与显示
 */
// ayTreeTable_change = function(model, cp, id_root,
// id_tr_img,id_tr_img_custom, img_custom_open, img_custom_close) {
function ayTreeTable_change(model, cp, id_root, id_tr_img, id_tr_img_custom,
		img_custom_open, img_custom_close) {
	// alert('1');
	try {
		var leaf_root = $("#" + id_root).attr("leaf");
		var path_root = $("#" + id_root).attr("path");
		var state_root = $("#" + id_root).attr("state");
		// 本身状态
		if (state_root == 'open') {
			// 隐藏所有的孩子跟孙子
			ayTreeTable_hide_all_childs(cp, id_root);
		}
		if (state_root == 'close') {
			// 显示所有的孩子和孙子
			ayTreeTable_show_all_childs(cp, id_root);
			// 显示所有的孩子和孙子
			// 查找所有的孩子和孙子
			$("tr").each(function() {
				var path = $(this).attr("path");
				if ("undefined" == typeof (path)) {
				} else {
					// 查找所有孩子和孙子
					if (path == path_root) {
					} else {
						if (path.indexOf(path_root + "") == 0) {
							var state = $(this).attr("state");
							var id = $(this).attr("id");
							if (state == 'close') {
								// 隐藏所有的孩子跟孙子
								ayTreeTable_hide_all_childs(cp, id);
							}
						}
					}
				}
			});
			// 隐藏所有的孩子和孙子
		}
		// 设置本身状态
		ayTreeTable_set_state(model, cp, id_root, id_tr_img, id_tr_img_custom,
				img_custom_open, img_custom_close);
		// alert('end');
	}catch (e) {
		alert('异常='+e.stack);
	}
}
// check模型的事件
// {
/**
 * check模型的事件;
 * 
 * 全选;
 * 
 * @param {}
 *            var_input
 * @param {}
 *            key
 */
// ayTreeTable_checkbox_all = function(var_input, key) {
function ayTreeTable_checkbox_all(var_input, key) {
	var var_checkbox = document.getElementsByName('name_checkbox' + "_" + key);
	var alt = var_input.alt;
	for ( var i = 0; i < var_checkbox.length; i++) {
		//所有的孩子节点被选中
		//if (var_checkbox[i].alt.indexOf(alt) == 0) {
		//所有的父亲节点也被选中
		
		if(var_input.checked){
			if (var_checkbox[i].alt.indexOf(alt) == 0||alt.indexOf(var_checkbox[i].alt) == 0) {
				var_checkbox[i].checked = var_input.checked;
			}
			
		}else{
			
			if (var_checkbox[i].alt.indexOf(alt) == 0) {
				var_checkbox[i].checked = var_input.checked;
			}
		}
		
			
	}
}
/**
 * check模型的事件;
 * 
 * 保存(暂时不用该方法);
 */
// ayTreeTable_checkbox_save = function(key) {
function ayTreeTable_checkbox_save(key) {
	var var_checkbox = document.getElementsByName('name_checkbox' + "_" + key);
	for ( var i = 0; i < var_checkbox.length; i++) {
		if (var_checkbox[i].checked) {
			alert(var_checkbox[i].value);
		}
	}
}
/**
 * check模型的事件;
 * 
 * 编辑;
 */
// ayTreeTable_checkbox_edit = function(alt_paths, key) {
function ayTreeTable_checkbox_edit(alt_paths, key) {
	// var alt_paths='1_2_3_21,1_2_3_4_7';
	var alt_paths_array = alt_paths.split(',');
	var var_checkbox = document.getElementsByName('name_checkbox' + "_" + key);
	for ( var i = 0; i < var_checkbox.length; i++) {
		for ( var j = 0; j < alt_paths_array.length; j++) {
			if (var_checkbox[i].alt == alt_paths_array[j]) {
				var_checkbox[i].checked = true;
			}
		}
	}
}
//}
//check模型的事件
