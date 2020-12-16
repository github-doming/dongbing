/**
 * 
 * table_Load延迟加载
 * 
 *  
 * @author 
 * @description 
 * @date 2006.5.28
 */
/**
 * 声明
 * 
 */
//edit_path
var ayTreeLoad_edit_path = null;


// 表格id
var ayTreeLoad_id_table = null;
// key
var ayTreeLoad_key = null;
// action
var ayTreeLoad_action = null;
// 首次加载
var ayTreeLoad_load_first = true;
// 加载第一个节点
var ayTreeLoad_node_first = true;
// 图片
var ayTreeLoad_img_file_b = '/pages/c/x/imports/compo/tree_table/images/v1/file.gif';
var ayTreeLoad_img_folder_close_b = '/pages/c/x/imports/compo/tree_table/images/v1/folder-closed.gif';
var ayTreeLoad_img_folder_open_b = '/pages/c/x/imports/compo/tree_table/images/v1/folder.gif';
var ayTreeLoad_img_file_a = '/pages/c/x/imports/compo/tree_table/images/v2/file.gif';
var ayTreeLoad_img_folder_close_a = '/pages/c/x/imports/compo/tree_table/images/v2/folder-closed.gif';
var ayTreeLoad_img_folder_open_a = '/pages/c/x/imports/compo/tree_table/images/v2/folder.gif';
/**
 * 初始化参数
 * 
 */
// 临时id;指定在那一行的tr下面增加;
var ayTreeLoad_id_tr$temp = "ayTreeLoad_id_tr$temp";
/**
 * 初始化
 * @param key
 * @param id_table
 * @param cp
 * @param action
 * @param ay$parent_id
 * @param path
 * @return
 */




function ayTreeLoad_init(key,id_table,cp, action, ay$parent_id, path) {
	//alert('path='+path);
	// 1初始化参数
	
	//ayTreeLoad_edit_path
	ayTreeLoad_edit_path=path;
	
	// table id
	ayTreeLoad_id_table = id_table;
	// key
	ayTreeLoad_key = key;
	// action
	ayTreeLoad_action = action;
	// 图片
	ayTreeLoad_img_file_b = cp + ayTreeLoad_img_file_b;
	// alert('ayTreeLoad_img_file_b='+ayTreeLoad_img_file_b);
	ayTreeLoad_img_folder_close_b = cp + ayTreeLoad_img_folder_close_b;
	ayTreeLoad_img_folder_open_b = cp + ayTreeLoad_img_folder_open_b;
	ayTreeLoad_img_file_a = cp + ayTreeLoad_img_file_a;
	ayTreeLoad_img_folder_close_a = cp + ayTreeLoad_img_folder_close_a;
	ayTreeLoad_img_folder_open_a = cp + ayTreeLoad_img_folder_open_a;
	// 2加载json数据
	ayTreeLoad_load(ay$parent_id);
	// 首次加载
	ayTreeLoad_load_first = false;
}
/**
 * 1;ajax请求，加载json数据;
 * 
 * 2;隐藏与显示;
 * 
 * @param ay$parent_id
 * @return
 */
function ayTreeLoad_load(ay$parent_id) {
	// 2父节点的id
//临时id;指定在那一行的tr下面增加;
	ayTreeLoad_id_tr$temp = "id_tr_" + ay$parent_id + "_" + ayTreeLoad_key;
	var obj_parent$tr = jQuery('#' + ayTreeLoad_id_tr$temp);
	// 3找到属性load
	var attr$parent$load = obj_parent$tr.attr("load");
	// alert('attr$parent$load='+attr$parent$load);
	if (attr$parent$load == 'true') {
	} else {
		// 5 ajax请求
		ayTreeLoad_ajax(ay$parent_id);
	}
	// 6 隐藏与显示
	ayTreeLoad_change(ay$parent_id);
}
/**
 * 加载json
 * 
 * @param ay$parent_id
 * @return
 */
