package c.a.util.core.date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import c.a.util.core.string.StringUtil;
/**
 * 日期工具类;
 * 
 * 偶尔会报java.lang.NumberFormatException: multiple points
 * 
 * 或者报java.lang.NumberFormatException: For input string: ".31023102EE22"
 * 
 * 原来是SimpleDateFormat是非线程安全的， 在多线程情况下会有问题，
 * 
 * 在每个线程下得各自new SimpleDateFormat()就可以了
 * 
 * @Description:
 * @ClassName: DateUtil
 * @date 2018年8月14日 上午10:00:14
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class DateUtil {

	/**
	 * 年月日;英文;
	 */
	public static String dateEnStr = "yyyy-MM-dd";

	/**
	 * 年月日;英文;8个字符;
	 */
	public static String dateEnStr8 = "yyyyMMdd";

	/**
	 * 年月日;英文;8个字符;
	 */
	public SimpleDateFormat dateEnDateFormat8 = new SimpleDateFormat(dateEnStr8);
	/**
	 * 年月日;英文;
	 */
	public SimpleDateFormat dateEnDateFormat = new SimpleDateFormat(dateEnStr);
	/**
	 * 年月日;中文;
	 */
	public static String dateCnStr = "yyyy年MM月dd日";
	/**
	 * 年月日;中文;
	 */
	public SimpleDateFormat dateCnDateFormat = new SimpleDateFormat(dateCnStr);
	/**
	 * 时分秒;12小时制;英文;
	 */
	public static String time12hEnStr = "hh:mm:ss";
	/**
	 * 时分秒;12小时制;英文;
	 */
	public SimpleDateFormat time12hEnDateFormat = new SimpleDateFormat(time12hEnStr);
	/**
	 * 时分秒;24小时制;英文;
	 */
	public static String time24hEnStr = "HH:mm:ss";
	/**
	 * 时分秒;24小时制;英文;
	 */
	public SimpleDateFormat time24hEnDateFormat = new SimpleDateFormat(time24hEnStr);
	/**
	 * 年月日;时分秒;12小时制;英文;
	 */
	public static String datetime12hEnStr = "yyyy-MM-dd hh:mm:ss";
	/**
	 * 年月日;时分秒;12小时制；英文;
	 */
	public SimpleDateFormat datetime12hEnDateFormat = new SimpleDateFormat(datetime12hEnStr);
	/**
	 * 年月日;时分秒;24小时制;英文;
	 */
	public static String datetime24hEnStr = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 年月日时分秒;24小时制；英文;
	 */
	public SimpleDateFormat datetime24hEnDateFormat = new SimpleDateFormat(datetime24hEnStr);
	/**
	 * 
	 * 毫秒;
	 * 
	 * 年月日;时分秒;24小时制;英文;
	 */
	public static String datetime24hEnMilliSecondStr = "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * 
	 * 
	 * 
	 * 年月日时分秒;24小时制；英文;毫秒;
	 */
	public SimpleDateFormat datetime24hEnMilliSecondDateFormat = new SimpleDateFormat(datetime24hEnMilliSecondStr);

	/**
	 * 14个字符(去掉冒号);
	 * 
	 * 
	 * 毫秒;
	 * 
	 * 年月日;时分秒;24小时制;英文;
	 */
	public static String datetime24hEnMilliSecondStr14 = "yyyyMMddHHmmss";

	/**
	 * 17个字符(去掉冒号);
	 * 
	 * 
	 * 毫秒;
	 * 
	 * 年月日;时分秒;24小时制;英文;
	 */
	public static String datetime24hEnMilliSecondStr17 = "yyyyMMddHHmmssSSS";
	/**
	 * 年月日时分秒;24小时制；英文;14个字符;
	 */
	public SimpleDateFormat datetime24hEnMilliSecondDateFormat14 = new SimpleDateFormat(datetime24hEnMilliSecondStr14);

	/**
	 * 年月日时分秒;24小时制；英文;毫秒; 17个字符;
	 */
	public SimpleDateFormat datetime24hEnMilliSecondDateFormat17 = new SimpleDateFormat(datetime24hEnMilliSecondStr17);

	/**
	 * 年月日;时分秒;12小时制;中文;
	 */
	public static String datetime12hCnStr = "yyyy年MM月dd日hh时mm分ss秒";
	/**
	 * 年月日;时分秒;12小时制；中文;
	 */
	public SimpleDateFormat datetime12hCnDateformat = new SimpleDateFormat(datetime12hCnStr);
	/**
	 * 年月日;时分秒;24小时制;中文;
	 */
	public static String datetime24hCnStr = "yyyy年MM月dd日 HH时mm分ss秒";
	/**
	 * 年月日;时分秒;24小时制;中文;
	 */
	public SimpleDateFormat datetime24hCnDateFormat = new SimpleDateFormat(datetime24hCnStr);
	private Map<String, Object> weekMap = null;
	public Map<String, Object> findWeekMap() {
		if (weekMap == null) {
			weekMap = new HashMap<String, Object>();
			weekMap.put("0", "SUN");
			weekMap.put("1", "MON");
			weekMap.put("2", "TUE");
			weekMap.put("3", "WED");
			weekMap.put("4", "THU");
			weekMap.put("5", "FRI");
			weekMap.put("6", "SAT");
		}
		return weekMap;
	}
	public void setWeekMap(Map<String, Object> weekMapInput) {
		weekMap = weekMapInput;
	}
	public Long doString2longSecond(String dateStr) throws ParseException {
		if (StringUtil.isBlank(dateStr)) {
			return null;
		}
		Date date = doString2Date(dateStr);
		if (date == null) {
			return null;
		} else {
			return date.getTime() / 1000;
		}
	}

	/**
	 * 字符串转Date
	 * 
	 * @Title: doString2Date
	 * @Description:
	 *
	 * 				参数说明
	 * @param dateStr
	 * @param format
	 * @return 返回类型 Date
	 */
	public Date doString2Date(String dateStr, String format) {
		if (StringUtil.isBlank(dateStr)) {
			return null;
		}
		java.util.Date date = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			date = dateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	/**
	 * 字符串转Date
	 * 
	 * @Description: desc @Title: doString2Date @param dateStr @return
	 *               参数说明 @return Date 返回类型 @throws
	 */
	public Date doString2Date(String dateStr) {
		if (StringUtil.isBlank(dateStr)) {
			return null;
		}
		java.util.Date date = null;
		try {
			date = datetime24hEnDateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}

	/**
	 * 重新转换格式(比如数据库这样的格式2018-07-03 15:30:31.0)
	 * 
	 * @Title: doString2String
	 * @Description:
	 *
	 * 				参数说明
	 * @param dateStr
	 * @return
	 * @throws ParseException
	 *             返回类型 String
	 */
	public String doString2String(String dateStr) {
		Date date = doString2Date(dateStr);

		dateStr = doUtilDate2String(date);

		return dateStr;
	}

	/**
	 * 重新转换格式
	 * 
	 * @Description @Title doString2String @param dateStr @param
	 *              formatSource @param formatDest @return return @throws
	 *              ParseException 参数说明 @return String 返回类型 @throws
	 */
	public String doString2String(String dateStr, String formatSource, String formatDest) throws ParseException {
		if (dateStr == null || dateStr.trim().equals("")) {
			return null;
		}
		DateFormat sourceDateFormat = new SimpleDateFormat(formatSource);
		java.util.Date date = sourceDateFormat.parse(dateStr);
		DateFormat destDateFormat = new SimpleDateFormat(formatDest);
		return destDateFormat.format(date);
	}

	/**
	 * 重新转换格式(年月日时分秒;24小时制；英文)
	 * 
	 * @Description @Title doString2String @param dateStr @param
	 *              formatSource @return return @throws ParseException
	 *              参数说明 @return String 返回类型 @throws
	 */
	public String doString2String(String dateStr, String formatSource) throws ParseException {
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}
		DateFormat sourceDateFormat = new SimpleDateFormat(formatSource);
		java.util.Date date = sourceDateFormat.parse(dateStr);
		return datetime24hEnMilliSecondDateFormat.format(date);
	}

	/**
	 * 重新转换格式(年月日时分秒;24小时制;英文;毫秒)
	 * 
	 * @Description @Title doString2String @param dateStr @param
	 *              formatSource @return return @throws ParseException
	 *              参数说明 @return String 返回类型 @throws
	 */
	public String doString2StringSSS(String dateStr, String formatSource) throws ParseException {
		if (dateStr == null || dateStr.equals("")) {
			return null;
		}
		DateFormat sourceDateFormat = new SimpleDateFormat(formatSource);
		java.util.Date date = sourceDateFormat.parse(dateStr);
		return datetime24hEnDateFormat.format(date);
	}

	/**
	 * 秒转成字符串
	 * 
	 * @Description @Title doLongSecond2str @param secondStr @return
	 *              参数说明 @return String 返回类型 @throws
	 */
	public String doLongSecond2String(String secondStr) {
		if (StringUtil.isBlank(secondStr)) {
			return "";
		}
		long longSecond = Long.parseLong(secondStr);
		return doLongSecond2String(longSecond);
	}
	/**
	 * 秒转成字符串
	 * 
	 * @Description @Title doLongSecond2str @param secondLong @return
	 *              参数说明 @return String 返回类型 @throws
	 */
	public String doLongSecond2String(Long secondLong) {
		if (secondLong == 0) {
			return null;
		}
		// millis=1446803196;
		// simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(secondLong * 1000);
		String str = datetime24hEnDateFormat.format(calendar.getTime());
		return str;
	}
	/**
	 * 秒转成字符串
	 * 
	 * @Description @Title doLongMillisSecond2str @param millisSecondLong @param
	 *              format @return 参数说明 @return String 返回类型 @throws
	 */
	public String doLongMillisSecond2String(Long millisSecondLong, String format) {
		if (millisSecondLong == null || millisSecondLong == 0) {
			return null;
		}
		// millis=1446803196;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millisSecondLong);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String str = simpleDateFormat.format(calendar.getTime());
		return str;
	}
	/**
	 * 毫秒转成字符串
	 * 
	 * @Description @Title doLongMillisSecond2str @param
	 *              millisSecondLong @return 参数说明 @return String 返回类型 @throws
	 */
	public String doLongMillisSecond2String(Long millisSecondLong) {
		if (millisSecondLong == null || millisSecondLong == 0) {
			return null;
		}
		// millis=1446803196;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millisSecondLong);
		String str = datetime24hEnDateFormat.format(calendar.getTime());
		return str;
	}
	/**
	 * 毫秒转成字符串
	 * 
	 * @Description @Title doLongMillisSecond2str @param millisSecondStr @return
	 *              参数说明 @return String 返回类型 @throws
	 */
	public String doLongMillisSecond2String(String millisSecondStr) {
		if (StringUtil.isBlank(millisSecondStr)) {
			return "";
		}
		long longSecond = Long.parseLong(millisSecondStr);
		return doLongMillisSecond2String(longSecond);
	}
	/**
	 * 毫秒转成字符串，中文
	 * 
	 * @Description @Title doLongMillisSecond2strCn @param
	 *              millisSecondLong @return 参数说明 @return String 返回类型 @throws
	 */
	public String doLongMillisSecond2StringSSS(Long millisSecondLong) {
		if (millisSecondLong == null || millisSecondLong == 0) {
			return null;
		}
		// millis=1446803196;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millisSecondLong);
		String str = datetime24hEnMilliSecondDateFormat.format(calendar.getTime());
		return str;
	}
	/**
	 * 毫秒转成字符串，中文
	 * 
	 * @Description @Title doLongMillisSecond2strCn @param
	 *              millisSecondLong @return 参数说明 @return String 返回类型 @throws
	 */
	public String doLongMillisSecond2StringCn(Long millisSecondLong) {
		if (millisSecondLong == null || millisSecondLong == 0) {
			return null;
		}
		// millis=1446803196;
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millisSecondLong);
		String str = datetime24hCnDateFormat.format(calendar.getTime());
		return str;
	}

	/**
	 * 日期转成java.util.Date
	 * 
	 * @param millis
	 * @return
	 */
	public Date doLongMillis2UtilDate(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}
	/**
	 * 日期转成java.sql.Date
	 * 
	 * @param millis
	 * @return
	 */
	public java.sql.Date doLongMillis2SqlDate(long millis) {
		java.sql.Date sqlDate = new java.sql.Date(millis);
		return sqlDate;
	}

	/**
	 * date2String方法
	 * 
	 * @param format
	 *            指定格式
	 * @param date
	 * @return
	 */
	public String doUtilDate2String(Date date, String format) {
		if (date == null) {
			return null;
		}
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	/**
	 * date2String方法( 年月日时分秒;24小时制；英文)
	 * 
	 * @param date
	 * @return
	 */
	public String doUtilDate2String24hEn(Date date) {
		if (date == null) {
			return null;
		}
		return datetime24hEnDateFormat.format(date);
	}

	/**
	 * date2String方法( 年月日时分秒; 8个字符)
	 * 
	 * @Description @Title doUtilDate2String17 @param date @return 参数说明 @return
	 *              String 返回类型 @throws
	 */
	public String doUtilDate2String8(Date date) {
		if (date == null) {
			return null;
		}

		return dateEnDateFormat8.format(date);
	}

	/**
	 * date2String方法( 年月日时分秒;24小时制；英文; 14个字符)
	 * 
	 * @Description @Title doUtilDate2String17 @param date @return 参数说明 @return
	 *              String 返回类型 @throws
	 */
	public String doUtilDate2String14(Date date) {
		if (date == null) {
			return null;
		}
		return datetime24hEnMilliSecondDateFormat14.format(date);
	}
	/**
	 * date2String方法( 年月日时分秒;24小时制；英文;毫秒; 17个字符)
	 * 
	 * @Description @Title doUtilDate2String17 @param date @return 参数说明 @return
	 *              String 返回类型 @throws
	 */
	public String doUtilDate2String17(Date date) {
		if (date == null) {
			return null;
		}
		return datetime24hEnMilliSecondDateFormat17.format(date);
	}
	/**
	 * date2String方法( 年月日时分秒;24小时制；英文)
	 * 
	 * @param date
	 * @return
	 */
	public String doUtilDate2String(Date date) {
		if (date == null) {
			return null;
		}
		return datetime24hEnDateFormat.format(date);
	}
	/**
	 * date2String方法( 年月日时分秒;24小时制；英文)
	 * 
	 * @param date
	 * @return
	 */
	public String doUtilDate2StringSSS(Date date) {
		if (date == null) {
			return null;
		}
		return datetime24hEnMilliSecondDateFormat.format(date);
	}
	/**
	 * date2String方法( 年月日时分秒;24小时制；中文)
	 * 
	 * @param date
	 * @return
	 */
	public String doUtilDate2StringCn(Date date) {
		if (date == null) {
			return null;
		}
		return datetime24hCnDateFormat.format(date);
	}
	/**
	 * 
	 * @Title: utilDate2Timestamp @Description:java.util.Date转java.sql.
	 *         Timestamp @param utilDate @return 参数说明 @return java.sql.Timestamp
	 *         返回类型 @throws
	 */
	public java.sql.Timestamp doUtilDate2Timestamp(java.util.Date utilDate) {
		java.sql.Timestamp timestamp = new java.sql.Timestamp(utilDate.getTime());
		return timestamp;
	}
	/**
	 * 自定义时间(年月日时分秒;24小时制；中文;)
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @return
	 */
	public String doInt2StringCn(int year, int month, int date, int hourOfDay, int minute, int second) {
		return doLongMillisSecond2StringCn(doInt2Long(year, month, date, hourOfDay, minute, second));
	}
	/**
	 * 
	 * 
	 * 
	 * 自定义时间(年月日时分秒;24小时制；英文;毫秒)
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public String doInt2StringSSS(int year, int month, int date, int hourOfDay, int minute, int second,
			int millisecond) {
		long valueLong = doInt2LongSSS(year, month, date, hourOfDay, minute, second, millisecond);
		return doLongMillisSecond2StringSSS(valueLong);
	}
	/**
	 * 自定义时间(年月日时分秒;24小时制；英文)
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hourOfDay
	 *            小时
	 * @param minute
	 * @param second
	 * @return
	 */
	public String doInt2String(int year, int month, int date, int hourOfDay, int minute, int second) {
		return doLongMillisSecond2String(doInt2Long(year, month, date, hourOfDay, minute, second));
	}
	/**
	 * 
	 * 
	 * 自定义时间(毫秒);
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hourOfDay
	 *            小时
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @return
	 */
	public Long doInt2LongSSS(int year, int month, int date, int hourOfDay, int minute, int second, int millisecond) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date, hourOfDay, minute, second);
		long longValue = calendar.getTimeInMillis();
		// log.trace(" valueLong=" + valueLong);
		longValue = longValue / 1000 * 1000 + millisecond;
		// log.trace(" valueLong=" + valueLong);
		// 2014-01-01 01:01:01.000
		// valueLong = 1388509261000l;
		return longValue;
	}
	/**
	 * 自定义时间
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @param hourOfDay
	 *            小时
	 * @param minute
	 * @param second
	 * @return
	 */
	public Long doInt2Long(int year, int month, int date, int hourOfDay, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, date, hourOfDay, minute, second);
		return calendar.getTimeInMillis();
	}
	/**
	 * 取昨天日期
	 * 
	 * @return
	 */
	public String findLastDay2String() {
		Calendar calendar = Calendar.getInstance();
		// 天 -1
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		// 月+1
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
		month = month.length() == 1 ? "0" + month : month;
		day = day.length() == 1 ? "0" + day : day;
		return year + month + day;
	}
	/**
	 * 取所指定日期的当天的最前时刻(8位数字)
	 * 
	 * findDayEnd2Long() - findDayStart2long()=3600*24*1000
	 * 
	 * @param strDate
	 * @return
	 */
	public Long findDayStart2Long(String strDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(strDate.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(strDate.substring(4, 6)) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(strDate.substring(6, 8)));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	/**
	 * 取当天日期的最前时刻
	 * 
	 * @return
	 */
	public Long findDayStart2Long() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}
	/**
	 * 取当天日期的最后时刻
	 * 
	 * @return
	 */
	public Long findDayEnd2Long() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// 设置 add
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTimeInMillis();
	}
	/**
	 * 取所指定日期的当天的最后时刻(8位数字)
	 * 
	 * @param dateStr
	 *            20120102
	 * @return
	 */
	public Long findDayEnd2Long(String dateStr) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(dateStr.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(4, 6)) - 1);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6, 8)));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// 设置 add
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTimeInMillis();
	}

	/**
	 * 取所指定日期的当天日期的最前时刻(英文)
	 * 
	 * @Title: findDayStart
	 * @Description:
	 *
	 * 				参数说明
	 * @param dateStr
	 * @return 返回类型 String
	 */
	public String findDayStart(String dateStr) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(Calendar.YEAR, Integer.parseInt(dateStr.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(5, 7)) - 1);
		int i = Integer.parseInt(dateStr.substring(5, 7)) - 1;

		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(8, 10)));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// 设置 add
		// calendar.add(Calendar.DAY_OF_MONTH, 1);
		return doLongMillisSecond2String(calendar.getTimeInMillis());
	}
	/**
	 * 取当天日期的最前时刻(英文)
	 * 
	 * @return
	 */
	public String findDayStart() {
		Calendar start = Calendar.getInstance();
		// start.set(Calendar.HOUR_OF_DAY, 00);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		return doLongMillisSecond2String(start.getTimeInMillis());
	}
	/**
	 * 取当天日期的最前时刻(英文)
	 * 
	 * @return
	 */
	public String findDayStart(int amount) {
		Calendar start = Calendar.getInstance();
		// start.set(Calendar.HOUR_OF_DAY, 00);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		// 自定义,最后加上增减数字
		start.add(Calendar.DAY_OF_MONTH, amount);
		return doLongMillisSecond2String(start.getTimeInMillis());
	}
	/**
	 * 取当天日期的最前时刻(英文)
	 * 
	 * @param format
	 *            指定格式 指定格式
	 * @param amount
	 *            增减数字
	 * @return
	 */
	public String findDayStart(int amount, String format) {
		Calendar start = Calendar.getInstance();
		// start.set(Calendar.HOUR_OF_DAY, 00);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		// 自定义,最后加上增减数字
		start.add(Calendar.DAY_OF_MONTH, amount);
		return doLongMillisSecond2String(start.getTimeInMillis());
	}
	/**
	 * 取所指定日期的当天日期的最后时刻(英文)
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param dateStr
	 * @return 返回类型 String
	 */
	public String findDayEnd(String dateStr) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.parseInt(dateStr.substring(0, 4)));
		calendar.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(5, 7)) - 1);
		int i = Integer.parseInt(dateStr.substring(5, 7)) - 1;
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(8, 10)));
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		// 设置 add
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return doLongMillisSecond2String(calendar.getTimeInMillis());
	}
	/**
	 * 取当天日期的最后时刻(英文)
	 * 
	 * @return
	 */
	public String findDayEnd() {
		Calendar end = Calendar.getInstance();
		if (false) {
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 加1天
		// end.add(Calendar.DATE, 1);
		end.add(Calendar.DAY_OF_MONTH, 1);
		return doLongMillisSecond2String(end.getTimeInMillis());
	}
	/**
	 * 取当天日期的最后时刻(英文)
	 * 
	 * @return
	 */
	public String findDayEnd(int amount) {
		Calendar end = Calendar.getInstance();
		if (false) {
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 加1天
		// end.add(Calendar.DATE, 1);
		end.add(Calendar.DAY_OF_MONTH, 1);
		// 自定义,最后加上增减数字
		end.add(Calendar.DAY_OF_MONTH, amount);
		return doLongMillisSecond2String(end.getTimeInMillis());
	}
	/**
	 * 取当天日期的最后时刻(英文)
	 * 
	 * @param format
	 *            指定格式 指定格式
	 * @param amount
	 *            增减数字
	 * @return
	 */
	public String findDayEnd(int amount, String format) {
		Calendar end = Calendar.getInstance();
		if (false) {
			end.set(Calendar.HOUR_OF_DAY, 23);
			end.set(Calendar.MINUTE, 59);
			end.set(Calendar.SECOND, 59);
		}
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 加1天
		// end.add(Calendar.DATE, 1);
		end.add(Calendar.DAY_OF_MONTH, 1);
		// 自定义,最后加上增减数字
		end.add(Calendar.DAY_OF_MONTH, amount);
		return doLongMillisSecond2String(end.getTimeInMillis());
	}
	/**
	 * 取当周日期的最前时刻(英文)
	 * 
	 * @return
	 */
	public String findWeekStart() {
		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		// 从星期天开始算,初始化值value为1
		int day = start.get(Calendar.DAY_OF_WEEK);
		// Baselog.trace("第几天day=" + day);
		if (day == 1) {
			// 如果是星期天，减去6天,从上星期一算起
			start.add(Calendar.DAY_OF_MONTH, -6);
			return doLongMillisSecond2String(start.getTimeInMillis());
		} else {
			// 如果不是星期天，减去当天加上第2天,从这周的星期一算起
			start.add(Calendar.DAY_OF_MONTH, -day + 2);
			return doLongMillisSecond2String(start.getTimeInMillis());
		}
	}
	/**
	 * 取当周日期的最前时刻(英文)
	 * 
	 * @return
	 */
	public String findWeekStart(int amount) {
		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		// 从星期天开始算,初始化值value为1
		int day = start.get(Calendar.DAY_OF_WEEK);
		// Baselog.trace("第几天day=" + day);
		if (day == 1) {
			// 如果是星期天，减去6天,从上星期一算起
			start.add(Calendar.DAY_OF_MONTH, -6);
			// 自定义,最后加上增减数字
			start.add(Calendar.WEEK_OF_YEAR, amount);
			return doLongMillisSecond2String(start.getTimeInMillis());
		} else {
			// 如果不是星期天，减去当天加上第2天,从这周的星期一算起
			start.add(Calendar.DAY_OF_MONTH, -day + 2);
			// 自定义,最后加上增减数字
			start.add(Calendar.WEEK_OF_YEAR, amount);
			return doLongMillisSecond2String(start.getTimeInMillis());
		}
	}
	/**
	 * 取当周日期的最前时刻(英文)
	 * 
	 * @param format
	 *            指定格式
	 * @param amount
	 * @return
	 */
	public String findWeekStart(int amount, String format) {
		Calendar start = Calendar.getInstance();
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		// 从星期天开始算,初始化值value为1
		int day = start.get(Calendar.DAY_OF_WEEK);
		// Baselog.trace("第几天day=" + day);
		if (day == 1) {
			// 如果是星期天，减去6天,从上星期一算起
			start.add(Calendar.DAY_OF_MONTH, -6);
			// 自定义,最后加上增减数字
			start.add(Calendar.WEEK_OF_YEAR, amount);
			return doLongMillisSecond2String(start.getTimeInMillis());
		} else {
			// 如果不是星期天，减去当天加上第2天,从这周的星期一算起
			start.add(Calendar.DAY_OF_MONTH, -day + 2);
			// 自定义,最后加上增减数字
			start.add(Calendar.WEEK_OF_YEAR, amount);
			return doLongMillisSecond2String(start.getTimeInMillis(), format);
		}
	}
	/**
	 * 取当周日期的最后时刻(英文)
	 * 
	 * @return
	 */
	public String findWeekEnd() {
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 从星期天开始算,初始化值value为1
		int day = end.get(Calendar.DAY_OF_WEEK);
		// Baselog.trace("第几天day=" + day);
		if (day == 1) {
			// 如果是星期天，加上1天,从上星期一算起
			end.add(Calendar.DAY_OF_MONTH, 1);
			return doLongMillisSecond2String(end.getTimeInMillis());
		} else {
			// 如果不是星期天，减去当天加上第9天,从这周的星期一算起
			end.add(Calendar.DATE, -day + 9);
			return doLongMillisSecond2String(end.getTimeInMillis());
		}
	}
	/**
	 * 取当周日期的最后时刻(英文)
	 * 
	 * @return
	 */
	public String findWeekEnd(int amount) {
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 从星期天开始算,初始化值value为1
		int day = end.get(Calendar.DAY_OF_WEEK);
		// Baselog.trace("第几天day=" + day);
		if (day == 1) {
			// 如果是星期天，加上1天,从上星期一算起
			end.add(Calendar.DAY_OF_MONTH, 1);
			// 自定义,最后加上增减数字
			end.add(Calendar.WEEK_OF_YEAR, amount);
			return doLongMillisSecond2String(end.getTimeInMillis());
		} else {
			// 如果不是星期天，减去当天加上第9天,从这周的星期一算起
			end.add(Calendar.DATE, -day + 9);
			// 自定义,最后加上增减数字
			end.add(Calendar.WEEK_OF_YEAR, amount);
			return doLongMillisSecond2String(end.getTimeInMillis());
		}
	}
	/**
	 * 取当周日期的最后时刻(英文)
	 * 
	 * @param format
	 *            指定格式
	 * @param amount
	 * @return
	 */
	public String findWeekEnd(int amount, String format) {
		Calendar end = Calendar.getInstance();
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 从星期天开始算,初始化值value为1
		int day = end.get(Calendar.DAY_OF_WEEK);
		// Baselog.trace("第几天day=" + day);
		if (day == 1) {
			// 如果是星期天，加上1天,从上星期一算起
			end.add(Calendar.DAY_OF_MONTH, 1);
			// 自定义,最后加上增减数字
			end.add(Calendar.WEEK_OF_YEAR, amount);
			return doLongMillisSecond2String(end.getTimeInMillis());
		} else {
			// 如果不是星期天，减去当天加上第9天,从这周的星期一算起
			end.add(Calendar.DATE, -day + 9);
			// 自定义,最后加上增减数字
			end.add(Calendar.WEEK_OF_YEAR, amount);
			return doLongMillisSecond2String(end.getTimeInMillis(), format);
		}
	}
	/**
	 * 取当月日期的最前时刻(英文)
	 * 
	 * @return
	 */
	public String findMonthStart() {
		Calendar start = Calendar.getInstance();
		start.set(Calendar.DAY_OF_MONTH, 1);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		return doLongMillisSecond2String(start.getTimeInMillis());
	}
	/**
	 * 取当月日期的最前时刻(英文)
	 * 
	 * @return
	 */
	public String findMonthStart(int amount) {
		Calendar start = Calendar.getInstance();
		start.set(Calendar.DAY_OF_MONTH, 1);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		// 自定义,最后加上增减数字
		start.add(Calendar.MONTH, amount);
		return doLongMillisSecond2String(start.getTimeInMillis());
	}
	/**
	 * 取当月日期的最前时刻(英文)
	 * 
	 * @param format
	 *            指定格式
	 * @param amount
	 * @return
	 */
	public String findMonthStart(int amount, String format) {
		Calendar start = Calendar.getInstance();
		start.set(Calendar.DAY_OF_MONTH, 1);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		// 自定义,最后加上增减数字
		start.add(Calendar.MONTH, amount);
		return doLongMillisSecond2String(start.getTimeInMillis(), format);
	}
	/**
	 * 取当月日期的最后时刻(英文)
	 * 
	 * @return
	 */
	public String findMonthEnd() {
		Calendar end = Calendar.getInstance();
		end.set(Calendar.DAY_OF_MONTH, 1);
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 加1个月
		end.add(Calendar.MONTH, 1);
		return doLongMillisSecond2String(end.getTimeInMillis());
	}
	/**
	 * 取当月日期的最后时刻(英文)
	 * 
	 * @return
	 */
	public String findMonthEnd(int amount) {
		Calendar end = Calendar.getInstance();
		end.set(Calendar.DAY_OF_MONTH, 1);
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 加1个月
		end.add(Calendar.MONTH, 1);
		// 自定义,最后加上增减数字
		end.add(Calendar.MONTH, amount);
		return doLongMillisSecond2String(end.getTimeInMillis());
	}
	/**
	 * 取当月日期的最后时刻(英文)
	 * 
	 * @param format
	 *            指定格式
	 * @param amount
	 * @return
	 */
	public String findMonthEnd(int amount, String format) {
		Calendar end = Calendar.getInstance();
		end.set(Calendar.DAY_OF_MONTH, 1);
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 加1个月
		end.add(Calendar.MONTH, 1);
		// 自定义,最后加上增减数字
		end.add(Calendar.MONTH, amount);
		return doLongMillisSecond2String(end.getTimeInMillis(), format);
	}
	/**
	 * 取当年日期的最前时刻(英文)
	 * 
	 * @return
	 */
	public String findYearStart() {
		Calendar start = Calendar.getInstance();
		// 设置为1月份
		start.set(Calendar.MONTH, 0);
		start.set(Calendar.DAY_OF_MONTH, 1);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		return doLongMillisSecond2String(start.getTimeInMillis());
	}
	/**
	 * 取当年日期的最前时刻(英文)
	 * 
	 * 
	 * 自定义,最后加上增减数字
	 * 
	 * @return
	 */
	public String findYearStart(int amount) {
		Calendar start = Calendar.getInstance();
		// 设置为1月份
		start.set(Calendar.MONTH, 0);
		start.set(Calendar.DAY_OF_MONTH, 1);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		// 自定义,最后加上增减数字
		start.add(Calendar.YEAR, amount);
		return doLongMillisSecond2String(start.getTimeInMillis());
	}
	/**
	 * 取当年日期的最前时刻(英文)
	 * 
	 * 
	 * @param format
	 *            指定格式
	 * @param amount
	 * @return
	 */
	public String findYearStart(int amount, String format) {
		Calendar start = Calendar.getInstance();
		// 设置为1月份
		start.set(Calendar.MONTH, 0);
		start.set(Calendar.DAY_OF_MONTH, 1);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		// 自定义,最后加上增减数字
		start.add(Calendar.YEAR, amount);
		return doLongMillisSecond2String(start.getTimeInMillis(), format);
	}
	/**
	 * 取当年日期的最后时刻(英文)
	 * 
	 * @return
	 */
	public String findYearEnd() {
		Calendar end = Calendar.getInstance();
		// 设置为1月份
		end.set(Calendar.MONTH, 0);
		end.set(Calendar.DAY_OF_MONTH, 1);
		end.set(Calendar.HOUR_OF_DAY, 00);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 加1年
		end.add(Calendar.YEAR, 1);
		// end.add(Calendar.SECOND, -1);
		return doLongMillisSecond2String(end.getTimeInMillis());
	}
	/**
	 * 取当年日期的最后时刻(英文)
	 * 
	 * 自定义,最后加上增减数字
	 * 
	 * @return
	 */
	public String findYearEnd(int amount) {
		Calendar end = Calendar.getInstance();
		// 设置为1月份
		end.set(Calendar.MONTH, 0);
		end.set(Calendar.DAY_OF_MONTH, 1);
		end.set(Calendar.HOUR_OF_DAY, 00);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 加1年
		end.add(Calendar.YEAR, 1);
		// end.add(Calendar.SECOND, -1);
		// 自定义,最后加上增减数字
		end.add(Calendar.YEAR, amount);
		return doLongMillisSecond2String(end.getTimeInMillis());
	}
	/**
	 * 取当年日期的最后时刻(英文)
	 * 
	 * @param format
	 *            指定格式
	 * @param amount
	 * @return
	 */
	public String findYearEnd(int amount, String format) {
		Calendar end = Calendar.getInstance();
		// 设置为1月份
		end.set(Calendar.MONTH, 0);
		end.set(Calendar.DAY_OF_MONTH, 1);
		end.set(Calendar.HOUR_OF_DAY, 00);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		// 加1年
		end.add(Calendar.YEAR, 1);
		// end.add(Calendar.SECOND, -1);
		// 自定义,最后加上增减数字
		end.add(Calendar.YEAR, amount);
		return doLongMillisSecond2String(end.getTimeInMillis(), format);
	}
	/**
	 * 取得现在时间
	 * 
	 * @param format
	 *            指定格式 指定格式
	 * @return
	 */
	public String findNow2StringByFormat(String format) {
		Calendar calendar = Calendar.getInstance();
		String now = doUtilDate2String(calendar.getTime(), format);
		return now;
	}
	/**
	 * 取得现在时间(年月日时分秒;24小时制；英文;毫秒;)
	 * 
	 * 17个字符(去掉冒号);
	 * 
	 * @return
	 */
	public String findNow2String17(Long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		String now = doUtilDate2String17(calendar.getTime());
		return now;
	}

	/**
	 * 取得现在时间(年月日) 8个字符(去掉冒号);
	 * 
	 * @return
	 */
	public String findNow2String8() {
		Calendar calendar = Calendar.getInstance();
		String now = doUtilDate2String8(calendar.getTime());
		return now;
	}
	/**
	 * 取得现在时间(年月日时分秒;24小时制；英文;) 14个字符(去掉冒号);
	 * 
	 * @return
	 */
	public String findNow2String14() {
		Calendar calendar = Calendar.getInstance();
		String now = doUtilDate2String14(calendar.getTime());
		return now;
	}
	/**
	 * 取得现在时间(年月日时分秒;24小时制；英文;毫秒;) 17个字符(去掉冒号);
	 * 
	 * @return
	 */
	public String findNow2String17() {
		Calendar calendar = Calendar.getInstance();
		String now = doUtilDate2String17(calendar.getTime());
		return now;
	}
	/**
	 * 取得现在时间(年月日时分秒;24小时制；英文;)
	 * 
	 * @return
	 */
	public String findNow2String() {
		Calendar calendar = Calendar.getInstance();
		String now = doUtilDate2String(calendar.getTime());
		return now;
	}
	/**
	 * 取得现在时间(年月日时分秒;24小时制；英文;)
	 * 
	 * @return
	 */
	public String findNow2StringSSS() {
		Calendar calendar = Calendar.getInstance();
		String now = doUtilDate2StringSSS(calendar.getTime());
		return now;
	}
	/**
	 * 取得现在时间(年月日时分秒;24小时制；中文;)
	 * 
	 * @return
	 */
	public String findNow2StringCn() {
		Calendar calendar = Calendar.getInstance();
		String now = doUtilDate2StringCn(calendar.getTime());
		return now;
	}
}
