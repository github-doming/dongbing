package com.common.plan.firstsecond;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.plan.PlanBase;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 冠亚和开某投某
 * @Author: null
 * @Date: 2020-09-10 10:53
 */
public class FirstAndSecondFollowOpenBet extends PlanBase {

	public static FirstAndSecondFollowOpenBet getInstance() {
		return new FirstAndSecondFollowOpenBet();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getMonitorPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData = JSONObject.parseObject(planItem.getPlanGroupData());
		planItem.setPlanGroupData(betFilter(planGroupData));
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		for (String activeKey : activeKeys) {
			JSONObject data = new JSONObject();
			data.put("select", planGroupData.getJSONObject(activeKey).getString("select"));
			data.put("bet", planGroupData.getJSONObject(activeKey).getString("bet"));
			activePlanGroup.put(activeKey, data);
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		String[] selects = planGroupItem().getString("select").split(",");
		int sum = NumberTool.getInteger(baseData[0]) + NumberTool.getInteger(baseData[1]);

		JSONObject groupData = new JSONObject();
		for (String select : selects) {
			if (sum == NumberTool.getInteger(select)) {
				groupData.put("bet", planGroupItem().getString("bet"));
				break;
			}
		}
		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		String[] bets = groupData.getString("bet").split(",");
		//投注位置为空
		if (ContainerTool.isEmpty(bets)) {
			log.info("解析数据出错，投注信息:{}", Arrays.toString(bets));
			return null;
		}
		String rank = PlanTool.getRankCn(0, gameCode());
		StringBuilder result = new StringBuilder();
		for (String bet : bets) {
			int num = Integer.parseInt(bet);
			if (num < 3 || num > 19) {
				continue;
			}
			result.append(rank).append("|").append(num).append("|").append(fundTh).append("#");

		}
		return result.toString();
	}

	private JSONObject betFilter(JSONObject planGroupData) {
		String[] bets, selects;
		StringBuilder betPlus, selectPlus;
		JSONObject data;

		for (Map.Entry<String, Object> entry : planGroupData.entrySet()) {
			betPlus = new StringBuilder();
			selectPlus = new StringBuilder();
			data = JSONObject.parseObject(entry.getValue().toString());
			selects = data.getString("select").split(",");
			for (String select : selects) {
				if (NumberTool.getInteger(select) > 19 || NumberTool.getInteger(select) < 3) {
					continue;
				}
				selectPlus.append(select).append(",");
			}
			if (selectPlus.length() > 0) {
				selectPlus.delete(selectPlus.length() - 1, selectPlus.length());
			}
			data.put("select", selectPlus);

			bets = data.getString("bet").split(",");
			for (String bet : bets) {
				if (NumberTool.getInteger(bet) > 19 || NumberTool.getInteger(bet) < 3) {
					continue;
				}
				betPlus.append(bet).append(",");
			}
			if (betPlus.length() > 0) {
				betPlus.delete(betPlus.length() - 1, betPlus.length());
			}
			data.put("bet", betPlus);
			entry.setValue(data);
		}
		return planGroupData;
	}
}
