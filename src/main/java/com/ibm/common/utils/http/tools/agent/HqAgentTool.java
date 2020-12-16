package com.ibm.common.utils.http.tools.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.job.bet.DetailBox;
import com.ibm.follow.servlet.client.core.job.bet.DetailInfo;
import com.ibm.follow.servlet.client.core.job.bet.SummaryInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: hq盘口代理工具类
 * @Author: zjj
 * @Date: 2019-09-10 09:55
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HqAgentTool {
	private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
	private static final long SLEEP = 1000L;

	protected static final Logger log = LogManager.getLogger(HqAgentTool.class);
	private static final Integer MAX_RECURSIVE_SIZE = 5;
	private static final String CRACK_URL = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

	// TODO 开启页面函数

	//  开启页面函数
	/**
	 * 打开页面获取代理href
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     url地址
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 获取代理登录href
	 */
	public static String getAgentHref(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		try {
			//获取导航页面
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl));
			if (StringTool.isEmpty(html)) {
				log.error("打开导航页面为空");
				return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, index[0]);
			}
			if (setCookie(httpConfig, handicapUrl, html)) {
				return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (html.contains("百度一下，你就知道")) {
				log.error("打开导航页面为错误");
				Thread.sleep(SLEEP);
				return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			String action = navigationHtml(html);
			if (StringTool.isEmpty(action)) {
				log.error("打开导航页面为空");
				Thread.sleep(SLEEP);
				return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			//验证码输入
			Map<String, Object> join = new HashMap<>(1);
			join.put("SafeCode", handicapCaptcha);
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(handicapUrl + action).map(join));

			String href = getAgentLoginHref(html);
			if (StringTool.isEmpty(href)) {
				log.error("打开角色选择页面失败" + html);
				Thread.sleep(SLEEP);
				return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			//只获取盘口代理href
			return href;
		} catch (Exception e) {
			log.error("打开导航页面失败", e);
			Thread.sleep(SLEEP);
			return getAgentHref(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}
	}

	/**
	 * 获取线路
	 *
	 * @param httpConfig  http请求配置类
	 * @param href        请求路径
	 * @param handicapUrl 盘口url
	 * @param index       循环次数
	 * @return 线路地址
	 */
	public static String[] getSelectRoutePage(HttpClientConfig httpConfig, String href, String handicapUrl,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		try {
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(handicapUrl + href));
			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "线路选择")) {
				log.error("打开选择线路界面失败" + html);
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
			}
			String url = handicapUrl.concat("/Url/GetUrlList?").concat(href.split("\\?")[1]).concat("&_=") + System
					.currentTimeMillis();

			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			String[] routes = getRoutes(httpConfig, html);

			if (ContainerTool.isEmpty(routes)) {
				log.error("获取线路失败");
				Thread.sleep(SLEEP);
				return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
			}
			return routes;
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			Thread.sleep(SLEEP);
			return getSelectRoutePage(httpConfig, href, handicapUrl, ++index[0]);
		}

	}

	/**
	 * 获取登录页面
	 *
	 * @param httpConfig      http请求配置类
	 * @param routes          线路
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 登录页面
	 */
	public static Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, String handicapCaptcha,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] >= MAX_RECURSIVE_SIZE) {
			index[1] = 0;
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return new HashMap<>(1);
		}
		Map<String, String> map = new HashMap<>(2);
		String url = "http://".concat(routes[index[0]])
				.concat("/Member/Login?_=" + System.currentTimeMillis() + "&token=" + handicapCaptcha);
		try {
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			if (StringTool.isEmpty(html)) {
				log.error("获取登录页面失败");
				Thread.sleep(SLEEP);
				return getLoginHtml(httpConfig, routes, handicapCaptcha, index[0], ++index[1]);
			}
			if (setCookie(httpConfig, routes[index[0]], html)) {
				return getLoginHtml(httpConfig, routes, handicapCaptcha, index[0], ++index[1]);
			}
			map.put("html", html);
			map.put("hostUrl", routes[index[0]]);
		} catch (Exception e) {
			log.error("获取登录页面失败", e);
			Thread.sleep(SLEEP);
			return getLoginHtml(httpConfig, routes, handicapCaptcha, ++index[0], index[1]);
		}
		return map;
	}

	/**
	 * 获取登录加密key
	 *
	 * @param httpConfig http请求配置类
	 * @param sessionId  参数sessionid
	 * @param hostUrl    主机地址
	 * @param index      循环次数
	 * @return 加密key
	 */
	public static JSONObject getEncryptKey(HttpClientConfig httpConfig, String sessionId, String hostUrl, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		try {
			String url = "http://".concat(hostUrl).concat(sessionId).concat("/Member/GK");

			String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url));
			if (ContainerTool.isEmpty(html)) {
				log.info("获取登录加密key失败");
				Thread.sleep(SLEEP);
				return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
			}
			JSONObject result = JSONObject.parseObject(html);
			if (ContainerTool.isEmpty(result) || result.getInteger("Status") - 1 != 0) {
				return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
			}
			return result.getJSONObject("Data");
		} catch (Exception e) {
			log.error("获取登录加密key失败", e);
			Thread.sleep(SLEEP);
			return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
		}
	}

	/**
	 * 登录盘口
	 *
	 * @param httpConfig      http请求配置类
	 * @param hostUrl         主机地址
	 * @param sessionId       参数sessionId
	 * @param handicapCaptcha 盘口验证码
	 * @param encryptKey      加密信息-vk
	 * @param logpk           参数logpk
	 * @param index           循环次数
	 * @return 登录结果
	 */
	public static String getLogin(HttpClientConfig httpConfig, String hostUrl, String sessionId, String handicapCaptcha,
			String encryptKey, String logpk, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = "http://".concat(hostUrl).concat(sessionId).concat("/Member/DoLogin");
		Map<String, Object> join = new HashMap<>(8);
		join.put("Token", handicapCaptcha);
		//TODO 验证码
		join.put("Captcha", "");
		join.put("info", encryptKey);
		join.put("pk", logpk);
		String html = null;
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.info("登录盘口失败");
				Thread.sleep(SLEEP);
				return getLogin(httpConfig, hostUrl, sessionId, handicapCaptcha, encryptKey, logpk, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("登录盘口失败", e);
			Thread.sleep(SLEEP);
			return getLogin(httpConfig, hostUrl, sessionId, handicapCaptcha, encryptKey, logpk, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], html));
		}
	}

	/**
	 * 获取index页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param index       循环次数
	 * @return index页面
	 */
	public static Map<String, Object> getIndex(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new HashMap<>(1);
		}
		try {
			String url = projectHost.concat("App/Index?_=") + System.currentTimeMillis();
			String indexHtml = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			if (StringTool.isEmpty(indexHtml)) {
				log.error("获取index页面失败");
				Thread.sleep(SLEEP);
				return getIndex(httpConfig, projectHost, ++index[0]);
			}
			if (setCookie(httpConfig, projectHost, indexHtml)) {
				return getIndex(httpConfig, projectHost, ++index[0]);
			}
			httpConfig.setHeader("Referer", url);

			Map<String, Object> map = new HashMap<>(4);
			map.put("version", StringUtils.substringBetween(indexHtml, "var VERSION = \"", "\";"));
			map.put("level", StringUtils.substringBetween(indexHtml, "var LEVEL = ", ";"));
			map.put("moneyType", StringUtils.substringBetween(indexHtml, "var MONEY_TYPE = ", ";"));
			map.put("sessionId", StringUtils.substringBetween(indexHtml, "var SESSIONID = \"", "\";"));

			return map;
		} catch (Exception e) {
			log.error("获取index页面失败", e);
			Thread.sleep(SLEEP);
			return getIndex(httpConfig, projectHost, ++index[0]);
		}
	}

	/**
	 * 获取会员列表信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @return 会员列表信息
	 */
	public static JSONArray getMemberList(HttpClientConfig httpConfig, String projectHost, Object moneyType,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		String url = projectHost.concat("Member/GetMemberList?");
		String parameters =
				"MemberLevel=1&MoneyType=" + moneyType + "&Status=-1&IsShowDirectMember=-1&Account=&lotteryId=4&_="
						+ System.currentTimeMillis();
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url.concat(parameters)));
			if (StringTool.isEmpty(html)||StringTool.isContains(html,"百度安全验证")) {
				log.error("获取会员列表信息失败");
				Thread.sleep(SLEEP);
				return getMemberList(httpConfig, projectHost, ++index[0]);
			}
			if(StringTool.contains(html,"登录超时，请重新登录","callback7")){
				log.error("获取会员列表信息失败,错误的信息="+html);
				return null;
			}
			return getMemberInfo(html);
		} catch (Exception e) {
			log.error("获取会员列表信息失败,错误的信息="+html, e);
			Thread.sleep(SLEEP);
			return getMemberList(httpConfig, projectHost, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], html));
		}
	}

	/**
	 * 获取未结算摘要信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param lotteryId   抓取的游戏
	 * @param period      抓取的期数
	 * @param day         抓取的天数
	 * @return 未结算摘要信息
	 */
	public static Map<String, Map<String, SummaryInfo>> getBetSummary(HttpClientConfig httpConfig, String projectHost,
			String lotteryId, Object period, String day, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost.concat("Report/GetSummary?");
		String parametersFormat = "lotteryId=%s&startDate=%s&endDate=%s&ReportAction=0&periodId=%s&moneyType=0&reportType=0&SettleStatus=0&Action=summary&_=%d";
		String parameters = String.format(parametersFormat, lotteryId, day, day, period, System.currentTimeMillis());
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算摘要信息失败");
				Thread.sleep(SLEEP);
				return getBetSummary(httpConfig, projectHost, lotteryId, period, day, ++index[0]);
			}
            Map<String, Map<String, SummaryInfo>> betSummary = betSummary(html);
			if (betSummary == null) {
				Thread.sleep(SLEEP);
				return getBetSummary(httpConfig, projectHost, lotteryId, period, day, ++index[0]);
			}
			return betSummary;

		} catch (Exception e) {
			log.error("获取未结算摘要信息失败", e);
			Thread.sleep(SLEEP);
			return getBetSummary(httpConfig, projectHost, lotteryId, period, day, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], html));
		}
	}

    /**
     * 获取代理或者子代理下的会员或代理信息
     * @param httpConfig  http请求配置类
     * @param projectHost 主机地址
     * @param lotteryId   抓取的游戏
     * @param period      抓取的期数
     * @param day         抓取的天数
     * @param memberId    会员id
     * @return
     */
    public static Map<String, Map<String, SummaryInfo>> getBetSummaryById(HttpClientConfig httpConfig, String projectHost,
         String lotteryId, Object period, String day, Object memberId,int... index) throws InterruptedException {
        if (index.length == 0) {
            index = new int[1];
        }
        if (index[0] > MAX_RECURSIVE_SIZE) {
            return null;
        }
        String url = projectHost.concat("Report/GetSummary?");
        String parametersFormat = "startDate=%s&endDate=%s&PreStartDate=%s&PreEndDate=%s&reportType=0&lotteryId=%s&PreLotteryId=%s&periodId=%s&SettleStatus=0&moneyType=0&ReportAction=0&Action=summary&parentMemberId=%s&IsDirectlyMember=1&IsSellBet=0&_=%d";
        String parameters = String.format(parametersFormat,day, day,day,day,lotteryId,lotteryId, period,memberId, System.currentTimeMillis());
        String html = null;
        try {
            html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url.concat(parameters)));
            if (StringTool.isEmpty(html)) {
                log.error("获取未结算摘要信息失败");
                Thread.sleep(SLEEP);
                return getBetSummaryById(httpConfig, projectHost, lotteryId, period, day,memberId, ++index[0]);
            }
            Map<String, Map<String, SummaryInfo>> betSummary = betSummary(html);
            if (betSummary == null) {
                Thread.sleep(SLEEP);
                return getBetSummaryById(httpConfig, projectHost, lotteryId, period, day,memberId,  ++index[0]);
            }
            return betSummary;

        } catch (Exception e) {
            log.error("获取未结算摘要信息失败", e);
            Thread.sleep(SLEEP);
            return getBetSummaryById(httpConfig, projectHost, lotteryId, period, day,memberId,  ++index[0]);
        } finally {
            log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], html));
        }
    }

	/**
	 * 获取投注详情
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param lotteryId   抓取的游戏
	 * @param day         抓取的天
	 * @param period      期数
	 * @param memberId    会员ID
	 * @return 投注详情
	 */
	public static String getBetDetail(HttpClientConfig httpConfig, String projectHost, String lotteryId, Object period,
			String day, Object memberId, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost.concat("Report/GetBetDetail?");
		String parametersFormat = "startDate=%s&endDate=%s&PreStartDate=%s&PreEndDate=%s"
				+ "&periodId=%s&reportType=0&Action=member&MemberId=%d&lotteryId=%s&PreLotteryId=%s&"
				+ "moneyType=0&SettleStatus=0BetNo=&IsDirectlyMember=0&ReportAction=0&_=%d";
		String parameters = String.format(parametersFormat, day, day, day, day, period, memberId, lotteryId, lotteryId,
				System.currentTimeMillis());
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算摘要信息失败");
				Thread.sleep(SLEEP);
				return getBetDetail(httpConfig, projectHost, lotteryId, period, day, memberId, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取未结算摘要信息失败", e);
			Thread.sleep(SLEEP);
			return getBetDetail(httpConfig, projectHost, lotteryId, period, day, memberId, ++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], html));
		}
	}

	/**
	 * 获取投注详情
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param lotteryId   抓取的游戏
	 * @param day         抓取的天
	 * @param period      期数
	 * @param memberId    会员ID
	 * @param pageIndex   页码索引
	 * @param pageSize    页面大小
	 * @return 投注详情
	 */
	public static String getBetDetail(HttpClientConfig httpConfig, String projectHost, String lotteryId, String period,
			String day, Object memberId, Object pageIndex, Object pageSize, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost.concat("Report/GetBetDetail?");
		String parametersFormat = "startDate=%s&endDate=%s&periodId=%s&reportType=0&SearchLowerId=&MemberId=%d"
				+ "&lotteryId=%s&ReportAction=0&moneyType=0&SettleStatus=0BetNo=&SubLotteryId=&BetResult=&"
				+ "pageindex=%s&pagesize=%s&_=%d";
		String parameters = String.format(parametersFormat, day, day, period, memberId, lotteryId, pageIndex, pageSize,
				System.currentTimeMillis());
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url.concat(parameters)));
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算摘要信息失败");
				Thread.sleep(SLEEP);
				return getBetDetail(httpConfig, projectHost, lotteryId, period, day, memberId, pageIndex, pageSize,
						++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取未结算摘要信息失败", e);
			Thread.sleep(SLEEP);
			return getBetDetail(httpConfig, projectHost, lotteryId, period, day, memberId, pageIndex, pageSize,
					++index[0]);
		} finally {
			log.trace(String.format(LOG_FORMAT, Thread.currentThread().getName(), url, parameters, index[0], html));
		}
	}

	//  开启页面函数

	// TODO Document解析页面函数
	/**
	 * 获取会员信息
	 *
	 * @param html 会员信息列表
	 * @return 会员信息
	 */
	private static JSONArray getMemberInfo(String html) {
		JSONObject data = JSONObject.parseObject(html).getJSONObject("Data");

		JSONArray array = data.getJSONArray("Data");

		JSONArray memberList = new JSONArray();
		JSONArray member;
		for (Object obj : array) {
			JSONObject jsonObject = (JSONObject) obj;
			member = new JSONArray();
			member.add(jsonObject.getString("Account"));
			member.add(jsonObject.getInteger("IsOnline") == 1 ? "online" : "offline");
			memberList.add(member);
		}
		return memberList;
	}

	// TODO 页面解析函数

	//  页面解析函数
	/**
	 * 解析导航页面获取action
	 *
	 * @param navigationHtml 导航页面
	 * @return 页面action
	 */
	public static String navigationHtml(String navigationHtml) {

		Document document = Jsoup.parse(navigationHtml);
		if (!StringTool.isContains(document.body().html(), "action")) {
			log.info("解析导航页面出错，页面为：" + navigationHtml);
			return null;
		}
		Element body = document.body();

		Elements select = body.select("form");

		return select.attr("action");
	}
	/**
	 * 需要设置cookie
	 *
	 * @param httpConfig http请求配置类
	 * @param html       需要解析的页面
	 * @return 设置结果
	 */
	private static boolean setCookie(HttpClientConfig httpConfig, String url, String html) {
		//是否要放入cookie
		String cookieStr = StringUtils.substringBetween(html, "document.cookie='", "'");
		if (StringTool.notEmpty(cookieStr)) {
			HttpClientContext context = httpConfig.httpContext();
			if (context == null) {
				context = HttpClientContext.create();
				httpConfig.httpContext(context);
			}
			String name = "", value = null, path = null, domain = url.replace("http://", "");
			for (String item : cookieStr.split(";")) {
				int index = item.indexOf('=');
				String key = item.substring(0, index).trim();
				switch (key) {
					case ClientCookie.PATH_ATTR:
						path = item.substring(index + 1).trim();
						break;
					case ClientCookie.DOMAIN_ATTR:
						break;
					default:
						name = key;
						value = item.substring(index + 1).trim();

				}
			}
			BasicClientCookie cookie = new BasicClientCookie(name, value);
			cookie.setDomain(domain);
			cookie.setPath(path);
			context.getCookieStore().addCookie(cookie);
			return true;
		}
		return false;
	}

	/**
	 * 获取登录href
	 *
	 * @param html 角色选择页面
	 * @return 代理登录地址
	 */
	public static String getAgentLoginHref(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		if (!StringTool.isContains(html, "为了更好的浏览体验，请使用竖屏浏览！")) {
			return null;
		}
		Document document = Jsoup.parse(html);

		Element id = document.getElementById("btnMiddle");

		return id.attr("href");
	}

	/**
	 * 获取线路并排序
	 *
	 * @param httpConfig http请求配置类
	 * @param html       线路
	 * @return 获取线路列表
	 */
	public static String[] getRoutes(HttpClientConfig httpConfig, String html) {
		if (ContainerTool.isEmpty(html) || ContainerTool.isEmpty(JSONObject.parseObject(html).getJSONArray("Data"))) {
			return null;
		}
		JSONArray array = JSONObject.parseObject(html).getJSONArray("Data");
		String[] routes = new String[array.size()];
		long[] arr = new long[array.size()];
		//判断时间延迟
		long t1, t2;
		for (int i = 0; i < array.size(); i++) {
			if (i == 5 || i == 6) {
				routes[i] = array.getString(i);
				arr[i] = 500;
				continue;
			}
			t1 = System.currentTimeMillis();
			String url =
					"http://" + array.getString(i).concat("/Test/GetNetSpeed?jsonp=callback").concat(String.valueOf(i))
							.concat("&_=") + System.currentTimeMillis();

			HttpClientUtil.findInstance().getHtml(httpConfig.url(url));

			t2 = System.currentTimeMillis();
			long mytime = t2 - t1;
			if (i == 3 || i == 4 || i == 7) {
				mytime += 200;
			}
			routes[i] = array.getString(i);
			arr[i] = mytime;
		}

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
	 * 获取登录页面的登录信息
	 *
	 * @param html 登录页面
	 * @return SESSIONID和VERSION
	 */
	public static Map<String, String> getScriptInfo(String html) {
		if (StringTool.isEmpty(html)) {
			return new HashMap<>(1);
		}
		Map<String, String> map = new HashMap<>(2);
		Document document = Jsoup.parse(html);

		Element script = document.selectFirst("script");

		String sessionid = StringUtils.substringBetween(script.html(), "var SESSIONID = \"", "\";");
		String version = StringUtils.substringBetween(script.html(), "var VERSION = \"", "\";");
		map.put("SESSIONID", "/".concat(sessionid));
		map.put("VERSION", version);
		return map;
	}

	/**
	 * 未结算摘要
	 *
	 * @param html 未结算摘要信息
	 * @return 未结算摘要 - memberId - memberAccount - betCount
	 */
	private static Map<String, Map<String, SummaryInfo>> betSummary(String html ) {
		if (!StringTool.contains(html, "Data")) {
			return null;
		}
		JSONObject outData = JSONObject.parseObject(html).getJSONObject("Data");
		if (outData.get("Data") == null){
			return new HashMap<>(1);
		}

		JSONArray data = outData.getJSONArray("Data");
		Map<String, SummaryInfo> member = new HashMap<>(data.size());
        Map<String, SummaryInfo> agent = new HashMap<>(data.size());
		JSONObject betSummaryInfo;
		for (int i = 0; i < data.size(); i++) {
			betSummaryInfo = data.getJSONObject(i);
			String memberAccount = betSummaryInfo.getString("MemberAccount").replace(" [会员]","");
			SummaryInfo summary = new SummaryInfo(betSummaryInfo.get("MemberId"), memberAccount);
			summary.setBetCount(betSummaryInfo.getInteger("BetCount"));
            summary.setBetAmount(betSummaryInfo.getInteger("BetMoney"));
            if(betSummaryInfo.getInteger("MemberLevel")==1){
                member.put(memberAccount, summary);
            }else{
                agent.put(memberAccount, summary);
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
	public static DetailBox analyzeDetail(DetailBox betDetail, GameUtil.Code gameCode, String html,
			String subOddNumber) {
		//没有投注项
		if (!StringTool.contains(html, "BetNoFullName")) {
			return null;
		}
		JSONObject data = JSONObject.parseObject(html).getJSONObject("Data");

		if (betDetail == null) {
			betDetail = new DetailBox();
			Object total = data.get("RecordCount");
			Object page = data.get("PageCount");
			betDetail.pages(page, total);
		}
		List<DetailInfo> details = betDetail.details();

		String maxOddNumber = betDetail.getMaxOddNumber(subOddNumber);
		int compare = maxOddNumber.compareTo(subOddNumber);

		//解析投注项
		JSONArray dataArr = data.getJSONArray("Data");
		JSONObject betDetailInfo;
		for (int i = 0; i < dataArr.size(); i++) {
			betDetailInfo = dataArr.getJSONObject(i);
			String oddNumber = betDetailInfo.getString("SerialNo");
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
			String betInfo = betDetailInfo.getString("BetNoFullName");
			//投注积分
			Object funds = betDetailInfo.get("BetMoney");

			//放入详情信息
			details.add(new DetailInfo(getBetItem(gameCode, betInfo), NumberTool.getDouble(funds)));
		}
		betDetail.maxOddNumber(maxOddNumber);
		return betDetail;
	}

	//  页面解析函数

	// TODO 功能函数

	//  功能函数
	/**
	 * 获取投注项
	 *
	 * @param gameCode 投注编码
	 * @param betInfo  投注信息
	 * @return 投注项
	 */
	private static String getBetItem(GameUtil.Code gameCode, String betInfo) {
		String[] infos = betInfo.split(" ");
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				switch (infos[0]) {
					case "冠军":
						return "第一名|".concat(infos[1]);
					case "亚军":
						return "第二名|".concat(infos[1]);
					case "冠亚和":
						return "冠亚|".concat(infos[1]);
					default:
						return infos[0].concat("|").concat(infos[1]);
				}
			case JSSSC:
			case CQSSC:
				return infos[0].concat("|").concat(infos[1]);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	/**
	 * 登陆错误
	 *
	 * @param msg 错误信息
	 * @return 错误信息
	 */
	public static HcCodeEnum loginError(String msg) {
		if (StringTool.contains(msg, "用户名或密码不正确")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "帐号被锁定")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "请求过于频繁")) {
			return HcCodeEnum.IBS_403_LOGIN_OFTEN;
		} else if (StringTool.contains(msg, "您的帐户为初次登陆", "密码由后台重新设定")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}
    //  功能函数
}
