package com.ibm.common.utils.game.tools;

/**
 * 快乐彩类游戏工具类
 *
 * @Author: Dongming
 * @Date: 2020-04-22 18:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class KlcTool {

	//region 翻译区

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 10 大
	 */
	public static String size(Integer number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number <= 10 ? "小" : "大";
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 5 BIG
	 */
	public static String sizeEn(int number) {
		return "小".equals(size(number)) ? "SMALL" : "BIG";
	}

	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	public static Object single(int number) {
		if (number < 1 || number > 20) {
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
	public static Object singleEn(int number) {
		return "双".equals(single(number)) ? "DOUBLE" : "SINGLE";
	}

	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 数字1>数字2 龙
	 */
	public static String dragon(Integer number1, Integer number2) {
		if (number1 < 1 || number1 > 20 || number2 < 1 || number2 > 20) {
			throw new RuntimeException("错误数值装换:" + number1 + "," + number2);
		}
		return number1 > number2 ? "龙" : "虎";
	}

	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 数字1>数字2 DRAGON
	 */
	public static Object dragonEn(Integer number1, Integer number2) {
		return "龙".equals(dragon(number1, number2)) ? "DRAGON" : "TIGER";
	}

	/**
	 * 总和尾大小
	 *
	 * @param number 数字
	 * @return 尾 > 5 BIG
	 */
	public static String tailTotalSizeEn(int number) {
		return "尾小".equals(tailTotalSize(number)) ? "TAIL_SMALL" : "TAIL_BIG";
	}

	/**
	 * 总和尾大小
	 *
	 * @param number 数字
	 * @return 尾 > 5 尾大
	 */
	public static String tailTotalSize(int number) {
		if (number < 36 || number > 132) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		number %= 10;
		return number <= 5 ? "尾小" : "尾大";
	}

	/**
	 * 总和单双
	 *
	 * @param number 数字
	 * @return 奇数 SINGLE
	 */
	public static String singleTotalEn(int number) {
		return "双".equals(singleTotal(number)) ? "DOUBLE" : "SINGLE";
	}

	/**
	 * 总和单双
	 *
	 * @param number 数字
	 * @return 奇数 SINGLE
	 */
	public static String singleTotal(int number) {
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
	public static String sizeTotal(int number) {
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
	 * 获取大小
	 *
	 * @param number 数字
	 * @return >= 23 BIG
	 */
	public static String sizeTotalEn(int number) {
		String size = sizeTotal(number);
		if ("小".equals(size)) {
			return "SMALL";
		} else if ("大".equals(size)) {
			return "BIG";
		} else {
			return "PEACE";
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
	public static String position(Integer number) {
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
	public static Object positionEn(Integer number) {
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
	public static String msw(Integer number) {
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
	public static String mswEn(Integer number) {
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
	public static Object tailSize(Integer number) {
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
	public static Object tailSizeEn(Integer number) {
		return "尾小".equals(tailSize(number)) ? "TAIL_SMALL" : "TAIL_BIG";
	}

	/**
	 * 合数单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	public static String sumSingle(Integer number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		number = number / 10 + number % 10;
		return number % 2 == 0 ? "合双" : "合单";
	}

	/**
	 * 合数单双
	 *
	 * @param number 数字
	 * @return 奇数 SINGLE
	 */
	public static String sumSingleEn(Integer number) {

		return "合双".equals(sumSingle(number)) ? "SUM_DOUBLE" : "SUM_SINGLE";
	}
	//endregion

}
