package com.ibm.old.v1.common.zjj.test.handicap;
import com.ibm.old.v1.common.doming.utils.http.httpclient.tools.HqTool;
import net.sf.json.JSONObject;
import org.apache.http.client.protocol.HttpClientContext;
import org.doming.core.tools.ContainerTool;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;
/**
 * @Description: hq盘口测试类
 * @Author: zjj
 * @Date: 2019-08-05 17:05
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HqTest {

	@Test public void test01() throws Exception {

		String id = "123";

		String memberAccount = "cxdb003";
		String memberPassword = "Ac135246";
		String handicapUrl = "http://sc1618.co";
		String handicapCaptcha = "aa0225";

		HttpClientConfig httpConfig = new HttpClientConfig();
		httpConfig.httpContext(HttpClientContext.create());
		httpConfig.httpClient(HttpClientUtil.findInstance().createHttpClient());

		//获取会员登入页面
		String href = HqTool.getMemberHtml(httpConfig, handicapUrl, handicapCaptcha);
		//获取线路
		String[] routes = HqTool.getSelectRoutePage(httpConfig, href, handicapUrl);

		Map<String, String> loginInfo = HqTool.getLoginHtml(httpConfig, routes, handicapCaptcha);

		Map<String, String> scriptInfo = HqTool.getScriptInfo(loginInfo.get("html"));

		String hostUrl = loginInfo.get("hostUrl");

		JSONObject encryptKey = HqTool.getEncryptKey(httpConfig, scriptInfo.get("SESSIONID"), hostUrl);
		if (ContainerTool.isEmpty(encryptKey)) {
			return;
		}
		String pke = encryptKey.getString("e");
		String pk = encryptKey.getString("m");
		String logpk = pke.concat(",").concat(pk);

		System.out.println("pke=" + Integer.parseInt(pke, 2));
		RSAPublicKey pubKey = RSAUtils.getPublicKey(new BigInteger(pk, 16).toString(), Integer.parseInt(pke, 16) + "");

		String account = URLEncoder.encode(memberAccount.trim(),"UTF-8").concat(",")
				.concat(URLEncoder.encode(memberPassword.trim(),"UTF-8"));

		String mi = RSAUtils.encryptByPublicKey(account, pubKey);

		System.out.println(mi);

		//登录成功，{"Status":1}
		String loginHtml=HqTool.getLogin(httpConfig,hostUrl,scriptInfo.get("SESSIONID"),handicapCaptcha,mi,logpk);

		System.out.println("loginHtml="+loginHtml);

		String agreementHtml=HqTool.agreementHtml(httpConfig,hostUrl,scriptInfo.get("SESSIONID"));

		System.out.println("agreementHtml="+agreementHtml);

		//同意页面，{"Status":1,"Data":0}
		String acceptHtml=HqTool.acceptHtml(httpConfig,hostUrl,scriptInfo.get("SESSIONID"));

		System.out.println("acceptHtml="+acceptHtml);

		String indexHtml=HqTool.indexHtml(httpConfig,hostUrl,scriptInfo.get("SESSIONID"));

		System.out.println("indexHtml="+indexHtml);



	}

	@Test public void test() throws Exception {
		String memberAccount = "123456";
		String memberPassword = "qweqwe";

		String account = URLEncoder.encode(memberAccount.trim(),"UTF-8").concat(",")
				.concat(URLEncoder.encode(memberPassword.trim(),"UTF-8"));

		String pk = "FD3BB26136C3D36E78505E3924135CC000C167DB90B98C93187178D70C6AC838388582A4B42CD9EBF92323B8B6A3AC142630EBFA7553655C4B96E03C29B8DE39F902AC7C188D1032F7227FAE7E5DC74377975B4812AA35A5F3083CEF48C84EC2686EE5D4BB5CD4EE75158188C3FFD60B53ED2D5B3C2DDA582793837E37E0F4C1A8364221CD385937A10C0A2BD0831B43D81C55DF4CA1C3799B0FD7057CFEE592BD8C6CCA57606B986D7317CCA410C39E9938D9779DC6DCAFD4FD218E51138CA73698533F3240651566E1136A03A5506EB2104D9B654D9B9871C7827A6F13D83DB931ACC93B121ACDB6617847BE11058E4DFC72B3DC61D4F2EF159E18502A21F1";
		String pke = "010001";
		String logpk = pke.concat(",").concat(pk);

		System.out.println(Integer.parseInt(pke, 16));
		System.out.println(new BigInteger(pk, 16));

		RSAPublicKey pubKey = RSAUtils.getPublicKey(new BigInteger(pk, 16).toString(), Integer.parseInt(pke, 16) + "");

		System.out.println(pubKey);

		String mi = RSAUtils.encryptByPublicKey(account, pubKey);


		System.out.println(mi);
	}





}
