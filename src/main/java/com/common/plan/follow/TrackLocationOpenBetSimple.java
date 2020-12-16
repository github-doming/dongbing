package com.common.plan.follow;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 普通号码追踪开位投某
 * @Author: admin1
 * @Date: 2020-09-17 10:35
 */
public class TrackLocationOpenBetSimple extends PlanBase {
	public static TrackLocationOpenBetSimple getInstance() {
		return new TrackLocationOpenBetSimple();
	}
	@Override
	public boolean valiData(PlanItem planItem) {
		if(StringTool.isEmpty(planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())){
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONArray selects=planGroupData.getJSONArray("selects");
		JSONObject data;
		for(Object object:selects){
			data = (JSONObject) object;
			PlanDataFilterTool.intFilter("select",data,0,10);
			PlanDataFilterTool.strFilter("bet",data,10);
		}
		planGroupData.put("selects",selects);
		planItem.setPlanGroupData(planGroupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONArray activeSelect=new JSONArray();
		JSONArray selects=planGroupData.getJSONArray("selects");
		String[] activeGroup=planGroupData.getString("activeKey").split(",");
		for(String group:activeGroup){
			activeSelect.add(selects.getJSONObject(Integer.parseInt(group)));
		}
		JSONObject activePlanGroup = new JSONObject();
		for (String activeKey : activeKeys) {
			JSONObject data=new JSONObject();
			data.put("activeKey",activeKey);
			data.put("selects",activeSelect);
			activePlanGroup.put(activeKey, data);
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		int activeKey = planGroupItem().getInteger("activeKey")+1;
		JSONArray selects = planGroupItem().getJSONArray("selects");
		try {
			//追踪号码
			String track=String.valueOf(activeKey);

			Object monitorPeriod = period().findBeforePeriod(basePeriod(), 1);
			String valiDrawNumber = CacheTool.getDraw(gameCode(),handicapCode(), drawType(), monitorPeriod);
			if (StringTool.isEmpty(valiDrawNumber)) {
				log.info("获取验证数据失败，监控期数为：" + (monitorPeriod));
				return null;
			}
			String[] valiData = valiDrawNumber.split(",");
			// 上期追踪号码所在位置（本期投注位置）
			int index = ContainerTool.findIndex(valiData, track);
			// 本期开奖号码，对应去找select位置
			String open = baseData[index];
			// 获取本期追踪号码所在位置
			index = ContainerTool.findIndex(baseData, track);

			JSONObject groupData = new JSONObject();
			//方案组详情
			JSONObject item;
			for (int i = 0; i <selects.size() ; i++) {
				item = selects.getJSONObject(i);
				String select = item.getString("select");
				if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2) {
					continue;
				}
				if (!open.equals(select)) {
					continue;
				}
				if (groupData.containsKey("rank")) {
					groupData.put("bet", groupData.getString("bet").concat(",").concat(item.getString("bet")));
				} else {
					groupData.put("rank",index);
					groupData.put("bet", item.getString("bet"));
				}
			}
			return groupData;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return null;
		}
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//解析数据
			String[] bets = groupData.getString("bet").split(",");
			if (StringTool.isEmpty(groupData.get("rank")) || ContainerTool.isEmpty(bets)) {
				log.info("解析数据出错，投注位置：{},投注信息：{}",groupData.get("rank"), Arrays.toString(bets));
				return null;
			}
			int rank = groupData.getInteger("rank");
			StringBuilder result = new StringBuilder();

			Game game=gameCode().getGameFactory().game();
			int drawMin=game.getDrawNumberMin();
			int drawMax=game.getDrawNumberMax();

			String rankCn = PlanTool.getRankCn(rank + 1,gameCode());
			for (String bet : bets) {
				if(Integer.parseInt(bet)<drawMin||Integer.parseInt(bet)>drawMax){
					continue;
				}
				result.append(rankCn).append("|").append(bet).append("|").append(fundTh).append("#");
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}
}
