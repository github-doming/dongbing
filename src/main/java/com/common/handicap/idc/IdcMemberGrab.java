package com.common.handicap.idc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.handicap.crawler.GrabMember;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * IDC盘口抓取操作类
 *
 * @Author: Dongming
 * @Date: 2020-05-11 18:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IdcMemberGrab extends BaseIdcGrab implements GrabMember {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.IDC;

	/**
	 * 获取登录url
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 登录url
	 */
	public JSONObject getLoginUrl(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
								  int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> map = new HashMap<>(1);
		map.put("code", handicapCaptcha);
		String url = handicapUrl.concat("/get_bet_url.ashx");
		String result = null;
		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(map));
			if (StringTool.isEmpty(result)) {
				log.error("获取登录url失败");
				sleep();
				return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (StringTool.isContains(result, "Gateway Time-out")) {
				log.error("获取登录url失败，网关超时,页面为=[{}]", result);
				sleep();
				return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (StringTool.isContains(result, "Web Access blocked")) {
				log.error("获取用户信息失败，Web访问被阻止,页面为=[{}]", result);
				return null;
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取登录url失败，错误信息=" + result);
				sleep();
				return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			//转换为json
			JSONObject json = parseObject(result);
			if (ContainerTool.isEmpty(json)) {
				sleep();
				return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("获取登录url失败,结果信息=" + result, e);
			sleep();
			return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, map, index[0], result);
		}
	}

	/**
	 * 登录并获取票证Ticket
	 *
	 * @param httpConfig http请求配置类
	 * @param loginUrl   登录url
	 * @param parameters 请求参数
	 * @param index      循环次数
	 * @return 票证Ticket
	 */
	public JSONObject getLoginTicket(HttpClientConfig httpConfig, String loginUrl, String parameters, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String result = null;
		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(loginUrl + parameters));
			if (StringTool.isEmpty(result)) {
				log.error("获取票证失败");
				sleep();
				return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
			}
			if (StringTool.isContains(result, "Gateway Time-out")) {
				log.error("获取票证失败，网关超时,页面为={}", result);
				sleep();
				return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取票证失败，错误信息=" + result);
				longSleep();
				return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
			}
			//转换为json
			JSONObject json = parseObject(result);
			if (ContainerTool.isEmpty(json)) {
				longSleep();
				return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("获取票证失败,结果信息=" + result, e);
			sleep();
			return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), loginUrl, parameters, index[0], result);
		}

	}

	/**
	 * 获取用户信息。10秒超过6次，锁定10秒
	 *
	 * @param index 循环次数
	 * @return 用户信息
	 */
	public JSONObject getUserInfo(int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> map = new HashMap<>(1);

		map.put("ticket", ticket());
		String result = null;
		String url = projectHost().concat("GetUserInfo.ashx");

		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(map));
			if (StringTool.isEmpty(result)) {
				log.error("获取用户信息失败");
				longSleep();
				return getUserInfo(++index[0]);
			}
			if (StringTool.isContains(result, "504 Gateway Time-out")) {
				log.error("获取用户信息失败，nginx超时,页面为[{}]", result);
				longSleep();
				return getUserInfo(++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取用户信息失败，错误信息=" + result);
				longSleep();
				return getUserInfo(++index[0]);
			}
			//转换为json
			JSONObject json = parseObject(result);
			if (ContainerTool.isEmpty(json)) {
				longSleep();
				return getUserInfo(++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("获取用户信息失败,结果信息=" + result, e);
			sleep();
			return getUserInfo(++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, map, index[0], result);
		}
	}

	/**
	 * 获取游戏限额，60秒超过15次，锁定60秒
	 *
	 * @param gameCode 游戏编号
	 * @param index    循环次数
	 * @return 游戏限额
	 */
	public JSONObject getQuotaList(BaseGameUtil.Code gameCode, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("ticket", ticket());
		join.put("gameno", BaseHandicapUtil.handicap(HANDICAP_CODE).gameType().get(gameCode.name()));
		String result = null;
		String url = projectHost().concat("GetQuotaList.ashx");
		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("获取游戏限额失败");
				longSleep();
				return getQuotaList(gameCode, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取游戏限额失败，错误信息=" + result);
				longSleep();
				return getQuotaList(gameCode, ++index[0]);
			}
			//转换为json
			JSONObject json = parseObject(result);
			if (ContainerTool.isEmpty(json)) {
				longSleep();
				return getQuotaList(gameCode, ++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("获取游戏限额失败,结果信息=" + result, e);
			longSleep();
			return getQuotaList(gameCode, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], result);
		}

	}

	/**
	 * 投注
	 *
	 * @param gameCode       游戏编号
	 * @param handicapPeriod 盘口期数
	 * @param betItemInfo    投注内容
	 * @param index          循环次数
	 * @return 投注
	 */
	public JSONObject betting(BaseGameUtil.Code gameCode, Object handicapPeriod, String betItemInfo, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("ticket", ticket());
		join.put("gameno", BaseHandicapUtil.handicap(HANDICAP_CODE).gameType().get(gameCode.name()));
		join.put("roundno", handicapPeriod);
		join.put("wagers", betItemInfo);
		String result = null;
		String url = projectHost().concat("Betting.ashx");
		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("投注失败");
				longSleep();
				return betting(gameCode, handicapPeriod, betItemInfo, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("投注失败，错误信息=" + result);
				longSleep();
				return betting(gameCode, handicapPeriod, betItemInfo, ++index[0]);
			}
			//转换为json
			JSONObject json = parseObject(result);
			if (ContainerTool.isEmpty(json)) {
				longSleep();
				return betting(gameCode, handicapPeriod, betItemInfo, ++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("投注失败,结果信息=" + result, e);
			longSleep();
			return betting(gameCode, handicapPeriod, betItemInfo, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], result);
		}

	}

	/**
	 * 过滤限额信息
	 *
	 * @param quotaList 游戏限额信息
	 * @return 过滤限额信息
	 */
	public JSONArray filterQuotaInfo(JSONArray quotaList) {
		JSONArray quota = new JSONArray();
		JSONObject info;
		JSONArray array;
		for (int i = 0; i < quotaList.size(); i++) {
			array = new JSONArray();
			info = quotaList.getJSONObject(i);
			array.add(info.get("noquota"));
			array.add(info.get("stakequota"));
			array.add(info.get("wagerquota"));
			quota.add(array);
		}
		return quota;
	}

	/**
	 * 获取投注项内容
	 *
	 * @param gameCode 游戏code
	 * @param betItems 投注详情
	 * @return 投注项内容
	 */
	public String getBetItemInfo(BaseGameUtil.Code gameCode, List<String> betItems) {
		Map<String, String> itemCode = BaseHandicapUtil.handicap(HANDICAP_CODE).itemCodeMap(gameCode);
		StringBuilder betBuilder = new StringBuilder();
		for (String betItem : betItems) {
			String[] items = betItem.split("\\|");
			String bet = items[0].concat("|").concat(items[1]);
			//单注金额须为整数值
			int fund = (int) NumberTool.doubleT(items[2]);
			if (StringTool.isEmpty(itemCode.get(bet))) {
				log.error("错误的投注项" + betItem);
				continue;
			}
			betBuilder.append(itemCode.get(bet)).append(":").append(fund).append(";");
		}
		return betBuilder.toString();
	}

	/**
	 * 获取错误投注项
	 *
	 * @param gameCode 游戏code
	 * @param errorMsg 错误信息
	 * @return 错误投注项
	 */
	public String getFailBetItem(BaseGameUtil.Code gameCode, String errorMsg) {
		String item = null;
		if (StringTool.isContains(errorMsg, "最低", "最高")) {
			item = errorMsg.substring(1, errorMsg.indexOf(" ")).replace("】", "");
		} else if (StringTool.isContains(errorMsg, "累计")) {
			item = errorMsg.substring(1, errorMsg.indexOf("累")).replace("】", "");
		}
		if (StringTool.isEmpty(item)) {
			return null;
		}
		return BaseHandicapUtil.handicap(HANDICAP_CODE).itemCodeMap(gameCode).get(item);
	}

	/**
	 * 获取投注结果
	 *
	 * @param gameCode 游戏code
	 * @param data     投注结果
	 * @return 投注结果
	 */
	public JSONArray getBetResult(BaseGameUtil.Code gameCode, JSONArray data) {
		Map<String, String> itemCode = BaseHandicapUtil.handicap(HANDICAP_CODE).infoItemMap(gameCode);

		JSONArray betResult = new JSONArray();
		JSONArray betInfo;
		for (int i = 0; i < data.size(); i++) {
			betInfo = new JSONArray();
			JSONObject info = data.getJSONObject(i);
			String item = itemCode.get(info.getString("wagertypename").concat(info.getString("wagerobject")));
			if (StringTool.isEmpty(item)) {
				log.error("错误的投注项：" + info);
				continue;
			}
			//注单号
			betInfo.add(info.get("idno"));
			//投注项
			betInfo.add(item);
			//投注金额
			betInfo.add(info.get("stake"));
			//赔率
			betInfo.add(info.get("wagerodds"));
			//盈亏金额
			betInfo.add(info.get("winnings"));
			betResult.add(betInfo);
		}
		return betResult;
	}
}
