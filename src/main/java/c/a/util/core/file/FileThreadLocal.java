package c.a.util.core.file;
public class FileThreadLocal {
	private static ThreadLocal<FileUtil> threadLocal = null;
	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<FileUtil> findThreadLocal() {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<FileUtil>();
		}
		return threadLocal;
	}
	// -- set与get --//
	public static ThreadLocal<FileUtil> getThreadLocal() {
		return threadLocal;
	}
	public static void setThreadLocal(
			ThreadLocal<FileUtil> threadLocalInput) {
		threadLocal = threadLocalInput;
	}
	// -- set与get --//
}
