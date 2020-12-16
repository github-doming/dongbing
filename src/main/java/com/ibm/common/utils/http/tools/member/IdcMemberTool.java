package com.ibm.common.utils.http.tools.member;

import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.handicap.config.IdcNumberConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
/**
 * @Description: IDC盘口会员工具类
 * @Author: zjj
 * @Date: 2019-09-03 14:29
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IdcMemberTool {
	private static Set<String> safetyRoute = new HashSet<>();

	static {
		safetyRoute.add("62x-dgw");
		safetyRoute.add("75fjdgw");
	}

	private static final long SHORT_SLEEP = 500L;
	private static final long SLEEP = 1000L;

	protected static final Logger log = LogManager.getLogger(IdcMemberTool.class);
	private static final Integer MAX_RECURSIVE_SIZE = 5;
	private static final String CRACK_URL = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

	// TODO 开启页面函数
	/**
	 * 打开登陆页面
	 *
	 * @param httpConfig http请求配置类
	 * @param url        url地址
	 * @param code       验证码
	 * @param index      循环次数
	 * @return 登陆页面
	 */
	public static String getLoginHtml(HttpClientConfig httpConfig, String url, String code, int... index)
			throws InterruptedException, IOException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(1);
		join.put("wd", code);
		String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
		if (StringTool.isEmpty(html)) {
			Thread.sleep(SHORT_SLEEP);
			return getLoginHtml(httpConfig, url, code, ++index[0]);
		}
		if(StringTool.isContains(html,"百度")){
			return null;
		}
		if (!isLoginHtml(html)) {
			log.error("打开选择登陆页面失败" + html);
			Thread.sleep(SHORT_SLEEP);
			return getLoginHtml(httpConfig, url, code, ++index[0]);
		}
		return html;
	}
	/**
	 * 获取登陆页面
	 *
	 * @param httpConfig http请求配置类
	 * @param url        可用线路
	 * @param index      循环次数
	 * @return 登陆页面
	 */
	public static Map<String, String> getLoginData(HttpClientConfig httpConfig, String url, int... index)
			throws InterruptedException, IOException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String result = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
		//线路打开失败,重新打开
		if (StringTool.isEmpty(result)) {
			log.error("线路打开失败");
			Thread.sleep(SHORT_SLEEP);
			return getLoginData(httpConfig, url, ++index[0]);
		}
		String key = url.substring(url.indexOf("://") + 3, url.indexOf("."));
		//线路打开不是登陆页面,换另一条线路
		if (!StringTool.isContains(result, "txt_U_name")) {
			log.error("错误的线路为：" + url);
			safetyRoute.remove(key);
			if (ContainerTool.isEmpty(safetyRoute)) {
				return null;
			} else {
				//替换一个成功的线路
				List<String> routes = new ArrayList<>(safetyRoute);
				String safetyKey = routes.get(RandomTool.getInt(routes.size()));
				url = url.replace(key, safetyKey);
			}
			log.info("线路打开不是登陆页面" + result);
			Thread.sleep(SHORT_SLEEP);
			return getLoginData(httpConfig, url, ++index[0]);
		}
		try {
			safetyRoute.add(key);
			Map<String, String> loginDateMap = findLoginDateMap4LoginPage(result);
			loginDateMap.put("loginSrc", url);
			return loginDateMap;
		} catch (Exception ignore) {
			log.error("获取登陆页面异常");
			Thread.sleep(SHORT_SLEEP);
			return getLoginData(httpConfig, url, ++index[0]);
		}
	}

	/**
	 * 获取登陆结果信息
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     url地址
	 * @param handicapCaptcha 验证码
	 * @param memberAccount   会员账号
	 * @param memberPassword  会员密码
	 * @param index           循环次数
	 * @return 登陆页面信息
	 */
	public static Map<String, String> getLogin(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
			String memberAccount, String memberPassword, int... index) throws InterruptedException, IOException {
		//没有入参，初始化参数
		if (index.length == 0) {
			index = new int[1];
		}
		//线路切换次数达到最高循环次数
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		//登陆页面失败,换另一条线路
		String loginHtml = getLoginHtml(httpConfig, handicapUrl, handicapCaptcha);
		if (StringTool.isEmpty(loginHtml)) {
			log.error("登陆页面打开失败");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword, ++index[0]);
		}
		//获取线路失败,换另一条线路
		String loginSrc = getLoginSrc(loginHtml);
		if (StringTool.isEmpty(loginSrc)) {
			log.error("获取线路失败失败");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword, ++index[0]);
		}
		//获取登陆页面
		Map<String, String> loginDateMap = getLoginData(httpConfig, loginSrc);
		//登陆页面打开失败,换另一条线路
		if (ContainerTool.isEmpty(loginDateMap)) {
			log.error("登陆页面打开失败");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword, ++index[0]);
		}
		String projectHost = StringTool.projectHost(loginSrc);

		//获取图片验证码
		String verifyCode = getVerifyCode(httpConfig, projectHost);
		if (StringTool.isEmpty(verifyCode)) {
			log.error("获取图片验证码失败");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword, ++index[0]);
		}
		Map<String, String> loginInfo = getLogin(httpConfig, projectHost, loginDateMap, memberAccount, memberPassword,
				verifyCode);

		//失败页面
		if (ContainerTool.isEmpty(loginInfo) || StringTool.isEmpty(loginInfo.get("html"))) {
			log.error("登陆失败");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword, ++index[0]);
		}
		//验证码不正确
		String html = loginInfo.get("html");
		if (StringTool.isEmpty(html) || StringTool.contains(html, "验证码不正确", "验证码数据丢失", "验证码不正确，请重新输入。")) {
			log.error("验证码不正确");
			log.error("验证码不正确,验证码为【" + verifyCode + "】");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword, ++index[0]);
		}
		return loginInfo;
	}

	/**
	 * 获取验证码code
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param index       循环次数
	 * @return 验证码code
	 */
	public static String getVerifyCode(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String valiImgUrl = projectHost + "/checknum.aspx?ts=" + System.currentTimeMillis();
		String content = HttpClientUtil.findInstance().getImage(httpConfig.url(valiImgUrl));
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			Thread.sleep(SHORT_SLEEP);
			return getVerifyCode(httpConfig, valiImgUrl, ++index[0]);
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("type", "IDC");
		join.put("content", content);
		HttpClientContext context = httpConfig.httpContext();
		httpConfig.httpContext(null);
		String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(CRACK_URL).map(join));
		if (StringTool.isEmpty(html)) {
			log.error("识别验证码失败");
			Thread.sleep(SHORT_SLEEP);
			return getVerifyCode(httpConfig, valiImgUrl, ++index[0]);
		}
		html = html.replace("\"", "");
		if (!isNumber(html)) {
			html = StringUtils.substringBetween(html, "/>", "</string>");
		}
		//判断验证码是否为数字
		if (!isNumber(html)) {
			log.error("验证码检验失败");
			Thread.sleep(SHORT_SLEEP);
			return getVerifyCode(httpConfig, valiImgUrl, ++index[0]);
		}
		httpConfig.httpContext(context);
		return html;
	}

	/**
	 * 获取登陆信息
	 *
	 * @param httpConfig     http请求配置类
	 * @param hostUrl        主机地址
	 * @param loginDateMap   登陆页面数据信息
	 * @param memberAccount  会员账号
	 * @param memberPassword 会员密码
	 * @param verifyCode     验证码
	 * @param index          循环次数
	 * @return 登陆信息
	 */
	public static Map<String, String> getLogin(HttpClientConfig httpConfig, String hostUrl,
			Map<String, String> loginDateMap, String memberAccount, String memberPassword, String verifyCode,
			int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, String> data = new HashMap<>(2);
		httpConfig.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		try {
			Map<String, Object> join = new HashMap<>(6);
			join.put("__VIEWSTATE", loginDateMap.get("viewstate"));
			if (loginDateMap.containsKey("viewstategenerator")) {
				join.put("__VIEWSTATEGENERATOR", loginDateMap.get("viewstategenerator"));
			}
			join.put("__RequestVerificationToken", loginDateMap.get("requestVerificationToken"));
			join.put("txt_U_name", memberAccount);
			join.put("txt_U_Password", memberPassword);
			join.put("txt_validate", verifyCode);
			String url = hostUrl + loginDateMap.get("action");
			HttpResult result = HttpClientUtil.findInstance().post(httpConfig.url(url).map(join));

			//没有读取到页面
			if (ContainerTool.isEmpty(result)) {
				log.error("读取页面失败");
				Thread.sleep(SLEEP);
				return getLogin(httpConfig, hostUrl, loginDateMap, memberAccount, memberPassword, verifyCode,
						++index[0]);
			}
			if (302 - result.getStatusCode() == 0) {
				log.error("url= " + url);
				log.error("获取登陆信息异常302" + result);
				result = HttpClientUtil.findInstance()
						.get(httpConfig.url(StringTool.getHttpHost(hostUrl) + result.getLocation()));
			}
			if (404 - result.getStatusCode() == 0) {
				log.error("url= " + url);
				log.error("获取登陆信息异常404" + result);
				Thread.sleep(2 * SLEEP);
				result = HttpClientUtil.findInstance()
						.post(httpConfig.url(hostUrl + loginDateMap.get("action")).map(join));
			}
			//进入验证码不正确页面
			if (StringTool.contains(result.getHtml(), "验证码不正确", "验证码数据丢失")) {
				log.error("验证码不正确,验证码为【" + verifyCode + "】");
				Thread.sleep(SHORT_SLEEP);
				verifyCode = getVerifyCode(httpConfig, hostUrl);
				return getLogin(httpConfig, hostUrl, loginDateMap, memberAccount, memberPassword, verifyCode,
						++index[0]);
			}
			data.put("projectHost", hostUrl);
			data.put("statusCode", result.getStatusCode() + "");
			data.put("html", result.getHtml());
		} catch (Exception e) {
			log.error("获取登陆信息失败", e);
		}
		return data;
	}

	/**
	 * 获取用户信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 项目路径
	 * @param index       循环次数
	 * @return 用户信息json
	 */
	public static JSONObject getUserInfo(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Content-Type", "application/json; charset=utf-8");
		String html = HttpClientUtil.findInstance()
				.postHtml(httpConfig.url(projectHost + "/app/ws_member.asmx/MembersInfo_Data"));
		if (StringTool.isEmpty(html)) {
			log.error("获取用户信息失败");
			Thread.sleep(SHORT_SLEEP);
			return getUserInfo(httpConfig, projectHost, ++index[0]);
		}
		try {
			return resolveUserInfo(html);
		} catch (Exception e) {
			log.error(String.format("解析页面出错，页面为【%s】", html));
			Thread.sleep(SHORT_SLEEP);
			return getUserInfo(httpConfig, projectHost, ++index[0]);
		}

	}

	/**
	 * 获取开奖信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 项目路径
	 * @param gameno      游戏no
	 * @param index       循环次数
	 * @return 开奖信息
	 */
	public static JSONObject getDrawsInfo(HttpClientConfig httpConfig, String projectHost, String gameno, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Content-Type", "application/json; charset=utf-8");
		Map<String, Object> join = new HashMap<>(1);
		JSONObject entity = new JSONObject();
		entity.put("gameno", gameno);
		join.put("$ENTITY_JSON$", entity);
		String html = HttpClientUtil.findInstance()
				.postHtml(httpConfig.url(projectHost + "/app/ws_game.asmx/LoadDrawsInfo").map(join));
		if (StringTool.isEmpty(html)) {
			log.error("获取开奖信息失败");
			Thread.sleep(SHORT_SLEEP);
			return getDrawsInfo(httpConfig, projectHost, gameno, ++index[0]);
		}
		try {
			return resolveDrawsInfo(html);
		} catch (Exception e) {
			log.error(String.format("解析页面出错，页面为【%s】", html));
			Thread.sleep(SHORT_SLEEP);
			return getDrawsInfo(httpConfig, projectHost, gameno, ++index[0]);
		}
	}
	/**
	 * 获取赔率信息
	 *
	 * @param httpConfig   http请求配置类
	 * @param projectHost  项目路径
	 * @param wagerRoundNo 所属盘口
	 * @param gameno       游戏no
	 * @param gameBetCode  游戏投注code
	 * @param index        循环次数
	 * @return 开奖信息
	 */
	public static JSONObject getOddInfo(HttpClientConfig httpConfig, String projectHost, String wagerRoundNo,
			String gameno, List<String> gameBetCode, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(5);
		join.put("stype", "getoddsbytype");
		join.put("gameno", gameno);
		join.put("oddsgroupnos", StringUtils.join(gameBetCode, ';') + ';');
		join.put("wagerroundno", wagerRoundNo);
		join.put("ts", System.currentTimeMillis());

		String html = HttpClientUtil.findInstance()
				.getHtml(httpConfig.url(projectHost + "/ashx/orderHandler.ashx").addParameter(join));
		if (StringTool.isEmpty(html)) {
			log.error("获取赔率信息失败");
			Thread.sleep(SHORT_SLEEP);
			return getOddInfo(httpConfig, projectHost, wagerRoundNo, gameno, gameBetCode, ++index[0]);
		}
		if (html.contains("请求过于频繁，请稍等")) {
			Thread.sleep(5 * 1000L);
		}
		try {
			return resolveOddInfo(html);
		} catch (Exception e) {
			log.error(String.format("解析页面出错，页面为【%s】", html));
			Thread.sleep(SHORT_SLEEP);
			return getOddInfo(httpConfig, projectHost, wagerRoundNo, gameno, gameBetCode, ++index[0]);
		}
	}

	/**
	 * 投注验证
	 *
	 * @param httpConfig http请求配置类
	 * @param userInfo   用户信息
	 * @param betItems   投注信息
	 * @param index      循环次数
	 * @return 验证结果
	 */
	public static String betValiToken(HttpClientConfig httpConfig, Map<String, String> userInfo, String[] betItems,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		JSONObject entity = new JSONObject();
		entity.put("wagerround", userInfo.get("memberType"));
		entity.put("transtring", betItems[0]);
		entity.put("arrstring", betItems[1]);
		entity.put("wagetype", 0);
		entity.put("allowcreditquota", userInfo.get("usedQuota"));
		entity.put("hasToken", true);
		entity.put("playgametype", 0);
		Map<String, Object> join = new HashMap<>(1);
		join.put("$ENTITY_JSON$", entity);
		String html = HttpClientUtil.findInstance()
				.postHtml(httpConfig.url(userInfo.get("projectHost") + "/ch/left.aspx/GetMemberMtran").map(join));
		if (StringTool.isEmpty(html)) {
			log.error("投注验证失败");
			Thread.sleep(SHORT_SLEEP);
			return betValiToken(httpConfig, userInfo, betItems, ++index[0]);
		}
		return getToken(html);
	}

	/**
	 * IDC投注检验
	 *
	 * @param httpConfig http配置类
	 * @param userInfo   用户信息
	 * @param gameno     投注code
	 * @param period     期数
	 * @param betItem    投注信息
	 */
	public static String orderHandler(HttpClientConfig httpConfig, Map<String, String> userInfo, String gameno,
			String period, String betItem) {
		Map<String, Object> join = new HashMap<>(5);
		join.put("stype", "checkxiadan");
		join.put("gameno", gameno);
		join.put("roundno", period);
		join.put("wagerroundno", userInfo.get("memberType"));
		join.put("wagers", betItem);
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
		String html = HttpClientUtil.findInstance()
				.postHtml(httpConfig.url(userInfo.get("projectHost") + "/ashx/orderHandler.ashx").map(join));
		httpConfig.setHeader("Content-Type", "application/json; charset=utf-8");

		if (StringTool.isEmpty(html)) {
			return null;
		}
		return html;
	}
	/**
	 * IDC投注
	 *
	 * @param httpConfig http配置类
	 * @param gameNo     投注code
	 * @param userInfo   用户信息
	 * @param betItemArr 投注信息
	 * @param period     当前期数
	 * @param token      token
	 * @return 投注页面
	 */
	public static String betting(HttpClientConfig httpConfig, String gameNo, Map<String, String> userInfo,
			String betItemArr, String period, String token) {
		JSONObject entity = new JSONObject();
		entity.put("gameno", gameNo);
		entity.put("wagerroundstring", userInfo.get("memberType"));
		entity.put("arrstring", betItemArr);
		entity.put("roundno", period);
		entity.put("lianma_transtrin", "");
		entity.put("token", token);
		Map<String, Object> join = new HashMap<>(1);
		join.put("$ENTITY_JSON$", entity);

		String html = HttpClientUtil.findInstance()
				.postHtml(httpConfig.url(userInfo.get("projectHost") + "/ch/left.aspx/mtran_XiaDan_New").map(join));
		if (StringTool.isEmpty(html)) {
			log.error(String.format("解析投注页面出错，页面为【%s】", html));
			return null;
		}
		return html;
	}

	/**
	 * 获取盈亏信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 项目路径
	 * @param date        日期字符串（yyyy-MM-dd）
	 * @param curPage     页数
	 * @param pageSize    每页条数
	 * @param index       循环次数
	 * @return 盈亏信息
	 */
	public static String getProfitLoss(HttpClientConfig httpConfig, String gameno, String projectHost, String date,
			String isJs, int curPage, int pageSize, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> map = new HashMap<>(8);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("gamestr", gameno);
		jsonObject.put("date1", date);
		jsonObject.put("date2", date);
		jsonObject.put("roundno", "");
		jsonObject.put("isjs", isJs);
		if ("".equals(isJs)) {
			jsonObject.put("status", "1");
		} else {
			jsonObject.put("status", "");
		}
		jsonObject.put("curpage", curPage);
		jsonObject.put("pagesize", pageSize);

		map.put("$ENTITY_JSON$", jsonObject);

		String html = HttpClientUtil.findInstance()
				.postHtml(httpConfig.url(projectHost + "/ch/mreportdetail.aspx/Getmemberdetail").map(map));
		if (StringTool.isEmpty(html)) {
			log.info("获取盈亏信息页面为空");
			Thread.sleep(SHORT_SLEEP);
			return getProfitLoss(httpConfig, gameno, projectHost, date, isJs, curPage, pageSize, ++index[0]);
		}
		return html;
	}

	/**
	 * 定时校验
	 *
	 * @param httpConfig    http请求配置类
	 * @param memberAccount 用户账号名
	 * @param projectHost   项目url
	 */
	public static String toCheckIn(HttpClientConfig httpConfig, String memberAccount, String projectHost,
			int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Content-Type", "application/json; charset=utf-8");
		String html = null;
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("loginno", memberAccount);
			Map<String, Object> map = new HashMap<>(1);
			map.put("$ENTITY_JSON$", jsonObject);
			html = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(projectHost + "/app/ws_system.asmx/ToCheckIn").map(map));
			if (StringTool.isEmpty(html)) {
				return toCheckIn(httpConfig, memberAccount, projectHost, ++index[0]);
			}
		} catch (Exception e) {
			log.error("定时校验失败", e);
		}
		return html;
	}
	/**
	 * 获取未结算注单
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param gamestring  盘口游戏code
	 * @param transDate   统计日期
	 * @param dateTime    日期
	 * @return 未结算注单
	 */
	public static JSONObject getReport(HttpClientConfig httpConfig, String projectHost, String gamestring,
			String transDate, String dateTime, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Content-Type", "text/plain, */*; q=0.01");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("memberno", "");
		jsonObject.put("gamestring", gamestring);
		jsonObject.put("sdate", transDate);
		jsonObject.put("edate", transDate);
		jsonObject.put("beginroundno", "");
		jsonObject.put("endroundno", "");
		jsonObject.put("wagerroundno", "");
		jsonObject.put("wagertypeno", "");
		jsonObject.put("_isjs", false);
		jsonObject.put("datetime", dateTime);
		jsonObject.put("status", 1);
		Map<String, Object> map = new HashMap<>(1);
		map.put("$ENTITY_JSON$", jsonObject);
		String html;
		try {
			html = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(projectHost + "//opadmin/mreport_new.aspx/GetReport").map(map));
			if (StringTool.isEmpty(html)) {
				log.error("获取用户管理页面失败" + html);
				Thread.sleep(SLEEP);
				return getReport(httpConfig, projectHost, gamestring, transDate, dateTime, ++index[0]);
			}
			return JSONObject.fromObject(html);
		} catch (Exception e) {
			log.error("获取用户未结算页面失败", e);
			Thread.sleep(SLEEP);
			return getReport(httpConfig, projectHost, gamestring, transDate, dateTime, ++index[0]);
		}

	}
	//TODO,新增代理页面解析
	/**
	 * 获取用户管理页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param index       循环次数
	 * @return 用户管理页面
	 */
	public static String getMemberManage(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(projectHost + "/opadmin/membermanage.aspx"));
		if (StringTool.isEmpty(html) || !StringTool.isContains(html, "会员管理")) {
			log.error("获取用户管理页面失败" + html);
			Thread.sleep(SLEEP);
			return getMemberManage(httpConfig, projectHost, ++index[0]);
		}
		return html;
	}
	/**
	 * 获取所有会员信息
	 *
	 * @param httpConfig    http请求配置类
	 * @param projectHost   主机地址
	 * @param agentAccount  代理账号
	 * @param curMemberName var curmembername = "c";
	 * @return 所有会员信息
	 */
	public static JSONArray getMemberInfoList(HttpClientConfig httpConfig, String projectHost, String agentAccount,
			String curMemberName, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("memberno", agentAccount);
		jsonObject.put("membername", curMemberName);
		jsonObject.put("gameno", "");
		jsonObject.put("topeffective", "");
		Map<String, Object> map = new HashMap<>(1);
		map.put("$ENTITY_JSON$", jsonObject);
		String html;
		try {
			html = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(projectHost + "/opadmin/membermanage.aspx/GetMembersAgent").map(map));
			if (StringTool.isEmpty(html)) {
				log.error("获取会员信息失败" + html);
				Thread.sleep(SLEEP);
				return getMemberInfoList(httpConfig, projectHost, agentAccount, curMemberName, ++index[0]);
			}
			return getMemberInfo(html);
		} catch (Exception e) {
			log.error("获取会员信息失败", e);
			Thread.sleep(SLEEP);
			return getMemberInfoList(httpConfig, projectHost, agentAccount, curMemberName, ++index[0]);
		}
	}
	/**
	 * 获取时间
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @return 时间
	 */
	public static String getDate(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("value", 0);
		Map<String, Object> map = new HashMap<>(1);
		map.put("$ENTITY_JSON$", jsonObject);
		try {
			String html = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(projectHost.concat("/opadmin/mreport_new.aspx/GetDate")).map(map));
			if (StringTool.isEmpty(html)) {
				log.error("获取会员信息失败" + html);
				Thread.sleep(SLEEP);
				return getDate(httpConfig, projectHost, ++index[0]);
			}
			return JSONObject.fromObject(html).getString("d");
		} catch (Exception e) {
			log.error("获取会员信息失败", e);
			Thread.sleep(SLEEP);
			return getDate(httpConfig, projectHost, ++index[0]);
		}
	}

	// TODO Document解析页面函数
	/**
	 * 获取用户信息
	 *
	 * @param html 会员信息
	 * @return 用户信息
	 */
	private static JSONArray getMemberInfo(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		JSONArray array = JSONArray.fromObject(html);

		if (ContainerTool.isEmpty(array)) {
			return null;
		}

		JSONObject object = array.getJSONObject(0);

		if (ContainerTool.isEmpty(object)) {
			return null;
		}
		return object.getJSONArray("Rows");
	}
	/**
	 * 是否是登陆选择线路页面
	 *
	 * @param html 线路页面
	 * @return 是 true
	 */
	private static boolean isLoginHtml(String html) {
		if (StringTool.isEmpty(html)) {
			return false;
		}
		return StringTool.isContains(html, "Welcome...", "</iframe>", "src");
	}
	/**
	 * 判断验证码是否全为数字
	 *
	 * @param number 数字
	 * @return 是 true
	 */
	private static boolean isNumber(String number) {
		for (int i = 0; i < number.length(); i++) {
			if (!Character.isDigit(number.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 获取登陆路径
	 *
	 * @param html 登陆页面
	 * @return 登陆路径
	 */
	public static String getLoginSrc(String html) {
		Document document = Jsoup.parse(html);
		Element iframeElement = document.selectFirst("iframe");
		if (iframeElement == null) {
			log.error("获取登陆路径错误=" + html);
			return null;
		}
		return iframeElement.attr("src");
	}

	/**
	 * 获取登陆界面包含的数据
	 *
	 * @param loginHtml 登陆html界面
	 * @return 包含的所需要的数据
	 */
	public static Map<String, String> findLoginDateMap4LoginPage(String loginHtml) {
		Document document = Jsoup.parse(loginHtml);
		Map<String, String> loginDateMap = new HashMap<>(4);

		Element form1 = document.selectFirst("form#form1");
		Element viewstate = form1.selectFirst("input[name=__VIEWSTATE]");
		Element viewstategenerator = form1.selectFirst("input[name=__VIEWSTATEGENERATOR]");
		Element requestVerificationToken = form1.selectFirst("input[name=__RequestVerificationToken]");
		String action = form1.attr("action");
		action = "/?".concat(action.substring(action.indexOf("pagegroup")));
		loginDateMap.put("action", action);
		loginDateMap.put("viewstate", viewstate.val());
		if (viewstategenerator != null) {
			loginDateMap.put("viewstategenerator", viewstategenerator.val());
		}
		loginDateMap.put("requestVerificationToken", requestVerificationToken.val());
		return loginDateMap;
	}

	/**
	 * 登陆错误
	 *
	 * @param html 登陆结果页面
	 * @return 错误信息
	 */
	public static HcCodeEnum loginError(String html) {
		if (StringTool.contains(html, "您输入的帐号与密码不匹配")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(html, "验证码不正确", "验证码数据丢失", "验证码不正确，请重新输入。")) {
			return HcCodeEnum.IBS_403_VERIFY_CODE;
		} else if (StringTool.contains(html, "请求过于频繁")) {
			return HcCodeEnum.IBS_403_LOGIN_OFTEN;
		} else if (StringTool.contains(html, "您的帐户为初次登陆", "密码由后台重新设定")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else if (StringTool.contains(html, "帐号已禁用")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(html, "<h2>404 -")) {
			return HcCodeEnum.IBS_403_LOGIN_FAIL;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}

	/**
	 * 解析获取用户信息数据<br>
	 * eg.{"d":"[{\"Rows\":[{\"memberno\":\"nhdb01\",\"membername\":\"db01\",\"opena\":true,\"openb\":false,
	 * \"openc\":false,\"opend\":false,\"opene\":false,\"credittype\":2,\"accounttype\":1,\"creditquota\":1000,
	 * \"usecreditquota\":0.00,\"usecreditquota2\":0.00,\"allowcreditquota\":1000.00}]},0.0000]"}
	 *
	 * @param html 用户信息
	 * @return 用户数据
	 */
	private static JSONObject resolveUserInfo(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		try {
			JSONObject jsonObject = JSONObject.fromObject(html);
			if (jsonObject.containsKey("d")) {
				Object d = JSONObject.fromObject(html).get("d");
				return JSONArray.fromObject(d).getJSONObject(1).getJSONArray("Rows").getJSONObject(0);
			} else if (jsonObject.containsKey("Message")) {
				return jsonObject;
			}
			log.error("解析获取用户信息数据失败，页面为：" + html);
			return null;
		} catch (Exception e) {
			log.error("解析获取用户信息数据失败，页面为：" + html);
			return null;
		}
	}

	/**
	 * 获取  投注盘
	 *
	 * @param userInfo 用户信息
	 * @return 投注盘
	 */
	public static String getWagerRoundNo(JSONObject userInfo) {
		if (userInfo.getBoolean("opena")) {
			return "A";
		} else if (userInfo.getBoolean("openb")) {
			return "B";
		} else if (userInfo.getBoolean("openc")) {
			return "C";
		} else if (userInfo.getBoolean("opend")) {
			return "D";
		} else if (userInfo.getBoolean("opene")) {
			return "E";
		} else {
			return null;
		}
	}
	/**
	 * 解析开奖数据<br>
	 * eg.{"d":"{\"code\":1,\"message\":{\"sd\":\"730229\",\"soTs\":-1,\"seTs\":-1,\"swTs\":-1,\"cd\":\"730230\",
	 * \"coTs\":-564,\"ceTs\":571,\"cwTs\":651,\"nd\":\"730231\",\"noTs\":65,\"neTs\":1135,\"nwTs\":80,\"jgTs\":15,
	 * \"kjTs\":1200,\"ld\":\"730229\",\"lr\":[\"10\",\"09\",\"04\",\"03\",\"02\",\"07\",\"06\",\"08\",\"05\",\"01\"]}}"}
	 *
	 * @param html 开奖数据
	 * @return 开奖数据
	 */
	public static JSONObject resolveDrawsInfo(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		try {
			Object d = JSONObject.fromObject(html).get("d");
			return JSONObject.fromObject(d).getJSONObject("message");
		} catch (Exception e) {
			log.error("解析开奖数据失败，页面为：" + html);
			return null;
		}
	}

	/**
	 * 解析赔率数据<br>eg.
	 * {"og611":{"Rows":[{"oddsgroupno":611,"objectid":1,"odds":1.9831},{"oddsgroupno":611,"objectid":2,"odds":1.9831}]},
	 * "og612":{"Rows":[{"oddsgroupno":612,"objectid":1,"odds":1.9831},{"oddsgroupno":612,"objectid":2,"odds":1.9831}]}}
	 *
	 * @param html 赔率数据
	 * @return 赔率数据
	 */
	public static JSONObject resolveOddInfo(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		JSONObject htmlObj;
		try {
			htmlObj = JSONObject.fromObject(html);
		} catch (Exception e) {
			return null;
		}
		JSONObject result = new JSONObject();
		for (Object obj : htmlObj.values()) {
			JSONArray row = JSONObject.fromObject(obj).getJSONArray("Rows");
			for (int i = 0; i < row.size(); i++) {
				JSONObject odds = row.getJSONObject(i);
				result.put(odds.getString("oddsgroupno") + ":" + odds.getString("objectid"), odds.getString("odds"));
			}
		}
		return result;
	}

	/**
	 * 获取token
	 *
	 * @param html 等待投注页面
	 * @return token
	 */
	public static String getToken(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		if (!html.contains("click_conform")) {
			return null;
		}
		String token = html.substring(html.lastIndexOf("$@") + 2);
		if (StringTool.isEmpty(token)) {
			return null;
		}
		return token.replace("\"", "").replace("}", "");
	}

	/**
	 * 获取投注结果
	 *
	 * @param errorData
	 * @param betItems
	 * @param html
	 * @return
	 */
	public static Map<String, List<Map<String, String>>> getBetResultData(List<Map<String, String>> errorData,
			List<String> betItems, String html) {

		Map<String, List<Map<String, String>>> resultData = new HashMap<>(2);
		List<Map<String, String>> successData = new ArrayList<>();
		String betDate = JSONObject.fromObject(html).getString("d");
		if (StringTool.isContains(html, "【", "】")) {
			//投注名次
			String complexRank = betDate.substring(betDate.indexOf("【") + 1, betDate.lastIndexOf("】"));
			//投注项
			String item = betDate.substring(betDate.indexOf("】") + 1, betDate.lastIndexOf(" "));
			//失败原因
			String reason = betDate.substring(betDate.lastIndexOf(" "));
			//获取投注信息
			String betInfoCode = IdcNumberConfig.BET_INFO_CODE.get(complexRank + item);
			//移除错误投注项
			boolean state = IdcMemberTool.removeBetInfo(betItems, betInfoCode);
			if (state) {
				Map<String, String> error = new HashMap<>(2);
				error.put("item", item);
				error.put("msg", reason);
				errorData.add(error);
			}
		} else {
			Document document = Jsoup.parse(betDate);
			Elements tables = document.select("table");
			for (Element table : tables) {
				Map<String, String> success = new HashMap<>(4);
				String tStr = table.text().replaceAll("\\s*", "");
				String betItem = tStr.substring(tStr.indexOf("#") + 1, tStr.indexOf("@"));

				//移除成功的投注项
				boolean state = IdcMemberTool.removeBetInfo(betItems, betItem);
				if (state) {
					success.put("betNumber", tStr.substring(tStr.indexOf("注单号：") + 4, tStr.indexOf("#")));
					success.put("betInfo", betItem);
					success.put("odds", tStr.substring(tStr.indexOf("@") + 1, tStr.indexOf("下注额：")));
					success.put("profit", tStr.substring(tStr.indexOf("可赢额：") + 4));
					successData.add(success);
				}

			}
		}

		resultData.put("successData", successData);
		resultData.put("errorData", errorData);
		return resultData;
	}

	// TODO 功能函数

	/**
	 * 积分是否足够
	 *
	 * @param allowCreditQuota 用户可用余额
	 * @param betItem          投注项
	 * @return 积分不足 true
	 */
	public static boolean isPointExcess(String allowCreditQuota, String[][] betItem) {
		int amount = Arrays.stream(betItem).mapToInt(item -> Integer.parseInt(item[1])).sum();
		double credit = Double.parseDouble(allowCreditQuota);
		return credit < amount;
	}
	/**
	 * 积分是否足够
	 *
	 * @param allowCreditQuota 用户可用余额
	 * @param betItem          投注信息
	 * @return 积分不足 true
	 */
	public static boolean isPointExcess(double allowCreditQuota, List<String> betItem) {
		String[] strings;
		int amount = 0;
		for (String str : betItem) {
			strings = str.split("#");
			amount += Integer.parseInt(strings[1]);
		}
		return allowCreditQuota < amount;
	}

	/**
	 * 获取投注项
	 *
	 * @param items    投注项
	 * @param ballCode 球码code
	 * @param oddInfo  赔率信息
	 * @return 装换后的投注信息
	 */
	public static String[] getBetItem(String[][] items, Map<String, String> ballCode, JSONObject oddInfo) {
		String[] str = new String[2];
		//transtring
		StringBuilder betBuilder = new StringBuilder();
		//arrstring
		StringBuilder betArr = new StringBuilder();

		for (String[] item : items) {
			String betCode = ballCode.get(item[0]);
			String odds = oddInfo.getString(betCode);
			betBuilder.append(betCode.replace(":", ",,")).append(",,").append(odds).append(",").append(item[1])
					.append(";");
			betArr.append(betCode).append(":").append(item[1]).append(";");
		}
		str[0] = betBuilder.toString();
		str[1] = betArr.toString();
		return str;
	}
	/**
	 * 获取投注项
	 *
	 * @param betItems 投注项
	 * @param ballCode 球码code
	 * @param oddInfo  赔率信息
	 * @return 装换后的投注信息
	 */
	public static String[] getBetItemInfo(List<String> betItems, Map<String, String> ballCode, JSONObject oddInfo) {
		String[] str = new String[2];
		//transtring
		StringBuilder betBuilder = new StringBuilder();
		//arrstring
		StringBuilder betArr = new StringBuilder();

		for (Object json : betItems) {
			String betItem = (String) json;
			String[] item = betItem.split("#");
			String betCode = ballCode.get(item[0]);
			String odds = oddInfo.getString(betCode);
			betBuilder.append(betCode.replace(":", ",,")).append(",,").append(odds).append(",").append(item[1])
					.append(";");
			betArr.append(betCode).append(":").append(item[1]).append(";");
		}
		str[0] = betBuilder.toString();
		str[1] = betArr.toString();
		return str;
	}

	/**
	 * 剔除错误投注项
	 *
	 * @param betItems 投注项
	 * @param bet      错误投注项
	 */
	public static boolean removeBetInfo(List<String> betItems, String bet) {
		for (Object json : betItems) {
			String betItem = (String) json;
			String[] item = betItem.split("#");
			if (bet.equals(item[0])) {
				betItems.remove(betItem);
				return true;
			}
		}
		return false;
	}
}
