package com.common.handicap.newcc;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.common.handicap.crawler.BaseHandicapGrab;
import org.apache.http.client.protocol.HttpClientContext;
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
 * NewWs盘口抓取操作抽象类
 *
 * @Author: Dongming
 * @Date: 2020-05-11 18:30
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseNewCcGrab extends BaseHandicapGrab {


	/**
	 * 获取线路页面
	 * 会员代理共用
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 可用线路
	 */
	public String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
									 int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String routeHtml;
		try {
			String navigationHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));
			if (StringTool.isContains(navigationHtml, "Bad Gateway")) {
				System.out.println("导航打开失败");
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			// 安全码
			Map<String, Object> map = new HashMap<>(3);
			map.put("unknown", "SuperMan");
			map.put("txt", handicapCaptcha);
			map.put("hidSiteUrl", handicapUrl);

			//获取线路页面
			routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl.concat("main")).map(map));

			if (StringTool.isEmpty(routeHtml)) {
				log.error("获取线路页面失败:{}", routeHtml);

				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			sleep();
			return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
		//可用线路页面
		return routeHtml;
	}

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
					.getImage(httpConfig.url(projectHost.concat("/Codenum?time=") + System.currentTimeMillis()));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			sleep();
			return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", "NewCC");
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
	 * 获取登录页面
	 *
	 * @param httpConfig http请求配置类
	 * @param routes     线路
	 * @param index      循环次数
	 * @param path       路径 -会员：member -代理：admin
	 * @return 登录信息map
	 */
	public Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, String path, int... index) {
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

		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]].concat(path)));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "Welcome")) {
				log.error("打开登录页面失败", html);
				sleep();
				return getLoginHtml(httpConfig, routes, path, index[0], ++index[1]);
			}
			loginInfoMap = new HashMap<>(3);
			loginInfoMap.put("loginSrc", routes[index[0]]);

		} catch (Exception e) {
			log.error("打开登录页面失败", e);
			sleep();
			return getLoginHtml(httpConfig, routes, path, index[0], ++index[1]);
		}
		//获取action;
		loginInfoMap.put("loginSrc1", routes[index[0]]);
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
	 * @param path           路径	-会员 member/Home/Ulogin
	 *                       -代理 admin
	 * @return 盘口主页面href
	 */
	public String getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
						   String verifyCode, String projectHost, String path, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.headers(null);
		httpConfig.httpContext(null);
		httpConfig.httpContext(HttpClientContext.create());

		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
		Map<String, Object> join = new HashMap<>(4);

		join.put("loginName", memberAccount);
		join.put("loginPwd", memberPassword);
		join.put("ValidateCode", verifyCode);

		String url = projectHost.concat(path);
		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				sleep();
				return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, path, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			sleep();
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, path, ++index[0]);
		}
		JSONObject jsonObj = JSONObject.parseObject(loginInfo);

		if (jsonObj.getString("FaildReason").contains("-1")) {
			log.error("验证码错误");
			sleep();
			verifyCode = getVerifyCode(httpConfig, projectHost, null);
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, path, ++index[0]);
		}
		return loginInfo;
	}


	/**
	 * 获取代理可用线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  线路页面
	 * @return 线路数组
	 */
	public static String[] getRoute(HttpClientConfig httpConfig, String routeHtml, String type) {
		Document routeDocument = Jsoup.parse(routeHtml);

		Element tbody = routeDocument.selectFirst("tbody");
		//包括会员，代理，开奖网线路
		Elements trs = tbody.select("tr");

		List<String> hrefs = new ArrayList<>();

		for (Element tr : trs) {
			Elements tds = tr.select("td");
			if (StringTool.isContains(tr.html(), type)) {
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
			String url = href.concat("?")
					.concat(Integer.toHexString((int) Math.floor((1 + Math.random()) * 0x10000)));
			try {
				HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			} catch (Exception e) {
				log.error("NEWCC连接失败,结果信息=", e);
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
	 * 登陆错误
	 *
	 * @param msg 登陆结果页面
	 * @return 错误信息
	 */
	public HcCodeEnum loginError(String msg) {
		log.error("NewCc盘口会员登陆异常，异常的登陆结果页面为：" + msg);
		if (StringTool.isContains(msg, "-1")) {
			return HcCodeEnum.IBS_403_VERIFY_CODE;
		} else if (StringTool.contains(msg, "锁定", "账户已禁用")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "账号或密码错误")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}
}
