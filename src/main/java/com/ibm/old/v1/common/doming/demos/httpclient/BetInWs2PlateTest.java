package com.ibm.old.v1.common.doming.demos.httpclient;
import c.a.util.core.test.CommTest;
import net.sf.json.JSONObject;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 测试投注进ws2挡板盘口
 * @Author: Dongming
 * @Date: 2018-10-23 10:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetInWs2PlateTest extends CommTest {

	@Test public void getOddTest() throws Exception {
		HttpResult result = getOdd();
		String html = result.getHtml();
		System.out.println(html);
	}

	@Test public void bettingTest() throws Exception {
		String[] betItem = {"第三名_龙虎_龙#30", "第一名_大小_大#10"};
		HttpResult result = getBetting(betItem, getOdd());
		System.out.println(result.getHtml());

	}

	/**
	 * 投注
	 *
	 * @param betItem 投注项
	 * @param result  赔率
	 * @return
	 * @throws Exception
	 */
	private HttpResult getBetting(String[] betItem, HttpResult result) throws Exception {
		HttpClientConfig httpConfig = new HttpClientConfig();
		String url = "http://a.tzxyz.xyz:81/ibm/ibm_plate/getWs2PlateBet.do";

		JSONObject htmlObj = JSONObject.fromObject(result.getHtml());
		JSONObject odds = htmlObj.getJSONObject("data");
		StringBuilder stringBuilder = new StringBuilder();
		for (String item : betItem) {
			String[] betStr = item.split("#");
			JSONObject odd = odds.getJSONObject(betStr[0]);
			String gameplayCode = odd.getString("gameplayCode");
			stringBuilder.append(gameplayCode, 0, gameplayCode.length() - 2).append("|")
					.append(gameplayCode.substring(gameplayCode.length() - 2)).append("|").append(odd.getString("odd"))
					.append("|").append(betStr[1]).append(";");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		Map<String, Object> parameterMap = new HashMap<>(1);
		parameterMap.put("t", stringBuilder);
		return HttpClientUtil.findInstance().post(httpConfig.url(url).map(parameterMap));
	}

	/**
	 * 获取赔率
	 *
	 * @return
	 * @throws Exception
	 */
	private HttpResult getOdd() throws Exception {
		HttpClientConfig httpConfig = new HttpClientConfig();
		String url = "http://a.tzxyz.xyz:81/ibm/ibm_plate/getWs2PlateOdd.do";
		Map<String, Object> parameterMap = new HashMap<>(1);
		String play = "bothSides";
		parameterMap.put("play", play);
		return HttpClientUtil.findInstance().post(httpConfig.url(url).map(parameterMap));

	}
}
