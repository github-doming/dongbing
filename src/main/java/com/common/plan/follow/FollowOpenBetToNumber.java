package com.common.plan.follow;

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
 * @Description: 开某投某指定位置
 * @Author: null
 * @Date: 2020-09-10 10:50
 */
public class FollowOpenBetToNumber extends PlanBase {

	public static FollowOpenBetToNumber getInstance() {
		return new FollowOpenBetToNumber();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getMonitorPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		//{"SAME_DOUBLE_":"CLOSE"}
		JSONObject expand = JSONObject.parseObject(planItem.getExpandInfo());
		if (!expand.containsKey(PlanInfoEnum.SAME_DOUBLE_.name())) {
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData = JSONObject.parseObject(planItem.getPlanGroupData());

		JSONArray selects=planGroupData.getJSONArray("selects");
		JSONObject data;
		for(Object object:selects){
			data = (JSONObject) object;
			PlanDataFilterTool.intFilter("select",data,0,10);
			PlanDataFilterTool.strFilter("location",data,10);
			PlanDataFilterTool.strFilter("bet",data,20);
		}
		planGroupData.put("selects",selects);
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
		String open = baseData[Integer.parseInt(activeKey)];
		JSONObject groupData = new JSONObject();
		JSONObject data;
		//方案组详情
		for (int i = 0; i < selects.size(); i++) {
			JSONObject item = selects.getJSONObject(i);
			String select = item.getString("select");
			if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2) {
				continue;
			}
			if (!open.equals(select)) {
				continue;
			}
			data = new JSONObject();
			data.put("rank", item.get("location"));
			data.put("bet", item.getString("bet"));
			groupData.put(String.valueOf(i), data);
		}
		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//TODO 扩展信息：下注相同内容时才翻倍
			JSONObject data;
			String[] bets;
			String[] ranks;
			StringBuilder result = new StringBuilder();
			Game game = gameCode().getGameFactory().game();
			int rankMin = game.getRankMin();
			int rankMax = game.getRankMax();
			int drawMin = game.getDrawNumberMin();
			int drawMax = game.getDrawNumberMax();
			//解析数据
			for (Map.Entry<String, Object> entry : groupData.entrySet()) {
				data = JSONObject.parseObject(entry.getValue().toString());
				bets = data.getString("bet").split(",");
				ranks = data.getString("rank").split(",");
				if (ContainerTool.isEmpty(ranks) || ContainerTool.isEmpty(bets)) {
					log.info("解析数据出错，投注位置：{},投注信息：{}", Arrays.toString(ranks), Arrays.toString(bets));
					continue;
				}
				for (String rank : ranks) {
					int num = NumberTool.getInteger(rank);
					if (num < rankMin || num > rankMax) {
						log.info("解析数据出错，投注位置：{},投注信息：{}", rank, Arrays.toString(bets));
						continue;
					}
					String rankCn = PlanTool.getRankCn(num, gameCode());
					for (String bet : bets) {
						if (Integer.parseInt(bet) < drawMin || Integer.parseInt(bet) > drawMax) {
							continue;
						}
						result.append(rankCn).append("|").append(bet).append("|").append(fundTh).append("#");
					}
				}
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

				String vali = valiData[Integer.parseInt(activeKey)];
				//监控期数内，匹配失败一次，就返回
				if (matchNum(vali, selects)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}

	private boolean matchNum(String vali, JSONArray selects) {
		for (Object object : selects) {
			JSONObject item = (JSONObject) object;
			String select = item.getString("select");
			if (ContainerTool.isEmpty(select.split(",")) || select.split(",").length >= 2 || !vali.equals(select)) {
				continue;
			}
			String bets = item.getString("bet");
			String locations = item.getString("location");
			if (ContainerTool.isEmpty(bets.split(",")) || ContainerTool.isEmpty(locations.split(","))) {
				continue;
			}
			String[] betArray = bets.split(",");
			String[] locationArray = locations.split(",");
			for (String bet : betArray) {
				for (String location : locationArray) {
					int index = NumberTool.getInteger(location);
					if (index > 10 || index <= 0) {
						continue;
					}
					String open = baseData[index - 1];
					if (open.equals(bet)) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
