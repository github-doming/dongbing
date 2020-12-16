package com.common.game.type;

import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 号码类游戏
 *
 * @Author: Dongming
 * @Date: 2020-05-13 13:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class NumberGame extends BaseGame {
	//region 翻译区

	/**
	 * > 5 大
	 */
	@Override
	public String size(int number, boolean state) {
		if (number < 1 || number > 10) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return state ? (number <= 5 ? "小" : "大" ):( number <= 5 ? "大" : "小");
	}

	/**
	 * 冠亚和大小
	 * @param number	总和/冠亚和
	 * @param state	正投反投
	 * @return
	 */
	@Override
	public String sumSize(int number, boolean state) {
		if (number < 1 || number > 19) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return state ? (number <= 11 ? "小" : "大" ):( number <= 11 ? "大" : "小");
	}

	@Override
	public int getRankMin() {
		return 1;
	}

	@Override
	public int getRankMax() {
		return 10;
	}

	@Override
	public int getDrawNumberMin() {
		return 1;
	}

	@Override
	public int getDrawNumberMax() {
		return 10;
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 11 大
	 */
	public String sizeChampionSum(int number) {
		if (number < 3 || number > 19) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number <= 11 ? "小" : "大";
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 11 BIG
	 */
	public String sizeChampionSumEn(int number) {
		return "小".equals(sizeChampionSum(number)) ? "SMALL" : "BIG";
	}

	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	@Override
	public String single(int number,boolean state) {
		return state ? (number % 2 == 0  ? "双" : "单" ): (number % 2 == 0  ? "单" : "双");
	}

	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	public String singleChampionSum(int number) {
		if (number < 3 || number > 19) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number % 2 == 0 ? "双" : "单";
	}

	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 SINGLE
	 */
	public String singleChampionSumEn(int number) {
		return "双".equals(singleChampionSum(number)) ? "DOUBLE" : "SINGLE";
	}

	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 数字1>数字2 龙
	 */
	@Override
	public String dragon(int number1, int number2,boolean state) {
		if (number1 < 1 || number1 > 10 || number2 < 1 || number2 > 10) {
			throw new RuntimeException("错误数值装换:" + number1 + "," + number2);
		}
		return state?(number1 > number2 ? "龙" : "虎"):(number1 > number2 ? "虎": "龙" );
	}

	//endregion

	//region 编码区

	private static final String[] RANK = {"第一名", "第二名", "第三名", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名", "冠亚"};

	private static final String[] TYPE = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "大", "小", "单", "双", "龙",
			"虎"};

	private static final String[] FIRST_SECOND_TYPE = {"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "大", "小", "单", "双"};

	/**
	 * 获取球类冠亚类型  数字类型
	 *
	 * @param typeStr 字符类型
	 * @return 数字类型
	 */
	public int firstSecondType(String typeStr) {
		for (int i = 0; i < FIRST_SECOND_TYPE.length; i++) {
			if (FIRST_SECOND_TYPE[i].equals(typeStr)) {
				return i;
			}
		}
		throw new IllegalArgumentException("没有找到球类冠亚类型");
	}

	@Override
	public String[] rank() {
		return RANK;
	}

	@Override
	public String[] type() {
		return TYPE;
	}

	@Override
	public Integer type(Integer rank, String typeStr) {
		Integer type = type(typeStr);
		//号码
		if (rank < 10) {
			//10-19 号码不可能
			if (type > 9 && type <= 18) {
				return null;
			}
			//龙虎 5-10不可能
			if (rank > 4 && type >= 23) {
				return null;
			}
			return type;
		}
		//冠亚 1,2,龙,虎不可能
		if (type < 2 || type >= 23) {
			return null;
		}
		return type;
	}

	//endregion

	//region 合并区

	/**
	 * 放入投注资金
	 *
	 * @param betInfo     投注资金信息
	 * @param betContents 投注正文
	 */
	@Override
	public void putBetInfo(Map<String, int[][]> betInfo, String[] betContents) {
		for (String content : betContents) {
			String[] bet = content.split("\\|");
			Integer rank = rank(bet[0]);
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
			int type = type(bet[1]);
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
	 * 合并号码类投注项详情信息
	 *
	 * @param betInfo 投注资金信息
	 * @param betRate 投注比例
	 * @return 投注详情信息
	 */
	@Override
	public List<Object> mergeItem(Map<String, int[][]> betInfo, double betRate) {
		//用来存储投注项和投注总金额
		List<Object> list = new ArrayList<>();
		Integer fundsTh = 0;
		StringBuilder betItem = new StringBuilder();
		//号码
		int[][] fundsMatrix = betInfo.get("NUMBER");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 0);
		//大小
		fundsMatrix = betInfo.get("BIG_SMALL");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 10);
		//单双
		fundsMatrix = betInfo.get("SINGLE_DOUBLE");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 12);
		//龙虎
		fundsMatrix = betInfo.get("DRAGON_TIGER");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 14);
		//冠亚
		fundsMatrix = betInfo.get("FIRST_SECOND");
		if (ContainerTool.notEmpty(fundsMatrix)) {
			String rank = rankStr(10);
			//循环 类型
			for (int type = 0; type < fundsMatrix[0].length; type++) {
				//金额 向上取整
				int fundTh = (int) (Math.ceil(fundsMatrix[0][type] * (betRate / 1000)) * 1000);
				//存在投注金额
				if (fundTh > 0) {
					fundsTh += fundTh;
					betItem.append(rank).append("|").append(FIRST_SECOND_TYPE[type]).append("|").append(fundTh)
							.append("#");
				}
			}
		}
		list.add(betItem.toString());
		list.add(fundsTh);
		return list;
	}

	/**
	 * 合并号码类投注项资金信息
	 *
	 * @param betInfo 投注资金信息
	 */
	@Override
	public void mergeInfo(Map<String, int[][]> betInfo) {
		// 去掉不合理的投注项
		removeUnreasonable(betInfo);
		// 双面合并
		mergeTwoSize(betInfo);
		// 去掉不合理的投注项
		removeUnreasonable(betInfo);
	}

	/**
	 * 去掉号码类不合理的投注项
	 *
	 * @param betInfo 投注资金信息
	 */
	private void removeUnreasonable(Map<String, int[][]> betInfo) {
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
			if ("DRAGON_TIGER".equals(entry.getKey())) {
				continue;
			}
			//纵向合并
			verticalMerge(fundsMatrix);
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
	@Override
	protected void mergeTwoSizeItem(Map<String, int[][]> betInfo, int[] fundsArr, int rank, int type,
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
	//endregion

}
