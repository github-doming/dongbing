package com.ibm.common.utils.http.utils.agent2;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.agent.IdcAgentTool;
import com.ibm.common.utils.http.tools.member.IdcMemberTool;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: IDC盘口代理的httpclient工具类
 * @Author: zjj
 * @Date: 2019-09-03 14:27
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IdcAgentUtil {
	protected Logger log = LogManager.getLogger(this.getClass());

	private static final Integer MAX_RECURSIVE_SIZE = 5;

	private Map<String, HttpClientConfig> hcConfigMap;
	/**
	 * 账户名-密码-网址-验证码
	 */
	private Map<String, Map<String, String>> accountMap;
	/**
	 * 用户信息-会员列表信息
	 */
	private Map<String, JSONArray> userMap;
	/**
	 * projectHost：主机地址
	 */
	private Map<String, Map<String, String>> agentMap;

	private static long checkLoginTime = 0;
	private static final long CHECK_LOGIN_TIME = 40 * 1000L;

	private static volatile IdcAgentUtil instance = null;

	public static IdcAgentUtil findInstance() {
		if (instance == null) {
			synchronized (IdcAgentUtil.class) {
				if (instance == null) {
					IdcAgentUtil instance = new IdcAgentUtil();
					// 初始化
					instance.init();
					IdcAgentUtil.instance = instance;
				}
			}
		}
		return instance;
	}
	private void init() {
		hcConfigMap = new HashMap<>(10);
		userMap = new HashMap<>(10);
		accountMap = new HashMap<>(10);
		agentMap = new HashMap<>(10);
	}

	/**
	 * 销毁工厂
	 */
	public static void destroy() {
		if (instance == null) {
			return;
		}
		if (ContainerTool.notEmpty(instance.hcConfigMap)) {
			for (HttpClientConfig clientConfig : instance.hcConfigMap.values()) {
				clientConfig.destroy();
			}
		}
		instance.userMap = null;
		instance.accountMap = null;
		instance.accountMap = null;
		instance = null;
	}
	/**
	 * 登陆
	 *
	 * @param existHaId       盘口会员存在id
	 * @param agentAccount    会员账号
	 * @param agentPassword   会员密码
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 */
	public JsonResultBeanPlus login(String existHaId, String agentAccount, String agentPassword, String handicapUrl,
									String handicapCaptcha) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//已存在数据
		if (agentMap.containsKey(existHaId)) {
			bean.setData(agentMap.get(existHaId));
			bean.success();
			return bean;
		}
		if (accountMap.containsKey(existHaId)) {
			Map<String, String> data = accountMap.get(existHaId);
			data.put("agentAccount", agentAccount);
			data.put("agentPassword", agentPassword);
			data.put("handicapUrl", handicapUrl);
			data.put("handicapCaptcha", handicapCaptcha);
		} else {
			Map<String, String> data = new HashMap<>(4);
			data.put("agentAccount", agentAccount);
			data.put("agentPassword", agentPassword);
			data.put("handicapUrl", handicapUrl);
			data.put("handicapCaptcha", handicapCaptcha);
			accountMap.put(existHaId, data);
		}
		try {
			//获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHaId);

			bean = login(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword);
			if (!bean.isSuccess()) {
				return bean;
			}
			Map<String, String> data = (Map<String, String>) bean.getData();
			agentMap.put(existHaId, data);
		} catch (Exception e) {
			log.error("IDC盘口代理【" + existHaId + "】登录失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 登录
	 *
	 * @param httpConfig      请求配置类
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param agentAccount    账号
	 * @param agentPassword   密码
	 * @return 登录结果
	 */
	public JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
			String agentAccount, String agentPassword) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//如果盘口地址不包含http://则添加
		String http = "http://";
		if (!handicapUrl.contains(http)) {
			handicapUrl = http.concat(handicapUrl);
		}
		try {
			Map<String, String> loginDateMap = null;
			for (int i = 0; i < 5; i++) {
				httpConfig.headers(null);
				httpConfig.httpContext(null);
				//登陆页面
				String loginHtml = IdcAgentTool.getLoginHtml(httpConfig, handicapUrl, handicapCaptcha);
				if (StringTool.isEmpty(loginHtml)) {
					log.info("获取登陆页面" + loginHtml);
					bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
					bean.putSysEnum(HcCodeEnum.CODE_403);
					return bean;
				}
				//获取线路
				String loginSrc = IdcAgentTool.getLoginSrc(loginHtml);
				if (StringTool.isEmpty(loginSrc)) {
					log.info("获取线路失败" + loginSrc);
					bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
					bean.putSysEnum(HcCodeEnum.CODE_404);
					return bean;
				}
				//登陆界面
				//登陆界面需要解析信息
				httpConfig.httpContext(HttpClientContext.create());
				loginDateMap = IdcAgentTool.getLoginData(httpConfig, loginSrc);
				if (ContainerTool.notEmpty(loginDateMap)) {
					break;
				}
			}
			if (ContainerTool.isEmpty(loginDateMap)) {
				log.info("获取登陆界面解析信息异常" + loginDateMap);
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String loginSrc = loginDateMap.get("loginSrc");
			String projectHost = StringTool.projectHost(loginSrc);

			//获取图片验证码
			String verifyCode = IdcAgentTool.getVerifyCode(httpConfig, projectHost);

			//登陆
			httpConfig.setHeader("Referer", loginSrc);
			Map<String, String> loginInfo = IdcAgentTool
					.getLogin(httpConfig, projectHost, loginDateMap, agentAccount, agentPassword, verifyCode);
			if (ContainerTool.isEmpty(loginInfo) || StringTool.isEmpty(loginInfo.get("html")) ) {
				log.info("获取登陆信息异常" + loginInfo);
				loginInfo = IdcAgentTool
						.getLogin(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword);
			}
			if (StringTool.isEmpty(loginInfo) || StringTool.isEmpty(loginInfo.get("html")) ) {
				log.info("获取登陆信息异常" + loginInfo);
				bean.putEnum(HcCodeEnum.IBS_403_LOGIN_FAIL);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			if (!StringTool.isContains(loginInfo.get("html"), "协议与规则")) {
				String html = loginInfo.get("html");
				log.info("获取异常的登陆结果页面" + html);
				if (StringTool.contains(html, "mypassword.aspx?type=1", "新密码")) {
					bean.putEnum(HcCodeEnum.IBS_403_CHANGE_PASSWORD);
					bean.putSysEnum(HcCodeEnum.CODE_403);
					return bean;
				}
				if (StringTool.isContains(html, "//<![CDATA[", "//]]>")) {
					html = html.substring(html.lastIndexOf("//<![CDATA["), html.lastIndexOf("//]]>"));
				}
				log.info("获取异常的登陆结果页面" + html);
				bean.putEnum(IdcMemberTool.loginError(html));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			httpConfig.setHeader("Referer", projectHost + "/ch/agreement.aspx");
			projectHost = loginInfo.get("projectHost");

			Map<String, String> data = new HashMap<>(1);
			data.put("projectHost", projectHost);

			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error("IDC盘口账号【" + agentAccount + "】登录失败,失败原因为：", e);
			bean.error(e.getMessage());
		} finally {
			httpConfig.defTimeOut();
		}
		return bean;
	}
	/**
	 * 校验登录
	 *
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param agentAccount    盘口账号
	 * @param agentPassword   盘口密码
	 * @return 校验登录结果
	 */
	public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String agentAccount,
			String agentPassword) {
		// 获取配置类
		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		JsonResultBeanPlus bean = login(httpConfig, handicapUrl, handicapCaptcha, agentAccount, agentPassword);
		if (bean.isSuccess()) {
			String existHaId = RandomTool.getNumLetter32();
			//存储账号信息
			Map<String, String> account = new HashMap<>(4);
			account.put("agentAccount", agentAccount);
			account.put("agentPassword", agentPassword);
			account.put("handicapUrl", handicapUrl);
			account.put("handicapCaptcha", handicapCaptcha);
			accountMap.put(existHaId, account);

			//存储爬虫信息
			Map<String, String> data = (Map<String, String>) bean.getData();
			agentMap.put(existHaId, data);
			hcConfigMap.put(existHaId, httpConfig);

			bean.setData(existHaId);
		}
		return bean;
	}
	/**
	 * 登陆
	 *
	 * @param existHaId 盘口会员存在id
	 */
	public JsonResultBeanPlus login(String existHaId) {
		synchronized (existHaId) {
			if (!agentMap.containsKey(existHaId)) {
				JsonResultBeanPlus bean = new JsonResultBeanPlus();
				if (!accountMap.containsKey(existHaId)) {
					bean.putEnum(HcCodeEnum.IBS_404_EXIST_INFO);
					bean.putSysEnum(HcCodeEnum.CODE_404);
					return bean;
				}
				Map<String, String> data = accountMap.get(existHaId);
				return login(existHaId, data.get("agentAccount"), data.get("agentPassword"), data.get("handicapUrl"),
						data.get("handicapCaptcha"));
			}
		}
		return new JsonResultBeanPlus().success();
	}
	/**
	 * 获取会员账号信息
	 *
	 * @param existHaId 已存在盘口代理id
	 * @return 会员账号信息
	 */
	public JsonResultBeanPlus memberListInfo(String existHaId) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		// 获取用户信息
		if (!agentMap.containsKey(existHaId)) {
			bean = login(existHaId);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		Map<String, String> accountInfo = agentMap.get(existHaId);
		if (ContainerTool.isEmpty(accountInfo)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		// 获取配置类
		HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHaId);
		JSONArray memberArray;
		try {
			String html = IdcAgentTool.getMemberManage(httpConfig, accountInfo.get("projectHost"));
			if (StringTool.isEmpty(html)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			String curmemberno = StringUtils.substringBetween(html, "var curmemberno = \"", "\";");
			String curmembername = StringUtils.substringBetween(html, "var curmembername = \"", "\";");
			memberArray = IdcAgentTool
					.getMemberList(httpConfig, accountInfo.get("projectHost"), curmemberno, curmembername);

			userMap.put(existHaId, memberArray);
			bean.success();
			bean.setData(memberArray);
		} catch (Exception e) {
			log.error("IDC盘口代理【" + existHaId + "】获取获取会员账号信息失败", e);
			agentMap.remove(existHaId);
		}
		return bean;
	}
	/**
	 * 获取会员列表信息
	 *
	 * @param existHaId 已存在盘口代理id
	 * @return 会员列表信息
	 */
	public JSONArray getMemberList(String existHaId) {
		if (userMap.containsKey(existHaId)) {
			return userMap.get(existHaId);
		}
		//获取用户信息
		JsonResultBeanPlus bean = memberListInfo(existHaId);
		//获取用户信息失败，返回空
		if (!bean.isSuccess()) {
			return null;
		}
		return userMap.get(existHaId);
	}

	/**
	 * 即时注单
	 * @param existHaId 已存在盘口代理id
	 * @return 即时注单
	 */
	public JsonResultBeanPlus instantBet(String existHaId) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		// 获取用户信息
		if (!agentMap.containsKey(existHaId)) {
			bean = login(existHaId);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		Map<String, String> accountInfo = agentMap.get(existHaId);
		if (ContainerTool.isEmpty(accountInfo)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(hcConfigMap, existHaId);
		try {
			JSONArray instantBet = IdcAgentTool.instantBet(httpConfig, accountInfo.get("projectHost"));
			bean.setData(instantBet);
		} catch (Exception e) {
			log.error("IDC盘口代理【" + existHaId + "】获取获取会员账号信息失败", e);
			agentMap.remove(existHaId);
		}
		return bean;
	}
}
