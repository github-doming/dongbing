package com.ibm.old.v1.common.doming.tools;

import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @Description: 期数工具类
 * @Author: Dongming
 * @Date: 2019-01-19 10:05
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class PeriodTool {

	//TODO PK10
	//PK10区域

	private static final int PK10_CYCLE = 44;
	private static final int PK10_BASE_PERIOD = 729392;

	private static final String PK10_TIME_START = "09:30";
	private static final Date PK10_DATA_START = new Date(1549848600000L);

	public static final long PK10_PERIOD = 20 * 60 * 1000L;

	public static final long BET_TIME = 15 * 1000L;

	public static Integer findLotteryPK10Period() {
		return findPK10Period(new Date()) - 1;
	}

	/**
	 * 获取下一次开奖的期数
	 *
	 * @return 下一次开奖的期数
	 */
	public static Integer findPK10Period() {
		return findPK10Period(new Date());
	}

	/**
	 * 查找历史期数
	 *
	 * @param period 指定期数
	 * @param number 查找数量
	 * @return 历史期数    Integer[]
	 */
	public static Integer[] findHistoryPK10Period(Integer period, int number) {
		Integer[] periods = new Integer[number];
		for (int i = 0; i < number; i++) {
			periods[number - i - 1] = period - i;
		}
		return periods;
	}

	/**
	 * 查找期数段内的所有期数
	 *
	 * @param startPeriod 开始期数
	 * @param endPeriod   结束期数
	 * @return 期数段内的所有期数
	 */
	public static Integer[] findPK10Periods(Integer startPeriod, Integer endPeriod) {
		int len = endPeriod - startPeriod;
		Integer[] periods = new Integer[len + 1];
		periods[0] = startPeriod;
		for (int i = 1; i <= len; i++) {
			periods[i] = startPeriod + i;
		}
		return periods;
	}

	/**
	 * 获取下一次开奖的期数
	 *
	 * @param date 时间
	 * @return 下一次开奖的期数
	 */
	public static int findPK10Period(Date date) {
		Date startTime = DateTool.getHm(date, PK10_TIME_START);
		int dayDifference = DateTool.getDaysBetween(PK10_DATA_START, startTime).intValue();
		int todayPeriod = 0;
		if (startTime.before(date)) {
			todayPeriod = (int) (DateTool.getPeriod(startTime, date, PK10_PERIOD) + 1);
		}
		return PK10_BASE_PERIOD + dayDifference * PK10_CYCLE + todayPeriod;
	}

	/**
	 * 获取PK10已开奖的开奖时间戳
	 *
	 * @return 已开奖的开奖时间戳
	 */
	public static long getLotteryPK10DrawTime() {
		return getPK10DrawTime(findLotteryPK10Period());
	}

	/**
	 * 获取PK10下一期的开奖时间戳
	 *
	 * @return 下一期的开奖时间戳
	 */
	public static long getPK10DrawTime() {
		return getPK10DrawTime(findPK10Period());
	}

	/**
	 * 获取PK10的开奖时间戳
	 *
	 * @return PK10的开奖时间戳
	 */
	public static long getPK10DrawTime(Integer period) {
		if (period < PK10_BASE_PERIOD) {
			throw new RuntimeException("不能查找基础期数之前的期数");
		}
		int day = (period - PK10_BASE_PERIOD) / PK10_CYCLE;
		int dayPeriod = (period - PK10_BASE_PERIOD) % PK10_CYCLE;
		return PK10_DATA_START.getTime() + DateTool.getMillisecondsDays(day) + dayPeriod * PK10_PERIOD;
	}

	//=======================================PK10区域===================================================================

	//TODO XYFT
	//XYFT区域

	private static final String XYFT_TIME_START = "13:09";
	private static final String XYFT_TIME_END = "04:04";
	private static final int XYFT_BASE_PERIOD = 132;

	private static final String XYFT_PERIOD_FORMAT = "%s-%03d";

	public static final long XYFT_PERIOD = 5 * 60 * 1000L;

	/**
	 * 获取 幸运飞艇的已开奖期数
	 *
	 * @return 幸运飞艇的期数
	 */
	public static String findLotteryXYFTPeriod() {
		Date date = new Date();
		int period = findXYFTDayPeriod(date) - 1;
		if (period < 1) {
			period = 180;
		}
		String day = DateTool.getDay(date);
		Date startTime = DateTool.getHm(date, XYFT_TIME_START);
		if (date.before(startTime)) {
			day = DateTool.getYesterday(date);
		}
		return String.format(XYFT_PERIOD_FORMAT, day, period);

	}

	/**
	 * 获取幸运飞艇的期数
	 *
	 * @return 幸运飞艇的期数
	 */
	public static String findXYFTPeriod() {
		return findXYFTPeriod(new Date());
	}

	/**
	 * 获取指定日期 幸运飞艇的期数
	 *
	 * @param date 指定日期
	 * @return 幸运飞艇的期数
	 */
	public static String findXYFTPeriod(Date date) {
		int period = findXYFTDayPeriod(date);
		String day = DateTool.getDay(date);
		Date endTime = DateTool.getHm(date, XYFT_TIME_END);
		if (date.before(endTime)) {
			day = DateTool.getYesterday(date);
		}
		return String.format(XYFT_PERIOD_FORMAT, day, period);
	}
	/**
	 * 获取指定日期 幸运飞艇每天的期数
	 *
	 * @param date 指定日期
	 * @return 幸运飞艇每天的期数
	 */
	private static int findXYFTDayPeriod(Date date) {
		Date startTime = DateTool.getHm(date, XYFT_TIME_START);
		Date endTime = DateTool.getHm(date, XYFT_TIME_END);
		Date dayStartTime = new Date(DateTool.getDayStart(date).getTime() - DateTool.getMillisecondsMinute());
		int period = 1;
		if (!date.before(startTime)) {
			period = (int) (DateTool.getPeriod(startTime, date, XYFT_PERIOD) + 2);
		}
		if (date.before(endTime)) {
			period = (int) (XYFT_BASE_PERIOD +DateTool.getPeriod(dayStartTime, date, XYFT_PERIOD));
		}
		return period;

	}

	/**
	 * 查找历史期数
	 *
	 * @param period 当前期数
	 * @param index  历史长度
	 * @return 历史期数
	 */
	public static String findXYFTBeforePeriod(String period, int index) throws ParseException {
		if (index == 0) {
			return period;
		}
		String[] strs = period.split("-");
		int day = index / 180;
		int mod = index % 180;

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
			num = num + 180;
			strs[0] = DateTool.getYesterday(strs[0]);

		}
		return String.format(XYFT_PERIOD_FORMAT, strs[0], num);
	}

	/**
	 * 查找历史期数
	 *
	 * @param period 指定期数
	 * @param number 查找数量
	 * @return 历史期数    Integer[]
	 */
	public static String[] findHistoryXYFTPeriod(String period, int number) throws ParseException {
		String[] periods = new String[number];
		String[] strs = period.split("-");
		int num = Integer.parseInt(strs[1]);
		if (num > number) {
			for (int i = 1; i <= number; i++) {
				periods[number - i] = String.format(XYFT_PERIOD_FORMAT, strs[0], num - i);
			}
		} else {
			for (int i = 1; i < num; i++) {
				periods[number - i] = String.format(XYFT_PERIOD_FORMAT, strs[0], num - i);
			}
			strs[0] = DateTool.getYesterday(strs[0]);
			for (int i = num; i <= number; i++) {
				periods[number - i] = String.format(XYFT_PERIOD_FORMAT, strs[0], 180 - i + num);
			}
		}
		return periods;
	}
	/**
	 * 查找期数段内的所有期数
	 *
	 * @param startPeriod 开始期数
	 * @param endPeriod   结束期数
	 * @return 期数段内的所有期数 String[]
	 */
	public static String[] findXYFTPeriods(String startPeriod, String endPeriod) throws ParseException {
		String[] startStrs = startPeriod.split("-");
		String[] endStrs = endPeriod.split("-");
		int startNum = Integer.parseInt(startStrs[1]);
		int endNum = Integer.parseInt(endStrs[1]);
		if (startStrs[0].equals(endStrs[0])) {
			int len = endNum - startNum;
			String[] periods = new String[len + 1];
			for (int i = 0; i <= len; i++) {
				periods[len - i] = String.format(XYFT_PERIOD_FORMAT, startStrs[0], endNum - i);
			}
			return periods;
		}
		Date startDate = DateTool.getDay(startStrs[0]);
		Date endDate = DateTool.getDay(endStrs[0]);
		int day = (int) Math.floor(DateTool.getDaysBetween(startDate, endDate));
		int len = 180 * day + endNum - startNum;

		String[] periods = new String[len + 1];
		for (int i = 0; i < endNum; i++) {
			periods[len - i] = String.format(XYFT_PERIOD_FORMAT, endStrs[0], endNum - i);
		}
		for (int i = 1; i < day; i++) {
			String dayStr = DateTool.getBeforeDay(endDate, i);
			for (int j = 1; j <= 180; j++) {
				periods[len - endNum - 180 * i + j] = String.format(XYFT_PERIOD_FORMAT, dayStr, j);
			}
		}
		for (int i = startNum; i <= 180; i++) {
			periods[i - startNum] = String.format(XYFT_PERIOD_FORMAT, startStrs[0], i);
		}
		return periods;
	}

	/**
	 * 获取下一期的开奖时间戳
	 *
	 * @return 下一期的开奖时间戳
	 */
	public static long getXYFTDrawTime() throws ParseException {
		String period = findXYFTPeriod();
		return getXYFTDrawTime(period);
	}

	/**
	 * 获取已开奖的时间戳
	 *
	 * @return 已开奖的时间戳
	 */
	public static long getLotteryXYFTDrawTime() throws ParseException {
		String period = findLotteryXYFTPeriod();
		return getXYFTDrawTime(period);
	}

	/**
	 * 获取开奖时间戳
	 *
	 * @param period 期数
	 * @return 开奖时间戳
	 */
	public static long getXYFTDrawTime(String period) throws ParseException {
		String day = period.split("-")[0];
		String number = period.split("-")[1];
		return DateTool.getStartTimeByTimeAndHm(DateTool.getDay(day), XYFT_TIME_START).getTime()
				+ (Integer.parseInt(number) - 1) * XYFT_PERIOD;
	}

	//=======================================XYFT区域===================================================================

	//TODO CQSSC
	//CQSSC 区域

	private static final String CQSSC_TIME_BEGIN = "00:30";
	private static final String CQSSC_TIME_END = "03:10";
	private static final String CQSSC_TIME_START = "07:30";
	private static final String CQSSC_TIME_OVER = "23:50";

	private static final String CQSSC_BEGIN_TIME = "00:30:00";
	private static final String CQSSC_START_TIME = "07:30:00";

	private static final int CQSSC_BASE_PERIOD = 10;

	private static final String CQSSC_PERIOD_FORMAT = "%s-%03d";

	public static final long CQSSC_PERIOD = 20 * 60 * 1000L;

	/**
	 * 获取 重庆时时彩的已开奖期数
	 *
	 * @return 重庆时时彩的已开奖期数
	 */
	public static String findLotteryCQSSCPeriod() {
		Date date = new Date();
		int period = findCQSSCDayPeriod(date) - 1;
		if (period < 1) {
			period = 59;
		}
		String day = DateTool.getDay(date);
		Date begin = DateTool.getHm(date, CQSSC_TIME_BEGIN);
		if (date.before(begin)) {
			day = DateTool.getYesterday(date);
		}
		return String.format(CQSSC_PERIOD_FORMAT, day, period);
	}

	/**
	 * 获取重庆时时彩的期数
	 *
	 * @return 重庆时时彩的期数
	 */
	public static String findCQSSCPeriod() {
		return findCQSSCPeriod(new Date());
	}

	/**
	 * 获取 指定日期 重庆时时彩的期数
	 *
	 * @param date 指定日期
	 * @return 重庆时时彩的期数
	 */
	public static String findCQSSCPeriod(Date date) {
		int period = findCQSSCDayPeriod(date);
		String day = DateTool.getDay(date);
		Date over = DateTool.getHm(date, CQSSC_TIME_OVER);
		if (date.after(over)) {
			day = DateTool.getTomorrow(date);
		}
		return String.format(CQSSC_PERIOD_FORMAT, day, period);
	}

	/**
	 * 获取已开奖的时间戳
	 *
	 * @return 已开奖的时间戳
	 */
	public static long getLotteryCQSSCDrawTime() {
		String period = findLotteryCQSSCPeriod();
		return getCQSSCDrawTime(period);
	}

	/**
	 * 获取下一期的开奖时间戳
	 *
	 * @return 下一期的开奖时间戳
	 */
	public static long getCQSSCDrawTime() {
		String period = findCQSSCPeriod();
		return getCQSSCDrawTime(period);
	}

	/**
	 * 获取开奖时间戳
	 *
	 * @param period 期数
	 * @return 开奖时间戳
	 */
	public static long getCQSSCDrawTime(String period) {
		String number = period.split("-")[1];
		if (Integer.parseInt(number) < 10) {
			return (Objects.requireNonNull(DateTool.getTime(CQSSC_BEGIN_TIME)).getTime()
					+ (Integer.parseInt(number) - 1) * CQSSC_PERIOD);
		} else {
			return (Objects.requireNonNull(DateTool.getTime(CQSSC_START_TIME)).getTime()
					+ (Integer.parseInt(number) - CQSSC_BASE_PERIOD) * CQSSC_PERIOD);
		}
	}

	/**
	 * 获取指定日期 重庆时时彩每天的期数
	 * e
	 *
	 * @param date 指定日期
	 * @return 重庆时时彩每天的期数
	 */
	private static int findCQSSCDayPeriod(Date date) {
		Date end = DateTool.getHm(date, CQSSC_TIME_END);
		Date begin = DateTool.getHm(date, CQSSC_TIME_BEGIN);
		Date start = DateTool.getHm(date, CQSSC_TIME_START);
		Date over = DateTool.getHm(date, CQSSC_TIME_OVER);

		if (date.after(begin) && date.before(end)) {
			return (int) (DateTool.getPeriod(begin, date, CQSSC_PERIOD) + 2);
		} else if (date.after(end) && date.before(start)) {
			return CQSSC_BASE_PERIOD;
		} else if (date.after(start) && date.before(over)) {
			return (int) (CQSSC_BASE_PERIOD + DateTool.getPeriod(start, date, CQSSC_PERIOD) + 1);
		} else {
			return 1;
		}
	}

	/**
	 * 查找历史期数
	 *
	 * @param period 当前期数
	 * @param index  历史长度
	 * @return 历史期数
	 */
	public static String findCQSSCBeforePeriod(String period, int index) throws ParseException {
		if (index == 0) {
			return period;
		}
		String[] strs = period.split("-");
		int day = index / 59;
		int mod = index % 59;

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
			num = num + 59;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(DateTool.getDay(strs[0]));
			calendar.set(Calendar.HOUR_OF_DAY, -1);
			strs[0] = DateTool.getDay(calendar.getTime());
		}
		return String.format(CQSSC_PERIOD_FORMAT, strs[0], num);
	}

	//=======================================CQSSC 区域===================================================================

	// TODO 广东快乐十分
	// KLC 区域

	private static final String KLC_TIME_START = "09:00";
	private static final String KLC_TIME_END = "23:00";

	private static final String KLC_START_TIME = "09:00:00";

	private static final String KLC_PERIOD_FORMAT = "%s-%02d";

	public static final long KLC_PERIOD = 20 * 60 * 1000L;

	/**
	 * 获取快乐彩的期数
	 *
	 * @return 快乐彩的期数
	 */
	public static String findKLCPeriod() {
		return findKLCPeriod(new Date());
	}

	/**
	 * 获取下一期的开奖时间戳
	 *
	 * @return 下一期的开奖时间戳
	 */
	public static long getKLCDrawTime() {
		String period = findKLCPeriod();
		String number = period.split("-")[1];
		return (Objects.requireNonNull(DateTool.getTime(KLC_START_TIME)).getTime()
				+ (Integer.parseInt(number)) * KLC_PERIOD);
	}

	/**
	 * 获取指定日期 快乐彩的期数
	 *
	 * @param date 指定日期
	 * @return 快乐彩的期数
	 */
	public static String findKLCPeriod(Date date) {
		int period = findKLCDayPeriod(date);
		String day = DateTool.getDay(date);
		Date endTime = DateTool.getHm(date, KLC_TIME_END);
		if (endTime.before(date)) {
			day = DateTool.getTomorrow(date);
		}
		return String.format(KLC_PERIOD_FORMAT, day, period);
	}

	/**
	 * 获取指定日期 快乐彩每天的期数
	 *
	 * @param date 指定日期
	 * @return 快乐彩每天的期数
	 */
	private static int findKLCDayPeriod(Date date) {
		Date startTime = DateTool.getHm(date, KLC_TIME_START);
		Date endTime = DateTool.getHm(date, KLC_TIME_END);
		int period = 1;
		if (date.after(startTime) && endTime.after(date)) {
			period = (int) (DateTool.getPeriod(startTime, date, KLC_PERIOD) + 1);
		}

		return period;
	}

	/**
	 * 获取 快乐彩的已开奖期数
	 *
	 * @return 快乐彩的期数
	 */
	public static String findLotteryKLCPeriod() {
		Date date = new Date();
		int period = findKLCDayPeriod(date) - 1;
		if (period < 1) {
			period = 42;
		}
		String day = DateTool.getDay(date);
		Date startTime = DateTool.getHm(date, KLC_TIME_START);
		if (date.before(startTime)) {
			day = DateTool.getYesterday(date);
		}
		return String.format(KLC_PERIOD_FORMAT, day, period);

	}

	//=======================================KLC 区域===================================================================

	// TODO 幸运农场
	// XYNC 区域

	private static final String XYNC_TIME_BEGIN = "00:01";
	private static final String XYNC_TIME_START = "07:01";
	private static final String XYNC_TIME_END = "03:01";
	private static final String XYNC_TIME_OVER = "23:41";

	private static final String XYNC_NIGHT_TIME = "00:01:00";
	private static final String XYNC_TIME = "07:01:00";

	private static final int XYNC_BASE_PERIOD = 10;
	private static final String XYNC_PERIOD_FORMAT = "%s-%02d";

	public static final long XYNC_PERIOD = 20 * 60 * 1000L;

	/**
	 * 获取幸运农场的期数
	 *
	 * @return 幸运农场的期数
	 */
	public static String findXYNCPeriod() {
		return findXYNCPeriod(new Date());
	}

	/**
	 * 获取下一期的开奖时间戳
	 *
	 * @return 下一期的开奖时间戳
	 */
	public static long getXYNCDrawTime() {
		String period = findXYNCPeriod();
		String number = period.split("-")[1];
		if (Integer.parseInt(number) - XYNC_BASE_PERIOD >= 0) {
			return (Objects.requireNonNull(DateTool.getTime(XYNC_TIME)).getTime()
					+ (Integer.parseInt(number) - XYNC_BASE_PERIOD + 1) * XYNC_PERIOD);
		} else {
			return (Objects.requireNonNull(DateTool.getTime(XYNC_NIGHT_TIME)).getTime()
					+ (Integer.parseInt(number)) * XYNC_PERIOD);
		}
	}

	/**
	 * 获取指定日期 幸运农场的期数
	 *
	 * @param date 指定日期
	 * @return 幸运农场的期数
	 */
	public static String findXYNCPeriod(Date date) {
		int period = findXYNCDayPeriod(date);
		String day = DateTool.getDay(date);
		Date overTime = DateTool.getHm(date, XYNC_TIME_OVER);
		Date beginTime = DateTool.getHm(date, XYNC_TIME_BEGIN);
		if (date.before(beginTime) || date.after(overTime)) {
			day = DateTool.getTomorrow(date);
		}
		return String.format(XYNC_PERIOD_FORMAT, day, period);
	}

	/**
	 * 获取指定日期 幸运农场每天的期数
	 *
	 * @param date 指定日期
	 * @return 幸运农场每天的期数
	 */
	private static int findXYNCDayPeriod(Date date) {
		Date startTime = DateTool.getHm(date, XYNC_TIME_START);
		Date beginTime = DateTool.getHm(date, XYNC_TIME_BEGIN);
		Date endTime = DateTool.getHm(date, XYNC_TIME_END);
		Date overTime = DateTool.getHm(date, XYNC_TIME_OVER);

		if (date.after(beginTime) && date.before(endTime)) {
			return (int) (DateTool.getPeriod(beginTime, date, XYNC_PERIOD) + 1);
		} else if (date.after(endTime) && date.before(startTime)) {
			return XYNC_BASE_PERIOD;
		} else if (date.after(startTime) && date.before(overTime)) {
			return (int) (XYNC_BASE_PERIOD + DateTool.getPeriod(startTime, date, XYNC_PERIOD));
		} else {
			return 1;
		}

	}

	/**
	 * 获取 幸运农场的已开奖期数
	 *
	 * @return 幸运农场的期数
	 */
	public static String findLotteryXYNCPeriod() {
		Date date = new Date();
		int period = findXYNCDayPeriod(date) - 1;
		if (period < 1) {
			period = 59;
		}
		String day = DateTool.getDay(date);
		return String.format(XYNC_PERIOD_FORMAT, day, period);

	}

	//=======================================XYNC 区域===================================================================

	/**
	 * 比对期数
	 *
	 * @param period     原期数
	 * @param valiPeriod 校对期数
	 * @return 相同true
	 */
	public static boolean equals(Object period, Object valiPeriod) {
		return (Integer.parseInt(period.toString()) - Integer.parseInt(valiPeriod.toString())) == 0;
	}

	/**
	 * 根据游戏code获取当前期数
	 *
	 * @param gameCode 游戏code
	 * @return 当前期数
	 * @throws Exception code异常
	 */
	public static Object findPeriod(String gameCode) throws Exception {
		if (StringTool.isEmpty(gameCode)) {
			throw new Exception("未识别的游戏code");
		}
		switch (gameCode) {
			case "PK10":
				return findPK10Period();
			case "CQSSC":
				return findCQSSCPeriod();
			case "XYFT":
				return findXYFTPeriod();
			case "KLC":
				return findKLCPeriod();
			case "XYNC":
				return findXYNCPeriod();
			default:
				throw new Exception("获取期数异常");
		}

	}

	/**
	 * 根据游戏code获取当前期数
	 *
	 * @param gameCode 游戏code
	 * @return 当前期数
	 * @throws Exception code异常
	 */
	public static String findLotteryPeriod(String gameCode) throws Exception {
		if (StringTool.isEmpty(gameCode)) {
			throw new Exception("未识别的游戏code");
		}
		switch (gameCode) {
			case "PK10":
				return findLotteryPK10Period().toString();
			case "CQSSC":
				return findLotteryCQSSCPeriod();
			case "XYFT":
				return findLotteryXYFTPeriod();
			case "KLC":
				return findLotteryKLCPeriod();
			case "XYNC":
				return findLotteryXYNCPeriod();
			default:
				throw new Exception("获取期数异常");
		}
	}

	/**
	 * 根据 游戏 获取开奖期数
	 *
	 * @param game 游戏
	 * @return 当前期数
	 */
	public static Object findLotteryPeriod(IbmGameEnum game) {
		switch (game) {
			case PK10:
				return findLotteryPK10Period();
			case CQSSC:
				return findLotteryCQSSCPeriod();
			case XYFT:
				return findLotteryXYFTPeriod();
			case KLC:
				return findLotteryKLCPeriod();
			case XYNC:
				return findLotteryXYNCPeriod();
			default:
				throw new RuntimeException("不存在的游戏" + game.getName());
		}
	}
	/**
	 * 根据游戏获取期数
	 *
	 * @param game 游戏
	 * @return 期数
	 */
	public static Object findPeriod(IbmGameEnum game) {
		switch (game) {
			case PK10:
				return findPK10Period();
			case CQSSC:
				return findCQSSCPeriod();
			case XYFT:
				return findXYFTPeriod();
			case KLC:
				return findKLCPeriod();
			case XYNC:
				return findXYNCPeriod();
			default:
				throw new RuntimeException("获取期数异常");
		}
	}
	/**
	 * 根据游戏和指定时间获取期数
	 *
	 * @param game 游戏
	 * @param date 指定时间
	 * @return 期数
	 */
	public static Object findPeriod(IbmGameEnum game, Date date) {
		switch (game) {
			case PK10:
				return findPK10Period(date);
			case CQSSC:
				return findCQSSCPeriod(date);
			case XYFT:
				return findXYFTPeriod(date);
			case KLC:
				return findKLCPeriod(date);
			case XYNC:
				return findXYNCPeriod(date);
			default:
				throw new RuntimeException("获取期数异常");
		}

	}

	/**
	 * 查找历史期数
	 *
	 * @param game   游戏
	 * @param period 指定期数
	 * @param number 查找数量
	 * @return 历史期数    Object[]
	 */
	public static Object[] findHistoryPeriod(IbmGameEnum game, Object period, int number) throws ParseException {
		switch (game) {
			case PK10:
				return findHistoryPK10Period(NumberTool.getInteger(period), number);
			case XYFT:
				return findHistoryXYFTPeriod(period.toString(), number);
			case CQSSC:
			case KLC:
			case XYNC:
			default:
				throw new RuntimeException("获取期数异常");
		}

	}
	/**
	 * 查找历史期数
	 *
	 * @param game   游戏
	 * @param number 查找数量
	 * @return 历史期数    Object[]
	 */
	public static Object[] findHistoryPeriod(IbmGameEnum game, int number) throws ParseException {
		switch (game) {
			case PK10:
				return findHistoryPK10Period(findPK10Period(), number);
			case XYFT:
				return findHistoryXYFTPeriod(findXYFTPeriod(), number);
			case CQSSC:
			case KLC:
			case XYNC:
			default:
				throw new RuntimeException("获取期数异常");
		}

	}
	/**
	 * 查找期数段内的所有期数
	 *
	 * @param game        游戏
	 * @param startPeriod 开始期数
	 * @param endPeriod   结束期数
	 * @return 期数段内的所有期数 String[]
	 */
	public static Object[] findPeriods(IbmGameEnum game, Object startPeriod, Object endPeriod) throws ParseException {
		switch (game) {
			case PK10:
				return findPK10Periods(NumberTool.getInteger(startPeriod), NumberTool.getInteger(endPeriod));
			case XYFT:
				return findXYFTPeriods(startPeriod.toString(), endPeriod.toString());
			case CQSSC:
			case KLC:
			case XYNC:
			default:
				throw new RuntimeException("获取期数异常");
		}

	}

	/**
	 * 根据游戏code获取下一期开奖时间戳
	 *
	 * @param gameCode 游戏code
	 * @return 当前期数
	 * @throws Exception code异常
	 */
	public static long getDrawTime(String gameCode) throws Exception {
		if (StringTool.isEmpty(gameCode)) {
			throw new RuntimeException("未识别的游戏code");
		}
		switch (gameCode) {
			case "PK10":
				return getPK10DrawTime();
			case "CQSSC":
				return getCQSSCDrawTime();
			case "XYFT":
				return getXYFTDrawTime();
			case "KLC":
				return getKLCDrawTime();
			case "XYNC":
				return getXYNCDrawTime();
			default:
				throw new RuntimeException("获取时间异常");
		}

	}

	/**
	 * 根据游戏code获取已开奖的时间戳
	 *
	 * @param gameCode 游戏code
	 * @return 当前期数
	 */
	public static long getLotteryDrawTime(String gameCode) throws Exception {
		if (StringTool.isEmpty(gameCode)) {
			throw new RuntimeException("未识别的游戏code");
		}
		switch (gameCode) {
			case "PK10":
				return getLotteryPK10DrawTime();
			case "CQSSC":
				return getLotteryCQSSCDrawTime();
			case "XYFT":
				return getLotteryXYFTDrawTime();
			default:
				throw new RuntimeException("获取时间异常");
		}
	}

	/**
	 * 获取开奖时间
	 *
	 * @param gameCode 游戏code
	 * @param period   期数
	 * @return 开奖时间
	 */
	public static long getDrawTime(String gameCode, Object period) throws ParseException {
		if (StringTool.isEmpty(gameCode)) {
			throw new RuntimeException("未识别的游戏code");
		}
		switch (gameCode) {
			case "PK10":
				return getPK10DrawTime(NumberTool.getInteger(period));
			case "CQSSC":
				return getCQSSCDrawTime(period.toString());
			case "XYFT":
				return getXYFTDrawTime(period.toString());
			default:
				throw new RuntimeException("获取时间异常");
		}
	}

	/**
	 * 获取开奖时间差
	 *
	 * @param gameCode 游戏code
	 * @return 开奖时间差
	 */
	public static long getPeriodTime(String gameCode) {
		switch (gameCode) {
			case "PK10":
				return PK10_PERIOD;
			case "XYFT":
				return XYFT_PERIOD;
			case "CQSSC":
				return CQSSC_PERIOD;
			default:
				throw new RuntimeException("获取开奖时间差异常");
		}
	}

	/**
	 * 获取跟进期数
	 *
	 * @param game 游戏
	 * @return 期数
	 */
	public static Object findBeforePeriod(IbmGameEnum game, Object period, int followPeriod) throws ParseException {
		switch (game) {
			case PK10:
				return (Integer) period - followPeriod;
			case XYFT:
				return findXYFTBeforePeriod(period.toString(), followPeriod);
			case CQSSC:
			case KLC:
			case XYNC:
				throw new RuntimeException("未开通的游戏" + game.getName());
			default:
				throw new RuntimeException("不存在的游戏" + game.getName());
		}
	}
	/**
	 * 获取指定期数的上一期期数
	 *
	 * @param game   游戏
	 * @param period 指定期数
	 * @return 期数
	 */
	public static Object findLastPeriod(IbmGameEnum game, Object period) throws ParseException {
		return findBeforePeriod(game, period, 1);
	}
}