function ayTreeLoad_ajax(ay$parent_id) {
	// 2ajax请求
	var ajax_url = cp + "/" + ayTreeLoad_action + '?parent_id='
			+ ay$parent_id+"&&path="+ayTreeLoad_edit_path;
	//alert('action='+ajax_url);
	jQuery.ajax( {
		url : ajax_url,
		type : "POST",
		async : false,
		cache : true,
		data : $('#form_id_a').serialize(),// 你的formid
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		error : function(request) {
			alert("Connection error");
		},
		success : function(input_data, input_status) {
			// 1状态与data
		if (false) {
			alert("Connection success,input_status=" + input_status);
			alert('input_data=' + input_data);
		}
		// 2解析json
		var json_data = jQuery.parseJSON(input_data);
		if (false) {
			alert('count=' + json_data.count);
		}
		// 3;data编历;
		var html_data = json_data.data;
		// alert('html_data='+html_data);
		// alert('html_data.length='+html_data.length);
		for ( var i = 0; i < html_data.length; i++) {
			// alert(html_data[i].id);
		// alert('size='+html_data[i].size);
		var html_data$id = html_data[i].id;
		var html_data$parent_id = html_data[i].parent;
		if (html_data$parent_id == null) {
			alert('请检查后台代码，找不到parent_id');
		}
		var size = html_data[i].size;
		if (size == null) {
			alert('请检查后台代码，找不到size');
			size = 0;
		}
		var custom = html_data[i].custom;
		//alert('custom='+custom );
		if (custom == null) {
			alert('请检查后台代码，找不到custom');
			custom = "";
		}
		var path = html_data[i].path;
		if (path == null) {
			alert('请检查后台代码，找不到path');
			path = "";
		}
		// 添加一行
		ayTreeLoad_add(size, html_data$id, html_data$parent_id, path,
				html_data[i].name, custom);
		// 临时id，第一个孩子节点的id
		ayTreeLoad_id_tr$temp = "id_tr_" + html_data$id + "_" + ayTreeLoad_key;
	}
	// 5设置tr的属性
	// 已经加载
	var id_tr$parent = "id_tr_" + ay$parent_id + "_" + ayTreeLoad_key;
	var obj_tr$parent = jQuery('#' + id_tr$parent);
	obj_tr$parent.attr("load", 'true');
	// 6结束
}
	});
	// ajax请求
}
/**
 * 增加一行
 * 
 * @param size
 * @param id
 * @param parent_id
 * @param path
 * @param name
 * @param custom
 * @return
 */
function ayTreeLoad_add(size, id, parent_id, path, name, custom) {
	// 1指定在那一行的tr下面增加
	var obj_tr = jQuery('#' + ayTreeLoad_id_tr$temp);
	// 2空格
	var html$space = null;
	if (true) {
		var ay$space = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		// 父节点
		var obj$span_layer$parent = null;
		var html$span_layer$parent = null;
		// 首次加载
		if (ayTreeLoad_load_first) {
			ay$space = "";
			html$span_layer$parent = "";
		} else {
			// alert('parent_id='+parent_id);
			obj$span_layer$parent = jQuery('#id_span_layer_' + parent_id + "_"
					+ ayTreeLoad_key);
			// alert('obj$span_layer$parent='+obj$span_layer$parent);
			html$span_layer$parent = obj$span_layer$parent.html();
			if (false) {
				if ("undefined" == typeof (html$span_layer$parent)) {
					// alert('a');
					html$span_layer$parent = "";
				}
			}
			// alert('html$span_layer$parent='+html$span_layer$parent);
		}
		var html_layer = "<span id='id_span_layer_" + id + "_"
				+ ayTreeLoad_key + "'>" + html$span_layer$parent + ay$space
				+ "</span>";
		html$space = html_layer;
	}
	// 3图片1
	var html_img_a = "";
	if (true) {
		var img_src = null;
		if (size == 0) {
			img_src = ayTreeLoad_img_file_a;
		} else {
			img_src = ayTreeLoad_img_folder_close_a;
		}
		var id_img_a = 'id_img_a_' + id + "_" + ayTreeLoad_key;
		var html_name = "<img id='" + id_img_a
				+ "'  onclick=' ayTreeLoad_load(" + id + ");'  src='"
				+ img_src + "'>";
		html_img_a = html_name;
	}
	// 5图片2
	var html_img_b = null;
	if (true) {
		var img_src = null;
		if (size == 0) {
			// alert('1 ayTreeLoad_img_file_b='+ayTreeLoad_img_file_b);
			img_src = ayTreeLoad_img_file_b;
		} else {
			img_src = ayTreeLoad_img_folder_close_b;
		}
		// alert('img_src='+img_src );
		// var id_img_a = 'id_img_a_' + id;
		var id_img_b = 'id_img_b_' + id + "_" + ayTreeLoad_key;
		var html_name = "<img id='" + id_img_b
				+ "'  onclick=' ayTreeLoad_load(" + id + ");'  src='"
				+ img_src + "'>";
		html_img_b = html_name;
	}
	// 6名称
	var html$name = null;
	if (true) {
		// html$name = name;
		html$name = "<input  class='btn btn-link' type='button' value='" + name
				+ "'></input>";
	}
	// 7拼装
	var html_td = html$space + html_img_a + html_img_b + html$name;
	var html_tr = null;
	var html_tr$id = "id_tr_" + id + "_" + ayTreeLoad_key;
	var html_load = "false";
	var html_state = 'close';
	var html_path = path;
	var html_leaf = "";
	if (size == 0) {
		html_leaf = 'false';
	} else {
		html_leaf = 'true';
	}
	if (true) {
		html_tr = "<tr leaf='" + html_leaf + "'  id='" + html_tr$id
				+ "' path='" + html_path + "' load='" + html_load + "' state='"
				+ html_state + "' >";
		// alert('custom='+custom);
		if (false) {
			// 显示主键
			html_tr = html_tr + "<td>" + id + "</td><td>" + html_td + "</td>"
					+ custom + "</tr>";
		}
		// 不显示主键
		html_tr = html_tr + "<td>" + html_td + "</td>" + custom + "</tr>";
	}
	// 8增加一行
	// 首次加载数据
	// 加载第一个节点
	if (ayTreeLoad_load_first && ayTreeLoad_node_first) {
		// alert('thead ayTreeLoad_id_tr$temp='+ayTreeLoad_id_tr$temp);
		obj_tr = jQuery('#' + ayTreeLoad_id_table + " thead:first");
		// alert('thead='+ obj_tr.html());
		// 追加
		obj_tr.after(html_tr);
		// //加载第一个节点
		// 设置false
		ayTreeLoad_node_first = false;
	} else {
		obj_tr.after(html_tr);
	}
}
/**
 * 设置状态
 * 
 * @param id
 * @return
 */
