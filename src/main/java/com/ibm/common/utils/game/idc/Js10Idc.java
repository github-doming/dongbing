package com.ibm.common.utils.game.idc;

import com.ibm.common.utils.game.PeriodConfig;
import com.ibm.common.utils.game.base.PeriodOperation;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.util.Date;

/**
 * IDC急速赛车
 *
 * @Author: Dongming
 * @Date: 2020-04-21 17:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Js10Idc implements PeriodOperation<Integer> {

	private static final long START_LONG = 1571182230000L;
	private static final int BASE_PERIOD = 31244985;

	public static final long PERIOD = PeriodConfig.PERIOD_TIME_1;

	private static final String START_TIME = "07:30:30";
	private static final String END_TIME = "04:00:30";

	//region 初始化区域

	private static final Js10Idc JS10IDC = new Js10Idc();
	private Js10Idc() {
	}
	public static Js10Idc js10idc() {
		return JS10IDC;
	}
	//endregion

	@Override public Integer findLotteryPeriod() {
		return findPeriod(new Date()) - 1;
	}

	@Override public Integer findPeriod() {
		return findPeriod(new Date());
	}

	@Override public long getDrawTime(Object period){
		int periodInt = NumberTool.getInteger(period);
		return (periodInt - BASE_PERIOD) * PERIOD + START_LONG;
	}

	@Override public Integer[] listPeriod(int size) {
		int period = findPeriod();
		//TODO 优化期数区间不获取封盘时间的期数 - 懒得做
		Integer[] periods = new Integer[size];
		for (int i = 0; i < size; i++) {
			periods[size - i - 1] = period - i;
		}
		return periods;
	}

	public Date getStartTime() {
		return DateTool.getTime(START_TIME);
	}

	public Date getEndTime() {
		return DateTool.getTime(END_TIME);
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
}
