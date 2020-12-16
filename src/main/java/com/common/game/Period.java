package com.common.game;
import org.doming.core.tools.DateTool;

import java.text.ParseException;

/**
 * 期数
 *
 * @Author: Dongming
 * @Date: 2020-05-12 18:13
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface Period<T> {

	/**
	 * 获取正在投注期数
	 *
	 * @return 正在投注期数
	 */
	T findPeriod();

	/**
	 * 获取以开奖期数
	 *
	 * @return 开奖期数
	 */
	T findLotteryPeriod();


	/**
	 * 获取开奖时间戳
	 *
	 * @param periodObj 期数
	 * @return 开奖时间戳
	 * @throws ParseException 转换时间错误
	 */
	long getDrawTime(Object periodObj) throws ParseException;


	/**
	 * 获取开奖期数列表
	 *
	 * @param size 获取数量
	 * @return 开奖期数列表
	 * @throws ParseException 转换时间错误
	 */
	T[] listPeriod(int size) throws ParseException;

	/**
	 * 获取正在投注的开奖时间
	 *
	 * @return 正在投注的开奖时间
	 * @throws ParseException 获取时间戳异常
	 */
	default long getDrawTime() throws ParseException {
		return getDrawTime(findPeriod());
	}

	/**
	 * 获取开奖期数的开奖时间
	 *
	 * @return 开奖期数的开奖时间
	 * @throws ParseException 获取时间戳异常
	 */
	default long getLotteryDrawTime() throws ParseException {
		return getDrawTime(findLotteryPeriod());
	}
	/**
	 * 获取开奖表名
	 * 开奖表名
	 * @return
	 */
	String getDrawTableName();

	/**
	 * 获取跟进期数
	 * @param period		期数
	 * @param followPeriod	跟随期数
	 * @return
	 * @throws ParseException 获取时间戳异常
	 */
	T findBeforePeriod(Object period, int followPeriod) throws ParseException;

	/**
	 * 获取DayXd 型期数数组
	 *
	 * @param period 开始期数
	 * @param format 格式化模板
	 * @param cycle  周期
	 * @param size   获取个数
	 * @return 期数数组
	 * @throws ParseException 转换时间错误
	 */
	default String[] listDayXdPeriod(String period, String format, int cycle, int size) throws ParseException {
		String[] periods = new String[size];
		String[] strs = period.split("-");
		int num = Integer.parseInt(strs[1]);
		if (num > size) {
			for (int i = 0; i < size; i++) {
				periods[size - i - 1] = String.format(format, strs[0], num - i);
			}
		} else {
			for (int i = 0; i < num; i++) {
				periods[size - i - 1] = String.format(format, strs[0], num - i);
			}
			strs[0] = DateTool.getYesterday(strs[0]);
			for (int i = num; i < size; i++) {
				periods[size - i - 1] = String.format(format, strs[0], cycle - i + num);
			}
		}
		return periods;
	}

}
