var DEFAULT_PAGE_SIZE = 10;

var DT_FULL_NUMBERS = "full_numbers";
var DT_TWO_BUTTON = "two_button";
var iDisplayStart = 0;
var iDisplayLength = 10;
function BSDataTable() {

	this.tableId = "";
	this.addDataUrl = "";
	this.editDataUrl = "";
	this.rmDataUrl = "";
	
	/**
	 * 新增数据
	 */
	this.addData = function () {
		try {
			window.location = this.addDataUrl;
  		} catch(e) { console.log(e); }
	}
	
	/**
	 * 修改数据
	 * @param params 参数
	 */
	this.editData = function (params) {
		try {
			var count = 0;
			var paramStr = "";
			for( var key in params ) {
				var value = eval("params." + key);
				paramStr += ( count == 0 ) ? ( "?" + key + "=" + value ) : ( "&" + key + "=" + value );
				count ++;
			}
			window.location = this.editDataUrl + paramStr;
  		} catch(e) { console.log(e); }
	}
	
	/**
	 * 删除数据
	 * @param params 参数
	 * @param func1 成功时回调函数
	 * @param func2 失败时回调函数
	 */
	this.rmData = function (params, func1, func2) {
		try {
			$.ajax({
				"type" : "post",
				"url" : this.rmDataUrl,
				"dataType" : "json",
				"cache" : false,
				"async" : true,
				"data" : params,
				"success" : function(data) {
					if (data.status) {
						if ( func1 != undefined ) func1();
					} else {
						if ( func2 != undefined ) func2();
					}
				}
			});
		} catch (e) {
			console.log(e);
		}
	}

	/**
	 * 刷新数据
	 */
	this.reloadData = function () {
		try {
			$('#' + this.tableId).dataTable().fnDraw(false);
		} catch(e) { console.log(e); }
	}
	
	/**
	 * 初始化数据表
	 * @param tableId 表格ID
	 * @param columns 表格列
	 * @param sorting 排序规则
	 * @param url AJAX URL
	 * @param extraParams AJAX请求参数
	 * @param style 分页器风格（full_numbers 或 two_button）
	 */
	this.initialize = function (obj) {
		try {
			this.tableId = obj.tableId;
			this.addDataUrl = obj.url.addDataUrl;
			this.editDataUrl = obj.url.editDataUrl;
			this.rmDataUrl = obj.url.rmDataUrl;
			if(obj.fnServerData==null){
				obj.fnServerData = fnServerData;
			}
			
			$('#' + this.tableId).dataTable({
				//"sPaginationType" : obj.style, // 用于指定分页器风格，分别为full_numbers和two_button，默认值为two_button
				"sPaginationType" : "full_numbers",
				"bScrollCollapse" : true, // 是否强制表格视图在任何时候都是使用给定的高度（有利于布局）
				"bJQueryUI" : false, // 是否应用JQuery UI ThemeRoller的风格
				"bPaginate" : true, // 是否显示分页器
				"bFilter" : false, // 是否启用客户端过滤功能
				"bLengthChange" : true, // 是否允许终端用户从选择列表中选择分页的页数，页数为10、25、50和100，需要分页组件支持
				// "iDisplayLength" : obj.pageSize, // 用于指定一屏显示的条数，需开启分页器，默认为10
				"iDisplayLength" : iDisplayLength,
				"bSort" : true, // 是否让各列具有按列排序功能
				"bInfo" : true, // 是否显示表格的一些信息（表格左下方的信息）
				"bProcessing" : false, // 是否显示处理中
				"iDisplayStart" : iDisplayStart,
				"oLanguage" : { // 自定义语言设置（汉化）
					"sProcessing" : "数据加载中",
					"sLengthMenu" : "显示 _MENU_ 条 ",
					"sZeroRecords" : "没有您要搜索的内容",
					"sInfo" : "从 _START_ 到 _END_ 条记录 - 总记录数为 _TOTAL_ 条",
					//"sInfoEmpty" : "暂无数据",
					"sInfoEmpty" : "",
					"sInfoFiltered" : "(全部记录数 _MAX_  条)",
					"sInfoPostFix" : "",
					"sSearch" : "搜索",
					"sUrl" : "",
					"oPaginate" : {
						"sFirst" : "首页",
						"sPrevious" : "上一页",
						"sNext" : "下一页",
						"sLast" : "尾页"
					}
				},
				"bServerSide" : true, // 配置DataTable使用服务器端处理，注意，sAjaxSource参数必须指定，以便给DataTable一个为每一行获取数据的数据源
				"bDestroy" : true, // 使用传递的新的初始化对象中的属性构造一个新的表格，并替换一个匹配指定的选择器的表格
				"bSortCellsTop" : true, // 是否允许DataTable使用顶部（默认为true）的单元格，或者底部（默认为false）的单元格，当使用复合表头的时候会有一定用处
				"sAjaxSource" : obj.dataSource, // 向DataTable指定加载的外部数据源（JSON格式数据）
				"aaSorting" : obj.sorting, // 定义初始化表格时需排序的列与排序方式（如："aaSorting": [[2,'asc'], [3,'desc']]）
				"aoColumns" : obj.columns,
				"aoColumnDefs": [{sDefaultContent: '',aTargets: [ '_all' ]}],
				"fnServerData" : obj.fnServerData,
				"fnServerParams" : function(aoData) { // 用于向服务器发送AJAX请求时添加额外的数据
					aoData.push({
						"name" : "extraParams",
						"value" : obj.extraParams
					});
				}
			});
		} catch (e) {
			console.log(e);
		}
	}
}

