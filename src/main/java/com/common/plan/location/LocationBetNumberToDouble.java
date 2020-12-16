package com.common.plan.location;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
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
 * @Description: 位置投注一起翻倍
 * @Author: null
 * @Date: 2020-09-03 15:16
 * @Version: v1.0
 */
public class LocationBetNumberToDouble extends PlanBase {

	public static LocationBetNumberToDouble getInstance() {
		return new LocationBetNumberToDouble();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),planItem.getMonitorPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"UN_INCREASE_":"CLOSE","INVERSE_":"CLOSE"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());

		if(!expand.containsKey(PlanInfoEnum.FUNDS_OPTIONS_.name())){
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.strFilter("select",data,10);
			PlanDataFilterTool.strFilter("bet",data,10);
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
		JSONObject planGroup=new JSONObject();
		planGroup.put("0",activePlanGroup);
		return planGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		return planGroupItem();
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try{
			StringBuilder result = new StringBuilder();
			JSONObject data;
			String[] bets;
			String[] selects;
			Integer[] selectsInt;
			Game game=gameCode().getGameFactory().game();
			int rankMin=game.getRankMin();
			int rankMax=game.getRankMax();
			for(Map.Entry<String, Object> entry:groupData.entrySet()){
				data=JSONObject.parseObject(entry.getValue().toString());
				//解析数据
				bets = data.getString("bet").split(",");
				selects = data.getString("select").split(",");
				//选择位置或者投注位置为空
				if (ContainerTool.isEmpty(selects) || ContainerTool.isEmpty(bets)) {
					log.info("解析数据出错，选择位置:{},投注信息:{}", Arrays.toString(selects), Arrays.toString(bets));
					continue;
				}
				selectsInt = NumberTool.intValue(selects);
				assert selectsInt != null;
				for (String bet : bets) {
					int num=Integer.parseInt(bet);
					if(num<rankMin||num>rankMax){
						continue;
					}
					String rank = PlanTool.getRankCn(num, gameCode());
					for (Integer select : selectsInt) {
						if(select<rankMin||select>rankMax){
							continue;
						}
						String number = baseData[select - 1];
						result.append(rank).append("|").append(number).append("|").append(fundTh).append("#");
					}
				}
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}
}
