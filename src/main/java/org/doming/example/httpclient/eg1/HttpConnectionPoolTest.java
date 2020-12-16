package org.doming.example.httpclient.eg1;
import org.junit.Test;
/**
 * @Description: http连接池测试
 * @Author: Dongming
 * @Date: 2018-10-31 16:07
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HttpConnectionPoolTest {
	@Test public void test() {

	}

	@Test public void test1(){

		String url = "http://116.62.144.86:8082/a/platform/admin/login2.do";
		String result = HttpConnectionPoolUtil.get(url),result1;

		for (int i = 0; i < 1000; i++) {
			result1 = HttpConnectionPoolUtil.get(url);
			if (result1 == null){
				System.out.println("error");
			}
			if (!result.equals(result1)){
				System.out.println("比对不上");
			}

		}
		System.out.println("end");

	}
}
