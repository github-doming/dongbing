package com.common.game.idc;


import com.common.config.PeriodConfig;
import com.common.game.Self102;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * EPS赛马 2分彩
 * @Author: Dongming
 * @Date: 2020-05-13 17:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Eps2 extends Self102<String> {
	private static final String PERIOD_FORMAT_D3 = "%s%03d";
	private static final String EPS2_TIME_START = "12:02";
	private static final String EPS2_TIME_BEGIN = "09:02";
	private static final String EPS2_TIME_END = "04:00";
	private static final int EPS2_PASS_PERIOD = 361;
	private static final int EPS2_BASE_PERIOD = 481;
	private static final int EPS2_CYCLE = 570;
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_2;

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
	@Override public long getDrawTime(Object periodObj) throws ParseException {
		Date day = DateTool.getDay(periodObj.toString().substring(0, 8));
		int number = NumberTool.getInteger(periodObj.toString().substring(8, 11));

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
				periods[size - i - 1] = String.format(PeriodConfig.PERIOD_FORMAT_D3, day, num - i);
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

	@Override
	public String findBeforePeriod(Object period, int index) throws ParseException {
		if (index == 0) {
			return period.toString();
		}
		String time=period.toString().substring(0,8);
		String basePeriod=period.toString().substring(8);
		int day = index / EPS2_CYCLE;
		int mod = index % EPS2_CYCLE;
		if (day != 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateTool.getDay(time));
			calendar.set(Calendar.HOUR_OF_DAY, -day);
			time = DateTool.getDay(calendar.getTime());
		}
		int num = Integer.parseInt(basePeriod);
		if (mod != 0) {
			num = StringTool.minus(basePeriod, mod);
		}
		if (num <= 0) {
			num = num + EPS2_CYCLE;
			time = DateTool.getYesterday(time);

		}
		return String.format(PERIOD_FORMAT_D3, time, num);
	}

	/**
	 * 获取EPS赛马 每一天的期数
	 *
	 * @param date 指定时间
	 * @return 期数
	 */
	private int findDayPeriod(Date date) {
		Date startTime = DateTool.getHm(date, EPS2_TIME_START);
		Date beginTime = DateTool.getHm(date, EPS2_TIME_BEGIN);
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
