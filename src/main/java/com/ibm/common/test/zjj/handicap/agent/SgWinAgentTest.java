package com.ibm.common.test.zjj.handicap.agent;
import com.ibm.common.utils.http.tools.agent.SgWinAgentTool;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 * @Description: SgWin代理测试类
 * @Author: null
 * @Date: 2019-10-28 10:09
 * @Version: v1.0
 */
public class SgWinAgentTest {
	@Test
	public void test() throws InterruptedException, IOException {
		String id = "123";

		String memberAccount = "hcdl001";
		String memberPassword = "Aasas1212.";
		String handicapUrl = "http://138831.co/";
		String handicapCaptcha = "85217";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		String routeHtml=SgWinAgentTool.getSelectRoutePage(httpConfig,handicapUrl,handicapCaptcha);
		if(StringTool.isEmpty(routeHtml)){
			return ;
		}
		//4条代理线路数组
		List<String> hrefs = SgWinAgentTool.getAgentRoute(routeHtml);
		String[] routes = SgWinAgentTool.speedRoute(httpConfig,hrefs);
		if(ContainerTool.isEmpty(routes)){
			return ;
		}

		Map<String, String> loginInfoMap =SgWinAgentTool.getLoginHtml(httpConfig, routes);
		if (ContainerTool.isEmpty(loginInfoMap)) {
			return ;
		}
		String loginSrc = loginInfoMap.get("loginSrc");

		//获取验证码
		String verifyCode = SgWinAgentTool.getVerifyCode(httpConfig, loginSrc, loginInfoMap.remove("code"));

		httpConfig.setHeader("Referer", loginSrc.concat("login"));
		httpConfig.httpContext(HttpClientContext.create());

		//TODO
	}
}
