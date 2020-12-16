package com.ibm.old.v1.common.doming.demos.pattern;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Description: 正则表达式测试类
 * @Author: Dongming
 * @Date: 2018-08-07 11:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Simple_eg1 {
	public static void main(String[] args) {
		String scriptStr = "var version = \"20902_20903_4.6.trunk_20150316\";var systemversion = \"4_6\";/******代碼合併******///刪除左右兩端的空格String.prototype.trim = function() {return this.replace(/(^\\s*)|(\\s*$)/g, '');};"
				+ "var current_page = {load_method: 'ajax', //ajaxor iframesource_path: [], "
				+ "//需要加載的資源front_source: [\"/webssc/htmlfront_new/klc/all.html?___=pre_htmlcache&version_=\" + version, \"/webssc/css/all_new.css?\" + version, \"/webssc/js/frontend_new/all.js?\" + version],"
				+ "back_source: [\"/webssc/htmlback/klc/all.html?___=pre_htmlcache&version_=\" + version, \"/webssc/css/all_b.css?\" + version, \"/webssc/js/backend/all.js?\" + version],"
				+ "company: '/uploadfile/loginbg/1040014/front/', //會員圖片路徑<#mmber_login_pic#>flag: false, //AJAX請求標緻login_flag: true, //登錄驗證請求f: false, //背景圖片請求標緻"
				+ "init: function() { //初始化window.setting = {password_need: false //是否開啟密碼登陸加密};current_page.password();current_page.bindEvent();current_page.s(true);"
				+ "if (current_page.load_method == 'ajax') {current_page.ajax_load();} else {current_page.iframe_load();}},bindEvent: function() { //綁定事件"
				+ "document.getElementById('login_form').onkeypress = current_page.keypress;var form = document.getElementsByTagName('form')[0];//清空輸入框所有的值form.__name.value = '';form.password.value = '';//為了特殊用戶的要求，這裡增加了清除密碼的功能。"
				+ "var th;form.__name.onfocus = function() {if (th) {clearInterval(th);th = null;}if (!form.password.value) {th = setInterval(function() {form.password.value = '';}, 99);}}form.__name.onblur = function() {"
				+ "if (th) {clearInterval(th);form.password.value = '';th = null;}}setTimeout(function() {form.__name.focus();}, 0);},validateForm: function(form) { //表單驗證準則var name = form.__name.value.trim();"
				+ "form.name.value = name;var password = form.password.value.trim();var vcode = form.VerifyCode.value.trim();form.password.value = password;"
				+ "if (!(/^[A-Za-z0-9_~!@$*\\-\\=\\(\\)\\{\\}\\:\\?\\[\\]\\;\\.\\,]{1,12}$/.test(name))) {alert('賬號由1-12位英文字母、數字和特殊字元組成');form.__name.focus();return false;}"
				+ "if (!(/^(?![^a-z]+$)(?![^A-Z]+$)(?!\\D+$)\\S{8,30}$/.test(password))) {alert('密碼由6-16位英文字母、數字字符組成');form.password.focus();return false;}if (vcode.length != 5) "
				+ "{alert('驗證碼由5位數字組成');form.VerifyCode.focus();return false;}if (!(/^\\d{5}$/.test(vcode))) {alert('驗證碼由5位數字組成');form.VerifyCode.focus();return false;} return true;},v: function() { //表單驗證"
				+ "var password_txt = document.getElementById('password_txt');if(password_txt.style.display != 'none'){if (!current_page.validateForm(document.getElementsByTagName('form')[0])) {return false;}}else{"
				+ "var objx = document.getElementById(\"textActive\");var iswebkit = document.body.style.WebkitBoxShadow !== undefined ? true : false;if(!iswebkit && objx && objx.PwdLength < 6){alert('密碼由6-16位英文字母、數字字符組成');"
				+ "return false;}}current_page.p();if (window.setting.password_need) {form.password.value = (hex_md5(hex_md5(form.password.value) + form.checksum.value)).substr(0, 10);}},Ajax: function() { //封裝AJAX請求公用方法"
				+ "var xhr = !! 0;try { //IE7+,Chrome and etc.xhr = new XMLHttpRequest;} catch (e) { //IE6系列try {xhr = new ActiveXObject(\"MSXML2.XMLHTTP\");} catch (e2) {try {xhr = new ActiveXObject(\"Microsoft.XMLHTTP\");} "
				+ "catch (e3) {xhr = false;}}}if (!xhr)return;this.xhr = xhr;/** *@param_url--AJAX請求的地址 *@param_content--AJAX請求的內容 *@param_callback 回調函數 *@param_method --請求方法GET 或POST *@param _isasync 同步或異步，默認為異步 *@return 返回一個xmlrequesthttp ***/"
				+ "this.send = function(_url, _content, _callback, _method, _isasync) {var Url = _url || \"\";var Content = _content || \"\";var Callback = _callback;var Method = _method || \"GET\";"
				+ "var IsAsync = _isasync == null ? true : _isasync;xhr.open(Method, Url, IsAsync);if (Method == \"POST\")xhr.setRequestHeader(\"Content-Type\", \"application/x-www-form-urlencoded;\");"
				+ "xhr.onreadystatechange = function() {if (xhr.readyState == 4 && Callback) {Callback(xhr.status == 200 ? xhr : null);}};xhr.send(Method == \"POST\" ? Content : \"\");}this.get = function(_url, _callback) "
				+ "{this.send(_url, \"\", _callback, \"GET\");}this.post = function(_url, _content, _callback) {this.send(_url, _content, _callback, \"POST\");}},p: function() { //使用POST方法發送表單數據if (current_page.login_flag == false) {"
				+ "return false;}current_page.login_flag = false;var form = document.getElementsByTagName('form')[0];/*var benbengvalue = '';for (i = 0; i < form.banbeng.length; i++) {if (form.banbeng[i].checked == true) {"
				+ "benbengvalue = form.banbeng[i].value;}}*/var postStr = \"\";var password_txt = document.getElementById('password_txt');postStr += \"VerifyCode=\" + form.VerifyCode.value + '&';"
				+ "postStr += \"__VerifyValue=\" + form.__VerifyValue.value + '&';postStr += \"__name=\" + form.__name.value + '&';if(password_txt.style.display != 'none'){ //Becky 20180525 replace passwordvar symbolMappingList = "
				+ "{'`' : '!V$', '-' : '!m$', '=' : '!k$', '[' : '!O$', ']' : '!K$', ';' : '!I$', '\\'' : '!S$', '\\\\' : '!T$', '/' : '!r$', '.' : '!Z$', ',' : '!a$', '~' : '!i$', '!' : '!p$', '@' : '!f$', '#' : '!7$', '$' : '!D$', '%' : '!l$', '^' : "
				+ "'!9$', '&' : '!q$', '*' : '!t$', '(' : '!6$', ')' : '!g$', '_' : '!v$', '+' : '!J$', '{' : '!L$', '}' : '!d$', '|' : '!W$', '\"' : '!E$', ':' : '!0$', '?' : '!H$', '>' : '!y$', '<' : '!b$' };"
				+ "var original_pwd = form.password.value.split(''); var replace_pwd= '';for (var property1 in original_pwd) {if(symbolMappingList[original_pwd[property1]] != undefined) { replace_pwd += symbolMappingList[original_pwd[property1]];} "
				+ "else { replace_pwd += original_pwd[property1];}}postStr += \"password=\" + replace_pwd+ '&';//postStr += \"password=\" + form.password.value + '&';}else{var objx = document.getElementById(\"textActive\");postStr += \"password=\" + "
				+ "objx.Pwd + '&'; } var sec = document.getElementById('sec');postStr += \"isSec=\" + (sec.checked ? 1 : 0) + '&';postStr += \"cid=\" + form.cid.value + '&';postStr += \"cname=\" + form.cname.value + '&';//postStr += \"banbeng=\" + "
				+ "benbengvalue + '&';postStr += \"systemversion=\" + systemversion + '&';var ajax = new current_page.Ajax();ajax.post(\"/loginVerify/.auth\", postStr, function(xmlhttp) {if (xmlhttp == null) {return false;}if (xmlhttp.responseText."
				+ "indexOf(\"êêê\") != -1) {var arr = xmlhttp.responseText.split(\"êêê\");document.getElementsById('bg').innerHTML = new Function(arr[1])();}if (xmlhttp.responseText.indexOf(\"密碼不正確\") != -1) {form.__name.value = '';form.password."
				+ "value = '';form.__name.focus();var objx = document.getElementById(\"textActive\"),ua = navigator.userAgent.toLowerCase();var isIE = ua.indexOf(\"trident\") > -1 || ua.indexOf(\"msie\") > -1;if(objx && typeof objx."
				+ "PwdClear == \"function\" && isIE){objx.PwdClear();}}if (xmlhttp.responseText.indexOf(\"://host\") == -1) {current_page.login_flag = true;}xmlhttp.responseText.indexOf(\"://host\") != -1 ? document.location.href = xmlhttp."
				+ "responseText.split('://host')[1] : alert(xmlhttp.responseText);if (xmlhttp.responseText.indexOf(\"驗證碼已過期\") != -1) {form.VerifyCode.value = '';current_page.s(false);}});},s: function(m) { //使用GET 方法得到圖片和current_page."
				+ "f = m ? m : current_page.f;if (current_page.flag) {alert(\"請求中\");return;}current_page.flag = true;document.getElementById(\"img\").innerHTML = \"請求中\";var ajax = new current_page.Ajax();ajax.get(\"/getCodeInfo/.auth?u=\" + "
				+ "Math.random() + '&systemversion=' + systemversion + '&.auth', function(xmlhttp) {if (xmlhttp) {var t = xmlhttp.responseText.split(\"_\");document.getElementById(\"img\").innerHTML = \"<img alt='請刷新' onerror='current_page.flag = "
				+ "false' title='如看不清，點擊刷新' onload='current_page.flag = false;' src='/getVcode/.auth?t=\" + t[0] + '&systemversion=' + systemversion + \"&.auth' />\";document.getElementsByName(\"__VerifyValue\")[0].value = t[1];var objx = "
				+ "document.getElementById(\"textActive\");if(objx){current_page.time = (t[t.length-1]+\"\").replace(/\\s+/g,\"\");objx.Time = current_page.time;}return;} else {document.getElementById(\"img\").innerHTML = \"失敗，請刷新\";flag = "
				+ "false;return;}current_page.flag = false;});tm = setTimeout(function() {current_page.flag = false;}, 3000);},keypress: function(e) { //鍵盤事件處理e = e || window.event;if (e.keyCode == 13) {current_page.v();}},ajax_load: function() "
				+ "{ //使用AJAX方法加載資源var source_path = current_page.source_path;var slength = source_path.length;for (var i = 0; i < slength; i++) {var ajax = new current_page.Ajax();ajax.get(source_path[i]);}},iframe_load: function() { "
				+ "//使用iframe 加載資源var source_path = current_page.source_path;var slength = source_path.length;for (var i = 0; i < slength; i++) {obj = document.createElement(\"iframe\");obj.style.display = \"none\";obj.src = source_path[i];"
				+ "document.body.appendChild(obj);}obj = null;},password: function() {var container = document.getElementById(\"password_container\"),ua = navigator.userAgent.toLowerCase(),isIE = ua.indexOf(\"trident\") > -1 || ua.indexOf(\"msie\")"
				+ " > -1,href = \"/webssc/js/plugins/anquan.exe\";if (isIE) {container.innerHTML = '<span><object version=\"1,0,0,2\" classid=\"clsid:7A68112B-96BC-4820-B65B-C459F14F889D\"standby=\"Waiting...\" id=\"textActive\" name=\"DVPLAYOCX12\" "
				+ "align=\"top\" width=\"176\" height=\"22\"></object></span><a id=\"downloadTip\" href=\"' + href + '\" class=\"downloadTip\">點擊下載控件,安裝完成後請刷新</a>';} else {container.innerHTML = '<span><object version=\"1,0,0,2\" "
				+ "width=\"176\" height=\"22\" tabindex=\"2\" type=\"application/npchedit\" id=\"textActive\"></object></span><a id=\"downloadTip\" href=\"' + href + '\" class=\"downloadTip\">點擊下載控件,完後請重啟瀏覽器</a>';}var isp = (function() "
				+ "{if (isIE) {return document.all.textActive.object != null;} else {var s = false;for (var i = 0; i < navigator.plugins.length; i++) {if (navigator.plugins[i].name.toLowerCase().indexOf(\"npchedit\") > -1 || navigator.plugins[i].name."
				+ "toLowerCase().indexOf(\"qcpass\") > -1) {s = true;break;}}return s;}})();var show = function(){var sec = document.getElementById('sec'),password_txt = document.getElementById('password_txt'),password_container = document."
				+ "getElementById('password_container');if (sec.checked) {password_txt.style.display = 'none';password_container.style.display = '';if (isp) {var plg = document.getElementById('textActive');if(!plg){return;}var newVersion = plg."
				+ "getAttribute(\"version\"),oldVersion = plg.OcxVersion;if(newVersion != oldVersion){var dt = document.getElementById('downloadTip');dt.className = dt.className + ' dtshow';plg.parentNode.removeChild(plg);}} else {var dt = document."
				+ "getElementById('downloadTip');dt.className = dt.className+' dtshow';//plg.parentNode.removeChild(plg);}} else {password_txt.style.display = '';password_txt.value = '';password_container.style.display = 'none';}}show();document."
				+ "getElementById('sec').onclick = function(){show();var plg = document.getElementById('textActive');if(!plg){return;}plg.Time = current_page.time;}}};window.onload = function() {//var href=location.href;//var tmp_arr=href."
				+ "split(\"/\");//var tmp_string=tmp_arr[3];//var platform_flag=tmp_string.substr(-1);//if(platform_flag=='f'){//平台標緻current_page.source_path = current_page.front_source;//}else{//current_page.source_path=current_page.back_source;"
				+ "//}current_page.init();};";
		Map<String,Object> scriptMapDate = new HashMap<>();
		String patternStr,selectStr,findStr;
		String[] strMap;
		Pattern pattern;
		selectStr = "var systemversion =";
		if (scriptStr.contains(selectStr)){
			patternStr = selectStr+ " \"\\w{3}\"";
			pattern = Pattern.compile(patternStr);
			Matcher matcher = pattern.matcher(scriptStr);
			if (matcher.find()) {
				findStr = StringTool.trimAll(matcher.group());
				strMap = findStr.split("=");
				scriptMapDate.put(strMap[0],strMap[1]);
			}
		}
		System.out.println(scriptMapDate);


	}
}
