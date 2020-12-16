var columnArray =null;
var tree_ay =null;
//json数据定义
var treeDef = null;
function cyui_treegrid_div_init() {
	tree_ay = $(".cyui_treegrid_div");
	var divId = tree_ay.attr("id");
	var url = tree_ay.attr("url");
	//alert('url='+url);
	//根节点父id
	var rootParentId = tree_ay.attr("rootParentId");
	//json数据定义
	treeDef = {
		"id": tree_ay.attr("idField"),
		"name": tree_ay.attr("treeColumn"),
		"parentId": tree_ay.attr("parentField")
	}
	columnArray = cyui_treegrid_div_initColumn(tree_ay);
	//cyui_treegrid_div_addNode(tree_ay, row);
	//cyui_treegrid_div_AfterNode(tree_ay, row);
	//cyui_treegrid_div_BeforeNode(tree_ay, row);
	var dataR = null;
}
function cyui_treegrid_div_initData() {
		//alert('1');
		cyui_treegrid_div_init();
		//初始化  by cjx
		//$(function() {
		//ajax请求
		var name = $("#nameId").val();
		var password = $("#passwordId").val();
		var json = {
			name: name,
			password: password
		}
		var jsonStr = JSON.stringify(json);
		var ajax_url = tree_ay.attr("url");
		jQuery.ajax({
			url: ajax_url,
			//type: "POST",
			type: "GET",
			async: false,
			//  async: true,
			cache: true,
			data: {
				json: jsonStr
			},
			contentType: 'application/x-www-form-urlencoded;charset=UTF-8',
			error: function(request) {
				alert("Connection error");
			},
			success: function(responseData, status) {
				//console.log("1 Connection success,status=" + status);
				//console.log("2 responseData=" + responseData);
				dataR = JSON.parse(responseData);
				//console.log("3 dataR =" + dataR );
				//dataR = responseData;
				//cyui_treegrid_div_bindData(dataR);
			}
		});
		//ajax请求
		//初始化，绑定数据
		var tagR = "";
		//动态创建树形菜单
		cyui_treegrid_div_rootBindData(tree_ay,dataR, tagR);
		$(".parent").click(function() {
			cyui_treegrid_div_rootOpenOrClose(this, 500);
		});
		//cyui_treegrid_div_openOrClose($("#1"), 0);
		//});
}
//循环绑定数据
function cyui_treegrid_div_rootBindData(tree_ay,data, tag) {
		for(var i = 0; i < data.length; i++) {
			var childArray = data[i].children;
			//console.log("childArray=" + childArray);
			//console.log("childArray.length=" + childArray.length);
			cyui_treegrid_div_addRootNode(tree_ay, data[i], tag, childArray.length);
			$.each(childArray, function(index, child) {
				tag = "&nbsp;&nbsp;&nbsp;&nbsp;";
				cyui_treegrid_div_bindData(child, tag);
			});
		}
}
//循环绑定数据
function cyui_treegrid_div_bindData(data, tagParent) {
		var childArray = data.children;
		cyui_treegrid_div_addNode(tree_ay, data, tagParent, childArray.length);
		$.each(childArray, function(index, child) {
			var tag = tagParent + "&nbsp;&nbsp;&nbsp;&nbsp;";
			cyui_treegrid_div_bindData(child, tag);
		});
}
//打开或关闭,by cjx
function cyui_treegrid_div_openOrClose(nodeObject, millisecond) {
		//id
		var parentId = $(nodeObject).attr("id");
		//状态
		var state = $(nodeObject).attr("state");
		var stateInt = parseInt(state);
		//console.log('parentId=' + parentId);
		//console.log('state=' + stateInt);
		//节点数
		var nodes = $(nodeObject).attr("nodes");
		var nodesInt = parseInt(nodes);
		switch(stateInt) {
			//状态是展开，需要关闭
			case 1:
				var childArray = $('*[parent="' + parentId + '"]');
				//console.log('child.length=' + childArray.length);
				$(nodeObject).attr("state", "2");
				if(nodesInt == 0) {
					$(nodeObject).find("img").attr("src",file_gif);
				} else {
					$(nodeObject).find("img").attr("src", folder-closed.gif);
				}
				$.each(childArray, function(index, child) {
					$(child).hide(millisecond);
					cyui_treegrid_div_openOrClose(child, millisecond);
				});
				break;
				//状态是关闭的，需要展开
			case 2:
				var childArray = $('*[parent="' + parentId + '"]');
				$(nodeObject).attr("state", "1");
				if(nodesInt == 0) {
					$(nodeObject).find("img").attr("src", file_gif);
				} else {
					$(nodeObject).find("img").attr("src", folder_gif);
				}
				$.each(childArray, function(index, child) {
					$(child).show(millisecond);
					cyui_treegrid_div_openOrClose(child, millisecond);
				});
				break;
			default:
				break;
		}
}
//打开或关闭,by cjx
function cyui_treegrid_div_rootOpenOrClose(nodeObject, millisecond) {
		//id
		var parentId = $(nodeObject).attr("id");
		//状态
		var state = $(nodeObject).attr("state");
		var stateInt = parseInt(state);
		//console.log('parentId=' + parentId);
		//console.log('state=' + stateInt);
		//节点数
		var nodes = $(nodeObject).attr("nodes");
		var nodesInt = parseInt(nodes);
		switch(stateInt) {
			//状态是展开，需要关闭
			case 1:
				var childArray = $('*[parent="' + parentId + '"]');
				//console.log('child.length=' + childArray.length);
				$(nodeObject).attr("state", "2");
				if(nodesInt == 0) {
					$(nodeObject).find("img").attr("src", file_gif);
				} else {
					$(nodeObject).find("img").attr("src", folder_closed_gif);
				}
				$.each(childArray, function(index, child) {
					$(child).hide(millisecond);
					cyui_treegrid_div_openOrClose(child, stateInt,millisecond);
				});
				break;
				//状态是关闭的，需要展开
			case 2:
				var childArray = $('*[parent="' + parentId + '"]');
				$(nodeObject).attr("state", "1");
				if(nodesInt == 0) {
					$(nodeObject).find("img").attr("src", file_gif);
				} else {
					$(nodeObject).find("img").attr("src", folder_gif);
				}
				$.each(childArray, function(index, child) {
					$(child).show(millisecond);
					cyui_treegrid_div_openOrClose(child, stateInt,millisecond);
				});
				break;
			default:
				break;
		}
}
//打开或关闭,by cjx
function cyui_treegrid_div_openOrClose(nodeObject, stateInt,millisecond) {
		//id
		var parentId = $(nodeObject).attr("id");
		//状态
		//console.log('parentId=' + parentId);
		//console.log('state=' + stateInt);
		//节点数
		var nodes = $(nodeObject).attr("nodes");
		var nodesInt = parseInt(nodes);
		switch(stateInt) {
			//状态是展开，需要关闭
			case 1:
				var childArray = $('*[parent="' + parentId + '"]');
				//console.log('child.length=' + childArray.length);
				$(nodeObject).attr("state", "2");
				if(nodesInt == 0) {
					$(nodeObject).find("img").attr("src",file_gif);
				} else {
					$(nodeObject).find("img").attr("src", folder_closed_gif);
				}
				$.each(childArray, function(index, child) {
					$(child).hide(millisecond);
					cyui_treegrid_div_openOrClose(child, stateInt,millisecond);
				});
				break;
				//状态是关闭的，需要展开
			case 2:
				var childArray = $('*[parent="' + parentId + '"]');
				$(nodeObject).attr("state", "1");
				if(nodesInt == 0) {
					$(nodeObject).find("img").attr("src",file_gif);
				} else {
					$(nodeObject).find("img").attr("src", folder_gif);
				}
				$.each(childArray, function(index, child) {
					$(child).show(millisecond);
					cyui_treegrid_div_openOrClose(child, stateInt,millisecond);
				});
				break;
			default:
				break;
		}
}
//初始化列
function cyui_treegrid_div_initColumn(table_ay) {
		var columnArray = new Array();
		table_ay = $(".cyui_treegrid_div");
		var columnElementArray = table_ay.children('div[property="columns"]').find("div");
		$.each(columnElementArray, function(i, item) {
			columnArray[columnArray.length] = $(item).attr("field");
		});
		return columnArray;
}
//添加root节点
function cyui_treegrid_div_addRootNode(tree_ay, row, tag, nodes) {
		//console.log('id=' + row[treeDef.id]);
		//console.log('parentId=' + row[treeDef.parentId]);
		var divParentElement = document.createElement("div");
		divParentElement.setAttribute("class", "list-group-item parent");
		divParentElement.setAttribute("state", "2");
		//divParentElement.setAttribute("style", "display: none;");
		divParentElement.setAttribute("id", "" + row[treeDef.id] + "");
		divParentElement.setAttribute("parent", "" + row[treeDef.parentId] + "");
		divParentElement.setAttribute("nodes", nodes);
		var divElement = null;
		$.each(columnArray, function(index, column) {
			//添加节点div
			divElement = document.createElement("div");
			if(column == 'text') {
				//divElement.innerHTML = tag + row[column];
				//divElement.innerHTML = tag + "<img src='../img/file.gif'></img>" + row[column];
				divElement.innerHTML = tag + "<img src='"+folder_closed_gif+"'></img>" + row[column];
				divElement.setAttribute("class", "list-group-item");
				divParentElement.append(divElement);
			} else {
				divElement.innerHTML = row[column];
				divElement.setAttribute("class", "list-group-item");
				divParentElement.append(divElement);
			}
		});
		//添加div
		tree_ay.append(divParentElement);
}
//添加节点
function cyui_treegrid_div_addNode(tree_ay, row, tag, nodes) {
		//console.log('id=' + row[treeDef.id]);
		//console.log('parentId=' + row[treeDef.parentId]);
		var divParentElement = document.createElement("div");
		divParentElement.setAttribute("class", "list-group-item parent");
		divParentElement.setAttribute("state", "2");
		divParentElement.setAttribute("style", "display: none;");
		divParentElement.setAttribute("id", "" + row[treeDef.id] + "");
		divParentElement.setAttribute("parent", "" + row[treeDef.parentId] + "");
		divParentElement.setAttribute("nodes", nodes);
		var divElement = null;
		$.each(columnArray, function(index, column) {
			//添加节点div
			divElement = document.createElement("div");
			if(column == 'text') {
				//divElement.innerHTML = tag + row[column];
				//divElement.innerHTML = tag + "<img src='../img/file.gif'></img>" + row[column];
				divElement.innerHTML = tag + "<img src='"+folder_closed_gif+"'></img>" + row[column];
				divElement.setAttribute("class", "list-group-item");
				divParentElement.append(divElement);
			} else {
				divElement.innerHTML = row[column];
				divElement.setAttribute("class", "list-group-item");
				divParentElement.append(divElement);
			}
		});
		//添加div
		tree_ay.append(divParentElement);
}
function cyui_treegrid_div_AfterNode(tree_ay, row) {
		//console.log('id=' + row[treeDef.id]);
		//console.log('parentId=' + row[treeDef.parentId]);
		var divParentElement = document.createElement("div");
		divParentElement.setAttribute("class", "list-group-item parent");
		divParentElement.setAttribute("state", "1");
		divParentElement.setAttribute("id", "" + row[treeDef.id] + "");
		divParentElement.setAttribute("parent", "" + row[treeDef.parentId] + "");
		var divElement = null;
		$.each(columnArray, function(index, column) {
			//添加节点div
			divElement = document.createElement("div");
			divElement.innerHTML = row[column];
			divElement.setAttribute("class", "list-group-item");
			divParentElement.append(divElement);
		});
		//添加div
		tree_ay.after(divParentElement);
}
function cyui_treegrid_div_BeforeNode(tree_ay, row) {
		console.log('id=' + row[treeDef.id]);
		console.log('parentId=' + row[treeDef.parentId]);
		var divParentElement = document.createElement("div");
		divParentElement.setAttribute("class", "list-group-item");
		divParentElement.setAttribute("state", "1");
		divParentElement.setAttribute("id", "" + row[treeDef.id] + "");
		divParentElement.setAttribute("parent", "" + row[treeDef.parentId] + "");
		var divElement = null;
		$.each(columnArray, function(index, column) {
			//添加节点div
			divElement = document.createElement("div");
			divElement.innerHTML = row[column];
			divElement.setAttribute("class", "list-group-item");
			divParentElement.append(divElement);
		});
		//添加div
		tree_ay.before(divParentElement);
}