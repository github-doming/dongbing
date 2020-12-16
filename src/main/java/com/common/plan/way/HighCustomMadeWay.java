package com.common.plan.way;

import com.alibaba.fastjson.JSONObject;
import com.common.entity.PlanItem;
import com.common.enums.ModeEnum;
import com.common.game.Game;
import com.common.plan.PlanBase;
import com.common.tools.CacheTool;
import com.common.tools.PlanTool;
import com.google.common.collect.ImmutableList;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 高级自定义路子
 * @Author: null
 * @Date: 2020-09-10 11:03
 */
public class HighCustomMadeWay extends PlanBase {

	public static HighCustomMadeWay getInstance() {
		return new HighCustomMadeWay();
	}

	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getMonitorPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData = JSONObject.parseObject(planItem.getPlanGroupData());
		planItem.setPlanGroupData(betFilter(planGroupData));
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
		String[] selectArr = planGroupItem().getString("select").split("=");
		String selectCutover = planGroupItem().getString("select_cutover");
		int selectIndex = 0;
		if (ContainerTool.notEmpty(historyMap)) {
			boolean execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
			selectIndex = ModeEnum.fundSwap(selectCutover, NumberTool.getInteger(historyMap.get("EXPAND_INFO_")), execResult, selectArr.length);

			selectIndex = selectIndex >= selectArr.length ? 0 : selectIndex;
		}
		historyMap.put("EXPAND_INFO_", selectIndex);

