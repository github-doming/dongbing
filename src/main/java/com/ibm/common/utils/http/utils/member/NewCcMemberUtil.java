package com.ibm.common.utils.http.utils.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.NewCcMemberTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import com.ibm.common.utils.http.utils.entity.MemberUserInfo;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 新CC会员工具类
 * @Author: wwj
 * @Date: 2020/5/14 11:29
 * @Version: v1.0
 */
public class NewCcMemberUtil extends BaseMemberUtil {

	private HandicapUtil.Code handicapCode = HandicapUtil.Code.NEWCC;

	private static volatile NewCcMemberUtil instance = null;

	public static NewCcMemberUtil findInstance() {
		if (instance == null) {
			synchronized (NewCcMemberUtil.class) {
				if (instance == null) {
					NewCcMemberUtil instance = new NewCcMemberUtil();
					// 初始化
					instance.init();
					NewCcMemberUtil.instance = instance;
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
		try {
			//获取线路选择页面
			String routeHtml = NewCcMemberTool.getSelectRoutePage(httpConfig, handicapUrl, accountInfo.getHandicapCaptcha());
			if (StringTool.isEmpty(routeHtml)) {
				log.info(message, handicapCode.name(), accountInfo.getAccount(), "获取线路页面失败" + routeHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//4条会员线路数组
			String[] routes = NewCcMemberTool.getMemberRoute(httpConfig, routeHtml);
			if (ContainerTool.isEmpty(routes)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//获取登录信息map
			Map<String, String> loginInfoMap = NewCcMemberTool.getLoginHtml(httpConfig, routes);
			if (ContainerTool.isEmpty(loginInfoMap)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String loginSrc = loginInfoMap.get("loginSrc");

			//获取验证码
			String verifyCode = NewCcMemberTool.getVerifyCode(httpConfig, loginSrc, null);

			httpConfig.httpContext(HttpClientContext.create());
			//登录
			String loginInfo = NewCcMemberTool.getLogin(httpConfig, accountInfo.getAccount(), accountInfo.getPassword(), verifyCode, loginSrc);

			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			JSONObject loginJson = JSONObject.parseObject(loginInfo);
			//错误处理
			if (StringTool.notEmpty(loginJson.getString("FaildReason"))) {
				log.error(message, handicapCode.name(), accountInfo.getAccount(), "登录的URL=" + loginSrc);
				bean.putEnum(NewCcMemberTool.loginError(loginInfo));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			if (loginJson.getInteger("LoginStatus") == 1 && loginJson.getInteger("IsNeedModityWord") != 0) {
				log.error(message, handicapCode.name(), accountInfo.getAccount(), "登录的URL=" + loginSrc);
				bean.putEnum(HcCodeEnum.IBS_403_CHANGE_PASSWORD);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			Map<String, String> userData = new HashMap<>(3);

			userData.put("projectHost", loginSrc);
			//会员账户
			userData.put("memberAccount", accountInfo.getAccount());

			bean.setData(userData);
			bean.success();
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
			log.error("NewCc盘口会员【" + existHmId + "】用户基本信息获取失败！");
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
			String userHtml = NewCcMemberTool.getUserInfo(member);
			if (StringTool.isEmpty(userHtml)) {
				log.info(message, handicapCode.name(), existHmId, "获取会员账号信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			// 掉线重新登录
			if (StringTool.contains(userHtml, "另一个地方登陆")) {
				member.setProjectHost(null);
				member.setHcConfig(null);
				return userInfo(existHmId, ++index[0]);
			}

			String gainHtml = NewCcMemberTool.getUserGain(member);
			if (ContainerTool.isEmpty(gainHtml)) {
				log.info(message, handicapCode.name(), existHmId, "获取会员账号信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			Document routeDocument = Jsoup.parse(userHtml);
			Element tbody = routeDocument.selectFirst("tbody");
			//获取用户信息
			MemberUserInfo memberUserInfo = member.getMemberUserInfo();
			//使用金额
			memberUserInfo.setUsedAmount(tbody.select("tr").last().text().split(" ")[1]);
			//信用额度
			String credidQuotal = tbody.getElementById("current").text();
			if (StringTool.contains(credidQuotal, "RMB")) {
				credidQuotal = credidQuotal.substring(0, credidQuotal.indexOf("("));
			}
			memberUserInfo.setCreditQuota(credidQuotal);
			//可用额度
			memberUserInfo.setUsedQuota(tbody.getElementById("Money_KY").text());
			//盈亏金额
			memberUserInfo.setProfitAmount(gainHtml.split("\\|")[1]);
			//会员盘
			memberUserInfo.setMemberType(tbody.getElementById("sel_Handicpa").text());
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
			String gameNo = BallCodeTool.getNewCcGameNo(gameCode, member.getMemberUserInfo().getMemberType());
			JSONArray gameLimit = NewCcMemberTool.getQuotaList(member.getHcConfig(), member.getProjectHost(), member.getMemberUserInfo().getMemberType(), gameNo);
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
	 * 获取盘口赔率  NewCc盘口不验证赔率，没有去获取赔率
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @param betType   投注类型
	 */
	public void getOddsInfo(String existHmId, GameUtil.Code gameCode, String betType, int... index) {
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
		String oddsCode = BallCodeTool.getGameOddsCode(HandicapUtil.Code.SGWIN, gameCode, betType);
		try {
			JSONObject oddsInfo = NewCcMemberTool.getOddsInfo(member, gameCode, oddsCode);
			if (ContainerTool.isEmpty(oddsInfo)) {
				log.info(message, handicapCode.name(), existHmId, "游戏【" + gameCode + "】获取赔率信息失败");
				if (!odds.containsKey(betType)) {
					member.setProjectHost(null);
					member.setHcConfig(null);
					getOddsInfo(existHmId, gameCode, betType, ++index[0]);
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
	 * @param existHmId 存在Id
	 * @param gameCode  游戏CODe
	 * @param betItems  投注详情
	 * @return JsonResultBeanPlus
	 */
	public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode, List<String> betItems) {

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
		try {
			// 获取投注信息
			String bets = NewCcMemberTool.getBetItemInfo(gameCode, betItems, member.getMemberUserInfo().getMemberType());
			// 预投注处理
			String beforeBetHtml = NewCcMemberTool.getOddsAndName(member, bets, gameCode);

			bean = beforeBetResultProcess(existHmId, beforeBetHtml);
			if (!bean.isSuccess()) {
				return bean;
			}
			String xdNum = (String) bean.getData();
			// 投注
			JSONObject betInfo = NewCcMemberTool.betting(member, xdNum);
			log.trace(message, handicapCode.name(), existHmId, "投注结果为：" + betInfo);

			bean = resultProcess(existHmId, betInfo);
			if (!bean.isSuccess()) {
				return bean;
			}
			JSONArray betResult = new JSONArray();
			JSONArray betInfos;

			Map<String, String> betInfoCode = NewCcMemberTool.getNewCcBallCode(gameCode);
			JSONArray results = betInfo.getJSONArray("OrderLst");
			for (int i = 0; i < results.size(); i++) {
				JSONObject result = results.getJSONObject(i);
				JSONObject bettingCodes = result.getJSONObject("BettingCode");

				betInfos = new JSONArray();
				// 注单
				betInfos.add(result.get("FormNO").toString().split("@")[0]);
				// 投注项
				betInfos.add(betInfoCode.get(bettingCodes.getString("ItemName")));
				// 金额
				betInfos.add(result.get("TotalFormMoney"));
				// 赔率
				betInfos.add(bettingCodes.getString("OddsValue1"));
				betResult.add(betInfos);
			}

			bean.success();
			bean.setData(betResult);

		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHmId, "投注失败,失败原因为：" + e);
			bean.error(e.getMessage());
		} finally {
			member.getHcConfig().defTimeOut();
		}
		return bean;
	}

	/**
	 * 处理预投注结果
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param betInfo   投注结果
	 * @return 投注结果
	 */
	private JsonResultBeanPlus beforeBetResultProcess(String existHmId, String betInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(betInfo)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(betInfo, "FaildReason")) {
			JSONObject betJSon = JSONObject.parseObject(betInfo);
			if (StringTool.contains(betJSon.getString("FaildReason"), "已超过您的可用余额", "停止投注", "获取赔率失败")) {
				log.error(message, handicapCode.name(), existHmId, "投注失败=" + betJSon.getString("FaildReason"));
				bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
		}
		JSONArray beforeBet = JSONArray.parseArray(betInfo);
		JSONObject json = (JSONObject) beforeBet.get(beforeBet.size() - 1);

		// 投注成功
		if (StringTool.notEmpty(json.get("XDnum"))) {
			bean.success();
			bean.setData(json.get("XDnum"));
			return bean;
		}

		log.error(message, handicapCode.name(), existHmId, "未知的错误信息=" + betInfo);
		bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
		bean.putSysEnum(HcCodeEnum.CODE_403);
		return bean;
	}

	/**
	 * 处理投注结果
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param betInfo   投注结果
	 * @return 投注结果
	 */
	private JsonResultBeanPlus resultProcess(String existHmId, JSONObject betInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (ContainerTool.isEmpty(betInfo)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(betInfo.getString("FaildReason"), "已超过您的可用余额", "停止投注", "获取赔率失败")) {
			log.error(message, handicapCode.name(), existHmId, "投注失败=" + betInfo);
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		int faildReason = betInfo.getInteger("FaildReason");
		// 投注成功
		if (faildReason == 0) {
			bean.success();
			return bean;
		}
		log.error(message, handicapCode.name(), existHmId, "未知的错误信息=" + betInfo);
		bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
		bean.putSysEnum(HcCodeEnum.CODE_403);
		return bean;
	}
}
