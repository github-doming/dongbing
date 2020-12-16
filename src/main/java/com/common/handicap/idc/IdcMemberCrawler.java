package com.common.handicap.idc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.config.handicap.IdcConfig;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.enums.HcCodeEnum;
import com.common.handicap.crawler.BaseMemberCrawler;
import com.common.handicap.crawler.entity.HandicapUser;
import com.common.handicap.crawler.entity.MemberUser;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IDC盘口爬虫类
 *
 * @Author: Dongming
 * @Date: 2020-05-11 14:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IdcMemberCrawler extends BaseMemberCrawler<IdcMemberGrab> {
	public IdcMemberCrawler() {
		handicapCode = BaseHandicapUtil.Code.IDC;
	}

	@Override protected JsonResultBeanPlus login(HttpClientConfig httpConfig, HandicapUser accountInfo) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			JSONObject loginHtml = crawlerGrab
					.getLoginUrl(httpConfig, accountInfo.handicapUrl(), accountInfo.handicapCaptcha());
			if (ContainerTool.isEmpty(loginHtml) || "1".equals(loginHtml.getString("code"))) {
				log.info(getLogTitle(), accountInfo.account() + ",获取登陆URL=" + loginHtml);
				bean.putEnum(HcCodeEnum.IBS_403_PAGE_NAVIGATE);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			String loginUrl = loginHtml.getString("login_url");

			String sign = String.format("accounts=%s&pwd=%s&extwagerno=%s&extwagerkey=%s", accountInfo.account(),
					accountInfo.password(), IdcConfig.PERMISSION_CODE, IdcConfig.PERMISSION_KEY);
			sign = Md5Tool.md5Hex(sign);
			String parameters = String
					.format("&accounts=%s&pwd=%s&extwagerno=%s&sign=%s", accountInfo.account(), accountInfo.password(),
							IdcConfig.PERMISSION_CODE, sign);
			JSONObject loginInfo = crawlerGrab.getLoginTicket(httpConfig, loginUrl, parameters);
			if (ContainerTool.isEmpty(loginInfo)) {
				bean.putEnum(HcCodeEnum.IBS_403_LOGIN_FAIL);
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			if ("1".equals(loginInfo.get("code"))) {

				log.info(getLogTitle(), accountInfo.account() + ",获取登录信息=" + loginHtml);
				bean.putEnum(crawlerGrab.loginError(loginInfo.getString("msg")));
				bean.putSysEnum(HcCodeEnum.CODE_403);
				return bean;
			}
			Map<String, String> data = new HashMap<>(2);
			data.put("projectHost", loginInfo.getString("api_url"));
			data.put("ticket", loginInfo.getString("ticket"));

			bean.setData(data);
			bean.success();
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
				bean.setCode(HcCodeEnum.IBS_404_DATA.getCode());
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if ("1".equals(userObj.get("code"))) {
				log.info(getLogTitle(), "获取会员账号信息错误" + userObj);
				bean.setCode(HcCodeEnum.IBS_404_DATA.getCode());
				bean.setMsg(userObj.getString("msg"));
				bean.putSysEnum(HcCodeEnum.CODE_404);
				return bean;
			}
			if ("2".equals(userObj.get("code"))) {
				log.info(getLogTitle(), "获取会员列表信息失败" + userObj);
				needLogin();
				bean.setCode(CodeEnum.IBS_403_ERROR.getCode());
				bean.setMsg(userObj.getString("msg"));
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			if (StringTool.notEmpty(userObj.get("ticket"))) {
				crawlerGrab.ticket(userObj.getString("ticket"));
			}
			MemberUser member = memberImage.member();
			if (member == null) {
				member = new MemberUser().memberAccount(accountName());
				memberImage.member(member);
			}
			JSONObject info = userObj.getJSONObject("data");
			//会员盘
			member.memberType(info.getString("wagerroundno"));
			//信用额度
			member.creditQuota(NumberTool.getDouble(info.get("creditquota")));
			//可用额度
			member.usedQuota(NumberTool.getDouble(info.get("allowcreditquota")));
			//使用金额
			member.usedAmount(NumberTool.getDouble(info.get("usecreditquota")));
			//盈亏金额
			member.profitAmount(NumberTool.getDouble(info.get("winningprofit")));

			bean.success(member);

		} catch (Exception e) {
			log.error(getLogTitle(), "获取用户信息失败:" + e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}
	@Override public JsonResultBeanPlus gameLimit(BaseGameUtil.Code gameCode) {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		try {
			JSONObject quotaList = crawlerGrab.getQuotaList(gameCode);

			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			if (ContainerTool.isEmpty(quotaList)) {
				log.info(getLogTitle(), "获取游戏【" + gameCode + "】限额信息失败");
				return bean;
			}
			if ("1".equals(quotaList.get("code"))) {
				log.info(getLogTitle(), "获取游戏【" + gameCode + "】限额信息错误=" + quotaList);
				bean.setMsg(quotaList.getString("msg"));
				return bean;
			}
			if ("2".equals(quotaList.get("code"))) {
				log.info(getLogTitle(), "获取游戏【" + gameCode + "】限额信息失败=" + quotaList);
				needLogin();
				bean.setCode(CodeEnum.IBS_403_ERROR.getCode());
				bean.putSysEnum(CodeEnum.CODE_403);
				bean.setMsg(quotaList.getString("msg"));
				return bean;
			}
			if (StringTool.notEmpty(quotaList.get("ticket"))) {
				crawlerGrab.ticket(quotaList.getString("ticket"));
			}
			//过滤限额信息
			JSONArray quotas = crawlerGrab.filterQuotaInfo(quotaList.getJSONArray("data"));

			bean.success();
			bean.setData(quotas);
		} catch (Exception e) {
			log.error(getLogTitle(), "获取游戏【" + gameCode.getName() + "】限额信息失败:" + e.getMessage());
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

		String betItemInfo = crawlerGrab.getBetItemInfo(gameCode, betItems);
		if (StringTool.isEmpty(betItemInfo)) {
			log.info(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注失败,未存在正确的投注项");
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		try {
			JSONObject betInfo = crawlerGrab
					.betting(gameCode, BaseHandicapUtil.handicap(handicapCode).handicapPeriod(gameCode, period),
							betItemInfo);
			//投注结果
			log.trace(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注结果为：" + betInfo);
			if (ContainerTool.isEmpty(betInfo)) {
				log.info(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注失败");
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			if ("1".equals(betInfo.get("code"))) {
				log.info(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注错误=" + betInfo);
				if (StringTool.contains(betInfo.getString("msg"), "指定期数为非交易状态", "非交易时间,不允许下注")) {
					bean.putEnum(HcCodeEnum.IBS_403_SEAL_HANDICAP);
					bean.putSysEnum(HcCodeEnum.CODE_403);
					return bean;
				}
				if (StringTool.contains(betInfo.getString("msg"), "禁用","暂停下注")) {
					bean.putEnum(HcCodeEnum.IBS_403_USER_STATE);
					bean.putSysEnum(HcCodeEnum.CODE_403);
					return bean;
				}
				String failBetItem = crawlerGrab.getFailBetItem(gameCode, betInfo.getString("msg"));
				if (StringTool.isEmpty(failBetItem)) {
					log.error("获取错误投注项失败，错误消息为：{}", betInfo.getString("msg"));
					bean.putEnum(CodeEnum.IBS_404_DATA);
					bean.putSysEnum(CodeEnum.CODE_404);
					return bean;

				}
				for (String items : betItems) {
					if (StringTool.isContains(items, failBetItem)) {
						betErrors.add(items);
						betItems.remove(items);
					}
				}
				if (ContainerTool.notEmpty(betItems)) {
					return betting(gameCode, period, betType, betItems, betErrors);
				}
				return bean.success();
			} else if ("2".equals(betInfo.get("code"))) {
				log.info(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】获取投注信息失败=" + betInfo);
				needLogin();
				return betting(gameCode, period, betType, betItems, betErrors);
			}
			//更换旧票证
			if (StringTool.notEmpty(betInfo.get("ticket"))) {
				crawlerGrab.ticket(betInfo.getString("ticket"));
			}
			//保存余额信息
			memberImage.member().usedQuota(NumberTool.getDouble(betInfo.get("balance")));

			//投注成功项信息
			JSONArray betResult = crawlerGrab.getBetResult(gameCode, betInfo.getJSONArray("data"));

			bean.success();
			bean.setData(betResult);
		} catch (Exception e) {
			log.error(getLogTitle(), "游戏【" + gameCode + "】期数【" + period + "】投注失败" + e.getMessage());
		}
		return bean;
	}

	@Override protected IdcMemberGrab crawlerGrab() {
		return new IdcMemberGrab();
	}
	@Deprecated @Override public JsonResultBeanPlus oddsInfo(BaseGameUtil.Code gameCode, String betType) {
		return null;
	}

}
