package com.ibm.old.v1.cloud.core.tool;

import com.ibm.old.v1.cloud.ibm_rep_draw_cqssc.t.service.IbmRepDrawCqsscTService;
import com.ibm.old.v1.common.doming.tools.PeriodTool;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 重庆时时彩工具类
 * @Author: Dongming
 * @Date: 2019-02-14 14:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CQSSCTool {

	private static Map<String, String[]> history20Data;

	private static volatile String period;
	private static volatile String lottery;
	public static String getLottery(String period) {
		if (period.equals(CQSSCTool.period)) {
			return lottery;
		}
		return null;
	}
	public static boolean setLottery(String period, String lottery) {
		if (!period.equals(CQSSCTool.period)) {
			synchronized (CQSSCTool.class) {
				if (!period.equals(CQSSCTool.period)) {
					CQSSCTool.period = period;
					CQSSCTool.lottery = lottery;
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 5 大
	 */
	public static String size(int number) {
		if (number < 0 || number > 9) {
			throw new RuntimeException("错误数值装换");
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
			throw new RuntimeException("错误数值装换");
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
			throw new RuntimeException("错误数值装换");
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
			throw new RuntimeException("错误数值装换");
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
			throw new RuntimeException("错误数值装换");
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
			throw new RuntimeException("错误数值装换");
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
			throw new RuntimeException("错误数值装换");
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
			throw new RuntimeException("错误数值装换");
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
		swapBig(num);
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

	private static void swapBig(int[] num) {
		int tmp;
		for (int i = 0, len = num.length; i < len; i++) {
			for (int j = 0; j < len - i - 1; j++) {
				if (num[j] > num[j + 1]) {
					tmp = num[j];
					num[j] = num[j + 1];
					num[j + 1] = tmp;
				}
			}
		}
	}
	/**
	 * 查找最近20期历史开奖数据
	 *
	 * @param period 当前期数
	 */
	public static void find20HistoryData(String period) throws SQLException, ParseException {
		history20Data = findHistoryData(period, 20);
	}
	/**
	 * 获取最近20期历史开奖数据
	 *
	 * @return 历史开奖数据
	 */
	public static Map<String, String[]> getHistoryData() {
		return history20Data;
	}
	/**
	 * 查找历史开奖数据
	 *
	 * @param period 当前期数
	 * @param number 查找数量
	 */
	private static Map<String, String[]> findHistoryData(String period, int number)
			throws SQLException, ParseException {
		String[] periods = findHistoryPeriod(period, number);
		Map<Object, Object> drawNumber = new IbmRepDrawCqsscTService().listDrawNumber(periods);
		Map<String, String[]> historyData = new HashMap<>(number);
		drawNumber.forEach((key, value) -> historyData.put(key.toString(), value.toString().split(",")));
		return historyData;
	}
	/**
	 * 查找历史期数
	 *
	 * @param period 当前期数
	 * @param number 查找数量
	 * @return 历史期数    Integer[]
	 */
	private static String[] findHistoryPeriod(String period, int number) throws ParseException {
		String[] periods = new String[number];
		for (int i = 1; i <= number; i++) {
			periods[i - 1] = PeriodTool.findCQSSCBeforePeriod(period, i + 1);
		}
		return periods;
	}

}
