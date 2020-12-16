package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.NewCcConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.handicap.config.NewCcBallConfig;
import com.ibm.common.utils.handicap.config.NewCcHappyConfig;
import com.ibm.common.utils.handicap.config.NewCcNumberConfig;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import org.apache.http.client.protocol.HttpClientContext;
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
public class NewCcMemberTool {
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
			routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl.concat("/main")).map(map));

			if (StringTool.isEmpty(routeHtml)) {
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
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]].concat("/member")));

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
					.getImage(httpConfig.url(projectHost.concat("/Codenum?time=") + System.currentTimeMillis()));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			Thread.sleep(SLEEP);
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
		httpConfig.headers(null);
		httpConfig.httpContext(null);
		httpConfig.httpContext(HttpClientContext.create());

		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
		Map<String, Object> join = new HashMap<>(4);

		join.put("loginName", memberAccount);
		join.put("loginPwd", memberPassword);
		join.put("ValidateCode", verifyCode);

		String url = projectHost.concat("/member/Home/Ulogin");
		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				Thread.sleep(2 * SLEEP);
				return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, ++index[0]);
		}
		JSONObject jsonObj = JSONObject.parseObject(loginInfo);

		if (jsonObj.getString("FaildReason").contains("-1")) {
			log.error("验证码错误");
			Thread.sleep(2 * SLEEP);
			verifyCode = getVerifyCode(httpConfig, projectHost, null);
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
		String url = member.getProjectHost().concat("/member/Main/left");

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
	 * 获取用户盈亏信息
	 *
	 * @param member 会员信息
	 * @return html
	 */
	public static String getUserGain(MemberCrawler member, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return "";
		}

		String html = null;
		String url = member.getProjectHost().concat("/member/Game_v2/getSyjq");

		try {
			html = HttpClientUtil.findInstance().getHtml(member.getHcConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取用户信息失败，结果信息=" + html);
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
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param gameNo      游戏NO
	 * @param index       循环次数
	 * @return 游戏限额信息
	 */
	public static JSONArray getQuotaList(HttpClientConfig httpConfig, String projectHost, String memberType, String gameNo, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONArray();
		}
		String html = null;
		String url = projectHost.concat("/member/User/userInfouu?gno=").concat(gameNo);
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败");
				Thread.sleep(SLEEP);
				return getQuotaList(httpConfig, projectHost, memberType, gameNo, ++index[0]);
			}
			if (!StringTool.isContains(html, "信用")) {
				log.error("获取游戏限额页面失败，错误页面=" + html);
				Thread.sleep(SLEEP);
				return getQuotaList(httpConfig, projectHost, memberType, gameNo, ++index[0]);
			}
			return getLimit(html, memberType);
		} catch (Exception e) {
			log.error("获取游戏限额失败,错误信息=" + html, e);
			Thread.sleep(SLEEP);
			return getQuotaList(httpConfig, projectHost, memberType, gameNo, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
		}
	}

	/**
	 * 加载页面，以免投注失败
	 *
	 * @param member   会员信息
	 * @param gameCode 游戏code
	 */
	public static void loadPage(MemberCrawler member, GameUtil.Code gameCode) {
		HttpClientConfig httpConfig = member.getHcConfig();
		String host = member.getProjectHost();

		String game = NewCcConfig.GAME_CODE.get(gameCode.name());
		Map<String, Object> join = new HashMap<>(2);
		join.put("No", BallCodeTool.getNewCcGameNo(gameCode, member.getMemberUserInfo().getMemberType()));
		try {
			HttpClientUtil.findInstance().postHtml(httpConfig.url(host.concat("/member/Submit/bcyx")).map(join));
			HttpClientUtil.findInstance().getHtml(httpConfig.url(host.concat("/member/Main/index_")));
			HttpClientUtil.findInstance().getHtml(httpConfig.url(host.concat("/member/Main/menu")));
			HttpClientUtil.findInstance().getHtml(httpConfig.url(host.concat("/member/Game_v2/").concat(game).concat("?webUU=HJK1HUWH2FBBS8DS9WQ")));
		} catch (Exception e) {
			log.error("加载页面失败,错误信息=", e);
		}
	}

	/**
	 * 获取赔率信息
	 *
	 * @param member   会员对象
	 * @param gameCode 盘口游戏code
	 * @param oddsCode 赔率code
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
		String url = projectHost.concat("member/Game_v2/gxpl");
		Map<String, Object> join = new HashMap<>(2);
		join.put("ItemNo", oddsCode);
		join.put("MD5", "-1");
		try {
			// 加载页面
			loadPage(member, gameCode);

			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
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
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
		}
	}

	/**
	 * 预投注处理
	 *
	 * @param member 会员对象
	 * @return 预投注信息
	 */
	public static String getOddsAndName(MemberCrawler member, String bets, GameUtil.Code gameCode, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return "";
		}

		String html = null;
		String url = member.getProjectHost().concat("/member/Submit/getOddsAndName");
		Map<String, Object> join = new HashMap<>(2);
		join.put("oddsName", bets);
		join.put("type", "1");
		try {
			// 加载页面
			loadPage(member, gameCode);
			html = HttpClientUtil.findInstance().postHtml(member.getHcConfig().url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("预投注处理失败，结果信息=" + html);
				Thread.sleep(SLEEP);
				return getOddsAndName(member, bets, gameCode, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("预投注处理失败,错误信息=" + html, e);
			Thread.sleep(SLEEP);
			return getOddsAndName(member, bets, gameCode, ++index[0]);
		}
	}

	/**
	 * 投注
	 *
	 * @param member 会员对象
	 * @param xdnum  投注验证编码
	 * @return 投注结果
	 */
	public static JSONObject betting(MemberCrawler member, String xdnum) {

		Map<String, Object> join = new HashMap<>(1);
		join.put("noLst", "");
		join.put("xdnum", xdnum);
		join.put("type", "1");
		join.put("MD5", "1348cc6337c41ec5075e3eefca3d3eee");
		String resultHtml;
		String url = member.getProjectHost().concat("/member/Submit/setOrder");
		try {
			resultHtml = HttpClientUtil.findInstance().postHtml(member.getHcConfig().url(url).map(join));
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
	 * @param html       游戏限额页面
	 * @param memberType 会员盘口类型
	 * @return 游戏限额
	 */
	private static JSONArray getLimit(String html, String memberType) {
		Document document = Jsoup.parse(html);

		Elements trs = document.getElementsByClass("t_list_tr_0");
		int minLimit = "B" .equals(memberType) ? 5 : 2;
		JSONArray quota = new JSONArray();
		JSONArray array;
		for (Element tr : trs) {
			array = new JSONArray();
			String text = tr.text();
			String[] limits = text.split(" ");

			array.add(minLimit);
			// 單注限額"
			array.add(Integer.parseInt(limits[2]));
			array.add(Integer.parseInt(limits[3]));
			quota.add(array);
		}
		return quota;
	}


	/**
	 * 获取赔率id
	 *
	 * @param gameCode 游戏code
	 * @param betItems 投注内容
	 * @return 投注参数
	 */
	public static String getBetItemInfo(GameUtil.Code gameCode, List<String> betItems, String memberType) {
		Map<String, String> ballCode = BallCodeTool.getBallCode(HandicapUtil.Code.NEWCC, gameCode);
		int gameNo = NumberTool.getInteger(BallCodeTool.getNewCcGameNo(gameCode, memberType)) * 100;
		StringBuilder oddsName = new StringBuilder();
		for (String betItem : betItems) {
			String[] items = betItem.split("\\|");
			String bet = items[0].concat("|").concat(items[1]);

			//单注金额须为整数值
			int fund = (int) NumberTool.doubleT(items[2]);
			String betCodeStr = ballCode.get(bet);
			if (StringTool.isEmpty(betCodeStr)) {
				log.error("错误的投注项" + betItem);
				continue;
			}
			int betCode = gameNo + NumberTool.getInteger(betCodeStr);
			oddsName.append(betCode).append(",").append("1.9829").append(",").append(fund).append("|");
		}
		oddsName.delete(oddsName.length() - 1, oddsName.length());

		return oddsName.toString();
	}


	/**
	 * 获取会员可用线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  线路页面
	 * @return 线路数组
	 */
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
	public static HcCodeEnum loginError(String msg) {
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

	/**
	 * 获取投注项信息
	 *
	 * @param gameCode
	 * @return
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
