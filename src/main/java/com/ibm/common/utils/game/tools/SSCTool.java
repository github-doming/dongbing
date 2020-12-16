package com.ibm.common.utils.game.tools;
import java.util.Arrays;
/**
 * @Description: 时时彩工具类
 * @Author: Dongming
 * @Date: 2019-10-17 18:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SSCTool {

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 5 大
	 */
	public static String size(int number) {
		if (number < 0 || number > 9) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number <= 5 ? "小" : "大";
	}
	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 5 BIG
	 */
	public static String sizeEn(int number) {
		if (number < 0 || number > 9) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number <= 5 ? "SMALL" : "BIG";
	}

	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	public static String single(int number) {
		if (number < 0 || number > 45) {
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
	public static String singleEn(int number) {
		if (number < 0 || number > 45) {
			throw new RuntimeException("错误数值装换:" + number);
		}
		return number % 2 == 0 ? "DOUBLE" : "SINGLE";
	}
	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 数字1>数字2 龙
	 */
	public static String dragon(Integer number1, Integer number2) {
		if (number1 < 0 || number1 > 9 || number2 < 0 || number2 > 9) {
			throw new RuntimeException("错误数值装换:" + number1+","+number2);
		}
		return number1 > number2 ? "龙" : number1 < number2 ? "虎" : "和";

	}
	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 数字1>数字2 龙
	 */
	public static String dragonEn(Integer number1, Integer number2) {
		if (number1 < 0 || number1 > 9 || number2 < 0 || number2 > 9) {
			throw new RuntimeException("错误数值装换:" + number1+","+number2);
		}
		return number1 > number2 ? "DRAGON" : number1 < number2 ? "TIGER" : "TOTAL";
	}
	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return >= 23 BIG
	 */
	public static String sizeTotal(int number) {
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
	public static String sizeTotalEn(int number) {
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
	public static String threeBalls(Integer number1, Integer number2, Integer number3) {
		int[] num = {number1, number2, number3};
		Arrays.sort(num);
		if (num[0] == num[1] && num[0] == num[2]) {
			return "豹子";

		} else if (num[0] + 1 == num[1] && num[1] + 1 == num[2]) {
			return "顺子";
		} else if (num[0] == 0 && num[2] == 9) {
			if (num[1] == 8 || num[1] == 1){
				//顺子089,019
				return "顺子";
			}else{
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
	public static String threeBallsEn(String threeBalls) {
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
}
