package com.ibm.common.utils.http.tools.agent;

import com.alibaba.fastjson.JSONArray;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.DetailInfo;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
 * @Description: SgWin盘口代理工具类
 * @Author: null
 * @Date: 2019-10-28 10:17
 * @Version: v1.0
 */
public class SgWinAgentTool {
	private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
	protected static final Logger log = LogManager.getLogger(SgWinAgentTool.class);

	private static final long SLEEP = 1000L;

	private static final Integer MAX_RECURSIVE_SIZE = 5;
	private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

	// TODO 开启页面函数

	//  开启页面函数

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

			if (StringTool.isEmpty(navigationHtml)) {
				log.debug("获取导航页面失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}

			if(StringTool.isContains(navigationHtml,"百度")){
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
				log.debug("获取线路页面失败" + routeHtml);
				Thread.sleep(SLEEP);
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
	 * 线路测速
	 *
	 * @param httpConfig http请求配置类
	 * @param hrefs      线路列表
	 * @return 测速后的线路 - 小到大排序
	 */
	public static String[] speedRoute(HttpClientConfig httpConfig, List<String> hrefs) {
		String[] routes = new String[hrefs.size()];
		long[] times = new long[hrefs.size()];
		long starTime, endTime;
		for (int i = 0; i < hrefs.size(); i++) {
			starTime = System.currentTimeMillis();
			String href = hrefs.get(i).concat("/");
			String url = href.concat("?random-no-cache=")
					.concat(Integer.toHexString((int) Math.floor((1 + Math.random()) * 0x10000)));
			HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			endTime = System.currentTimeMillis();
			long myTime = endTime - starTime;
			routes[i] = href;
			times[i] = myTime;
		}
		//线路按延时从小到大排序
		sortRoute(routes, times);
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
	public static Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[0] >= routes.length) {
			index[0] = 0;
			index[1]++;
		}
		if (index[1] > MAX_RECURSIVE_SIZE) {
			return new HashMap<>(1);
		}
		String html;
		Map<String, String> loginInfoMap;

		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]].concat("login")));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "Welcome")) {
				log.debug("打开选择线路界面失败=" + html);
				Thread.sleep(SLEEP);
				++index[1];
				return getLoginHtml(httpConfig, routes, index);
			}
			loginInfoMap = new HashMap<>(3);
			loginInfoMap.put("loginSrc", routes[index[0]]);

		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(2 * SLEEP);
			++index[0];
			return getLoginHtml(httpConfig, routes, index);
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
		try {
			String content;
			//获取验证码内容,code等于空时，刷新验证码
			if (StringTool.isEmpty(code)) {
				content = HttpClientUtil.findInstance()
						.getImage(httpConfig.url(projectHost.concat("code?_=") + System.currentTimeMillis()));
			} else {
				content = HttpClientUtil.findInstance().getImage(httpConfig.url(projectHost.concat(code)));
			}
			if (StringTool.isEmpty(content)) {
				log.debug("获取验证码失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
			}
			Map<String, Object> join = new HashMap<>(2);
			join.put("type", "SGWIN");
			join.put("content", content);

			String html = HttpClientTool.doPost(CRACK_CONTENT, join);

			if (StringTool.isEmpty(html)) {
				log.debug("破解验证码失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
			}
			html = html.replace("\"", "");
			//验证码位数低于4位
			if (html.length() < 4) {
				return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("破解验证码失败", e);
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, null, ++index[0]);
		}
	}

	/**
	 * 去登录
	 *
	 * @param httpConfig    http请求配置类
	 * @param agentAccount  代理账号
	 * @param agentPassword 代理密码
	 * @param verifyCode    验证码
	 * @param projectHost   主机地址
	 * @param action        登录地址
	 * @return 登录结果页
	 */
	public static String getLogin(HttpClientConfig httpConfig, String agentAccount, String agentPassword,
			String verifyCode, String projectHost, String action, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(4);

		join.put("type", 2);
		join.put("account", agentAccount);
		join.put("password", agentPassword);
		join.put("code", verifyCode);
		join.put("facode", "");

		String url = projectHost.concat(action);
		String loginInfo = null;
		try {
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.debug("获取登陆信息失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getLogin(httpConfig, agentAccount, agentPassword, verifyCode, projectHost, action, ++index[0]);
			}
			//账号冻结,账户已禁用,第一次登陆
			if (StringTool.contains(loginInfo, "账号或密码错误", "抱歉!你的帐号已被冻结", "账户已禁用", "修改密码")) {
				return loginInfo;
			}
			//验证码错误
			if (StringTool.isContains(loginInfo, "验证码")) {
				log.debug("验证码错误" + loginInfo);
				Thread.sleep(SLEEP);
				verifyCode = getVerifyCode(httpConfig, projectHost, null);
				return getLogin(httpConfig, agentAccount, agentPassword, verifyCode, projectHost, action, ++index[0]);
			}
			return loginInfo;
		} catch (Exception e) {
			log.error("获取登陆信息失败", e);
			Thread.sleep(2 * SLEEP);
			return getLogin(httpConfig, agentAccount, agentPassword, verifyCode, projectHost, action, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], loginInfo));
		}
	}

	/**
	 * 获取会员列表
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @return 会员列表
	 */
	public static JSONArray getMemberList(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost.concat("agent/user/list?type=1");
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.debug("获取会员列表信息失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getMemberList(httpConfig, projectHost, ++index[0]);
			}
			return getMemberInfo(html);
		} catch (Exception e) {
			log.error("获取会员列表失败,结果信息=" + html, e);
			Thread.sleep(2 * SLEEP);
			return getMemberList(httpConfig, projectHost, ++index[0]);
		}
	}

	/**
	 * 获取未结算摘要信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param lottery     抓取的游戏
	 * @param day         抓取的天数
	 * @return 未结算摘要信息
	 */
	public static Map<String, Map<String, SummaryInfo>> getBetSummary(HttpClientConfig httpConfig, String projectHost,
			String lottery, String day, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost.concat("agent/report/list?&");
		String parametersFormat = "begin=%s&end=%s&settle=false&amount=&dividend=&lottery=%s&period=";
		String parameters = String.format(parametersFormat, day, day, lottery);
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.debug("获取未结算摘要信息失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetSummary(httpConfig, projectHost, lottery, day, ++index[0]);
			}
			Map<String, Map<String, SummaryInfo>> betSummary = getBetSummary(html);
			if (betSummary == null) {
				log.info("获取未结算摘要信息失败,结果信息：{}", html);
				Thread.sleep(SLEEP);
				return getBetSummary(httpConfig, projectHost, lottery, day, ++index[0]);
			}
			return betSummary;
		} catch (Exception e) {
			log.error("未结算摘要信息失败", e);
			Thread.sleep(2 * SLEEP);
			return getBetSummary(httpConfig, projectHost, lottery, day, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], html));
		}
	}

	/**
	 * 获取子代理未结算摘要信息
	 *
	 * @param httpConfig   http请求配置类
	 * @param projectHost  主机地址
	 * @param lottery      抓取的游戏
	 * @param day          抓取的天数
	 * @param agentAccount 子代理账户
	 * @return 子代理未结算摘要信息
	 */
	public static Map<String, Map<String, SummaryInfo>> getBetSummary(HttpClientConfig httpConfig, String projectHost,
			String lottery, String day, String agentAccount, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost.concat("agent/report/list?&");
		String parametersFormat = "username=%s&lottery=%s&begin=%s&end=%s&settle=false";
		String parameters = String.format(parametersFormat, agentAccount, lottery, day, day);
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.debug("获取未结算摘要信息失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetSummary(httpConfig, projectHost, lottery, day, agentAccount, ++index[0]);
			}
			Map<String, Map<String, SummaryInfo>> betSummary = getBetSummary(html);
			if (betSummary == null) {
				log.debug("获取未结算摘要信息失败,结果信息:{}", html);
				Thread.sleep(SLEEP);
				return getBetSummary(httpConfig, projectHost, lottery, day, agentAccount, ++index[0]);
			}
			return betSummary;
		} catch (Exception e) {
			log.error("获取子代理未结算摘要信息失败", e);
			Thread.sleep(2 * SLEEP);
			return getBetSummary(httpConfig, projectHost, lottery, day, agentAccount, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], html));
		}
	}

	/**
	 * 获取投注详情
	 *
	 * @param httpConfig    http请求配置类
	 * @param projectHost   主机地址
	 * @param pageIndex          获取的页码
	 * @param lottery       抓取的游戏
	 * @param day           抓取的天
	 * @param memberAccount 会员账户
	 * @return 投注详情
	 */
	public static String getBetDetail(HttpClientConfig httpConfig, String projectHost, Integer pageIndex,
			String lottery, String day, String memberAccount, Object roundno, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost.concat("agent/report/bets?");
		String parametersFormat = "username=%s&lottery=%s&begin=%s&end=%s&settle=false";
		if (roundno != null) {
			parametersFormat += "&period=" + roundno;
		}
		String parameters = String.format(parametersFormat, memberAccount, lottery, day, day);
		if (pageIndex != null) {
			parameters = parameters.concat("&page=" + pageIndex);
		}
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.debug("获取未结算详情信息失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetDetail(httpConfig, projectHost, pageIndex, lottery, day, memberAccount, roundno,
						++index[0]);
			}
			if (!StringTool.isContains(html, "注单明细")) {
				log.debug("获取未结算详情信息失败,结果信息=" + html);
				Thread.sleep(SLEEP);
				return getBetDetail(httpConfig, projectHost, pageIndex, lottery, day, memberAccount, roundno,
						++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取投注详情失败", e);
			Thread.sleep(2 * SLEEP);
			return getBetDetail(httpConfig, projectHost, pageIndex, lottery, day, memberAccount, roundno, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], html));
		}

	}

	//  开启页面函数

	// TODO 页面解析函数

	//  页面解析函数

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
	 * 获取代理线路
	 *
	 * @param routeHtml 代理页面
	 * @return 代理线路
	 */
	public static List<String> getAgentRoute(String routeHtml) {
		Document routeDocument = Jsoup.parse(routeHtml);

		Element tbody = routeDocument.selectFirst("tbody");
		//包括会员，代理，开奖网线路
		Elements trs = tbody.select("tr");

		List<String> hrefs = new ArrayList<>();

		for (Element tr : trs) {
			String type = tr.selectFirst("td").text();
			if (StringTool.isContains(type, "代理线路")) {
				String href = tr.select("a").attr("href");
				hrefs.add(href.split("=")[1]);
			}
		}
		return hrefs;
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
	 * 获取会员信息
	 *
	 * @param html 会员信息页面
	 * @return 会员信息
	 */
	private static JSONArray getMemberInfo(String html) {
		Document document = Jsoup.parse(html);
		//找到会员列表
		Elements trElements = document.select("div.contents>div.user_list>table.data_table>tbody>tr");
		JSONArray memberList = new JSONArray();
		JSONArray member;
		//循环会员
		for (Element trElement : trElements) {
			String online = "offline";
			//会员在线
			Element onlineSpan = trElement.selectFirst("td.online>a>span");
			if (onlineSpan != null && "s1".equals(onlineSpan.className())) {
				online = "online";
			}

			String userName = trElement.selectFirst("td.username").text().split(" \\[")[0];
			member = new JSONArray();
			member.add(userName);
			member.add(online);
			memberList.add(member);
		}
		return memberList;
	}

	/**
	 * 未结算摘要
	 *
	 * @param html 未结算摘要页面
	 * @return 未结算摘要
	 */
	private static Map<String, Map<String, SummaryInfo>> getBetSummary(String html) {
		Document document = Jsoup.parse(html);
		Elements trElements = document.select("div.contents>table.data_table>tbody>tr");
		if (trElements.isEmpty()) {
			//没有未结算数据,页面不为空
			if (StringTool.isContains(html, "会员输赢")) {
				return new HashMap<>(2);
			}
			return null;
		}
		Map<String, SummaryInfo> member = new HashMap<>(trElements.size() * 3 / 4 + 1);
		Map<String, SummaryInfo> agent = new HashMap<>(trElements.size() / 4 + 1);
		for (Element trElement : trElements) {
			SummaryInfo summaryInfo = new SummaryInfo();
			summaryInfo.setAccount(trElement.child(1).text());
			summaryInfo.setBetCount(trElement.child(4).text());
			summaryInfo.setBetAmount(trElement.child(5).text());
			String type = trElement.child(0).text();
			if ("会员".equals(type)) {
				member.put(trElement.child(1).text(), summaryInfo);
			} else {
				agent.put(trElement.child(1).text(), summaryInfo);
			}
		}
		Map<String, Map<String, SummaryInfo>> betSummary = new HashMap<>(2);
		betSummary.put("member", member);
		betSummary.put("agent", agent);
		return betSummary;
	}

	/**
	 * 获取投注详情
	 *
	 * @param betDetail    投注详情
	 * @param gameCode     游戏编码
	 * @param html         投注详情页面
	 * @param subOddNumber 上一次请求的主单号
	 * @return 投注详情
	 */
	public static Map<String, Object> betDetail(Map<String, Object> betDetail, GameUtil.Code gameCode, String html,
			String subOddNumber) {
		Document document = Jsoup.parse(html);
		Elements trElements = document.select("div.contents>table.data_table>tbody>tr");
		if (trElements.isEmpty()) {
			return null;
		}
		List<Map<String, Object>> details;
		if (betDetail == null) {
			betDetail = new HashedMap(3);

			Elements pageElements = document.select("div.page>div.page_info>span");
			String total = StringUtils.substringBetween(pageElements.get(0).text(), "共 ", " 笔注单");
			String pages = StringUtils.substringBetween(pageElements.get(1).text(), "共 ", " 页");

			details = new ArrayList<>(NumberTool.getInteger(total) * 3 / 4 + 1);
			betDetail.put("pages", NumberTool.getInteger(pages));
		} else {
			details = (List<Map<String, Object>>) betDetail.get("details");
		}

		//放入投注项
		String maxOddNumber = betDetail.getOrDefault("maxOddNumber", subOddNumber).toString();

		int compare = maxOddNumber.compareTo(subOddNumber);
		boolean flag = true;
		for (Element trElement : trElements) {
			//注单号
			String oddNumber = trElement.child(0).text();
			int tmpCompare = oddNumber.compareTo(subOddNumber);
			if (tmpCompare <= 0) {
				flag = false;
				break;
			} else {
				if (compare < tmpCompare) {
					compare = tmpCompare;
					maxOddNumber = oddNumber;
				}
			}
			Map<String, Object> info = new HashMap<>(2);
			//投注项
			String betInfo = trElement.child(4).selectFirst("span").text();
			betInfo = betInfo.replace("』", "");
			info.put("betItem", getBetItem(gameCode, betInfo));
			//投注积分
			String funds = trElement.child(5).text();
			info.put("funds", funds);
			details.add(info);
		}
		if (!flag) {
			betDetail.remove("pages");
		}

		betDetail.put("maxOddNumber", maxOddNumber);
		betDetail.put("details", details);
		return betDetail;
	}

	/**
	 * 获取投注详情
	 *
	 * @param betDetail    投注详情
	 * @param gameCode     游戏编码
	 * @param html         投注详情页面
	 * @param subOddNumber 上一次请求的主单号
	 * @return 投注详情
	 */
	public static DetailBox analyzeDetail(DetailBox betDetail, GameUtil.Code gameCode, String html, String subOddNumber) {
		Document document = Jsoup.parse(html);
		Elements trElements = document.select("div.contents>table.data_table>tbody>tr");
		if (trElements.isEmpty()) {
			return null;
		}
		if (betDetail == null) {
			betDetail = new DetailBox();
			Elements pageElements = document.select("div.page>div.page_info>span");
			String total = StringUtils.substringBetween(pageElements.get(0).text(), "共 ", " 笔注单");
			String page = StringUtils.substringBetween(pageElements.get(1).text(), "共 ", " 页");
			betDetail.pages(page, total);
		}
		List<DetailInfo> details = betDetail.details();

		String maxOddNumber = betDetail.getMaxOddNumber(subOddNumber);
		int compare = maxOddNumber.compareTo(subOddNumber);

		//放入投注项
		for (Element trElement : trElements) {
			//注单号
			String oddNumber = trElement.child(0).text();
			int tmpCompare = oddNumber.compareTo(subOddNumber);
			if (tmpCompare <= 0) {
				betDetail.hasNext(false);
				break;
			} else {
				if (compare < tmpCompare) {
					compare = tmpCompare;
					maxOddNumber = oddNumber;
				}
			}
			//投注项
			String betInfo = trElement.child(4).selectFirst("span").text();
			betInfo = betInfo.replace("』", "");
			//投注积分
			String funds = trElement.child(5).text();

			details.add(new DetailInfo(getBetItem(gameCode, betInfo),NumberTool.getDouble(funds)));
		}
		betDetail.maxOddNumber(maxOddNumber);
		return betDetail;
	}

	//  页面解析函数

	// TODO 功能函数

	//  功能函数

	/**
	 * 根据时延排序线路 排序由小到大
	 *
	 * @param routes 线路数组
	 * @param times  时延数组
	 */
	private static void sortRoute(String[] routes, long[] times) {
		for (int i = 0; i < times.length; i++) {
			for (int j = 0; j < times.length - i - 1; j++) {
				if (times[j] > times[j + 1]) {
					long time = times[j];
					String route = routes[j];

					times[j] = times[j + 1];
					routes[j] = routes[j + 1];

					times[j + 1] = time;
					routes[j + 1] = route;
				}
			}
		}
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
	 * 获取投注项
	 *
	 * @param gameCode 投注编码
	 * @param betInfo  投注信息
	 * @return 投注项
	 */
	private static String getBetItem(GameUtil.Code gameCode, String betInfo) {
		String[] infos = betInfo.split("『");
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				if (infos.length == 1) {
					return betInfo.substring(0, 2).concat("|").concat(betInfo.substring(2));
				}
				switch (infos[0]) {
					case "冠军":
						return "第一名|".concat(infos[1]);
					case "亚军":
						return "第二名|".concat(infos[1]);
					case "冠亚军和":
						return "冠亚|".concat(infos[1]);
					default:
                        return infos[0].concat("|").concat(infos[1]);
				}
			case JSSSC:
			case CQSSC:
				if (infos.length == 1) {
					if (betInfo.contains("总和")) {
						return betInfo.substring(0, 2).concat("|").concat(betInfo.substring(2));
					}
					return "龙虎和|".concat(betInfo);
				}
				return infos[0].concat("|").concat(infos[1]);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取盘口主页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param index       循环次数
	 * @return 盘口主页面
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
			indexHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(projectHost.concat("agent/index")));
			if (StringTool.isEmpty(indexHtml) || !StringTool.isContains(indexHtml, "Welcome")) {
				log.error("获取盘口代理主页面失败：{}", indexHtml);
				Thread.sleep(SLEEP);
				return getHomePage(httpConfig, projectHost, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取盘口主页面失败", e);
			Thread.sleep(SLEEP);
			return getHomePage(httpConfig, projectHost, ++index[0]);
		}
		return indexHtml;
	}

	//  功能函数

}
