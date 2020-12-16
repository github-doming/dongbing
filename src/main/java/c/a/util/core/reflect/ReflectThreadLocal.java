package c.a.util.core.reflect;
public class ReflectThreadLocal {
	private static ThreadLocal<ReflectUtil> threadLocal = null;
	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<ReflectUtil> findThreadLocal() {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<ReflectUtil>();
		}
		return threadLocal;
	}
	// -- set与get --//
	public static ThreadLocal<ReflectUtil> getThreadLocal() {
		return threadLocal;
	}
	public static void setThreadLocal(
			ThreadLocal<ReflectUtil> threadLocalInput) {
		threadLocal = threadLocalInput;
	}
	// -- set与get --//
}
