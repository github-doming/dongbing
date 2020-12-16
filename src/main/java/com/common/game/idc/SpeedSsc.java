package com.common.game.idc;

import com.common.config.PeriodConfig;
import com.common.game.Jsssc;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.util.Date;

/**
 * 急速时时彩
 *
 * @Author: Dongming
 * @Date: 2020-05-13 17:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SpeedSsc extends Jsssc<Integer> {

	private static final long JS_DATA_START_LONG = 1585697430000L;
	private static final int JSSSC_BASE_PERIOD = 11407785;
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_1;

	private static final String START_TIME = "07:30:30";
	private static final String END_TIME = "04:00:30";

	@Override public Integer findPeriod() {
		return findPeriod(new Date());
	}
	@Override public Integer findLotteryPeriod() {
		return findPeriod(new Date()) - 1;
	}
	@Override public long getDrawTime(Object periodObj) {
		return (NumberTool.getInteger(periodObj) - JSSSC_BASE_PERIOD) * PERIOD + JS_DATA_START_LONG;

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

	public Date getStartTime() {
		return DateTool.getTime(START_TIME);
	}

	public Date getEndTime() {
		return DateTool.getTime(END_TIME);
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
			return DateTool.getPeriod(endTime.getTime(), JS_DATA_START_LONG, PERIOD).intValue() + JSSSC_BASE_PERIOD;
		}
		return DateTool.getPeriod(date.getTime(), JS_DATA_START_LONG, PERIOD).intValue() + 1 + JSSSC_BASE_PERIOD;
	}
}
