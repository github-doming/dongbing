package com.ibm.old.v1.pc.ibm_handicap_member.t.controller;
import com.ibm.old.v1.cloud.ibm_profit.t.service.IbmProfitTService;
import com.ibm.old.v1.cloud.ibm_profit_vr.t.service.IbmProfitVrTService;
import com.ibm.old.v1.common.doming.core.CommMethod;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 盘口页面控制器
 * @Author: zjj
 * @Author: wck
 * @Date: 2019-01-12 16:43
 * @Version: v1.0
 */
public class HandicapInfoController implements CommMethod {

	public JSONArray execute(String handicapMemberIds) throws Exception {

		List<String> handicapMemberIdList = Arrays.asList(handicapMemberIds.split(","));
		
		//当前用户所有在登录的会员账号信息
		IbmPcHandicapMemberTService ibmHandicapMemberTService = new IbmPcHandicapMemberTService();
		List<Map<String, Object>> list=ibmHandicapMemberTService.listLoginAccount(handicapMemberIdList);

		// 真实投注结果
		IbmProfitTService ibmProfitTService = new IbmProfitTService();
		Map<String, Map<String, Object>> realBet= ibmProfitTService.listProfit(handicapMemberIdList);

		// 模拟投注结果
		IbmProfitVrTService ibmProfitVrTService = new IbmProfitVrTService();
		Map<String, Map<String, Object>> vrBet= ibmProfitVrTService.listProfitVr(handicapMemberIdList);

		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject;
		for(Map<String,Object> map:list){
			jsonObject=new JSONObject();
			double betRate = NumberTool.doubleT(map.get("BET_RATE_T_"));
			map.put("BET_RATE_T_", betRate);
			double profitLimitMax = NumberTool.doubleT(map.get("PROFIT_LIMIT_MAX_T_"));
			map.put("PROFIT_LIMIT_MAX_T_", profitLimitMax);
			double profitLimitMin = NumberTool.doubleT(map.get("PROFIT_LIMIT_MIN_T_"));
			map.put("PROFIT_LIMIT_MIN_T_", profitLimitMin);
			double lossLimitMin = NumberTool.doubleT(map.get("LOSS_LIMIT_MIN_T_"));
			map.put("LOSS_LIMIT_MIN_T_", lossLimitMin);
			double resetProfitMax = NumberTool.doubleT(map.get("RESET_PROFIT_MAX_T_"));
			map.put("RESET_PROFIT_MAX_T_", resetProfitMax);
			double resetLossMin = NumberTool.doubleT(map.get("RESET_LOSS_MIN_T_"));
			map.put("RESET_LOSS_MIN_T_", resetLossMin);
			map.put("LANDED_TIME_", DateTool.getLongTime(map.get("LANDED_TIME_")));
			if(map.get("PROFIT_LIMIT_MIN_T_")!=null&&!"0.0".equals(map.get("PROFIT_LIMIT_MIN_T_").toString())){
				map.put("isStopProfit", "TRUE");
			}else{
				map.put("isStopProfit", "FALSE");
			}
			String handicapMemberId = map.get("handicapMemberId").toString();
			Map<String,Object> handicapInfo = new HashMap<>(4);
			handicapInfo.put("realBet", realBet.get(handicapMemberId));
			handicapInfo.put("vrBet", vrBet.get(handicapMemberId));
			if (map.get("MEMBER_INFO_")!=null) {
				JSONObject desc = JSONObject.fromObject(map.get("MEMBER_INFO_"));
				handicapInfo.put("NICKNAME", desc.get("NICKNAME"));
				handicapInfo.put("BALANCE", desc.get("BALANCE"));
			}
			map.remove("MEMBER_INFO_");
			jsonObject.put("set",map);
			jsonObject.put("handicapInfo",handicapInfo);
			jsonObject.put("handicapMemberId",handicapMemberId);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
}