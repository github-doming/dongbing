var context_path = 'http://127.0.0.1:8080/a/';

var context_ws = 'ws://127.0.0.1:8080/a/';

var benyipc_token = null;
if (window.localStorage.benyipc_token != null) {
	benyipc_token = window.localStorage.benyipc_token; // window.localStorage.token
}
var benyipc_roomId = null;
if (window.localStorage.benyipc_roomId != null) {
	benyipc_roomId = window.localStorage.benyipc_roomId;
}
var benyipc_gameId = null;
if (window.localStorage.benyipc_gameId != null) {
	benyipc_gameId = window.localStorage.benyipc_gameId;
}
// 判断类型
var user_type = null;
if (window.localStorage.user_type != null) {
	user_type = window.localStorage.user_type;
}
// encodeURI
function getURL(url) {
	url = encodeURI(url);
	window.location.href = url;
}
// 检测是否存在token
function IStoken() {
	getURL('../login/login.html');
	localStorage.removeItem('benyipc_token');
	localStorage.removeItem('benyipc_roomId');
	localStorage.clear();
}
//定时器-循环对象
var time;

// 右边错误提示
function rightMsg(text_) {
	$('.flex_right').show();
	$('.flex_right span').text(text_);
	setTimeout(function () {
		$('.flex_right').hide();
	}, 1000);
}
// 删除数组指定元素
function removeByValue(arr, val) {
	for (var i = 0; i < arr.length; i++) {
		if (arr[i] == val) {
			arr.splice(i, 1);
			break;
		}
	}
}
/**
 * 动态按需加载css
 */
function loadStyleString(url_css) {
	// 判断是否已经加载过其他有loaded类名的css
	if ($('link').hasClass('loaded')) {
		$('link.loaded').remove();
	}
	if (url_css == '../../common/css/agent/report_from.css') {
		url_css = '../../common/css/owner_mine/report_from.css'
	} else if (url_css == '../../common/css/agent/room_detail.css') {
		url_css = '../../common/css/owner_mine/room_detail.css'
	} else if (url_css == '../../common/css/agent/player_new.css' || url_css == '../../common/css/owner_member/player_new.css') {
		url_css = '../../common/css/owner_member/agent_new.css'
	}
	var head_id = document.getElementsByTagName('head')[0];
	var link = document.createElement('link');
	link.type = 'text/css';
	link.rel = 'stylesheet';
	link.href = url_css;
	link.className = "loaded";
	head_id.appendChild(link);
}
/**
 * 动态按需加载js
 */
var loadScript;
function loadScriptString(code) {
	// 判断是否已经加载过其他有loaded类名的js
	if ($('script').hasClass('loaded')) {
		$('script.loaded').remove();
	}
	loadScript = document.createElement("script");
	loadScript.type = "text/javascript";
	loadScript.src = code;
	loadScript.className = "loaded";
	document.body.appendChild(loadScript);
}