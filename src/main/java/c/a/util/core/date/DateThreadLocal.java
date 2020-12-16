package c.a.util.core.date;
/**
 * 日期工具线程类;
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
 * @date 2018年8月14日 上午10:00:14
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class DateThreadLocal {
	/**
	 * connection
	 */
	private static ThreadLocal<DateUtil> threadLocal = null;
	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<DateUtil> findThreadLocal() {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<DateUtil>();
		}
		return threadLocal;
	}
	// -- set与get --//
	public static ThreadLocal<DateUtil> getThreadLocal() {
		return threadLocal;
	}
	public static void setThreadLocal(
			ThreadLocal<DateUtil> threadLocalInput) {
		threadLocal = threadLocalInput;
	}
	// -- set与get --//
}
