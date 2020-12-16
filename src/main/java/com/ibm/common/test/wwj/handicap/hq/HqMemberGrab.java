package com.ibm.common.test.wwj.handicap.hq;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.test.wwj.handicap.GrabMember;
import com.ibm.common.test.wwj.handicap.HttpType;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 环球会员盘口爬虫抓取类
 * @Author: Dongming
 * @Date: 2019-11-22 15:38
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HqMemberGrab extends AbsHqGrab implements GrabMember {


	@Override public String[] routes(HttpClientConfig httpConfig, String handicapCaptcha, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}

		return routes(httpConfig, httpConfig.url(), handicapCaptcha, "1", index[0]);
	}

	/**
	 * 登录
	 *
	 * @param httpConfig
	 * @param loginUrl   登录地址
	 * @param verifyCode 验证码
	 * @param account    盘口账号
	 * @param password   盘口密码
	 * @return 登录结果
	 */
	@Override
	public String login(HttpClientConfig httpConfig, String loginUrl, String verifyCode, String account, String password) {
		return null;
	}



	/**
	 * 获取协议页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param sessionid   sessionId
	 * @param index       循环次数
	 * @return
	 */
	public String getAcceptAgreement(HttpClientConfig httpConfig, String projectHost, String sessionid,
									 int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html;

		String url = "http://".concat(projectHost).concat(sessionid).concat("/Member/AcceptAgreement");
		try {
			html = html(httpConfig.url(url).method(HttpConfig.Method.POST), HttpType.Normal);
			if (StringTool.isEmpty(html)) {
				log.error("获取协议页面失败");
				sleep("获取协议页面");
				return getAcceptAgreement(httpConfig, projectHost, sessionid, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取协议页面失败");
			sleep("获取协议页面");
			return getAcceptAgreement(httpConfig, projectHost, sessionid, ++index[0]);
		}
		httpConfig.setHeader("Referer",
				"http://".concat(projectHost).concat(sessionid).concat("/All/Agreement.html?s=").concat(sessionid));
		return html;
	}

	/**
	 * 获取index页面
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param sessionid   sessionId
	 * @param index       循环次数
	 * @return
	 */
	public String getIndex(HttpClientConfig httpConfig, String projectHost, String sessionid, int... index){
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html;
		String url = "http://".concat(projectHost).concat(sessionid).concat("/App/Index?_=") + System.currentTimeMillis();

		try {
			html = html(httpConfig.url(url).method(HttpConfig.Method.GET),HttpType.Normal);
			if (StringTool.isEmpty(html)) {
				log.error("获取主页面失败");
				sleep("获取index页面");
				return getIndex(httpConfig, projectHost, sessionid, ++index[0]);
			}
		} catch (Exception e) {
			log.error("获取主页面失败");
			sleep("获取index页面");
			return getIndex(httpConfig, projectHost, sessionid, ++index[0]);
		}
		httpConfig.setHeader("Referer", url);
		return html;
	}

	/**
	 * 获取用户个人信息
	 * @param httpConfig
	 * @param projectHost
	 * @return
	 */
	public JSONObject getUserInfo(HttpClientConfig httpConfig, String projectHost, int... index){
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] >= MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html = null;
		JSONObject data;
		String url = projectHost.concat("Member/GetMemberInfo?lotteryId=&periodId=").concat("&_=") + System.currentTimeMillis();
		try {
			html = html(httpConfig.url(url).method(HttpConfig.Method.GET),HttpType.UserInfo);
			if (StringTool.isEmpty(html)) {
				log.error("获取用户信息失败");
				sleep("获取用户信息");
				return getUserInfo(httpConfig, projectHost, ++index[0]);
			}
			if(StringTool.isContains(html,"系统升级中")){
				log.error("获取用户信息失败",html);
				data=new JSONObject();
				data.put("data","系统升级中");
				return data;
			}
			if(StringTool.contains(html,"Server:","robot7_session_id","document.cookie")){
				log.error("获取用户信息失败",html);
				data=new JSONObject();
				data.put("data","login");
				return data;
			}
			data = JSONObject.parseObject(html).getJSONObject("Data");
		} catch (Exception e) {
			log.error("获取用户信息失败,结果信息="+html, e);
			sleep("获取用户信息");
			return getUserInfo(httpConfig, projectHost, ++index[0]);
		}
		return data;
	}

	/**
	 * 获取游戏限额
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param game        盘口游戏code
	 * @param index       循环次数
	 * @return 游戏限额信息
	 */
	public JSONArray getGameLimit(HttpClientConfig httpConfig, String projectHost, String game, int... index){
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html = null;
		String url = projectHost.concat("Member/GetMemberBetInfo/?lotteryId=").concat(game).concat("&_=") + System
				.currentTimeMillis();
		try {
			html = html(httpConfig.url(url).method(HttpConfig.Method.GET),HttpType.GameLimit);
			if (StringTool.isEmpty(html.trim())) {
				log.error("获取游戏限额失败");
				sleep("获取游戏限额");
				return getGameLimit(httpConfig, projectHost, game, ++index[0]);
			}
			if(StringTool.isContains(html,"系统升级中")){
				log.error("获取游戏限额失败",html);
				JSONArray data=new JSONArray();
				data.add("系统升级中");
				return data;
			}
			if(StringTool.isContains(html,"robot7_session_id","document.cookie")){
				log.error("获取游戏限额失败",html);
				JSONArray data=new JSONArray();
				data.add("login");
				return data;
			}
			return getLimit(html, game);
		} catch (Exception e) {
			log.error("获取游戏限额信息="+html);
			log.error("获取游戏限额失败", e);
			sleep("获取游戏限额");
			return getGameLimit(httpConfig, projectHost, game, ++index[0]);
		}
	}

	/**
	 * 获取赔率信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param m           类别位置
	 * @param marketTypes 赔率code
	 * @param game        盘口游戏code
	 * @param index       循环次数
	 * @return
	 */
	public JSONObject getOddsInfo(HttpClientConfig httpConfig, String projectHost, String m,
								  JSONArray marketTypes, String game, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html=null;
		String url = projectHost.concat("Bet/GetBetPageRefresh/?m=").concat(m).concat("&statNo=0&marketTypes=")
				.concat(marketTypes.toString()).concat("&lotteryId=").concat(game).concat("&_=") + System
				.currentTimeMillis();
		try {

			html = html(httpConfig.url(url).method(HttpConfig.Method.GET),HttpType.OddsInfo);
			if (StringTool.isEmpty(html)) {
				log.error("获取游戏赔率失败");
				sleep("获取游戏赔率");
				return getOddsInfo(httpConfig, projectHost, m, marketTypes, game, ++index[0]);
			}
			if(StringTool.isContains(html,"robot7_session_id","document.cookie")){
				log.error("获取游戏赔率失败",html);
				return null;
			}
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取游戏赔率失败,结果信息="+html, e);
			sleep("获取游戏赔率");
			return getOddsInfo(httpConfig, projectHost, m, marketTypes, game, ++index[0]);
		}
	}

	/**
	 * 投注
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param betParams   投注参数
	 * @param game        盘口游戏code
	 * @return 投注结果
	 */
	public JSONObject betting(HttpClientConfig httpConfig, String projectHost, JSONObject betParams,
							  String game) {
		httpConfig.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		httpConfig.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");

		Map<String, Object> map = new HashMap<>(2);
		map.put("betParams", betParams.toString());
		map.put("lotteryId", Integer.parseInt(game));

		String html=null;
		String url=projectHost.concat("Bet/DoBet/");
		try {
			html = html(httpConfig.url(url).map(map).method(HttpConfig.Method.POST),HttpType.Betting);
			if (ContainerTool.isEmpty(html)||StringTool.contains(html,"系统升级中","robot7_session_id")) {
				log.error("投注失败,结果="+html+",投注项为：" + betParams);
				return null;
			}
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("投注结果页面为空,结果信息="+html, e);
			return null;
		}
	}

	/**
	 * 获取投注成功项
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param game        盘口游戏code
	 * @param index       循环次数
	 * @return
	 */
	public JSONObject getBettingResult(HttpClientConfig httpConfig, String projectHost, String game,
									   int... index){
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String html=null;

		String url = projectHost.concat("Bet/GetBettingSuccess/?lotteryId=").concat(game).concat("&_=") + System
				.currentTimeMillis();
		try {
			html = html(httpConfig.url(url).method(HttpConfig.Method.GET),HttpType.Betting);
			if (StringTool.isEmpty(html)) {
				log.error("获取投注成功项失败");
				sleep("获取投注成功项");
				return getBettingResult(httpConfig, projectHost, game, ++index[0]);
			}
			if(StringTool.isContains(html,"系统升级中")){
				log.error("获取投注成功项失败",html);
				JSONObject data=new JSONObject();
				data.put("data","系统升级中");
				return data;
			}
			if(StringTool.isContains(html,"robot7_session_id","document.cookie")){
				log.error("获取投注成功项失败",html);
				JSONObject data=new JSONObject();
				data.put("data","login");
				return data;
			}
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("获取投注成功项信息=", html);
			log.error("获取投注成功项失败", e);
			sleep("获取投注成功项");
			return getBettingResult(httpConfig, projectHost, game, ++index[0]);
		}
	}


}
