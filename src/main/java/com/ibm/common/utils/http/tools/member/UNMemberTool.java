package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.UNConfig;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.handicap.config.NewCcBallConfig;
import com.ibm.common.utils.handicap.config.NewCcHappyConfig;
import com.ibm.common.utils.handicap.config.NewCcNumberConfig;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import org.apache.commons.lang3.StringUtils;
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
public class UNMemberTool {
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
	public static String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String code, String handicapCaptcha,
											int... index) throws InterruptedException {
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
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
			}
			if (StringTool.contains(routeHtml, "验证码")) {
				code = getVerifyCode(httpConfig, handicapUrl, null);
				return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
			}
			routeHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl.concat("wb4/index")));

			return routeHtml;
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(SLEEP);
			return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
		}
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

		httpConfig.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n");

		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]].concat("iwm3")));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "UN")) {
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
					.getImage(httpConfig.url(projectHost.concat("captcha_image?d=") + System.currentTimeMillis()));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			Thread.sleep(SLEEP);
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
	 * @param index          循环次数
	 * @return 盘口主页面href
	 */
	public static String getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
								  String verifyCode, String projectHost, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		httpConfig.setHeader("Accept", "*/*");
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		Map<String, Object> join = new HashMap<>(4);

		join.put("userName", memberAccount);
		join.put("password", memberPassword);
		join.put("code", verifyCode);
		join.put("btnSubmit", "%E7%AB%8B%E5%8D%B3%E7%99%BB%E5%BD%95");
		join.put("gotourl", "");
		join.put("login_0521_type", "3");
		join.put("language", "cn");
		join.put("pubKey", "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCrZCuRaDnL09yPIbtOBqCPY2PFBecVh/iuPyfkehqgOE4/pUnKCZjnkv9gC+W6phTgZwMXxv51Hzqb2KZCu0b05ag5uThxvLK/+CZB8wcYiyNYogM6Hc7kwJmQ5O4JpBkKuSRUz6OCKdRSnYb2O4w/uut9CnyGt9jDWDhf5iugrQIDAQAB");

		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("login")).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				Thread.sleep(2 * SLEEP);
				return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, ++index[0]);
			}

			if (loginInfo.contains("验证码")) {
				log.error("验证码错误");
				Thread.sleep(2 * SLEEP);
				verifyCode = getVerifyCode(httpConfig, projectHost, null);
				return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, ++index[0]);
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
		String url = member.getProjectHost().concat("wb3/us0001/queryUserLeft?gameId=G_30&t=" + System.currentTimeMillis());
		try {
			html = HttpClientUtil.findInstance().getHtml(member.getHcConfig().url(url));
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
	 * Request URL: https://www.bbb71.net/wb3/us0001/personalData?t=1591092200116&user_id=&palyId=G_1
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param gameNo      游戏Id
	 * @param index       循环次数
	 * @return 游戏限额信息
	 */
	public static JSONArray getQuotaList(HttpClientConfig httpConfig, String projectHost, String gameNo, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONArray();
		}
		String html = null;
		String url = projectHost.concat("wb3/us0001/personalData?user_id=&t=%s&palyId=%s");
		url = String.format(url, System.currentTimeMillis(), gameNo);
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败");
				Thread.sleep(SLEEP);
				return getQuotaList(httpConfig, projectHost, gameNo, ++index[0]);
			}
			if (!StringTool.isContains(html, "user_Name")) {
				log.error("获取游戏限额页面失败，错误页面=" + html);
				Thread.sleep(SLEEP);
				return getQuotaList(httpConfig, projectHost, gameNo, ++index[0]);
			}
			return getLimit(html);
		} catch (Exception e) {
			log.error("获取游戏限额失败,错误信息=" + html, e);
			Thread.sleep(SLEEP);
			return getQuotaList(httpConfig, projectHost, gameNo, ++index[0]);
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
	public static JSONObject getOddsInfo(MemberCrawler member, GameUtil.Code gameCode,
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
		String url = projectHost.concat("wb3/001/m001?b=%s&g=%s&auto=0&hType=%s&t=%s");
		url = String.format(url, oddsCode, UNConfig.GAME_CODE_ID.get(gameCode.name()), member.getMemberUserInfo().getMemberType(), System.currentTimeMillis());

		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
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
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], ""));
		}
	}

	/**
	 * 投注
	 *
	 * @param httpConfig httpclient
	 * @param url        请求地址
	 * @param bets       投注参数
	 * @return 投注结果
	 */
	public static JSONObject betting(HttpClientConfig httpConfig, String url, String bets) {

		httpConfig.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpConfig.setHeader("Content-Type", "application/json");

		Map<String, Object> join = new HashMap<>(1);
		join.put("$ENTITY_STRING$", bets);

		try {
			String resultHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(url.concat("wb3/001/s001s")).map(join));
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

	/**
	 * 获取未结算页面
	 * https://fkiael.58jxw.com/wb3/us0001/m0010?t=1593571134036&palyId=G_3&tg=2
	 * @param member      会员信息
	 * @param page        页数
	 * @param index       循环次数
	 * @return
	 */
	public static String getIsSettlePage(MemberCrawler member, int page, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url=member.getProjectHost().concat("wb3/us0001/m0010?t=").concat(System.currentTimeMillis()+"&palyId=G_3&tg=").concat(page+"");
		String html=null;
		try {
			html = HttpClientUtil.findInstance().getHtml(member.getHcConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算页面失败", html);
				Thread.sleep(SLEEP);
				return getIsSettlePage(member, page, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取未结算页面失败", e);
			Thread.sleep(SLEEP);
			return getIsSettlePage(member,page, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
		}
	}


	// TODO 功能函数

	/**
	 * 获取未结算信息
	 * @param html		未结算页面
	 * @return
	 */
	public static JSONArray getIsSettleInfo(String html,Object roundno) {
		JSONArray array=new JSONArray();
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("user_table").first().select("tr");
		elements.remove(elements.first());
		elements.remove(elements.first());
		if(elements.isEmpty()){
			return array;
		}
		for(Element tr:elements){
			String period= StringUtils.substringBetween(tr.child(1).text()," ","期");
			if(!roundno.equals(period)){
				continue;
			}
			String[] betItems = tr.getElementsByClass("font_blue").text().split("[ 【】]");
			if(StringTool.contains(betItems[0],"冠","亚")){
				betItems[0] = UNConfig.BET_INFO_CODE.get(betItems[0]);
			}
			if(NumberTool.isInteger(betItems[1])){
				betItems[1] = NumberTool.getInteger(betItems[1])+"";
			}
			String betItem = betItems[0].concat("|").concat(betItems[1]);
			array.add(betItem.concat("|")+NumberTool.intValueT(tr.child(5).text()));
		}
		return array;
	}


	/**
	 * 解析游戏限额页面
	 *
	 * @param html 游戏限额页面
	 * @return 游戏限额
	 */
	private static JSONArray getLimit(String html) {
		Document document = Jsoup.parse(html);

		Element element = document.getElementsByClass("user_table").last();
		Elements trs = element.select("tr");
		trs.remove(0);
		JSONArray quota = new JSONArray();
		JSONArray array;
		for (Element tr : trs) {
			array = new JSONArray();
			array.add(tr.child(2).text());
			array.add(tr.child(3).text());
			array.add(tr.child(4).text());
			quota.add(array);
		}
		return quota;
	}


	/**
	 * 获取赔率id
	 * <p>
	 * {\"betPlays\":\"B_3,B_3\",\"numbers\":\"N_21,N_22\",\"moneys\":\"2,2\",\"rates\":\"1.9879,1.9879\",\"g\":\"G_3\"," +
	 * "\"qh\":\"745475\",\"B_TYPE\":\"B_109\",\"H_rate_bh\":\"0\",\"B_hType\":\"A\"}
	 *
	 * @param gameCode   游戏code
	 * @param odds       赔率
	 * @param betItems   投注信息
	 * @param member 会员
	 * @return 投注参数
	 */
	public static String getBetItemInfo(GameUtil.Code gameCode,  JSONObject odds, List<String> betItems, MemberCrawler member) {

		Map<String, String> ballCodePlace = UNConfig.BALL_CODE_PLACE;
		Map<String, String> ballCodeRange = UNConfig.BALL_CODE_RANGE;
		String gameNo = UNConfig.GAME_CODE_ID.get(gameCode.name());
		StringBuilder rates = new StringBuilder();
		StringBuilder betPlays = new StringBuilder();
		StringBuilder numbers = new StringBuilder();
		StringBuilder moneys = new StringBuilder();
		for (String betItem : betItems) {
			String[] items = betItem.split("\\|");
			JSONObject placeOdds = (JSONObject) odds.get(ballCodePlace.get(items[0]));
			// 名次
			betPlays.append(ballCodePlace.get(items[0])).append(",");
			// 范围
			numbers.append(ballCodeRange.get(items[1])).append(",");
			//赔率
			rates.append(placeOdds.get(ballCodeRange.get(items[1]))).append(",");
			//金额
			int fund = (int) NumberTool.doubleT(items[2]);
			moneys.append(fund).append(",");
		}
		rates.deleteCharAt(rates.length() - 1);
		betPlays.deleteCharAt(betPlays.length() - 1);
		numbers.deleteCharAt(numbers.length() - 1);
		moneys.deleteCharAt(moneys.length() - 1);
		String bets = "{\"betPlays\":\"%s\",\"numbers\":\"%s\",\"moneys\":\"%s\",\"rates\":\"%s\",\"g\":\"%s\"," +
				"\"qh\":\"%s\",\"B_TYPE\":\"B_109\",\"H_rate_bh\":\"0\",\"B_hType\":\"%s\"}";
		bets = String.format(bets, betPlays.toString(), numbers.toString(), moneys.toString(), rates.toString(), gameNo,
				member.getPIdMap().get(gameCode).getPeriod(), member.getMemberUserInfo().getMemberType());
		return bets;
	}


	/**
	 * 获取会员可用线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  线路页面
	 * @return 线路数组
	 */
	public static String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml) {
		Document document = Jsoup.parse(routeHtml);
		Elements as = document.select("a");
		List<String> hrefs = new ArrayList<>();
		for (Element el : as) {
			if (StringTool.contains(el.text(), "会员线路")) {
				String on = el.attr("onclick");
				String url = StringUtils.substringBetween(on, "goToUrl('", "/");
				hrefs.add("https://" .concat(url));
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
	 * 登陆错误
	 *
	 * @param msg 登陆结果页面
	 * @return 错误信息
	 */
	public static HcCodeEnum loginError(String msg) {
		log.error("UN盘口会员登陆异常，异常的登陆结果页面为：" + msg);
		if (StringTool.isContains(msg, "验证码")) {
			return HcCodeEnum.IBS_403_VERIFY_CODE;
		} else if (StringTool.contains(msg, "-1", "-3", "用户名在系统中不存在", "密码不正确")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "-4", "-6", "-11")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "3")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}

	/**
	 * 获取投注项信息
	 *
	 * @param gameCode 游戏code
	 * @return code信息
	 */
	public static Map<String, String> getNewCcBallCode(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return NewCcNumberConfig.BET_INFO_CODE;
			case CQSSC:
			case JSSSC:
			case SELF_SSC_5:
				return NewCcBallConfig.BET_INFO_CODE;
			case GDKLC:
				return NewCcHappyConfig.BET_INFO_CODE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}


}
