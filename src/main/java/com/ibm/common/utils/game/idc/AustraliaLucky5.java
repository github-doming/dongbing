package com.ibm.common.utils.game.idc;
import com.ibm.common.utils.game.PeriodConfig;
import com.ibm.common.utils.game.base.PeriodOperation;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;

import java.util.Date;
/**
 * 澳洲幸运5
 *
 * @Author: Dongming
 * @Date: 2020-04-30 11:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AustraliaLucky5 implements PeriodOperation<Integer> {
	private static final long START_LONG = 1585697640000L;
	private static final int BASE_PERIOD = 50671652;
	public static final long PERIOD = PeriodConfig.PERIOD_TIME_5;

	private static final String START_TIME = "07:34";
	private static final String END_TIME = "03:59";

	//region 初始化区域

	private static final AustraliaLucky5 AUSTRALIA_LUCKY_5 = new AustraliaLucky5();
	private AustraliaLucky5() {
	}
	public static AustraliaLucky5 australiaLucky5() {
		return AUSTRALIA_LUCKY_5;
	}
	//endregion

	@Override public Integer findPeriod() {
		return findPeriod(new Date());
	}
	@Override public Integer findLotteryPeriod() {
		return findPeriod(new Date()) - 1;
	}
	@Override public long getDrawTime(Object period) {
		int periodInt = NumberTool.getInteger(period);
		return (periodInt - BASE_PERIOD) * PERIOD + START_LONG;
	}
	@Override public Integer[] listPeriod(int size) {
		int period = findPeriod();
		Integer[] periods = new Integer[size];
		//TODO 优化期数区间不获取封盘时间的期数 - 懒得做
		for (int i = 0; i < size; i++) {
			periods[size - i - 1] = period - i;
		}
		return periods;
	}

	public Date getStartTime(){
		return DateTool.getHm(START_TIME);
	}

	public Date getEndTime(){
		return DateTool.getHm(END_TIME);
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
		return DateTool.getPeriod(date.getTime(), START_LONG, PERIOD).intValue() + BASE_PERIOD + 1;
	}
}
