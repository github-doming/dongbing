package com.ibm.common.test.zjj.handicap.agent;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.tools.agent.IdcAgentApiTool;
import com.ibm.common.utils.http.tools.agent.IdcAgentTool;
import com.ibm.common.utils.http.tools.member.IdcMemberApiTool;
import com.ibm.common.utils.http.utils.agent.IdcAgentApiUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: IDC代理测试
 * @Author: zjj
 * @Date: 2019-09-10 18:31
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IdcAgentTest {
    @Test
    public void test03(){
        String id = "123";

        String agentAccount = "ch36366";
        String agentPassword = "wsx357357.";
        String handicapUrl = "http://88w.sfg666.com";
        String handicapCaptcha = "91919";

        IdcAgentApiUtil agentApiUtil=IdcAgentApiUtil.findInstance();
        JsonResultBeanPlus bean= agentApiUtil.login(id,agentAccount,agentPassword,handicapUrl,handicapCaptcha);

        System.out.println("登录结果信息="+bean.toJsonString());

        bean=agentApiUtil.memberListInfo(id);

        System.out.println("会员列表信息="+bean.toJsonString());

        //ah881:0;ah1881:0
        bean=agentApiUtil.getUnsettledDetailed(id, GameUtil.Code.PK10,"","0");

        System.out.println("未结算信息="+bean.toJsonString());



    }
    @Test
    public void test01(){
		String id = "123";

		String agentAccount = "ch36366";
		String agentPassword = "wsx357357.";
		String handicapUrl = "http://88w.sfg666.com";
		String handicapCaptcha = "91919";

		IdcAgentApiUtil agentUtil = IdcAgentApiUtil.findInstance();

		JsonResultBeanPlus bean=agentUtil.login(id, agentAccount, agentPassword, handicapUrl, handicapCaptcha);
		System.out.println("登录结果信息="+bean.toJsonString());

		JSONArray memberList=agentUtil.getMemberList(id);
		System.out.println("会员列表信息="+memberList);

		bean=agentUtil.getUnsettledDetailed(id,GameUtil.Code.PK10,"","378927785");
		System.out.println("未结算信息="+bean.toJsonString());
	}




	@Test public void test() throws IOException, InterruptedException {
		String id = "123";

		String agentAccount = "ah8389";
		String agentPassword = "qwqw1212v";
		String handicapUrl = "http://n2k.scy888.com";
		String handicapCaptcha = "80077";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		String loginHtml = IdcAgentTool.getLoginHtml(httpConfig, handicapUrl, handicapCaptcha);
		if (StringTool.isEmpty(loginHtml)) {
			return;
		}

		//获取线路
		String loginSrc = IdcAgentTool.getLoginSrc(loginHtml);
		if (StringTool.isEmpty(loginSrc)) {
			return;
		}
		//登陆界面
		//登陆界面需要解析信息
		httpConfig.httpContext(HttpClientContext.create());

		Map<String, String> loginDateMap = IdcAgentTool.getLoginData(httpConfig, loginSrc);
		if (StringTool.isEmpty(loginDateMap)) {
			return;
		}

		loginSrc = loginDateMap.get("loginSrc");
		String projectHost = StringTool.projectHost(loginSrc);

		//获取图片验证码
		String verifyCode = IdcAgentTool.getVerifyCode(httpConfig, projectHost);

		//登陆
		httpConfig.setHeader("Referer", loginSrc);
		Map<String, String> loginInfo = IdcAgentTool
				.getLogin(httpConfig, projectHost, loginDateMap, agentAccount, agentPassword, verifyCode);
		if (StringTool.isEmpty(loginInfo)) {
			return;
		}
		httpConfig.setHeader("Referer", projectHost + "/ch/agreement.aspx");
		projectHost = loginInfo.get("projectHost").toString();

		Map<String, String> data = new HashMap<>(1);
		data.put("projectHost", projectHost);

		String html = IdcAgentTool.getMemberManage(httpConfig, projectHost);
		if (StringTool.isEmpty(html)) {
			return;
		}
		String curmemberno = StringUtils.substringBetween(html, "var curmemberno = \"", "\";");
		String curmembername = StringUtils.substringBetween(html, "var curmembername = \"", "\";");
		JSONArray memberArray = IdcAgentTool.getMemberList(httpConfig, projectHost, curmemberno, curmembername);

		System.out.println(memberArray);
	}
	/**
	 * 获取会员列表信息
	 */
	@Test public void test001() throws Exception {
		String existHaId = "123";

		String agentAccount = "hcad009";
		String agentPassword = "asas1212";
		String handicapUrl = "http://22u.sx699.com";
		String handicapCaptcha = "91165";
		//授权码
		String extwagerno = "BYZN";
		String extwagerkey = "EF64586B3AF643FD9A8F365086C8FC0A";
		String ticket="9EA21DD3287F32FA6F61FB0683031FF5B717923BAD73EEA3A57AFF8C8853F2203C464D03A7D04C445E55D3FD230A659C0DFABE0CBB82F0975653AD48E7E4D9B17F713C8DB678A8F452754708C6F579F0F9A2D8CBA7EE67AF50E24026F7B2B778C2DF1FFE05B07697A19330DF8FE03F261449049AFB962DC852A9CCFBA87C77FF7EFAA67C9E1333A598D942627606B6B1E8BCE33A2B5A32B25297605F315541726542E0BEA927BDA80D30B3F11793771D96A87F2F00F593B7BDAF106D6350BC000847B5CBD747C9F8B8E27A1DC1A8B0B829CCE007FF8ADE0D458F8EF929B589D6";
		String loginUrl="https://57jfdgw.51ttyin.com/cp3-1-ag/outapi/";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		//玩法
		JSONObject wagerTypeList =IdcAgentApiTool.getGameList(httpConfig,loginUrl,ticket);

		System.out.println("玩法列表："+wagerTypeList);
	}
	/**
	 * 获取会员列表信息
	 */
	@Test public void test004() throws Exception {
		String id = "123";

		String agentAccount = "hcad007";
		String agentPassword = "asas1212";
		String handicapUrl = "http://22u.sx699.com";
		String handicapCaptcha = "91165";
		//授权码
		String extwagerno = "BYZN";
		String extwagerkey = "EF64586B3AF643FD9A8F365086C8FC0A";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		JSONObject loginHtml = IdcMemberApiTool.getLoginUrl(httpConfig, handicapUrl, handicapCaptcha);

		if (ContainerTool.isEmpty(loginHtml)) {
			return;
		}
		System.out.println("获取登录url:" + loginHtml);

		String loginUrl = loginHtml.getString("login_url");

		String sign =String.format("accounts=%s&pwd=%s&extwagerno=%s&extwagerkey=%s",agentAccount,agentPassword,extwagerno,extwagerkey);

		sign= Md5Tool.md5Hex(sign);

		String data=String.format("&accounts=%s&pwd=%s&extwagerno=%s&sign=%s",agentAccount,agentPassword,extwagerno,sign);

		JSONObject loginInfo = IdcMemberApiTool.getLoginTicket(httpConfig,loginUrl,data);
		if(ContainerTool.isEmpty(loginInfo)){
			return ;
		}
		System.out.println(loginInfo);

		loginUrl=loginInfo.getString("api_url");

		String ticket=loginInfo.getString("ticket");

		System.out.println("loginUrl="+loginUrl);
		System.out.println("loginUrl="+ticket);

		JSONObject wagerTypeList =IdcAgentApiTool.getGameList(httpConfig,loginUrl,ticket);

		System.out.println("玩法列表："+wagerTypeList);

		//		JSONObject memberList=IdcMemberApiTool.getUserInfo(httpConfig,loginUrl,ticket);
		//
		//		if(ContainerTool.isEmpty(memberList)){
		//			return;
		//		}
		//		System.out.println(memberList);

		//		JSONArray list=memberList.getJSONArray("data");
		//		String member2=list.getJSONArray(0).getString(2);

		//		JSONObject unSettle=IdcNewAgentTool.getUnsettledDetailed(httpConfig,loginUrl,ticket,agentAccount);

		//		System.out.println(unSettle);
	}

}
