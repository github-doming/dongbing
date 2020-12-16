package com.common.handicap.un;

import com.common.enums.HcCodeEnum;
import com.common.handicap.crawler.BaseHandicapGrab;
import org.apache.commons.lang3.StringUtils;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * UN盘口抓取操作抽象类
 *
 * @Author: Dongming
 * @Date: 2020-05-11 18:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseUnGrab extends BaseHandicapGrab {

	/**
	 * 获取验证码
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param code        验证码地址
	 * @param index       循环次数
	 * @return 验证码
	 */
	public String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String code, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String content;
		//获取验证码内容,code等于空时，刷新验证码
		if (StringTool.isEmpty(code)) {
			content = HttpClientUtil.findInstance()
					.getImage(httpConfig.url(projectHost.concat("captcha_image?d=") + System.currentTimeMillis()));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			sleep();
			return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", "UN");
		join.put("content", content);

		String html;
		try {
			html = HttpClientTool.doPost(CRACK_CONTENT, join);
		} catch (Exception e) {
			log.error("破解验证码失败", e);
			sleep();
			return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
		}
		if (StringTool.isEmpty(html)) {
			log.error("破解验证码失败");
			sleep();
			return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
		}
		html = html.replace("\"", "");
		if (html.length() < 4) {
			return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
		}
		return html;
	}

	/**
	 * 获取线路页面
	 * http://z1.kp6668.com/main?unknown=SuperMan&txt=335599&hidSiteUrl=z1.kp6668.com
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 会员可用线路
	 */
	public String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String code, String handicapCaptcha,
									 int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpConfig.setHeader("Content-Type", "application/json;charset=utf-8");

		String routeHtml;
		try {

			Map<String, Object> join = new HashMap<>(1);
			join.put("$ENTITY_STRING$", "{\"lineCode\":\"" + handicapCaptcha + "\",\"code\":\"" + code + "\"}");
			//获取线路页面
			routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl.concat("wb4/websiteLogin")).map(join));
			if (StringTool.isEmpty(routeHtml)) {
				log.error("获取线路页面失败:{}", routeHtml);
				sleep();
				return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
			}
			if (StringTool.contains(routeHtml, "验证码")) {
				code = getVerifyCode(httpConfig, handicapUrl, null);
				return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
			}
			routeHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl.concat("wb4/index")));

		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			sleep();
			return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
		}
		//会员可用线路页面
		return routeHtml;
	}

	/**
	 * 获取会员可用线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  线路页面
	 * @return 线路数组
	 */
	public String[] getRoute(HttpClientConfig httpConfig, String routeHtml, String type) {

		Document document = Jsoup.parse(routeHtml);
		Elements as = document.select("a");
		List<String> hrefs = new ArrayList<>();
		for (Element el : as) {
			if (StringTool.contains(el.text(), type)) {
				String on = el.attr("onclick");
				String url = StringUtils.substringBetween(on, "('", "/");
				hrefs.add("https://".concat(url));
			}
		}

		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟
		long t1, t2;
		for (int i = 0; i < hrefs.size(); i++) {
			t1 = System.currentTimeMillis();
			String href = hrefs.get(i).concat("/");
			try {
				HttpClientUtil.findInstance().getHtml(httpConfig.url(href));
			} catch (Exception e) {
				log.error("UN连接失败,结果信息=", e);
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

	/**
	 * 获取登录页面
	 *
	 * @param httpConfig http请求配置类
	 * @param routes     线路
	 * @param index      循环次数
	 * @return 登录信息map
	 */
	public Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, String type, int... index) {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] > MAX_RECURSIVE_SIZE) {
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return new HashMap<>(1);
		}
		String html;
		Map<String, String> loginInfoMap;

		httpConfig.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n");

		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]].concat(type)));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "UN")) {
				log.error("打开登录页面失败", html);
				longSleep();
				return getLoginHtml(httpConfig, routes, type, index[0], ++index[1]);
			}
			loginInfoMap = new HashMap<>(1);
			loginInfoMap.put("loginSrc", routes[index[0]]);

		} catch (Exception e) {
			log.error("打开登录页面失败", e);
			longSleep();
			return getLoginHtml(httpConfig, routes, type, index[0], ++index[1]);
		}
		return loginInfoMap;
	}

	/**
	 * 盘口登录
	 *
	 * @param httpConfig     http请求配置类
	 * @param memberAccount  盘口会员账号
	 * @param memberPassword 盘口会员密码
	 * @param verifyCode     验证码
	 * @param projectHost    线路地址
	 * @param index          循环次数
	 * @return 盘口主页面href
	 */
	public String getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
						   String verifyCode, String projectHost, String type, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		httpConfig.setHeader("Accept", "*/*");
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		Map<String, Object> join = new HashMap<>(8);

		join.put("userName", memberAccount);
		join.put("password", memberPassword);
		join.put("code", verifyCode);
		join.put("btnSubmit", "%E7%AB%8B%E5%8D%B3%E7%99%BB%E5%BD%95");
		join.put("gotourl", "");
		join.put("login_0521_type", type);
		join.put("language", "cn");
		join.put("pubKey", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrZCuRaDnL09yPIbtOBqCPY2PFBecVh/iuPyfkehqgOE4/pUnKCZjnkv9gC+W6phTgZwMXxv51Hzqb2KZCu0b05ag5uThxvLK/+CZB8wcYiyNYogM6Hc7kwJmQ5O4JpBkKuSRUz6OCKdRSnYb2O4w/uut9CnyGt9jDWDhf5iugrQIDAQAB");

		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("login")).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				longSleep();
				return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, type, ++index[0]);
			}

			if (loginInfo.contains("验证码")) {
				log.error("验证码错误");
				longSleep();
				verifyCode = getVerifyCode(httpConfig, projectHost, null);
				return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, type, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			longSleep();
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, type, ++index[0]);
		}

		return loginInfo;
	}


	/**
	 * 登陆错误
	 *
	 * @param msg 错误信息
	 * @return 错误信息
	 */
	public HcCodeEnum loginError(String msg) {
		if (StringTool.contains(msg, "帐号与密码不匹配")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "帐号被锁定")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "请求过于频繁")) {
			return HcCodeEnum.IBS_403_LOGIN_OFTEN;
		} else if (StringTool.contains(msg, "您的帐户为初次登陆", "密码由后台重新设定")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}

	public String ticket() {
		return crawlerImage().crawlerInfo().get("ticket");
	}

	public void ticket(String ticket) {
		crawlerImage().crawlerInfo("ticket", ticket);
	}
}
