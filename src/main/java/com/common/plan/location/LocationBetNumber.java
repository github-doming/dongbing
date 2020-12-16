package com.common.plan.location;

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
import java.util.List;
import java.util.Map;

/**
 * @Description: 位置投注
 * @Author: null
 * @Date: 2020-05-19 14:15
 * @Version: v1.0
 */
public class LocationBetNumber extends PlanBase {

	public static LocationBetNumber getInstance() {
		return new LocationBetNumber();
	}


	@Override
	public boolean valiData(PlanItem planItem) {
		if (StringTool.isEmpty(planItem.getFollowPeriod(), planItem.getMonitorPeriod(),
				planItem.getBetMode(), planItem.getFundSwapMode(), planItem.getPeriodRollMode())) {
			return true;
		}
		//{"UN_INCREASE_":"CLOSE","INVERSE_":"CLOSE"}
		JSONObject expand = JSONObject.parseObject(planItem.getExpandInfo());

		if (!expand.containsKey(PlanInfoEnum.UN_INCREASE_.name()) || !expand.containsKey(PlanInfoEnum.INVERSE_.name())) {
			return true;
		}
		//方案组数据过滤
		JSONObject planGroupData = JSONObject.parseObject(planItem.getPlanGroupData());
		JSONObject data;
		for (Map.Entry<String, Object> entry : planGroupData.entrySet()) {
			data = JSONObject.parseObject(entry.getValue().toString());
			PlanDataFilterTool.strFilter("select", data, 10);
			PlanDataFilterTool.strFilter("bet", data, 10);
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
		String select = planGroupItem().getString("select");
		String bet = planGroupItem().getString("bet");
		//算出跟进期数
		//监控期数=0，即不监控，所有方案都按规则生成投注项
		if (monitor() != 0) {
			//一直反投（连续失败才会运行）
			boolean flag = valiBet(select.split(","), bet.split(","));
			if (!flag) {
				return null;
			}
		}
		return planGroupItem();
	}

	@Override
	public String betContent(JSONObject groupData, long fundTh, Object expandInfo, Map<String, Object> historyMap) {
		try {
			//扩展信息：反投，不中停止新增
			JSONObject expand = JSONObject.parseObject(expandInfo.toString());
			if (StateEnum.OPEN.name().equals(expand.get(PlanInfoEnum.UN_INCREASE_.name())) && ContainerTool.notEmpty(historyMap)) {
				boolean execResult = Boolean.parseBoolean(historyMap.get("EXEC_RESULT_").toString());
				if (!execResult) {
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
			//反投状态
			String inverse = expand.getString(PlanInfoEnum.INVERSE_.name());
			Integer[] selectsInt = NumberTool.intValue(selects);
			switch (gameCode()) {
				case PK10:
				case JS10:
				case XYFT:
				case SELF_10_2:
				case COUNTRY_10:
					return betContentNumber(bets, selectsInt, fundTh, inverse);
				case JSSSC:
				case CQSSC:
				case SELF_SSC_5:
				case COUNTRY_SSC:
					return betContentBall(bets, selectsInt, fundTh, inverse);
				case XYNC:
				case GDKLC:
					return betContentHappy(bets, selectsInt, fundTh, inverse);
				default:
					log.warn("非法的游戏类型，类型为" + gameCode().name());
					return null;
			}
		} catch (Exception e) {
			log.error("获取投注内容出错，期数为:{},方案组详情为:{}", basePeriod(), groupData);
			return null;
		}
	}

	/**
	 * 投注信息
	 *
	 * @param bets       投注位置
	 * @param selectsInt 选择位置
	 * @param fundTh     金额
	 * @return
	 */
	private String betContent(String[] bets, Integer[] selectsInt, long fundTh) {
		Game game = gameCode().getGameFactory().game();
		int rankMin = game.getRankMin();
		int rankMax = game.getRankMax();

		StringBuilder result = new StringBuilder();
		for (String bet : bets) {
			int num = Integer.parseInt(bet);
			if (num < rankMin || num > rankMax) {
				continue;
			}
			String rank = PlanTool.getRankCn(num, gameCode());

			for (Integer select : selectsInt) {
				if (select < rankMin || select > rankMax) {
					continue;
				}
				String number = baseData[select - 1];
				result.append(rank).append("|").append(number).append("|").append(fundTh).append("#");
			}
		}
		return result.toString();
	}

	/**
	 * 球类投注信息
	 *
	 * @param bets       投注位置
	 * @param selectsInt 选择位置
	 * @param fundTh     金额
	 * @param inverse    反投
	 * @return
	 */
	private String betContentHappy(String[] bets, Integer[] selectsInt, long fundTh, String inverse) {
		StringBuilder result = new StringBuilder();
		//没有开启反投
		if (StateEnum.CLOSE.name().equals(inverse)) {
			return betContent(bets, selectsInt, fundTh);
		}
		int[][] fundsArr = new int[8][20];
		for (String bet : bets) {
			int num = Integer.parseInt(bet);
			if (num < 1 || num > 8) {
				continue;
			}
			for (Integer select : selectsInt) {
				if (select < 1 || select > 8) {
					continue;
				}
				String number = baseData[select - 1];
				fundsArr[Integer.parseInt(bet) - 1][Integer.parseInt(number) - 1] += fundTh;
			}
		}
		int[][] transpose = NumberTool.transpose(fundsArr);
		int[] rowSize = new int[8];
		int[] colSize = new int[20];
		for (int i = 0; i < 8; i++) {
			rowSize[i] = NumberTool.findNoZeroValSize(fundsArr[i]);
			colSize[i] = NumberTool.findNoZeroValSize(transpose[i]);
		}
		//横轴金额不为空的索引
		opposing(fundsArr, transpose, rowSize, colSize);
		for (int i = 0; i < fundsArr.length; i++) {
			for (int j = 0; j < fundsArr[0].length; j++) {
				int fundsTh = fundsArr[i][j];
				if (fundsTh < 2) {
					continue;
				}
				String rank = PlanTool.getRankCn(i + 1, gameCode());
				result.append(rank).append("|").append((j + 1)).append("|")
						.append(fundsTh).append("#");
			}
		}
		return result.toString();
	}


	/**
	 * 球类投注信息
	 *
	 * @param bets       投注位置
	 * @param selectsInt 选择位置
	 * @param fundTh     金额
	 * @param inverse    反投
	 * @return
	 */
	private String betContentBall(String[] bets, Integer[] selectsInt, long fundTh, String inverse) {
		StringBuilder result = new StringBuilder();
		//没有开启反投
		if (StateEnum.CLOSE.name().equals(inverse)) {
			return betContent(bets, selectsInt, fundTh);
		}
		int[][] fundsArr = new int[5][10];
		for (String bet : bets) {
			int num = Integer.parseInt(bet);
			if (num < 1 || num > 5) {
				continue;
			}
			for (Integer select : selectsInt) {
				if (select < 1 || select > 5) {
					continue;
				}
				String number = baseData[select - 1];
				fundsArr[num - 1][Integer.parseInt(number)] += fundTh;
			}
		}
		for (int[] rows : fundsArr) {
			int funds = NumberTool.findMax(rows);
			if (funds == 0) {
				continue;
			}
			NumberTool.less(funds, rows);
		}
		for (int i = 0; i < fundsArr.length; i++) {
			for (int j = 0; j < fundsArr[0].length; j++) {
				int fundsTh = fundsArr[i][j];
				if (fundsTh < 2) {
					continue;
				}
				String rank = PlanTool.getRankCn(i + 1, gameCode());
				result.append(rank).append("|").append((j)).append("|")
						.append(fundsTh).append("#");
			}
		}
		return result.toString();
	}

	/**
	 * 号码投注信息
	 *
	 * @param bets       投注位置
	 * @param selectsInt 选择位置
	 * @param fundTh     金额
	 * @param inverse    反投
	 * @return
	 */
	private String betContentNumber(String[] bets, Integer[] selectsInt, long fundTh, String inverse) {
		StringBuilder result = new StringBuilder();
		//没有开启反投
		if (StateEnum.CLOSE.name().equals(inverse)) {
			return betContent(bets, selectsInt, fundTh);
		}
		int[][] fundsArr = new int[10][10];
		for (String bet : bets) {
			int num = Integer.parseInt(bet);
			if (num < 1 || num > 10) {
				continue;
			}
			for (Integer select : selectsInt) {
				if (select < 1 || select > 10) {
					continue;
				}
				String number = baseData[select - 1];
				fundsArr[Integer.parseInt(bet) - 1][Integer.parseInt(number) - 1] += fundTh;
			}
		}
		int[][] transpose = NumberTool.transpose(fundsArr);
		int[] rowSize = new int[10];
		int[] colSize = new int[10];
		for (int i = 0; i < 10; i++) {
			rowSize[i] = NumberTool.findNoZeroValSize(fundsArr[i]);
			colSize[i] = NumberTool.findNoZeroValSize(transpose[i]);
		}
		//横轴金额不为空的索引
		opposing(fundsArr, transpose, rowSize, colSize);
		for (int i = 0; i < fundsArr.length; i++) {
			for (int j = 0; j < fundsArr[0].length; j++) {
				int fundsTh = fundsArr[i][j];
				if (fundsTh < 2) {
					continue;
				}
				String rank = PlanTool.getRankCn(i + 1, gameCode());
				result.append(rank).append("|").append((j + 1)).append("|")
						.append(fundsTh).append("#");
			}
		}
		return result.toString();
	}

	/**
	 * 反投
	 *
	 * @param matrix    资金矩阵
	 * @param transpose 资金矩阵转置
	 * @param rowSize   行不为0 列表
	 * @param colSize   列不为0 列表
	 */
	private static void opposing(int[][] matrix, int[][] transpose, int[] rowSize, int[] colSize) {
		//横轴金额不为空的索引
		List<Integer> rowIndex = NumberTool.findNoZeroValIndex(rowSize);
		if (rowIndex.size() == 0) {
			return;
		}
		//纵轴金额不为空的索引
		List<Integer> colIndex = NumberTool.findNoZeroValIndex(colSize);

		if (rowIndex.size() <= colIndex.size()) {
			//横轴反投
			for (int row : rowIndex) {
				int funds = NumberTool.findMax(matrix[row]);
				NumberTool.less(funds, matrix[row]);
			}
		} else {
			//纵轴反投
			for (int col : colIndex) {
				int funds = NumberTool.findMax(transpose[col]);
				NumberTool.less(funds, matrix, col);
			}
		}
	}

	/**
	 * 验证该方案组是否验证通过<br>
	 * 监控期数内全部匹配成功-位置投注
	 *
	 * @param selects 选择位置
	 * @param bets    投注位置
	 * @return 开启方案组验证通过 true
	 */
	private boolean valiBet(String[] selects, String[] bets) {
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
				if (matchNum(valiData, selects, bets)) {
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
	 * 位置投注
	 *
	 * @param valiData 验证开奖数据
	 * @param selects  选择位置《验证开奖数据》
	 * @param bets     投注位置《基准开奖数据》
	 * @return 匹配成功
	 */
	private boolean matchNum(String[] valiData, String[] selects, String[] bets) {
		for (String select : selects) {
			if (baseData.length < Integer.parseInt(select)) {
				continue;
			}
			String base = baseData[Integer.parseInt(select) - 1];
			for (String bet : bets) {
				if (baseData.length < Integer.parseInt(bet)) {
					continue;
				}
				String vail = valiData[Integer.parseInt(bet) - 1];
				if (base.equals(vail)) {
					return true;
				}
			}
		}
		return false;
	}
}
