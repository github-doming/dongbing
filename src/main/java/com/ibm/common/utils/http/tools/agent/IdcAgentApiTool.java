package com.ibm.common.utils.http.tools.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.HcCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.BallCodeTool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: IDC新盘口代理工具类
 * @Author: zjj
 * @Date: 2019-09-16 15:42
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IdcAgentApiTool {
    private static final String LOG_FORMAT = "线程{%s}，请求地址[%s]，请求参数[%s]，循环次数[%d]，请求结果：【%s】";

	protected static final Logger log = LogManager.getLogger(IdcAgentApiTool.class);

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
			result = HttpClientUtil.findInstance()
					.postHtml(httpConfig.url(handicapUrl.concat("/get_bet_url.ashx")).map(map));
			if (StringTool.isEmpty(result)) {
				log.error("获取登录url失败");
				Thread.sleep(SLEEP);
				return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if(StringTool.isContains(result,"Gateway Time-out")){
				log.error("获取登录url失败，网关超时，页面为="+result);
				Thread.sleep(SLEEP);
				return getLoginUrl(httpConfig, handicapUrl, handicapCaptcha, ++index[0]);
			}
			if(StringTool.isContains(result,"百度")){
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
				log.error("获取登录url失败，网关超时");
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
			log.error("获取票证失败", e);
			Thread.sleep(LONG_SLEEP);
			return getLoginTicket(httpConfig, loginUrl, parameters, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),loginUrl,parameters,index[0],result));
        }
	}
	/**
	 * 获取会员列表信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 接口url
	 * @param ticket      票证
	 * @param index       循环次数
	 * @return 会员列表信息
	 */
	public static JSONObject getMemberList(HttpClientConfig httpConfig, String projectHost, String ticket, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
        Map<String, Object> join = new HashMap<>(5);
        join.put("ticket", ticket);
        //代理帐号（登入帐号的下线代理帐号），为“”为登入帐号（代理）
        join.put("agentno", "");
        //过滤帐号类型:“”:所有 1.代理 2.会员
        join.put("mbtype", "");
        //过虑在线状态:“”:全部 1.在线 0.不在线
        join.put("online", "");
        //0.详细完整多栏位 1.只有帐号栏位（帐号编号）可用于查询更新在线会员状态
        join.put("returntype", 0);
        String url=projectHost.concat("GetMemberList.ashx");
        String result=null;
        try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("获取会员列表信息失败");
				Thread.sleep(LONG_SLEEP);
				return getMemberList(httpConfig, projectHost, ticket, ++index[0]);
			}
			if(StringTool.isContains(result,"Gateway Time-out")){
				log.error("获取会员列表信息失败，网关超时");
				log.fatal("页面为="+result);
				Thread.sleep(SLEEP);
				return getMemberList(httpConfig, projectHost, ticket, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取登录url失败，错误信息=" + result);
				Thread.sleep(LONG_SLEEP);
				return getMemberList(httpConfig, projectHost, ticket, ++index[0]);
			}
			//转换为json
			JSONObject json = parseObject(result);
			if (ContainerTool.isEmpty(json)) {
				Thread.sleep(LONG_SLEEP);
				return getMemberList(httpConfig, projectHost, ticket, ++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("获取会员列表信息失败", e);
			Thread.sleep(LONG_SLEEP);
			return getMemberList(httpConfig, projectHost, ticket, ++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,join,index[0],result));
        }
	}
	/**
	 * 获取当前期数未结明细
	 *
	 * @param httpConfig    http请求配置类
	 * @param projectHost   接口url
	 * @param ticket        票证
	 * @param agentAccount  代理账号
	 * @param memberlist    过滤会员列表
	 * @param game          游戏类别code
	 * @param lastOddNumber 最后一笔注单号
	 * @param index         循环次数
	 * @return 未结明细
	 */
	public static JSONObject getUnsettledDetailed(HttpClientConfig httpConfig, String projectHost, String ticket,
			String agentAccount, String memberlist, String game, String lastOddNumber, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
        Map<String, Object> join = new HashMap<>(6);
        join.put("ticket", ticket);
        //会员(代理)编号
        join.put("memberno", agentAccount);
        //过滤多个会员
        join.put("memberlist", memberlist);
        //游戏类别(;号隔开)
        join.put("gamestring", game);
        //类型(0:全部 2:会员明细 3:下线收补 4:自己补牌)
        join.put("mtype", 0);
        //注单编号，第一次传0，之后每次请求传上一次返回结果最后一笔的idno
        join.put("idno", lastOddNumber);
        String url=projectHost.concat("UnsettledDetailed.ashx");
        String result=null;
		try {
			result = HttpClientUtil.findInstance().postHtml(httpConfig.url(url).map(join));
			if (StringTool.isEmpty(result)) {
				log.error("获取未结明细失败");
				Thread.sleep(LONG_SLEEP);
				return getUnsettledDetailed(httpConfig, projectHost, ticket, agentAccount, memberlist, game,
						lastOddNumber, ++index[0]);
			}
			if(StringTool.isContains(result,"Gateway Time-out")){
				log.error("获取当前期数未结明细失败，网关超时");
				log.fatal("页面为="+result);
				Thread.sleep(SLEEP);
				return getUnsettledDetailed(httpConfig, projectHost, ticket, agentAccount, memberlist, game,
						lastOddNumber, ++index[0]);
			}
			if (StringTool.isContains(result, "http://help.yunaq.com/faq/110")) {
				log.error("获取未结明细失败，DNS错误");
				log.fatal("页面为=[" + result);
				Thread.sleep(LONG_SLEEP);
				return getUnsettledDetailed(httpConfig, projectHost, ticket, agentAccount, memberlist, game,
						lastOddNumber, ++index[0]);
			}
			if (!StringTool.isContains(result, "code", "msg")) {
				log.error("获取未结明细失败，错误信息=" + result);
				Thread.sleep(LONG_SLEEP);
				return getUnsettledDetailed(httpConfig, projectHost, ticket, agentAccount, memberlist, game,
						lastOddNumber, ++index[0]);
			}
			//转换为json
			JSONObject json = parseObject(result);
			if (ContainerTool.isEmpty(json)) {
				Thread.sleep(LONG_SLEEP);
				return getUnsettledDetailed(httpConfig, projectHost, ticket, agentAccount, memberlist, game,
						lastOddNumber, ++index[0]);
			}
			return json;
		} catch (Exception e) {
			log.error("获取未结明细失败", e);
			Thread.sleep(LONG_SLEEP);
			return getUnsettledDetailed(httpConfig, projectHost, ticket, agentAccount, memberlist, game, lastOddNumber,
					++index[0]);
		}finally {
            log.trace(String.format(LOG_FORMAT,Thread.currentThread().getName(),url,join,index[0],result));
        }
	}

	/**
	 * 获取游戏列表
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 接口url
	 * @param ticket      票证
	 * @param index       循环次数
	 * @return 游戏列表
	 */
	public static JSONObject getGameList(HttpClientConfig httpConfig, String projectHost, String ticket, int... index)
			throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		Map<String, Object> join = new HashMap<>(6);
		join.put("ticket", ticket);

		String result = HttpClientUtil.findInstance()
				.postHtml(httpConfig.url(projectHost.concat("gamelist.ashx")).map(join));
		//转换为json
		JSONObject json = parseObject(result);
		if (ContainerTool.isEmpty(json)) {
			Thread.sleep(LONG_SLEEP);
			return getGameList(httpConfig, projectHost, ticket, ++index[0]);
		}
		return json;
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
	 * @return 登录错误
	 */
	public static HcCodeEnum loginError(String msg) {
		if (StringTool.contains(msg, "帐号与密码不匹配")) {
			return HcCodeEnum.IBS_403_USER_ACCOUNT;
		} else if (StringTool.contains(msg, "帐号被锁定","帐号已禁用")) {
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
	 * 获取会员详情
	 *
	 * @param data 会员列表信息
	 * @return 会员详情
	 */
	public static JSONArray getMemberDetailed(JSONArray data) {
		JSONArray array = new JSONArray();

		for (int i = 0; i < data.size(); i++) {
			JSONArray member = new JSONArray();
			JSONArray memberInfo = data.getJSONArray(i);
			member.add(memberInfo.getString(1));
			member.add(memberInfo.getInteger(4) == 1 ? "online" : "offline");

			array.add(member);
		}
		return array;
	}

	/**
	 * 获取注单详情
	 *
	 * @param gameCode 游戏code
	 * @param data     注单列表信息
	 * @return 注单详情
	 */
	public static int getUnsettledDetailedInfo(GameUtil.Code gameCode, JSONArray data,String agentAccount,JSONArray unsettledDetailed) {
		Map<String, String> ballItem = BallCodeTool.getBallItem(HandicapUtil.Code.IDC, gameCode);

		String gameType=BallCodeTool.getGameType(HandicapUtil.Code.IDC,gameCode,null);

		int oddNumberMax=0;
		for (int i = 0; i < data.size(); i++) {
			JSONArray info = data.getJSONArray(i);
			if(oddNumberMax<info.getInteger(0)){
				oddNumberMax=info.getInteger(0);
			}
			//判断游戏类型
			if(!gameType.equals(info.getString(3))){
				continue;
			}
			//只获取直属会员的投注信息
			if(!agentAccount.equals(info.getString(1))){
				continue;
			}
			String ballCode = String.valueOf(info.getInteger(7)).concat(":").concat(info.getString(8));
			String betItem = ballItem.get(ballCode);
			if (StringTool.isEmpty(betItem)) {
				log.error("错误的投注信息" + info);
				continue;
			}
			JSONObject object = new JSONObject();
			object.put("oddNumber", info.getInteger(0));
			object.put("memberAccount", info.getString(2));
			object.put("betItem", betItem);
			object.put("funds", info.getDouble(10));
			object.put("rno", info.get(4));
			unsettledDetailed.add(object);
		}
		return oddNumberMax;
	}
}
