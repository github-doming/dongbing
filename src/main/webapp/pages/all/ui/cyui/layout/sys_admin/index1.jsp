<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap3/css/bootstrap.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap3/css/bootstrap-theme.min.css"
	rel="stylesheet" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/jquery/jquery-3.3.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/pages/c/x/includes/simple/bootstrap3/js/bootstrap.min.js"></script>

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width" />
<title>用户自定义树形菜单</title>
<style>
.page_hidden {
	display: none;
}

.page_show {
	display: block !important;
}
</style>
<style type="text/css">


header {
	height: 50px;
	line-height: 50px;
	background-color: #438eb9;
	/*border: 1px #0B0B0B solid;*/
	font-size: 24px;
	color: #FFFFFF;
}

header span {
	margin-left: 20px;
}

header p {
	display: inline;
	margin: 0;
}
</style>
</head>
<body>


<!-- start header -->
	<header class="main-header">
		<div class="container">
			<div class="row">
		<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<h1 style="color: #FFFFFF;">奔亿娱乐</h1>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<a href="http://www.163.com/laravel/docs/5.1/"
						class="btn btn-default btn-doc" target="_blank">定时管理</a> <a
						href="http://www.163.com/laravel/docs/5.0/"
						class="btn btn-default btn-doc" target="_blank">APP管理</a> <a
						href="http://www.163.com/laravel/docs/4.2/"
						class="btn btn-default btn-doc" target="_blank">平台管理</a> <a
						href="http://www.163.com/laravel/docs/4.1/"
						class="btn btn-default btn-doc" target="_blank">开卡管理</a> <a
						href="http://lumen.golaravel.com/docs/"
						class="btn btn-default btn-doc" target="_blank">系统监控</a>
				</div>
			</div>
		</div>
	</header>
	<!-- end header -->
	
	
	<!-- start header -->
	<div class="row">
		<header>
			<span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>
			<p>后台管理系统</p>
		</header>
		
		
	</div>
	<!-- end header -->
	
	<div>
		<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2">
			<div id="cyui_tree_id" class="cyui_tree"
				url="${pageContext.request.contextPath}/easyui/layout/menu/comm/list.do"
				treeColumn="text" idField="id" parentField="pid" urlField="url"
				rootParentId="0"></div>
		</div>
		<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
			<ul id="cyui_tab_id" class="nav nav-tabs">

				<li class="active"><a href="#home" data-toggle="tab"> 技术后台</a></li>


			</ul>
			<div id="cyui_tab_content_id" class="tab-content">

				<div class="tab-pane fade in active" id="home">
					<p>这里的技术后台是程序员管理的后台，不是业务后台</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script>
	var contextPath = '${pageContext.request.contextPath}';
	//var file_gif=contextPath+"/pages/c/x/imports/compo/tree_table/images/v2/file.gif";
	//var folder_gif=contextPath+"/pages/c/x/imports/compo/tree_table/images/v2/folder.gif";
	//var folder_closed_gif=contextPath+"/pages/c/x/imports/compo/tree_table/images/v2/folder-closed.gif";

	var file_gif = contextPath
			+ "/pages/c/x/imports/compo/tree_table/images/v1/file.gif";
	var folder_gif = contextPath
			+ "/pages/c/x/imports/compo/tree_table/images/v1/folder.gif";
	var folder_closed_gif = contextPath
			+ "/pages/c/x/imports/compo/tree_table/images/v1/folder-closed.gif";

	//数据
	var data = null;
	var winH = $(window).height();
	var iframeHeight = winH - 92 + "px";
	var tree_ay = $(".cyui_tree");
	var tree_ay_id = tree_ay.attr("id");
	var tree_ay_url = tree_ay.attr("url");
	//alert('url='+url);
	//根节点父id
	var tree_ay_rootParentId = tree_ay.attr("rootParentId");
	//json数据定义
	var treeDef = {
		"id" : tree_ay.attr("idField"),
		"name" : tree_ay.attr("treeColumn"),
		"parentId" : tree_ay.attr("parentField"),
		"url" : tree_ay.attr("urlField")
	}
	init_ajax(contextPath, treeDef, tree_ay);
	//初始化  by cjx
	function init_ajax(contextPath, treeDef, tree_ay) {
		//console.log('treeDef.id=' + treeDef.id);

		//ajax请求
		var name = $("#nameId").val();
		var password = $("#passworId").val();
		var json = {
			name : name,
			password : password
		}
		var jsonStr = JSON.stringify(json);
		var ajax_url = tree_ay.attr("url");
		jQuery.ajax({
			url : ajax_url,
			type : "POST",
			async : false,
			//  async: true,
			cache : true,
			data : {
				json : jsonStr
			},
			contentType : 'application/x-www-form-urlencoded;charset=UTF-8',
			error : function(request) {
				alert("Connection error");
			},
			success : function(responseData, status) {
				//alert("Connection success,status=" + status);
				//alert('responseData=' + responseData);
				data = JSON.parse(responseData);
				//data= data1;
			}
		});
		//ajax请求
		//初始化，绑定数据
		cyui_tree_bindData(data, treeDef, tree_ay);
		$(".parent").click(function() {
			cyui_tree_openOrClose(this, contextPath, 500, treeDef, tree_ay);
		})
		$(".parent").each(function() {
			cyui_tree_openOrClose(this, contextPath, 0, treeDef, tree_ay);
		});
	};
	//打开或关闭,by cjx
	function cyui_tree_openOrClose(nodeObject, contextPath, millisecond,
			treeDef, tree_ay) {
		//状态
		var state = $(nodeObject).children("state").html();
		var stateInt = parseInt(state);
		//节点数
		var nodes = $(nodeObject).children("nodes").html();
		var nodesInt = parseInt(nodes);
		//alert('bb=' + bInt);
		switch (stateInt) {
		//关闭
		case 1:
			$(nodeObject).children("state").html("2");
			$(nodeObject).siblings().hide(millisecond);
			if (nodesInt == 0) {
				$(nodeObject).find("img").attr("src", file_gif);
			} else {
				$(nodeObject).find("img").attr("src", folder_closed_gif);
			}
			break;
		//展开
		case 2:
			$(nodeObject).children("state").html("1");
			$(nodeObject).siblings().show(millisecond);
			if (nodesInt == 0) {
				//var idHtml=$(nodeObject).children("id").html();
				//var urlHtml=$(nodeObject).children("url").html();
				var id_data = $(nodeObject).attr("id_data");
				var url_data = $(nodeObject).attr("url_data");
				var title_data = $(nodeObject).attr("title_data");
				var tab_options = {
					contextPath : contextPath,
					close : true,
					id : id_data,
					url : url_data,
					title : title_data
				//content:title_data
				}
				cyui_tree_addTab(tab_options);
				$(nodeObject).find("img").attr("src", file_gif);
			} else {
				$(nodeObject).find("img").attr("src", folder_gif);
			}
			break;
		default:
			break;
		}
	}
	//循环绑定数据
	function cyui_tree_bindData(data, treeDef, tree_ay) {
		//console.log('treeDef.id=' + treeDef.id);
		//动态创建树形菜单
		var parentNodeDiv = tree_ay;
		for (var i = 0; i < data.length; i++) {
			//console.log("data[i]=" + data[i][treeDef.name]);
			//console.log("data[i].children.length=" + data[i].children.length);
			//最上层父节点
			if (data[i][treeDef.parentId] == tree_ay_rootParentId) {
				cyui_tree_addNode(parentNodeDiv, data[i], treeDef, tree_ay);
			} else {
				for (var j = 0; j < $(".parent").length; j++) {
					if (data[i][treeDef.parentId] == $(".parent")[j].id) {
						var parentNodeDiv = $($(".parent")[j]).parent();
						var ulE = parentNodeDiv.find("div");
						if (ulE.is()) {
						} else {
							ulE = document.createElement("div");
							ulE.className = 'list-group';
							parentNodeDiv.append(ulE);
						}
						cyui_tree_addNode(ulE, data[i], treeDef, tree_ay);
					}
				}
			}
			if (data[i].children.length == 0) {
				//叶子
			} else {
				//非叶子
				cyui_tree_bindData(data[i].children, treeDef, tree_ay);
			}
		}
	}
	//添加节点
	function cyui_tree_addNode(parentNodeDiv, data, treeDef, tree_ay) {
		//console.log('id=' + data[treeDef.id]);
		//console.log('name=' + data[treeDef.parentId]);
		//状态
		var state = document.createElement("state");
		state.innerHTML = 1;
		state.style = "display: none;"
		//节点数
		var nodes = document.createElement("nodes");
		nodes.innerHTML = data.children.length;
		nodes.style = "display: none;"
		//span
		var spanE = document.createElement("span");
		spanE.onclick = function() {
			if (data.children.length == 0) {
				//alert('ok');
			}
		}
		spanE.id = data[treeDef.id];
		spanE.innerHTML = "<img src='"+file_gif+"'></img>" + data["text"];
		spanE.setAttribute("class", "parent");
		//id
		spanE.setAttribute("id_data", data[treeDef.id]);
		//url
		spanE.setAttribute("url_data", data[treeDef.url]);
		//名称
		spanE.setAttribute("title_data", data["text"]);
		//span追加
		//spanE.appendChild(idElement);
		//spanE.appendChild(urlElement);
		spanE.appendChild(state);
		spanE.appendChild(nodes);
		var liElement = document.createElement("ay_ui_tree_div");
		liElement.href = "#";
		liElement.className = 'list-group-item';
		liElement.onclick = function() {
			if (data.children.length == 0) {
				$('ay_ui_tree_div').each(function() {
					this.className = 'list-group-item';
				});
				this.className = 'list-group-item active';
				//this.className = 'list-group-item btn-danger';
				//this.className = 'list-group-item btn-success';
			}
		}
		liElement.appendChild(spanE);
		parentNodeDiv.append(liElement);
	}
