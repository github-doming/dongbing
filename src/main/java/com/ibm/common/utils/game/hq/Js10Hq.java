package com.ibm.common.utils.game.hq;

import com.ibm.common.utils.game.PeriodConfig;
import com.ibm.common.utils.game.base.PeriodOperation;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 英国急速赛车
 *
 * @Author: Dongming
 * @Date: 2020-04-21 19:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Js10Hq implements PeriodOperation<String> {
	private static final String PERIOD_FORMAT_D4 = "%s%04d";

	private static final int JS10_CYCLE = 1251;
	private static final String JS_DATA_START = "00:01:05";
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_1;

	//region 初始化区域

	private static final Js10Hq JS10HQ = new Js10Hq();

	private Js10Hq() {
	}

	public static Js10Hq js10hq() {
		return JS10HQ;
	}
	//endregion

	@Override
	public String findPeriod() {
		Date date = new Date();
		long period = findPeriod(date) + 1;
		return String.format(PERIOD_FORMAT_D4, DateTool.getDay(date), period);
	}

	@Override
	public String findLotteryPeriod() {
		Date date = new Date();
		int period = findPeriod(date);
		return String.format(PERIOD_FORMAT_D4, DateTool.getDay(date), period);
	}


	@Override
	public long getDrawTime(Object period) {
		return (Integer.parseInt(period.toString().substring(8)) - 1) * PERIOD + DateTool.getTime(JS_DATA_START).getTime();
	}

	@Override
	public String[] listPeriod(int size) throws ParseException {
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
				periods[size - i] = String.format(PERIOD_FORMAT_D4, day, JS10_CYCLE - i + num);
			}
		}
		return periods;
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
