package com.ibm.old.v1.common.doming.utils.http.httpclient.tools;
import com.ibm.old.v1.common.doming.configs.SgWinConfig;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: sgwin工具类
 * @Author: zjj
 * @Date: 2019-08-07 14:19
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class SgWinTool {

	private static final long SLEEP = 1000L;

	protected static final Logger log = LogManager.getLogger(SgWinTool.class);
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
			//导航页
			String navigationHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));

			if (StringTool.isEmpty(navigationHtml)) {
				log.error("获取导航页面失败", navigationHtml);
				Thread.sleep(2 * SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			//多次输入验证码判断是否为线路选择页面
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
				log.error("获取线路页面失败", routeHtml);
				Thread.sleep(2 * SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(2 * SLEEP);
			return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
		//会员可用线路
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
		if (index[0] >= routes.length) {
			index[1]++;
		}
		if (index[1] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html;
		Map<String, String> loginInfoMap;
		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]].concat("login")));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "Welcome")) {
				log.error("打开选择线路界面失败", html);
				Thread.sleep(2 * SLEEP);
				return getLoginHtml(httpConfig, routes, ++index[1]);
			}
			loginInfoMap = new HashMap<>(3);
			loginInfoMap.put("loginSrc", routes[index[0]]);
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(2 * SLEEP);
			return getLoginHtml(httpConfig, routes, ++index[0]);
		}
		//获取验证码code和action
		return getCodeAndAction(html, loginInfoMap);
	}
	/**
	 * 获取验证码
	 *
	 * @param httpConfig http请求配置类
	 * @param host       线路地址
	 * @param code       验证码地址
	 * @param index      循环次数
	 * @throws IOException
	 * @return 验证码
	 */
	public static String getVerifyCode(HttpClientConfig httpConfig, String host, String code, int... index)
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
					.getImage(httpConfig.url(host.concat("code?_=") + System.currentTimeMillis()));
		} else {
			content = HttpClientUtil.findInstance().getImage(httpConfig.url(host.concat(code)));
		}
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, host, null, ++index[0]);
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", "SGWIN");
		join.put("content", content);
		String html;
		html = HttpClientTool.doPost(CRACK_CONTENT, join);

		if (StringTool.isEmpty(html)) {
			log.error("破解验证码失败");
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, host, null, ++index[0]);
		}
		html = html.replace("\"", "");

		return html;
	}
	/**
	 * 盘口登录
	 *
	 * @param httpConfig     http请求配置类
	 * @param memberAccount  盘口会员账号
	 * @param memberPassword 盘口会员密码
	 * @param verifyCode     验证码
	 * @param host           线路地址
	 * @param action         请求action
	 * @param roleType       角色类型
	 * @param index          循环次数
	 * @throws IOException
	 * @return 盘口主页面href
	 */
	public static String getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
			String verifyCode, String host, String action,int roleType, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(4);
		join.put("type", roleType);
		join.put("account", memberAccount);
		join.put("password", memberPassword);
		join.put("code", verifyCode);
		if(roleType==2){
			join.put("facode","");
		}
		String agreementHtml;

		try {
			//协议页面
			agreementHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(host.concat(action)).map(join));
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, host, action,roleType, ++index[0]);
		}

		if (StringTool.isEmpty(agreementHtml)) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, host, action,roleType, ++index[0]);
		}
		//账号冻结,账户已禁用,第一次登陆
		if (StringTool.contains(agreementHtml, "抱歉!你的帐号已被冻结", "账户已禁用", "修改密码")) {
			return agreementHtml;
		}
		//验证码错误
		if (StringTool.isContains(agreementHtml, "验证码")) {
			log.error("验证码错误");
			Thread.sleep(2 * SLEEP);
			verifyCode = getVerifyCode(httpConfig, host, null);
			return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, host, action,roleType, ++index[0]);
		}
		//同意协议href
		return agreementHtml;
	}
	/**
	 * 获取盘口主页面
	 *
	 * @param httpConfig http请求配置类
	 * @param host       主机地址
	 * @param indexHref  主页面href
	 * @param index      循环次数
	 * @return 盘口主页面
	 * @throws IOException
	 */
	public static String getHomePage(HttpClientConfig httpConfig, String host, String indexHref, int... index)
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
			indexHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(host.concat("member/").concat(indexHref)));

			if (StringTool.isEmpty(indexHtml) || !StringTool.isContains(indexHtml, "Welcome")) {
				log.error("获取盘口主页面失败", indexHtml);
				Thread.sleep(2 * SLEEP);
				return getHomePage(httpConfig, host, indexHref, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取盘口主页面失败", e);
			Thread.sleep(2 * SLEEP);
			return getHomePage(httpConfig, host, indexHref, ++index[0]);
		}
		Document document = Jsoup.parse(indexHtml);
		//获取盘口账号和会员盘
		return document.getElementsByClass("inline-name").text();
	}
	/**
	 * 获取用户余额信息
	 *
	 * @param httpConfig http请求配置类
	 * @param host       主机地址
	 * @return 用户余额信息
	 */
	public static JSONObject getUserInfo(HttpClientConfig httpConfig, String host, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		JSONObject userInfo = null;
		try {
			String url = host.concat("member/accounts?_=") + System.currentTimeMillis();
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (ContainerTool.isEmpty(html)) {
				log.error("获取用户信息失败");
				Thread.sleep( SLEEP);
				return getUserInfo(httpConfig, host, ++index[0]);
			}
			JSONArray array = JSONArray.fromObject(html);

			for (Object object : array) {
				userInfo = (JSONObject) object;
				if (userInfo.containsKey("maxLimit") && userInfo.getInt("maxLimit") > 0) {
					break;
				}
			}
		} catch (Exception e) {
			log.error("获取用户余额信息失败", e);
			Thread.sleep(SLEEP);
			return getUserInfo(httpConfig, host, ++index[0]);
		}
		return userInfo;
	}

	/**
	 * 获取期数内容
	 *
	 * @param httpConfig
	 * @param host
	 * @param game
	 * @return
	 */
	public static JSONObject getPeriod(HttpClientConfig httpConfig, String host, String game, int... index)
			throws IOException, InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = host.concat("member/period?lottery=").concat(game).concat("&_=") + System.currentTimeMillis();

		String periodHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

		if (StringTool.isEmpty(periodHtml)) {
			log.error("获取期数信息失败", periodHtml);
			Thread.sleep(SLEEP);
			return getPeriod(httpConfig, host, game, ++index[0]);
		}

		return JSONObject.fromObject(periodHtml);
	}
	/**
	 * 获取盘口赔率
	 *
	 * @param httpConfig http请求配置类
	 * @param host       主机地址
	 * @param game       盘口游戏code
	 * @param oddsCode   赔率code
	 * @param index      循环次数
	 * @return
	 */
	public static JSONObject getOdds(HttpClientConfig httpConfig, String host, String game, String oddsCode,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url =
				host.concat("member/odds?lottery=").concat(game).concat("&games=").concat(oddsCode).concat("&_=") + System
						.currentTimeMillis();

		String oddsHtml;
		try {
			oddsHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			//赔率信息获取为空，有可能是处于封盘中
			if (ContainerTool.isEmpty(oddsHtml)) {
				log.error("获取盘口赔率信息失败",oddsHtml);
				Thread.sleep(SLEEP);
				return getOdds(httpConfig, host, game, oddsCode, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取盘口赔率信息失败", e);
			Thread.sleep(SLEEP);
			return getOdds(httpConfig, host, game, oddsCode, ++index[0]);
		}

		return JSONObject.fromObject(oddsHtml);
	}
	/**
	 * 投注
	 *
	 * @param httpConfig http请求配置类
	 * @param host       主机地址
	 * @param betsArray  投注信息
	 * @param drawNumber 开奖期数
	 * @param game       盘口游戏code
	 * @throws IOException
	 * @return 投注结果
	 */
	public static JSONObject betting(HttpClientConfig httpConfig, String host, JSONArray betsArray, String drawNumber,
			String game) throws IOException {
		JSONObject info = new JSONObject();

		info.put("bets", betsArray);
		info.put("drawNumber", drawNumber);
		info.put("ignore", false);
		info.put("lottery", game);

		httpConfig.setHeader("Content-Type", "application/json");
		Map<String, Object> join = new HashMap<>(1);
		join.put("$ENTITY_JSON$", info);
		String url = host.concat("member/bet");
		String resultHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));

		if (ContainerTool.isEmpty(resultHtml)) {
			log.error("投注结果页面为空,投注项为：" + betsArray);
			return null;
		}
		if (StringTool.isContains(resultHtml, "已被上级禁止投注，请与上级联系")) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", "已被上级禁止投注，请与上级联系");
			return jsonObject;
		}

		return JSONObject.fromObject(resultHtml);
	}
	/**
	 * 获取未结算信息
	 *
	 * @param httpConfig http请求配置类
	 * @param hostUrl    主机地址
	 * @param game       盘口游戏code
	 * @return 未结算信息
	 */
	public static String getNoSettle(HttpClientConfig httpConfig, String hostUrl, String game, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html;
		try {
			String url = hostUrl.concat("member/lasts?lottery=").concat(game).concat("&_=") + System.currentTimeMillis();
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算页面失败", html);
				Thread.sleep(SLEEP);
				return getNoSettle(httpConfig, hostUrl, game, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取未结算页面失败", e);
			Thread.sleep(SLEEP);
			return getNoSettle(httpConfig, hostUrl, game, ++index[0]);
		}
		return html;
	}
	/**
	 * 获取结算信息
	 *
	 * @param httpConfig http请求配置类
	 * @param hostUrl    主机地址
	 * @return
	 */
	public static JSONArray profit(HttpClientConfig httpConfig, String hostUrl, int page, int... index)
			throws InterruptedException, ParseException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html;
		try {
			String url = hostUrl.concat("member/bets?settled=true");
			if (page != 1) {
				url = url.concat("&page=") + page;
			}
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "Welcome")) {
				log.error("获取结算信息失败", html);
				Thread.sleep(SLEEP);
				return profit(httpConfig, hostUrl, page, ++index[0]);
			}
			//没开奖信息
			if (StringTool.isContains(html, "暂无数据")) {
				log.trace("暂未有结算信息");
				return null;
			}
		} catch (Exception e) {
			log.error("获取结算信息失败", e);
			Thread.sleep(SLEEP);
			return profit(httpConfig, hostUrl, page, ++index[0]);
		}

		return getSettleInfo(html);
	}
	/**
	 * 定时检验盘口
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @return
	 */
	public static String toCheckIn(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html;
		try {
			String url = projectHost.concat("member/notice?_=") + System.currentTimeMillis();

			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			if (StringTool.isEmpty(html)) {
				log.error("定时检验失败", html);
				Thread.sleep(SLEEP);
				return toCheckIn(httpConfig, projectHost, ++index[0]);
			}
		} catch (Exception e) {
			log.error("定时检验失败", e);
			Thread.sleep(SLEEP);
			return toCheckIn(httpConfig, projectHost, ++index[0]);
		}
		return html;
	}
	/**
	 * 获取游戏限额
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param game        盘口游戏code
	 * @return
	 */
	public static JSONObject getMemberInfo(HttpClientConfig httpConfig, String projectHost, String game, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html;
		try {
			String url = projectHost.concat("member/info?lottery=").concat(game);
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败", html);
				Thread.sleep(SLEEP);
				return getMemberInfo(httpConfig, projectHost, game, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取游戏限额失败", e);
			Thread.sleep(SLEEP);
			return getMemberInfo(httpConfig, projectHost, game, ++index[0]);
		}
		return getLimit(html);
	}
	//TODO,代理
	/**
	 * 获取会员信息页面
	 * @param httpConfig		http请求配置类
	 * @param projectHost		主机地址
	 * @param agentAccount	代理账号
	 * @param roleType		查询角色类型
	 * @return
	 */
	public static JSONArray getMemberList(HttpClientConfig httpConfig, String projectHost, String agentAccount,
			int roleType, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url=projectHost.concat("agent/user/list?parent=").concat(agentAccount).concat("&type=")+roleType+"&lv=";

		String html;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if(StringTool.isEmpty(html)){
				log.error("获取会员信息页面失败", html);
				Thread.sleep(SLEEP);
				return getMemberList(httpConfig, projectHost, agentAccount,roleType, ++index[0]);
			}

		} catch (Exception e) {
			log.error("获取会员信息页面失败", e);
			Thread.sleep(SLEEP);
			return getMemberList(httpConfig, projectHost, agentAccount,roleType, ++index[0]);
		}
		return memberInfoList(html);
	}
	/**
	 * 获取会员在线信息
	 * @param httpConfig			http请求配置类
	 * @param projectHost			主机地址
	 * @return
	 */
	public static String onlineInfo(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html;
		try {
			html = HttpClientUtil.findInstance()
					.getHtml(httpConfig.url(projectHost.concat("agent/online?_=") + System.currentTimeMillis()));
			if (StringTool.isEmpty(html)) {
				log.error("获取会员在线信息失败", html);
				Thread.sleep(SLEEP);
				return onlineInfo(httpConfig, projectHost, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取会员在线信息失败", e);
			Thread.sleep(SLEEP);
			return onlineInfo(httpConfig, projectHost, ++index[0]);
		}

		return html;
	}
	/**
	 * 获取结算合计页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param date        时间
	 * @param game        盘口游戏
	 * @return
	 */
	public static JSONArray getTotalList(HttpClientConfig httpConfig, String projectHost, String date, String game ,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");

		String url = projectHost.concat("agent/report/list?&begin=").concat(date).concat("&end=").concat(date)
				.concat("&settle=") + false + "&amount=&dividend=&lottery=".concat(game).concat("&period=");

		String html;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取结算合计页面失败", html);
				Thread.sleep(SLEEP);
				return getTotalList(httpConfig, projectHost, date, game, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取结算合计页面失败", e);
			Thread.sleep(SLEEP);
			return getTotalList(httpConfig, projectHost, date, game, ++index[0]);
		}
		Document document=Jsoup.parse(html);

		Element tbody=document.selectFirst("tbody");

		if(StringTool.isEmpty(tbody)){
			log.info("暂无会员投注信息");
			return null;
		}
		JSONArray array=new JSONArray();
		Elements trs=tbody.select("tr");
		for(Element tr:trs){
			Elements tds=tr.getElementsByTag("td");
			array.add(tds.get(1).text());
		}

		return array;
	}
	/**
	 * 获取投注详情信息
	 * @param httpConfig
	 * @param projectHost
	 * @param date
	 * @param game
	 * @param followMember
	 * @param index
	 * @return
	 */
	public static JSONArray betDetails(HttpClientConfig httpConfig, String projectHost, String date, String game,
			String followMember,int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		//还没加分页
		String url =
				projectHost.concat("agent/report/bets?username=").concat(followMember).concat("&lottery=").concat(game)
						.concat("&begin=").concat(date).concat("&end=").concat(date).concat("&settle=") + false;

		String html;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if(StringTool.isEmpty(html)){
				log.error("获取投注详情信息失败", html);
				Thread.sleep(SLEEP);
				return betDetails(httpConfig,projectHost,date,game,followMember,++index[0]);
			}
		} catch (Exception e) {
			log.error("获取投注详情信息失败", e);
			Thread.sleep(SLEEP);
			return betDetails(httpConfig,projectHost,date,game,followMember,++index[0]);
		}

		return getBetDetails(html);
	}
	// TODO Document解析页面函数
	/**
	 * 获取投注详情信息
	 * @param html		投注详情页面
	 * @return
	 */
	private static JSONArray getBetDetails(String html) {
		JSONArray array=getBetDetails(html);
		Document document=Jsoup.parse(html);
		Element tbody=document.selectFirst("tbody");

		Elements trs=tbody.select("tr");

		JSONObject object=new JSONObject();
		for(Element tr:trs){
			Elements tds=tr.getElementsByTag("td");
			//注单号
			object.put("single",tds.get(0).text());
			//期数
			object.put("period",tds.get(2).text().split(" ")[1]);
			//投注内容
			object.put("bet",tds.get(4).text().split(" ")[0]);
			//投注金额
			object.put("amount",tds.get(5).text());
			array.add(object);
			object.clear();
		}
		return array;
	}
	/**
	 * 解析会员列表页面
	 * @param html
	 * @return
	 */
	private static JSONArray memberInfoList(String html) {
		Document document = Jsoup.parse(html);
		Element tbody = document.selectFirst("tbody");

		Elements trs = tbody.select("tr");

		JSONArray array=new JSONArray();
		JSONObject object=new JSONObject();
		for(Element tr:trs){
			Elements tds = tr.getElementsByTag("td");
			String online=tds.get(0).selectFirst("span").attr("class");
			String appOnline=tds.get(1).selectFirst("span").attr("class");
			object.put("online",online);
			object.put("appOnline",appOnline);
			object.put("parent",tds.get(2).text());
			object.put("userName",tds.get(4).text());
			object.put("limit",tds.get(5).text());
			object.put("range",tds.get(7).text());
			object.put("status",tds.get(9).selectFirst("input").val());
			array.add(object);
			object.clear();
		}
		return array;
	}
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
	public static String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml,int role) throws IOException {

		Document routeDocument = Jsoup.parse(routeHtml);

		Element tbody = routeDocument.selectFirst("tbody");

		//包括会员，代理，开奖网线路
		Elements trs = tbody.select("tr");

		//role==1为会员，role==2为代理
		String roleType;
		if (role == 1) {
			roleType="会员线路";
		}else{
			roleType="代理线路";
		}
		JSONArray hrefArray = new JSONArray();

		for (Element tr : trs) {
			String type=tr.selectFirst("td").text();
			if(StringTool.isContains(type,roleType)){
				String href = tr.select("a").attr("href");
				hrefArray.add(href.split("=")[1]);
			}
		}
		String[] routes = new String[4];
		long[] arr = new long[4];

		//判断时间延迟
		long t1, t2;
		for (int i = 0; i < hrefArray.size(); i++) {
			t1 = System.currentTimeMillis();
			String url = hrefArray.getString(i).concat("/?random-no-cache=")
					.concat(Integer.toHexString((int) Math.floor((1 + Math.random()) * 0x10000)));
			HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			t2 = System.currentTimeMillis();
			long myTime = t2 - t1;
			routes[i] = hrefArray.getString(i).concat("/");
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
	 * @param loginInfoMap
	 * @return
	 */
	private static Map<String, String> getCodeAndAction(String loginHtml, Map<String, String> loginInfoMap) {
		Document document = Jsoup.parse(loginHtml);

		Element select = document.selectFirst("form");

		String action = select.attr("action");

		String code = select.select("img").attr("src");

		loginInfoMap.put("action", action);
		loginInfoMap.put("code", code);

		return loginInfoMap;
	}
	/**
	 * 获取协议页面href
	 *
	 * @param agreementHtml 协议页面
	 * @return href
	 */
	public static String getAgreementHref(String agreementHtml) {
		Document document = Jsoup.parse(agreementHtml);

		Element ul = document.selectFirst("ul");

		Element span = ul.selectFirst("span");

		Elements as = span.select("a");

		as.remove(0);

		return as.attr("href");
	}
	/**
	 * 登陆错误
	 *
	 * @param loginInfo 登陆结果页面
	 * @return 错误信息
	 */
	public static IbmHcCodeEnum loginError(String loginInfo) {
		log.error("SgWin盘口会员登陆异常，异常的登陆结果页面为：" + loginInfo);
		if (StringTool.isContains(loginInfo, "验证码错误")) {
			return IbmHcCodeEnum.IBM_403_VERIFY_CODE;
		} else if (StringTool.contains(loginInfo, "抱歉!你的帐号已被冻结", "账户已禁用")) {
			return IbmHcCodeEnum.IBM_403_USER_STATE;
		} else if (StringTool.contains(loginInfo, "账号或密码错误")) {
			return IbmHcCodeEnum.IBM_403_USER_ACCOUNT;
		} else if (StringTool.contains(loginInfo, "修改密码")) {
			return IbmHcCodeEnum.IBM_403_CHANGE_PASSWORD;
		} else {
			return IbmHcCodeEnum.IBM_403_UNKNOWN;
		}
	}
	/**
	 * 解析结算页面信息
	 *
	 * @param html 结算页面
	 * @return 结算信息
	 */
	private static JSONArray getSettleInfo(String html) throws ParseException {
		Document document = Jsoup.parse(html);

		Element tbody = document.selectFirst("tbody");

		Elements trs = tbody.select("tr");
		JSONArray array = new JSONArray();
		for (Element tr : trs) {
			JSONObject jsonObject = new JSONObject();
			//注单号
			jsonObject.put("single", tr.selectFirst("td").text());
			//投注时间,转成时间戳
			jsonObject.put("betTime", DateTool.getMinute(tr.getElementsByClass("time").text()).getTime());
			//赔率
			jsonObject.put("odds", tr.getElementsByClass("odds").text());
			//投注结果
			jsonObject.put("result", tr.getElementsByClass("result").text());
			array.add(jsonObject);
		}
		return array;
	}
	/**
	 * 解析游戏限额页面
	 *
	 * @param html 游戏限额页面
	 * @return 游戏限额
	 */
	private static JSONObject getLimit(String html) {
		JSONObject limit = new JSONObject();
		String number = "00";
		Document document = Jsoup.parse(html);

		Element tableClass = document.getElementsByClass("list table data_table").first();

		Elements trs = tableClass.selectFirst("tbody").select("tr");
		for (Element tr : trs) {
			JSONArray array = new JSONArray();
			Elements tds = tr.select("td");
			tds.remove(3);
			tds.remove(0);
			for (Element td : tds) {
				array.add(td.text());
			}
			limit.put(number, array);
			number = StringTool.addOne(number);
		}
		return limit;
	}
	// TODO 功能函数
	/**
	 * 编码后的投注信息
	 *
	 * @param game     盘口游戏code
	 * @param betItems 投注项
	 * @param odds     赔率信息
	 * @return 转换后的投注信息
	 */
	public static JSONArray getBetInfo(String game, List<String> betItems, JSONObject odds) {
		switch (game) {
			case "BJPK10":
			case "XYFT":
				return getBetInfo(betItems, odds, SgWinConfig.getRankCode(game), SgWinConfig.getBallCode(game));
			default:
				throw new RuntimeException("未知的盘口游戏code：" + game);
		}
	}
	/**
	 * 编码后的投注信息
	 *
	 * @param betItems    投注项
	 * @param odds        赔率信息
	 * @param rankCodeMap 排名code
	 * @param ballCodeMap 球号code
	 * @return 转换后的投注信息
	 */
	private static JSONArray getBetInfo(List<String> betItems, JSONObject odds, Map<String, String> rankCodeMap,
			Map<String, String> ballCodeMap) {
		JSONArray betsArray = new JSONArray();
		for (String bet : betItems) {
			JSONObject betsJson = new JSONObject();
			String[] strs = bet.split("#");
			String ballCode = ballCodeMap.get(strs[0]);
			if (StringTool.isEmpty(ballCode) || !odds.containsKey(ballCode)) {
				continue;
			}
			String[] ballCodes = ballCode.split("_");
			betsJson.put("amount", Integer.parseInt(strs[1]));
			betsJson.put("contents", ballCodes[1]);
			betsJson.put("game", ballCodes[0]);
			betsJson.put("odds", odds.get(ballCode));
			betsJson.put("title", rankCodeMap.get(strs[0].split("\\|")[0]));
			betsArray.add(betsJson);
		}
		return betsArray;
	}

}
