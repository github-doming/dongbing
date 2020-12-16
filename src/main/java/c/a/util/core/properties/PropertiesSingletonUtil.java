package c.a.util.core.properties;
/**
 * 
 *  Properties工具类(单例)
 * 
 * @Description:
 * @date 2017年3月28日 下午6:18:58
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class PropertiesSingletonUtil extends PropertiesUtil{
	private static PropertiesUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private PropertiesSingletonUtil() {
	}
	public static PropertiesUtil findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new PropertiesUtil();
				}
			}
		}
		return instance;
	}
}
