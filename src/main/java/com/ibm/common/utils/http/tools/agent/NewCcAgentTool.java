package com.ibm.common.utils.http.tools.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.member.NewCcMemberTool;
import com.ibm.common.utils.http.tools.member.SgWinMemberTool;
import com.ibm.common.utils.http.utils.entity.AgentCrawler;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.DetailInfo;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
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
 * @Description: 新CC盘口代理工具类
 * @Author: wwj
 * @Date: 2020/5/14 15:54
 * @Version: v1.0
 */
public class NewCcAgentTool {
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
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]].concat("/admin")));

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
		//获取action;
		loginInfoMap.put("loginSrc1", routes[index[0]]);
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
	 * @param agentAccount  盘口代理账号
	 * @param agentPassword 盘口代理密码
	 * @param verifyCode     验证码
	 * @param projectHost    线路地址
	 * @param index          循环次数
	 * @return 盘口主页面href
	 */
	public static String getLogin(HttpClientConfig httpConfig, String agentAccount, String agentPassword,
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

		join.put("loginName", agentAccount);
		join.put("loginPwd", agentPassword);
		join.put("ValidateCode", verifyCode);

		String url = projectHost.concat("/admin");
		String loginInfo;
		try {
			//登录信息
			loginInfo = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(loginInfo)) {
				log.error("获取登陆信息失败");
				Thread.sleep(2 * SLEEP);
				return getLogin(httpConfig, agentAccount, agentPassword, verifyCode, projectHost, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			Thread.sleep(2 * SLEEP);
			return getLogin(httpConfig, agentAccount, agentPassword, verifyCode, projectHost, ++index[0]);
		}

		return loginInfo;
	}

	/**
	 * 获取会员列表
	 *
	 * @param agent 代理信息
	 * @param index 循环次数
	 * @return
	 */
	public static JSONArray getMemberList(AgentCrawler agent,int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = agent.getProjectHost().concat("admin/member?MT=5?page=1&limit=100");
		String html ;
		try {
			html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取会员列表信息失败");
				Thread.sleep(SLEEP);
				return getMemberList(agent, ++index[0]);
			}
			return getMemberInfo(html);
		} catch (Exception e) {
			log.error("获取会员列表信息失败", e);
			Thread.sleep(SLEEP);
			return getMemberList(agent, ++index[0]);
		}
	}


	/**
	 * 获取期数阶段码
	 * Request URL: http://c21752.cac788.com/admin/report/get_game_way_list/400
	 * @param agent 代理信息
	 * @param game  盘口游戏id
	 * @param index 循环次数
	 * @return
	 */
	public static JSONObject getPhaseoption(AgentCrawler agent, String game, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("gameno", game);
		String url = agent.getProjectHost().concat("admin/report/get_game_way_list/").concat(game);
		String html = null;
		try {
			html = HttpClientUtil.findInstance().postHtml(agent.getHcConfig().url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取期数阶段码信息失败");
				Thread.sleep(SLEEP);
				return getPhaseoption(agent, game, ++index[0]);
			}
			if(StringTool.isContains(html,"<script>top.location='/admin'</script>")){
				log.error("获取期数阶段码信息失败,错误的信息="+html);
				agent.setProjectHost(null);
				return null;
			}
			// 您的账号已经在另一个地方登陆，当前被退出
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取期数阶段码信息失败,错误的信息="+html, e);
			Thread.sleep(SLEEP);
			return getPhaseoption(agent, game, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0],""));
		}
	}

	/**
	 * 获取未结算摘要信息
	 * Request URL: http://c21752.cac788.com/admin/report/report_b1?
	 * gameNO=400&gID=&t_FT=LID&t_LID=1543931&BeginDate=&EndDate=&timeQD1=&timeQD2=&ReportType=B&t_Balance=0
	 * @param agent       代理信息
	 * @param game        盘口游戏id
	 * @param phaseoption 期数阶段码
	 * @param date        时间
	 * @return 未结算摘要信息
	 */
	public static Map<String, SummaryInfo> getBetSummary(AgentCrawler agent,
														 String game, String phaseoption, String date, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url=agent.getProjectHost().concat("admin/report/report_b1?");
		String parametersFormat ="gameNO=%s&gID=&t_FT=LID&t_LID=%s&BeginDate=%s&EndDate=%s&timeQD1=&timeQD2&ReportType=B&t_Balance=0";
		String parameters = String.format(parametersFormat, game, phaseoption, date,date);
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.debug("获取未结算摘要信息失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetSummary(agent, game, phaseoption, date, ++index[0]);
			}
			if(StringTool.contains(html,"<script>top.location.href='/'</script>","抱歉，處理您的請求時出錯")){
				log.error("未结算摘要信息失败,错误信息="+html);
				return null;
			}
//			if (!StringTool.isContains(html, "報表查詢")) {
//				log.debug("获取未结算摘要信息失败,结果信息="+html);
//				Thread.sleep(SLEEP);
//				return getBetSummary(agent, game, phaseoption, date, ++index[0]);
//			}
			// <script>alert('您的账号已经在另一个地方登陆，当前被退出！')</script><script>top.location='/admin'</script>
			Map<String, SummaryInfo> betSummary = getBetSummary(html);
			if (betSummary == null) {
				log.info("获取未结算摘要信息失败,结果信息：{}", html);
				Thread.sleep(SLEEP);
				return getBetSummary(agent, game, phaseoption, date, ++index[0]);
			}
			return betSummary;
		} catch (Exception e) {
			log.error("未结算摘要信息失败,错误信息="+html, e);
			Thread.sleep(2 * SLEEP);
			return getBetSummary(agent, game, phaseoption, date, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], ""));
		}
	}
	/**
	 * 获取投注详情
	 *  http://c21752.cac788.com/admin/Reportmx/reportmx2_t_six?count=38&PartyID=9255&Org_Type=6&gameNO=400&t_LID=1544113&t_Balance=0&page=1
	 * @param agent     盘口代理信息
	 * @param summaryInfo   摘要信息
	 * @param game          盘口游戏id
	 * @param pageIndex     页数
	 * @param phaseoption   期数阶段码
	 * @param date          时间
	 * @param index         循环次数
	 * @return
	 */
	public static String getBetDetail(AgentCrawler agent, SummaryInfo summaryInfo, String game,Integer pageIndex,
									  String phaseoption, String date, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url=agent.getProjectHost().concat("admin/reportmx/reportmx2_t_six?");
		String parametersFormat;
		String parameters;
		if(pageIndex==null){
			parametersFormat ="PartyID=%s&Org_Type=6&gameNO=%s&t_LID=%s&t_Balance=0&ReportType=B";
			parameters = String.format(parametersFormat, summaryInfo.getMemberId(),game,phaseoption);
		}else{
			parametersFormat="PartyID=%s&Org_Type=6&gameNO=%s&t_LID=%s&t_Balance=0&ReportType=B&page=%s";
			parameters =String.format(parametersFormat, summaryInfo.getMemberId(),game,phaseoption,pageIndex);
		}
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.debug("获取投注详情失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetDetail(agent,summaryInfo, game,pageIndex, phaseoption, date, ++index[0]);
			}
			if (!StringTool.isContains(html, "明細")) {
				log.debug("获取未结算详情信息失败,结果信息=" + html);
				Thread.sleep(SLEEP);
				return getBetDetail(agent,summaryInfo, game,pageIndex, phaseoption, date, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取投注详情信息失败,错误信息="+html, e);
			Thread.sleep(2 * SLEEP);
			return getBetDetail(agent,summaryInfo, game,pageIndex, phaseoption, date, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], ""));
		}
	}

	// TODO 功能函数

	/**
	 *
	 * @param betDetail     投注详情
	 * @param gameCode      游戏编码
	 * @param html          投注详情页面
	 * @param subOddNumber  上一次请求的主单号
	 * @return
	 */
	public static DetailBox analyzeDetail(DetailBox betDetail, GameUtil.Code gameCode, String html, String subOddNumber) {
		Document document = Jsoup.parse(html);
		Elements trs=document.getElementsByClass("t_list_tr_0");
		if (trs.isEmpty()) {
			return null;
		}
		trs.remove(trs.last());

		if (betDetail == null) {
			betDetail = new DetailBox();
			Element pageElements = document.getElementById("pager");

			String total =StringUtils.substringBetween(pageElements.text(),"共 "," 條記錄");
			String page =StringUtils.substringBetween(pageElements.text(),"分頁：1/","頁 ");
			betDetail.pages(page, total);
		}
		List<DetailInfo> details = betDetail.details();

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
			String betInfo = tr.child(3).text().split("@")[0];
			//投注积分
			String funds = tr.child(4).text();
			Map<String, String> betInfoCode = NewCcMemberTool.getNewCcBallCode(gameCode);
			String betItem = betInfoCode.get(betInfo);
			details.add(new DetailInfo(betItem, NumberTool.getDouble(funds)));
		}
		betDetail.maxOddNumber(maxOddNumber);
		return betDetail;
	}


	/**
	 * 未结算摘要
	 *
	 * @param html 未结算摘要页面
	 * @return 未结算摘要
	 */
	private static Map<String, SummaryInfo> getBetSummary(String html) {
		Document document = Jsoup.parse(html);
		Elements trs=document.getElementsByClass("t_list mt3").select("tbody>tr");

		if(trs.isEmpty()){
			//没有结算数据，页面不为空
			if(StringTool.isContains(html,"報表查詢")){
				return new HashMap<>(2);
			}
			return null;
		}
		trs.remove(trs.first());
		trs.remove(trs.last());
		Map<String, SummaryInfo> member = new HashMap<>(trs.size() * 3 / 4 + 1);
		for(Element tr:trs){
			SummaryInfo summaryInfo = new SummaryInfo();
			summaryInfo.setAccount(tr.child(0).text());
			summaryInfo.setBetCount(tr.child(2).text());
			summaryInfo.setBetAmount(NumberTool.intValueT(tr.child(3).text())/1000);
			summaryInfo.setMemberId(StringUtils.substringBetween(tr.child(3).child(0).attr("onclick"),"PartyID=","&"));
			member.put(tr.child(0).text(), summaryInfo);
		}
		return member;
	}


	/**
	 * 获取会员信息
	 *
	 * @param html 会员信息列表
	 * @return 会员信息
	 */
	private static JSONArray getMemberInfo(String html) {
		Document document = Jsoup.parse(html);
		Elements trs = document.select("table#datalist>tbody>tr");
		JSONArray memberList = new JSONArray();
		JSONArray member;
		for (Element tr : trs) {
			member = new JSONArray();
			member.add(tr.child(4).text());
			member.add(StringTool.isContains(tr.child(0).html(), "USER_1.gif") ? "online" : "offline");
			memberList.add(member);
		}
		return memberList;
	}

	/**
	 * 获取代理可用线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  线路页面
	 * @return 线路数组
	 */
	public static String[] getAgentRoute(HttpClientConfig httpConfig, String routeHtml) {
		Document routeDocument = Jsoup.parse(routeHtml);

		Element tbody = routeDocument.selectFirst("tbody");
		//包括会员，代理，开奖网线路
		Elements trs = tbody.select("tr");

		List<String> hrefs = new ArrayList<>();

		for (Element tr : trs) {
			Elements tds = tr.select("td");
			if (StringTool.isContains(tr.html(), "代理")) {
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
		} else if (StringTool.contains(msg, "修改密码")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}

	/**
	 * 验证获取到的最新阶段码是否是本期的
	 * @param gameCode 游戏code
	 * @param period 期数
	 * @param grabPeriod 获取到的期数
	 * @return true false
	 */
	public static boolean checkPeriod(GameUtil.Code gameCode, String period, int grabPeriod) {
		int periodInt;
		switch (gameCode) {
			case GDKLC:
			case CQSSC:
			case XYFT:
				periodInt = NumberTool.getInteger(period.split("-")[1]);
				break;
			default:
				periodInt = NumberTool.getInteger(period);
				break;
		}
		return grabPeriod - periodInt != 0;
	}

}
