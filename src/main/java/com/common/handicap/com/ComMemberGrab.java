package com.common.handicap.com;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.config.handicap.ComConfig;
import com.common.handicap.crawler.GrabMember;
import com.common.util.BaseGameUtil;
import com.common.util.BaseHandicapUtil;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * COM盘口 抓取操作类
 *
 * @Author: Dongming
 * @Date: 2020-06-09 18:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ComMemberGrab extends BaseComGrab implements GrabMember {

	//region 开启页面
	/**
	 * 获取线路页面
	 *
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 线路页面
	 */
	public String getSelectRoutePage(String handicapUrl, String handicapCaptcha, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String routeHtml = null;
		try {
			String navigationHtml = HttpClientUtil.findInstance().getHtml(httpConfig().url(handicapUrl));
			if (StringTool.isEmpty(navigationHtml)) {
				log.error("获取导航页面失败:{}", navigationHtml);
				sleep();
				return getSelectRoutePage(handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (!StringTool.isContains(navigationHtml, "登 錄")) {
				setCookie(handicapUrl);
				sleep();
				return getSelectRoutePage(handicapUrl, handicapCaptcha, ++index[0]);
			}
			Map<String, Object> map = new HashMap<>(2);
			map.put("code", handicapCaptcha);
			map.put("Submit", "登 錄");
			routeHtml = HttpClientUtil.findInstance()
					.postHtml(httpConfig().url(handicapUrl.concat("Web/SearchLine.aspx")).map(map));
			if (StringTool.isEmpty(routeHtml) || !StringTool.isContains(routeHtml, "線路選擇")) {
				log.error("获取线路页面失败:{}", routeHtml);
				longSleep();
				return getSelectRoutePage(handicapUrl, handicapCaptcha, ++index[0]);
			}
		} catch (Exception e) {
			log.error("打开选择线路界面失败", e);
			longSleep();
			return getSelectRoutePage(handicapUrl, handicapCaptcha, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), handicapUrl, handicapCaptcha, index[0],
					routeHtml);
		}
		//线路页面
		return routeHtml;
	}

	/**
	 * 获取登录页面
	 *
	 * @param httpConfig http请求配置类
	 * @param routes     线路
	 * @param index      循环次数
	 * @return 登录信息map
	 */
	public String getLoginHtml(HttpClientConfig httpConfig, String[] routes, int... index) {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] > MAX_RECURSIVE_SIZE) {
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return null;
		}
		String html = null;
		String loginSrc;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig.url(routes[index[0]]));
			//获取登录页面
			if (StringTool.isEmpty(html)) {
				log.error("打开登录页面失败");
				longSleep();
				return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
			}
			if (!StringTool.isContains(html, "用戶登錄")) {
				sleep();
				setCookie(routes[index[0]].concat("/"));
				return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
			}
			loginSrc = routes[index[0]];
		} catch (Exception e) {
			log.error("打开登录页面失败", e);
			longSleep();
			return getLoginHtml(httpConfig, routes, index[0], ++index[1]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), routes[index[0]], "", index[1], html);
		}
		return loginSrc;
	}

	public JSONObject getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
							   String projectHost, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(3);
		join.put("loginName", memberAccount);
		join.put("loginPwd", memberPassword);
		join.put("ValidateCode", "");

		String html = null;
		String url = projectHost.concat("/Handler/LoginHandler.ashx?action=user_login");
		try {
			//登录信息
			html = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取登陆信息失败");
				longSleep();
				return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
			}
			return parseObject(html);
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			longSleep();
			return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], html);
		}
	}

	/**
	 * 盘口主页面
	 * http://mem1.wzlkah517.hlbrhuanyou.com:88/Index.aspx
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 线路地址
	 * @param index       循环次数
	 * @return 盘口主页面href
	 */
	public String getIndex(HttpClientConfig httpConfig, String projectHost, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String indexPage = null;
		String url = projectHost.concat("/Index.aspx");
		try {
			indexPage = HttpClientUtil.findInstance().getHtml(httpConfig.url(url));
			if (StringTool.isEmpty(indexPage) || !StringTool.isContains(indexPage, "browserCode")) {
				log.error("获取盘口主页面失败,页面={}", indexPage);
				longSleep();
				return getIndex(httpConfig, projectHost, ++index[0]);
			}
			return StringUtils.substringBetween(indexPage, "var browserCode='", "';");
		} catch (Exception e) {
			log.error("获取盘口主页面失败");
			longSleep();
			return getIndex(httpConfig, projectHost, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], indexPage);
		}
	}

	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	public JSONObject getUserInfo(int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("action", "get_ad");
		join.put("browserCode", browserCode());
		String html = null;
		String url = projectHost().concat("Handler/QueryHandler.ashx");
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(html) || !StringTool.isContains(html, "success", "data")) {
				log.error("获取用户信息失败，结果信息=" + html);
				sleep();
				return getUserInfo(++index[0]);
			}
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取用户余额信息失败,错误信息=" + html, e);
			sleep();
			return getUserInfo(++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], html);
		}
	}

	/**
	 * 获取游戏限额
	 *
	 * @param gameCode 盘口游戏code
	 * @return 游戏限额信息
	 */
	public JSONArray getQuotaList(BaseGameUtil.Code gameCode) {
		return getQuotaList(BaseHandicapUtil.handicap(HANDICAP_CODE).gameType().get(gameCode.name()));
	}

	/**
	 * 获取游戏限额
	 *
	 * @param gameType 盘口游戏TYPE
	 * @param index    循环次数
	 * @return 游戏限额信息
	 */
	private JSONArray getQuotaList(String gameType, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONArray();
		}
		String html = null;
		String url = projectHost().concat("CreditInfo.aspx?id=").concat(gameType);
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败");
				sleep();
				return getQuotaList(gameType, ++index[0]);
			}
			if (StringTool.isContains(html, "<script>top.location.href='/'</script>", "抱歉，處理您的請求時出錯")) {
				log.error("获取游戏限额页面失败，错误页面=" + html);
				return null;
			}
			if (!StringTool.isContains(html, "信用資料")) {
				log.error("获取游戏限额页面失败，错误页面=" + html);
				sleep();
				return getQuotaList(gameType, ++index[0]);
			}
			return getLimit(html);
		} catch (Exception e) {
			log.error("获取游戏限额失败,错误信息=" + html, e);
			sleep();
			return getQuotaList(gameType, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);
		}
	}

	/**
	 * 获取赔率信息
	 *
	 * @param gameCode 游戏code
	 * @param betType  投注类型
	 * @return 赔率信息
	 */
	public JSONObject getOddsInfo(BaseGameUtil.Code gameCode, String betType) {
		String betUrl = ComConfig.GAME_URL.get(gameCode.name());
		String gameType = BaseHandicapUtil.handicap(HANDICAP_CODE).gameType().get(gameCode.name());
		String type = BaseHandicapUtil.handicap(HANDICAP_CODE).betType(gameCode).get(betType);
		return getOddsInfo(betUrl, gameType, type);
	}

	/**
	 * 获取赔率信息
	 *
	 * @param betUrl   游戏投注url
	 * @param gameType 游戏类型
	 * @param betType  赔率code
	 * @param index    循环次数
	 * @return 赔率信息
	 */
	private JSONObject getOddsInfo(String betUrl, String gameType, String betType, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		Map<String, Object> join = new HashMap<>(3);
		join.put("action", "get_oddsinfo");
		join.put("playid", betType);
		join.put("playpage", gameType);
		String html = null;
		String url = projectHost().concat(betUrl).concat("/Handler/Handler.ashx");
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取赔率信息页面失败");
				sleep();
				return getOddsInfo(betUrl, gameType, betType, ++index[0]);
			}
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取赔率信息页面失败,结果信息=" + html, e);
			sleep();
			return getOddsInfo(betUrl, gameType, betType, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, join, index[0], html);
		}
	}

	/**
	 * 获取盘口验证数据
	 *
	 * @param gameType 游戏类型
	 * @param gameUrl  游戏url
	 * @param index    循环次数
	 * @return 盘口验证数据
	 */
	public String getJeuValidate(String gameType, String gameUrl, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost().concat(gameUrl).concat("/index.aspx?lid=").concat(gameType).concat("&path=")
				.concat(gameUrl);
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取盘口验证信息失败");
				sleep();
				return getJeuValidate(gameType, gameUrl, ++index[0]);
			}
			return StringUtils.substringBetween(html, "var JeuValidate = '", "';");
		} catch (Exception e) {
			log.error("获取赔率信息页面失败,结果信息=" + html, e);
			sleep();
			return getJeuValidate(gameType, gameUrl, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);
		}
	}

	/**
	 * 开始投注
	 *
	 * @param gameUrl  游戏地址
	 * @param betInfos 投注信息
	 * @return 投注结果
	 */
	public JSONObject betting(String gameUrl, Map<String, Object> betInfos) {
		String html = null;
		String url = projectHost().concat(gameUrl).concat("/Handler/Handler.ashx");
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(betInfos));
			if (ContainerTool.isEmpty(html)) {
				log.error("投注失败,结果-{},投注项为：{}", html, betInfos);
				return new JSONObject();
			}
			return parseObject(html);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, betInfos, 0, html);
		}
	}

	/**
	 * 获取未结算页面
	 *
	 * @param gameType 游戏信息
	 * @param page     页数
	 * @param masterId 主页
	 * @param index    循环次数
	 * @return 未结算页面
	 */
	public String getIsSettlePage(String gameType, int page, String masterId, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost().concat("Bet.aspx?");
		if (StringTool.isEmpty(masterId)) {
			url += "Bet.aspx?id=" + gameType;
		} else {
			url += "Bet.aspx?page=".concat(String.valueOf(page)).concat("&masterId=").concat(masterId);
		}
		String html = null;
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算页面失败-{}", html);
				sleep();
				return getIsSettlePage(gameType, page, masterId, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("获取未结算页面失败", e);
			sleep();
			return getIsSettlePage(gameType, page, masterId, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);
		}
	}

	//endregion

	//region 解析页面
	/**
	 * 获取会员可用线路
	 *
	 * @param routeHtml 线路页面
	 * @return 线路数组
	 */
	public String[] getMemberRoute(String routeHtml) {
		Document document = Jsoup.parse(routeHtml);

		Elements as = document.select("div.left1>div.box>ul>li>a");
		String[] routes = new String[as.size()];
		for (int i = 0; i < as.size(); i++) {
			routes[i] = as.get(i).attr("href");
		}

		return routes;
	}

	/**
	 * 解析游戏限额页面
	 *
	 * @param html 游戏限额页面
	 * @return 游戏限额
	 */
	private JSONArray getLimit(String html) {
		Document document = Jsoup.parse(html);
		Elements trs = document.select("tbody.hover_list>tr");
		JSONArray quota = new JSONArray();
		JSONArray array;
		for (Element tr : trs) {
			array = new JSONArray();
			String text = tr.text();
			String[] limits = text.split(" ");
			if (limits.length < 5) {
				continue;
			}
			array.add(Integer.parseInt(tr.child(2).text()));
			array.add(Integer.parseInt(tr.child(3).text()));
			array.add(Integer.parseInt(tr.child(4).text()));
			quota.add(array);
		}
		return quota;
	}
	public Map<String, Object> getBetItemInfo(BaseGameUtil.Code gameCode, List<String> betItems, JSONObject odds) {
		Map<String, String> itemCode = BaseHandicapUtil.handicap(HANDICAP_CODE).itemCodeMap(gameCode);
		Map<String, Object> betInfos = new HashMap<>(8);

		StringBuilder oddsId = new StringBuilder();
		StringBuilder upiP = new StringBuilder();
		StringBuilder upiM = new StringBuilder();
		StringBuilder index = new StringBuilder();

		JSONObject playOdds;
		for (int i = 0, length = betItems.size(); i < length; i++) {
			String[] items = betItems.get(i).split("\\|");
			String bet = items[0].concat("|").concat(items[1]);

			//单注金额须为整数值
			int fund = (int) Math.ceil(NumberTool.doubleT(items[2]));

			String betCode = itemCode.get(bet);
			if (StringTool.isEmpty(betCode)) {
				log.error("错误的投注项" + betItems.get(i));
				continue;
			}
			playOdds = odds.getJSONObject(betCode);

			oddsId.append(betCode.split("_")[1]).append(",");
			upiP.append(playOdds.getString("pl")).append(",");
			upiM.append(fund).append(",");
			index.append(i).append(",");
		}
		oddsId.delete(oddsId.length() - 1, oddsId.length());
		upiP.delete(upiP.length() - 1, upiP.length());
		upiM.delete(upiM.length() - 1, upiM.length());
		index.delete(index.length() - 1, index.length());

		betInfos.put("oddsid", oddsId.toString());
		betInfos.put("uPI_P", upiP.toString());
		betInfos.put("uPI_M", upiM.toString());
		betInfos.put("i_index", index.toString());

		return betInfos;
	}
	/**
	 * 获取未结算信息
	 * @param html 未结算
	 * @param gameCode 游戏编码
	 * @param roundno 平台期数
	 * @return 未结算信息
	 */
	public List<String> getIsSettleInfo(String html, BaseGameUtil.Code gameCode, Object roundno) {
		List<String> array = new ArrayList<>();
		Document document = Jsoup.parse(html);

		Elements trList =document.getElementById("box_kc").select("tbody>tr");
		trList.remove(0);
		trList.remove(trList.size()-1);
		trList.remove(trList.size()-1);

		if(StringTool.isContains(trList.text(),"無未結算記錄")){
			return array;
		}
		for(Element tr:trList){
			String period=StringUtils.substringBetween(tr.child(1).text(),"# ","期");
			if(!roundno.equals(period)){
				continue;
			}
			String bet=getBetItem(gameCode,tr.child(2).text().split("】")[0]);
			array.add(bet.concat("|")+ NumberTool.intValueT(tr.child(3).text()));
		}
		return array;
	}
	/**
	 * 获取投注项
	 *
	 * @param gameCode 投注编码
	 * @param betInfo  投注信息
	 * @return 投注项
	 */
	private static String getBetItem(BaseGameUtil.Code gameCode, String betInfo) {
		String[] infos = betInfo.split("【");

		String rank=infos[0].replace("大小","").replace("單雙","").replace("總","总")
				.replace(" ","");

		if(ComConfig.GAME_RANK_CODE.containsKey(rank)){
			rank= ComConfig.GAME_RANK_CODE.get(rank);
		}
		//單,雙,龍
		String item=infos[1].replace("單","单").replace("雙","双")
				.replace("龍","龙");
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				rank = rank.replace("龍虎","");
				return rank.concat("|").concat(item);
			case JSSSC:
			case CQSSC:
				rank=rank.replace("龍虎","龙虎和");
				//順子,對子,半順,雜六,總和,後三
				item = item.replace("順", "顺").replace("對", "对")
						.replace("雜", "杂");
				return rank.concat("|").concat(item);
			case GDKLC:
			case XYNC:
				rank=rank.replace("龍虎","总和").replace("中發白","").replace("方位","");
				item=item.replace("東","东").replace("發","发");
				if(StringTool.isContains(rank,"尾數")){
					rank=rank.replace("尾數","");
					item="尾".concat(item);
				}
				if(StringTool.isContains(rank,"合數")){
					rank=rank.replace("合數","");
					item="合".concat(item);
				}
				if (item.startsWith("0")){
					item = item.substring(1);
				}
				return rank.concat("|").concat(item);
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	//endregion
}
