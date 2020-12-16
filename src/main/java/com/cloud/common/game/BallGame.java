package com.cloud.common.game;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
/**
 * 球类游戏
 *
 * @Author: Dongming
 * @Date: 2020-05-21 10:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BallGame implements GameOperation {
	@Override public String getDrawItem(String drawNumber) {
		StringBuilder drawItem = new StringBuilder();
		//号码
		String[] drawNumberStrs = drawNumber.split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 5 != drawNumbers.length) {
			return null;
		}
		for (int i = 0; i < 5; i++) {
			int num = drawNumbers[i];
			drawItem.append(StringTool.CHINESE_BALL_RANK[i+1]).append("|").append(num).append(",");
			drawItem.append(StringTool.CHINESE_BALL_RANK[i+1]).append("|").append(size(num)).append(",");
			drawItem.append(StringTool.CHINESE_BALL_RANK[i+1]).append("|").append(single(num)).append(",");
		}
		int total = 0;
		for (int i = 0; i < 5; i++) {
			total += drawNumbers[i];
		}
		drawItem.append(StringTool.CHINESE_BALL_RANK[0]).append("|").append(total).append(",");
		drawItem.append(StringTool.CHINESE_BALL_RANK[0]).append("|").append(single(total)).append(",");
		drawItem.append(StringTool.CHINESE_BALL_RANK[0]).append("|").append(sizeTotal(total)).append(",");
		drawItem.append(StringTool.CHINESE_BALL_RANK[6]).append("|").append(dragon(drawNumbers[0], drawNumbers[4])).append(",");
		drawItem.append(StringTool.CHINESE_BALL_RANK[7]).append("|")
				.append(threeBalls(drawNumbers[0], drawNumbers[1], drawNumbers[2])).append(",");
		drawItem.append(StringTool.CHINESE_BALL_RANK[8]).append("|")
				.append(threeBalls(drawNumbers[1], drawNumbers[2], drawNumbers[3])).append(",");
		drawItem.append(StringTool.CHINESE_BALL_RANK[9]).append("|")
				.append(threeBalls(drawNumbers[2], drawNumbers[3], drawNumbers[4])).append(",");

		return drawItem.toString();
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 5 大
	 */
	private String size(int number) {
		if (number < 0 || number > 9) {
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
	 * 获取大小
	 *
	 * @param number 数字
	 * @return >= 23 BIG
	 */
	private String sizeTotal(int number) {
		if (number < 0 || number > 45) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number < 23 ? "小" : "大";
	}

	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 数字1>数字2 龙
	 */
	private String dragon(Integer number1, Integer number2) {
		if (number1 < 0 || number1 > 9 || number2 < 0 || number2 > 9) {
			throw new RuntimeException("错误数值装换:" + number1 + "," + number2);
		}
		return number1 > number2 ? "龙" : number1 < number2 ? "虎" : "和";
	}

	/**
	 * 获取三球信息
	 *
	 * @param number1 球1
	 * @param number2 球2
	 * @param number3 球3
	 * @return 三球
	 */
	private String threeBalls(Integer number1, Integer number2, Integer number3) {
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

}
