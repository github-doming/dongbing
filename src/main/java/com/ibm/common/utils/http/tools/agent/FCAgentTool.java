package com.ibm.common.utils.http.tools.agent;

import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.handicap.config.NewCcBallConfig;
import com.ibm.common.utils.handicap.config.NewCcHappyConfig;
import com.ibm.common.utils.handicap.config.NewCcNumberConfig;
import com.ibm.common.utils.http.tools.member.SgWinMemberTool;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.DetailInfo;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
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
public class FCAgentTool {
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
		Map<String,Object> join = new HashMap<>();
		join.put("q",handicapCaptcha);
		join.put("Submit","%E6%9F%A5+%E8%AF%A2");
		String routeHtml;
		try {


			//获取线路页面
			routeHtml = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl).map(join));
			if (StringTool.isEmpty(routeHtml)|| !StringTool.isContains(routeHtml, "線路")) {
				log.error("获取线路页面失败:{}", routeHtml);
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(SLEEP);
			return getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
		//代理可用线路页面
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
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "Welcome")) {
				log.error("打开登录页面失败", html);
				Thread.sleep(2 * SLEEP);
				return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
			}
			loginInfoMap = new HashMap<>(1);
			loginInfoMap.put("loginSrc", routes[index[0]]);

		} catch (Exception e) {
			log.error("打开登录页面失败", e);
			Thread.sleep(2 * SLEEP);
			return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
		}
		return loginInfoMap;
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
	 */
	public static String getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
								  String projectHost, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		Map<String, Object> join = new HashMap<>(2);

		join.put("loginName", memberAccount);
		join.put("loginPwd", memberPassword);
		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("logindo.aspx")).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				Thread.sleep(2 * SLEEP);
				return getLogin(httpConfig, memberAccount, memberPassword,  projectHost, ++index[0]);
			}
			if (StringTool.contains(loginInfo, "帳號或者密碼錯誤", "帳號已經停用", "重置密碼")) {
				return loginInfo;
			}
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
		}

		return loginInfo;
	}

	/**
	 * 加载代理主页
	 *
	 * @param httpConfig
	 */
	public static void toMain(HttpClientConfig httpConfig, String projectHost) throws InterruptedException {
		try {
			//登录信息
			HttpClientUtil.findInstance().getHtml(httpConfig.url(projectHost.concat("index.php?s=/Home/Home/main.aspx")));
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return;
		}
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
	public static String getMemberList(AgentCrawler agent, Object page, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		HttpClientConfig httpClient = agent.getHcConfig();
		String url = agent.getProjectHost().concat("Account_List_hy.aspx?MT=5&UP_ID=0&VIP_Estate=1&Compositor=1&Ascending=1&p="+page);

		try {
			String html = HttpClientUtil.findInstance().getHtml(httpClient.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取会员列表信息失败");
				Thread.sleep(SLEEP);
				return getMemberList(agent, page, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取会员列表信息失败", e);
			Thread.sleep(SLEEP);
			return getMemberList(agent, page, ++index[0]);
		}
	}

	/**
	 * 获取未结算摘要信息
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
		String url = agent.getProjectHost().concat("Report_B1_bak.aspx?");
		String parametersFormat = "t_LT=%s&t_FT=1&t_LID=%s&BeginDate=%s&EndDate=%s&ReportType=B&t_Balance=2";
		String parameters = String.format(parametersFormat, game, period, date, date);
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.debug("获取未结算摘要信息失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetSummary(agent, game, date, period, ++index[0]);
			}
			if(StringTool.contains(html,"重新登陸")){
				return null;
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
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], ""));
		}
	}

	/**
	 * 获取投注详情
	 *
	 * @param agent       盘口代理信息
	 * @param summaryInfo 摘要信息
	 * @param game 游戏Id
	 * @param pageIndex   页数
	 * @param date        时间
	 * @param index       循环次数
	 * @return
	 */
	public static String getBetDetail(AgentCrawler agent, SummaryInfo summaryInfo,String game , Integer pageIndex,
									  String date, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = agent.getProjectHost().concat("Report_list.aspx?");
		String parametersFormat = "uid=%s&&t_LT=%s&t_FT=2&BeginDate=%s&EndDate=%s&ReportType=B&t_Balance=2&p=%s";
		String parameters = String.format(parametersFormat, summaryInfo.getMemberId(),game, date, date,pageIndex);

		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.debug("获取投注详情失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetDetail(agent, summaryInfo,game, pageIndex, date);
			}
			if (!StringTool.isContains(html, "註單明細")) {
				log.debug("获取未结算详情信息失败,结果信息=" + html);
				Thread.sleep(SLEEP);
				return getBetDetail(agent, summaryInfo,game,pageIndex, date);
			}
			return html;
		} catch (Exception e) {
			log.error("获取投注详情信息失败,错误信息=" + html, e);
			Thread.sleep(2 * SLEEP);
			return getBetDetail(agent, summaryInfo, game, pageIndex, date);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], ""));
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
		Elements trs = document.getElementById("sort_list").select("tr");

		if (trs.isEmpty()) {
			//没有结算数据，页面不为空
			if (StringTool.isContains(html, "交收报表")) {
				return new HashMap<>(2);
			}
			return null;
		}
		trs.remove(trs.last());
		trs.remove(trs.last());
		Map<String, SummaryInfo> member = new HashMap<>(trs.size() * 3 / 4 + 1);
		for (Element tr : trs) {
			SummaryInfo summaryInfo = new SummaryInfo();
			summaryInfo.setAccount(tr.child(1).text());
			summaryInfo.setBetCount(tr.child(3).text());
			summaryInfo.setBetAmount(NumberTool.intValueT(tr.child(4).text()) / 1000);
			String on = tr.selectFirst("a").attr("onclick");
			String uid = StringUtils.substringBetween(on,"uid=","&t");
			summaryInfo.setMemberId(uid);
			member.put(tr.child(1).text(), summaryInfo);
		}
		return member;
	}

	/**
	 * 解析投注明细
	 * @param betDetail    投注详情
	 * @param html         投注详情页面
	 * @param subOddNumber 上一次请求的注单号
	 * @return
	 */
	public static DetailBox analyzeDetail(DetailBox betDetail,  String html, String subOddNumber,Map<String, String> ballInfoCode) {
		Document document = Jsoup.parse(html);
		Elements trs = document.select("tbody>tr");
		if (trs.isEmpty()) {
			return null;
		}
		trs.remove(trs.first());
		boolean flag=false;
		if (betDetail == null) {
			betDetail = new DetailBox();
			// 共 6 條記錄 分頁：1/1頁 上一頁 『』 下一頁
			String pager = document.getElementById("pager").text();
			String page = StringUtils.substringBetween(pager,"分頁：1/","頁");
			String total = StringUtils.substringBetween(pager,"共 "," 條");
			betDetail.pages(page, total);
			flag=true;
//			subOddNumber="";
		}
		List<DetailInfo> details = betDetail.details();

		String maxOddNumber = betDetail.getMaxOddNumber(subOddNumber);

		//放入投注项
		for (Element tr : trs) {
			//注单号
			String oddNumber = tr.child(0).text().split("#")[0];
			// 注单号一样,跳出
			if(oddNumber.equalsIgnoreCase(maxOddNumber)){
				betDetail.hasNext(false);
				break;
			}
			String[] items = tr.child(3).text().split("[『』@]");
			//投注项
			String betItem = ballInfoCode.get(items[0].concat(items[1]));
			//投注积分
			String funds = tr.child(4).text();
			details.add(new DetailInfo(betItem, NumberTool.getDouble(funds)));
		}
		if(flag){
			// 第一条数据为最大注单
			betDetail.maxOddNumber(trs.get(0).child(0).text().split("#")[0]);
		}
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
		Document document = Jsoup.parse(routeHtml);
		Element trs = document.getElementById("form1");
		Elements as = trs.select("a");
		List<String> hrefs = new ArrayList<>();

		for (Element a : as) {
			String url = a.attr("href");
			if(url.contains("g")){
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
					.concat(System.currentTimeMillis()+"");
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
		log.error("FC盘口代理登陆异常，异常的登陆结果页面为：" + msg);
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
