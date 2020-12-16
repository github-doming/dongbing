package com.ibm.old.v1.common.zjj.test.handicap.agent;
import com.ibm.old.v1.common.doming.configs.SgWinConfig;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.SgWinTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.util.Date;
import java.util.Map;
/**
 * @Description: SGWIN爬取代理页面
 * @Author: zjj
 * @Date: 2019-08-19 14:34
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class SwGinAgentTest {
	@Test public void test01() throws Exception {
		String id = "123";

		String agentAccount = "qq369369qq";
		String agentPassword = "Aa138138.";
		String handicapUrl = "https://138789.co/";
		String handicapCaptcha = "056118";


		String followMember="cxz0001";


		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());
		String gameCode = IbmGameEnum.PK10.name();

		//获取线路选择页面
		String routeHtml =SgWinTool.getSelectRoutePage(httpConfig, handicapUrl, handicapCaptcha);

		//4条代理会员线路数组
		String[] routes = SgWinTool.getMemberRoute(httpConfig, routeHtml, SgWinConfig.HANDICAP_AGENT);

		//获取登录信息map
		Map<String, String> loginInfoMap = SgWinTool.getLoginHtml(httpConfig, routes);

		if (ContainerTool.isEmpty(loginInfoMap)) {
			return ;
		}
		String projectHost = loginInfoMap.get("loginSrc");

		//获取验证码
		String verifyCode = SgWinTool.getVerifyCode(httpConfig, projectHost, loginInfoMap.remove("code"));

		httpConfig.setHeader("Referer", projectHost.concat("login"));
		httpConfig.httpContext(HttpClientContext.create());

		//盘口协议页面
		String loginInfo = SgWinTool.getLogin(httpConfig, agentAccount, agentPassword, verifyCode, projectHost,
				loginInfoMap.remove("action"),SgWinConfig.HANDICAP_AGENT);

		if (StringTool.isEmpty(loginInfo)) {
			return ;
		}
		//获取会员列表信息
		JSONArray array=SgWinTool.getMemberList(httpConfig,projectHost,agentAccount,SgWinConfig.HANDICAP_MEMBER);
		System.out.println(array);

		//获取在线用户信息，用于定时检验，一分钟一次
		String online=SgWinTool.onlineInfo(httpConfig,projectHost);
		System.out.println("会员在线情况："+online);

		//获取游戏code
		IbmGameEnum game = IbmGameEnum.valueOf(gameCode);

		String period=PeriodTool.findPeriod(gameCode).toString();
		//获取日期字符串
		String date;
		switch (game) {
			case PK10:
				date = DateTool.getDate(new Date());
				break;
			case XYFT:
				String[] str = period.split("-");
				StringBuilder stringBuilder = new StringBuilder(str[0]);
				stringBuilder.insert(6, "-");
				stringBuilder.insert(4, "-");
				date = stringBuilder.toString();
				break;
			default:
				throw new Exception("获取日期异常");
		}
		System.out.println(date);

		//报表合计页面
		JSONArray totalArray=SgWinTool.getTotalList(httpConfig,projectHost,date,SgWinConfig.GAME_CODE_MAP.get(gameCode));
		System.out.println("有未结算信息会员："+totalArray);
		if(ContainerTool.isEmpty(totalArray)){
			return ;
		}
		//跟随会员，可多账号，需切割
		if(totalArray.contains(followMember)){
			//投注信息
			JSONArray jsonArray=SgWinTool.betDetails(httpConfig,projectHost,date,SgWinConfig.GAME_CODE_MAP.get(gameCode),followMember);
			if(ContainerTool.isEmpty(jsonArray)){
				return ;
			}
			System.out.println(jsonArray);
			//匹配期数是否一致
			for(Object object:jsonArray){
				JSONObject json= (JSONObject) object;
				if(!period.equals(json.getString("period"))){
					break;
				}
				//匹配注单号是否和已获取过的第一条投注信息注单号一致

			}

		}



	}
}
