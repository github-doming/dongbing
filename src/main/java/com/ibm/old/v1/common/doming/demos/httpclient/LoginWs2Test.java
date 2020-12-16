package com.ibm.old.v1.common.doming.demos.httpclient;

import com.ibm.old.v1.common.doming.configs.Ws2Config;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.junit.Test;

import java.util.List;
import java.util.Map;


/**
 * @Description:
 * @Date 2018年06月13日 14:21
 * @Author Dongming
 * @Email: job.dongming@foxmail.com
 **/
public class LoginWs2Test {

	private static final String MAIN_URL = "http://sf33.qr68.us/";
	private static final String VALI_CODE = "az311";

	private static final String USERNAME = "cs7654322";
	private static final String PASSWORD = "Cs987we12..";

	/**
	 * pk10
	 */
	private static final Integer GAME_INDEX = 2;

	private static final String[][] BET_ITEM = {{"冠军|大", "10"}, {"第三名|虎", "30"}};

	private static HttpClientConfig HTTP_CONFIG;

	static {
		HTTP_CONFIG = new HttpClientConfig();
		HTTP_CONFIG.httpContext(HttpClientContext.create());
	}

	@Test public void testLogin() {

		String gameCode = "PK10";
		try {
			//主界面
			String selectRoutePage = HttpClientWs2Util.getSelectRoutePage(HTTP_CONFIG, MAIN_URL, VALI_CODE);
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t主界面打开" + HttpClientWs2Util.resultBean().msg()
							+ "\tmainUrl：[" + MAIN_URL + "]\t" + HttpClientWs2Util.resultBean().message());

			//获取线路
			List<String> routes = HttpClientWs2Util.findRoutes4Login(selectRoutePage);
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t获取线路信息" + HttpClientWs2Util.resultBean().msg()
							+ "\troutes：[" + routes.size() + "]条线路\t" + HttpClientWs2Util.resultBean().message());

			//放入验证条件
			HTTP_CONFIG.setHeader("Referer", MAIN_URL);

