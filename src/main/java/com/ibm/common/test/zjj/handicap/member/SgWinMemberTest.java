package com.ibm.common.test.zjj.handicap.member;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.SgWinConfig;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.member.SgWinMemberTool;
import com.ibm.common.utils.http.utils.member.SgWinMemberUtil;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * @Description: 138会员测试类
 * @Author: null
 * @Date: 2019-10-25 10:12
 * @Version: v1.0
 */
public class SgWinMemberTest {

    @Test
    public void test03(){
        String id = "123";

        String memberAccount = "rjbet01";
        String memberPassword = "Aa321321..";
        String handicapUrl = "http://fk1849.com/";
        String handicapCaptcha = "228888";

        SgWinMemberUtil sgWinMemberUtil=SgWinMemberUtil.findInstance();

        JsonResultBeanPlus bean=sgWinMemberUtil.login(id,memberAccount,memberPassword,handicapUrl,handicapCaptcha);
        System.out.println("登录结果="+bean.toJsonString());


    }
	@Test
	public void test02(){
		String id = "123";

		String memberAccount = "rjbet01";
		String memberPassword = "Aa321321.";
		String handicapUrl = "http://fk1849.com/";
		String handicapCaptcha = "228888";


		GameUtil.Code gameCode= GameUtil.Code.XYFT;
		String[] betItem = {"第一名|6|20000", "第一名|7|20000", "第一名|8|20000"};
		List<String> betItems=new ArrayList<>(Arrays.asList(betItem));

		SgWinMemberUtil sgWinMemberUtil=SgWinMemberUtil.findInstance();

		JsonResultBeanPlus bean=sgWinMemberUtil.login(id,memberAccount,memberPassword,handicapUrl,handicapCaptcha);
		System.out.println("登录结果="+bean.toJsonString());

        bean=sgWinMemberUtil.userInfo(id);
		System.out.println("用户信息="+bean.toJsonString());
        //获取赔率信息
        sgWinMemberUtil.getOddsInfo(id, gameCode, "ballNO");

		String roundno = PeriodUtil.getHandicapGamePeriod(HandicapUtil.Code.SGWIN, gameCode, gameCode.getGameFactory().period(HandicapUtil.Code.SGWIN).findPeriod());




		//投注
		bean = sgWinMemberUtil.betting(id, gameCode, roundno, betItems, "ballNO");
		System.out.println("投注结果="+bean.toJsonString());
	}

	@Test
	public void test() throws InterruptedException, IOException {
		String id = "123";

		String memberAccount = "11s";
		String memberPassword = "135589";
		String handicapUrl = "http://mx1265.com/";
		String handicapCaptcha = "135589";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());


		String routeHtml=SgWinMemberTool.getSelectRoutePage(httpConfig,handicapUrl,handicapCaptcha);

		if(StringTool.isEmpty(routeHtml)){
			return ;
		}
		//4条会员线路数组
		String[] routes =SgWinMemberTool.getMemberRoute(httpConfig,routeHtml);

		if(ContainerTool.isEmpty(routes)){
			return ;
		}
		Map<String, String> loginInfoMap =SgWinMemberTool.getLoginHtml(httpConfig, routes);
		if (ContainerTool.isEmpty(loginInfoMap)) {
			return ;
		}
		String loginSrc = loginInfoMap.get("loginSrc");

		//获取验证码
		String verifyCode = SgWinMemberTool.getVerifyCode(httpConfig, loginSrc, loginInfoMap.remove("code"));

		httpConfig.setHeader("Referer", loginSrc.concat("login"));

		//盘口协议页面
		String loginInfo = SgWinMemberTool.getLogin(httpConfig, memberAccount, memberPassword, verifyCode, loginSrc,
				loginInfoMap.remove("action"));

		System.out.println("登录信息="+loginInfo);
		if(StringTool.isEmpty(loginInfo)){
			return ;
		}
		if (StringTool.contains(loginInfo, "alert", "修改密码")) {
			System.out.println("登录信息="+SgWinMemberTool.loginError(loginInfo).getMsg());
			return ;
		}
		String indexHtml=SgWinMemberTool.getHomePage(httpConfig,loginSrc);

		httpConfig.setHeader("Referer", loginSrc.concat("member/index"));
		//获取用户信息
		JSONObject userInfo=SgWinMemberTool.getUserInfo(httpConfig,loginSrc);
		if (ContainerTool.isEmpty(userInfo)) {
			return ;
		}
		String oddsCode="DX1,DX2,DX3,DX4,DX5,DX6,DX7,DX8,DX9,DX10,DS1,DS2,DS3,DS4,DS5,DS6,DS7,DS8,DS9,DS10,GDX,GDS,LH1,LH2,LH3,LH4,LH5";

		String code=SgWinConfig.BET_CODE.get("PK10");

		JSONObject oddsInfo=SgWinMemberTool.getOddsInfo(httpConfig,loginSrc,code,oddsCode);

		List<String> betItems=new ArrayList<>();
		betItems.add("第一名|大|5000");
		betItems.add("第一名|大|6000");

		JSONArray betsArray = SgWinMemberTool.getBetItemInfo(GameUtil.Code.PK10, betItems, oddsInfo);

		String roundno = PeriodUtil.getHandicapGamePeriod(HandicapUtil.Code.SGWIN, GameUtil.Code.PK10, "740415");


		JSONObject object=SgWinMemberTool.betting(httpConfig,loginSrc,betsArray,roundno,code);

		System.out.println(object);
	}
}
