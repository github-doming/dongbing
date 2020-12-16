package c.a.config.core;
public class ContextThreadLocal {
	private static ThreadLocal<ContextUtil> threadLocal = null;
	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<ContextUtil> findThreadLocal() {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<ContextUtil>();
		}
		return threadLocal;
	}
	// -- set与get --//
	public static ThreadLocal<ContextUtil> getThreadLocal() {
		return threadLocal;
	}
	public static void setThreadLocal(
			ThreadLocal<ContextUtil> threadLocalInput) {
		threadLocal = threadLocalInput;
	}
	// -- set与get --//
}
