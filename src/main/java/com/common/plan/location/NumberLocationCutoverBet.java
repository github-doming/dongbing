package com.common.plan.location;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.ModeEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanDataFilterTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 号码位置切换投注
 * @Author: null
 * @Date: 2020-08-24 17:59
 * @Version: v1.0
 */
public class NumberLocationCutoverBet extends PlanBase {

	public static NumberLocationCutoverBet getInstance() {
		return new NumberLocationCutoverBet();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getMonitorPeriod(), planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}

		JSONObject planGroupData = JSONObject.parseObject(planItem.getPlanGroupData());
		PlanDataFilterTool.selectAndBetCutoverFilter(planGroupData, "select", "bet", false);
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
		// 号码
		String[] numberArr = planGroupItem().getString("select").split("=");
		// 位置
		String[] placeArr = planGroupItem().getString("bet").split("=");

		String numberCutover = planGroupItem().getString("select_cutover");
		String placeCutover = planGroupItem().getString("bet_cutover");

		boolean execResult = false;
		String lastIndex = "";

		if (ContainerTool.notEmpty(historyMap)) {
			execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
			lastIndex = historyMap.get("EXPAND_INFO_").toString();
		}

		int numberIndex = 0, placeIndex = 0;
		if (StringTool.notEmpty(lastIndex)) {
			numberIndex = ModeEnum.fundSwap(numberCutover, Integer.parseInt(lastIndex.split("_")[0]), execResult, numberArr.length);
			placeIndex = ModeEnum.fundSwap(placeCutover, Integer.parseInt(lastIndex.split("_")[1]), execResult, placeArr.length);

			numberIndex = numberIndex >= numberArr.length ? 0 : numberIndex;
			placeIndex = placeIndex >= placeArr.length ? 0 : placeIndex;
		}
		historyMap.put("EXPAND_INFO_", numberIndex + "_" + placeIndex);

		String numberStr = numberArr[numberIndex];
		String placeStr = placeArr[placeIndex];

		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor() != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = valiBet(placeStr, numberStr);
			if (!flag) {
				return null;
			}
		}
		//方案组详情
		JSONObject groupData = new JSONObject();
		groupData.put("select", placeStr);
		groupData.put("bet", numberStr);

		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		//解析数据
		String[] bets = groupData.getString("bet").split(",");
		String[] selects = groupData.getString("select").split(",");
		if (ContainerTool.isEmpty(bets)||ContainerTool.isEmpty(selects)) {
			log.info("解析数据出错,方案组信息：{}", groupData);
			return null;
		}
		try {
			Game game=gameCode().getGameFactory().game();
			int rankMin=game.getRankMin();
			int rankMax=game.getRankMax();

			StringBuilder result = new StringBuilder();
			for (String bet : bets) {
				int num=Integer.parseInt(bet);
				if(num<rankMin||num>rankMax){
					continue;
				}
				String rank = PlanTool.getRankCn(num, gameCode());

				for (String select : selects) {
					int number = NumberTool.getInteger(bet);
					if(number<rankMin||number>rankMax){
						continue;
					}
					select = baseData[number - 1];
					result.append(rank).append("|").append(select).append("|").append(fundTh).append("#");
				}
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{},异常信息={}", basePeriod(), groupData, e);
			return null;
		}
	}
	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-开某投某
	 *
	 * @param numberStr 号码
	 * @param placeStr  名次
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(String placeStr, String numberStr) {
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
				if (matchNum(valiData, placeStr, numberStr)) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}

	private boolean matchNum(String[] valiData, String placeStr, String selects) {
		String open;
		for (String place : placeStr.split(",")) {
			int num=NumberTool.getInteger(place);
			if(num>valiData.length||num<=0){
				continue;
			}
			open = valiData[num - 1];
			for(String select:selects.split(",")){
				if(open.equals(select)){
					return true;
				}
			}
		}
		return false;
	}

}
