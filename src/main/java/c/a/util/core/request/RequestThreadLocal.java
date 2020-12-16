package c.a.util.core.request;
import javax.servlet.http.HttpServletRequest;
public class RequestThreadLocal {
	private static ThreadLocal<RequestUtil> threadLocal = null;
	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<RequestUtil> findThreadLocal() {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<RequestUtil>();
		}
		return threadLocal;
	}
	// -- set与get --//
	public static ThreadLocal<RequestUtil> getThreadLocal() {
		return threadLocal;
	}
	public static void setThreadLocal(ThreadLocal<RequestUtil> threadLocalInput) {
		threadLocal = threadLocalInput;
	}
	// -- set与get --//
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/

	/**
	 * 
	 * request.getParameterNames转成map;
	 * 
	 * 
	 * 
	 * 然后再转成实体类;
	 * 
	 * @param tableName
	 * @param classOrigin
	 * @param classDestination
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Object doRequest2Entity(Class classOrigin, Class classDestination, HttpServletRequest request)
			throws Exception {
		return RequestThreadLocal.findThreadLocal().get().doRequest2Entity(classOrigin, classDestination, request);
	}

	/**
	 * 
	 * request.getParameterNames转成map;
	 * 
	 * 
	 * 
	 * 然后再转成实体类;
	 * 
	 * @param tableName
	 * @param classOrigin
	 * @param classDestination
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static Object doRequest2EntityByJson(Class classOrigin, Class classDestination, HttpServletRequest request)
			throws Exception {
		return RequestThreadLocal.findThreadLocal().get().doRequest2EntityByJson(classOrigin, classDestination,
				request);
	}
}
