package com.common.plan.number;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 统计号码下号码
 * @Author: null
 * @Date: 2020-09-10 10:54
 */
public class TotalNumberToNumber extends PlanBase {

	public static TotalNumberToNumber getInstance() {
		return new TotalNumberToNumber();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getFundSwapMode(),planItem.getPeriodRollMode())){
			return true;
		}
		//{"UN_INCREASE_":"CLOSE"}
		JSONObject expand=JSONObject.parseObject(planItem.getExpandInfo());
		if(!expand.containsKey(PlanInfoEnum.UN_INCREASE_.name())){
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());

		JSONObject data;
		for(Map.Entry<String, Object> entry:planGroupData.entrySet()){
			data=JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.strFilter("select",data,10);
			PlanDataFilterTool.strFilter("bet",data,10);
			PlanDataFilterTool.strFilter("total_number",data,20);
			PlanDataFilterTool.strFilter("bet_number",data,20);
			PlanDataFilterTool.intFilter("total_period",data,0,20);
			PlanDataFilterTool.intFilter("number_condition",data,0,20);
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
			if (data.getInteger("total_period") == 0 || data.getInteger("number_condition") == 0 ||
					StringTool.isEmpty(data.get("select"), data.get("bet"), data.get("total_number"), data.get("bet_number"))) {
				continue;
			}
			activePlanGroup.put(activeKey, data);
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		String[] selects = planGroupItem().getString("select").split(",");
		String[] totalNumber = planGroupItem().getString("total_number").split(",");
		int totalPeriod = planGroupItem().getInteger("total_period");
		int numberCondition = planGroupItem().getInteger("number_condition");

		try {
			int total=0;
			//遍历选择位置
			for(String select:selects){
				int num=NumberTool.getInteger(select);
				if(num<=0||num>10){
					continue;
				}
				//统计期数内计算统计号码的数量条件
				for(int i=0;i<totalPeriod;i++){
					Object monitorPeriod = period().findBeforePeriod(basePeriod(), i);
					String valiDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), monitorPeriod);
					if (StringTool.isEmpty(valiDrawNumber)) {
						continue;
					}
					String[] valiData = valiDrawNumber.split(",");
					String vali=valiData[num-1];
					if(StringTool.contains(totalNumber,vali)){
						total++;
					}
				}
				if(total==numberCondition){
					break;
				}else{
					total=0;
				}
			}
			if(total!=numberCondition){
				return null;
			}
			//方案组详情
			JSONObject groupData = new JSONObject();
			groupData.put("select", planGroupItem().getString("bet"));
			groupData.put("bet", planGroupItem().getString("bet_number"));
			return groupData;
		} catch (Exception e) {
			log.error("验证方案组失败", e);
			return null;
		}

	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//扩展信息：不中停止新增
			JSONObject expand = JSONObject.parseObject(expandInfo.toString());
			if (StateEnum.OPEN.name().equals(expand.get(PlanInfoEnum.UN_INCREASE_.name())) && ContainerTool.notEmpty(historyMap)) {
				boolean execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
				if(!execResult){
					log.info("触发扩展信息，不中停止新增,方案组信息:{}", groupData);
					return null;
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
			Integer[] betsInt = NumberTool.intValue(bets);
			assert betsInt != null;
			Game game=gameCode().getGameFactory().game();
			int rankMin=game.getRankMin();
			int rankMax=game.getRankMax();
			StringBuilder result = new StringBuilder();
			for (String select : selects) {
				int num=Integer.parseInt(select);
				if(num<rankMin||num>rankMax){
					continue;
				}
				String rank = PlanTool.getRankCn(num, gameCode());
				for(int bet:betsInt){
					if(bet<rankMin||bet>rankMax){
						continue;
					}
					result.append(rank).append("|").append(bet).append("|").append(fundTh).append("#");
				}
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}
}
