package com.common.handicap.un;

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
 * UN盘口爬虫类
 *
 * @Author: Dongming
 * @Date: 2020-05-11 14:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class UnMemberCrawler extends BaseMemberCrawler<UnMemberGrab> {
	public UnMemberCrawler() {
		handicapCode = BaseHandicapUtil.Code.UN;
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
			String verifyCode = crawlerGrab.getVerifyCode(httpConfig, handicapUrl, "");
			String routeHtml = crawlerGrab.getSelectRoutePage(httpConfig, handicapUrl, verifyCode, accountInfo.handicapCaptcha());
			if (StringTool.isEmpty(routeHtml)) {
				log.info(getLogTitle(), accountInfo.account() + ",获取登陆URL=" + routeHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}

			//4条会员线路数组
			String[] routes = crawlerGrab.getMemberRoute(httpConfig, routeHtml);
			if (ContainerTool.isEmpty(routes)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			//获取登录信息map
			Map<String, String> loginInfoMap = crawlerGrab.getLoginHtml(httpConfig, routes);
			String loginUrl = loginInfoMap.get("loginSrc");
			verifyCode = crawlerGrab.getVerifyCode(httpConfig, handicapUrl, "");

			String loginInfo = crawlerGrab.getLogin(httpConfig, accountInfo.account(), accountInfo.password(), verifyCode, loginUrl);
			if (StringTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			if (StringTool.notEmpty(loginInfo) && !StringTool.contains(loginInfo, "登录成功")) {
				log.error(getLogTitle(), handicapCode.name(), accountInfo.account(), "登录的URL=" + loginUrl);
				bean.putEnum(crawlerGrab.loginError(loginInfo));
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
			if (StringTool.contains(userHtml, "resultStr")) {
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

			JSONObject info = JSONObject.parseObject(userHtml);
			//会员盘
			member.memberType(info.getString("userHandicap"));
			//信用额度
			member.creditQuota(NumberTool.getDouble(info.get("credits")));
			//可用额度
			member.usedQuota(NumberTool.getDouble(info.get("avacredits")));
			//使用金额
//			member.usedAmount(NumberTool.getDouble(info.get("usecreditquota")));
			//盈亏金额
			member.profitAmount(NumberTool.getDouble(info.get("ykMoney")));

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
			if (StringTool.isEmpty(quotaHtml)) {
				bean.putEnum(HcCodeEnum.IBS_404_BET_LIMIT);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//过滤限额信息
			JSONArray quotas = crawlerGrab.filterQuotaInfo(quotaHtml);

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
		JSONObject odds = (JSONObject) memberImage.oddsInfo().get(gameCode).get(betType);


		try {
			String betItemInfo = crawlerGrab.getBetItemInfo(gameCode, betItems, odds, memberImage.member().memberType(), period);
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
			if ("1".equals(betInfo.get("message"))) {
				int page = 1;
				int pageSize = 20;
				while (pageSize >= 20) {
					String html = crawlerGrab.getIsSettlePage(page);
					if (StringTool.isEmpty(html)) {
						bean.putEnum(HcCodeEnum.IBS_404_RULE_ERROR);
						bean.putSysEnum(HcCodeEnum.CODE_404);
						break;
					}
					JSONArray data = crawlerGrab.getIsSettleInfo(html, period);
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

				if (ContainerTool.notEmpty(betItems)) {
					return betting(gameCode, period, betType, betItems, betErrors);
				}
				return bean.success();

			} else if ("0".equals(betInfo.get("message"))) {
				log.info(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】获取投注信息失败=" + betInfo);
				needLogin();
				return betting(gameCode, period, betType, betItems, betErrors);
			}
			bean.success();
		} catch (Exception e) {
			log.error(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注失败" + e.getMessage());
		}
		return bean;
	}

	@Override
	protected UnMemberGrab crawlerGrab() {
		return new UnMemberGrab();
	}

	@Deprecated
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

			JSONObject oddsInfo = crawlerGrab.getOddsInfo(gameCode.name(), memberImage.member().memberType());
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

			odds.put(betType, oddsInfo.getJSONObject("result"));
		} catch (Exception e) {
			log.error(getLogTitle(), "获取赔率信息异常，失败原因为:" + e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}

}
