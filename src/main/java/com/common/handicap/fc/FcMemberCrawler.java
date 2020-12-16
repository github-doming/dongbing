package com.common.handicap.fc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.config.handicap.FcConfig;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.common.handicap.Handicap;
import com.common.handicap.crawler.BaseMemberCrawler;
import com.common.handicap.crawler.entity.HandicapUser;
import com.common.handicap.crawler.entity.MemberUser;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口爬虫类
 * @Author: null
 * @Date: 2020-07-13 16:27
 * @Version: v1.0
 */
public class FcMemberCrawler extends BaseMemberCrawler<FcMemberGrab> {
	public FcMemberCrawler() {
		handicapCode = BaseHandicapUtil.Code.FC;
	}

	@Override
	protected JsonResultBeanPlus login(HttpClientConfig httpConfig, HandicapUser accountInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			String routeHtml = crawlerGrab.getSelectRoutePage(httpConfig, accountInfo.handicapUrl(), accountInfo.handicapCaptcha());
			if (StringTool.isEmpty(routeHtml)) {
				log.info("FC获取线路页面失败=" + routeHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//5条会员线路数组
			String[] routes = crawlerGrab.getMemberRoute(httpConfig, routeHtml);
			//获取登录信息map
			String loginSrc = crawlerGrab.getMemberLoginHtml(httpConfig, routes);
			if (ContainerTool.isEmpty(loginSrc)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//盘口协议页面
			JSONObject loginInfo = crawlerGrab.getLogin(httpConfig, accountInfo.account(), accountInfo.password(), loginSrc);
			if (ContainerTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//错误处理和是否等一次登录盘口
			String msg = loginInfo.getString("tipinfo");
			if (StringTool.notEmpty(msg) && StringTool.contains(msg, "帳號或者密碼錯誤", "帳號已經停用", "重置密碼")) {
				bean.putEnum(crawlerGrab.loginError(msg));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			Map<String, String> data = new HashMap<>(1);
			data.put("projectHost", loginSrc);
			bean.success(data);
		} catch (Exception e) {
			log.error(getLogTitle(), accountInfo.account() + ",登录失败,失败原因为：" + e);
			bean.error(e.getMessage());
		}
		return bean;
	}


	@Override
	protected JsonResultBeanPlus userInfo() {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		try {
			String userInfoHtml = crawlerGrab.getUserInfo();
			// 登录超时
			if (StringTool.isEmpty(userInfoHtml)) {
				log.info(getLogTitle(), "获取会员账号信息失败");
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			if (StringTool.contains(userInfoHtml, "snatchMsg")) {
				needLogin();
				bean.putEnum(CodeEnum.IBS_403_ERROR);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			JSONObject userInfo = JSONObject.parseObject(userInfoHtml).getJSONObject("data").getJSONObject("game_2");
			//获取用户信息
			MemberUser member = memberImage.member();
			//获取账号信息
			if (member == null) {
				member = new MemberUser().memberAccount(accountName());
				memberImage.member(member);
			}
			putMember(userInfo, member);
			bean.success(member);
		} catch (Exception e) {
			log.error(getLogTitle(), "获取基本信息异常，失败原因为:" + e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override
	public JsonResultBeanPlus gameLimit(BaseGameUtil.Code gameCode) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			String game = BaseHandicapUtil.handicap(handicapCode).gameType().get(gameCode);
			JSONArray gameLimit = crawlerGrab.getQuotaList(game);
			log.trace(getLogTitle(), "游戏【" + gameCode.getName() + "】限额信息为：" + gameLimit);
			if (ContainerTool.isEmpty(gameLimit)) {
				bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			bean.success(gameLimit);
		} catch (Exception e) {
			log.error(getLogTitle(), "获取游戏限额信息失败,失败原因为：" + e);
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override
	public JsonResultBeanPlus oddsInfo(BaseGameUtil.Code gameCode, String betType) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//赔率信息
		Map<BaseGameUtil.Code, Map<String, Object>> memberOdds = memberImage.oddsInfo();
		Map<String, Object> odds;
		if (memberOdds.containsKey(gameCode)) {
			odds = memberOdds.get(gameCode);
		} else {
			odds = new HashMap<>(3);
			memberOdds.put(gameCode, odds);
		}
		try {
			//盘口游戏code
			JSONObject data = crawlerGrab.getOddsInfo(gameCode, betType);
			if (ContainerTool.isEmpty(data)) {
				log.info(getLogTitle(), "游戏【" + gameCode + "】获取赔率信息失败");
				if (!odds.containsKey(betType)) {
					needLogin();
				}
				bean.putEnum(HcCodeEnum.IBS_404_DATA);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if (!"y".equals(data.getString("openning")) || StringTool.isEmpty(data.get("play_odds"))) {
				log.info("FC盘口会员【" + memberImage.member().memberAccount() + "】账号被冻结");
				return bean;
			}
			// 获取赔率信息
			JSONObject oddsInfo = data.getJSONObject("play_odds");
			JSONObject newOdds = new JSONObject();
			for (Map.Entry<String, Object> entry : oddsInfo.entrySet()) {
				String key = entry.getKey();
				JSONObject value = (JSONObject) entry.getValue();
				newOdds.put(key.split("_")[1], value.get("pl"));
			}
			crawlerGrab.phaseid(data.getString("p_id"));

			odds.put(betType, newOdds);
		} catch (Exception e) {
			log.error(getLogTitle(), "获取赔率信息失败，失败原因为:" + e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override
	public JsonResultBeanPlus betting(BaseGameUtil.Code gameCode, Object period, String betType, List<String> betItems, List<String> betErrors) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (memberImage.needLogin()) {
			log.info(getLogTitle() + ",用户需要登录");
			bean = login();
			if (bean.notSuccess()) {
				return bean;
			}
			bean.setData(null);
			bean.setSuccess(false);
		}
		Map<String, Object> oddMap = memberImage.oddsInfo().get(gameCode);
		if (!oddMap.containsKey(betType)) {
			bean.putEnum(HcCodeEnum.IBS_404_DATA);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		Map<String, String> betParameter = crawlerGrab.getBetParameter(gameCode, betType);
		String phaseid = crawlerGrab.phaseid();
		String codePath = FcConfig.GAME_CODE_PATH.get(gameCode.name());
		JSONObject odds = (JSONObject) oddMap.get(betType);

		Handicap handicap = BaseHandicapUtil.handicap(handicapCode);
		try {
			Map<String, String> betsMap = crawlerGrab.getBetItemInfo(handicap, gameCode, betItems, odds);
			if (ContainerTool.isEmpty(betsMap)) {
				bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			String gameNo = handicap.gameType().get(gameCode);
			JSONObject betInfo = crawlerGrab.betting(betsMap, betParameter.get("playpage"), codePath, phaseid, gameNo);
			log.trace(getLogTitle(), "投注结果为：" + betInfo);
			//处理投注结果
			bean = resultProcess(betInfo);
			if (!bean.isSuccess()) {
				return bean;
			}
			bean.success();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}


	@Override
	protected FcMemberGrab crawlerGrab() {
		return new FcMemberGrab();
	}

	/**
	 * 放入会员信息
	 *
	 * @param userObj 用户信息
	 * @param member  会员主要类
	 */
	private void putMember(JSONObject userObj, MemberUser member) {
		//信用额度
		member.creditQuota(userObj.getDouble("credit"));
		//可用额度
		member.usedQuota(userObj.getDouble("usable_credit"));

		//盈亏金额
		member.profitAmount(userObj.getDouble("profit"));
		//会员盘
		member.memberType(userObj.getString("kind"));
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
