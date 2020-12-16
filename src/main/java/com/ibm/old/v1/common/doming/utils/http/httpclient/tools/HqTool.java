package com.ibm.old.v1.common.doming.utils.http.httpclient.tools;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: HQ工具类
 * @Author: zjj
 * @Date: 2019-08-05 17:32
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HqTool {
	private static final long SHORT_SLEEP = 500L;
	private static final long SLEEP = 1000L;

	protected static final Logger log = LogManager.getLogger(IdcTool.class);
	private static final Integer MAX_RECURSIVE_SIZE = 5;
	private static final String CRACK_URL = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

	// TODO 开启页面函数
	/**
	 * 打开登陆页面
	 *
	 * @param httpConfig http请求配置类
	 * @param url        url地址
	 * @param code       验证码
	 * @param index      循环次数
	 * @return 登陆页面
	 */
	public static String getMemberHtml(HttpClientConfig httpConfig, String url, String code, int... index)
			throws IOException, InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

		if (StringTool.isEmpty(memberHtml(html))) {
			log.error("打开选择登陆页面失败" + html);
			Thread.sleep(SHORT_SLEEP);
			return getMemberHtml(httpConfig, url, code, ++index[0]);
		}
		String action = memberHtml(html);

		Map<String, Object> join = new HashMap<>(1);
		join.put("SafeCode", code);
		html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url + action).map(join));

		if (StringTool.isEmpty(getMemberHtml(html))) {
			log.error("打开选择登陆页面失败" + html);
			Thread.sleep(SHORT_SLEEP);
			return getMemberHtml(httpConfig, url, code, ++index[0]);
		}
		//目前只获取盘口会员pc端href
		return getMemberHtml(html);
	}
	/**
	 * 获取选择线路页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param href        请求路径
	 * @param handicapUrl url地址
	 * @param index       循环次数
	 * @return 选择线路页面
	 */
	public static String[] getSelectRoutePage(HttpClientConfig httpConfig, String href, String handicapUrl,
			int... index) throws IOException, InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl + href));

		if (StringTool.isEmpty(html) || !StringTool.isContains(html, "线路选择")) {
			log.error("打开选择线路界面失败");
			Thread.sleep(SHORT_SLEEP);
			return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
		}
		html = HttpClientUtil.findInstance().getHtml(httpConfig
				.url(handicapUrl.concat("/Url/GetUrlList?").concat(href.split("\\?")[1]).concat("$_=") + System
						.currentTimeMillis()));

		if (ContainerTool.isEmpty(html) || ContainerTool.isEmpty(JSONObject.fromObject(html).getJSONArray("Data"))) {
			log.error("获取线路失败");
			Thread.sleep(SHORT_SLEEP);
			return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
		}

		JSONArray array = JSONObject.fromObject(html).getJSONArray("Data");

		String[] routes = new String[array.size()];

		long[] arr = new long[array.size()];

		//判断时间延迟
		long t1, t2;
		for (int i = 0; i < array.size(); i++) {
			if (i == 5 || i == 6) {
				routes[i] = array.getString(i);
				arr[i] = 500;
				continue;
			}
			t1 = System.currentTimeMillis();
			String url = "http://" + array.getString(i) + "/Test/GetNetSpeed";
			HttpClientUtil.findInstance().getHtml(httpConfig
					.url(url.concat("?jsonp=callback").concat(String.valueOf(i)).concat("&_=") + System
							.currentTimeMillis()));
			t2 = System.currentTimeMillis();
			long mytime = t2 - t1;
			if (i == 3 || i == 4 || i == 7) {
				mytime += 200;
			}
			routes[i] = array.getString(i);
			arr[i] = mytime;
		}

		for (int x = 0; x < arr.length; x++) {
			for (int j = 0; j < arr.length - x - 1; j++) {
				if (arr[j] > arr[j + 1]) {
					long t = arr[j];
					String route = routes[j];

					arr[j] = arr[j + 1];
					routes[j] = routes[j + 1];

					arr[j + 1] = t;
					routes[j + 1] = route;
				}
			}
		}

		return routes;
	}
	/**
	 * 获取登录页面
	 *
	 * @param httpConfig      http请求配置类
	 * @param routes          线路
	 * @param handicapCaptcha 盘口验证码
	 * @return 登录页面
	 */
	public static Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, String handicapCaptcha,
			int... index) throws IOException, InterruptedException {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[0] >= routes.length) {
			index[1]++;
		}
		if (index[1] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		String html = HttpClientUtil.findInstance().getHtml(httpConfig.url("http://".concat(routes[index[0]])
				.concat("/Member/Login?_=" + System.currentTimeMillis() + "&token=" + handicapCaptcha)));

		if (StringTool.isEmpty(html)) {
			log.error("获取线路失败");
			Thread.sleep(SHORT_SLEEP);
			return getLoginHtml(httpConfig, routes, handicapCaptcha, ++index[0], index[1]);
		}
		Map<String, String> map = new HashMap<>(2);
		map.put("html", html);
		map.put("hostUrl", routes[index[0]]);
		return map;
	}
	/**
	 * 获取登录加密key
	 * @param httpConfig			http请求配置类
	 * @param sessionId			参数sessionId
	 * @param hostUrl				主机地址
	 * @param index				循环次数
	 * @return						返回结果map
	 * @throws IOException
	 */
	public static JSONObject getEncryptKey(HttpClientConfig httpConfig, String sessionId, String hostUrl, int... index)
			throws IOException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = "http://".concat(hostUrl).concat(sessionId).concat("/Member/GK");

		String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url));
		if (StringTool.isEmpty(html)) {
			return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
		}
		JSONObject result = JSONObject.fromObject(html);
		if (ContainerTool.isEmpty(result) || result.getInt("Status") - 1 != 0) {
			return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
		}
		return result.getJSONObject("Data");
	}
	/**
	 * 登录盘口
	 * @param httpConfig			http请求配置类
	 * @param hostUrl				主机地址
	 * @param sessionId			参数sessionId
	 * @param handicapCaptcha		盘口验证码
	 * @param encryptKey			加密信息-vk
	 * @param logpk				参数logpk
	 * @param index				循环次数
	 * @return
	 * @throws IOException
	 */
	public static String getLogin(HttpClientConfig httpConfig, String hostUrl, String sessionId, String handicapCaptcha,
			String encryptKey, String logpk, int... index) throws IOException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url="http://".concat(hostUrl).concat(sessionId).concat("/Member/DoLogin");
		Map<String, Object> join = new HashMap<>(8);
		join.put("token",handicapCaptcha);
		join.put("Captcha","");
		join.put("info",encryptKey);
		join.put("pk",logpk);

		String html =HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));

		if(StringTool.isEmpty(html)){
			return getLogin(httpConfig,hostUrl,sessionId,handicapCaptcha,encryptKey,logpk,++index[0]);
		}
		return html;
	}

	/**
	 * 协议页面
	 * @param httpConfig			http请求配置类
	 * @param hostUrl				主机地址
	 * @param sessionId			参数sessionId
	 * @param index				循环次数
	 * @return						协议页面
	 * @throws IOException
	 */
	public static String agreementHtml(HttpClientConfig httpConfig, String hostUrl, String sessionId, int... index)
			throws IOException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url="http://".concat(hostUrl).concat(sessionId).concat("/All/Agreement.html?s=").concat(sessionId);

		String html=HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
		if(StringTool.isEmpty(html)){
			return agreementHtml(httpConfig,hostUrl,sessionId,++index[0]);
		}

		return html;
	}
	/**
	 * 同意协议页面
	 * @param httpConfig			http请求配置类
	 * @param hostUrl				主机地址
	 * @param sessionId			参数sessionId
	 * @param index				循环次数
	 * @return
	 * @throws IOException
	 */
	public static String acceptHtml(HttpClientConfig httpConfig, String hostUrl, String sessionId, int... index)
			throws IOException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = "http://".concat(hostUrl).concat(sessionId).concat("/Member/AcceptAgreement");

		String acceptHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(url));

		if (StringTool.isEmpty(acceptHtml)) {
			return acceptHtml(httpConfig, hostUrl, sessionId, ++index[0]);
		}

		return acceptHtml;
	}
	/**
	 * 主页面
	 * @param httpConfig			http请求配置类
	 * @param hostUrl				主机地址
	 * @param sessionId			参数sessionId
	 * @param index				循环次数
	 * @return
	 * @throws IOException
	 */
	public static String indexHtml(HttpClientConfig httpConfig, String hostUrl, String sessionId, int... index)
			throws IOException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url="http://".concat(hostUrl).concat(sessionId).concat("/App/Index?_=")+System.currentTimeMillis();

		String html=HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

		if(StringTool.isEmpty(html)){
			return null;
		}
		return html;
	}


	// TODO Document解析页面函数
	/**
	 * 获取盘口会员登入路径
	 *
	 * @param html 线路页面
	 * @return 是 true
	 */
	private static String getMemberHtml(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		if (!StringTool.isContains(html, "为了更好的浏览体验，请使用竖屏浏览！")) {
			return null;
		}
		Document document = Jsoup.parse(html);

		Element a = document.body().selectFirst("a");

		return a.attr("href");
	}
	/**
	 * 解析页面获取action
	 *
	 * @param html 线路页面
	 * @return 是 true
	 */
	private static String memberHtml(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		Document document = Jsoup.parse(html);

		if (!StringTool.isContains(document.body().html(), "action")) {
			return null;
		}
		Element body = document.body();

		Elements select = body.select("form");

		return select.attr("action");
	}

	public static Map<String, String> getScriptInfo(String html) {
		Map<String, String> map = new HashMap<>(2);
		Document document = Jsoup.parse(html);

		Element script = document.selectFirst("script");

		String sessionid = StringUtils.substringBetween(script.html(), "var SESSIONID = \"", "\";");
		String version = StringUtils.substringBetween(script.html(), "var VERSION = \"", "\";");
		map.put("SESSIONID", "/".concat(sessionid));
		map.put("VERSION", version);
		return map;

	}

	// TODO String功能函数
}
