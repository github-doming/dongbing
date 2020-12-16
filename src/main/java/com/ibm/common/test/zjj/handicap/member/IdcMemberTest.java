package com.ibm.common.test.zjj.handicap.member;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IdcConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.member.IdcMemberApiTool;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.Md5Tool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
/**
 * @Description: IDC会员测试类
 * @Author: zjj
 * @Date: 2019-09-16 16:09
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IdcMemberTest {
	@Test
	public void test(){
		String id = "123";

		String memberAccount = "chzz123";
		String memberPassword = "qwqw1212.";
		String handicapUrl = "http://88w.sfg666.com";
		String handicapCaptcha = "90909";
		GameUtil.Code gameCode = GameUtil.Code.PK10;
		String[] betItems = {"第一名|大|20000", "第一名|小|20000", "第一名|8|20000"};
		List<String> betItem=new ArrayList<>(Arrays.asList(betItems));
		HandicapUtil.Code handicapCode=HandicapUtil.Code.IDC;
		Object period=GameUtil.Code.PK10.getGameFactory().period(HandicapUtil.Code.IDC).findPeriod();


		JsonResultBeanPlus bean=handicapCode.getMemberFactory().login(id,handicapUrl,handicapCaptcha,memberAccount,memberPassword);
		System.out.println("登录结果="+bean.toJsonString());

		Object info =handicapCode.getMemberFactory().memberOption(id).userInfo(true);
		System.out.println("用户信息="+info);
		//获取期数字符串
		String roundno = PeriodUtil.getHandicapGamePeriod(handicapCode, gameCode, period);
		//投注
		List<String> errorInfo = new ArrayList<>();
		bean = handicapCode.getMemberFactory().memberOption(id).betting(gameCode, roundno,null, betItem, errorInfo);
		System.out.println("投注结果="+bean.toJsonString());


	}

	@Test public void test05() throws Exception {
		String id = "123";

		String memberAccount = "hcdbt001";
		String memberPassword = "asas1212";
		String handicapUrl = "http://22u.sx699.com";
		String handicapCaptcha = "79912";

		//授权码
		String extwagerno = "BYZN";
		String extwagerkey = "EF64586B3AF643FD9A8F365086C8FC0A";
		String game = GameUtil.Code.PK10.name();
		String[] betItems = {"第一名|6|20000", "第一名|7|20000", "第一名|8|20000"};
		List<String> betItem=new ArrayList<>(Arrays.asList(betItems));

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		JSONObject loginHtml = IdcMemberApiTool.getLoginUrl(httpConfig, handicapUrl, handicapCaptcha);

		if (ContainerTool.isEmpty(loginHtml)) {
			return;
		}
		System.out.println("获取登录url:" + loginHtml);
		String loginUrl = loginHtml.getString("login_url");

		String sign = String
				.format("accounts=%s&pwd=%s&extwagerno=%s&extwagerkey=%s", memberAccount.trim(), memberPassword.trim(),
						extwagerno, extwagerkey);

		sign = Md5Tool.md5Hex(sign);

		String data = String
				.format("&accounts=%s&pwd=%s&extwagerno=%s&sign=%s", memberAccount.trim(), memberPassword.trim(),
						extwagerno, sign);

		JSONObject loginInfo = IdcMemberApiTool.getLoginTicket(httpConfig, loginUrl, data);

		if (ContainerTool.isEmpty(loginInfo)) {
			return;
		}
		System.out.println("登录结果" + loginInfo);

		String projectHost = loginInfo.getString("api_url");

		String ticket = loginInfo.getString("ticket");

		//获取用户信息
		JSONObject userInfo = IdcMemberApiTool.getUserInfo(httpConfig, projectHost, ticket);

		System.out.println("用户信息：" + userInfo);

		Object period=GameUtil.Code.PK10.getGameFactory().period(HandicapUtil.Code.IDC).findPeriod();

		System.out.println("期数：" + period);

		String wagers=IdcMemberApiTool.getBetItemInfo(GameUtil.Code.PK10,betItem);

		JSONObject bettingInfo = IdcMemberApiTool
				.betting(httpConfig, projectHost, ticket, Integer.parseInt(IdcConfig.BET_CODE.get(game)),
						period.toString(), wagers);

		System.out.println("投注结果："+bettingInfo);

	}


	@Test public void test01() throws Exception {
		String id = "123";

		String memberAccount = "hcdbt001";
		String memberPassword = "asas1212";
		String handicapUrl = "http://22u.sx699.com";
		String handicapCaptcha = "79912";
		//授权码
		String extwagerno = "BYZN";
		String extwagerkey = "EF64586B3AF643FD9A8F365086C8FC0A";
		String game = GameUtil.Code.PK10.name();
		String betItem = "第一名|大";
		String[] betItems = {"第一名|大#10", "第一名|大#21", "第一名|1#8", "第一名|2#5", "第三名|虎#5"};

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		JSONObject loginHtml = IdcMemberApiTool.getLoginUrl(httpConfig, handicapUrl, handicapCaptcha);

		if (ContainerTool.isEmpty(loginHtml)) {
			return;
		}
		System.out.println("获取登录url:" + loginHtml);
		String loginUrl = loginHtml.getString("login_url");

		String sign = String
				.format("accounts=%s&pwd=%s&extwagerno=%s&extwagerkey=%s", memberAccount.trim(), memberPassword.trim(),
						extwagerno, extwagerkey);

		sign = Md5Tool.md5Hex(sign);

		String data = String
				.format("&accounts=%s&pwd=%s&extwagerno=%s&sign=%s", memberAccount.trim(), memberPassword.trim(),
						extwagerno, sign);

		JSONObject loginInfo = IdcMemberApiTool.getLoginTicket(httpConfig, loginUrl, data);

		if (ContainerTool.isEmpty(loginInfo)) {
			return;
		}

		System.out.println("登录结果" + loginInfo);

		String projectHost = loginInfo.getString("api_url");

		String ticket = loginInfo.getString("ticket");

		//获取用户信息
		JSONObject userInfo = IdcMemberApiTool.getUserInfo(httpConfig, projectHost, ticket);

		System.out.println("用户信息：" + userInfo);

		//		JSONObject gameList = IdcNewMemberTool.getGameList(httpConfig, projectHost, ticket);

		//		System.out.println("游戏列表:" + gameList);

		//		JSONObject quotaList = IdcNewMemberTool
		//				.getQuotaList(httpConfig, projectHost, ticket, Integer.parseInt(IdcConfig.BET_CODE.get(game)));

		//		System.out.println("游戏限额：" + quotaList);

		//		StringBuilder betBuilder = new StringBuilder();
		//		for(String item:betItems){
		//			String[] items=item.split("#");
		//			if(StringTool.isEmpty(IdcBallConfig.BALL_CODE.get(items[0]))){
		//				break;
		//			}
		//			betBuilder.append(IdcBallConfig.BALL_CODE.get(items[0])).append(":").append(items[1]).append(";");
		//		}
		Object period=GameUtil.Code.PK10.getGameFactory().period(HandicapUtil.Code.IDC).findPeriod();

		System.out.println("期数：" + period);

		//		JSONObject bettingInfo = IdcNewMemberTool
		//				.betting(httpConfig, projectHost, ticket, Integer.parseInt(IdcConfig.BET_CODE.get(game)), period.toString(),
		//						betBuilder.toString());
		//
		//		System.out.println(bettingInfo);
		String date = DateTool.getDate(new Date());
		String isjs = "0";

		JSONObject betDetail = IdcMemberApiTool
				.getBetDetail(httpConfig, projectHost, ticket, IdcConfig.BET_CODE.get(game), date, isjs);

		System.out.println("注单明细：" + betDetail);
	}
	@Test public void test03() throws Exception {
		Object period=GameUtil.Code.PK10.getGameFactory().period(HandicapUtil.Code.IDC).findPeriod();
		System.out.println(period);
		String date = DateTool.getDate(new Date());
	}
}
