package org.doming.core.tools;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Dongming
 * @Description: 时间工具类
 * @ClassName: DateTool
 * @date 2018年4月28日 上午9:51:43
 * @Email: job.dongming@foxmail.com
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class DateTool {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat CN_SDF = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");

	private static final SimpleDateFormat MINUTE_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	private static final SimpleDateFormat DATE_SDF = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DAY_SDF = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat NYRSF_SDF = new SimpleDateFormat("yyyyMMdd HH:mm");

	private static final SimpleDateFormat HMS_SDF = new SimpleDateFormat("HH:mm:ss");

	private static final SimpleDateFormat MD_SDF = new SimpleDateFormat("MM_dd");
	private static final SimpleDateFormat YEAR_SDF = new SimpleDateFormat("yyyy");

	// FIXME 获取格式化时间区域
	//获取格式化时间区域
	/**
	 * 将Date时间转换为字符串
	 *
	 * @param date 时间
	 * @param sdf  格式化格式
	 * @return 时间字符串
	 */
	public static String getStr(Date date, SimpleDateFormat sdf) {
		synchronized (DateTool.class) {
			return sdf.format(date);
		}
	}

	/**
	 * 将时间字符串转换为时间
	 *
	 * @param timeStr 字符串
	 * @param sdf     格式化模板
	 * @return Date时间
	 */
	public static Date get(String timeStr, SimpleDateFormat sdf) throws ParseException {
		synchronized (DateTool.class) {
			return sdf.parse(timeStr);
		}
	}

	/**
	 * 转换
	 * yyyy-MM-dd HH:mm:ss
	 * 型时间字符串
	 *
	 * @param date 时间
	 * @return 时间字符串
	 */
	public static String get(Date date) {
		return getStr(date, SDF);
	}

	/**
	 * 转换
	 * yyyy-MM-dd HH:mm:ss
	 * 型时间
	 *
	 * @param timeStr 时间字符串
	 * @return 时间
	 */
	public static Date get(String timeStr) throws ParseException {
		return get(timeStr, SDF);
	}

	/**
	 * 转换
	 * yyyyMMdd
	 * 型日期型字符串
	 *
	 * @param date 时间
	 * @return 时间天字符串
	 */
	public static String getDay(Date date) {
		return getStr(date, DAY_SDF);
	}
	/**
	 * 转换
	 * yyyyMMdd
	 * 型日期型字符串
	 *
	 * @param date 时间
	 * @return 时间天字符串
	 */
	public static String getYear(Date date) {
		return getStr(date, YEAR_SDF);
	}


	/**
	 * 转换
	 * yyyyMMdd
	 * 型日期型字符串
	 *
	 * @param dateStr 时间字符串
	 * @return 时间天
	 */
	public static Date getDay(String dateStr) throws ParseException {
		return get(dateStr, DAY_SDF);
	}

	/**
	 * 转换
	 * yyyy-MM-dd
	 * 型日期型字符串
	 *
	 * @param date 时间
	 * @return 时间天字符串
	 */
	public static String getDate(Date date) {
		return getStr(date, DATE_SDF);
	}

	/**
	 * 转换
	 * MM_dd
	 * 型日期型字符串
	 *
	 * @param date 时间
	 * @return 时间月天字符串
	 */
	public static String getMDDate(Date date) {
		return getStr(date, MD_SDF);
	}


	/**
	 * 转换
	 * yyyy-MM-dd
	 * 型日期时间
	 *
	 * @param dateStr 日期符串
	 * @return 时间
	 */
	public static Date getDate(String dateStr) throws ParseException {
		return get(dateStr, DATE_SDF);
	}

	/**
	 * 转换
	 * yyyy-MM-dd
	 * 型日期型字符串
	 *
	 * @param date 时间
	 * @return 时间天字符串
	 */
	public static String getMinute(Date date) {
		return getStr(date, MINUTE_SDF);
	}

	/**
	 * 转换
	 * HH:mm:ss
	 * 型时间
	 *
	 * @param timeStr HH:mm:ss时间字符串
	 * @return 时间
	 */
	public static Date getTime(Object timeStr) {
		String[] strs = timeStr.toString().split(":");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strs[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(strs[1]));
		calendar.set(Calendar.SECOND, Integer.parseInt(strs[2]));
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取指定时间    HH:mm:ss
	 */
	public static String getTime(Date time) throws ParseException {
		return getStr(time, HMS_SDF);
	}

	/**
	 * 设定时分到指定的时间中
	 *
	 * @param date  指定的时间
	 * @param hmStr 设定时分
	 * @return 时间
	 */
	public static Date getHm(Date date, String hmStr) {
		String[] strs = hmStr.split(":");
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strs[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(strs[1]));
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 设定时分秒到指定的时间中
	 *
	 * @param date  指定的时间
	 * @param hmStr 设定时分
	 * @return 时间
	 */
	public static Date getHms(Date date, String hmStr) {
		String[] strs = hmStr.split(":");
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
		}
		calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strs[0]));
		calendar.set(Calendar.MINUTE, Integer.parseInt(strs[1]));
		calendar.set(Calendar.SECOND, Integer.parseInt(strs[2]));
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获取字符串
	 *
	 * @param obj 获取对象
	 * @return 时间字符串
	 */
	public static String getStr(Object obj) throws ParseException {
		if (StringTool.isEmpty(obj)) {
			return null;
		}
		if (obj instanceof Date) {
			return get((Date) obj);
		} else if (obj instanceof Number) {
			return get(new Date(NumberTool.getInteger(obj)));
		} else {
			System.out.println(obj.toString());
			return get(get(obj.toString(), SDF));
		}
	}

	/**
	 * 设定时分到指定的时间中
	 *
	 * @param hmStr 设定时分
	 * @return 时间
	 */
	public static Date getHm(String hmStr) {
		return getHm(null, hmStr);
	}
	/**
	 * 设定时分秒到指定的时间中
	 *
	 * @param hmStr 设定时分
	 * @return 时间
	 */
	public static Date getHms(String hmStr) {
		return getHms(null, hmStr);
	}

	/**
	 * 计算时间差多少秒
	 *
	 * @param timeEnd   结束时间戳
	 * @param timeStart 开始时间戳
	 * @return 时间差
	 */
	public static long distanceSecond(long timeEnd, long timeStart) {
		long diff = timeEnd - timeStart;
		return diff / 1000L;
	}

	/**
	 * 转换
	 * MM-dd HH:mm
	 * 型时间
	 *
	 * @param timeStr MM-dd HH:mm时间字符串
	 * @return 时间
	 */
	public static Date getMdHmDate(String timeStr) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		timeStr = year + "-" + timeStr;
		return get(timeStr, MINUTE_SDF);
	}

	/**
	 * 转换
	 * yyyy年MM月dd日 HH时mm分ss秒
	 * 型时间字符串
	 *
	 * @param date 时间
	 * @return 中文时间字符串
	 */
	public static String getCnStr(Date date) {
		return getStr(date, CN_SDF);
	}

	/**
	 * 转换
	 * yyyy年MM月dd日 HH时mm分ss秒
	 * 型时间字符串
	 *
	 * @param time 时间戳
	 * @return 中文时间字符串
	 */
	public static String getCnStr(long time) {
		return getCnStr(new Date(time));
	}

	/**
	 * 转换
	 * yyyy年MM月dd日 HH时mm分ss秒
	 * 型时间字符串
	 *
	 * @return 中文时间字符串
	 */
	public static String getCnStr() {
		return getCnStr(new Date());
	}

	/**
	 * 转换
	 * yyyy-MM-dd HH:mm
	 * 型时间
	 *
	 * @param timeStr yyyy-MM-dd HH:mm时间字符串
	 * @return 时间
	 */
	public static Date getMinute(String timeStr) throws ParseException {
		return get(timeStr, MINUTE_SDF);
	}

	/**
	 * 转换
	 * yyyyMMdd HH:mm
	 * 型时间
	 *
	 * @param timeStr yyyyMMdd HH:mm时间字符串
	 * @return 时间
	 */
	public static Date getDateByStr(String timeStr) throws ParseException {
		return get(timeStr, NYRSF_SDF);
	}

	//获取格式化时间区域

	// FIXME 获取毫秒区域
	//获取毫秒区域

	/**
	 * 获取一天的毫秒
	 *
	 * @return 毫秒
	 */
	public static Long getMillisecondsDay() {
		return 24 * 60 * 60 * 1000L;
	}

	/**
	 * 获取一小时的毫秒
	 *
	 * @return 毫秒
	 */
	public static Long getMillisecondsMinute() {
		return 60 * 1000L;
	}
	/**
	 * 获取一分钟的毫秒
	 *
	 * @return 毫秒
	 */
	public static Long getMillisecondsHour() {
		return 60 * 60 * 1000L;
	}

	/**
	 * 将天数转换为毫秒数
	 *
	 * @param days 天数
	 * @return 毫秒数
	 */
	public static Long getMillisecondsDays(Integer days) {
		return days * getMillisecondsDay();
	}

	/**
	 * 将分钟数转换为毫秒数
	 *
	 * @param minutes 分钟数
	 * @return 毫秒数
	 */
	public static Long getMillisecondsMinutes(Integer minutes) {
		return minutes * getMillisecondsMinute();
	}
	/**
	 * 将分钟数转换为毫秒数
	 *
	 * @param hours 分钟数
	 * @return 毫秒数
	 */
	public static Long getMillisecondsHours(Integer hours) {
		return hours * getMillisecondsHour();
	}

	/**
	 * 根据规则获取毫秒数
	 * h:小时
	 * m:分钟
	 *
	 * @param ruleTime 规则字符串
	 * @return 毫秒数
	 */

	public static Long getMillisecondsByRule(String ruleTime) throws Exception {
		String[] strs = StringTool.split(ruleTime, 1);
		switch (strs[0]) {
			case "D":
			case "d":
				if (StringTool.isInteger(strs[1])) {
					return getMillisecondsDays(Integer.parseInt(strs[1]));
				} else {
					throw new Exception("时间写入错误失败，必须为数字型" + strs[1]);
				}
			case "H":
			case "h":
				if (StringTool.isInteger(strs[1])) {
					return getMillisecondsHours(Integer.parseInt(strs[1]));
				} else {
					throw new Exception("时间写入错误失败，必须为数字型" + strs[1]);
				}
			case "M":
			case "m":
				if (StringTool.isInteger(strs[1])) {
					return getMillisecondsMinutes(Integer.parseInt(strs[1]));
				} else {
					throw new Exception("时间写入错误失败，必须为数字型" + strs[1]);
				}
			default:
				throw new Exception("时间规则定义失败");
		}
	}

	/**
	 * 获取时间的毫秒差
	 *
	 * @param dateStart 开始时间
	 * @param dateEnd   截止时间
	 * @return 毫秒差
	 */
	public static Long getMilliseconds(Date dateStart, Date dateEnd) {
		return dateStart.getTime() - dateEnd.getTime();
	}

	//获取毫秒区域

	// FIXME 功能区域
	// 功能区
	/**
	 * 获取xDay天之后的时刻
	 *
	 * @param date 时间起点
	 * @param xDay 过去天数
	 * @return 计算的时间 返回类型 Date
	 * @Description: 参数说明
	 */
	public static Date getTimeForXDayLater(Date date, Integer xDay) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.DATE, xDay);
		return now.getTime();
	}

	/**
	 * 根据时分字符串，获取指定时间的相应时间
	 * 参数说明
	 *
	 * @param time  指定时间天
	 * @param hmstr 时分字符串
	 * @return 指定时间的相应时间
	 */
	public static Date getStartTimeByTimeAndHm(Date time, String hmstr) throws ParseException {
		String[] strs = hmstr.split(":");
		Calendar now = Calendar.getInstance();
		now.setTime(time);
		now.set(Calendar.HOUR_OF_DAY, Integer.parseInt(strs[0]));
		now.set(Calendar.MINUTE, Integer.parseInt(strs[1]));
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.MILLISECOND, 0);
		return now.getTime();
	}

	/**
	 * 获取时间起点和时间终点中度过的时间段
	 *
	 * @param dateStart  起点
	 * @param dateEnd    终点
	 * @param periodTime 时间段毫秒
	 * @return 时间段
	 */
	public static Long getPeriod(Date dateStart, Date dateEnd, long periodTime) {
		return Math.abs(getMilliseconds(dateStart, dateEnd)) / periodTime;
	}
	/**
	 * 获取时间起点和时间终点中度过的时间段
	 *
	 * @param startLong  起点
	 * @param endLong    终点
	 * @param periodTime 时间段毫秒
	 * @return 时间段
	 */
	public static Long getPeriod(long startLong, long endLong, long periodTime) {
		return Math.abs(startLong - endLong) / periodTime;
	}

	/**
	 * 计算两个时间段内的天数差
	 *
	 * @param dateStart 起点
	 * @param dateEnd   终点
	 * @return 时间差
	 */
	public static Double getDaysBetween(Date dateStart, Date dateEnd) {
		return Math.abs(((double) (getMilliseconds(dateStart, dateEnd)) / getMillisecondsDay()));
	}

	/**
	 * 计算两个时间段内的天数差
	 *
	 * @param startTime 起点
	 * @param endTime   终点
	 * @return 时间差
	 */
	public static Double getDaysBetween(long startTime, long endTime) {
		return Math.abs((double) (startTime- endTime) / getMillisecondsDay());
	}

	/**
	 * 获取指定天的00:00:00
	 *
	 * @param date 时间
	 * @return 当前时间的 00:00:00
	 */
	public static Date getDayStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	/**
	 * 获取指定天的 23:59:59
	 *
	 * @param date 时间
	 * @return 指定天的 23:59:59
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	/**
	 * 获取指定天的 23:59:59
	 *
	 * @param day 时间
	 * @return 指定天的 23:59:59
	 */
	private static Date getDayEnd(String day) throws ParseException {
		day = day.concat(" 23:59:59");
		return get(day);
	}

	/**
	 * 获取当前天的 23:59:59
	 *
	 * @return 当前时间的 23:59:59
	 */
	public static Date getDayEnd() {
		return getDayEnd(new Date());
	}
	/**
	 * 获取当前天的00:00:00
	 *
	 * @return 当前时间的 00:00:00
	 */
	public static Date getDayStart() {
		return getDayStart(new Date());
	}

	/**
	 * 获取指定天的 05:30:00
	 *
	 * @param day 时间
	 * @return 指定天的 23:59:59
	 */
	public static Date getBetTime(String day) throws ParseException {
		day = day.concat(" 05:30:00");
		return get(day);
	}

	/**
	 * 获取昨天的开始时间
	 *
	 * @return 昨天的开始时间
	 */
	public static Date getYesterdayStart() {
		return new Date(getDayStart().getTime() - getMillisecondsDay());
	}
	/**
	 * 获取昨天的结束时间
	 *
	 * @return 昨天的结束时间
	 */
	public static Date getYesterdayEnd() {
		return new Date(getDayStart().getTime() - 1L);
	}

	/**
	 * 获取指定日期前一天的日期
	 * yyyyMMdd
	 *
	 * @param date 指定日期
	 * @return 日期
	 */
	public static String getYesterday(Date date) {
		return getBeforeDay(date, 1);
	}

	/**
	 * 获取指定日期前一天的日期
	 * yyyyMMdd
	 *
	 * @param str 指定日期
	 * @return 日期
	 */
	public static String getYesterday(String str) throws ParseException {
		return getBeforeDay(str, 1);
	}

	/**
	 * 获取指定日期前 before 天的日期
	 * yyyyMMdd
	 *
	 * @param str    指定日期
	 * @param before 天数
	 * @return 日期
	 */
	public static String getBeforeDay(String str, int before) throws ParseException {
		return getAfterDay(str, 0 - before);
	}
	/**
	 * 获取指定日期前 before 天的日期
	 * yyyyMMdd
	 *
	 * @param date   指定日期
	 * @param before 天数
	 * @return 日期
	 */
	public static String getBeforeDay(Date date, int before) {
		return getAfterDay(date, 0 - before);
	}

	/**
	 * 获取指定日期后一天的日期
	 * yyyyMMdd
	 *
	 * @param date 指定日期
	 * @return 日期
	 */
	public static String getTomorrow(Date date) {
		return getAfterDay(date, 1);
	}
	/**
	 * 获取指定日期后 after 天的日期
	 * yyyyMMdd
	 *
	 * @param str   指定日期
	 * @param after 天数
	 * @return 日期
	 */
	public static String getAfterDay(String str, int after) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(DateTool.getDay(str));
		calendar.set(Calendar.HOUR_OF_DAY, after);
		return DateTool.getDay(calendar.getTime());
	}
	/**
	 * 获取指定日期后 after 天的日期
	 * yyyyMMdd
	 *
	 * @param date  指定日期
	 * @param after 天数
	 * @return 日期
	 */
	public static String getAfterDay(Date date, int after) {
		return getDay(getAfterDate(date,after));
	}

	/**
	 * 获取明天的时间
	 * @param date 指定日期
	 * @return 明天的时间
	 */
	public static Date getAfterDate(Date date) {
		return getAfterDate(date,1);
	}
	/**
	 * 获取明天的时间
	 * @param date 指定日期
	 * @return 明天的时间
	 */
	public static Date getBeforeDate(Date date) {
		return getAfterDate(date,-1);
	}

	/**
	 * 获取X天后的日期
	 * @param date   指定日期
	 * @param after 天数
	 * @return 日期
	 */
	private static Date getAfterDate(Date date, int after) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, after);
		return calendar.getTime();
	}

	/**
	 * 获取指定日期后 after 天的日期
	 * yyyyMMdd
	 *
	 * @param date         指定日期
	 * @param milliseconds 毫秒
	 * @return 日期
	 */
	public static Date getAfterMilliseconds(Date date, int milliseconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND, milliseconds);
		return calendar.getTime();
	}
	/**
	 * 获取指定日期后 after 天的日期
	 * yyyyMMdd
	 *
	 * @param date    指定日期
	 * @param minutes 分钟
	 * @return 日期
	 */
	public static Date getAfterMinutes(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		return calendar.getTime();
	}
	/**
	 * 获取当前日期后 after 天的日期
	 * yyyyMMdd
	 *
	 * @param days 天数
	 * @return 日期
	 */
	public static Date getAfterDays(int days) {
		return getAfterDays(new Date(), days);
	}
	/**
	 * 获取指定日期后 after 天的日期
	 * yyyyMMdd
	 *
	 * @param date 指定日期
	 * @param days 天数
	 * @return 日期
	 */
	public static Date getAfterDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, days);
		return calendar.getTime();
	}
	/**
	 * 获取指定日期前 before 天的日期
	 *
	 * @param date 指定日期
	 * @param days 天数
	 * @return 日期
	 */
	public static Date getBeforeDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -days);
		return calendar.getTime();
	}
	/**
	 * 获取指定日期后 ruleTime 规则的日期
	 * yyyyMMdd
	 *
	 * @param date     指定日期
	 * @param ruleTime 规则
	 * @return 日期
	 */
	public static Date getAfterRule(Date date, String ruleTime) throws Exception {
		return getAfterMilliseconds(date, getMillisecondsByRule(ruleTime).intValue());
	}

	/**
	 * 获取当月开始时间
	 *
	 * @return 当月开始时间
	 */
	public static Date getMonthStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return getDayStart(calendar.getTime());
	}

	/**
	 * 获取上月开始时间
	 *
	 * @return 上月开始时间
	 */
	public static Date getLastMonthStart() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return getDayStart(calendar.getTime());
	}
	/**
	 * 获取上月结束时间
	 *
	 * @return 上月结束时间
	 */
	public static Date getLastMonthEnd() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return getDayEnd(calendar.getTime());
	}

	/**
	 * 获取上周一时间
	 * @param date
	 * @return
	 */
	public static Date geLastWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, -7);
		return cal.getTime();
	}
	/**
	 * 获取本周一时间
	 * @param date
	 * @return
	 */
	public static Date getThisWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 获得当前日期是一个星期的第几天
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}

	/**
	 * 获取下周一时间
	 * @param date
	 * @return
	 */
	public static Date getNextWeekMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getThisWeekMonday(date));
		cal.add(Calendar.DATE, 7);
		return cal.getTime();
	}

	/**
	 * 检查时间差是否小于定义规则时间
	 * 默认结束时间为当前时间
	 *
	 * @param startDate 开始时间
	 * @param ruleTime  规则时间
	 * @return 小于true
	 */
	public static boolean checkTimeDiff(Date startDate, String ruleTime) throws Exception {
		return checkTimeDiff(startDate, new Date(), ruleTime);
	}

	/**
	 * 检查时间差是否小于定义规则时间
	 *
	 * @param startDate 开始时间
	 * @param endDate   结束时间
	 * @param ruleTime  规则时间
	 * @return 小于true
	 */
	public static boolean checkTimeDiff(Date startDate, Date endDate, String ruleTime) throws Exception {
		Long diffMilliseconds = getMillisecondsByRule(ruleTime);
		return endDate.getTime() - startDate.getTime() < diffMilliseconds;

	}

	/**
	 * 比较两个时间的大小
	 *
	 * @param data1 时间一
	 * @param data2 时间二
	 * @return data1 <= data2 true
	 */
	public static boolean compare(Date data1, Date data2) {
		return data1.getTime() <= data2.getTime();
	}

	public static long getLog() {
		return System.currentTimeMillis();
	}

	/**
	 * 转换
	 * HH:mm:ss
	 * 型时间戳
	 *
	 * @param obj HH:mm:ss时间字符串
	 * @return 时间
	 */
	public static Long getLongTime(Object obj) {
		Date time = getTime(obj);
		if (StringTool.isEmpty(time)) {
			return null;
		}
		return time.getTime();
	}

	/**
	 * 返回秒钟
	 *
	 * @return 返回秒钟
	 */
	public static int getSecond() {
		return getSecond(new Date());
	}
	/**
	 * 返回秒钟
	 *
	 * @param date Date 日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}
	/**
	 * 获取两个日期字符串之间的日期集合
	 *
	 * @param startTime:开始时间
	 * @param endTime:结束时间
	 * @return list:yyyy-MM-dd
	 */
	public static List<String> getDates(String startTime, String endTime) throws ParseException {
		// 声明保存日期集合
		List<String> list = new ArrayList<>();

		// 转化成日期类型
		Date startDate = getDate(startTime);
		Date endDate = getDayEnd(endTime);

		//用Calendar 进行日期比较判断
		Calendar calendar = Calendar.getInstance();
		while (startDate.before(endDate)) {
			// 把日期添加到集合
			list.add(DATE_SDF.format(startDate));
			// 设置日期
			calendar.setTime(startDate);
			//把日期增加一天
			calendar.add(Calendar.DATE, 1);
			// 获取增加后的日期
			startDate = calendar.getTime();
		}
		return list;
	}

	// 功能区

	// FIXME 判断区
	// 判断区
	/**
	 * 判断是否属于 yyyy-MM-dd 型时间
	 *
	 * @param dateStr 时间字符串
	 * @return 是 true
	 */
	public static boolean isDate(String dateStr) {
		String format = "[0-9]{4}-[0-9]{2}-[0-9]{2}";
		Pattern pattern = Pattern.compile(format);
		return pattern.matcher(dateStr).matches();
	}
	/**
	 * 判断是否属于 HH:mm 型时间
	 *
	 * @param timeStr 时间字符串
	 * @return 是 true
	 */
	public static boolean isTime(String timeStr) {
		String format = "[0-2][0-9]:[0-6][0-9]";
		Pattern pattern = Pattern.compile(format);
		return pattern.matcher(timeStr).matches();
	}
	// 判断区

	/**
	 * 转换成时间戳
	 *
	 * @param obj 时间对象
	 * @return 时间戳
	 */
	public static long parseLong(Object obj) {
		if (obj instanceof Time) {
			return ((Time) obj).getTime();
		} else if (obj instanceof Date) {
			return ((Date) obj).getTime();
		} else {
			return getTime(obj).getTime();
		}
	}

}