</script>
<script>
	init_tab();
	var cyui_tree_addTab = function(options) {
		//var rand = Math.random().toString();
		//var id = rand.substring(rand.indexOf('.') + 1);
		//var url = window.location.protocol + '//' + window.location.host;
		//var url = '${pageContext.request.contextPath}';
		//alert('options.contextPath='+options.contextPath);
		options.url = options.contextPath + options.url;
		//alert('options.url='+ options.url);
		id = "tab_" + options.id;
		$(".active").removeClass("active");
		//如果TAB不存在，创建一个新的TAB
		if (!$("#" + id)[0]) {
			//固定TAB中IFRAME高度
			//mainHeight = $(document.body).height() - 90;
			//mainHeight = $(document.body).height();
			//alert('mainHeight='+mainHeight);
			//mainHeight = 900;
			mainHeight = iframeHeight;
			//创建新TAB的title
			title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">'
					+ options.title;
			//是否允许关闭
			if (options.close) {
				title += ' <i class="glyphicon glyphicon-remove" tabclose="' + id + '"></i>';
			}
			title += '</a></li>';
			//是否指定TAB内容
			if (options.content) {
				content = '<div role="tabpanel" class="tab-pane" id="' + id + '">'
						+ options.content + '</div>';
			} else {//没有内容，使用IFRAME打开链接
				content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe src="'
						+ options.url
						+ '" width="100%" height="'
						+ mainHeight
						+ '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
			}
			//加入TABS
			$(".nav-tabs").append(title);
			$(".tab-content").append(content);
		}
		//激活TAB
		$("#tab_" + id).addClass('active');
		$("#" + id).addClass("active");
	};
	var closeTab = function(id) {
		//如果关闭的是当前激活的TAB，激活他的前一个TAB
		if ($("li.active").attr('id') == "tab_" + id) {
			$("#tab_" + id).prev().addClass('active');
			$("#" + id).prev().addClass('active');
		}
		//关闭TAB
		$("#tab_" + id).remove();
		$("#" + id).remove();
	};
	function init_tab() {
		//mainHeight = $(document.body).height() - 45;
		mainHeight = iframeHeight;
		$('.main-left,.main-right').height(mainHeight);
		$("[addtabs]").click(function() {
			cyui_tree_addTab({
				id : $(this).attr("id"),
				title : $(this).attr('title'),
				close : true
			});
		});
		$(".nav-tabs").on("click", "[tabclose]", function(e) {
			id = $(this).attr("tabclose");
			closeTab(id);
		});
	};
</script>