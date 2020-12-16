package com.common.plan.follow;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 号码追踪位置冷热排名
 * @Author: admin1
 * @Date: 2020-09-17 10:35
 */
public class TrackLocationHotAndCold extends PlanBase {
	public static TrackLocationHotAndCold getInstance() {
		return new TrackLocationHotAndCold();
	}
	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())){
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		for (Map.Entry<String, Object> entry : planGroupData.entrySet()) {
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.intFilter("rank",data,0,10);
			PlanDataFilterTool.intFilter("bet",data,0,10);
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

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		return planGroupItem();
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		//解析数据
		int bet = groupData.getInteger("bet");
		int rank = groupData.getInteger("rank");
		//投注位置或遗漏排名为空
		if (rank <= 0 || bet <= 0) {
			log.info("解析数据出错,方案组信息：{}", groupData);
			return null;
		}
		try {
			String[][] hotAndColdData = CacheTool.getLocationHotAndCold(gameCode(),handicapCode(),drawType(),period().findLotteryPeriod());
			if (ContainerTool.isEmpty(hotAndColdData)) {
				log.info("获取冷热排名为空，期数为：{}" , period().findLotteryPeriod());
				return null;
			}
			Game game=gameCode().getGameFactory().game();
			if(bet<game.getRankMin()||bet>game.getRankMax()){
				return null;
			}
			String betStr = hotAndColdData[bet-1][rank-1];
			String rankBet = PlanTool.getRankCn(bet,gameCode());
			assert rankBet != null;
			return rankBet.concat("|").concat(betStr).concat("|")+fundTh;
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{},异常信息={}", basePeriod(), groupData,e);
			return null;
		}
	}

}
