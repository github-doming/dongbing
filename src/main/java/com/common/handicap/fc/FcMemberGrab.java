package com.common.handicap.fc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.config.handicap.FcBallConfig;
import com.common.config.handicap.FcConfig;
import com.common.config.handicap.FcHappyConfig;
import com.common.config.handicap.FcNumberConfig;
import com.common.handicap.Handicap;
import com.common.handicap.crawler.GrabMember;
import com.common.util.BaseGameUtil;
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
 * @Description:
 * @Author: null
 * @Date: 2020-07-13 16:27
 * @Version: v1.0
 */
public class FcMemberGrab extends BaseFcGrab implements GrabMember {

	/**
	 * 获取会员可用线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  线路页面
	 * @return 线路数组
	 */
	public String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml) {

		return getRoute(httpConfig, routeHtml, "w");
	}

	/**
	 * 获取会员登录页面
	 *
	 * @param httpConfig http请求配置类
	 * @param routes     线路数组
	 * @return 登录页面
	 */
	public String getMemberLoginHtml(HttpClientConfig httpConfig, String[] routes) {
		return getLoginHtml(httpConfig, routes, "用户");
	}

	/**
	 * 盘口登录
	 *
	 * @param httpConfig     http请求配置类
	 * @param memberAccount  盘口会员账号
	 * @param memberPassword 盘口会员密码
	 * @param projectHost    线路地址
	 * @param index          循环次数
	 * @return 盘口主页面href
	 */
	public JSONObject getLogin(HttpClientConfig httpConfig, String memberAccount, String memberPassword,
							   String projectHost, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded");
		Map<String, Object> join = new HashMap<>(4);
		join.put("ValidateCode", "");
		join.put("loginName", memberAccount);
		join.put("loginPwd", memberPassword);
		String url = projectHost.concat("Handler/LoginHandler.ashx?action=user_login");
		String result;
		try {
			//登录信息
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("获取登陆信息失败");
				longSleep();
				return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
			}
			//转换为json
			JSONObject json = parseObject(result);
			if (ContainerTool.isEmpty(json)) {
				longSleep();
				return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("获取登陆信息失败");
			longSleep();
			return getLogin(httpConfig, memberAccount, memberPassword, projectHost, ++index[0]);
		}

	}

	/**
	 * 获取用户信息
	 *
	 * @param index 循环次数
	 * @return 用户信息
	 */
	public String getUserInfo(int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return null;
		}
		String userInfoHtml;
		try {
			Map<String, Object> join = new HashMap<>(3);
			join.put("action", "get_ad");
			join.put("browserCode", "4443");
			String url = projectHost().concat("Handler/QueryHandler.ashx");
			//获取用户信息
			userInfoHtml = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(userInfoHtml)) {
				log.error("获取用户信息", userInfoHtml);
				longSleep();
				return getUserInfo(++index[0]);
			}
			if (StringTool.isContains(userInfoHtml, "請重新登陸")) {
				return "snatchMsg";
			}
			return userInfoHtml;
		} catch (Exception e) {
			log.error("获取用户信息", e);
			longSleep();
			return getUserInfo(++index[0]);
		}
	}

	/**
	 * 获取游戏限额
	 *
	 * @param gameCode 游戏编码
	 * @return 限额信息
	 */
	public JSONArray getQuotaList(String gameCode, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONArray();
		}
		String html = null;

		String url = projectHost().concat("CreditInfo.aspx?p=1&id=").concat(gameCode);
		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏限额失败");
				longSleep();
				return getQuotaList(gameCode, ++index[0]);
			}
			return getLimit(html);
		} catch (Exception e) {
			log.error("获取游戏限额失败,错误信息=" + html, e);
			longSleep();
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
	 * @param index    循环次数
	 * @return 赔率信息
	 */
	public JSONObject getOddsInfo(BaseGameUtil.Code gameCode, String betType, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return new JSONObject();
		}
		Map<String, String> betParameter = getBetParameter(gameCode, betType);
		String html = null;
		Map<String, Object> join = new HashMap<>(3);
		join.put("action", "get_oddsinfo");
		join.put("playid", betParameter.get("playid"));
		join.put("playpage", betParameter.get("playpage"));
		String url = projectHost().concat(FcConfig.GAME_CODE_PATH.get(gameCode.name())).concat("/Handler/Handler.ashx");
		try {
			html = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("获取赔率信息页面失败");
				sleep();
				return getOddsInfo(gameCode, betType, ++index[0]);
			}
			JSONObject resultHtml = JSONObject.parseObject(html);
			if (resultHtml.getInteger("success") != 200) {
				return null;
			}
			return resultHtml.getJSONObject("data");
		} catch (Exception e) {
			log.error("获取赔率信息页面失败,结果信息=" + html, e);
			sleep();
			return getOddsInfo(gameCode, betType, ++index[0]);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, "", index[0], html);
		}

	}

	/**
	 * 投注
	 *
	 * @param gameNo   游戏ID
	 * @param betsMap  投注内容
	 * @param playpage 路径
	 * @param codePath 路径
	 * @param phaseid  id
	 * @return 投注结果
	 */
	public JSONObject betting(Map<String, String> betsMap, String playpage, String codePath, String phaseid, String gameNo) {
		String url = projectHost().concat(codePath);
		httpConfig().setHeader("Content-Type", "application/x-www-form-urlencoded");
		httpConfig().setHeader("Referer", url + "/index.aspx?lid=" + gameNo + "2&path=" + codePath);

		Map<String, Object> info = new HashMap<>(8);
		info.put("action", "put_money");
		info.put("phaseid", phaseid);
		info.put("oddsid", betsMap.get("oddsid"));
		info.put("uPI_P", betsMap.get("uPI_P"));
		info.put("uPI_M", betsMap.get("uPI_M"));
		info.put("i_index", betsMap.get("i_index"));
		info.put("JeuValidate", "07171024394568");
		info.put("playpage", playpage);
		try {
			String resultHtml = HttpClientUtil.findInstance()
					.postHtml(httpConfig().url(url.concat("/Handler/Handler.ashx")).map(info));
			if (ContainerTool.isEmpty(resultHtml)) {
				log.error("投注结果页面为空,投注项为：{}", betsMap);
				return new JSONObject();
			}
			return JSONObject.parseObject(resultHtml);
		} finally {
			httpLog.trace(HTTP_LOG_FORMAT, Thread.currentThread().getName(), url, info, 0, "");
		}

	}

	/**
	 * 获取投注详情内容
	 *
	 * @param handicap 盘口工具类
	 * @param gameCode 游戏编码
	 * @param betItems 投注详情
	 * @param odds     赔率
	 * @return 投注参数
	 */
	public Map<String, String> getBetItemInfo(Handicap handicap, BaseGameUtil.Code gameCode, List<String> betItems, JSONObject odds) {
		Map<String, String> ballCode = handicap.codeItemMap(gameCode);
		Map<String, String> betsMap = new HashMap<>(4);

		List<Object> oddsId = new ArrayList<>();
		List<Object> uPip = new ArrayList<>();
		List<Object> uPim = new ArrayList<>();
		List<Object> iIndex = new ArrayList<>();
		for (int i = 0; i < betItems.size(); i++) {
			String betItem = betItems.get(i);
			String[] items = betItem.split("\\|");
			String bet = items[0].concat("|").concat(items[1]);
			//单注金额须为整数值
			int fund = (int) NumberTool.doubleT(items[2]);
			String betCode = ballCode.get(bet);
			oddsId.add(betCode);
			uPip.add(odds.get(betCode));
			uPim.add(fund);
			iIndex.add(i);
		}
		betsMap.put("oddsid", StringUtils.join(oddsId.toArray(), ","));
		betsMap.put("uPI_P", StringUtils.join(uPip.toArray(), ","));
		betsMap.put("uPI_M", StringUtils.join(uPim.toArray(), ","));
		betsMap.put("i_index", StringUtils.join(iIndex.toArray(), ","));
		return betsMap;
	}

	/**
	 * 获取请求参数
	 *
	 * @param gameCode 游戏编码
	 * @param betType  投注类型
	 * @return 参数
	 */
	public Map<String, String> getBetParameter(BaseGameUtil.Code gameCode, String betType) {
		Map<String, String> betParameter = new HashMap<>(2);
		String playid = "";
		String playpage = FcConfig.GAME_CODE_PAGE.get(gameCode.name());
		switch (gameCode) {
			case PK10:
			case JS10:
			case XYFT:
			case COUNTRY_10:
				playpage += FcNumberConfig.GAME_TYPE_CODE.get(betType);
				playid = FcNumberConfig.GAME_ODDS_ID.get(betType);
				break;
			case CQSSC:
			case JSSSC:
			case COUNTRY_SSC:
				playpage += FcBallConfig.GAME_TYPE_CODE.get(betType);
				playid = FcBallConfig.GAME_ODDS_ID.get(betType);
				break;
			case GDKLC:
				playpage += FcHappyConfig.GAME_TYPE_CODE.get(betType);
				playid = FcHappyConfig.GAME_ODDS_ID.get(betType);
				break;
			default:
				break;
		}
		betParameter.put("playid", playid);
		betParameter.put("playpage", playpage);

		return betParameter;
	}

	/**
	 * 解析游戏限额页面
	 *
	 * @param html 游戏限额页面
	 * @return 游戏限额
	 */
	private JSONArray getLimit(String html) {
		Document document = Jsoup.parse(html);
		Elements tableClass = document.getElementsByClass("hover_list");
		JSONArray quota = new JSONArray();
		JSONArray array;
		for (Element table : tableClass) {
			Elements trs = table.select("tr");
			for (Element tr : trs) {
				array = new JSONArray();
				String text = tr.text();
				if (StringTool.notEmpty(text)) {
					String[] limits = text.split(" ");
					if (limits.length == 6) {
						array.add(limits[3]);
						array.add(limits[4]);
						array.add(limits[5]);
					} else {
						array.add(limits[2]);
						array.add(limits[3]);
						array.add(limits[4]);
					}
					quota.add(array);
				}
			}
		}
		return quota;
	}


}
