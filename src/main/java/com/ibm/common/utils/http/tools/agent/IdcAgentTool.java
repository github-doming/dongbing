package com.ibm.common.utils.http.tools.agent;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: IDC盘口代理工具类
 * @Author: zjj
 * @Date: 2019-09-03 14:30
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IdcAgentTool {
	private static final long SHORT_SLEEP = 500L;
	private static final long SLEEP = 1000L;

	protected static final Logger log = LogManager.getLogger(IdcAgentTool.class);
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

			log.info("线路打开不是登陆页面" + result);
			Thread.sleep(SHORT_SLEEP);
			return getLoginData(httpConfig, url, ++index[0]);
		}
		try {
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
	 * 获取验证码code
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param index       循环次数
	 * @return 验证码code
	 */
	public static String getVerifyCode(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException, IOException {
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
	 * @param httpConfig    http请求配置类
	 * @param projectHost   主机地址
	 * @param loginDateMap  登陆页面数据信息
	 * @param agentAccount  代理账号
	 * @param agentPassword 代理密码
	 * @param verifyCode    验证码
	 * @param index         循环次数
	 * @return 登陆信息
	 */
	public static Map<String, String> getLogin(HttpClientConfig httpConfig, String projectHost,
			Map<String, String> loginDateMap, String agentAccount, String agentPassword, String verifyCode,
			int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		Map<String, String> data = new HashMap<>(2);
		try {
			Map<String, Object> join = new HashMap<>(6);
			join.put("__VIEWSTATE", loginDateMap.get("viewstate"));
			if (loginDateMap.containsKey("viewstategenerator")) {
				join.put("__VIEWSTATEGENERATOR", loginDateMap.get("viewstategenerator"));
			}
			join.put("__RequestVerificationToken", loginDateMap.get("requestVerificationToken"));
			join.put("txt_U_name", agentAccount);
			join.put("txt_U_Password", agentPassword);
			join.put("txt_validate", verifyCode);
			String url = projectHost + loginDateMap.get("action");
			HttpResult result = HttpClientUtil.findInstance().post(httpConfig.url(url).map(join));

			//没有读取到页面
			if (ContainerTool.isEmpty(result)) {
				log.error("读取页面失败");
				Thread.sleep(SLEEP);
				return getLogin(httpConfig, projectHost, loginDateMap, agentAccount, agentPassword, verifyCode,
						++index[0]);
			}
			if (302 - result.getStatusCode() == 0) {
				log.error("url= " + url);
				log.error("获取登陆信息异常302" + result);
				result = HttpClientUtil.findInstance()
						.get(httpConfig.url(StringTool.getHttpHost(projectHost) + result.getLocation()));
			}
			if (404 - result.getStatusCode() == 0) {
				log.error("url= " + url);
				log.error("获取登陆信息异常404" + result);
				Thread.sleep(2 * SLEEP);
				result = HttpClientUtil.findInstance()
						.post(httpConfig.url(projectHost + loginDateMap.get("action")).map(join));
			}
			//进入验证码不正确页面
			if (StringTool.contains(result.getHtml(), "验证码不正确", "验证码数据丢失")) {
				log.error("验证码不正确,验证码为【" + verifyCode + "】");
				Thread.sleep(SHORT_SLEEP);
				verifyCode = getVerifyCode(httpConfig, projectHost);
				return getLogin(httpConfig, projectHost, loginDateMap, agentAccount, agentPassword, verifyCode,
						++index[0]);
			}
			data.put("projectHost", projectHost);
			data.put("html", result.getHtml());
		} catch (Exception e) {
			log.error("获取登陆信息失败", e);
		}
		return data;
	}
	/**
	 * 获取登陆结果信息
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     url地址
	 * @param handicapCaptcha 验证码
	 * @param agentAccount    会员账号
	 * @param agentPassword   会员密码
	 * @param index           循环次数
	 * @return 登陆页面信息
	 */
	public static Map<String, String> getLogin(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
			String agentAccount, String agentPassword, int... index) throws InterruptedException, IOException {
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
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword, ++index[0]);
		}
		//获取线路失败,换另一条线路
		String loginSrc = getLoginSrc(loginHtml);
		if (StringTool.isEmpty(loginSrc)) {
			log.error("获取线路失败失败");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword, ++index[0]);
		}
		//获取登陆页面
		Map<String, String> loginDateMap = getLoginData(httpConfig, loginSrc);
		//登陆页面打开失败,换另一条线路
		if (ContainerTool.isEmpty(loginDateMap)) {
			log.error("登陆页面打开失败");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword, ++index[0]);
		}
		String projectHost = StringTool.projectHost(loginSrc);
		//获取图片验证码
		String verifyCode = getVerifyCode(httpConfig, projectHost);
		if (StringTool.isEmpty(verifyCode)) {
			log.error("获取图片验证码失败");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword, ++index[0]);
		}
		Map<String, String> loginInfo = getLogin(httpConfig, projectHost, loginDateMap, agentAccount, agentPassword,
				verifyCode);
		//失败页面
		if (ContainerTool.isEmpty(loginInfo) || StringTool.isEmpty(loginInfo.get("html"))) {
			log.error("登陆失败");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword, ++index[0]);
		}
		//验证码不正确
		String html = loginInfo.get("html").toString();
		if (StringTool.isEmpty(html) || StringTool.contains(html, "验证码不正确", "验证码数据丢失", "验证码不正确，请重新输入。")) {
			log.error("验证码不正确");
			log.error("验证码不正确,验证码为【" + verifyCode + "】");
			Thread.sleep(SHORT_SLEEP);
			return getLogin(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword, ++index[0]);
		}
		return loginInfo;
	}
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
		httpConfig.setHeader("Referer", projectHost.concat("/opadmin/main.aspx"));
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
	public static JSONArray getMemberList(HttpClientConfig httpConfig, String projectHost, String agentAccount,
			String curMemberName, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Referer", projectHost.concat("/opadmin/membermanage.aspx"));
		httpConfig.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("memberno", agentAccount);
		jsonObject.put("membername", curMemberName);
		jsonObject.put("gameno", "8");
		//启用状态=1，冻结=2，停用=3
		jsonObject.put("topeffective", "1");
		Map<String, Object> map = new HashMap<>(1);
		map.put("$ENTITY_JSON$", jsonObject);
		String html = HttpClientUtil.findInstance()
				.postHtml(httpConfig.url(projectHost + "/opadmin/membermanage.aspx/GetMembersAgent").map(map));
		if (StringTool.isEmpty(html)) {
			log.error("获取会员信息失败");
			Thread.sleep(SLEEP);
			return getMemberList(httpConfig, projectHost, agentAccount, curMemberName, ++index[0]);
		}
		return getMemberInfo(html);
	}

	/**
	 * 即时注单
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @return 即时注单
	 */
	public static JSONArray instantBet(HttpClientConfig httpConfig, String projectHost, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		try {
			String html = HttpClientUtil.findInstance().getHtml(httpConfig.url(projectHost + "/opadmin/odds_ploss.ashx?"
					+ "gameno=21&ps=601;602;611;612;621;622;631;632;636;637;638;603;604;605;606;613;614;615;616;623;624;625;"
					+ "626;633;634;635;607;608;609;610;617;618;619;620;627;628;629;630"));
			return getInstantBet(html);
		} catch (Exception e) {
			log.error("获取即时注单失败", e);
			Thread.sleep(SLEEP);
			return instantBet(httpConfig, projectHost, ++index[0]);
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

		JSONArray array = JSONObject.parseObject(html).getJSONArray("d");

		if (ContainerTool.isEmpty(array)) {
			return null;
		}

		JSONObject object = array.getJSONObject(1);

		if (ContainerTool.isEmpty(object)) {
			return null;
		}
		JSONArray rows = object.getJSONArray("Rows");

		JSONArray memberList = new JSONArray();
		JSONObject memberInfo = new JSONObject();
		for (Object obj : rows) {
			JSONObject jsonObject = (JSONObject) obj;
			memberInfo.put("account", jsonObject.getString("memberno"));
			memberInfo.put("nickName", jsonObject.getString("membername"));
			memberList.add(memberInfo);
		}
		return memberList;
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
	 * 获取登陆路径
	 *
	 * @param loginHtml 登陆页面
	 * @return 登陆路径
	 */
	public static String getLoginSrc(String loginHtml) {
		Document document = Jsoup.parse(loginHtml);
		Element iframeElement = document.selectFirst("iframe");
		if (iframeElement == null) {
			log.error("获取登陆路径错误=" + loginHtml);
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
	 * 获取即时注单
	 *
	 * @param html 即时注单 界面
	 * @return 即时注单
	 */
	private static JSONArray getInstantBet(String html) {
		html = html.substring(html.indexOf("[{wtno:"), html.lastIndexOf("},{ Rows: [{pageno:"));
		return JSON.parseArray(html);
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
}
