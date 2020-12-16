package com.ibm.common.test.zjj.handicap.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.HqConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.member.HqMemberTool;
import com.ibm.common.utils.http.utils.RSAUtils;
import com.ibm.common.utils.http.utils.member.HqMemberUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
/**
 * @Description: HQ测试类
 * @Author: null
 * @Date: 2019-10-28 16:35
 * @Version: v1.0
 */
public class HqMemberTest {

	@Test
	public void test03(){

		List<String> marketTypes = getMarketTypes(GameUtil.Code.PK10,"ballNO");
		System.out.println(marketTypes);

		JSONArray array=JSONArray.parseArray(String.valueOf(marketTypes));

		System.out.println(array);
	}

	@Test
	public void test02(){
		String id = "123";

		String handicapUrl = "http://w1.sc3588.co/";
		String handicapCaptcha = "99812a";
		String memberAccount = "gbd0010";
		String memberPassword = "Aa321321.";

//		String period="740596";
		GameUtil.Code gameCode= GameUtil.Code.XYFT;
		Object  period = gameCode.getGameFactory().period(HandicapUtil.Code.HQ).findPeriod();

		String[] betItem = {"第一名|6|5000", "第一名|7|5000", "第一名|8|5000"};
		List<String> betItems=new ArrayList<>(Arrays.asList(betItem));

		HqMemberUtil hqMemberUtil=HqMemberUtil.findInstance();

		JsonResultBeanPlus bean=hqMemberUtil.login(id,memberAccount,memberPassword,handicapUrl,handicapCaptcha);
		System.out.println("登录结果="+bean.toJsonString());

		String roundno = PeriodUtil.getHandicapGamePeriod(HandicapUtil.Code.HQ, gameCode, period);
		String game = HqConfig.BET_CODE.get(gameCode.name());

		//盘口参数m
		String m = HqConfig.BET_TYPE_CODE.get(gameCode.name().concat("_").concat("ballNO"));

		List<String> marketTypes = getMarketTypes(gameCode,"ballNO");
        hqMemberUtil.getUserInfo(id,null,null,false);


	}


	@Test public void test06(){
	    String str = "11<!DOCTYPE HTML><html><head><meta charset=utf-8><script id='robot7_session_id'>document.cookie='robot7=bvskNp/MoFoxVB5dDGK6h1YNyDIh2fGu+/ClQNbx5sh1NkwkM4PyTI0h4kwg/nkIM8L8JzhSQl4qrChxfBMXVw==; path=/;';if (document.cookie.indexOf('robot7')>-1){window.location.reload();} else {alert('您当前使用的浏览器不支持cookie，无法使用本系统，请检查浏览器设置！');}</script></head></html>";
        String cookie = StringUtils.substringBetween(str,"document.cookie='","'");
//        System.out.println(cookie);

        String obj="{\"Status\":1,\"Data\":{\"CompanyName\":\"AG\",\"IsPaused\":0,\"Credit\":333.00000,\"Balance\":318.30000,\"BetMoney\":0.0,\"BetData\":{\"BettingResult\":[]}}}";
        JSONObject object=JSONObject.parseObject(obj);

        JSONObject userObj=object.getJSONObject("Data");

        Map<String,Object> userInfo=new HashMap<>();
        //信用额度
        userInfo.put("creditQuota", String.valueOf(userObj.getDouble("Credit")));
        //可用额度
        userInfo.put("usedQuota", String.valueOf(userObj.getDouble("Balance")));
        //使用金额
        userInfo.put("usedAmount", String.valueOf(userObj.getDouble("BetMoney")));
        //盈亏金额
        double dou= userObj.getDouble("Balance") + userObj.getDouble("BetMoney") - userObj.getDouble("Credit");
//        dou = (double) Math.round(dou * 100) / 100;
        userInfo.put("profitAmount",String.format("%.1f", userObj.getDouble("Balance") + userObj.getDouble("BetMoney") - userObj.getDouble("Credit")));
        System.out.println(userInfo);


	}

