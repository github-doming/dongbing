function cyui_select(idDiv) {
	//var selectDiv = $(".cyui_select");
	var selectDiv = $("#"+idDiv);
	var ajax_url = selectDiv.attr("url");
	var defValue = selectDiv.attr("defValue");
	var selectName = selectDiv.attr("name");
	// json数据定义
	var selectDef = {
		"id" : selectDiv.attr("idColumn"),
		"text" : selectDiv.attr("textColumn")
	}
	jQuery.ajax({
		url : ajax_url,
		type : "POST",
		async : false,
		// async: true,
		cache : true,
		contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
		error : function(request) {
			alert("Connection error");
		},
		success : function(responseData, status) {
			// alert("Connection success,status=" + status);
			// alert('responseData=' + responseData);
			data = JSON.parse(responseData);
			// alert('data=' + data);
			// data= data1;
			var selectE = document.createElement("select");
			selectE.id = idDiv + "_select";
			selectE.name = selectName;
			// 添加一个选项
			if (defValue == null || defValue == "" || defValue == "NaN"
					|| defValue == "undefined") {
				$.each(data, function(i, row) {
					selectE.options.add(new Option(row[selectDef.text],
							row[selectDef.id], row.def_, row.def_)); // 这个兼容IE与firefox
					selectDiv.append(selectE);
				});
			} else {
				$.each(data, function(i, row) {
					if (defValue == row[selectDef.id]) {
						selectE.options.add(new Option(row[selectDef.text],
								row[selectDef.id], true, true)); // 这个兼容IE与firefox
						selectDiv.append(selectE);
					} else {
						selectE.options.add(new Option(row[selectDef.text],
								row[selectDef.id], false, false)); // 这个兼容IE与firefox
						selectDiv.append(selectE);
					}
				});
			}
		}
	});
	// ajax请求
};