			//登陆界面
			HttpResult loginPage = HttpClientWs2Util.getLoginPage(HTTP_CONFIG, routes);
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t登陆界面打开" + HttpClientWs2Util.resultBean().msg()
							+ "\tloginUrl:[" + loginPage.getData() + "]\t" + HttpClientWs2Util.resultBean().message());

			//登陆界面需要解析信息
			Map<String, Object> loginDateMap = HttpClientWs2Util.findLoginDateMap4LoginPage(loginPage);
			System.out.println("花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t登陆界面script信息解析"
					+ HttpClientWs2Util.resultBean().msg() + "\tscriptDateMap：[" + loginDateMap + "]\t"
					+ HttpClientWs2Util.resultBean().message());


			/*
			 * 获取主机地址
			 * eg.
			 * 	http://pc4.1ll11.com
			 */
			String hostUrl = StringTool.getHttpHost((String) loginPage.getData());

			//获取图片验证码地址
			String valiImgUrl = HttpClientWs2Util.getValiImageUrl(hostUrl, loginDateMap);
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t图片验证码地址解析" + HttpClientWs2Util.resultBean()
							.msg() + "\tvaliImgUrl：[" + valiImgUrl + "]\t" + HttpClientWs2Util.resultBean().message());

			//识别图片验证码
			String verifyCode = HttpClientWs2Util.getVerifyCode(valiImgUrl);
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t验证识别" + HttpClientWs2Util.resultBean().msg()
							+ "\tverifyCode：[" + verifyCode + "]\t" + HttpClientWs2Util.resultBean().message());

			//获取拼装后用户登陆地址
			String toLoginUrl = HttpClientWs2Util.findUrl2Login(hostUrl, loginDateMap, USERNAME, PASSWORD, verifyCode);
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t登陆地址拼装" + HttpClientWs2Util.resultBean().msg()
							+ "\ttoLoginUrl：[" + toLoginUrl + "]\t" + HttpClientWs2Util.resultBean().message());

			//放入验证条件
			HTTP_CONFIG.setHeader("Referer", (String) loginPage.getData());

			//存储cookie
			HTTP_CONFIG.httpContext(HttpClientContext.create());

			//登陆结果
			HttpResult loginResultMap = HttpClientWs2Util.getLoginResult(HTTP_CONFIG, toLoginUrl);
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t登陆" + HttpClientWs2Util.resultBean().msg()
							+ "\t登陆结果页面状态为：[" + loginResultMap.getStatusCode() + "]\t" + HttpClientWs2Util.resultBean()
							.message());

			/*
			 * 获取用户专属code
			 * eg.
			 * 	/sscas8137262f_4964/
			 */
			String userCode = HttpClientWs2Util.getUserCode((String) loginResultMap.getHtml());
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t获取" + HttpClientWs2Util.resultBean().msg()
							+ "\t用户专属code：[" + userCode + "]\t" + "\t" + HttpClientWs2Util.resultBean().message());

			//更新定向地址
			HTTP_CONFIG.setHeader("Referer", hostUrl + userCode + ".auth");

			//响应登陆信息结果，获取登入页面信息
			HttpClientWs2Util.getKPage(HTTP_CONFIG, hostUrl, loginResultMap);
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t响应登陆信息" + HttpClientWs2Util.resultBean().msg()
							+ HttpClientWs2Util.resultBean().message());

			//更新定向地址
			HTTP_CONFIG.setHeader("Referer", hostUrl + userCode + "index.htm?2018-1122-1400");

			// 获取用户信息
			JSONObject userInfo = HttpClientWs2Util
					.getUserInfo(HTTP_CONFIG, hostUrl, userCode, Ws2Config.getGameCode(gameCode));
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t获取用户信息" + HttpClientWs2Util.resultBean().msg()
							+ "\t用户信息：[" + userInfo + "]\t" + HttpClientWs2Util.resultBean().message());

			//  获取投注赔率基础信息
			JSONObject betInfo = HttpClientWs2Util
					.getGameBetInfo(HTTP_CONFIG, hostUrl, userCode, Ws2Config.getGameCode(gameCode),
							Ws2Config.BET_INFO_CODE.get(Ws2Config.getGameCode(gameCode)));
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t获取投注基础信息" + HttpClientWs2Util.resultBean()
							.msg() + "\t投注基础信息：[" + betInfo + "]\t" + HttpClientWs2Util.resultBean().message());

			JSONObject userObj = userInfo.getJSONObject("data").getJSONObject("user");
			System.out.println("账户名称=" + userObj.getString("account"));
			System.out.println("账户余额=" + userObj.getString("re_credit"));
			System.out.println("已下金额=" + userObj.getString("total_amount"));

			JSONObject betNotice = betInfo.getJSONObject("data").getJSONObject("betnotice");
			System.out.println("当前期数=" + betNotice.getString("timesnow"));
			System.out.println("开奖时间=" + betNotice.getString("timeopen"));
			System.out.println("封盘时间=" + betNotice.getString("timeclose"));


			//游戏是否封盘
			int timeclose = betNotice.getInt("timeclose");
			if(timeclose < 0){
				throw new Exception("游戏已封盘");
			}

			//获取投注列表
			JSONArray betItems = HttpClientWs2Util
					.getBetItems(Ws2Config.BALL_BET_CODE, BET_ITEM, betInfo.getJSONObject("data").getJSONObject("integrate"));

			//获取投注验证码
			String versionNumber = betInfo.getJSONObject("data").getString("version_number");

			//投注
			JSONObject betResult = HttpClientWs2Util
					.bettingPk10(Ws2Config.BALL_BET_CODE, HTTP_CONFIG, hostUrl, userCode,Ws2Config.getGameCode(gameCode), betItems,
							versionNumber);
			System.out.println(
					"花费时长\ttime=" + HttpClientWs2Util.resultBean().useTime() + "\t投注" + HttpClientWs2Util.resultBean()
							.msg() + "\t投注结果：[" + betResult + "]\t" + HttpClientWs2Util.resultBean().message());



		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}
