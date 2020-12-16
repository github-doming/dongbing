package com.ibm.common.test.wwj;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.agent.HqAgentTool;
import com.ibm.common.utils.http.utils.agent.HqAgentUtil;
import com.ibm.common.utils.http.utils.agent.SgWinAgentUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-09-16 15:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpTest {

	@Test public void test() {
		String handicapUrl = "http://sc3388.co";
		String handicapCaptcha = "hzz828";
		HttpClientConfig httpConfig = new HttpClientConfig();

		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		httpConfig.httpContext(HttpClientContext.create());
		String href = getAgentHref(httpConfig, handicapUrl, handicapCaptcha);

		if (StringTool.isEmpty(href)) {

			return;
		}
		//获取线路
		String[] routes = getSelectRoutePage(httpConfig, href, handicapUrl);

		httpConfig.httpContext().getCookieStore().clear();
		httpConfig.headers(null);
		//获取登录页面
		Map<String, String> loginInfo = getLoginHtml(httpConfig, routes, handicapCaptcha, System.currentTimeMillis());

		HttpClients.custom().setDefaultCookieStore(new BasicCookieStore()).build();
		System.out.println(loginInfo);

	}

	/**
	 * 打开页面获取代理href
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     url地址
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 获取代理登录href
	 */
	public static String getAgentHref(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
			int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > 5) {
			return null;
		}

		try {
			//获取导航页面
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));
			if (StringTool.isEmpty(html)) {
				System.out.println("打开导航页面为空");
				return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, index[0]);
			}
			if (setCookie(httpConfig, handicapUrl, html)) {
				return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (html.contains("百度一下，你就知道")) {
				System.out.println("打开导航页面为错误");
				return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			String action = HqAgentTool.navigationHtml(html);
			if (StringTool.isEmpty(action)) {
				System.out.println("打开导航页面为空");
				return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			//验证码输入
			Map<String, Object> join = new HashMap<>(1);
			join.put("SafeCode", handicapCaptcha);
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl + action).map(join));

			String href = HqAgentTool.getAgentLoginHref(html);
			if (StringTool.isEmpty(href)) {
				System.out.println("打开角色选择页面失败");
				return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			//只获取盘口代理href
			return href;
		} catch (Exception e) {
			e.printStackTrace();
			return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
	}

	/**
	 * 获取线路
	 *
	 * @param httpConfig  http请求配置类
	 * @param href        请求路径
	 * @param handicapUrl 盘口url
	 * @param index       循环次数
	 * @return 线路地址
	 */
	public static String[] getSelectRoutePage(HttpClientConfig httpConfig, String href, String handicapUrl,
			int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > 5) {
			return null;
		}
		try {
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl + href));
			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "线路选择")) {
				System.out.println("打开选择线路界面失败");
				return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
			}
			String url = handicapUrl.concat("/Url/GetUrlList?").concat(href.split("\\?")[1]).concat("&_=") + System
					.currentTimeMillis();

			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			String[] routes = HqAgentTool.getRoutes(httpConfig, html);

			if (ContainerTool.isEmpty(routes)) {
				System.out.println("获取线路失败");
				return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
			}
			return routes;
		} catch (Exception e) {
			e.printStackTrace();
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
	public static Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, String handicapCaptcha,
			long nowTime, int... index) {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[0] >= routes.length) {
			return null;
		}
		if (index[1] > 5) {
			index[0]++;
			index[1] = 0;
		}
		String url = "http://".concat(routes[index[0]])
				.concat("/Member/Login?_=" + nowTime + "&token=" + handicapCaptcha);
		Map<String,String> data = new HashMap<>();
		try {
			HttpResult result = HttpClientUtil.findInstance().get(httpConfig.url(url));

			if (result == null) {
				System.out.println("获取登录页面失败");
				return getLoginHtml(httpConfig, routes, handicapCaptcha, nowTime, index[0], ++index[1]);
			}
			if (setCookie2(httpConfig, routes[index[0]], result.getHtml())) {
				return getLoginHtml(httpConfig, routes, handicapCaptcha, nowTime, index[0], ++index[1]);
			}

			if (302-result.getStatusCode() == 0) {
				url = "http://".concat(routes[index[0]]).concat(result.getLocation());
				result = HttpClientUtil.findInstance().get(httpConfig.url(url));
			}

			data.put("hostUrl", routes[index[0]]);
			data.put("html",result.getHtml());
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			return getLoginHtml(httpConfig, routes, handicapCaptcha, nowTime, ++index[0], index[1]);
		}
	}
	/**
	 * 需要设置cookie
	 *
	 * @param httpConfig http请求配置类
	 * @param html       需要解析的页面
	 * @return 设置结果
	 */
	private static boolean setCookie(HttpClientConfig httpConfig, String url, String html) {
		//是否要放入cookie
		String cookieStr = StringUtils.substringBetween(html, "document.cookie='", "'");
		if (StringTool.notEmpty(cookieStr)) {

			httpConfig.setHeader("Cookie", cookieStr);
			return true;
		}
		return false;
	}

	/**
	 * 需要设置cookie
	 *
	 * @param httpConfig http请求配置类
	 * @param html       需要解析的页面
	 * @return 设置结果
	 */
	private static boolean setCookie2(HttpClientConfig httpConfig, String url, String html) {
		//是否要放入cookie
		String cookieStr = StringUtils.substringBetween(html, "document.cookie='", "'");
		if (StringTool.notEmpty(cookieStr)) {
			HttpClientContext context = httpConfig.httpContext();
			if (context == null) {
				context = HttpClientContext.create();
				httpConfig.httpContext(context);
			}
			String name = "", value = null, path = null, domain = url.replace("http://", "");
			for (String item : cookieStr.split(";")) {
				int index = item.indexOf('=');
				String key = item.substring(0, index).trim();
				switch (key) {
					case ClientCookie.PATH_ATTR:
						path = item.substring(index + 1).trim();
						break;
					case ClientCookie.DOMAIN_ATTR:
						//						domain = item.substring(index + 1).trim();
						break;
					default:
						name = key;
						value = item.substring(index + 1).trim();

				}
			}
			BasicClientCookie cookie = new BasicClientCookie(name, value);
			cookie.setDomain(domain);
			cookie.setPath(path);
			context.getCookieStore().addCookie(cookie);
			return true;
		}
		return false;
	}

	@Test public void testHq() {
		String handicapUrl = "http://sc3388.co";
		String handicapCaptcha = "hzz828";
		String agentAccount = "qq903903d02";
		String agentPassword = "Aa123123.";

		String existHaId;
		HqAgentUtil agentUtil = HqAgentUtil.findInstance();
		JsonResultBeanPlus bean = agentUtil.valiLogin(handicapUrl, handicapCaptcha, agentAccount, agentPassword);
		if (!bean.isSuccess()) {
			System.out.println("登录错误 =".concat(bean.toJsonString()));
			return;
		}
		existHaId = bean.getData().toString();
		Object period=GameUtil.Code.PK10.getGameFactory().period(HandicapUtil.Code.IDC).findPeriod();
		bean = agentUtil.getBetSummary(existHaId, GameUtil.Code.PK10,period,new HashMap<>());
		if (!bean.isSuccess()) {
			System.out.println("获取未结算摘要信息错误 =".concat(bean.toJsonString()));
			return;
		}
		Map<String, Map<String, Integer>> betSummary = (Map<String, Map<String, Integer>>) bean.getData();
		if (ContainerTool.isEmpty(betSummary)) {
			System.out.println("获取未结算摘要信息为空");
		}
		for (Map.Entry<String, Map<String, Integer>> entry : betSummary.entrySet()) {
			Map<String, Integer> info = entry.getValue();
			bean = agentUtil.getBetDetail(existHaId, GameUtil.Code.PK10, period,
					info.get("MemberId"), null);
			System.out.println("获取投注详情= ".concat(bean.toJsonString()));
		}
	}
	@Test public void PeriodTooltestSgWin() {
		String handicapUrl = "http://138831.co/";
		String handicapCaptcha = "85217";
		String agentAccount = "hczh001";
		String agentPassword = "Aasas1212.";

		String existHaId;
		SgWinAgentUtil agentUtil = SgWinAgentUtil.findInstance();
		JsonResultBeanPlus bean = agentUtil.valiLogin(handicapUrl, handicapCaptcha, agentAccount, agentPassword);
		if (!bean.isSuccess()) {
			System.out.println("登录错误 =".concat(bean.toJsonString()));
			return;
		}
		existHaId = bean.getData().toString();
		bean = agentUtil.memberListInfo(existHaId);
		if (!bean.isSuccess()) {
			System.out.println("获取会员账号信息错误 =".concat(bean.toJsonString()));
			return;
		}
		bean = agentUtil.getBetSummary(existHaId, GameUtil.Code.PK10, new HashedMap(),"");
		if (!bean.isSuccess()) {
			System.out.println("获取未结算摘要信息错误 =".concat(bean.toJsonString()));
			return;
		}
		Map<String, Map<String, Integer>> betSummary = (Map<String, Map<String, Integer>>) bean.getData();
		if (ContainerTool.isEmpty(betSummary)) {
			System.out.println("获取未结算摘要信息为空");
			return;
		}
		Map<String, Integer> member = betSummary.get("member");
		for (Map.Entry<String, Integer> entry : member.entrySet()) {
			bean = agentUtil.getBetDetail(existHaId, GameUtil.Code.PK10, entry.getKey(), "","",null);
			System.out.println("获取投注详情 =" + bean.toJsonString());

		}

	}


	@Test public void test06() throws IOException {

		String handicapUrl = "http://w8.sc3588.co";
		String handicapCaptcha = "hzz828";
		String url = "%s/Url/GetUrlList?token=%s&isbackend=%s&_=&d";

		url = String.format(url, handicapUrl, handicapCaptcha, "1", System.currentTimeMillis());
		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		httpConfig.httpContext(HttpClientContext.create());

		String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

		JSONObject json;
		try {
			json = JSONObject.parseObject(html);
			if (!json.containsKey("Data")) {

				System.out.println("获取线路失败，不存在key{Data}，页面为：【" + html + "】");
				return;
			}
		} catch (Exception e) {
			System.out.println("转换结果页面失败【" + html + "】");
			e.printStackTrace();
			return;
		}

		JSONArray data = json.getJSONArray("Data");
		String[] routes = new String[data.size()];

		long[] times = new long[data.size()];

		//判断时间延迟
		long timeStart, timeEnd;
		url = "http://%s/%d.jpg";
		for (int i = 0; i < data.size(); i++) {
			timeStart = System.currentTimeMillis();
			try {
				HttpClientTool.doGet(String.format(url, data.getString(i), System.currentTimeMillis()), 500);
			}catch (Exception ignore){
			}
			timeEnd = System.currentTimeMillis();
			routes[i] = data.getString(i);
			times[i] = timeEnd - timeStart;
		}

		//排序
		for (int x = 0; x < times.length; x++) {
			for (int j = 0; j < times.length - x - 1; j++) {
				if (times[j] > times[j + 1]) {
					long time = times[j];
					String route = routes[j];

					times[j] = times[j + 1];
					routes[j] = routes[j + 1];

					times[j + 1] = time;
					routes[j + 1] = route;
				}
			}
		}

		System.out.println(Arrays.toString(times));
		System.out.println(Arrays.toString(routes));

	}
}
