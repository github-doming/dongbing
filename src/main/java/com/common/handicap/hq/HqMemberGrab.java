package com.common.handicap.hq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.handicap.Handicap;
import com.common.handicap.crawler.GrabMember;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HQ盘口 抓取操作类
 *
 * @Author: Dongming
 * @Date: 2020-05-16 17:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HqMemberGrab extends BaseHqGrab implements GrabMember {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.HQ;

	/**
	 * 获取登录href
	 *
	 * @param html 角色选择页面
	 * @return 登录href
	 */
	@Override protected String getLoginHref(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		if (!StringTool.isContains(html, "为了更好的浏览体验，请使用竖屏浏览！")) {
			return null;
		}
		Document document = Jsoup.parse(html);
		Element a = document.body().selectFirst("a");

		return a.attr("href");
	}

	/**
	 * 登录盘口
	 *
	 * @param httpConfig      http请求配置类
	 * @param projectHost     主机地址
	 * @param sessionId       参数sessionId
	 * @param handicapCaptcha 盘口验证码
	 * @param encryptKey      加密信息-vk
	 * @param logPk           参数logpk
	 * @param index           循环次数
	 */
	public String getLogin(HttpClientConfig httpConfig, String projectHost, String sessionId, String handicapCaptcha,
						   String encryptKey, String logPk, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		String url = "http://".concat(projectHost).concat(sessionId).concat("/Member/DoLogin");
		Map<String, Object> join = new HashMap<>(8);
		join.put("token", handicapCaptcha);
		// 验证码
		join.put("Captcha", "");
		join.put("info", encryptKey);
		join.put("pk", logPk);
		String html = null;
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("登录盘口失败");
				sleep();
				return getLogin(httpConfig, projectHost, sessionId, handicapCaptcha, encryptKey, logPk, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("登录盘口失败", e);
			sleep();
			return getLogin(httpConfig, projectHost, sessionId, handicapCaptcha, encryptKey, logPk, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], html);
		}
	}

	/**
	 * 获取协议页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param sessionId   sessionId
	 * @param index       循环次数
	 * @return 协议页面
	 */
	public String getAcceptAgreement(HttpClientConfig httpConfig, String projectHost, String sessionId, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html = null;

		String url = "http://".concat(projectHost).concat(sessionId).concat("/Member/AcceptAgreement");
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取协议页面失败");
				sleep();
				return getAcceptAgreement(httpConfig, projectHost, sessionId, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取协议页面失败");
			sleep();
			return getAcceptAgreement(httpConfig, projectHost, sessionId, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);

		}
		httpConfig.setHeader("Referer",
				"http://".concat(projectHost).concat(sessionId).concat("/All/Agreement.html?s=").concat(sessionId));
		return html;
	}

	/**
	 * 获取index页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param sessionId   sessionId
	 * @param index       循环次数
	 * @return index页面
	 */
	public String getIndex(HttpClientConfig httpConfig, String projectHost, String sessionId, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html = null;
		String url =
				"http://".concat(projectHost).concat(sessionId).concat("/App/Index?_=") + System.currentTimeMillis();

		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取主页面失败");
				sleep();
				return getIndex(httpConfig, projectHost, sessionId, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取主页面失败");
			sleep();
			return getIndex(httpConfig, projectHost, sessionId, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);
		}
		httpConfig.setHeader("Referer", url);
		return html;
	}
	/**
	 * 获取用户信息（也可以获取到当前期该游戏投注未结算投注项信息）
	 *
	 * @return 用户信息
	 */
	public JSONObject getUserInfo() {
		Handicap handicap = BaseHandicapUtil.handicap(HANDICAP_CODE);
		BaseGameUtil.Code gameCode = BaseGameUtil.Code.CQSSC;
		String gameType = handicap.gameType().get(gameCode.name());
		Object handicapPeriod = handicap.handicapPeriod(gameCode);
		return getUserInfo(gameType, handicapPeriod);
	}

	/**
	 * http://f1.hy7994666.xyz/(S(pwxofckob0zgwx4drp15xi3o))/NetTest/NetTestNew?_=1585995849570
	 * {"Status":1,"Data":{"GameId":4,"CompanyId":67,"Account":"gbd11hy02","ClientIP":"119.28.186.226","UrlLine":"f1.hy7994666.xyz","ServerIp":"10.10.56.93","ServerTime":"2020-04-04 18:25:29"}}
	 * 获取会员账号信息
	 *
	 * @param index 循环次数
	 * @return 会员账号信息
	 */
	public JSONObject getMemberAccount(int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		String html = null;
		String url = projectHost().concat("NetTest/NetTestNew?_=") + System.currentTimeMillis();
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取会员账号信息失败");
				sleep();
				return getMemberAccount(++index[0]);
			}
			JSONObject data;
			if (StringTool.isContains(html, "系统升级中")) {
				log.error("获取会员账号信息失败{}", html);
				data = new JSONObject();
				data.put("data", "系统升级中");
				return data;
			}
			if (StringTool
					.contains(html, "Server:", "robot7_session_id", "document.cookie", "SafeCode", "callback7()")) {
				log.error("获取会员账号信息失败{}", html);
				data = new JSONObject();
				data.put("data", "login");
				return data;
			}
			return parseObject(html).getJSONObject("Data");
		} catch (Exception e) {
			log.error("获取会员账号信息失败,结果信息=" + html, e);
			sleep();
			return getMemberAccount(++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);
		}

	}
	/**
	 * 获取游戏限额
	 *
	 * @param gameCode 盘口游戏code
	 * @param index    循环次数
	 * @return 游戏限额信息
	 */
	public JSONArray getQuotaList(BaseGameUtil.Code gameCode, int... index) {
		Handicap handicap = BaseHandicapUtil.handicap(HANDICAP_CODE);
		String gameType = handicap.gameType().get(gameCode.name());
		String html = null;
		String url = projectHost().concat("Member/GetMemberBetInfo/?lotteryId=").concat(gameType).concat("&_=") + System
				.currentTimeMillis();
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败");
				sleep();
				return getQuotaList(gameCode, ++index[0]);
			}
			if (StringTool.isContains(html, "系统升级中", "请输入有效彩种")) {
				log.error("获取游戏限额失败,{}", html);
				JSONArray data = new JSONArray();
				data.add("系统升级中");
				return data;
			}
			if (StringTool.isContains(html, "robot7_session_id", "document.cookie")) {
				log.error("获取游戏限额失败,{}", html);
				JSONArray data = new JSONArray();
				data.add("login");
				return data;
			}
			return getLimit(html, gameType);
		} catch (Exception e) {
			log.error("获取游戏限额失败" + html, e);
			sleep();
			return getQuotaList(gameCode, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);
		}
	}

	/**
	 * 获取赔率信息
	 *
	 * @param gameCode 游戏编码
	 * @param betType  投注类型
	 * @return 赔率信息
	 */
	public JSONObject getOddsInfo(BaseGameUtil.Code gameCode, String betType) {
		Handicap handicap = BaseHandicapUtil.handicap(HANDICAP_CODE);
		String type = handicap.betType(gameCode).get(betType);
		List<?> betRanks = handicap.betRanks(gameCode, betType);
		String gameType = handicap.gameType().get(gameCode.name());
		return getOddsInfo(type, JSONArray.parseArray(JSON.toJSONString(betRanks)), gameType);

	}

	/**
	 * 获取投注项信息
	 *
	 * @param gameCode  游戏编码
	 * @param gameType  游戏类型
	 * @param oddsData  赔率数据
	 * @param betItems  投注详情
	 * @param betErrors 投注失败项
	 * @return 投注项信息
	 */
	public JSONArray getBetItemInfo(BaseGameUtil.Code gameCode, String gameType, JSONArray oddsData, List<String> betItems,
			List<String> betErrors) {
		Map<String, String> itemCode = BaseHandicapUtil.handicap(HANDICAP_CODE).itemCodeMap(gameCode);
		JSONArray betsArray = new JSONArray();
		JSONObject betInfo;
		JSONObject odds;
		for (String betItem : betItems) {
			betInfo = new JSONObject();
			//投注排名|投注类型|投注金额
			String[] items = betItem.split("\\|");
			String bet = items[0].concat("|").concat(items[1]);
			//单注金额须为整数值
			int fund = (int) NumberTool.doubleT(items[2]);
			String betCode = itemCode.get(bet);
			if (StringTool.isEmpty(betCode)) {
				log.error("错误的投注项" + betItem);
				betErrors.add(betItem);
				continue;
			}
			for (int i = 0; i < oddsData.size(); i++) {
				odds = oddsData.getJSONObject(i);
				if (!gameType.concat(betCode).equals(String.valueOf(odds.getInteger("BetNo")))) {
					continue;
				}
				betInfo.put("BetNo", gameType.concat(betCode));
				betInfo.put("Odds", String.valueOf(odds.getDouble("Odds")));
				betInfo.put("BetMoney", fund);
				betsArray.add(betInfo);
				break;
			}
		}
		return betsArray;
	}

	/**
	 * 投注
	 *
	 * @param betParams 投注参数
	 * @param gameType  盘口游戏code
	 * @return 投注结果
	 */
	public JSONObject betting(JSONObject betParams, String gameType) {
		HttpClientConfig httpConfig = httpConfig();
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpConfig.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");

		httpConfig.httpTimeOut(20 * 1000);
		Map<String, Object> map = new HashMap<>(2);
		map.put("betParams", betParams.toString());
		map.put("lotteryId", Integer.parseInt(gameType));
		String html = null;
		String url = projectHost().concat("Bet/DoBet/");
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(map));
			if (ContainerTool.isEmpty(html) || StringTool.contains(html, "系统升级中", "robot7_session_id")) {
				log.error("投注失败,结果=" + html + ",投注项为：" + betParams);
				return new JSONObject();
			}
			return JSONObject.parseObject(html);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, map, 0, html);
		}
	}

	/**
	 * 获取投注成功项
	 *
	 * @param gameType 盘口游戏code
	 * @param index    循环次数
	 * @return 投注成功项
	 */
	public JSONObject getBettingResult(String gameType, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		String html = null;

		String url = projectHost().concat("Bet/GetBettingSuccess/?lotteryId=").concat(gameType).concat("&_=") + System
				.currentTimeMillis();

		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取投注成功项失败");
				sleep();
				return getBettingResult(gameType, ++index[0]);
			}
			if (StringTool.isContains(html, "系统升级中")) {
				log.error("获取投注成功项失败{}", html);
				JSONObject data = new JSONObject();
				data.put("data", "系统升级中");
				return data;
			}
			if (StringTool.isContains(html, "robot7_session_id", "document.cookie")) {
				log.error("获取投注成功项失败{}", html);
				JSONObject data = new JSONObject();
				data.put("data", "login");
				return data;
			}
			return parseObject(html);
		} catch (Exception e) {
			log.error("获取投注成功项失败", e);
			sleep();
			return getBettingResult(gameType, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);
		}
	}

	/**
	 * 过滤投注成功项
	 *
	 * @param data     投注成功项
	 * @param betItems 投注详情信息
	 * @param gameCode 游戏编码
	 */
	public JSONArray filterSuccessInfo(JSONObject data, List<String> betItems, BaseGameUtil.Code gameCode) {
		Map<String, String> infoItem = BaseHandicapUtil.handicap(HANDICAP_CODE).infoItemMap(gameCode);
		JSONArray bettingResult = data.getJSONArray("BettingResult");
		JSONArray betResult = new JSONArray();
		JSONArray betInfo;
		JSONObject info;
		for (int i = 0; i < bettingResult.size(); i++) {
			info = bettingResult.getJSONObject(i);
			String item = info.getString("BetNoFullName").replace(" ", "");
			long fund = NumberTool.longValueT(info.getInteger("BetMoney"));
			String betCode = infoItem.get(item);
			if (StringTool.isEmpty(betCode)) {
				log.error("未解析的投注项" + betCode);
				continue;
			}
			String betItem = betCode.concat("|").concat(String.valueOf(fund));
			if (betItems.contains(betItem)) {
				betInfo = new JSONArray();
				//注单号
				betInfo.add(info.getString("SerialNo"));
				//投注项
				betInfo.add(betCode);
				//投注金额
				betInfo.add(info.get("BetMoney"));
				//赔率
				betInfo.add(info.get("Odds"));
				betResult.add(betInfo);
				betItems.remove(betCode);
			}
		}
		return betResult;
	}

	/**
	 * 获取投注结果
	 *
	 * @param gameCode  游戏code
	 * @param betErrors 错误投注项
	 * @param items     投注结果
	 * @param gameType  盘口游戏code
	 * @return 投注结果
	 */
	public JSONArray getBetResult(BaseGameUtil.Code gameCode, String gameType, JSONArray items, List<String> betErrors) {
		Map<String, String> codeItem = BaseHandicapUtil.handicap(HANDICAP_CODE).codeItemMap(gameCode);

		JSONArray betResult = new JSONArray();
		JSONObject info;
		JSONArray betInfo;
		for (int i = 0; i < items.size(); i++) {
			info = items.getJSONObject(i);
			String item = codeItem.get(String.valueOf(info.getInteger("BetNo")).substring(gameType.length()));
			if (StringTool.isEmpty(item)) {
				log.error("错误的投注结果项：" + info);
				betErrors.add(info.toString());
				continue;
			}
			betInfo = new JSONArray();
			//注单号
			betInfo.add(info.getString("SerialNo"));
			//投注项
			betInfo.add(item);
			//投注金额
			betInfo.add(info.get("BetMoney"));
			//赔率
			betInfo.add(info.get("Odds"));
			betResult.add(betInfo);
		}
		return betResult;
	}

	/**
	 * 获取用户信息（也可以获取到当前期该游戏投注未结算投注项信息）
	 *
	 * @param gameType       盘口游戏type
	 * @param handicapPeriod 盘口期数
	 * @param index          循环次数
	 * @return 用户信息
	 */
	private JSONObject getUserInfo(String gameType, Object handicapPeriod, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html = null;
		JSONObject data;
		String url = projectHost().concat("Member/GetMemberInfo?lotteryId=").concat(gameType).concat("&periodId=")
				.concat(handicapPeriod.toString()).concat("&_=") + System.currentTimeMillis();
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取用户信息失败");
				sleep();
				return getUserInfo(gameType, handicapPeriod, ++index[0]);
			}
			if (StringTool.isContains(html, "系统升级中")) {
				log.error("获取用户信息失败{}", html);
				data = new JSONObject();
				data.put("data", "系统升级中");
				return data;
			}
			if (StringTool
					.contains(html, "Server:", "robot7_session_id", "document.cookie", "SafeCode", "callback7()")) {
				log.error("获取用户信息失败{}", html);
				data = new JSONObject();
				data.put("data", "login");
				return data;
			}
			return parseObject(html).getJSONObject("Data");
		} catch (Exception e) {
			log.error("获取用户信息失败,结果信息=" + html, e);
			sleep();
			return getUserInfo(gameType, handicapPeriod, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);
		}
	}

	/**
	 * 解析获取盘口游戏限额
	 *
	 * @param html     会员投注信息页面
	 * @param gameType 盘口游戏code
	 * @return 游戏限额
	 */
	private JSONArray getLimit(String html, String gameType) {

		JSONObject data = JSONObject.parseObject(html).getJSONObject("Data");

		if (!data.containsKey("BetLimits")) {
			return new JSONArray();
		}
		JSONArray betLimits = data.getJSONArray("BetLimits");
		JSONArray quota = new JSONArray();
		JSONArray array;
		JSONObject infos;
		for (int i = 0; i < betLimits.size(); i++) {
			if (!String.valueOf(betLimits.getJSONObject(i).getInteger("LotteryId")).equals(gameType)) {
				continue;
			}
			JSONArray limits = betLimits.getJSONObject(i).getJSONArray("BetLimitList");
			for (int j = 0; j < limits.size(); j++) {
				array = new JSONArray();
				infos = limits.getJSONObject(j);
				array.add(infos.get("OneBetMin"));
				array.add(infos.get("OneBetMax"));
				array.add(infos.get("OneItemMax"));
				quota.add(array);
			}
			break;
		}
		return quota;
	}

	/**
	 * 获取赔率信息
	 *
	 * @param type     盘口游戏类型
	 * @param betRanks 投注球号
	 * @param gameType 游戏类型
	 * @param index    循环次数
	 * @return 赔率信息
	 */
	private JSONObject getOddsInfo(String type, JSONArray betRanks, String gameType, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html = null;
		String url = projectHost().concat("Bet/GetBetPageRefresh/?m=").concat(type).concat("&statNo=0&marketTypes=")
				.concat(betRanks.toString()).concat("&lotteryId=").concat(gameType).concat("&_=") + System
				.currentTimeMillis();
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏赔率失败");
				sleep();
				return getOddsInfo(type, betRanks, gameType, ++index[0]);
			}
			if (StringTool.isContains(html, "robot7_session_id", "document.cookie", "请输入有效彩种")) {
				log.error("获取游戏赔率失败{}", html);
				return new JSONObject();
			}
			if (StringTool.isContains(html, "请输入有效彩种")) {
				JSONObject json = new JSONObject();
				json.put("errorInfo", html);
				return json;
			}
			return parseObject(html);
		} catch (Exception e) {
			log.error("获取赔率信息失败,结果信息=" + html, e);
			sleep();
			return getOddsInfo(type, betRanks, gameType, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);
		}
	}

}
