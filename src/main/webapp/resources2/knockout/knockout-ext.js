//Save form data
function saveData(url, vm) {
	var status = false;
	try {
		$.ajax({
			type : "POST",
			dataType : "json",
			contentType : "application/json;charset=UTF-8",
			url : url,
			cache : false,
			async : false,
			data : JSON.stringify(ko.mapping.toJS(vm)),
			success : function(data) {
				if (data.status)
					status = data.status;
			}
		});
	} catch (e) {
		console.log(e);
	}
	return status;
}

//Load form data
function loadData(url, params) {
	var jsonData = null;
	try {
		$.ajax({
			dataType : "json",
			url : url,
			cache : false,
			async : false,
			data : params,
			success : function(data) {
				jsonData = data.entity;
			}
		});
	} catch (e) {
		console.log(e);
	}
	return jsonData;
}