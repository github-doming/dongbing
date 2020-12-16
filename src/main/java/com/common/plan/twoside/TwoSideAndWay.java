package com.common.plan.twoside;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.ModeEnum;
import com.common.enums.PlanInfoEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 双面组合路子
 * @Author: null
 * @Date: 2020-08-20 16:07
 * @Version: v1.0
 */
public class TwoSideAndWay extends PlanBase {
	public static TwoSideAndWay getInstance() {
		return new TwoSideAndWay();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getBetMode(),
				planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		JSONObject expand = JSONObject.parseObject(planItem.getExpandInfo());
		if (!expand.containsKey(PlanInfoEnum.ADVANCE_MODE_.name())) {
			return true;
		}
		JSONObject groupData = JSONObject.parseObject(planItem.getPlanGroupData());

		JSONObject data;
		StringBuilder betPlus;
		for (Map.Entry<String, Object> entry : groupData.entrySet()) {
			data = JSONObject.parseObject(entry.getValue().toString());
			betPlus = new StringBuilder();
			for (String bet : data.getString("bet").split(",")) {
				if (StringTool.TWO_SIDE_AND_WAY.contains(bet)) {
					if (StringTool.contains(bet, "龙", "虎") && data.getInteger("select") > 5) {
						continue;
					}
					betPlus.append(bet).append(",");
				}
			}
			if (betPlus.length() > 0) {
				betPlus.delete(betPlus.length() - 1, betPlus.length());
			}
			data.put("bet", betPlus);
			PlanDataFilterTool.intFilter("select",data,1,10);
			entry.setValue(data);
		}
		planItem.setPlanGroupData(groupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		JSONObject data;
		for (String activeKey : activeKeys) {
			data=planGroupData.getJSONObject(activeKey);
			if(StringTool.isEmpty(data.get("bet"))||data.getInteger("select")==0){
				continue;
			}
			activePlanGroup.put(activeKey, planGroupData.get(activeKey));
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		int select = planGroupItem().getInteger("select");
		String[] betArr = planGroupItem().getString("bet").split(",");
		JSONObject expand = JSONObject.parseObject(expandInfo.toString());
		String advanceMode = expand.getString(PlanInfoEnum.ADVANCE_MODE_.name());
		try {
			int lastIndex = 0;
			if (ContainerTool.notEmpty(historyMap)) {
				boolean execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
				lastIndex = Integer.parseInt(historyMap.get("EXPAND_INFO_").toString());
				lastIndex = ModeEnum.fundSwap(advanceMode, lastIndex, execResult, betArr.length);
				lastIndex = lastIndex >= betArr.length ? 0 : lastIndex;
			}
			historyMap.put("EXPAND_INFO_", lastIndex);

			int number = Integer.parseInt(baseData[select - 1]);
			//对位号码
			int conNumber = Integer.parseInt(baseData[baseData.length - select]);
			String betType = betArr[lastIndex];
			if(betType.length()>4){
				return null;
			}
			boolean state = betType.contains("正投");
			if (betType.length()>1){
				Game game = gameCode().getGameFactory().game();
				String size = game.size(number,state);
				String single = game.single(number,state);
				String dragon = game.dragon(number,conNumber,state);

				if (betType.contains(size)) {
					betType = size;
				} else if (betType.contains(single)) {
					betType = single;
				} else if (betType.contains(dragon)) {
					betType = dragon;
				}
			}
			//方案组详情
			JSONObject groupData = new JSONObject();
			groupData.put("select", select);
			groupData.put("bet", betType);
			return groupData;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return null;
		}
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			Game game = gameCode().getGameFactory().game();
			int select = groupData.getInteger("select");
			if(select<game.getRankMin()||select>game.getRankMax()){
				return null;
			}
			String bet = groupData.getString("bet");

			return PlanTool.getRankCn(select, gameCode()) +"|" + bet + "|" + fundTh;
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}
}
