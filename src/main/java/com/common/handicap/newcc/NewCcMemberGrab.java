package com.common.handicap.newcc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.config.handicap.NewCcConfig;
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
public class NewCcMemberGrab extends BaseNewCcGrab implements GrabMember {
	private static final BaseHandicapUtil.Code HANDICAP_CODE = BaseHandicapUtil.Code.NEWCC;

	/**
	 * 获取会员线路
	 *
	 * @param httpConfig http请求配置类
	 * @param routeHtml  html
	 * @return 会员线路
	 */
	public String[] getMemberRoute(HttpClientConfig httpConfig, String routeHtml) {

		return getRoute(httpConfig, routeHtml, "会员");
	}

	public Map<String, String> getLoginHtml(HttpClientConfig httpConfig, String[] routes) {
		return getLoginHtml(httpConfig, routes, "member");
	}


	public String getMemberLogin(HttpClientConfig httpConfig, String projectHost, String account, String password, String verifyCode) throws InterruptedException {
		return getLogin(httpConfig, account, password, verifyCode, projectHost, "member/Main/left");

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
		String url = projectHost().concat("member/Main/left");

		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
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
	 * 获取用户盈亏信息
	 *
	 * @return html
	 */
	public String getUserGain(int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return "";
		}

		String html = null;
		String url = projectHost().concat("member/Game_v2/getSyjq");

		try {
			html = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(html)) {
				log.error("获取用户信息失败，结果信息=" + html);
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
	public String getQuotaList(BaseGameUtil.Code gameCode, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String gameNo = BaseHandicapUtil.handicap(HANDICAP_CODE).gameType().get(gameCode.name());
		String result = null;
		String url = projectHost().concat("member/User/userInfouu?gno=").concat(gameNo);

		try {
			result = HttpClientUtil.findInstance().getHtml(httpConfig().url(url));
			if (StringTool.isEmpty(result)) {
				log.error("获取游戏限额失败");
				longSleep();
				return getQuotaList(gameCode, ++index[0]);
			}
			if (!StringTool.isContains(result, "信用")) {
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
	 * 预投注处理
	 *
	 * @param bets     投注信息
	 * @param gameCode 游戏编码
	 * @return 预投注信息
	 */
	public String getOddsAndName(String bets, String gameCode, String memberType, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return "";
		}
		String html = null;
		String url = projectHost().concat("member/Submit/getOddsAndName");
		Map<String, Object> join = new HashMap<>(2);
		join.put("oddsName", bets);
		join.put("type", "1");
		try {
			// 加载页面
			loadPage(gameCode, memberType);
			html = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(html)) {
				log.error("预投注处理失败，结果信息=" + html);
				sleep();
				return getOddsAndName(bets, gameCode, memberType, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("预投注处理失败,错误信息=" + html, e);
			sleep();
			return getOddsAndName(bets, gameCode, memberType, ++index[0]);
		}
	}

	/**
	 * 加载页面，以免投注失败
	 *
	 * @param gameCode 游戏code
	 */
	public void loadPage(String gameCode, String memberType) {

		String game = NewCcConfig.GAME_CODE.get(gameCode);
		Map<String, Object> join = new HashMap<>(2);
		join.put("No", getNewCcGameNo(gameCode, memberType));
		try {
			HttpClientUtil.findInstance().postHtml(httpConfig().url(projectHost().concat("member/Submit/bcyx")).map(join));
			HttpClientUtil.findInstance().getHtml(httpConfig().url(projectHost().concat("member/Main/index_")));
			HttpClientUtil.findInstance().getHtml(httpConfig().url(projectHost().concat("member/Main/menu")));
			HttpClientUtil.findInstance().getHtml(httpConfig().url(projectHost().concat("member/Game_v2/").concat(game).concat("?webUU=HJK1HUWH2FBBS8DS9WQ")));
		} catch (Exception e) {
			log.error("加载页面失败,错误信息=", e);
		}
	}

	/**
	 * 投注
	 *
	 * @param xdnum 投注码
	 * @param index 循环次数
	 * @return 投注
	 */
	public JSONObject betting(String xdnum, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(1);
		join.put("noLst", "");
		join.put("xdnum", xdnum);
		join.put("type", "1");
		join.put("MD5", "1348cc6337c41ec5075e3eefca3d3eee");
		String result = null;
		String url = projectHost().concat("member/Submit/setOrder");

		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig().url(url).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("投注失败");
				longSleep();
				return betting(xdnum, ++index[0]);
			}

			//转换为json
			JSONObject json = parseObject(result);
			if (ContainerTool.isEmpty(json)) {
				longSleep();
				return betting(xdnum, ++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("投注失败,结果信息=" + result, e);
			longSleep();
			return betting(xdnum, ++index[0]);
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
	public String getBetItemInfo(BaseGameUtil.Code gameCode, List<String> betItems, String memberType) {

		Map<String, String> itemCode = BaseHandicapUtil.handicap(HANDICAP_CODE).itemCodeMap(gameCode);
		int gameNo = NumberTool.getInteger(getNewCcGameNo(gameCode.name(), memberType)) * 100;
		StringBuilder oddsName = new StringBuilder();
		for (String betItem : betItems) {
			String[] items = betItem.split("\\|");
			String bet = items[0].concat("|").concat(items[1]);

			//单注金额须为整数值
			int fund = (int) NumberTool.doubleT(items[2]);
			String betCodeStr = itemCode.get(bet);
			if (StringTool.isEmpty(betCodeStr)) {
				log.error("错误的投注项" + betItem);
				continue;
			}
			int betCode = gameNo + NumberTool.getInteger(betCodeStr);
			oddsName.append(betCode).append(",").append("1.9829").append(",").append(fund).append("|");
		}
		oddsName.delete(oddsName.length() - 1, oddsName.length());

		return oddsName.toString();
	}

	/**
	 * 获取盘口游戏CODE
	 *
	 * @param gameCode     游戏CODE
	 * @param handicapType 盘口类别
	 * @return 盘口游戏CODE
	 */
	private String getNewCcGameNo(String gameCode, String handicapType) {
		if ("A".equals(handicapType)) {
			return NewCcConfig.A_GAME_BALL_CODE.get(gameCode);
		} else {
			return NewCcConfig.B_GAME_BALL_CODE.get(gameCode);
		}
	}

	/**
	 * 过滤限额信息
	 *
	 * @param quotahtnl  游戏限额信息
	 * @param memberType 会员盘口
	 * @return 过滤限额信息
	 */
	public JSONArray filterQuotaInfo(String quotahtnl, String memberType) {
		Document document = Jsoup.parse(quotahtnl);

		Elements trs = document.getElementsByClass("t_list_tr_0");
		int minLimit = "B".equals(memberType) ? 5 : 2;
		JSONArray quota = new JSONArray();
		JSONArray array;
		for (Element tr : trs) {
			array = new JSONArray();
			String text = tr.text();
			String[] limits = text.split(" ");

			array.add(minLimit);
			array.add(Integer.parseInt(limits[2]));
			array.add(Integer.parseInt(limits[3]));
			quota.add(array);
		}
		return quota;
	}

}
