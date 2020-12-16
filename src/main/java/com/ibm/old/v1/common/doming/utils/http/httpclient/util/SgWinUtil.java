package com.ibm.old.v1.common.doming.utils.http.httpclient.util;
import com.ibm.old.v1.common.doming.configs.SgWinConfig;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.HandicapHttpClientTool;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.SgWinTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: SgWin盘口的httpClient工具类
 * @Author: zjj
 * @Date: 2019-08-08 17:04
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class SgWinUtil {

	protected Logger log = LogManager.getLogger(this.getClass());

	private static Map<String, HttpClientConfig> clientMap;
	private static Map<String, Map<String, String>> userMap;
	private static Map<String, Map<String, String>> dataMap;
	private static Map<String, Boolean> statusMap;
	private static Map<String, JsonResultBeanPlus> beanMap;

	private static volatile SgWinUtil instance = null;

	public static SgWinUtil findInstance() {
		if (instance == null) {
			synchronized (Ws2Util.class) {
				if (instance == null) {
					instance = new SgWinUtil();
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
		//已存在数据
		if (userMap.containsKey(existHmId)) {
			bean.setData(userMap.get(existHmId));
			bean.success();
			return bean;
		}
		if (dataMap.containsKey(existHmId)) {
			Map<String, String> data = dataMap.get(existHmId);
			data.put("memberAccount", memberAccount);
			data.put("memberPassword", memberPassword);
			data.put("handicapUrl", handicapUrl);
			data.put("handicapCaptcha", handicapCaptcha);
		} else {
			Map<String, String> data = new HashMap<>(4);
			data.put("memberAccount", memberAccount);
			data.put("memberPassword", memberPassword);
			data.put("handicapUrl", handicapUrl);
			data.put("handicapCaptcha", handicapCaptcha);
			dataMap.put(existHmId, data);
		}
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
			log.error("SgWin盘口会员【" + existHmId + "】登录失败,失败原因为：", e);
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
		String http = "http";
		if (!handicapUrl.contains(http)) {
			handicapUrl = http.concat("://").concat(handicapUrl);
		}
		try {
			//获取线路选择页面
			String routeHtml = SgWinTool.getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha);

			if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "线路选择")) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_NAVIGATE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			//4条会员线路数组
			String[] routes = SgWinTool.getMemberRoute(httpConfig, routeHtml, SgWinConfig.HANDICAP_MEMBER);

			if (ContainerTool.isEmpty(routes)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_ROUTE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			//获取登录信息map
			Map<String, String> loginInfoMap = SgWinTool.getLoginHtml(httpConfig, routes);
			if (ContainerTool.isEmpty(loginInfoMap)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			String loginSrc = loginInfoMap.get("loginSrc");
			//获取验证码
			String verifyCode = SgWinTool.getVerifyCode(httpConfig, loginSrc, loginInfoMap.remove("code"));

			httpConfig.setHeader("Referer", loginSrc.concat("/login"));
			httpConfig.httpContext(HttpClientContext.create());

			//盘口协议页面
			String loginInfo = SgWinTool.getLogin(httpConfig, memberAccount, memberPassword, verifyCode, loginSrc,
					loginInfoMap.remove("action"),SgWinConfig.HANDICAP_MEMBER);

			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			//错误处理和是否等一次登录盘口
			if (StringTool.contains(loginInfo, "alert", "修改密码")) {
				bean.putEnum(SgWinTool.loginError(loginInfo));
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			Map<String, String> userData = new HashMap<>(3);

			String indexHref = SgWinTool.getAgreementHref(loginInfo);
			//获取盘口主页面,未处理页面
			String account = SgWinTool.getHomePage(httpConfig, loginSrc, indexHref);

			if (StringTool.notEmpty(account)) {
				userData.put("memberType", account.split(" ")[1]);
			}

			userData.put("projectHost", loginSrc);
			userData.put("memberAccount", memberAccount);

			bean.setData(userData);
			bean.success();
		} catch (Exception e) {
			log.error("SgWin盘口账号【" + memberAccount + "】登录失败,失败原因为：", e);
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
				log.error("SgWin盘口会员【" + existHmId + "】结束登陆失败，正在重试，登陆的结果为=" + bean.toJsonString());
				statusMap.put(existHmId, true);
				beanMap.put(existHmId, bean);
			}
		} else {
			do {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					log.info("SgWin盘口会员【" + existHmId + "】等待其他线程登陆的线程出错，错误原因为=" + e.getMessage() + "，正在退出等待");
					break;
				}
			} while (HandicapHttpClientTool.isLogging(statusMap, existHmId));
			bean = beanMap.get(existHmId);
			if (IbmHcCodeEnum.CODE_404.getCode().equals(bean.getCodeSys())) {
				log.info("SgWin盘口会员【" + existHmId + "】其他线程登陆的结果为=" + bean.toJsonString() + ",现正在重新登陆");
				return login(existHmId);
			}
		}
		return bean;
	}

	/**
	 * 用户基本信息
	 *
	 * @param existHmId 已存在id
	 * @return 用户基本信息
	 */
	public JsonResultBeanPlus userInfo(String existHmId) {
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
			//获取四条用户信息中限制额度大于0的用户信息
			//后续判断是否直接取第一条快彩的用户信息
			JSONObject userInfo = SgWinTool.getUserInfo(httpConfig, data.get("projectHost"));
			log.trace("SgWin盘口会员【" + existHmId + "】用户基本信息为：" + userInfo);

			if (ContainerTool.isEmpty(userInfo)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}

			//会员账户,会员盘已存在，不在获取
			//信用额度
			data.put("creditQuota", String.valueOf(userInfo.getDouble("maxLimit")));
			//可用额度
			data.put("usedQuota", String.valueOf(userInfo.getDouble("balance")));
			//使用金额
			data.put("usedAmount", String.valueOf(
					(userInfo.containsKey("result") ? userInfo.getDouble("result") : 0) - (userInfo
							.containsKey("betting") ? userInfo.getDouble("betting") : 0)));
			//盈亏额度
			data.put("profitQuota", String.valueOf(userInfo.getDouble("maxLimit") + (userInfo.containsKey("result") ?
					userInfo.getDouble("result") :
					0)));
			//盈亏金额
			data.put("profitAmount",
					String.valueOf((userInfo.containsKey("result") ? userInfo.getDouble("result") : 0)));

			bean.success();
			bean.setData(data);
		} catch (Exception e) {
			log.error("SgWin盘口会员【" + existHmId + "】获取基本信息异常，失败原因为:", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	/**
	 * 获取用户信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return
	 */
	public Map<String, String> getUserInfo(String existHmId) {
		Map<String, String> data = userMap.get(existHmId);
		if (ContainerTool.isEmpty(data) || StringTool.isEmpty(data.get("usedQuota"))) {
			JsonResultBeanPlus bean = userInfo(existHmId);
			if (!bean.isSuccess()) {
				log.error("SgWin盘口会员【" + existHmId + "】获取用户信息异常，失败原因为:" + bean.toJsonString());
				return null;
			}
			data = userMap.get(existHmId);
		}
		return data;
	}

	/**
	 * 游戏基本信息
	 *
	 * @param existHmId 已存在id
	 * @param gameCode  游戏code
	 * @return 投注基本信息
	 */
	public JsonResultBeanPlus gameInfo(String existHmId, String gameCode) {
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

			JSONObject betInfo = SgWinTool.getPeriod(httpConfig, data.get("projectHost"), gameCode);
			log.trace("SgWin盘口会员【" + existHmId + "】投注基本信息为：" + betInfo);
			if (StringTool.isEmpty(betInfo)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_BET_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			bean.success();
			bean.setData(betInfo);
		} catch (Exception e) {
			log.error("SgWin盘口会员【" + existHmId + "】获取游戏基本信息失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	/**
	 * 获取游戏赔率信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  盘口游戏code
	 * @param oddsCode  赔率code
	 * @return 赔率信息
	 */
	public JsonResultBeanPlus oddsInfo(String existHmId, String gameCode, String oddsCode) {
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
			JSONObject odds = SgWinTool.getOdds(httpConfig, data.get("projectHost"), gameCode, oddsCode);
			if (ContainerTool.isEmpty(odds)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_BET_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			bean.success();
			bean.setData(odds);
		} catch (Exception e) {
			log.error("SgWin盘口会员【" + existHmId + "】获取投注基本信息失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;

	}
	/**
	 * 投注
	 *
	 * @param existHmId  已存在id
	 * @param game       盘口游戏code
	 * @param drawNumber 开奖期数
	 * @param betItems   投注项
	 * @param odds       赔率
	 * @return
	 */
	public JsonResultBeanPlus betting(String existHmId, String game, String drawNumber, List<String> betItems,
			JSONObject odds) {
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
			httpConfig.httpTimeOut(20 * 1000);
			//获取投注信息
			JSONArray betsArray = SgWinTool.getBetInfo(game, betItems, odds);
			if (ContainerTool.isEmpty(betsArray)) {
				log.info("投注信息为空", betsArray);
				bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			JSONObject betResult = SgWinTool.betting(httpConfig, data.get("projectHost"), betsArray, drawNumber, game);
			//投注结果
			log.trace("盘口会员【" + existHmId + "】投注结果为：" + betResult);
			if (ContainerTool.isEmpty(betResult)) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			if (betResult.containsKey("data")) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_USER_BAN_BET);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			JSONObject memberInfo = betResult.getJSONObject("account");

			//信用额度
			data.put("creditQuota", String.valueOf(memberInfo.getDouble("maxLimit")));
			//可用额度
			data.put("usedQuota", String.valueOf(memberInfo.getDouble("balance")));
			//使用金额
			data.put("usedAmount", String.valueOf(
					(memberInfo.containsKey("result") ? memberInfo.getDouble("result") : 0) - (memberInfo
							.containsKey("betting") ? memberInfo.getDouble("betting") : 0)));
			//盈亏额度
			data.put("profitQuota", String.valueOf(
					memberInfo.getDouble("maxLimit") + (memberInfo.containsKey("result") ?
							memberInfo.getDouble("result") : 0)));
			//盈亏金额
			data.put("profitAmount",
					String.valueOf((memberInfo.containsKey("result") ? memberInfo.getDouble("result") : 0)));
			//投注结果返回信息
			bean.success();
		} catch (Exception e) {
			log.error("SgWin盘口会员【" + existHmId + "】投注失败,失败原因为：", e);
			bean.error(e.getMessage());
		} finally {
			httpConfig.defTimeOut();
		}
		return bean;

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
				log.info("SgWin盘口会员【" + existHmId + "】等待其他线程登陆的线程出错，错误原因为=" + e.getMessage() + "，正在退出等待");
				break;
			}
		}
		//用户执行状态
		JsonResultBeanPlus bean = beanMap.get(existHmId);
		if (IbmHcCodeEnum.CODE_404.getCode().equals(bean.getCodeSys())) {
			log.info("SgWin盘口会员【" + existHmId + "】其他线程登陆的结果为=" + bean.toJsonString() + ",现正在重新登陆");
			bean = login(existHmId);
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
	 * 获取未结算信息（正在投注信息）
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param game      盘口游戏code
	 * @return
	 */
	public JsonResultBeanPlus getNoSettle(String existHmId, String game) {
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
			String html = SgWinTool.getNoSettle(httpConfig, data.get("projectHost"), game);
			log.info("SgWin盘口会员【" + existHmId + "】获取未结算页面=" + html);
			if (StringTool.isEmpty(html)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}

			bean.success();
			bean.setData(html);
		} catch (Exception e) {
			log.error("SgWin盘口会员【" + existHmId + "】获取未结算页面失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	/**
	 * 获取结算页面
	 *
	 * @param existHmId 盘口会员存在id
	 * @param page      页数
	 * @return
	 */
	public JsonResultBeanPlus profit(String existHmId, int page) {
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
		JSONArray resultArray;
		try {
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);
			//获取结算页面
			resultArray = SgWinTool.profit(httpConfig, data.get("projectHost"), page);
			if (ContainerTool.isEmpty(resultArray)) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_PAGE_FAIL);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			bean.success();
			bean.setData(resultArray);
		} catch (Exception e) {
			log.error("SgWin盘口会员【" + existHmId + "】获取未结算页面失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	/**
	 * 定时检验
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return 定时检验信息
	 */
	public JsonResultBeanPlus toCheckIn(String existHmId) throws InterruptedException {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (!userMap.containsKey(existHmId)) {
			//重新登录
			bean = login(existHmId);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		HttpClientConfig httpConfig;
		Map<String, String> data;
		String html;
		httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);
		data = userMap.get(existHmId);
		if (ContainerTool.isEmpty(data)) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		html = SgWinTool.toCheckIn(httpConfig, data.get("projectHost"));
		log.trace("SgWin盘口会员【" + existHmId + "】定时检验结果" + html);
		if (ContainerTool.isEmpty(html)) {
			bean.putEnum(IbmHcCodeEnum.IBM_404_CHECK_CODE);
			bean.putSysEnum(IbmHcCodeEnum.CODE_404);
			return bean;
		}

		bean.setData(html);
		bean.success();
		return bean;

	}
	/**
	 * 获取游戏限额信息
	 *
	 * @param existHmId 已存在id
	 * @param game      盘口游戏
	 * @return 限额信息
	 */
	public JsonResultBeanPlus gameLimit(String existHmId, String game) {
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
			JSONObject gameLimit = SgWinTool.getMemberInfo(httpConfig, data.get("projectHost"), game);
			log.trace("SgWin盘口会员【" + existHmId + "】游戏【" + game + "】限额信息为：" + gameLimit);
			if (ContainerTool.isEmpty(gameLimit)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_BET_LIMIT);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			bean.setData(gameLimit);
			bean.success();
		} catch (Exception e) {
			log.error("SgWin盘口会员【" + existHmId + "】获取游戏限额信息失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	/**
	 * 清除该盘口会员用户数据
	 *
	 * @param existHmId 已存在盘口会员用户id
	 */
	public void removeExistHmInfo(String existHmId) {
		if (StringTool.isEmpty(existHmId)) {
			return;
		}
		userMap.remove(existHmId);
		dataMap.remove(existHmId);
		clientMap.remove(existHmId);
	}
}
