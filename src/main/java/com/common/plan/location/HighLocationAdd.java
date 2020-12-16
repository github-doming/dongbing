package com.common.plan.location;

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

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 高级位置加号
 * @Author: null
 * @Date: 2020-08-19 10:24
 * @Version: v1.0
 */
public class HighLocationAdd extends PlanBase {

	public static HighLocationAdd getInstance() {
		return new HighLocationAdd();
	}


	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFollowPeriod(),
				planItem.getBetMode(),planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"UN_INCREASE_":"CLOSE","INVERSE_":"CLOSE"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());
		if(!expand.containsKey(PlanInfoEnum.RENEW_PERIOD_.name()) || !expand.containsKey(PlanInfoEnum.TAKE_NUMBER_ADD_.name())){
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
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		return  planGroupItem();
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//扩展信息：N期换新号，取号相加
			JSONObject expand = JSONObject.parseObject(expandInfo.toString());
			//取号相加
			int takeNumberAdd=expand.getInteger(PlanInfoEnum.TAKE_NUMBER_ADD_.name());
			int renewPeriod = expand.getInteger(PlanInfoEnum.RENEW_PERIOD_.name());
			if (ContainerTool.notEmpty(historyMap) && renewPeriod > 1) {
				//历史期的基准期数
				Object oldBasePeriod = historyMap.get("BASE_PERIOD_");
				for (int i = 1; i < renewPeriod; i++) {
					Object basePeriod=gameCode().getGameFactory().period(handicapCode()).findBeforePeriod(basePeriod(),i-1);
					if(basePeriod.equals(oldBasePeriod)){
						getPlanGroup().setBasePeriod(basePeriod);
						String baseDrawNumber=CacheTool.getDraw(gameCode(),handicapCode(), drawType(), basePeriod());
						baseData = baseDrawNumber.split(",");
						break;
					}
				}
			}
			//解析数据
			String[] bets = groupData.getString("bet").split(",");
			String[] selects = groupData.getString("select").split(",");
			//选择位置或者投注位置为空
			if (ContainerTool.isEmpty(selects) || ContainerTool.isEmpty(bets)) {
				log.info("解析数据出错，选择位置:{},投注信息:{}", Arrays.toString(selects), Arrays.toString(bets));
				return null;
			}
			Integer[] selectsInt = NumberTool.intValue(selects);
			assert selectsInt != null;
			StringBuilder result = new StringBuilder();

			Game game=gameCode().getGameFactory().game();
			int rankMin=game.getRankMin();
			int rankMax=game.getRankMax();
			int drawNumberMax=game.getDrawNumberMax();
			int drawNumberMin=game.getDrawNumberMin();

			for(String bet:bets){
				int num= NumberTool.getInteger(bet);
				if(num<rankMin||num>rankMax){
					continue;
				}
				String rank = PlanTool.getRankCn(num, gameCode());
				for (Integer select : selectsInt) {
					if(select<rankMin||select>rankMax){
						continue;
					}
					int number=NumberTool.getInteger(baseData[select - 1])+takeNumberAdd;
					if(number>drawNumberMax){
						number=number%drawNumberMax+drawNumberMin;
					}
					result.append(rank).append("|").append(number).append("|").append(fundTh).append("#");
				}
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}

}
