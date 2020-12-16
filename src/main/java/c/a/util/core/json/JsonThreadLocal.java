package c.a.util.core.json;
public class JsonThreadLocal {
	/**
	 * connection
	 */
	private static ThreadLocal<JsonUtil> threadLocal = null;
	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<JsonUtil> findThreadLocal() {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<JsonUtil>();
		}
		return threadLocal;
	}
	// -- set与get --//
	public static ThreadLocal<JsonUtil> getThreadLocal() {
		return threadLocal;
	}
	public static void setThreadLocal(
			ThreadLocal<JsonUtil> threadLocalInput) {
		threadLocal = threadLocalInput;
	}
	// -- set与get --//
	
	public static String bean2json(Object object) {
		return JsonThreadLocal.findThreadLocal().get().bean2json(object);
	}
	
	
	
}
