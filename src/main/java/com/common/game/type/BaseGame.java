package com.common.game.type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.common.enums.TypeEnum;
import com.common.game.Game;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.util.*;

/**
 * 游戏抽象类
 *
 * @Author: Dongming
 * @Date: 2020-05-13 13:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public abstract class BaseGame implements Game {
	//region 翻译区

	/**
	 * 获取尾大尾小
	 * @param number	号码
	 * @param state	正投反投
	 * @return
	 */
	@Override
	public String tailSize(int number, boolean state){
		return null;
	}
	/**
	 * 合单合双
	 * @param number	号码
	 * @param state	正投反投
	 * @return
	 */
	@Override
	public String sumSingle(int number, boolean state){
		return null;
	}
	//endregion

	//region 编码区

	/**
	 * 获取游戏的排名
	 *
	 * @return 游戏排名数组
	 */
	protected abstract String[] rank();

	/**
	 * 获取游戏的类型
	 *
	 * @return 游戏类型数组
	 */
	protected abstract String[] type();

	@Override public Integer rank(String rankStr) {
		for (int i = 0; i < rank().length; i++) {
			if (rank()[i].equals(rankStr)) {
				return i;
			}
		}
		return null;
	}

	@Override public Integer type(String typeStr) {
		for (int i = 0; i < type().length; i++) {
			if (type()[i].equals(typeStr)) {
				return i;
			}
		}
		throw new IllegalArgumentException("没有找到球类基础类型" + typeStr);
	}

	@Override public String rankStr(Integer rank) {
		return rank < rank().length ? rank()[rank] : null;
	}

	@Override public String typeStr(Integer type) {
		return type < type().length ? type()[type] : null;
	}

	@Override public Map<Integer, Set<Integer>> getBetCodeByJson(String betContent) {
		Map<Integer, Set<Integer>> betCode = new HashMap<>(10);
		JSONArray betContents = JSON.parseArray(betContent);

		for (int i = 0; i < betContents.size(); i++) {
			JSONArray contents = betContents.getJSONArray(i);
			String rankStr = contents.getString(0);
			String typeStr = contents.getString(1);
			Integer rank = rank(rankStr);
			if (rank == null) {
				continue;
			}

			Integer type =type(rank, typeStr);
			if (type == null) {
				continue;
			}
			if (betCode.containsKey(rank)) {
				betCode.get(rank).add(type);
			} else {
				Set<Integer> set = new HashSet<>();
				set.add(type);
				betCode.put(rank, set);
			}
		}
		return betCode;
	}

	@Override public String itemStr( int rank, int type, int funds) {
		return rankStr(rank).concat("|").concat(typeStr(type)).concat("|").concat(Integer.toString(funds));
	}
	//endregion

	//region 合并区

	protected List<Integer> SMALL_INDEX = Arrays.asList(0, 1, 2, 3, 4);
	protected List<Integer> BIG_INDEX = Arrays.asList(5, 6, 7, 8, 9);
	protected List<Integer> SINGLE_INDEX = Arrays.asList(1, 3, 5, 7, 9);
	protected List<Integer> DOUBLE_INDEX = Arrays.asList(0, 2, 4, 6, 8);
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
	protected abstract void mergeTwoSizeItem(Map<String, int[][]> betInfo, int[] fundsArr, int rank, int type,
			String typeKey, List<Integer> nums);
	/**
	 * 拼接投注项
	 *
	 * @param fundsMatrix 资金矩阵
	 * @param betItem     投注详情项
	 * @param betRate     投注比例
	 * @param fundsTh     投注总金额
	 * @param rankPoint   排名起点
	 * @param typePoint   位置起点
	 * @return 总金额
	 */
	protected Integer concatBetItem(int[][] fundsMatrix, StringBuilder betItem, double betRate, Integer fundsTh,
			int rankPoint, int typePoint) {
		if (ContainerTool.notEmpty(fundsMatrix)) {
			//循环 排名
			for (int rank = 0; rank < fundsMatrix.length; rank++) {
				String rankStr = rankStr(rank + rankPoint);
				//循环 类型
				for (int type = 0; type < fundsMatrix[rank].length; type++) {
					//金额 向上取整
					int fundTh = (int) (Math.ceil(fundsMatrix[rank][type] * (betRate / 1000)) * 1000);
					//存在投注金额
					if (fundTh > 0) {
						fundsTh += fundTh;
						betItem.append(rankStr).append("|").append(type()[type + typePoint]).append("|").append(fundTh)
								.append("#");
					}
				}
			}
		}
		return fundsTh;
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
	protected void fundPutArray(Map<String, int[][]> betInfo, int fundsTh, String betType, Integer rank, int type,
			int x, int y) {
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
	 *
	 * @param fundsMatrix 资金信息
	 */
	protected void crosswiseMerge(int[][] fundsMatrix) {
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
	protected void verticalMerge(int[][] fundsMatrix) {
		int[][] transpose = NumberTool.transpose(fundsMatrix);

		for (int type = 0; type < transpose.length; type++) {
			int min = NumberTool.findMin(transpose[type]);
			if (min == 0) {
				continue;
			}
			NumberTool.less(fundsMatrix, type, min);
		}
	}

	/**
	 * 合并双面投注项
	 *
	 * @param betInfo 投注资金信息
	 */
	protected void mergeTwoSize(Map<String, int[][]> betInfo) {
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
			TypeEnum type = getMergeType(indexList);
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
	private TypeEnum getMergeType(List<Integer> indexList) {
		if (indexList.containsAll(SMALL_INDEX)) {
			return TypeEnum.SMALL;
		} else if (indexList.containsAll(BIG_INDEX)) {
			return TypeEnum.BIG;
		} else if (indexList.containsAll(SINGLE_INDEX)) {
			return TypeEnum.SINGLE;
		} else if (indexList.containsAll(DOUBLE_INDEX)) {
			return TypeEnum.DOUBLE;
		} else {
			return null;
		}
	}

	//endregion
}
