package com.ibm.common.test.doming;
import com.ibm.common.core.CommTest;
import net.sf.json.JSONObject;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 盘口测试类
 * @Author: Dongming
 * @Date: 2019-11-27 17:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HandicapTest extends CommTest {


	@Test public void test() throws NoSuchAlgorithmException {
		String account = "mwwssdd111";
		String password = "ABab135135a";
		String handicapUrl = "http://z1.kp6668.com";
		String handicapCaptcha = "335599";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		httpConfig.httpContext(HttpClientContext.create());

		String selectPage = geSelectPage(httpConfig, handicapUrl, handicapCaptcha);
		if (StringTool.isEmpty(selectPage)) {
			return;
		}
		//获取线路
		String[] routes = getSelectRoute(httpConfig, selectPage);

		httpConfig.httpContext().getCookieStore().clear();
		httpConfig.headers(null);

		String projectHost = "http://75151.sm6532.com/";
		String code = getVerifyCode(httpConfig, projectHost, "");
		// 登录
		JSONObject jsonObj = login(httpConfig, projectHost, account, password, code);

		if (jsonObj.getInt("LoginStatus") == 1 && jsonObj.getInt("IsNeedModityWord") == 0 && StringTool
				.isEmpty(jsonObj.getString("FaildReason"))) {
			System.out.println("登录成功");

			httpConfig.headers(null);
			String url = projectHost + "member/Main/getnews";
			String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url));
			System.out.println(url + ":" + html);
			url = projectHost + "member/Game_v2/getSyjq";
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url));
			System.out.println(url + ":" + html);

			url = projectHost + "member/Game_v2/gxpl";

			Map<String,Object> map = new HashMap<>();
			map.put("$ENTITY_JSON$","");
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(map));
			System.out.println(url + ":" + html);
		}

	}
	private JSONObject login(HttpClientConfig httpConfig, String projectHost, String account, String password,
			String code) {
		Map<String, Object> map = new HashMap<>(2);
		map.put("loginName", account);
		map.put("loginPwd", password);
		map.put("ValidateCode", code);
		httpConfig.setHeader("Referer", projectHost.concat("/member/Home/login_new"));
		String html = HttpClientUtil.findInstance()
				.postHtml(httpConfig.url(projectHost.concat("member/Home/Ulogin")).map(map));
		// FaildReason = -1 验证码错误
		JSONObject jsonObj = JSONObject.fromObject(html);

		if (jsonObj.getString("FaildReason").contains("-1")) {
			System.out.println("验证码不正确");
			return login(httpConfig, projectHost, account, password, getVerifyCode(httpConfig, projectHost, ""));
		}
		return jsonObj;
	}

	private String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String code) {

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
	private String getLoginHtml(HttpClientConfig httpConfig, String[] routes) {
		return HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[0].concat("member")));
	}
	private String[] getSelectRoute(HttpClientConfig httpConfig, String routeHtml) {
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
			String url = href.concat("?").concat(Integer.toHexString((int) Math.floor((1 + Math.random()) * 0x10000)));
			try {
				HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
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
	private String geSelectPage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha) {
		String navigationHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));

		if (StringTool.isContains(navigationHtml, "Bad Gateway")) {
			System.out.println("导航打开失败");
			return null;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("unknown", "SuperMan");
		map.put("txt", handicapCaptcha);
		map.put("hidSiteUrl", handicapUrl);
		String selectPage = HttpClientUtil.findInstance()
				.postHtml(httpConfig.url(handicapUrl.concat("/main")).map(map));
		if (StringTool.isContains(selectPage, "安全码", "当前ip非法操作！")) {
			System.out.println("安全码错误！");
		}
		return selectPage;
	}

}
