package com.ibm.common.utils.http.tools.agent;

import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.UNConfig;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.handicap.config.NewCcBallConfig;
import com.ibm.common.utils.handicap.config.NewCcHappyConfig;
import com.ibm.common.utils.handicap.config.NewCcNumberConfig;
import com.ibm.common.utils.http.tools.member.NewCcMemberTool;
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
public class UNAgentTool {
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
				code = NewCcMemberTool.getVerifyCode(httpConfig, handicapUrl, null);
				return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
			}
			routeHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl.concat("wb4/index")));

		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(SLEEP);
			return getSelectRoutePage(httpConfig, handicapUrl, code, handicapCaptcha, ++index[0]);
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

		httpConfig.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3\n");

		try {
			//获取登录页面
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]].concat("rov2")));

			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "UN")) {
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
		join.put("login_0521_type", "2");
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
	 * Request URL: https://khwtul.ppltz.com/
	 * 加载代理主页
	 *
	 * @param httpConfig
	 */
	public static void toMain(HttpClientConfig httpConfig, String projectHost) throws InterruptedException {
		httpConfig.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
		try {
			//登录信息
			HttpClientUtil.findInstance().postHtml(httpConfig.url(projectHost.concat("wb2/dttj001/daiLiToMain")));
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
	public static JSONObject getMemberList(AgentCrawler agent, Object page, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		HttpClientConfig httpClient = agent.getHcConfig();
		httpClient.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpClient.setHeader("Content-Type", "application/json;charset=utf-8");
		String url = agent.getProjectHost().concat("wb2/hy0001hy/s001s");
		String stringEntity = "{\"targetpage\":\"%s\",\"state\":null,\"username\":\"\",\"utypes\":\"6\",\"sjUsername\":\"%s\",\"user_ip\":\"\",\"opType\":\"0\",\"desc_skxy\":\"0\",\"reback_tag\":\"0\",\"is_system\":\"1\"}";
		Map<String, Object> join = new HashMap<>(1);
		join.put("$ENTITY_STRING$", String.format(stringEntity, page, agent.getAccountInfo().getAccount()));
		try {
			String html = HttpClientUtil.findInstance().postHtml(httpClient.url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取会员列表信息失败");
				Thread.sleep(SLEEP);
				return getMemberList(agent, page, ++index[0]);
			}
			// com.alibaba.fastjson.JSONArray cannot be cast to com.alibaba.fastjson.JSONObject
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取会员列表信息失败", e);
			Thread.sleep(SLEEP);
			return getMemberList(agent, page, ++index[0]);
		}
	}

	/**
	 * 获取未结算摘要信息
	 * https://khwtul.ppltz.com/wb2/b1001/m001?
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
		String url = agent.getProjectHost().concat("wb2/b1001/m001?");
		String parametersFormat = "gid=%s&iss=%s&status=0&startTime=%s&endTime=%s&ziOrsj=0&uid=&uType=&orderType=&t01=%s&userName=";
		String parameters = String.format(parametersFormat, game, period, date, date, System.currentTimeMillis());
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.debug("获取未结算摘要信息失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetSummary(agent, game, date, period, ++index[0]);
			}
//			if (StringTool.contains(html, "<script>top.location.href='/'</script>", "抱歉，處理您的請求時出錯")) {
//				log.error("未结算摘要信息失败,错误信息=" + html);
//				return null;
//			}

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
	 * /wb2/b1001/m003?gid=BJC&iss=745523&status=0&startTime=2020-06-03&endTime=2020-06-03&uid=ee5566&orderDate=2020-06-03&targetpage1=1&ziOrsj=0&gameId_old=BJC&uType=&orderType=&recordCount=undefined&hyName=&timeSep=1591179432974
	 *
	 * @param agent       盘口代理信息
	 * @param summaryInfo 摘要信息
	 * @param game        盘口游戏id
	 * @param pageIndex   页数
	 * @param date        时间
	 * @param index       循环次数
	 * @return
	 */
	public static String getBetDetail(AgentCrawler agent, SummaryInfo summaryInfo, String game, Integer pageIndex,Object totalCount,
									  String date, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = agent.getProjectHost().concat("wb2/b1001/m003?");
		String parametersFormat = "gid=%s&iss=%s&status=0&startTime=%s&endTime=%s&uid=%s&orderDate=%s&targetpage1=%s" +
				"&ziOrsj=0&gameId_old=%s&uType=&orderType=&recordCount=%s&hyName=&timeSep=%s";
		String parameters = String.format(parametersFormat, game, summaryInfo.getPeriod(), date, date, summaryInfo.getAccount(), date,
				pageIndex, game,totalCount, System.currentTimeMillis());

		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(agent.getHcConfig().url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.debug("获取投注详情失败,结果信息为空");
				Thread.sleep(SLEEP);
				return getBetDetail(agent, summaryInfo, game, pageIndex,totalCount, date);
			}

			return html;
		} catch (Exception e) {
			log.error("获取投注详情信息失败,错误信息=" + html, e);
			Thread.sleep(2 * SLEEP);
			return getBetDetail(agent, summaryInfo, game, pageIndex,totalCount, date);
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
		Elements trs = document.getElementById("deliveryReport_tbody").select("tr");

		if (trs.isEmpty()) {
			//没有结算数据，页面不为空
			if (StringTool.isContains(html, "交收报表")) {
				return new HashMap<>(2);
			}
			return null;
		}

		trs.remove(trs.first());
		trs.remove(trs.last());
		Map<String, SummaryInfo> member = new HashMap<>(trs.size() * 3 / 4 + 1);
		for (Element tr : trs) {
			SummaryInfo summaryInfo = new SummaryInfo();
			summaryInfo.setAccount(tr.child(1).text());
			summaryInfo.setBetCount(tr.child(3).text());
			summaryInfo.setBetAmount(NumberTool.intValueT(tr.child(4).text()) / 1000);
			member.put(tr.child(1).text(), summaryInfo);
		}
		return member;
	}

	/**
	 * @param betDetail    投注详情
	 * @param html         投注详情页面
	 * @param subOddNumber 上一次请求的注单号
	 * @return
	 */
	public static DetailBox analyzeDetail(DetailBox betDetail, String html, String subOddNumber) {
		Document document = Jsoup.parse(html);
		Elements trs = document.getElementsByClass("clear_table comtable tabletrhover").first().select("tr");
		if (trs.isEmpty()) {
			return null;
		}
		trs.remove(trs.first());
		trs.remove(trs.last());
		boolean flag=false;
		if (betDetail == null) {
			betDetail = new DetailBox();

			String total = document.getElementById("recordCount").val();
			String page = document.getElementById("total_strong").text().split(" ")[1];
			betDetail.pages(page, total);
			flag=true;
		}
		List<DetailInfo> details = betDetail.details();
		String maxOddNumber = betDetail.getMaxOddNumber(subOddNumber);

		//放入投注项
		for (Element tr : trs) {
			//注单号
			String oddNumber = tr.child(2).text();
			// 注单号一样,跳出
			if(oddNumber.equalsIgnoreCase(maxOddNumber)){
				betDetail.hasNext(false);
				break;
			}
			//投注项
			String[] betItems = tr.child(6).text().split("[ @]");

			if(StringTool.contains(betItems[0],"冠","亚")){
				betItems[0] = UNConfig.BET_INFO_CODE.get(betItems[0]);
			}
			if(NumberTool.isInteger(betItems[1])){
				betItems[1] = NumberTool.getInteger(betItems[1])+"";
			}

			String betItem = betItems[0].concat("|").concat(betItems[1]);
			//投注积分
			String funds = tr.child(8).text();
			details.add(new DetailInfo(betItem, NumberTool.getDouble(funds)));
		}
		if(flag){
			// 第一条数据为最大注单
			betDetail.maxOddNumber(trs.get(0).child(2).text());
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
		Elements as = document.select("a");
		List<String> hrefs = new ArrayList<>();
		for (Element el : as) {
			if (StringTool.contains(el.text(), "管理线路")) {
				String on = el.attr("onclick");
				String url = StringUtils.substringBetween(on, "goToUrlDL('", "/");
				hrefs.add("https://".concat(url));
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
		log.error("NewCc盘口会员登陆异常，异常的登陆结果页面为：" + msg);
		if (StringTool.isContains(msg, "验证码")) {
			return HcCodeEnum.IBS_403_VERIFY_CODE;
		} else if (StringTool.contains(msg, "-1", "-3", "用户名在系统中不存在", "密码不正确")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "-4", "-6")) {
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
