package com.ibm.old.v1.servlet.ibm_plate.common.job;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlateGetOddsAndBet {
	
	private static final String HOST="http://192.168.2.140:8080";
	
	private static final String PROJECT="/a";
	
	private static final String PLATE_ODDS_URL = HOST+PROJECT+"/ibm/ibm_plate/getPlateOdds.do";
	
	private static final String PLATE_BET_URL=HOST+PROJECT+"/ibm/ibm_plate/getPlateBet.do";
	
	/**
	 * 获取挡板赔率
	 *
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getOdds() throws Exception{
		return getOdds(PLATE_ODDS_URL);
	}
	/**
	 * 获取挡板赔率
	 *
	 * @return
	 * @throws Exception
	 */
	private static JSONObject getOdds(String url) throws Exception {

		JSONObject jsonObject = JSONObject.fromObject(HttpTool.doPost(url, null));

		return JSONObject.fromObject(jsonObject.get("data"));
	}
	/**
	 * 投注
	 *
	 * @param betItemList 投注项
	 * @param result 赔率
	 * @return
	 * @throws Exception
	 */
	public static JSONObject getBetting(List<String> betItemList, JSONObject result) throws Exception {
		return getBetting(betItemList, result, PLATE_BET_URL);
	}
	/**
	 * 投注
	 *
	 * @param betItemList 投注项
	 * @param result 赔率
	 * @return
	 * @throws Exception
	 */
	private static JSONObject getBetting(List<String> betItemList, JSONObject result,String url) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		
		for (String item : betItemList) {
			String[] betStr =item.split("#");
			JSONObject odds = result.getJSONObject(betStr[0]);
			String gameplayCode = odds.getString("gameplayCode");
			stringBuilder.append(gameplayCode, 0, gameplayCode.length() - 2).append("|")
					.append(gameplayCode.substring(gameplayCode.length() - 2)).append("|")
					.append(NumberTool.doubleT(odds.getString("odds"))).append("|").append(betStr[1]).append(";");
		}
		stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		Map<String, Object> parameterMap = new HashMap<>(1);
		parameterMap.put("t", stringBuilder);

		String parm = StringUtils.strip(parameterMap.toString(), "{}");
	
		return JSONObject.fromObject(HttpTool.doPost(url.concat("?").concat(parm), null));
	}
}
