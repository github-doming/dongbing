package com.common.game;

import com.common.config.PeriodConfig;
import com.common.game.type.HappyGame;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 广西快乐十分
 *
 * @Author: Dongming
 * @Date: 2020-04-21 18:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Gxklsf extends HappyGame implements Period<String> {

	private static final String GXKLSF_TIME_START = "09:21";
	private static final String GXKLSF_TIME_END = "22:21";

	private static final Long GXKLSF_START_LONG = 1584633600000L;
	private static final int GXKLSF_INDEX = 30;
	private static final int CYCLE = 42;

	private static final String GXKLSF_PERIOD_FORMAT = "%s%03d-%02d";
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_20;


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


	@Override public long getDrawTime(Object periodObj){
		String number = periodObj.toString().split("-")[1];
		int index = NumberTool.getInteger(periodObj.toString().split("-")[0].substring(4));
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
	@Override public String findBeforePeriod(Object period, int index) throws ParseException {
		if (index == 0) {
			return period.toString();
		}
		String[] strs = period.toString().split("-");
		int day = index / CYCLE;
		int mod = index % CYCLE;

		if (day != 0) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateTool.getDay(strs[0]));
			calendar.set(Calendar.HOUR_OF_DAY, -day);
			strs[0] = DateTool.getDay(calendar.getTime());
		}
		int num = Integer.parseInt(strs[1]);
		if (mod != 0) {
			num = StringTool.minus(strs[1], mod);
		}
		if (num <= 0) {
			num = num + 42;
			strs[0] = DateTool.getYesterday(strs[0]);

		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D_3, strs[0], num);
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

	@Override
	public String getDrawTableName() {
		return "rep_draw_gxklsf";
	}
}
