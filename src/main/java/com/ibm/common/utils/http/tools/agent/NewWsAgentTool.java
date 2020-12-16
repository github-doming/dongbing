package com.ibm.common.utils.http.tools.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.member.NewWsMemberTool;
import com.ibm.common.utils.http.tools.member.SgWinMemberTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.DetailInfo;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
public class NewWsAgentTool {
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
	public static String getSelectRoutePage(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String routeHtml;
		try {

			Map<String, Object> join = new HashMap<>(2);
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
	 * @param httpConfig http请求配置类
	 * @param routes     线路
	 * @param index      循环次数
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
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(routes[index[0]].concat("b/login.jsp")).map(join));

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
					.getImage(httpConfig.url(projectHost.concat("user/getValidateCodeB.do?t=") + System.currentTimeMillis() + "&ticket=" + ticket));
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
	 * @param accountInfo 账号信息
	 * @param verifyCode  验证码
	 * @param projectHost 线路地址
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

		Map<String, Object> join = new HashMap<>(6);
		join.put("codeY", accountInfo.getHandicapCaptcha());
		join.put("snn", snn);
		join.put("zhanghao", accountInfo.getAccount());
		join.put("mima", Md5Tool.md5Hex(accountInfo.getPassword()));
		join.put("validateCode", verifyCode);
		join.put("ticket", ticket);
		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("user/loginb.do")).map(join));
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
	 * 获取会员列表
	 *
	 * @param agent 代理信息
	 * @param page  页码
	 * @param index 循环次数
	 * @return
	 * @throws InterruptedException
	 */
	public static JSONObject getMemberList(AgentCrawler agent, Object page, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		HttpClientConfig httpClient = agent.getHcConfig();

		String url = agent.getProjectHost().concat("user/query/queryHY.do");

		Map<String, Object> join = new HashMap<>(7);
		join.put("userID", 0);
		join.put("name", "");
		join.put("ancestorName", "");
		join.put("status", 1);
		join.put("page", page);
		join.put("ticket", agent.getTicket());
		join.put("uid", agent.getMemberno());

		try {
			String html = HttpClientUtil.findInstance().postHtml(httpClient.url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取会员列表信息失败");
				Thread.sleep(SLEEP);
				return getMemberList(agent, page, ++index[0]);
			}
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取会员列表信息失败", e);
			Thread.sleep(SLEEP);
			return getMemberList(agent, page, ++index[0]);
		}
	}

	/**
	 * 获取未结算摘要信息
	 *
	 * @param agent  代理信息
	 * @param game   盘口游戏id
	 * @param date   时间
	 * @param period 期数
	 * @return 未结算摘要信息
	 */
	public static Map<String, SummaryInfo> getBetSummary(AgentCrawler agent, String game, String date, Object period, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(8);
		join.put("userID", agent.getMemberno());
		join.put("gameCode", game);
		join.put("startDate", date);
		join.put("endDate", date);
		join.put("isSettle", 0);
		join.put("seqNumber", period);
		join.put("ticket", agent.getTicket());
		join.put("uid", agent.getMemberno());
		String url = agent.getProjectHost().concat("game/report/queryDL.do");

		String html = null;
		try {
			html = HttpClientUtil.findInstance().postHtml(agent.getHcConfig().url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.debug("获取未结算摘要信息失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetSummary(agent, game, date, period, ++index[0]);
			}

			Map<String, SummaryInfo> betSummary = getBetSummary(html);
			if (betSummary == null) {
				log.info("获取未结算摘要信息失败,结果信息：{}", html);
				Thread.sleep(SLEEP);
				return getBetSummary(agent, game, date, period, ++index[0]);
			}
			return betSummary;
		} catch (Exception e) {
			log.error("未结算摘要信息失败,错误信息=" + html, e);
			Thread.sleep(2 * SLEEP);
			return getBetSummary(agent, game, date, period, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], ""));
		}
	}

	/**
	 * 获取投注详情
	 *
	 * @param agent       盘口代理信息
	 * @param summaryInfo 摘要信息
	 * @param game        盘口游戏id
	 * @param period      期数
	 * @param pageIndex   页数
	 * @param date        时间
	 * @param index       循环次数
	 * @return
	 */
	public static String getBetDetail(AgentCrawler agent, SummaryInfo summaryInfo, String game, Object period, Integer pageIndex,
									  String date, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(8);
		join.put("userID", summaryInfo.getMemberId());
		join.put("gameCode", game);
		join.put("startDate", date);
		join.put("endDate", date);
		join.put("page", pageIndex);
		join.put("isSettle", 0);
		join.put("seqNumber", period);
		join.put("isZBB", 0);
		join.put("ticket", agent.getTicket());
		join.put("uid", agent.getMemberno());
		String url = agent.getProjectHost().concat("game/report/queryHuizongDetails.do");
		String html = null;
		try {
			html = HttpClientUtil.findInstance().postHtml(agent.getHcConfig().url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.debug("获取投注详情失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetDetail(agent, summaryInfo, game, period, pageIndex, date);
			}
			return html;
		} catch (Exception e) {
			log.error("获取投注详情信息失败,错误信息=" + html, e);
			Thread.sleep(2 * SLEEP);
			return getBetDetail(agent, summaryInfo, game, period, pageIndex, date);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], ""));
		}
	}


	/**
	 * 未结算摘要
	 *
	 * @param html 未结算摘要页面
	 * @return 未结算摘要
	 */
	private static Map<String, SummaryInfo> getBetSummary(String html) {
		JSONObject jsonObject = JSONObject.parseObject(html);
		JSONArray infoObj = jsonObject.getJSONArray("dataObject");

		Map<String, SummaryInfo> member = new HashMap<>(infoObj.size());
		for (int i = 0; i < infoObj.size(); i++) {
			JSONObject info = (JSONObject) infoObj.get(i);
			SummaryInfo summaryInfo = new SummaryInfo();
			String memberName = info.getJSONObject("userHY").getString("loginName");
			summaryInfo.setMemberId(info.getJSONObject("userHY").getInteger("id"));
			summaryInfo.setAccount(memberName);
			summaryInfo.setBetCount(info.getInteger("count"));
			summaryInfo.setBetAmount(NumberTool.intValueT(info.getDouble("amount")) / 1000);
			member.put(memberName, summaryInfo);
		}
		return member;
	}

	/**
	 * @param betDetail    投注详情
	 * @param gameCode     游戏编码
	 * @param html         投注详情页面
	 * @param subOddNumber 上一次请求的主单号
	 * @return
	 */
	public static DetailBox analyzeDetail(DetailBox betDetail, GameUtil.Code gameCode, String html, String subOddNumber) {
		JSONObject jsonObject = JSONObject.parseObject(html);
		JSONObject result = jsonObject.getJSONObject("dataObject");
		if (result.getJSONArray("dataObject").isEmpty()) {
			return null;
		}

		if (betDetail == null) {
			betDetail = new DetailBox();
			betDetail.pages(result.getInteger("totalPages"), result.getInteger("totalCount"));
		}
		List<DetailInfo> details = betDetail.details();

		String maxOddNumber = betDetail.getMaxOddNumber(subOddNumber);
		int compare = maxOddNumber.compareTo(subOddNumber);
		JSONArray betArr = result.getJSONArray("dataObject");
		for (int i = 0; i < betArr.size(); i++) {
			JSONArray item = betArr.getJSONArray(i);
			String oddNumber = item.getString(0);
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
			String betInfo = item.getString(4).concat("-").concat(item.getString(6));
			//投注积分
			String funds = item.getString(10);
			Map<String, String> betInfoCode = NewWsMemberTool.getNewCcBallCode(gameCode);
			String betItem = betInfoCode.get(betInfo);
			details.add(new DetailInfo(betItem, NumberTool.getDouble(funds)));
		}
		betDetail.maxOddNumber(maxOddNumber);
		return betDetail;
	}

	/**
	 * 获取会员可用线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  线路页面
	 * @return 线路数组
	 */
	public static String[] getAgentRoute(HttpClientConfig httpConfig, String routeHtml) {
		List<String> hrefs = new ArrayList<>();
		Document document = Jsoup.parse(routeHtml);
		Elements as = document.getElementsByClass("datalist-contain datalist").select("a");
		for (int i = 8; i < 13; i++) {
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


}
