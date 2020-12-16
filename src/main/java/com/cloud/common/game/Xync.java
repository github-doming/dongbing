package com.cloud.common.game;

import org.doming.core.tools.DateTool;

import java.text.ParseException;
import java.util.Date;

/**
 * @Author: Dongming
 * @Date: 2020-04-21 16:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Xync extends HappyGame implements PeriodOperation<String>{
	private static final String XYNC_TIME_BEGIN = "00:01";
	private static final String XYNC_TIME_START = "07:01";
	private static final String XYNC_TIME_END = "03:01";
	private static final String XYNC_TIME_OVER = "23:41";

	private static final int XYNC_CYCLE = 59;
	private static final int XYNC_BASE_PERIOD = 10;


	public static final long PERIOD = PeriodConfig.PERIOD_TIME_20;

	@Override public long period() {
		return PERIOD;
	}

	@Override
	public String findLotteryPeriod() {
		Date date = new Date();
		int period = findDayPeriod(date) - 1;
		String day = DateTool.getDay(date);
		if (period < 1) {
			period = XYNC_CYCLE;
			Date overTime = DateTool.getHm(date, XYNC_TIME_OVER);
			if (date.before(overTime)) {
				day = DateTool.getYesterday(date);
			}
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D_2, day, period);
	}

	@Override
	public String findPeriod() {
		Date date = new Date();
		int period = findDayPeriod(date);
		String day;
		Date overTime = DateTool.getHm(date, XYNC_TIME_OVER);
		if (date.after(overTime)) {
			day = DateTool.getTomorrow(date);
		} else {
			day = DateTool.getDay(date);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D_2, day, period);

	}

	@Override
	public long getDrawTime(String period) throws ParseException {
		String number = period.split("-")[1];
		String day = period.split("-")[0];
		if (Integer.parseInt(number) - XYNC_BASE_PERIOD >= 0) {
			return DateTool.getDateByStr(day + " " + XYNC_TIME_START).getTime()
					+ (Integer.parseInt(number) - XYNC_BASE_PERIOD + 1) * PERIOD;
		} else {
			return DateTool.getDateByStr(day + " " + XYNC_TIME_BEGIN).getTime()
					+ Integer.parseInt(number) * PERIOD;
		}
	}

	@Override
	public String[] listPeriod(int size) throws ParseException {
		return listDayXdPeriod(findPeriod(), PeriodConfig.PERIOD_FORMAT_D_2, XYNC_CYCLE, size);
	}

	/**
	 * 获取指定日期 幸运农场每天的期数
	 *
	 * @param date 指定日期
	 * @return 幸运农场每天的期数
	 */
	private int findDayPeriod(Date date) {
		Date startTime = DateTool.getHm(date, XYNC_TIME_START);
		Date beginTime = DateTool.getHm(date, XYNC_TIME_BEGIN);
		Date endTime = DateTool.getHm(date, XYNC_TIME_END);
		Date overTime = DateTool.getHm(date, XYNC_TIME_OVER);

		if (date.after(beginTime) && date.before(endTime)) {
			return DateTool.getPeriod(beginTime, date, PERIOD).intValue() + 1;
		} else if (date.after(endTime) && date.before(startTime)) {
			return XYNC_BASE_PERIOD;
		} else if (date.after(startTime) && date.before(overTime)) {
			return XYNC_BASE_PERIOD + DateTool.getPeriod(startTime, date, PERIOD).intValue();
		} else {
			return 1;
		}
	}



}
