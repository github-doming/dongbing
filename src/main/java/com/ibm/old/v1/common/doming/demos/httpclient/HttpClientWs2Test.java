package com.ibm.old.v1.common.doming.demos.httpclient;

import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Date 2018年06月13日 14:21
 * @Author Dongming
 * @Email: job.dongming@foxmail.com
 **/
public class HttpClientWs2Test {

	@Test public void testHttpClientWs2() {
		try {
			HttpClientConfig httpConfig = new HttpClientConfig();

			httpConfig.httpContext(HttpClientContext.create());

			String mainUrl = "http://sf33.qr68.us/";
			String valiCode = "s2p69";

			Long time1, time2;
			//主界面
			time1 = System.currentTimeMillis();
			String selectRoutePage = HttpClientWs2Util.getSelectRoutePage(httpConfig, mainUrl, valiCode);

			time2 = System.currentTimeMillis();
			System.out.println("花费时长\ttime=" + (time2 - time1) + "主界面打开成功\tmianUrl：" + mainUrl);

			//获取线路
			time1 = System.currentTimeMillis();
			List<String> routes = HttpClientWs2Util.findRoutes4Login(selectRoutePage);
			time2 = System.currentTimeMillis();
			System.out.println("花费时长\ttime=" + (time2 - time1) + "\t获取线路信息成功\troutes：" + routes);
			httpConfig.setHeader("Referer", mainUrl);

			//登陆界面
			time1 = System.currentTimeMillis();
			HttpResult loginPage = HttpClientWs2Util.getLoginPage(httpConfig, routes);
			time2 = System.currentTimeMillis();
			System.out.println("花费时长\ttime=" + (time2 - time1) + "\t登陆界面打开成功\tloginUrl:" + loginPage.getData());

			//登陆界面需要解析信息
			time1 = System.currentTimeMillis();
			Map<String, Object> loginDateMap = HttpClientWs2Util.findLoginDateMap4LoginPage(loginPage);
			time2 = System.currentTimeMillis();
			System.out.println("花费时长\ttime=" + (time2 - time1) + "\t登陆界面script信息解析成功\tscriptDateMap：" + loginDateMap);

			String hostUrl = StringTool.getHttpHost((String) loginPage.getData());

			//获取图片验证码地址
			time1 = System.currentTimeMillis();
			String valiImgUrl = HttpClientWs2Util.getValiImageUrl(hostUrl, loginDateMap);
			time2 = System.currentTimeMillis();
			System.out.println("花费时长\ttime=" + (time2 - time1) + "\t图片验证码地址解析成功\tvaliImgUrl：" + valiImgUrl);

			//识别图片验证码
			time1 = System.currentTimeMillis();
			String verifyCode = HttpClientWs2Util.getVerifyCode(valiImgUrl);
			time2 = System.currentTimeMillis();
			System.out.println("花费时长\ttime=" + (time2 - time1) + "\t验证识别成功\tverifyCode：" + verifyCode);

	/*		String username = "cs7654321";
			String password = "Cs987we12...";*/
			String username = "cs765432";
			String password = "Cs987we12.";

			time1 = System.currentTimeMillis();
			String toLoginUrl = HttpClientWs2Util.findUrl2Login(hostUrl, loginDateMap, username, password, verifyCode);
			time2 = System.currentTimeMillis();
			System.out.println("花费时长\ttime=" + (time2 - time1) + "\t登陆地址拼装成功\ttoLoginUrl：" + toLoginUrl);

			time1 = System.currentTimeMillis();
			httpConfig.setHeader("Referer", (String) loginPage.getData());
			httpConfig.httpContext(HttpClientContext.create());
			HttpResult loginResultMap = HttpClientUtil.findInstance().post(httpConfig.url(toLoginUrl));
			time2 = System.currentTimeMillis();
			System.out.println("花费时长\ttime=" + (time2 - time1) + "\t登陆成功\tloginResultMap：" + loginResultMap);

			String html = (String) loginResultMap.getHtml();
			String[] strs = html.split("\n");

			httpConfig.setHeader("Referer", hostUrl + strs[0] + ".auth");
			System.out.println(hostUrl + strs[0] + ".auth");
			strs = html.split("://host");
			String kUrl = hostUrl + strs[1];
			System.out.println(kUrl);
			HttpResult kPage = HttpClientUtil.findInstance().get(httpConfig.url(kUrl));
			System.out.println(kPage);

			if ("302".equalsIgnoreCase((String) kPage.getHtml())) {
				HttpResult toPage = HttpClientUtil.findInstance().get(httpConfig.url((String) kPage.getLocation()));
				System.out.println(toPage);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);

	}
}
