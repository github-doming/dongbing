package com.common.game.idc;

import com.common.game.Js10;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.text.ParseException;
import java.util.Date;

/**
 * 急速赛车
 *
 * @Author: Dongming
 * @Date: 2020-05-13 16:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SpeedRacing extends Js10<Integer> {
	private static final long START_LONG = 1571182230000L;
	private static final int BASE_PERIOD = 31244985;


	private static final String START_TIME = "07:30:30";
	private static final String END_TIME = "04:00:30";


	@Override public Integer findPeriod() {
		return findPeriod(new Date());
	}
	@Override public Integer findLotteryPeriod() {
		return findPeriod(new Date()) - 1;
	}
	@Override public long getDrawTime(Object periodObj) throws ParseException {
		return (NumberTool.getInteger(periodObj) - BASE_PERIOD) * PERIOD + START_LONG;
	}
	@Override public Integer[] listPeriod(int size) throws ParseException {
		int period = findPeriod();
		//TODO 优化期数区间不获取封盘时间的期数 - 懒得做
		Integer[] periods = new Integer[size];
		for (int i = 0; i < size; i++) {
			periods[size - i - 1] = period - i;
		}
		return periods;
	}

	@Override
	public Integer findBeforePeriod(Object period, int followPeriod)  {
		if(NumberTool.getInteger(period)==0){
			return 0;
		}
		return NumberTool.getInteger(period)- followPeriod;
	}

	/**
	 * 根据时间获取期数
	 *
	 * @param date 指定时间
	 * @return 期数
	 */
	private Integer findPeriod(Date date) {
		Date startTime = getStartTime();
		Date endTime = getEndTime();
		if (date.before(startTime) && date.after(endTime)) {
			return DateTool.getPeriod(endTime.getTime(), START_LONG, PERIOD).intValue() + 1 + BASE_PERIOD;
		}
		return DateTool.getPeriod(date.getTime(), START_LONG, PERIOD).intValue() + 1 + BASE_PERIOD;
	}

	public Date getStartTime() {
		return DateTool.getTime(START_TIME);
	}

	public Date getEndTime() {
		return DateTool.getTime(END_TIME);
	}

}
