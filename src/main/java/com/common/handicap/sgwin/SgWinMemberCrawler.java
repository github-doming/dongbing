package com.common.handicap.sgwin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.HcCodeEnum;
import com.common.handicap.Handicap;
import com.common.handicap.crawler.BaseMemberCrawler;
import com.common.handicap.crawler.entity.HandicapUser;
import com.common.handicap.crawler.entity.MemberUser;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Dongming
 * @Date: 2020-06-09 14:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SgWinMemberCrawler extends BaseMemberCrawler<SgWinMemberGrab> {
	public SgWinMemberCrawler() {
		handicapCode = BaseHandicapUtil.Code.SGWIN;
	}

	@Override protected JsonResultBeanPlus login(HttpClientConfig httpConfig, HandicapUser accountInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapUrl = accountInfo.handicapUrl();
		if (!handicapUrl.endsWith("/")) {
			handicapUrl = handicapUrl.concat("/");
		}
		try {
			//获取线路选择页面
			String routeHtml = crawlerGrab
					.getSelectRoutePage(httpConfig, handicapUrl, accountInfo.handicapCaptcha());
			if (StringTool.isEmpty(routeHtml)) {
				log.info(getLogTitle(), accountInfo.account() + "获取会员登入页面失败");
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//会员线路数组
			String[] routes = crawlerGrab.getMemberRoute(httpConfig, routeHtml);
			if (ContainerTool.isEmpty(routes)) {
				log.info(getLogTitle(), accountInfo.account() + "获取会员线路失败");
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//获取登录信息map
			Map<String, String> loginInfoMap = crawlerGrab.getLoginHtml(httpConfig, routes);
			if (ContainerTool.isEmpty(loginInfoMap)) {
				log.info(getLogTitle(), accountInfo.account() + "获取登录页面信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String loginSrc = loginInfoMap.get("loginSrc");
			//获取验证码
			String verifyCode = crawlerGrab.getVerifyCode(httpConfig, loginSrc, loginInfoMap.remove("code"));

			httpConfig.setHeader("Referer", loginSrc.concat("login"));
			httpConfig.httpContext(HttpClientContext.create());
			//盘口协议页面
			String loginInfo = crawlerGrab
					.getLogin(httpConfig, accountInfo.account(), accountInfo.password(), verifyCode, loginSrc,
							loginInfoMap.remove("action"));

			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//错误处理和是否第一次登录盘口
			if (!StringTool.isContains(loginInfo, "为避免出现争议") || StringTool.isContains(loginInfo, "修改密码")) {
				log.info(getLogTitle(), accountInfo.account() + "登录失败，URL=" + loginSrc);
				bean.putEnum(crawlerGrab.loginError(loginInfo));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			Map<String, String> data = new HashMap<>(1);
			data.put("projectHost", loginSrc.concat("member/index"));
			bean.success(data);
		} catch (Exception e) {
			log.error(getLogTitle(), accountInfo.account() + ",登录失败,失败原因为：" + e);
			bean.error(e.getMessage());
		}
		return bean;
	}
	@Override protected JsonResultBeanPlus userInfo() {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			JSONObject userObj = crawlerGrab.getUserInfo();
			if (ContainerTool.isEmpty(userObj)) {
				log.info(getLogTitle(), "获取会员账号信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//获取用户信息
			MemberUser member = memberImage.member();

			//获取账号信息
			if (member == null) {
				member = new MemberUser().memberAccount(accountName());
				memberImage.member(member);
			}
			putMember(userObj, member);
			bean.success(member);
		} catch (Exception e) {
			log.error(getLogTitle(), "获取基本信息异常，失败原因为:" + e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override public JsonResultBeanPlus gameLimit(BaseGameUtil.Code gameCode) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			JSONArray gameLimit = crawlerGrab.getQuotaList(gameCode);
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

	@Override public JsonResultBeanPlus oddsInfo(BaseGameUtil.Code gameCode, String betType) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			//赔率信息
			Map<BaseGameUtil.Code, Map<String, Object>> memberOdds = memberImage.oddsInfo();
			Map<String, Object> odds;
			if (memberOdds.containsKey(gameCode)) {
				odds = memberOdds.get(gameCode);
			} else {
				odds = new HashMap<>(3);
				memberOdds.put(gameCode, odds);
			}
			//盘口游戏code
			JSONObject oddsInfo = crawlerGrab.getOddsInfo(gameCode, betType);
			if (ContainerTool.isEmpty(oddsInfo)) {
				log.info(getLogTitle(), "游戏【" + gameCode + "】获取赔率信息失败");
				if (!odds.containsKey(betType)) {
					needLogin();
				}
				bean.putEnum(HcCodeEnum.IBS_404_DATA);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			odds.put(betType, oddsInfo);
		} catch (Exception e) {
			log.error(getLogTitle(), "获取赔率信息失败，失败原因为:" + e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override public JsonResultBeanPlus betting(BaseGameUtil.Code gameCode, Object period, String betType,
			List<String> betItems, List<String> betErrors) {
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
		JSONObject odds = (JSONObject) oddMap.get(betType);
		Handicap handicap = BaseHandicapUtil.handicap(handicapCode);
		String gameType = handicap.gameType().get(gameCode.name());
		//获取投注项信息
		JSONArray betsArray = crawlerGrab.getBetItemInfo(handicap, gameCode, betItems, odds);
		if (ContainerTool.isEmpty(betsArray)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}

		try {
			Object roundno = handicap.handicapPeriod(gameCode, period);
			//获取投注信息
			JSONObject betInfo = crawlerGrab.betting(betsArray, roundno, gameType);
			log.trace(getLogTitle(), "投注结果为：" + betInfo);
			//处理投注结果
			resultProcess(bean, betInfo);
			if (!bean.isSuccess()) {
				return bean;
			}

			Map<String, String> infoItem = handicap.infoItemMap(gameCode);
			//注单号
			JSONArray ids = betInfo.getJSONArray("ids");
			//获取未结算信息
			int page = 1;
			int pageSize = 15;
			JSONArray betResult = new JSONArray();
			while (pageSize >= 15 && ids.size() > betResult.size()) {
				JSONArray data = crawlerGrab.getIsSettlePage(page);
				pageSize = data.size();
				page++;
				crawlerGrab.matchIsSettleInfo(betResult,infoItem, data, ids);
			}
			//投注结果返回信息
			bean.success(betResult);
		} catch (Exception e) {
			log.error(getLogTitle(), "投注失败,失败原因为：" + e);
			bean.error(e.getMessage());
		} finally {
			memberImage.httpConfig().defTimeOut();
		}
		return bean;
	}

	/**
	 * 处理投注结果
	 *
	 * @param bean    投注结果
	 * @param betInfo 投注结果
	 */
	private void resultProcess(JsonResultBeanPlus bean, JSONObject betInfo) {
		if (ContainerTool.isEmpty(betInfo)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return;
		}
		//status==0,投注成功
		int status = betInfo.getInteger("status");
		if (status != 0) {
			if (status == 3) {
				//低于或高于限额
				bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return;
			} else if (status == 1) {
				//赔率不对，补投
				bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return;
			}
		}
		if (betInfo.containsKey("message")) {
			String message = betInfo.getString("data");
			if (StringTool.isContains(message, "已被上级禁止投注，请与上级联系")) {
				bean.putEnum(HcCodeEnum.IBS_403_STOP_BET);
			} else if (StringTool.contains(message, "最高", "最低")) {
				bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
			} else {
				log.error(getLogTitle(), "未知的错误信息=" + betInfo);
				bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			}
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return;
		}
		if (!betInfo.containsKey("ids")) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return;
		}
		//获取用户信息
		MemberUser member = memberImage.member();
		JSONObject userObj = betInfo.getJSONObject("account");
		//信用额度
		putMember(userObj, member);
		bean.success(member);

	}
	/**
	 * 放入会员信息
	 *
	 * @param userObj 用户信息
	 * @param member  会员主要类
	 */
	private void putMember(JSONObject userObj, MemberUser member) {
		//信用额度
		member.creditQuota(userObj.getDouble("maxLimit"));
		//可用额度
		member.usedQuota(userObj.getDouble("balance"));
		//使用金额
		member.usedAmount(userObj.getDouble("betting"));
		//盈亏金额
		member.profitAmount(NumberTool.getDouble(userObj, "result", 0));
	}

	@Override protected SgWinMemberGrab crawlerGrab() {
		return new SgWinMemberGrab();
	}
}
