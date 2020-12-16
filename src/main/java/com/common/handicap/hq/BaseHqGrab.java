package com.common.handicap.hq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.handicap.crawler.BaseHandicapGrab;
import org.apache.commons.lang3.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

/**
 * Hq盘口抓取操作抽象类
 *
 * @Author: Dongming
 * @Date: 2020-05-16 16:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseHqGrab extends BaseHandicapGrab {

	/**
	 * 获取登录地址
	 *
	 * @param html 登录页面
	 * @return 登录地址
	 */
	protected abstract String getLoginHref(String html);

	/**
	 * 打开页面获取会员href
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     url地址
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 会员href
	 */
	public String getMemberHref(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));
			if (StringTool.isEmpty(html)) {
				log.error("打开导航页面为空");
				return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (StringTool.contains(html, "百度一下，你就知道", "http://parked-content.godaddy.com/park/MaNmZmt4YaOv")) {
				log.error("打开导航页面为错误");
				sleep();
				return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			String action = navigationHtml(html);

			if (StringTool.isEmpty(action)) {
				log.error("打开导航页面失败" + html);
				sleep();
				return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			Map<String, Object> join = new HashMap<>(1);
			join.put("SafeCode", handicapCaptcha);
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl + action).map(join));

			String href = getLoginHref(html);
			if (StringTool.isEmpty(href)) {
				log.error("打开角色选择页面失败" + html);
				sleep();
				return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			//只获取盘口会员href
			return href;
		} catch (Exception e) {
			log.error("打开角色选择页面失败", e);
			sleep();
			return getMemberHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), handicapUrl, handicapCaptcha, index[0],
					html);
		}
	}

	/**
	 * 获取线路
	 *
	 * @param httpConfig  http请求配置类
	 * @param href        请求路径
	 * @param handicapUrl 盘口url
	 * @param index       循环次数
	 * @return 线路
	 */
	public String[] getSelectRoutePage(HttpClientConfig httpConfig, String href, String handicapUrl, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		try {
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl + href));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "线路选择")) {
				log.error("打开选择线路界面失败" + html);
				sleep();
				return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
			}
			String url = handicapUrl.concat("/Url/GetUrlList?").concat(href.split("\\?")[1]).concat("&_=") + System
					.currentTimeMillis();

			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			String[] routes = getRoutes(httpConfig, html);
			if (ContainerTool.isEmpty(routes)) {
				log.error("获取线路失败");
				sleep();
				return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
			}
			return routes;

		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			sleep();
			return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
		}
	}

	/**
	 * 获取登录页面
	 *
	 * @param httpConfig      http请求配置类
	 * @param routes          线路
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 登录页面
	 */
	public Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, String handicapCaptcha,
											int... index) {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] >= MAX_RECURSIVE_SIZE) {
			index[1] = 0;
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return new HashMap<>(1);
		}
		String url = "http://".concat(routes[index[0]])
				.concat("/Member/Login?_=" + System.currentTimeMillis() + "&token=" + handicapCaptcha);

		Map<String, String> map = new HashMap<>(2);
		try {
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			if (StringTool.isEmpty(html)) {
				log.error("获取登录页面失败");
				sleep();
				return getLoginHtml(httpConfig, routes, handicapCaptcha, index[0], ++index[1]);
			}
			map.put("html", html);
			map.put("hostUrl", routes[index[0]]);
		} catch (Exception e) {
			log.error("获取登录页面失败", e);
			sleep();
			return getLoginHtml(httpConfig, routes, handicapCaptcha, index[0], ++index[1]);
		}
		return map;
	}

	/**
	 * 获取SESSIONID和VERSION
	 *
	 * @param html 登录页面
	 */
	public Map<String, String> getScriptInfo(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		Map<String, String> map = new HashMap<>(2);
		Document document = Jsoup.parse(html);

		Element script = document.selectFirst("script");

		String sessionid = StringUtils.substringBetween(script.html(), "var SESSIONID = \"", "\";");
		String version = StringUtils.substringBetween(script.html(), "var VERSION = \"", "\";");
		map.put("SESSIONID", "/".concat(sessionid));
		map.put("VERSION", version);
		return map;
	}

	/**
	 * 获取登录加密key
	 *
	 * @param httpConfig http请求配置类
	 * @param sessionId  参数sessionid
	 * @param hostUrl    主机地址
	 * @param index      循环次数
	 * @return 加密key
	 */
	public JSONObject getEncryptKey(HttpClientConfig httpConfig, String sessionId, String hostUrl, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		String url = "http://".concat(hostUrl).concat(sessionId).concat("/Member/GK");
		String html = null;
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url));
			if (ContainerTool.isEmpty(html)) {
				log.error("获取登录加密key失败");
				sleep();
				return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
			}
			JSONObject result = JSONObject.parseObject(html);
			if (ContainerTool.isEmpty(result) || result.getInteger("Status") - 1 != 0) {
				return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
			}
			return result.getJSONObject("Data");
		} catch (Exception e) {
			log.error("获取登录加密key失败,结果信息=" + html, e);
			sleep();
			return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
		}
	}

	/**
	 * 解析导航页面获取action
	 *
	 * @param navigationHtml 导航页面
	 * @return 导航页面
	 */
	protected String navigationHtml(String navigationHtml) {
		Document document = Jsoup.parse(navigationHtml);

		if (!StringTool.isContains(document.body().html(), "action")) {
			log.info("解析导航页面出错，页面为：" + navigationHtml);
			return null;
		}
		Element body = document.body();
		Elements select = body.select("form");
		return select.attr("action");
	}

	/**
	 * 获取线路并排序
	 *
	 * @param httpConfig http请求配置类
	 * @param html       线路
	 * @return 线路
	 */
	protected String[] getRoutes(HttpClientConfig httpConfig, String html) {
		if (ContainerTool.isEmpty(html)) {
			return null;
		}
		JSONArray array;
		try {
			array = JSONObject.parseObject(html).getJSONArray("Data");
		} catch (Exception e) {
			log.error("转换结果页面失败【" + html + "】", e);
			return null;
		}

		String[] routes = new String[array.size()];

		long[] arr = new long[array.size()];

		//判断时间延迟
		long t1, t2;
		for (int i = 0; i < array.size(); i++) {
			if (i == 5 || i == 6) {
				routes[i] = array.getString(i);
				//				arr[i] = 500;
				continue;
			}
			t1 = System.currentTimeMillis();
			String url =
					"http://" + array.getString(i).concat("/Test/GetNetSpeed?jsonp=callback").concat(String.valueOf(i))
							.concat("&_=") + System.currentTimeMillis();

			HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			t2 = System.currentTimeMillis();
			routes[i] = array.getString(i);
			arr[i] = t2 - t1;
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
}
