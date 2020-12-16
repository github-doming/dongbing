package com.cloud.common.game;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
/**
 * 快乐类游戏
 *
 * @Author: Dongming
 * @Date: 2020-05-21 10:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HappyGame implements GameOperation {
	@Override public String getDrawItem(String drawNumber) {
		StringBuilder drawItem = new StringBuilder();
		//号码
		String[] drawNumberStrs = drawNumber.split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 8 != drawNumbers.length) {
			return null;
		}
		for (int i = 1; i <= 8; i++) {
			int num = drawNumbers[i - 1];
			drawItem.append(StringTool.CHINESE_HAPPY_RANK[i]).append("|").append(num).append(",");
			drawItem.append(StringTool.CHINESE_HAPPY_RANK[i]).append("|").append(size(num)).append(",");
			drawItem.append(StringTool.CHINESE_HAPPY_RANK[i]).append("|").append(single(num)).append(",");
			drawItem.append(StringTool.CHINESE_HAPPY_RANK[i]).append("|").append(tailSize(num)).append(",");
			drawItem.append(StringTool.CHINESE_HAPPY_RANK[i]).append("|").append(sumSingle(num)).append(",");
			drawItem.append(StringTool.CHINESE_HAPPY_RANK[i]).append("|").append(msw(num)).append(",");
			drawItem.append(StringTool.CHINESE_HAPPY_RANK[i]).append("|").append(position(num)).append(",");
		}
		for (int i = 1; i <= 4; i++) {
			drawItem.append(StringTool.CHINESE_HAPPY_RANK[i]).append("|")
					.append(dragon(drawNumbers[i - 1], drawNumbers[8 - i])).append(",");
		}
		int total = 0;
		for (int i = 0; i < 8; i++) {
			total += drawNumbers[i];
		}
		drawItem.append(StringTool.CHINESE_HAPPY_RANK[0]).append("|").append(sizeTotal(total)).append(",");
		drawItem.append(StringTool.CHINESE_HAPPY_RANK[0]).append("|").append(singleTotal(total)).append(",");
		drawItem.append(StringTool.CHINESE_HAPPY_RANK[0]).append("|").append(tailTotalSize(total)).append(",");
		drawItem.append(StringTool.CHINESE_HAPPY_RANK[0]).append("|").append(dragon(drawNumbers[0], drawNumbers[7])).append(",");
		return drawItem.toString();
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 10 大
	 */
	public String size(Integer number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number <= 10 ? "小" : "大";
	}
	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	public Object single(int number) {
		if (number < 1 || number > 20) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number % 2 == 0 ? "双" : "单";
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
		return number < 5 ? "尾小" : "尾大";
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
}