function fnServerData(sSource, aoData, fnCallback){
	try {
		$.ajax({
			"type" : "post",
			"url" : sSource,
			"dataType" : "json",
			"data" : {
				aoData : JSON.stringify(aoData)
			},
			"success" : function(resp) {
				fnCallback(resp);
				$('#table1_length').css('margin-left','10px');
				
				$('#table1_info').css('margin-left','10px');
			}
		});
	} catch (e) {
		console.log(e);
	}
}
$(window).load(function() {
	try {
		// Page Preloader
		$('#status').fadeOut();
		$('#preloader').delay(350).fadeOut(function() {
			//$('body').delay(350).css( { "overflow": "visible" } );
  	    });
	} catch(e) { console.log(e); }
});

//var DEV_BROWSER = {
	//versions : function() {
		//var u = navigator.userAgent, app = navigator.appVersion;
		//return {
			//trident : u.indexOf('Trident') > -1, //Core: IE
			//presto : u.indexOf('Presto') > -1, //Core: Opera
			//webKit : u.indexOf('AppleWebKit') > -1, //Core: WebKit
			//gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //Core: Firefox
			//mobile : !!u.match(/AppleWebKit.*Mobile.*/), //Mobile Device
			//ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //iOS Device
			//android : u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //Android Device or UC Browser
			//iPhone : u.indexOf('iPhone') > -1, //iPhone Device or QQ HD Browser
			//iPad : u.indexOf('iPad') > -1, //iPad
			//webApp : u.indexOf('Safari') == -1 //Web Application
		//};
	//}(),
	//language : (navigator.browserLanguage || navigator.language).toLowerCase()
//}

//$(document).ready(function() {
	
   // Tooltip
   //if ( !DEV_BROWSER.versions.mobile ) $('.tooltips').tooltip({ container: 'body' });
   
   // Popover
   //$('.popovers').popover();
   
   // Close Button in Panels
   //$('.panel .panel-close').click(function(){
      //$(this).closest('.panel').fadeOut(200);
      //return false;
   //});
   
   //Form Toggles
   //$('.toggle').toggles({ on: true });
   
   //$('#mainPanel').css("background", "#E4E7EA");
   //$('#mainPanel').css("width", $(window).width() + "px").css("height", $(window).height() + "px");
//});

function formatDate(date){
	var html = "";
	if(date!=null){
		html="<label title=\""+date+"\">"+new Date(date).Format("yyyy-MM-dd")+"</label>";
	}else{
		html="-";
	}
	return html
}