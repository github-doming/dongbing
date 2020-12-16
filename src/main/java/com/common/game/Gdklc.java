package com.common.game;

import com.common.config.PeriodConfig;
import com.common.game.type.HappyGame;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 广东快乐十分
 * @Author: Dongming
 * @Date: 2020-05-13 16:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Gdklc extends HappyGame implements Period<String>{
	private static final String PERIOD_FORMAT_D2 = "%s-%02d";

	private static final String GDKLC_TIME_START = "09:20";
	private static final String GDKLC_TIME_END = "23:00";

	private static final int GDKLC_CYCLE = 42;
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_20;


	@Override public String findPeriod() {
		Date date = new Date();
		int period = findDayPeriod(date);
		String day;
		Date endTime = DateTool.getHm(date, GDKLC_TIME_END);
		if (date.after(endTime)) {
			day = DateTool.getTomorrow(date);
		} else {
			day = DateTool.getDay(date);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D_2, day, period);
	}
	@Override public String findLotteryPeriod() {
		Date date = new Date();
		int period = findDayPeriod(date) - 1;
		if (period < 1) {
			period = GDKLC_CYCLE;
		}
		String day;
		Date startTime = DateTool.getHm(date, GDKLC_TIME_START);
		if (startTime.after(date)) {
			day = DateTool.getYesterday(date);
		} else {
			day = DateTool.getDay(date);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D_2, day, period);
	}
	@Override public long getDrawTime(Object periodObj) throws ParseException {
		String number = periodObj.toString().split("-")[1];
		String day =  periodObj.toString().split("-")[0].concat(" ").concat(GDKLC_TIME_START);
		return DateTool.getDateByStr(day).getTime() + (Integer.parseInt(number) - 1) * PERIOD;
	}
	@Override public String[] listPeriod(int size) throws ParseException {
		return listDayXdPeriod(findPeriod(), PeriodConfig.PERIOD_FORMAT_D_2, GDKLC_CYCLE, size);
	}

	@Override
	public String findBeforePeriod(Object period, int index) throws ParseException {
		if (index == 0) {
			return period.toString();
		}
		String[] strs = period.toString().split("-");
		int day = index / GDKLC_CYCLE;
		int mod = index % GDKLC_CYCLE;

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
		return String.format(PERIOD_FORMAT_D2, strs[0], num);
	}
	@Override
	public String getDrawTableName() {
		return "rep_draw_gdklc";
	}

	/**
	 * 获取指定日期 快乐彩每天的期数
	 *
	 * @param date 指定日期
	 * @return 快乐彩每天的期数
	 */
	private int findDayPeriod(Date date) {
		Date startTime = DateTool.getHm(date, GDKLC_TIME_START);
		Date endTime = DateTool.getHm(date, GDKLC_TIME_END);
		int period = 1;
		if (date.after(startTime) && endTime.after(date)) {
			period = DateTool.getPeriod(startTime, date, PERIOD).intValue() + 2;
		}
		return period;
	}
}
