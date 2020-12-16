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
 * @Description: 球类游戏工具类
 * @Author: Dongming
 * @Date: 2019-10-23 18:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MergeBallTool extends MergeTool{

	private static final List<Integer> SINGLE_INDEX = Arrays.asList(1, 3, 5, 7, 9);
	private static final List<Integer> DOUBLE_INDEX = Arrays.asList(0, 2, 4, 6, 8);

	// TODO: 合并区
	/**
	 * 放入球类投注资金
	 *
	 * @param betInfo     投注资金信息
	 * @param betContents 投注正文
	 */
	static void putBetInfo(GameUtil.Code gameCode, Map<String, int[][]> betInfo, String[] betContents) {
		for (String content : betContents) {
			String[] bet = content.split("\\|");
			Integer rank = GameTool.rank(gameCode, bet[0]);
			if (rank == null) {
				log.error("没有找到球类游戏排名:".concat(content));
				continue;
			}
			int fundsTh = NumberTool.getInteger(bet[2]);
			int type = ballType(gameCode,bet[1]);
			//1-5球号
			if (rank <= 4) {
				//号码
				if (type <= 9) {
					fundPutArray(betInfo, fundsTh, "NUMBER", rank, type, 5, 10);
				} else if (type <= 11) {
					//大小
					type -= 10;
					fundPutArray(betInfo, fundsTh, "BIG_SMALL", rank, type, 5, 2);
				} else {
					//单双
					type -= 12;
					fundPutArray(betInfo, fundsTh, "SINGLE_DOUBLE", rank, type, 5, 2);
				}
			} else if (rank == 5) {
				//总和大小
				rank -= 5;
				if (type <= 11) {
					type -= 10;
					fundPutArray(betInfo, fundsTh, "SUM_SIZE", rank, type, 1, 2);
				} else {
					type -= 12;
					fundPutArray(betInfo, fundsTh, "SUM_SINGLE_DOUBLE", rank, type, 1, 2);
				}
			} else if (rank == 6) {
				//龙虎和
				rank -= 6;
				if (type <= 15) {
					type -= 14;
					fundPutArray(betInfo, fundsTh, "DRAGON_TIGER", rank, type, 1, 2);
				} else {
					type -= 16;
					fundPutArray(betInfo, fundsTh, "DRAW", rank, type, 1, 1);
				}
			} else {
				//前三，中三，后三
				rank -= 7;
				type -= 17;
				fundPutArray(betInfo, fundsTh, "THREE_NUMBER", rank, type, 3, 5);
			}
		}
	}
	/**
	 * 合并球类投注项资金信息
	 *
	 * @param betInfo 投注资金信息
	 */
	static void mergeInfo(Map<String, int[][]> betInfo) {
		// 去掉不合理的投注项
		removeUnreasonable(betInfo);
		// 双面合并
		mergeTwoSize(betInfo);
		// 去掉不合理的投注项
		removeUnreasonable(betInfo);
	}
	/**
	 * 合并球类投注项详情信息
	 *
	 * @param betInfo  投注资金信息
	 * @param betRate  投注比例
	 * @param gameCode 游戏编码
	 * @return 投注详情信息
	 */
	static List<Object> mergeItem(GameUtil.Code gameCode, Map<String, int[][]> betInfo, double betRate) {
		//用来存储投注项和投注总金额
		List<Object> list=new ArrayList<>();
		Integer fundsTh=0;
		StringBuilder betItem = new StringBuilder();
		//号码
		int[][] fundsMatrix = betInfo.get("NUMBER");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate,fundsTh, 0, 0);
		//大小
		fundsMatrix = betInfo.get("BIG_SMALL");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,0, 10);
		//单双
		fundsMatrix = betInfo.get("SINGLE_DOUBLE");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,0, 12);
		//总和大小
		fundsMatrix = betInfo.get("SUM_SIZE");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,5, 10);
		//总和单双
		fundsMatrix = betInfo.get("SUM_SINGLE_DOUBLE");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,5, 12);
		//龙虎
		fundsMatrix = betInfo.get("DRAGON_TIGER");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,6, 14);
		//和
		fundsMatrix = betInfo.get("DRAW");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,6, 16);
		//前三，中三，后三
		fundsMatrix = betInfo.get("THREE_NUMBER");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,7, 17);

		list.add(betItem.toString());
		list.add(fundsTh);
		return list;
	}

	//TODO 方法区
	/**
	 * 获取数字类基础类型  数字类型
	 *
	 *
	 * @param gameCode 游戏类型
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	private static int ballType(GameUtil.Code gameCode, String typeStr) {
		for (int i = 0; i < getType(gameCode).length; i++) {
			if (getType(gameCode)[i].equals(typeStr)) {
				return i;
			}
		}
		throw new IllegalArgumentException("没有找到球类基础类型" + typeStr);
	}
	/**
	 * 去掉球类不合理的投注项
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
			//和不合并，前三，中三，后三不合并
			if (fundsMatrix[0].length == 1 || fundsMatrix[0].length == 5) {
				continue;
			}
			//横向合并，不需要纵向合并
			crosswiseMerge(fundsMatrix);
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
			fundsMatrix = new int[5][2];
		}
		int fundsTh = 0;
		for (int num : nums) {
			fundsTh += fundsArr[num];
			fundsArr[num] = 0;
		}
		fundsMatrix[rank][type] += fundsTh;
		betInfo.put(typeKey, fundsMatrix);
	}
}
