package com.ibm.common.utils.game.tools;

import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.game.GameUtil;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: Dongming
 * @Date: 2020-04-23 18:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MergeHappyTool extends MergeTool {

	private static final List<Integer> DOUBLE_INDEX = Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15, 17, 19);
	private static final List<Integer> SINGLE_INDEX = Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14, 16, 18);
	private static final List<Integer> SMALL_INDEX = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
	private static final List<Integer> BIG_INDEX = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19);

	/**
	 * 放入快乐类投注资金
	 *
	 * @param betInfo     投注资金信息
	 * @param betContents 投注正文
	 */
	public static void putBetInfo(GameUtil.Code gameCode, Map<String, int[][]> betInfo, String[] betContents) {
		for (String content : betContents) {
			String[] bet = content.split("\\|");
			Integer rank = GameTool.rank(gameCode, bet[0]);
			if (rank == null) {
				log.error("没有找到快乐类游戏排名:".concat(content));
				continue;
			}
			int fundsTh = NumberTool.getInteger(bet[2]);
			int type = GameTool.type(gameCode, bet[1]);
			//1-8
			if (rank < 8) {
				if (type < 20) {
					//号码
					fundPutArray(betInfo, fundsTh, "NUMBER", rank, type, 8, 20);
				} else if (type < 24) {
					//方位
					type -= 20;
					fundPutArray(betInfo, fundsTh, "POSITION", rank, type, 8, 4);
				} else if (type < 27) {
					//中发白
					type -= 24;
					fundPutArray(betInfo, fundsTh, "MSW", rank, type, 8, 3);
				} else if (type < 29) {
					//合数单双
					type -= 27;
					fundPutArray(betInfo, fundsTh, "SUM_SINGLE", rank, type, 8, 2);
				} else if (type < 31) {
					//大小
					type -= 29;
					fundPutArray(betInfo, fundsTh, "BIG_SMALL", rank, type, 8, 2);
				} else if (type < 33) {
					//单双
					type -= 31;
					fundPutArray(betInfo, fundsTh, "SINGLE_DOUBLE", rank, type, 8, 2);
				} else if (type < 35) {
					//尾数大小
					type -= 33;
					fundPutArray(betInfo, fundsTh, "TAIL_SIZE", rank, type, 8, 2);
				} else {
					if (rank > 3) {
						log.error("龙虎不允许投注超过排名为4的投注项:".concat(content));
						continue;
					}
					type -= 35;
					fundPutArray(betInfo, fundsTh, "DRAGON_TIGER", rank, type, 4, 2);
				}

			} else if (rank == 8) {
				type -= 29;
				if (betInfo.containsKey("SUM")) {
					betInfo.get("SUM")[0][type] += fundsTh;
				} else {
					int[][] fundsArr = new int[1][8];
					fundsArr[0][type] = fundsTh;
					betInfo.put("SUM", fundsArr);
				}
			} else {
				log.error("错误的快乐类游戏排名:".concat(content));

			}

		}

	}

	/**
	 * 合并号码类投注项资金信息
	 *
	 * @param betInfo 投注资金信息
	 */
	public static void mergeInfo(Map<String, int[][]> betInfo) {
		// 去掉不合理的投注项
		removeUnreasonable(betInfo);
		// 双面合并
		mergeTwoSize(betInfo);
		// 去掉不合理的投注项
		removeUnreasonable(betInfo);
	}


	/**
	 * 合并号码类投注项详情信息
	 *
	 * @param betInfo  投注资金信息
	 * @param betRate  投注比例
	 * @param gameCode 游戏编码
	 * @return 投注详情信息
	 */

	public static List<Object> mergeItem(GameUtil.Code gameCode, Map<String, int[][]> betInfo, double betRate) {
		//用来存储投注项和投注总金额
		List<Object> list = new ArrayList<>();
		Integer fundsTh = 0;
		StringBuilder betItem = new StringBuilder();
		//号码
		int[][] fundsMatrix = betInfo.get("NUMBER");
		fundsTh = concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh, 0, 0);
		//方位
		fundsMatrix = betInfo.get("POSITION");
		fundsTh = concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh, 0, 20);
		//中发白
		fundsMatrix = betInfo.get("MSW");
		fundsTh = concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh, 0, 24);
		//合单双
		fundsMatrix = betInfo.get("SUM_SINGLE");
		fundsTh = concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh, 0, 27);
		//大小
		fundsMatrix = betInfo.get("BIG_SMALL");
		fundsTh = concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh, 0, 29);
		//单双
		fundsMatrix = betInfo.get("SINGLE_DOUBLE");
		fundsTh = concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh, 0, 31);
		//尾数大小
		fundsMatrix = betInfo.get("TAIL_SIZE");
		fundsTh = concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh, 0, 33);

		//龙虎
		fundsMatrix = betInfo.get("DRAGON_TIGER");
		fundsTh = concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh, 0, 35);
		//冠亚
		fundsMatrix = betInfo.get("FIRST_SECOND");
		if (ContainerTool.notEmpty(fundsMatrix)) {
			String rank = GameTool.rankStr(gameCode, 8);
			//循环 类型
			for (int type = 0; type < fundsMatrix[0].length; type++) {
				//金额 向上取整
				int fundTh = (int) (Math.ceil(fundsMatrix[0][type] * (betRate / 1000)) * 1000);
				//存在投注金额
				if (fundTh > 0) {
					fundsTh += fundTh;
					betItem.append(rank).append("|").append(getType(gameCode)[type + 29]).append("|").append(fundTh)
							.append("#");
				}
			}
		}
		list.add(betItem.toString());
		list.add(fundsTh);
		return list;
	}


	/**
	 * 去掉号码类不合理的投注项
	 *
	 * @param betInfo 投注资金信息
	 */
	private static void removeUnreasonable(Map<String, int[][]> betInfo) {
		int[][] fundsMatrix;
		for (Map.Entry<String, int[][]> entry : betInfo.entrySet()) {
			fundsMatrix = entry.getValue();
			//无数据不合并
			if (ContainerTool.isEmpty(fundsMatrix)) {
				continue;
			}
			//总和不合并
			if ("SUM".equals(entry.getKey())) {
				continue;
			}
			//横向合并
			crosswiseMerge(fundsMatrix);
			//龙虎不合并合并
			if ("DRAGON_TIGER".equals(entry.getKey())) {
				continue;
			}
			//纵向合并
			lengthwaysMerge(fundsMatrix);
		}
	}

	/**
	 * 合并双面投注项
	 *
	 * @param betInfo 投注资金信息
	 */
	private static void mergeTwoSize(Map<String, int[][]> betInfo) {
		int[][] fundsMatrix = betInfo.get("NUMBER");
		//无数据不合并
		if (ContainerTool.isEmpty(fundsMatrix)) {
			return;
		}
		for (int rank = 0; rank < fundsMatrix.length; rank++) {
			List<Integer> indexList = NumberTool.findNoZeroValIndex(fundsMatrix[rank]);
			//非零数据不超过5个，直接放弃
			if (indexList.size() < 5) {
				continue;
			}
			IbmTypeEnum type = getMergeType(indexList);
			if (type == null) {
				continue;
			}
			switch (type) {
				case BIG:
					mergeTwoSizeItem(betInfo, fundsMatrix[rank], rank, 0, "BIG_SMALL", BIG_INDEX);
					break;
				case SMALL:
					mergeTwoSizeItem(betInfo, fundsMatrix[rank], rank, 1, "BIG_SMALL", SMALL_INDEX);
					break;
				case SINGLE:
					mergeTwoSizeItem(betInfo, fundsMatrix[rank], rank, 0, "SINGLE_DOUBLE", SINGLE_INDEX);
					break;
				case DOUBLE:
					mergeTwoSizeItem(betInfo, fundsMatrix[rank], rank, 1, "SINGLE_DOUBLE", DOUBLE_INDEX);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * 合并双面详细做法
	 *
	 * @param betInfo  投注资金信息
	 * @param fundsArr 待合并资金数组
	 * @param rank     合并名次
	 * @param type     合并类型
	 * @param typeKey  类型键
	 * @param nums     类型索引
	 */
	private static void mergeTwoSizeItem(Map<String, int[][]> betInfo, int[] fundsArr, int rank, int type,
										 String typeKey, List<Integer> nums) {
		int[][] fundsMatrix;
		if (betInfo.containsKey(typeKey)) {
			fundsMatrix = betInfo.get(typeKey);
		} else {
			fundsMatrix = new int[8][2];
		}
		int fundsTh = 0;
		for (int num : nums) {
			fundsTh += fundsArr[num];
			fundsArr[num] = 0;
		}
		fundsMatrix[rank][type] += fundsTh;
		betInfo.put(typeKey, fundsMatrix);
	}

	/**
	 * 获取合并类型
	 *
	 * @param indexList 索引列表
	 * @return 合并类型
	 */
	private static IbmTypeEnum getMergeType(List<Integer> indexList) {
		if (indexList.containsAll(SMALL_INDEX)) {
			return IbmTypeEnum.SMALL;
		} else if (indexList.containsAll(BIG_INDEX)) {
			return IbmTypeEnum.BIG;
		} else if (indexList.containsAll(SINGLE_INDEX)) {
			return IbmTypeEnum.SINGLE;
		} else if (indexList.containsAll(DOUBLE_INDEX)) {
			return IbmTypeEnum.DOUBLE;
		} else {
			return null;
		}
	}

}