		String selectStr = selectArr[selectIndex];

		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor() != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = valiBet(selectStr);
			if (!flag) {
				return null;
			}
		}
		//方案组详情
		JSONObject groupData = new JSONObject();
		groupData.put("bet", selectStr);

		return groupData;
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {

		String[] bets = groupData.getString("bet").split(",");
		if (ContainerTool.isEmpty(bets)) {
			log.info("解析数据出错，投注信息：{}", Arrays.toString(bets));
			return null;
		}
		Game game = gameCode().getGameFactory().game();
		int rankMin = game.getRankMin();
		int rankMax = game.getRankMax();
		int rank;
		String[] info;
		StringBuilder result = new StringBuilder();
		for (String bet : bets) {
			info = bet.split("-");
			if (checkBet(info[1],info[0])) {
				continue;
			}
			int select = NumberTool.getInteger(info[0]);
			rank = select;
			// 冠亚,总和...
			if (select == 0) {
				rank = 10;
			}
			if (rank < rankMin || rank > rankMax) {
				continue;
			}
			result.append(PlanTool.getRankCn(select, gameCode())).append("|").append(info[1]).append("|").append(fundTh).append("#");
		}
		return result.toString();
	}


	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-开某投某
	 *
	 * @param selectStr 取号位置
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(String selectStr) {
		try {
			String[] valiData,selects = selectStr.split(",");
			//监控期数计算
			for (int i = 1; i <= monitor(); i++) {
				Object monitorPeriod = period().findBeforePeriod(basePeriod(), i);
				String valiDrawNumber = CacheTool.getDraw(gameCode(), handicapCode(), drawType(), monitorPeriod);
				if (StringTool.isEmpty(valiDrawNumber)) {
					log.info("获取验证数据失败，监控期数为：" + (monitorPeriod));
					return false;
				}
				valiData = valiDrawNumber.split(",");
				//监控期数内，匹配失败一次，就返回
				if (matchNum(valiData, selects)) {
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
	 * @param valiData 验证开奖数据
	 * @return 匹配成功
	 */
	public boolean matchNum(String[] valiData, String[] selects) {
		String[] bets;
		int rank;
		//		投注位		验证位
		String betNumber,  valiOpen;
		for (String select : selects) {
			bets = select.split("-");
			rank = NumberTool.getInteger(bets[0]) - 1;
			betNumber = bets[1];

			//这里不验证冠亚、总和
			if (rank >= 0) {
				valiOpen = valiData[rank];
				if (valiOpen.equals(betNumber)) {
					return true;
				}
			}
			switch (gameCode()) {
				case PK10:
				case JS10:
				case XYFT:
				case SELF_10_2:
				case COUNTRY_10:
					return matchNumber(valiData, rank, betNumber);
				case SELF_SSC_5:
				case COUNTRY_SSC:
				case CQSSC:
				case JSSSC:
					return matchBall(valiData, rank, betNumber);
				case GDKLC:
				case XYNC:
					return matchHappy(valiData, rank, betNumber);
				default:
					throw new RuntimeException("非法的类型捕捉，类型为" + gameCode().name());
			}
		}
		return true;
	}

	private boolean matchHappy(String[] valiData, int rank, String betNumberStr) {
		int baseNum;
		int valiNum;
		int threshold = 10;
		if (rank < 0) {
			//总和
			baseNum = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]) + Integer.parseInt(baseData[2])
					+ Integer.parseInt(baseData[3]) + Integer.parseInt(baseData[4]) + Integer.parseInt(baseData[5])
					+ Integer.parseInt(baseData[6]) + Integer.parseInt(baseData[7]);
			valiNum = Integer.parseInt(valiData[0]) + Integer.parseInt(valiData[1]) + Integer.parseInt(valiData[2])
					+ Integer.parseInt(valiData[3]) + Integer.parseInt(valiData[4]) + Integer.parseInt(valiData[5])
					+ Integer.parseInt(valiData[6]) + Integer.parseInt(valiData[7]);
			threshold = 84;
		} else if (rank > 8) {
			throw new RuntimeException("非法的排序捕捉，索引为" + rank);
		} else {
			baseNum = Integer.parseInt((baseData[rank]));
			valiNum = Integer.parseInt((valiData[rank]));
		}
		int betNumber = NumberTool.getInteger(betNumberStr);
		if (betNumber != 0) {
			if (betNumber > 20 || betNumber < 0) {
				return true;
			}
		} else {
			switch (betNumberStr) {
				case "总和":
					if (threshold == 84 && (baseNum == threshold || valiNum == threshold)) {
						return false;
					}
					return isBoS(baseNum, valiNum, threshold);
				case "大":
				case "小":
					return isBoS(baseNum, valiNum, threshold);
				case "单":
				case "双":
					return isParity(baseNum, valiNum);
				case "尾大":
				case "尾小":
					return tailSize(baseNum) == tailSize(valiNum);
				case "合单":
				case "合双":
					return sumSingle(baseNum) == sumSingle(valiNum);
				case "龙":
				case "虎":
					int base2Num = Integer.parseInt(baseData[7]);
					int vali2Num = Integer.parseInt(valiData[7]);
					return isDoT(baseNum, valiNum, base2Num, vali2Num);
				default:
					throw new RuntimeException("非法的类型捕捉，类型为" + betNumberStr);
			}
		}
		return false;

	}

	/**
	 *
	 */
	private boolean matchBall(String[] valiData, int rank, String betNumberStr) {
		int baseNum;
		int valiNum;
		int threshold = 4;
		if (rank < 0) {
			//总和
			baseNum = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]) + Integer.parseInt(baseData[2])
					+ Integer.parseInt(baseData[3]) + Integer.parseInt(baseData[4]);
			valiNum = Integer.parseInt(valiData[0]) + Integer.parseInt(valiData[1]) + Integer.parseInt(valiData[2])
					+ Integer.parseInt(valiData[3]) + Integer.parseInt(valiData[4]);
			threshold = 22;
		} else if (rank > 5) {
			throw new RuntimeException("非法的排序捕捉，索引为" + rank);
		} else {
			baseNum = Integer.parseInt((baseData[rank]));
			valiNum = Integer.parseInt((valiData[rank]));
		}
		int betNumber = NumberTool.getInteger(betNumberStr);
		if (betNumber != 0) {
			if (betNumber > 10 || betNumber < 0) {
				return true;
			}
		} else {
			switch (betNumberStr) {
				case "大":
				case "小":
					return isBoS(baseNum, valiNum, threshold);
				case "单":
				case "双":
					return isParity(baseNum, valiNum);
				case "龙":
				case "虎":
					int base2Num = Integer.parseInt(baseData[4]);
					int vali2Num = Integer.parseInt(valiData[4]);
					//和的暂时不投注
					if (baseNum == base2Num || valiNum == vali2Num) {
						return true;
					}
					return isDoT(baseNum, valiNum, base2Num, vali2Num);
				default:
					throw new RuntimeException("非法的类型捕捉，类型为" + betNumberStr);
			}
		}
		return false;
	}

	/**
	 * 匹配号码
	 * 跟上期双面
	 *
	 * @param valiData 验证开奖数据
	 * @return 匹配成功
	 */
	private boolean matchNumber(String[] valiData, int rank, String betNumberStr) {
		int baseNum;
		int valiNum;
		int threshold = 5;
		if (rank < 0) {
			//冠亚和
			baseNum = Integer.parseInt(baseData[0]) + Integer.parseInt(baseData[1]);
			valiNum = Integer.parseInt(valiData[0]) + Integer.parseInt(valiData[1]);
		} else if (rank > 10) {
			throw new RuntimeException("非法的排序捕捉，rang为" + rank);
		} else {
			baseNum = Integer.parseInt((baseData[rank]));
			valiNum = Integer.parseInt((valiData[rank]));
		}
		int betNumber = NumberTool.getInteger(betNumberStr);
		if (betNumber != 0) {
			if (betNumber > 10 || betNumber < 0) {
				return true;
			}
		} else {
			switch (betNumberStr) {
				case "大":
				case "小":
					return isBoS(baseNum, valiNum, threshold);
				case "单":
				case "双":
					return isParity(baseNum, valiNum);
				case "龙":
				case "虎":
					int base2Num = Integer.parseInt(baseData[baseData.length - rank - 1]);
					int vali2Num = Integer.parseInt(valiData[baseData.length - rank - 1]);
					return isDoT(baseNum, valiNum, base2Num, vali2Num);
				default:
					throw new RuntimeException("非法的类型捕捉，类型为" + betNumberStr);
			}
		}
		return false;
	}


	/**
	 * 大小匹配
	 *
	 * @param baseNum 基准号码
	 * @param valiNum 验证号码
	 * @return 匹配大小+单双
	 */
	private static boolean isBoS(int baseNum, int valiNum, int i) {
		return baseNum > i == valiNum > i;
	}

	/**
	 * 单双匹配
	 *
	 * @param baseNum 基准号码
	 * @param valiNum 验证号码
	 * @return 匹配正反+单双
	 */
	private static boolean isParity(int baseNum, int valiNum) {
		return (baseNum % 2 != 0) == (valiNum % 2 != 0);
	}

	/**
	 * 尾大 尾小
	 *
	 * @param number 数字
	 * @return > 5 大
	 */
	private static boolean tailSize(Integer number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		number %= 10;
		return number >= 5;
	}

	/**
	 * 合数单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	private static boolean sumSingle(Integer number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		number = number / 10 + number % 10;
		return number % 2 == 0;
	}

	/**
	 * 龙虎匹配
	 *
	 * @param baseNum  基准号码
	 * @param base2Num 基准号码2
	 * @param valiNum  验证号码
	 * @param vali2Num 验证号码2
	 * @return 匹配龙虎+单双
	 */
	private static boolean isDoT(int baseNum, int valiNum, int base2Num, int vali2Num) {
		return baseNum > base2Num == valiNum > vali2Num;
	}

	private JSONObject betFilter(JSONObject planGroupData) {
		String[] selects, bets, info;
		StringBuilder selectPlus;
		JSONObject data;

		for (Map.Entry<String, Object> entry : planGroupData.entrySet()) {
			selectPlus = new StringBuilder();
			data = JSONObject.parseObject(entry.getValue().toString());
			selects = data.getString("select").split("=");
			if (ContainerTool.isEmpty(selects)) {
				continue;
			}
			for (String select : selects) {
				bets = select.split(",");
				for (String bet : bets) {
					info = bet.split("-");
					if (info.length != 2) {
						continue;
					}
					if (NumberTool.getInteger(info[0]) > 10 || !Arrays.asList(StringTool.HIGH_WAY).contains(info[1])) {
						continue;
					}
					selectPlus.append(bet).append(",");
				}
				if (selectPlus.length() > 0) {
					selectPlus.delete(selectPlus.length() - 1, selectPlus.length());
				}
				selectPlus.append("=");
			}

			if (selectPlus.length() > 0) {
				selectPlus.delete(selectPlus.length() - 1, selectPlus.length());
			}
			data.put("select", selectPlus);
			entry.setValue(data);
		}
		return planGroupData;
	}

	private boolean checkBet(String bet,String rank) {
		String[] positions;
		int select = NumberTool.getInteger(rank);
		int num=NumberTool.getInteger(bet);
		switch (gameCode()) {
			case PK10:
			case COUNTRY_10:
			case SELF_10_2:
			case JS10:
			case XYFT:
				positions = StringTool.NUMBER_BET_CONTENT;
				// 冠亚,总和...
				if (num > 10 && select != 0) {
					return true;
				}
				//龙虎
				if ("龙".equals(bet) || "虎".equals(bet)) {
					if (select <= 0 || select > 5) {
						return true;
					}
				}
				break;
			case COUNTRY_SSC:
			case SELF_SSC_5:
			case CQSSC:
			case JSSSC:
				positions = StringTool.BALL_BET_CONTENT;
				//总和
				if (select == 0) {
					List<String> number = ImmutableList.<String>builder().add("大").add("小").add("单").add("双").build();
					if (num != 0 || !number.contains(bet)) {
						return true;
					}
				}
				break;
			case XYNC:
			case GDKLC:
				positions = StringTool.HAPPY_BET_CONTENT;
				//总和
				if (select == 0) {
					List<String> number = ImmutableList.<String>builder().add("大").add("小").add("单").add("双").add("龙").add("虎")
							.add("尾大").add("尾小").build();
					if (num != 0 || !number.contains(bet)) {
						return true;
					}
				}
				break;
			default:
				return true;
		}
		return !Arrays.asList(positions).contains(bet);
	}

}
