package com.common.plan.twoside;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.ModeEnum;
import com.common.enums.PlanInfoEnum;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 双面位置遗漏切换
 * @Author: null
 * @Date: 2020-08-24 17:56
 * @Version: v1.0
 */
public class DoubleLocationLostCutover extends PlanBase {

	public static DoubleLocationLostCutover getInstance() {
		return new DoubleLocationLostCutover();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getMonitorPeriod(), planItem.getBetMode(),
				planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		JSONObject expand = JSONObject.parseObject(planItem.getExpandInfo());
		if (!expand.containsKey(PlanInfoEnum.STATISTICS_PERIOD_.name())) {
			return true;
		}
		JSONObject data;
		StringBuilder cutoverPlus;
		String cutovers;
		JSONObject planGroupData = JSONObject.parseObject(planItem.getPlanGroupData());
		for (Map.Entry<String, Object> entry : planGroupData.entrySet()) {
			data = JSONObject.parseObject(entry.getValue().toString());
			cutovers = data.getString("cold_hot_cutover");
			cutoverPlus = new StringBuilder();
			for (String cutover : cutovers.split("=")) {
				if(!"冷".equals(cutover)&&!"热".equals(cutover)){
					continue;
				}
				cutoverPlus.append(cutover).append("=");
			}
			if (cutoverPlus.length() > 0) {
				cutoverPlus.delete(cutoverPlus.length() - 1, cutoverPlus.length());
			}
			data.put("cold_hot_cutover", cutoverPlus);
			PlanDataFilterTool.intFilter("select",data,1,10);
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
		int select = planGroupItem().getInteger("select");
		String clodHotCutover = planGroupItem().getString("cold_hot_cutover");
		String cutover = planGroupItem().getString("cutover");
		String doubleSideSelect = planGroupItem().getString("double_side_select");
		JSONObject expand = JSONObject.parseObject(expandInfo.toString());
		int statisticePeriod = expand.getInteger(PlanInfoEnum.STATISTICS_PERIOD_.name());

		JSONObject groupData = new JSONObject();
		try {
			String[][] hotAndColdData = CacheTool.getHotAndCold(gameCode(), handicapCode(), drawType(), period().findLotteryPeriod());
			if (ContainerTool.isEmpty(hotAndColdData)) {
				log.info("获取冷热排名为空，期数为：{}", period().findLotteryPeriod());
				return null;
			}
			boolean execResult = false;
			int lastIndex = -1;

			if (ContainerTool.notEmpty(historyMap)) {
				execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
				lastIndex = Integer.parseInt(historyMap.get("EXPAND_INFO_").toString());
			}
			String[] clodHots = clodHotCutover.split(",");
			int planIndex = 0;
			if (lastIndex != -1) {
				planIndex = ModeEnum.fundSwap(cutover, lastIndex, execResult, clodHots.length);
				planIndex = planIndex >= clodHots.length ? 0 : planIndex;
			}
			String[] selectData = hotAndColdData[select - 1];
			String[] selectDragonData = hotAndColdData[hotAndColdData.length - select];

			historyMap.put("EXPAND_INFO_", planIndex);
			String cutoverType = clodHots[planIndex];
			int hotNumber = NumberTool.getInteger(selectData[0]);
			int clodNumber = NumberTool.getInteger(selectData[statisticePeriod - 1]);
			String hotBet, clodBet;
			switch (doubleSideSelect) {
				case "SIZE":
					hotBet = hotNumber <= 5 ? "小" : "大";
					clodBet = clodNumber <= 5 ? "小" : "大";
					break;
				case "DOUBLE_SIDE":
					hotBet = hotNumber % 2 == 0 ? "双" : "单";
					clodBet = clodNumber % 2 == 0 ? "双" : "单";
					break;
				case "DRAGON_TIGER":
					hotBet = hotNumber > NumberTool.getInteger(selectDragonData[0]) ? "龙" : "虎";
					clodBet = clodNumber > NumberTool.getInteger(selectDragonData[statisticePeriod - 1]) ? "龙" : "虎";
					break;
				default:
					log.error("错误的切换模式:", doubleSideSelect);
					return null;
			}
			String bet = "冷".equals(cutoverType) ? clodBet : hotBet;

			//监控期数=0，即不监控，所有方案都按规则生成投注项
			if (monitor() != 0) {
				boolean flag = valiBet(hotAndColdData, select, bet, doubleSideSelect);
				if (flag) {
					return null;
				}
			}
			//方案组详情
			groupData.put("select", select);
			groupData.put("bet", bet);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		//解析数据
		String bet = groupData.getString("bet");
		int select = groupData.getInteger("select");
		if (StringTool.isEmpty(bet)) {
			log.info("解析数据出错,方案组信息：{}", groupData);
			return null;
		}
		try {
			String rank = PlanTool.getRankCn(select, gameCode());
			return rank + "|" + bet + "|" + fundTh;
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{},异常信息={}", basePeriod(), groupData, e);
			return null;
		}
	}


	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功 - 跟上期双面
	 */
	private boolean valiBet(String[][] hotAndColdData, int select, String bet, String doubleSideSelect) {
		try {
			String[] valiData = hotAndColdData[select];
			if (ContainerTool.isEmpty(valiData)) {
				log.info("获取验证数据失败，监控期数为：" + select);
				return false;
			}
			String openType = null;
			int openData;
			for (int i = 1; i <= monitor(); i++) {
				openData = NumberTool.getInteger(valiData[i]);
				switch (doubleSideSelect) {
					case "SIZE":
						openType = openData > 5 ? "大" : "小";
						break;
					case "DOUBLE_SIDE":
						openType = openData % 2 == 0 ? "双" : "单";
						break;
					case "DRAGON_TIGER":
						openType = openData > NumberTool.getInteger(hotAndColdData[hotAndColdData.length - select - 1]) ? "龙" : "虎";
						break;
					default:
						log.error("错误的切换模式:", doubleSideSelect);
						openType = "";
						break;
				}
			}
			return bet.equals(openType);

		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}
}
