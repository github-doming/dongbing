package com.cloud.common.game.idc;

import com.cloud.common.game.NumberGame;
import com.cloud.common.game.PeriodConfig;
import com.cloud.common.game.PeriodOperation;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.text.ParseException;
import java.util.Date;

/**
 * EPS赛马
 *
 * @Author: Dongming
 * @Date: 2020-04-21 18:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Eps2 extends NumberGame implements PeriodOperation<String> {

	private static final String EPS2_TIME_START = "12:02";
	private static final String EPS2_TIME_BEGIN = "09:02";
	private static final String EPS2_TIME_END = "04:00";
	private static final int EPS2_PASS_PERIOD = 361;
	private static final int EPS2_BASE_PERIOD = 481;
	private static final int EPS2_CYCLE = 570;
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_2;

	@Override public long period() {
		return PERIOD;
	}

	@Override public String findPeriod() {
		Date date = new Date();
		int period = findDayPeriod(date);
		String day;
		Date begin = DateTool.getHm(date, "12:00");
		if (date.before(begin)) {
			day = DateTool.getYesterday(date);
		} else {
			day = DateTool.getDay(date);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D3, day, period);
	}

	@Override public String findLotteryPeriod() {
		Date date = new Date();
		int period = findDayPeriod(date) - 1;
		if (period < 1) {
			period = 570;
		}
		String day;
		Date begin = DateTool.getHm(date, EPS2_TIME_START);
		if (date.before(begin)) {
			day = DateTool.getYesterday(date);
		} else {
			day = DateTool.getDay(date);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D3, day, period);
	}

	@Override public long getDrawTime(String period) throws ParseException {
		Date day = DateTool.getDay(period.substring(0, 8));
		int number = NumberTool.getInteger(period.substring(8, 11));

		if (number < EPS2_PASS_PERIOD) {
			return DateTool.getMillisecondsHours(12) + number * PERIOD + day.getTime();
		} else if (number < EPS2_BASE_PERIOD) {
			day = DateTool.getAfterDate(day);
			return (number - EPS2_PASS_PERIOD + 1) * PERIOD + day.getTime();
		} else {
			day = DateTool.getAfterDate(day);
			return DateTool.getMillisecondsHours(12) - (EPS2_CYCLE - number) * PERIOD + day.getTime();
		}
	}

	@Override public String[] listPeriod(int size) throws ParseException {
		String[] periods = new String[size];
		String period = findPeriod();
		String day = period.substring(0, 8);
		int num = NumberTool.getInteger(period.substring(8, 11));
		if (num > size) {
			for (int i = 0; i < size; i++) {
				periods[size - i - 1] = String.format(PeriodConfig.PERIOD_FORMAT_D_3, day, num - i);
			}
		} else {
			for (int i = 0; i < num; i++) {
				periods[size - i - 1] = String.format(PeriodConfig.PERIOD_FORMAT_D3, day, num - i);
			}
			day = DateTool.getYesterday(day);
			for (int i = num; i < size; i++) {
				periods[size - i - 1] = String.format(PeriodConfig.PERIOD_FORMAT_D3, day, EPS2_CYCLE - i + num);
			}
		}
		return periods;
	}

	public Date getBeginTime() {
		return DateTool.getHm(EPS2_TIME_BEGIN);
	}

	/**
	 * 获取EPS赛马 每一天的期数
	 *
	 * @param date 指定时间
	 * @return 期数
	 */
	private int findDayPeriod(Date date) {
		Date startTime = DateTool.getHm(date, EPS2_TIME_START);
		Date beginTime = getBeginTime();
		Date endTime = DateTool.getHm(date, EPS2_TIME_END);
		Date dayStart = DateTool.getDayStart();
		int period;
		if (date.before(endTime)) {
			period = DateTool.getPeriod(dayStart, date, PERIOD).intValue() + EPS2_PASS_PERIOD;
		} else if (date.after(startTime)) {
			period = DateTool.getPeriod(startTime, date, PERIOD).intValue() + 2;
		} else if (date.after(beginTime)) {
			period = EPS2_BASE_PERIOD + DateTool.getPeriod(beginTime, date, PERIOD).intValue() + 1;
		} else {
			period = EPS2_BASE_PERIOD;
		}
		return period;
	}
}
