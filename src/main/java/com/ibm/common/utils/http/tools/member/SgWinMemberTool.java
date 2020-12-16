package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 138新盘口会员工具类
 * @Author: null
 * @Date: 2019-10-25 10:15
 * @Version: v1.0
 */
public class SgWinMemberTool {
	protected static final Logger log = LogManager.getLogger(SgWinMemberTool.class);
	private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
	private static final long SLEEP = 1000L;

	private static final Integer MAX_RECURSIVE_SIZE = 5;
	private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";
	// TODO 开启页面函数

	/**
	 * 获取线路页面
	 * https://138111.co/user-search-result.php?search=160127
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 会员可用线路
	 */
	public static String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
											int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String routeHtml;
		try {
			String navigationHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));

			if (StringTool.isEmpty(navigationHtml) || StringTool.isContains(navigationHtml, "Bad Gateway")) {
				log.error("获取导航页面失败：{}", navigationHtml);
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (StringTool.isContains(navigationHtml, "百度")) {
				return null;
			}
			//多次输入验证码,可能跳过导航页面，判断是否为线路选择页面
			if (StringTool.isContains(navigationHtml, "线路选择")) {
				return navigationHtml;
			}

			//导航页action
			String actionUrl = getNavigationAction(navigationHtml);
			//获取线路页面url
			String url = handicapUrl.concat(actionUrl).concat("?search=").concat(handicapCaptcha);
			//获取线路页面
			routeHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "线路选择")) {
				log.error("获取线路页面失败:{}", routeHtml);
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(SLEEP);
			return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
		//会员可用线路页面
		return routeHtml;
	}

	/**
	 * 获取登录页面
	 *
	 * @param httpConfig http请求配置类
	 * @param routes     线路
	 * @param index      循环次数
	 * @return 登录信息map
	 */
	public static Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, int... index)
			throws InterruptedException {
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
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]].concat("login")));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "Welcome")) {
				log.error("打开登录页面失败", html);
				Thread.sleep(2 * SLEEP);
				return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
			}
			loginInfoMap = new HashMap<>(3);
			loginInfoMap.put("loginSrc", routes[index[0]]);

		} catch (Exception e) {
			log.error("打开登录页面失败", e);
			Thread.sleep(2 * SLEEP);
			return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
		}
		//获取验证码code和action
		getCodeAndAction(html, loginInfoMap);

		return loginInfoMap;
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
	public static String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String code, int... index)
			throws InterruptedException {
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
					.getImage(httpConfig.url(projectHost.concat("code?_=") + System.currentTimeMillis()));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", "SGWIN");
		join.put("content", content);

		String html;
		try {
			html = HttpClientTool.doPost(CRACK_CONTENT, join);
		} catch (Exception e) {
			log.error("破解验证码失败", e);
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
		}
		if (StringTool.isEmpty(html)) {
			log.error("破解验证码失败");
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
		}
		html = html.replace("\"", "");
		if (html.length() < 4) {
			return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
		}
		return html;
	}

	/**
	 * 盘口登录
	 *
	 * @param httpConfig     http请求配置类
	 * @param memberAccount  盘口会员账号
	 * @param memberPassword 盘口会员密码
	 * @param verifyCode     验证码
	 * @param projectHost    线路地址
	 * @param action         请求action
	 * @param index          循环次数
	 * @return 盘口主页面href
	 * @throws IOException
	 */
	public static String getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
								  String verifyCode, String projectHost, String action, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
		Map<String, Object> join = new HashMap<>(4);

		join.put("type", 1);
		join.put("account", memberAccount);
		join.put("password", memberPassword);
		join.put("code", verifyCode);

		String url = projectHost.concat(action);
		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				Thread.sleep(2 * SLEEP);
				return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, action, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, action, ++index[0]);
		}
		//账号冻结,账户已禁用,第一次登陆
		if (StringTool.contains(loginInfo, "账号或密码错误", "抱歉!你的帐号已被冻结", "账户已禁用", "修改密码")) {
			return loginInfo;
		}
		//验证码错误
		if (StringTool.isContains(loginInfo, "验证码")) {
			log.error("验证码错误");
			Thread.sleep(2 * SLEEP);
			verifyCode = getVerifyCode(httpConfig, projectHost, null);
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, action, ++index[0]);
		}
		//登录结果
		return loginInfo;
	}

	/**
	 * 获取盘口主页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param index       循环次数
	 * @return 会员信息
	 */
	public static String getHomePage(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String indexHtml;
		try {
			//获取盘口主页面
			indexHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(projectHost.concat("member/index")));
			if (StringTool.isEmpty(indexHtml) || !StringTool.isContains(indexHtml, "Welcome")) {
				log.error("获取盘口主页面失败", indexHtml);
				Thread.sleep(2 * SLEEP);
				return getHomePage(httpConfig, projectHost, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取盘口主页面失败", e);
			Thread.sleep(2 * SLEEP);
			return getHomePage(httpConfig, projectHost, ++index[0]);
		}
		return indexHtml;
	}

	/**
	 * 获取用户信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param index       循环次数
	 * @return 用户信息
	 */
	public static JSONObject getUserInfo(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		String html = null;
		JSONObject userInfo;
		String url = projectHost.concat("member/accounts?_=") + System.currentTimeMillis();
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html) || StringTool.isContains(html, "An error occurred")) {
				log.error("获取用户信息失败，结果信息=" + html);
				Thread.sleep(SLEEP);
				return getUserInfo(httpConfig, projectHost, ++index[0]);
			}
			JSONArray htmlJson = JSONArray.parseArray(html);

			double maxLimit = 0;
			double balance = 0;
			double betting = 0;
			double result = 0;
			for (int i = 0; i < htmlJson.size(); i++) {
				JSONObject json = htmlJson.getJSONObject(i);
				maxLimit += NumberTool.getDouble(json.get("maxLimit"), 0);
				balance += NumberTool.getDouble(json.get("balance"), 0);
				betting += NumberTool.getDouble(json.get("betting"), 0);
				result += NumberTool.getDouble(json.get("result"), 0);
			}
			userInfo = new JSONObject();
			userInfo.put("maxLimit", maxLimit);
			userInfo.put("balance", balance);
			userInfo.put("betting", betting);
			userInfo.put("result", result);
			return userInfo;
		} catch (Exception e) {
			log.error("获取用户余额信息失败,错误信息=" + html, e);
			Thread.sleep(SLEEP);
			return getUserInfo(httpConfig, projectHost, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
		}
	}

	/**
	 * 获取游戏限额
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param game        盘口游戏code
	 * @param index       循环次数
	 * @return 游戏限额信息
	 */
	public static JSONArray getQuotaList(HttpClientConfig httpConfig, String projectHost, String game, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONArray();
		}
		String html = null;

		String url = projectHost.concat("member/info?lottery=").concat(game);
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败");
				Thread.sleep(SLEEP);
				return getQuotaList(httpConfig, projectHost, game, ++index[0]);
			}
			if (!StringTool.isContains(html, "会员资料")) {
				log.error("获取游戏限额页面失败，错误页面=" + html);
				Thread.sleep(SLEEP);
				return getQuotaList(httpConfig, projectHost, game, ++index[0]);
			}
			return getLimit(html);
		} catch (Exception e) {
			log.error("获取游戏限额失败,错误信息=" + html, e);
			Thread.sleep(SLEEP);
			return getQuotaList(httpConfig, projectHost, game, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
		}
	}

	/**
	 * 投注
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param betsArray   投注信息
	 * @param drawNumber  开奖期数
	 * @param gameCode    游戏code
	 * @return 投注结果
	 */
	public static JSONObject betting(HttpClientConfig httpConfig, String projectHost, JSONArray betsArray,
									 String drawNumber, String gameCode) {
		httpConfig.setHeader("Content-Type", "application/json");
        httpConfig.httpTimeOut(20 * 1000);
		JSONObject info = new JSONObject();
		info.put("bets", betsArray);
		info.put("drawNumber", drawNumber);
		info.put("ignore", false);
		info.put("lottery", gameCode);

		Map<String, Object> join = new HashMap<>(1);
		join.put("$ENTITY_JSON$", info);
		String resultHtml = null;
		String url = projectHost.concat("member/bet");
		try {
			resultHtml = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(url).map(join));
			if (ContainerTool.isEmpty(resultHtml)) {
				log.error("投注结果页面为空,投注项为：" + betsArray);
				return new JSONObject();
			}
			return JSONObject.parseObject(resultHtml);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, info, 0, resultHtml));
		}
	}

	/**
	 * 获取未结算信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param game        盘口游戏code
	 * @param index       循环次数
	 * @return 未结算信息
	 */
	public static String getIsSettle(HttpClientConfig httpConfig, String projectHost, String game, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url =
				projectHost.concat("member/lasts?lottery=").concat(game).concat("&_=") + System.currentTimeMillis();
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算页面失败", html);
				Thread.sleep(SLEEP);
				return getIsSettle(httpConfig, projectHost, game, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取未结算页面失败", e);
			Thread.sleep(SLEEP);
			return getIsSettle(httpConfig, projectHost, game, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
		}
	}

	/**
	 * 获取未结算页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param index       循环次数
	 * @return
	 */
	public static JSONArray getIsSettlePage(HttpClientConfig httpConfig, String projectHost, int page, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONArray();
		}
		String url = projectHost.concat("member/bets?page=") + page;
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算页面失败", html);
				Thread.sleep(SLEEP);
				return getIsSettlePage(httpConfig, projectHost, page, ++index[0]);
			}
			if (StringTool.isContains(html, "暂无数据")) {
				return new JSONArray();
			}
			return getSettleInfo(html);
		} catch (Exception e) {
			log.error("获取未结算页面失败", e);
			Thread.sleep(SLEEP);
			return getIsSettlePage(httpConfig, projectHost, page, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
		}
	}

	/**
	 * 获取赔率信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param gameCode    盘口游戏code
	 * @param oddsCode    赔率code
	 * @param index       循环次数
	 * @return
	 */
	public static JSONObject getOddsInfo(HttpClientConfig httpConfig, String projectHost, String gameCode,
										 String oddsCode, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		String html = null;
		String url = projectHost.concat("member/odds?lottery=").concat(gameCode).concat("&games=").concat(oddsCode)
				.concat("&_=") + System.currentTimeMillis();
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取赔率信息页面失败");
				Thread.sleep(SLEEP);
				return getOddsInfo(httpConfig, projectHost, gameCode, oddsCode, ++index[0]);
			}
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取赔率信息页面失败,结果信息=" + html, e);
			Thread.sleep(SLEEP);
			return getOddsInfo(httpConfig, projectHost, gameCode, oddsCode, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
		}
	}

	// TODO 功能函数

	/**
	 * 获取导航页action
	 *
	 * @param navigationHtml 导航页面
	 * @return action路径
	 */
	private static String getNavigationAction(String navigationHtml) {
		Document navigationDocument = Jsoup.parse(navigationHtml);

		Element select = navigationDocument.selectFirst("form");

		return select.attr("action");
	}

	/**
	 * 获取会员可用线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  线路页面
	 * @return 线路数组
	 */
	public static String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml)  {
		Document routeDocument = Jsoup.parse(routeHtml);

		Element tbody = routeDocument.selectFirst("tbody");
		//包括会员，代理，开奖网线路
		Elements trs = tbody.select("tr");

		List<String> hrefs = new ArrayList<>();

		for (Element tr : trs) {
			String type = tr.selectFirst("td").text();
			if (StringTool.isContains(type, "会员")) {
				String href = tr.select("a").attr("href");
				hrefs.add(href.split("=")[1]);
			}
		}
		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟
		long t1, t2;
        for (int i=0; i < hrefs.size(); i++) {
            t1 = System.currentTimeMillis();
            String href = hrefs.get(i).concat("/");
            String url = href.concat("?random-no-cache=")
                    .concat(Integer.toHexString((int) Math.floor((1 + Math.random()) * 0x10000)));
            try {
                HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
            } catch (Exception e) {
                log.error("SGWIN连接失败,结果信息=", e);
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
	 * 获取验证码code和action
	 *
	 * @param loginHtml    登录页面
	 * @param loginInfoMap 登录信息map
	 */
	private static void getCodeAndAction(String loginHtml, Map<String, String> loginInfoMap) {
		Document document = Jsoup.parse(loginHtml);
		Element select = document.selectFirst("form");

		String action = select.attr("action");

		String code = select.select("img").attr("src");

		loginInfoMap.put("action", action);
		loginInfoMap.put("code", code);
	}

	/**
	 * 登陆错误
	 *
	 * @param msg 登陆结果页面
	 * @return 错误信息
	 */
	public static HcCodeEnum loginError(String msg) {
		log.error("SgWin盘口会员登陆异常，异常的登陆结果页面为：" + msg);
		if (StringTool.isContains(msg, "验证码错误")) {
			return HcCodeEnum.IBS_403_VERIFY_CODE;
		} else if (StringTool.contains(msg, "抱歉!你的帐号已被冻结", "账户已禁用")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "账号或密码错误")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "修改密码")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else if (StringTool.contains(msg, "变更密码")) {
			return HcCodeEnum.IBS_403_PASSWORD_EXPIRED;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}

	/**
	 * 解析游戏限额页面
	 *
	 * @param html 游戏限额页面
	 * @return 游戏限额
	 */
	private static JSONArray getLimit(String html) {
		Document document = Jsoup.parse(html);
		Element tableClass = document.getElementsByClass("list table data_table").first();

		Elements trs = tableClass.select("tbody tr");

		JSONArray quota = new JSONArray();
		JSONArray array;
		for (Element tr : trs) {
			array = new JSONArray();
			String text = tr.text();
			String[] limits = text.split(" ");

			array.add(Integer.parseInt(limits[2]));
			array.add(Integer.parseInt(limits[3]));
			array.add(Integer.parseInt(limits[4]));
			quota.add(array);
		}
		return quota;
	}

	/**
	 * 获取投注详情内容
	 *
	 * @param gameCode 游戏code
	 * @param betItems 投注详情
	 * @param odds     赔率
	 * @return
	 */
	public static JSONArray getBetItemInfo(GameUtil.Code gameCode, List<String> betItems, JSONObject odds) {
		JSONArray betsArray = new JSONArray();
		Map<String, String> ballCode = BallCodeTool.getBallCode(HandicapUtil.Code.SGWIN, gameCode);
		JSONObject betInfo;
		for (String betItem : betItems) {
			betInfo = new JSONObject();
			String[] items = betItem.split("\\|");
			String bet = items[0].concat("|").concat(items[1]);
			//单注金额须为整数值
			int fund = (int) NumberTool.doubleT(items[2]);
			String betCode = ballCode.get(bet);
			if (StringTool.isEmpty(betCode)) {
				log.error("错误的投注项" + betItem);
				continue;
			}
			String[] betCodes = betCode.split(":");
			betInfo.put("game", betCodes[1]);
			betInfo.put("contents", betCodes[2]);
			betInfo.put("amount", fund);
			betInfo.put("odds", odds.get(betCodes[1].concat("_").concat(betCodes[2])));
			betInfo.put("title", betCodes[0]);
			betsArray.add(betInfo);
		}
		return betsArray;
	}

	/**
	 * 获取投注结果
	 *
	 * @param gameCode 游戏code
	 * @param isSettle 未结算信息
	 * @param ids      当前投注成功注单号
	 * @return
	 */
	public static JSONArray getBetResult(GameUtil.Code gameCode, String isSettle, String ids) {
		Map<String, String> ballInfo = BallCodeTool.getBallInfoCode(HandicapUtil.Code.SGWIN, gameCode);

		JSONArray betResult = new JSONArray();
		JSONArray data = JSONArray.parseArray(isSettle);
		JSONObject info;
		JSONArray betInfo;
		for (int i = 0; i < data.size(); i++) {
			info = data.getJSONObject(i);
			String item = ballInfo.get(info.getString("t").replace(" ", ""));
			if (StringTool.isEmpty(item)) {
				log.error("错误的投注项：" + info);
				continue;
			}
			if (!StringTool.isContains(ids, info.getString("id"))) {
				continue;
			}
			betInfo = new JSONArray();
			//注单号
			betInfo.add(info.get("id"));
			//投注项
			betInfo.add(item);
			//投注金额
			betInfo.add(info.get("a"));
			//赔率
			betInfo.add(info.get("o"));
			betResult.add(betInfo);
		}
		return betResult;
	}

	/**
	 * 解析未结算页面
	 *
	 * @param html 未结算页面
	 * @return
	 */
	private static JSONArray getSettleInfo(String html) {
		Document document = Jsoup.parse(html);

		Elements trs = document.select("table[class=list table]>tbody>tr");

		JSONArray jsonArray = new JSONArray();
		for (Element tr : trs) {
			JSONArray array = new JSONArray();
			array.add(tr.child(0).text());
			array.add(tr.child(3).text().replace(" ", ""));
			array.add(tr.child(5).text());
			jsonArray.add(array);
		}
		return jsonArray;
	}

	/**
	 * @param ballInfo 球号信息
	 * @param data     未结算信息
	 * @param ids      当前投注成功注单号
	 * @return
	 */
	public static void matchIsSettleInfo(JSONArray betResult, Map<String, String> ballInfo, JSONArray data, JSONArray ids) {
		JSONArray info;
		JSONArray betInfo;
		for (int i = 0; i < data.size(); i++) {
			info = data.getJSONArray(i);
			if (!StringTool.isContains(ids.toString(), info.getString(0))) {
				continue;
			}
			String[] items = info.getString(1).split("@");
			String item = ballInfo.get(items[0]);
			if (StringTool.isEmpty(item)) {
				log.error("错误的投注项：" + info);
				continue;
			}
			betInfo = new JSONArray();
			//注单号
			betInfo.add(info.getString(0));
			//投注项
			betInfo.add(item);
			//投注金额
			betInfo.add(info.getString(2));
			//赔率
			betInfo.add(items[1]);
			betResult.add(betInfo);
			if (betResult.size() >= ids.size()) {
				break;
			}
		}
	}
}
