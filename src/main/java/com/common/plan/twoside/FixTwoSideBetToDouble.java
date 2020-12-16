package com.common.plan.twoside;


import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.PlanInfoEnum;
import com.common.enums.TypeEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * @Description: 固定双面投注一起翻倍
 * @Author: Dongming
 * @Date: 2020-06-11 16:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class FixTwoSideBetToDouble extends PlanBase {

	public static FixTwoSideBetToDouble getInstance() {
		return new FixTwoSideBetToDouble();
	}


	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getMonitorPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		JSONObject expand = JSONObject.parseObject(planItem.getExpandInfo());
		return !expand.containsKey(PlanInfoEnum.FUNDS_OPTIONS_.name());
	}

	@Override
	public JSONObject advance(String[] activeKeys, JSONObject planGroupData) {
		JSONObject activePlanGroup = new JSONObject();
		JSONObject activePlanGroupDouble = new JSONObject();
		for (String activeKey : activeKeys) {
			activePlanGroup.put(activeKey, planGroupData.get(activeKey));
		}
		activePlanGroupDouble.put("0", activePlanGroup);
		return activePlanGroupDouble;
	}

	@Override
	public JSONObject splice(Map<String, Object> historyMap, Object expandInfo) {
		JSONObject betGroupData;

		for (Map.Entry<String, Object> planGroup : planGroupItem().entrySet()) {
			betGroupData = JSONObject.parseObject(planGroup.getValue().toString());
			String stateStr = betGroupData.getString("state");
			if (!TypeEnum.TRUE.name().equalsIgnoreCase(stateStr) && !TypeEnum.FALSE.name().equalsIgnoreCase(stateStr)) {
				log.info("获取方案组数据失败，方案组数据:{}", planGroupItem().toString());
				continue;
			}
			boolean state = Boolean.parseBoolean(stateStr);
			String code = betGroupData.getString("code");
			//监控期数=0，即不监控，所有方案都按规则生成投注项
			if (monitor() != 0) {
				//一直反投（连续失败才会运行）
				boolean flag = valiBet(state, code);
				if (!flag) {
					return null;
				}
			}
		}
		return planGroupItem();
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			StringBuilder result = new StringBuilder();
			Game game = gameCode().getGameFactory().game();
			JSONObject betData;
			boolean state;
			String[] code;
			for (Map.Entry<String, Object> planGroup : groupData.entrySet()) {
				betData = JSONObject.parseObject(planGroup.getValue().toString());
				state = betData.getBoolean("state");
				code = betData.getString("code").split("_");
				int numIndex = Integer.parseInt(code[0]);
				int typeIndex = Integer.parseInt(code[1]);

				switch (gameCode()) {
					case XYFT:
					case JS10:
					case SELF_10_2:
					case PK10:
					case COUNTRY_10:
						betContentNumber(result, state, numIndex, typeIndex, game, fundTh);
						break;
					case JSSSC:
					case CQSSC:
					case SELF_SSC_5:
					case COUNTRY_SSC:
						betContentBall(result, state, numIndex, typeIndex, game, fundTh);
						break;
					case XYNC:
					case GDKLC:
						betContentHappy(result, state, numIndex, typeIndex, game, fundTh);
						break;
					default:
						log.warn("非法的游戏类型，类型为" + gameCode().name());
						return null;
				}
			}
			return result.toString();
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}

	private void betContentHappy(StringBuilder result, boolean state, int numIndex, int typeIndex, Game game, long fundTh) {
		int number;
		if (numIndex == 8) {
			//总和
			number = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]) + Integer.parseInt(baseData[2])
					+ Integer.parseInt(baseData[3]) + Integer.parseInt(baseData[4]) + Integer.parseInt(baseData[5])
					+ Integer.parseInt(baseData[6]) + Integer.parseInt(baseData[7]);
			result.append("总和");
		} else if (numIndex < 0 || numIndex > 8) {
			throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
		} else {
			number = Integer.parseInt(baseData[numIndex]);
			if (typeIndex == 4) {
				result.append("总和");
			} else {
				result.append(PlanTool.getRankCn(numIndex + 1, gameCode()));
			}
		}
		switch (typeIndex) {
			//大小
			case 0:
				String size;
				if (numIndex == 8) {
					//总和和
					if (number == 84) {
						return;
					}
					size = game.sumSize(number, state);
				} else {
					size = game.size(number, state);
				}
				result.append("|").append(size);
				break;
			//单双
			case 1:
				result.append("|").append(game.single(number, state));
				break;
			//尾大尾小
			case 2:
				result.append("|").append(game.tailSize(number, state));
				break;
			//合单合双
			case 3:
				result.append("|").append(game.sumSingle(number, state));
				break;
			//龙虎
			case 4:
				int number2 = Integer.parseInt(baseData[7]);
				result.append("|").append(game.dragon(number, number2, state));
				break;
			default:
				throw new RuntimeException("非法的类型捕捉，类型为" + typeIndex);
		}
		result.append("|").append(fundTh).append("#");
	}


	private void betContentBall(StringBuilder result, boolean state, int numIndex, int typeIndex, Game game, long fundTh) {
		int number;
		if (numIndex == 5) {
			//总和
			number = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]) + Integer.parseInt(baseData[2])
					+ Integer.parseInt(baseData[3]) + Integer.parseInt(baseData[4]);
			result.append("总和");
		} else if (numIndex < 0 || numIndex > 5) {
			log.warn("非法的排序捕捉，索引为" + numIndex);
			return;
		} else {
			number = Integer.parseInt(baseData[numIndex]);
			if (typeIndex == 2) {
				numIndex = 6;
			}
			result.append(PlanTool.getRankCn(numIndex + 1, gameCode()));
		}
		switch (typeIndex) {
			case 0:
				//大小
				String size;
				if (numIndex == 5) {
					//总和大小
					size = game.sumSize(number, state);
				} else {
					size = game.size(number, state);
				}
				result.append("|").append(size);
				break;
			case 1:
				//单双
				result.append("|").append(game.single(number, state));
				break;
			case 2:
				//龙虎
				if (numIndex != 6) {
					throw new RuntimeException("非法的排序捕捉，索引为" + numIndex);
				}
				int number2 = Integer.parseInt(baseData[4]);
				String dTSum = game.dragon(number, number2, state);
				//龙虎和不投注
				if ("和".equals(dTSum)) {
					return;
				}
				result.append("|").append(dTSum);
				break;
			default:
				log.warn("非法的类型捕捉，类型为" + typeIndex);
				return;
		}
		result.append("|").append(fundTh).append("#");
	}

	private void betContentNumber(StringBuilder result, boolean state, int numIndex, int typeIndex, Game game, long fundTh) {

		int number;
		if (numIndex == 10) {
			//冠亚和
			number = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]);
			result.append("冠亚");
		} else if (numIndex < 0 || numIndex > 10) {
			log.warn("非法的排序捕捉，索引为" + numIndex);
			return;
		} else {
			result.append(PlanTool.getRankCn(numIndex + 1, gameCode()));
			number = Integer.parseInt(baseData[numIndex]);
		}
		switch (typeIndex) {
			case 0:
				//大小
				String size;
				if (numIndex == 10) {
					//冠亚和大小
					size = game.sumSize(number, state);
				} else {
					size = game.size(number, state);
				}
				result.append("|").append(size);
				break;
			case 1:
				//单双
				result.append("|").append(game.single(number, state));
				break;
			case 2:
				//龙虎
				if (numIndex > 5) {
					log.warn("非法的排序捕捉，索引为" + numIndex);
					return;
				}
				int number2 = Integer.parseInt(baseData[baseData.length - numIndex - 1]);
				result.append("|").append(game.dragon(number, number2, state));
				break;
			default:
				log.warn("非法的类型捕捉，类型为" + typeIndex);
				return;
		}
		result.append("|").append(fundTh).append("#");
	}

	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功 - 固定双面投注
	 *
	 * @param state 操作状态
	 * @param code  操作类型
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(boolean state, String code) {
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
				try {
					//监控期数内，匹配失败一次，就返回
					if (PlanTool.matchNum(baseData, valiData, state, code, gameCode())) {
						return false;
					}
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			log.warn("验证方案组失败", e);
			return false;
		}
	}
}
