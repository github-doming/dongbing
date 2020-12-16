package com.common.plan;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.game.Game;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import com.google.common.collect.ImmutableList;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 高级杀号
 * @Author: null
 * @Date: 2020-09-10 10:48
 */
public class HighKillNumber extends PlanBase {

	public static HighKillNumber getInstance() {
		return new HighKillNumber();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getBetMode(),
				planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"LIMIT_AMOUNT_":"0"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());
		if(!expand.containsKey(PlanInfoEnum.LIMIT_AMOUNT_.name())){
			return true;
		}
		int limitAmount=NumberTool.getInteger(expand.get(PlanInfoEnum.LIMIT_AMOUNT_.name()));
		if(limitAmount<0){
			expand.put(PlanInfoEnum.LIMIT_AMOUNT_.name(),"0");
			planItem.setExpandInfo(expand);
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());

		JSONObject data;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.intFilter("select",data,0,10);
			PlanDataFilterTool.intFilter("bet",data,0,10);
			PlanDataFilterTool.strFilter("bet_appear",data,10);
			entry.setValue(data);
		}
		planItem.setPlanGroupData(planGroupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		JSONObject data;
		for (String activeKey : activeKeys) {
			data=planGroupData.getJSONObject(activeKey);
			if (data.getInteger("select") == 0 || data.getInteger("bet") == 0) {
				continue;
			}
			activePlanGroup.put(activeKey, planGroupData.get(activeKey));
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		int select = planGroupItem().getInteger("select");
		int bet = planGroupItem().getInteger("bet");
		String appear = planGroupItem().getString("bet_appear");

		//TODO 限制数量暂不处理
//		JSONObject expand=JSONObject.parseObject(expandInfo.toString());
//		int limitAmount=expand.getInteger(PlanInfoEnum.LIMIT_AMOUNT_.name());
		if (StringTool.notEmpty(appear)) {
			String[] appears = appear.split(",");
			String betDraw = baseData[bet - 1];
			if (!StringTool.contains(appears, betDraw)) {
				return null;
			}
		}
		String draw = baseData[select - 1];

		//方案组详情
		JSONObject groupData = new JSONObject();
		groupData.put("select", draw);
		groupData.put("bet", bet);
		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		//解析数据
		String bet = groupData.getString("bet");
		String select = groupData.getString("select");

		List<String> numbers = ImmutableList.<String>builder().add("1").add("2").add("3").add("4").add("5").add("6")
				.add("7").add("8").add("9").add("10").build();

		Game game=gameCode().getGameFactory().game();
		int rankMin=game.getRankMin();
		int rankMax=game.getRankMax();
		int num=Integer.parseInt(bet);
		if(num<rankMin||num>rankMax){
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
		String rank = PlanTool.getRankCn(num, gameCode());

		StringBuilder result=new StringBuilder();
		for(String number:numbers){
			if(number.equals(select)){
				continue;
			}
			result.append(rank).append("|").append(number).append("|").append(fundTh).append("#");
		}
		return result.toString();
	}
}
