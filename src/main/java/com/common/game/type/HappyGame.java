package com.common.game.type;

import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 快乐彩类游戏 抽象类
 *
 * @Author: Dongming
 * @Date: 2020-05-13 15:34
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HappyGame extends BaseGame {
	public HappyGame() {
		DOUBLE_INDEX = Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15, 17, 19);
		SINGLE_INDEX = Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14, 16, 18);
		SMALL_INDEX = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		BIG_INDEX = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
	}
	//region 翻译区
	@Override
	public int getRankMin() {
		return 1;
	}

	@Override
	public int getRankMax() {
		return 8;
	}

	@Override
	public int getDrawNumberMin() {
		return 1;
	}

	@Override
	public int getDrawNumberMax() {
		return 20;
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 10 大
	 */
	@Override
	public String size(int number, boolean state) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return state ? (number <= 10 ? "小" : "大" ): (number <= 10 ? "大" : "小");
	}

	/**
	 * 获取总和大小
	 * @param number	总和/冠亚和
	 * @param state	正投反投
	 * @return
	 */
	@Override
	public String sumSize(int number, boolean state) {
		if (number < 36 || number > 132) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return state ?( number <= 83 ? "小" : "大") :( number <= 83 ? "大" : "小");
	}

	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	@Override
	public String single(int number, boolean state) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return state ? (number % 2 == 0 ? "双" : "单" ): (number % 2 == 0 ? "单" : "双");
	}

	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @param state   正投反投
	 * @return 数字1>数字2 龙
	 */
	@Override
	public String dragon(int number1, int number2, boolean state) {
		if (number1 < 1 || number1 > 20 || number2 < 1 || number2 > 20) {
			throw new RuntimeException("错误数值装换:" + number1 + "," + number2);
		}
		return state ? (number1 > number2 ? "龙" : "虎") : (number1 > number2 ? "虎" : "龙");
	}
	/**
	 * 尾大尾小
	 * @param number	号码
	 * @param state	正投反投
	 * @return
	 */
	@Override
	public String tailSize(int number, boolean state) {
		number %= 10;
		return state ? number <= 4 ? "尾小" : "尾大" : number <= 4 ? "尾大" : "尾小";
	}
	/**
	 * 合数单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	@Override
	public String sumSingle(int number, boolean state) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		number = number / 10 + number % 10;
		return state ? number % 2 == 0 ? "合双" : "合单" : number % 2 == 0 ? "合单" : "合双";
	}
	/**
	 * 总和单双
	 *
	 * @param number 数字
	 * @return 奇数 SINGLE
	 */
	public String singleTotal(int number) {
		if (number < 36 || number > 132) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number % 2 == 0 ? "双" : "单";
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return >= 23 BIG
	 */
	public String sizeTotal(int number) {
		if (number < 36 || number > 132) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		if (number < 83) {
			return "小";
		} else if (number > 84) {
			return "大";
		} else {
			return "和";
		}
	}

	/**
	 * 获取方位信息<br/>
	 * 东：01、05、09、13、17<br/>
	 * <p>
	 * 南：02、06、10、14、18<br/>
	 * <p>
	 * 西：03、07、11、15、19<br/>
	 * <p>
	 * 北：04、08、12、16、20<br/>
	 *
	 * @param number 数字
	 * @return 方位
	 */
	public String position(int number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		number %= 4;
		if (number - 1 == 0) {
			return "东";
		} else if (number - 2 == 0) {
			return "南";

		} else if (number - 3 == 0) {
			return "西";
		} else {
			return "北";
		}
	}

	/**
	 * 获取方位英文
	 *
	 * @param number 数字
	 * @return 方位
	 */
	public Object positionEn(int number) {
		String position = position(number);
		switch (position) {
			case "东":
				return "EAST";
			case "南":
				return "SOUTH";
			case "西":
				return "WEST";
			default:
				return "NORTH";
		}
	}

	/**
	 * 获取中发白信息<br/>
	 * 中：01、02、03、04、05、06、07 <br/>
	 * <p>
	 * 发：08、09、10、11、12、13、14 <br/>
	 * <p>
	 * 白：15、16、17、18、19、20 <br/>
	 *
	 * @param number 数字
	 * @return 中发白
	 */
	public String msw(int number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		if (number <= 7) {
			return "中";
		} else if (number <= 14) {
			return "发";
		} else {
			return "白";
		}
	}

	/**
	 * 获取中发白英文信息
	 *
	 * @param number 数字
	 * @return MEDIUM    SEND	WHITE
	 */
	public String mswEn(int number) {
		String msw = msw(number);
		if ("中".equals(msw)) {
			return "MEDIUM";
		} else if ("发".equals(msw)) {
			return "SEND";
		} else {
			return "WHITE";
		}
	}
	/**
	 * 尾大 尾小
	 *
	 * @param number 数字
	 * @return > 5 大
	 */
	public Object tailSize(int number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		number %= 10;
		return number <= 5 ? "尾小" : "尾大";
	}

	/**
	 * 尾大 尾小
	 *
	 * @param number 数字
	 * @return > 5 BIG
	 */
	public Object tailSizeEn(int number) {
		return "尾小".equals(tailSize(number)) ? "TAIL_SMALL" : "TAIL_BIG";
	}



	//endregion

	//region 编码区

	private static final String[] RANK = {"第一球", "第二球", "第三球", "第四球", "第五球", "第六球", "第七球", "第八球", "总和"};

	public static final String[] TYPE = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14",
			"15", "16", "17", "18", "19", "20", "东", "南", "西", "北", "中", "发", "白", "合单", "合双", "大", "小", "单", "双", "尾大",
			"尾小", "龙", "虎"};

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
		//0-3后 龙虎和不可能
		if (rank > 3 && type > 36) {
			return null;
		}
		//总和  数字-方位-中发白 不可能
		if (rank == 8 && type < 28) {
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
		//方位
		fundsMatrix = betInfo.get("POSITION");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 20);
		//中发白
		fundsMatrix = betInfo.get("MSW");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 24);
		//合单双
		fundsMatrix = betInfo.get("SUM_SINGLE");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 27);
		//大小
		fundsMatrix = betInfo.get("BIG_SMALL");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 29);
		//单双
		fundsMatrix = betInfo.get("SINGLE_DOUBLE");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 31);
		//尾数大小
		fundsMatrix = betInfo.get("TAIL_SIZE");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 33);

		//龙虎
		fundsMatrix = betInfo.get("DRAGON_TIGER");
		fundsTh = concatBetItem(fundsMatrix, betItem, betRate, fundsTh, 0, 35);
		//冠亚
		fundsMatrix = betInfo.get("FIRST_SECOND");
		if (ContainerTool.notEmpty(fundsMatrix)) {
			String rank = rankStr(8);
			//循环 类型
			for (int type = 0; type < fundsMatrix[0].length; type++) {
				//金额 向上取整
				int fundTh = (int) (Math.ceil(fundsMatrix[0][type] * (betRate / 1000)) * 1000);
				//存在投注金额
				if (fundTh > 0) {
					fundsTh += fundTh;
					betItem.append(rank).append("|").append(TYPE[type + 29]).append("|").append(fundTh).append("#");
				}
			}
		}
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
				log.error("没有找到快乐类游戏排名:".concat(content));
				continue;
			}
			int fundsTh = NumberTool.getInteger(bet[2]);
			int type = type(bet[1]);
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
			verticalMerge(fundsMatrix);
		}
	}

	@Override
	protected void mergeTwoSizeItem(Map<String, int[][]> betInfo, int[] fundsArr, int rank, int type,
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

	//endregion

}
