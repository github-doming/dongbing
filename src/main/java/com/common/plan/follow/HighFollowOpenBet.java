package com.common.plan.follow;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.ModeEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 高级开某投某
 * @Author: null
 * @Date: 2020-09-10 10:51
 */
public class HighFollowOpenBet extends PlanBase {

	public static HighFollowOpenBet getInstance() {
		return new HighFollowOpenBet();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFollowPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData = JSONObject.parseObject(planItem.getPlanGroupData());

		JSONArray selects=planGroupData.getJSONArray("selects");
		JSONObject data;
		for(Object object:selects){
			data = (JSONObject) object;
			PlanDataFilterTool.intFilter("select",data,0,10);
		}
		JSONArray bets=planGroupData.getJSONArray("bets");
		for(Object object:bets){
			data = (JSONObject) object;
			PlanDataFilterTool.intFilter("select",data,0,20);
			PlanDataFilterTool.strFilter("bet",data,20);
		}
		planGroupData.put("selects",selects);
		planGroupData.put("bets",bets);
		planItem.setPlanGroupData(planGroupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONArray activeSelect = new JSONArray();
		JSONArray bets = planGroupData.getJSONArray("bets");
		String[] activeGroup = planGroupData.getString("activeKey").split(",");
		for (String group : activeGroup) {
			activeSelect.add(bets.getJSONObject(Integer.parseInt(group)));
		}
		JSONObject activePlanGroup = new JSONObject();
		JSONArray selects = planGroupData.getJSONArray("selects");
		for(String activeKey:activeKeys){
			JSONObject data = new JSONObject();
			JSONObject object=selects.getJSONObject(Integer.parseInt(activeKey));
			if(StringTool.isEmpty(object.get("select"))){
				continue;
			}
			data.put("selects", object);
			data.put("bets", activeSelect);
			activePlanGroup.put(activeKey, data);
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		JSONObject selects = planGroupItem().getJSONObject("selects");
		JSONArray bets = planGroupItem().getJSONArray("bets");

		String[] selectArray=selects.getString("select").split("=");
		int key=0;
		if(ContainerTool.notEmpty(historyMap)){
			String selectCutover=selects.getString("select_cutover");
			boolean execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
			String rankKey=historyMap.getOrDefault("EXPAND_INFO_","0").toString();
			key=ModeEnum.fundSwap(selectCutover, Integer.parseInt(rankKey), execResult, selectArray.length);
			if (selectArray.length <= key) {
				key=0;
			}
		}
		String[] ranks=selectArray[key].split(",");

		JSONObject groupData = new JSONObject();
		JSONObject bet;
		for(String rank:ranks){
			int num= NumberTool.getInteger(rank);
			if (num == 0 || num > baseData.length) {
				continue;
			}
			String open=baseData[num-1];
			for(Object object:bets){
				bet= (JSONObject) object;
				String select = bet.getString("select");
				if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2) {
					continue;
				}
				if (!open.equals(select)) {
					continue;
				}
				groupData.put(rank,bet.getString("bet"));
			}
		}
		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		StringBuilder result = new StringBuilder();

		Game game=gameCode().getGameFactory().game();
		int drawMin=game.getDrawNumberMin();
		int drawMax=game.getDrawNumberMax();

		String[] bets;
		for(Map.Entry<String, Object> entry:groupData.entrySet()){
			String rank = entry.getKey();
			bets = entry.getValue().toString().split(",");
			if (StringTool.isEmpty(rank) || ContainerTool.isEmpty(bets)) {
				log.info("解析数据出错，投注位置：{},投注信息：{}",rank, Arrays.toString(bets));
				continue;
			}
			String rankCn = PlanTool.getRankCn(Integer.parseInt(rank),gameCode());
			for (String bet : bets) {
				if(Integer.parseInt(bet)<drawMin||Integer.parseInt(bet)>drawMax){
					continue;
				}
				result.append(rankCn).append("|").append(bet).append("|").append(fundTh).append("#");
			}
		}

		return result.toString();
	}
}
