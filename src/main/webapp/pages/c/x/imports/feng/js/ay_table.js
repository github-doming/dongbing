/**
 * 
 * 依赖
 * 
 * 
 * @description 用于table的js
 * 
 * 
 */
/**
 * 日志
 */
// console.log("加载js");
/**
 * @author 陈俊先
 * @description 隐藏列;
 * 
 * 用法;
 * 
 * 1指定td的id;
 * 
 * 2指定option value="id"(匹配td的id);
 * 
 * 
 * @param {}
 *            c_obj下拉列表控件select的this
 * @param {}
 *            c_id_table表的id
 * @param {}
 *            c_row从第几行开始显示值
 */
// ayTableHiddenColumn = function(c_obj, c_id_table, c_row) {
function ayTableHiddenColumn(c_obj, c_id_table, c_row) {
	try {
		var j = c_row - 1;
		// alert(j);
		var c_element_table = document.getElementById(c_id_table);

		if ("undefined" == typeof (c_element_table.rows[j])) {
			// alert("undefined c_element_table.rows[j]") ;
			return;
		}
		if (null == c_element_table.rows[j]) {
			// alert("null c_element_table.rows[j]") ;
			return;
		}

		var c_element_table_cells_length = c_element_table.rows[j].cells.length;
		var c_selectedIndex_value = c_obj[c_obj.selectedIndex].value;
		var c_element_td = document.getElementsByTagName("td");
		for ( var i = 0; i < c_element_td.length; i++) {
			if ("undefined" == typeof (c_element_td[i].id)
					|| !c_element_td[i].id) {
				continue;
			}
			/**
			 * 判断value是否相等
			 */
			// alert('c_selectedIndex_value='+c_selectedIndex_value);
			// alert('c_element_td[i].id='+c_element_td[i].id);
			if (c_element_td[i].id == c_selectedIndex_value) {
				// alert(c_element_td[i].innerHTML);
				/**
				 * 放到最后一列
				 */
				// alert(c_element_table_cells_length);
				c_element_table.rows[j].cells[c_element_table_cells_length - 1].innerHTML = c_element_td[i].innerHTML;
				j = j + 1;
			}
		}
	} catch (e) {
		
		alert(e.stack);
	}
}
/**
 * 全选
 */
// ayTableCheckAll = function() {
function ayTableCheckAll() {
	var obj_name_checkbox_ids = document.getElementsByName("name_checkbox_ids");
	var obj_ifAll = document.getElementById("id_checkbox_if_all");
	for ( var i = 0; i < obj_name_checkbox_ids.length; i++) {
		obj_name_checkbox_ids[i].checked = obj_ifAll.checked;
	}
}
/**
 * 编辑(复选框checkbox)时,加载所有被选中的id
 * 
 * @param {}
 *            value_ids
 * @param {}
 *            name_checkbox_ids
 */
// ayTableCheckboxEditLoadIds = function(value_ids, name_checkbox_ids) {
function ayTableCheckboxEditLoadIds(value_ids, name_checkbox_ids) {
	// alert('value_ids='+value_ids);
	var alt_paths_array = value_ids.split(',');
	var var_checkbox = document.getElementsByName(name_checkbox_ids);
	// alert('var_checkbox.length='+var_checkbox.length);
	// alert('alt_paths_array.length='+alt_paths_array.length);
	for ( var i = 0; i < var_checkbox.length; i++) {
		for ( var j = 0; j < alt_paths_array.length; j++) {
			if (var_checkbox[i].value == alt_paths_array[j]) {
				var_checkbox[i].checked = true;
			}
		}
	}
}