package c.a.util.core.date;
/**
 * 
 *  Date工具类(单例)
 * 
 * @Description:
 * @date 2017年3月28日 下午6:18:58
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class DateSingletonUtil extends DateUtil{
	private static DateUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private DateSingletonUtil() {
	}
	public static DateUtil findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new DateUtil();
				}
			}
		}
		return instance;
	}
}
