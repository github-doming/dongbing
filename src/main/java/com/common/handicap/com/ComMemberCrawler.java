package com.common.handicap.com;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.config.handicap.ComConfig;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.HcCodeEnum;
import com.common.handicap.Handicap;
import com.common.handicap.crawler.BaseMemberCrawler;
import com.common.handicap.crawler.entity.HandicapUser;
import com.common.handicap.crawler.entity.MemberUser;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * COM 盘口爬虫类
 *
 * @Author: Dongming
 * @Date: 2020-06-09 18:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ComMemberCrawler extends BaseMemberCrawler<ComMemberGrab> {

	public ComMemberCrawler() {
		handicapCode = BaseHandicapUtil.Code.COM;
	}

	@Override protected JsonResultBeanPlus login(HttpClientConfig httpConfig, HandicapUser accountInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			httpConfig.httpContext(HttpClientContext.create());
			//获取线路选择页面
			String routeHtml = crawlerGrab.getSelectRoutePage(accountInfo.handicapUrl(), accountInfo.handicapCaptcha());
			if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "線路選擇")) {
				log.info(getLogTitle(), accountInfo.account() + "获取线路页面失败：" + routeHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//会员线路数组
			String[] routes = crawlerGrab.getMemberRoute(routeHtml);
			httpConfig.httpContext().getCookieStore().clear();

			//选择登录线路
			String loginSrc = crawlerGrab.getLoginHtml(httpConfig, routes);
			if (StringTool.isEmpty(loginSrc)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_ROUTE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//TODO 暂未处理验证码
			//处理登录结果json
			JSONObject loginInfo = crawlerGrab
					.getLogin(httpConfig, accountInfo.account(), accountInfo.password(), loginSrc);
			if (ContainerTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_404_PAGE_LOGIN_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//错误处理和是否第一次登录盘口
			if (loginInfo.getInteger("success") != 200 || StringTool.notEmpty(loginInfo.get("tipinfo"))) {
				bean.putEnum(crawlerGrab.loginError(loginInfo.getString("tipinfo")));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			//获取browserCode
			String browserCode = crawlerGrab.getIndex(httpConfig, loginSrc);
			if (StringTool.isEmpty(browserCode)) {
				bean.putEnum(HcCodeEnum.IBS_404_INDEX_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}

			Map<String, String> data = new HashMap<>(1);
			data.put("projectHost", loginSrc.concat("/"));
			data.put("browserCode", browserCode);
			//会员账户

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
			if (userObj.getInteger("success") == 100 || StringTool.isEmpty(userObj.get("data"))) {
				log.info(getLogTitle(), "获取会员账号信息错误,错误内容=" + userObj);
				needLogin();
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if (!userObj.getJSONObject("data").containsKey("game_2")) {
				bean.putEnum(HcCodeEnum.IBS_404_USER_INFO);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			JSONObject userInfo = userObj.getJSONObject("data").getJSONObject("game_2");
			//获取用户信息
			MemberUser member = memberImage.member();
			//获取账号信息
			if (member == null) {
				member = new MemberUser().memberAccount(accountName());
				memberImage.member(member);
			}
			member.usedAmount(0D);
			putMember(userInfo, member);

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
			Map<String, Object> odds = memberOdds.computeIfAbsent(gameCode, k -> new HashMap<>(3));
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
			//判断success状态码
			if (oddsInfo.getInteger("success") != 200 || StringTool.isEmpty(oddsInfo.get("data")) || !oddsInfo
					.getJSONObject("data").containsKey("play_odds")) {
				log.info(getLogTitle(), "游戏【" + gameCode + "】获取赔率信息失败,错误信息=" + oddsInfo);
				if (!odds.containsKey(betType)) {
					needLogin();
				}
				bean.putEnum(HcCodeEnum.IBS_404_DATA);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			//获取用户信息
			JSONObject data = oddsInfo.getJSONObject("data");
			putMember(data, memberImage.member());

			//p_id，赔率
			if (memberImage.otherInfo() == null) {
				memberImage.otherInfo(new HashMap<>(4));
			}
			Map<String, Object> info = memberImage.otherInfo().computeIfAbsent(gameCode, k -> new HashMap<>(2));
			info.put("period", data.get("nn"));
			info.put("pId", data.get("p_id"));

			odds.put(betType, data.getJSONObject("play_odds"));
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
		Handicap handicap = BaseHandicapUtil.handicap(handicapCode);
		Object roundno = handicap.handicapPeriod(gameCode, period);
		Map<String, Object> info = memberImage.otherInfo().get(gameCode);
		//判断期数是否一致,不一致要重新获取pid
		if (!roundno.equals(info.get("period"))) {
			oddsInfo(gameCode, betType);
			if (!roundno.equals(info.get("period"))) {
				bean.putEnum(HcCodeEnum.IBS_404_DATA);
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
		}
		String playPage = crawlerGrab.getComGameType(gameCode, betType);
		JSONObject odds = (JSONObject) oddMap.get(betType);
		String gameUrl = ComConfig.GAME_URL.get(gameCode.name());

		String gameType = handicap.gameType().get(gameCode.name());
		Map<String, Object> betInfos = crawlerGrab.getBetItemInfo(gameCode, betItems, odds);
		betInfos.put("action", "put_money");
		betInfos.put("phaseid", info.get("pId"));
		betInfos.put("playpage", playPage);

		lock.lock();
		try {
			if (StringTool.isEmpty(info.get("jeuValidate"))) {
				String jeuValidate = crawlerGrab.getJeuValidate(gameType, gameUrl);
				if (StringTool.isEmpty(jeuValidate)) {
					log.error(getLogTitle(), "获取jeuValidate失败");
					needLogin();
					return betting(gameCode, period, betType, betItems, betErrors);
				}
				info.put("jeuValidate", jeuValidate);
			}
			betInfos.put("JeuValidate", info.get("jeuValidate"));

			//处理投注
			JSONObject result = crawlerGrab.betting(gameUrl, betInfos);
			log.trace(getLogTitle(), "投注结果为：" + result);
			//处理投注结果
			bean = resultProcess(result, gameCode, betItems, gameType, info);

		} catch (Exception e) {
			log.error(getLogTitle() + ",投注失败,失败原因为：" + e);
			bean.error(e.getMessage());
		} finally {
			lock.unlock();
		}
		return bean;
	}


	@Override protected ComMemberGrab crawlerGrab() {
		return new ComMemberGrab();
	}

	/**
	 * 放入会员信息
	 *
	 * @param userInfo 用户信息
	 * @param member   会员主要类
	 */
	private void putMember(JSONObject userInfo, MemberUser member) {
		member.creditQuota(userInfo.get("credit"));
		member.usedQuota(userInfo.get("usable_credit"));
		member.profitAmount(userInfo.get("profit"));
		member.memberType(userInfo.getString("kind"));
	}
	/**
	 * 处理投注结果
	 * @param betInfo  投注结果
	 * @param gameCode 投注编码
	 * @param betItems 投注详情
	 * @param gameType 游戏类型
	 * @param info 其他信息
	 * @return 投注结果
	 */
	private JsonResultBeanPlus resultProcess(JSONObject betInfo, BaseGameUtil.Code gameCode, List<String> betItems,
			String gameType, Map<String, Object> info) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (ContainerTool.isEmpty(betInfo)) {
			bean.putEnum(HcCodeEnum.IBS_404_DATA);
			bean.putSysEnum(HcCodeEnum.CODE_404);
			return bean;
		}
		if (StringTool.isEmpty(betInfo.get("data"))) {
			needLogin();
			bean.putEnum(HcCodeEnum.IBS_403_BET_FAIL);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		if (betInfo.getInteger("success") == 200) {
			bean.success();
		} else if (betInfo.getInteger("success") == 400) {
			int page = 1;
			int pageSize = 10;
			String masterId = "";
			while (pageSize >= 10) {
				String html = crawlerGrab.getIsSettlePage(gameType, page, masterId);
				if (StringTool.isEmpty(html)) {
					bean.putEnum(HcCodeEnum.IBS_404_SETTLEMENT_PAGE);
					bean.putSysEnum(HcCodeEnum.CODE_404);
					break;
				}
				List<String> settleInfos = crawlerGrab.getIsSettleInfo(html, gameCode, info.get("period"));
				if (ContainerTool.isEmpty(settleInfos)) {
					bean.putEnum(HcCodeEnum.IBS_404_SETTLEMENT_PAGE);
					bean.putSysEnum(HcCodeEnum.CODE_404);
					break;
				}
				for (String settleInfo : settleInfos) {
					betItems.remove(settleInfo);
				}
				if (ContainerTool.isEmpty(betItems)) {
					bean.success();
					break;
				}
				masterId = StringUtils.substringBetween(html, "var masterid = '", "';");
				page++;
				pageSize = settleInfos.size();
			}
			if (ContainerTool.notEmpty(betItems)) {
				bean.putEnum(HcCodeEnum.IBS_404_SETTLEMENT_PAGE);
				bean.putSysEnum(HcCodeEnum.CODE_404);
			}
		} else {
			needLogin();
			bean.putEnum(HcCodeEnum.IBS_403_UNKNOWN);
			bean.putSysEnum(HcCodeEnum.CODE_403);
			return bean;
		}
		JSONObject data = betInfo.getJSONObject("data");
		info.put("jeuValidate", data.get("JeuValidate"));
		return bean;
	}
}
