package com.cloud.common.game.idc;
import com.cloud.common.game.NumberGame;
import com.cloud.common.game.PeriodConfig;
import com.cloud.common.game.PeriodOperation;
import org.doming.core.tools.DateTool;

import java.util.Date;
/**
 * 澳洲幸运10
 *
 * @Author: Dongming
 * @Date: 2020-04-30 11:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AustraliaLucky10 extends NumberGame implements PeriodOperation<Integer> {
	private static final long START_LONG = 1585697640000L;
	private static final int BASE_PERIOD = 20680552;
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_5;

	private static final String START_TIME = "07:34";
	private static final String END_TIME = "03:59";

	@Override public long period() {
		return PERIOD;
	}

	@Override public Integer findPeriod() {
		return findPeriod(new Date());
	}
	@Override public Integer findLotteryPeriod() {
		return findPeriod(new Date()) - 1;
	}
	@Override public long getDrawTime(Integer period) {
		return (period - BASE_PERIOD) * PERIOD + START_LONG;
	}
	@Override public Integer[] listPeriod(int size) {
		int period = findPeriod();
		Integer[] periods = new Integer[size];
		for (int i = 0; i < size; i++) {
			periods[size - i - 1] = period - i;
		}
		return periods;
	}

	public Date getStartTime(){
		return DateTool.getHm(START_TIME);
	}

	public Date getEndTime(){
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