function ayTreeLoad_state(id) {
	// alert('id='+id);
	// tr
	var id_tr = "id_tr_" + id + "_" + ayTreeLoad_key;
	// 图片1
	var id_img_a = "id_img_a_" + id + "_" + ayTreeLoad_key;
	// 图片2
	var id_img_b = "id_img_b_" + id + "_" + ayTreeLoad_key;
	// 对象
	var obj_tr = jQuery('#' + id_tr);
	var state = obj_tr.attr("state");
	var leaf_root = obj_tr.attr("leaf");
	var obj_img_a = jQuery('#' + id_img_a);
	var obj_img_b = jQuery('#' + id_img_b);
	if (state == 'open') {
		// 设置状态为关闭
		obj_img_a.attr("src", ayTreeLoad_img_folder_close_a);
		obj_img_b.attr("src", ayTreeLoad_img_folder_close_b);
		obj_tr.attr("state", 'close');
	}
	if (state == 'close') {
		// 设置状态为打开
		obj_img_a.attr("src", ayTreeLoad_img_folder_open_a);
		obj_img_b.attr("src", ayTreeLoad_img_folder_open_b);
		obj_tr.attr("state", 'open');
	}
	if ("undefined" == typeof (state)) {
		// alert('2');
		// 设置状态为打开
		// alert('obj_img_a='+obj_img_a);
		obj_img_a.attr("src", ayTreeLoad_img_folder_open_a);
		obj_img_b.attr("src", ayTreeLoad_img_folder_open_b);
		obj_tr.attr("state", 'open');
	}
}
/**
 * 显示所有的孩子跟孙子
 * 
 * @param id_tr_root
 * @return
 */
function ayTreeLoad_show_all_childs(id_tr_root) {
	// var id_tr_root = "id_tr_" + id_root;
	var path_root = $("#" + id_tr_root).attr("path");
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
 * 
 * @param msg
 * @param id_tr_root
 * @return
 */
function ayTreeLoad_hide_all_childs(msg, id_tr_root) {
	// var id_tr_root = "id_tr_" + id_root;
	if (false) {
		alert('msg=' + msg);
		alert('隐藏所有的孩子跟孙子 id_tr_root=' + id_tr_root);
	}
	var path_root = $("#" + id_tr_root).attr("path");
	$("tr").each(function() {
		var path = $(this).attr("path");
		if ("undefined" == typeof (path)) {
		} else {
			if (path == path_root) {
			} else {
				// 查找所有孩子和孙子
			if (path.indexOf(path_root + "") == 0) {
				// 设置style
				if (false) {
					alert('path=' + path);
				}
				$(this).attr("style", "display: none;");
			}
		}
	}
}	);
}
/**
 * 
 * 隐藏与显示
 * 
 * @param id_root
 * @return
 */
function ayTreeLoad_change(id_root) {
	// alert('1');
	try {
		// alert('id_root='+id_root);
		var id_tr_root = "id_tr_" + id_root + "_" + ayTreeLoad_key;
		// alert('id_tr_root ='+id_tr_root );
		var path_root = $("#" + id_tr_root).attr("path");
		// alert('path_root='+path_root);
		var state_root = $("#" + id_tr_root).attr("state");
		// alert('state_roott='+state_root);
		// 本身状态
		if (state_root == 'open') {
			// 隐藏所有的孩子跟孙子
			var msg = '关闭';
			ayTreeLoad_hide_all_childs(msg, id_tr_root);
		}
		if (state_root == 'close') {
			// 显示所有的孩子和孙子
			// {
			ayTreeLoad_show_all_childs(id_tr_root);
			// }
			// 显示所有的孩子和孙子
			// 查找所有的孩子和孙子
			jQuery("tr").each(function() {
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
								var msg = '打开';
								ayTreeLoad_hide_all_childs(msg, id);
							}
						}
					}
				}
			});
			// 隐藏所有的孩子和孙子
		}
		// 设置本身状态
		ayTreeLoad_state(id_root);
		// alert('end');
	} catch (e) {
		alert('异常=' + e.stack);
	}
}