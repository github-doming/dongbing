package com.ibm.old.v1.common.doming.utils.http.httpclient.util;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.HandicapHttpClientTool;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.Ws2Tool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: WS2盘口的httpClient工具类
 * @Author: Dongming
 * @Date: 2018-12-08 16:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Ws2Util {
	protected Logger log = LogManager.getLogger(this.getClass());

	private static Map<String, HttpClientConfig> clientMap;
	private static Map<String, Map<String, String>> userMap;
	private static Map<String, Map<String, String>> dataMap;
	private static Map<String, Boolean> statusMap;
	private static Map<String, JsonResultBeanPlus> beanMap;
	private static long checkLoginTime = 0;
	private static final long CHECK_LOGIN_TIME = 40 * 1000L;

	private static volatile Ws2Util instance = null;
	public static Ws2Util findInstance() {
		if (instance == null) {
			synchronized (Ws2Util.class) {
				if (instance == null) {
					instance = new Ws2Util();
					// 初始化
					init();
				}
			}
		}
		return instance;
	}

	private static void init() {
		clientMap = new HashMap<>(10);
		userMap = new HashMap<>(10);
		dataMap = new HashMap<>(10);
		statusMap = new HashMap<>(10);
		beanMap = new HashMap<>(10);
	}

	/**
	 * 销毁工厂
	 */
	public static void destroy() {
		if (instance == null) {
			return;
		}
		if (ContainerTool.notEmpty(clientMap)) {
			for (HttpClientConfig clientConfig : clientMap.values()) {
				clientConfig.destroy();
			}

		}
		clientMap.clear();
		clientMap = null;
		userMap.clear();
		userMap = null;
		dataMap.clear();
		dataMap = null;
		statusMap.clear();
		statusMap = null;
		beanMap.clear();
		beanMap = null;
		instance = null;
	}

	// TODO 功能
	/**
	 * 登陆
	 *
	 * @param existHmId       盘口会员存在id
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 * @param memberAccount   会员账号
	 * @param memberPassword  会员密码
	 */
	public JsonResultBeanPlus login(String existHmId, String handicapUrl, String handicapCaptcha, String memberAccount,
			String memberPassword) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		Map<String, String> data = new HashMap<>(4);
		data.put("handicapUrl", handicapUrl);
		data.put("handicapCaptcha", handicapCaptcha);
		data.put("memberAccount", memberAccount);
		data.put("memberPassword", memberPassword);
		dataMap.put(existHmId, data);
		try {
			// 获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);
			httpConfig.headers(null);
			httpConfig.httpContext(null);

			bean = login(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword);

			if (!bean.isSuccess()) {
				return bean;
			}
			Map<String, String> userData = (Map<String, String>) bean.getData();
			userMap.put(existHmId, userData);
		} catch (Exception e) {
			log.error("WS2盘口会员【" + existHmId + "】登录失败,失败原因为：", e);
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
	 * @param memberAccount   盘口账号
	 * @param memberPassword  盘口密码
	 * @return
	 */
	public JsonResultBeanPlus login(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
			String memberAccount, String memberPassword) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//如果盘口地址不包含http://则添加
		String http = "http://";
		if (!handicapUrl.contains(http)) {
			handicapUrl = http.concat(handicapUrl);
		}
		try {
			//选择线路页面
			String selectRoutePage = Ws2Tool.getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha);
			if (StringTool.isEmpty(selectRoutePage)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_NAVIGATE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			// 获取线路
			List<String> routes = Ws2Tool.findRoutes4Login(selectRoutePage);
			if (ContainerTool.isEmpty(routes)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_ROUTE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			// 设置重定向地址为 登陆地址
			httpConfig.setHeader("Referer", handicapUrl);
			// 登陆界面
			Map<String, Object> loginPage = Ws2Tool.getLoginPage(httpConfig, routes);
			if (ContainerTool.isEmpty(loginPage)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			// 登陆界面需要解析信息
			Map<String, Object> loginDateMap = Ws2Tool.findLoginDateMap4LoginPage(loginPage.get("html").toString());
			if (ContainerTool.isEmpty(loginDateMap)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			String hostUrl = StringTool.getHttpHost(loginPage.get("url").toString());
			// 获取图片验证码
			String verifyCode = Ws2Tool.getVerifyCodeByContent(httpConfig, hostUrl, loginDateMap);
			// 重定向 - 刷新头信息
			httpConfig.setHeader("Referer", (String) loginPage.get("url"));
			httpConfig.httpContext(HttpClientContext.create());
			// 登陆
			String loginInfo = Ws2Tool
					.getLogin(httpConfig, hostUrl, loginDateMap, memberAccount, memberPassword, verifyCode);
			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			if (StringTool.isContains(loginInfo, "尊敬的会员,此账户已经被停用,请与管理员联系。")) {
				log.error("登录错误信息：" + loginInfo);
				bean.putEnum(IbmHcCodeEnum.IBM_403_USER_STATE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			if (!StringTool.isContains(loginInfo, "://host")) {
				bean.putEnum(Ws2Tool.loginError(loginInfo));
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			String userCode = Ws2Tool.getUserCode(loginInfo);
			// 刷新头信息
			httpConfig.setHeader("Referer", hostUrl + userCode + ".auth");

			// 获取登入页面信息
			String homePage = Ws2Tool.getHomePage(httpConfig, hostUrl, loginInfo);
			//判断是否等一次登录盘口
			if (StringTool.isEmpty(homePage) || "1".equals(Ws2Tool.getFirstLogin(homePage))) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_CHANGE_PASSWORD);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			httpConfig.setHeader("Referer", hostUrl + userCode + "index.htm?" + loginDateMap.get("version"));

			Map<String, String> userData = new HashMap<>(2);
			userData.put("hostUrl", hostUrl);
			userData.put("userCode", userCode);
			bean.setData(userData);
			bean.success();
		} catch (Exception e) {
			log.error("WS2盘口账号【" + memberAccount + "】登录失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	/**
	 * 校验登录
	 *
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param memberAccount   盘口账号
	 * @param memberPassword  盘口密码
	 * @return
	 */
	public JsonResultBeanPlus login(String handicapUrl, String handicapCaptcha, String memberAccount,
			String memberPassword) {
		// 获取配置类
		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		httpConfig.headers(null);
		httpConfig.httpContext(null);

		return login(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
	}

	/**
	 * 登陆
	 *
	 * @param existHmId 盘口会员存在id
	 */
	public JsonResultBeanPlus login(String existHmId) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		/*
			1. 开始登陆成功
				1.1 执行登陆
				1.2 登陆结果写入
			2. 开始登陆失败
				2.1 等待300毫秒
				2.2 重新校验是否登陆结束
				2.3 获取登陆结果
				2.4 如果SysCode 为404 的时候重新登陆，其他的情况直接返回
		 */
		if (HandicapHttpClientTool.startLogin(statusMap, beanMap, userMap, existHmId)) {
			if (!dataMap.containsKey(existHmId)) {
				bean.setData(existHmId);
				bean.putEnum(IbmHcCodeEnum.IBM_404_EXIST_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			Map<String, String> data = dataMap.get(existHmId);
			bean = login(existHmId, data.get("handicapUrl"), data.get("handicapCaptcha"), data.get("memberAccount"),
					data.get("memberPassword"));
			if (!HandicapHttpClientTool.finishLogin(statusMap, beanMap, existHmId, bean)) {
				log.error("WS2盘口会员【" + existHmId + "】结束登陆失败，正在重试，登陆的结果为=" + bean.toJsonString());
				statusMap.put(existHmId, true);
				beanMap.put(existHmId, bean);
			}
		} else {
			do {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					log.info("WS2盘口会员【" + existHmId + "】等待其他线程登陆的线程出错，错误原因为=" + e.getMessage() + "，正在退出等待");
					break;
				}
			} while (HandicapHttpClientTool.isLogging(statusMap, existHmId));
			bean = beanMap.get(existHmId);
			if (IbmHcCodeEnum.CODE_404.getCode().equals(bean.getCodeSys())) {
				log.info("WS2盘口会员【" + existHmId + "】其他线程登陆的结果为=" + bean.toJsonString() + ",现正在重新登陆");
				return login(existHmId);
			}
		}
		return bean;
	}

	/**
	 * 用户基本信息
	 *
	 * @param existHmId 已存在id
	 * @param gameCode  游戏code
	 * @return 用户基本信息
	 */
	public JsonResultBeanPlus userInfo(String existHmId, String gameCode) {
		JsonResultBeanPlus bean = loggingIn(existHmId);
		if (!bean.isSuccess()) {
			return bean;
		}
		bean = new JsonResultBeanPlus();
		// 获取用户信息
		Map<String, String> data = userMap.get(existHmId);
		if (ContainerTool.isEmpty(data)) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		try {
			// 获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);
			JSONObject userInfo = Ws2Tool.getUserInfo(httpConfig, gameCode, data.get("hostUrl"), data.get("userCode"));
			log.trace("WS2盘口会员【" + existHmId + "】用户基本信息为：" + userInfo);
			if (ContainerTool.isEmpty(userInfo) || !userInfo.containsKey("data")) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			} else if ("您已經在其它地方登錄".equals(userInfo.get("data"))) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			if (ContainerTool.notEmpty(userInfo.get("data")) && ContainerTool
					.notEmpty(userInfo.getJSONObject("data").get("user"))) {
				JSONObject userObj = userInfo.getJSONObject("data").getJSONObject("user");
				data.put("re_credit", userObj.getString(""));
				data.put("account", userObj.getString(""));
				data.put("credit", userObj.getString(""));
				//会员账户
				data.put("memberAccount", userInfo.getString("account"));
				//TODO 会员盘
				data.put("memberType","");
				//信用额度
				data.put("creditQuota", userInfo.getString("re_credit"));
				//TODO 可用额度
				data.put("usedQuota", "0");
				//TODO 使用金额
				data.put("usedAmount", "0");
				//TODO 盈亏金额
				data.put("profitAmount",  "0");
			}

			bean.success();
			bean.setData(data);
		} catch (Exception e) {
			log.error("WS2盘口会员【" + existHmId + "】获取基本信息异常，失败原因为:", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	/**
	 * 获取用户信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param game      盘口游戏code
	 * @return
	 */
	public Map<String, String> getUserInfo(String existHmId, String game) {
		Map<String, String> data = userMap.get(existHmId);
		if (ContainerTool.isEmpty(data) || StringTool.isEmpty(data.get("usedQuota"))) {
			JsonResultBeanPlus bean = userInfo(existHmId, game);
			if (!bean.isSuccess()) {
				log.error("WS2盘口会员【" + existHmId + "】获取用户信息异常，失败原因为:" + bean.toJsonString());
				return null;
			}
			data = userMap.get(existHmId);
		}
		return data;
	}

	/**
	 * 投注基本信息
	 *
	 * @param existHmId   已存在id
	 * @param gameCode    游戏code
	 * @param gameBetCode 游戏投注code
	 * @param type			 执行状态
	 * @return 投注基本信息
	 */
	public JsonResultBeanPlus betInfo(String existHmId, String gameCode, String gameBetCode, IbmStateEnum type) {
		JsonResultBeanPlus bean = loggingIn(existHmId);
		if (!bean.isSuccess()) {
			return bean;
		}
		bean = new JsonResultBeanPlus();
		// 获取用户信息
		Map<String, String> data = userMap.get(existHmId);
		if (ContainerTool.isEmpty(data)) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		try {
			// 获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);
			JSONObject betInfo = Ws2Tool
					.getBetInfo(httpConfig, gameCode, gameBetCode, data.get("hostUrl"), data.get("userCode"),type);
			log.trace("WS2盘口会员【" + existHmId + "】投注基本信息为：" + betInfo);
			if (StringTool.isEmpty(betInfo)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_BET_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			bean.success();
			bean.setData(betInfo);
		} catch (Exception e) {
			log.error("WS2盘口会员【" + existHmId + "】获取投注基本信息失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 获取游戏限额信息
	 *
	 * @param existHmId 已存在id
	 * @param gameCode  游戏code
	 * @return 限额信息
	 */
	public JsonResultBeanPlus gameLimit(String existHmId, String gameCode) {
		JsonResultBeanPlus bean = loggingIn(existHmId);
		if (!bean.isSuccess()) {
			return bean;
		}
		bean = new JsonResultBeanPlus();
		// 获取用户信息
		Map<String, String> data = userMap.get(existHmId);
		if (ContainerTool.isEmpty(data)) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		try {
			// 获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);
			JSONObject userInfo = Ws2Tool.getUserInfo(httpConfig, gameCode, data.get("hostUrl"), data.get("userCode"));
			if (ContainerTool.isEmpty(userInfo)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_BET_LIMIT);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			} else if ("您已經在其它地方登錄".equals(userInfo.get("data"))) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			JSONObject gameLimit = userInfo.getJSONObject("data").getJSONObject("user").getJSONObject("game_limit");
			log.trace("WS2盘口会员【" + existHmId + "】游戏限额信息为：" + gameLimit);
			bean.setData(gameLimit);
			bean.success();
		} catch (Exception e) {
			log.error("WS2盘口会员【" + existHmId + "】获取游戏限额信息失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 投注
	 *
	 * @param existHmId 已存在id
	 * @param gameCode  游戏code
	 * @param betCode   投注code
	 * @param betItems  投注项
	 * @param odds      赔率信息
	 * @param version   版本号
	 * @return 投注结果
	 */
	public JsonResultBeanPlus betting(String existHmId, String gameCode, String betCode, List<String> betItems,JSONObject odds,String version) {
		JsonResultBeanPlus bean = loggingIn(existHmId);
		if (!bean.isSuccess()) {
			return bean;
		}
		bean = new JsonResultBeanPlus();
		// 获取用户信息
		Map<String, String> data = userMap.get(existHmId);
		if (ContainerTool.isEmpty(data)) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);
		try {
			if(ContainerTool.isEmpty(odds)||StringTool.isEmpty(version)){
				//获取赔率信息
				bean = betInfo(existHmId, gameCode, betCode,IbmStateEnum.BET);
				if (!bean.isSuccess()) {
					log.error("盘口会员【" + existHmId + "】获取赔率信息失败,失败原因为：", bean.toJsonString());
					return bean;
				}
				bean.setSuccess(false);
				JSONObject betInfoData = JSONObject.fromObject(bean.getData()).getJSONObject("data");
				odds=betInfoData.getJSONObject("integrate");
				version=betInfoData.getString("version_number");
			}

			//编码后的投注信息
			JSONArray betCodes = Ws2Tool.getBetCode(gameCode, betItems, odds);

			httpConfig.httpTimeOut(20 * 1000);
			//投注
			JSONObject betResult = Ws2Tool
					.betting(httpConfig, data.get("hostUrl"), data.get("userCode"), gameCode, betCodes,
							version);
			//投注结果
			log.trace("盘口会员【" + existHmId + "】投注结果为：" + betResult);
			if (ContainerTool.isEmpty(betResult)||StringTool.contains(betResult.toString(),"網絡繁忙，請稍後再試")) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			//投注结果返回信息
			Map<String, List<Map<String, String>>> resultData = Ws2Tool.getResultData(betResult);
			bean.success();
			bean.setData(resultData);
		} catch (Exception e) {
			log.error("Ws2盘口会员【" + existHmId + "】投注失败,失败原因为：", e);
			bean.error(e.getMessage());
		}finally {
			httpConfig.defTimeOut();
		}
		return bean;
	}

	/**
	 * 投注盈利信息
	 *
	 * @param existHmId    已存在id
	 * @param gamePlatform 游戏平台code
	 * @param date         查找的日期
	 * @param period       期数
	 * @return 结果信息
	 */
	public JsonResultBeanPlus betProfit(String existHmId, String gamePlatform, String date, String period) {
		JsonResultBeanPlus bean = loggingIn(existHmId);
		if (!bean.isSuccess()) {
			return bean;
		}
		bean = new JsonResultBeanPlus();
		// 获取用户信息
		Map<String, String> data = userMap.get(existHmId);
		if (ContainerTool.isEmpty(data)) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		try {
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);
			JSONObject jsonProfit = Ws2Tool
					.getHistory(httpConfig, data.get("hostUrl"), data.get("userCode"), gamePlatform, date, "1", period);
			if (ContainerTool.isEmpty(jsonProfit)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_RESULT_PAGE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			JSONObject jsonData = jsonProfit.getJSONObject("data");
			if (!jsonData.getBoolean("success")) {
				log.error("WS2盘口会员【" + existHmId + "】获取success的结果信息错误：" + jsonData);
				bean.putEnum(IbmHcCodeEnum.IBM_403_PAGE_FAIL);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			JSONArray jsonAll = jsonData.getJSONArray("all");
			if (ContainerTool.isEmpty(jsonAll)) {
				log.info("WS2盘口会员【" + existHmId + "】没有获取到投注结果信息：" + jsonData);
				bean.putEnum(IbmHcCodeEnum.IBM_403_PAGE_FAIL);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			List<Map<String, String>> historyList = new ArrayList<>();
			jsonAll = jsonData.getJSONArray("all");
			log.trace("WS2盘口会员【" + existHmId + "】投注结果信息为：" + jsonAll);
			Ws2Tool.betHistory(jsonAll, historyList);
			int total = jsonData.getJSONObject("pager").getInt("total");
			//循环获取多页
			for (int i = 2; i < total; i++) {
				jsonProfit = Ws2Tool
						.getHistory(httpConfig, data.get("hostUrl"), data.get("userCode"), gamePlatform, date, i,
								period);
				if (ContainerTool.isEmpty(jsonProfit)) {
					continue;
				}
				// 放入历史数据
				jsonAll = jsonProfit.getJSONObject("data").getJSONArray("all");
				if (ContainerTool.isEmpty(jsonAll)) {
					i--;
					continue;
				}
				log.trace("WS2盘口会员【" + existHmId + "】投注结果信息为：" + jsonAll);
				Ws2Tool.betHistory(jsonAll, historyList);
			}
			bean.setData(historyList);
			bean.success();
		} catch (Exception e) {
			log.error("WS2盘口会员【" + existHmId + "】投注结果失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 校验用户
	 *
	 * @param existHmId 已存在盘口会员id
	 */
	public JsonResultBeanPlus checkUser(String existHmId) {
		JsonResultBeanPlus bean = loggingIn(existHmId);
		if (!bean.isSuccess()) {
			return bean;
		}
		bean = new JsonResultBeanPlus();
		// 获取用户信息
		Map<String, String> data = userMap.get(existHmId);
		if (ContainerTool.isEmpty(data)) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		try {
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);
			JSONObject checkInfo = Ws2Tool.checkout(httpConfig, data.get("hostUrl"), data.get("userCode"));
			log.trace("WS2盘口会员【" + existHmId + "】检验结果为：" + checkInfo);
			//检验失败
			if (ContainerTool.isEmpty(checkInfo)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_CHECK_CODE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			if ("检验异常，请重新登录！".equals(checkInfo.get("data"))) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_CHECK_CODE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			} else if ("您已經在其它地方登錄".equals(checkInfo.get("data"))) {
				bean.setData("您已經在其它地方登錄");
				bean.putEnum(IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			} else if ("用户名或密码不正确".equals(checkInfo.get("data"))) {
				bean.setData("用户名或密码不正确");
				bean.putEnum(IbmHcCodeEnum.IBM_403_USER_ACCOUNT);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			} else if ("定时检验失败".equals(checkInfo.get("data"))) {
				bean.setData("定时检验失败");
				bean.putEnum(IbmHcCodeEnum.IBM_403_TIMING_CHECKOUT);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			} else if ("定时检验出錯".equals(checkInfo.get("data"))) {
				bean.setData("定时检验出錯");
				bean.success();
				return bean;
			}else if("账号已停押，请联系管理员".equals(checkInfo.get("data"))){
				bean.setData("账号已停押，请联系管理员");
				bean.putEnum(IbmHcCodeEnum.IBM_403_USER_BAN_BET);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			bean.setData(checkInfo);
			if (checkInfo.getInt("state") != 1) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_CHECK_CODE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			bean.success();
		} catch (Exception e) {
			log.error("WS2盘口会员【" + existHmId + "】定时检验失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 首次登陆
	 *
	 * @param existHmId 盘口会员存在id
	 * @param bean      登陆结果bean
	 */
	public void firstLogin(String existHmId, JsonResultBeanPlus bean) {
		statusMap.put(existHmId, true);
		beanMap.put(existHmId, bean);
	}

	/**
	 * 登陆中
	 *
	 * @param existHmId 存在盘口会员Id
	 * @return 登陆结果
	 */
	private JsonResultBeanPlus loggingIn(String existHmId) {
		// 现在正在执行的状态
		while (HandicapHttpClientTool.isLogging(statusMap, existHmId)) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				log.info("WS2盘口会员【" + existHmId + "】等待其他线程登陆的线程出错，错误原因为=" + e.getMessage() + "，正在退出等待");
				break;
			}
		}
		//用户执行状态
		JsonResultBeanPlus bean = beanMap.get(existHmId);
		if (IbmHcCodeEnum.CODE_404.getCode().equals(bean.getCodeSys())) {
			log.info("WS2盘口会员【" + existHmId + "】其他线程登陆的结果为=" + bean.toJsonString() + ",现正在重新登陆");
			bean = login(existHmId);
		}
		return bean;
	}

	/**
	 * 清除该盘口会员用户数据
	 *
	 * @param existHmId 已存在盘口会员id
	 */
	public void removeExistHmInfo(String existHmId) {
		if (StringTool.isEmpty(existHmId)) {
			return;
		}
		userMap.remove(existHmId);
		clientMap.remove(existHmId);
		dataMap.remove(existHmId);
		statusMap.remove(existHmId);
		beanMap.remove(existHmId);
	}

	/**
	 * 定时检验 专有登录方法
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return 登录结果
	 */
	public JsonResultBeanPlus checkLogin(String existHmId) {
		synchronized (Ws2Util.class) {
			if ((System.currentTimeMillis() - checkLoginTime) < CHECK_LOGIN_TIME) {
				return null;
			}
			checkLoginTime = System.currentTimeMillis();
		}
		return login(existHmId);

	}
}
