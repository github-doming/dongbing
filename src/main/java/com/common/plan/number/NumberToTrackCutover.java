package com.common.plan.number;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.ModeEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 号码追踪切换
 * @Author: null
 * @Date: 2020-08-20 16:09
 * @Version: v1.0
 */
public class NumberToTrackCutover extends PlanBase {
	public static NumberToTrackCutover getInstance() {
		return new NumberToTrackCutover();
	}


	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getMonitorPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		JSONObject planGroupData = JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		StringBuilder betPlus, trackPlus;
		String[] bets, tracks;
		for (Map.Entry<String, Object> entry : planGroupData.entrySet()) {
			data = JSONObject.parseObject(entry.getValue().toString());
			betPlus = new StringBuilder();
			bets = data.getString("bet").split("=");
			for (String bet : bets) {
				for (String b : bet.split(",")) {
					if (NumberTool.getInteger(b) <= 0 || NumberTool.getInteger(b) > 10) {
						continue;
					}
					betPlus.append(b).append(",");
				}
				if (betPlus.length() > 0) {
					betPlus.delete(betPlus.length() - 1, betPlus.length());
				}
				betPlus.append("=");
			}
			if (betPlus.length() > 0) {
				betPlus.delete(betPlus.length() - 1, betPlus.length());
			}else{
				betPlus.append("0");
			}
			trackPlus = new StringBuilder();
			tracks = data.getString("track").split("=");
			for (String track : tracks) {
				// 追踪号码如果有多个矫正为第一个
				track = track.split(",")[0];
				int num=NumberTool.getInteger(track);
				if (num <= 0 || num > 10) {
					continue;
				}
				trackPlus.append(track).append("=");
			}

			if (trackPlus.length() > 0) {
				trackPlus.delete(trackPlus.length() - 1, trackPlus.length());
			}else{
				trackPlus.append("0");
			}
			data.put("track", trackPlus.toString());
			data.put("bet", betPlus.toString());
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
			if("0".equals(data.get("track"))||"0".equals(data.get("bet"))){
				continue;
			}
			activePlanGroup.put(activeKey, planGroupData.get(activeKey));
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		String track = planGroupItem().getString("track");
		String bet = planGroupItem().getString("bet");
		String trackCutover = planGroupItem().getString("track_cutover");
		String betCutover = planGroupItem().getString("bet_cutover");
		if(StringTool.isEmpty(track,bet,trackCutover,betCutover)){
			log.info("解析数据出错，方案组数据为：{}", planGroupItem());
			return null;
		}
		//解析数据
		String[] trackArr = track.split("=");
		String[] betArr = bet.split("=");

		int trackIndex = 0, betIndex = 0;
		if (ContainerTool.notEmpty(historyMap)) {
			String indexStr = historyMap.get("EXPAND_INFO_").toString();
			boolean execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());

			trackIndex = ModeEnum.fundSwap(trackCutover, Integer.parseInt(indexStr.split("_")[0]), execResult, trackArr.length);
			betIndex = ModeEnum.fundSwap(betCutover, Integer.parseInt(indexStr.split("_")[1]), execResult, betArr.length);

			trackIndex = trackIndex >= trackArr.length ? 0 : trackIndex;
			betIndex = betIndex >= betArr.length ? 0 : betIndex;
		}
		historyMap.put("EXPAND_INFO_", trackIndex + "_" + betIndex);

		String betStr = betArr[betIndex];
		String trackStr = trackArr[trackIndex];

		//方案组详情
		JSONObject groupData = new JSONObject();
		groupData.put("track", trackStr);
		groupData.put("bet", betStr);
		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//解析数据
			String track = groupData.getString("track");
			String betStr = groupData.getString("bet");
			if(StringTool.isEmpty(track,betStr)){
				log.info("解析数据出错，投注号码：{},追踪信息：{}", betStr, track);
				return null;
			}
			Game game=gameCode().getGameFactory().game();
			int drawNumberMin=game.getDrawNumberMin();
			int drawNumberMax=game.getDrawNumberMax();

			String[] bets = betStr.split(",");

			StringBuilder result = new StringBuilder();

			int index = ContainerTool.findIndex(baseData, track);
			//投注位置
			String rank = PlanTool.getRankCn(index + 1, gameCode());
			for (String bet : bets) {
				if (Integer.parseInt(bet) < drawNumberMin || Integer.parseInt(bet) > drawNumberMax) {
					continue;
				}
				result.append(rank).append("|").append(bet).append("|").append(fundTh).append("#");
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}
}
