package com.cloud.lottery.doc;
import c.a.util.core.test.CommTest;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.io.IOException;
/**
 * @Author: Dongming
 * @Date: 2020-03-05 15:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpTest extends CommTest {


	@Test public void test() throws IOException {
		String url = "https://api.api861861.com/pks/getLotteryPksInfo.do?lotCode=10057";
		//10057 -  xyft   10037 - jssc
//		String url = "https://api.api861861.com/pks/getPksHistoryList.do?lotCode=10037";
		//https://api.api861861.com/pks/getPksHistoryList.do?lotCode=10037
		HttpClientConfig hcc =  HttpClientUtil.getConfig();
		//hcc.setHeader("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; ) Opera/UCWEB7.0.2.37/28/999");
		HttpClientUtil hcu = HttpClientUtil.findInstance();
		hcc.httpClient(hcu.createHttpClient());

		String html = hcu.getHtml(hcc.url(url));
		System.out.println(html);

	}
}
