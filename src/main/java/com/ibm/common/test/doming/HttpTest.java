package com.ibm.common.test.doming;

import c.a.util.core.test.CommTest;
import com.alibaba.fastjson.JSON;
import com.ibm.common.utils.http.utils.RSAUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ImageTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPublicKey;
import java.util.*;

/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-09-16 15:11
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpTest extends CommTest {

	private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

	//region BW
	@Test
	public void testBW() throws InterruptedException {
		String handicapUrl = "http://216.aa18933.com";
		String handicapCaptcha = "66819";
		//		String memberAccount = "s30011";
		//		String memberPassword = "A159159.";
		String memberAccount = "1";
		String memberPassword = "1";

		HttpClientConfig httpConfig = new HttpClientConfig();

		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		httpConfig.httpContext(HttpClientContext.create());

		String routeHtml = getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha);

		String[] routes = getMemberRoute(httpConfig, routeHtml);

		Map<String, String> loginHtml = getLoginHtml(httpConfig, routes);

		String loginSrc = loginHtml.get("loginSrc");
		String verifyCode = getVerifyCode(httpConfig, loginSrc);
		Thread.sleep(1000);
		String login = getLogin(httpConfig, memberAccount, memberPassword, verifyCode, loginSrc, loginHtml.get("info"));

		//region 登录结果判断
		if (StringTool.contains(login, "驗證碼錯誤")) {
			System.out.println("验证码不正确:" + login);
			return;
		}
		if (StringTool.contains(login, "密碼錯誤", "帳號或密碼錯誤")) {
			System.out.println("用户名或密码错误:" + login);
			return;
		}
		if (StringTool.contains(login, "帳號已被鎖定", "帳號被鎖定")) {
			System.out.println("账号已被锁定:" + login);
			return;
		}
		if (StringTool.contains(login, "帳號被停用")) {
			System.out.println("账号被停用:" + login);
			return;
		}
		if (StringTool.isContains(login, "登陸成功", "帳號已被凍結", "User/ChangePwd")) {
			System.out.println("账号已被冻结:" + login);
			return;
		}
		if (StringTool.isContains(login, "登陸成功", "User/ChangePwd")) {
			System.out.println("需要修改密码:" + login);
			return;
		}
		//endregion
		String url = StringTool.getBetween(login, "\"url\":\"", "\",");

		String home = getHome(httpConfig, loginSrc, url);


		System.out.println(login);

	}

	private String getHome(HttpClientConfig httpConfig, String loginSrc, String url, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > 5) {
			return null;
		}
		String html;
		try {
			html = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(loginSrc.concat(url)));
		} catch (Exception e) {
			log.error("获取登陆信息失败", e);
			return getHome(httpConfig, loginSrc, url, ++index[0]);
		}
		return html;

	}

	private String getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword, String verifyCode,
							String loginSrc, String info, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > 5) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(1);
		String code = getLoginCode(memberAccount, memberPassword, verifyCode, info);
		join.put("code", code);
		String html;
		try {
			httpConfig.setHeader("Referer", loginSrc + "/User/login/index");
			html = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(loginSrc.concat("/User/login/CheckLogin")).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取登陆信息失败");
				return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, loginSrc, info, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取登陆信息失败", e);
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, loginSrc, info, ++index[0]);
		}
		return html;
	}

	private String getVerifyCode(HttpClientConfig httpConfig, String loginSrc, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > 5) {
			return null;
		}
		String html;
		try {
			String content = HttpClientUtil.findInstance()
					.getImage(httpConfig.url(loginSrc.concat("/Image/VerifyCodeN?r=") + RandomTool.getDouble()));
			if (StringTool.isEmpty(content)) {
				log.error("获取验证码失败");
				return getVerifyCode(httpConfig, loginSrc, ++index[0]);
			}
			Map<String, Object> join = new HashMap<>(2);
			join.put("type", "BW");
			join.put("content", content);

			html = HttpClientTool.doPost(CRACK_CONTENT, join);
			if (StringTool.isEmpty(html)) {
				log.error("破解验证码失败");
				return getVerifyCode(httpConfig, loginSrc, ++index[0]);
			}
			html = html.replace("\"", "");
			if (html.length() < 4) {
				return getVerifyCode(httpConfig, loginSrc, ++index[0]);
			}

			ImageTool.base64ToImage(content, "D:\\log\\" + html + ".jpg");
		} catch (Exception e) {
			log.error("破解验证码失败", e);
			return getVerifyCode(httpConfig, loginSrc, ++index[0]);
		}

		return html;

	}

	private Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, int... index) {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] >= 5) {
			index[1] = 0;
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return new HashMap<>(1);
		}
		Map<String, String> map = new HashMap<>(2);
		try {
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));
			if (StringTool.isEmpty(html)) {
				log.error("获取登录页面失败");
				return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
			}
			String urlTop = StringTool.getLeft(routes[index[0]], "/user");
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(urlTop + "/User/login/index"));
			map.put("info", getLoginInfo(html));
			map.put("loginSrc", urlTop);
		} catch (Exception e) {
			log.error("获取登录页面失败", e);
			return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
		}
		return map;
	}

	private String getLoginInfo(String html) {
		return StringUtils.substringBetween(html, "name=\"txtVer\" value=\"", "\"");
	}

	public String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
									 int... index) {

		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > 5) {
			return null;
		}
		//获取导航页面
		Map<String, Object> join = new HashMap<>(1);
		join.put("aqm1", handicapCaptcha);
		join.put("aqm", getAqmCode(handicapCaptcha));
		String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl + "/Nav/weblist").map(join));
		if (StringTool.isEmpty(html) || !StringTool.isContains(html, "tab_member_10")) {
			return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
		return html;
	}

	private String getAqmCode(String handicapCaptcha) {
		int rand = RandomTool.getInt(100000);
		String code = rand + "|" + handicapCaptcha;
		return Base64.encodeBase64String(code.getBytes(StandardCharsets.UTF_8));
	}

	private String getLoginCode(String memberAccount, String memberPassword, String verifyCode, String info) {
		String code = info + "|" + memberAccount + "|" + memberPassword + "|" + verifyCode;
		return Base64.encodeBase64String(code.getBytes(StandardCharsets.UTF_8));
	}

	private String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml) {
		List<String> routes = new ArrayList<>();
		Document document = Jsoup.parse(routeHtml);
		Elements elements = document.select("div#div_member_list table a");
		for (Element element : elements) {
			if (StringTool.isContains(element.text(), "会员")) {
				routes.add(element.attr("href"));
			}
		}
		return routes.toArray(new String[0]);
	}
	//endregion


	//region MOA
	@Test
	public void testMOA() throws Exception {
		String handicapUrl = "http://ak3.qkqk666.com";
		String handicapCaptcha = "2691";
		String memberAccount = "asas1212";
		String memberPassword = "asas1212a";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		httpConfig.httpContext(HttpClientContext.create());


		String routeHtml = getSelectRoutePageMoa(httpConfig, handicapUrl, handicapCaptcha);

		String[] routes = getMemberRouteMoa(httpConfig, routeHtml);
		routes = new String[]{"http://3m.nmlfx.com/"};

		Map<String, String> loginHtml = getLoginHtmlMoa(httpConfig, routes);

		String loginSrc = loginHtml.get("loginSrc");
		String verifyCode = getVerifyCodeMoa(httpConfig, loginSrc);
		Thread.sleep(1000);
		String login = getLoginMoa(httpConfig, memberAccount, memberPassword, verifyCode, loginSrc, loginHtml.get("html"));

		//region 登录结果判断
		if (StringTool.contains(login, "驗證碼錯誤")) {
			System.out.println("验证码不正确:" + login);
			return;
		}
		if (StringTool.contains(login, "密碼錯誤", "帳號或密碼錯誤")) {
			System.out.println("用户名或密码错误:" + login);
			return;
		}
		if (StringTool.contains(login, "你已被鎖定", "帳號被鎖定")) {
			System.out.println("账号已被锁定:" + login);
			return;
		}
		if (StringTool.contains(login, "帳號被停用")) {
			System.out.println("账号被停用:" + login);
			return;
		}
		if (StringTool.isContains(login, "登陸成功", "帳號已被凍結", "User/ChangePwd")) {
			System.out.println("账号已被冻结:" + login);
			return;
		}
		if (StringTool.isContains(login, "登陸成功", "User/ChangePwd")) {
			System.out.println("需要修改密码:" + login);
			return;
		}
		//endregion
	}

	private String getLoginMoa(HttpClientConfig httpConfig, String memberAccount, String memberPassword, String verifyCode, String loginSrc, String html) throws Exception {

		String publicKey = StringUtils.substringBetween(html, "10001\", \"\", \"", "\");");
		RSAPublicKey pubKey = RSAUtils
				.getPublicKey(new BigInteger(publicKey, 16).toString(), new BigInteger("10001", 16).toString());
		memberPassword = RSAUtils.encryptByPublicKey(memberPassword, pubKey).toLowerCase();

		Map<String,Object> map = new HashMap<>();
		map.put("page_type", "1");
		map.put("login_page_name", "LG");
		map.put("offsetHeight", "");
		map.put("offsetWidth", "");
		map.put("request_locale", "zh_TW");
		map.put("txt_pass", "");
		map.put("user_id", memberAccount);
		map.put("password", memberPassword);
		map.put("attach", verifyCode);
		HttpResult result = HttpClientUtil.findInstance().post(httpConfig.url(loginSrc.concat("/userLoginAction.do")).map(map));
		System.out.println(JSON.toJSONString(result));
		return result.getHtml();
	}

	private String getVerifyCodeMoa(HttpClientConfig httpConfig, String loginSrc) {
		String content = HttpClientUtil.findInstance()
				.getImage(httpConfig.url(loginSrc.concat("public/attachImage.do?v=") + System.currentTimeMillis()));
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			return getVerifyCode(httpConfig, loginSrc);
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", "NewMOA");
		join.put("content", content);

		String html = HttpClientTool.doPost(CRACK_CONTENT, join);
		if (StringTool.isEmpty(html)) {
			log.error("破解验证码失败");
			return getVerifyCode(httpConfig, loginSrc);
		}
		html = html.replace("\"", "");
		if (html.length() < 4) {
			return getVerifyCode(httpConfig, loginSrc);
		}
		ImageTool.base64ToImage(content, "D:\\log\\NewMOA\\" + html + ".jpg");
		return html;
	}

	private Map<String, String> getLoginHtmlMoa(HttpClientConfig httpConfig, String[] routes, int... index) {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] >= 5) {
			index[1] = 0;
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return new HashMap<>(1);
		}
		Map<String, String> map = new HashMap<>();
		String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));
		if (StringTool.isEmpty(html)) {
			log.error("获取登录页面失败");
			return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
		}
		map.put("html", html);
		map.put("loginSrc", routes[index[0]]);
		return map;
	}

	private String[] getMemberRouteMoa(HttpClientConfig httpConfig, String navigationHtml) {
		Document document = Jsoup.parse(navigationHtml);
		Elements as = document.select("a");
		List<String> hrefs = new ArrayList<>();

		for (Element a : as) {
			if (StringTool.contains(a.text(), "会")) {
				String on = a.attr("onclick");
				String url = on.substring(on.indexOf("http"), on.indexOf("')"));
				hrefs.add(url);
			}
		}
		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟 public/check.htm?d=0.7056380597312475
		long t1, t2;
		for (int i = 0; i < hrefs.size(); i++) {
			t1 = System.currentTimeMillis();
			String href = hrefs.get(i);
			String url = href.concat("public/check.htm?d=")
					.concat(Integer.toHexString((int) Math.floor((1 + Math.random()) * 0x10000)));
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

	private String getSelectRoutePageMoa(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha) {

		String content = HttpClientUtil.findInstance()
				.getImage(httpConfig.url(handicapUrl.concat("/attachImage.jsp?t=" + System.currentTimeMillis())));
		Map<String, Object> map = new HashMap<>(3);
		map.put("type", "NewMOANav");
		map.put("content", content);

		String code = HttpClientTool.doPost(CRACK_CONTENT, map);
		code = code.replace("\"", "");

		map.clear();
		map.put("attach", handicapCaptcha);
		map.put("sys_attach", code);
		return HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl.concat("/loginAction.do")).map(map));
	}
	//endregion


}
