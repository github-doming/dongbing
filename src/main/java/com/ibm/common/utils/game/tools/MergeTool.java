package com.ibm.common.utils.game.tools;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.game.GameUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * @Description: 合并工具类
 * @Author: Dongming
 * @Date: 2019-09-17 11:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MergeTool {
	protected static Logger log = LogManager.getLogger(MergeTool.class);

	static final String[] NUMBER_TYPE = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "大", "小", "单", "双", "龙",
			"虎"};


	static final List<Integer> SMALL_INDEX = Arrays.asList(0, 1, 2, 3, 4);
	static final List<Integer> BIG_INDEX = Arrays.asList(5, 6, 7, 8, 9);

	// TODO: 方法区
	/**
	 * 拼接投注项
	 *
	 * @param gameCode    游戏编码
	 * @param fundsMatrix 资金矩阵
	 * @param betItem     投注详情项
	 * @param betRate     投注比例
	 * @param fundsTh     投注总金额
	 * @param rankPoint   排名起点
	 * @param typePoint   位置起点
	 */
	static Integer concatBetItem(GameUtil.Code gameCode, int[][] fundsMatrix, StringBuilder betItem, double betRate,
								 Integer fundsTh, int rankPoint, int typePoint) {
		String[] numberOrBall = getType(gameCode);

		if (ContainerTool.notEmpty(fundsMatrix)) {
			//循环 排名
			for (int rank = 0; rank < fundsMatrix.length; rank++) {
				String rankStr = GameTool.rankStr(gameCode, rank + rankPoint);
				//循环 类型
				for (int type = 0; type < fundsMatrix[rank].length; type++) {
					//金额 向上取整
					int fundTh = (int) (Math.ceil(fundsMatrix[rank][type] * (betRate/1000)) *1000);
					//存在投注金额
					if (fundTh > 0) {
						fundsTh+=fundTh;
						betItem.append(rankStr).append("|").append(numberOrBall[type + typePoint]).append("|")
								.append(fundTh).append("#");
					}
				}
			}
		}
		return fundsTh;
	}
	/**
	 * 获取类型
	 *
	 * @param gameCode 游戏编码
	 * @return 类型
	 */
	protected static String[] getType(GameUtil.Code gameCode) {
		switch (gameCode) {
			case PK10:
			case XYFT:
			case JS10:
			case SELF_10_2:
			case COUNTRY_10:
				return NUMBER_TYPE;
			case JSSSC:
			case CQSSC:
			case SELF_SSC_5:
			case COUNTRY_SSC:
				return GameTool.BALL_TYPE;
			case XYNC:
			case GDKLC:
				return GameTool.KLC_TYPE;
			default:
				throw new RuntimeException("错误的游戏类型捕捉");
		}
	}
	/**
	 * 将资金存放到数组
	 *
	 * @param betInfo 投注资金信息
	 * @param fundsTh 金额
	 * @param betType 投注类型
	 * @param rank    排名
	 * @param type    类型
	 * @param x       行
	 * @param y       列
	 */
	static void fundPutArray(Map<String, int[][]> betInfo, int fundsTh, String betType, Integer rank, int type, int x,
							 int y) {
		if (betInfo.containsKey(betType)) {
			betInfo.get(betType)[rank][type] += fundsTh;
		} else {
			int[][] fundsArr = new int[x][y];
			fundsArr[rank][type] += fundsTh;
			betInfo.put(betType, fundsArr);
		}
	}
	/**
	 * 横向合并<br/>
	 * 合并同一个排名的不同投注项   <br/>
	 * 如：第一名：大 20，第一名：小 10 <br/>
	 * 合并为：第一名：大 10<br/>
	 * @param fundsMatrix 资金信息
	 */
	static void crosswiseMerge(int[][] fundsMatrix) {
		for (int[] fundsTh : fundsMatrix) {
			int min = NumberTool.findMin(fundsTh);
			if (min == 0) {
				continue;
			}
			NumberTool.less(fundsTh, min);
		}
	}
	/**
	 * 纵向合并
	 *
	 * @param fundsMatrix 资金信息
	 */
	static void lengthwaysMerge(int[][] fundsMatrix) {
		int[][] transpose = NumberTool.transpose(fundsMatrix);

		for (int type = 0; type < transpose.length; type++) {
			int min = NumberTool.findMin(transpose[type]);
			if (min == 0) {
				continue;
			}
			NumberTool.less(fundsMatrix, type, min);
		}
	}

}
