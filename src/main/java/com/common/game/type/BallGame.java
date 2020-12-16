package com.common.game.type;

import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 球类游戏
 *
 * @Author: Dongming
 * @Date: 2020-05-13 14:50
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BallGame extends BaseGame {
	//region 翻译区

	@Override
	public int getRankMin() {
		return 1;
	}

	@Override
	public int getRankMax() {
		return 5;
	}

	@Override
	public int getDrawNumberMin() {
		return 0;
	}

	@Override
	public int getDrawNumberMax() {
		return 9;
	}
	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @param state  正投反投
	 * @return > 5 大
	 */
	@Override
	public String size(int number, boolean state) {
		if (number < 0 || number > 9) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return state ?( number <= 5 ? "小" : "大") :( number <= 5 ? "大" : "小");
	}

	/**
	 * 总和大小
	 * @param number	总和/冠亚和
	 * @param state	正投反投
	 * @return
	 */
	@Override
	public String sumSize(int number, boolean state) {
		if (number < 0 || number > 45) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return state ?( number <= 22 ? "小" : "大") :( number <= 22 ? "大" : "小");
	}
	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @param state  正投反投
	 * @return 奇数 单
	 */
	@Override
	public String single(int number, boolean state) {
		if (number < 0 || number > 45) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return state ? (number % 2 == 0 ? "双" : "单" ): (number % 2 == 0 ? "单" : "双");
	}

	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @param state	正投反投
	 * @return 数字1>数字2 龙
	 */
	@Override
	public String dragon(int number1, int number2,boolean state) {
		if (number1 < 0 || number1 > 9 || number2 < 0 || number2 > 9) {
			throw new RuntimeException("错误数值装换:" + number1 + "," + number2);
		}
		if(number1==number2){
			return "和";
		}
		return state ? (number1 > number2 ? "龙" : "虎") : (number1 > number2 ? "虎" : "龙");
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return >= 23 BIG
	 */
	public String sizeTotal(int number) {
		if (number < 0 || number > 45) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number < 23 ? "小" : "大";
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return >= 23 BIG
	 */
	public String sizeTotalEn(int number) {
		if (number < 0 || number > 45) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number < 23 ? "SMALL" : "BIG";
	}

	/**
	 * 获取三球信息
	 *
	 * @param number1 球1
	 * @param number2 球2
	 * @param number3 球3
	 * @return 三球
	 */
	public String threeBalls(int number1, int number2, int number3) {
		int[] num = {number1, number2, number3};
		Arrays.sort(num);
		if (num[0] == num[1] && num[0] == num[2]) {
			return "豹子";

		} else if (num[0] + 1 == num[1] && num[1] + 1 == num[2]) {
			return "顺子";
		} else if (num[0] == 0 && num[2] == 9) {
			if (num[1] == 8 || num[1] == 1) {
				//顺子089,019
				return "顺子";
			} else {
				return "半顺";
			}
		} else if (num[0] == num[1] || num[1] == num[2]) {
			return "对子";

		} else if (num[0] + 1 == num[1] || num[1] + 1 == num[2]) {
			return "半顺";
		} else {
			return "杂六";
		}
	}

	/**
	 * 获取三球信息
	 *
	 * @param threeBalls 三球
	 * @return 三球英文
	 */
	public String threeBallsEn(String threeBalls) {
		switch (threeBalls) {
			case "豹子":
				return "LEOPARD";
			case "顺子":
				return "STRAIGHT";
			case "对子":
				return "HALF";
			case "半顺":
				return "PAIR";
			case "杂六":
				return "MISCELLANEOUS";
			default:
				return null;
		}
	}

	//endregion

	//region 编码区

	public static final String[] RANK = {"第一球", "第二球", "第三球", "第四球", "第五球", "总和", "龙虎和", "前三", "中三", "后三"};

	public static final String[] TYPE = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "大", "小", "单", "双", "龙", "虎",
			"和", "豹子", "顺子", "对子", "半顺", "杂六"};

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
		if (rank <= 4) {
			//双面之后都不可能
			if (type >= 14) {
				return null;
			}
			return type;
		}
		//总和
		if (rank == 5) {
			//0-9不可能，龙虎之后都不可能
			if (type <= 9 || type >= 14) {
				return null;
			}
			return type;
		}
		//龙虎和
		if (rank == 6) {
			if (type <= 13 || type >= 17) {
				return null;
			}
			return type;
		}
		//前三、中三、后三
		//0-9不可能，双面龙虎都不可能
		if (type <= 16) {
			return null;
		}
		return type;
	}

	//endregion
	//region 合并区
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
		//总和大小
		fundsMatrix = betInfo.get("SUM_SIZE");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 5, 10);
		//总和单双
		fundsMatrix = betInfo.get("SUM_SINGLE_DOUBLE");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 5, 12);
		//龙虎
		fundsMatrix = betInfo.get("DRAGON_TIGER");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 6, 14);
		//和
		fundsMatrix = betInfo.get("DRAW");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 6, 16);
		//前三，中三，后三
		fundsMatrix = betInfo.get("THREE_NUMBER");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 7, 17);

		list.add(betItem.toString());
		list.add(fundsTh);
		return list;
	}

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
			int type = type(bet[1]);
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
	 * 去掉球类不合理的投注项
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
			//和不合并，前三，中三，后三不合并
			if (fundsMatrix[0].length == 1 || fundsMatrix[0].length == 5) {
				continue;
			}
			//横向合并，不需要纵向合并
			crosswiseMerge(fundsMatrix);
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
	//endregion

}
