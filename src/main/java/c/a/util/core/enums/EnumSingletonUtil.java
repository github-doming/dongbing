package c.a.util.core.enums;
/**
 * 
 *  Enum工具类(单例)
 * 
 * @Description:
 * @date 2017年3月28日 下午6:18:58
 * @author cxy
 * @Email:
 * @Copyright
 * 
 */
public class EnumSingletonUtil extends EnumUtil{
	private static EnumUtil instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造函数
	 */
	private EnumSingletonUtil() {
	}
	public static EnumUtil findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new EnumUtil();
				}
			}
		}
		return instance;
	}
}
