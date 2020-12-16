package org.doming.example.idear.test;
import org.doming.develop.http.jsoup.HttpJsoupTool;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-11-09 14:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class StringTest {

	@Test public void test(){

		String s1 = "MqQueue[%s]";
		String s2 = "queue.sql";
		System.out.println(String.format(s1,s2));

	}

	@Test public void test2(){

		String s1 = "MqQueue[%s]";

		String s2 = "queue.sql";
		System.out.println(String.format(s1,s2));
	}

	@Test public void test3() throws IOException {
		String url = "http://115.159.55.225/Code/GetVerifyCodeFromUrl";

		Map<String,Object> map = new HashMap<>(2);
		map.put("type", "WS");
		map.put("url", "www.baidu.com");
		System.out.println(HttpJsoupTool.doPost(url,map).text());
	}


}
