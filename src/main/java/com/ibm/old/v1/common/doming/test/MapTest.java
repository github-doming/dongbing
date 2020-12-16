package com.ibm.old.v1.common.doming.test;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.common.doming.core.BaseTest;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.servlet.ibm_plan_statistics.ibm_event_planstatistics.t.service.IbmEventPlanstatisticsTService;
import net.sf.json.JSONObject;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-01-09 10:00
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MapTest extends BaseTest {

	@Test public void test() throws Exception {
		super.connectionBegin("");
		System.out.println(GameTool.findId(IbmGameEnum.PK10.name()));
		super.connectionEnd();
	}

	@Test public void test01() {
		Map<String, Integer> map = new HashMap<>(5);
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 4);
		System.out.println(map);
		System.out.println(map.putIfAbsent("12", 12));
		System.out.println(map);
	}

	@Test public void test02() {
		List<Map<String, Integer>> list = new ArrayList<>();
		Map<String, Integer> map;

		for (int i = 0; i < 10; i++) {
			map = new HashMap<>(1);
			map.put("key", i);
			list.add(map);
		}
		list.get(5).put("key", 555555);
		System.out.println(list);

	}

	/**
	 * ①②③
	 */
	@Test public void test03() {
		int len = 2;
		long time1, time2;
		Map<String, List<Integer>> map = new HashMap<>(1);
		List<Integer> list = new ArrayList<>();
		time1 = System.nanoTime();
		for (int i = 0; i < len; i++) {
			list.add(i);
			map.put("num", list);
		}
		time2 = System.nanoTime();
		System.out.println("直接放入耗时=" + (double) (time2 - time1) / 1000000 + "ms");

		Map<String, List<Integer>> map1 = new HashMap<>(1);
		time1 = System.nanoTime();
		for (int i = 0; i < len; i++) {
			if (map1.containsKey("num")) {
				map1.get("num").add(i);
			} else {
				List<Integer> list1 = new ArrayList<>();
				list.add(i);
				map1.put("num", list1);
			}
		}
		time2 = System.nanoTime();
		System.out.println("存在放入耗时=" + (double) (time2 - time1) / 1000000 + "ms");
	}

	@Test public void test04() {
		Boolean flag = null;
		Map<String, Boolean> map = new HashMap<>(1);
		map.put("flag", flag);
		if (map.get("flag")) {
			System.out.println("OK");
		}
	}

	@Test public void test06() throws SQLException {
		long start, end, start1, end1;
		Connection connection = super.connectionBegin(null);

		IbmEventPlanstatisticsTService planstatisticsTService = new IbmEventPlanstatisticsTService();

		planstatisticsTService.transaction(planstatisticsTService::findNewEvent);

		start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			beginTransaction(connection, Connection.TRANSACTION_REPEATABLE_READ);
			try {
				planstatisticsTService.findNewEvent();
			} catch (Exception e) {
				rollTransaction(connection);
			} finally {
				endTransaction(connection);
			}

		}
		end = System.currentTimeMillis();

		start1 = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			planstatisticsTService.transaction(planstatisticsTService::findNewEvent);
		}
		end1 = System.currentTimeMillis();

		System.out.println("执行时间1=" + (end - start));
		System.out.println("执行时间2=" + (end1 - start1));
		super.connectionEnd();

	}

	public static void main(String[] args) throws Exception {
		String url = "http://132.232.130.99:9080/ibm_main/ibm/pc/ibm_plan_user/editState.do" ;
		JSONObject data = new JSONObject();
		data.put("PLAN_CODE_", "LOCATION_BET_NUMBER");
		data.put("GAME_CODE_", "PK10");
		data.put("STATE_", "CLOSE");
		JSONObject json = new JSONObject();
		json.put("token", "d833b40afc284a24b36300d7655a4382");
		json.put("time", System.currentTimeMillis());
		json.put("data", data);
		Map<String, Object> map = new HashMap<>(1);
		map.put("json", json);
		while (true){
			json.put("time", System.currentTimeMillis());
			JSONObject result = JSONObject.fromObject( HttpClientTool.doPost(url,map));
			System.out.println(json);
			if (!"200".equals(result.getString("code"))){
				System.out.println(result);
				break;
			}
			Thread.sleep(10);
		}

	}

}
