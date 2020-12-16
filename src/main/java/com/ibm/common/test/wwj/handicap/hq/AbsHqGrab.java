package com.ibm.common.test.wwj.handicap.hq;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.core.configs.HqConfig;
import com.ibm.common.test.wwj.handicap.AbsHandicapGrab;
import com.ibm.common.test.wwj.handicap.HttpType;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 环球盘口爬虫支持类 抽象类
 * @Author: Dongming
 * @Date: 2019-11-22 14:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class AbsHqGrab extends AbsHandicapGrab {

	AbsHqGrab() {
		super(HandicapUtil.Code.HQ);
	}

	// TODO 开启页面函数

	//  开启页面函数

	/**
	 * 进入主页面
	 *
	 * @param httpConfig
	 * @param homeUrl    主页面地址
	 * @return 主页面
	 */
	@Deprecated @Override
	public String home(HttpClientConfig httpConfig, String homeUrl) {
		return null;
	}


	/**
	 * 没有实现-无作用
	 */
	@Deprecated @Override public String selectRoute(HttpClientConfig httpConfig, String handicapUrl,
													String handicapCaptcha, int... index) {
		return null;
	}
	/**
	 * 获取线路
	 *
	 * @param httpConfig      请求配置
	 * @param handicapCaptcha 请求验证码
	 * @param roleId          角色主键
	 * @param index           循环次数
	 * @return 线路
	 */
	protected String[] routes(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha, String roleId,
							  int index) {
		try {
			String urlFormat = "%s/Url/GetUrlList?token=%s&isbackend=%s&_=&d";
			httpConfig.url(String
					.format(urlFormat, httpConfig.url(), handicapCaptcha, roleId, System.currentTimeMillis()));

			String html = html(httpConfig.method(HttpConfig.Method.GET), HttpType.Normal);
			if (ContainerTool.isEmpty(html)) {
				Thread.sleep(SLEEP);
				return routes(httpConfig.url(handicapUrl), handicapCaptcha, ++index);
			}
			String[] routes = getRoutes(html);
			if (ContainerTool.isEmpty(routes)) {
				log.error("获取线路失败，页面为：" + html);
				Thread.sleep(SLEEP);
				return routes(httpConfig.url(handicapUrl), handicapCaptcha, ++index);
			}
			return routes;
		} catch (Exception e) {
			log.error("获取线路失败", e);
			sleep("获取线路");
			return routes(httpConfig.url(handicapUrl), handicapCaptcha, ++index);
		}

	}

	/**
	 * 代理和会员的方法一致
	 */
	@Override public Map<String, String> loginInfo(HttpClientConfig httpConfig, String[] routes, int... index) {
		if (index.length == 0) {
			index = new int[2];
		}
		if (index[1] >= MAX_RECURSIVE_SIZE) {
			index[1] = 0;
			index[0]++;
		}
		if (index[0] >= routes.length) {
			return null;
		}
		try {
			String url = "http://".concat(routes[index[0]])
					.concat("/Member/Login?_=" + System.currentTimeMillis() + "&token=" + httpConfig.data());
			String html = html(httpConfig.url(url).method(HttpConfig.Method.GET),HttpType.Normal);

			Map<String, String> info = getScriptInfo(html);
			if (ContainerTool.isEmpty(info)) {
				log.error("解析登录页面信息失败，页面为：" + html);
				Thread.sleep(SLEEP);
				return loginInfo(httpConfig, routes,index[0], ++index[1]);
			}
			info.put("hostUrl", routes[index[0]]);
			return info;
		} catch (Exception e) {
			log.error("获取登录信息失败", e);
			sleep("获取登录信息");
			return loginInfo(httpConfig, routes,index[0], ++index[1]);
		}
	}


	/**
	 * 获取登录加密key
	 *
	 * @param httpConfig http请求配置类
	 * @param sessionId  参数sessionid
	 * @param hostUrl    主机地址
	 * @param index      循环次数
	 * @return 登录加密key
	 */
	protected JSONObject getEncryptKey(HttpClientConfig httpConfig, String sessionId, String hostUrl, int... index)
	{
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		try {
			String url = "http://".concat(hostUrl).concat(sessionId).concat("/Member/GK");
			String html = html(httpConfig.url(url).method(HttpConfig.Method.POST),HttpType.Normal);
			if (ContainerTool.isEmpty(html)) {
				log.error("获取登录加密key失败");
				sleep("获取登录加密key");
				return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
			}
			JSONObject result = JSONObject.parseObject(html);
			if (ContainerTool.isEmpty(result) || result.getInteger("Status") - 1 != 0) {
				return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
			}
			return result.getJSONObject("Data");
		} catch (Exception e) {
			log.error("获取登录加密key失败",e);
			sleep("获取登录加密key");
			return getEncryptKey(httpConfig, sessionId, hostUrl, ++index[0]);
		}
	}
	/**
	 * 登录盘口
	 *
	 * @param httpConfig      http请求配置类
	 * @param hostUrl         主机地址
	 * @param sessionId       参数sessionId
	 * @param handicapCaptcha 盘口验证码
	 * @param encryptKey      加密信息-vk
	 * @param logpk           参数logpk
	 * @param index           循环次数
	 * @return
	 * @throws InterruptedException
	 */
	protected String getLogin(HttpClientConfig httpConfig, String hostUrl, String sessionId, String handicapCaptcha,
			String encryptKey, String logpk, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		try {
			String url = "http://".concat(hostUrl).concat(sessionId).concat("/Member/DoLogin");
			Map<String, Object> join = new HashMap<>(4);
			join.put("token", handicapCaptcha);
			join.put("Captcha", "");
			join.put("info", encryptKey);
			join.put("pk", logpk);

			String html = html(httpConfig.url(url).map(join).method(HttpConfig.Method.POST),HttpType.Normal);
			if (StringTool.isEmpty(html)) {
				log.error("登录盘口失败");
				sleep("登录盘口");
				return getLogin(httpConfig, hostUrl, sessionId, handicapCaptcha, encryptKey, logpk, ++index[0]);
			}
			return html;
		} catch (Exception e) {
			log.error("登录盘口失败", e);
			sleep("登录盘口");
			return getLogin(httpConfig, hostUrl, sessionId, handicapCaptcha, encryptKey, logpk, ++index[0]);
		}
	}

	//  开启页面函数

	// TODO 页面解析函数

	//  页面解析函数


	/**
	 * 解析登录界面的 Script 信息
	 * @param html 登录界面
	 * @return  Script 信息
	 */
	private Map<String, String> getScriptInfo(String html) {
		if (StringTool.isEmpty(html)) {
			return null;
		}
		Document document = Jsoup.parse(html);
		Element script = document.selectFirst("script");
		if (script == null) {
			return null;
		}
		String sessionId = StringUtils.substringBetween(script.html(), "var SESSIONID = \"", "\";");
		String version = StringUtils.substringBetween(script.html(), "var VERSION = \"", "\";");
		if (StringTool.isEmpty(sessionId, version)) {
			return null;
		}
		Map<String, String> map = new HashMap<>(2);
		map.put("SESSIONID", "/".concat(sessionId));
		map.put("VERSION", version);
		return map;

	}

	/**
	 * 获取会员信息
	 *
	 * @param html 会员信息列表
	 * @return 会员信息
	 */
	public List<Map<String,String>> getMemberInfo(String html) {
		JSONObject data = JSONObject.parseObject(html).getJSONObject("Data");

		JSONArray array = data.getJSONArray("Data");
		List<Map<String,String>> memberList = new ArrayList<>();
		Map member;
		for (Object obj : array) {
			JSONObject jsonObject = (JSONObject) obj;
			member = new HashMap();
			member.put("Account",jsonObject.getString("Account"));
			member.put("IsOnline",jsonObject.getInteger("IsOnline") == 1 ? "online" : "offline");
			memberList.add(member);
		}
		return memberList;
	}

	/**
	 * 未结算摘要
	 *
	 * @param html 未结算摘要信息
	 * @return 未结算摘要 - memberId - memberAccount - betCount
	 */
	public Map<String, Map<String, Integer>> getBetSummary(String html) {
		if (!StringTool.contains(html, "MemberNickName")) {
			return null;
		}
		JSONArray data = JSONObject.parseObject(html).getJSONObject("Data").getJSONArray("Data");
		Map<String, Map<String, Integer>> betSummary = new HashMap<>(data.size());

		JSONObject betSummaryInfo;
		for (int i = 0; i < data.size(); i++) {
			Map<String, Integer> info = new HashMap<>(2);
			betSummaryInfo = data.getJSONObject(i);
			System.out.println("betSummaryInfo=="+betSummaryInfo);
			info.put("memberId", betSummaryInfo.getInteger("MemberId"));
			info.put("betCount", betSummaryInfo.getInteger("BetCount"));
			betSummary.put(betSummaryInfo.getString("MemberAccount"), info);
		}
		return betSummary;
	}

	/**
	 * 解析获取盘口游戏限额
	 *
	 * @param html 会员投注信息页面
	 * @param game 盘口游戏code
	 * @return
	 */
	protected JSONArray getLimit(String html, String game) {

		JSONObject data = JSONObject.parseObject(html).getJSONObject("Data");

		if (!data.containsKey("BetLimits")) {
			return null;
		}
		JSONArray betLimits = data.getJSONArray("BetLimits");
		JSONArray quota = new JSONArray();
		JSONArray array;
		JSONObject infos;
		for (int i = 0; i < betLimits.size(); i++) {
			if (!String.valueOf(betLimits.getJSONObject(i).getInteger("LotteryId")).equals(game)) {
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


	//  页面解析函数

	// TODO 功能函数

	//  功能函数


	/**
	 * 获取 排序后的线路
	 *
	 * @param html 线路页面
	 * @return 排序后的线路
	 */
	private static String[] getRoutes(String html) {
		JSONObject json;
		try {
			json = JSONObject.parseObject(html);
			if (!json.containsKey("Data")) {
				log.warn("获取线路失败，不存在key{Data}，页面为：【" + html + "】");
				return null;
			}
		} catch (Exception e) {
			log.error("转换结果页面失败【" + html + "】", e);
			return null;
		}

		JSONArray data = json.getJSONArray("Data");
		String[] routes = new String[data.size()];

		long[] times = new long[data.size()];

		//判断时间延迟
		long timeStart, timeEnd;
		String url = "http://%s/%d.jpg";
		for (int i = 0; i < data.size(); i++) {
			timeStart = System.currentTimeMillis();
			try {
				HttpClientTool.doGet(String.format(url, data.getString(i), System.currentTimeMillis()), 500);
			}catch (Exception ignore){
			}
			timeEnd = System.currentTimeMillis();
			routes[i] = data.getString(i);
			times[i] = timeEnd - timeStart;
		}

		//排序
		for (int x = 0; x < times.length; x++) {
			for (int j = 0; j < times.length - x - 1; j++) {
				if (times[j] > times[j + 1]) {
					long time = times[j];
					String route = routes[j];

					times[j] = times[j + 1];
					routes[j] = routes[j + 1];

					times[j + 1] = time;
					routes[j + 1] = route;
				}
			}
		}
		return routes;
	}

	/**
	 * 登陆错误
	 *
	 * @param msg 错误信息
	 * @return 错误信息
	 */
	public HcCodeEnum loginError(String msg) {
		if (StringTool.contains(msg, "用户名或密码不正确")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "帐号被锁定")) {
			return HcCodeEnum.IBS_403_USER_STATE;
		} else if (StringTool.contains(msg, "请求过于频繁")) {
			return HcCodeEnum.IBS_403_LOGIN_OFTEN;
		} else if (StringTool.contains(msg, "您的帐户为初次登陆", "密码由后台重新设定")) {
			return HcCodeEnum.IBS_403_CHANGE_PASSWORD;
		} else {
			return HcCodeEnum.IBS_403_UNKNOWN;
		}
	}

	/**
	 * 获取投注项
	 *
	 * @param gameCode 投注编码
	 * @param betInfo  投注信息
	 * @return 投注项
	 */
	protected String getBetItem(GameUtil.Code gameCode, String betInfo) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
				String[] infos = betInfo.split(" ");
				switch (infos[0]) {
					case "冠军":
						return "第一名|".concat(infos[1]);
					case "亚军":
						return "第二名|".concat(infos[1]);
					case "冠亚和":
						return "冠亚|".concat(infos[1]);
					default:
						return infos[0].concat("|").concat(infos[1]);
				}
			case JSSSC:
			case CQSSC:
				return betInfo;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}

	/**
	 * 获取赔率code
	 *
	 * @param gameCode     游戏编码
	 * @param handicapType 盘口类型
	 * @return
	 */
	protected List<String> getMarketTypes(GameUtil.Code gameCode, String handicapType) {
		switch (gameCode) {
			case CQSSC:
				return HqConfig.CQSSC_CODE;
			case JSSSC:
				return HqConfig.JSSSC_CODE;
			case PK10:
				switch (handicapType) {
					case "dobleSides":
						return HqConfig.PK10_DOUBLE_SIDES;
					case "ballNO":
						return HqConfig.PK10_BALL_NO;
					case "sumDT":
						return HqConfig.PK10_SUM_DT;
					default:
						return null;
				}
			case XYFT:
				switch (handicapType) {
					case "dobleSides":
						return HqConfig.XYFT_DOUBLE_SIDES;
					case "ballNO":
						return HqConfig.XYFT_BALL_NO;
					case "sumDT":
						return HqConfig.XYFT_SUM_DT;
					default:
						return null;
				}
			case JS10:
				switch (handicapType) {
					case "dobleSides":
						return HqConfig.JS10_DOUBLE_SIDES;
					case "ballNO":
						return HqConfig.JS10_BALL_NO;
					case "sumDT":
						return HqConfig.JS10_SUM_DT;
					default:
						return null;
				}
			default:
				return null;
		}
	}

	/**
	 * @param gameCode
	 * @param betItems
	 * @param oddsData
	 * @return
	 */
	public JSONArray getBetItemInfo(GameUtil.Code gameCode, String game, List<String> betItems,
									JSONArray oddsData) {
		JSONArray betsArray = new JSONArray();
		Map<String, String> ballCode = BallCodeTool.getBallCode(HandicapUtil.Code.HQ, gameCode);
		JSONObject betInfo;
		JSONObject odds;
		for (String betItem : betItems) {
			betInfo = new JSONObject();
			String[] items = betItem.split("\\|");
			String bet = items[0].concat("|").concat(items[1]);
			//单注金额须为整数值
			int fund = (int) NumberTool.doubleT(items[2]);
			String betCode = ballCode.get(bet);
			if (StringTool.isEmpty(betCode)) {
				log.error("错误的投注项" + betItem);
				continue;
			}
			for (int i = 0; i < oddsData.size(); i++) {
				odds = oddsData.getJSONObject(i);
				if (!game.concat(betCode).equals(String.valueOf(odds.getInteger("BetNo")))) {
					continue;
				}
				betInfo.put("BetNo", game.concat(betCode));
				betInfo.put("Odds", String.valueOf(odds.getDouble("Odds")));
				betInfo.put("BetMoney", fund);
				betsArray.add(betInfo);
				break;
			}
		}
		return betsArray;
	}

	/**
	 * 获取投注结果
	 *
	 * @param gameCode 游戏code
	 * @param items    投注结果
	 * @param game     盘口游戏code
	 * @return
	 */
	public JSONArray getBetResult(GameUtil.Code gameCode, JSONArray items, String game) {
		Map<String, String> ballInfo = BallCodeTool.getBallInfoCode(HandicapUtil.Code.HQ, gameCode);

		JSONArray betResult = new JSONArray();
		JSONObject info;
		JSONArray betInfo;
		for (int i = 0; i < items.size(); i++) {
			info = items.getJSONObject(i);
			String item = ballInfo.get(String.valueOf(info.getInteger("BetNo")).substring(game.length()));
			if (StringTool.isEmpty(item)) {
				log.error("错误的投注项：" + info);
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
	 * 过滤投注成功项
	 *
	 * @param data     投注成功项
	 * @param betItems 投注详情信息
	 * @param gameCode 游戏编码
	 */
	protected JSONArray filterSuccessInfo(JSONObject data, List<String> betItems, GameUtil.Code gameCode) {
		Map<String, String> ballCode = BallCodeTool.getResultCode(HandicapUtil.Code.HQ, gameCode);

		JSONArray bettingResult = data.getJSONArray("BettingResult");

		JSONArray betResult = new JSONArray();
		JSONArray betInfo;
		JSONObject info;
		for (int i = 0; i < bettingResult.size(); i++) {
			info = bettingResult.getJSONObject(i);
			String item = info.getString("BetNoFullName").replace(" ", "");
			long fund = NumberTool.longValueT(info.getInteger("BetMoney"));
			String betCode = ballCode.get(item);
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
	 * 获取注单详情
	 *
	 * @param gameCode 游戏code
	 * @param data     注单列表信息
	 * @return 注单详情
	 */
	public static JSONArray getUnsettledDetailedInfo(GameUtil.Code gameCode, JSONArray data) {
		Map<String, String> ballItem = BallCodeTool.getBallItem(HandicapUtil.Code.IDC, gameCode);

		JSONArray array = new JSONArray();
		for (int i = 0; i < data.size(); i++) {
			JSONObject object = new JSONObject();
			JSONArray info = data.getJSONArray(i);
			object.put("oddNumber", info.getInteger(0));
			object.put("memberAccount", info.getString(2));
			String ballCode = String.valueOf(info.getInteger(7)).concat(":").concat(info.getString(8));

			String betItem = ballItem.get(ballCode);
			if (StringTool.isEmpty(betItem)) {
				log.error("错误的投注信息" + info);
				continue;
			}
			object.put("betItem", betItem);
			object.put("funds", info.getDouble(10));
			object.put("rno", info.get(4));
			array.add(object);
		}
		return array;
	}
	//  功能函数
}
