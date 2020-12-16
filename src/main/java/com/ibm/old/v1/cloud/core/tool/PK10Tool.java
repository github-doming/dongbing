package com.ibm.old.v1.cloud.core.tool;
import com.ibm.old.v1.cloud.ibm_rep_draw_pk10.t.service.IbmRepDrawPk10TService;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
/**
 * @Description: pk10工具类
 * @Author: Dongming
 * @Date: 2018-12-22 16:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PK10Tool {

	private static Map<Object, String[]> history20Data;

	private static Map<Integer, String[][]> hotAndCold = new HashMap<>(1000);
	private static Map<Integer, String[]> historyData = new HashMap<>(1000);

	private static volatile Integer period;
	private static volatile String lottery;
	public static String getLottery(Integer period) {
		if (period.equals(PK10Tool.period)) {
			return lottery;
		}
		return null;
	}
	public static boolean setLottery(Integer period, String lottery) {
		if (!period.equals(PK10Tool.period)) {
			synchronized (PK10Tool.class) {
				if (!period.equals(PK10Tool.period)) {
					PK10Tool.period = period;
					PK10Tool.lottery = lottery;
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 查找最近20期历史开奖数据<br>
	 * eg. 历史开奖数据    key-期数，value-开奖号码
	 *
	 * @param period 当前期数
	 */
	public static void find20HistoryData(Integer period) throws SQLException {
		Integer[] periods = findHistoryPeriod(period, 20);
		history20Data = mapHistoryData(periods);
	}

	/**
	 * 获取冷热数据
	 *
	 * @param period 基准期数
	 * @return 冷热数据
	 */
	public static String[][] findHotAndCold(Integer period) throws SQLException {
		if (!hotAndCold.containsKey(period)) {
			synchronized (PK10Tool.class) {
				if (!hotAndCold.containsKey(period)) {
					hotAndCold.remove(period - 1000);
					String[][] hotAndColdData = new IbmRepDrawPk10TService()
							.findHotAndCold(PeriodTool.getPK10DrawTime(period));
					hotAndCold.put(period, hotAndColdData);
				}
			}
		}
		return hotAndCold.get(period);
	}

	/**
	 * 获取最近20期历史开奖数据
	 *
	 * @return 历史开奖数据
	 */
	public static Map<Object, String[]> getHistoryData() {
		return history20Data;
	}

	/**
	 * 获取历史开奖数据
	 *
	 * @param period 基准期数
	 * @return 历史开奖数据
	 */
	public static String[] getHistoryData(Integer period) throws SQLException {
		if (!historyData.containsKey(period)) {
			synchronized (PK10Tool.class) {
				if (!historyData.containsKey(period)) {
					String drawNumber = new IbmRepDrawPk10TService().findDrawNumber(period);
					if (StringTool.notEmpty(drawNumber)) {
						historyData.remove(period - 1000);
						historyData.put(period, drawNumber.split(","));
					}
				}
			}
		}
		return historyData.get(period);
	}

	/**
	 * 查找历史开奖数据
	 *
	 * @param periods 期数数组
	 * @return 历史开奖数据    key-期数，value-开奖号码
	 */

	public static Map<Object, String[]> mapHistoryData(Integer[] periods) throws SQLException {
		Map<Object, Object> drawNumber = new IbmRepDrawPk10TService().listDrawNumber(periods);
		Map<Object, String[]> historyData = new HashMap<>(periods.length);
		drawNumber.forEach(
				(key, value) -> historyData.put(Integer.parseInt(key.toString()), value.toString().split(",")));
		return historyData;
	}

	/**
	 * 查找历史期数
	 *
	 * @param period 当前期数
	 * @param number 查找数量
	 * @return 历史期数    Integer[]
	 */
	public static Integer[] findHistoryPeriod(Integer period, int number) {
		Integer[] periods = new Integer[number];
		for (int i = 1; i <= number; i++) {
			periods[i - 1] = period - i;
		}
		return periods;
	}

	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 5 大
	 */
	public static String size(int number) {
		if (number < 1 || number > 10) {
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
		if (number < 1 || number > 10) {
			throw new RuntimeException("错误数值装换");
		}
		return number <= 5 ? "SMALL" : "BIG";
	}
	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 11 大
	 */
	public static String sizeChampionSum(int number) {
		if (number < 3 || number > 19) {
			throw new RuntimeException("错误数值装换");
		}
		return number <= 11 ? "小" : "大";
	}
	/**
	 * 获取大小
	 *
	 * @param number 数字
	 * @return > 11 BIG
	 */
	public static String sizeChampionSumEn(int number) {
		if (number < 3 || number > 19) {
			throw new RuntimeException("错误数值装换");
		}
		return number <= 11 ? "SMALL" : "BIG";
	}

	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	public static Object single(int number) {
		if (number < 1 || number > 10) {
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
	public static Object singleEn(int number) {
		if (number < 1 || number > 10) {
			throw new RuntimeException("错误数值装换");
		}
		return number % 2 == 0 ? "DOUBLE" : "SINGLE";
	}

	/**
	 * 获取单双
	 *
	 * @param number 数字
	 * @return 奇数 单
	 */
	public static String singleChampionSum(int number) {
		if (number < 3 || number > 19) {
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
	public static String singleChampionSumEn(int number) {
		if (number < 3 || number > 19) {
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
	public static Object dragon(Integer number1, Integer number2) {
		if (number1 < 1 || number1 > 10 || number2 < 1 || number2 > 10) {
			throw new RuntimeException("错误数值装换");
		}
		return number1 > number2 ? "龙" : "虎";
	}
	/**
	 * 获取龙虎
	 *
	 * @param number1 数字1
	 * @param number2 数字2
	 * @return 数字1>数字2 龙
	 */
	public static Object dragonEn(Integer number1, Integer number2) {
		if (number1 < 1 || number1 > 10 || number2 < 1 || number2 > 10) {
			throw new RuntimeException("错误数值装换");
		}
		return number1 > number2 ? "DRAGON" : "TIGER";
	}

}
