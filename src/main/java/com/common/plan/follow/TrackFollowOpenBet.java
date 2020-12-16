package com.common.plan.follow;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.plan.PlanBase;
import com.common.tools.PlanTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 号码追踪下开某投某
 * @Author: admin1
 * @Date: 2020-09-17 10:35
 */
public class TrackFollowOpenBet extends PlanBase {
	public static TrackFollowOpenBet getInstance() {
		return new TrackFollowOpenBet();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		JSONObject expand = JSONObject.parseObject(planItem.getExpandInfo());
		return !expand.containsKey(PlanInfoEnum.UN_INCREASE_.name());
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();

		for (String activeKey : activeKeys) {
			JSONObject data = new JSONObject();
			data.put("activeKey", activeKey);
			activePlanGroup.put(activeKey, data);
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		String activeKey = planGroupItem().getString("activeKey");
		int select = NumberTool.getInteger(activeKey);
		JSONObject data = new JSONObject();
		data.put("bet", baseData[select]);
		data.put("select", select + 1);
		return data;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		String bet = groupData.getString("bet");
		int select = groupData.getInteger("select");
		if (StringTool.isEmpty(bet) || select == 0) {
			log.info("解析数据出错，选择位置:{},投注信息:{}", select, bet);
			return null;
		}
		String rank = PlanTool.getRankCn(select, gameCode());
		assert rank != null;
		return rank.concat("|").concat(bet).concat("|").concat(fundTh + "#");
	}
}