	/**
	 * 获取赔率code
	 *
	 * @param gameCode     游戏编码
	 * @param handicapType 盘口类型
	 * @return
	 */
	private List<String> getMarketTypes(GameUtil.Code gameCode, String handicapType) {
		switch (gameCode) {
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
			default:
				return null;
		}
	}

	@Test
	public void test() throws Exception {
		String id = "123";

		String handicapUrl = "http://sc3388.co";
		String handicapCaptcha = "hzz828";
		String memberAccount = "qq903903h2";
		String memberPassword = "Aa123123";

		String[] betItems = {"第一名|6|20000", "第一名|7|20000", "第一名|8|20000"};
		List<String> betItem=new ArrayList<>(Arrays.asList(betItems));

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		//获取会员登入href
		String href = HqMemberTool.getMemberHref(httpConfig,handicapUrl,handicapCaptcha);
		if (StringTool.isEmpty(href)) {
			return ;
		}
		//获取线路
		String[] routes = HqMemberTool.getSelectRoutePage(httpConfig, href, handicapUrl);
		if (ContainerTool.isEmpty(routes)) {
			return ;
		}
		//获取登录页面
		Map<String, String> loginInfo =HqMemberTool.getLoginHtml(httpConfig, routes, handicapCaptcha);
		if (ContainerTool.isEmpty(loginInfo)) {
			return ;
		}

		Map<String, String> scriptInfo =HqMemberTool.getScriptInfo(loginInfo.get("html"));
		if (ContainerTool.isEmpty(scriptInfo)) {
			return;
		}
		String projectHost = loginInfo.get("hostUrl");

		//加密key
		JSONObject encryptKey =HqMemberTool.getEncryptKey(httpConfig, scriptInfo.get("SESSIONID"), projectHost);
		if (ContainerTool.isEmpty(encryptKey)) {
			return;
		}
		String pke = encryptKey.getString("e");
		String pk = encryptKey.getString("m");
		String logpk = pke.concat(",").concat(pk);

		RSAPublicKey pubKey = RSAUtils
				.getPublicKey(new BigInteger(pk, 16).toString(), Integer.parseInt(pke, 16) + "");

		String account = URLEncoder.encode(memberAccount.trim(), "UTF-8").concat(",")
				.concat(URLEncoder.encode(memberPassword.trim(), "UTF-8"));

		String mi = RSAUtils.encryptByPublicKey(account, pubKey);

		//登录
		String loginHtml =HqMemberTool.getLogin(httpConfig, projectHost, scriptInfo.get("SESSIONID"), handicapCaptcha, mi, logpk);

		if (StringTool.isEmpty(loginHtml)) {
			return ;
		}
		//TODO 获取盘口游戏code
		String game = "2";
		//开奖期数
		String periodId="";

		//协议页面
		String agreement = HqMemberTool.getAcceptAgreement(httpConfig, projectHost, scriptInfo.get("SESSIONID"));
		if (StringTool.isEmpty(agreement)) {
			return ;
		}
		JSONObject agreementResult = JSONObject.parseObject(agreement);
		if (agreementResult.getInteger("Status") == 1) {
			//需要修改密码
			if (agreementResult.getInteger("Data") == 1 || agreementResult.getInteger("Data") == 3) {
				return ;
			}
		}
		String index = HqMemberTool.getIndex(httpConfig, projectHost, scriptInfo.get("SESSIONID"));
		Map<String, String> data = new HashMap<>(1);
		data.put("projectHost", "http://".concat(projectHost).concat(scriptInfo.get("SESSIONID")).concat("/"));

		//获取盘口会员信息
		JSONObject userInfo=HqMemberTool.getUserInfo(httpConfig,data.get("projectHost"),game,periodId);



		//获取游戏限额
		JSONArray gameLimit=HqMemberTool.getQuotaList(httpConfig,data.get("projectHost"), game);

		System.out.println(gameLimit);


	}



}
