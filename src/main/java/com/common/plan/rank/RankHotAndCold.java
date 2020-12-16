package com.common.plan.rank;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 冷热排名
 *
 * @Author: Dongming
 * @Date: 2020-06-11 16:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class RankHotAndCold extends PlanBase {
	public static RankHotAndCold getInstance() {
		return new RankHotAndCold();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),planItem.getBetMode(),
				planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//反投，不管号码重复{"UN_REPEAT_":"CLOSE","INVERSE_":"CLOSE"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());
		if(!expand.containsKey(PlanInfoEnum.UN_REPEAT_.name()) || !expand.containsKey(PlanInfoEnum.INVERSE_.name())){
			return true;
		}
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.intFilter("bet",data,1,10);
			PlanDataFilterTool.strFilter("rank",data,10);
			entry.setValue(data);
		}
		planItem.setPlanGroupData(planGroupData);
		return false;
	}
	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		for (String activeKey : activeKeys) {
			activePlanGroup.put(activeKey, planGroupData.get(activeKey));
		}
		return activePlanGroup;
	}

	@Override public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		return planGroupItem();
	}
	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		//解析数据
		String[] ranks = groupData.getString("rank").split(",");
		//投注位置或遗漏排名为空
		if (StringTool.isEmpty(groupData.get("bet")) || groupData.getString("bet").length() != 1 || ContainerTool.isEmpty(ranks)) {
			log.info("解析数据出错,方案组信息：{}", groupData);
			return null;
		}
		try {
			String[][] hotAndColdData = CacheTool.getHotAndCold(gameCode(),handicapCode(),drawType(),period().findLotteryPeriod());
			if (ContainerTool.isEmpty(hotAndColdData)) {
				log.info("获取冷热排名为空，期数为：{}" , period().findLotteryPeriod());
				return null;
			}
			Game game=gameCode().getGameFactory().game();
			int bet = groupData.getInteger("bet");
			if(bet<game.getRankMin()||bet>game.getRankMax()){
				return null;
			}

			Integer[] ranksInt = NumberTool.intValue(ranks);
			assert ranksInt != null;
			StringBuilder result = new StringBuilder();
			String number;

			int lenMax=game.getDrawNumberMax()-game.getDrawNumberMin()+1;
			String rank = PlanTool.getRankCn(bet,gameCode());
			for (int rankInt : ranksInt) {
				if (rankInt < 1 || rankInt > lenMax) {
					continue;
				}
				//第几号排名
				number = hotAndColdData[bet - 1][rankInt - 1];
				result.append(rank).append("|").append(number).append("|").append(fundTh).append("#");
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{},异常信息={}", basePeriod(), groupData,e);
			return null;
		}
	}

}
