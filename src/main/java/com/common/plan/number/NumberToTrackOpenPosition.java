package com.common.plan.number;

import com.alibaba.fastjson.JSONArray;
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
 * 号码追踪开位投位
 *
 * @Author: Dongming
 * @Date: 2020-06-11 16:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class NumberToTrackOpenPosition extends PlanBase {
	public static NumberToTrackOpenPosition getInstance() {
		return new NumberToTrackOpenPosition();
	}


	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getMonitorPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		JSONObject expand = JSONObject.parseObject(planItem.getExpandInfo());
		if (!expand.containsKey(PlanInfoEnum.UN_INCREASE_.name())) {
			return true;
		}
		JSONObject planGroupData=JSONObject.parseObject(planItem.getPlanGroupData());
		JSONArray  planData = planGroupData.getJSONArray("selects");
		JSONObject data;
		for (int i = 0; i <planData.size() ; i++) {
			data = planData.getJSONObject(i);
			PlanDataFilterTool.intFilter("select",data,1,10);
			PlanDataFilterTool.strFilter("bet",data,10);
		}
		planItem.setPlanGroupData(planGroupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONArray activeSelect = new JSONArray();
		JSONArray selects = planGroupData.getJSONArray("selects");
		String[] activeGroup = planGroupData.getString("activeKey").split(",");
		for (String group : activeGroup) {
			activeSelect.add(selects.getJSONObject(Integer.parseInt(group)));
		}
		JSONObject activePlanGroup = new JSONObject();
		for (String activeKey : activeKeys) {
			JSONObject data = new JSONObject();
			data.put("activeKey", activeKey);
			data.put("selects", activeSelect);
			activePlanGroup.put(activeKey, data);
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		JSONArray selects = planGroupItem().getJSONArray("selects");
		String activeKey = planGroupItem().getString("activeKey");

		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor() != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = valiBet(selects, activeKey);
			if (!flag) {
				return null;
			}
		}
		String track = String.valueOf(NumberTool.getInteger(activeKey) + 1);
		//获取追踪号码上期开奖位置
		String openTrack =String.valueOf(ContainerTool.findIndex(baseData,track)+1);

		JSONObject groupData = new JSONObject();
		JSONArray betInfos = new JSONArray();
		//方案组详情
		for (Object object : selects) {
			JSONObject item = (JSONObject) object;
			String select = item.getString("select");
			if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2) {
				continue;
			}
			if (openTrack.equals(select)) {
				String bets = item.getString("bet");
				String[] betArr = bets.split(",");
				betInfos.addAll(Arrays.asList(betArr));
			}
		}
		groupData.put("bet", track);
		groupData.put("betInfos", betInfos);
		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			Game game=gameCode().getGameFactory().game();
			int rankMin=game.getRankMin();
			int rankMax=game.getRankMax();
			int bet = groupData.getInteger("bet");
			if (bet < rankMin || bet > rankMax) {
				log.error("投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
				return null;
			}
			JSONArray betInfos = groupData.getJSONArray("betInfos");
			StringBuilder result = new StringBuilder();

			for (Object betObj : betInfos) {
				int num=NumberTool.getInteger(betObj.toString());
				if(num<rankMin||num>rankMax){
					continue;
				}
				//投注位置
				String rank = PlanTool.getRankCn(num, gameCode());
				result.append(rank).append("|").append(bet).append("|").append(fundTh).append("#");
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}


	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-开某投某
	 *
	 * @param selects   方案组详情
	 * @param activeKey 名次
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(JSONArray selects, String activeKey) {
		try {
			//监控期数计算
			for (int i = 1; i <= monitor(); i++) {
				Object monitorPeriod = period().findBeforePeriod(basePeriod(), i);
				String valiDrawNumber = CacheTool.getDraw(gameCode(), handicapCode(), drawType(), monitorPeriod);
				if (StringTool.isEmpty(valiDrawNumber)) {
					log.info("获取验证数据失败，监控期数为：" + (monitorPeriod));
					return false;
				}
				String[] valiData = valiDrawNumber.split(",");

				//监控期数内，匹配失败一次，就返回
				if (matchNum(activeKey, valiData, selects)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}
	/**
	 * 号码追踪开位投位
	 * @param activeKey	追踪号码
	 * @param valiData	基准号码
	 * @param selects		方案组详情
	 * @return
	 */
	private boolean matchNum(String activeKey, String[] valiData, JSONArray selects) {
		String track = String.valueOf(NumberTool.getInteger(activeKey) + 1);
		String valiIndex =String.valueOf(ContainerTool.findIndex(valiData,track)+1);

		for (Object object : selects) {
			JSONObject item = (JSONObject) object;
			String select = item.getString("select");
			if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2 || !valiIndex.equals(select)) {
				continue;
			}
			String bets = item.getString("bet");
			if (ContainerTool.isEmpty(bets.split(","))) {
				continue;
			}
			for (String bet : bets.split(",")) {
				String vali = baseData[NumberTool.getInteger(bet)-1];
				if (activeKey.equals(vali)) {
					return true;
				}
			}
		}
		return false;
	}

}
