package com.ibm.common.utils.http.tools.agent;

import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.tools.member.SgWinMemberTool;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.DetailInfo;
import com.ibm.follow.servlet.client.core.job.bet.Pages;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.lang3.StringUtils;
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
 * @Description:
 * @Author: wwj
 * @Date: 2020/5/14 15:54
 * @Version: v1.0
 */
public class NewMoaAgentTool {
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
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]].concat("100")));

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
			return getVerifyCode(httpConfig, projectHost, null, type, ++index[0]);
		}
		if (StringTool.isEmpty(html)) {
			log.error("破解验证码失败");
			Thread.sleep(SLEEP);
			return getVerifyCode(httpConfig, projectHost, null, type, ++index[0]);
		}
		html = html.replace("\"", "");
		if (!html.matches("^\\d{4}$")) {
			return getVerifyCode(httpConfig, projectHost, null, type, ++index[0]);
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
		join.put("login_page_name", "LG_A");
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
	 * 获取会员列表
	 *
	 * @param agent 代理信息
	 * @param index 循环次数
	 * @return html
	 */
	public static String getMemberList(AgentCrawler agent, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		HttpClientConfig httpClient = agent.getHcConfig();

		String url = agent.getProjectHost().concat("userparam/user_manage.do?leader_type=80&user_type=90&type=0");

		try {
			String html = HttpClientUtil.findInstance().getHtml(httpClient.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取会员列表信息失败");
				Thread.sleep(SLEEP);
				return getMemberList(agent, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取会员列表信息失败", e);
			Thread.sleep(SLEEP);
			return getMemberList(agent, ++index[0]);
		}
	}

	/**
	 * 获取会员列表
	 *
	 * @param agent   代理信息
	 * @param curPage 当前页
	 * @param token   token
	 * @param forward forward
	 * @return html
	 */
	public static String getMemberList(AgentCrawler agent, int curPage, int pageCount, int totalCount, String token, String forward, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		HttpClientConfig httpClient = agent.getHcConfig();
		Map<String, Object> params = new HashMap<>(20);
		params.put("hint_type", "clew");
		params.put("seach_type", "");
		params.put("oper_type", "");
		params.put("user_id", "");
		params.put("leader_id", "");
		params.put("level_type", "");
		params.put("user_type", "90");
		params.put("leader_type", "80");
		params.put("user_state", "");
		params.put("forward", forward);
		params.put("token", token);
		params.put("seach_open", "0");
		params.put("s_user_state", "0");
		params.put("s_leader_type", "-1");
		params.put("seach_field", "USER_ID");
		params.put("seach_value", "");
		params.put("curPage", curPage);
		params.put("totalPage", pageCount);
		params.put("totalCount", totalCount);
		params.put("pageMaxCount", "");

		String url = agent.getProjectHost().concat("userparam/user_manage.do");

		try {
			String html = HttpClientUtil.findInstance().postHtml(httpClient.url(url).map(params));
			if (StringTool.isEmpty(html)) {
				log.error("获取会员列表信息失败");
				Thread.sleep(SLEEP);
				return getMemberList(agent, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取会员列表信息失败", e);
			Thread.sleep(SLEEP);
			return getMemberList(agent, ++index[0]);
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

		Map<String, Object> join = new HashMap<>(16);
		join.put("q_game_type", game);
		join.put("q_pro_type", "-1");
		join.put("q_by_dateOrIssue", "BY_ISSUE");
		join.put("q_issue_id", period);
		join.put("q_start_date", date);
		join.put("q_end_date", date);
		join.put("q_report_type", "U");
		join.put("q_is_settled", "0");
		join.put("q_user_id", "");
		join.put("gameType", game);
		join.put("proType", "-1");
		join.put("query_cond_type", "0");
		join.put("issueId", "-1");
		join.put("q_FT", period);
		join.put("contain_today_check", "1");
		join.put("contain_today", "1");
		join.put("ReportType", "U");
		join.put("t_Balance", "0");

		String url = agent.getProjectHost().concat("report/report_001_user.do");
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
	 * "report/order_detail.do?v=253&is_report=1&is_ext=0&user_id=eded1516&game_type=B&pro_type=-1&
	 * issue_id=746753&start_date=&end_date=&is_settled=0&report_type=U&by_dateOrIssue=BY_ISSUE&contain_today=1
	 * @param agent       盘口代理信息
	 * @param summaryInfo 摘要信息
	 * @param game        盘口游戏id
	 * @param period      期数
	 * @param index       循环次数
	 * @return html
	 */
	public static String getBetDetail(AgentCrawler agent, SummaryInfo summaryInfo, String game, Object period, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = agent.getProjectHost().concat("report/order_detail.do?v=253&is_report=1&is_ext=0&user_id=%s&game_type=%s&pro_type=-1&issue_id=%s" +
				"&start_date=&end_date=&is_settled=0&report_type=U&by_dateOrIssue=BY_ISSUE&contain_today=1");
		url = String.format(url,summaryInfo.getAccount(),game,period);
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.debug("获取投注详情失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetDetail(agent, summaryInfo, game, period,++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取投注详情信息失败,错误信息=" + html, e);
			Thread.sleep(2 * SLEEP);
			return getBetDetail(agent, summaryInfo, game, period,++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], ""));
		}
	}


	/**
	 * 获取投注详情
	 * @param agent       盘口代理信息
	 * @param summaryInfo 摘要信息
	 * @param game        盘口游戏id
	 * @param period      期数
	 * @param index       循环次数
	 * @return html
	 */
	public static String getBetDetailByPage(AgentCrawler agent, SummaryInfo summaryInfo, String game, Object period, Pages pages,
									   int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String,Object> join = new HashMap<>();
		join.put("is_ext",0);
		join.put("user_id",summaryInfo.getAccount());
		join.put("is_report","1");
		join.put("game_type",game);
		join.put("issue_id",period);
		join.put("pro_type","-1");
		join.put("start_date","");
		join.put("end_date","");
		join.put("is_settled",0);
		join.put("report_type","U");
		join.put("by_dateOrIssue","BY_ISSUE");
		join.put("filter_date","");
		join.put("contain_today",1);
		join.put("curPage",pages.nextPage());
		join.put("pageCount",50);
		join.put("totalPage",pages.getPageCount());
		join.put("totalCount",pages.getTotalCount());
		join.put("pageMaxCount",30);
		join.put("limitMaxCount",0);
		String url = agent.getProjectHost().concat("report/order_detail.do");
		url = String.format(url,summaryInfo.getAccount(),game,period);
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.debug("获取投注详情失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetDetailByPage(agent, summaryInfo, game, period, pages,++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取投注详情信息失败,错误信息=" + html, e);
			Thread.sleep(2 * SLEEP);
			return getBetDetailByPage(agent, summaryInfo, game, period, pages, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], ""));
		}
	}


	/**
	 * 未结算摘要
	 *
	 * @param html 未结算摘要页面
	 * @return 未结算摘要
	 */
	private static Map<String, SummaryInfo> getBetSummary(String html) {
		Document document = Jsoup.parse(html);
		Elements trs = document.getElementsByClass("t_list_tr_0");
		trs.remove(trs.last());

		Map<String, SummaryInfo> member = new HashMap<>();
		for (Element tr :trs){
			SummaryInfo summaryInfo = new SummaryInfo();
			String memberName = tr.child(0).text().split(" ")[1];
			summaryInfo.setMemberId(memberName);
			summaryInfo.setAccount(memberName);
			summaryInfo.setBetCount(tr.child(2).text());
			summaryInfo.setBetAmount(NumberTool.intValueT(tr.child(3).text()) / 1000);
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
		Document document = Jsoup.parse(html);
		Elements trs = document.getElementsByClass("t_list_tr_0");
		if (trs.isEmpty()) {
			return null;
		}

		if (betDetail == null) {
			betDetail = new DetailBox();
			String total = document.getElementById("totalCount").val();
			String page = document.getElementById("totalPage").val();
			betDetail.pages(total, page);
		}
		List<DetailInfo> details = betDetail.details();
		// TODO 订单号问题
		String maxOddNumber = betDetail.getMaxOddNumber(subOddNumber);
		int compare = maxOddNumber.compareTo(subOddNumber);
		//放入投注项
		for (Element tr : trs) {
			//注单号
			String oddNumber = tr.child(0).text().split("#")[0];
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
			String[] arr = tr.getElementsByClass("Font_B").first().text().split("『 | 』");

			String betItem = BallCodeTool.getNewMoaBallInfo(gameCode).get(arr[0].concat(arr[1]));

			//投注积分
			String funds = tr.child(8).text();
			details.add(new DetailInfo(betItem, NumberTool.getDouble(funds)));
		}
		betDetail.maxOddNumber(maxOddNumber);
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
