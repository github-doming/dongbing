package com.cloud.common.game.idc;

import com.cloud.common.game.BallGame;
import com.cloud.common.game.PeriodConfig;
import com.cloud.common.game.PeriodOperation;
import org.doming.core.tools.DateTool;

import java.util.Date;

/**
 * IDC急速时时彩
 *
 * @Author: Dongming
 * @Date: 2020-04-21 17:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JssscIdc extends BallGame implements PeriodOperation<Integer> {

	private static final long JS_DATA_START_LONG = 1585697430000L;
	private static final int JSSSC_BASE_PERIOD = 11407785;
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
		return (period - JSSSC_BASE_PERIOD) * PERIOD + JS_DATA_START_LONG;
	}

	@Override public Integer[] listPeriod(int size) {
		int period = findPeriod();
		Integer[] periods = new Integer[size];
		for (int i = 0; i < size; i++) {
			periods[size - i - 1] = period - i;
		}
		return periods;
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
			return DateTool.getPeriod(endTime.getTime(), JS_DATA_START_LONG, PERIOD).intValue() + 1 + JSSSC_BASE_PERIOD;
		}
		return DateTool.getPeriod(date.getTime(), JS_DATA_START_LONG, PERIOD).intValue() + JSSSC_BASE_PERIOD + 1;
	}

	public Date getStartTime() {
		return DateTool.getTime(START_TIME);
	}

	public Date getEndTime() {
		return DateTool.getTime(END_TIME);
	}

}
