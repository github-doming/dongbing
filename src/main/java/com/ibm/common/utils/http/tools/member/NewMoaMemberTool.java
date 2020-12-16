package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.NewMoaConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * @Description: NEWMOA会员工具类
 * @Author: wwj
 * @Date: 2020/6/11 15:54
 * @Version: v1.0
 */
public class NewMoaMemberTool {
	protected static final Logger log = LogManager.getLogger(SgWinMemberTool.class);
	private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
	private static final long SLEEP = 1000L;

	private static final Integer MAX_RECURSIVE_SIZE = 5;
	private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

	// TODO 开启页面函数

	/**
	 * 获取线路页面
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

		String routeHtml;
		try {

			Map<String, Object> join = new HashMap<>(1);
			join.put("attach", handicapCaptcha);
			join.put("sys_attach", code);
			//获取线路页面
			routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl.concat("loginAction.do")).map(join));
			if (StringTool.isEmpty(routeHtml)) {
				log.error("获取线路页面失败:{}", routeHtml);
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
			}
			if (StringTool.contains(routeHtml, "登入頁面")) {
				code = getVerifyCode(httpConfig, handicapUrl, "NewMOANav", null);
				return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
			}

			return routeHtml;
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(SLEEP);
			return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
		}
	}

	/**
	 * 获取登录加密公钥
	 *
	 * @param httpConfig http请求配置类
	 * @param routes     线路
	 * @param index      循环次数
	 * @return 登录信息map
	 */
	public static Map<String, String> getLoginPublicKey(HttpClientConfig httpConfig, String[] routes, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] > MAX_RECURSIVE_SIZE) {
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return new HashMap<>(2);
		}
		String html;
		Map<String, String> loginInfoMap = new HashMap<>(2);
		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));

			if (StringTool.isEmpty(html)) {
				log.error("打开登录页面失败", html);
				Thread.sleep(SLEEP);
				return getLoginPublicKey(httpConfig, routes, index[0], ++index[1]);
			}
			String publicKey = StringUtils.substringBetween(html, "10001\", \"\", \"", "\");");
			loginInfoMap.put("loginSrc", routes[index[0]]);
			loginInfoMap.put("publicKey", publicKey);
		} catch (Exception e) {
			log.error("打开登录页面失败", e);
			Thread.sleep(SLEEP);
			return getLoginPublicKey(httpConfig, routes, index[0], ++index[1]);
		}
		return loginInfoMap;
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

		httpConfig.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");

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
	public static String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String code, String type, int... index)
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
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat("public/attachImage.do?t=" + System.currentTimeMillis())));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, null, type, ++index[0]);
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", type);
		join.put("content", content);

		String html;
		try {
			html = HttpClientTool.doPost(CRACK_CONTENT, join);
		} catch (Exception e) {
			log.error("破解验证码失败", e);
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, content, type, ++index[0]);
		}
		if (StringTool.isEmpty(html)) {
			log.error("破解验证码失败");
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, content, type, ++index[0]);
		}
		html = html.replace("\"", "");

		if (!html.matches("^\\d{4}$")) {
			return getVerifyCode(httpConfig, projectHost, content, type, ++index[0]);
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

		Map<String, Object> join = new HashMap<>(4);
		join.put("page_type", "1");
		join.put("login_page_name", "LG");
		join.put("offsetHeight", "");
		join.put("offsetWidth", "");
		join.put("request_locale", "zh_TW");
		join.put("txt_pass", "");
		join.put("user_id", memberAccount);
		join.put("attach", verifyCode);
		join.put("password", memberPassword);

		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("userLoginAction.do")).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				Thread.sleep(2 * SLEEP);
				return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, ++index[0]);
			}

			if (StringTool.contains(loginInfo, "验证码", "驗證碼", "請重新登錄")) {
				log.error("验证码错误");
				Thread.sleep(2 * SLEEP);
				verifyCode = getVerifyCode(httpConfig, projectHost, null, "NewMOA");
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
	 * 获取主页面
	 *
	 * @param httpConfig http请求配置类
	 * @param loginSrc   url
	 * @param index      循环次数
	 * @return 首页信息
	 */
	public static String getHomePage(HttpClientConfig httpConfig, String loginSrc, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return "";
		}
		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(loginSrc.concat("member3/member_index.do")));

		} catch (Exception e) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return getHomePage(httpConfig, loginSrc, ++index[0]);
		}

		return loginInfo;
	}

	/**
	 * 获取用户信息
	 *
	 * @param member 会员信息
	 * @return 用户信息
	 */
	public static String getUserInfo(MemberCrawler member, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return "";
		}
		String html = null;
		String url = member.getProjectHost().concat("member3/member_index.do");
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
	 *
	 * @param member   会员信息
	 * @param gameCode 游戏CODE
	 * @param period   期数
	 * @param index    循环次数
	 * @return 游戏限额信息
	 */
	public static JSONArray getQuotaList(MemberCrawler member, GameUtil.Code gameCode, Object period, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONArray();
		}
		HttpClientConfig httpConfig = member.getHcConfig();
		String projectHost = member.getProjectHost();

		String gameNo = NewMoaConfig.GAME_CODE.get(gameCode.name());
		Map<String, Object> paramInfo = getBallIndex(gameCode, "");
		Map<String, Object> join = new HashMap<>(6);
		join.put("oper_type", "GET_ZH_RATE");
		join.put("game_type", gameNo);
		join.put("issue_id", period);
		join.put("user_set", member.getMemberUserInfo().getMemberType());
		join.put("ball_idx", paramInfo.get("ballIndex"));
		join.put("is_detail_rate", "1");
		String html = null;
		String url = projectHost.concat("member3/game/get_rate_data.do");

		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败");
				Thread.sleep(SLEEP);
				return getQuotaList(member, gameCode, period, ++index[0]);
			}
			return getLimit(html);
		} catch (Exception e) {
			log.error("获取游戏限额失败,错误信息=" + html, e);
			Thread.sleep(SLEEP);
			return getQuotaList(member, gameCode, period, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], ""));
		}
	}

	/**
	 * 获取赔率信息
	 *
	 * @param member   会员对象
	 * @param gameCode 盘口游戏code
	 * @param betType  赔率code
	 * @param period   期数
	 * @param index    循环次数
	 * @return 赔率信息
	 */
	public static JSONObject getOddsInfo(MemberCrawler member, GameUtil.Code gameCode,
										 String betType, String period, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		HttpClientConfig httpConfig = member.getHcConfig();
		String projectHost = member.getProjectHost();

		String gameNo = NewMoaConfig.GAME_CODE.get(gameCode.name());
		Map<String, Object> paramInfo = getBallIndex(gameCode, betType);
		Map<String, Object> join = new HashMap<>(6);
		join.put("oper_type", paramInfo.get("operType"));
		join.put("game_type", gameNo);
		join.put("issue_id", period);
		join.put("user_set", member.getMemberUserInfo().getMemberType());
		join.put("ball_idx", paramInfo.get("ballIndex"));
		join.put("is_detail_rate", "1");
		String html = null;
		String url = projectHost.concat("member3/game/get_rate_data.do");

		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取赔率信息页面失败");
				Thread.sleep(SLEEP);
				return getOddsInfo(member, gameCode, betType, period, ++index[0]);
			}
			return getOdds(html);
		} catch (Exception e) {
			log.error("获取赔率信息页面失败,结果信息=" + html, e);
			Thread.sleep(SLEEP);
			return getOddsInfo(member, gameCode, betType, period, ++index[0]);
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
	public static JSONObject betting(HttpClientConfig httpConfig, String url, Map<String, Object> bets) {

		try {
			String resultHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(url.concat("member3/game/orderServlet")).map(bets));
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
	 * 获取投注详情
	 * Request URL: https://jpd.gxgfjy.com/member3/common/order_list.do?game_type=C
	 * @param httpConfig
	 * @param projectHost
	 * @param gameCode
	 * @param index
	 * @return
	 */
	public static String getIsSettlePage(HttpClientConfig httpConfig, String projectHost, GameUtil.Code gameCode,int page,int totalPage, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return "";
		}

		String gameNo = NewMoaConfig.GAME_CODE.get(gameCode.name());

		String html = null;
		String url ;

		try {
			if(page==1){
				url = projectHost.concat("member3/common/order_list.do?game_type=").concat(gameNo);
				html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
				if (StringTool.isEmpty(html)) {
					log.error("获取投注详情页面失败");
					Thread.sleep(SLEEP);
					return getIsSettlePage(httpConfig, projectHost, gameCode,page,totalPage, ++index[0]);
				}
			}else if(page!=1){
				//is_report=0&game_type=B&settle_date=20200701&curPage=2&pageCount=10&totalPage=3&totalCount=30&pageMaxCount=30&limitMaxCount=0
				url = projectHost.concat("member3/common/order_list.do?game_type=").concat(gameNo);
				Map<String,Object> join = new HashMap<>();
				join.put("is_report","0");
				join.put("game_type",gameNo);
				join.put("settle_date","0");
				join.put("curPage",page);
				join.put("pageCount",10);
				join.put("totalPage",totalPage);
				join.put("pageMaxCount","30");
				join.put("limitMaxCount","0");
				html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
				if (StringTool.isEmpty(html)) {
					log.error("获取投注详情页面失败");
					Thread.sleep(SLEEP);
					return getIsSettlePage(httpConfig, projectHost, gameCode,page,totalPage, ++index[0]);
				}
			}
			return html;
		} catch (Exception e) {
			log.error("获取赔率信息页面失败,结果信息=" + html, e);
			Thread.sleep(SLEEP);
			return getIsSettlePage(httpConfig, projectHost, gameCode,page,totalPage, ++index[0]);
		}
	}

	// TODO 功能函数

	/**
	 * 获取未结算信息
	 * @param html		未结算页面
	 * @return
	 */
	public static JSONObject getIsSettleInfo(String html,Object roundno,GameUtil.Code gameCode) {
		JSONObject jsonObject = new JSONObject();
		JSONArray array=new JSONArray();
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("date_sd_tr");
		if(elements.isEmpty()){
			return jsonObject;
		}
		for(Element tr:elements){
			String period= StringUtils.substringBetween(tr.child(1).text()," "," ");
			if(!roundno.equals(period)){
				continue;
			}
			String[] arr = tr.child(1).text().split("『 | 』");

			String bet = BallCodeTool.getNewMoaBallInfo(gameCode).get(arr[0].concat(arr[1]));
			array.add(bet.concat("|")+NumberTool.intValueT(tr.child(5).text()));
		}

		jsonObject.put("info",array);
		jsonObject.put("totalPage",document.getElementById("totalPage").val());
		jsonObject.put("totalCount",document.getElementById("totalCount").val());
		return jsonObject;
	}


	public static void main(String[] args) {
		String s ="第四名『 龍 』";

		System.out.println(Arrays.toString(s.split("『 | 』")));
	}
	/**
	 * 获取球号对应参数
	 *
	 * @param gameCode 游戏code
	 * @param betType  投注类型
	 * @return 参数
	 */
	private static Map<String, Object> getBallIndex(GameUtil.Code gameCode, String betType) {
		Map<String, Object> paramInfo = new HashMap<>(2);
		String ballIndex = "0", operType = "GET_ZH_RATE";
		switch (gameCode) {
			case XYFT:
			case JS10:
			case PK10:
			case COUNTRY_10:
				ballIndex = "10";
				break;
			case CQSSC:
			case JSSSC:
			case COUNTRY_SSC:
				ballIndex = "4";
				break;
			case GXKLSF:
				ballIndex = "8";
				if (StringTool.notEmpty(betType)) {
					switch (betType) {
						case "第一球":
							ballIndex = "1";
							break;
						case "第二球":
							ballIndex = "2";
							break;
						case "第三球":
							ballIndex = "3";
							break;
						case "第四球":
							ballIndex = "4";
							break;
						case "第五球":
							ballIndex = "5";
							break;
						case "第六球":
							ballIndex = "6";
							break;
						case "第七球":
							ballIndex = "7";
							break;
						default:
							ballIndex = "8";
							break;
					}
					operType = "GET_18_RATE";
				}
				break;
			default:
				break;
		}
		paramInfo.put("ballIndex", ballIndex);
		paramInfo.put("operType", operType);
		return paramInfo;
	}

	/**
	 * 解析游戏赔率
	 *
	 * @param html 游戏限额页面
	 * @return 游戏限额
	 */
	private static JSONObject getOdds(String html) {
		JSONObject odds = new JSONObject();
		String[] oddsInfo = html.split("##")[2].split("%%");
		for (String info : oddsInfo) {
			String[] odd = info.split(";");
			odds.put(odd[0], odd[2]);
		}
		return odds;
	}

	/**
	 * 解析游戏限额页面
	 *
	 * @param html 游戏限额页面
	 * @return 游戏限额
	 */
	private static JSONArray getLimit(String html) {
		JSONArray quota = new JSONArray();
		JSONArray array;
		String[] limitArrs = html.split("##")[1].split("%%");
		for (String limitArr : limitArrs) {
			String[] limit = limitArr.split(";");
			array = new JSONArray();
			array.add(limit[1]);
			array.add(limit[2]);
			array.add(limit[4]);
			quota.add(array);
		}
		return quota;
	}


	/**
	 * 拼接投注参数
	 *
	 * @param gameCode   游戏code
	 * @param drawNumber 期数
	 * @param odds       赔率
	 * @param betItems   投注项
	 * @return 参数
	 */
	public static Map<String, Object> getBetItemInfo(GameUtil.Code gameCode, String drawNumber, JSONObject odds, List<String> betItems) {

		Map<String, String> ballCodes = BallCodeTool.getBallCode(HandicapUtil.Code.NEWMOA, gameCode);
		StringBuilder orderInfo = new StringBuilder();
		for (String betItem : betItems) {
			String[] items = betItem.split("\\|");
			String ballCode = ballCodes.get(items[0].concat("|").concat(items[1]));
			String[] codeArr = ballCode.split("-");
			orderInfo.append(codeArr[0]).append(";").append(codeArr[1]).append(";")
					.append(odds.get(ballCode)).append(";")
					.append(NumberTool.doubleT(items[2])).append(";").append("0;;")
					.append("codeArr[1]").append(";").append("@");
		}
		orderInfo.deleteCharAt(orderInfo.length() - 1);

		Map<String, Object> bets = new HashMap<>(6);
		bets.put("TRACK_TYPE", "");
		bets.put("oper_type", "ORDER");
		bets.put("game_type", NewMoaConfig.GAME_CODE.get(gameCode.name()));
		bets.put("issue_id", drawNumber);
		bets.put("order_attach", RandomTool.getInt(1000) + "" + RandomTool.getInt(1000));
		bets.put("order_info", orderInfo);

		return bets;
	}


	/**
	 * 获取会员可用线路
	 *
	 * @param httpConfig     http请求配置类
	 * @param navigationHtml 线路页面
	 * @return 线路数组
	 */
	public static String[] getMemberRoute(HttpClientConfig httpConfig, String navigationHtml) {
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

		//判断时间延迟
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

	/**
	 * 登陆错误
	 *
	 * @param msg 登陆结果页面
	 * @return 错误信息
	 */
	public static HcCodeEnum loginError(String msg) {
		log.error("NEWMOA盘口会员登陆异常，异常的登陆结果页面为：" + msg);
		if (StringTool.isContains(msg, "验证码", "驗證碼")) {
			return HcCodeEnum.IBS_403_VERIFY_CODE;
		} else if (StringTool.contains(msg, "用戶名或密碼不正確")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "凍結", "停用")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}


}
