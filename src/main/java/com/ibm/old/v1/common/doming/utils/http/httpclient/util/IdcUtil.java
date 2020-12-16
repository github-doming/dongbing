package com.ibm.old.v1.common.doming.utils.http.httpclient.util;
import com.ibm.old.v1.common.doming.configs.IdcConfig;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.HandicapHttpClientTool;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.IdcTool;
import net.sf.json.JSONObject;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: IDC盘口的httpClient工具类
 * @Author: Dongming
 * @Date: 2019-02-28 10:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IdcUtil {
	protected Logger log = LogManager.getLogger(this.getClass());

	private static final Integer MAX_RECURSIVE_SIZE = 5;

	private Map<String, HttpClientConfig> clientMap;
	private Map<String, Map<String, String>> userMap;
	private Map<String, Map<String, String>> dataMap;
	private static long checkLoginTime = 0;
	private static final long CHECK_LOGIN_TIME = 40 * 1000L;

	private static volatile IdcUtil instance = null;

	public static IdcUtil findInstance() {
		if (instance == null) {
			synchronized (Ws2Util.class) {
				if (instance == null) {
					IdcUtil instance = new IdcUtil();
					// 初始化
					instance.init();
					IdcUtil.instance = instance;
				}
			}
		}
		return instance;
	}

	private void init() {
		clientMap = new HashMap<>(10);
		userMap = new HashMap<>(10);
		dataMap = new HashMap<>(10);
	}

	/**
	 * 销毁工厂
	 */
	public static void destroy() {
		if (instance == null) {
			return;
		}
		if (ContainerTool.notEmpty(instance.clientMap)) {
			for (HttpClientConfig clientConfig : instance.clientMap.values()) {
				clientConfig.destroy();
			}
		}
		instance.userMap.clear();
		instance.userMap = null;
		instance.dataMap.clear();
		instance.dataMap = null;
		instance = null;
	}

	/**
	 * 登陆
	 *
	 * @param hmExistId       盘口会员存在id
	 * @param memberAccount   会员账号
	 * @param memberPassword  会员密码
	 * @param handicapUrl     盘口地址
	 * @param handicapCaptcha 盘口验证码
	 */
	public JsonResultBeanPlus login(String hmExistId, String memberAccount, String memberPassword, String handicapUrl,
			String handicapCaptcha) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//已存在数据
		if (userMap.containsKey(hmExistId)) {
			bean.setData(userMap.get(hmExistId));
			bean.success();
			return bean;
		}
		if (dataMap.containsKey(hmExistId)) {
			Map<String, String> data = dataMap.get(hmExistId);
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
			dataMap.put(hmExistId, data);
		}
		try {
			//获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, hmExistId);

			bean = login(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
			if (!bean.isSuccess()) {
				return bean;
			}
			Map<String, String> data = (Map<String, String>) bean.getData();
			userMap.put(hmExistId, data);
		} catch (Exception e) {
			log.error("IDC盘口会员【" + hmExistId + "】登录失败,失败原因为：", e);
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
	 * @param memberAccount   账号
	 * @param memberPassword  密码
	 * @return 登录
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
			Map<String, String> loginDateMap = null;
			for (int i = 0; i < 5; i++) {
				httpConfig.headers(null);
				httpConfig.httpContext(null);
				//登陆页面
				String loginHtml = IdcTool.getLoginHtml(httpConfig, handicapUrl, handicapCaptcha);
				if (StringTool.isEmpty(loginHtml)) {
					log.info("获取登陆页面" + loginHtml);
					bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_NAVIGATE);
					bean.putSysEnum(IbmHcCodeEnum.CODE_404);
					return bean;
				}
				//获取线路
				String loginSrc = IdcTool.getLoginSrc(loginHtml);
				if (StringTool.isEmpty(loginSrc)) {
					log.info("获取线路失败" + loginSrc);
					bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_ROUTE);
					bean.putSysEnum(IbmHcCodeEnum.CODE_404);
					return bean;
				}
				//登陆界面
				//登陆界面需要解析信息
				httpConfig.httpContext(HttpClientContext.create());
				loginDateMap = IdcTool.getLoginData(httpConfig, loginSrc);
				if (ContainerTool.notEmpty(loginDateMap)) {
					break;
				}
			}
			if (ContainerTool.isEmpty(loginDateMap)) {
				log.info("获取登陆界面解析信息异常" + loginDateMap);
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_LOGIN);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			String loginSrc = loginDateMap.get("loginSrc");
			String projectHost = StringTool.projectHost(loginSrc);

			//获取图片验证码
			String verifyCode = IdcTool.getVerifyCode(httpConfig, projectHost);

			//登陆
			httpConfig.setHeader("Referer", loginSrc);
			HttpResult loginInfo = IdcTool
					.getLogin(httpConfig, projectHost, loginDateMap, memberAccount, memberPassword, verifyCode);

			if (ContainerTool.isEmpty(loginInfo) || StringTool.isEmpty(loginInfo.getHtml()) || "404"
					.equals(loginInfo.getStatusCode().toString())) {
				log.info("获取登陆信息异常" + loginInfo);
				loginInfo = IdcTool.getLogin(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
			}
			if (StringTool.isEmpty(loginInfo) || StringTool.isEmpty(loginInfo.getHtml()) || "404"
					.equals(loginInfo.getStatusCode().toString())) {
				log.info("获取登陆信息异常" + loginInfo);
				bean.putEnum(IbmHcCodeEnum.IBM_403_LOGIN_FAIL);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			if (!StringTool.isContains(loginInfo.getHtml().toString(), "协议与规则")) {
				String html = loginInfo.getHtml().toString();
				log.info("获取异常的登陆结果页面" + html);
				if (StringTool.contains(html, "mypassword.aspx?type=1", "新密码")) {
					bean.putEnum(IbmHcCodeEnum.IBM_403_CHANGE_PASSWORD);
					bean.putSysEnum(IbmHcCodeEnum.CODE_403);
					return bean;
				}
				if (StringTool.isContains(html, "//<![CDATA[", "//]]>")) {
					html = html.substring(html.lastIndexOf("//<![CDATA["), html.lastIndexOf("//]]>"));
				}
				log.info("获取异常的登陆结果页面" + html);
				bean.putEnum(IdcTool.loginError(html));
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			httpConfig.setHeader("Referer", projectHost + "/ch/main.aspx");

			projectHost = loginInfo.getData().toString();

			Map<String, String> data = new HashMap<>(1);
			data.put("projectHost", projectHost);

			bean.setData(data);
			bean.success();
		} catch (Exception e) {
			log.error("IDC盘口账号【" + memberAccount + "】登录失败,失败原因为：", e);
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

		return login(httpConfig, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
	}
	/**
	 * 登陆
	 *
	 * @param hmExistId 盘口会员存在id
	 */
	public JsonResultBeanPlus login(String hmExistId) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (!dataMap.containsKey(hmExistId)) {
			bean.putEnum(IbmHcCodeEnum.IBM_404_EXIST_INFO);
			bean.putSysEnum(IbmHcCodeEnum.CODE_404);
			return bean;
		}
		Map<String, String> data = dataMap.get(hmExistId);
		return login(hmExistId, data.get("memberAccount"), data.get("memberPassword"), data.get("handicapUrl"),
				data.get("handicapCaptcha"));
	}

	/**
	 * 用户基本信息
	 *
	 * @param hmExistId 已存在id
	 * @return 用户基本信息
	 */
	public JsonResultBeanPlus userInfo(String hmExistId, int... index) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			bean.putEnum(IbmHcCodeEnum.IBM_404_USER_INFO);
			bean.putSysEnum(IbmHcCodeEnum.CODE_404);
			return bean;
		}
		// 获取用户信息
		if (!userMap.containsKey(hmExistId)) {
			bean = login(hmExistId);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		Map<String, String> data = userMap.get(hmExistId);
		if (ContainerTool.isEmpty(data)) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return bean;
		}
		// 获取配置类
		HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, hmExistId);
		JSONObject userInfo;
		try {
			userInfo = IdcTool.getUserInfo(httpConfig, data.get("projectHost"));
			if (StringTool.isEmpty(userInfo) || userInfo.containsKey("Message")) {
				log.info("IDC盘口会员【" + hmExistId + "】获取用户基本信息失败" + userInfo);
				userMap.remove(hmExistId);
				return userInfo(hmExistId, ++index[0]);
			}
			//会员账户
			data.put("memberAccount", userInfo.getString("memberno"));
			//会员盘
			data.put("memberType", IdcTool.getWagerRoundNo(userInfo));
			//信用额度
			data.put("creditQuota", userInfo.getString("creditquota"));
			//可用额度
			data.put("usedQuota", (userInfo.getDouble("creditquota") + userInfo.getDouble("usecreditquota")) + "");
			//使用金额
			data.put("usedAmount", userInfo.getString("usecreditquota"));
			//盈亏金额
			data.put("profitAmount", userInfo.getString("usecreditquota2"));
			bean.success();
			bean.setData(data);
			return bean;
		} catch (Exception e) {
			log.error("IDC盘口会员【" + hmExistId + "】获取用户信息失败", e);
			userMap.remove(hmExistId);
			return userInfo(hmExistId, ++index[0]);
		}
	}
	/**
	 * 获取用户信息
	 *
	 * @param hmExistId 已存在盘口会员id
	 * @return
	 */
	public Map<String, String> getUserInfo(String hmExistId) {
		Map<String, String> data = userMap.get(hmExistId);
		if (ContainerTool.isEmpty(data)) {
			return null;
		}
		return data;
	}

	/**
	 * 投注基本信息
	 *
	 * @param hmExistId   已存在id
	 * @param gameInfo    游戏信息
	 * @param gameBetCode 游戏投注code
	 * @return 投注基本信息
	 */
	public JsonResultBeanPlus betInfo(String hmExistId, Map<String, String> gameInfo, List<String> gameBetCode) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		// 获取用户信息
		if (!userMap.containsKey(hmExistId)) {
			bean = login(hmExistId);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		Map<String, String> data = userMap.get(hmExistId);
		try {
			// 获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, hmExistId);

			JSONObject drawsInfo = IdcTool.getDrawsInfo(httpConfig, data.get("projectHost"), gameInfo.get("gameno"));
			if (StringTool.isEmpty(drawsInfo)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PAGE_DRAW_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			JSONObject oddInfo = IdcTool
					.getOddInfo(httpConfig, data.get("projectHost"), data.get("wagerRoundNo"), gameInfo.get("gameno"),
							gameBetCode);
			if (StringTool.isEmpty(oddInfo)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_BET_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			Map<String, JSONObject> map = new HashMap<>(2);
			map.put("drawsInfo", drawsInfo);
			map.put("oddInfo", oddInfo);
			bean.setData(map);
			bean.success();
		} catch (Exception e) {
			log.error("IDC盘口会员【" + hmExistId + "】获取投注基本信息失败,失败原因为：", e);
			bean.setSuccess(false);
		}
		return bean;
	}
	/**
	 * 投注
	 *
	 * @param hmExistId 已存在id
	 * @param gameCode  游戏code
	 * @param ballCode  球码code
	 * @param drawsInfo 开奖信息
	 * @param oddInfo   赔率
	 * @param betItems  投注项
	 * @return 投注结果
	 */
	public JsonResultBeanPlus betting(String hmExistId, Map<String, String> gameCode, Map<String, String> ballCode,
			JSONObject drawsInfo, JSONObject oddInfo, List<String> betItems, String period) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (!userMap.containsKey(hmExistId)) {
			//获取用户信息
			bean = userInfo(hmExistId);
			if (!bean.isSuccess()) {
				bean = login(hmExistId);
				if (!bean.isSuccess()) {
					return bean;
				}
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		// 获取用户信息
		Map<String, String> data = userMap.get(hmExistId);
		HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, hmExistId);
		httpConfig.httpTimeOut(20 * 1000);

		String[] betItemInfo = IdcTool.getBetItemInfo(betItems, ballCode, oddInfo);
		try {

			List<Map<String, String>> errorData = new ArrayList<>();

			//校验出投注项内的错误数
			while (true) {
				//投注项为空
				if (ContainerTool.isEmpty(betItems)) {
					break;
				}
				String checkResult = IdcTool
						.orderHandler(httpConfig, data, gameCode.get("gameno"), period, betItemInfo[1]);

				if (StringTool.isEmpty(checkResult)) {
					//校验结果 - 成功
					break;
				} else if (StringTool.isContains(checkResult, "【", "】")) {
					//校验结果 - 失败
					checkResult = checkResult.trim();
					//投注名次
					String complexRank = checkResult
							.substring(checkResult.indexOf("【") + 1, checkResult.lastIndexOf("】"));
					//投注项
					String item = checkResult.substring(checkResult.indexOf("】") + 1, checkResult.lastIndexOf(" "));
					//失败原因
					String reason = checkResult.substring(checkResult.lastIndexOf(" "));

					//获取投注信息
					String betInfoCode = IdcConfig.BET_INFO_CODE.get(complexRank + item);
					//找出betItem 中的该错误投注项
					boolean state = IdcTool.removeBetInfo(betItems, betInfoCode);
					if (state) {
						Map<String, String> error = new HashMap<>(2);
						error.put("item", betInfoCode);
						error.put("msg", reason);
						errorData.add(error);
					}
				} else if ("该帐号或上级代理被禁用或暂停下注".equals(checkResult)) {
					bean.putEnum(IbmHcCodeEnum.IBM_403_USER_BAN_BET);
					bean.putSysEnum(IbmHcCodeEnum.CODE_403);
					bean.setData(IbmHcCodeEnum.IBM_403_USER_BAN_BET.getMsgCn());
					return bean;
					//非正常情况下校验结果
				} else if (StringTool.isContains(checkResult, "非")) {
					bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL);
					bean.putSysEnum(IbmHcCodeEnum.CODE_403);
					bean.setData(checkResult);
					return bean;
				} else {
					log.error("未知的返回数据解析" + checkResult);
					bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
					bean.putSysEnum(IbmHcCodeEnum.CODE_404);
					bean.setData(checkResult);
					return bean;
				}
			}

			//全部校验失败
			if (ContainerTool.isEmpty(betItems)) {
				Map<String, List<Map<String, String>>> resultData = new HashMap<>(2);
				resultData.put("errorData", errorData);
				bean.setData(resultData);
				bean.success();
				return bean;
			}

			//投注 token
			String token = IdcTool.betValiToken(httpConfig, data, betItemInfo);
			if (StringTool.isEmpty(token)) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL_TOKEN);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				bean.setData(IbmHcCodeEnum.IBM_403_BET_FAIL_TOKEN.getMsgCn());
				return bean;
			}

			//投注
			String html = IdcTool
					.betting(httpConfig, gameCode.get("gameno"), data, betItemInfo[1], drawsInfo.getString("cd"),
							token);
			log.trace("投注结果=" + html);
			if (StringTool.isEmpty(html) || StringTool
					.isContains(html, "指定期数为非交易状态!", "非交易时间", "没有该游戏操作权限", "超过您的每日信用额度", "最低单注金额", "最高单注金额",
							"累计投注金额超过您的单项额度")) {
				bean.putEnum(IbmHcCodeEnum.IBM_403_BET_FAIL);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				bean.setData(html);
				return bean;
			}
			//TODO 判断补投

			//返回信息

			bean.setData(IdcTool.getBetResultData(errorData, betItems, html));
			bean.success();
		} catch (Exception e) {
			log.error("盘口会员【" + hmExistId + "】投注失败,失败原因为：", e);
			bean.setSuccess(false);
		} finally {
			httpConfig.defTimeOut();
			userInfo(hmExistId);
		}
		return bean;

	}
	/**
	 * 定时校验
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return 定时校验结果
	 */
	public JsonResultBeanPlus toCheckIn(String existHmId) {
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
		try {
			// 获取配置类
			httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);
			data = userMap.get(existHmId);

			if (ContainerTool.isEmpty(data)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			html = IdcTool.toCheckIn(httpConfig, data.get("memberno"), data.get("projectHost"));
			log.trace("盘口会员" + existHmId + "定时检验结果" + html);
			if (ContainerTool.isEmpty(html)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_CHECK_CODE);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			if (StringTool.isContains(html, "{\"d\":\"\"}")) {
				bean.success();
				return bean;
			} else if (html.contains("您已被系統登出，請重新登入！")) {
				bean.setData("您已被系統登出，請重新登入！");
				bean.putEnum(IbmHcCodeEnum.IBM_403_OTHER_PLACE_LOGIN);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			} else if (html.contains("Session is empty")) {
				bean.setData("您尚未登入，請重新登入！");
				bean.putEnum(IbmHcCodeEnum.IBM_403_TIMING_CHECKOUT);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}else if(html.contains("处理请求时出错")){
				bean.putEnum(IbmHcCodeEnum.IBM_403_REQUEST);
				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
				return bean;
			}
			bean.success();
		} catch (Exception e) {
			log.error("IDC盘口会员【" + existHmId + "】定时校验失败", e);
			bean.putEnum(IbmCodeEnum.IBM_500);
			bean.putSysEnum(IbmCodeEnum.CODE_500);
		}
		return bean;
	}

	/**
	 * 获取投注类型list
	 *
	 * @param codeType 类型code
	 * @return 投注类型list
	 */
	public List<String> getBetType(String codeType) {
		switch (codeType) {
			case "PK10_NUMBER":
				return IdcConfig.PK10_BALL;
			case "XYFT_NUMBER":
				return IdcConfig.XYFT_BALL;
			case "CQSSC_NUMBER":
				return IdcConfig.CQSSC_NUMBER_BALL;
			case "CQSSC_POKER":
				return IdcConfig.CQSSC_POKER_BALL;
			case "CQSSC_SUM":
				return IdcConfig.CQSSC_SUM_BALL;
			default:
				return null;
		}
	}
	/**
	 * 获取球码类型map
	 *
	 * @param codeType 类型code
	 * @return 球码类型map
	 */
	public Map<String, String> getBallCodeType(String codeType) {
		switch (codeType) {
			case "PK10_NUMBER":
				return IdcConfig.PK10_BALL_CODE;
			case "XYFT_NUMBER":
				return IdcConfig.XYFT_BET_CODE;
			case "CQSSC_NUMBER":
				return IdcConfig.CQSSC_NUMBER_CODE;
			case "CQSSC_POKER":
				return IdcConfig.CQSSC_POKER_CODE;
			case "CQSSC_SUM":
				return IdcConfig.CQSSC_SUM_CODE;
			default:
				return null;
		}
	}
	/**
	 * 获取盈亏信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @param date      日期字符串（yyyy-MM-dd）
	 * @param curPage   页数
	 * @param pageSize  条数
	 * @return 盈亏信息
	 */
	public JsonResultBeanPlus profitLoss(String existHmId, Map<String, String> gameCode, String date, String isJs,
			int curPage, int pageSize) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		// 获取用户信息
		if (!userMap.containsKey(existHmId)) {
			bean = login(existHmId);
			if (!bean.isSuccess()) {
				return bean;
			}
		}
		try {
			Map<String, String> data = userMap.get(existHmId);
			// 获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(clientMap, existHmId);

			String html = IdcTool
					.getProfitLoss(httpConfig, gameCode.get("gameno"), data.get("projectHost"), date, isJs, curPage,
							pageSize);

			if (StringTool.isEmpty(html)) {
				log.info("IDC盘口会员【" + existHmId + "】获取盈亏信息页面为空");
				bean.setSuccess(false);
				return bean;
			}
			bean.success();
			bean.setData(html);
		} catch (Exception e) {
			log.error("IDC盘口会员【" + existHmId + "】获取盈亏信息失败", e);
			bean.setSuccess(false);
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
	/**
	 * 定时检验 专有登录方法
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return 登录结果
	 */
	public JsonResultBeanPlus checkLogin(String existHmId) {
		synchronized (IdcUtil.class) {
			if ((System.currentTimeMillis() - checkLoginTime) < CHECK_LOGIN_TIME) {
				return null;
			}
			checkLoginTime = System.currentTimeMillis();
		}
		return login(existHmId);
	}
}
