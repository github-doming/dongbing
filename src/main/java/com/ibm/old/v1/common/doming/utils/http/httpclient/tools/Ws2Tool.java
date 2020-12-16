package com.ibm.old.v1.common.doming.utils.http.httpclient.tools;
import com.ibm.old.v1.common.doming.configs.Ws2Config;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: ws2盘口登陆工具类
 * @Author: Dongming
 * @Date: 2018-12-10 18:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Ws2Tool {

	private static final long SHORT_SLEEP = 500L;
	private static final long SLEEP = 1000L;

	static final Logger log = LogManager.getLogger(Ws2Tool.class);

	private static final Integer MAX_RECURSIVE_SIZE = 5;

	private static final String CRACK_CONTENT = "http://115.159.55.225/Code/GetVerifyCodeFromContent";

	// TODO 开启页面函数

	/**
	 * 打开选择线路界面
	 *
	 * @param httpConfig http请求配置类
	 * @param url        url地址
	 * @param code       验证码
	 * @param index      循环次数
	 * @return 选择线路界面
	 */
	public static String getSelectRoutePage(HttpClientConfig httpConfig, String url, String code, int... index)
			throws InterruptedException, IOException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String submitBt = "搜索";
		Map<String, Object> join = new HashMap<>(2);
		join.put("code", code);
		join.put("submit_bt", submitBt);
		String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
		if (StringTool.isEmpty(html)) {
			log.error("打开选择线路界面失败");
			Thread.sleep(SHORT_SLEEP);
			return getSelectRoutePage(httpConfig, url, code, ++index[0]);
		}
		return html;
	}

	/**
	 * 获取登陆页面
	 *
	 * @param httpConfig http请求配置类
	 * @param routes     可用线路
	 * @param index      【线路下标，循环次数】
	 * @return 登陆页面
	 */
	public static Map<String, Object> getLoginPage(HttpClientConfig httpConfig, List<String> routes, int... index)
			throws InterruptedException {
		//没有入参，初始化参数
		if (index.length == 0) {
			index = new int[2];
		}
		//线路切换了一轮
		if (index[0] >= routes.size()) {
			index[0] = 0;
			index[1]++;
		}
		//线路轮换次数达到最高循环次数
		if (index[1] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> result = (Map<String, Object>) HttpClientUtil.findInstance().get(httpConfig.url(routes.get(index[0])));

		//线路打开失败,换另一条线路
		if (result == null) {
			return getLoginPage(httpConfig, routes, ++index[0], index[1]);
		}
		//线路打开不是登陆页面,换另一条线路
		if (!isLoginPage(result)) {
			log.error("线路打开不是登陆页面");
			Thread.sleep(SHORT_SLEEP);
			return getLoginPage(httpConfig, routes, ++index[0], index[1]);
		}
		result.put("url", routes.get(index[0]));
		return result;
	}

	/**
	 * 破解验证码
	 *
	 * @param httpConfig   http请求配置类
	 * @param hostUrl      主机地址
	 * @param loginDateMap 登陆页面数据信息
	 * @param index        循环次数
	 * @return 验证码
	 */
	public static String getVerifyCodeByContent(HttpClientConfig httpConfig, String hostUrl,
			Map<String, Object> loginDateMap, int... index) throws IOException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String valiImgUrl = getValiImageUrl(hostUrl, loginDateMap);
		String content = HttpClientUtil.findInstance().getImage(httpConfig.url(valiImgUrl));
		if (StringTool.isEmpty(content)) {
			log.error("获取验证码失败");
			try {
				Thread.sleep(SHORT_SLEEP);
			} catch (InterruptedException e) {
				log.trace("获取验证码睡眠失败", e);
			}
			return getVerifyCodeByContent(httpConfig, hostUrl, loginDateMap, ++index[0]);
		}

		Map<String, Object> join = new HashMap<>(2);
		join.put("type", "WS");
		join.put("content", content);

		String html = HttpClientTool.doPost(CRACK_CONTENT, join);
		if (StringTool.isEmpty(html)) {
			log.error("破解验证码失败");
			try {
				Thread.sleep(SHORT_SLEEP);
			} catch (InterruptedException e) {
				log.trace("破解验证码睡眠失败", e);
			}
			return getVerifyCodeByContent(httpConfig, hostUrl, loginDateMap, ++index[0]);
		}
		html = html.replace("\"", "");
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
	public static String getLogin(HttpClientConfig httpConfig, String hostUrl, Map<String, Object> loginDateMap,
			String memberAccount, String memberPassword, String verifyCode, int... index)
			throws InterruptedException, IOException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(8);
		join.put("VerifyCode", verifyCode);
		join.put("__VerifyValue", loginDateMap.get("verifyvalue"));
		join.put("__name", memberAccount);
		join.put("password", codingPass(memberPassword));
		join.put("isSec", 0);
		join.put("cid", loginDateMap.get("cid"));
		join.put("cname", loginDateMap.get("cname"));
		join.put("systemversion", loginDateMap.get("systemversion"));
		String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(hostUrl + "/loginVerify/.auth").map(join));

		//没有读取到页面
		if (StringTool.isEmpty(html)) {
			log.error("获取登陆信息失败");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, hostUrl, loginDateMap, memberAccount, memberPassword, verifyCode, ++index[0]);
		}
		//验证码错误
		if (StringTool.isContains(html, "验证码")) {
			log.error("验证码错误");
			Thread.sleep(SHORT_SLEEP);
			verifyCode = getVerifyCodeByContent(httpConfig, hostUrl, loginDateMap);
			return getLogin(httpConfig, hostUrl, loginDateMap, memberAccount, memberPassword, verifyCode, ++index[0]);
		}
		return html;

	}

	/**
	 * 获取主页面
	 *
	 * @param httpConfig http请求配置类
	 * @param hostUrl    主机地址
	 * @param loginInfo  登陆信息
	 * @param index      循环次数
	 * @return 主页面信息
	 */
	public static String getHomePage(HttpClientConfig httpConfig, String hostUrl, String loginInfo, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String[] strs = loginInfo.split("://host");
		Map<String, Object> homePage = (Map<String, Object>) HttpClientUtil.findInstance()
				.get(httpConfig.url(StringTool.format(hostUrl + strs[1])));
		if (ContainerTool.isEmpty(homePage)) {
			log.error("获取主页面失败");
			Thread.sleep(SHORT_SLEEP);
			return getHomePage(httpConfig, hostUrl, loginInfo, ++index[0]);
		}
		if ("302".equals(homePage.get("statusCode"))) {
			homePage = (Map<String, Object>) HttpClientUtil.findInstance().get(httpConfig.url(homePage.get("location").toString()));
		}
		if (ContainerTool.isEmpty(homePage)) {
			log.error("获取主页面失败");
			Thread.sleep(SHORT_SLEEP);
			return getHomePage(httpConfig, hostUrl, loginInfo, ++index[0]);
		}
		return homePage.get("html").toString();
	}

	/**
	 * 获取用户信息
	 *
	 * @param httpConfig http请求配置类
	 * @param gameCode   游戏code
	 * @param hostUrl    主机地址
	 * @param userCode   用户code
	 * @param index      循环次数
	 * @return 用户信息json
	 */
	public static JSONObject getUserInfo(HttpClientConfig httpConfig, String gameCode, String hostUrl, String userCode,
			int... index) {
		JSONObject jsonObject = new JSONObject();
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = hostUrl + userCode + gameCode + "/order/leftInfo/?_=" + System.currentTimeMillis() + "__ajax";

		try {
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(StringTool.format(url)));
			if (StringTool.isEmpty(html)) {
				log.error("获取用户信息失败");
				Thread.sleep(SHORT_SLEEP);
				return getUserInfo(httpConfig, gameCode, hostUrl, userCode, ++index[0]);
			}
			//已经在其他地方登陆处理
			if (html.contains("您已經在其它地方登錄")) {
				log.error(String.format("您已經在其它地方登錄，页面为【%s】", html));
				jsonObject.put("data", "您已經在其它地方登錄");
				return jsonObject;
			}
			if (StringTool
					.isContains(html, "出錯的原因可能是", "您還沒有登錄或沒有權限，請聯繫管理員", "剛更新了程序，係統正在初始化", "當前正在訪問的人數太多，係統來不及處理當前請求")) {
				log.error(String.format("盘口繁忙，页面为【%s】", html));
				Thread.sleep(SLEEP);
				return getUserInfo(httpConfig, gameCode, hostUrl, userCode, ++index[0]);
			}
			return resolvePage("获取用户信息",html);
		} catch (Exception e) {
			log.error("获取用户信息错误", e);
			shortSleep("获取用户信息");
			return getUserInfo(httpConfig, gameCode, hostUrl, userCode, ++index[0]);
		}
	}

	/**
	 * 获取游戏投注信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param gameCode    游戏code
	 * @param gameBetCode 游戏投注code
	 * @param hostUrl     主机地址
	 * @param userCode    用户code
	 * @param type        执行状态
	 * @param index       循环次数
	 * @return 游戏投注信息
	 */
	public static JSONObject getBetInfo(HttpClientConfig httpConfig, String gameCode, String gameBetCode,
			String hostUrl, String userCode, IbmStateEnum type, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = hostUrl + userCode + gameCode + "/order/list?" + gameBetCode + "&_=" + System.currentTimeMillis()
				+ "__ajax";
		try {
			String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(StringTool.format(url)));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏投注信息失败");
				Thread.sleep(SLEEP);
				return getBetInfo(httpConfig, gameCode, gameBetCode, hostUrl, userCode, type, ++index[0]);
			}
			JSONObject betInfo = resolvePage("获取游戏投注信息",html);
			if (betInfo == null) {
				return getBetInfo(httpConfig, gameCode, gameBetCode, hostUrl, userCode, type, ++index[0]);
			}
			if (IbmStateEnum.LOGIN.name().equals(type.name())) {
				return betInfo;
			}
			if (ContainerTool.isEmpty(betInfo.getJSONObject("data").get("integrate"))) {
				return getBetInfo(httpConfig, gameCode, gameBetCode, hostUrl, userCode, type, ++index[0]);
			}
			return betInfo;
		} catch (Exception e) {
			log.error("获取游戏投注信息错误", e);
			shortSleep("获取游戏投注信息");
			return getBetInfo(httpConfig, gameCode, gameBetCode, hostUrl, userCode, type, ++index[0]);
		}
	}
	/**
	 * 投注
	 *
	 * @param httpConfig    http请求配置类
	 * @param hostUrl       主机地址
	 * @param userCode      用户code
	 * @param gameCode      游戏code
	 * @param betItems      投注项
	 * @param versionNumber 版本号
	 * @return 投注结果
	 */
	public static JSONObject betting(HttpClientConfig httpConfig, String hostUrl, String userCode, String gameCode,
			JSONArray betItems, String versionNumber, int... index) {

		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		String url = hostUrl + userCode + gameCode + "/order/leftInfo";

		StringBuilder t = new StringBuilder();
		Map<String, Object> data = new HashMap<>(4);

		for (int i = 0; i < betItems.size(); i++) {
			JSONObject betItem = betItems.getJSONObject(i);
			t.append(betItem.getString("betCode")).append("|").append(betItem.getString("odd")).append("|")
					.append(betItem.getString("amount")).append(";");
		}
		data.put("t", t.toString());
		data.put("v", versionNumber);
		data.put("post_submit", "");
		data.put("_", System.currentTimeMillis() + ("__ajax"));
		data.put("", null);

		try {
			String html = HttpClientUtil.findInstance().postHtml(httpConfig.map(data).url(StringTool.format(url)));
			if (StringTool.isEmpty(html)) {
				log.error("投注结果页面为空,投注项为：" + betItems);
				return null;
			}
			if (StringTool.contains(html, "抱歉！出錯啦…", "網絡繁忙，請稍後再試")) {
				betting(httpConfig, hostUrl, userCode, gameCode, betItems, versionNumber, ++index[0]);
			}
			return resolvePage("投注",html);
		} catch (Exception e) {
			log.error("投注错误", e);
			shortSleep("投注");
			return null;
		}
	}

	/**
	 * 定时检验请求
	 *
	 * @param httpConfig http请求配置类
	 * @param hostUrl    主机地址
	 * @param userCode   用户code
	 * @param index      循环次数
	 */
	public static JSONObject checkout(HttpClientConfig httpConfig, String hostUrl, String userCode, int... index) {
		JSONObject jsonObject = new JSONObject();
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			jsonObject.put("data", "检验异常，请重新登录！");
			return jsonObject;
		}
		String url = hostUrl + userCode + "pk/header/index?key[]=marquee&key[]=win&_=" + System.currentTimeMillis()
				+ "__autorefresh";
		try {
			String html = HttpClientUtil.findInstance().postHtml(httpConfig.url(StringTool.format(url)));
			if (StringTool.isEmpty(html)) {
				log.info("定时检验请求失败");
				Thread.sleep(SLEEP);
				return checkout(httpConfig, hostUrl, userCode, ++index[0]);
			}
			if (StringTool.isContains(html, "您已經在其它地方登錄")) {
				jsonObject.put("data", "您已經在其它地方登錄");
				return jsonObject;
			}
			if (StringTool.isContains(html, "用户名或密码不正确")) {
				jsonObject.put("data", "用户名或密码不正确");
				return jsonObject;
			}
			if (StringTool.isContains(html, "ValidatorAlertScript")) {
				jsonObject.put("data", "定时检验失败");
				return jsonObject;
			}
			if (StringTool
					.isContains(html, "出錯的原因可能是", "您還沒有登錄或沒有權限，請聯繫管理員", "剛更新了程序，係統正在初始化", "當前正在訪問的人數太多，係統來不及處理當前請求")) {
				jsonObject.put("data", "定时检验出錯");
				return jsonObject;
			}
			if (StringTool.isContains(html, hostUrl, "auth")) {
				jsonObject.put("data", "账号已停押，请联系管理员");
				return jsonObject;
			}
			return resolvePage("定时检验",html);
		} catch (Exception e) {
			log.error("定时检验", e);
			shortSleep("定时检验");
			return checkout(httpConfig, hostUrl, userCode, ++index[0]);
		}
	}

	/**
	 * 获取结算页面
	 *
	 * @param httpConfig   http请求配置类
	 * @param hostUrl      主机地址
	 * @param userCode     用户code
	 * @param gamePlatform 游戏平台code
	 * @param date         请求日期
	 * @param pager        页面
	 * @param period       期数
	 * @param index        循环次数
	 * @return 结算页面
	 */
	public static JSONObject getHistory(HttpClientConfig httpConfig, String hostUrl, String userCode,
			String gamePlatform, String date, Object pager, String period, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = hostUrl + userCode + "klc/history/detail?_=" + System.currentTimeMillis() + "__ajax";
		Map<String, Object> data = new HashMap<>(5);
		data.put("date", date);
		data.put("platform", gamePlatform);
		data.put("pager", pager);
		data.put("status", "1");
		data.put("number", period.replace("-", ""));

		try {
			String html = HttpClientUtil.findInstance().postHtml(httpConfig.map(data).url(StringTool.format(url)));
			if (StringTool.isEmpty(html)) {
				log.info("获取结算页面为空");
				Thread.sleep(SLEEP);
				return getHistory(httpConfig, hostUrl, userCode, gamePlatform, date, pager, period, ++index[0]);
			}
			return resolvePage("获取结算信息",html);
		} catch (Exception e) {
			log.error("获取结算信息", e);
			shortSleep("获取结算信息");
			return getHistory(httpConfig, hostUrl, userCode, gamePlatform, date, pager, period, ++index[0]);
		}
	}

	// TODO Document解析页面函数

	/**
	 * 获取线路
	 *
	 * @param pageStr 页面字符串
	 * @return 线路列表
	 */
	public static List<String> findRoutes4Login(String pageStr) {
		Document document = Jsoup.parse(pageStr);
		if (!StringTool.isContains(document.title(), "线路选择")) {
			return null;
		}
		Elements uiList = document.select(".datalist>ul");
		Elements liList = uiList.get(0).select("li [href]");
		List<String> routes = new ArrayList<>(liList.size());
		for (Element element : liList) {
			routes.add(element.attr("href"));
		}
		//过滤一些不必要的线路
		return filterRoutes(routes);
	}

	/**
	 * 判断是否打开了登陆页面
	 *
	 * @param map 登陆界面信息
	 * @return 是否打开了登陆页面
	 */
	public static boolean isLoginPage(Map<String, Object> map) {
		String html = (String) map.get("html");
		Document document = Jsoup.parse(html);
		Element cid = document.selectFirst("div.sbt>input[name=cid]");
		return cid != null;
	}

	/**
	 * 获取登陆界面包含的数据
	 *
	 * @param loginHtml 登陆html界面
	 * @return 包含的所需要的数据
	 */
	public static Map<String, Object> findLoginDateMap4LoginPage(String loginHtml) {
		Document document = Jsoup.parse(loginHtml);
		//获取 script中包含的信息
		Map<String, Object> loginDateMap = getScriptInfo(document.selectFirst(("script")).html(), "version",
				"systemversion");
		if (ContainerTool.isEmpty(loginDateMap)) {
			return null;
		}
		//cid
		Element cid = document.selectFirst("div.sbt>input[name=cid]");
		//cname
		Element cname = document.selectFirst("div.sbt>input[name=cname]");
		loginDateMap.put("cid", cid.val());
		loginDateMap.put("cname", cname.val());
		return loginDateMap;
	}

	// TODO String功能函数
	/**
	 * 过滤不能访问的页面
	 *
	 * @param reRoutes 线路
	 * @return 线路
	 */
	public static List<String> filterRoutes(List<String> reRoutes) {
		// 过滤一些不必要的线路
		List<String> routes = new ArrayList<>();
		for (String route : reRoutes) {
			if (StringTool.contains(route, "sky339", "g9g9g")) {
				routes.add(route.replace("http://", "https://"));
			} else {
				routes.add(route);
			}
		}
		return routes;
	}

	/**
	 * 获取验证码地址
	 *
	 * @param hostUrl      登陆界面主机地址
	 * @param loginDateMap script数据信息
	 * @return 验证码地址
	 */
	public static String getValiImageUrl(String hostUrl, Map<String, Object> loginDateMap) {
		String key = "systemversion", valiCodeUrl;
		String result = null;
		//如果盘口服务器问题会造成死循环，建议改成重试多次更换其它的线路
		while (result == null) {
			try {
				valiCodeUrl = getValiCodeUrl(hostUrl, key, loginDateMap.get(key));
				result = HttpClientTool.doGet(valiCodeUrl);
			} catch (Exception e) {
				log.error("获取验证码地址失败", e);
			}
		}
		String[] results = result.split("_");
		loginDateMap.put("verifyvalue", results[1]);
		return hostUrl + "/getVcode/.auth?t=" + results[0] + "&" + key + "=" + loginDateMap.get(key) + "&.auth";
	}

	/**
	 * 组装验证code地址
	 *
	 * @param hostUrl 主机url地址
	 * @param key     验证key
	 * @param value   验证value
	 * @return 验证code地址
	 */
	public static String getValiCodeUrl(String hostUrl, String key, Object value) {
		return hostUrl + "/getCodeInfo/.auth?u=" + RandomTool.getDouble() + "&" + key + "=" + value + "&.auth";
	}

	/**
	 * 密码编码便于传输
	 *
	 * @param password 原密码
	 * @return 编码密码
	 */
	public static String codingPass(String password) {
		//符号字符串
		String symbolStr = "'`': '!V$', '-': '!m$', '=': '!k$', '[': '!O$', ']': '!K$', ';': '!I$', '\\'': '!S$', '\\\\': '!T$', '/': '!r$', '.': '!Z$', ',': '!a$', '~': '!i$', '!': '!p$', '@': '!f$', '#': '!7$', '$': '!D$', '%': '!l$', '^': '!9$', '&': '!q$', '*': '!t$', '(': '!6$', ')': '!g$', '_': '!v$', '+': '!J$', '{': '!L$', '}': '!d$', '|': '!W$', '\"': '!E$', ':': '!0$', '?': '!H$', '>': '!y$', '<': '!b$'";
		//符号映射map
		Map<String, Object> symbolMap = new HashMap<>(32);
		String[] symbolStrs = symbolStr.replace("\'", "").split(", ");
		String[] symbolInfo;
		//将符号映射放入map中
		for (String s : symbolStrs) {
			symbolInfo = s.split(": ");
			symbolMap.put(symbolInfo[0].trim(), symbolInfo[1].trim());
		}

		String[] originalPasswordsCharArr = password.split("");
		StringBuilder replacePassword = new StringBuilder();
		//拼装映射后的字符串
		for (String originalPasswordChr : originalPasswordsCharArr) {
			if (symbolMap.get(originalPasswordChr) != null) {
				replacePassword.append(symbolMap.get(originalPasswordChr));
			} else {
				replacePassword.append(originalPasswordChr);
			}
		}
		return String.valueOf(replacePassword);
	}

	/**
	 * 获取用户专属code
	 *
	 * @param loginInfo 登陆结果
	 * @return 用户专属code
	 */
	public static String getUserCode(String loginInfo) {
		String[] strs = loginInfo.split("\\r?\\n");
		return strs[0];
	}

	/**
	 * 获取script中的信息
	 *
	 * @param scriptStr script字符串
	 * @param strs      info
	 */
	private static Map<String, Object> getScriptInfo(String scriptStr, String... strs) {
		if (strs.length == 0) {
			return null;
		}
		Map<String, Object> loginDateMap = new HashMap<>(strs.length);
		for (String key : strs) {
			loginDateMap.put(key, StringUtils.substringBetween(scriptStr, "var " + key + " = \"", "\";"));
		}
		return loginDateMap;
	}
	/**
	 * 解析页面信息数据
	 *
	 * @param html 页面
	 * @return 页面数据
	 */
	private static JSONObject resolvePage(String msg, String html) {
		if (!html.contains("êêê")) {
			log.error("解析[".concat(msg).concat("]页面失败，页面为：").concat(html));
			return null;
		}
		//获取到第一行返回信息
		html = html.split("\\r?\\n")[0];
		//解码
		html = StringTool.unicode2String(html);
		//规范信息
		html = html.replaceAll("êêê", "");
		return JSONObject.fromObject(html);
	}

	/**
	 * 登陆错误
	 *
	 * @param loginInfo 登陆结果页面
	 * @return 错误信息
	 */
	public static IbmHcCodeEnum loginError(String loginInfo) {
		log.error("WS2盘口会员登陆异常，异常的登陆结果页面为：" + loginInfo);
		if (StringTool.isContains(loginInfo, "验证码")) {
			return IbmHcCodeEnum.IBM_403_VERIFY_CODE;
		} else if (StringTool.isContains(loginInfo, "停用")) {
			return IbmHcCodeEnum.IBM_403_USER_STATE;
		} else if (StringTool.isContains(loginInfo, "用户名或密码")) {
			return IbmHcCodeEnum.IBM_403_USER_ACCOUNT;
		} else if (StringTool.isContains(loginInfo, "事件編號")) {
			//亲爱的游戏玩家您好，由于您的异常操作以致无法提供服务给您！如有任何问题请联繫游戏客服
			return IbmHcCodeEnum.IBM_403_USER_TEMPORARY_STOP_STATE;
		} else {
			return IbmHcCodeEnum.IBM_403_UNKNOWN;
		}
	}

	/**
	 * 获取球码的投注code
	 *
	 * @param betItem 投注项
	 * @param oddInfo 赔率信息
	 * @return 装换后的投注信息
	 */
	public static JSONArray getBallBetCode(List<String> betItem, JSONObject oddInfo) {
		JSONArray betItems = new JSONArray();

		for (String bet : betItem) {
			JSONObject jsonObject = new JSONObject();
			String[] item = bet.split("#");
			jsonObject.put("item", item[0]);
			jsonObject.put("amount", item[1]);
			String code = Ws2Config.BALL_BET_CODE.get(item[0]);
			jsonObject.put("betCode", code);
			code = code.replace("|", "");
			jsonObject.put("oddCode", code);
			jsonObject.put("odd", oddInfo.getString(code));
			betItems.add(jsonObject);
		}
		return betItems;
	}

	/**
	 * 编码后的投注信息
	 *
	 * @param gameCode 盘口游戏code
	 * @param betItems 投注项
	 * @param oddInfo  赔率信息
	 * @return 转换后的投注信息
	 */
	public static JSONArray getBetCode(String gameCode, List<String> betItems, JSONObject oddInfo) {
		switch (gameCode) {
			case "pk":
			case "xyft":
				return getBallBetCode(betItems, oddInfo);
			default:
				throw new RuntimeException("未知的盘口游戏code：" + gameCode);
		}
	}

	/**
	 * 转换投注结果项
	 *
	 * @param resultItem 投注结果项
	 * @return 投注结果项
	 */
	public static String getBetResult(String resultItem) {
		return resultItem.replace(" ", "|").replace("冠軍", "第一名").replace("亞軍", "第二名").replace("單", "单")
				.replace("雙", "双").replace("龍", "龙").replace("冠亞", "冠亚");
	}

	/**
	 * 转换投注消息
	 *
	 * @param betMsg 投注消息
	 * @return 投注消息
	 */
	public static String getBetMsg(String betMsg) {
		switch (betMsg) {
			case "低於 『單注最低限額』":
				return "单注低于最低限额";
			case "纍計超過 『單期最高限額』":
				return "单期高于最高限额";
			case "超過 『單注最高限額』":
				return "单注高于最高限额";
			default:
				return betMsg;
		}
	}
	/**
	 * 获取投注信息
	 *
	 * @param msg 投注信息
	 * @return 投注信息
	 */
	public static String getBetMsgType(String msg) {
		switch (msg) {
			case "低於 『單注最低限額』":
				return IbmTypeEnum.FALSE.name();
			case "纍計超過 『單期最高限額』":
			case "超過 『單注最高限額』":
			default:
				return IbmTypeEnum.TRUE.name();
		}
	}

	/**
	 * 投注历史
	 *
	 * @param jsonAll     投注中找出的项目
	 * @param historyList 投注历史数据
	 */
	public static void betHistory(JSONArray jsonAll, List<Map<String, String>> historyList) {
		for (int i = 0; i < jsonAll.size(); i++) {
			JSONArray jsonItem = jsonAll.getJSONArray(i);
			Map<String, String> history = new HashMap<>(4);
			history.put("betNumber", StringTool.subLast(jsonItem.getString(0), 8));
			history.put("betResult", IbmTypeEnum.isWin(jsonItem.getString(8)));
			history.put("profit", jsonItem.getString(9));
			history.put("odds", jsonItem.getString(4));
			historyList.add(history);
		}
	}

	/**
	 * 获取返回信息
	 *
	 * @param betResult 投注结果
	 * @return 投注结果返回信息
	 */
	public static Map<String, List<Map<String, String>>> getResultData(JSONObject betResult) {
		Map<String, List<Map<String, String>>> resultData = new HashMap<>(3);
		JSONObject jsonUser = betResult.getJSONObject("data").getJSONObject("user");

		//投注成功
		if (ContainerTool.isContain(jsonUser, "suc_orders")) {
			JSONArray sucOrders = jsonUser.getJSONArray("suc_orders");
			log.trace("投注成功的项：" + sucOrders);
			String[] resultItems;
			List<Map<String, String>> successData = new ArrayList<>(sucOrders.size());
			for (int i = 0; i < sucOrders.size(); i++) {
				Map<String, String> success = new HashMap<>(4);
				JSONArray sucOrder = sucOrders.getJSONArray(i);
				resultItems = sucOrder.getString(1).split("\\|");
				success.put("betNumber", sucOrder.getString(0));
				success.put("betInfo", getBetResult(resultItems[0]).concat("#").concat(sucOrder.getString(2)));
				success.put("odds", resultItems[1]);
				success.put("profit", sucOrder.getString(3));
				successData.add(success);
			}
			resultData.put("successData", successData);
		}
		// 赔率失败,需要重新投注的-不作处理
		/*if (ContainerTool.isContain(jsonUser, "orders")) {
			JSONArray orders = jsonUser.getJSONArray("orders");
			log.info("赔率异常,需要重新投注的项" + orders);
			String[] resultItems;
			List<Map<String, String>> failData = new ArrayList<>(orders.size());
			for (int i = 0; i < orders.size(); i++) {
				Map<String, String> result = new HashMap<>(4);
				JSONArray failOrder = orders.getJSONArray(i);
				resultItems = failOrder.getString(1).split("\\|");
				result.put("betInfo", getBetResult(resultItems[0]).concat("#").concat(failOrder.getString(2)));
				failData.add(result);
			}
			resultData.put("failData", failData);
		}*/
		// 投注异常的
		if (ContainerTool.isContain(betResult, "errors")) {
			JSONArray errors = betResult.getJSONArray("errors");
			log.info("投注异常的的项" + errors);
			String[] resultItems;
			List<Map<String, String>> errorData = new ArrayList<>(errors.size());
			for (int i = 0; i < errors.size(); i++) {
				Map<String, String> result = new HashMap<>(4);
				JSONObject errorOrder = errors.getJSONObject(i);
				String note = errorOrder.getString("note");
				resultItems = note.split("\\|");
				result.put("item", getBetResult(resultItems[0]));
				result.put("msg", getBetMsg(resultItems[2]));
				result.put("type", getBetMsgType(resultItems[2]));
				errorData.add(result);
			}
			resultData.put("errorData", errorData);
		}
		return resultData;
	}
	/**
	 * 解析主页面
	 *
	 * @param homePage 主页面
	 * @return 主页面信息
	 */
	public static String getFirstLogin(String homePage) {
		Document document = Jsoup.parse(homePage);
		Elements body = document.select("body");

		return body.attr("firstlogin");
	}

	// TODO 功能函数
	/**
	 * 短睡眠
	 *
	 * @param msg 睡眠失败消息
	 */
	private static void shortSleep(String msg) {
		try {
			Thread.sleep(SHORT_SLEEP);
		} catch (InterruptedException e) {
			log.debug(msg.concat("睡眠失败"), e);
		}
	}
}
