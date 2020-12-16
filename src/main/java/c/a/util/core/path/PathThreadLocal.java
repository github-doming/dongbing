package c.a.util.core.path;
public class PathThreadLocal {
	private static ThreadLocal<PathUtil> threadLocal = null;
	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<PathUtil> findThreadLocal() {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<PathUtil>();
		}
		return threadLocal;
	}
	// -- set与get --//
	public static ThreadLocal<PathUtil> getThreadLocal() {
		return threadLocal;
	}
	public static void setThreadLocal(
			ThreadLocal<PathUtil> threadLocalInput) {
		threadLocal = threadLocalInput;
	}
	// -- set与get --//
}
