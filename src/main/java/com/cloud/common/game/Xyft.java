package com.cloud.common.game;

import org.doming.core.tools.DateTool;

import java.text.ParseException;
import java.util.Date;

/**
 * 幸运飞艇
 * @Author: Dongming
 * @Date: 2020-04-21 16:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Xyft extends NumberGame implements PeriodOperation<String> {
	private static final String XYFT_TIME_START = "13:09";
	private static final String XYFT_TIME_END = "04:04";
	private static final int XYFT_BASE_PERIOD = 132;

	private static final int XYFT_CYCLE = 180;
	public static final long PERIOD =PeriodConfig. PERIOD_TIME_5;
	@Override public long period() {
		return PERIOD;
	}
	@Override
	public String findLotteryPeriod() {
		Date date = new Date();
		int period = findXyftDayPeriod(date) - 1;
		if (period < 1) {
			period = 180;
		}
		String day = DateTool.getDay(date);
		Date startTime = DateTool.getHm(date, XYFT_TIME_START);
		if (date.before(startTime)) {
			day = DateTool.getYesterday(date);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D_3, day, period);
	}


	@Override
	public String findPeriod() {
		Date nowTime = new Date();
		int period = findXyftDayPeriod(nowTime);
		String day = DateTool.getDay(nowTime);
		Date endTime = DateTool.getHm(nowTime, XYFT_TIME_END);
		if (nowTime.before(endTime)) {
			day = DateTool.getYesterday(nowTime);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D_3, day, period);
	}

	@Override
	public long getDrawTime(String period) throws ParseException {
		String day = period.split("-")[0];
		String number = period.split("-")[1];
		return DateTool.getDateByStr(day + " " + XYFT_TIME_START).getTime()
				+ (Integer.parseInt(number) - 1) * PERIOD;
	}

	@Override
	public String[] listPeriod(int size) throws ParseException {
		return listDayXdPeriod(findPeriod(), PeriodConfig.PERIOD_FORMAT_D_3, XYFT_CYCLE, size);
	}


	/**
	 * 获取指定日期 幸运飞艇每天的期数
	 *
	 * @param date 指定日期
	 * @return 幸运飞艇每天的期数
	 */
	private int findXyftDayPeriod(Date date) {
		Date startTime = DateTool.getHm(date, XYFT_TIME_START);
		Date endTime = DateTool.getHm(date, XYFT_TIME_END);
		Date dayStartTime = new Date(DateTool.getDayStart(date).getTime() - DateTool.getMillisecondsMinute());
		int period = 1;
		if (!date.before(startTime)) {
			period = DateTool.getPeriod(startTime, date, PERIOD).intValue() + 2;
		}
		if (date.before(endTime)) {
			period = XYFT_BASE_PERIOD + DateTool.getPeriod(dayStartTime, date, PERIOD).intValue();
		}
		return period;
	}


}
