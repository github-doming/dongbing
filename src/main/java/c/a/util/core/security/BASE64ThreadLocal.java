package c.a.util.core.security;
public class BASE64ThreadLocal {
	private static ThreadLocal<BASE64Util> threadLocal = null;
	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<BASE64Util> findThreadLocal() {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<BASE64Util>();
		}
		return threadLocal;
	}
	// -- set与get --//
	public static ThreadLocal<BASE64Util> getThreadLocal() {
		return threadLocal;
	}
	public static void setThreadLocal(
			ThreadLocal<BASE64Util> threadLocalInput) {
		threadLocal = threadLocalInput;
	}
	// -- set与get --//
}
