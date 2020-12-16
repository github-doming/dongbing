package com.common.handicap.newws;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.common.handicap.crawler.BaseMemberCrawler;
import com.common.handicap.crawler.entity.HandicapUser;
import com.common.handicap.crawler.entity.MemberUser;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NEWWS盘口爬虫类
 *
 * @Author: Dongming
 * @Date: 2020-05-11 14:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class NewWsMemberCrawler extends BaseMemberCrawler<NewWsMemberGrab> {
	public NewWsMemberCrawler() {
		handicapCode = BaseHandicapUtil.Code.NEWWS;
	}

	@Override
	protected JsonResultBeanPlus login(HttpClientConfig httpConfig, HandicapUser accountInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapUrl = accountInfo.handicapUrl();
		if (handicapUrl.startsWith("http://")) {
			handicapUrl = handicapUrl.replace("http", "https");
		}
		if (!handicapUrl.endsWith("/")) {
			handicapUrl = handicapUrl.concat("/");
		}

		try {
			String routeHtml = crawlerGrab.getSelectRoutePage(httpConfig, handicapUrl, accountInfo.handicapCaptcha());
			if (StringTool.isEmpty(routeHtml)) {
				log.info(getLogTitle(), accountInfo.account() + ",获取登陆URL=" + routeHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			//5条会员线路数组
			String[] routes = crawlerGrab.getMemberRoute(httpConfig, routeHtml);
			if (ContainerTool.isEmpty(routes)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			String snn = accountInfo.handicapUrl().replace("http://", "");

			//获取登录信息map
			Map<String, String> loginInfoMap = crawlerGrab.getLoginHtml(httpConfig, routes, snn, accountInfo.handicapCaptcha());
			String loginUrl = loginInfoMap.get("loginSrc");
			String ticket = loginInfoMap.get("ticket");

			String verifyCode = crawlerGrab.getVerifyCode(httpConfig, loginUrl, ticket);

			String loginInfo = crawlerGrab.getLogin(httpConfig, accountInfo, verifyCode, loginUrl, ticket, snn);
			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			JSONObject result = JSONObject.parseObject(loginInfo);
			if (StringTool.notEmpty(result.get("errorMessage"))) {
				log.error(getLogTitle(), handicapCode.name(), accountInfo.account(), "登录的URL=" + loginUrl);
				bean.putEnum(crawlerGrab.loginError(loginInfo));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			JSONObject jsonObject = result.getJSONObject("dataObject");

			crawlerGrab.ticket(jsonObject.getString("ticket"));
			crawlerGrab.memberId(jsonObject.getString("id"));

			Map<String, String> data = new HashMap<>(1);
			data.put("projectHost", loginUrl);
			bean.setData(data);
			bean.success();
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
			String userHtml = crawlerGrab.getUserInfo();
			if (StringTool.isEmpty(userHtml)) {
				log.info(getLogTitle(), handicapCode.name(), "获取会员账号信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if (StringTool.contains(userHtml, "您沒有登錄")) {
				log.info(getLogTitle(), "获取会员列表信息失败" + userHtml);
				needLogin();
				bean.setCode(CodeEnum.IBS_403_ERROR.getCode());
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}

			MemberUser member = memberImage.member();
			if (member == null) {
				member = new MemberUser().memberAccount(accountName());
				memberImage.member(member);
			}

			JSONObject jsonObject = JSONObject.parseObject(userHtml);
			JSONArray info = jsonObject.getJSONArray("dataObject");
			//会员盘
			member.memberType(info.getString(5));
			//信用额度
			member.creditQuota(NumberTool.getDouble(info.get(2)));
			//可用额度
			member.usedQuota(NumberTool.getDouble(info.get(3)));
			//盈亏金额
			member.profitAmount(NumberTool.getDouble(info.get(3)));

			bean.success(member);

		} catch (Exception e) {
			log.error(getLogTitle(), "获取用户信息失败:" + e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override
	public JsonResultBeanPlus gameLimit(BaseGameUtil.Code gameCode) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			JSONObject quota = crawlerGrab.getQuotaList(gameCode);
			log.trace(getLogTitle(), handicapCode.name(), "游戏【" + gameCode.name() + "】限额信息为：" + quota);
			if (ContainerTool.isEmpty(quota)) {
				bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			bean.success();
			bean.setData(quota);
		} catch (Exception e) {
			log.error(getLogTitle(), "获取游戏【" + gameCode.getName() + "】限额信息失败:" + e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}

	@Override
	public JsonResultBeanPlus betting(BaseGameUtil.Code gameCode, Object period, String betType,
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
		JSONObject odds = (JSONObject) memberImage.oddsInfo().get(gameCode).get(betType);

		try {
			Map<String, Object> betItemInfo = crawlerGrab.getBetItemInfo(gameCode, betItems, odds, period);
			if (StringTool.isEmpty(betItemInfo)) {
				log.info(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注失败,未存在正确的投注项");
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			JSONObject betInfo = crawlerGrab.betting(betItemInfo);
			//投注结果
			log.trace(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注结果为：" + betInfo);
			if (ContainerTool.isEmpty(betInfo)) {
				log.info(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注失败");
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			String errorMessage = betInfo.getString("errorMessage");
			if (StringTool.notEmpty(errorMessage) && StringTool.contains(errorMessage, "賠率變動", "赔率变动")) {
				oddsInfo(gameCode, betType);
				return betting(gameCode, period, betType, betItems, betErrors);
			}

			bean = resultProcess(betInfo);
			if (!bean.isSuccess()) {
				return bean;
			}
			JSONArray betResult = new JSONArray();
			JSONArray betInfos;
			JSONArray result = betInfo.getJSONArray("dataObject");
			Map<String, String> itemCode = BaseHandicapUtil.handicap(handicapCode).infoItemMap(gameCode);
			for (int i = 0; i < result.size(); i++) {
				JSONArray info = result.getJSONArray(i);
				betInfos = new JSONArray();
				// 注单
				betInfos.add(info.get(0));
				// 投注项
				betInfos.add(itemCode.get(info.getString(6).concat("-").concat(info.getString(8))));
				// 金额
				betInfos.add(info.getInteger(4));
				// 赔率
				betInfos.add(info.get(3));
				betResult.add(betInfos);

			}

			bean.success();
			bean.setData(betResult);
		} catch (Exception e) {
			log.error(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注失败" + e.getMessage());
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
		String errorCode = betInfo.getString("errorCode");
		// 投注成功
		if (StringTool.contains(errorCode, "-1")) {
			bean.success();
			return bean;
		}
		String errorMessage = betInfo.getString("errorMessage");
		if (StringTool.contains(errorMessage, "餘額不足")) {
			log.error(getLogTitle(), handicapCode.name(), "投注失败=" + betInfo);
			bean.putEnum(HcCodeEnum.IBS_403_POINT_NOT_ENOUGH);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(errorMessage, "低於")) {
			log.error(getLogTitle(), handicapCode.name(), "投注失败=" + betInfo);
			bean.putEnum(HcCodeEnum.IBS_403_POINT_LESS);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(errorMessage, "高於")) {
			log.error(getLogTitle(), handicapCode.name(), "投注失败=" + betInfo);
			bean.putEnum(HcCodeEnum.IBS_403_MORE_THAN_LIMIT);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(errorMessage, "已封盤")) {
			log.error(getLogTitle(), handicapCode.name(), "投注失败=" + betInfo);
			bean.putEnum(HcCodeEnum.IBS_403_SEAL_HANDICAP);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}

		log.error(getLogTitle(), handicapCode.name(), "未知的错误信息=" + betInfo);
		bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
		bean.putSysEnum(HcCodeEnum.CODE_403);
		return bean;
	}

	@Override
	protected NewWsMemberGrab crawlerGrab() {
		return new NewWsMemberGrab();
	}

	@Override
	public JsonResultBeanPlus oddsInfo(BaseGameUtil.Code gameCode, String betType) {
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

			odds.put(betType, oddsInfo.getJSONArray("dataObject").getJSONObject(5));
		} catch (Exception e) {
			log.error(getLogTitle(), "获取赔率信息异常，失败原因为:" + e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}

}
