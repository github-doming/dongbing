package com.ibm.common.utils.game.idc;
import com.ibm.common.utils.game.PeriodConfig;
import com.ibm.common.utils.game.base.PeriodOperation;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.text.ParseException;
import java.util.Date;
/**
 * 超级快5
 *
 * @Author: Dongming
 * @Date: 2020-04-29 18:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SuperFast5 implements PeriodOperation<String> {

	private static final String TIME_START = "12:04";
	private static final String TIME_BEGIN = "09:04";
	private static final String TIME_END = "03:59";

	private static final int PASS_PERIOD = 145;
	private static final int BASE_PERIOD = 193;
	private static final int CYCLE = 228;

	public static final long PERIOD = PeriodConfig.PERIOD_TIME_5;

	//region 初始化区域

	private static final SuperFast5 SUPER_FAST_5 = new SuperFast5();
	private SuperFast5() {
	}
	public static SuperFast5 superFast5() {
		return SUPER_FAST_5;
	}
	//endregion

	@Override public String findPeriod() {
		Date date = new Date();
		int period = findDayPeriod(date);
		if (period > CYCLE) {
			period = 1;
		}
		String day;
		Date begin = DateTool.getHm(date, "11:59");
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
			period = CYCLE;
		}
		String day;
		Date begin = DateTool.getHm(date, TIME_START);
		if (date.before(begin)) {
			day = DateTool.getYesterday(date);
		} else {
			day = DateTool.getDay(date);
		}
		return String.format(PeriodConfig.PERIOD_FORMAT_D3, day, period);
	}

	@Override public long getDrawTime(Object period) throws ParseException {
		int number = NumberTool.getInteger(period.toString().substring(8, 11));
		if (number < BASE_PERIOD) {
			return (number - 1) * PERIOD + DateTool.getDateByStr(period.toString().substring(0, 8).concat(" ").concat(TIME_START))
					.getTime();
		} else {
			return (number - BASE_PERIOD) * PERIOD + DateTool
					.getDateByStr(period.toString().substring(0, 8).concat(" ").concat(TIME_BEGIN)).getTime() + DateTool
					.getMillisecondsDay();
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
				periods[size - i - 1] = String.format(PeriodConfig.PERIOD_FORMAT_D3, day, CYCLE - i + num);
			}
		}
		return periods;
	}

	/**
	 * 获取指定日期 幸运农场每天的期数
	 *
	 * @param date 指定日期
	 * @return 幸运农场每天的期数
	 */
	private int findDayPeriod(Date date) {
		Date startTime = DateTool.getHm(date, TIME_START);
		Date beginTime = DateTool.getHm(date, TIME_BEGIN);
		Date endTime = DateTool.getHm(date, TIME_END);
		Date dayStart = DateTool.getDayStart();
		int period;
		if (date.before(endTime)) {
			period = DateTool.getPeriod(dayStart, date, PERIOD).intValue() + PASS_PERIOD;
		} else if (date.after(startTime)) {
			period = DateTool.getPeriod(startTime, date, PERIOD).intValue() + 2;
		} else if (date.after(beginTime)) {
			period = BASE_PERIOD + DateTool.getPeriod(beginTime, date, PERIOD).intValue() + 1;
		} else {
			period = BASE_PERIOD;
		}
		return period;
	}
}
