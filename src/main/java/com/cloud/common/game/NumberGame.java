package com.cloud.common.game;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
/**
 * 号码类游戏
 *
 * @Author: Dongming
 * @Date: 2020-05-21 10:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class NumberGame implements GameOperation {
	@Override public String getDrawItem(String drawNumber) {
		StringBuilder drawItem = new StringBuilder();
		//号码
		String[] drawNumberStrs = drawNumber.split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 10 != drawNumbers.length) {
			return null;
		}
		for (int i = 1; i <= 10; i++) {
			int num = drawNumbers[i - 1];
			drawItem.append(StringTool.CHINESE_NUMBER_RANK[i]).append("|").append(num).append(",");
			drawItem.append(StringTool.CHINESE_NUMBER_RANK[i]).append("|").append(size(num)).append(",");
			drawItem.append(StringTool.CHINESE_NUMBER_RANK[i]).append("|").append(single(num)).append(",");
		}
		for (int i = 1; i <= 5; i++) {
			drawItem.append(StringTool.CHINESE_NUMBER_RANK[i]).append("|")
					.append(dragon(drawNumbers[i - 1], drawNumbers[10 - i])).append(",");
		}
		int championSum = drawNumbers[0] + drawNumbers[1];
		drawItem.append(StringTool.CHINESE_NUMBER_RANK[0]).append("|").append(championSum).append(",");
		drawItem.append(StringTool.CHINESE_NUMBER_RANK[0]).append("|").append(sizeChampionSum(championSum)).append(",");
		drawItem.append(StringTool.CHINESE_NUMBER_RANK[0]).append("|").append(singleChampionSum(championSum)).append(",");

		return drawItem.toString();
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 5 大
	 */
	private String size(int number) {
		if (number < 1 || number > 10) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number <= 5 ? "小" : "大";
	}

	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	private String single(int number) {
		if (number < 0 || number > 45) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number % 2 == 0 ? "双" : "单";
	}

	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 数字1>数字2 龙
	 */
	public static Object dragon(Integer number1, Integer number2) {
		if (number1 < 1 || number1 > 10 || number2 < 1 || number2 > 10) {
			throw new RuntimeException("错误数值装换:" + number1+","+number2);
		}
		return number1 > number2 ? "龙" : "虎";
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 11 大
	 */
	private String sizeChampionSum(int number) {
		if (number < 3 || number > 19) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number <= 11 ? "小" : "大";
	}

	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	private String singleChampionSum(int number) {
		if (number < 3 || number > 19) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number % 2 == 0 ? "双" : "单";
	}
}
