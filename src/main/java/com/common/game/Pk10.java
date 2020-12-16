package com.common.game;

import com.common.config.PeriodConfig;
import com.common.game.type.NumberGame;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.util.Date;

/**
 * 北京赛车
 *
 * @Author: Dongming
 * @Date: 2020-05-12 18:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Pk10 extends NumberGame implements Period<Integer> {

	private static final int PK10_CYCLE = 44;
	private static final int PK10_BASE_PERIOD = 744264;

	private static final String PK10_TIME_START = "09:30";
	private static final long PK10_DATA_START = 1588728600000L;

	public final long PERIOD = PeriodConfig.PERIOD_TIME_20;

	@Override
	public Integer findPeriod() {
		return findPeriod(new Date());
	}

	@Override
	public Integer findLotteryPeriod() {
		return findPeriod(new Date()) - 1;
	}

	@Override
	public long getDrawTime(Object periodObj) {
		int period = NumberTool.getInteger(periodObj);
		if (period < PK10_BASE_PERIOD) {
			throw new RuntimeException("不能查找基础期数之前的期数");
		}
		int day = (period - PK10_BASE_PERIOD) / PK10_CYCLE;
		int dayPeriod = (period - PK10_BASE_PERIOD) % PK10_CYCLE;
		return PK10_DATA_START + DateTool.getMillisecondsDays(day) + dayPeriod * PERIOD;
	}

	@Override
	public Integer[] listPeriod(int size) {
		Integer[] periods = new Integer[size];
		int period = findPeriod();
		for (int i = 0; i < size; i++) {
			periods[i] = period - i;
		}
		return periods;
	}

	@Override
	public Integer findBeforePeriod(Object period, int index) {
		if (NumberTool.getInteger(period) == 0) {
			return 0;
		}
		return NumberTool.getInteger(period) - index;
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

	@Override
	public String getDrawTableName() {
		return "rep_draw_pk10";
	}

}
