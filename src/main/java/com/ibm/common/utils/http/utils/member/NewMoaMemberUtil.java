package com.ibm.common.utils.http.utils.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.NewMoaMemberTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import com.ibm.common.utils.http.utils.entity.MemberUserInfo;
import com.ibm.common.utils.http.utils.js.ScriptEngineManagerTool;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: NewMoa会员工具类
 * @Author: wwj
 * @Date: 2020/6/11 11:29
 * @Version: v1.0
 */
public class NewMoaMemberUtil extends BaseMemberUtil {

	private HandicapUtil.Code handicapCode = HandicapUtil.Code.NEWMOA;

	private static volatile NewMoaMemberUtil instance = null;

	public static NewMoaMemberUtil findInstance() {
		if (instance == null) {
			synchronized (NewMoaMemberUtil.class) {
				if (instance == null) {
					NewMoaMemberUtil instance = new NewMoaMemberUtil();
					// 初始化
					instance.init();
					NewMoaMemberUtil.instance = instance;
				}
			}
		}
		return instance;
	}

	/**
	 * 销毁工厂
	 */
	public static void destroy() {
		if (instance == null) {
			return;
		}
		if (ContainerTool.notEmpty(instance.memberCrawlers)) {
			for (MemberCrawler memberCrawler : instance.memberCrawlers.values()) {
				memberCrawler.getHcConfig().destroy();
			}
		}
		instance.memberCrawlers = null;
		instance = null;
	}

	@Override
	public JsonResultBeanPlus login(String existHmId, String memberAccount, String memberPassword, String handicapUrl, String handicapCaptcha) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//已存在数据
		MemberCrawler member;
		if (memberCrawlers.containsKey(existHmId)) {
			member = memberCrawlers.get(existHmId);
			if (StringTool.notEmpty(member.getProjectHost())) {
				bean.setData(member.getProjectHost());
				bean.success();
				return bean;
			}
		} else {
			member = new MemberCrawler();
		}
		AccountInfo accountInfo = member.getAccountInfo();
		accountInfo.setItemInfo(memberAccount, memberPassword, handicapUrl, handicapCaptcha);

