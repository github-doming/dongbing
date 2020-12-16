package com.common.handicap.newcc;

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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
public class NewCcMemberCrawler extends BaseMemberCrawler<NewCcMemberGrab> {
	public NewCcMemberCrawler() {
		handicapCode = BaseHandicapUtil.Code.NEWCC;
	}

	@Override
	protected JsonResultBeanPlus login(HttpClientConfig httpConfig, HandicapUser accountInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapUrl = accountInfo.handicapUrl();
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

			//获取登录信息map
			Map<String, String> loginInfoMap = crawlerGrab.getLoginHtml(httpConfig, routes);
			String loginUrl = loginInfoMap.get("loginSrc");
			String verifyCode = crawlerGrab.getVerifyCode(httpConfig, loginUrl, null);

			String loginInfo = crawlerGrab.getMemberLogin(httpConfig, loginUrl, accountInfo.account(), accountInfo.password(),verifyCode);
			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			JSONObject result = JSONObject.parseObject(loginInfo);
			if (StringTool.notEmpty(result.get("FaildReason"))) {
				log.error(getLogTitle(), handicapCode.name(), accountInfo.account(), "登录的URL=" + loginUrl);
				bean.putEnum(crawlerGrab.loginError(loginInfo));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			if (result.getInteger("LoginStatus") == 1 && result.getInteger("IsNeedModityWord") != 0) {
				log.error(getLogTitle(), handicapCode.name(), accountInfo.account(), "登录的URL=" + loginUrl);
				bean.putEnum(HcCodeEnum.IBS_403_CHANGE_PASSWORD);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

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
			if (StringTool.contains(userHtml, "另一个地方登陆")) {
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
			//盈亏
			String gainHtml = crawlerGrab.getUserGain();
			if (ContainerTool.isEmpty(gainHtml)) {
				log.info(getLogTitle(), handicapCode.name(), "获取会员账号信息失败");
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			Document routeDocument = Jsoup.parse(userHtml);
			Element tbody = routeDocument.selectFirst("tbody");

			//信用额度
			String credidQuotal = tbody.getElementById("current").text();
			if (StringTool.contains(credidQuotal, "RMB")) {
				credidQuotal = credidQuotal.substring(0, credidQuotal.indexOf("("));
			}

			//会员盘
			member.memberType(tbody.getElementById("sel_Handicpa").text());
			//信用额度
			member.creditQuota(NumberTool.getDouble(credidQuotal));
			//可用额度
			member.usedQuota(NumberTool.getDouble(tbody.getElementById("Money_KY").text()));
			//使用金额
			member.usedAmount(NumberTool.getDouble(tbody.select("tr").last().text().split(" ")[1]));
			//盈亏金额
			member.profitAmount(NumberTool.getDouble(gainHtml.split("\\|")[1]));

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
			String quotaHtml = crawlerGrab.getQuotaList(gameCode);
			log.trace(getLogTitle(), handicapCode.name(), "游戏【" + gameCode.name() + "】限额信息为：" + quotaHtml);
			if (ContainerTool.isEmpty(quotaHtml)) {
				bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			JSONArray quotas = crawlerGrab.filterQuotaInfo(quotaHtml,memberImage.member().memberType());
			bean.success();
			bean.setData(quotas);
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
		try {
			String betItemInfo = crawlerGrab.getBetItemInfo(gameCode, betItems, memberImage.member().memberType());
			if (StringTool.isEmpty(betItemInfo)) {
				log.info(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注失败,未存在正确的投注项");
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			// 预投注处理
			String beforeBetHtml =crawlerGrab.getOddsAndName(betItemInfo, gameCode.name(),memberImage.member().memberType());
			bean = beforeBetResultProcess(beforeBetHtml);
			if (!bean.isSuccess()) {
				return bean;
			}
			String xdNum = (String) bean.getData();

			JSONObject betInfo = crawlerGrab.betting(xdNum);
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
				return betting( gameCode, period,  betType,betItems,betErrors);
			}

			bean = resultProcess(betInfo);
			if (!bean.isSuccess()) {
				return bean;
			}

			JSONArray betResult = new JSONArray();
			JSONArray betInfos;
			JSONArray result = betInfo.getJSONArray("OrderLst");
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
	 * 处理预投注结果
	 *
	 * @param betInfo   投注结果
	 * @return 投注结果
	 */
	private JsonResultBeanPlus beforeBetResultProcess( String betInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(betInfo)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(betInfo, "FaildReason")) {
			JSONObject betJson = JSONObject.parseObject(betInfo);
			if (StringTool.contains(betJson.getString("FaildReason"), "已超过您的可用余额", "停止投注", "获取赔率失败")) {
				log.error(getLogTitle(), handicapCode.name(),  "投注失败=" + betJson.getString("FaildReason"));
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

		log.error(getLogTitle(), handicapCode.name(), "未知的错误信息=" + betInfo);
		bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
		bean.putSysEnum(HcCodeEnum.CODE_403);
		return bean;
	}

	/**
	 * 处理投注结果
	 *
	 * @param betInfo   投注结果
	 * @return 投注结果
	 */
	private JsonResultBeanPlus resultProcess(JSONObject betInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (ContainerTool.isEmpty(betInfo)) {
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (StringTool.contains(betInfo.getString("FaildReason"), "已超过您的可用余额", "停止投注", "获取赔率失败")) {
			log.error(getLogTitle(), handicapCode.name(),  "投注失败=" + betInfo);
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
		log.error(getLogTitle(), handicapCode.name(),  "未知的错误信息=" + betInfo);
		bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
		bean.putSysEnum(HcCodeEnum.CODE_403);
		return bean;
	}

	@Override
	protected NewCcMemberGrab crawlerGrab() {
		return new NewCcMemberGrab();
	}

	@Override
	public JsonResultBeanPlus oddsInfo(BaseGameUtil.Code gameCode, String betType) {return null;}

}
