package com.ibm.common.utils.http.tools.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: IDC新盘口会员工具类
 * @Author: zjj
 * @Date: 2019-09-16 15:43
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IdcMemberApiTool {
	protected static final Logger log = LogManager.getLogger(IdcMemberApiTool.class);
    private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";
	private static final long SLEEP = 1000L;
	private static final long LONG_SLEEP = 2000L;

	private static final Integer MAX_RECURSIVE_SIZE = 5;

	// TODO 内部接口
	/**
	 * 获取登录url
	 *
	 * @param httpConfig      http请求配置类
	 * @param handicapUrl     盘口url
	 * @param handicapCaptcha 盘口验证码
	 * @param index           循环次数
	 * @return 登录url
	 */
	public static JSONObject getLoginUrl(HttpClientConfig httpConfig, String handicapUrl, String handicapCaptcha,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> map = new HashMap<>(1);
		map.put("code", handicapCaptcha);
		String url=handicapUrl.concat("/get_bet_url.ashx");
		String result=null;
		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(map));
			if (StringTool.isEmpty(result)) {
				log.error("获取登录url失败");
				Thread.sleep(SLEEP);
				return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if(StringTool.isContains(result,"Gateway Time-out")){
				log.error("获取登录url失败，网关超时");
				log.fatal("页面为="+result);
				Thread.sleep(SLEEP);
				return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if (StringTool.isContains(result, "Web Access blocked")) {
				log.error("获取用户信息失败，Web访问被阻止");
				log.fatal("页面为=[" + result);
				return null;
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取登录url失败，错误信息=" + result);
				Thread.sleep(SLEEP);
				return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
            //转换为json
            JSONObject json = parseObject(result);
            if (ContainerTool.isEmpty(json)) {
                Thread.sleep(LONG_SLEEP);
                return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
            }
			return json;
		} catch (Exception e) {
			log.error("获取登录url失败,结果信息="+result, e);
			Thread.sleep(SLEEP);
			return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,map,index[0],result));
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
	public static JSONObject getLoginTicket(HttpClientConfig httpConfig, String loginUrl, String parameters,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String result=null;
		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(loginUrl + parameters));
			if (StringTool.isEmpty(result)) {
				log.error("获取票证失败");
				Thread.sleep(LONG_SLEEP);
				return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
			}
			if(StringTool.isContains(result,"Gateway Time-out")){
				log.error("获取票证失败，网关超时");
				log.fatal("页面为="+result);
				Thread.sleep(SLEEP);
				return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取票证失败，错误信息=" + result);
				Thread.sleep(LONG_SLEEP);
				return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
			}
            //转换为json
            JSONObject json = parseObject(result);
            if (ContainerTool.isEmpty(json)) {
                Thread.sleep(LONG_SLEEP);
                return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
            }
            return json;
		} catch (Exception e) {
			log.error("获取票证失败,结果信息="+result, e);
			Thread.sleep(LONG_SLEEP);
			return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),loginUrl,parameters,index[0],result));
        }
	}
	/**
	 * 获取用户信息。10秒超过6次，锁定10秒
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 登录url
	 * @param ticket      票证
	 * @param index       循环次数
	 * @return 用户信息
	 */
	public static JSONObject getUserInfo(HttpClientConfig httpConfig, String projectHost, String ticket, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> map = new HashMap<>(1);
		map.put("ticket", ticket);
		String result=null;
		String url=projectHost.concat("GetUserInfo.ashx");
		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(map));
			if (StringTool.isEmpty(result)) {
				log.error("获取用户信息失败");
				Thread.sleep(LONG_SLEEP);
				return getUserInfo(httpConfig, projectHost, ticket, ++index[0]);
			}
			if (StringTool.isContains(result, "504 Gateway Time-out")) {
				log.error("获取用户信息失败，nginx超时");
				log.fatal("页面为=[" + result);
				Thread.sleep(LONG_SLEEP);
				return getUserInfo(httpConfig, projectHost, ticket, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取用户信息失败，错误信息=" + result);
				Thread.sleep(LONG_SLEEP);
				return getUserInfo(httpConfig, projectHost, ticket, ++index[0]);
			}
            //转换为json
            JSONObject json = parseObject(result);
            if (ContainerTool.isEmpty(json)) {
                Thread.sleep(LONG_SLEEP);
                return getUserInfo(httpConfig, projectHost, ticket, ++index[0]);
            }
            return json;
		} catch (Exception e) {
			log.error("获取用户信息失败,结果信息="+result, e);
			Thread.sleep(LONG_SLEEP);
			return getUserInfo(httpConfig, projectHost, ticket, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,map,index[0],result));
        }
	}
	/**
	 * 获取游戏限额，60秒超过15次，锁定60秒
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 接口url
	 * @param ticket      票证
	 * @param gameno      游戏编号code
	 * @param index       循环次数
	 * @return 游戏限额
	 */
	public static JSONObject getQuotaList(HttpClientConfig httpConfig, String projectHost, String ticket, int gameno,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("ticket", ticket);
		join.put("gameno", gameno);
		String result=null;
		String url=projectHost.concat("GetQuotaList.ashx");
		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("获取游戏限额失败");
				Thread.sleep(4 * SLEEP);
				return getQuotaList(httpConfig, projectHost, ticket, gameno, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取游戏限额失败，错误信息=" + result);
				Thread.sleep(4 * SLEEP);
				return getQuotaList(httpConfig, projectHost, ticket, gameno, ++index[0]);
			}
            //转换为json
            JSONObject json = parseObject(result);
            if (ContainerTool.isEmpty(json)) {
                Thread.sleep(LONG_SLEEP);
                return getQuotaList(httpConfig, projectHost, ticket, gameno, ++index[0]);
            }
            return json;
		} catch (Exception e) {
			log.error("获取游戏限额失败,结果信息="+result, e);
			Thread.sleep(4 * SLEEP);
			return getQuotaList(httpConfig, projectHost, ticket, gameno, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,join,index[0],result));
        }
	}
	/**
	 * 投注
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 请求url
	 * @param ticket      票证
	 * @param gameno      游戏编号
	 * @param period      期数
	 * @param wagers      投注内容
	 * @param index       循环次数
	 * @return 投注
	 */
	public static JSONObject betting(HttpClientConfig httpConfig, String projectHost, String ticket, int gameno,
			String period, String wagers, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("ticket", ticket);
		join.put("gameno", gameno);
		join.put("roundno", period);
		join.put("wagers", wagers);
		String result=null;
		String url=projectHost.concat("Betting.ashx");
		try {
			result = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("投注失败");
				Thread.sleep(LONG_SLEEP);
				return betting(httpConfig, projectHost, ticket, gameno, period, wagers, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("投注失败，错误信息=" + result);
				Thread.sleep(LONG_SLEEP);
				return betting(httpConfig, projectHost, ticket, gameno, period, wagers, ++index[0]);
			}
            //转换为json
            JSONObject json = parseObject(result);
            if (ContainerTool.isEmpty(json)) {
                Thread.sleep(LONG_SLEEP);
                return betting(httpConfig, projectHost, ticket, gameno, period, wagers, ++index[0]);
            }
            return json;
		} catch (Exception e) {
			log.error("投注失败,结果信息="+result,e);
			Thread.sleep(LONG_SLEEP);
			return betting(httpConfig, projectHost, ticket, gameno, period, wagers, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,join,index[0],result));
        }
	}
	/**
	 * 获取注单明细。15秒超过8次，锁定15秒
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 请求url
	 * @param ticket      票证
	 * @param games       游戏编号字符串
	 * @param date        统计时间
	 * @param isjs        是否结账数据
	 * @param index       循环次数
	 * @return 注单明细
	 */
	public static JSONObject getBetDetail(HttpClientConfig httpConfig, String projectHost, String ticket, String games,
			String date, String isjs, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(5);
		join.put("ticket", ticket);
		join.put("gamestring", games);
		join.put("data1", date);
		join.put("data2", date);
		join.put("isjs", isjs);
		String url=projectHost.concat("BetDetail.ashx");
        String result=null;
		try {
			result = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(projectHost.concat("BetDetail.ashx")).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("获取注单明细失败");
				Thread.sleep(3 * SLEEP);
				return getBetDetail(httpConfig, projectHost, ticket, games, date, isjs, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取注单明细失败，错误信息=" + result);
				Thread.sleep(3 * SLEEP);
				return getBetDetail(httpConfig, projectHost, ticket, games, date, isjs, ++index[0]);
			}
            //转换为json
            JSONObject json = parseObject(result);
            if (ContainerTool.isEmpty(json)) {
                Thread.sleep(LONG_SLEEP);
                return getBetDetail(httpConfig, projectHost, ticket, games, date, isjs, ++index[0]);
            }
            return json;
		} catch (Exception e) {
			log.error("获取注单明细失败");
			Thread.sleep(3 * SLEEP);
			return getBetDetail(httpConfig, projectHost, ticket, games, date, isjs, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,join,index[0],result));
        }
	}
	/**
	 * 获取期数数据。300秒超过25次，锁定300秒
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 请求url
	 * @param ticket      票证
	 * @param gameno      游戏编号code
	 * @param index       循环次数
	 * @return 期数数据
	 */
	public static JSONObject getPeriodData(HttpClientConfig httpConfig, String projectHost, String ticket, int gameno,
			int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(2);
		join.put("ticket", ticket);
		join.put("gameno", gameno);
		String url=projectHost.concat("GetWagerRoundno.ashx");
        String result=null;
		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("获取期数数据失败");
				Thread.sleep(3 * SLEEP);
				return getPeriodData(httpConfig, projectHost, ticket, gameno, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取期数数据失败，错误信息=" + result);
				Thread.sleep(3 * SLEEP);
				return getPeriodData(httpConfig, projectHost, ticket, gameno, ++index[0]);
			}
            //转换为json
            JSONObject json = parseObject(result);
            if (ContainerTool.isEmpty(json)) {
                Thread.sleep(LONG_SLEEP);
                return getPeriodData(httpConfig, projectHost, ticket, gameno, ++index[0]);
            }
            return json;
		} catch (Exception e) {
			log.error("获取期数数据失败", e);
			Thread.sleep(3 * SLEEP);
			return getPeriodData(httpConfig, projectHost, ticket, gameno, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,join,index[0],result));
        }
	}

	//TODO,功能函数

    /**
     * 将结果转换为json
     *
     * @param html 结果界面
     * @return 结果json
     */
    private static JSONObject parseObject(String html) {
        try {
            return JSONObject.parseObject(html);
        } catch (Exception e) {
            log.error("转换结果页面失败【" + html + "】", e);
        }
        return null;
    }

	/**
	 * 登陆错误
	 *
	 * @param msg 错误信息
	 * @return 错误信息
	 */
	public static HcCodeEnum loginError(String msg) {
		if (StringTool.contains(msg, "帐号与密码不匹配")) {
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
	 * 获取投注项内容
	 *
	 * @param gameCode 游戏code
	 * @param betItems 投注详情
	 * @return 投注项内容
	 */
	public static String getBetItemInfo(GameUtil.Code gameCode, List<String> betItems) {
		Map<String, String> ballCode = BallCodeTool.getBallCode(HandicapUtil.Code.IDC, gameCode);
		StringBuilder betBuilder = new StringBuilder();
		for (String betItem : betItems) {
			String[] items = betItem.split("\\|");
			String bet = items[0].concat("|").concat(items[1]);
			//单注金额须为整数值
			int fund = (int) NumberTool.doubleT(items[2]);
			if (StringTool.isEmpty(ballCode.get(bet))) {
				log.error("错误的投注项" + betItem);
				continue;
			}
			betBuilder.append(ballCode.get(bet)).append(":").append(fund).append(";");
		}
		return betBuilder.toString();
	}
	/**
	 * 获取投注结果
	 *
	 * @param gameCode 游戏code
	 * @param data     投注结果
	 * @return 投注结果
	 */
	public static JSONArray getBetResult(GameUtil.Code gameCode, JSONArray data) {
		Map<String, String> ballInfo = BallCodeTool.getBallInfoCode(HandicapUtil.Code.IDC, gameCode);

		JSONArray betResult = new JSONArray();
        JSONArray betInfo;
		for (int i = 0; i < data.size(); i++) {
			betInfo = new JSONArray();
			JSONObject info = data.getJSONObject(i);
			String item = ballInfo.get(info.getString("wagertypename").concat(info.getString("wagerobject")));
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
	/**
	 * 获取注单明细
	 *
	 * @param gameCode 游戏code
	 * @param data     注单信息
	 * @return 注单明细
	 */
	public static JSONArray getDetailInfo(GameUtil.Code gameCode, JSONArray data) {
		Map<String, String> ballInfo = BallCodeTool.getBallInfoCode(HandicapUtil.Code.IDC, gameCode);

		JSONArray detailInfo = new JSONArray();
		for (int i = 0; i < data.size(); i++) {
			JSONArray array = new JSONArray();
			JSONObject info = data.getJSONObject(i);
			String item = ballInfo.get(info.getString("wagertypename").concat(info.getString("wagerobject")));
			if (StringTool.isEmpty(item)) {
				log.error("错误的投注项：" + info);
				continue;
			}
			//注单号
			array.add(info.get("idno"));
			//期数
			array.add(info.get("roundno"));
			//投注项
			array.add(item);
			//投注金额
			array.add(info.get("stake"));
			//赔率
			array.add(info.get("wagerodds"));
			//盈亏金额
			array.add(info.get("winnings"));
			detailInfo.add(array);
		}
		return detailInfo;
	}
	/**
	 * 获取错误投注项
	 *
	 * @param gameCode 游戏code
	 * @param msg      错误信息
	 * @return 错误投注项
	 */
	public static String getFailBetItem(GameUtil.Code gameCode, String msg) {
		String item = null;
		if (StringTool.isContains(msg, "最低", "最高")) {
			item = msg.substring(1, msg.indexOf(" ")).replace("】", "");
		} else if (StringTool.isContains(msg, "累计")) {
			item = msg.substring(1, msg.indexOf("累")).replace("】", "");
		}
		if (StringTool.isEmpty(item)) {
			return null;
		}
		Map<String, String> ballInfo = BallCodeTool.getBallInfoCode(HandicapUtil.Code.IDC, gameCode);

		return ballInfo.get(item);
	}
	/**
	 * 过滤限额信息
	 *
	 * @param quotaList 游戏限额信息
	 * @return 过滤限额信息
	 */
	public static JSONArray filterQuotaInfo(JSONArray quotaList) {
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
}
