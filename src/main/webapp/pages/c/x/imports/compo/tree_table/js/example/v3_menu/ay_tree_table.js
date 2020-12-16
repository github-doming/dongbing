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
 * 日志
 */
// console.log("加载js");
/**
 * 定义
 */
if ("undefined" == typeof (Ay) || !Ay || Ay == null) {
	var Ay = {};
}
if ("undefined" == typeof (	Ay.tree_table) || !	Ay.tree_table || 	Ay.tree_table == null) {
	Ay.tree_table = {};
}

/**
 * 显示所有的孩子跟孙子
 */

Ay.tree_table.show_all_childs = function(cp, id_root) {

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
Ay.tree_table.hide_all_childs = function(cp, id_root) {

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
Ay.tree_table.set_state = function(model, cp, id_root, id_tr_img,
		id_tr_img_custom, img_custom_open, img_custom_close) {

	var leaf_root = $("#" + id_root).attr("leaf");
	var state_root = $("#" + id_root).attr("state");

	// 设置本身状态
	if (true) {

		if (state_root == 'open') {
			// 设置本身图片

			if (leaf_root = 'false') {
				if (model=='menu') {
$("#i_" +  id_root).attr("class","icon-chevron-right");

					
}else{
					
					$("#" + id_tr_img).attr("src",cp+ "/pages/c/x/platform/root/common/tree_table/images/v2/folder-closed.gif");
					
				}
				

				// alert(' img_custom_close='+ img_custom_close);
				$("#" + id_tr_img_custom).attr("src", cp + img_custom_close);

			}

			// 设置状态
			$("#" + id_root).attr("state", "close");
		} else {

			// 设置本身图片

			if (leaf_root == 'false') {

				if (model=='menu') {
					$("#i_" +  id_root).attr("class","icon-chevron-down");
					
				
				}else{
					$("#" + id_tr_img)
					.attr("src",cp+ "/pages/c/x/platform/root/common/tree_table/images/v2/folder.gif");
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
Ay.tree_table.change = function(model, cp, id_root, id_tr_img,
		id_tr_img_custom, img_custom_open, img_custom_close) {

	//alert('1');
	
	var leaf_root = $("#" + id_root).attr("leaf");
	var path_root = $("#" + id_root).attr("path");
	var state_root = $("#" + id_root).attr("state");

	// 本身状态
	if (state_root == 'open') {

		// 隐藏所有的孩子跟孙子
		Ay.tree_table.hide_all_childs(cp, id_root);
	}
	if (state_root == 'close') {

		// 显示所有的孩子和孙子

		Ay.tree_table.show_all_childs(cp, id_root);
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
							Ay.tree_table.hide_all_childs(cp, id);

						}

					}

				}
			}
		});
		// 隐藏所有的孩子和孙子

	}

	// 设置本身状态

	Ay.tree_table.set_state(model, cp, id_root, id_tr_img, id_tr_img_custom,
			img_custom_open, img_custom_close);

	// alert('end');
}
