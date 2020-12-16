package com.common.plan.location;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.ModeEnum;
import com.common.enums.PlanInfoEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.Map;

/**
 * @Description: 位置号码遗漏切换
 * @Author: null
 * @Date: 2020-08-24 16:43
 * @Version: v1.0
 */
public class LocationLostCutover extends PlanBase {

	public static LocationLostCutover getInstance() {
		return new LocationLostCutover();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getMonitorPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		//{"SELECT_AMOUNT_":"2","STATISTICS_PERIOD_":"10"}
		JSONObject expand = JSONObject.parseObject(planItem.getExpandInfo());
		if (!expand.containsKey(PlanInfoEnum.SELECT_AMOUNT_.name()) || !expand.containsKey(PlanInfoEnum.STATISTICS_PERIOD_.name())) {
			return true;
		}
		if (StringTool.isEmpty(expand.get(PlanInfoEnum.SELECT_AMOUNT_.name()))) {
			expand.put(PlanInfoEnum.SELECT_AMOUNT_.name(), "1");
		}
		if (StringTool.isEmpty(expand.get(PlanInfoEnum.STATISTICS_PERIOD_.name()))) {
			expand.put(PlanInfoEnum.STATISTICS_PERIOD_.name(), "10");
		}
		//方案组数据过滤
		JSONObject planGroupData = JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		String[] bets;
		StringBuilder betPlus;
		for (Map.Entry<String, Object> entry : planGroupData.entrySet()) {
			betPlus = new StringBuilder();
			data = JSONObject.parseObject(entry.getValue().toString());
			bets = data.getString("cold_hot_cutover").split("=");
			for (String bet : bets) {
				if (!"冷".equals(bet) && !"热".equals(bet)) {
					continue;
				}
				betPlus.append(bet).append("=");
			}
			if (betPlus.length() > 0) {
				betPlus.delete(betPlus.length() - 1, betPlus.length());
			}
			data.put("cold_hot_cutover", betPlus.toString());
			PlanDataFilterTool.intFilter("select",data,1,10);
			entry.setValue(data);
		}
		planItem.setPlanGroupData(planGroupData);
		return false;
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		JSONObject object;
		for (String activeKey : activeKeys) {
			object = planGroupData.getJSONObject(activeKey);
			if (StringTool.isEmpty(object.get("select"), object.get("cold_hot_cutover"))) {
				continue;
			}
			activePlanGroup.put(activeKey, object);
		}
		return activePlanGroup;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		int select = planGroupItem().getInteger("select");
		String coldHotCutover = planGroupItem().getString("cold_hot_cutover");
		String cutover = planGroupItem().getString("cutover");

		String[] coldOrHots = coldHotCutover.split("=");

		if (select <= 0) {
			return null;
		}
		JSONObject groupData = new JSONObject();
		try {
			int coldHotKey = 0;
			if (ContainerTool.notEmpty(historyMap)) {
				boolean execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
				int historyColdHotKey = NumberTool.getInteger(historyMap, "EXPAND_INFO_", 0);
				coldHotKey = ModeEnum.fundSwap(cutover, historyColdHotKey, execResult, coldOrHots.length);
				if (coldOrHots.length <= coldHotKey) {
					coldHotKey = 0;
				}
			}
			historyMap.put("EXPAND_INFO_", coldHotKey);
			String[][] hotAndColdData = CacheTool.getHotAndCold(gameCode(), handicapCode(), drawType(), period().findLotteryPeriod());
			if (ContainerTool.isEmpty(hotAndColdData)) {
				log.info("获取冷热数据为空，期数为：{}", period().findLotteryPeriod());
				return null;
			}
			String[] data = hotAndColdData[select - 1];
			StringBuilder bet = new StringBuilder();
			groupData.put("select", select);
			//扩展信息：选号数量，统计期数
			JSONObject expand = JSONObject.parseObject(expandInfo.toString());
			int amount = expand.getInteger("SELECT_AMOUNT_");
			if (amount >= data.length) {
				groupData.put("bet", StringUtils.join(data, ","));
				return groupData;
			}
			String coldOrHot = coldOrHots[coldHotKey];
			int index;
			for (int i = 0; i < amount; i++) {
				index = i;
				if ("冷".equals(coldOrHot)) {
					index = data.length - i - 1;
				}
				bet.append(data[index]).append(",");
			}
			if (monitor() != 0) {
				//一直反投（连续失败才会运行）
				boolean flag = valiBet(select, bet.toString().split(","));
				if (!flag) {
					return null;
				}
			}
			groupData.put("bet", bet.toString());
			return groupData;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return null;
		}
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		//解析数据
		String[] bets = groupData.getString("bet").split(",");
		int select = groupData.getInteger("select");

		//选择位置或者投注位置为空
		if (ContainerTool.isEmpty(bets)) {
			log.info("解析数据出错，选择位置:{},投注信息:{}", select, Arrays.toString(bets));
			return null;
		}
		try {
			Game game = gameCode().getGameFactory().game();
			int rankMin = game.getRankMin();
			int rankMax = game.getRankMax();
			StringBuilder result = new StringBuilder();
			String rank = PlanTool.getRankCn(select, gameCode());
			for (String bet : bets) {
				int num = Integer.parseInt(bet);
				if (num < rankMin || num > rankMax) {
					continue;
				}
				result.append(rank).append("|").append(num).append("|").append(fundTh).append("#");
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}

	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-位置投注
	 *
	 * @param select 选择位置
	 * @param bets   投注位置
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(int select, String[] bets) {
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
				//监控期数内，匹配成功失败一次，就返回
				if (matchNum(valiData, select, bets)) {
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
	 * 匹配号码 - 反投
	 *
	 * @param valiData 验证开奖数据
	 * @param select   选择位置《验证开奖数据》
	 * @param bets     投注位置《基准开奖数据》
	 * @return 匹配成功
	 */
	private boolean matchNum(String[] valiData, int select, String[] bets) {
		String vail = valiData[select - 1];
		for (String bet : bets) {
			if (baseData.length < Integer.parseInt(bet)) {
				continue;
			}
			if (bet.equals(vail)) {
				return true;
			}
		}
		return false;
	}
}
