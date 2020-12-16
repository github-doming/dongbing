package com.common.handicap.un;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.config.handicap.UnConfig;
import com.common.handicap.crawler.GrabMember;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.apache.commons.lang3.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
public class UnMemberGrab extends BaseUnGrab implements GrabMember {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.UN;

	/**
	 * 获取会员线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  html
	 * @return 会员线路
	 */
	public String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml) {
		return getRoute(httpConfig, routeHtml, "会员线路");
	}

	public Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes) {
		return getLoginHtml(httpConfig, routes, "iwm3");
	}

	public String getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
						   String verifyCode, String projectHost) {
		return getLogin(httpConfig, memberAccount, memberPassword, verifyCode, projectHost, "3");
	}

	/**
	 * 获取用户信息。10秒超过6次，锁定10秒
	 *
	 * @param index 循环次数
	 * @return 用户信息
	 */
	public String getUserInfo(int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		String result = null;
		String url = projectHost().concat("wb3/us0001/queryUserLeft?gameId=G_30&t=" + System.currentTimeMillis());

		try {
			result = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(result)) {
				log.error("获取用户信息失败");
				longSleep();
				return getUserInfo(++index[0]);
			}
			return result;

		} catch (Exception e) {
			log.error("获取用户信息失败,结果信息=" + result, e);
			sleep();
			return getUserInfo(++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, index[0], result);
		}
	}

	/**
	 * 获取游戏限额，60秒超过15次，锁定60秒
	 *
	 * @param gameCode 游戏编号
	 * @param index    循环次数
	 * @return 游戏限额
	 */
	public String getQuotaList(BaseGameUtil.Code gameCode, int... index) {
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

			return result;
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
	 * @param index    循环次数
	 * @return 赔率信息
	 */
	public JSONObject getOddsInfo(String gameCode, String memberType, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		String gameNo = BaseHandicapUtil.handicap(HANDICAP_CODE).gameType().get(gameCode);
		String html = null;
		String url = projectHost().concat("wb3/001/m001?b=B_109&g=%s&auto=0&hType=%s&t=%s");
		url = String.format(url, gameNo, memberType, System.currentTimeMillis());

		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取赔率信息页面失败");
				sleep();
				return getOddsInfo(gameCode, memberType, ++index[0]);
			}
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取赔率信息页面失败,结果信息=" + html, e);
			sleep();
			return getOddsInfo(gameCode, memberType, ++index[0]);
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
	public JSONObject betting(String betItemInfo, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		HttpClientConfig httpConfig = httpConfig();
		httpConfig.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		httpConfig.setHeader("Content-Type", "application/json");
		String result = null;
		String url = projectHost().concat("wb3/001/s001s");

		Map<String, Object> join = new HashMap<>(1);
		join.put("$ENTITY_STRING$", betItemInfo);

		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
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
	 * 获取未结算页面
	 * https://fkiael.58jxw.com/wb3/us0001/m0010?t=1593571134036&palyId=G_3&tg=2
	 *
	 * @param page  页数
	 * @param index 循环次数
	 * @return 未结算页面
	 */
	public String getIsSettlePage(int page, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost().concat("wb3/us0001/m0010?t=").concat(System.currentTimeMillis() + "&palyId=G_3&tg=").concat(page + "");
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算页面失败", html);
				sleep();
				return getIsSettlePage(page, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取未结算页面失败", e);
			sleep();
			return getIsSettlePage(page, ++index[0]);
		} finally {
			log.trace(String.format(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html));
		}
	}


	/**
	 * 获取未结算信息
	 *
	 * @param html 未结算页面
	 * @return JSONArray
	 */
	public JSONArray getIsSettleInfo(String html, Object roundno) {
		JSONArray array = new JSONArray();
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("user_table").first().select("tr");
		elements.remove(elements.first());
		elements.remove(elements.first());
		if (elements.isEmpty()) {
			return array;
		}
		for (Element tr : elements) {
			String period = StringUtils.substringBetween(tr.child(1).text(), " ", "期");
			if (!roundno.equals(period)) {
				continue;
			}
			String[] betItems = tr.getElementsByClass("font_blue").text().split("[ 【】]");
			if (StringTool.contains(betItems[0], "冠", "亚")) {
				betItems[0] = UnConfig.BET_INFO_CODE.get(betItems[0]);
			}
			if (NumberTool.isInteger(betItems[1])) {
				betItems[1] = NumberTool.getInteger(betItems[1]) + "";
			}
			String betItem = betItems[0].concat("|").concat(betItems[1]);
			array.add(betItem.concat("|") + NumberTool.intValueT(tr.child(5).text()));
		}
		return array;
	}

	/**
	 * 过滤限额信息
	 *
	 * @param html 游戏限额信息
	 * @return 过滤限额信息
	 */
	public JSONArray filterQuotaInfo(String html) {
		Document document = Jsoup.parse(html);

		Element element = document.getElementsByClass("user_table").last();
		Elements trs = element.select("tr");
		trs.remove(0);
		JSONArray quota = new JSONArray();
		JSONArray array;
		for (Element tr : trs) {
			array = new JSONArray();
			array.add(tr.child(2).text());
			array.add(tr.child(3).text());
			array.add(tr.child(4).text());
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
	public String getBetItemInfo(BaseGameUtil.Code gameCode, List<String> betItems, JSONObject odds, String memberType, Object period) {
		Map<String, String> ballCodePlace = UnConfig.BALL_CODE_PLACE;
		Map<String, String> ballCodeRange = UnConfig.BALL_CODE_RANGE;
		String gameNo = BaseHandicapUtil.handicap(HANDICAP_CODE).gameType().get(gameCode.name());
		StringBuilder rates = new StringBuilder();
		StringBuilder betPlays = new StringBuilder();
		StringBuilder numbers = new StringBuilder();
		StringBuilder moneys = new StringBuilder();
		for (String betItem : betItems) {
			String[] items = betItem.split("\\|");
			JSONObject placeOdds = (JSONObject) odds.get(ballCodePlace.get(items[0]));
			// 名次
			betPlays.append(ballCodePlace.get(items[0])).append(",");
			// 范围
			numbers.append(ballCodeRange.get(items[1])).append(",");
			//赔率
			rates.append(placeOdds.get(ballCodeRange.get(items[1]))).append(",");
			//金额
			int fund = (int) NumberTool.doubleT(items[2]);
			moneys.append(fund).append(",");
		}
		rates.deleteCharAt(rates.length() - 1);
		betPlays.deleteCharAt(betPlays.length() - 1);
		numbers.deleteCharAt(numbers.length() - 1);
		moneys.deleteCharAt(moneys.length() - 1);
		String bets = "{\"betPlays\":\"%s\",\"numbers\":\"%s\",\"moneys\":\"%s\",\"rates\":\"%s\",\"g\":\"%s\"," +
				"\"qh\":\"%s\",\"B_TYPE\":\"B_109\",\"H_rate_bh\":\"0\",\"B_hType\":\"%s\"}";
		bets = String.format(bets, betPlays.toString(), numbers.toString(), moneys.toString(), rates.toString(), gameNo,
				period, memberType);
		return bets;
	}


}
