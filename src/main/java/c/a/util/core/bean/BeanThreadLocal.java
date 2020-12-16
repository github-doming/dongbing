package c.a.util.core.bean;
import javax.servlet.http.HttpServletRequest;
public class BeanThreadLocal {
	private static ThreadLocal<BeanUtil> threadLocal = null;
	/**
	 * 
	 * @return
	 */
	public static ThreadLocal<BeanUtil> findThreadLocal() {
		if (threadLocal == null) {
			threadLocal = new ThreadLocal<BeanUtil>();
		}
		return threadLocal;
	}
	// -- set与get --//
	public static ThreadLocal<BeanUtil> getThreadLocal() {
		return threadLocal;
	}
	public static void setThreadLocal(ThreadLocal<BeanUtil> threadLocalInput) {
		threadLocal = threadLocalInput;
	}
	// -- set与get --//
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
	public static String find(Object objectSource, String strDefault) {
		BeanUtil beanUtil = findThreadLocal().get();
		if (beanUtil != null) {
			return beanUtil.find(objectSource, strDefault);
		} else {
			return null;
		}
	}
	public static Integer find(Object objectSource, Integer intDefault) {
		BeanUtil beanUtil = findThreadLocal().get();
		if (beanUtil != null) {
		return beanUtil.find(objectSource, intDefault);
		} else {
			return null;
		}
	}
	public static Long find(Object objectSource, Long longDefault) {
		BeanUtil beanUtil = findThreadLocal().get();
		if (beanUtil != null) {
		return beanUtil.find(objectSource, longDefault);
		} else {
			return null;
		}
	}
	/**
	 * 取值,空值则返回缺省值;
	 * 
	 * @Title: find
	 * @Description:
	 *
	 * 				参数说明
	 * @param request
	 * @param key
	 * @param strDefault
	 * @return 返回类型 String
	 */
	public static String find(HttpServletRequest request, String key, String strDefault) {
		BeanUtil beanUtil = findThreadLocal().get();
		if (beanUtil != null) {
		return beanUtil.find(request, key, strDefault);
		} else {
			return null;
		}
	}
	public static Integer find(HttpServletRequest request, String key, Integer intDefault) {
		BeanUtil beanUtil = findThreadLocal().get();
		if (beanUtil != null) {
		return beanUtil.find(request, key, intDefault);
		} else {
			return null;
		}
	}
}
