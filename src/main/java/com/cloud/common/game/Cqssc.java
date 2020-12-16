package com.cloud.common.game;

import org.doming.core.tools.DateTool;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

/**
 * 重庆时时彩
 *
 * @Author: Dongming
 * @Date: 2020-04-21 16:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Cqssc extends BallGame implements PeriodOperation<String>{

	private static final String CQSSC_TIME_BEGIN = "00:30";
	private static final String CQSSC_TIME_END = "03:10";
	private static final String CQSSC_TIME_START = "07:30";
	private static final String CQSSC_TIME_OVER = "23:50";

	private static final String CQSSC_BEGIN_TIME = "00:30:00";
	private static final String CQSSC_START_TIME = "07:30:00";

	private static final int CQSSC_CYCLE = 59;
	private static final int CQSSC_BASE_PERIOD = 10;
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_20;

	@Override public long period() {
		return PERIOD;
	}


	@Override
	public String findLotteryPeriod() {
		Date date = new Date();
		int period = findDayPeriod(date) - 1;
		if (period < 1) {
			period = 59;
		}
		String day = DateTool.getDay(date);
		Date begin = DateTool.getHm(date, CQSSC_TIME_BEGIN);
		if (date.before(begin)) {
			day = DateTool.getYesterday(date);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D_3, day, period);
	}


	@Override
	public String findPeriod() {
		Date date = new Date();
		int period = findDayPeriod(date);
		String day = DateTool.getDay(date);
		Date over = DateTool.getHm(date, CQSSC_TIME_OVER);
		if (date.after(over)) {
			day = DateTool.getTomorrow(date);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D_3, day, period);
	}

	@Override
	public long getDrawTime(String period) {
		String number = period.split("-")[1];
		if (Integer.parseInt(number) < 10) {
			return (Objects.requireNonNull(DateTool.getTime(CQSSC_BEGIN_TIME)).getTime()
					+ (Integer.parseInt(number) - 1) * PERIOD);
		} else {
			return (Objects.requireNonNull(DateTool.getTime(CQSSC_START_TIME)).getTime()
					+ (Integer.parseInt(number) - CQSSC_BASE_PERIOD) * PERIOD);
		}
	}

	@Override
	public String[] listPeriod(int size) throws ParseException {
		return listDayXdPeriod(findPeriod(), PeriodConfig.PERIOD_FORMAT_D_3, CQSSC_CYCLE, size);
	}

	/**
	 * 获取指定日期 重庆时时彩每天的期数
	 *
	 * @param date 指定日期
	 * @return 重庆时时彩每天的期数
	 */
	private int findDayPeriod(Date date) {
		Date end = DateTool.getHm(date, CQSSC_TIME_END);
		Date begin = DateTool.getHm(date, CQSSC_TIME_BEGIN);
		Date start = DateTool.getHm(date, CQSSC_TIME_START);
		Date over = DateTool.getHm(date, CQSSC_TIME_OVER);

		if (date.after(begin) && date.before(end)) {
			return DateTool.getPeriod(begin, date, PERIOD).intValue() + 2;
		} else if (date.after(end) && date.before(start)) {
			return CQSSC_BASE_PERIOD;
		} else if (date.after(start) && date.before(over)) {
			return CQSSC_BASE_PERIOD + DateTool.getPeriod(start, date, PERIOD).intValue() + 1;
		} else {
			return 1;
		}
	}


}
