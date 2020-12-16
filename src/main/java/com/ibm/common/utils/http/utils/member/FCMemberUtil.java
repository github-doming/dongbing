package com.ibm.common.utils.http.utils.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.FCConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import com.ibm.common.utils.http.tools.HandicapHttpClientTool;
import com.ibm.common.utils.http.tools.member.FCMemberTool;
import com.ibm.common.utils.http.utils.entity.AccountInfo;
import com.ibm.common.utils.http.utils.entity.MemberCrawler;
import com.ibm.common.utils.http.utils.entity.MemberUserInfo;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/27 10:50
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class FCMemberUtil extends BaseMemberUtil {

	private HandicapUtil.Code handicapCode = HandicapUtil.Code.FC;

	private static volatile FCMemberUtil instance = null;

	public static FCMemberUtil findInstance() {
		if (instance == null) {
			synchronized (FCMemberUtil.class) {
				if (instance == null) {
					FCMemberUtil instance = new FCMemberUtil();
					// 初始化
					instance.init();
					FCMemberUtil.instance = instance;
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

		httpConfig.headers(null);
		httpConfig.httpContext(null);
		try {
			//获取线路选择页面
			String routeHtml = FCMemberTool.getSelectRoutePage(httpConfig, accountInfo.getHandicapUrl(), accountInfo.getHandicapCaptcha());
			if (StringTool.isEmpty(routeHtml)) {
				log.info("FC获取线路页面失败=" + routeHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
//            String[] routes = {"http://vip.w1.hahha320.com:99","http://vip.w2.hahha320.com:99","http://vip.w3.hahha320.com:99","http://vip.w6.hahha320.com:99"};
			//5条会员线路数组
			String[] routes = FCMemberTool.getMemberRoute(httpConfig, routeHtml);
			//获取登录信息map
			String loginSrc = FCMemberTool.getLoginHtml(httpConfig, routes);
			if (ContainerTool.isEmpty(loginSrc)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//盘口协议页面
			JSONObject loginInfo = FCMemberTool.getLogin(httpConfig, accountInfo.getAccount(), accountInfo.getPassword(), loginSrc);
			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			//错误处理和是否等一次登录盘口
			String msg = loginInfo.getString("tipinfo");
			if (StringTool.notEmpty(msg) && StringTool.contains(msg, "帳號或者密碼錯誤", "帳號已經停用", "重置密碼")) {
				bean.putEnum(FCMemberTool.loginError(msg));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
//            //获取用户信息
//            Map<String, String> userData = new HashMap<>(2);
//            userData.put("projectHost", loginSrc);
//            userData.put("memberAccount", memberAccount);

			bean.setData(loginSrc);
			bean.success();
		} catch (Exception e) {
			log.error("FC盘口账号【" + accountInfo.getAccount() + "】登录失败,失败原因为：", e);
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
	 * @return 校验登录结果
	 */
	@Override
	public JsonResultBeanPlus valiLogin(String handicapUrl, String handicapCaptcha, String memberAccount,
										String memberPassword) {

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
			// 获取登录信息
			String userInfoHtml = FCMemberTool.getUserInfo(member.getHcConfig(), member.getProjectHost());
			// 登录超时
			if (StringTool.isEmpty(userInfoHtml)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			if (StringTool.contains(userInfoHtml,"snatchMsg")) {
				// 登陸超時、抢登、請重新登陸
				member.setProjectHost(null);
				member.setHcConfig(null);
				return userInfo(existHmId, ++index[0]);
			}
			JSONObject userInfo = JSONObject.parseObject(userInfoHtml).getJSONObject("data").getJSONObject("game_2");
			//获取用户信息
			MemberUserInfo memberUserInfo = member.getMemberUserInfo();
			//信用额度
			memberUserInfo.setCreditQuota(userInfo.getString("credit"));
			//可用额度
			memberUserInfo.setUsedQuota(userInfo.getDouble("usable_credit"));
			//盈亏金额
			memberUserInfo.setProfitAmount(userInfo.getDouble("profit"));
			//会员盘
			memberUserInfo.setMemberType(userInfo.getString("kind"));
			bean.success();
			bean.setData(memberUserInfo);
		} catch (Exception e) {
			log.error("FC盘口会员【" + existHmId + "】获取基本信息异常，失败原因为:", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 获取用户信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param flag      执行状态
	 * @return 用户信息
	 */
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
	 * 获取游戏限额
	 * http://vip.w1.hahha320.com:99/CreditInfo.aspx?p=1&id=2
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @return 游戏限额
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
		//获取盘口游戏code
		String game = FCConfig.GAME_CODE_ID.get(gameCode.name());

		try {
			JSONArray gameLimit = FCMemberTool.getQuotaList(member.getHcConfig(), member.getProjectHost(), game);
			log.trace("FC盘口会员【" + existHmId + "】游戏【" + gameCode.name() + "】限额信息为：" + gameLimit);
			if (ContainerTool.isEmpty(gameLimit)) {
				bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			bean.setData(gameLimit);
			bean.success();
		} catch (Exception e) {
			log.error("FC盘口会员【" + existHmId + "】获取游戏限额信息失败,失败原因为：", e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	/**
	 * 获取盘口赔率
	 * http://vip.w1.hahha320.com:99/L_PK10/Handler/Handler.ashx
	 * action: get_oddsinfo
	 *
	 * @param existHmId 已存在盘口会员id
	 * @param gameCode  游戏code
	 * @param betType   投注类型
	 * @return
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
			JSONObject data = FCMemberTool.getOddsInfo(member.getHcConfig(), member.getProjectHost(), gameCode, betType);
			if (data != null && !"y".equals(data.getString("openning"))
					|| StringTool.isEmpty(data.get("play_odds"))) {
				log.info("FC盘口会员【" + existHmId + "】账号被冻结");
				return;
			}

			// 获取赔率信息
			JSONObject oddsInfo = data.getJSONObject("play_odds");
			JSONObject newOdds = new JSONObject();
			Set<Map.Entry<String, Object>> set = oddsInfo.entrySet();
			for (Map.Entry entry : set) {
				String key = (String) entry.getKey();
				JSONObject value = (JSONObject) entry.getValue();
				newOdds.put(key.split("_")[1], value.get("pl"));
			}
			odds.put(betType, newOdds);
 			member.setJeuValidate((String) data.get("p_id"));
		} catch (Exception e) {
			log.error("FC盘口会员【" + existHmId + "】获取盘口赔率失败,失败原因为：", e);
		}
	}

	/**
	 * 检验信息,上次成功检验时间超过两分钟，删除用户信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return
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
	 * 投注
	 *
	 * @param existHmId 已存在id
	 * @param gameCode  游戏code
	 * @param betItems  投注项
	 * @param betType   投注类型
	 * @return 投注结果
	 */
	public JsonResultBeanPlus betting(String existHmId, GameUtil.Code gameCode, List<String> betItems, String betType) {
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

		//请求参数
		Map<String, String> betParameter = FCMemberTool.getBetParameter(gameCode, betType);
		String phaseid = member.getJeuValidate();
		String playpage = betParameter.get("playpage");
		String codePath = FCConfig.GAME_CODE_PATH.get(gameCode.name());
		JSONObject odds = (JSONObject) member.getOdds().get(gameCode).get(betType);

		try {
			//获取投注信息
			Map<String, String> ballCode = BallCodeTool.getBallCode(HandicapUtil.Code.FC, gameCode);
			Map<String, String> betsMap = FCMemberTool.getBetItemInfo(ballCode, betItems, odds);
			if (ContainerTool.isEmpty(betsMap)) {
				bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			JSONObject betInfo = FCMemberTool.betting(member.getHcConfig(), member.getProjectHost(), gameCode, betsMap, playpage, codePath, phaseid);
			log.trace("FC盘口会员【" + existHmId + "】投注结果为：" + betInfo);
			//处理投注结果
			bean = resultProcess(betInfo);
			if (!bean.isSuccess()) {
				return bean;
			}

			bean.success();
		} catch (Exception e) {
			log.error("FC盘口会员【" + existHmId + "】投注失败,失败原因为：", e);
			bean.error(e.getMessage());
		} finally {
			member.getHcConfig().defTimeOut();
		}
		return bean;
	}


	/**
	 * 处理投注结果
	 *
	 * @param betInfo 投注结果
	 * @return 投注结果
	 */
	private JsonResultBeanPlus resultProcess(JSONObject betInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (ContainerTool.isEmpty(betInfo)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		String message = betInfo.getString("tipinfo");
		//status==200,投注成功
		int status = betInfo.getInteger("success");
		if (status != 200) {
			if (status == 400) {
				// 已分盘
				if ("已截止".equals(message)) {
					bean.putEnum(HcCodeEnum.IBS_403_SEAL_HANDICAP);
					bean.putSysEnum(HcCodeEnum.CODE_403);
				}
				if ("頻率太快".equals(message)) {
					log.debug("下注頻率太快！");
					bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
					bean.putSysEnum(HcCodeEnum.CODE_403);
				}
				return bean;
			} else if (status == 600) {
				//赔率不对，补投
				bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
		}

		if (!StringTool.isContains(message, "下注成功")) {
			if (StringTool.contains(message, "額度")) {
				bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
			} else {
				log.error("未知的错误信息=" + betInfo);
				bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			}
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		bean.success();
		return bean;
	}
}
