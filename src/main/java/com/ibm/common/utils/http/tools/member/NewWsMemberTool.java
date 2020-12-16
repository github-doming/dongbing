package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.NewWsConfig;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.handicap.config.NewWsBallConfig;
import com.ibm.common.utils.handicap.config.NewWsHappyConfig;
import com.ibm.common.utils.handicap.config.NewWsNumberConfig;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2020/5/14 15:54
 * @Version: v1.0
 */
public class NewWsMemberTool {
	protected static final Logger log = LogManager.getLogger(SgWinMemberTool.class);
	private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
	private static final long SLEEP = 1000L;

	private static final Integer MAX_RECURSIVE_SIZE = 5;
	private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

	// TODO 开启页面函数

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
	public static String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, int... index) throws InterruptedException {
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
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			return routeHtml;
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(SLEEP);
			return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
	}

	/**
	 * 获取登录页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param accountInfo 会员信息
	 * @param snn         snn
	 * @param routes      线路
	 * @param index       循环次数
	 * @return 登录信息map
	 */
	public static Map<String, String> getLoginHtml(HttpClientConfig httpConfig, AccountInfo accountInfo, String snn, String[] routes, int... index)
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
		Map<String, Object> join = new HashMap<>(5);
		join.put("codeY", accountInfo.getHandicapCaptcha());
		join.put("snn", snn);
		join.put("d", "");
		join.put("uid", "");
		join.put("ticket", "");

		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(routes[index[0]].concat("f/login_new.jsp")).map(join));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "Email")) {
				log.error("打开登录页面失败", html);
				Thread.sleep(2 * SLEEP);
				return getLoginHtml(httpConfig, accountInfo, snn, routes, index[0], ++index[1]);
			}
			loginInfoMap = new HashMap<>(3);
			loginInfoMap.put("loginSrc", routes[index[0]]);
			loginInfoMap.put("codeY", accountInfo.getHandicapCaptcha());
			loginInfoMap.put("snn", snn);
			loginInfoMap.put("ticket", StringUtils.substringBetween(html, "ticketL=\"", "\";"));
		} catch (Exception e) {
			log.error("打开登录页面失败", e);
			Thread.sleep(2 * SLEEP);
			return getLoginHtml(httpConfig, accountInfo, snn, routes, index[0], ++index[1]);
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
	public static String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String ticket, String code, int... index)
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
					.getImage(httpConfig.url(projectHost.concat("user/getValidateCodeF.do?t=") + System.currentTimeMillis() + "&ticket=" + ticket));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, ticket, null, ++index[0]);
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", "NewWs");
		join.put("content", content);

		String html;
		try {
			html = HttpClientTool.doPost(CRACK_CONTENT, join);
		} catch (Exception e) {
			log.error("破解验证码失败", e);
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, ticket, null, ++index[0]);
		}
		if (StringTool.isEmpty(html)) {
			log.error("破解验证码失败");
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, ticket, null, ++index[0]);
		}
		html = html.replace("\"", "");
		if (html.length() < 4) {
			return getVerifyCode(httpConfig, projectHost, ticket, null, ++index[0]);
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
	public static String getLogin(HttpClientConfig httpConfig, AccountInfo accountInfo,
								  String verifyCode, String projectHost, String ticket, String snn, int... index) throws InterruptedException, NoSuchAlgorithmException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		Map<String, Object> join = new HashMap<>(4);
		join.put("codeY", accountInfo.getHandicapCaptcha());
		join.put("snn", snn);
		join.put("zhanghao", accountInfo.getAccount());
		join.put("mima", Md5Tool.md5Hex(accountInfo.getPassword()));
		join.put("validateCode", verifyCode);
		join.put("ticket", ticket);
		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("user/loginf.do")).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				Thread.sleep(2 * SLEEP);
				return getLogin(httpConfig, accountInfo, verifyCode, projectHost, ticket, snn);
			}

			if (loginInfo.contains("驗證碼")) {
				log.error("验证码错误");
				Thread.sleep(2 * SLEEP);
				verifyCode = getVerifyCode(httpConfig, projectHost, ticket, null);
				return getLogin(httpConfig, accountInfo, verifyCode, projectHost, ticket, snn);
			}
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return getLogin(httpConfig, accountInfo, verifyCode, projectHost, ticket, snn);
		}

		return loginInfo;
	}

	/**
	 * 获取用户信息
	 *
	 * @param member 会员信息
	 * @return html
	 */
	public static String getUserInfo(MemberCrawler member, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return "";
		}
		String html = null;
		String url = member.getProjectHost();
		HttpClientConfig httpClient = member.getHcConfig();
		Map<String, Object> join = new HashMap<>(2);
		join.put("uid", member.getMemberUserInfo().gettMemberId());
		join.put("ticket", member.getTicket());
		try {
			html = HttpClientUtil.findInstance().postHtml(httpClient.url(url.concat("user/query/querySelfLeft.do")).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取用户信息失败，结果信息" + html);
				Thread.sleep(SLEEP);
				return getUserInfo(member, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取用户余额信息失败,错误信息=" + html, e);
			Thread.sleep(SLEEP);
			return getUserInfo(member, ++index[0]);
		}
	}

	/**
	 * 获取游戏限额
	 * Request URL: Request URL: http://uc2-567.av9av9.com:217/hmclient/user/query/listSelfWithIntime.do
	 *
	 * @param member   会员信息
	 * @param gameCode 游戏Id
	 * @param index    循环次数
	 * @return 游戏限额信息
	 */
	public static JSONArray getQuotaList(MemberCrawler member, String gameCode, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONArray();
		}
		HttpClientConfig httpConfig = member.getHcConfig();
		String html = null;
		String url = member.getProjectHost();

		Map<String, Object> join = new HashMap<>(10);
		join.put("uid", member.getMemberUserInfo().gettMemberId());
		join.put("ticket", member.getTicket());
		join.put("gameCode", gameCode);
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url.concat("user/query/listSelfWithIntime.do")).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败");
				Thread.sleep(SLEEP);
				return getQuotaList(member, gameCode, ++index[0]);
			}
			if (!StringTool.isContains(html, "-1")) {
				log.error("获取游戏限额页面失败，错误页面=" + html);
				Thread.sleep(SLEEP);
				return getQuotaList(member, gameCode, ++index[0]);
			}
			return getLimit(html, gameCode);
		} catch (Exception e) {
			log.error("获取游戏限额失败,错误信息=" + html, e);
			Thread.sleep(SLEEP);
			return getQuotaList(member, gameCode, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], ""));
		}
	}

	/**
	 * 获取赔率信息
	 *
	 * @param member   会员对象
	 * @param gameCode 盘口游戏code
	 * @param oddsCode 赔率code 	固定值：B_109
	 * @param index    循环次数
	 * @return 赔率信息
	 */
	public static JSONObject getOddsInfo(MemberCrawler member, String gameCode,
										 String oddsCode, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		HttpClientConfig httpConfig = member.getHcConfig();
		String projectHost = member.getProjectHost();

		String html = null;

		Map<String, Object> join = new HashMap<>(10);
		join.put("uid", member.getMemberUserInfo().gettMemberId());
		join.put("ticket", member.getTicket());
		join.put("gameCode", gameCode);
		join.put("code", oddsCode);

		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("game/bet/loadBet.do")).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取赔率信息页面失败");
				Thread.sleep(SLEEP);
				return getOddsInfo(member, gameCode, oddsCode, ++index[0]);
			}
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取赔率信息页面失败,结果信息=" + html, e);
			Thread.sleep(SLEEP);
			return getOddsInfo(member, gameCode, oddsCode, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), projectHost, join, index[0], ""));
		}
	}

	/**
	 * 投注
	 *
	 * @param member 会员信息
	 * @param bets   投注参数
	 * @return 投注结果
	 */
	public static JSONObject betting(MemberCrawler member, Map<String, Object> bets) {

		Map<String, Object> join = new HashMap<>(1);
		join.put("uid", member.getMemberUserInfo().gettMemberId());
		join.put("ticket", member.getTicket());
		join.put("gambleString", bets);
		HttpClientConfig httpConfig = member.getHcConfig();
		String projectHost = member.getProjectHost();
		try {
			String resultHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("game/gamble.do")).map(join));
			if (ContainerTool.isEmpty(resultHtml)) {
				log.error("投注结果页面为空");
				return new JSONObject();
			}
			return JSONObject.parseObject(resultHtml);
		} catch (Exception e) {
			log.error("投注结果页面为空");
			return new JSONObject();
		}
	}

	// TODO 功能函数

	/**
	 * 解析游戏限额页面
	 *
	 * @param html     游戏限额页面
	 * @param gameCode 游戏code
	 * @return 游戏限额
	 */
	private static JSONArray getLimit(String html, String gameCode) {
		JSONObject jsonObject = JSONObject.parseObject(html);
		JSONObject limitInfo = jsonObject.getJSONArray("dataObject").getJSONObject(5);
		JSONArray quota = new JSONArray();
		JSONArray array;
		// 只要前8位即可
		int num = limitInfo.size() >= 7 ? 7 : limitInfo.size();
		for (int i = 0; i < num; i++) {
			array = limitInfo.getJSONArray(gameCode + "_" + (i + 1));
			array.remove(5);
			array.remove(4);
			array.remove(3);
			quota.add(array);
		}

		return quota;
	}


	/**
	 * 获取赔率id
	 * <p>
	 *
	 * @param gameCode   游戏code
	 * @param drawNumber 期数
	 * @param odds       赔率
	 * @param betItems   投注信息
	 * @return 投注信息参数
	 */
	public static Map<String, Object> getBetItemInfo(String gameCode, String drawNumber, JSONObject odds, List<String> betItems) {
		JSONArray arr = new JSONArray();
		Map<String, Object> gambleString = new HashMap<>();
		Map<String, Object> gamble;
		for (String betItem : betItems) {
			String[] items = betItem.split("\\|");
			// 冠亚|11|2000
			String ruleCode = getRuleCode(gameCode, items[0], items[1]);
			gamble = new HashMap<>(4);
			gamble.put("seqNumber", drawNumber);
			gamble.put("ruleCode", ruleCode);
			gamble.put("odds", odds.getJSONArray(ruleCode).getDouble(0));
			gamble.put("amount", (int) NumberTool.doubleT(items[2]));
			arr.add(gamble);
		}
		gambleString.put("zds", arr);
		return gambleString;
	}

	/**
	 * 获取参数
	 *
	 * @param gameCode 游戏code
	 * @param betPlace 名次
	 * @param betRange 范围
	 * @return ruleCode
	 */
	private static String getRuleCode(String gameCode, String betPlace, String betRange) {
		String place = NewWsConfig.BALL_CODE_PLACE.get(betPlace);
		String ruleCode = gameCode.concat("_");
		Map<String, String> codeRange = getCodeRange(gameCode);
		if ("otherCode" .equals(place)) {
			ruleCode = ruleCode.concat(NewWsConfig.BALL_CODE_OTHER.get(betPlace.concat("|").concat(betRange)));
		} else {
			ruleCode = ruleCode.concat(place).concat("_").concat(codeRange.get(betRange));
		}
		return ruleCode;
	}

	private static Map<String, String> getCodeRange(String gameCode) {
		if ("1_1" .equals(gameCode)) {
			return NewWsConfig.KLC_BALL_CODE_RANGE;
		} else if ("1_2" .equals(gameCode)) {
			return NewWsConfig.SSC_BALL_CODE_RANGE;
		}
		return NewWsConfig.BALL_CODE_RANGE;

	}

	/**
	 * 获取会员可用线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  线路页面
	 * @return 线路数组
	 */
	public static String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml) {
		List<String> hrefs = new ArrayList<>();
		Document document = Jsoup.parse(routeHtml);
		Elements as = document.getElementsByClass("datalist-contain datalist").select("a");
		for (int i = 0; i < 5; i++) {
			hrefs.add(as.get(i).attr("name"));
		}

		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟
		long t1, t2;
		for (int i = 0; i < hrefs.size(); i++) {
			t1 = System.currentTimeMillis();
			String href = hrefs.get(i).concat("/");
			String url = href.concat("speed.gif?" + System.currentTimeMillis());
			try {
				HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			} catch (Exception e) {
				log.error("NEWWS连接失败,结果信息=", e);
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
	public static HcCodeEnum loginError(String msg) {
		log.error("UN盘口会员登陆异常，异常的登陆结果页面为：" + msg);
		if (StringTool.isContains(msg, "驗證碼")) {
			return HcCodeEnum.IBS_403_VERIFY_CODE;
		} else if (StringTool.contains(msg, "用戶登陸名或者密碼錯誤")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "賬號已被禁止使用")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "50001")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}

	/**
	 * 获取投注项信息
	 *
	 * @param gameCode 游戏code
	 * @return betInfo
	 */
	public static Map<String, String> getNewCcBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return NewWsNumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
				return NewWsBallConfig.BET_INFO_CODE;
			case GDKLC:
				return NewWsHappyConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}


}
