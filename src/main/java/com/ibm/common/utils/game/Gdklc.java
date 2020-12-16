package com.ibm.common.utils.game;

import com.ibm.common.utils.game.base.PeriodOperation;
import org.doming.core.tools.DateTool;

import java.text.ParseException;
import java.util.Date;

/**
 * 广东快乐十分
 *
 * @Author: Dongming
 * @Date: 2020-04-21 16:52
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Gdklc implements PeriodOperation<String> {
	private static final String GDKLC_TIME_START = "09:20";
	private static final String GDKLC_TIME_END = "23:00";

	private static final int GDKLC_CYCLE = 42;
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_20;

	//region 初始化区域

	private static final Gdklc GDKLC = new Gdklc();
	private Gdklc() {
	}
	public static Gdklc gdklc() {
		return GDKLC;
	}
	//endregion

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

	@Override public long getDrawTime(Object period) throws ParseException {
		String number = period.toString().split("-")[1];
		String day = period.toString().split("-")[0].concat(" ").concat(GDKLC_TIME_START);
		return DateTool.getDateByStr(day).getTime() + (Integer.parseInt(number) - 1) * PERIOD;
	}

	@Override public String[] listPeriod(int size) throws ParseException {
		return listDayXdPeriod(findPeriod(), PeriodConfig.PERIOD_FORMAT_D_2, GDKLC_CYCLE, size);
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
