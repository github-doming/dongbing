package com.common.game;

import com.common.config.PeriodConfig;
import com.common.game.type.NumberGame;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 幸运飞艇
 *
 * @Author: Dongming
 * @Date: 2020-05-13 16:25
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Xyft  extends NumberGame implements Period<String>{
	private static final String PERIOD_FORMAT_D3 = "%s-%03d";

	private static final String XYFT_TIME_START = "13:09";
	private static final String XYFT_TIME_END = "04:04";
	private static final int XYFT_BASE_PERIOD = 132;

	private static final int XYFT_CYCLE = 180;
	public static final long PERIOD = PeriodConfig. PERIOD_TIME_5;
	@Override public String findPeriod() {
		Date nowTime = new Date();
		int period = findDayPeriod(nowTime);
		String day = DateTool.getDay(nowTime);
		Date endTime = DateTool.getHm(nowTime, XYFT_TIME_END);
		if (nowTime.before(endTime)) {
			day = DateTool.getYesterday(nowTime);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D_3, day, period);
	}
	@Override public String findLotteryPeriod() {
		Date date = new Date();
		int period = findDayPeriod(date) - 1;
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
	@Override public long getDrawTime(Object periodObj) throws ParseException {
		String day = periodObj.toString().split("-")[0];
		String number = periodObj.toString().split("-")[1];
		return DateTool.getDateByStr(day + " " + XYFT_TIME_START).getTime()
				+ (Integer.parseInt(number) - 1) * PERIOD;
	}
	@Override public String[] listPeriod(int size) throws ParseException {
		return listDayXdPeriod(findPeriod(), PeriodConfig.PERIOD_FORMAT_D_3, XYFT_CYCLE, size);
	}

	@Override
	public String findBeforePeriod(Object period, int index) throws ParseException {
		if (index == 0) {
			return period.toString();
		}
		String[] strs = period.toString().split("-");
		int day = index / XYFT_CYCLE;
		int mod = index % XYFT_CYCLE;

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
			num = num + XYFT_CYCLE;
			strs[0] = DateTool.getYesterday(strs[0]);

		}
		return String.format(PERIOD_FORMAT_D3, strs[0], num);
	}

	/**
	 * 获取指定日期 幸运飞艇每天的期数
	 *
	 * @param date 指定日期
	 * @return 幸运飞艇每天的期数
	 */
	private int findDayPeriod(Date date) {
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
	@Override
	public String getDrawTableName() {
		return "rep_draw_xyft";
	}
}
