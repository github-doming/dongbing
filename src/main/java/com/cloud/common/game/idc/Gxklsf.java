package com.cloud.common.game.idc;

import com.cloud.common.game.HappyGame;
import com.cloud.common.game.PeriodConfig;
import com.cloud.common.game.PeriodOperation;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.util.Date;

/**
 * 广西快乐十分
 *
 * @Author: Dongming
 * @Date: 2020-04-21 18:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Gxklsf extends HappyGame implements PeriodOperation<String> {

	private static final String GXKLSF_TIME_START = "09:21";
	private static final String GXKLSF_TIME_END = "22:21";
	private static final Long GXKLSF_START_LONG = 1584633600000L;
	private static final int GXKLSF_INDEX = 30;
	private static final String GXKLSF_PERIOD_FORMAT = "%s%03d-%02d";
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_20;

	@Override public long period() {
		return PERIOD;
	}

	@Override public String findLotteryPeriod() {
		Date date = new Date();
		int period = findGxklsfDayPeriod(date) - 1;
		if (period < 1) {
			period = 40;
		}
		long index = GXKLSF_INDEX + (date.getTime() - GXKLSF_START_LONG) / DateTool.getMillisecondsDay();
		Date startTime = DateTool.getHm(date, GXKLSF_TIME_START);
		if (startTime.after(date)) {
			index--;
		}
		String year = DateTool.getYear(date);
		return String.format(GXKLSF_PERIOD_FORMAT, year, index, period);
	}

	@Override public String findPeriod() {
		Date date = new Date();
		int period = findGxklsfDayPeriod(date);
		long index = GXKLSF_INDEX + (date.getTime() - GXKLSF_START_LONG) / DateTool.getMillisecondsDay();
		Date endTime = DateTool.getHm(date, GXKLSF_TIME_END);
		if (date.after(endTime)) {
			index++;
		}
		String year = DateTool.getYear(date);
		return String.format(GXKLSF_PERIOD_FORMAT, year, index, period);
	}

	@Override public long getDrawTime(String period) {
		String number = period.split("-")[1];
		int index = NumberTool.getInteger(period.split("-")[0].substring(4));
		return GXKLSF_START_LONG + DateTool.getMillisecondsDays(index - GXKLSF_INDEX) + DateTool.getMillisecondsHours(9)
				+ DateTool.getMillisecondsMinutes(21) + (Integer.parseInt(number) - 1) * PERIOD;
	}

	@Override public String[] listPeriod(int size) {
		Date date = new Date();
		String period = findPeriod();
		String[] periods = new String[size];
		String[] strs = period.split("-");
		int num = Integer.parseInt(strs[1]);
		if (num > size) {
			for (int i = 0; i < size; i++) {
				periods[size - i - 1] = String.format(PeriodConfig.PERIOD_FORMAT_D_3, strs[0], num - i);
			}
		} else {
			String year = DateTool.getYear(date);
			int index = NumberTool.getInteger(strs[0].substring(4));
			for (int i = 0; i < num; i++) {
				periods[size - i - 1] = String.format(GXKLSF_PERIOD_FORMAT, year, index, num - i);
			}
			index--;
			for (int i = num; i < size; i++) {
				periods[size - i - 1] = String.format(GXKLSF_PERIOD_FORMAT, year, index, 40 - i + num);
			}
		}
		return periods;
	}

	/**
	 * 获取广西快乐十分 每一天的期数
	 *
	 * @param date 指定时间
	 * @return 期数
	 */
	private static int findGxklsfDayPeriod(Date date) {
		Date startTime = DateTool.getHm(date, GXKLSF_TIME_START);
		Date endTime = DateTool.getHm(date, GXKLSF_TIME_END);
		int period = 1;
		if (date.after(startTime) && endTime.after(date)) {
			period = DateTool.getPeriod(startTime, date, PERIOD).intValue() + 2;
		}
		return period;
	}
}
