package c.a.util.core.request;
/**
 * 
 *  Request工具类(单例)
 * 
 * @Description:
 * @date 2017年3月28日 下午6:18:58
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class RequestSingletonUtil extends RequestUtil{
	private static RequestUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private RequestSingletonUtil() {
	}
	public static RequestUtil findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new RequestUtil();
				}
			}
		}
		return instance;
	}
}
