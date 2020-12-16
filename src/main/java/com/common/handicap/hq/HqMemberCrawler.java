package com.common.handicap.hq;

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
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.RsaTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HQ 盘口爬虫类
 *
 * @Author: Dongming
 * @Date: 2020-05-16 17:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HqMemberCrawler extends BaseMemberCrawler<HqMemberGrab> {
	public HqMemberCrawler() {
		handicapCode = BaseHandicapUtil.Code.HQ;
	}
	@Override protected JsonResultBeanPlus login(HttpClientConfig httpConfig, HandicapUser accountInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			String href = crawlerGrab
					.getMemberHref(httpConfig, accountInfo.handicapUrl(), accountInfo.handicapCaptcha());
			if (StringTool.isEmpty(href)) {
				log.info(getLogTitle(), accountInfo.account() + "获取会员登入页面失败");
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			//获取线路
			String[] routes = crawlerGrab.getSelectRoutePage(httpConfig, href, accountInfo.handicapUrl());
			if (ContainerTool.isEmpty(routes)) {
				log.info(getLogTitle(), accountInfo.account() + "获取会员线路失败");
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			httpConfig.httpContext().getCookieStore().clear();
			//获取登录页面
			Map<String, String> loginInfo = crawlerGrab.getLoginHtml(httpConfig, routes, accountInfo.handicapCaptcha());
			if (ContainerTool.isEmpty(loginInfo) || !loginInfo.containsKey("hostUrl")) {
				log.info(getLogTitle(), accountInfo.account() + "获取登录页面信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			Map<String, String> scriptInfo = crawlerGrab.getScriptInfo(loginInfo.get("html"));
			if (ContainerTool.isEmpty(scriptInfo)) {
				log.info(getLogTitle(), accountInfo.account() + "获取登录页面失败");
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String projectHost = loginInfo.get("hostUrl");
			String sessionId = scriptInfo.get("SESSIONID");
			//加密key
			JSONObject encryptKey = crawlerGrab.getEncryptKey(httpConfig, sessionId, projectHost);
			if (ContainerTool.isEmpty(encryptKey)) {
				log.info(getLogTitle(), accountInfo.account() + "获取登录加密key失败");
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String pke = encryptKey.getString("e");
			String pk = encryptKey.getString("m");
			String logpk = pke.concat(",").concat(pk);

			RSAPublicKey pubKey = RsaTool
					.getPublicKey(new BigInteger(pk, 16).toString(), Integer.parseInt(pke, 16) + "");

			String account = URLEncoder.encode(accountInfo.account().trim(), "UTF-8").concat(",")
					.concat(URLEncoder.encode(accountInfo.account().trim(), "UTF-8"));

			String mi = RsaTool.encryptByPublicKey(account, pubKey);

			//登录
			String loginHtml = crawlerGrab
					.getLogin(httpConfig, projectHost, sessionId, accountInfo.handicapCaptcha(), mi, logpk);

			if (StringTool.isEmpty(loginHtml)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if (StringTool.isContains(loginHtml, "用户名或密码不正确")) {
				bean.putEnum(HcCodeEnum.IBS_403_USER_ACCOUNT);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			if (StringTool.isContains(loginHtml, "账号已被禁用")) {
				bean.putEnum(HcCodeEnum.IBS_403_USER_STATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//协议页面
			String agreement = crawlerGrab.getAcceptAgreement(httpConfig, projectHost, sessionId);
			if (StringTool.isEmpty(agreement)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			JSONObject agreementResult = JSONObject.parseObject(agreement);
			if (agreementResult.getInteger("Status") == 1) {
				//需要修改密码
				if (agreementResult.getInteger("Data") == 1 || agreementResult.getInteger("Data") == 3) {
					bean.putEnum(HcCodeEnum.IBS_403_CHANGE_PASSWORD);
					bean.putSysEnum(HcCodeEnum.CODE_403);
					return bean;
				}
			}
			String index = crawlerGrab.getIndex(httpConfig, projectHost, sessionId);
			if (StringTool.isEmpty(index)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			Map<String, String> data = new HashMap<>(1);
			data.put("projectHost", String.format("http://%s%s/", projectHost, sessionId));
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
				log.info(getLogTitle(), "获取会员用户信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if (userObj.containsKey("data")) {
				if ("login".equals(userObj.get("data"))) {
					needLogin();
				}
				bean.putEnum(HcCodeEnum.IBS_404_DATA);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//获取用户信息
			MemberUser member = memberImage.member();

			//获取账号信息
			if (member == null || StringTool.isEmpty(member.memberAccount())) {
				JSONObject accountObj = crawlerGrab.getMemberAccount();
				if (ContainerTool.isEmpty(accountObj)) {
					log.info(getLogTitle(), "获取会员账号信息失败");
					bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
					bean.putSysEnum(HcCodeEnum.CODE_404);
					return bean;
				}
				if (accountObj.containsKey("data")) {
					if ("login".equals(accountObj.get("data"))) {
						needLogin();
					}
					bean.putEnum(HcCodeEnum.IBS_404_DATA);
					bean.putSysEnum(HcCodeEnum.CODE_404);
					return bean;
				}
				if (member == null) {
					member = new MemberUser();
					memberImage.member(member);
				}
				member.memberAccount(accountObj.getString("Account"));
			}
			//信用额度
			member.creditQuota(userObj.getDouble("Credit"));
			//可用额度
			member.usedQuota(userObj.getDouble("Balance"));
			//使用金额
			member.usedAmount(userObj.getDouble("BetMoney"));
			//盈亏金额
			member.profitAmount(
					userObj.getDouble("Balance") + userObj.getDouble("BetMoney") - userObj.getDouble("Credit"));
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
			if (StringTool.isContains(gameLimit.toJSONString(), "系统升级中")) {
				bean.putEnum(HcCodeEnum.IBS_404_DATA);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			} else if (StringTool.isContains(gameLimit.toJSONString(), "login")) {
				needLogin();
				bean.putEnum(HcCodeEnum.IBS_404_DATA);
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
			JSONObject oddsInfo = crawlerGrab.getOddsInfo(gameCode, betType);
			if (ContainerTool.isEmpty(oddsInfo)) {
				log.info(getLogTitle(), "获取赔率信息为空");
				//没有历史投注赔率数据
				if (!odds.containsKey(betType)) {
					needLogin();
				}
				bean.putEnum(HcCodeEnum.IBS_404_DATA);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if (oddsInfo.containsKey("errorInfo")) {
				log.error(getLogTitle(), "获取赔率信息为空,错误信息=" + oddsInfo);
				bean.putEnum(HcCodeEnum.IBS_404_DATA);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			odds.put(betType, oddsInfo.getJSONObject("Data").getJSONArray("OddsData"));
		} catch (Exception e) {
			log.error(getLogTitle(), "获取赔率信息异常，失败原因为:" + e.getMessage());
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
		Object oddsObj = memberImage.oddsInfo().get(gameCode).get(betType);
		if (ContainerTool.isEmpty(oddsObj)) {
			bean.putEnum(HcCodeEnum.IBS_404_DATA);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		JSONArray odds = (JSONArray) oddsObj;
		Handicap handicap = BaseHandicapUtil.handicap(handicapCode);
		String gameType = handicap.gameType().get(gameCode.name());
		//获取投注项信息
		JSONArray betsArray = crawlerGrab.getBetItemInfo(gameCode, gameType, odds, betItems, betErrors);
		if (ContainerTool.isEmpty(betsArray)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		JSONObject betParams = new JSONObject();
		betParams.put("BetItems", betsArray);
		betParams.put("LotteryId", Integer.parseInt(gameType));
		betParams.put("PeriodId", handicap.handicapPeriod(gameCode, period));
		try {
			JSONObject betInfo = crawlerGrab.betting(betParams, gameType);
			//投注结果
			log.trace(getLogTitle(), "投注结果为：" + betInfo);
			if (ContainerTool.isEmpty(betInfo)) {
				bean = getBettingSuccess(gameCode, gameType, betItems);
				if (!bean.isSuccess()) {
					if (HcCodeEnum.IBS_403_BET_FAIL.getCode().equals(bean.getCode())) {
						return betting(gameCode, period, betType, betItems, betErrors);
					}
				}
				return bean;
			}
			if (StringTool.contains(betInfo.get("Data").toString(), "最高", "最低")) {
				bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				betErrors.addAll(betItems);
				return bean;
			} else if (StringTool.contains(betInfo.get("Data").toString(), "停押", "停用")) {
				bean.putEnum(HcCodeEnum.IBS_403_STOP_BET);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				betErrors.addAll(betItems);
				return bean;
			}
			//获取用户信息
			MemberUser member=memberImage.member();

			JSONObject data = betInfo.getJSONObject("Data");
			//可用额度
			member.usedQuota(data.getDouble("Balance"));

			JSONArray items = data.getJSONArray("BetItems");
			//投注成功项信息
			JSONArray betResult = crawlerGrab.getBetResult(gameCode,gameType ,items,betErrors );

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
	 * 获取投注成功信息
	 *
	 * @param gameType 盘口游戏code
	 * @param betItems 投注信息
	 * @param gameCode 游戏code
	 * @return 投注成功信息
	 */
	private JsonResultBeanPlus getBettingSuccess(BaseGameUtil.Code gameCode, String gameType, List<String> betItems) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		//投注成功项
		JSONObject successResult = crawlerGrab.getBettingResult(gameType);
		if (ContainerTool.isEmpty(successResult)) {
			bean.putEnum(HcCodeEnum.IBS_404_DATA);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		if (successResult.containsKey("data")) {
			if (StringTool.isContains(successResult.getString("data"), "系统升级中")) {
				bean.putEnum(HcCodeEnum.IBS_403_HANDICAP_UPDATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			} else if (StringTool.isContains(successResult.getString("data"), "login")) {
				needLogin();
				bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
		}
		JSONObject data = successResult.getJSONObject("Data");
		//投注项个数为0，补投
		if (data.getInteger("RecordCount") <= 0) {
			needLogin();
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		//过滤投注成功项(遇到相同投注项暂未处理)
		JSONArray betResult = crawlerGrab.filterSuccessInfo(data, betItems, gameCode);
		if (ContainerTool.isEmpty(betResult)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		bean.success(betResult);
		return bean;
	}

	@Override protected HqMemberGrab crawlerGrab() {
		return new HqMemberGrab();
	}
}
