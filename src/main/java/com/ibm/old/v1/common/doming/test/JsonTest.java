package com.ibm.old.v1.common.doming.test;
import c.a.security.util.CommRSAUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2018-09-10 14:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JsonTest {

	@Test public void test() throws Exception {

		Object jsonStr = "{'token' :'5585d69bd2e64741b00e54c07517a0f7','data' :{'softwareId' : '4d933a57542946b9bff5e0b9bdde988f'} }";

		String data = "{'softwareId' : '4d933a57542946b9bff5e0b9bdde988f'}";
		JSONObject json = JSONObject.fromObject(jsonStr);
		System.out.println(json.get("data"));
		System.out.println(CommRSAUtil.decode(data));

	}

	public static Map<String, Object> json2map(String jsonStr) {
		Map<String, Object> returnMap = new HashMap<>();
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Iterator keyIterator = jsonObject.keys();
		String key = null;
		Object value = null;
		while (keyIterator.hasNext()) {
			key = (String) keyIterator.next();
			value = jsonObject.get(key);
			returnMap.put(key, value);
		}
		return returnMap;
	}

	@Test public void test01() {
		JSONArray jArr = JSONArray.fromObject(null);
		System.out.println(jArr);
		System.out.println(jArr == null || jArr.size() == 0);
		System.out.println(ContainerTool.isEmpty(jArr));
	}
}
