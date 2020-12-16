package com.ibm.common.test.zjj.handicap.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.PeriodUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.agent.HqAgentTool;
import com.ibm.common.utils.http.utils.RSAUtils;
import com.ibm.common.utils.http.utils.agent.HqAgentUtil;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: HQ代理登录
 * @Author: zjj
 * @Date: 2019-09-10 14:27
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HqAgentTest {
    @Test
    public void test01(){
        String id = "123";

        String agentAccount = "nb7799";
        String agentPassword = "As135135";
        String handicapUrl = "http://w1.sc9988.co/";
        String handicapCaptcha = "92449h";

        HqAgentUtil agentUtil=HqAgentUtil.findInstance();
        JsonResultBeanPlus bean=agentUtil.login(id,agentAccount,agentPassword,handicapUrl,handicapCaptcha);
        System.out.println("登录结果="+bean.toJsonString());
        GameUtil.Code gameCode=GameUtil.Code.CQSSC;
		Object  period = gameCode.getGameFactory().period(HandicapUtil.Code.HQ).findPeriod();

        //获取期数字符串
        Object platformPeriod = PeriodUtil.getPeriod(HandicapUtil.Code.HQ,  GameUtil.Code.CQSSC, period);
        // 获取跟投会员的未结算摘要信息
        bean = agentUtil.getBetSummary(id, gameCode, platformPeriod,new HashMap<>());



    }

	@Test public void test() throws Exception {

		String id = "123";

		String agentAccount = "fcv5231";
		String agentPassword = "Aa123123";
		String handicapUrl = "http://w1.sc3588.co";
		String handicapCaptcha = "99812a";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		//获取代理登入href
		String href = HqAgentTool.getAgentHref(httpConfig, handicapUrl, handicapCaptcha);

		//获取线路
		String[] routes = HqAgentTool.getSelectRoutePage(httpConfig, href, handicapUrl);

		//获取登录页面
		Map<String, String> loginInfo = HqAgentTool.getLoginHtml(httpConfig, routes, handicapCaptcha);
		if (ContainerTool.isEmpty(loginInfo) || !loginInfo.containsKey("hostUrl")) {

			return ;
		}
		Map<String, String> scriptInfo = HqAgentTool.getScriptInfo(loginInfo.get("html"));

		if (ContainerTool.isEmpty(scriptInfo)) {
			return ;
		}
		String projectHost = loginInfo.get("hostUrl");

		//加密key
		JSONObject encryptKey = HqAgentTool.getEncryptKey(httpConfig, scriptInfo.get("SESSIONID"), projectHost);
		if (ContainerTool.isEmpty(encryptKey)) {
			return ;
		}

		String pke = encryptKey.getString("e");
		String pk = encryptKey.getString("m");
		String logpk = pke.concat(",").concat(pk);

		RSAPublicKey pubKey = RSAUtils
				.getPublicKey(new BigInteger(pk, 16).toString(), Integer.parseInt(pke, 16) + "");

		String account = URLEncoder.encode(agentAccount.trim(), "UTF-8").concat(",")
				.concat(URLEncoder.encode(agentPassword.trim(), "UTF-8"));

		String mi = RSAUtils.encryptByPublicKey(account, pubKey);

		//登录
		String loginHtml = HqAgentTool
				.getLogin(httpConfig, projectHost, scriptInfo.get("SESSIONID"), handicapCaptcha, mi, logpk);

		if (StringTool.isEmpty(loginHtml)) {
			return ;
		}

		Map<String, String> data = new HashMap<>(1);
		data.put("projectHost", "http://".concat(projectHost).concat("/").concat(scriptInfo.get("SESSIONID")));

		//获取主页面
		Map<String,Object> indexMap=HqAgentTool.getIndex(httpConfig,data.get("projectHost"));

		if(ContainerTool.isEmpty(indexMap)){
			return ;
		}

		JSONArray array=HqAgentTool.getMemberList(httpConfig,data.get("projectHost"),indexMap.get("moneyType"));

		System.out.println(array);


	}

}
