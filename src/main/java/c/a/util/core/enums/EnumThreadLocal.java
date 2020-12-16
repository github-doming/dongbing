package c.a.util.core.enums;



public class EnumThreadLocal {
	/**
	 * connection
	 */
	private static ThreadLocal<EnumUtil> threadLocal = null;

	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<EnumUtil> findThreadLocal() {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<EnumUtil>();
		}
		return threadLocal;
	}

	// -- set与get --//
	public static ThreadLocal<EnumUtil> getThreadLocal() {
		return threadLocal;
	}

	public static void setThreadLocal(
			ThreadLocal<EnumUtil> threadLocalInput) {
		threadLocal = threadLocalInput;
	}

	// -- set与get --//
}
