package com.common.game.hq;

import com.common.game.Js10;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 英国急速赛车
 * @Author: Dongming
 * @Date: 2020-05-13 16:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BritishSpeedRacing extends Js10<String> {
	private static final String PERIOD_FORMAT_D4 = "%s%04d";

	private static final int CYCLE = 1251;
	private static final String JS_DATA_START = "00:01:05";

	@Override public String findPeriod() {
		Date date = new Date();
		long period = findPeriod(date) + 1;
		return String.format(PERIOD_FORMAT_D4, DateTool.getDay(date), period);

	}
	@Override public String findLotteryPeriod() {
		Date date = new Date();
		int period = findPeriod(date);
		return String.format(PERIOD_FORMAT_D4, DateTool.getDay(date), period);

	}
	@Override public long getDrawTime(Object periodObj) {
		return (Integer.parseInt(periodObj.toString().substring(8)) - 1) * PERIOD + DateTool.getTime(JS_DATA_START).getTime();
	}
	@Override public String[] listPeriod(int size) throws ParseException {
		String[] periods = new String[size];
		String period = findPeriod();
		String day = period.substring(0, 8);
		int num = Integer.parseInt(period.substring(8));
		if (num > size) {
			for (int i = 1; i <= size; i++) {
				periods[size - i] = String.format(PERIOD_FORMAT_D4, day, num - i);
			}
		} else {
			for (int i = 1; i < num; i++) {
				periods[size - i] = String.format(PERIOD_FORMAT_D4, day, num - i);
			}
			day = DateTool.getYesterday(day);
			for (int i = num; i <= size; i++) {
				periods[size - i] = String.format(PERIOD_FORMAT_D4, day, CYCLE - i + num);
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
		int day = index / CYCLE;
		int mod = index % CYCLE;

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
			num = num + CYCLE;
			time = DateTool.getYesterday(time);
		}
		return String.format(PERIOD_FORMAT_D4, time, num);
	}

	/**
	 * 根据时间获取期数
	 *
	 * @param date 指定时间
	 * @return 期数
	 */
	private int findPeriod(Date date) {
		return DateTool.getPeriod(date, DateTool.getHms(JS_DATA_START), PERIOD).intValue() + 1;
	}
}
