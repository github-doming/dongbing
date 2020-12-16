package com.common.game.idc;

import com.common.config.PeriodConfig;
import com.common.game.Country10;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.util.Date;

/**
 * 澳洲幸运10
 *
 * @Author: Dongming
 * @Date: 2020-05-13 17:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AustraliaLucky10 extends Country10<Integer> {
	private static final long START_LONG = 1585697640000L;
	private static final int BASE_PERIOD = 20680552;
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_5;

	private static final String START_TIME = "07:34";
	private static final String END_TIME = "03:59";

	@Override public Integer findPeriod() {

		return findPeriod(new Date());
	}
	@Override public Integer findLotteryPeriod() {
		return findPeriod(new Date()) - 1;
	}
	@Override public long getDrawTime(Object periodObj) {
		return (NumberTool.getInteger(periodObj) - BASE_PERIOD) * PERIOD + START_LONG;
	}
	@Override public Integer[] listPeriod(int size) {
		int period = findPeriod();
		Integer[] periods = new Integer[size];
		//TODO 优化期数区间不获取封盘时间的期数 - 懒得做
		for (int i = 0; i < size; i++) {
			periods[size - i - 1] = period - i;
		}
		return periods;
	}

	@Override
	public Integer findBeforePeriod(Object period, int index)  {
		if(NumberTool.getInteger(period)==0){
			return 0;
		}
		return NumberTool.getInteger(period)- index;
	}

	public static Date getStartTime() {
		return DateTool.getHm(START_TIME);
	}

	public static Date getEndTime() {
		return DateTool.getHm(END_TIME);
	}

	/**
	 * 根据时间获取期数
	 *
	 * @param date 指定时间
	 * @return 期数
	 */
	private Integer findPeriod(Date date) {
		Date startTime = getStartTime();
		Date endTime = getEndTime();
		if (date.before(startTime) && date.after(endTime)) {
			return DateTool.getPeriod(endTime.getTime(), START_LONG, PERIOD).intValue() + 1 + BASE_PERIOD;
		}
		return DateTool.getPeriod(date.getTime(), START_LONG, PERIOD).intValue() + BASE_PERIOD + 1;
	}
}
