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
 * @Description: 号码类游戏工具类
 * @Author: Dongming
 * @Date: 2019-10-23 18:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MergeNumberTool extends MergeTool{

	private static final String[] FIRST_SECOND_TYPE = {"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "大", "小", "单", "双"};

	private static final List<Integer> DOUBLE_INDEX = Arrays.asList(1, 3, 5, 7, 9);
	private static final List<Integer> SINGLE_INDEX = Arrays.asList(0, 2, 4, 6, 8);


	// TODO: 合并区
	/**
	 * 放入号码类投注资金
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
			//冠亚
			if (rank > 9) {
				if (betInfo.containsKey("FIRST_SECOND")) {
					betInfo.get("FIRST_SECOND")[0][firstSecondType(bet[1])] += fundsTh;
				} else {
					int[][] fundsArr = new int[1][21];
					fundsArr[0][firstSecondType(bet[1])] = fundsTh;
					betInfo.put("FIRST_SECOND", fundsArr);
				}
				continue;
			}
			//获取球类基本类型
			int type = baseType(bet[1]);
			if (type < 10) {
				//号码
				fundPutArray(betInfo, fundsTh, "NUMBER", rank, type, 10, 10);
			} else if (type < 12) {
				//大小
				type -= 10;
				fundPutArray(betInfo, fundsTh, "BIG_SMALL", rank, type, 10, 2);
			} else if (type < 14) {
				//单双
				type -= 12;
				fundPutArray(betInfo, fundsTh, "SINGLE_DOUBLE", rank, type, 10, 2);
			} else {
				//龙虎
				if (rank > 4) {
					log.error("龙虎不允许投注超过排名为5的投注项:".concat(content));
					continue;
				}
				type -= 14;
				fundPutArray(betInfo, fundsTh, "DRAGON_TIGER", rank, type, 5, 2);
			}
		}
	}
	/**
	 * 合并号码类投注项资金信息
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
	 * 合并号码类投注项详情信息
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
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,0, 0);
		//大小
		fundsMatrix = betInfo.get("BIG_SMALL");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,0, 10);
		//单双
		fundsMatrix = betInfo.get("SINGLE_DOUBLE");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,0, 12);
		//龙虎
		fundsMatrix = betInfo.get("DRAGON_TIGER");
		fundsTh=concatBetItem(gameCode, fundsMatrix, betItem, betRate, fundsTh,0, 14);
		//冠亚
		fundsMatrix = betInfo.get("FIRST_SECOND");
		if (ContainerTool.notEmpty(fundsMatrix)) {
			String rank = GameTool.rankStr(gameCode, 10);
			//循环 类型
			for (int type = 0; type < fundsMatrix[0].length; type++) {
				//金额 向上取整
				int fundTh = (int) (Math.ceil(fundsMatrix[0][type] * (betRate/1000))*1000);
				//存在投注金额
				if (fundTh > 0) {
					fundsTh+=fundTh;
					betItem.append(rank).append("|").append(FIRST_SECOND_TYPE[type]).append("|").append(fundTh)
							.append("#");
				}
			}
		}
		list.add(betItem.toString());
		list.add(fundsTh);
		return list;
	}

	//TODO 方法区
	/**
	 * 获取号码类基础类型  数字类型
	 *
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	private static int baseType(String typeStr) {
		for (int i = 0; i < NUMBER_TYPE.length; i++) {
			if (NUMBER_TYPE[i].equals(typeStr)) {
				return i;
			}
		}
		throw new IllegalArgumentException("没有找到号码类基础类型" + typeStr);
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
			//冠亚不合并
			if (fundsMatrix[0].length > 10) {
				continue;
			}
			//横向合并
			crosswiseMerge(fundsMatrix);
			if("DRAGON_TIGER".equals(entry.getKey())){
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
			fundsMatrix = new int[10][2];
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
	 * 获取球类冠亚类型  数字类型
	 *
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	private static int firstSecondType(String typeStr) {
		for (int i = 0; i < FIRST_SECOND_TYPE.length; i++) {
			if (FIRST_SECOND_TYPE[i].equals(typeStr)) {
				return i;
			}
		}
		throw new IllegalArgumentException("没有找到球类冠亚类型");
	}
}
