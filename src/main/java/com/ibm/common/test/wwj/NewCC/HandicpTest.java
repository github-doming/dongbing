package com.ibm.common.test.wwj.NewCC;

import net.sf.json.JSONObject;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import javax.json.JsonObject;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;


public class HandicpTest {

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println();
	}

	@Test
	public void testNcc() {
		String account = "cc689";
		String password = "bb11223344";
		String handicapUrl = "http://a1.zk188.co";
		String handicapCaptcha = "88996";

		HttpClientConfig config = new HttpClientConfig();
		config.httpClient(HttpClientUtil.findInstance().createHttpClient());
		try {
//			// 导航栏
			String navigationHtml = HttpClientUtil.findInstance().getHtml(config.url(handicapUrl));

			if (StringTool.isContains(navigationHtml, "Bad Gateway")) {
				System.out.println("导航打开失败");
				return;
			}

			// 安全码
			Map<String, Object> map = new HashMap<>();
			map.put("unknown", "SuperMan");
			map.put("txt", handicapCaptcha);
			map.put("hidSiteUrl", handicapUrl);
			String url = HttpClientUtil.findInstance().postHtml(config.url(handicapUrl.concat("/main")).map(map));
			if (StringTool.isContains(url, "安全码", "当前ip非法操作！")) {
				System.out.println("安全码错误！");
			}
			String[] routes = getMemberRoute(config, url);
			System.out.println(Arrays.toString(routes));

			// 登录
			// 获取登录页面


			String code = getVerifyCode(config, routes[0], "");
			// 登录
			map = new HashMap<>(2);
			map.put("loginName", account);
			map.put("loginPwd", password);
			map.put("ValidateCode", code);

			config.headers(null);
			config.httpContext(null);
			config.httpContext(HttpClientContext.create());
			String html = HttpClientUtil.findInstance().postHtml(config.url(routes[0].concat("member/Home/Ulogin")).map(map));
			// FaildReason = -1 验证码错误
			JSONObject jsonObj = JSONObject.fromObject(html);

			if (jsonObj.getString("FaildReason").contains("-1")) {
				System.out.println("验证码不正确");
				return;
			}


			if (jsonObj.getInt("LoginStatus") == 1 && jsonObj.getInt("IsNeedModityWord") == 0 && StringTool.isEmpty(jsonObj.getString("FaildReason"))) {
				System.out.println("登录成功");

				html = HttpClientUtil.findInstance().getHtml(config.url(routes[0].concat("member/Main/by_linker")));
				html = HttpClientUtil.findInstance().getHtml(config.url(routes[0].concat("member/Main")));
				html = HttpClientUtil.findInstance().getHtml(config.url(routes[0].concat("member/Main/index_")));
				html = HttpClientUtil.findInstance().getHtml(config.url(routes[0].concat("member/Main/menu")));
				html = HttpClientUtil.findInstance().getHtml(config.url(routes[0].concat("member/Main/left")));
				html = HttpClientUtil.findInstance().getHtml(config.url(routes[0].concat("member/Game_v2/bjpk10_lmp?webUU=HJK1HUWH2FBBS8DS9WQ")));


//				html = HttpClientUtil.findInstance().postHtml(config.url(routes[0].concat("member/Game_v2/gxpl")).map(join));
//				System.out.println(html);


				config.setHeader("Content-Type", "application/x-www-form-urlencoded charset=UTF-8");
				config.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
				config.setHeader("Accept-Language", "zh-CN,zh;q=0.9");
				config.setHeader("X-Requested-With", "XMLHttpRequest");
				config.setHeader("Referer", "http://51251.snn368.com/member/Game_v2/bjpk10_lmp?webUU=HJK1HUWH2FBBS8DS9WQ");
				config.setHeader("Host", "51251.snn368.com");

				Map<String, Object> join = new HashMap<>(2);
				join.put("oddsName", "40016,1.942,5");
				join.put("type", "1");
				map.clear();
				map.put("$ENTITY_JSON$", "oddsName=40016,1.942,5&type=1");
				map.put("$ENTITY_JSON$", "oddsName=40016,1.942,5&type=1");
				html = HttpClientUtil.findInstance().postHtml(config.url("http://51251.snn368.com/member/Submit/getOddsAndName").map(join));

				System.out.println(html);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}


	/**
	 * 获取验证码
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param code        验证码地址
	 * @return 验证码
	 */
	public static String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String code) {

		String content;
		//获取验证码内容,code等于空时，刷新验证码
		if (StringTool.isEmpty(code)) {
			content = HttpClientUtil.findInstance()
					.getImage(httpConfig.url(projectHost.concat("Codenum?time=") + System.currentTimeMillis()));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			System.out.println("获取验证码失败");
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", "NewCC");
		join.put("content", content);
		String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";
		String html = null;

		try {
			html = HttpClientTool.doPost(CRACK_CONTENT, join);
		} catch (Exception e) {
			System.out.println("获取验证码失败");
		}
		if (StringTool.isEmpty(html)) {
			System.out.println("获取验证码失败");
		}
		html = html.replace("\"", "");

		return html;
	}

	public static String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml) {
		Document routeDocument = Jsoup.parse(routeHtml);

		Element tbody = routeDocument.selectFirst("tbody");
		//包括会员，代理，开奖网线路
		Elements trs = tbody.select("tr");

		List<String> hrefs = new ArrayList<>();

		for (Element tr : trs) {
			Elements tds = tr.select("td");
			if (StringTool.isContains(tr.html(), "会员")) {
				for (Element td : tds) {
					String href = td.select("a").attr("href");
					if (StringTool.notEmpty(href)) {
						hrefs.add(href);
						System.out.println(href);
					}

				}
			}
		}

		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟
		long t1, t2;
		for (int i = 0; i < hrefs.size(); i++) {
			t1 = System.currentTimeMillis();
			String href = hrefs.get(i).concat("/");
//			String url = href.concat("?")
//					.concat(Integer.toHexString((int) Math.floor((1 + Math.random()) * 0x10000)));
			try {
//				HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			} catch (Exception e) {
				routes[i] = href;
				arr[i] = 1000 * 1000L;
				continue;
			}
			t2 = System.currentTimeMillis();
			long myTime = t2 - t1;
			routes[i] = href;
			arr[i] = myTime;
		}

		//线路按延时从小到大排序
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
