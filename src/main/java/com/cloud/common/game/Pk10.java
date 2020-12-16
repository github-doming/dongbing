package com.cloud.common.game;


import org.doming.core.tools.DateTool;

import java.util.Date;

/**
 * 北京赛车
 *
 * @Author: Dongming
 * @Date: 2020-04-21 16:12
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Pk10 extends NumberGame implements PeriodOperation<Integer> {

	private static final int PK10_CYCLE = 44;
	private static final int PK10_BASE_PERIOD = 744264;

	private static final String PK10_TIME_START = "09:30";
	private static final long PK10_DATA_START =1588728600000L;

	public static final long PERIOD = PeriodConfig.PERIOD_TIME_20;
	@Override public long period() {
		return PERIOD;
	}

	@Override
	public Integer findLotteryPeriod() {
		return findPeriod(new Date()) - 1;
	}

	@Override
	public Integer findPeriod() {
		return findPeriod(new Date());
	}

	@Override
	public Integer[] listPeriod(int size) {
		return new Integer[0];
	}


	/**
	 * 根据时间获取期数
	 *
	 * @param date 指定时间
	 * @return 期数
	 */
	private Integer findPeriod(Date date) {
		Date startTime = DateTool.getHm(date, PK10_TIME_START);
		int dayDifference = DateTool.getDaysBetween(PK10_DATA_START, startTime.getTime()).intValue();
		int todayPeriod = 0;
		if (startTime.before(date)) {
			todayPeriod = DateTool.getPeriod(startTime, date, PERIOD).intValue() + 1;
		}
		return PK10_BASE_PERIOD + dayDifference * PK10_CYCLE + todayPeriod;
	}

	/**
	 * 获取开奖时间戳
	 * @param period 指定期数
	 * @return 开奖时间戳
	 */
	@Override
	public long getDrawTime(Integer period) {
		if (period < PK10_BASE_PERIOD) {
			throw new RuntimeException("不能查找基础期数之前的期数");
		}
		int day = (period - PK10_BASE_PERIOD) / PK10_CYCLE;
		int dayPeriod = (period - PK10_BASE_PERIOD) % PK10_CYCLE;
		return PK10_DATA_START + DateTool.getMillisecondsDays(day) + dayPeriod * PERIOD;
	}
}
