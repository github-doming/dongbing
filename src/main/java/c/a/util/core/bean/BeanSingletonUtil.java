package c.a.util.core.bean;

/**
 * 
 * bean工具类(单例)
 * 
 * @Description:
 * @date 2017年3月28日 下午6:18:58
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class BeanSingletonUtil extends BeanUtil{
	private static BeanUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private BeanSingletonUtil() {
	}
	public static BeanUtil findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new BeanUtil();
				}
			}
		}
		return instance;
	}
}