		try {
			//获取配置类
			HttpClientConfig httpConfig = HandicapHttpClientTool.getHttpConfig(member);
			httpConfig.httpTimeOut(10 * 1000);
			bean = login(httpConfig, accountInfo);
			if (!bean.isSuccess()) {
				return bean;
			}
			Map<String, String> data = (Map<String, String>) bean.getData();
			member.setProjectHost(data.get("projectHost"));

			MemberUserInfo memberUserInfo = member.getMemberUserInfo();
			memberUserInfo.setMemberAccount(accountInfo.getAccount());
			memberUserInfo.setCreditQuota(data.get("creditQuota"));
			memberUserInfo.setMemberType(data.get("memberType"));
			memberUserInfo.setProfitAmount(data.get("surplusAmount"));
			memberUserInfo.setUsedQuota(data.get("ableAmount"));
			if (!memberCrawlers.containsKey(existHmId)) {
				memberCrawlers.put(existHmId, member);
			}
		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHmId, "登录失败,失败原因为：" + e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override
	public JsonResultBeanPlus login(HttpClientConfig httpConfig, AccountInfo accountInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapUrl = accountInfo.getHandicapUrl();
		if (!handicapUrl.endsWith("/")) {
			handicapUrl = handicapUrl.concat("/");
		}

		httpConfig.headers(null);
		httpConfig.httpContext(null);
		httpConfig.httpContext(HttpClientContext.create());
		try {
//			//获取验证码
//			String verifyCode = NewMoaMemberTool.getVerifyCode(httpConfig, handicapUrl,null, "NewMOANav");
//			//获取线路选择页面
//			String routeHtml = NewMoaMemberTool.getSelectRoutePage(httpConfig, handicapUrl, verifyCode,accountInfo.getHandicapCaptcha());
//			if (StringTool.isEmpty(routeHtml) ) {
//				log.info(message,handicapCode.name(),accountInfo.getAccount(), "获取线路页面失败" + routeHtml);
//				bean.putEnum(IbmHcCodeEnum.IBM_403_PAGE_NAVIGATE);
//				bean.putSysEnum(IbmHcCodeEnum.CODE_403);
//				return bean;
//			}
			//会员线路数组
//			String[] routes = NewMoaMemberTool.getMemberRoute(httpConfig, routeHtml);
			String[] routes = {"https://msam4822.productbang.com/"};
			if (ContainerTool.isEmpty(routes)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			// 获取RAS加密公钥
			Map<String, String> loginInfoMap = NewMoaMemberTool.getLoginPublicKey(httpConfig, routes);
			if (ContainerTool.isEmpty(loginInfoMap)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String loginSrc = loginInfoMap.get("loginSrc");
			String publicKey = loginInfoMap.get("publicKey");
			// 登录密码进行加密


			String rsaPassword = ScriptEngineManagerTool.findInstance().getRSAPassword(publicKey, accountInfo.getPassword());

			String verifyCode = NewMoaMemberTool.getVerifyCode(httpConfig, loginSrc, null, "NewMOA");
			//登录
			String loginInfo = NewMoaMemberTool.getLogin(httpConfig, accountInfo.getAccount(), rsaPassword, verifyCode, loginSrc);
			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			//错误处理
			if (StringTool.notEmpty(loginInfo) && !StringTool.contains(loginInfo, "member3/public/check_rule.do", "此网页使用了框架，但您的浏览器不支持框架")) {
				log.error(message, handicapCode.name(), accountInfo.getAccount(), "登录的URL=" + loginSrc);
				bean.putEnum(NewMoaMemberTool.loginError(loginInfo));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			Map<String, String> userData = new HashMap<>(5);
			//获取主页面
			String indexHtml = NewMoaMemberTool.getHomePage(httpConfig, loginSrc);
			if (ContainerTool.notEmpty(indexHtml)) {
				Document document = Jsoup.parse(indexHtml);
				//会员盘
				userData.put("memberType", document.getElementById("td_user_set").text());
				//信用额度
				userData.put("creditQuota", document.getElementById("td_frozen_amount").text());
				//可用金额
				userData.put("ableAmount", document.getElementById("td_able_amount").text());
				//今日输赢
				userData.put("surplusAmount", document.getElementById("td_surplus").text());
			}

			userData.put("projectHost", loginSrc);

			bean.success(userData);
		} catch (Exception e) {
			log.error(message, handicapCode.name(), accountInfo.getAccount(), "登录失败,失败原因为：" + e);
			bean.error(e.getMessage());
		} finally {
			httpConfig.defTimeOut();
		}
		return bean;
	}

	@Override
	public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String memberAccount, String memberPassword) {
		// 获取配置类
		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		AccountInfo accountInfo = new AccountInfo();
		accountInfo.setItemInfo(memberAccount, memberPassword, handicapUrl, handicapCaptcha);

		JsonResultBeanPlus bean = login(httpConfig, accountInfo);
		if (bean.isSuccess()) {
			String existHmId = RandomTool.getNumLetter32();
			//存储账号信息
			MemberCrawler member = new MemberCrawler();
			//存储爬虫信息
			Map<String, String> data = (Map<String, String>) bean.getData();
			member.setAccountInfo(accountInfo);
			member.setProjectHost(data.get("projectHost"));
			member.setHcConfig(httpConfig);
			member.setExistId(existHmId);

			MemberUserInfo memberUserInfo = member.getMemberUserInfo();
			memberUserInfo.setMemberAccount(accountInfo.getAccount());
			memberUserInfo.setCreditQuota(data.get("creditQuota"));
			memberUserInfo.setMemberType(data.get("memberType"));
			memberUserInfo.setProfitAmount(data.get("surplusAmount"));
			memberUserInfo.setUsedQuota(data.get("ableAmount"));

			memberCrawlers.put(existHmId, member);
			bean.setData(existHmId);
		}
		return bean;
	}

	/**
	 * 用户基本信息
	 *
	 * @param existHmId 盘口会员存在id
	 * @return 用户基本信息
	 */
	public JsonResultBeanPlus userInfo(String existHmId, int... index) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			log.error("UN盘口会员【" + existHmId + "】用户基本信息获取失败！");
			return null;
		}
		// 获取用户信息
		MemberCrawler member = memberCrawlers.get(existHmId);
		if (StringTool.isEmpty(member.getProjectHost(), member.getHcConfig())) {
			bean = login(existHmId);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		try {
			String userHtml = NewMoaMemberTool.getUserInfo(member);
			if (StringTool.isEmpty(userHtml)) {
				log.info(message, handicapCode.name(), existHmId, "获取会员账号信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			Document document = Jsoup.parse(userHtml);
			//获取用户信息
			MemberUserInfo memberUserInfo = member.getMemberUserInfo();
			//信用额度
			memberUserInfo.setCreditQuota(document.getElementById("td_frozen_amount").text());
			//可用额度
			memberUserInfo.setUsedQuota(document.getElementById("td_able_amount").text());
			//盈亏金额
			memberUserInfo.setProfitAmount(document.getElementById("td_surplus").text());
			//会员盘
			memberUserInfo.setMemberType(document.getElementById("td_user_set").text());
			bean.success();
			bean.setData(memberUserInfo);
		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHmId, "获取基本信息异常，失败原因为:" + e);
			bean.error(e.getMessage());
		}
		return bean;
	}


	@Override
	public MemberUserInfo getUserInfo(String existHmId, boolean flag) {
		if (!memberCrawlers.containsKey(existHmId)) {
			return new MemberUserInfo();
		}
		if (flag) {
			//获取用户信息
			JsonResultBeanPlus bean = userInfo(existHmId);
			//获取用户信息失败，返回空
			if (!bean.isSuccess()) {
				return new MemberUserInfo();
			}
		}
		return memberCrawlers.get(existHmId).getMemberUserInfo();

	}

	/**
	 * 获取用户限额信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @return bean
	 */
	@Override
	public JsonResultBeanPlus getQuotaList(String existHmId, GameUtil.Code gameCode) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		if (!memberCrawlers.containsKey(existHmId)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		MemberCrawler member = memberCrawlers.get(existHmId);
		if (StringTool.isEmpty(member.getProjectHost(), member.getHcConfig())) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		try {
			//获取盘口游戏code
			String period = PeriodUtil.getHandicapGamePeriod(handicapCode, gameCode, gameCode.getGameFactory().period(handicapCode).findPeriod());
			JSONArray gameLimit = NewMoaMemberTool.getQuotaList(member, gameCode, period);
			log.trace(message, handicapCode.name(), existHmId, "游戏【" + gameCode.name() + "】限额信息为：" + gameLimit);
			if (ContainerTool.isEmpty(gameLimit)) {
				bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			bean.setData(gameLimit);
			bean.success();
		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHmId, "获取游戏限额信息失败,失败原因为：" + e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 检验信息,上次成功检验时间超过两分钟，删除用户信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return bean
	 */
	@Override
	public JsonResultBeanPlus checkInfo(String existHmId) {
		synchronized (existHmId) {
			if (!memberCrawlers.containsKey(existHmId)) {
				return new JsonResultBeanPlus();
			}
			JsonResultBeanPlus bean;
			MemberCrawler member = memberCrawlers.get(existHmId);
			//上次校验时间
			long lastTime;
			if (member.getCheckTime() == 0) {
				lastTime = System.currentTimeMillis();
				member.setCheckTime(lastTime);
			} else {
				lastTime = member.getCheckTime();
			}
			//是否大于上次校验时间
			boolean flag = System.currentTimeMillis() - lastTime > MIN_CHECK_TIME;
			if (flag || member.getMemberUserInfo().getUsedQuota() == null) {
				//获取用户信息
				bean = userInfo(existHmId);
				//获取用户信息失败，返回空
				if (!bean.isSuccess()) {
					return bean;
				}
			} else {
				//使用内存数据
				bean = new JsonResultBeanPlus().success(member.getMemberUserInfo());
			}
			if (ContainerTool.isEmpty(bean.getData())) {
				if (System.currentTimeMillis() - lastTime > MAX_CHECK_TIME) {
					member.setProjectHost(null);
					member.setCheckTime(System.currentTimeMillis());
				}
			}
			if (flag) {
				member.setCheckTime(System.currentTimeMillis());
			}
			return bean;
		}
	}

	/**
	 * 获取盘口赔率
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @param betType   投注类型 固定值 B_109
	 */
	public void getOddsInfo(String existHmId, GameUtil.Code gameCode, String betType, String period, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			log.error(message, handicapCode.name(), existHmId, "获取赔率信息失败");
			return;
		}
		MemberCrawler member = memberCrawlers.get(existHmId);
		if (StringTool.isEmpty(member.getProjectHost(), member.getHcConfig())) {
			JsonResultBeanPlus bean = login(existHmId);
			if (!bean.isSuccess()) {
				log.error(message, handicapCode.name(), existHmId, "重新登录失败");
				return;
			}
		}
		//赔率信息
		Map<GameUtil.Code, Map<String, Object>> memberOdds = member.getOdds();
		Map<String, Object> odds;
		if (memberOdds.containsKey(gameCode)) {
			odds = memberOdds.get(gameCode);
		} else {
			odds = new HashMap<>(3);
			memberOdds.put(gameCode, odds);
		}
		try {
			JSONObject oddsInfo = NewMoaMemberTool.getOddsInfo(member, gameCode, betType, period);

			if (ContainerTool.isEmpty(oddsInfo)) {
				log.info(message, handicapCode.name(), existHmId, "游戏【" + gameCode + "】获取赔率信息失败");
				if (!odds.containsKey(betType)) {
					member.setProjectHost(null);
					member.setHcConfig(null);
					getOddsInfo(existHmId, gameCode, betType, period, ++index[0]);
				}
				return;
			}
			odds.put(betType, oddsInfo);

		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHmId, "获取盘口赔率失败,失败原因为：" + e);
		}
	}


	/**
	 * 投注
	 *
	 * @param existHmId  存在ID
	 * @param gameCode   游戏code
	 * @param drawNumber 期数
	 * @param betItems   投注信息
	 * @param betType    投注类型
	 * @return bean
	 */
	public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode, String drawNumber, List<String> betItems, String betType) {

		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (!memberCrawlers.containsKey(existHmId)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		MemberCrawler member = memberCrawlers.get(existHmId);

		if (StringTool.isEmpty(member.getProjectHost(), member.getHcConfig())) {
			bean = login(existHmId);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		JSONObject odds = (JSONObject) member.getOdds().get(gameCode).get(betType);

		try {
			// 拼接投注信息
			Map<String, Object> bets = NewMoaMemberTool.getBetItemInfo(gameCode, drawNumber, odds, betItems);

			// 投注
			JSONObject betInfo = NewMoaMemberTool.betting(member.getHcConfig(), member.getProjectHost(), bets);

			log.trace(message, handicapCode.name(), existHmId, "投注结果为：" + betInfo);
			bean = resultProcess(existHmId, betInfo.getString("message"), member, gameCode, betItems);
			if (!bean.isSuccess()) {
				return bean;
			}

			bean.success();
		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHmId, "投注失败,失败原因为：" + e);
			bean.error(e.getMessage());
		} finally {
			member.getHcConfig().defTimeOut();
		}
		return bean;
	}

	/**
	 * 处理投注结果
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param result    投注结果
	 * @return 投注结果
	 */
	private JsonResultBeanPlus resultProcess(String existHmId, String result, MemberCrawler member, GameUtil.Code gameCode, List<String> betItems) throws InterruptedException {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (ContainerTool.isEmpty(result)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}

		if (StringTool.contains(result, "超出可用余额")) {
			log.error(message, handicapCode.name(), existHmId, "投注失败=" + result);
			bean.putEnum(HcCodeEnum.IBS_403_POINT_NOT_ENOUGH);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(result, "低于最低下注额", "超过单项最高")) {
			log.error(message, handicapCode.name(), existHmId, "投注失败=" + result);
			bean.putEnum(HcCodeEnum.IBS_403_POINT_LESS);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(result, "大于最高下注额")) {
			log.error(message, handicapCode.name(), existHmId, "投注失败=" + result);
			bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(result, "期号不一致", "已封盘", "不允许下注")) {
			log.error(message, handicapCode.name(), existHmId, "投注失败=" + result);
			bean.putEnum(HcCodeEnum.IBS_403_SEAL_HANDICAP);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(result, "1")) {
			return bean;
//			String html = NewMoaMemberTool.getIsSettlePage(member.getHcConfig(), member.getProjectHost(), gameCode, 1, 1);
//
//			JSONObject betDetailData = NewMoaMemberTool.getIsSettleInfo(html, member.getPIdMap().get(gameCode).getPeriod(), gameCode);
//			int totalPage = Integer.parseInt(betDetailData.getString("totalPage"));
//			JSONArray data = betDetailData.getJSONArray("info");
//			for (Object bet : data) {
//				betItems.remove(bet);
//			}
//			if (ContainerTool.isEmpty(betItems)) {
//				bean.success();
//				return bean;
//			}
//			if (totalPage > 1) {
//				for (int i = 1; i <= totalPage; i++) {
//					html = NewMoaMemberTool.getIsSettlePage(member.getHcConfig(), member.getProjectHost(), gameCode, i + 1, totalPage);
//					if (StringTool.isEmpty(html)) {
//						bean.putEnum(IbmHcCodeEnum.IBM_404_RULE_ERROR);
//						bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//						break;
//					}
//					betDetailData = NewMoaMemberTool.getIsSettleInfo(html, member.getPIdMap().get(gameCode).getPeriod(), gameCode);
//					data = betDetailData.getJSONArray("info");
//					if (ContainerTool.isEmpty(data)) {
//						bean.putEnum(IbmHcCodeEnum.IBM_404_RESULT_PAGE);
//						bean.putSysEnum(IbmHcCodeEnum.CODE_404);
//						break;
//					}
//					for (Object bet : data) {
//						betItems.remove(bet);
//					}
//					if (ContainerTool.isEmpty(betItems)) {
//						bean.success();
//						return bean;
//					}
//
//				}
//			}

		} else {
			member.setProjectHost(null);
			bean.putEnum(HcCodeEnum.IBS_403_UNKNOWN);
			bean.putSysEnum(HcCodeEnum.CODE_403);
		}

		log.error(message, handicapCode.name(), existHmId, "未知的错误信息=" + result);
		bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
		bean.putSysEnum(HcCodeEnum.CODE_403);
		return bean;
	}
}
