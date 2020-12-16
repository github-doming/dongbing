package com.cloud.common.game.hq;

import com.cloud.common.game.BallGame;
import com.cloud.common.game.PeriodConfig;
import com.cloud.common.game.PeriodOperation;
import org.doming.core.tools.DateTool;

import java.util.Date;

/**
 * @Author: Dongming
 * @Date: 2020-04-21 19:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class JssscHq extends BallGame implements PeriodOperation<String> {
	private static final String PERIOD_FORMAT_D4 = "%s%04d";

	private static final String JSSSC_DATA_START = "00:01:05";
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_1;

	@Override public long period() {
		return PERIOD;
	}

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
	public long getDrawTime(String period) {
		return (Integer.parseInt(period.substring(8)) - 1) * PERIOD + DateTool.getTime(JSSSC_DATA_START).getTime();
	}

	@Override
	public String[] listPeriod(int size) {
		return new String[0];
	}

	private int findPeriod(Date date) {
		return DateTool.getPeriod(date, DateTool.getHms(JSSSC_DATA_START), PERIOD).intValue() + 1;
	}
}
