package com.ibm.old.v1.common.zjj.test.handicap.agent;
import com.ibm.old.v1.common.doming.configs.IdcConfig;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.IdcTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.util.Map;
/**
 * @Description: idc爬取代理页面
 * @Author: zjj
 * @Date: 2019-08-16 16:24
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IdcAgentTest {
	@Test public void test01() throws Exception {
		String id = "123";

		String agentAccount = "gbe55";
		String agentPassword = "asas1212";
		String handicapUrl = "http://am2.ukk556.com";
		String handicapCaptcha = "79799";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		String gameCode = IbmGameEnum.PK10.name();




		//打开登陆页面
		String loginHtml = IdcTool.getLoginHtml(httpConfig, handicapUrl, handicapCaptcha);
		System.out.println("登录页面=" + loginHtml);

		//获取线路
		String loginSrc = IdcTool.getLoginSrc(loginHtml);
		System.out.println("线路地址=" + loginSrc);

		httpConfig.httpContext(HttpClientContext.create());
		Map<String, String> loginDateMap = IdcTool.getLoginData(httpConfig, loginSrc);
		System.out.println("登录页面信息=" + loginDateMap);


		if (ContainerTool.isEmpty(loginDateMap)) {
			return;
		}
		loginSrc = loginDateMap.get("loginSrc");
		String projectHost = StringTool.projectHost(loginSrc);

		//获取图片验证码
		String verifyCode = IdcTool.getVerifyCode(httpConfig, projectHost);
		System.out.println("验证码=" + verifyCode);

		httpConfig.setHeader("Referer", loginSrc);
		HttpResult loginInfo = IdcTool
				.getLogin(httpConfig, projectHost, loginDateMap, agentAccount, agentPassword, verifyCode);
		System.out.println("登录信息=" + loginInfo);
		if(ContainerTool.isEmpty(loginInfo)){
			return ;
		}
		httpConfig.setHeader("Referer", projectHost + "/ch/main.aspx");
		projectHost = loginInfo.getData().toString();

		//代理用户管理页面
		String memberManageInfo=IdcTool.getMemberManage(httpConfig,projectHost);
		if(StringTool.isEmpty(memberManageInfo)){
			return ;
		}
		// var curmembername = "c";
		String curMemberName =StringUtils.substringBetween(memberManageInfo, "var curmembername = \"", "\";");
		// var _datetime = "2019-08-19";
		String dateTime=StringUtils.substringBetween(memberManageInfo,"var _datetime = \"","\";");
		//var transdate = '2019-08-19';
		String transDate=StringUtils.substringBetween(memberManageInfo,"var transdate = '","';");

		//获取所有会员信息
		JSONArray memberInfoList=IdcTool.getMemberInfoList(httpConfig,projectHost,agentAccount,curMemberName);
		System.out.println("会员信息="+memberInfoList);

		//获取时间
//		String date=IdcTool.getDate(httpConfig,projectHost);
		//获取日期字符串
//		String[] dates=date.split(",");

		//获取报表总账 {memberno:"",gamestring:"11",sdate:"2019-08-19",edate:"2019-08-19",beginroundno:"",endroundno:"",wagerroundno:"",wagertypeno:"",_isjs:false,datetime:"2019-08-19",status:1}
		String gamestring=IdcConfig.GAME_BET_CODE.get(gameCode).get("gameno");

		JSONObject noSettlement=IdcTool.getReport(httpConfig,projectHost, gamestring,transDate,dateTime);


	}
}
