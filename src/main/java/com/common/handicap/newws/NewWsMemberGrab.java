package com.common.handicap.newws;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.config.handicap.NewWsConfig;
import com.common.handicap.crawler.GrabMember;
import com.common.handicap.crawler.entity.HandicapUser;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
public class NewWsMemberGrab extends BaseNewWsGrab implements GrabMember {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.NEWWS;

	/**
	 * 获取会员线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  html
	 * @return 会员线路
	 */
	public String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml) {
		List<String> hrefs = new ArrayList<>();
		Document document = Jsoup.parse(routeHtml);
		Elements as = document.getElementsByClass("datalist-contain datalist").select("a");
		for (int i = 0; i < 5; i++) {
			hrefs.add(as.get(i).attr("name"));
		}
		return getRoute(httpConfig, hrefs);
	}

	public Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes, String snn, String captcha) {
		return getLoginHtml(httpConfig, captcha, snn, routes, "f/login_new.jsp");
	}

	public String getVerifyCode(HttpClientConfig httpConfig, String projectHost, String ticket) {
		return getVerifyCode(httpConfig, projectHost, ticket, null, "user/getValidateCodeF.do?t=");
	}

	public String getLogin(HttpClientConfig httpConfig, HandicapUser accountInfo, String verifyCode, String projectHost, String ticket, String snn) throws NoSuchAlgorithmException {
		return getLogin(httpConfig, accountInfo, verifyCode, projectHost, ticket, snn, "user/loginf.do");

	}

	/**
	 * 获取用户信息
	 *
	 * @return html
	 */
	public String getUserInfo(int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return "";
		}
		String html = null;
		String url = projectHost().concat("user/query/querySelfLeft.do");

		Map<String, Object> join = new HashMap<>(2);
		join.put("uid", memberId());
		join.put("ticket", ticket());
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取用户信息失败，结果信息" + html);
				sleep();
				return getUserInfo(++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取用户余额信息失败,错误信息=" + html, e);
			sleep();
			return getUserInfo(++index[0]);
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
		String gameNo = BaseHandicapUtil.handicap(HANDICAP_CODE).gameType().get(gameCode.name());
		String result = null;
		String url = projectHost().concat("wb3/us0001/personalData?user_id=&t=%s&palyId=%s");
		url = String.format(url, System.currentTimeMillis(), gameNo);

		try {
			result = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(result)) {
				log.error("获取游戏限额失败");
				longSleep();
				return getQuotaList(gameCode, ++index[0]);
			}
			if (!StringTool.isContains(result, "user_Name")) {
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
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, index[0], result);
		}

	}

	/**
	 * 获取赔率信息
	 *
	 * @param gameCode 盘口游戏code
	 * @return 赔率信息
	 */
	public JSONObject getOddsInfo(BaseGameUtil.Code gameCode, String betType, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		String oddsCode = getNewWsOddsCode(gameCode, betType);
		String gameNo = BaseHandicapUtil.handicap(HANDICAP_CODE).gameType().get(gameCode.name());
		String html = null;
		String url = projectHost().concat("game/bet/loadBet.do");

		Map<String, Object> join = new HashMap<>(10);
		join.put("uid", memberId());
		join.put("ticket", ticket());
		join.put("gameCode", gameNo);
		join.put("code", oddsCode);
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取赔率信息页面失败");
				sleep();
				return getOddsInfo(gameCode, betType, ++index[0]);
			}
			//转换为json
			JSONObject json = parseObject(html);
			if (ContainerTool.isEmpty(json)) {
				longSleep();
				return getQuotaList(gameCode, ++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("获取赔率信息页面失败,结果信息=" + html, e);
			sleep();
			return getOddsInfo(gameCode, betType, ++index[0]);
		} finally {
			log.trace(String.format(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], ""));
		}
	}

	/**
	 * 投注
	 *
	 * @param betItemInfo 投注内容
	 * @param index       循环次数
	 * @return 投注
	 */
	public JSONObject betting(Map<String, Object> betItemInfo, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(1);
		join.put("uid", memberId());
		join.put("ticket", ticket());
		join.put("gambleString", betItemInfo);
		String result = null;
		String url = projectHost().concat("game/gamble.do");

		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("投注失败");
				longSleep();
				return betting(betItemInfo, ++index[0]);
			}

			//转换为json
			JSONObject json = parseObject(result);
			if (ContainerTool.isEmpty(json)) {
				longSleep();
				return betting(betItemInfo, ++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("投注失败,结果信息=" + result, e);
			longSleep();
			return betting(betItemInfo, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], result);
		}
	}

	/**
	 * 获取投注项内容
	 *
	 * @param gameCode 游戏code
	 * @param betItems 投注详情
	 * @return 投注项内容
	 */
	public Map<String, Object> getBetItemInfo(BaseGameUtil.Code gameCode, List<String> betItems, JSONObject odds, Object period) {

		String gameNo = BaseHandicapUtil.handicap(HANDICAP_CODE).gameType().get(gameCode.name());
		JSONArray arr = new JSONArray();
		Map<String, Object> gambleString = new HashMap<>();
		Map<String, Object> gamble;
		for (String betItem : betItems) {
			String[] items = betItem.split("\\|");
			// 冠亚|11|2000
			String ruleCode = getRuleCode(gameNo, items[0], items[1]);
			gamble = new HashMap<>(4);
			gamble.put("seqNumber", period);
			gamble.put("ruleCode", ruleCode);
			gamble.put("odds", odds.getJSONArray(ruleCode).getDouble(0));
			gamble.put("amount", (int) NumberTool.doubleT(items[2]));
			arr.add(gamble);
		}
		gambleString.put("zds", arr);
		return gambleString;
	}

	public String getNewWsOddsCode(BaseGameUtil.Code gameCode, String type) {
		switch (gameCode) {
			case PK10:
				return NewWsConfig.PK10_ODDS_CODE.get(type);
			case SELF_10_5:
				return NewWsConfig.SELF_10_5_ODDS_CODE.get(type);
			case CQSSC:
				return NewWsConfig.SSC_ODDS_CODE.get(gameCode.name());
			case GDKLC:
				return NewWsConfig.GDKLC_ODDS_CODE.get(type);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取参数
	 *
	 * @param gameCode 游戏code
	 * @param betPlace 名次
	 * @param betRange 范围
	 * @return ruleCode
	 */
	private static String getRuleCode(String gameCode, String betPlace, String betRange) {
		String place = NewWsConfig.BALL_CODE_PLACE.get(betPlace);
		String ruleCode = gameCode.concat("_");
		Map<String, String> codeRange = getCodeRange(gameCode);
		if ("otherCode".equals(place)) {
			ruleCode = ruleCode.concat(NewWsConfig.BALL_CODE_OTHER.get(betPlace.concat("|").concat(betRange)));
		} else {
			ruleCode = ruleCode.concat(place).concat("_").concat(codeRange.get(betRange));
		}
		return ruleCode;
	}

	private static Map<String, String> getCodeRange(String gameCode) {
		if ("1_1".equals(gameCode)) {
			return NewWsConfig.KLC_BALL_CODE_RANGE;
		} else if ("1_2".equals(gameCode)) {
			return NewWsConfig.SSC_BALL_CODE_RANGE;
		}
		return NewWsConfig.BALL_CODE_RANGE;

	}

}
