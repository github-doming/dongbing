package com.ibm.common.utils.http.utils.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.UNConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.UNMemberTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.ComGameInfo;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import com.ibm.common.utils.http.utils.entity.MemberUserInfo;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: UN会员工具类
 * @Author: wwj
 * @Date: 2020/5/14 11:29
 * @Version: v1.0
 */
public class UNMemberUtil extends BaseMemberUtil {

	private HandicapUtil.Code handicapCode = HandicapUtil.Code.UN;

	private static volatile UNMemberUtil instance = null;

	public static UNMemberUtil findInstance() {
		if (instance == null) {
			synchronized (UNMemberUtil.class) {
				if (instance == null) {
					UNMemberUtil instance = new UNMemberUtil();
					// 初始化
					instance.init();
					UNMemberUtil.instance = instance;
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

			member.setProjectHost(bean.getData());
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
		if (handicapUrl.startsWith("http://")) {
			handicapUrl = handicapUrl.replace("http", "https");
		}
		if (!handicapUrl.endsWith("/")) {
			handicapUrl = handicapUrl.concat("/");
		}

		httpConfig.headers(null);
		httpConfig.httpContext(null);
		httpConfig.httpContext(HttpClientContext.create());
		try {
			//获取验证码
			String verifyCode = UNMemberTool.getVerifyCode(httpConfig, handicapUrl, null);
			//获取线路选择页面
			String routeHtml = UNMemberTool.getSelectRoutePage(httpConfig, handicapUrl, verifyCode, accountInfo.getHandicapCaptcha());
			if (StringTool.isEmpty(routeHtml)) {
				log.info(message, handicapCode.name(), accountInfo.getAccount(), "获取线路页面失败" + routeHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//4条会员线路数组
			String[] routes = UNMemberTool.getMemberRoute(httpConfig, routeHtml);
			if (ContainerTool.isEmpty(routes)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			//获取登录信息map
			Map<String, String> loginInfoMap = UNMemberTool.getLoginHtml(httpConfig, routes);
			if (ContainerTool.isEmpty(loginInfoMap)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String loginSrc = loginInfoMap.get("loginSrc");

			verifyCode = UNMemberTool.getVerifyCode(httpConfig, loginSrc, null);

			//登录
			String loginInfo = UNMemberTool.getLogin(httpConfig, accountInfo.getAccount(), accountInfo.getPassword(), verifyCode, loginSrc);
			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//错误处理 1@@^登录成功，请稍候…
			if (StringTool.notEmpty(loginInfo) && !StringTool.contains(loginInfo, "登录成功")) {
				log.error(message, handicapCode.name(), accountInfo.getAccount(), "登录的URL=" + loginSrc);
				bean.putEnum(UNMemberTool.loginError(loginInfo));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			bean.success(loginSrc);
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
			member.setAccountInfo(accountInfo);
			member.setProjectHost(bean.getData());
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
			String userHtml = UNMemberTool.getUserInfo(member);
			if (StringTool.isEmpty(userHtml)) {
				log.info(message, handicapCode.name(), existHmId, "获取会员账号信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}


			if (StringTool.contains(userHtml, "resultStr")) {
				member.setProjectHost(null);
				member.setHcConfig(null);
				return userInfo(existHmId, ++index[0]);
			}
			// [{"resultStr":"3~`^&/wb3/us0001/queryUserLeft?gameId=G_30&t=1593829580168&"}]

			JSONObject jsonObject = JSONObject.parseObject(userHtml);
			//获取用户信息
			MemberUserInfo memberUserInfo = member.getMemberUserInfo();
			//信用额度
			memberUserInfo.setCreditQuota(jsonObject.getString("credits"));
			//可用额度
			memberUserInfo.setUsedQuota(jsonObject.getString("avacredits"));
			//盈亏金额
			memberUserInfo.setProfitAmount(jsonObject.getString("ykMoney"));
			//会员盘
			memberUserInfo.setMemberType(jsonObject.getString("userHandicap"));
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
			String gameNo = UNConfig.GAME_CODE_ID.get(gameCode.name());
			JSONArray gameLimit = UNMemberTool.getQuotaList(member.getHcConfig(), member.getProjectHost(), gameNo);
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
		try {
			JSONObject oddsInfo = UNMemberTool.getOddsInfo(member, gameCode, betType);

			if (ContainerTool.isEmpty(oddsInfo)) {
				log.info(message, handicapCode.name(), existHmId, "游戏【" + gameCode + "】获取赔率信息失败");
				if (!odds.containsKey(betType)) {
					member.setProjectHost(null);
					member.setHcConfig(null);
					getOddsInfo(existHmId, gameCode, betType, ++index[0]);
				}
				return;
			}
			//p_id，赔率
			Map<GameUtil.Code, ComGameInfo> pIdMap = member.getPIdMap();
			ComGameInfo gameInfo;
			if (pIdMap.containsKey(gameCode)) {
				gameInfo = pIdMap.get(gameCode);
			} else {
				gameInfo = new ComGameInfo();
				pIdMap.put(gameCode, gameInfo);
			}
			gameInfo.setPeriod(oddsInfo.getString("nowIss"));

			odds.put(betType, oddsInfo.getJSONObject("result"));

		} catch (Exception e) {
			log.error(message, handicapCode.name(), existHmId, "获取盘口赔率失败,失败原因为：" + e);
		}
	}

	/**
	 * 投注
	 *
	 * @param existHmId  存在ID
	 * @param gameCode   游戏code
	 * @param betItems   投注信息
	 * @param betType    投注类型 固定值 B_109
	 * @return JsonResultBeanPlus
	 */
	public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode,List<String> betItems, String betType) {

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
			String bets = UNMemberTool.getBetItemInfo(gameCode, odds, betItems, member);
			log.trace( "bets==" + bets);
			// 投注
			JSONObject betInfo = UNMemberTool.betting(member.getHcConfig(), member.getProjectHost(), bets);
			// message: "({success:'1',promptName:'',isclose:''})"
			log.trace(message, handicapCode.name(), existHmId, "投注结果为：" + betInfo);
			//message -> ({success:'0',promptName:'出现重复下注，下注失败，请刷新下注状况，确认无误后再进行下注！',isclose:''})
			bean = resultProcess(betInfo.getString("message"), member, gameCode, betItems);
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
	 * @param betInfo  投注结果
	 * @param member   会员信息
	 * @param gameCode 游戏code
	 * @param betItems 投注信息
	 * @return JsonResultBeanPlus
	 */
	private JsonResultBeanPlus resultProcess(String betInfo, MemberCrawler member, GameUtil.Code gameCode, List<String> betItems) throws InterruptedException {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (ContainerTool.isEmpty(betInfo)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}

		String existHmId = member.getExistId();
		if (StringTool.contains(betInfo, "超出可用余额")) {
			log.error(message, handicapCode.name(), existHmId, "投注失败=" + betInfo);
			bean.putEnum(HcCodeEnum.IBS_403_POINT_NOT_ENOUGH);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(betInfo, "低于最低下注额", "超过单项最高")) {
			log.error(message, handicapCode.name(), existHmId, "投注失败=" + betInfo);
			bean.putEnum(HcCodeEnum.IBS_403_POINT_LESS);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(betInfo, "大于最高下注额")) {
			log.error(message, handicapCode.name(), existHmId, "投注失败=" + betInfo);
			bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(betInfo, "期号不一致", "已封盘", "不允许下注")) {
			log.error(message, handicapCode.name(), existHmId, "投注失败=" + betInfo);
			bean.putEnum(HcCodeEnum.IBS_403_SEAL_HANDICAP);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}

		if (StringTool.contains(betInfo, "1")) {
			int page = 1;
			int pageSize = 20;
			while (pageSize >= 20) {
				String html = UNMemberTool.getIsSettlePage(member, page);
				if (StringTool.isEmpty(html)) {
					bean.putEnum(HcCodeEnum.IBS_404_RULE_ERROR);
					bean.putSysEnum(HcCodeEnum.CODE_404);
					break;
				}
				JSONArray data = UNMemberTool.getIsSettleInfo(html, member.getPIdMap().get(gameCode).getPeriod());
				if (ContainerTool.isEmpty(data)) {
					bean.putEnum(HcCodeEnum.IBS_404_RESULT_PAGE);
					bean.putSysEnum(HcCodeEnum.CODE_404);
					break;
				}
				for (Object bet : data) {
					betItems.remove(bet);
				}
				if (ContainerTool.isEmpty(betItems)) {
					bean.success();
					break;
				}
				page++;
				pageSize = data.size();
			}
		} else {
			member.setProjectHost(null);
			bean.putEnum(HcCodeEnum.IBS_403_UNKNOWN);
			bean.putSysEnum(HcCodeEnum.CODE_403);
		}
		return bean.success();
	}
}
