package com.common.handicap.newws;

import com.common.enums.HcCodeEnum;
import com.common.handicap.crawler.BaseHandicapGrab;
import com.common.handicap.crawler.entity.HandicapUser;
import org.apache.commons.lang3.StringUtils;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.security.NoSuchAlgorithmException;
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
public abstract class BaseNewWsGrab extends BaseHandicapGrab {


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
	public String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String routeHtml;
		try {

			Map<String, Object> join = new HashMap<>(1);
			join.put("codeY", handicapCaptcha);
			join.put("submit_bt", "搜索");
			//获取线路页面
			routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl.concat("hmclient/sys/line/lportal.do?d=")).map(join));
			if (StringTool.isEmpty(routeHtml)) {
				log.error("获取线路页面失败:{}", routeHtml);
				sleep();
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			return routeHtml;
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			sleep();
			return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
	}

	/**
	 * 获取登录页面
	 *
	 * @param httpConfig http请求配置类
	 * @param captcha    安全码
	 * @param snn        snn
	 * @param routes     线路
	 * @param index      循环次数
	 * @return 登录信息map
	 */
	public Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String captcha, String snn, String[] routes, String path, int... index) {
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
		Map<String, Object> join = new HashMap<>(5);
		join.put("codeY", captcha);
		join.put("snn", snn);
		join.put("d", "");
		join.put("uid", "");
		join.put("ticket", "");
		String url = routes[index[0]].concat(path);
		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "Email")) {
				log.error("打开登录页面失败", html);
				longSleep();
				return getLoginHtml(httpConfig, captcha, snn, routes, path, index[0], ++index[1]);
			}
			loginInfoMap = new HashMap<>(3);
			loginInfoMap.put("loginSrc", routes[index[0]]);
			loginInfoMap.put("codeY", captcha);
			loginInfoMap.put("snn", snn);
			loginInfoMap.put("ticket", StringUtils.substringBetween(html, "ticketL=\"", "\";"));
		} catch (Exception e) {
			log.error("打开登录页面失败", e);
			longSleep();
			return getLoginHtml(httpConfig, captcha, snn, routes, path, index[0], ++index[1]);
		}
		return loginInfoMap;
	}

	/**
	 * 获取验证码
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param ticket      票证
	 * @param code        验证码地址
	 * @param index       循环次数
	 * @return 验证码
	 */
	public String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String ticket, String code, String path, int... index) {
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
					.getImage(httpConfig.url(projectHost.concat(path) + System.currentTimeMillis() + "&ticket=" + ticket));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			sleep();
			return getVerifyCode(httpConfig, projectHost, ticket, null, path, ++index[0]);
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", "NewWs");
		join.put("content", content);

		String html;
		try {
			html = HttpClientTool.doPost(CRACK_CONTENT, join);
		} catch (Exception e) {
			log.error("破解验证码失败", e);
			sleep();
			return getVerifyCode(httpConfig, projectHost, ticket, null, path, ++index[0]);
		}
		if (StringTool.isEmpty(html)) {
			log.error("破解验证码失败");
			sleep();
			return getVerifyCode(httpConfig, projectHost, ticket, null, path, ++index[0]);
		}
		html = html.replace("\"", "");
		if (html.length() < 4) {
			return getVerifyCode(httpConfig, projectHost, ticket, null, path, ++index[0]);
		}
		return html;
	}

	/**
	 * 盘口登录
	 *
	 * @param httpConfig  http请求配置类
	 * @param accountInfo 盘口会员
	 * @param ticket      盘口票证
	 * @param verifyCode  验证码
	 * @param projectHost 线路地址
	 * @param snn         snn
	 * @param index       循环次数
	 * @return 盘口主页面href
	 */
	public String getLogin(HttpClientConfig httpConfig, HandicapUser accountInfo,
						   String verifyCode, String projectHost, String ticket, String snn, String path, int... index) throws NoSuchAlgorithmException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		Map<String, Object> join = new HashMap<>(6);
		join.put("codeY", accountInfo.handicapCaptcha());
		join.put("snn", snn);
		join.put("zhanghao", accountInfo.account());
		join.put("mima", Md5Tool.md5Hex(accountInfo.password()));
		join.put("validateCode", verifyCode);
		join.put("ticket", ticket);
		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat(path)).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				longSleep();
				return getLogin(httpConfig, accountInfo, verifyCode, projectHost, ticket, snn, path, index[0], ++index[1]);
			}

			if (loginInfo.contains("驗證碼")) {
				log.error("验证码错误");
				longSleep();
				// TODO区分代理和会员path
				verifyCode = getVerifyCode(httpConfig, projectHost, ticket, null, "user/getValidateCodeF.do?t=");
				return getLogin(httpConfig, accountInfo, verifyCode, projectHost, ticket, snn, path, index[0], ++index[1]);
			}
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			longSleep();
			return getLogin(httpConfig, accountInfo, verifyCode, projectHost, ticket, snn, path, index[0], ++index[1]);
		}

		return loginInfo;
	}


	/**
	 * 获取会员可用线路
	 *
	 * @param httpConfig http请求配置类
	 * @param hrefs      线路集合
	 * @return 线路数组
	 */
	public String[] getRoute(HttpClientConfig httpConfig, List<String> hrefs) {

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

	public String memberId() {
		return crawlerImage().crawlerInfo().get("memberId");
	}

	public void memberId(String memberId) {
		crawlerImage().crawlerInfo("memberId", memberId);
	}
}
