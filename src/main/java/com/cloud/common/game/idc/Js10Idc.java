package com.cloud.common.game.idc;

import com.cloud.common.game.NumberGame;
import com.cloud.common.game.PeriodConfig;
import com.cloud.common.game.PeriodOperation;
import org.doming.core.tools.DateTool;

import java.util.Date;

/**
 * IDC急速赛车
 *
 * @Author: Dongming
 * @Date: 2020-04-21 17:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Js10Idc extends NumberGame implements PeriodOperation<Integer> {

	private static final long JS_DATA_START_LONG = 1571182230000L;
	private static final int JS10_BASE_PERIOD = 31244985;
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_1;

	private static final String START_TIME = "07:30:30";
	private static final String END_TIME = "04:00:30";

	@Override public long period() {
		return PERIOD;
	}

	@Override public Integer findLotteryPeriod() {
		return findPeriod(new Date()) - 1;
	}

	@Override public Integer findPeriod() {
		return findPeriod(new Date());
	}

	@Override public long getDrawTime(Integer period) {
		return (period - JS10_BASE_PERIOD) * PERIOD + JS_DATA_START_LONG;
	}

	@Override public Integer[] listPeriod(int size) {
		int period = findPeriod();
		Integer[] periods = new Integer[size];
		for (int i = 0; i < size; i++) {
			periods[size - i - 1] = period - i;
		}
		return periods;
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
			return DateTool.getPeriod(endTime.getTime(), JS_DATA_START_LONG, PERIOD).intValue() + 1 + JS10_BASE_PERIOD;
		}
		return DateTool.getPeriod(date.getTime(), JS_DATA_START_LONG, PERIOD).intValue() + JS10_BASE_PERIOD + 1;
	}
}
