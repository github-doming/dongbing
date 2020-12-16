package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.FCConfig;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.handicap.config.FCBallConfig;
import com.ibm.common.utils.handicap.config.FCHappyConfig;
import com.ibm.common.utils.handicap.config.FCNumberConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
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
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/27 14:17
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class FCMemberTool {

	protected static final Logger log = LogManager.getLogger(FCMemberTool.class);
	private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
	private static final long SLEEP = 1000L;

	private static final Integer MAX_RECURSIVE_SIZE = 5;

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
			//导航页action
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("q", handicapCaptcha);
			dataMap.put("Submit", "%E6%9F%A5+%E8%AF%A2");

			// 获取线路
			routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl).map(dataMap));

			if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "線路")) {
				log.error("获取线路页面失败", routeHtml);
				Thread.sleep(2 * SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(2 * SLEEP);
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
	public static String getLoginHtml(HttpClientConfig httpConfig, String[] routes, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] > MAX_RECURSIVE_SIZE) {
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return "";
		}
		String html;
		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "用戶")) {
				log.error("打开登录页面失败", html);
				Thread.sleep(2 * SLEEP);
				return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
			}

		} catch (Exception e) {
			log.error("打开登录页面失败", e);
			Thread.sleep(2 * SLEEP);
			return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
		}

		return routes[index[0]];
	}

	/**
	 * 盘口登录
	 *
	 * @param httpConfig     http请求配置类
	 * @param memberAccount  盘口会员账号
	 * @param memberPassword 盘口会员密码
	 * @param projectHost    线路地址
	 * @param index          循环次数
	 * @return 盘口主页面href
	 * @throws IOException
	 */
	public static JSONObject getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
									  String projectHost, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
		Map<String, Object> join = new HashMap<>(4);
		join.put("ValidateCode", "");
		join.put("loginName", memberAccount);
		join.put("loginPwd", memberPassword);

		String url = projectHost.concat("Handler/LoginHandler.ashx?action=user_login");
		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				Thread.sleep(2 * SLEEP);
				return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
			}
			if (StringTool.contains(loginInfo, "帳號或者密碼錯誤", "帳號已經停用", "重置密碼")) {
				return JSONObject.parseObject(loginInfo);
			}
			//登录结果
			return JSONObject.parseObject(loginInfo);
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
		}
	}


	/**
	 * 获取用户信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param index       循环次数
	 * @return 会员信息
	 */
	public static String getUserInfo(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String userInfoHtml;
		try {
			Map<String, Object> join = new HashMap<>(3);
			join.put("action", "get_ad");
			join.put("browserCode", "4443");
			//获取用户信息
			userInfoHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("Handler/QueryHandler.ashx")).map(join));
			if (StringTool.isEmpty(userInfoHtml)) {
				log.error("获取用户信息", userInfoHtml);
				Thread.sleep(2 * SLEEP);
				return getUserInfo(httpConfig, projectHost, ++index[0]);
			}
			if (StringTool.isContains(userInfoHtml, "請重新登陸")) {
				return "snatchMsg";
			}

			return userInfoHtml;
		} catch (Exception e) {
			log.error("获取用户信息", e);
			Thread.sleep(2 * SLEEP);
			return getUserInfo(httpConfig, projectHost, ++index[0]);
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

		String url = projectHost.concat("CreditInfo.aspx?p=1&id=").concat(game);
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败");
				Thread.sleep(SLEEP);
				return getQuotaList(httpConfig, projectHost, game, ++index[0]);
			}

			return getLimit(html);
		} catch (Exception e) {
			log.error("获取游戏限额失败,错误信息=" + html, e);
			Thread.sleep(SLEEP);
			return getQuotaList(httpConfig, projectHost, game, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], ""));
		}
	}


	/**
	 * 获取赔率信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param betType     类型
	 * @param index       循环次数
	 * @return
	 */
	public static JSONObject getOddsInfo(HttpClientConfig httpConfig, String projectHost, GameUtil.Code gameCode,
										 String betType, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		Map<String, String> betParameter = getBetParameter(gameCode, betType);
		String html = null;
		Map<String, Object> join = new HashMap<>(3);
		join.put("action", "get_oddsinfo");
		join.put("playid", betParameter.get("playid"));
		join.put("playpage", betParameter.get("playpage"));
		String url = projectHost.concat(FCConfig.GAME_CODE_PATH.get(gameCode.name())).concat("/Handler/Handler.ashx");
		try {

			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (ContainerTool.isEmpty(html)) {
				log.error("获取游戏赔率失败");
				Thread.sleep(SLEEP);
				return getOddsInfo(httpConfig, projectHost, gameCode, betType, ++index[0]);
			}
			JSONObject resultHtml = JSONObject.parseObject(html);
			int state = (int) resultHtml.get("success");
			if (state == 200) {
				return (JSONObject) resultHtml.get("data");
			}
			return null;

		} catch (Exception e) {
			log.error("获取游戏赔率失败,结果信息=" + html, e);
			Thread.sleep(SLEEP);
			return getOddsInfo(httpConfig, projectHost, gameCode, betType, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], ""));
		}
	}

	/**
	 * 投注
	 *
	 * @param httpConfig
	 * @param projectHost
	 * @param betsMap
	 * @param playpage
	 * @return
	 */
	public static JSONObject betting(HttpClientConfig httpConfig, String projectHost, GameUtil.Code gameCode, Map<String, String> betsMap,
									 String playpage, String codePath, String phaseid) {

		String url = projectHost.concat(codePath);
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpConfig.setHeader("Referer", url + "/index.aspx?lid=" + FCConfig.GAME_CODE_ID.get(gameCode.name()) + "2&path=" + codePath);

		Map<String, Object> info = new HashMap<>();
		info.put("action", "put_money");
		info.put("phaseid", phaseid);
		info.put("oddsid", betsMap.get("oddsid"));
		info.put("uPI_P", betsMap.get("uPI_P"));
		info.put("uPI_M", betsMap.get("uPI_M"));
		info.put("i_index", betsMap.get("i_index"));
		info.put("JeuValidate", "07171024394568");
		info.put("playpage", playpage);

		String resultHtml = null;

		try {
			resultHtml = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(url.concat("/Handler/Handler.ashx")).map(info));
			if (ContainerTool.isEmpty(resultHtml)) {
				log.error("投注结果页面为空,投注项为：" + betsMap);
				return new JSONObject();
			}
			return JSONObject.parseObject(resultHtml);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, info, 0, ""));
		}
	}

	/**
	 * 投注  只能获取最近投注的5注记录

	 */
	public static JSONObject getbetResult(HttpClientConfig httpConfig, String projectHost, GameUtil.Code gameCode, String playpage, String codePath) {
		String url = projectHost.concat(codePath);
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpConfig.setHeader("Referer", url + "/index.aspx?lid=" + FCConfig.GAME_CODE_ID.get(gameCode.name()) + "2&path=" + codePath);

		Map<String, Object> info = new HashMap<>();
		info.put("action", "get_putinfo");
		info.put("playpage", playpage);

		String resultHtml = null;

		try {
			resultHtml = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(url.concat("/Handler/Handler.ashx")).map(info));
			if (ContainerTool.isEmpty(resultHtml)) {
				log.error("获取投注结果页面为空");
				return new JSONObject();
			}
			return JSONObject.parseObject(resultHtml);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, info, 0, resultHtml));
		}
	}


	/*************解析************/

	/**
	 * 解析游戏限额页面
	 *
	 * @param html 游戏限额页面
	 * @return 游戏限额
	 */
	private static JSONArray getLimit(String html) {
		Document document = Jsoup.parse(html);
		Elements tableClass = document.getElementsByClass("hover_list");
		JSONArray quota = new JSONArray();
		JSONArray array;
		for (Element table : tableClass) {
			Elements trs = table.select("tr");
			for (Element tr : trs) {
				array = new JSONArray();
				String text = tr.text();
				if (StringTool.notEmpty(text)) {
					String[] limits = text.split(" ");
					if (limits.length == 6) {
						array.add(limits[3]);
						array.add(limits[4]);
						array.add(limits[5]);
					} else {
						array.add(limits[2]);
						array.add(limits[3]);
						array.add(limits[4]);
					}
					quota.add(array);
				}
			}
		}
		return quota;
	}

	private static Map<String, String> userInfo(String userInfoHtml) {
		Map<String, String> userData = new HashMap<>(5);
		try {
			JSONObject userInfo = JSONObject.parseObject(userInfoHtml).getJSONObject("data").getJSONObject("game_2");

			//信用额度
			userData.put("creditQuota", userInfo.getString("credit"));
			//使用金额
			userData.put("usedQuota", String.valueOf(userInfo.getDouble("usable_credit")));
			//盈亏金额
			userData.put("profitAmount", String.valueOf(userInfo.getDouble("profit")));
		} catch (Exception e) {
			log.error("用户信息解析错误", e);
		}
		return userData;
	}

	/**
	 * 获取会员可用线路
	 *
	 * @param routeHtml 线路页面
	 * @return 线路数组
	 */
	public static String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml) {
		Document document = Jsoup.parse(routeHtml);
		Element trs = document.getElementById("form1");
		Elements as = trs.select("a");
		List<String> hrefs = new ArrayList<>();

		for (Element a : as) {
			String url = a.attr("href");
			if (url.contains("w")) {
				hrefs.add(url);
			}
		}
		String[] routes = new String[hrefs.size()];
		long[] arr = new long[hrefs.size()];

		//判断时间延迟 public/check.htm?d=0.7056380597312475
		long t1, t2;
		for (int i = 0; i < hrefs.size(); i++) {
			t1 = System.currentTimeMillis();
			String href = hrefs.get(i).concat("/");
			String url = href.concat("?")
					.concat(System.currentTimeMillis() + "");
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

	/**
	 * 获取投注详情内容
	 *
	 * @param ballCode 球号code
	 * @param betItems 投注详情
	 * @param odds     赔率
	 * @return
	 */
	public static Map<String, String> getBetItemInfo(Map<String, String> ballCode, List<String> betItems, JSONObject odds) {
		Map<String, String> betsMap = new HashMap<>();

		List<Object> oddsId = new ArrayList<>();
		List<Object> uPIP = new ArrayList<>();
		List<Object> uPIM = new ArrayList<>();
		List<Object> iindex = new ArrayList<>();
		for (int i = 0; i < betItems.size(); i++) {
			String betItem = betItems.get(i);
			String[] items = betItem.split("\\|");
			String bet = items[0].concat("|").concat(items[1]);
			//单注金额须为整数值
			int fund = (int) NumberTool.doubleT(items[2]);
			String betCode = ballCode.get(bet);
			oddsId.add(betCode);
			uPIP.add(odds.get(betCode));
			uPIM.add(fund);
			iindex.add(i);
		}
		betsMap.put("oddsid", StringUtils.join(oddsId.toArray(), ","));
		betsMap.put("uPI_P", StringUtils.join(uPIP.toArray(), ","));
		betsMap.put("uPI_M", StringUtils.join(uPIM.toArray(), ","));
		betsMap.put("i_index", StringUtils.join(iindex.toArray(), ","));
		return betsMap;
	}

	/**
	 * 获取请求参数
	 *
	 * @param gameCode
	 * @param betType
	 * @return
	 */
	public static Map<String, String> getBetParameter(GameUtil.Code gameCode, String betType) {
		Map<String, String> betParameter = new HashMap<>();
		String playid = "";
		String playpage = FCConfig.GAME_CODE_PAGE.get(gameCode.name());
		switch (gameCode) {
			case PK10:
			case JS10:
			case XYFT:
			case COUNTRY_10:
				playpage += FCNumberConfig.GAME_TYPE_CODE.get(betType);
				playid = FCNumberConfig.GAME_ODDS_ID.get(betType);
				break;
			case CQSSC:
			case JSSSC:
			case COUNTRY_SSC:
				playpage += FCBallConfig.GAME_TYPE_CODE.get(betType);
				playid = FCBallConfig.GAME_ODDS_ID.get(betType);
				break;
			case GDKLC:
				playpage += FCHappyConfig.GAME_TYPE_CODE.get(betType);
				playid = FCHappyConfig.GAME_ODDS_ID.get(betType);
				break;
			default:
				break;
		}
		betParameter.put("playid", playid);
		betParameter.put("playpage", playpage);

		return betParameter;

	}


	/**
	 * 登陆错误
	 *
	 * @param msg 登陆结果页面
	 * @return 错误信息
	 */
	public static HcCodeEnum loginError(String msg) {
		log.error("FC盘口会员登陆异常，异常的登陆结果页面为：" + msg);
		if (StringTool.contains(msg, "帳號已經停用")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "帳號或者密碼錯誤")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "重置密碼")) {
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
	public static Map<String, String> getFCBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case SELF_10_5:
			case COUNTRY_10:
				return FCNumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
			case COUNTRY_SSC:
				return FCBallConfig.BET_INFO_CODE;
            case GDKLC:
                return FCHappyConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
}
